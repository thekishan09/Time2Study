package com.google.firebase.firestore.core;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.LimboDocumentChange;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.remote.TargetChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class View {
    private boolean current;
    private DocumentSet documentSet;
    private ImmutableSortedSet<DocumentKey> limboDocuments;
    private ImmutableSortedSet<DocumentKey> mutatedKeys;
    private final Query query;
    private ViewSnapshot.SyncState syncState = ViewSnapshot.SyncState.NONE;
    private ImmutableSortedSet<DocumentKey> syncedDocuments;

    public static class DocumentChanges {
        final DocumentViewChangeSet changeSet;
        final DocumentSet documentSet;
        final ImmutableSortedSet<DocumentKey> mutatedKeys;
        /* access modifiers changed from: private */
        public final boolean needsRefill;

        /* synthetic */ DocumentChanges(DocumentSet x0, DocumentViewChangeSet x1, ImmutableSortedSet x2, boolean x3, C18961 x4) {
            this(x0, x1, x2, x3);
        }

        private DocumentChanges(DocumentSet newDocuments, DocumentViewChangeSet changes, ImmutableSortedSet<DocumentKey> mutatedKeys2, boolean needsRefill2) {
            this.documentSet = newDocuments;
            this.changeSet = changes;
            this.mutatedKeys = mutatedKeys2;
            this.needsRefill = needsRefill2;
        }

        public boolean needsRefill() {
            return this.needsRefill;
        }
    }

    public View(Query query2, ImmutableSortedSet<DocumentKey> remoteDocuments) {
        this.query = query2;
        this.documentSet = DocumentSet.emptySet(query2.comparator());
        this.syncedDocuments = remoteDocuments;
        this.limboDocuments = DocumentKey.emptyKeySet();
        this.mutatedKeys = DocumentKey.emptyKeySet();
    }

    public ViewSnapshot.SyncState getSyncState() {
        return this.syncState;
    }

    public <D extends MaybeDocument> DocumentChanges computeDocChanges(ImmutableSortedMap<DocumentKey, D> docChanges) {
        return computeDocChanges(docChanges, (DocumentChanges) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0121, code lost:
        if (r0.query.comparator().compare(r15, r10) <= 0) goto L_0x0126;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0132, code lost:
        if (r0.query.comparator().compare(r15, r11) < 0) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0134, code lost:
        r12 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <D extends com.google.firebase.firestore.model.MaybeDocument> com.google.firebase.firestore.core.View.DocumentChanges computeDocChanges(com.google.firebase.database.collection.ImmutableSortedMap<com.google.firebase.firestore.model.DocumentKey, D> r22, com.google.firebase.firestore.core.View.DocumentChanges r23) {
        /*
            r21 = this;
            r0 = r21
            r1 = r23
            if (r1 == 0) goto L_0x0009
            com.google.firebase.firestore.core.DocumentViewChangeSet r2 = r1.changeSet
            goto L_0x000e
        L_0x0009:
            com.google.firebase.firestore.core.DocumentViewChangeSet r2 = new com.google.firebase.firestore.core.DocumentViewChangeSet
            r2.<init>()
        L_0x000e:
            if (r1 == 0) goto L_0x0013
            com.google.firebase.firestore.model.DocumentSet r3 = r1.documentSet
            goto L_0x0015
        L_0x0013:
            com.google.firebase.firestore.model.DocumentSet r3 = r0.documentSet
        L_0x0015:
            r9 = r3
            if (r1 == 0) goto L_0x001b
            com.google.firebase.database.collection.ImmutableSortedSet<com.google.firebase.firestore.model.DocumentKey> r3 = r1.mutatedKeys
            goto L_0x001d
        L_0x001b:
            com.google.firebase.database.collection.ImmutableSortedSet<com.google.firebase.firestore.model.DocumentKey> r3 = r0.mutatedKeys
        L_0x001d:
            r4 = r9
            r5 = 0
            com.google.firebase.firestore.core.Query r6 = r0.query
            boolean r6 = r6.hasLimitToFirst()
            r7 = 0
            if (r6 == 0) goto L_0x003c
            int r6 = r9.size()
            long r10 = (long) r6
            com.google.firebase.firestore.core.Query r6 = r0.query
            long r12 = r6.getLimitToFirst()
            int r6 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r6 != 0) goto L_0x003c
            com.google.firebase.firestore.model.Document r6 = r9.getLastDocument()
            goto L_0x003d
        L_0x003c:
            r6 = r7
        L_0x003d:
            r10 = r6
            com.google.firebase.firestore.core.Query r6 = r0.query
            boolean r6 = r6.hasLimitToLast()
            if (r6 == 0) goto L_0x005a
            int r6 = r9.size()
            long r11 = (long) r6
            com.google.firebase.firestore.core.Query r6 = r0.query
            long r13 = r6.getLimitToLast()
            int r6 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r6 != 0) goto L_0x005a
            com.google.firebase.firestore.model.Document r7 = r9.getFirstDocument()
            goto L_0x005b
        L_0x005a:
        L_0x005b:
            r11 = r7
            java.util.Iterator r6 = r22.iterator()
            r12 = r5
        L_0x0061:
            boolean r5 = r6.hasNext()
            if (r5 == 0) goto L_0x01a2
            java.lang.Object r5 = r6.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r13 = r5.getKey()
            com.google.firebase.firestore.model.DocumentKey r13 = (com.google.firebase.firestore.model.DocumentKey) r13
            com.google.firebase.firestore.model.Document r14 = r9.getDocument(r13)
            r15 = 0
            java.lang.Object r16 = r5.getValue()
            r7 = r16
            com.google.firebase.firestore.model.MaybeDocument r7 = (com.google.firebase.firestore.model.MaybeDocument) r7
            boolean r8 = r7 instanceof com.google.firebase.firestore.model.Document
            if (r8 == 0) goto L_0x0087
            r15 = r7
            com.google.firebase.firestore.model.Document r15 = (com.google.firebase.firestore.model.Document) r15
        L_0x0087:
            if (r15 == 0) goto L_0x00b4
            com.google.firebase.firestore.model.DocumentKey r8 = r15.getKey()
            boolean r8 = r13.equals(r8)
            r18 = r5
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r16 = 0
            r5[r16] = r13
            com.google.firebase.firestore.model.DocumentKey r19 = r15.getKey()
            r17 = 1
            r5[r17] = r19
            r19 = r6
            java.lang.String r6 = "Mismatching key in doc change %s != %s"
            com.google.firebase.firestore.util.Assert.hardAssert(r8, r6, r5)
            com.google.firebase.firestore.core.Query r5 = r0.query
            boolean r5 = r5.matches(r15)
            if (r5 != 0) goto L_0x00ba
            r15 = 0
            goto L_0x00ba
        L_0x00b4:
            r18 = r5
            r19 = r6
            r17 = 1
        L_0x00ba:
            if (r14 == 0) goto L_0x00ca
            com.google.firebase.database.collection.ImmutableSortedSet<com.google.firebase.firestore.model.DocumentKey> r5 = r0.mutatedKeys
            com.google.firebase.firestore.model.DocumentKey r6 = r14.getKey()
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x00ca
            r5 = 1
            goto L_0x00cb
        L_0x00ca:
            r5 = 0
        L_0x00cb:
            if (r15 == 0) goto L_0x00e8
            boolean r6 = r15.hasLocalMutations()
            if (r6 != 0) goto L_0x00e5
            com.google.firebase.database.collection.ImmutableSortedSet<com.google.firebase.firestore.model.DocumentKey> r6 = r0.mutatedKeys
            com.google.firebase.firestore.model.DocumentKey r8 = r15.getKey()
            boolean r6 = r6.contains(r8)
            if (r6 == 0) goto L_0x00e8
            boolean r6 = r15.hasCommittedMutations()
            if (r6 == 0) goto L_0x00e8
        L_0x00e5:
            r16 = 1
            goto L_0x00ea
        L_0x00e8:
            r16 = 0
        L_0x00ea:
            r6 = r16
            r8 = 0
            if (r14 == 0) goto L_0x014c
            if (r15 == 0) goto L_0x014c
            r20 = r7
            com.google.firebase.firestore.model.ObjectValue r7 = r14.getData()
            r16 = r8
            com.google.firebase.firestore.model.ObjectValue r8 = r15.getData()
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x0139
            boolean r8 = r0.shouldWaitForSyncedDocument(r14, r15)
            if (r8 != 0) goto L_0x0136
            com.google.firebase.firestore.core.DocumentViewChange$Type r8 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED
            com.google.firebase.firestore.core.DocumentViewChange r8 = com.google.firebase.firestore.core.DocumentViewChange.create(r8, r15)
            r2.addChange(r8)
            r8 = 1
            if (r10 == 0) goto L_0x0124
            r17 = r7
            com.google.firebase.firestore.core.Query r7 = r0.query
            java.util.Comparator r7 = r7.comparator()
            int r7 = r7.compare(r15, r10)
            if (r7 > 0) goto L_0x0134
            goto L_0x0126
        L_0x0124:
            r17 = r7
        L_0x0126:
            if (r11 == 0) goto L_0x014b
            com.google.firebase.firestore.core.Query r7 = r0.query
            java.util.Comparator r7 = r7.comparator()
            int r7 = r7.compare(r15, r11)
            if (r7 >= 0) goto L_0x014b
        L_0x0134:
            r12 = 1
            goto L_0x014b
        L_0x0136:
            r17 = r7
            goto L_0x0149
        L_0x0139:
            r17 = r7
            if (r5 == r6) goto L_0x0149
            com.google.firebase.firestore.core.DocumentViewChange$Type r7 = com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA
            com.google.firebase.firestore.core.DocumentViewChange r7 = com.google.firebase.firestore.core.DocumentViewChange.create(r7, r15)
            r2.addChange(r7)
            r7 = 1
            r8 = r7
            goto L_0x014b
        L_0x0149:
            r8 = r16
        L_0x014b:
            goto L_0x0176
        L_0x014c:
            r20 = r7
            r16 = r8
            if (r14 != 0) goto L_0x015f
            if (r15 == 0) goto L_0x015f
            com.google.firebase.firestore.core.DocumentViewChange$Type r7 = com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED
            com.google.firebase.firestore.core.DocumentViewChange r7 = com.google.firebase.firestore.core.DocumentViewChange.create(r7, r15)
            r2.addChange(r7)
            r8 = 1
            goto L_0x0176
        L_0x015f:
            if (r14 == 0) goto L_0x0174
            if (r15 != 0) goto L_0x0174
            com.google.firebase.firestore.core.DocumentViewChange$Type r7 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED
            com.google.firebase.firestore.core.DocumentViewChange r7 = com.google.firebase.firestore.core.DocumentViewChange.create(r7, r14)
            r2.addChange(r7)
            r8 = 1
            if (r10 != 0) goto L_0x0171
            if (r11 == 0) goto L_0x0176
        L_0x0171:
            r7 = 1
            r12 = r7
            goto L_0x0176
        L_0x0174:
            r8 = r16
        L_0x0176:
            if (r8 == 0) goto L_0x019e
            if (r15 == 0) goto L_0x0196
            com.google.firebase.firestore.model.DocumentSet r4 = r4.add(r15)
            boolean r7 = r15.hasLocalMutations()
            if (r7 == 0) goto L_0x018d
            com.google.firebase.firestore.model.DocumentKey r7 = r15.getKey()
            com.google.firebase.database.collection.ImmutableSortedSet r3 = r3.insert(r7)
            goto L_0x019e
        L_0x018d:
            com.google.firebase.firestore.model.DocumentKey r7 = r15.getKey()
            com.google.firebase.database.collection.ImmutableSortedSet r3 = r3.remove(r7)
            goto L_0x019e
        L_0x0196:
            com.google.firebase.firestore.model.DocumentSet r4 = r4.remove(r13)
            com.google.firebase.database.collection.ImmutableSortedSet r3 = r3.remove(r13)
        L_0x019e:
            r6 = r19
            goto L_0x0061
        L_0x01a2:
            r17 = 1
            com.google.firebase.firestore.core.Query r5 = r0.query
            boolean r5 = r5.hasLimitToFirst()
            if (r5 != 0) goto L_0x01b8
            com.google.firebase.firestore.core.Query r5 = r0.query
            boolean r5 = r5.hasLimitToLast()
            if (r5 == 0) goto L_0x01b5
            goto L_0x01b8
        L_0x01b5:
            r13 = r3
            r14 = r4
            goto L_0x020a
        L_0x01b8:
            com.google.firebase.firestore.core.Query r5 = r0.query
            boolean r5 = r5.hasLimitToFirst()
            if (r5 == 0) goto L_0x01c7
            com.google.firebase.firestore.core.Query r5 = r0.query
            long r5 = r5.getLimitToFirst()
            goto L_0x01cd
        L_0x01c7:
            com.google.firebase.firestore.core.Query r5 = r0.query
            long r5 = r5.getLimitToLast()
        L_0x01cd:
            int r7 = r4.size()
            long r7 = (long) r7
            long r7 = r7 - r5
        L_0x01d3:
            r13 = 0
            int r15 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r15 <= 0) goto L_0x0208
            com.google.firebase.firestore.core.Query r13 = r0.query
            boolean r13 = r13.hasLimitToFirst()
            if (r13 == 0) goto L_0x01e6
            com.google.firebase.firestore.model.Document r13 = r4.getLastDocument()
            goto L_0x01ea
        L_0x01e6:
            com.google.firebase.firestore.model.Document r13 = r4.getFirstDocument()
        L_0x01ea:
            com.google.firebase.firestore.model.DocumentKey r14 = r13.getKey()
            com.google.firebase.firestore.model.DocumentSet r4 = r4.remove(r14)
            com.google.firebase.firestore.model.DocumentKey r14 = r13.getKey()
            com.google.firebase.database.collection.ImmutableSortedSet r3 = r3.remove(r14)
            com.google.firebase.firestore.core.DocumentViewChange$Type r14 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED
            com.google.firebase.firestore.core.DocumentViewChange r14 = com.google.firebase.firestore.core.DocumentViewChange.create(r14, r13)
            r2.addChange(r14)
            r13 = 1
            long r7 = r7 - r13
            goto L_0x01d3
        L_0x0208:
            r13 = r3
            r14 = r4
        L_0x020a:
            if (r12 == 0) goto L_0x0211
            if (r1 != 0) goto L_0x020f
            goto L_0x0211
        L_0x020f:
            r7 = 0
            goto L_0x0212
        L_0x0211:
            r7 = 1
        L_0x0212:
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r4 = "View was refilled using docs that themselves needed refilling."
            com.google.firebase.firestore.util.Assert.hardAssert(r7, r4, r3)
            com.google.firebase.firestore.core.View$DocumentChanges r15 = new com.google.firebase.firestore.core.View$DocumentChanges
            r8 = 0
            r3 = r15
            r4 = r14
            r5 = r2
            r6 = r13
            r7 = r12
            r3.<init>(r4, r5, r6, r7, r8)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.View.computeDocChanges(com.google.firebase.database.collection.ImmutableSortedMap, com.google.firebase.firestore.core.View$DocumentChanges):com.google.firebase.firestore.core.View$DocumentChanges");
    }

    private boolean shouldWaitForSyncedDocument(Document oldDoc, Document newDoc) {
        return oldDoc.hasLocalMutations() && newDoc.hasCommittedMutations() && !newDoc.hasLocalMutations();
    }

    public ViewChange applyChanges(DocumentChanges docChanges) {
        return applyChanges(docChanges, (TargetChange) null);
    }

    public ViewChange applyChanges(DocumentChanges docChanges, TargetChange targetChange) {
        ViewSnapshot snapshot;
        List<LimboDocumentChange> limboDocumentChanges;
        DocumentChanges documentChanges = docChanges;
        Assert.hardAssert(!docChanges.needsRefill, "Cannot apply changes that need a refill", new Object[0]);
        DocumentSet oldDocumentSet = this.documentSet;
        this.documentSet = documentChanges.documentSet;
        this.mutatedKeys = documentChanges.mutatedKeys;
        List<DocumentViewChange> viewChanges = documentChanges.changeSet.getChanges();
        Collections.sort(viewChanges, View$$Lambda$1.lambdaFactory$(this));
        applyTargetChange(targetChange);
        List<LimboDocumentChange> limboDocumentChanges2 = updateLimboDocuments();
        ViewSnapshot.SyncState newSyncState = this.limboDocuments.size() == 0 && this.current ? ViewSnapshot.SyncState.SYNCED : ViewSnapshot.SyncState.LOCAL;
        boolean syncStatedChanged = newSyncState != this.syncState;
        this.syncState = newSyncState;
        if (viewChanges.size() != 0 || syncStatedChanged) {
            ViewSnapshot.SyncState syncState2 = newSyncState;
            limboDocumentChanges = limboDocumentChanges2;
            snapshot = new ViewSnapshot(this.query, documentChanges.documentSet, oldDocumentSet, viewChanges, newSyncState == ViewSnapshot.SyncState.LOCAL, documentChanges.mutatedKeys, syncStatedChanged, false);
        } else {
            ViewSnapshot.SyncState syncState3 = newSyncState;
            limboDocumentChanges = limboDocumentChanges2;
            snapshot = null;
        }
        return new ViewChange(snapshot, limboDocumentChanges);
    }

    static /* synthetic */ int lambda$applyChanges$0(View view, DocumentViewChange o1, DocumentViewChange o2) {
        int typeComp = Util.compareIntegers(changeTypeOrder(o1), changeTypeOrder(o2));
        o1.getType().compareTo(o2.getType());
        if (typeComp != 0) {
            return typeComp;
        }
        return view.query.comparator().compare(o1.getDocument(), o2.getDocument());
    }

    public ViewChange applyOnlineStateChange(OnlineState onlineState) {
        if (!this.current || onlineState != OnlineState.OFFLINE) {
            return new ViewChange((ViewSnapshot) null, Collections.emptyList());
        }
        this.current = false;
        return applyChanges(new DocumentChanges(this.documentSet, new DocumentViewChangeSet(), this.mutatedKeys, false, (C18961) null));
    }

    private void applyTargetChange(TargetChange targetChange) {
        if (targetChange != null) {
            Iterator<DocumentKey> it = targetChange.getAddedDocuments().iterator();
            while (it.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.insert(it.next());
            }
            Iterator<DocumentKey> it2 = targetChange.getModifiedDocuments().iterator();
            while (it2.hasNext()) {
                DocumentKey documentKey = it2.next();
                Assert.hardAssert(this.syncedDocuments.contains(documentKey), "Modified document %s not found in view.", documentKey);
            }
            Iterator<DocumentKey> it3 = targetChange.getRemovedDocuments().iterator();
            while (it3.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.remove(it3.next());
            }
            this.current = targetChange.isCurrent();
        }
    }

    private List<LimboDocumentChange> updateLimboDocuments() {
        if (!this.current) {
            return Collections.emptyList();
        }
        ImmutableSortedSet<DocumentKey> oldLimboDocs = this.limboDocuments;
        this.limboDocuments = DocumentKey.emptyKeySet();
        Iterator<Document> it = this.documentSet.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            if (shouldBeLimboDoc(doc.getKey())) {
                this.limboDocuments = this.limboDocuments.insert(doc.getKey());
            }
        }
        List<LimboDocumentChange> changes = new ArrayList<>(oldLimboDocs.size() + this.limboDocuments.size());
        Iterator<DocumentKey> it2 = oldLimboDocs.iterator();
        while (it2.hasNext()) {
            DocumentKey key = it2.next();
            if (!this.limboDocuments.contains(key)) {
                changes.add(new LimboDocumentChange(LimboDocumentChange.Type.REMOVED, key));
            }
        }
        Iterator<DocumentKey> it3 = this.limboDocuments.iterator();
        while (it3.hasNext()) {
            DocumentKey key2 = it3.next();
            if (!oldLimboDocs.contains(key2)) {
                changes.add(new LimboDocumentChange(LimboDocumentChange.Type.ADDED, key2));
            }
        }
        return changes;
    }

    private boolean shouldBeLimboDoc(DocumentKey key) {
        Document doc;
        if (!this.syncedDocuments.contains(key) && (doc = this.documentSet.getDocument(key)) != null && !doc.hasLocalMutations()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<DocumentKey> getLimboDocuments() {
        return this.limboDocuments;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<DocumentKey> getSyncedDocuments() {
        return this.syncedDocuments;
    }

    /* renamed from: com.google.firebase.firestore.core.View$1 */
    static /* synthetic */ class C18961 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type */
        static final /* synthetic */ int[] f414x33862af7;

        static {
            int[] iArr = new int[DocumentViewChange.Type.values().length];
            f414x33862af7 = iArr;
            try {
                iArr[DocumentViewChange.Type.ADDED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f414x33862af7[DocumentViewChange.Type.MODIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f414x33862af7[DocumentViewChange.Type.METADATA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f414x33862af7[DocumentViewChange.Type.REMOVED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static int changeTypeOrder(DocumentViewChange change) {
        int i = C18961.f414x33862af7[change.getType().ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i == 2 || i == 3) {
            return 2;
        }
        if (i == 4) {
            return 0;
        }
        throw new IllegalArgumentException("Unknown change type: " + change.getType());
    }
}
