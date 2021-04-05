package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.k */
public interface C1125k extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.k$a */
    public static abstract class C1126a extends Binder implements C1125k {

        /* renamed from: com.google.android.youtube.player.internal.k$a$a */
        private static class C1127a implements C1125k {

            /* renamed from: a */
            private IBinder f194a;

            C1127a(IBinder iBinder) {
                this.f194a = iBinder;
            }

            /* renamed from: a */
            public final void mo16641a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f194a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16642a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    obtain.writeString(str);
                    this.f194a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16643a(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.f194a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f194a;
            }

            /* renamed from: b */
            public final void mo16644b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f194a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16645c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f194a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo16646d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                    this.f194a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        /* renamed from: a */
        public static C1125k m265a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C1125k)) ? new C1127a(iBinder) : (C1125k) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16642a(parcel.readString());
                        break;
                    case 2:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16643a(parcel.readString(), parcel.readInt());
                        break;
                    case 3:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16641a();
                        break;
                    case 4:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16644b();
                        break;
                    case 5:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16645c();
                        break;
                    case 6:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderService");
                        mo16646d();
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString("com.google.android.youtube.player.internal.IThumbnailLoaderService");
            return true;
        }
    }

    /* renamed from: a */
    void mo16641a() throws RemoteException;

    /* renamed from: a */
    void mo16642a(String str) throws RemoteException;

    /* renamed from: a */
    void mo16643a(String str, int i) throws RemoteException;

    /* renamed from: b */
    void mo16644b() throws RemoteException;

    /* renamed from: c */
    void mo16645c() throws RemoteException;

    /* renamed from: d */
    void mo16646d() throws RemoteException;
}
