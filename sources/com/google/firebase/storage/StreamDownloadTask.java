package com.google.firebase.storage;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.common.net.HttpHeaders;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class StreamDownloadTask extends StorageTask<TaskSnapshot> {
    static final long PREFERRED_CHUNK_SIZE = 262144;
    private static final String TAG = "StreamDownloadTask";
    private long bytesDownloaded;
    private long bytesDownloadedSnapped;
    private String eTagVerification;
    private volatile Exception exception = null;
    /* access modifiers changed from: private */
    public InputStream inputStream;
    private StreamProcessor processor;
    /* access modifiers changed from: private */
    public NetworkRequest request;
    private volatile int resultCode = 0;
    private ExponentialBackoffSender sender;
    private StorageReference storageRef;
    private long totalBytes = -1;

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    public interface StreamProcessor {
        void doInBackground(TaskSnapshot taskSnapshot, InputStream inputStream) throws IOException;
    }

    StreamDownloadTask(StorageReference storageRef2) {
        this.storageRef = storageRef2;
        FirebaseStorage storage = storageRef2.getStorage();
        this.sender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    /* access modifiers changed from: package-private */
    public StreamDownloadTask setStreamProcessor(StreamProcessor processor2) {
        Preconditions.checkNotNull(processor2);
        Preconditions.checkState(this.processor == null);
        this.processor = processor2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public StorageReference getStorage() {
        return this.storageRef;
    }

    /* access modifiers changed from: package-private */
    public long getTotalBytes() {
        return this.totalBytes;
    }

    /* access modifiers changed from: package-private */
    public void recordDownloadedBytes(long bytesDownloaded2) {
        long j = this.bytesDownloaded + bytesDownloaded2;
        this.bytesDownloaded = j;
        if (this.bytesDownloadedSnapped + 262144 > j) {
            return;
        }
        if (getInternalState() == 4) {
            tryChangeState(4, false);
        } else {
            this.bytesDownloadedSnapped = this.bytesDownloaded;
        }
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        StorageTaskScheduler.getInstance().scheduleDownload(getRunnable());
    }

    /* access modifiers changed from: private */
    public InputStream createDownloadStream() throws Exception {
        String str;
        this.sender.reset();
        NetworkRequest networkRequest = this.request;
        if (networkRequest != null) {
            networkRequest.performRequestEnd();
        }
        GetNetworkRequest getNetworkRequest = new GetNetworkRequest(this.storageRef.getStorageUri(), this.storageRef.getApp(), this.bytesDownloaded);
        this.request = getNetworkRequest;
        boolean success = false;
        this.sender.sendWithExponentialBackoff(getNetworkRequest, false);
        this.resultCode = this.request.getResultCode();
        this.exception = this.request.getException() != null ? this.request.getException() : this.exception;
        if (isValidHttpResponseCode(this.resultCode) && this.exception == null && getInternalState() == 4) {
            success = true;
        }
        if (success) {
            String newEtag = this.request.getResultString(HttpHeaders.ETAG);
            if (TextUtils.isEmpty(newEtag) || (str = this.eTagVerification) == null || str.equals(newEtag)) {
                this.eTagVerification = newEtag;
                if (this.totalBytes == -1) {
                    this.totalBytes = (long) this.request.getResultingContentLength();
                }
                return this.request.getStream();
            }
            this.resultCode = 409;
            throw new IOException("The ETag on the server changed.");
        }
        throw new IOException("Could not open resulting stream.");
    }

    /* access modifiers changed from: package-private */
    public void run() {
        int i = 64;
        if (this.exception != null) {
            tryChangeState(64, false);
        } else if (tryChangeState(4, false)) {
            StreamProgressWrapper streamWrapper = new StreamProgressWrapper(new Callable<InputStream>() {
                public InputStream call() throws Exception {
                    return StreamDownloadTask.this.createDownloadStream();
                }
            }, this);
            this.inputStream = new BufferedInputStream(streamWrapper);
            try {
                boolean unused = streamWrapper.ensureStream();
                if (this.processor != null) {
                    try {
                        this.processor.doInBackground((TaskSnapshot) snapState(), this.inputStream);
                    } catch (Exception e) {
                        Log.w(TAG, "Exception occurred calling doInBackground.", e);
                        this.exception = e;
                    }
                }
            } catch (IOException e2) {
                Log.d(TAG, "Initial opening of Stream failed", e2);
                this.exception = e2;
            }
            if (this.inputStream == null) {
                this.request.performRequestEnd();
                this.request = null;
            }
            if (this.exception == null && getInternalState() == 4) {
                tryChangeState(4, false);
                tryChangeState(128, false);
                return;
            }
            if (getInternalState() == 32) {
                i = 256;
            }
            if (!tryChangeState(i, false)) {
                Log.w(TAG, "Unable to change download task to final state from " + getInternalState());
            }
        }
    }

    public boolean resume() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    public boolean pause() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    /* access modifiers changed from: package-private */
    public TaskSnapshot snapStateImpl() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.exception, this.resultCode), this.bytesDownloadedSnapped);
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
        this.sender.cancel();
        this.exception = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
    }

    /* access modifiers changed from: protected */
    public void onProgress() {
        this.bytesDownloadedSnapped = this.bytesDownloaded;
    }

    private boolean isValidHttpResponseCode(int code) {
        return code == 308 || (code >= 200 && code < 300);
    }

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    static class StreamProgressWrapper extends InputStream {
        private long mDownloadedBytes;
        private Callable<InputStream> mInputStreamCallable;
        private long mLastExceptionPosition;
        private StreamDownloadTask mParentTask;
        private boolean mStreamClosed;
        private IOException mTemporaryException;
        private InputStream mWrappedStream;

        StreamProgressWrapper(Callable<InputStream> inputStreamCallable, StreamDownloadTask parentTask) {
            this.mParentTask = parentTask;
            this.mInputStreamCallable = inputStreamCallable;
        }

        private void checkCancel() throws IOException {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null && streamDownloadTask.getInternalState() == 32) {
                throw new CancelException();
            }
        }

        private void recordDownloadedBytes(long delta) {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null) {
                streamDownloadTask.recordDownloadedBytes(delta);
            }
            this.mDownloadedBytes += delta;
        }

        /* access modifiers changed from: private */
        public boolean ensureStream() throws IOException {
            checkCancel();
            if (this.mTemporaryException != null) {
                try {
                    if (this.mWrappedStream != null) {
                        this.mWrappedStream.close();
                    }
                } catch (IOException e) {
                }
                this.mWrappedStream = null;
                if (this.mLastExceptionPosition == this.mDownloadedBytes) {
                    Log.i(StreamDownloadTask.TAG, "Encountered exception during stream operation. Aborting.", this.mTemporaryException);
                    return false;
                }
                Log.i(StreamDownloadTask.TAG, "Encountered exception during stream operation. Retrying at " + this.mDownloadedBytes, this.mTemporaryException);
                this.mLastExceptionPosition = this.mDownloadedBytes;
                this.mTemporaryException = null;
            }
            if (this.mStreamClosed) {
                throw new IOException("Can't perform operation on closed stream");
            } else if (this.mWrappedStream != null) {
                return true;
            } else {
                try {
                    this.mWrappedStream = this.mInputStreamCallable.call();
                    return true;
                } catch (Exception e2) {
                    if (e2 instanceof IOException) {
                        throw ((IOException) e2);
                    }
                    throw new IOException("Unable to open stream", e2);
                }
            }
        }

        public int read() throws IOException {
            while (ensureStream()) {
                try {
                    int returnValue = this.mWrappedStream.read();
                    if (returnValue != -1) {
                        recordDownloadedBytes(1);
                    }
                    return returnValue;
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        public int available() throws IOException {
            while (ensureStream()) {
                try {
                    return this.mWrappedStream.available();
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        public void close() throws IOException {
            InputStream inputStream = this.mWrappedStream;
            if (inputStream != null) {
                inputStream.close();
            }
            this.mStreamClosed = true;
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (!(streamDownloadTask == null || streamDownloadTask.request == null)) {
                this.mParentTask.request.performRequestEnd();
                NetworkRequest unused = this.mParentTask.request = null;
            }
            checkCancel();
        }

        public void mark(int readlimit) {
        }

        public boolean markSupported() {
            return false;
        }

        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            int bytesRead = 0;
            while (ensureStream()) {
                while (((long) byteCount) > 262144) {
                    try {
                        int deltaBytesRead = this.mWrappedStream.read(buffer, byteOffset, 262144);
                        if (deltaBytesRead != -1) {
                            bytesRead += deltaBytesRead;
                            byteOffset += deltaBytesRead;
                            byteCount -= deltaBytesRead;
                            recordDownloadedBytes((long) deltaBytesRead);
                            checkCancel();
                        } else if (bytesRead == 0) {
                            return -1;
                        } else {
                            return bytesRead;
                        }
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (byteCount > 0) {
                    int deltaBytesRead2 = this.mWrappedStream.read(buffer, byteOffset, byteCount);
                    if (deltaBytesRead2 != -1) {
                        byteOffset += deltaBytesRead2;
                        bytesRead += deltaBytesRead2;
                        byteCount -= deltaBytesRead2;
                        recordDownloadedBytes((long) deltaBytesRead2);
                    } else if (bytesRead == 0) {
                        return -1;
                    } else {
                        return bytesRead;
                    }
                }
                if (byteCount == 0) {
                    return bytesRead;
                }
            }
            throw this.mTemporaryException;
        }

        public long skip(long byteCount) throws IOException {
            long bytesSkipped = 0;
            while (ensureStream()) {
                while (byteCount > 262144) {
                    try {
                        long deltaBytesSkipped = this.mWrappedStream.skip(262144);
                        if (deltaBytesSkipped >= 0) {
                            bytesSkipped += deltaBytesSkipped;
                            byteCount -= deltaBytesSkipped;
                            recordDownloadedBytes(deltaBytesSkipped);
                            checkCancel();
                        } else if (bytesSkipped == 0) {
                            return -1;
                        } else {
                            return bytesSkipped;
                        }
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (byteCount > 0) {
                    long deltaBytesSkipped2 = this.mWrappedStream.skip(byteCount);
                    if (deltaBytesSkipped2 >= 0) {
                        bytesSkipped += deltaBytesSkipped2;
                        byteCount -= deltaBytesSkipped2;
                        recordDownloadedBytes(deltaBytesSkipped2);
                    } else if (bytesSkipped == 0) {
                        return -1;
                    } else {
                        return bytesSkipped;
                    }
                }
                if (byteCount == 0) {
                    return bytesSkipped;
                }
            }
            throw this.mTemporaryException;
        }
    }

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    public class TaskSnapshot extends StorageTask<TaskSnapshot>.SnapshotBase {
        private final long mBytesDownloaded;

        TaskSnapshot(Exception error, long bytesDownloaded) {
            super(error);
            this.mBytesDownloaded = bytesDownloaded;
        }

        public long getBytesTransferred() {
            return this.mBytesDownloaded;
        }

        public long getTotalByteCount() {
            return StreamDownloadTask.this.getTotalBytes();
        }

        public InputStream getStream() {
            return StreamDownloadTask.this.inputStream;
        }
    }
}
