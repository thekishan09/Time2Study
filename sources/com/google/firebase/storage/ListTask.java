package com.google.firebase.storage;

import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.ListNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import org.json.JSONException;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
class ListTask implements Runnable {
    private static final String TAG = "ListTask";
    private final Integer maxResults;
    private final String pageToken;
    private final TaskCompletionSource<ListResult> pendingResult;
    private final ExponentialBackoffSender sender;
    private final StorageReference storageRef;

    ListTask(StorageReference storageRef2, Integer maxResults2, String pageToken2, TaskCompletionSource<ListResult> pendingResult2) {
        Preconditions.checkNotNull(storageRef2);
        Preconditions.checkNotNull(pendingResult2);
        this.storageRef = storageRef2;
        this.maxResults = maxResults2;
        this.pageToken = pageToken2;
        this.pendingResult = pendingResult2;
        FirebaseStorage storage = storageRef2.getStorage();
        this.sender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    public void run() {
        NetworkRequest request = new ListNetworkRequest(this.storageRef.getStorageUri(), this.storageRef.getApp(), this.maxResults, this.pageToken);
        this.sender.sendWithExponentialBackoff(request);
        ListResult listResult = null;
        if (request.isResultSuccess()) {
            try {
                listResult = ListResult.fromJSON(this.storageRef.getStorage(), request.getResultBody());
            } catch (JSONException e) {
                Log.e(TAG, "Unable to parse response body. " + request.getRawResult(), e);
                this.pendingResult.setException(StorageException.fromException(e));
                return;
            }
        }
        TaskCompletionSource<ListResult> taskCompletionSource = this.pendingResult;
        if (taskCompletionSource != null) {
            request.completeTask(taskCompletionSource, listResult);
        }
    }
}
