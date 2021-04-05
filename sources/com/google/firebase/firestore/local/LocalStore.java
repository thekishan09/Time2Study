package com.google.firebase.firestore.local;

import android.util.SparseArray;
import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.Target;
import com.google.firebase.firestore.core.TargetIdGenerator;
import com.google.firebase.firestore.local.LruGarbageCollector;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.model.mutation.MutationBatchResult;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.remote.RemoteEvent;
import com.google.firebase.firestore.remote.TargetChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class LocalStore {
    private static final long RESUME_TOKEN_MAX_AGE_SECONDS = TimeUnit.MINUTES.toSeconds(5);
    private LocalDocumentsView localDocuments;
    private final ReferenceSet localViewReferences = new ReferenceSet();
    private MutationQueue mutationQueue;
    private final Persistence persistence;
    private final SparseArray<TargetData> queryDataByTarget;
    private QueryEngine queryEngine;
    private final RemoteDocumentCache remoteDocuments;
    private final TargetCache targetCache;
    private final Map<Target, Integer> targetIdByTarget;
    private final TargetIdGenerator targetIdGenerator;

    public LocalStore(Persistence persistence2, QueryEngine queryEngine2, User initialUser) {
        Assert.hardAssert(persistence2.isStarted(), "LocalStore was passed an unstarted persistence implementation", new Object[0]);
        this.persistence = persistence2;
        TargetCache targetCache2 = persistence2.getTargetCache();
        this.targetCache = targetCache2;
        this.targetIdGenerator = TargetIdGenerator.forTargetCache(targetCache2.getHighestTargetId());
        this.mutationQueue = persistence2.getMutationQueue(initialUser);
        this.remoteDocuments = persistence2.getRemoteDocumentCache();
        LocalDocumentsView localDocumentsView = new LocalDocumentsView(this.remoteDocuments, this.mutationQueue, persistence2.getIndexManager());
        this.localDocuments = localDocumentsView;
        this.queryEngine = queryEngine2;
        queryEngine2.setLocalDocumentsView(localDocumentsView);
        persistence2.getReferenceDelegate().setInMemoryPins(this.localViewReferences);
        this.queryDataByTarget = new SparseArray<>();
        this.targetIdByTarget = new HashMap();
    }

    public void start() {
        startMutationQueue();
    }

    private void startMutationQueue() {
        this.persistence.runTransaction("Start MutationQueue", LocalStore$$Lambda$1.lambdaFactory$(this));
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> handleUserChange(User user) {
        List<MutationBatch> oldBatches = this.mutationQueue.getAllMutationBatches();
        this.mutationQueue = this.persistence.getMutationQueue(user);
        startMutationQueue();
        List<MutationBatch> newBatches = this.mutationQueue.getAllMutationBatches();
        LocalDocumentsView localDocumentsView = new LocalDocumentsView(this.remoteDocuments, this.mutationQueue, this.persistence.getIndexManager());
        this.localDocuments = localDocumentsView;
        this.queryEngine.setLocalDocumentsView(localDocumentsView);
        ImmutableSortedSet<DocumentKey> changedKeys = DocumentKey.emptyKeySet();
        for (List<MutationBatch> batches : Arrays.asList(new List[]{oldBatches, newBatches})) {
            for (MutationBatch batch : batches) {
                for (Mutation mutation : batch.getMutations()) {
                    changedKeys = changedKeys.insert(mutation.getKey());
                }
            }
        }
        return this.localDocuments.getDocuments(changedKeys);
    }

    public LocalWriteResult writeLocally(List<Mutation> mutations) {
        Timestamp localWriteTime = Timestamp.now();
        Set<DocumentKey> keys = new HashSet<>();
        for (Mutation mutation : mutations) {
            keys.add(mutation.getKey());
        }
        return (LocalWriteResult) this.persistence.runTransaction("Locally write mutations", LocalStore$$Lambda$2.lambdaFactory$(this, keys, mutations, localWriteTime));
    }

    static /* synthetic */ LocalWriteResult lambda$writeLocally$1(LocalStore localStore, Set keys, List mutations, Timestamp localWriteTime) {
        ImmutableSortedMap<DocumentKey, MaybeDocument> existingDocuments = localStore.localDocuments.getDocuments(keys);
        List<Mutation> baseMutations = new ArrayList<>();
        Iterator it = mutations.iterator();
        while (it.hasNext()) {
            Mutation mutation = (Mutation) it.next();
            ObjectValue baseValue = mutation.extractBaseValue(existingDocuments.get(mutation.getKey()));
            if (baseValue != null) {
                baseMutations.add(new PatchMutation(mutation.getKey(), baseValue, baseValue.getFieldMask(), Precondition.exists(true)));
            }
        }
        MutationBatch batch = localStore.mutationQueue.addMutationBatch(localWriteTime, baseMutations, mutations);
        return new LocalWriteResult(batch.getBatchId(), batch.applyToLocalDocumentSet(existingDocuments));
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> acknowledgeBatch(MutationBatchResult batchResult) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Acknowledge batch", LocalStore$$Lambda$3.lambdaFactory$(this, batchResult));
    }

    static /* synthetic */ ImmutableSortedMap lambda$acknowledgeBatch$2(LocalStore localStore, MutationBatchResult batchResult) {
        MutationBatch batch = batchResult.getBatch();
        localStore.mutationQueue.acknowledgeBatch(batch, batchResult.getStreamToken());
        localStore.applyWriteToRemoteDocuments(batchResult);
        localStore.mutationQueue.performConsistencyCheck();
        return localStore.localDocuments.getDocuments(batch.getKeys());
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> rejectBatch(int batchId) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Reject batch", LocalStore$$Lambda$4.lambdaFactory$(this, batchId));
    }

    static /* synthetic */ ImmutableSortedMap lambda$rejectBatch$3(LocalStore localStore, int batchId) {
        MutationBatch toReject = localStore.mutationQueue.lookupMutationBatch(batchId);
        Assert.hardAssert(toReject != null, "Attempt to reject nonexistent batch!", new Object[0]);
        localStore.mutationQueue.removeMutationBatch(toReject);
        localStore.mutationQueue.performConsistencyCheck();
        return localStore.localDocuments.getDocuments(toReject.getKeys());
    }

    public int getHighestUnacknowledgedBatchId() {
        return this.mutationQueue.getHighestUnacknowledgedBatchId();
    }

    public ByteString getLastStreamToken() {
        return this.mutationQueue.getLastStreamToken();
    }

    public void setLastStreamToken(ByteString streamToken) {
        this.persistence.runTransaction("Set stream token", LocalStore$$Lambda$5.lambdaFactory$(this, streamToken));
    }

    public SnapshotVersion getLastRemoteSnapshotVersion() {
        return this.targetCache.getLastRemoteSnapshotVersion();
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> applyRemoteEvent(RemoteEvent remoteEvent) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Apply remote event", LocalStore$$Lambda$6.lambdaFactory$(this, remoteEvent, remoteEvent.getSnapshotVersion()));
    }

    static /* synthetic */ ImmutableSortedMap lambda$applyRemoteEvent$5(LocalStore localStore, RemoteEvent remoteEvent, SnapshotVersion remoteVersion) {
        LocalStore localStore2 = localStore;
        SnapshotVersion snapshotVersion = remoteVersion;
        Map<Integer, TargetChange> targetChanges = remoteEvent.getTargetChanges();
        long sequenceNumber = localStore2.persistence.getReferenceDelegate().getCurrentSequenceNumber();
        for (Map.Entry<Integer, TargetChange> entry : targetChanges.entrySet()) {
            int targetId = entry.getKey().intValue();
            TargetChange change = entry.getValue();
            TargetData oldTargetData = localStore2.queryDataByTarget.get(targetId);
            if (oldTargetData != null) {
                localStore2.targetCache.removeMatchingKeys(change.getRemovedDocuments(), targetId);
                localStore2.targetCache.addMatchingKeys(change.getAddedDocuments(), targetId);
                ByteString resumeToken = change.getResumeToken();
                if (!resumeToken.isEmpty()) {
                    TargetData newTargetData = oldTargetData.withResumeToken(resumeToken, remoteEvent.getSnapshotVersion()).withSequenceNumber(sequenceNumber);
                    localStore2.queryDataByTarget.put(targetId, newTargetData);
                    if (shouldPersistTargetData(oldTargetData, newTargetData, change)) {
                        localStore2.targetCache.updateTargetData(newTargetData);
                    }
                }
            }
        }
        Map<DocumentKey, MaybeDocument> changedDocs = new HashMap<>();
        Map<DocumentKey, MaybeDocument> documentUpdates = remoteEvent.getDocumentUpdates();
        Set<DocumentKey> limboDocuments = remoteEvent.getResolvedLimboDocuments();
        Map<DocumentKey, MaybeDocument> existingDocs = localStore2.remoteDocuments.getAll(documentUpdates.keySet());
        for (Map.Entry<DocumentKey, MaybeDocument> entry2 : documentUpdates.entrySet()) {
            DocumentKey key = entry2.getKey();
            MaybeDocument doc = entry2.getValue();
            MaybeDocument existingDoc = existingDocs.get(key);
            if ((doc instanceof NoDocument) && doc.getVersion().equals(SnapshotVersion.NONE)) {
                localStore2.remoteDocuments.remove(doc.getKey());
                changedDocs.put(key, doc);
            } else if (existingDoc == null || doc.getVersion().compareTo(existingDoc.getVersion()) > 0 || (doc.getVersion().compareTo(existingDoc.getVersion()) == 0 && existingDoc.hasPendingWrites())) {
                Assert.hardAssert(!SnapshotVersion.NONE.equals(remoteEvent.getSnapshotVersion()), "Cannot add a document when the remote version is zero", new Object[0]);
                localStore2.remoteDocuments.add(doc, remoteEvent.getSnapshotVersion());
                changedDocs.put(key, doc);
            } else {
                Logger.debug("LocalStore", "Ignoring outdated watch update for %s.Current version: %s  Watch version: %s", key, existingDoc.getVersion(), doc.getVersion());
            }
            if (limboDocuments.contains(key)) {
                localStore2.persistence.getReferenceDelegate().updateLimboDocument(key);
            }
        }
        SnapshotVersion lastRemoteVersion = localStore2.targetCache.getLastRemoteSnapshotVersion();
        if (!snapshotVersion.equals(SnapshotVersion.NONE)) {
            Assert.hardAssert(snapshotVersion.compareTo(lastRemoteVersion) >= 0, "Watch stream reverted to previous snapshot?? (%s < %s)", snapshotVersion, lastRemoteVersion);
            localStore2.targetCache.setLastRemoteSnapshotVersion(snapshotVersion);
        }
        return localStore2.localDocuments.getLocalViewOfDocuments(changedDocs);
    }

    private static boolean shouldPersistTargetData(TargetData oldTargetData, TargetData newTargetData, TargetChange change) {
        Assert.hardAssert(!newTargetData.getResumeToken().isEmpty(), "Attempted to persist query data with empty resume token", new Object[0]);
        if (!oldTargetData.getResumeToken().isEmpty() && newTargetData.getSnapshotVersion().getTimestamp().getSeconds() - oldTargetData.getSnapshotVersion().getTimestamp().getSeconds() < RESUME_TOKEN_MAX_AGE_SECONDS && change.getAddedDocuments().size() + change.getModifiedDocuments().size() + change.getRemovedDocuments().size() <= 0) {
            return false;
        }
        return true;
    }

    public void notifyLocalViewChanges(List<LocalViewChanges> viewChanges) {
        this.persistence.runTransaction("notifyLocalViewChanges", LocalStore$$Lambda$7.lambdaFactory$(this, viewChanges));
    }

    static /* synthetic */ void lambda$notifyLocalViewChanges$6(LocalStore localStore, List viewChanges) {
        Iterator it = viewChanges.iterator();
        while (it.hasNext()) {
            LocalViewChanges viewChange = (LocalViewChanges) it.next();
            int targetId = viewChange.getTargetId();
            localStore.localViewReferences.addReferences(viewChange.getAdded(), targetId);
            ImmutableSortedSet<DocumentKey> removed = viewChange.getRemoved();
            Iterator<DocumentKey> it2 = removed.iterator();
            while (it2.hasNext()) {
                localStore.persistence.getReferenceDelegate().removeReference(it2.next());
            }
            localStore.localViewReferences.removeReferences(removed, targetId);
            if (!viewChange.isFromCache()) {
                TargetData targetData = localStore.queryDataByTarget.get(targetId);
                Assert.hardAssert(targetData != null, "Can't set limbo-free snapshot version for unknown target: %s", Integer.valueOf(targetId));
                localStore.queryDataByTarget.put(targetId, targetData.withLastLimboFreeSnapshotVersion(targetData.getSnapshotVersion()));
            }
        }
    }

    public MutationBatch getNextMutationBatch(int afterBatchId) {
        return this.mutationQueue.getNextMutationBatchAfterBatchId(afterBatchId);
    }

    public MaybeDocument readDocument(DocumentKey key) {
        return this.localDocuments.getDocument(key);
    }

    public TargetData allocateTarget(Target target) {
        int targetId;
        TargetData cached = this.targetCache.getTargetData(target);
        if (cached != null) {
            targetId = cached.getTargetId();
        } else {
            AllocateQueryHolder holder = new AllocateQueryHolder();
            this.persistence.runTransaction("Allocate target", LocalStore$$Lambda$8.lambdaFactory$(this, holder, target));
            int targetId2 = holder.targetId;
            cached = holder.cached;
            targetId = targetId2;
        }
        if (this.queryDataByTarget.get(targetId) == null) {
            this.queryDataByTarget.put(targetId, cached);
            this.targetIdByTarget.put(target, Integer.valueOf(targetId));
        }
        return cached;
    }

    static /* synthetic */ void lambda$allocateTarget$7(LocalStore localStore, AllocateQueryHolder holder, Target target) {
        holder.targetId = localStore.targetIdGenerator.nextId();
        holder.cached = new TargetData(target, holder.targetId, localStore.persistence.getReferenceDelegate().getCurrentSequenceNumber(), QueryPurpose.LISTEN);
        localStore.targetCache.addTargetData(holder.cached);
    }

    /* access modifiers changed from: package-private */
    public TargetData getTargetData(Target target) {
        Integer targetId = this.targetIdByTarget.get(target);
        if (targetId != null) {
            return this.queryDataByTarget.get(targetId.intValue());
        }
        return this.targetCache.getTargetData(target);
    }

    private static class AllocateQueryHolder {
        TargetData cached;
        int targetId;

        private AllocateQueryHolder() {
        }
    }

    public void releaseTarget(int targetId) {
        this.persistence.runTransaction("Release target", LocalStore$$Lambda$9.lambdaFactory$(this, targetId));
    }

    static /* synthetic */ void lambda$releaseTarget$8(LocalStore localStore, int targetId) {
        TargetData targetData = localStore.queryDataByTarget.get(targetId);
        Assert.hardAssert(targetData != null, "Tried to release nonexistent target: %s", Integer.valueOf(targetId));
        Iterator<DocumentKey> it = localStore.localViewReferences.removeReferencesForId(targetId).iterator();
        while (it.hasNext()) {
            localStore.persistence.getReferenceDelegate().removeReference(it.next());
        }
        localStore.persistence.getReferenceDelegate().removeTarget(targetData);
        localStore.queryDataByTarget.remove(targetId);
        localStore.targetIdByTarget.remove(targetData.getTarget());
    }

    public QueryResult executeQuery(Query query, boolean usePreviousResults) {
        TargetData targetData = getTargetData(query.toTarget());
        SnapshotVersion lastLimboFreeSnapshotVersion = SnapshotVersion.NONE;
        ImmutableSortedSet<DocumentKey> remoteKeys = DocumentKey.emptyKeySet();
        if (targetData != null) {
            lastLimboFreeSnapshotVersion = targetData.getLastLimboFreeSnapshotVersion();
            remoteKeys = this.targetCache.getMatchingKeysForTargetId(targetData.getTargetId());
        }
        return new QueryResult(this.queryEngine.getDocumentsMatchingQuery(query, usePreviousResults ? lastLimboFreeSnapshotVersion : SnapshotVersion.NONE, usePreviousResults ? remoteKeys : DocumentKey.emptyKeySet()), remoteKeys);
    }

    public ImmutableSortedSet<DocumentKey> getRemoteDocumentKeys(int targetId) {
        return this.targetCache.getMatchingKeysForTargetId(targetId);
    }

    private void applyWriteToRemoteDocuments(MutationBatchResult batchResult) {
        MutationBatch batch = batchResult.getBatch();
        for (DocumentKey docKey : batch.getKeys()) {
            MaybeDocument remoteDoc = this.remoteDocuments.get(docKey);
            MaybeDocument doc = remoteDoc;
            SnapshotVersion ackVersion = batchResult.getDocVersions().get(docKey);
            Assert.hardAssert(ackVersion != null, "docVersions should contain every doc in the write.", new Object[0]);
            if (doc == null || doc.getVersion().compareTo(ackVersion) < 0) {
                MaybeDocument doc2 = batch.applyToRemoteDocument(docKey, doc, batchResult);
                if (doc2 == null) {
                    Assert.hardAssert(remoteDoc == null, "Mutation batch %s applied to document %s resulted in null.", batch, remoteDoc);
                } else {
                    this.remoteDocuments.add(doc2, batchResult.getCommitVersion());
                }
            }
        }
        this.mutationQueue.removeMutationBatch(batch);
    }

    public LruGarbageCollector.Results collectGarbage(LruGarbageCollector garbageCollector) {
        return (LruGarbageCollector.Results) this.persistence.runTransaction("Collect garbage", LocalStore$$Lambda$10.lambdaFactory$(this, garbageCollector));
    }
}
