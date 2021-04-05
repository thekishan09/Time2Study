package com.google.android.gms.auth.api.signin.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzc implements Runnable {
    private static final Logger zzcb = new Logger("RevokeAccessOperation", new String[0]);
    private final String zzcc;
    private final StatusPendingResult zzcd = new StatusPendingResult((GoogleApiClient) null);

    private zzc(String str) {
        this.zzcc = Preconditions.checkNotEmpty(str);
    }

    public final void run() {
        Status status = Status.RESULT_INTERNAL_ERROR;
        try {
            String valueOf = String.valueOf("https://accounts.google.com/o/oauth2/revoke?token=");
            String valueOf2 = String.valueOf(this.zzcc);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).openConnection();
            httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                status = Status.RESULT_SUCCESS;
            } else {
                zzcb.mo11929e("Unable to revoke access!", new Object[0]);
            }
            Logger logger = zzcb;
            StringBuilder sb = new StringBuilder(26);
            sb.append("Response Code: ");
            sb.append(responseCode);
            logger.mo11927d(sb.toString(), new Object[0]);
        } catch (IOException e) {
            Logger logger2 = zzcb;
            String valueOf3 = String.valueOf(e.toString());
            logger2.mo11929e(valueOf3.length() != 0 ? "IOException when revoking access: ".concat(valueOf3) : new String("IOException when revoking access: "), new Object[0]);
        } catch (Exception e2) {
            Logger logger3 = zzcb;
            String valueOf4 = String.valueOf(e2.toString());
            logger3.mo11929e(valueOf4.length() != 0 ? "Exception when revoking access: ".concat(valueOf4) : new String("Exception when revoking access: "), new Object[0]);
        }
        this.zzcd.setResult(status);
    }

    public static PendingResult<Status> zzf(String str) {
        if (str == null) {
            return PendingResults.immediateFailedResult(new Status(4), (GoogleApiClient) null);
        }
        zzc zzc = new zzc(str);
        new Thread(zzc).start();
        return zzc.zzcd;
    }
}
