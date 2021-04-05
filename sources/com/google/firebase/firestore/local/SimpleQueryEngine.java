package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;

public class SimpleQueryEngine implements QueryEngine {
    private LocalDocumentsView localDocumentsView;

    public void setLocalDocumentsView(LocalDocumentsView localDocuments) {
        this.localDocumentsView = localDocuments;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query, SnapshotVersion lastLimboFreeSnapshotVersion, ImmutableSortedSet<DocumentKey> immutableSortedSet) {
        Assert.hardAssert(this.localDocumentsView != null, "setLocalDocumentsView() not called", new Object[0]);
        return this.localDocumentsView.getDocumentsMatchingQuery(query, SnapshotVersion.NONE);
    }

    public void handleDocumentChange(MaybeDocument oldDocument, MaybeDocument newDocument) {
    }
}
