package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
public class zzir extends IOException {
    private zzjr zza = null;

    public zzir(String str) {
        super(str);
    }

    public final zzir zza(zzjr zzjr) {
        this.zza = zzjr;
        return this;
    }

    static zzir zza() {
        return new zzir("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzir zzb() {
        return new zzir("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzir zzc() {
        return new zzir("CodedInputStream encountered a malformed varint.");
    }

    static zzir zzd() {
        return new zzir("Protocol message contained an invalid tag (zero).");
    }

    static zzir zze() {
        return new zzir("Protocol message end-group tag did not match expected tag.");
    }

    static zziq zzf() {
        return new zziq("Protocol message tag had invalid wire type.");
    }

    static zzir zzg() {
        return new zzir("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    static zzir zzh() {
        return new zzir("Failed to parse the message.");
    }

    static zzir zzi() {
        return new zzir("Protocol message had invalid UTF-8.");
    }
}
