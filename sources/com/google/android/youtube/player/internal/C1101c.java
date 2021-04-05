package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.c */
public interface C1101c extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.c$a */
    public static abstract class C1102a extends Binder implements C1101c {

        /* renamed from: com.google.android.youtube.player.internal.c$a$a */
        private static class C1103a implements C1101c {

            /* renamed from: a */
            private IBinder f186a;

            C1103a(IBinder iBinder) {
                this.f186a = iBinder;
            }

            /* renamed from: a */
            public final void mo16557a(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IConnectionCallbacks");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.f186a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f186a;
            }
        }

        public C1102a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IConnectionCallbacks");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IConnectionCallbacks");
                mo16557a(parcel.readString(), parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IConnectionCallbacks");
                return true;
            }
        }
    }

    /* renamed from: a */
    void mo16557a(String str, IBinder iBinder) throws RemoteException;
}
