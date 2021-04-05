package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.f */
public interface C1110f extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.f$a */
    public static abstract class C1111a extends Binder implements C1110f {

        /* renamed from: com.google.android.youtube.player.internal.f$a$a */
        private static class C1112a implements C1110f {

            /* renamed from: a */
            private IBinder f189a;

            C1112a(IBinder iBinder) {
                this.f189a = iBinder;
            }

            /* renamed from: a */
            public final void mo16610a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f189a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16611a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    obtain.writeInt(i);
                    this.f189a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16612a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    obtain.writeInt(z ? 1 : 0);
                    this.f189a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f189a;
            }

            /* renamed from: b */
            public final void mo16613b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f189a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16614c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaybackEventListener");
                    this.f189a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C1111a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlaybackEventListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaybackEventListener");
                mo16610a();
            } else if (i == 2) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaybackEventListener");
                mo16613b();
            } else if (i == 3) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaybackEventListener");
                mo16614c();
            } else if (i == 4) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaybackEventListener");
                mo16612a(parcel.readInt() != 0);
            } else if (i == 5) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaybackEventListener");
                mo16611a(parcel.readInt());
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IPlaybackEventListener");
                return true;
            }
            parcel2.writeNoException();
            return true;
        }
    }

    /* renamed from: a */
    void mo16610a() throws RemoteException;

    /* renamed from: a */
    void mo16611a(int i) throws RemoteException;

    /* renamed from: a */
    void mo16612a(boolean z) throws RemoteException;

    /* renamed from: b */
    void mo16613b() throws RemoteException;

    /* renamed from: c */
    void mo16614c() throws RemoteException;
}
