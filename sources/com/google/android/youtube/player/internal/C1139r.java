package com.google.android.youtube.player.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.internal.C1101c;
import com.google.android.youtube.player.internal.C1119i;
import com.google.android.youtube.player.internal.C1151t;
import java.util.ArrayList;

/* renamed from: com.google.android.youtube.player.internal.r */
public abstract class C1139r<T extends IInterface> implements C1151t {

    /* renamed from: a */
    final Handler f227a;

    /* renamed from: b */
    private final Context f228b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public T f229c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public ArrayList<C1151t.C1152a> f230d;

    /* renamed from: e */
    private final ArrayList<C1151t.C1152a> f231e = new ArrayList<>();

    /* renamed from: f */
    private boolean f232f = false;

    /* renamed from: g */
    private ArrayList<C1151t.C1153b> f233g;

    /* renamed from: h */
    private boolean f234h = false;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public final ArrayList<C1142b<?>> f235i = new ArrayList<>();

    /* renamed from: j */
    private ServiceConnection f236j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public boolean f237k = false;

    /* renamed from: com.google.android.youtube.player.internal.r$1 */
    static /* synthetic */ class C11401 {

        /* renamed from: a */
        static final /* synthetic */ int[] f238a;

        static {
            int[] iArr = new int[YouTubeInitializationResult.values().length];
            f238a = iArr;
            try {
                iArr[YouTubeInitializationResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$a */
    final class C1141a extends Handler {
        C1141a() {
        }

        public final void handleMessage(Message message) {
            if (message.what == 3) {
                C1139r.this.mo16665a((YouTubeInitializationResult) message.obj);
            } else if (message.what == 4) {
                synchronized (C1139r.this.f230d) {
                    if (C1139r.this.f237k && C1139r.this.mo16668f() && C1139r.this.f230d.contains(message.obj)) {
                        ((C1151t.C1152a) message.obj).mo16519a();
                    }
                }
            } else if (message.what == 2 && !C1139r.this.mo16668f()) {
            } else {
                if (message.what == 2 || message.what == 1) {
                    ((C1142b) message.obj).mo16674a();
                }
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$b */
    protected abstract class C1142b<TListener> {

        /* renamed from: b */
        private TListener f241b;

        public C1142b(TListener tlistener) {
            this.f241b = tlistener;
            synchronized (C1139r.this.f235i) {
                C1139r.this.f235i.add(this);
            }
        }

        /* renamed from: a */
        public final void mo16674a() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.f241b;
            }
            mo16675a(tlistener);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public abstract void mo16675a(TListener tlistener);

        /* renamed from: b */
        public final void mo16676b() {
            synchronized (this) {
                this.f241b = null;
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$c */
    protected final class C1143c extends C1142b<Boolean> {

        /* renamed from: b */
        public final YouTubeInitializationResult f242b;

        /* renamed from: c */
        public final IBinder f243c;

        public C1143c(String str, IBinder iBinder) {
            super(true);
            this.f242b = C1139r.m309b(str);
            this.f243c = iBinder;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public final /* synthetic */ void mo16675a(Object obj) {
            if (((Boolean) obj) == null) {
                return;
            }
            if (C11401.f238a[this.f242b.ordinal()] != 1) {
                C1139r.this.mo16665a(this.f242b);
                return;
            }
            try {
                if (C1139r.this.mo16660b().equals(this.f243c.getInterfaceDescriptor())) {
                    IInterface unused = C1139r.this.f229c = C1139r.this.mo16658a(this.f243c);
                    if (C1139r.this.f229c != null) {
                        C1139r.this.mo16669g();
                        return;
                    }
                }
            } catch (RemoteException e) {
            }
            C1139r.this.mo16554a();
            C1139r.this.mo16665a(YouTubeInitializationResult.INTERNAL_ERROR);
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$d */
    protected final class C1144d extends C1101c.C1102a {
        protected C1144d() {
        }

        /* renamed from: a */
        public final void mo16557a(String str, IBinder iBinder) {
            C1139r.this.f227a.sendMessage(C1139r.this.f227a.obtainMessage(1, new C1143c(str, iBinder)));
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$e */
    final class C1145e implements ServiceConnection {
        C1145e() {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            C1139r.this.mo16666b(iBinder);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            IInterface unused = C1139r.this.f229c = null;
            C1139r.this.mo16670h();
        }
    }

    protected C1139r(Context context, C1151t.C1152a aVar, C1151t.C1153b bVar) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            this.f228b = (Context) C1098ab.m123a(context);
            ArrayList<C1151t.C1152a> arrayList = new ArrayList<>();
            this.f230d = arrayList;
            arrayList.add(C1098ab.m123a(aVar));
            ArrayList<C1151t.C1153b> arrayList2 = new ArrayList<>();
            this.f233g = arrayList2;
            arrayList2.add(C1098ab.m123a(bVar));
            this.f227a = new C1141a();
            return;
        }
        throw new IllegalStateException("Clients must be created on the UI thread.");
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void mo16554a() {
        ServiceConnection serviceConnection = this.f236j;
        if (serviceConnection != null) {
            try {
                this.f228b.unbindService(serviceConnection);
            } catch (IllegalArgumentException e) {
                Log.w("YouTubeClient", "Unexpected error from unbindService()", e);
            }
        }
        this.f229c = null;
        this.f236j = null;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static YouTubeInitializationResult m309b(String str) {
        try {
            return YouTubeInitializationResult.valueOf(str);
        } catch (IllegalArgumentException e) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        } catch (NullPointerException e2) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract T mo16658a(IBinder iBinder);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo16665a(YouTubeInitializationResult youTubeInitializationResult) {
        this.f227a.removeMessages(4);
        synchronized (this.f233g) {
            this.f234h = true;
            ArrayList<C1151t.C1153b> arrayList = this.f233g;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                if (this.f237k) {
                    if (this.f233g.contains(arrayList.get(i))) {
                        arrayList.get(i).mo16521a(youTubeInitializationResult);
                    }
                    i++;
                } else {
                    return;
                }
            }
            this.f234h = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16659a(C1119i iVar, C1144d dVar) throws RemoteException;

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract String mo16660b();

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16666b(IBinder iBinder) {
        try {
            mo16659a(C1119i.C1120a.m253a(iBinder), new C1144d());
        } catch (RemoteException e) {
            Log.w("YouTubeClient", "service died");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public abstract String mo16661c();

    /* renamed from: d */
    public void mo16662d() {
        mo16670h();
        this.f237k = false;
        synchronized (this.f235i) {
            int size = this.f235i.size();
            for (int i = 0; i < size; i++) {
                this.f235i.get(i).mo16676b();
            }
            this.f235i.clear();
        }
        mo16554a();
    }

    /* renamed from: e */
    public final void mo16667e() {
        this.f237k = true;
        YouTubeInitializationResult isYouTubeApiServiceAvailable = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this.f228b);
        if (isYouTubeApiServiceAvailable != YouTubeInitializationResult.SUCCESS) {
            Handler handler = this.f227a;
            handler.sendMessage(handler.obtainMessage(3, isYouTubeApiServiceAvailable));
            return;
        }
        Intent intent = new Intent(mo16661c()).setPackage(C1162z.m378a(this.f228b));
        if (this.f236j != null) {
            Log.e("YouTubeClient", "Calling connect() while still connected, missing disconnect().");
            mo16554a();
        }
        C1145e eVar = new C1145e();
        this.f236j = eVar;
        if (!this.f228b.bindService(intent, eVar, 129)) {
            Handler handler2 = this.f227a;
            handler2.sendMessage(handler2.obtainMessage(3, YouTubeInitializationResult.ERROR_CONNECTING_TO_SERVICE));
        }
    }

    /* renamed from: f */
    public final boolean mo16668f() {
        return this.f229c != null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public final void mo16669g() {
        synchronized (this.f230d) {
            boolean z = true;
            C1098ab.m126a(!this.f232f);
            this.f227a.removeMessages(4);
            this.f232f = true;
            if (this.f231e.size() != 0) {
                z = false;
            }
            C1098ab.m126a(z);
            ArrayList<C1151t.C1152a> arrayList = this.f230d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.f237k && mo16668f(); i++) {
                if (!this.f231e.contains(arrayList.get(i))) {
                    arrayList.get(i).mo16519a();
                }
            }
            this.f231e.clear();
            this.f232f = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public final void mo16670h() {
        this.f227a.removeMessages(4);
        synchronized (this.f230d) {
            this.f232f = true;
            ArrayList<C1151t.C1152a> arrayList = this.f230d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.f237k; i++) {
                if (this.f230d.contains(arrayList.get(i))) {
                    arrayList.get(i).mo16520b();
                }
            }
            this.f232f = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public final void mo16671i() {
        if (!mo16668f()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public final T mo16672j() {
        mo16671i();
        return this.f229c;
    }
}
