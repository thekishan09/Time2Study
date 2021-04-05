package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.NetworkInfo;
import android.os.Build;
import com.squareup.picasso.NetworkRequestHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final RequestHandler ERRORING_HANDLER = new RequestHandler() {
        public boolean canHandleRequest(Request data) {
            return true;
        }

        public RequestHandler.Result load(Request request, int networkPolicy) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: " + request);
        }
    };
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifOrientation;
    Future<?> future;
    final String key;
    Picasso.LoadedFrom loadedFrom;
    final int memoryPolicy;
    int networkPolicy;
    final Picasso picasso;
    Picasso.Priority priority;
    final RequestHandler requestHandler;
    Bitmap result;
    int retryCount;
    final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
    final Stats stats;

    BitmapHunter(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2, RequestHandler requestHandler2) {
        this.picasso = picasso2;
        this.dispatcher = dispatcher2;
        this.cache = cache2;
        this.stats = stats2;
        this.action = action2;
        this.key = action2.getKey();
        this.data = action2.getRequest();
        this.priority = action2.getPriority();
        this.memoryPolicy = action2.getMemoryPolicy();
        this.networkPolicy = action2.getNetworkPolicy();
        this.requestHandler = requestHandler2;
        this.retryCount = requestHandler2.getRetryCount();
    }

    static Bitmap decodeStream(Source source, Request request) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(source);
        boolean isWebPFile = Utils.isWebPFile(bufferedSource);
        boolean isPurgeable = request.purgeable && Build.VERSION.SDK_INT < 21;
        BitmapFactory.Options options = RequestHandler.createBitmapOptions(request);
        boolean calculateSize = RequestHandler.requiresInSampleSize(options);
        if (isWebPFile || isPurgeable) {
            byte[] bytes = bufferedSource.readByteArray();
            if (calculateSize) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
        InputStream stream = bufferedSource.inputStream();
        if (calculateSize) {
            MarkableInputStream markStream = new MarkableInputStream(stream);
            stream = markStream;
            markStream.allowMarksToExpire(false);
            long mark = markStream.savePosition(1024);
            BitmapFactory.decodeStream(stream, (Rect) null, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            markStream.reset(mark);
            markStream.allowMarksToExpire(true);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream, (Rect) null, options);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IOException("Failed to decode stream.");
    }

    public void run() {
        try {
            updateThreadName(this.data);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
            }
            Bitmap hunt = hunt();
            this.result = hunt;
            if (hunt == null) {
                this.dispatcher.dispatchFailed(this);
            } else {
                this.dispatcher.dispatchComplete(this);
            }
        } catch (NetworkRequestHandler.ResponseException e) {
            if (!NetworkPolicy.isOfflineOnly(e.networkPolicy) || e.code != 504) {
                this.exception = e;
            }
            this.dispatcher.dispatchFailed(this);
        } catch (IOException e2) {
            this.exception = e2;
            this.dispatcher.dispatchRetry(this);
        } catch (OutOfMemoryError e3) {
            StringWriter writer = new StringWriter();
            this.stats.createSnapshot().dump(new PrintWriter(writer));
            this.exception = new RuntimeException(writer.toString(), e3);
            this.dispatcher.dispatchFailed(this);
        } catch (Exception e4) {
            this.exception = e4;
            this.dispatcher.dispatchFailed(this);
        } catch (Throwable th) {
            Thread.currentThread().setName("Picasso-Idle");
            throw th;
        }
        Thread.currentThread().setName("Picasso-Idle");
    }

    /* access modifiers changed from: package-private */
    public Bitmap hunt() throws IOException {
        Bitmap bitmap = null;
        if (!MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy) || (bitmap = this.cache.get(this.key)) == null) {
            int i = this.retryCount == 0 ? NetworkPolicy.OFFLINE.index : this.networkPolicy;
            this.networkPolicy = i;
            RequestHandler.Result result2 = this.requestHandler.load(this.data, i);
            if (result2 != null) {
                this.loadedFrom = result2.getLoadedFrom();
                this.exifOrientation = result2.getExifOrientation();
                bitmap = result2.getBitmap();
                if (bitmap == null) {
                    Source source = result2.getSource();
                    try {
                        bitmap = decodeStream(source, this.data);
                    } finally {
                        try {
                            source.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            if (bitmap != null) {
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId());
                }
                this.stats.dispatchBitmapDecoded(bitmap);
                if (this.data.needsTransformation() || this.exifOrientation != 0) {
                    synchronized (DECODE_LOCK) {
                        if (this.data.needsMatrixTransform() || this.exifOrientation != 0) {
                            bitmap = transformResult(this.data, bitmap, this.exifOrientation);
                            if (this.picasso.loggingEnabled) {
                                Utils.log("Hunter", "transformed", this.data.logId());
                            }
                        }
                        if (this.data.hasCustomTransformations()) {
                            bitmap = applyCustomTransformations(this.data.transformations, bitmap);
                            if (this.picasso.loggingEnabled) {
                                Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                            }
                        }
                    }
                    if (bitmap != null) {
                        this.stats.dispatchBitmapTransformed(bitmap);
                    }
                }
            }
            return bitmap;
        }
        this.stats.dispatchCacheHit();
        this.loadedFrom = Picasso.LoadedFrom.MEMORY;
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
        }
        return bitmap;
    }

    /* access modifiers changed from: package-private */
    public void attach(Action action2) {
        boolean loggingEnabled = this.picasso.loggingEnabled;
        Request request = action2.request;
        if (this.action == null) {
            this.action = action2;
            if (loggingEnabled) {
                List<Action> list = this.actions;
                if (list == null || list.isEmpty()) {
                    Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                } else {
                    Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                }
            }
        } else {
            if (this.actions == null) {
                this.actions = new ArrayList(3);
            }
            this.actions.add(action2);
            if (loggingEnabled) {
                Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
            }
            Picasso.Priority actionPriority = action2.getPriority();
            if (actionPriority.ordinal() > this.priority.ordinal()) {
                this.priority = actionPriority;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void detach(Action action2) {
        boolean detached = false;
        if (this.action == action2) {
            this.action = null;
            detached = true;
        } else {
            List<Action> list = this.actions;
            if (list != null) {
                detached = list.remove(action2);
            }
        }
        if (detached && action2.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action2.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    private Picasso.Priority computeNewPriority() {
        Picasso.Priority newPriority = Picasso.Priority.LOW;
        List<Action> list = this.actions;
        boolean hasAny = false;
        boolean hasMultiple = list != null && !list.isEmpty();
        if (this.action != null || hasMultiple) {
            hasAny = true;
        }
        if (!hasAny) {
            return newPriority;
        }
        Action action2 = this.action;
        if (action2 != null) {
            newPriority = action2.getPriority();
        }
        if (hasMultiple) {
            int n = this.actions.size();
            for (int i = 0; i < n; i++) {
                Picasso.Priority actionPriority = this.actions.get(i).getPriority();
                if (actionPriority.ordinal() > newPriority.ordinal()) {
                    newPriority = actionPriority;
                }
            }
        }
        return newPriority;
    }

    /* access modifiers changed from: package-private */
    public boolean cancel() {
        Future<?> future2;
        if (this.action != null) {
            return false;
        }
        List<Action> list = this.actions;
        if ((list == null || list.isEmpty()) && (future2 = this.future) != null && future2.cancel(false)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isCancelled() {
        Future<?> future2 = this.future;
        return future2 != null && future2.isCancelled();
    }

    /* access modifiers changed from: package-private */
    public boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        if (!(this.retryCount > 0)) {
            return false;
        }
        this.retryCount--;
        return this.requestHandler.shouldRetry(airplaneMode, info);
    }

    /* access modifiers changed from: package-private */
    public boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }

    /* access modifiers changed from: package-private */
    public Bitmap getResult() {
        return this.result;
    }

    /* access modifiers changed from: package-private */
    public String getKey() {
        return this.key;
    }

    /* access modifiers changed from: package-private */
    public int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    /* access modifiers changed from: package-private */
    public Request getData() {
        return this.data;
    }

    /* access modifiers changed from: package-private */
    public Action getAction() {
        return this.action;
    }

    /* access modifiers changed from: package-private */
    public Picasso getPicasso() {
        return this.picasso;
    }

    /* access modifiers changed from: package-private */
    public List<Action> getActions() {
        return this.actions;
    }

    /* access modifiers changed from: package-private */
    public Exception getException() {
        return this.exception;
    }

    /* access modifiers changed from: package-private */
    public Picasso.LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    /* access modifiers changed from: package-private */
    public Picasso.Priority getPriority() {
        return this.priority;
    }

    static void updateThreadName(Request data2) {
        String name = data2.getName();
        StringBuilder builder = NAME_BUILDER.get();
        builder.ensureCapacity("Picasso-".length() + name.length());
        builder.replace("Picasso-".length(), builder.length(), name);
        Thread.currentThread().setName(builder.toString());
    }

    static BitmapHunter forRequest(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2) {
        Request request = action2.getRequest();
        List<RequestHandler> requestHandlers = picasso2.getRequestHandlers();
        int count = requestHandlers.size();
        for (int i = 0; i < count; i++) {
            RequestHandler requestHandler2 = requestHandlers.get(i);
            if (requestHandler2.canHandleRequest(request)) {
                return new BitmapHunter(picasso2, dispatcher2, cache2, stats2, action2, requestHandler2);
            }
        }
        return new BitmapHunter(picasso2, dispatcher2, cache2, stats2, action2, ERRORING_HANDLER);
    }

    static Bitmap applyCustomTransformations(List<Transformation> transformations, Bitmap result2) {
        int i = 0;
        int count = transformations.size();
        while (i < count) {
            final Transformation transformation = transformations.get(i);
            try {
                Bitmap newResult = transformation.transform(result2);
                if (newResult == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Transformation ");
                    sb.append(transformation.key());
                    sb.append(" returned null after ");
                    sb.append(i);
                    final StringBuilder builder = sb.append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation t : transformations) {
                        builder.append(t.key());
                        builder.append(10);
                    }
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new NullPointerException(builder.toString());
                        }
                    });
                    return null;
                } else if (newResult == result2 && result2.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new IllegalStateException("Transformation " + transformation.key() + " returned input Bitmap but recycled it.");
                        }
                    });
                    return null;
                } else if (newResult == result2 || result2.isRecycled()) {
                    result2 = newResult;
                    i++;
                } else {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new IllegalStateException("Transformation " + transformation.key() + " mutated input Bitmap but failed to recycle the original.");
                        }
                    });
                    return null;
                }
            } catch (RuntimeException e) {
                Picasso.HANDLER.post(new Runnable() {
                    public void run() {
                        throw new RuntimeException("Transformation " + transformation.key() + " crashed with exception.", e);
                    }
                });
                return null;
            }
        }
        return result2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:90:0x0330  */
    /* JADX WARNING: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.graphics.Bitmap transformResult(com.squareup.picasso.Request r47, android.graphics.Bitmap r48, int r49) {
        /*
            r0 = r47
            int r1 = r48.getWidth()
            int r2 = r48.getHeight()
            boolean r3 = r0.onlyScaleDown
            r4 = 0
            r5 = 0
            r6 = r1
            r7 = r2
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r8.<init>()
            boolean r9 = r47.needsMatrixTransform()
            if (r9 != 0) goto L_0x002c
            if (r49 == 0) goto L_0x001e
            goto L_0x002c
        L_0x001e:
            r16 = r4
            r17 = r5
            r20 = r6
            r21 = r7
            r4 = r8
            r8 = r3
            r3 = r2
            r2 = r1
            goto L_0x031b
        L_0x002c:
            int r9 = r0.targetWidth
            int r10 = r0.targetHeight
            float r11 = r0.rotationDegrees
            r12 = 0
            int r12 = (r11 > r12 ? 1 : (r11 == r12 ? 0 : -1))
            if (r12 == 0) goto L_0x01f6
            double r12 = (double) r11
            double r12 = java.lang.Math.toRadians(r12)
            double r12 = java.lang.Math.cos(r12)
            double r14 = (double) r11
            double r14 = java.lang.Math.toRadians(r14)
            double r14 = java.lang.Math.sin(r14)
            r16 = r4
            boolean r4 = r0.hasRotationPivot
            if (r4 == 0) goto L_0x0139
            float r4 = r0.rotationPivotX
            r17 = r5
            float r5 = r0.rotationPivotY
            r8.setRotate(r11, r4, r5)
            float r4 = r0.rotationPivotX
            double r4 = (double) r4
            r18 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r20 = r18 - r12
            java.lang.Double.isNaN(r4)
            double r4 = r4 * r20
            r20 = r6
            float r6 = r0.rotationPivotY
            r21 = r7
            double r6 = (double) r6
            java.lang.Double.isNaN(r6)
            double r6 = r6 * r14
            double r4 = r4 + r6
            float r6 = r0.rotationPivotY
            double r6 = (double) r6
            double r18 = r18 - r12
            java.lang.Double.isNaN(r6)
            double r6 = r6 * r18
            r18 = r9
            float r9 = r0.rotationPivotX
            r19 = r10
            double r9 = (double) r9
            java.lang.Double.isNaN(r9)
            double r9 = r9 * r14
            double r6 = r6 - r9
            int r9 = r0.targetWidth
            double r9 = (double) r9
            java.lang.Double.isNaN(r9)
            double r9 = r9 * r12
            double r9 = r9 + r4
            r22 = r3
            int r3 = r0.targetWidth
            r23 = r2
            double r2 = (double) r3
            java.lang.Double.isNaN(r2)
            double r2 = r2 * r14
            double r2 = r2 + r6
            r24 = r1
            int r1 = r0.targetWidth
            r25 = r2
            double r1 = (double) r1
            java.lang.Double.isNaN(r1)
            double r1 = r1 * r12
            double r1 = r1 + r4
            int r3 = r0.targetHeight
            r27 = r8
            r28 = r9
            double r8 = (double) r3
            java.lang.Double.isNaN(r8)
            double r8 = r8 * r14
            double r1 = r1 - r8
            int r3 = r0.targetWidth
            double r8 = (double) r3
            java.lang.Double.isNaN(r8)
            double r8 = r8 * r14
            double r8 = r8 + r6
            int r3 = r0.targetHeight
            r30 = r11
            double r10 = (double) r3
            java.lang.Double.isNaN(r10)
            double r10 = r10 * r12
            double r8 = r8 + r10
            int r3 = r0.targetHeight
            double r10 = (double) r3
            java.lang.Double.isNaN(r10)
            double r10 = r10 * r14
            double r10 = r4 - r10
            int r3 = r0.targetHeight
            r31 = r14
            double r14 = (double) r3
            java.lang.Double.isNaN(r14)
            double r14 = r14 * r12
            double r14 = r14 + r6
            r33 = r12
            r12 = r28
            r28 = r14
            double r14 = java.lang.Math.max(r4, r12)
            double r14 = java.lang.Math.max(r1, r14)
            double r14 = java.lang.Math.max(r10, r14)
            r35 = r14
            double r14 = java.lang.Math.min(r4, r12)
            double r14 = java.lang.Math.min(r1, r14)
            double r14 = java.lang.Math.min(r10, r14)
            r37 = r1
            r1 = r25
            r25 = r4
            double r3 = java.lang.Math.max(r6, r1)
            double r3 = java.lang.Math.max(r8, r3)
            r39 = r10
            r10 = r28
            double r3 = java.lang.Math.max(r10, r3)
            r28 = r12
            double r12 = java.lang.Math.min(r6, r1)
            double r12 = java.lang.Math.min(r8, r12)
            double r12 = java.lang.Math.min(r10, r12)
            double r41 = r35 - r14
            r43 = r1
            double r1 = java.lang.Math.floor(r41)
            int r1 = (int) r1
            double r41 = r3 - r12
            r5 = r1
            double r1 = java.lang.Math.floor(r41)
            int r10 = (int) r1
            r9 = r5
            goto L_0x020c
        L_0x0139:
            r24 = r1
            r23 = r2
            r22 = r3
            r17 = r5
            r20 = r6
            r21 = r7
            r27 = r8
            r18 = r9
            r19 = r10
            r30 = r11
            r33 = r12
            r31 = r14
            r1 = r27
            r2 = r30
            r1.setRotate(r2)
            r3 = 0
            r5 = 0
            int r7 = r0.targetWidth
            double r7 = (double) r7
            java.lang.Double.isNaN(r7)
            double r7 = r7 * r33
            int r9 = r0.targetWidth
            double r9 = (double) r9
            java.lang.Double.isNaN(r9)
            double r9 = r9 * r31
            int r11 = r0.targetWidth
            double r11 = (double) r11
            java.lang.Double.isNaN(r11)
            double r11 = r11 * r33
            int r13 = r0.targetHeight
            double r13 = (double) r13
            java.lang.Double.isNaN(r13)
            double r13 = r13 * r31
            double r11 = r11 - r13
            int r13 = r0.targetWidth
            double r13 = (double) r13
            java.lang.Double.isNaN(r13)
            double r13 = r13 * r31
            int r15 = r0.targetHeight
            double r1 = (double) r15
            java.lang.Double.isNaN(r1)
            double r1 = r1 * r33
            double r13 = r13 + r1
            int r1 = r0.targetHeight
            double r1 = (double) r1
            java.lang.Double.isNaN(r1)
            double r1 = r1 * r31
            double r1 = -r1
            int r15 = r0.targetHeight
            r25 = r13
            double r13 = (double) r15
            java.lang.Double.isNaN(r13)
            double r13 = r13 * r33
            r28 = r13
            double r13 = java.lang.Math.max(r3, r7)
            double r13 = java.lang.Math.max(r11, r13)
            double r13 = java.lang.Math.max(r1, r13)
            r35 = r13
            double r13 = java.lang.Math.min(r3, r7)
            double r13 = java.lang.Math.min(r11, r13)
            double r13 = java.lang.Math.min(r1, r13)
            r37 = r1
            double r1 = java.lang.Math.max(r5, r9)
            r39 = r3
            r3 = r25
            double r1 = java.lang.Math.max(r3, r1)
            r25 = r7
            r7 = r28
            double r1 = java.lang.Math.max(r7, r1)
            r28 = r11
            double r11 = java.lang.Math.min(r5, r9)
            double r11 = java.lang.Math.min(r3, r11)
            double r11 = java.lang.Math.min(r7, r11)
            double r41 = r35 - r13
            r43 = r3
            double r3 = java.lang.Math.floor(r41)
            int r3 = (int) r3
            double r41 = r1 - r11
            r45 = r1
            double r1 = java.lang.Math.floor(r41)
            int r1 = (int) r1
            r10 = r1
            r9 = r3
            goto L_0x020c
        L_0x01f6:
            r24 = r1
            r23 = r2
            r22 = r3
            r16 = r4
            r17 = r5
            r20 = r6
            r21 = r7
            r27 = r8
            r18 = r9
            r19 = r10
            r30 = r11
        L_0x020c:
            if (r49 == 0) goto L_0x0238
            int r1 = getExifRotation(r49)
            int r2 = getExifTranslation(r49)
            if (r1 == 0) goto L_0x022c
            float r3 = (float) r1
            r4 = r27
            r4.preRotate(r3)
            r3 = 90
            if (r1 == r3) goto L_0x0226
            r3 = 270(0x10e, float:3.78E-43)
            if (r1 != r3) goto L_0x022e
        L_0x0226:
            r3 = r10
            r5 = r9
            r6 = r3
            r10 = r5
            r9 = r6
            goto L_0x022e
        L_0x022c:
            r4 = r27
        L_0x022e:
            r3 = 1
            if (r2 == r3) goto L_0x023a
            float r3 = (float) r2
            r5 = 1065353216(0x3f800000, float:1.0)
            r4.postScale(r3, r5)
            goto L_0x023a
        L_0x0238:
            r4 = r27
        L_0x023a:
            boolean r1 = r0.centerCrop
            if (r1 == 0) goto L_0x02cd
            if (r9 == 0) goto L_0x0248
            float r1 = (float) r9
            r2 = r24
            float r3 = (float) r2
            float r1 = r1 / r3
            r3 = r23
            goto L_0x024f
        L_0x0248:
            r2 = r24
            float r1 = (float) r10
            r3 = r23
            float r5 = (float) r3
            float r1 = r1 / r5
        L_0x024f:
            if (r10 == 0) goto L_0x0254
            float r5 = (float) r10
            float r6 = (float) r3
            goto L_0x0256
        L_0x0254:
            float r5 = (float) r9
            float r6 = (float) r2
        L_0x0256:
            float r5 = r5 / r6
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r6 <= 0) goto L_0x0288
            float r6 = (float) r3
            float r7 = r5 / r1
            float r6 = r6 * r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            int r7 = r0.centerCropGravity
            r8 = 48
            r7 = r7 & r8
            if (r7 != r8) goto L_0x026f
            r7 = 0
            goto L_0x027d
        L_0x026f:
            int r7 = r0.centerCropGravity
            r8 = 80
            r7 = r7 & r8
            if (r7 != r8) goto L_0x0279
            int r7 = r3 - r6
            goto L_0x027d
        L_0x0279:
            int r7 = r3 - r6
            int r7 = r7 / 2
        L_0x027d:
            r8 = r6
            r11 = r1
            float r12 = (float) r10
            float r13 = (float) r8
            float r12 = r12 / r13
            r17 = r7
            r7 = r8
            r6 = r20
            goto L_0x02bf
        L_0x0288:
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r6 >= 0) goto L_0x02b6
            float r6 = (float) r2
            float r7 = r1 / r5
            float r6 = r6 * r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            int r7 = r0.centerCropGravity
            r8 = 3
            r7 = r7 & r8
            if (r7 != r8) goto L_0x029f
            r7 = 0
            goto L_0x02ac
        L_0x029f:
            int r7 = r0.centerCropGravity
            r8 = 5
            r7 = r7 & r8
            if (r7 != r8) goto L_0x02a8
            int r7 = r2 - r6
            goto L_0x02ac
        L_0x02a8:
            int r7 = r2 - r6
            int r7 = r7 / 2
        L_0x02ac:
            r8 = r6
            float r11 = (float) r9
            float r12 = (float) r8
            float r11 = r11 / r12
            r12 = r5
            r16 = r7
            r7 = r21
            goto L_0x02bf
        L_0x02b6:
            r6 = 0
            r7 = r2
            r12 = r5
            r11 = r5
            r16 = r6
            r6 = r7
            r7 = r21
        L_0x02bf:
            r8 = r22
            boolean r13 = shouldResize(r8, r2, r3, r9, r10)
            if (r13 == 0) goto L_0x02ca
            r4.preScale(r11, r12)
        L_0x02ca:
            r5 = r17
            goto L_0x0321
        L_0x02cd:
            r8 = r22
            r3 = r23
            r2 = r24
            boolean r1 = r0.centerInside
            if (r1 == 0) goto L_0x02f8
            if (r9 == 0) goto L_0x02dc
            float r1 = (float) r9
            float r5 = (float) r2
            goto L_0x02de
        L_0x02dc:
            float r1 = (float) r10
            float r5 = (float) r3
        L_0x02de:
            float r1 = r1 / r5
            if (r10 == 0) goto L_0x02e4
            float r5 = (float) r10
            float r6 = (float) r3
            goto L_0x02e6
        L_0x02e4:
            float r5 = (float) r9
            float r6 = (float) r2
        L_0x02e6:
            float r5 = r5 / r6
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r6 >= 0) goto L_0x02ed
            r6 = r1
            goto L_0x02ee
        L_0x02ed:
            r6 = r5
        L_0x02ee:
            boolean r7 = shouldResize(r8, r2, r3, r9, r10)
            if (r7 == 0) goto L_0x02fd
            r4.preScale(r6, r6)
            goto L_0x02fd
        L_0x02f8:
            if (r9 != 0) goto L_0x02fe
            if (r10 == 0) goto L_0x02fd
            goto L_0x02fe
        L_0x02fd:
            goto L_0x031b
        L_0x02fe:
            if (r9 != r2) goto L_0x0302
            if (r10 == r3) goto L_0x02fd
        L_0x0302:
            if (r9 == 0) goto L_0x0307
            float r1 = (float) r9
            float r5 = (float) r2
            goto L_0x0309
        L_0x0307:
            float r1 = (float) r10
            float r5 = (float) r3
        L_0x0309:
            float r1 = r1 / r5
            if (r10 == 0) goto L_0x030f
            float r5 = (float) r10
            float r6 = (float) r3
            goto L_0x0311
        L_0x030f:
            float r5 = (float) r9
            float r6 = (float) r2
        L_0x0311:
            float r5 = r5 / r6
            boolean r6 = shouldResize(r8, r2, r3, r9, r10)
            if (r6 == 0) goto L_0x031b
            r4.preScale(r1, r5)
        L_0x031b:
            r5 = r17
            r6 = r20
            r7 = r21
        L_0x0321:
            r15 = 1
            r9 = r48
            r10 = r16
            r11 = r5
            r12 = r6
            r13 = r7
            r14 = r4
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r9, r10, r11, r12, r13, r14, r15)
            if (r1 == r9) goto L_0x0334
            r48.recycle()
            r9 = r1
        L_0x0334:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.BitmapHunter.transformResult(com.squareup.picasso.Request, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }

    private static boolean shouldResize(boolean onlyScaleDown, int inWidth, int inHeight, int targetWidth, int targetHeight) {
        return !onlyScaleDown || (targetWidth != 0 && inWidth > targetWidth) || (targetHeight != 0 && inHeight > targetHeight);
    }

    static int getExifRotation(int orientation) {
        switch (orientation) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    static int getExifTranslation(int orientation) {
        if (orientation == 2 || orientation == 7 || orientation == 4 || orientation == 5) {
            return -1;
        }
        return 1;
    }
}
