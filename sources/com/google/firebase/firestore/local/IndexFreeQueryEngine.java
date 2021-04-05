package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class IndexFreeQueryEngine implements QueryEngine {
    private static final String LOG_TAG = "IndexFreeQueryEngine";
    private LocalDocumentsView localDocumentsView;

    public void setLocalDocumentsView(LocalDocumentsView localDocuments) {
        this.localDocumentsView = localDocuments;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query, SnapshotVersion lastLimboFreeSnapshotVersion, ImmutableSortedSet<DocumentKey> remoteKeys) {
        Assert.hardAssert(this.localDocumentsView != null, "setLocalDocumentsView() not called", new Object[0]);
        if (query.matchesAllDocuments()) {
            return executeFullCollectionScan(query);
        }
        if (lastLimboFreeSnapshotVersion.equals(SnapshotVersion.NONE)) {
            return executeFullCollectionScan(query);
        }
        ImmutableSortedSet<Document> previousResults = applyQuery(query, this.localDocumentsView.getDocuments(remoteKeys));
        if ((query.hasLimitToFirst() || query.hasLimitToLast()) && needsRefill(query.getLimitType(), previousResults, remoteKeys, lastLimboFreeSnapshotVersion)) {
            return executeFullCollectionScan(query);
        }
        if (Logger.isDebugEnabled()) {
            Logger.debug(LOG_TAG, "Re-using previous result from %s to execute query: %s", lastLimboFreeSnapshotVersion.toString(), query.toString());
        }
        ImmutableSortedMap<DocumentKey, Document> updatedResults = this.localDocumentsView.getDocumentsMatchingQuery(query, lastLimboFreeSnapshotVersion);
        Iterator<Document> it = previousResults.iterator();
        while (it.hasNext()) {
            Document result = it.next();
            updatedResults = updatedResults.insert(result.getKey(), result);
        }
        return updatedResults;
    }

    private ImmutableSortedSet<Document> applyQuery(Query query, ImmutableSortedMap<DocumentKey, MaybeDocument> documents) {
        ImmutableSortedSet<Document> queryResults = new ImmutableSortedSet<>(Collections.emptyList(), query.comparator());
        Iterator<Map.Entry<DocumentKey, MaybeDocument>> it = documents.iterator();
        while (it.hasNext()) {
            MaybeDocument maybeDoc = it.next().getValue();
            if ((maybeDoc instanceof Document) && query.matches((Document) maybeDoc)) {
                queryResults = queryResults.insert((Document) maybeDoc);
            }
        }
        return queryResults;
    }

    private boolean needsRefill(Query.LimitType limitType, ImmutableSortedSet<Document> sortedPreviousResults, ImmutableSortedSet<DocumentKey> remoteKeys, SnapshotVersion limboFreeSnapshotVersion) {
        Document documentAtLimitEdge;
        if (remoteKeys.size() != sortedPreviousResults.size()) {
            return true;
        }
        if (limitType == Query.LimitType.LIMIT_TO_FIRST) {
            documentAtLimitEdge = sortedPreviousResults.getMaxEntry();
        } else {
            documentAtLimitEdge = sortedPreviousResults.getMinEntry();
        }
        if (documentAtLimitEdge == null) {
            return false;
        }
        if (documentAtLimitEdge.hasPendingWrites() || documentAtLimitEdge.getVersion().compareTo(limboFreeSnapshotVersion) > 0) {
            return true;
        }
        return false;
    }

    public void handleDocumentChange(MaybeDocument oldDocument, MaybeDocument newDocument) {
    }

    private ImmutableSortedMap<DocumentKey, Document> executeFullCollectionScan(Query query) {
        if (Logger.isDebugEnabled()) {
            Logger.debug(LOG_TAG, "Using full collection scan to execute query: %s", query.toString());
        }
        return this.localDocumentsView.getDocumentsMatchingQuery(query, SnapshotVersion.NONE);
    }
}
