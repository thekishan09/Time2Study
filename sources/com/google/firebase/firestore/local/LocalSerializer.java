package com.google.firebase.firestore.local;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.proto.MaybeDocument;
import com.google.firebase.firestore.proto.NoDocument;
import com.google.firebase.firestore.proto.Target;
import com.google.firebase.firestore.proto.UnknownDocument;
import com.google.firebase.firestore.proto.WriteBatch;
import com.google.firebase.firestore.remote.RemoteSerializer;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Document;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

public final class LocalSerializer {
    private final RemoteSerializer rpcSerializer;

    public LocalSerializer(RemoteSerializer rpcSerializer2) {
        this.rpcSerializer = rpcSerializer2;
    }

    /* access modifiers changed from: package-private */
    public MaybeDocument encodeMaybeDocument(com.google.firebase.firestore.model.MaybeDocument document) {
        MaybeDocument.Builder builder = MaybeDocument.newBuilder();
        if (document instanceof NoDocument) {
            NoDocument noDocument = (NoDocument) document;
            builder.setNoDocument(encodeNoDocument(noDocument));
            builder.setHasCommittedMutations(noDocument.hasCommittedMutations());
        } else if (document instanceof Document) {
            Document existingDocument = (Document) document;
            builder.setDocument(encodeDocument(existingDocument));
            builder.setHasCommittedMutations(existingDocument.hasCommittedMutations());
        } else if (document instanceof UnknownDocument) {
            builder.setUnknownDocument(encodeUnknownDocument((UnknownDocument) document));
            builder.setHasCommittedMutations(true);
        } else {
            throw Assert.fail("Unknown document type %s", document.getClass().getCanonicalName());
        }
        return (MaybeDocument) builder.build();
    }

    /* access modifiers changed from: package-private */
    public com.google.firebase.firestore.model.MaybeDocument decodeMaybeDocument(MaybeDocument proto) {
        int i = C18981.f415xe45654f0[proto.getDocumentTypeCase().ordinal()];
        if (i == 1) {
            return decodeDocument(proto.getDocument(), proto.getHasCommittedMutations());
        }
        if (i == 2) {
            return decodeNoDocument(proto.getNoDocument(), proto.getHasCommittedMutations());
        }
        if (i == 3) {
            return decodeUnknownDocument(proto.getUnknownDocument());
        }
        throw Assert.fail("Unknown MaybeDocument %s", proto);
    }

    private com.google.firestore.p012v1.Document encodeDocument(Document document) {
        Document.Builder builder = com.google.firestore.p012v1.Document.newBuilder();
        builder.setName(this.rpcSerializer.encodeKey(document.getKey()));
        builder.putAllFields(document.getData().getFieldsMap());
        builder.setUpdateTime(this.rpcSerializer.encodeTimestamp(document.getVersion().getTimestamp()));
        return (com.google.firestore.p012v1.Document) builder.build();
    }

    private com.google.firebase.firestore.model.Document decodeDocument(com.google.firestore.p012v1.Document document, boolean hasCommittedMutations) {
        Document.DocumentState documentState;
        DocumentKey key = this.rpcSerializer.decodeKey(document.getName());
        SnapshotVersion version = this.rpcSerializer.decodeVersion(document.getUpdateTime());
        ObjectValue fromMap = ObjectValue.fromMap(document.getFieldsMap());
        if (hasCommittedMutations) {
            documentState = Document.DocumentState.COMMITTED_MUTATIONS;
        } else {
            documentState = Document.DocumentState.SYNCED;
        }
        return new com.google.firebase.firestore.model.Document(key, version, fromMap, documentState);
    }

    private com.google.firebase.firestore.proto.NoDocument encodeNoDocument(NoDocument document) {
        NoDocument.Builder builder = com.google.firebase.firestore.proto.NoDocument.newBuilder();
        builder.setName(this.rpcSerializer.encodeKey(document.getKey()));
        builder.setReadTime(this.rpcSerializer.encodeTimestamp(document.getVersion().getTimestamp()));
        return (com.google.firebase.firestore.proto.NoDocument) builder.build();
    }

    private com.google.firebase.firestore.model.NoDocument decodeNoDocument(com.google.firebase.firestore.proto.NoDocument proto, boolean hasCommittedMutations) {
        return new com.google.firebase.firestore.model.NoDocument(this.rpcSerializer.decodeKey(proto.getName()), this.rpcSerializer.decodeVersion(proto.getReadTime()), hasCommittedMutations);
    }

    private com.google.firebase.firestore.proto.UnknownDocument encodeUnknownDocument(UnknownDocument document) {
        UnknownDocument.Builder builder = com.google.firebase.firestore.proto.UnknownDocument.newBuilder();
        builder.setName(this.rpcSerializer.encodeKey(document.getKey()));
        builder.setVersion(this.rpcSerializer.encodeTimestamp(document.getVersion().getTimestamp()));
        return (com.google.firebase.firestore.proto.UnknownDocument) builder.build();
    }

    private com.google.firebase.firestore.model.UnknownDocument decodeUnknownDocument(com.google.firebase.firestore.proto.UnknownDocument proto) {
        return new com.google.firebase.firestore.model.UnknownDocument(this.rpcSerializer.decodeKey(proto.getName()), this.rpcSerializer.decodeVersion(proto.getVersion()));
    }

    /* access modifiers changed from: package-private */
    public WriteBatch encodeMutationBatch(MutationBatch batch) {
        WriteBatch.Builder result = WriteBatch.newBuilder();
        result.setBatchId(batch.getBatchId());
        result.setLocalWriteTime(this.rpcSerializer.encodeTimestamp(batch.getLocalWriteTime()));
        for (Mutation mutation : batch.getBaseMutations()) {
            result.addBaseWrites(this.rpcSerializer.encodeMutation(mutation));
        }
        for (Mutation mutation2 : batch.getMutations()) {
            result.addWrites(this.rpcSerializer.encodeMutation(mutation2));
        }
        return (WriteBatch) result.build();
    }

    /* access modifiers changed from: package-private */
    public MutationBatch decodeMutationBatch(WriteBatch batch) {
        int batchId = batch.getBatchId();
        Timestamp localWriteTime = this.rpcSerializer.decodeTimestamp(batch.getLocalWriteTime());
        int baseMutationsCount = batch.getBaseWritesCount();
        List<Mutation> baseMutations = new ArrayList<>(baseMutationsCount);
        for (int i = 0; i < baseMutationsCount; i++) {
            baseMutations.add(this.rpcSerializer.decodeMutation(batch.getBaseWrites(i)));
        }
        int mutationsCount = batch.getWritesCount();
        List<Mutation> mutations = new ArrayList<>(mutationsCount);
        for (int i2 = 0; i2 < mutationsCount; i2++) {
            mutations.add(this.rpcSerializer.decodeMutation(batch.getWrites(i2)));
        }
        return new MutationBatch(batchId, localWriteTime, baseMutations, mutations);
    }

    /* access modifiers changed from: package-private */
    public Target encodeTargetData(TargetData targetData) {
        Assert.hardAssert(QueryPurpose.LISTEN.equals(targetData.getPurpose()), "Only queries with purpose %s may be stored, got %s", QueryPurpose.LISTEN, targetData.getPurpose());
        Target.Builder result = Target.newBuilder();
        result.setTargetId(targetData.getTargetId()).setLastListenSequenceNumber(targetData.getSequenceNumber()).setLastLimboFreeSnapshotVersion(this.rpcSerializer.encodeVersion(targetData.getLastLimboFreeSnapshotVersion())).setSnapshotVersion(this.rpcSerializer.encodeVersion(targetData.getSnapshotVersion())).setResumeToken(targetData.getResumeToken());
        com.google.firebase.firestore.core.Target target = targetData.getTarget();
        if (target.isDocumentQuery()) {
            result.setDocuments(this.rpcSerializer.encodeDocumentsTarget(target));
        } else {
            result.setQuery(this.rpcSerializer.encodeQueryTarget(target));
        }
        return (Target) result.build();
    }

    /* access modifiers changed from: package-private */
    public TargetData decodeTargetData(Target targetProto) {
        com.google.firebase.firestore.core.Target target;
        int targetId = targetProto.getTargetId();
        SnapshotVersion version = this.rpcSerializer.decodeVersion(targetProto.getSnapshotVersion());
        SnapshotVersion lastLimboFreeSnapshotVersion = this.rpcSerializer.decodeVersion(targetProto.getLastLimboFreeSnapshotVersion());
        ByteString resumeToken = targetProto.getResumeToken();
        long sequenceNumber = targetProto.getLastListenSequenceNumber();
        int i = C18981.f416x5167ea64[targetProto.getTargetTypeCase().ordinal()];
        if (i == 1) {
            target = this.rpcSerializer.decodeDocumentsTarget(targetProto.getDocuments());
        } else if (i == 2) {
            target = this.rpcSerializer.decodeQueryTarget(targetProto.getQuery());
        } else {
            throw Assert.fail("Unknown targetType %d", targetProto.getTargetTypeCase());
        }
        return new TargetData(target, targetId, sequenceNumber, QueryPurpose.LISTEN, version, lastLimboFreeSnapshotVersion, resumeToken);
    }

    /* renamed from: com.google.firebase.firestore.local.LocalSerializer$1 */
    static /* synthetic */ class C18981 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase */
        static final /* synthetic */ int[] f415xe45654f0;

        /* renamed from: $SwitchMap$com$google$firebase$firestore$proto$Target$TargetTypeCase */
        static final /* synthetic */ int[] f416x5167ea64;

        static {
            int[] iArr = new int[Target.TargetTypeCase.values().length];
            f416x5167ea64 = iArr;
            try {
                iArr[Target.TargetTypeCase.DOCUMENTS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f416x5167ea64[Target.TargetTypeCase.QUERY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[MaybeDocument.DocumentTypeCase.values().length];
            f415xe45654f0 = iArr2;
            try {
                iArr2[MaybeDocument.DocumentTypeCase.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f415xe45654f0[MaybeDocument.DocumentTypeCase.NO_DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f415xe45654f0[MaybeDocument.DocumentTypeCase.UNKNOWN_DOCUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
        }
    }
}
