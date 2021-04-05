package com.google.firebase.firestore.core;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.remote.Datastore;
import com.google.firebase.firestore.remote.RemoteStore;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.ExponentialBackoff;
import com.google.firebase.firestore.util.Function;

public class TransactionRunner<TResult> {
    private static final int RETRY_COUNT = 5;
    private AsyncQueue asyncQueue;
    private ExponentialBackoff backoff;
    private RemoteStore remoteStore;
    private int retriesLeft;
    private TaskCompletionSource<TResult> taskSource = new TaskCompletionSource<>();
    private Function<Transaction, Task<TResult>> updateFunction;

    public TransactionRunner(AsyncQueue asyncQueue2, RemoteStore remoteStore2, Function<Transaction, Task<TResult>> updateFunction2) {
        this.asyncQueue = asyncQueue2;
        this.remoteStore = remoteStore2;
        this.updateFunction = updateFunction2;
        this.retriesLeft = 5;
        this.backoff = new ExponentialBackoff(asyncQueue2, AsyncQueue.TimerId.RETRY_TRANSACTION);
    }

    public Task<TResult> run() {
        runWithBackoff();
        return this.taskSource.getTask();
    }

    private void runWithBackoff() {
        this.backoff.backoffAndRun(TransactionRunner$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$runWithBackoff$2(TransactionRunner transactionRunner) {
        Transaction transaction = transactionRunner.remoteStore.createTransaction();
        transactionRunner.updateFunction.apply(transaction).addOnCompleteListener(transactionRunner.asyncQueue.getExecutor(), TransactionRunner$$Lambda$2.lambdaFactory$(transactionRunner, transaction));
    }

    static /* synthetic */ void lambda$runWithBackoff$1(TransactionRunner transactionRunner, Transaction transaction, Task userTask) {
        if (!userTask.isSuccessful()) {
            transactionRunner.handleTransactionError(userTask);
        } else {
            transaction.commit().addOnCompleteListener(transactionRunner.asyncQueue.getExecutor(), TransactionRunner$$Lambda$3.lambdaFactory$(transactionRunner, userTask));
        }
    }

    static /* synthetic */ void lambda$runWithBackoff$0(TransactionRunner transactionRunner, Task userTask, Task commitTask) {
        if (commitTask.isSuccessful()) {
            transactionRunner.taskSource.setResult(userTask.getResult());
        } else {
            transactionRunner.handleTransactionError(commitTask);
        }
    }

    private void handleTransactionError(Task task) {
        if (this.retriesLeft <= 0 || !isRetryableTransactionError(task.getException())) {
            this.taskSource.setException(task.getException());
            return;
        }
        this.retriesLeft--;
        runWithBackoff();
    }

    private static boolean isRetryableTransactionError(Exception e) {
        if (!(e instanceof FirebaseFirestoreException)) {
            return false;
        }
        FirebaseFirestoreException.Code code = ((FirebaseFirestoreException) e).getCode();
        if (code == FirebaseFirestoreException.Code.ABORTED || code == FirebaseFirestoreException.Code.FAILED_PRECONDITION || !Datastore.isPermanentError(((FirebaseFirestoreException) e).getCode())) {
            return true;
        }
        return false;
    }
}
