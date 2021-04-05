package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C2502zaa> zaco;

    public zaa(Activity activity) {
        this(C2502zaa.zaa(activity));
    }

    private zaa(C2502zaa zaa) {
        this.zaco = new WeakReference<>(zaa);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C2502zaa zaa = (C2502zaa) this.zaco.get();
        if (zaa != null) {
            zaa.zaa(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }

    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa  reason: collision with other inner class name */
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    static class C2502zaa extends LifecycleCallback {
        private List<Runnable> zacn = new ArrayList();

        /* access modifiers changed from: private */
        public static C2502zaa zaa(Activity activity) {
            C2502zaa zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                zaa = (C2502zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C2502zaa.class);
                if (zaa == null) {
                    zaa = new C2502zaa(fragment);
                }
            }
            return zaa;
        }

        private C2502zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacn.add(runnable);
        }

        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacn;
                this.zacn = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }
}
