package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.h */
public interface C1116h extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.h$a */
    public static abstract class C1117a extends Binder implements C1116h {

        /* renamed from: com.google.android.youtube.player.internal.h$a$a */
        private static class C1118a implements C1116h {

            /* renamed from: a */
            private IBinder f191a;

            C1118a(IBinder iBinder) {
                this.f191a = iBinder;
            }

            /* renamed from: a */
            public final void mo16627a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f191a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f191a;
            }

            /* renamed from: b */
            public final void mo16628b() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f191a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: c */
            public final void mo16629c() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IPlaylistEventListener");
                    this.f191a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C1117a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IPlaylistEventListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaylistEventListener");
                mo16627a();
            } else if (i == 2) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaylistEventListener");
                mo16628b();
            } else if (i == 3) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IPlaylistEventListener");
                mo16629c();
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IPlaylistEventListener");
                return true;
            }
            parcel2.writeNoException();
            return true;
        }
    }

    /* renamed from: a */
    void mo16627a() throws RemoteException;

    /* renamed from: b */
    void mo16628b() throws RemoteException;

    /* renamed from: c */
    void mo16629c() throws RemoteException;
}
