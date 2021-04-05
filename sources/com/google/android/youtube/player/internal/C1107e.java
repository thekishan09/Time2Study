package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.e */
public interface C1107e extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.e$a */
    public static abstract class C1108a extends Binder implements C1107e {

        /* renamed from: com.google.android.youtube.player.internal.e$a$a */
        private static class C1109a implements C1107e {

            /* renamed from: a */
            private IBinder f188a;

            C1109a(IBinder iBinder) {
                this.f188a = iBinder;
            }

            /* renamed from: a */
            public final void mo16606a(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IOnFullscreenListener");
                    obtain.writeInt(z ? 1 : 0);
                    this.f188a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f188a;
            }
        }

        public C1108a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IOnFullscreenListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IOnFullscreenListener");
                mo16606a(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IOnFullscreenListener");
                return true;
            }
        }
    }

    /* renamed from: a */
    void mo16606a(boolean z) throws RemoteException;
}
