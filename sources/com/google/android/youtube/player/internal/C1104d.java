package com.google.android.youtube.player.internal;

import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.google.android.youtube.player.internal.C1154u;
import java.util.List;

/* renamed from: com.google.android.youtube.player.internal.d */
public interface C1104d extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.d$a */
    public static abstract class C1105a extends Binder implements C1104d {

        /* renamed from: com.google.android.youtube.player.internal.d$a$a */
        private static class C1106a implements C1104d {

            /* renamed from: a */
            private IBinder f187a;

            C1106a(IBinder iBinder) {
                this.f187a = iBinder;
            }

            /* renamed from: a */
            public final void mo16561a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16562a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f187a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16563a(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    if (configuration != null) {
                        obtain.writeInt(1);
                        configuration.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f187a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16564a(C1107e eVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(eVar != null ? eVar.asBinder() : null);
                    this.f187a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16565a(C1110f fVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(fVar != null ? fVar.asBinder() : null);
                    this.f187a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16566a(C1113g gVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(gVar != null ? gVar.asBinder() : null);
                    this.f187a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16567a(C1116h hVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStrongBinder(hVar != null ? hVar.asBinder() : null);
                    this.f187a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16568a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    this.f187a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16569a(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f187a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16570a(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f187a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16571a(List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f187a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16572a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f187a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final boolean mo16573a(int i, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    boolean z = true;
                    if (keyEvent != null) {
                        obtain.writeInt(1);
                        keyEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f187a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final boolean mo16574a(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = true;
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f187a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f187a;
            }

            /* renamed from: b */
            public final void mo16575b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16576b(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f187a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16577b(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f187a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16578b(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f187a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16579b(List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f187a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16580b(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f187a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final boolean mo16581b(int i, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    boolean z = true;
                    if (keyEvent != null) {
                        obtain.writeInt(1);
                        keyEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f187a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16582c(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f187a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16583c(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f187a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final boolean mo16584c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f187a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo16585d(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(i);
                    this.f187a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo16586d(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f187a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final boolean mo16587d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f187a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: e */
            public final void mo16588e(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    obtain.writeInt(z ? 1 : 0);
                    this.f187a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: e */
            public final boolean mo16589e() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    boolean z = false;
                    this.f187a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: f */
            public final void mo16590f() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: g */
            public final void mo16591g() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: h */
            public final int mo16592h() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: i */
            public final int mo16593i() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: j */
            public final int mo16594j() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: k */
            public final void mo16595k() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: l */
            public final void mo16596l() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: m */
            public final void mo16597m() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: n */
            public final void mo16598n() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: o */
            public final void mo16599o() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: p */
            public final void mo16600p() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: q */
            public final void mo16601q() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: r */
            public final Bundle mo16602r() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: s */
            public final C1154u mo16603s() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IEmbeddedPlayer");
                    this.f187a.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return C1154u.C1155a.m366a(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C1104d m178a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IEmbeddedPlayer");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C1104d)) ? new C1106a(iBinder) : (C1104d) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: android.content.res.Configuration} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: android.view.KeyEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: android.view.KeyEvent} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1, types: [com.google.android.youtube.player.internal.e] */
        /* JADX WARNING: type inference failed for: r3v5, types: [com.google.android.youtube.player.internal.h] */
        /* JADX WARNING: type inference failed for: r3v9, types: [com.google.android.youtube.player.internal.g] */
        /* JADX WARNING: type inference failed for: r3v13, types: [com.google.android.youtube.player.internal.f] */
        /* JADX WARNING: type inference failed for: r3v29, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v31 */
        /* JADX WARNING: type inference failed for: r3v32 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: type inference failed for: r3v42 */
        /* JADX WARNING: type inference failed for: r3v43 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                java.lang.String r1 = "com.google.android.youtube.player.internal.IEmbeddedPlayer"
                r2 = 1
                if (r5 == r0) goto L_0x0326
                r0 = 0
                r3 = 0
                switch(r5) {
                    case 1: goto L_0x0315;
                    case 2: goto L_0x0303;
                    case 3: goto L_0x02f1;
                    case 4: goto L_0x02db;
                    case 5: goto L_0x02c5;
                    case 6: goto L_0x02af;
                    case 7: goto L_0x0299;
                    case 8: goto L_0x028f;
                    case 9: goto L_0x0285;
                    case 10: goto L_0x0277;
                    case 11: goto L_0x0269;
                    case 12: goto L_0x025b;
                    case 13: goto L_0x0251;
                    case 14: goto L_0x0247;
                    case 15: goto L_0x0239;
                    case 16: goto L_0x022b;
                    case 17: goto L_0x021d;
                    case 18: goto L_0x020f;
                    case 19: goto L_0x01fe;
                    case 20: goto L_0x01f0;
                    case 21: goto L_0x01e2;
                    case 22: goto L_0x01d4;
                    case 23: goto L_0x01c6;
                    case 24: goto L_0x01b5;
                    case 25: goto L_0x01a4;
                    case 26: goto L_0x017e;
                    case 27: goto L_0x0158;
                    case 28: goto L_0x0132;
                    case 29: goto L_0x010c;
                    case 30: goto L_0x0102;
                    case 31: goto L_0x00f8;
                    case 32: goto L_0x00df;
                    case 33: goto L_0x00d5;
                    case 34: goto L_0x00cb;
                    case 35: goto L_0x00c1;
                    case 36: goto L_0x00b7;
                    case 37: goto L_0x00a6;
                    case 38: goto L_0x009c;
                    case 39: goto L_0x0085;
                    case 40: goto L_0x0068;
                    case 41: goto L_0x0047;
                    case 42: goto L_0x0026;
                    case 43: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r5 = super.onTransact(r5, r6, r7, r8)
                return r5
            L_0x0012:
                r6.enforceInterface(r1)
                com.google.android.youtube.player.internal.u r5 = r4.mo16603s()
                r7.writeNoException()
                if (r5 == 0) goto L_0x0022
                android.os.IBinder r3 = r5.asBinder()
            L_0x0022:
                r7.writeStrongBinder(r3)
                return r2
            L_0x0026:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                int r8 = r6.readInt()
                if (r8 == 0) goto L_0x003c
                android.os.Parcelable$Creator r8 = android.view.KeyEvent.CREATOR
                java.lang.Object r6 = r8.createFromParcel(r6)
                r3 = r6
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
            L_0x003c:
                boolean r5 = r4.mo16581b((int) r5, (android.view.KeyEvent) r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0047:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                int r8 = r6.readInt()
                if (r8 == 0) goto L_0x005d
                android.os.Parcelable$Creator r8 = android.view.KeyEvent.CREATOR
                java.lang.Object r6 = r8.createFromParcel(r6)
                r3 = r6
                android.view.KeyEvent r3 = (android.view.KeyEvent) r3
            L_0x005d:
                boolean r5 = r4.mo16573a((int) r5, (android.view.KeyEvent) r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0068:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x007a
                android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r3 = r5
                android.os.Bundle r3 = (android.os.Bundle) r3
            L_0x007a:
                boolean r5 = r4.mo16574a((android.os.Bundle) r3)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0085:
                r6.enforceInterface(r1)
                android.os.Bundle r5 = r4.mo16602r()
                r7.writeNoException()
                if (r5 == 0) goto L_0x0098
                r7.writeInt(r2)
                r5.writeToParcel(r7, r2)
                goto L_0x009b
            L_0x0098:
                r7.writeInt(r0)
            L_0x009b:
                return r2
            L_0x009c:
                r6.enforceInterface(r1)
                r4.mo16601q()
                r7.writeNoException()
                return r2
            L_0x00a6:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x00b0
                r0 = 1
            L_0x00b0:
                r4.mo16588e(r0)
                r7.writeNoException()
                return r2
            L_0x00b7:
                r6.enforceInterface(r1)
                r4.mo16600p()
                r7.writeNoException()
                return r2
            L_0x00c1:
                r6.enforceInterface(r1)
                r4.mo16599o()
                r7.writeNoException()
                return r2
            L_0x00cb:
                r6.enforceInterface(r1)
                r4.mo16598n()
                r7.writeNoException()
                return r2
            L_0x00d5:
                r6.enforceInterface(r1)
                r4.mo16597m()
                r7.writeNoException()
                return r2
            L_0x00df:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x00f1
                android.os.Parcelable$Creator r5 = android.content.res.Configuration.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r3 = r5
                android.content.res.Configuration r3 = (android.content.res.Configuration) r3
            L_0x00f1:
                r4.mo16563a((android.content.res.Configuration) r3)
                r7.writeNoException()
                return r2
            L_0x00f8:
                r6.enforceInterface(r1)
                r4.mo16596l()
                r7.writeNoException()
                return r2
            L_0x0102:
                r6.enforceInterface(r1)
                r4.mo16595k()
                r7.writeNoException()
                return r2
            L_0x010c:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0116
                goto L_0x012b
            L_0x0116:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlaybackEventListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0126
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C1110f
                if (r8 == 0) goto L_0x0126
                r3 = r6
                com.google.android.youtube.player.internal.f r3 = (com.google.android.youtube.player.internal.C1110f) r3
                goto L_0x012b
            L_0x0126:
                com.google.android.youtube.player.internal.f$a$a r3 = new com.google.android.youtube.player.internal.f$a$a
                r3.<init>(r5)
            L_0x012b:
                r4.mo16565a((com.google.android.youtube.player.internal.C1110f) r3)
                r7.writeNoException()
                return r2
            L_0x0132:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x013c
                goto L_0x0151
            L_0x013c:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlayerStateChangeListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x014c
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C1113g
                if (r8 == 0) goto L_0x014c
                r3 = r6
                com.google.android.youtube.player.internal.g r3 = (com.google.android.youtube.player.internal.C1113g) r3
                goto L_0x0151
            L_0x014c:
                com.google.android.youtube.player.internal.g$a$a r3 = new com.google.android.youtube.player.internal.g$a$a
                r3.<init>(r5)
            L_0x0151:
                r4.mo16566a((com.google.android.youtube.player.internal.C1113g) r3)
                r7.writeNoException()
                return r2
            L_0x0158:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0162
                goto L_0x0177
            L_0x0162:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IPlaylistEventListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0172
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C1116h
                if (r8 == 0) goto L_0x0172
                r3 = r6
                com.google.android.youtube.player.internal.h r3 = (com.google.android.youtube.player.internal.C1116h) r3
                goto L_0x0177
            L_0x0172:
                com.google.android.youtube.player.internal.h$a$a r3 = new com.google.android.youtube.player.internal.h$a$a
                r3.<init>(r5)
            L_0x0177:
                r4.mo16567a((com.google.android.youtube.player.internal.C1116h) r3)
                r7.writeNoException()
                return r2
            L_0x017e:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                if (r5 != 0) goto L_0x0188
                goto L_0x019d
            L_0x0188:
                java.lang.String r6 = "com.google.android.youtube.player.internal.IOnFullscreenListener"
                android.os.IInterface r6 = r5.queryLocalInterface(r6)
                if (r6 == 0) goto L_0x0198
                boolean r8 = r6 instanceof com.google.android.youtube.player.internal.C1107e
                if (r8 == 0) goto L_0x0198
                r3 = r6
                com.google.android.youtube.player.internal.e r3 = (com.google.android.youtube.player.internal.C1107e) r3
                goto L_0x019d
            L_0x0198:
                com.google.android.youtube.player.internal.e$a$a r3 = new com.google.android.youtube.player.internal.e$a$a
                r3.<init>(r5)
            L_0x019d:
                r4.mo16564a((com.google.android.youtube.player.internal.C1107e) r3)
                r7.writeNoException()
                return r2
            L_0x01a4:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x01ae
                r0 = 1
            L_0x01ae:
                r4.mo16586d((boolean) r0)
                r7.writeNoException()
                return r2
            L_0x01b5:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x01bf
                r0 = 1
            L_0x01bf:
                r4.mo16583c((boolean) r0)
                r7.writeNoException()
                return r2
            L_0x01c6:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                r4.mo16568a((java.lang.String) r5)
                r7.writeNoException()
                return r2
            L_0x01d4:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo16585d((int) r5)
                r7.writeNoException()
                return r2
            L_0x01e2:
                r6.enforceInterface(r1)
                int r5 = r4.mo16594j()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x01f0:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo16582c((int) r5)
                r7.writeNoException()
                return r2
            L_0x01fe:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x0208
                r0 = 1
            L_0x0208:
                r4.mo16580b((boolean) r0)
                r7.writeNoException()
                return r2
            L_0x020f:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo16576b((int) r5)
                r7.writeNoException()
                return r2
            L_0x021d:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                r4.mo16562a((int) r5)
                r7.writeNoException()
                return r2
            L_0x022b:
                r6.enforceInterface(r1)
                int r5 = r4.mo16593i()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0239:
                r6.enforceInterface(r1)
                int r5 = r4.mo16592h()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0247:
                r6.enforceInterface(r1)
                r4.mo16591g()
                r7.writeNoException()
                return r2
            L_0x0251:
                r6.enforceInterface(r1)
                r4.mo16590f()
                r7.writeNoException()
                return r2
            L_0x025b:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo16589e()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0269:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo16587d()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0277:
                r6.enforceInterface(r1)
                boolean r5 = r4.mo16584c()
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0285:
                r6.enforceInterface(r1)
                r4.mo16575b()
                r7.writeNoException()
                return r2
            L_0x028f:
                r6.enforceInterface(r1)
                r4.mo16561a()
                r7.writeNoException()
                return r2
            L_0x0299:
                r6.enforceInterface(r1)
                java.util.ArrayList r5 = r6.createStringArrayList()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo16579b((java.util.List<java.lang.String>) r5, (int) r8, (int) r6)
                r7.writeNoException()
                return r2
            L_0x02af:
                r6.enforceInterface(r1)
                java.util.ArrayList r5 = r6.createStringArrayList()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo16571a((java.util.List<java.lang.String>) r5, (int) r8, (int) r6)
                r7.writeNoException()
                return r2
            L_0x02c5:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo16578b((java.lang.String) r5, (int) r8, (int) r6)
                r7.writeNoException()
                return r2
            L_0x02db:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r8 = r6.readInt()
                int r6 = r6.readInt()
                r4.mo16570a((java.lang.String) r5, (int) r8, (int) r6)
                r7.writeNoException()
                return r2
            L_0x02f1:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r6 = r6.readInt()
                r4.mo16577b((java.lang.String) r5, (int) r6)
                r7.writeNoException()
                return r2
            L_0x0303:
                r6.enforceInterface(r1)
                java.lang.String r5 = r6.readString()
                int r6 = r6.readInt()
                r4.mo16569a((java.lang.String) r5, (int) r6)
                r7.writeNoException()
                return r2
            L_0x0315:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x031f
                r0 = 1
            L_0x031f:
                r4.mo16572a((boolean) r0)
                r7.writeNoException()
                return r2
            L_0x0326:
                r7.writeString(r1)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.internal.C1104d.C1105a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    /* renamed from: a */
    void mo16561a() throws RemoteException;

    /* renamed from: a */
    void mo16562a(int i) throws RemoteException;

    /* renamed from: a */
    void mo16563a(Configuration configuration) throws RemoteException;

    /* renamed from: a */
    void mo16564a(C1107e eVar) throws RemoteException;

    /* renamed from: a */
    void mo16565a(C1110f fVar) throws RemoteException;

    /* renamed from: a */
    void mo16566a(C1113g gVar) throws RemoteException;

    /* renamed from: a */
    void mo16567a(C1116h hVar) throws RemoteException;

    /* renamed from: a */
    void mo16568a(String str) throws RemoteException;

    /* renamed from: a */
    void mo16569a(String str, int i) throws RemoteException;

    /* renamed from: a */
    void mo16570a(String str, int i, int i2) throws RemoteException;

    /* renamed from: a */
    void mo16571a(List<String> list, int i, int i2) throws RemoteException;

    /* renamed from: a */
    void mo16572a(boolean z) throws RemoteException;

    /* renamed from: a */
    boolean mo16573a(int i, KeyEvent keyEvent) throws RemoteException;

    /* renamed from: a */
    boolean mo16574a(Bundle bundle) throws RemoteException;

    /* renamed from: b */
    void mo16575b() throws RemoteException;

    /* renamed from: b */
    void mo16576b(int i) throws RemoteException;

    /* renamed from: b */
    void mo16577b(String str, int i) throws RemoteException;

    /* renamed from: b */
    void mo16578b(String str, int i, int i2) throws RemoteException;

    /* renamed from: b */
    void mo16579b(List<String> list, int i, int i2) throws RemoteException;

    /* renamed from: b */
    void mo16580b(boolean z) throws RemoteException;

    /* renamed from: b */
    boolean mo16581b(int i, KeyEvent keyEvent) throws RemoteException;

    /* renamed from: c */
    void mo16582c(int i) throws RemoteException;

    /* renamed from: c */
    void mo16583c(boolean z) throws RemoteException;

    /* renamed from: c */
    boolean mo16584c() throws RemoteException;

    /* renamed from: d */
    void mo16585d(int i) throws RemoteException;

    /* renamed from: d */
    void mo16586d(boolean z) throws RemoteException;

    /* renamed from: d */
    boolean mo16587d() throws RemoteException;

    /* renamed from: e */
    void mo16588e(boolean z) throws RemoteException;

    /* renamed from: e */
    boolean mo16589e() throws RemoteException;

    /* renamed from: f */
    void mo16590f() throws RemoteException;

    /* renamed from: g */
    void mo16591g() throws RemoteException;

    /* renamed from: h */
    int mo16592h() throws RemoteException;

    /* renamed from: i */
    int mo16593i() throws RemoteException;

    /* renamed from: j */
    int mo16594j() throws RemoteException;

    /* renamed from: k */
    void mo16595k() throws RemoteException;

    /* renamed from: l */
    void mo16596l() throws RemoteException;

    /* renamed from: m */
    void mo16597m() throws RemoteException;

    /* renamed from: n */
    void mo16598n() throws RemoteException;

    /* renamed from: o */
    void mo16599o() throws RemoteException;

    /* renamed from: p */
    void mo16600p() throws RemoteException;

    /* renamed from: q */
    void mo16601q() throws RemoteException;

    /* renamed from: r */
    Bundle mo16602r() throws RemoteException;

    /* renamed from: s */
    C1154u mo16603s() throws RemoteException;
}
