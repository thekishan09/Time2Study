package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.youtube.player.internal.j */
public interface C1122j extends IInterface {

    /* renamed from: com.google.android.youtube.player.internal.j$a */
    public static abstract class C1123a extends Binder implements C1122j {

        /* renamed from: com.google.android.youtube.player.internal.j$a$a */
        private static class C1124a implements C1122j {

            /* renamed from: a */
            private IBinder f193a;

            C1124a(IBinder iBinder) {
                this.f193a = iBinder;
            }

            /* renamed from: a */
            public final void mo16636a(Bitmap bitmap, String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                    if (bitmap != null) {
                        obtain.writeInt(1);
                        bitmap.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(z2 ? 1 : 0);
                    this.f193a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            /* renamed from: a */
            public final void mo16637a(String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                    obtain.writeString(str);
                    int i = 1;
                    obtain.writeInt(z ? 1 : 0);
                    if (!z2) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.f193a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.f193a;
            }
        }

        public C1123a() {
            attachInterface(this, "com.google.android.youtube.player.internal.IThumbnailLoaderClient");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            if (i == 1) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                Bitmap bitmap = parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null;
                String readString = parcel.readString();
                boolean z2 = parcel.readInt() != 0;
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo16636a(bitmap, readString, z2, z);
            } else if (i == 2) {
                parcel.enforceInterface("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                String readString2 = parcel.readString();
                boolean z3 = parcel.readInt() != 0;
                if (parcel.readInt() != 0) {
                    z = true;
                }
                mo16637a(readString2, z3, z);
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString("com.google.android.youtube.player.internal.IThumbnailLoaderClient");
                return true;
            }
            parcel2.writeNoException();
            return true;
        }
    }

    /* renamed from: a */
    void mo16636a(Bitmap bitmap, String str, boolean z, boolean z2) throws RemoteException;

    /* renamed from: a */
    void mo16637a(String str, boolean z, boolean z2) throws RemoteException;
}
