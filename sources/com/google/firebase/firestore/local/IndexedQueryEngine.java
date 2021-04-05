package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.IndexRange;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;
import java.util.Arrays;
import java.util.List;

public class IndexedQueryEngine implements QueryEngine {
    private static final double HIGH_SELECTIVITY = 1.0d;
    private static final double LOW_SELECTIVITY = 0.5d;
    private static final List<Value.ValueTypeCase> lowCardinalityTypes = Arrays.asList(new Value.ValueTypeCase[]{Value.ValueTypeCase.BOOLEAN_VALUE, Value.ValueTypeCase.ARRAY_VALUE, Value.ValueTypeCase.MAP_VALUE});
    private final SQLiteCollectionIndex collectionIndex;
    private LocalDocumentsView localDocuments;

    public IndexedQueryEngine(SQLiteCollectionIndex collectionIndex2) {
        this.collectionIndex = collectionIndex2;
    }

    public void setLocalDocumentsView(LocalDocumentsView localDocuments2) {
        this.localDocuments = localDocuments2;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query, SnapshotVersion lastLimboFreeSnapshotVersion, ImmutableSortedSet<DocumentKey> immutableSortedSet) {
        Assert.hardAssert(this.localDocuments != null, "setLocalDocumentsView() not called", new Object[0]);
        if (query.isDocumentQuery()) {
            return this.localDocuments.getDocumentsMatchingQuery(query, SnapshotVersion.NONE);
        }
        return performCollectionQuery(query);
    }

    private ImmutableSortedMap<DocumentKey, Document> performCollectionQuery(Query query) {
        Assert.hardAssert(!query.isDocumentQuery(), "matchesCollectionQuery() called with document query.", new Object[0]);
        IndexRange indexRange = extractBestIndexRange(query);
        if (indexRange != null) {
            return performQueryUsingIndex(query, indexRange);
        }
        Assert.hardAssert(query.getFilters().isEmpty(), "If there are any filters, we should be able to use an index.", new Object[0]);
        return this.localDocuments.getDocumentsMatchingQuery(query, SnapshotVersion.NONE);
    }

    private ImmutableSortedMap<DocumentKey, Document> performQueryUsingIndex(Query query, IndexRange indexRange) {
        ImmutableSortedMap<DocumentKey, Document> results = DocumentCollections.emptyDocumentMap();
        IndexCursor cursor = this.collectionIndex.getCursor(query.getPath(), indexRange);
        while (cursor.next()) {
            try {
                Document document = (Document) this.localDocuments.getDocument(cursor.getDocumentKey());
                if (query.matches(document)) {
                    results = results.insert(cursor.getDocumentKey(), document);
                }
            } finally {
                cursor.close();
            }
        }
        return results;
    }

    private static double estimateFilterSelectivity(Filter filter) {
        Assert.hardAssert(filter instanceof FieldFilter, "Filter type expected to be FieldFilter", new Object[0]);
        FieldFilter fieldFilter = (FieldFilter) filter;
        Value filterValue = fieldFilter.getValue();
        boolean isNullValue = Values.isNullValue(filterValue);
        double typeSelectivity = HIGH_SELECTIVITY;
        if (isNullValue || Values.isNanValue(filterValue)) {
            return HIGH_SELECTIVITY;
        }
        double operatorSelectivity = fieldFilter.getOperator().equals(Filter.Operator.EQUAL) ? 1.0d : 0.5d;
        if (lowCardinalityTypes.contains(fieldFilter.getValue().getValueTypeCase())) {
            typeSelectivity = 0.5d;
        }
        return typeSelectivity * operatorSelectivity;
    }

    static IndexRange extractBestIndexRange(Query query) {
        double currentSelectivity = -1.0d;
        if (!query.getFilters().isEmpty()) {
            Filter selectedFilter = null;
            for (Filter currentFilter : query.getFilters()) {
                double estimatedSelectivity = estimateFilterSelectivity(currentFilter);
                if (estimatedSelectivity > currentSelectivity) {
                    selectedFilter = currentFilter;
                    currentSelectivity = estimatedSelectivity;
                }
            }
            Assert.hardAssert(selectedFilter != null, "Filter should be defined", new Object[0]);
            return convertFilterToIndexRange(selectedFilter);
        } else if (!query.getOrderBy().get(0).getField().equals(FieldPath.KEY_PATH)) {
            return IndexRange.builder().setFieldPath(query.getOrderBy().get(0).getField()).build();
        } else {
            return null;
        }
    }

    private static IndexRange convertFilterToIndexRange(Filter filter) {
        IndexRange.Builder indexRange = IndexRange.builder().setFieldPath(filter.getField());
        if (filter instanceof FieldFilter) {
            FieldFilter fieldFilter = (FieldFilter) filter;
            Value filterValue = fieldFilter.getValue();
            int i = C18971.$SwitchMap$com$google$firebase$firestore$core$Filter$Operator[fieldFilter.getOperator().ordinal()];
            if (i == 1) {
                indexRange.setStart(filterValue).setEnd(filterValue);
            } else if (i == 2 || i == 3) {
                indexRange.setEnd(filterValue);
            } else if (i == 4 || i == 5) {
                indexRange.setStart(filterValue);
            } else {
                throw Assert.fail("Unexpected operator in query filter", new Object[0]);
            }
        }
        return indexRange.build();
    }

    /* renamed from: com.google.firebase.firestore.local.IndexedQueryEngine$1 */
    static /* synthetic */ class C18971 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;

        static {
            int[] iArr = new int[Filter.Operator.values().length];
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = iArr;
            try {
                iArr[Filter.Operator.EQUAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.LESS_THAN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void handleDocumentChange(MaybeDocument oldDocument, MaybeDocument newDocument) {
        throw new RuntimeException("Not yet implemented.");
    }
}
