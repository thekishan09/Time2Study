package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.internal.Slashes;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class StorageReference implements Comparable<StorageReference> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "StorageReference";
    private final FirebaseStorage mFirebaseStorage;
    private final Uri mStorageUri;

    StorageReference(Uri storageUri, FirebaseStorage firebaseStorage) {
        boolean z = true;
        Preconditions.checkArgument(storageUri != null, "storageUri cannot be null");
        Preconditions.checkArgument(firebaseStorage == null ? false : z, "FirebaseApp cannot be null");
        this.mStorageUri = storageUri;
        this.mFirebaseStorage = firebaseStorage;
    }

    public StorageReference child(String pathString) {
        Preconditions.checkArgument(!TextUtils.isEmpty(pathString), "childName cannot be null or empty");
        return new StorageReference(this.mStorageUri.buildUpon().appendEncodedPath(Slashes.preserveSlashEncode(Slashes.normalizeSlashes(pathString))).build(), this.mFirebaseStorage);
    }

    public StorageReference getParent() {
        String path;
        String path2 = this.mStorageUri.getPath();
        if (TextUtils.isEmpty(path2) || path2.equals("/")) {
            return null;
        }
        int childIndex = path2.lastIndexOf(47);
        if (childIndex == -1) {
            path = "/";
        } else {
            path = path2.substring(0, childIndex);
        }
        return new StorageReference(this.mStorageUri.buildUpon().path(path).build(), this.mFirebaseStorage);
    }

    public StorageReference getRoot() {
        return new StorageReference(this.mStorageUri.buildUpon().path("").build(), this.mFirebaseStorage);
    }

    public String getName() {
        String path = this.mStorageUri.getPath();
        int lastIndex = path.lastIndexOf(47);
        if (lastIndex != -1) {
            return path.substring(lastIndex + 1);
        }
        return path;
    }

    public String getPath() {
        return this.mStorageUri.getPath();
    }

    public String getBucket() {
        return this.mStorageUri.getAuthority();
    }

    public FirebaseStorage getStorage() {
        return this.mFirebaseStorage;
    }

    /* access modifiers changed from: package-private */
    public FirebaseApp getApp() {
        return getStorage().getApp();
    }

    public UploadTask putBytes(byte[] bytes) {
        Preconditions.checkArgument(bytes != null, "bytes cannot be null");
        UploadTask task = new UploadTask(this, (StorageMetadata) null, bytes);
        task.queue();
        return task;
    }

    public UploadTask putBytes(byte[] bytes, StorageMetadata metadata) {
        boolean z = true;
        Preconditions.checkArgument(bytes != null, "bytes cannot be null");
        if (metadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask task = new UploadTask(this, metadata, bytes);
        task.queue();
        return task;
    }

    public UploadTask putFile(Uri uri) {
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        UploadTask task = new UploadTask(this, (StorageMetadata) null, uri, (Uri) null);
        task.queue();
        return task;
    }

    public UploadTask putFile(Uri uri, StorageMetadata metadata) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (metadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask task = new UploadTask(this, metadata, uri, (Uri) null);
        task.queue();
        return task;
    }

    public UploadTask putFile(Uri uri, StorageMetadata metadata, Uri existingUploadUri) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (metadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask task = new UploadTask(this, metadata, uri, existingUploadUri);
        task.queue();
        return task;
    }

    public UploadTask putStream(InputStream stream) {
        Preconditions.checkArgument(stream != null, "stream cannot be null");
        UploadTask task = new UploadTask(this, (StorageMetadata) null, stream);
        task.queue();
        return task;
    }

    public UploadTask putStream(InputStream stream, StorageMetadata metadata) {
        boolean z = true;
        Preconditions.checkArgument(stream != null, "stream cannot be null");
        if (metadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask task = new UploadTask(this, metadata, stream);
        task.queue();
        return task;
    }

    public List<UploadTask> getActiveUploadTasks() {
        return StorageTaskManager.getInstance().getUploadTasksUnder(this);
    }

    public List<FileDownloadTask> getActiveDownloadTasks() {
        return StorageTaskManager.getInstance().getDownloadTasksUnder(this);
    }

    public Task<StorageMetadata> getMetadata() {
        TaskCompletionSource<StorageMetadata> pendingResult = new TaskCompletionSource<>();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetMetadataTask(this, pendingResult));
        return pendingResult.getTask();
    }

    public Task<Uri> getDownloadUrl() {
        TaskCompletionSource<Uri> pendingResult = new TaskCompletionSource<>();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetDownloadUrlTask(this, pendingResult));
        return pendingResult.getTask();
    }

    public Task<StorageMetadata> updateMetadata(StorageMetadata metadata) {
        Preconditions.checkNotNull(metadata);
        TaskCompletionSource<StorageMetadata> pendingResult = new TaskCompletionSource<>();
        StorageTaskScheduler.getInstance().scheduleCommand(new UpdateMetadataTask(this, pendingResult, metadata));
        return pendingResult.getTask();
    }

    public Task<byte[]> getBytes(final long maxDownloadSizeBytes) {
        final TaskCompletionSource<byte[]> pendingResult = new TaskCompletionSource<>();
        StreamDownloadTask task = new StreamDownloadTask(this);
        task.setStreamProcessor(new StreamDownloadTask.StreamProcessor() {
            public void doInBackground(StreamDownloadTask.TaskSnapshot state, InputStream stream) throws IOException {
                try {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int totalRead = 0;
                    byte[] data = new byte[16384];
                    while (true) {
                        int read = stream.read(data, 0, data.length);
                        int nRead = read;
                        if (read != -1) {
                            totalRead += nRead;
                            if (((long) totalRead) <= maxDownloadSizeBytes) {
                                buffer.write(data, 0, nRead);
                            } else {
                                Log.e(StorageReference.TAG, "the maximum allowed buffer size was exceeded.");
                                throw new IndexOutOfBoundsException("the maximum allowed buffer size was exceeded.");
                            }
                        } else {
                            buffer.flush();
                            pendingResult.setResult(buffer.toByteArray());
                            return;
                        }
                    }
                } finally {
                    stream.close();
                }
            }
        }).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
            public void onSuccess(StreamDownloadTask.TaskSnapshot state) {
                if (!pendingResult.getTask().isComplete()) {
                    Log.e(StorageReference.TAG, "getBytes 'succeeded', but failed to set a Result.");
                    pendingResult.setException(StorageException.fromErrorStatus(Status.RESULT_INTERNAL_ERROR));
                }
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<StorageReference> cls = StorageReference.class;
            }

            public void onFailure(Exception e) {
                pendingResult.setException(StorageException.fromExceptionAndHttpCode(e, 0));
            }
        });
        task.queue();
        return pendingResult.getTask();
    }

    public FileDownloadTask getFile(Uri destinationUri) {
        FileDownloadTask task = new FileDownloadTask(this, destinationUri);
        task.queue();
        return task;
    }

    public FileDownloadTask getFile(File destinationFile) {
        return getFile(Uri.fromFile(destinationFile));
    }

    public StreamDownloadTask getStream() {
        StreamDownloadTask task = new StreamDownloadTask(this);
        task.queue();
        return task;
    }

    public StreamDownloadTask getStream(StreamDownloadTask.StreamProcessor processor) {
        StreamDownloadTask task = new StreamDownloadTask(this);
        task.setStreamProcessor(processor);
        task.queue();
        return task;
    }

    public Task<Void> delete() {
        TaskCompletionSource<Void> pendingResult = new TaskCompletionSource<>();
        StorageTaskScheduler.getInstance().scheduleCommand(new DeleteStorageTask(this, pendingResult));
        return pendingResult.getTask();
    }

    public Task<ListResult> list(int maxResults) {
        boolean z = true;
        Preconditions.checkArgument(maxResults > 0, "maxResults must be greater than zero");
        if (maxResults > 1000) {
            z = false;
        }
        Preconditions.checkArgument(z, "maxResults must be at most 1000");
        return listHelper(Integer.valueOf(maxResults), (String) null);
    }

    public Task<ListResult> list(int maxResults, String pageToken) {
        boolean z = true;
        Preconditions.checkArgument(maxResults > 0, "maxResults must be greater than zero");
        Preconditions.checkArgument(maxResults <= 1000, "maxResults must be at most 1000");
        if (pageToken == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "pageToken must be non-null to resume a previous list() operation");
        return listHelper(Integer.valueOf(maxResults), pageToken);
    }

    public Task<ListResult> listAll() {
        final TaskCompletionSource<ListResult> pendingResult = new TaskCompletionSource<>();
        final List<StorageReference> prefixes = new ArrayList<>();
        final List<StorageReference> items = new ArrayList<>();
        Executor executor = StorageTaskScheduler.getInstance().getCommandPoolExecutor();
        final Executor executor2 = executor;
        listHelper((Integer) null, (String) null).continueWithTask(executor, new Continuation<ListResult, Task<Void>>() {
            public Task<Void> then(Task<ListResult> currentPage) {
                if (currentPage.isSuccessful()) {
                    ListResult result = currentPage.getResult();
                    prefixes.addAll(result.getPrefixes());
                    items.addAll(result.getItems());
                    if (result.getPageToken() != null) {
                        StorageReference.this.listHelper((Integer) null, result.getPageToken()).continueWithTask(executor2, this);
                    } else {
                        pendingResult.setResult(new ListResult(prefixes, items, (String) null));
                    }
                } else {
                    pendingResult.setException(currentPage.getException());
                }
                return Tasks.forResult(null);
            }
        });
        return pendingResult.getTask();
    }

    /* access modifiers changed from: private */
    public Task<ListResult> listHelper(Integer maxResults, String pageToken) {
        TaskCompletionSource<ListResult> pendingResult = new TaskCompletionSource<>();
        StorageTaskScheduler.getInstance().scheduleCommand(new ListTask(this, maxResults, pageToken, pendingResult));
        return pendingResult.getTask();
    }

    /* access modifiers changed from: package-private */
    public Uri getStorageUri() {
        return this.mStorageUri;
    }

    public String toString() {
        return "gs://" + this.mStorageUri.getAuthority() + this.mStorageUri.getEncodedPath();
    }

    public boolean equals(Object other) {
        if (!(other instanceof StorageReference)) {
            return false;
        }
        return ((StorageReference) other).toString().equals(toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public int compareTo(StorageReference other) {
        return this.mStorageUri.compareTo(other.mStorageUri);
    }
}
