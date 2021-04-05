package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.util.Assert;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class LocalDocumentsView {
    private final IndexManager indexManager;
    private final MutationQueue mutationQueue;
    private final RemoteDocumentCache remoteDocumentCache;

    LocalDocumentsView(RemoteDocumentCache remoteDocumentCache2, MutationQueue mutationQueue2, IndexManager indexManager2) {
        this.remoteDocumentCache = remoteDocumentCache2;
        this.mutationQueue = mutationQueue2;
        this.indexManager = indexManager2;
    }

    /* access modifiers changed from: package-private */
    public RemoteDocumentCache getRemoteDocumentCache() {
        return this.remoteDocumentCache;
    }

    /* access modifiers changed from: package-private */
    public MutationQueue getMutationQueue() {
        return this.mutationQueue;
    }

    /* access modifiers changed from: package-private */
    public IndexManager getIndexManager() {
        return this.indexManager;
    }

    /* access modifiers changed from: package-private */
    public MaybeDocument getDocument(DocumentKey key) {
        return getDocument(key, this.mutationQueue.getAllMutationBatchesAffectingDocumentKey(key));
    }

    private MaybeDocument getDocument(DocumentKey key, List<MutationBatch> inBatches) {
        MaybeDocument document = this.remoteDocumentCache.get(key);
        for (MutationBatch batch : inBatches) {
            document = batch.applyToLocalView(key, document);
        }
        return document;
    }

    private Map<DocumentKey, MaybeDocument> applyLocalMutationsToDocuments(Map<DocumentKey, MaybeDocument> docs, List<MutationBatch> batches) {
        for (Map.Entry<DocumentKey, MaybeDocument> base : docs.entrySet()) {
            MaybeDocument localView = base.getValue();
            for (MutationBatch batch : batches) {
                localView = batch.applyToLocalView(base.getKey(), localView);
            }
            base.setValue(localView);
        }
        return docs;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedMap<DocumentKey, MaybeDocument> getDocuments(Iterable<DocumentKey> keys) {
        return getLocalViewOfDocuments(this.remoteDocumentCache.getAll(keys));
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedMap<DocumentKey, MaybeDocument> getLocalViewOfDocuments(Map<DocumentKey, MaybeDocument> baseDocs) {
        ImmutableSortedMap<DocumentKey, MaybeDocument> results = DocumentCollections.emptyMaybeDocumentMap();
        for (Map.Entry<DocumentKey, MaybeDocument> entry : applyLocalMutationsToDocuments(baseDocs, this.mutationQueue.getAllMutationBatchesAffectingDocumentKeys(baseDocs.keySet())).entrySet()) {
            DocumentKey key = entry.getKey();
            MaybeDocument maybeDoc = entry.getValue();
            if (maybeDoc == null) {
                maybeDoc = new NoDocument(key, SnapshotVersion.NONE, false);
            }
            results = results.insert(key, maybeDoc);
        }
        return results;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query, SnapshotVersion sinceReadTime) {
        ResourcePath path = query.getPath();
        if (query.isDocumentQuery()) {
            return getDocumentsMatchingDocumentQuery(path);
        }
        if (query.isCollectionGroupQuery()) {
            return getDocumentsMatchingCollectionGroupQuery(query, sinceReadTime);
        }
        return getDocumentsMatchingCollectionQuery(query, sinceReadTime);
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingDocumentQuery(ResourcePath path) {
        ImmutableSortedMap<DocumentKey, Document> result = DocumentCollections.emptyDocumentMap();
        MaybeDocument doc = getDocument(DocumentKey.fromPath(path));
        if (doc instanceof Document) {
            return result.insert(doc.getKey(), (Document) doc);
        }
        return result;
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingCollectionGroupQuery(Query query, SnapshotVersion sinceReadTime) {
        Assert.hardAssert(query.getPath().isEmpty(), "Currently we only support collection group queries at the root.", new Object[0]);
        String collectionId = query.getCollectionGroup();
        ImmutableSortedMap<DocumentKey, Document> results = DocumentCollections.emptyDocumentMap();
        for (ResourcePath parent : this.indexManager.getCollectionParents(collectionId)) {
            Iterator<Map.Entry<DocumentKey, Document>> it = getDocumentsMatchingCollectionQuery(query.asCollectionQueryAtPath((ResourcePath) parent.append(collectionId)), sinceReadTime).iterator();
            while (it.hasNext()) {
                Map.Entry<DocumentKey, Document> docEntry = it.next();
                results = results.insert(docEntry.getKey(), docEntry.getValue());
            }
        }
        return results;
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingCollectionQuery(Query query, SnapshotVersion sinceReadTime) {
        ImmutableSortedMap<DocumentKey, Document> results = this.remoteDocumentCache.getAllDocumentsMatchingQuery(query, sinceReadTime);
        List<MutationBatch> matchingBatches = this.mutationQueue.getAllMutationBatchesAffectingQuery(query);
        ImmutableSortedMap<DocumentKey, Document> results2 = addMissingBaseDocuments(matchingBatches, results);
        for (MutationBatch batch : matchingBatches) {
            for (Mutation mutation : batch.getMutations()) {
                if (query.getPath().isImmediateParentOf(mutation.getKey().getPath())) {
                    DocumentKey key = mutation.getKey();
                    MaybeDocument baseDoc = results2.get(key);
                    MaybeDocument mutatedDoc = mutation.applyToLocalView(baseDoc, baseDoc, batch.getLocalWriteTime());
                    if (mutatedDoc instanceof Document) {
                        results2 = results2.insert(key, (Document) mutatedDoc);
                    } else {
                        results2 = results2.remove(key);
                    }
                }
            }
        }
        Iterator<Map.Entry<DocumentKey, Document>> it = results2.iterator();
        while (it.hasNext()) {
            Map.Entry<DocumentKey, Document> docEntry = it.next();
            if (!query.matches(docEntry.getValue())) {
                results2 = results2.remove(docEntry.getKey());
            }
        }
        return results2;
    }

    private ImmutableSortedMap<DocumentKey, Document> addMissingBaseDocuments(List<MutationBatch> matchingBatches, ImmutableSortedMap<DocumentKey, Document> existingDocs) {
        HashSet<DocumentKey> missingDocKeys = new HashSet<>();
        for (MutationBatch batch : matchingBatches) {
            for (Mutation mutation : batch.getMutations()) {
                if ((mutation instanceof PatchMutation) && !existingDocs.containsKey(mutation.getKey())) {
                    missingDocKeys.add(mutation.getKey());
                }
            }
        }
        ImmutableSortedMap<DocumentKey, Document> mergedDocs = existingDocs;
        for (Map.Entry<DocumentKey, MaybeDocument> entry : this.remoteDocumentCache.getAll(missingDocKeys).entrySet()) {
            if (entry.getValue() != null && (entry.getValue() instanceof Document)) {
                mergedDocs = mergedDocs.insert(entry.getKey(), (Document) entry.getValue());
            }
        }
        return mergedDocs;
    }
}
