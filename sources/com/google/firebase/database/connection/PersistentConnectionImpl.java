package com.google.firebase.database.connection;

import androidx.core.app.NotificationCompat;
import com.google.firebase.database.connection.Connection;
import com.google.firebase.database.connection.ConnectionAuthTokenProvider;
import com.google.firebase.database.connection.PersistentConnection;
import com.google.firebase.database.connection.util.RetryHelper;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.util.GAuthToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PersistentConnectionImpl implements Connection.Delegate, PersistentConnection {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String IDLE_INTERRUPT_REASON = "connection_idle";
    private static final long IDLE_TIMEOUT = 60000;
    private static final long INVALID_AUTH_TOKEN_THRESHOLD = 3;
    private static final String REQUEST_ACTION = "a";
    private static final String REQUEST_ACTION_AUTH = "auth";
    private static final String REQUEST_ACTION_GAUTH = "gauth";
    private static final String REQUEST_ACTION_MERGE = "m";
    private static final String REQUEST_ACTION_ONDISCONNECT_CANCEL = "oc";
    private static final String REQUEST_ACTION_ONDISCONNECT_MERGE = "om";
    private static final String REQUEST_ACTION_ONDISCONNECT_PUT = "o";
    private static final String REQUEST_ACTION_PUT = "p";
    private static final String REQUEST_ACTION_QUERY = "q";
    private static final String REQUEST_ACTION_QUERY_UNLISTEN = "n";
    private static final String REQUEST_ACTION_STATS = "s";
    private static final String REQUEST_ACTION_UNAUTH = "unauth";
    private static final String REQUEST_AUTHVAR = "authvar";
    private static final String REQUEST_COMPOUND_HASH = "ch";
    private static final String REQUEST_COMPOUND_HASH_HASHES = "hs";
    private static final String REQUEST_COMPOUND_HASH_PATHS = "ps";
    private static final String REQUEST_COUNTERS = "c";
    private static final String REQUEST_CREDENTIAL = "cred";
    private static final String REQUEST_DATA_HASH = "h";
    private static final String REQUEST_DATA_PAYLOAD = "d";
    private static final String REQUEST_ERROR = "error";
    private static final String REQUEST_NUMBER = "r";
    private static final String REQUEST_PATH = "p";
    private static final String REQUEST_PAYLOAD = "b";
    private static final String REQUEST_QUERIES = "q";
    private static final String REQUEST_STATUS = "s";
    private static final String REQUEST_TAG = "t";
    private static final String RESPONSE_FOR_REQUEST = "b";
    private static final String SERVER_ASYNC_ACTION = "a";
    private static final String SERVER_ASYNC_AUTH_REVOKED = "ac";
    private static final String SERVER_ASYNC_DATA_MERGE = "m";
    private static final String SERVER_ASYNC_DATA_RANGE_MERGE = "rm";
    private static final String SERVER_ASYNC_DATA_UPDATE = "d";
    private static final String SERVER_ASYNC_LISTEN_CANCELLED = "c";
    private static final String SERVER_ASYNC_PAYLOAD = "b";
    private static final String SERVER_ASYNC_SECURITY_DEBUG = "sd";
    private static final String SERVER_DATA_END_PATH = "e";
    private static final String SERVER_DATA_RANGE_MERGE = "m";
    private static final String SERVER_DATA_START_PATH = "s";
    private static final String SERVER_DATA_TAG = "t";
    private static final String SERVER_DATA_UPDATE_BODY = "d";
    private static final String SERVER_DATA_UPDATE_PATH = "p";
    private static final String SERVER_DATA_WARNINGS = "w";
    private static final String SERVER_KILL_INTERRUPT_REASON = "server_kill";
    private static final String SERVER_RESPONSE_DATA = "d";
    private static final long SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY = 30000;
    private static final String TOKEN_REFRESH_INTERRUPT_REASON = "token_refresh";
    private static long connectionIds = 0;
    /* access modifiers changed from: private */
    public String authToken;
    /* access modifiers changed from: private */
    public final ConnectionAuthTokenProvider authTokenProvider;
    private String cachedHost;
    /* access modifiers changed from: private */
    public ConnectionState connectionState = ConnectionState.Disconnected;
    private final ConnectionContext context;
    /* access modifiers changed from: private */
    public long currentGetTokenAttempt = 0;
    /* access modifiers changed from: private */
    public final PersistentConnection.Delegate delegate;
    private final ScheduledExecutorService executorService;
    private boolean firstConnection = true;
    /* access modifiers changed from: private */
    public boolean forceAuthTokenRefresh;
    private boolean hasOnDisconnects;
    private final HostInfo hostInfo;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> inactivityTimer = null;
    private HashSet<String> interruptReasons = new HashSet<>();
    /* access modifiers changed from: private */
    public int invalidAuthTokenCount = 0;
    private long lastConnectionEstablishedTime;
    private String lastSessionId;
    private long lastWriteTimestamp;
    /* access modifiers changed from: private */
    public Map<ListenQuerySpec, OutstandingListen> listens;
    /* access modifiers changed from: private */
    public final LogWrapper logger;
    private List<OutstandingDisconnect> onDisconnectRequestQueue;
    /* access modifiers changed from: private */
    public Map<Long, OutstandingPut> outstandingPuts;
    /* access modifiers changed from: private */
    public Connection realtime;
    private Map<Long, ConnectionRequestCallback> requestCBHash;
    private long requestCounter = 0;
    /* access modifiers changed from: private */
    public final RetryHelper retryHelper;
    private long writeCounter = 0;

    private interface ConnectionRequestCallback {
        void onResponse(Map<String, Object> map);
    }

    private enum ConnectionState {
        Disconnected,
        GettingToken,
        Connecting,
        Authenticating,
        Connected
    }

    static /* synthetic */ int access$1308(PersistentConnectionImpl x0) {
        int i = x0.invalidAuthTokenCount;
        x0.invalidAuthTokenCount = i + 1;
        return i;
    }

    static /* synthetic */ long access$608(PersistentConnectionImpl x0) {
        long j = x0.currentGetTokenAttempt;
        x0.currentGetTokenAttempt = 1 + j;
        return j;
    }

    private static class ListenQuerySpec {
        /* access modifiers changed from: private */
        public final List<String> path;
        /* access modifiers changed from: private */
        public final Map<String, Object> queryParams;

        public ListenQuerySpec(List<String> path2, Map<String, Object> queryParams2) {
            this.path = path2;
            this.queryParams = queryParams2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListenQuerySpec)) {
                return false;
            }
            ListenQuerySpec that = (ListenQuerySpec) o;
            if (!this.path.equals(that.path)) {
                return false;
            }
            return this.queryParams.equals(that.queryParams);
        }

        public int hashCode() {
            return (this.path.hashCode() * 31) + this.queryParams.hashCode();
        }

        public String toString() {
            return ConnectionUtils.pathToString(this.path) + " (params: " + this.queryParams + ")";
        }
    }

    private static class OutstandingListen {
        private final ListenHashProvider hashFunction;
        /* access modifiers changed from: private */
        public final ListenQuerySpec query;
        /* access modifiers changed from: private */
        public final RequestResultCallback resultCallback;
        private final Long tag;

        private OutstandingListen(RequestResultCallback callback, ListenQuerySpec query2, Long tag2, ListenHashProvider hashFunction2) {
            this.resultCallback = callback;
            this.query = query2;
            this.hashFunction = hashFunction2;
            this.tag = tag2;
        }

        public ListenQuerySpec getQuery() {
            return this.query;
        }

        public Long getTag() {
            return this.tag;
        }

        public ListenHashProvider getHashFunction() {
            return this.hashFunction;
        }

        public String toString() {
            return this.query.toString() + " (Tag: " + this.tag + ")";
        }
    }

    private static class OutstandingPut {
        private String action;
        /* access modifiers changed from: private */
        public RequestResultCallback onComplete;
        private Map<String, Object> request;
        private boolean sent;

        private OutstandingPut(String action2, Map<String, Object> request2, RequestResultCallback onComplete2) {
            this.action = action2;
            this.request = request2;
            this.onComplete = onComplete2;
        }

        public String getAction() {
            return this.action;
        }

        public Map<String, Object> getRequest() {
            return this.request;
        }

        public RequestResultCallback getOnComplete() {
            return this.onComplete;
        }

        public void markSent() {
            this.sent = true;
        }

        public boolean wasSent() {
            return this.sent;
        }
    }

    private static class OutstandingDisconnect {
        private final String action;
        private final Object data;
        /* access modifiers changed from: private */
        public final RequestResultCallback onComplete;
        private final List<String> path;

        private OutstandingDisconnect(String action2, List<String> path2, Object data2, RequestResultCallback onComplete2) {
            this.action = action2;
            this.path = path2;
            this.data = data2;
            this.onComplete = onComplete2;
        }

        public String getAction() {
            return this.action;
        }

        public List<String> getPath() {
            return this.path;
        }

        public Object getData() {
            return this.data;
        }

        public RequestResultCallback getOnComplete() {
            return this.onComplete;
        }
    }

    public PersistentConnectionImpl(ConnectionContext context2, HostInfo info, PersistentConnection.Delegate delegate2) {
        this.delegate = delegate2;
        this.context = context2;
        this.executorService = context2.getExecutorService();
        this.authTokenProvider = context2.getAuthTokenProvider();
        this.hostInfo = info;
        this.listens = new HashMap();
        this.requestCBHash = new HashMap();
        this.outstandingPuts = new HashMap();
        this.onDisconnectRequestQueue = new ArrayList();
        this.retryHelper = new RetryHelper.Builder(this.executorService, context2.getLogger(), "ConnectionRetryHelper").withMinDelayAfterFailure(1000).withRetryExponent(1.3d).withMaxDelay(SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY).withJitterFactor(0.7d).build();
        long connId = connectionIds;
        connectionIds = 1 + connId;
        Logger logger2 = context2.getLogger();
        this.logger = new LogWrapper(logger2, "PersistentConnection", "pc_" + connId);
        this.lastSessionId = null;
        doIdleCheck();
    }

    public void onReady(long timestamp, String sessionId) {
        if (this.logger.logsDebug()) {
            this.logger.debug("onReady", new Object[0]);
        }
        this.lastConnectionEstablishedTime = System.currentTimeMillis();
        handleTimestamp(timestamp);
        if (this.firstConnection) {
            sendConnectStats();
        }
        restoreAuth();
        this.firstConnection = false;
        this.lastSessionId = sessionId;
        this.delegate.onConnect();
    }

    public void onCacheHost(String host) {
        this.cachedHost = host;
    }

    public void listen(List<String> path, Map<String, Object> queryParams, ListenHashProvider currentHashFn, Long tag, RequestResultCallback listener) {
        ListenQuerySpec query = new ListenQuerySpec(path, queryParams);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("Listening on " + query, new Object[0]);
        }
        ConnectionUtils.hardAssert(!this.listens.containsKey(query), "listen() called twice for same QuerySpec.", new Object[0]);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper2 = this.logger;
            logWrapper2.debug("Adding listen query: " + query, new Object[0]);
        }
        OutstandingListen outstandingListen = new OutstandingListen(listener, query, tag, currentHashFn);
        this.listens.put(query, outstandingListen);
        if (connected()) {
            sendListen(outstandingListen);
        }
        doIdleCheck();
    }

    public void initialize() {
        tryScheduleReconnect();
    }

    public void shutdown() {
        interrupt("shutdown");
    }

    public void put(List<String> path, Object data, RequestResultCallback onComplete) {
        putInternal("p", path, data, (String) null, onComplete);
    }

    public void compareAndPut(List<String> path, Object data, String hash, RequestResultCallback onComplete) {
        putInternal("p", path, data, hash, onComplete);
    }

    public void merge(List<String> path, Map<String, Object> data, RequestResultCallback onComplete) {
        putInternal("m", path, data, (String) null, onComplete);
    }

    public void purgeOutstandingWrites() {
        for (OutstandingPut put : this.outstandingPuts.values()) {
            if (put.onComplete != null) {
                put.onComplete.onRequestResult("write_canceled", (String) null);
            }
        }
        for (OutstandingDisconnect onDisconnect : this.onDisconnectRequestQueue) {
            if (onDisconnect.onComplete != null) {
                onDisconnect.onComplete.onRequestResult("write_canceled", (String) null);
            }
        }
        this.outstandingPuts.clear();
        this.onDisconnectRequestQueue.clear();
        if (!connected()) {
            this.hasOnDisconnects = false;
        }
        doIdleCheck();
    }

    public void onDataMessage(Map<String, Object> message) {
        if (message.containsKey(REQUEST_NUMBER)) {
            ConnectionRequestCallback responseListener = this.requestCBHash.remove(Long.valueOf((long) ((Integer) message.get(REQUEST_NUMBER)).intValue()));
            if (responseListener != null) {
                responseListener.onResponse((Map) message.get("b"));
            }
        } else if (!message.containsKey(REQUEST_ERROR)) {
            if (message.containsKey("a")) {
                onDataPush((String) message.get("a"), (Map) message.get("b"));
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                logWrapper.debug("Ignoring unknown message: " + message, new Object[0]);
            }
        }
    }

    public void onDisconnect(Connection.DisconnectReason reason) {
        boolean lastConnectionWasSuccessful;
        boolean z = false;
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("Got on disconnect due to " + reason.name(), new Object[0]);
        }
        this.connectionState = ConnectionState.Disconnected;
        this.realtime = null;
        this.hasOnDisconnects = false;
        this.requestCBHash.clear();
        cancelSentTransactions();
        if (shouldReconnect()) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.lastConnectionEstablishedTime;
            long timeSinceLastConnectSucceeded = currentTimeMillis - j;
            if (j > 0) {
                if (timeSinceLastConnectSucceeded > SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY) {
                    z = true;
                }
                lastConnectionWasSuccessful = z;
            } else {
                lastConnectionWasSuccessful = false;
            }
            if (reason == Connection.DisconnectReason.SERVER_RESET || lastConnectionWasSuccessful) {
                this.retryHelper.signalSuccess();
            }
            tryScheduleReconnect();
        }
        this.lastConnectionEstablishedTime = 0;
        this.delegate.onDisconnect();
    }

    public void onKill(String reason) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("Firebase Database connection was forcefully killed by the server. Will not attempt reconnect. Reason: " + reason, new Object[0]);
        }
        interrupt(SERVER_KILL_INTERRUPT_REASON);
    }

    public void unlisten(List<String> path, Map<String, Object> queryParams) {
        ListenQuerySpec query = new ListenQuerySpec(path, queryParams);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("unlistening on " + query, new Object[0]);
        }
        OutstandingListen listen = removeListen(query);
        if (listen != null && connected()) {
            sendUnlisten(listen);
        }
        doIdleCheck();
    }

    private boolean connected() {
        return this.connectionState == ConnectionState.Authenticating || this.connectionState == ConnectionState.Connected;
    }

    public void onDisconnectPut(List<String> path, Object data, RequestResultCallback onComplete) {
        this.hasOnDisconnects = true;
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_PUT, path, data, onComplete);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_PUT, path, data, onComplete));
        }
        doIdleCheck();
    }

    private boolean canSendWrites() {
        return this.connectionState == ConnectionState.Connected;
    }

    public void onDisconnectMerge(List<String> path, Map<String, Object> updates, RequestResultCallback onComplete) {
        this.hasOnDisconnects = true;
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_MERGE, path, updates, onComplete);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_MERGE, path, updates, onComplete));
        }
        doIdleCheck();
    }

    public void onDisconnectCancel(List<String> path, RequestResultCallback onComplete) {
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_CANCEL, path, (Object) null, onComplete);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_CANCEL, path, (Object) null, onComplete));
        }
        doIdleCheck();
    }

    public void interrupt(String reason) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("Connection interrupted for: " + reason, new Object[0]);
        }
        this.interruptReasons.add(reason);
        Connection connection = this.realtime;
        if (connection != null) {
            connection.close();
            this.realtime = null;
        } else {
            this.retryHelper.cancel();
            this.connectionState = ConnectionState.Disconnected;
        }
        this.retryHelper.signalSuccess();
    }

    public void resume(String reason) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("Connection no longer interrupted for: " + reason, new Object[0]);
        }
        this.interruptReasons.remove(reason);
        if (shouldReconnect() && this.connectionState == ConnectionState.Disconnected) {
            tryScheduleReconnect();
        }
    }

    public boolean isInterrupted(String reason) {
        return this.interruptReasons.contains(reason);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldReconnect() {
        return this.interruptReasons.size() == 0;
    }

    public void refreshAuthToken() {
        this.logger.debug("Auth token refresh requested", new Object[0]);
        interrupt(TOKEN_REFRESH_INTERRUPT_REASON);
        resume(TOKEN_REFRESH_INTERRUPT_REASON);
    }

    public void refreshAuthToken(String token) {
        this.logger.debug("Auth token refreshed.", new Object[0]);
        this.authToken = token;
        if (!connected()) {
            return;
        }
        if (token != null) {
            upgradeAuth();
        } else {
            sendUnauth();
        }
    }

    /* access modifiers changed from: private */
    public void tryScheduleReconnect() {
        if (shouldReconnect()) {
            ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Disconnected, "Not in disconnected state: %s", this.connectionState);
            final boolean forceRefresh = this.forceAuthTokenRefresh;
            this.logger.debug("Scheduling connection attempt", new Object[0]);
            this.forceAuthTokenRefresh = false;
            this.retryHelper.retry(new Runnable() {
                public void run() {
                    PersistentConnectionImpl.this.logger.debug("Trying to fetch auth token", new Object[0]);
                    ConnectionUtils.hardAssert(PersistentConnectionImpl.this.connectionState == ConnectionState.Disconnected, "Not in disconnected state: %s", PersistentConnectionImpl.this.connectionState);
                    ConnectionState unused = PersistentConnectionImpl.this.connectionState = ConnectionState.GettingToken;
                    PersistentConnectionImpl.access$608(PersistentConnectionImpl.this);
                    final long thisGetTokenAttempt = PersistentConnectionImpl.this.currentGetTokenAttempt;
                    PersistentConnectionImpl.this.authTokenProvider.getToken(forceRefresh, new ConnectionAuthTokenProvider.GetTokenCallback() {
                        public void onSuccess(String token) {
                            if (thisGetTokenAttempt != PersistentConnectionImpl.this.currentGetTokenAttempt) {
                                PersistentConnectionImpl.this.logger.debug("Ignoring getToken result, because this was not the latest attempt.", new Object[0]);
                            } else if (PersistentConnectionImpl.this.connectionState == ConnectionState.GettingToken) {
                                PersistentConnectionImpl.this.logger.debug("Successfully fetched token, opening connection", new Object[0]);
                                PersistentConnectionImpl.this.openNetworkConnection(token);
                            } else {
                                ConnectionUtils.hardAssert(PersistentConnectionImpl.this.connectionState == ConnectionState.Disconnected, "Expected connection state disconnected, but was %s", PersistentConnectionImpl.this.connectionState);
                                PersistentConnectionImpl.this.logger.debug("Not opening connection after token refresh, because connection was set to disconnected", new Object[0]);
                            }
                        }

                        public void onError(String error) {
                            if (thisGetTokenAttempt == PersistentConnectionImpl.this.currentGetTokenAttempt) {
                                ConnectionState unused = PersistentConnectionImpl.this.connectionState = ConnectionState.Disconnected;
                                LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                                access$400.debug("Error fetching token: " + error, new Object[0]);
                                PersistentConnectionImpl.this.tryScheduleReconnect();
                                return;
                            }
                            PersistentConnectionImpl.this.logger.debug("Ignoring getToken error, because this was not the latest attempt.", new Object[0]);
                        }
                    });
                }
            });
        }
    }

    public void openNetworkConnection(String token) {
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.GettingToken, "Trying to open network connection while in the wrong state: %s", this.connectionState);
        if (token == null) {
            this.delegate.onAuthStatus(false);
        }
        this.authToken = token;
        this.connectionState = ConnectionState.Connecting;
        Connection connection = new Connection(this.context, this.hostInfo, this.cachedHost, this, this.lastSessionId);
        this.realtime = connection;
        connection.open();
    }

    private void sendOnDisconnect(String action, List<String> path, Object data, final RequestResultCallback onComplete) {
        Map<String, Object> request = new HashMap<>();
        request.put("p", ConnectionUtils.pathToString(path));
        request.put("d", data);
        sendAction(action, request, new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> response) {
                String status = (String) response.get("s");
                String errorMessage = null;
                String errorCode = null;
                if (!status.equals("ok")) {
                    errorCode = status;
                    errorMessage = (String) response.get("d");
                }
                RequestResultCallback requestResultCallback = onComplete;
                if (requestResultCallback != null) {
                    requestResultCallback.onRequestResult(errorCode, errorMessage);
                }
            }
        });
    }

    private void cancelSentTransactions() {
        List<OutstandingPut> cancelledTransactionWrites = new ArrayList<>();
        Iterator<Map.Entry<Long, OutstandingPut>> iter = this.outstandingPuts.entrySet().iterator();
        while (iter.hasNext()) {
            OutstandingPut put = iter.next().getValue();
            if (put.getRequest().containsKey(REQUEST_DATA_HASH) && put.wasSent()) {
                cancelledTransactionWrites.add(put);
                iter.remove();
            }
        }
        for (OutstandingPut put2 : cancelledTransactionWrites) {
            put2.getOnComplete().onRequestResult("disconnected", (String) null);
        }
    }

    private void sendUnlisten(OutstandingListen listen) {
        Map<String, Object> request = new HashMap<>();
        request.put("p", ConnectionUtils.pathToString(listen.query.path));
        Long tag = listen.getTag();
        if (tag != null) {
            request.put("q", listen.getQuery().queryParams);
            request.put("t", tag);
        }
        sendAction(REQUEST_ACTION_QUERY_UNLISTEN, request, (ConnectionRequestCallback) null);
    }

    /* access modifiers changed from: private */
    public OutstandingListen removeListen(ListenQuerySpec query) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("removing query " + query, new Object[0]);
        }
        if (this.listens.containsKey(query)) {
            OutstandingListen oldListen = this.listens.get(query);
            this.listens.remove(query);
            doIdleCheck();
            return oldListen;
        } else if (!this.logger.logsDebug()) {
            return null;
        } else {
            LogWrapper logWrapper2 = this.logger;
            logWrapper2.debug("Trying to remove listener for QuerySpec " + query + " but no listener exists.", new Object[0]);
            return null;
        }
    }

    private Collection<OutstandingListen> removeListens(List<String> path) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("removing all listens at path " + path, new Object[0]);
        }
        List<OutstandingListen> removedListens = new ArrayList<>();
        for (Map.Entry<ListenQuerySpec, OutstandingListen> entry : this.listens.entrySet()) {
            OutstandingListen listen = entry.getValue();
            if (entry.getKey().path.equals(path)) {
                removedListens.add(listen);
            }
        }
        for (OutstandingListen toRemove : removedListens) {
            this.listens.remove(toRemove.getQuery());
        }
        doIdleCheck();
        return removedListens;
    }

    private void onDataPush(String action, Map<String, Object> body) {
        String str = action;
        Map<String, Object> map = body;
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            logWrapper.debug("handleServerMessage: " + str + " " + map, new Object[0]);
        }
        if (str.equals("d") || str.equals("m")) {
            boolean isMerge = str.equals("m");
            String pathString = (String) map.get("p");
            Object payloadData = map.get("d");
            Long tagNumber = ConnectionUtils.longFromObject(map.get("t"));
            if (!isMerge || !(payloadData instanceof Map) || ((Map) payloadData).size() != 0) {
                this.delegate.onDataUpdate(ConnectionUtils.stringToPath(pathString), payloadData, isMerge, tagNumber);
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper2 = this.logger;
                logWrapper2.debug("ignoring empty merge for path " + pathString, new Object[0]);
            }
        } else if (str.equals(SERVER_ASYNC_DATA_RANGE_MERGE)) {
            String pathString2 = (String) map.get("p");
            List<String> path = ConnectionUtils.stringToPath(pathString2);
            Object payloadData2 = map.get("d");
            Long tag = ConnectionUtils.longFromObject(map.get("t"));
            List<Map<String, Object>> ranges = (List) payloadData2;
            List<RangeMerge> rangeMerges = new ArrayList<>();
            Iterator<Map<String, Object>> it = ranges.iterator();
            while (it.hasNext()) {
                Map<String, Object> range = it.next();
                String startString = (String) range.get("s");
                String endString = (String) range.get(SERVER_DATA_END_PATH);
                List<String> end = null;
                List<String> start = startString != null ? ConnectionUtils.stringToPath(startString) : null;
                if (endString != null) {
                    end = ConnectionUtils.stringToPath(endString);
                }
                rangeMerges.add(new RangeMerge(start, end, range.get("m")));
                it = it;
                payloadData2 = payloadData2;
                ranges = ranges;
            }
            List<Map<String, Object>> list = ranges;
            if (!rangeMerges.isEmpty()) {
                this.delegate.onRangeMergeUpdate(path, rangeMerges, tag);
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper3 = this.logger;
                logWrapper3.debug("Ignoring empty range merge for path " + pathString2, new Object[0]);
            }
        } else if (str.equals("c")) {
            onListenRevoked(ConnectionUtils.stringToPath((String) map.get("p")));
        } else if (str.equals(SERVER_ASYNC_AUTH_REVOKED)) {
            onAuthRevoked((String) map.get("s"), (String) map.get("d"));
        } else if (str.equals(SERVER_ASYNC_SECURITY_DEBUG)) {
            onSecurityDebugPacket(map);
        } else if (this.logger.logsDebug()) {
            LogWrapper logWrapper4 = this.logger;
            logWrapper4.debug("Unrecognized action from server: " + str, new Object[0]);
        }
    }

    private void onListenRevoked(List<String> path) {
        Collection<OutstandingListen> listens2 = removeListens(path);
        if (listens2 != null) {
            for (OutstandingListen listen : listens2) {
                listen.resultCallback.onRequestResult("permission_denied", (String) null);
            }
        }
    }

    private void onAuthRevoked(String errorCode, String errorMessage) {
        LogWrapper logWrapper = this.logger;
        logWrapper.debug("Auth token revoked: " + errorCode + " (" + errorMessage + ")", new Object[0]);
        this.authToken = null;
        this.forceAuthTokenRefresh = true;
        this.delegate.onAuthStatus(false);
        this.realtime.close();
    }

    private void onSecurityDebugPacket(Map<String, Object> message) {
        this.logger.info((String) message.get(NotificationCompat.CATEGORY_MESSAGE));
    }

    private void upgradeAuth() {
        sendAuthHelper(false);
    }

    private void sendAuthAndRestoreState() {
        sendAuthHelper(true);
    }

    private void sendAuthHelper(final boolean restoreStateAfterComplete) {
        ConnectionUtils.hardAssert(connected(), "Must be connected to send auth, but was: %s", this.connectionState);
        ConnectionUtils.hardAssert(this.authToken != null, "Auth token must be set to authenticate!", new Object[0]);
        ConnectionRequestCallback onComplete = new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> response) {
                ConnectionState unused = PersistentConnectionImpl.this.connectionState = ConnectionState.Connected;
                String status = (String) response.get("s");
                if (status.equals("ok")) {
                    int unused2 = PersistentConnectionImpl.this.invalidAuthTokenCount = 0;
                    PersistentConnectionImpl.this.delegate.onAuthStatus(true);
                    if (restoreStateAfterComplete) {
                        PersistentConnectionImpl.this.restoreState();
                        return;
                    }
                    return;
                }
                String unused3 = PersistentConnectionImpl.this.authToken = null;
                boolean unused4 = PersistentConnectionImpl.this.forceAuthTokenRefresh = true;
                PersistentConnectionImpl.this.delegate.onAuthStatus(false);
                LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                access$400.debug("Authentication failed: " + status + " (" + ((String) response.get("d")) + ")", new Object[0]);
                PersistentConnectionImpl.this.realtime.close();
                if (status.equals("invalid_token")) {
                    PersistentConnectionImpl.access$1308(PersistentConnectionImpl.this);
                    if (((long) PersistentConnectionImpl.this.invalidAuthTokenCount) >= 3) {
                        PersistentConnectionImpl.this.retryHelper.setMaxDelay();
                        PersistentConnectionImpl.this.logger.warn("Provided authentication credentials are invalid. This usually indicates your FirebaseApp instance was not initialized correctly. Make sure your google-services.json file has the correct firebase_url and api_key. You can re-download google-services.json from https://console.firebase.google.com/.");
                    }
                }
            }
        };
        Map<String, Object> request = new HashMap<>();
        GAuthToken gAuthToken = GAuthToken.tryParseFromString(this.authToken);
        if (gAuthToken != null) {
            request.put(REQUEST_CREDENTIAL, gAuthToken.getToken());
            if (gAuthToken.getAuth() != null) {
                request.put(REQUEST_AUTHVAR, gAuthToken.getAuth());
            }
            sendSensitive(REQUEST_ACTION_GAUTH, true, request, onComplete);
            return;
        }
        request.put(REQUEST_CREDENTIAL, this.authToken);
        sendSensitive(REQUEST_ACTION_AUTH, true, request, onComplete);
    }

    private void sendUnauth() {
        ConnectionUtils.hardAssert(connected(), "Must be connected to send unauth.", new Object[0]);
        ConnectionUtils.hardAssert(this.authToken == null, "Auth token must not be set.", new Object[0]);
        sendAction(REQUEST_ACTION_UNAUTH, Collections.emptyMap(), (ConnectionRequestCallback) null);
    }

    private void restoreAuth() {
        if (this.logger.logsDebug()) {
            this.logger.debug("calling restore state", new Object[0]);
        }
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Connecting, "Wanted to restore auth, but was in wrong state: %s", this.connectionState);
        if (this.authToken == null) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Not restoring auth because token is null.", new Object[0]);
            }
            this.connectionState = ConnectionState.Connected;
            restoreState();
            return;
        }
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring auth.", new Object[0]);
        }
        this.connectionState = ConnectionState.Authenticating;
        sendAuthAndRestoreState();
    }

    /* access modifiers changed from: private */
    public void restoreState() {
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Connected, "Should be connected if we're restoring state, but we are: %s", this.connectionState);
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring outstanding listens", new Object[0]);
        }
        for (OutstandingListen listen : this.listens.values()) {
            if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                logWrapper.debug("Restoring listen " + listen.getQuery(), new Object[0]);
            }
            sendListen(listen);
        }
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring writes.", new Object[0]);
        }
        ArrayList<Long> outstanding = new ArrayList<>(this.outstandingPuts.keySet());
        Collections.sort(outstanding);
        Iterator<Long> it = outstanding.iterator();
        while (it.hasNext()) {
            sendPut(it.next().longValue());
        }
        for (OutstandingDisconnect disconnect : this.onDisconnectRequestQueue) {
            sendOnDisconnect(disconnect.getAction(), disconnect.getPath(), disconnect.getData(), disconnect.getOnComplete());
        }
        this.onDisconnectRequestQueue.clear();
    }

    private void handleTimestamp(long timestamp) {
        if (this.logger.logsDebug()) {
            this.logger.debug("handling timestamp", new Object[0]);
        }
        Map<String, Object> updates = new HashMap<>();
        updates.put(Constants.DOT_INFO_SERVERTIME_OFFSET, Long.valueOf(timestamp - System.currentTimeMillis()));
        this.delegate.onServerInfoUpdate(updates);
    }

    private Map<String, Object> getPutObject(List<String> path, Object data, String hash) {
        Map<String, Object> request = new HashMap<>();
        request.put("p", ConnectionUtils.pathToString(path));
        request.put("d", data);
        if (hash != null) {
            request.put(REQUEST_DATA_HASH, hash);
        }
        return request;
    }

    private void putInternal(String action, List<String> path, Object data, String hash, RequestResultCallback onComplete) {
        Map<String, Object> request = getPutObject(path, data, hash);
        long writeId = this.writeCounter;
        this.writeCounter = 1 + writeId;
        this.outstandingPuts.put(Long.valueOf(writeId), new OutstandingPut(action, request, onComplete));
        if (canSendWrites()) {
            sendPut(writeId);
        }
        this.lastWriteTimestamp = System.currentTimeMillis();
        doIdleCheck();
    }

    private void sendPut(long putId) {
        OutstandingPut put = this.outstandingPuts.get(Long.valueOf(putId));
        RequestResultCallback onComplete = put.getOnComplete();
        String action = put.getAction();
        put.markSent();
        final String str = action;
        final long j = putId;
        final OutstandingPut outstandingPut = put;
        final RequestResultCallback requestResultCallback = onComplete;
        sendAction(action, put.getRequest(), new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> response) {
                if (PersistentConnectionImpl.this.logger.logsDebug()) {
                    LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                    access$400.debug(str + " response: " + response, new Object[0]);
                }
                if (((OutstandingPut) PersistentConnectionImpl.this.outstandingPuts.get(Long.valueOf(j))) == outstandingPut) {
                    PersistentConnectionImpl.this.outstandingPuts.remove(Long.valueOf(j));
                    if (requestResultCallback != null) {
                        String status = (String) response.get("s");
                        if (status.equals("ok")) {
                            requestResultCallback.onRequestResult((String) null, (String) null);
                        } else {
                            requestResultCallback.onRequestResult(status, (String) response.get("d"));
                        }
                    }
                } else if (PersistentConnectionImpl.this.logger.logsDebug()) {
                    LogWrapper access$4002 = PersistentConnectionImpl.this.logger;
                    access$4002.debug("Ignoring on complete for put " + j + " because it was removed already.", new Object[0]);
                }
                PersistentConnectionImpl.this.doIdleCheck();
            }
        });
    }

    private void sendListen(final OutstandingListen listen) {
        Map<String, Object> request = new HashMap<>();
        request.put("p", ConnectionUtils.pathToString(listen.getQuery().path));
        Long tag = listen.getTag();
        if (tag != null) {
            request.put("q", listen.query.queryParams);
            request.put("t", tag);
        }
        ListenHashProvider hashFunction = listen.getHashFunction();
        request.put(REQUEST_DATA_HASH, hashFunction.getSimpleHash());
        if (hashFunction.shouldIncludeCompoundHash()) {
            CompoundHash compoundHash = hashFunction.getCompoundHash();
            List<String> posts = new ArrayList<>();
            for (List<String> path : compoundHash.getPosts()) {
                posts.add(ConnectionUtils.pathToString(path));
            }
            Map<String, Object> hash = new HashMap<>();
            hash.put(REQUEST_COMPOUND_HASH_HASHES, compoundHash.getHashes());
            hash.put(REQUEST_COMPOUND_HASH_PATHS, posts);
            request.put(REQUEST_COMPOUND_HASH, hash);
        }
        sendAction("q", request, new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> response) {
                String status = (String) response.get("s");
                if (status.equals("ok")) {
                    Map<String, Object> serverBody = (Map) response.get("d");
                    if (serverBody.containsKey(PersistentConnectionImpl.SERVER_DATA_WARNINGS)) {
                        PersistentConnectionImpl.this.warnOnListenerWarnings((List) serverBody.get(PersistentConnectionImpl.SERVER_DATA_WARNINGS), listen.query);
                    }
                }
                if (((OutstandingListen) PersistentConnectionImpl.this.listens.get(listen.getQuery())) != listen) {
                    return;
                }
                if (!status.equals("ok")) {
                    OutstandingListen unused = PersistentConnectionImpl.this.removeListen(listen.getQuery());
                    listen.resultCallback.onRequestResult(status, (String) response.get("d"));
                    return;
                }
                listen.resultCallback.onRequestResult((String) null, (String) null);
            }
        });
    }

    private void sendStats(Map<String, Integer> stats) {
        if (!stats.isEmpty()) {
            Map<String, Object> request = new HashMap<>();
            request.put("c", stats);
            sendAction("s", request, new ConnectionRequestCallback() {
                public void onResponse(Map<String, Object> response) {
                    String status = (String) response.get("s");
                    if (!status.equals("ok")) {
                        String errorMessage = (String) response.get("d");
                        if (PersistentConnectionImpl.this.logger.logsDebug()) {
                            LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                            access$400.debug("Failed to send stats: " + status + " (message: " + errorMessage + ")", new Object[0]);
                        }
                    }
                }
            });
        } else if (this.logger.logsDebug()) {
            this.logger.debug("Not sending stats because stats are empty", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void warnOnListenerWarnings(List<String> warnings, ListenQuerySpec query) {
        if (warnings.contains("no_index")) {
            LogWrapper logWrapper = this.logger;
            logWrapper.warn("Using an unspecified index. Your data will be downloaded and filtered on the client. Consider adding '" + ("\".indexOn\": \"" + query.queryParams.get("i") + '\"') + "' at " + ConnectionUtils.pathToString(query.path) + " to your security and Firebase Database rules for better performance");
        }
    }

    private void sendConnectStats() {
        Map<String, Integer> stats = new HashMap<>();
        if (this.context.isPersistenceEnabled()) {
            stats.put("persistence.android.enabled", 1);
        }
        stats.put("sdk.android." + this.context.getClientSdkVersion().replace('.', '-'), 1);
        if (this.logger.logsDebug()) {
            this.logger.debug("Sending first connection stats", new Object[0]);
        }
        sendStats(stats);
    }

    private void sendAction(String action, Map<String, Object> message, ConnectionRequestCallback onResponse) {
        sendSensitive(action, false, message, onResponse);
    }

    private void sendSensitive(String action, boolean isSensitive, Map<String, Object> message, ConnectionRequestCallback onResponse) {
        long rn = nextRequestNumber();
        Map<String, Object> request = new HashMap<>();
        request.put(REQUEST_NUMBER, Long.valueOf(rn));
        request.put("a", action);
        request.put("b", message);
        this.realtime.sendRequest(request, isSensitive);
        this.requestCBHash.put(Long.valueOf(rn), onResponse);
    }

    private long nextRequestNumber() {
        long j = this.requestCounter;
        this.requestCounter = 1 + j;
        return j;
    }

    /* access modifiers changed from: private */
    public void doIdleCheck() {
        if (isIdle()) {
            ScheduledFuture<?> scheduledFuture = this.inactivityTimer;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.inactivityTimer = this.executorService.schedule(new Runnable() {
                public void run() {
                    ScheduledFuture unused = PersistentConnectionImpl.this.inactivityTimer = null;
                    if (PersistentConnectionImpl.this.idleHasTimedOut()) {
                        PersistentConnectionImpl.this.interrupt(PersistentConnectionImpl.IDLE_INTERRUPT_REASON);
                    } else {
                        PersistentConnectionImpl.this.doIdleCheck();
                    }
                }
            }, 60000, TimeUnit.MILLISECONDS);
        } else if (isInterrupted(IDLE_INTERRUPT_REASON)) {
            ConnectionUtils.hardAssert(!isIdle());
            resume(IDLE_INTERRUPT_REASON);
        }
    }

    private boolean isIdle() {
        return this.listens.isEmpty() && this.requestCBHash.isEmpty() && !this.hasOnDisconnects && this.outstandingPuts.isEmpty();
    }

    /* access modifiers changed from: private */
    public boolean idleHasTimedOut() {
        return isIdle() && System.currentTimeMillis() > this.lastWriteTimestamp + 60000;
    }

    public void injectConnectionFailure() {
        Connection connection = this.realtime;
        if (connection != null) {
            connection.injectConnectionFailure();
        }
    }
}
