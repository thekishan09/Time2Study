package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.firebase_auth.zze;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public final class zzeg extends GmsClient<zzer> implements zzeh {
    private static Logger zza = new Logger("FirebaseAuth", "FirebaseAuth:");
    private final Context zzb;
    private final zzew zzc;

    public zzeg(Context context, Looper looper, ClientSettings clientSettings, zzew zzew, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 112, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzb = (Context) Preconditions.checkNotNull(context);
        this.zzc = zzew;
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.firebase.auth.api.gms.service.START";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.firebase.auth.api.internal.IFirebaseAuthService";
    }

    /* access modifiers changed from: protected */
    public final String getStartServicePackage() {
        if (this.zzc.zza) {
            zza.mo11932i("Preparing to create service connection to fallback implementation", new Object[0]);
            return this.zzb.getPackageName();
        }
        zza.mo11932i("Preparing to create service connection to gms implementation", new Object[0]);
        return "com.google.android.gms";
    }

    public final boolean requiresGooglePlayServices() {
        return DynamiteModule.getLocalVersion(this.zzb, "com.google.firebase.auth") == 0;
    }

    /* access modifiers changed from: protected */
    public final Bundle getGetServiceRequestExtraArgs() {
        Bundle getServiceRequestExtraArgs = super.getGetServiceRequestExtraArgs();
        if (getServiceRequestExtraArgs == null) {
            getServiceRequestExtraArgs = new Bundle();
        }
        zzew zzew = this.zzc;
        if (zzew != null) {
            getServiceRequestExtraArgs.putString("com.google.firebase.auth.API_KEY", zzew.zzb());
        }
        getServiceRequestExtraArgs.putString("com.google.firebase.auth.LIBRARY_VERSION", zzey.zzc());
        return getServiceRequestExtraArgs;
    }

    public final int getMinApkVersion() {
        return 12451000;
    }

    public final Feature[] getApiFeatures() {
        return zze.zzc;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.auth.api.internal.IFirebaseAuthService");
        if (queryLocalInterface instanceof zzer) {
            return (zzer) queryLocalInterface;
        }
        return new zzes(iBinder);
    }

    public final /* synthetic */ zzer zza() throws DeadObjectException {
        return (zzer) super.getService();
    }
}
