package com.google.firebase.firestore;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.EmptyCredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Preconditions;
import java.util.concurrent.Executor;

public class FirebaseFirestore {
    private static final String TAG = "FirebaseFirestore";
    private final AsyncQueue asyncQueue;
    private volatile FirestoreClient client;
    private final Context context;
    private final CredentialsProvider credentialsProvider;
    private final DatabaseId databaseId;
    private final FirebaseApp firebaseApp;
    private final InstanceRegistry instanceRegistry;
    private final GrpcMetadataProvider metadataProvider;
    private final String persistenceKey;
    private FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
    private final UserDataReader userDataReader;

    public interface InstanceRegistry {
        void remove(String str);
    }

    public static FirebaseFirestore getInstance() {
        FirebaseApp app = FirebaseApp.getInstance();
        if (app != null) {
            return getInstance(app, DatabaseId.DEFAULT_DATABASE_ID);
        }
        throw new IllegalStateException("You must call FirebaseApp.initializeApp first.");
    }

    public static FirebaseFirestore getInstance(FirebaseApp app) {
        return getInstance(app, DatabaseId.DEFAULT_DATABASE_ID);
    }

    private static FirebaseFirestore getInstance(FirebaseApp app, String database) {
        Preconditions.checkNotNull(app, "Provided FirebaseApp must not be null.");
        FirestoreMultiDbComponent component = (FirestoreMultiDbComponent) app.get(FirestoreMultiDbComponent.class);
        Preconditions.checkNotNull(component, "Firestore component is not present.");
        return component.get(database);
    }

    static FirebaseFirestore newInstance(Context context2, FirebaseApp app, InternalAuthProvider authProvider, String database, InstanceRegistry instanceRegistry2, GrpcMetadataProvider metadataProvider2) {
        CredentialsProvider provider;
        InternalAuthProvider internalAuthProvider = authProvider;
        String projectId = app.getOptions().getProjectId();
        if (projectId != null) {
            DatabaseId databaseId2 = DatabaseId.forDatabase(projectId, database);
            AsyncQueue queue = new AsyncQueue();
            if (internalAuthProvider == null) {
                Logger.debug(TAG, "Firebase Auth not available, falling back to unauthenticated usage.", new Object[0]);
                provider = new EmptyCredentialsProvider();
            } else {
                provider = new FirebaseAuthCredentialsProvider(internalAuthProvider);
            }
            return new FirebaseFirestore(context2, databaseId2, app.getName(), provider, queue, app, instanceRegistry2, metadataProvider2);
        }
        String str = database;
        throw new IllegalArgumentException("FirebaseOptions.getProjectId() cannot be null");
    }

    FirebaseFirestore(Context context2, DatabaseId databaseId2, String persistenceKey2, CredentialsProvider credentialsProvider2, AsyncQueue asyncQueue2, FirebaseApp firebaseApp2, InstanceRegistry instanceRegistry2, GrpcMetadataProvider metadataProvider2) {
        this.context = (Context) Preconditions.checkNotNull(context2);
        this.databaseId = (DatabaseId) Preconditions.checkNotNull((DatabaseId) Preconditions.checkNotNull(databaseId2));
        this.userDataReader = new UserDataReader(databaseId2);
        this.persistenceKey = (String) Preconditions.checkNotNull(persistenceKey2);
        this.credentialsProvider = (CredentialsProvider) Preconditions.checkNotNull(credentialsProvider2);
        this.asyncQueue = (AsyncQueue) Preconditions.checkNotNull(asyncQueue2);
        this.firebaseApp = firebaseApp2;
        this.instanceRegistry = instanceRegistry2;
        this.metadataProvider = metadataProvider2;
    }

    public FirebaseFirestoreSettings getFirestoreSettings() {
        return this.settings;
    }

    public void setFirestoreSettings(FirebaseFirestoreSettings settings2) {
        synchronized (this.databaseId) {
            Preconditions.checkNotNull(settings2, "Provided settings must not be null.");
            if (this.client != null) {
                if (!this.settings.equals(settings2)) {
                    throw new IllegalStateException("FirebaseFirestore has already been started and its settings can no longer be changed. You can only call setFirestoreSettings() before calling any other methods on a FirebaseFirestore object.");
                }
            }
            this.settings = settings2;
        }
    }

    private void ensureClientConfigured() {
        if (this.client == null) {
            synchronized (this.databaseId) {
                if (this.client == null) {
                    this.client = new FirestoreClient(this.context, new DatabaseInfo(this.databaseId, this.persistenceKey, this.settings.getHost(), this.settings.isSslEnabled()), this.settings, this.credentialsProvider, this.asyncQueue, this.metadataProvider);
                }
            }
        }
    }

    public FirebaseApp getApp() {
        return this.firebaseApp;
    }

    public CollectionReference collection(String collectionPath) {
        Preconditions.checkNotNull(collectionPath, "Provided collection path must not be null.");
        ensureClientConfigured();
        return new CollectionReference(ResourcePath.fromString(collectionPath), this);
    }

    public DocumentReference document(String documentPath) {
        Preconditions.checkNotNull(documentPath, "Provided document path must not be null.");
        ensureClientConfigured();
        return DocumentReference.forPath(ResourcePath.fromString(documentPath), this);
    }

    public Query collectionGroup(String collectionId) {
        Preconditions.checkNotNull(collectionId, "Provided collection ID must not be null.");
        if (!collectionId.contains("/")) {
            ensureClientConfigured();
            return new Query(new Query(ResourcePath.EMPTY, collectionId), this);
        }
        throw new IllegalArgumentException(String.format("Invalid collectionId '%s'. Collection IDs must not contain '/'.", new Object[]{collectionId}));
    }

    private <ResultT> Task<ResultT> runTransaction(Transaction.Function<ResultT> updateFunction, Executor executor) {
        ensureClientConfigured();
        return this.client.transaction(FirebaseFirestore$$Lambda$1.lambdaFactory$(this, executor, updateFunction));
    }

    public <TResult> Task<TResult> runTransaction(Transaction.Function<TResult> updateFunction) {
        Preconditions.checkNotNull(updateFunction, "Provided transaction update function must not be null.");
        return runTransaction(updateFunction, com.google.firebase.firestore.core.Transaction.getDefaultExecutor());
    }

    public WriteBatch batch() {
        ensureClientConfigured();
        return new WriteBatch(this);
    }

    public Task<Void> runBatch(WriteBatch.Function batchFunction) {
        WriteBatch batch = batch();
        batchFunction.apply(batch);
        return batch.commit();
    }

    /* access modifiers changed from: package-private */
    public Task<Void> terminateInternal() {
        ensureClientConfigured();
        return this.client.terminate();
    }

    public Task<Void> terminate() {
        this.instanceRegistry.remove(getDatabaseId().getDatabaseId());
        return terminateInternal();
    }

    public Task<Void> waitForPendingWrites() {
        return this.client.waitForPendingWrites();
    }

    /* access modifiers changed from: package-private */
    public AsyncQueue getAsyncQueue() {
        return this.asyncQueue;
    }

    public Task<Void> enableNetwork() {
        ensureClientConfigured();
        return this.client.enableNetwork();
    }

    public Task<Void> disableNetwork() {
        ensureClientConfigured();
        return this.client.disableNetwork();
    }

    public static void setLoggingEnabled(boolean loggingEnabled) {
        if (loggingEnabled) {
            Logger.setLogLevel(Logger.Level.DEBUG);
        } else {
            Logger.setLogLevel(Logger.Level.WARN);
        }
    }

    public Task<Void> clearPersistence() {
        TaskCompletionSource<Void> source = new TaskCompletionSource<>();
        this.asyncQueue.enqueueAndForgetEvenAfterShutdown(FirebaseFirestore$$Lambda$2.lambdaFactory$(this, source));
        return source.getTask();
    }

    static /* synthetic */ void lambda$clearPersistence$2(FirebaseFirestore firebaseFirestore, TaskCompletionSource source) {
        try {
            if (firebaseFirestore.client != null) {
                if (!firebaseFirestore.client.isTerminated()) {
                    throw new FirebaseFirestoreException("Persistence cannot be cleared while the firestore instance is running.", FirebaseFirestoreException.Code.FAILED_PRECONDITION);
                }
            }
            SQLitePersistence.clearPersistence(firebaseFirestore.context, firebaseFirestore.databaseId, firebaseFirestore.persistenceKey);
            source.setResult(null);
        } catch (FirebaseFirestoreException e) {
            source.setException(e);
        }
    }

    public ListenerRegistration addSnapshotsInSyncListener(Runnable runnable) {
        return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, runnable);
    }

    public ListenerRegistration addSnapshotsInSyncListener(Activity activity, Runnable runnable) {
        return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, activity, runnable);
    }

    public ListenerRegistration addSnapshotsInSyncListener(Executor executor, Runnable runnable) {
        return addSnapshotsInSyncListener(executor, (Activity) null, runnable);
    }

    private ListenerRegistration addSnapshotsInSyncListener(Executor userExecutor, Activity activity, Runnable runnable) {
        ensureClientConfigured();
        AsyncEventListener<Void> asyncListener = new AsyncEventListener<>(userExecutor, FirebaseFirestore$$Lambda$3.lambdaFactory$(runnable));
        this.client.addSnapshotsInSyncListener(asyncListener);
        return ActivityScope.bind(activity, FirebaseFirestore$$Lambda$4.lambdaFactory$(this, asyncListener));
    }

    static /* synthetic */ void lambda$addSnapshotsInSyncListener$3(Runnable runnable, Void v, FirebaseFirestoreException error) {
        Assert.hardAssert(error == null, "snapshots-in-sync listeners should never get errors.", new Object[0]);
        runnable.run();
    }

    static /* synthetic */ void lambda$addSnapshotsInSyncListener$4(FirebaseFirestore firebaseFirestore, AsyncEventListener asyncListener) {
        asyncListener.mute();
        firebaseFirestore.client.removeSnapshotsInSyncListener(asyncListener);
    }

    /* access modifiers changed from: package-private */
    public FirestoreClient getClient() {
        return this.client;
    }

    /* access modifiers changed from: package-private */
    public DatabaseId getDatabaseId() {
        return this.databaseId;
    }

    /* access modifiers changed from: package-private */
    public UserDataReader getUserDataReader() {
        return this.userDataReader;
    }

    /* access modifiers changed from: package-private */
    public void validateReference(DocumentReference docRef) {
        Preconditions.checkNotNull(docRef, "Provided DocumentReference must not be null.");
        if (docRef.getFirestore() != this) {
            throw new IllegalArgumentException("Provided document reference is from a different Cloud Firestore instance.");
        }
    }
}
