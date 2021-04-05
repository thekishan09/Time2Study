package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.internal.C1122j;

/* renamed from: com.google.android.youtube.player.internal.p */
public final class C1134p extends C1096a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final Handler f212a = new Handler(Looper.getMainLooper());

    /* renamed from: b */
    private C1100b f213b;

    /* renamed from: c */
    private C1125k f214c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f215d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public boolean f216e;

    /* renamed from: com.google.android.youtube.player.internal.p$a */
    private final class C1135a extends C1122j.C1123a {
        private C1135a() {
        }

        /* synthetic */ C1135a(C1134p pVar, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo16636a(Bitmap bitmap, String str, boolean z, boolean z2) {
            final boolean z3 = z;
            final boolean z4 = z2;
            final Bitmap bitmap2 = bitmap;
            final String str2 = str;
            C1134p.this.f212a.post(new Runnable() {
                public final void run() {
                    boolean unused = C1134p.this.f215d = z3;
                    boolean unused2 = C1134p.this.f216e = z4;
                    C1134p.this.mo16539a(bitmap2, str2);
                }
            });
        }

        /* renamed from: a */
        public final void mo16637a(final String str, final boolean z, final boolean z2) {
            C1134p.this.f212a.post(new Runnable() {
                public final void run() {
                    boolean unused = C1134p.this.f215d = z;
                    boolean unused2 = C1134p.this.f216e = z2;
                    C1134p.this.mo16544b(str);
                }
            });
        }
    }

    public C1134p(C1100b bVar, YouTubeThumbnailView youTubeThumbnailView) {
        super(youTubeThumbnailView);
        this.f213b = (C1100b) C1098ab.m124a(bVar, (Object) "connectionClient cannot be null");
        this.f214c = bVar.mo16555a((C1122j) new C1135a(this, (byte) 0));
    }

    /* renamed from: a */
    public final void mo16540a(String str) {
        try {
            this.f214c.mo16642a(str);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: a */
    public final void mo16541a(String str, int i) {
        try {
            this.f214c.mo16643a(str, i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo16542a() {
        return super.mo16542a() && this.f214c != null;
    }

    /* renamed from: c */
    public final void mo16545c() {
        try {
            this.f214c.mo16641a();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: d */
    public final void mo16546d() {
        try {
            this.f214c.mo16644b();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: e */
    public final void mo16547e() {
        try {
            this.f214c.mo16645c();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: f */
    public final boolean mo16548f() {
        return this.f216e;
    }

    /* renamed from: g */
    public final boolean mo16549g() {
        return this.f215d;
    }

    /* renamed from: h */
    public final void mo16550h() {
        try {
            this.f214c.mo16646d();
        } catch (RemoteException e) {
        }
        this.f213b.mo16662d();
        this.f214c = null;
        this.f213b = null;
    }
}
