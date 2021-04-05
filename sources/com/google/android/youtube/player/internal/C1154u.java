package com.google.android.youtube.player.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.u */
public interface C1154u extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.u$a */
    public static abstract class C1155a extends Binder implements C1154u {

        /* renamed from: com.google.android.youtube.player.internal.u$a$a */
        private static class C1156a implements C1154u {

            /* renamed from: a */
            private IBinder f257a;

            C1156a(IBinder iBinder) {
                this.f257a = iBinder;
            }

            public final IBinder asBinder() {
                return this.f257a;
            }
        }

        public C1155a() {
            attachInterface(this, "com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
        }

        /* renamed from: a */
        public static C1154u m366a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C1154u)) ? new C1156a(iBinder) : (C1154u) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.google.android.youtube.player.internal.dynamic.IObjectWrapper");
            return true;
        }
    }
}
