package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.local.TargetData;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.mutation.VerifyMutation;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.ArrayValue;
import com.google.firestore.p012v1.BatchGetDocumentsResponse;
import com.google.firestore.p012v1.Cursor;
import com.google.firestore.p012v1.Document;
import com.google.firestore.p012v1.DocumentChange;
import com.google.firestore.p012v1.DocumentDelete;
import com.google.firestore.p012v1.DocumentMask;
import com.google.firestore.p012v1.DocumentRemove;
import com.google.firestore.p012v1.DocumentTransform;
import com.google.firestore.p012v1.ExistenceFilter;
import com.google.firestore.p012v1.ListenResponse;
import com.google.firestore.p012v1.Precondition;
import com.google.firestore.p012v1.StructuredQuery;
import com.google.firestore.p012v1.Target;
import com.google.firestore.p012v1.TargetChange;
import com.google.firestore.p012v1.Value;
import com.google.firestore.p012v1.Write;
import com.google.firestore.p012v1.WriteResult;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import p003io.grpc.Status;

public final class RemoteSerializer {
    private final DatabaseId databaseId;
    private final String databaseName;

    public RemoteSerializer(DatabaseId databaseId2) {
        this.databaseId = databaseId2;
        this.databaseName = encodedDatabaseId(databaseId2).canonicalString();
    }

    public Timestamp encodeTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp.Builder builder = Timestamp.newBuilder();
        builder.setSeconds(timestamp.getSeconds());
        builder.setNanos(timestamp.getNanoseconds());
        return (Timestamp) builder.build();
    }

    public com.google.firebase.Timestamp decodeTimestamp(Timestamp proto) {
        return new com.google.firebase.Timestamp(proto.getSeconds(), proto.getNanos());
    }

    public Timestamp encodeVersion(SnapshotVersion version) {
        return encodeTimestamp(version.getTimestamp());
    }

    public SnapshotVersion decodeVersion(Timestamp proto) {
        if (proto.getSeconds() == 0 && proto.getNanos() == 0) {
            return SnapshotVersion.NONE;
        }
        return new SnapshotVersion(decodeTimestamp(proto));
    }

    public String encodeKey(DocumentKey key) {
        return encodeResourceName(this.databaseId, key.getPath());
    }

    public DocumentKey decodeKey(String name) {
        ResourcePath resource = decodeResourceName(name);
        Assert.hardAssert(resource.getSegment(1).equals(this.databaseId.getProjectId()), "Tried to deserialize key from different project.", new Object[0]);
        Assert.hardAssert(resource.getSegment(3).equals(this.databaseId.getDatabaseId()), "Tried to deserialize key from different database.", new Object[0]);
        return DocumentKey.fromPath(extractLocalPathFromResourceName(resource));
    }

    private String encodeQueryPath(ResourcePath path) {
        return encodeResourceName(this.databaseId, path);
    }

    private ResourcePath decodeQueryPath(String name) {
        ResourcePath resource = decodeResourceName(name);
        if (resource.length() == 4) {
            return ResourcePath.EMPTY;
        }
        return extractLocalPathFromResourceName(resource);
    }

    private String encodeResourceName(DatabaseId databaseId2, ResourcePath path) {
        return ((ResourcePath) ((ResourcePath) encodedDatabaseId(databaseId2).append("documents")).append(path)).canonicalString();
    }

    private ResourcePath decodeResourceName(String encoded) {
        ResourcePath resource = ResourcePath.fromString(encoded);
        Assert.hardAssert(isValidResourceName(resource), "Tried to deserialize invalid key %s", resource);
        return resource;
    }

    private static ResourcePath encodedDatabaseId(DatabaseId databaseId2) {
        return ResourcePath.fromSegments(Arrays.asList(new String[]{"projects", databaseId2.getProjectId(), "databases", databaseId2.getDatabaseId()}));
    }

    private static ResourcePath extractLocalPathFromResourceName(ResourcePath resourceName) {
        Assert.hardAssert(resourceName.length() > 4 && resourceName.getSegment(4).equals("documents"), "Tried to deserialize invalid key %s", resourceName);
        return (ResourcePath) resourceName.popFirst(5);
    }

    private static boolean isValidResourceName(ResourcePath path) {
        if (path.length() < 4 || !path.getSegment(0).equals("projects") || !path.getSegment(2).equals("databases")) {
            return false;
        }
        return true;
    }

    public String databaseName() {
        return this.databaseName;
    }

    public Document encodeDocument(DocumentKey key, ObjectValue value) {
        Document.Builder builder = Document.newBuilder();
        builder.setName(encodeKey(key));
        builder.putAllFields(value.getFieldsMap());
        return (Document) builder.build();
    }

    public MaybeDocument decodeMaybeDocument(BatchGetDocumentsResponse response) {
        if (response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND)) {
            return decodeFoundDocument(response);
        }
        if (response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING)) {
            return decodeMissingDocument(response);
        }
        throw new IllegalArgumentException("Unknown result case: " + response.getResultCase());
    }

    private com.google.firebase.firestore.model.Document decodeFoundDocument(BatchGetDocumentsResponse response) {
        Assert.hardAssert(response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND), "Tried to deserialize a found document from a missing document.", new Object[0]);
        DocumentKey key = decodeKey(response.getFound().getName());
        ObjectValue value = ObjectValue.fromMap(response.getFound().getFieldsMap());
        SnapshotVersion version = decodeVersion(response.getFound().getUpdateTime());
        Assert.hardAssert(!version.equals(SnapshotVersion.NONE), "Got a document response with no snapshot version", new Object[0]);
        return new com.google.firebase.firestore.model.Document(key, version, value, Document.DocumentState.SYNCED);
    }

    private NoDocument decodeMissingDocument(BatchGetDocumentsResponse response) {
        Assert.hardAssert(response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING), "Tried to deserialize a missing document from a found document.", new Object[0]);
        DocumentKey key = decodeKey(response.getMissing());
        SnapshotVersion version = decodeVersion(response.getReadTime());
        Assert.hardAssert(!version.equals(SnapshotVersion.NONE), "Got a no document response with no snapshot version", new Object[0]);
        return new NoDocument(key, version, false);
    }

    public Write encodeMutation(Mutation mutation) {
        Write.Builder builder = Write.newBuilder();
        if (mutation instanceof SetMutation) {
            builder.setUpdate(encodeDocument(mutation.getKey(), ((SetMutation) mutation).getValue()));
        } else if (mutation instanceof PatchMutation) {
            builder.setUpdate(encodeDocument(mutation.getKey(), ((PatchMutation) mutation).getValue()));
            builder.setUpdateMask(encodeDocumentMask(((PatchMutation) mutation).getMask()));
        } else if (mutation instanceof TransformMutation) {
            TransformMutation transform = (TransformMutation) mutation;
            DocumentTransform.Builder transformBuilder = DocumentTransform.newBuilder();
            transformBuilder.setDocument(encodeKey(transform.getKey()));
            for (FieldTransform fieldTransform : transform.getFieldTransforms()) {
                transformBuilder.addFieldTransforms(encodeFieldTransform(fieldTransform));
            }
            builder.setTransform(transformBuilder);
        } else if (mutation instanceof DeleteMutation) {
            builder.setDelete(encodeKey(mutation.getKey()));
        } else if (mutation instanceof VerifyMutation) {
            builder.setVerify(encodeKey(mutation.getKey()));
        } else {
            throw Assert.fail("unknown mutation type %s", mutation.getClass());
        }
        if (!mutation.getPrecondition().isNone()) {
            builder.setCurrentDocument(encodePrecondition(mutation.getPrecondition()));
        }
        return (Write) builder.build();
    }

    public Mutation decodeMutation(Write mutation) {
        Precondition precondition;
        if (mutation.hasCurrentDocument()) {
            precondition = decodePrecondition(mutation.getCurrentDocument());
        } else {
            precondition = Precondition.NONE;
        }
        int i = C19201.$SwitchMap$com$google$firestore$v1$Write$OperationCase[mutation.getOperationCase().ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2) {
                return new DeleteMutation(decodeKey(mutation.getDelete()), precondition);
            }
            if (i == 3) {
                ArrayList<FieldTransform> fieldTransforms = new ArrayList<>();
                for (DocumentTransform.FieldTransform fieldTransform : mutation.getTransform().getFieldTransformsList()) {
                    fieldTransforms.add(decodeFieldTransform(fieldTransform));
                }
                Boolean exists = precondition.getExists();
                if (exists == null || !exists.booleanValue()) {
                    z = false;
                }
                Assert.hardAssert(z, "Transforms only support precondition \"exists == true\"", new Object[0]);
                return new TransformMutation(decodeKey(mutation.getTransform().getDocument()), fieldTransforms);
            } else if (i == 4) {
                return new VerifyMutation(decodeKey(mutation.getVerify()), precondition);
            } else {
                throw Assert.fail("Unknown mutation operation: %d", mutation.getOperationCase());
            }
        } else if (mutation.hasUpdateMask()) {
            return new PatchMutation(decodeKey(mutation.getUpdate().getName()), ObjectValue.fromMap(mutation.getUpdate().getFieldsMap()), decodeDocumentMask(mutation.getUpdateMask()), precondition);
        } else {
            return new SetMutation(decodeKey(mutation.getUpdate().getName()), ObjectValue.fromMap(mutation.getUpdate().getFieldsMap()), precondition);
        }
    }

    private com.google.firestore.p012v1.Precondition encodePrecondition(Precondition precondition) {
        Assert.hardAssert(!precondition.isNone(), "Can't serialize an empty precondition", new Object[0]);
        Precondition.Builder builder = com.google.firestore.p012v1.Precondition.newBuilder();
        if (precondition.getUpdateTime() != null) {
            return (com.google.firestore.p012v1.Precondition) builder.setUpdateTime(encodeVersion(precondition.getUpdateTime())).build();
        }
        if (precondition.getExists() != null) {
            return (com.google.firestore.p012v1.Precondition) builder.setExists(precondition.getExists().booleanValue()).build();
        }
        throw Assert.fail("Unknown Precondition", new Object[0]);
    }

    private com.google.firebase.firestore.model.mutation.Precondition decodePrecondition(com.google.firestore.p012v1.Precondition precondition) {
        int i = C19201.f437x8f18ca41[precondition.getConditionTypeCase().ordinal()];
        if (i == 1) {
            return com.google.firebase.firestore.model.mutation.Precondition.updateTime(decodeVersion(precondition.getUpdateTime()));
        }
        if (i == 2) {
            return com.google.firebase.firestore.model.mutation.Precondition.exists(precondition.getExists());
        }
        if (i == 3) {
            return com.google.firebase.firestore.model.mutation.Precondition.NONE;
        }
        throw Assert.fail("Unknown precondition", new Object[0]);
    }

    private DocumentMask encodeDocumentMask(FieldMask mask) {
        DocumentMask.Builder builder = DocumentMask.newBuilder();
        for (FieldPath path : mask.getMask()) {
            builder.addFieldPaths(path.canonicalString());
        }
        return (DocumentMask) builder.build();
    }

    private FieldMask decodeDocumentMask(DocumentMask mask) {
        int count = mask.getFieldPathsCount();
        Set<FieldPath> paths = new HashSet<>(count);
        for (int i = 0; i < count; i++) {
            paths.add(FieldPath.fromServerFormat(mask.getFieldPaths(i)));
        }
        return FieldMask.fromSet(paths);
    }

    private DocumentTransform.FieldTransform encodeFieldTransform(FieldTransform fieldTransform) {
        TransformOperation transform = fieldTransform.getOperation();
        if (transform instanceof ServerTimestampOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setSetToServerValue(DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME).build();
        }
        if (transform instanceof ArrayTransformOperation.Union) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setAppendMissingElements(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Union) transform).getElements())).build();
        }
        if (transform instanceof ArrayTransformOperation.Remove) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setRemoveAllFromArray(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Remove) transform).getElements())).build();
        }
        if (transform instanceof NumericIncrementTransformOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setIncrement(((NumericIncrementTransformOperation) transform).getOperand()).build();
        }
        throw Assert.fail("Unknown transform: %s", transform);
    }

    private FieldTransform decodeFieldTransform(DocumentTransform.FieldTransform fieldTransform) {
        int i = C19201.f435xdd498c9f[fieldTransform.getTransformTypeCase().ordinal()];
        if (i == 1) {
            Assert.hardAssert(fieldTransform.getSetToServerValue() == DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME, "Unknown transform setToServerValue: %s", fieldTransform.getSetToServerValue());
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), ServerTimestampOperation.getInstance());
        } else if (i == 2) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Union(fieldTransform.getAppendMissingElements().getValuesList()));
        } else {
            if (i == 3) {
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Remove(fieldTransform.getRemoveAllFromArray().getValuesList()));
            }
            if (i == 4) {
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new NumericIncrementTransformOperation(fieldTransform.getIncrement()));
            }
            throw Assert.fail("Unknown FieldTransform proto: %s", fieldTransform);
        }
    }

    public MutationResult decodeMutationResult(WriteResult proto, SnapshotVersion commitVersion) {
        SnapshotVersion version = decodeVersion(proto.getUpdateTime());
        if (SnapshotVersion.NONE.equals(version)) {
            version = commitVersion;
        }
        List<Value> transformResults = null;
        int transformResultsCount = proto.getTransformResultsCount();
        if (transformResultsCount > 0) {
            transformResults = new ArrayList<>(transformResultsCount);
            for (int i = 0; i < transformResultsCount; i++) {
                transformResults.add(proto.getTransformResults(i));
            }
        }
        return new MutationResult(version, transformResults);
    }

    public Map<String, String> encodeListenRequestLabels(TargetData targetData) {
        String value = encodeLabel(targetData.getPurpose());
        if (value == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>(1);
        result.put("goog-listen-tags", value);
        return result;
    }

    private String encodeLabel(QueryPurpose purpose) {
        int i = C19201.$SwitchMap$com$google$firebase$firestore$local$QueryPurpose[purpose.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            return "existence-filter-mismatch";
        }
        if (i == 3) {
            return "limbo-document";
        }
        throw Assert.fail("Unrecognized query purpose: %s", purpose);
    }

    public Target encodeTarget(TargetData targetData) {
        Target.Builder builder = Target.newBuilder();
        com.google.firebase.firestore.core.Target target = targetData.getTarget();
        if (target.isDocumentQuery()) {
            builder.setDocuments(encodeDocumentsTarget(target));
        } else {
            builder.setQuery(encodeQueryTarget(target));
        }
        builder.setTargetId(targetData.getTargetId());
        builder.setResumeToken(targetData.getResumeToken());
        return (Target) builder.build();
    }

    public Target.DocumentsTarget encodeDocumentsTarget(com.google.firebase.firestore.core.Target target) {
        Target.DocumentsTarget.Builder builder = Target.DocumentsTarget.newBuilder();
        builder.addDocuments(encodeQueryPath(target.getPath()));
        return (Target.DocumentsTarget) builder.build();
    }

    public com.google.firebase.firestore.core.Target decodeDocumentsTarget(Target.DocumentsTarget target) {
        int count = target.getDocumentsCount();
        Assert.hardAssert(count == 1, "DocumentsTarget contained other than 1 document %d", Integer.valueOf(count));
        return Query.atPath(decodeQueryPath(target.getDocuments(0))).toTarget();
    }

    public Target.QueryTarget encodeQueryTarget(com.google.firebase.firestore.core.Target target) {
        Target.QueryTarget.Builder builder = Target.QueryTarget.newBuilder();
        StructuredQuery.Builder structuredQueryBuilder = StructuredQuery.newBuilder();
        ResourcePath path = target.getPath();
        boolean z = true;
        if (target.getCollectionGroup() != null) {
            Assert.hardAssert(path.length() % 2 == 0, "Collection Group queries should be within a document path or root.", new Object[0]);
            builder.setParent(encodeQueryPath(path));
            StructuredQuery.CollectionSelector.Builder from = StructuredQuery.CollectionSelector.newBuilder();
            from.setCollectionId(target.getCollectionGroup());
            from.setAllDescendants(true);
            structuredQueryBuilder.addFrom(from);
        } else {
            if (path.length() % 2 == 0) {
                z = false;
            }
            Assert.hardAssert(z, "Document queries with filters are not supported.", new Object[0]);
            builder.setParent(encodeQueryPath((ResourcePath) path.popLast()));
            StructuredQuery.CollectionSelector.Builder from2 = StructuredQuery.CollectionSelector.newBuilder();
            from2.setCollectionId(path.getLastSegment());
            structuredQueryBuilder.addFrom(from2);
        }
        if (target.getFilters().size() > 0) {
            structuredQueryBuilder.setWhere(encodeFilters(target.getFilters()));
        }
        for (OrderBy orderBy : target.getOrderBy()) {
            structuredQueryBuilder.addOrderBy(encodeOrderBy(orderBy));
        }
        if (target.hasLimit()) {
            structuredQueryBuilder.setLimit(Int32Value.newBuilder().setValue((int) target.getLimit()));
        }
        if (target.getStartAt() != null) {
            structuredQueryBuilder.setStartAt(encodeBound(target.getStartAt()));
        }
        if (target.getEndAt() != null) {
            structuredQueryBuilder.setEndAt(encodeBound(target.getEndAt()));
        }
        builder.setStructuredQuery(structuredQueryBuilder);
        return (Target.QueryTarget) builder.build();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v2, resolved type: java.util.ArrayList} */
    /* JADX WARNING: type inference failed for: r6v14, types: [com.google.firebase.firestore.model.BasePath] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.firebase.firestore.core.Target decodeQueryTarget(com.google.firestore.p012v1.Target.QueryTarget r24) {
        /*
            r23 = this;
            r0 = r23
            java.lang.String r1 = r24.getParent()
            com.google.firebase.firestore.model.ResourcePath r1 = r0.decodeQueryPath(r1)
            com.google.firestore.v1.StructuredQuery r2 = r24.getStructuredQuery()
            r3 = 0
            int r4 = r2.getFromCount()
            if (r4 <= 0) goto L_0x003c
            r5 = 0
            r6 = 1
            if (r4 != r6) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            r6 = 0
        L_0x001b:
            java.lang.Object[] r7 = new java.lang.Object[r5]
            java.lang.String r8 = "StructuredQuery.from with more than one collection is not supported."
            com.google.firebase.firestore.util.Assert.hardAssert(r6, r8, r7)
            com.google.firestore.v1.StructuredQuery$CollectionSelector r5 = r2.getFrom(r5)
            boolean r6 = r5.getAllDescendants()
            if (r6 == 0) goto L_0x0031
            java.lang.String r3 = r5.getCollectionId()
            goto L_0x003c
        L_0x0031:
            java.lang.String r6 = r5.getCollectionId()
            com.google.firebase.firestore.model.BasePath r6 = r1.append((java.lang.String) r6)
            r1 = r6
            com.google.firebase.firestore.model.ResourcePath r1 = (com.google.firebase.firestore.model.ResourcePath) r1
        L_0x003c:
            boolean r5 = r2.hasWhere()
            if (r5 == 0) goto L_0x004b
            com.google.firestore.v1.StructuredQuery$Filter r5 = r2.getWhere()
            java.util.List r5 = r0.decodeFilters(r5)
            goto L_0x004f
        L_0x004b:
            java.util.List r5 = java.util.Collections.emptyList()
        L_0x004f:
            int r15 = r2.getOrderByCount()
            if (r15 <= 0) goto L_0x006e
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>(r15)
            r7 = 0
        L_0x005b:
            if (r7 >= r15) goto L_0x006b
            com.google.firestore.v1.StructuredQuery$Order r8 = r2.getOrderBy(r7)
            com.google.firebase.firestore.core.OrderBy r8 = r0.decodeOrderBy(r8)
            r6.add(r8)
            int r7 = r7 + 1
            goto L_0x005b
        L_0x006b:
            r16 = r6
            goto L_0x0074
        L_0x006e:
            java.util.List r6 = java.util.Collections.emptyList()
            r16 = r6
        L_0x0074:
            r6 = -1
            boolean r8 = r2.hasLimit()
            if (r8 == 0) goto L_0x0088
            com.google.protobuf.Int32Value r8 = r2.getLimit()
            int r8 = r8.getValue()
            long r6 = (long) r8
            r17 = r6
            goto L_0x008a
        L_0x0088:
            r17 = r6
        L_0x008a:
            r6 = 0
            boolean r7 = r2.hasStartAt()
            if (r7 == 0) goto L_0x009c
            com.google.firestore.v1.Cursor r7 = r2.getStartAt()
            com.google.firebase.firestore.core.Bound r6 = r0.decodeBound(r7)
            r19 = r6
            goto L_0x009e
        L_0x009c:
            r19 = r6
        L_0x009e:
            r6 = 0
            boolean r7 = r2.hasEndAt()
            if (r7 == 0) goto L_0x00b0
            com.google.firestore.v1.Cursor r7 = r2.getEndAt()
            com.google.firebase.firestore.core.Bound r6 = r0.decodeBound(r7)
            r20 = r6
            goto L_0x00b2
        L_0x00b0:
            r20 = r6
        L_0x00b2:
            com.google.firebase.firestore.core.Query r21 = new com.google.firebase.firestore.core.Query
            com.google.firebase.firestore.core.Query$LimitType r13 = com.google.firebase.firestore.core.Query.LimitType.LIMIT_TO_FIRST
            r6 = r21
            r7 = r1
            r8 = r3
            r9 = r5
            r10 = r16
            r11 = r17
            r14 = r19
            r22 = r15
            r15 = r20
            r6.<init>(r7, r8, r9, r10, r11, r13, r14, r15)
            com.google.firebase.firestore.core.Target r6 = r21.toTarget()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.decodeQueryTarget(com.google.firestore.v1.Target$QueryTarget):com.google.firebase.firestore.core.Target");
    }

    private StructuredQuery.Filter encodeFilters(List<Filter> filters) {
        List<StructuredQuery.Filter> protos = new ArrayList<>(filters.size());
        for (Filter filter : filters) {
            if (filter instanceof FieldFilter) {
                protos.add(encodeUnaryOrFieldFilter((FieldFilter) filter));
            }
        }
        if (filters.size() == 1) {
            return protos.get(0);
        }
        StructuredQuery.CompositeFilter.Builder composite = StructuredQuery.CompositeFilter.newBuilder();
        composite.setOp(StructuredQuery.CompositeFilter.Operator.AND);
        composite.addAllFilters(protos);
        return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setCompositeFilter(composite).build();
    }

    private List<Filter> decodeFilters(StructuredQuery.Filter proto) {
        List<StructuredQuery.Filter> filters;
        if (proto.getFilterTypeCase() == StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER) {
            Assert.hardAssert(proto.getCompositeFilter().getOp() == StructuredQuery.CompositeFilter.Operator.AND, "Only AND-type composite filters are supported, got %d", proto.getCompositeFilter().getOp());
            filters = proto.getCompositeFilter().getFiltersList();
        } else {
            filters = Collections.singletonList(proto);
        }
        List<Filter> result = new ArrayList<>(filters.size());
        for (StructuredQuery.Filter filter : filters) {
            int i = C19201.f439x9d2ee979[filter.getFilterTypeCase().ordinal()];
            if (i == 1) {
                throw Assert.fail("Nested composite filters are not supported.", new Object[0]);
            } else if (i == 2) {
                result.add(decodeFieldFilter(filter.getFieldFilter()));
            } else if (i == 3) {
                result.add(decodeUnaryFilter(filter.getUnaryFilter()));
            } else {
                throw Assert.fail("Unrecognized Filter.filterType %d", filter.getFilterTypeCase());
            }
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public StructuredQuery.Filter encodeUnaryOrFieldFilter(FieldFilter filter) {
        if (filter.getOperator() == Filter.Operator.EQUAL) {
            StructuredQuery.UnaryFilter.Builder unaryProto = StructuredQuery.UnaryFilter.newBuilder();
            unaryProto.setField(encodeFieldPath(filter.getField()));
            if (Values.isNanValue(filter.getValue())) {
                unaryProto.setOp(StructuredQuery.UnaryFilter.Operator.IS_NAN);
                return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setUnaryFilter(unaryProto).build();
            } else if (Values.isNullValue(filter.getValue())) {
                unaryProto.setOp(StructuredQuery.UnaryFilter.Operator.IS_NULL);
                return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setUnaryFilter(unaryProto).build();
            }
        }
        StructuredQuery.FieldFilter.Builder proto = StructuredQuery.FieldFilter.newBuilder();
        proto.setField(encodeFieldPath(filter.getField()));
        proto.setOp(encodeFieldFilterOperator(filter.getOperator()));
        proto.setValue(filter.getValue());
        return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setFieldFilter(proto).build();
    }

    /* access modifiers changed from: package-private */
    public FieldFilter decodeFieldFilter(StructuredQuery.FieldFilter proto) {
        return FieldFilter.create(FieldPath.fromServerFormat(proto.getField().getFieldPath()), decodeFieldFilterOperator(proto.getOp()), proto.getValue());
    }

    private Filter decodeUnaryFilter(StructuredQuery.UnaryFilter proto) {
        FieldPath fieldPath = FieldPath.fromServerFormat(proto.getField().getFieldPath());
        int i = C19201.f440xf473b456[proto.getOp().ordinal()];
        if (i == 1) {
            return FieldFilter.create(fieldPath, Filter.Operator.EQUAL, Values.NAN_VALUE);
        }
        if (i == 2) {
            return FieldFilter.create(fieldPath, Filter.Operator.EQUAL, Values.NULL_VALUE);
        }
        throw Assert.fail("Unrecognized UnaryFilter.operator %d", proto.getOp());
    }

    private StructuredQuery.FieldReference encodeFieldPath(FieldPath field) {
        return (StructuredQuery.FieldReference) StructuredQuery.FieldReference.newBuilder().setFieldPath(field.canonicalString()).build();
    }

    private StructuredQuery.FieldFilter.Operator encodeFieldFilterOperator(Filter.Operator operator) {
        switch (C19201.$SwitchMap$com$google$firebase$firestore$core$Filter$Operator[operator.ordinal()]) {
            case 1:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN;
            case 2:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case 3:
                return StructuredQuery.FieldFilter.Operator.EQUAL;
            case 4:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN;
            case 5:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case 6:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS;
            case 7:
                return StructuredQuery.FieldFilter.Operator.IN;
            case 8:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS_ANY;
            default:
                throw Assert.fail("Unknown operator %d", operator);
        }
    }

    private Filter.Operator decodeFieldFilterOperator(StructuredQuery.FieldFilter.Operator operator) {
        switch (C19201.f438xaf95d2b[operator.ordinal()]) {
            case 1:
                return Filter.Operator.LESS_THAN;
            case 2:
                return Filter.Operator.LESS_THAN_OR_EQUAL;
            case 3:
                return Filter.Operator.EQUAL;
            case 4:
                return Filter.Operator.GREATER_THAN_OR_EQUAL;
            case 5:
                return Filter.Operator.GREATER_THAN;
            case 6:
                return Filter.Operator.ARRAY_CONTAINS;
            case 7:
                return Filter.Operator.IN;
            case 8:
                return Filter.Operator.ARRAY_CONTAINS_ANY;
            default:
                throw Assert.fail("Unhandled FieldFilter.operator %d", operator);
        }
    }

    private StructuredQuery.Order encodeOrderBy(OrderBy orderBy) {
        StructuredQuery.Order.Builder proto = StructuredQuery.Order.newBuilder();
        if (orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) {
            proto.setDirection(StructuredQuery.Direction.ASCENDING);
        } else {
            proto.setDirection(StructuredQuery.Direction.DESCENDING);
        }
        proto.setField(encodeFieldPath(orderBy.getField()));
        return (StructuredQuery.Order) proto.build();
    }

    private OrderBy decodeOrderBy(StructuredQuery.Order proto) {
        OrderBy.Direction direction;
        FieldPath fieldPath = FieldPath.fromServerFormat(proto.getField().getFieldPath());
        int i = C19201.$SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[proto.getDirection().ordinal()];
        if (i == 1) {
            direction = OrderBy.Direction.ASCENDING;
        } else if (i == 2) {
            direction = OrderBy.Direction.DESCENDING;
        } else {
            throw Assert.fail("Unrecognized direction %d", proto.getDirection());
        }
        return OrderBy.getInstance(direction, fieldPath);
    }

    private Cursor encodeBound(Bound bound) {
        Cursor.Builder builder = Cursor.newBuilder();
        builder.addAllValues(bound.getPosition());
        builder.setBefore(bound.isBefore());
        return (Cursor) builder.build();
    }

    private Bound decodeBound(Cursor proto) {
        return new Bound(proto.getValuesList(), proto.getBefore());
    }

    /* renamed from: com.google.firebase.firestore.remote.RemoteSerializer$1 */
    static /* synthetic */ class C19201 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$local$QueryPurpose;

        /* renamed from: $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase */
        static final /* synthetic */ int[] f435xdd498c9f;

        /* renamed from: $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase */
        static final /* synthetic */ int[] f436x1837d9f;

        /* renamed from: $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase */
        static final /* synthetic */ int[] f437x8f18ca41;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction;

        /* renamed from: $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator */
        static final /* synthetic */ int[] f438xaf95d2b;

        /* renamed from: $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase */
        static final /* synthetic */ int[] f439x9d2ee979;

        /* renamed from: $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator */
        static final /* synthetic */ int[] f440xf473b456;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Write$OperationCase;

        static {
            int[] iArr = new int[ListenResponse.ResponseTypeCase.values().length];
            f436x1837d9f = iArr;
            try {
                iArr[ListenResponse.ResponseTypeCase.TARGET_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f436x1837d9f[ListenResponse.ResponseTypeCase.DOCUMENT_CHANGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f436x1837d9f[ListenResponse.ResponseTypeCase.DOCUMENT_DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f436x1837d9f[ListenResponse.ResponseTypeCase.DOCUMENT_REMOVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f436x1837d9f[ListenResponse.ResponseTypeCase.FILTER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f436x1837d9f[ListenResponse.ResponseTypeCase.RESPONSETYPE_NOT_SET.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            int[] iArr2 = new int[TargetChange.TargetChangeType.values().length];
            $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = iArr2;
            try {
                iArr2[TargetChange.TargetChangeType.NO_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.REMOVE.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.CURRENT.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.RESET.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.UNRECOGNIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e12) {
            }
            int[] iArr3 = new int[StructuredQuery.Direction.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = iArr3;
            try {
                iArr3[StructuredQuery.Direction.ASCENDING.ordinal()] = 1;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[StructuredQuery.Direction.DESCENDING.ordinal()] = 2;
            } catch (NoSuchFieldError e14) {
            }
            int[] iArr4 = new int[StructuredQuery.FieldFilter.Operator.values().length];
            f438xaf95d2b = iArr4;
            try {
                iArr4[StructuredQuery.FieldFilter.Operator.LESS_THAN.ordinal()] = 1;
            } catch (NoSuchFieldError e15) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError e16) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError e17) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 4;
            } catch (NoSuchFieldError e18) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.GREATER_THAN.ordinal()] = 5;
            } catch (NoSuchFieldError e19) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS.ordinal()] = 6;
            } catch (NoSuchFieldError e20) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.IN.ordinal()] = 7;
            } catch (NoSuchFieldError e21) {
            }
            try {
                f438xaf95d2b[StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS_ANY.ordinal()] = 8;
            } catch (NoSuchFieldError e22) {
            }
            int[] iArr5 = new int[Filter.Operator.values().length];
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = iArr5;
            try {
                iArr5[Filter.Operator.LESS_THAN.ordinal()] = 1;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN.ordinal()] = 4;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 5;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.ARRAY_CONTAINS.ordinal()] = 6;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.IN.ordinal()] = 7;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.ARRAY_CONTAINS_ANY.ordinal()] = 8;
            } catch (NoSuchFieldError e30) {
            }
            int[] iArr6 = new int[StructuredQuery.UnaryFilter.Operator.values().length];
            f440xf473b456 = iArr6;
            try {
                iArr6[StructuredQuery.UnaryFilter.Operator.IS_NAN.ordinal()] = 1;
            } catch (NoSuchFieldError e31) {
            }
            try {
                f440xf473b456[StructuredQuery.UnaryFilter.Operator.IS_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError e32) {
            }
            int[] iArr7 = new int[StructuredQuery.Filter.FilterTypeCase.values().length];
            f439x9d2ee979 = iArr7;
            try {
                iArr7[StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER.ordinal()] = 1;
            } catch (NoSuchFieldError e33) {
            }
            try {
                f439x9d2ee979[StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER.ordinal()] = 2;
            } catch (NoSuchFieldError e34) {
            }
            try {
                f439x9d2ee979[StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER.ordinal()] = 3;
            } catch (NoSuchFieldError e35) {
            }
            int[] iArr8 = new int[QueryPurpose.values().length];
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = iArr8;
            try {
                iArr8[QueryPurpose.LISTEN.ordinal()] = 1;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[QueryPurpose.EXISTENCE_FILTER_MISMATCH.ordinal()] = 2;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[QueryPurpose.LIMBO_RESOLUTION.ordinal()] = 3;
            } catch (NoSuchFieldError e38) {
            }
            int[] iArr9 = new int[DocumentTransform.FieldTransform.TransformTypeCase.values().length];
            f435xdd498c9f = iArr9;
            try {
                iArr9[DocumentTransform.FieldTransform.TransformTypeCase.SET_TO_SERVER_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError e39) {
            }
            try {
                f435xdd498c9f[DocumentTransform.FieldTransform.TransformTypeCase.APPEND_MISSING_ELEMENTS.ordinal()] = 2;
            } catch (NoSuchFieldError e40) {
            }
            try {
                f435xdd498c9f[DocumentTransform.FieldTransform.TransformTypeCase.REMOVE_ALL_FROM_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError e41) {
            }
            try {
                f435xdd498c9f[DocumentTransform.FieldTransform.TransformTypeCase.INCREMENT.ordinal()] = 4;
            } catch (NoSuchFieldError e42) {
            }
            int[] iArr10 = new int[Precondition.ConditionTypeCase.values().length];
            f437x8f18ca41 = iArr10;
            try {
                iArr10[Precondition.ConditionTypeCase.UPDATE_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError e43) {
            }
            try {
                f437x8f18ca41[Precondition.ConditionTypeCase.EXISTS.ordinal()] = 2;
            } catch (NoSuchFieldError e44) {
            }
            try {
                f437x8f18ca41[Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e45) {
            }
            int[] iArr11 = new int[Write.OperationCase.values().length];
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = iArr11;
            try {
                iArr11[Write.OperationCase.UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError e46) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Write$OperationCase[Write.OperationCase.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError e47) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Write$OperationCase[Write.OperationCase.TRANSFORM.ordinal()] = 3;
            } catch (NoSuchFieldError e48) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Write$OperationCase[Write.OperationCase.VERIFY.ordinal()] = 4;
            } catch (NoSuchFieldError e49) {
            }
        }
    }

    public WatchChange decodeWatchChange(ListenResponse protoChange) {
        WatchChange.WatchTargetChangeType changeType;
        int i = C19201.f436x1837d9f[protoChange.getResponseTypeCase().ordinal()];
        if (i == 1) {
            TargetChange targetChange = protoChange.getTargetChange();
            Status cause = null;
            int i2 = C19201.$SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[targetChange.getTargetChangeType().ordinal()];
            if (i2 == 1) {
                changeType = WatchChange.WatchTargetChangeType.NoChange;
            } else if (i2 == 2) {
                changeType = WatchChange.WatchTargetChangeType.Added;
            } else if (i2 == 3) {
                changeType = WatchChange.WatchTargetChangeType.Removed;
                cause = fromStatus(targetChange.getCause());
            } else if (i2 == 4) {
                changeType = WatchChange.WatchTargetChangeType.Current;
            } else if (i2 == 5) {
                changeType = WatchChange.WatchTargetChangeType.Reset;
            } else {
                throw new IllegalArgumentException("Unknown target change type");
            }
            return new WatchChange.WatchTargetChange(changeType, targetChange.getTargetIdsList(), targetChange.getResumeToken(), cause);
        } else if (i == 2) {
            DocumentChange docChange = protoChange.getDocumentChange();
            List<Integer> added = docChange.getTargetIdsList();
            List<Integer> removed = docChange.getRemovedTargetIdsList();
            DocumentKey key = decodeKey(docChange.getDocument().getName());
            SnapshotVersion version = decodeVersion(docChange.getDocument().getUpdateTime());
            Assert.hardAssert(true ^ version.equals(SnapshotVersion.NONE), "Got a document change without an update time", new Object[0]);
            com.google.firebase.firestore.model.Document document = new com.google.firebase.firestore.model.Document(key, version, ObjectValue.fromMap(docChange.getDocument().getFieldsMap()), Document.DocumentState.SYNCED);
            return new WatchChange.DocumentChange(added, removed, document.getKey(), document);
        } else if (i == 3) {
            DocumentDelete docDelete = protoChange.getDocumentDelete();
            List<Integer> removed2 = docDelete.getRemovedTargetIdsList();
            NoDocument doc = new NoDocument(decodeKey(docDelete.getDocument()), decodeVersion(docDelete.getReadTime()), false);
            return new WatchChange.DocumentChange(Collections.emptyList(), removed2, doc.getKey(), doc);
        } else if (i == 4) {
            DocumentRemove docRemove = protoChange.getDocumentRemove();
            return new WatchChange.DocumentChange(Collections.emptyList(), docRemove.getRemovedTargetIdsList(), decodeKey(docRemove.getDocument()), (MaybeDocument) null);
        } else if (i == 5) {
            ExistenceFilter protoFilter = protoChange.getFilter();
            return new WatchChange.ExistenceFilterWatchChange(protoFilter.getTargetId(), new ExistenceFilter(protoFilter.getCount()));
        } else {
            throw new IllegalArgumentException("Unknown change type set");
        }
    }

    public SnapshotVersion decodeVersionFromListenResponse(ListenResponse watchChange) {
        if (watchChange.getResponseTypeCase() != ListenResponse.ResponseTypeCase.TARGET_CHANGE) {
            return SnapshotVersion.NONE;
        }
        if (watchChange.getTargetChange().getTargetIdsCount() != 0) {
            return SnapshotVersion.NONE;
        }
        return decodeVersion(watchChange.getTargetChange().getReadTime());
    }

    private Status fromStatus(com.google.rpc.Status status) {
        return Status.fromCodeValue(status.getCode()).withDescription(status.getMessage());
    }
}
