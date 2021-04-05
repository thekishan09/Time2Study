package com.google.firebase.firestore;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.core.UserData;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.firestore.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Transaction {
    private final FirebaseFirestore firestore;
    private final com.google.firebase.firestore.core.Transaction transaction;

    public interface Function<TResult> {
        TResult apply(Transaction transaction) throws FirebaseFirestoreException;
    }

    Transaction(com.google.firebase.firestore.core.Transaction transaction2, FirebaseFirestore firestore2) {
        this.transaction = (com.google.firebase.firestore.core.Transaction) Preconditions.checkNotNull(transaction2);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firestore2);
    }

    public Transaction set(DocumentReference documentRef, Object data) {
        return set(documentRef, data, SetOptions.OVERWRITE);
    }

    public Transaction set(DocumentReference documentRef, Object data, SetOptions options) {
        UserData.ParsedSetData parsed;
        this.firestore.validateReference(documentRef);
        Preconditions.checkNotNull(data, "Provided data must not be null.");
        Preconditions.checkNotNull(options, "Provided options must not be null.");
        if (options.isMerge()) {
            parsed = this.firestore.getUserDataReader().parseMergeData(data, options.getFieldMask());
        } else {
            parsed = this.firestore.getUserDataReader().parseSetData(data);
        }
        this.transaction.set(documentRef.getKey(), parsed);
        return this;
    }

    public Transaction update(DocumentReference documentRef, Map<String, Object> data) {
        return update(documentRef, this.firestore.getUserDataReader().parseUpdateData(data));
    }

    public Transaction update(DocumentReference documentRef, String field, Object value, Object... moreFieldsAndValues) {
        return update(documentRef, this.firestore.getUserDataReader().parseUpdateData(Util.collectUpdateArguments(1, field, value, moreFieldsAndValues)));
    }

    public Transaction update(DocumentReference documentRef, FieldPath fieldPath, Object value, Object... moreFieldsAndValues) {
        return update(documentRef, this.firestore.getUserDataReader().parseUpdateData(Util.collectUpdateArguments(1, fieldPath, value, moreFieldsAndValues)));
    }

    private Transaction update(DocumentReference documentRef, UserData.ParsedUpdateData updateData) {
        this.firestore.validateReference(documentRef);
        this.transaction.update(documentRef.getKey(), updateData);
        return this;
    }

    public Transaction delete(DocumentReference documentRef) {
        this.firestore.validateReference(documentRef);
        this.transaction.delete(documentRef.getKey());
        return this;
    }

    private Task<DocumentSnapshot> getAsync(DocumentReference documentRef) {
        return this.transaction.lookup(Collections.singletonList(documentRef.getKey())).continueWith(Executors.DIRECT_EXECUTOR, Transaction$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ DocumentSnapshot lambda$getAsync$0(Transaction transaction2, Task task) throws Exception {
        if (task.isSuccessful()) {
            List<MaybeDocument> docs = (List) task.getResult();
            if (docs.size() == 1) {
                MaybeDocument doc = docs.get(0);
                if (doc instanceof Document) {
                    return DocumentSnapshot.fromDocument(transaction2.firestore, (Document) doc, false, false);
                }
                if (doc instanceof NoDocument) {
                    return DocumentSnapshot.fromNoDocument(transaction2.firestore, doc.getKey(), false, false);
                }
                throw Assert.fail("BatchGetDocumentsRequest returned unexpected document type: " + doc.getClass().getCanonicalName(), new Object[0]);
            }
            throw Assert.fail("Mismatch in docs returned from document lookup.", new Object[0]);
        }
        throw task.getException();
    }

    public DocumentSnapshot get(DocumentReference documentRef) throws FirebaseFirestoreException {
        this.firestore.validateReference(documentRef);
        try {
            return (DocumentSnapshot) Tasks.await(getAsync(documentRef));
        } catch (ExecutionException ee) {
            if (ee.getCause() instanceof FirebaseFirestoreException) {
                throw ((FirebaseFirestoreException) ee.getCause());
            }
            throw new RuntimeException(ee.getCause());
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}
