package com.google.firebase.firestore;

import android.app.Activity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.ListenerRegistrationImpl;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.ServerTimestamps;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.p012v1.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Query {
    final FirebaseFirestore firestore;
    final com.google.firebase.firestore.core.Query query;

    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    Query(com.google.firebase.firestore.core.Query query2, FirebaseFirestore firestore2) {
        this.query = (com.google.firebase.firestore.core.Query) Preconditions.checkNotNull(query2);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firestore2);
    }

    public FirebaseFirestore getFirestore() {
        return this.firestore;
    }

    public Query whereEqualTo(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.EQUAL, value);
    }

    public Query whereEqualTo(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.EQUAL, value);
    }

    public Query whereLessThan(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.LESS_THAN, value);
    }

    public Query whereLessThan(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.LESS_THAN, value);
    }

    public Query whereLessThanOrEqualTo(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.LESS_THAN_OR_EQUAL, value);
    }

    public Query whereLessThanOrEqualTo(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.LESS_THAN_OR_EQUAL, value);
    }

    public Query whereGreaterThan(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.GREATER_THAN, value);
    }

    public Query whereGreaterThan(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.GREATER_THAN, value);
    }

    public Query whereGreaterThanOrEqualTo(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.GREATER_THAN_OR_EQUAL, value);
    }

    public Query whereGreaterThanOrEqualTo(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.GREATER_THAN_OR_EQUAL, value);
    }

    public Query whereArrayContains(String field, Object value) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.ARRAY_CONTAINS, value);
    }

    public Query whereArrayContains(FieldPath fieldPath, Object value) {
        return whereHelper(fieldPath, Filter.Operator.ARRAY_CONTAINS, value);
    }

    public Query whereArrayContainsAny(String field, List<? extends Object> values) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.ARRAY_CONTAINS_ANY, values);
    }

    public Query whereArrayContainsAny(FieldPath fieldPath, List<? extends Object> values) {
        return whereHelper(fieldPath, Filter.Operator.ARRAY_CONTAINS_ANY, values);
    }

    public Query whereIn(String field, List<? extends Object> values) {
        return whereHelper(FieldPath.fromDotSeparatedPath(field), Filter.Operator.IN, values);
    }

    public Query whereIn(FieldPath fieldPath, List<? extends Object> values) {
        return whereHelper(fieldPath, Filter.Operator.IN, values);
    }

    /* JADX WARNING: type inference failed for: r2v13, types: [com.google.protobuf.GeneratedMessageLite] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.firebase.firestore.Query whereHelper(com.google.firebase.firestore.FieldPath r7, com.google.firebase.firestore.core.Filter.Operator r8, java.lang.Object r9) {
        /*
            r6 = this;
            java.lang.String r0 = "Provided field path must not be null."
            com.google.firebase.firestore.util.Preconditions.checkNotNull(r7, r0)
            java.lang.String r0 = "Provided op must not be null."
            com.google.firebase.firestore.util.Preconditions.checkNotNull(r8, r0)
            com.google.firebase.firestore.model.FieldPath r0 = r7.getInternalPath()
            boolean r1 = r0.isKeyField()
            if (r1 == 0) goto L_0x0075
            com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS
            if (r8 == r1) goto L_0x0055
            com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS_ANY
            if (r8 == r1) goto L_0x0055
            com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.IN
            if (r8 != r1) goto L_0x0050
            r6.validateDisjunctiveFilterElements(r9, r8)
            com.google.firestore.v1.ArrayValue$Builder r1 = com.google.firestore.p012v1.ArrayValue.newBuilder()
            r2 = r9
            java.util.List r2 = (java.util.List) r2
            java.util.Iterator r2 = r2.iterator()
        L_0x002e:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0040
            java.lang.Object r3 = r2.next()
            com.google.firestore.v1.Value r4 = r6.parseDocumentIdValue(r3)
            r1.addValues((com.google.firestore.p012v1.Value) r4)
            goto L_0x002e
        L_0x0040:
            com.google.firestore.v1.Value$Builder r2 = com.google.firestore.p012v1.Value.newBuilder()
            com.google.firestore.v1.Value$Builder r2 = r2.setArrayValue((com.google.firestore.p012v1.ArrayValue.Builder) r1)
            com.google.protobuf.GeneratedMessageLite r2 = r2.build()
            r1 = r2
            com.google.firestore.v1.Value r1 = (com.google.firestore.p012v1.Value) r1
            goto L_0x0091
        L_0x0050:
            com.google.firestore.v1.Value r1 = r6.parseDocumentIdValue(r9)
            goto L_0x0091
        L_0x0055:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Invalid query. You can't perform '"
            r2.append(r3)
            java.lang.String r3 = r8.toString()
            r2.append(r3)
            java.lang.String r3 = "' queries on FieldPath.documentId()."
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0075:
            com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.IN
            if (r8 == r1) goto L_0x007d
            com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS_ANY
            if (r8 != r1) goto L_0x0080
        L_0x007d:
            r6.validateDisjunctiveFilterElements(r9, r8)
        L_0x0080:
            com.google.firebase.firestore.FirebaseFirestore r1 = r6.firestore
            com.google.firebase.firestore.UserDataReader r1 = r1.getUserDataReader()
            com.google.firebase.firestore.core.Filter$Operator r2 = com.google.firebase.firestore.core.Filter.Operator.IN
            if (r8 != r2) goto L_0x008c
            r2 = 1
            goto L_0x008d
        L_0x008c:
            r2 = 0
        L_0x008d:
            com.google.firestore.v1.Value r1 = r1.parseQueryValue(r9, r2)
        L_0x0091:
            com.google.firebase.firestore.model.FieldPath r2 = r7.getInternalPath()
            com.google.firebase.firestore.core.FieldFilter r2 = com.google.firebase.firestore.core.FieldFilter.create(r2, r8, r1)
            r6.validateNewFilter(r2)
            com.google.firebase.firestore.Query r3 = new com.google.firebase.firestore.Query
            com.google.firebase.firestore.core.Query r4 = r6.query
            com.google.firebase.firestore.core.Query r4 = r4.filter(r2)
            com.google.firebase.firestore.FirebaseFirestore r5 = r6.firestore
            r3.<init>(r4, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.Query.whereHelper(com.google.firebase.firestore.FieldPath, com.google.firebase.firestore.core.Filter$Operator, java.lang.Object):com.google.firebase.firestore.Query");
    }

    private void validateOrderByField(FieldPath field) {
        FieldPath inequalityField = this.query.inequalityField();
        if (this.query.getFirstOrderByField() == null && inequalityField != null) {
            validateOrderByFieldMatchesInequality(field, inequalityField);
        }
    }

    private Value parseDocumentIdValue(Object documentIdValue) {
        if (documentIdValue instanceof String) {
            String documentId = (String) documentIdValue;
            if (documentId.isEmpty()) {
                throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid document ID, but it was an empty string.");
            } else if (this.query.isCollectionGroupQuery() || !documentId.contains("/")) {
                ResourcePath path = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(documentId));
                if (DocumentKey.isDocumentKey(path)) {
                    return Values.refValue(getFirestore().getDatabaseId(), DocumentKey.fromPath(path));
                }
                throw new IllegalArgumentException("Invalid query. When querying a collection group by FieldPath.documentId(), the value provided must result in a valid document path, but '" + path + "' is not because it has an odd number of segments (" + path.length() + ").");
            } else {
                throw new IllegalArgumentException("Invalid query. When querying a collection by FieldPath.documentId() you must provide a plain document ID, but '" + documentId + "' contains a '/' character.");
            }
        } else if (documentIdValue instanceof DocumentReference) {
            return Values.refValue(getFirestore().getDatabaseId(), ((DocumentReference) documentIdValue).getKey());
        } else {
            throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid String or DocumentReference, but it was of type: " + Util.typeName(documentIdValue));
        }
    }

    private void validateDisjunctiveFilterElements(Object value, Filter.Operator op) {
        if (!(value instanceof List) || ((List) value).size() == 0) {
            throw new IllegalArgumentException("Invalid Query. A non-empty array is required for '" + op.toString() + "' filters.");
        } else if (((List) value).size() > 10) {
            throw new IllegalArgumentException("Invalid Query. '" + op.toString() + "' filters support a maximum of 10 elements in the value array.");
        } else if (((List) value).contains((Object) null)) {
            throw new IllegalArgumentException("Invalid Query. '" + op.toString() + "' filters cannot contain 'null' in the value array.");
        } else if (((List) value).contains(Double.valueOf(Double.NaN)) || ((List) value).contains(Float.valueOf(Float.NaN))) {
            throw new IllegalArgumentException("Invalid Query. '" + op.toString() + "' filters cannot contain 'NaN' in the value array.");
        }
    }

    private void validateOrderByFieldMatchesInequality(FieldPath orderBy, FieldPath inequality) {
        if (!orderBy.equals(inequality)) {
            String inequalityString = inequality.canonicalString();
            throw new IllegalArgumentException(String.format("Invalid query. You have an inequality where filter (whereLessThan(), whereGreaterThan(), etc.) on field '%s' and so you must also have '%s' as your first orderBy() field, but your first orderBy() is currently on field '%s' instead.", new Object[]{inequalityString, inequalityString, orderBy.canonicalString()}));
        }
    }

    private void validateNewFilter(Filter filter) {
        if (filter instanceof FieldFilter) {
            FieldFilter fieldFilter = (FieldFilter) filter;
            Filter.Operator filterOp = fieldFilter.getOperator();
            List<Filter.Operator> arrayOps = Arrays.asList(new Filter.Operator[]{Filter.Operator.ARRAY_CONTAINS, Filter.Operator.ARRAY_CONTAINS_ANY});
            List<Filter.Operator> disjunctiveOps = Arrays.asList(new Filter.Operator[]{Filter.Operator.ARRAY_CONTAINS_ANY, Filter.Operator.IN});
            boolean isArrayOp = arrayOps.contains(filterOp);
            boolean isDisjunctiveOp = disjunctiveOps.contains(filterOp);
            if (fieldFilter.isInequality()) {
                FieldPath existingInequality = this.query.inequalityField();
                FieldPath newInequality = filter.getField();
                if (existingInequality == null || existingInequality.equals(newInequality)) {
                    FieldPath firstOrderByField = this.query.getFirstOrderByField();
                    if (firstOrderByField != null) {
                        validateOrderByFieldMatchesInequality(firstOrderByField, newInequality);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException(String.format("All where filters other than whereEqualTo() must be on the same field. But you have filters on '%s' and '%s'", new Object[]{existingInequality.canonicalString(), newInequality.canonicalString()}));
            } else if (isDisjunctiveOp || isArrayOp) {
                Filter.Operator conflictingOp = null;
                if (isDisjunctiveOp) {
                    conflictingOp = this.query.findFilterOperator(disjunctiveOps);
                }
                if (conflictingOp == null && isArrayOp) {
                    conflictingOp = this.query.findFilterOperator(arrayOps);
                }
                if (conflictingOp == null) {
                    return;
                }
                if (conflictingOp == filterOp) {
                    throw new IllegalArgumentException("Invalid Query. You cannot use more than one '" + filterOp.toString() + "' filter.");
                }
                throw new IllegalArgumentException("Invalid Query. You cannot use '" + filterOp.toString() + "' filters with '" + conflictingOp.toString() + "' filters.");
            }
        }
    }

    public Query orderBy(String field) {
        return orderBy(FieldPath.fromDotSeparatedPath(field), Direction.ASCENDING);
    }

    public Query orderBy(FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), Direction.ASCENDING);
    }

    public Query orderBy(String field, Direction direction) {
        return orderBy(FieldPath.fromDotSeparatedPath(field), direction);
    }

    public Query orderBy(FieldPath fieldPath, Direction direction) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), direction);
    }

    private Query orderBy(FieldPath fieldPath, Direction direction) {
        OrderBy.Direction dir;
        Preconditions.checkNotNull(direction, "Provided direction must not be null.");
        if (this.query.getStartAt() != null) {
            throw new IllegalArgumentException("Invalid query. You must not call Query.startAt() or Query.startAfter() before calling Query.orderBy().");
        } else if (this.query.getEndAt() == null) {
            validateOrderByField(fieldPath);
            if (direction == Direction.ASCENDING) {
                dir = OrderBy.Direction.ASCENDING;
            } else {
                dir = OrderBy.Direction.DESCENDING;
            }
            return new Query(this.query.orderBy(OrderBy.getInstance(dir, fieldPath)), this.firestore);
        } else {
            throw new IllegalArgumentException("Invalid query. You must not call Query.endAt() or Query.endBefore() before calling Query.orderBy().");
        }
    }

    public Query limit(long limit) {
        if (limit > 0) {
            return new Query(this.query.limitToFirst(limit), this.firestore);
        }
        throw new IllegalArgumentException("Invalid Query. Query limit (" + limit + ") is invalid. Limit must be positive.");
    }

    public Query limitToLast(long limit) {
        if (limit > 0) {
            return new Query(this.query.limitToLast(limit), this.firestore);
        }
        throw new IllegalArgumentException("Invalid Query. Query limitToLast (" + limit + ") is invalid. Limit must be positive.");
    }

    public Query startAt(DocumentSnapshot snapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAt", snapshot, true)), this.firestore);
    }

    public Query startAt(Object... fieldValues) {
        return new Query(this.query.startAt(boundFromFields("startAt", fieldValues, true)), this.firestore);
    }

    public Query startAfter(DocumentSnapshot snapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAfter", snapshot, false)), this.firestore);
    }

    public Query startAfter(Object... fieldValues) {
        return new Query(this.query.startAt(boundFromFields("startAfter", fieldValues, false)), this.firestore);
    }

    public Query endBefore(DocumentSnapshot snapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endBefore", snapshot, true)), this.firestore);
    }

    public Query endBefore(Object... fieldValues) {
        return new Query(this.query.endAt(boundFromFields("endBefore", fieldValues, true)), this.firestore);
    }

    public Query endAt(DocumentSnapshot snapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endAt", snapshot, false)), this.firestore);
    }

    public Query endAt(Object... fieldValues) {
        return new Query(this.query.endAt(boundFromFields("endAt", fieldValues, false)), this.firestore);
    }

    private Bound boundFromDocumentSnapshot(String methodName, DocumentSnapshot snapshot, boolean before) {
        Preconditions.checkNotNull(snapshot, "Provided snapshot must not be null.");
        if (snapshot.exists()) {
            Document document = snapshot.getDocument();
            List<Value> components = new ArrayList<>();
            for (OrderBy orderBy : this.query.getOrderBy()) {
                if (orderBy.getField().equals(FieldPath.KEY_PATH)) {
                    components.add(Values.refValue(this.firestore.getDatabaseId(), document.getKey()));
                } else {
                    Value value = document.getField(orderBy.getField());
                    if (ServerTimestamps.isServerTimestamp(value)) {
                        throw new IllegalArgumentException("Invalid query. You are trying to start or end a query using a document for which the field '" + orderBy.getField() + "' is an uncommitted server timestamp. (Since the value of this field is unknown, you cannot start/end a query with it.)");
                    } else if (value != null) {
                        components.add(value);
                    } else {
                        throw new IllegalArgumentException("Invalid query. You are trying to start or end a query using a document for which the field '" + orderBy.getField() + "' (used as the orderBy) does not exist.");
                    }
                }
            }
            return new Bound(components, before);
        }
        throw new IllegalArgumentException("Can't use a DocumentSnapshot for a document that doesn't exist for " + methodName + "().");
    }

    private Bound boundFromFields(String methodName, Object[] values, boolean before) {
        List<OrderBy> explicitOrderBy = this.query.getExplicitOrderBy();
        if (values.length <= explicitOrderBy.size()) {
            List<Value> components = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                String str = values[i];
                if (!explicitOrderBy.get(i).getField().equals(FieldPath.KEY_PATH)) {
                    components.add(this.firestore.getUserDataReader().parseQueryValue(str));
                } else if (str instanceof String) {
                    String documentId = str;
                    if (this.query.isCollectionGroupQuery() || !documentId.contains("/")) {
                        ResourcePath path = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(documentId));
                        if (DocumentKey.isDocumentKey(path)) {
                            components.add(Values.refValue(this.firestore.getDatabaseId(), DocumentKey.fromPath(path)));
                        } else {
                            throw new IllegalArgumentException("Invalid query. When querying a collection group and ordering by FieldPath.documentId(), the value passed to " + methodName + "() must result in a valid document path, but '" + path + "' is not because it contains an odd number of segments.");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid query. When querying a collection and ordering by FieldPath.documentId(), the value passed to " + methodName + "() must be a plain document ID, but '" + documentId + "' contains a slash.");
                    }
                } else {
                    throw new IllegalArgumentException("Invalid query. Expected a string for document ID in " + methodName + "(), but got " + str + ".");
                }
            }
            return new Bound(components, before);
        }
        throw new IllegalArgumentException("Too many arguments provided to " + methodName + "(). The number of arguments must be less than or equal to the number of orderBy() clauses.");
    }

    public Task<QuerySnapshot> get() {
        return get(Source.DEFAULT);
    }

    public Task<QuerySnapshot> get(Source source) {
        validateHasExplicitOrderByForLimitToLast();
        if (source == Source.CACHE) {
            return this.firestore.getClient().getDocumentsFromLocalCache(this.query).continueWith(Executors.DIRECT_EXECUTOR, Query$$Lambda$1.lambdaFactory$(this));
        }
        return getViaSnapshotListener(source);
    }

    static /* synthetic */ QuerySnapshot lambda$get$0(Query query2, Task viewSnap) throws Exception {
        return new QuerySnapshot(new Query(query2.query, query2.firestore), (ViewSnapshot) viewSnap.getResult(), query2.firestore);
    }

    private Task<QuerySnapshot> getViaSnapshotListener(Source source) {
        TaskCompletionSource<QuerySnapshot> res = new TaskCompletionSource<>();
        TaskCompletionSource<ListenerRegistration> registration = new TaskCompletionSource<>();
        EventManager.ListenOptions options = new EventManager.ListenOptions();
        options.includeDocumentMetadataChanges = true;
        options.includeQueryMetadataChanges = true;
        options.waitForSyncWhenOnline = true;
        registration.setResult(addSnapshotListenerInternal(Executors.DIRECT_EXECUTOR, options, (Activity) null, Query$$Lambda$2.lambdaFactory$(res, registration, source)));
        return res.getTask();
    }

    static /* synthetic */ void lambda$getViaSnapshotListener$1(TaskCompletionSource res, TaskCompletionSource registration, Source source, QuerySnapshot snapshot, FirebaseFirestoreException error) {
        if (error != null) {
            res.setException(error);
            return;
        }
        try {
            ((ListenerRegistration) Tasks.await(registration.getTask())).remove();
            if (!snapshot.getMetadata().isFromCache() || source != Source.SERVER) {
                res.setResult(snapshot);
            } else {
                res.setException(new FirebaseFirestoreException("Failed to get documents from server. (However, these documents may exist in the local cache. Run again without setting source to SERVER to retrieve the cached documents.)", FirebaseFirestoreException.Code.UNAVAILABLE));
            }
        } catch (ExecutionException e) {
            throw Assert.fail(e, "Failed to register a listener for a query result", new Object[0]);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw Assert.fail(e2, "Failed to register a listener for a query result", new Object[0]);
        }
    }

    public ListenerRegistration addSnapshotListener(EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(executor, MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(activity, MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(Executors.DEFAULT_CALLBACK_EXECUTOR, metadataChanges, listener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        Preconditions.checkNotNull(executor, "Provided executor must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(listener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(executor, internalOptions(metadataChanges), (Activity) null, listener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        Preconditions.checkNotNull(activity, "Provided activity must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(listener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(Executors.DEFAULT_CALLBACK_EXECUTOR, internalOptions(metadataChanges), activity, listener);
    }

    private ListenerRegistration addSnapshotListenerInternal(Executor executor, EventManager.ListenOptions options, Activity activity, EventListener<QuerySnapshot> userListener) {
        validateHasExplicitOrderByForLimitToLast();
        AsyncEventListener<ViewSnapshot> asyncListener = new AsyncEventListener<>(executor, Query$$Lambda$3.lambdaFactory$(this, userListener));
        return ActivityScope.bind(activity, new ListenerRegistrationImpl(this.firestore.getClient(), this.firestore.getClient().listen(this.query, options, asyncListener), asyncListener));
    }

    static /* synthetic */ void lambda$addSnapshotListenerInternal$2(Query query2, EventListener userListener, ViewSnapshot snapshot, FirebaseFirestoreException error) {
        if (error != null) {
            userListener.onEvent(null, error);
            return;
        }
        Assert.hardAssert(snapshot != null, "Got event without value or error set", new Object[0]);
        userListener.onEvent(new QuerySnapshot(query2, snapshot, query2.firestore), (FirebaseFirestoreException) null);
    }

    private void validateHasExplicitOrderByForLimitToLast() {
        if (this.query.hasLimitToLast() && this.query.getExplicitOrderBy().isEmpty()) {
            throw new IllegalStateException("limitToLast() queries require specifying at least one orderBy() clause");
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }
        Query that = (Query) o;
        if (!this.query.equals(that.query) || !this.firestore.equals(that.firestore)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.query.hashCode() * 31) + this.firestore.hashCode();
    }

    private static EventManager.ListenOptions internalOptions(MetadataChanges metadataChanges) {
        EventManager.ListenOptions internalOptions = new EventManager.ListenOptions();
        boolean z = true;
        internalOptions.includeDocumentMetadataChanges = metadataChanges == MetadataChanges.INCLUDE;
        if (metadataChanges != MetadataChanges.INCLUDE) {
            z = false;
        }
        internalOptions.includeQueryMetadataChanges = z;
        internalOptions.waitForSyncWhenOnline = false;
        return internalOptions;
    }
}
