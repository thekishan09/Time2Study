package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.youtube.player.internal.C1122j;
import com.google.android.youtube.player.internal.C1125k;

/* renamed from: com.google.android.youtube.player.internal.l */
public interface C1128l extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.l$a */
    public static abstract class C1129a extends Binder implements C1128l {

        /* renamed from: com.google.android.youtube.player.internal.l$a$a */
        private static class C1130a implements C1128l {

            /* renamed from: a */
            private IBinder f195a;

            C1130a(IBinder iBinder) {
                this.f195a = iBinder;
            }

            /* renamed from: a */
            public final IBinder mo16649a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    this.f195a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final C1125k mo16650a(C1122j jVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    obtain.writeStrongBinder(jVar != null ? jVar.asBinder() : null);
                    this.f195a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return C1125k.C1126a.m265a(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16651a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IYouTubeService");
                    obtain.writeInt(z ? 1 : 0);
                    this.f195a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f195a;
            }
        }

        /* renamed from: a */
        public static C1128l m275a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IYouTubeService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C1128l)) ? new C1130a(iBinder) : (C1128l) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            C1122j jVar;
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IYouTubeService");
                IBinder a = mo16649a();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(a);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IYouTubeService");
                IBinder readStrongBinder = parcel.readStrongBinder();
                IBinder iBinder = null;
                if (readStrongBinder == null) {
                    jVar = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                    jVar = (queryLocalInterface == null || !(queryLocalInterface instanceof C1122j)) ? new C1122j.C1123a.C1124a(readStrongBinder) : (C1122j) queryLocalInterface;
                }
                C1125k a2 = mo16650a(jVar);
                parcel2.writeNoException();
                if (a2 != null) {
                    iBinder = a2.asBinder();
                }
                parcel2.writeStrongBinder(iBinder);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IYouTubeService");
                mo16651a(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IYouTubeService");
                return true;
            }
        }
    }

    /* renamed from: a */
    IBinder mo16649a() throws RemoteException;

    /* renamed from: a */
    C1125k mo16650a(C1122j jVar) throws RemoteException;

    /* renamed from: a */
    void mo16651a(boolean z) throws RemoteException;
}
