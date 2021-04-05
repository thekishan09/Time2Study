package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzig;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* compiled from: com.google.firebase:firebase-auth@@19.3.2 */
final class zzjs {
    static String zza(zzjr zzjr, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zza(zzjr, sb, 0);
        return sb.toString();
    }

    private static void zza(zzjr zzjr, StringBuilder sb, int i) {
        boolean z;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet<>();
        for (Method method : zzjr.getClass().getDeclaredMethods()) {
            hashMap2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                hashMap.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String substring = str.startsWith("get") ? str.substring(3) : str;
            boolean z2 = true;
            if (substring.endsWith("List") && !substring.endsWith("OrBuilderList") && !substring.equals("List")) {
                String valueOf = String.valueOf(substring.substring(0, 1).toLowerCase());
                String valueOf2 = String.valueOf(substring.substring(1, substring.length() - 4));
                String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                Method method2 = (Method) hashMap.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zza(sb, i, zza(concat), zzig.zza(method2, (Object) zzjr, new Object[0]));
                }
            }
            if (substring.endsWith("Map") && !substring.equals("Map")) {
                String valueOf3 = String.valueOf(substring.substring(0, 1).toLowerCase());
                String valueOf4 = String.valueOf(substring.substring(1, substring.length() - 3));
                String concat2 = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
                Method method3 = (Method) hashMap.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    zza(sb, i, zza(concat2), zzig.zza(method3, (Object) zzjr, new Object[0]));
                }
            }
            String valueOf5 = String.valueOf(substring);
            if (((Method) hashMap2.get(valueOf5.length() != 0 ? "set".concat(valueOf5) : new String("set"))) != null) {
                if (substring.endsWith("Bytes")) {
                    String valueOf6 = String.valueOf(substring.substring(0, substring.length() - 5));
                    if (hashMap.containsKey(valueOf6.length() != 0 ? "get".concat(valueOf6) : new String("get"))) {
                    }
                }
                String valueOf7 = String.valueOf(substring.substring(0, 1).toLowerCase());
                String valueOf8 = String.valueOf(substring.substring(1));
                String concat3 = valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7);
                String valueOf9 = String.valueOf(substring);
                Method method4 = (Method) hashMap.get(valueOf9.length() != 0 ? "get".concat(valueOf9) : new String("get"));
                String valueOf10 = String.valueOf(substring);
                Method method5 = (Method) hashMap.get(valueOf10.length() != 0 ? "has".concat(valueOf10) : new String("has"));
                if (method4 != null) {
                    Object zza = zzig.zza(method4, (Object) zzjr, new Object[0]);
                    if (method5 == null) {
                        if (zza instanceof Boolean) {
                            z = !((Boolean) zza).booleanValue();
                        } else if (zza instanceof Integer) {
                            z = ((Integer) zza).intValue() == 0;
                        } else if (zza instanceof Float) {
                            z = ((Float) zza).floatValue() == 0.0f;
                        } else if (zza instanceof Double) {
                            z = ((Double) zza).doubleValue() == 0.0d;
                        } else if (zza instanceof String) {
                            z = zza.equals("");
                        } else if (zza instanceof zzgv) {
                            z = zza.equals(zzgv.zza);
                        } else if (zza instanceof zzjr) {
                            z = zza == ((zzjr) zza).zzag();
                        } else if (zza instanceof Enum) {
                            z = ((Enum) zza).ordinal() == 0;
                        } else {
                            z = false;
                        }
                        if (z) {
                            z2 = false;
                        }
                    } else {
                        z2 = ((Boolean) zzig.zza(method5, (Object) zzjr, new Object[0])).booleanValue();
                    }
                    if (z2) {
                        zza(sb, i, zza(concat3), zza);
                    }
                }
            }
        }
        if (zzjr instanceof zzig.zzd) {
            Iterator<Map.Entry<zzig.zzc, Object>> zzd = ((zzig.zzd) zzjr).zzc.zzd();
            if (zzd.hasNext()) {
                zzig.zzc zzc = (zzig.zzc) zzd.next().getKey();
                throw new NoSuchMethodError();
            }
        }
        zzig zzig = (zzig) zzjr;
        if (zzig.zzb != null) {
            zzig.zzb.zza(sb, i);
        }
    }

    static final void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object zza : (List) obj) {
                zza(sb, i, str, zza);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry zza2 : ((Map) obj).entrySet()) {
                zza(sb, i, str, zza2);
            }
        } else {
            sb.append(10);
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(' ');
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                sb.append(zzkv.zza(zzgv.zza((String) obj)));
                sb.append('\"');
            } else if (obj instanceof zzgv) {
                sb.append(": \"");
                sb.append(zzkv.zza((zzgv) obj));
                sb.append('\"');
            } else if (obj instanceof zzig) {
                sb.append(" {");
                zza((zzig) obj, sb, i + 2);
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry = (Map.Entry) obj;
                int i4 = i + 2;
                zza(sb, i4, "key", entry.getKey());
                zza(sb, i4, "value", entry.getValue());
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj.toString());
            }
        }
    }

    private static final String zza(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(charAt));
        }
        return sb.toString();
    }
}
