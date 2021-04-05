package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.g */
public interface C1113g extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.g$a */
    public static abstract class C1114a extends Binder implements C1113g {

        /* renamed from: com.google.android.youtube.player.internal.g$a$a */
        private static class C1115a implements C1113g {

            /* renamed from: a */
            private IBinder f190a;

            C1115a(IBinder iBinder) {
                this.f190a = iBinder;
            }

            /* renamed from: a */
            public final void mo16618a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f190a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16619a(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    obtain.writeString(str);
                    this.f190a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f190a;
            }

            /* renamed from: b */
            public final void mo16620b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f190a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: b */
            public final void mo16621b(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    obtain.writeString(str);
                    this.f190a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16622c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f190a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: d */
            public final void mo16623d() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                    this.f190a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C1114a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlayerStateChangeListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16618a();
                        break;
                    case 2:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16619a(parcel.readString());
                        break;
                    case 3:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16620b();
                        break;
                    case 4:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16622c();
                        break;
                    case 5:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16623d();
                        break;
                    case 6:
                        parcel.enforceInterface("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
                        mo16621b(parcel.readString());
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString("com.google.android.youtube.player.internal.IPlayerStateChangeListener");
            return true;
        }
    }

    /* renamed from: a */
    void mo16618a() throws RemoteException;

    /* renamed from: a */
    void mo16619a(String str) throws RemoteException;

    /* renamed from: b */
    void mo16620b() throws RemoteException;

    /* renamed from: b */
    void mo16621b(String str) throws RemoteException;

    /* renamed from: c */
    void mo16622c() throws RemoteException;

    /* renamed from: d */
    void mo16623d() throws RemoteException;
}
