package com.google.android.youtube.player.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.youtube.player.internal.C1128l;
import com.google.android.youtube.player.internal.C1139r;
import com.google.android.youtube.player.internal.C1151t;

/* renamed from: com.google.android.youtube.player.internal.o */
public final class C1133o extends C1139r<C1128l> implements C1100b {

    /* renamed from: b */
    private final String f208b;

    /* renamed from: c */
    private final String f209c;

    /* renamed from: d */
    private final String f210d;

    /* renamed from: e */
    private boolean f211e;

    public C1133o(Context context, String str, String str2, String str3, C1151t.C1152a aVar, C1151t.C1153b bVar) {
        super(context, aVar, bVar);
        this.f208b = (String) C1098ab.m123a(str);
        this.f209c = C1098ab.m125a(str2, (Object) "callingPackage cannot be null or empty");
        this.f210d = C1098ab.m125a(str3, (Object) "callingAppVersion cannot be null or empty");
    }

    /* renamed from: k */
    private final void m282k() {
        mo16671i();
        if (this.f211e) {
            throw new IllegalStateException("Connection client has been released");
        }
    }

    /* renamed from: a */
    public final IBinder mo16554a() {
        m282k();
        try {
            return ((C1128l) mo16672j()).mo16649a();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final /* synthetic */ IInterface mo16658a(IBinder iBinder) {
        return C1128l.C1129a.m275a(iBinder);
    }

    /* renamed from: a */
    public final C1125k mo16555a(C1122j jVar) {
        m282k();
        try {
            return ((C1128l) mo16672j()).mo16650a(jVar);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo16659a(C1119i iVar, C1139r.C1144d dVar) throws RemoteException {
        iVar.mo16633a(dVar, 1202, this.f209c, this.f210d, this.f208b, (Bundle) null);
    }

    /* renamed from: a */
    public final void mo16556a(boolean z) {
        if (mo16668f()) {
            try {
                ((C1128l) mo16672j()).mo16651a(z);
            } catch (RemoteException e) {
            }
            this.f211e = true;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final String mo16660b() {
        return "com.google.android.youtube.player.internal.IYouTubeService";
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public final String mo16661c() {
        return "com.google.android.youtube.api.service.START";
    }

    /* renamed from: d */
    public final void mo16662d() {
        if (!this.f211e) {
            mo16556a(true);
        }
        super.mo16662d();
    }
}
