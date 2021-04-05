package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* renamed from: com.google.android.youtube.player.internal.a */
public abstract class C1096a implements YouTubeThumbnailLoader {

    /* renamed from: a */
    private final WeakReference<YouTubeThumbnailView> f181a;

    /* renamed from: b */
    private YouTubeThumbnailLoader.OnThumbnailLoadedListener f182b;

    /* renamed from: c */
    private boolean f183c;

    /* renamed from: d */
    private boolean f184d;

    public C1096a(YouTubeThumbnailView youTubeThumbnailView) {
        this.f181a = new WeakReference<>(C1098ab.m123a(youTubeThumbnailView));
    }

    /* renamed from: i */
    private void m105i() {
        if (!mo16542a()) {
            throw new IllegalStateException("This YouTubeThumbnailLoader has been released");
        }
    }

    /* renamed from: a */
    public final void mo16539a(Bitmap bitmap, String str) {
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) this.f181a.get();
        if (mo16542a() && youTubeThumbnailView != null) {
            youTubeThumbnailView.setImageBitmap(bitmap);
            YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = this.f182b;
            if (onThumbnailLoadedListener != null) {
                onThumbnailLoadedListener.onThumbnailLoaded(youTubeThumbnailView, str);
            }
        }
    }

    /* renamed from: a */
    public abstract void mo16540a(String str);

    /* renamed from: a */
    public abstract void mo16541a(String str, int i);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo16542a() {
        return !this.f184d;
    }

    /* renamed from: b */
    public final void mo16543b() {
        if (mo16542a()) {
            C1161y.m375a("The finalize() method for a YouTubeThumbnailLoader has work to do. You should have called release().", new Object[0]);
            release();
        }
    }

    /* renamed from: b */
    public final void mo16544b(String str) {
        YouTubeThumbnailLoader.ErrorReason errorReason;
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) this.f181a.get();
        if (mo16542a() && this.f182b != null && youTubeThumbnailView != null) {
            try {
                errorReason = YouTubeThumbnailLoader.ErrorReason.valueOf(str);
            } catch (IllegalArgumentException | NullPointerException e) {
                errorReason = YouTubeThumbnailLoader.ErrorReason.UNKNOWN;
            }
            this.f182b.onThumbnailError(youTubeThumbnailView, errorReason);
        }
    }

    /* renamed from: c */
    public abstract void mo16545c();

    /* renamed from: d */
    public abstract void mo16546d();

    /* renamed from: e */
    public abstract void mo16547e();

    /* renamed from: f */
    public abstract boolean mo16548f();

    public final void first() {
        m105i();
        if (this.f183c) {
            mo16547e();
            return;
        }
        throw new IllegalStateException("Must call setPlaylist first");
    }

    /* renamed from: g */
    public abstract boolean mo16549g();

    /* renamed from: h */
    public abstract void mo16550h();

    public final boolean hasNext() {
        m105i();
        return mo16548f();
    }

    public final boolean hasPrevious() {
        m105i();
        return mo16549g();
    }

    public final void next() {
        m105i();
        if (!this.f183c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (mo16548f()) {
            mo16545c();
        } else {
            throw new NoSuchElementException("Called next at end of playlist");
        }
    }

    public final void previous() {
        m105i();
        if (!this.f183c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (mo16549g()) {
            mo16546d();
        } else {
            throw new NoSuchElementException("Called previous at start of playlist");
        }
    }

    public final void release() {
        if (mo16542a()) {
            this.f184d = true;
            this.f182b = null;
            mo16550h();
        }
    }

    public final void setOnThumbnailLoadedListener(YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener) {
        m105i();
        this.f182b = onThumbnailLoadedListener;
    }

    public final void setPlaylist(String str) {
        setPlaylist(str, 0);
    }

    public final void setPlaylist(String str, int i) {
        m105i();
        this.f183c = true;
        mo16541a(str, i);
    }

    public final void setVideo(String str) {
        m105i();
        this.f183c = false;
        mo16540a(str);
    }
}
