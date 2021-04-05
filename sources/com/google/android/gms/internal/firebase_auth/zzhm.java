package com.google.android.gms.internal.firebase_auth;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzhm extends zzhh {
    private final InputStream zze;
    private final byte[] zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;
    private int zzl;
    private zzhl zzm;

    private zzhm(InputStream inputStream, int i) {
        super();
        this.zzl = Integer.MAX_VALUE;
        this.zzm = null;
        zzii.zza(inputStream, "input");
        this.zze = inputStream;
        this.zzf = new byte[i];
        this.zzg = 0;
        this.zzi = 0;
        this.zzk = 0;
    }

    public final int zza() throws IOException {
        if (zzt()) {
            this.zzj = 0;
            return 0;
        }
        int zzv = zzv();
        this.zzj = zzv;
        if ((zzv >>> 3) != 0) {
            return zzv;
        }
        throw zzir.zzd();
    }

    public final void zza(int i) throws zzir {
        if (this.zzj != i) {
            throw zzir.zze();
        }
    }

    public final boolean zzb(int i) throws IOException {
        int zza;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzg - this.zzi >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.zzf;
                    int i4 = this.zzi;
                    this.zzi = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzir.zzc();
            }
            while (i3 < 10) {
                if (zzaa() < 0) {
                    i3++;
                }
            }
            throw zzir.zzc();
            return true;
        } else if (i2 == 1) {
            zzj(8);
            return true;
        } else if (i2 == 2) {
            zzj(zzv());
            return true;
        } else if (i2 == 3) {
            do {
                zza = zza();
                if (zza == 0 || !zzb(zza)) {
                    zza(((i >>> 3) << 3) | 4);
                }
                zza = zza();
                break;
            } while (!zzb(zza));
            zza(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzj(4);
                return true;
            }
            throw zzir.zzf();
        }
    }

    public final double zzb() throws IOException {
        return Double.longBitsToDouble(zzy());
    }

    public final float zzc() throws IOException {
        return Float.intBitsToFloat(zzx());
    }

    public final long zzd() throws IOException {
        return zzw();
    }

    public final long zze() throws IOException {
        return zzw();
    }

    public final int zzf() throws IOException {
        return zzv();
    }

    public final long zzg() throws IOException {
        return zzy();
    }

    public final int zzh() throws IOException {
        return zzx();
    }

    public final boolean zzi() throws IOException {
        return zzw() != 0;
    }

    public final String zzj() throws IOException {
        int zzv = zzv();
        if (zzv > 0 && zzv <= this.zzg - this.zzi) {
            String str = new String(this.zzf, this.zzi, zzv, zzii.zza);
            this.zzi += zzv;
            return str;
        } else if (zzv == 0) {
            return "";
        } else {
            if (zzv > this.zzg) {
                return new String(zza(zzv, false), zzii.zza);
            }
            zzf(zzv);
            String str2 = new String(this.zzf, this.zzi, zzv, zzii.zza);
            this.zzi += zzv;
            return str2;
        }
    }

    public final String zzk() throws IOException {
        byte[] bArr;
        int zzv = zzv();
        int i = this.zzi;
        if (zzv <= this.zzg - i && zzv > 0) {
            bArr = this.zzf;
            this.zzi = i + zzv;
        } else if (zzv == 0) {
            return "";
        } else {
            if (zzv <= this.zzg) {
                zzf(zzv);
                bArr = this.zzf;
                this.zzi = zzv;
                i = 0;
            } else {
                bArr = zza(zzv, false);
                i = 0;
            }
        }
        return zzli.zzb(bArr, i, zzv);
    }

    public final zzgv zzl() throws IOException {
        int zzv = zzv();
        int i = this.zzg;
        int i2 = this.zzi;
        if (zzv <= i - i2 && zzv > 0) {
            zzgv zza = zzgv.zza(this.zzf, i2, zzv);
            this.zzi += zzv;
            return zza;
        } else if (zzv == 0) {
            return zzgv.zza;
        } else {
            byte[] zzh2 = zzh(zzv);
            if (zzh2 != null) {
                return zzgv.zza(zzh2);
            }
            int i3 = this.zzi;
            int i4 = this.zzg;
            int i5 = i4 - i3;
            this.zzk += i4;
            this.zzi = 0;
            this.zzg = 0;
            List<byte[]> zzi2 = zzi(zzv - i5);
            byte[] bArr = new byte[zzv];
            System.arraycopy(this.zzf, i3, bArr, 0, i5);
            for (byte[] next : zzi2) {
                System.arraycopy(next, 0, bArr, i5, next.length);
                i5 += next.length;
            }
            return zzgv.zzb(bArr);
        }
    }

    public final int zzm() throws IOException {
        return zzv();
    }

    public final int zzn() throws IOException {
        return zzv();
    }

    public final int zzo() throws IOException {
        return zzx();
    }

    public final long zzp() throws IOException {
        return zzy();
    }

    public final int zzq() throws IOException {
        return zze(zzv());
    }

    public final long zzr() throws IOException {
        return zza(zzw());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzv() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.zzi
            int r1 = r5.zzg
            if (r1 == r0) goto L_0x006d
            byte[] r2 = r5.zzf
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0011
            r5.zzi = r3
            return r0
        L_0x0011:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x006d
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0022
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            goto L_0x006a
        L_0x0022:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x002f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            r1 = r3
            goto L_0x006a
        L_0x002f:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x003d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x006a
        L_0x003d:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r2 = r2[r3]
            if (r2 < 0) goto L_0x006d
            goto L_0x006a
        L_0x0069:
            r1 = r3
        L_0x006a:
            r5.zzi = r1
            return r0
        L_0x006d:
            long r0 = r5.zzs()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzhm.zzv():int");
    }

    private final long zzw() throws IOException {
        long j;
        int i = this.zzi;
        int i2 = this.zzg;
        if (i2 != i) {
            byte[] bArr = this.zzf;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzi = i3;
                return (long) b;
            } else if (i2 - i3 >= 9) {
                int i4 = i3 + 1;
                byte b2 = b ^ (bArr[i3] << 7);
                if (b2 < 0) {
                    j = (long) (b2 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                } else {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << Ascii.f323SO);
                    if (b3 >= 0) {
                        i4 = i5;
                        j = (long) (b3 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                    } else {
                        i4 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << Ascii.NAK);
                        if (b4 < 0) {
                            j = (long) (b4 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                        } else {
                            long j2 = (long) b4;
                            int i6 = i4 + 1;
                            long j3 = j2 ^ (((long) bArr[i4]) << 28);
                            if (j3 >= 0) {
                                j = 266354560 ^ j3;
                                i4 = i6;
                            } else {
                                i4 = i6 + 1;
                                long j4 = j3 ^ (((long) bArr[i6]) << 35);
                                if (j4 < 0) {
                                    j = j4 ^ -34093383808L;
                                } else {
                                    int i7 = i4 + 1;
                                    long j5 = j4 ^ (((long) bArr[i4]) << 42);
                                    if (j5 >= 0) {
                                        j = 4363953127296L ^ j5;
                                        i4 = i7;
                                    } else {
                                        i4 = i7 + 1;
                                        long j6 = j5 ^ (((long) bArr[i7]) << 49);
                                        if (j6 < 0) {
                                            j = j6 ^ -558586000294016L;
                                        } else {
                                            int i8 = i4 + 1;
                                            long j7 = (j6 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                i4 = i8 + 1;
                                                if (((long) bArr[i8]) >= 0) {
                                                    j = j7;
                                                }
                                            } else {
                                                i4 = i8;
                                                j = j7;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.zzi = i4;
                return j;
            }
        }
        return zzs();
    }

    /* access modifiers changed from: package-private */
    public final long zzs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzaa = zzaa();
            j |= ((long) (zzaa & Ascii.DEL)) << i;
            if ((zzaa & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return j;
            }
        }
        throw zzir.zzc();
    }

    private final int zzx() throws IOException {
        int i = this.zzi;
        if (this.zzg - i < 4) {
            zzf(4);
            i = this.zzi;
        }
        byte[] bArr = this.zzf;
        this.zzi = i + 4;
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    private final long zzy() throws IOException {
        int i = this.zzi;
        if (this.zzg - i < 8) {
            zzf(8);
            i = this.zzi;
        }
        byte[] bArr = this.zzf;
        this.zzi = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    public final int zzc(int i) throws zzir {
        if (i >= 0) {
            int i2 = i + this.zzk + this.zzi;
            int i3 = this.zzl;
            if (i2 <= i3) {
                this.zzl = i2;
                zzz();
                return i3;
            }
            throw zzir.zza();
        }
        throw zzir.zzb();
    }

    private final void zzz() {
        int i = this.zzg + this.zzh;
        this.zzg = i;
        int i2 = this.zzk + i;
        int i3 = this.zzl;
        if (i2 > i3) {
            int i4 = i2 - i3;
            this.zzh = i4;
            this.zzg = i - i4;
            return;
        }
        this.zzh = 0;
    }

    public final void zzd(int i) {
        this.zzl = i;
        zzz();
    }

    public final boolean zzt() throws IOException {
        return this.zzi == this.zzg && !zzg(1);
    }

    public final int zzu() {
        return this.zzk + this.zzi;
    }

    private final void zzf(int i) throws IOException {
        if (zzg(i)) {
            return;
        }
        if (i > (this.zzc - this.zzk) - this.zzi) {
            throw zzir.zzg();
        }
        throw zzir.zza();
    }

    private final boolean zzg(int i) throws IOException {
        while (this.zzi + i > this.zzg) {
            int i2 = this.zzc;
            int i3 = this.zzk;
            int i4 = this.zzi;
            if (i > (i2 - i3) - i4 || i3 + i4 + i > this.zzl) {
                return false;
            }
            if (i4 > 0) {
                int i5 = this.zzg;
                if (i5 > i4) {
                    byte[] bArr = this.zzf;
                    System.arraycopy(bArr, i4, bArr, 0, i5 - i4);
                }
                this.zzk += i4;
                this.zzg -= i4;
                this.zzi = 0;
            }
            InputStream inputStream = this.zze;
            byte[] bArr2 = this.zzf;
            int i6 = this.zzg;
            int read = inputStream.read(bArr2, i6, Math.min(bArr2.length - i6, (this.zzc - this.zzk) - this.zzg));
            if (read == 0 || read < -1 || read > this.zzf.length) {
                String valueOf = String.valueOf(this.zze.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 91);
                sb.append(valueOf);
                sb.append("#read(byte[]) returned invalid result: ");
                sb.append(read);
                sb.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb.toString());
            } else if (read <= 0) {
                return false;
            } else {
                this.zzg += read;
                zzz();
                if (this.zzg >= i) {
                    return true;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder(77);
        sb2.append("refillBuffer() called when ");
        sb2.append(i);
        sb2.append(" bytes were already available in buffer");
        throw new IllegalStateException(sb2.toString());
    }

    private final byte zzaa() throws IOException {
        if (this.zzi == this.zzg) {
            zzf(1);
        }
        byte[] bArr = this.zzf;
        int i = this.zzi;
        this.zzi = i + 1;
        return bArr[i];
    }

    private final byte[] zza(int i, boolean z) throws IOException {
        byte[] zzh2 = zzh(i);
        if (zzh2 != null) {
            return zzh2;
        }
        int i2 = this.zzi;
        int i3 = this.zzg;
        int i4 = i3 - i2;
        this.zzk += i3;
        this.zzi = 0;
        this.zzg = 0;
        List<byte[]> zzi2 = zzi(i - i4);
        byte[] bArr = new byte[i];
        System.arraycopy(this.zzf, i2, bArr, 0, i4);
        for (byte[] next : zzi2) {
            System.arraycopy(next, 0, bArr, i4, next.length);
            i4 += next.length;
        }
        return bArr;
    }

    private final byte[] zzh(int i) throws IOException {
        if (i == 0) {
            return zzii.zzb;
        }
        if (i >= 0) {
            int i2 = this.zzk + this.zzi + i;
            if (i2 - this.zzc <= 0) {
                int i3 = this.zzl;
                if (i2 <= i3) {
                    int i4 = this.zzg - this.zzi;
                    int i5 = i - i4;
                    if (i5 >= 4096 && i5 > this.zze.available()) {
                        return null;
                    }
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.zzf, this.zzi, bArr, 0, i4);
                    this.zzk += this.zzg;
                    this.zzi = 0;
                    this.zzg = 0;
                    while (i4 < i) {
                        int read = this.zze.read(bArr, i4, i - i4);
                        if (read != -1) {
                            this.zzk += read;
                            i4 += read;
                        } else {
                            throw zzir.zza();
                        }
                    }
                    return bArr;
                }
                zzj((i3 - this.zzk) - this.zzi);
                throw zzir.zza();
            }
            throw zzir.zzg();
        }
        throw zzir.zzb();
    }

    private final List<byte[]> zzi(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            int min = Math.min(i, 4096);
            byte[] bArr = new byte[min];
            int i2 = 0;
            while (i2 < min) {
                int read = this.zze.read(bArr, i2, min - i2);
                if (read != -1) {
                    this.zzk += read;
                    i2 += read;
                } else {
                    throw zzir.zza();
                }
            }
            i -= min;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    private final void zzj(int i) throws IOException {
        int i2 = this.zzg;
        int i3 = this.zzi;
        if (i <= i2 - i3 && i >= 0) {
            this.zzi = i3 + i;
        } else if (i >= 0) {
            int i4 = this.zzk;
            int i5 = this.zzi;
            int i6 = i4 + i5 + i;
            int i7 = this.zzl;
            if (i6 <= i7) {
                this.zzk = i4 + i5;
                int i8 = this.zzg - i5;
                this.zzg = 0;
                this.zzi = 0;
                while (i8 < i) {
                    try {
                        long j = (long) (i - i8);
                        long skip = this.zze.skip(j);
                        if (skip >= 0 && skip <= j) {
                            if (skip == 0) {
                                break;
                            }
                            i8 += (int) skip;
                        } else {
                            String valueOf = String.valueOf(this.zze.getClass());
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 92);
                            sb.append(valueOf);
                            sb.append("#skip returned invalid result: ");
                            sb.append(skip);
                            sb.append("\nThe InputStream implementation is buggy.");
                            throw new IllegalStateException(sb.toString());
                        }
                    } catch (Throwable th) {
                        this.zzk += i8;
                        zzz();
                        throw th;
                    }
                }
                this.zzk += i8;
                zzz();
                if (i8 < i) {
                    int i9 = this.zzg;
                    int i10 = i9 - this.zzi;
                    this.zzi = i9;
                    zzf(1);
                    while (true) {
                        int i11 = i - i10;
                        int i12 = this.zzg;
                        if (i11 > i12) {
                            i10 += i12;
                            this.zzi = i12;
                            zzf(1);
                        } else {
                            this.zzi = i11;
                            return;
                        }
                    }
                }
            } else {
                zzj((i7 - i4) - i5);
                throw zzir.zza();
            }
        } else {
            throw zzir.zzb();
        }
    }
}
