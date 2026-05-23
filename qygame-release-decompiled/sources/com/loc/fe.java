package com.loc;

import android.content.Context;
import com.loc.bl;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class fe {
    private static fe b;
    bg a;
    private Context c;
    private int d = fj.i;
    private boolean e = false;
    private int f = 0;

    private fe(Context context) {
        this.a = null;
        this.c = null;
        try {
            q.a().a(context);
        } catch (Throwable unused) {
        }
        this.c = context;
        this.a = bg.a();
    }

    public static fe a(Context context) {
        if (b == null) {
            b = new fe(context);
        }
        return b;
    }

    public final bm a(ff ffVar) throws Throwable {
        if (this.e) {
            ffVar.a(bl.c.HTTPS);
        }
        return bg.a(ffVar);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final ff a(Context context, byte[] bArr, String str, String str2, boolean z) {
        String str3;
        String str4;
        try {
            HashMap map = new HashMap(16);
            ff ffVar = new ff(context, fj.c());
            try {
                map.put("Content-Type", "application/octet-stream");
                map.put("Accept-Encoding", "gzip");
                map.put("gzipped", "1");
                map.put("Connection", "Keep-Alive");
                map.put("User-Agent", "AMAP_Location_SDK_Android 6.1.0");
                map.put("KEY", l.f(context));
                map.put("enginever", fj.a);
                String strA = n.a();
                String strA2 = n.a(context, strA, "key=" + l.f(context));
                map.put("ts", strA);
                map.put("scode", strA2);
                if (Double.valueOf(fj.a).doubleValue() >= 5.3d) {
                    map.put("aps_s_src", "openapi");
                }
                map.put("encr", "1");
                ffVar.b(map);
                String str5 = z ? "loc" : "locf";
                ffVar.c(true);
                ffVar.b(String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s&loc_channel=%s", "6.1.0", str5, 3));
                ffVar.b(z);
                ffVar.c(str);
                ffVar.d(str2);
                ffVar.c(fq.a(bArr));
                ffVar.a(v.a(context));
                HashMap map2 = new HashMap(16);
                map2.put("output", "bin");
                map2.put("policy", "3103");
                switch (this.f) {
                    case 0:
                        map2.remove("custom");
                        break;
                    case 1:
                        str3 = "custom";
                        str4 = "language:cn";
                        map2.put(str3, str4);
                        break;
                    case 2:
                        str3 = "custom";
                        str4 = "language:en";
                        map2.put(str3, str4);
                        break;
                    default:
                        map2.remove("custom");
                        break;
                }
                ffVar.a((Map<String, String>) map2);
                ffVar.a(this.d);
                ffVar.b(this.d);
                if (!this.e) {
                    return ffVar;
                }
                ffVar.a(bl.c.HTTPS);
                return ffVar;
            } catch (Throwable unused) {
                return ffVar;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final String a(Context context, double d, double d2) {
        String str;
        String str2;
        try {
            HashMap map = new HashMap(16);
            ff ffVar = new ff(context, fj.c());
            map.clear();
            map.put("Content-Type", "application/x-www-form-urlencoded");
            map.put("Connection", "Keep-Alive");
            map.put("User-Agent", "AMAP_Location_SDK_Android 6.1.0");
            HashMap map2 = new HashMap(16);
            map2.put("custom", "26260A1F00020002");
            map2.put("key", l.f(context));
            switch (this.f) {
                case 0:
                    map2.remove("language");
                    break;
                case 1:
                    str = "language";
                    str2 = "zh-CN";
                    map2.put(str, str2);
                    break;
                case 2:
                    str = "language";
                    str2 = "en";
                    map2.put(str, str2);
                    break;
                default:
                    map2.remove("language");
                    break;
            }
            map2.put("curLocationType", fq.m(this.c) ? "coarseLoc" : "fineLoc");
            String strA = n.a();
            String strA2 = n.a(context, strA, x.b(map2));
            map2.put("ts", strA);
            map2.put("scode", strA2);
            ffVar.b(("output=json&radius=1000&extensions=all&location=" + d2 + com.igexin.push.core.b.an + d).getBytes(com.alipay.sdk.sys.a.m));
            ffVar.c(false);
            ffVar.b(true);
            ffVar.b(String.format(Locale.US, "platform=Android&sdkversion=%s&product=%s&loc_channel=%s", "6.1.0", "loc", 3));
            ffVar.a((Map<String, String>) map2);
            ffVar.b(map);
            ffVar.a(v.a(context));
            ffVar.a(fj.i);
            ffVar.b(fj.i);
            try {
                ffVar.d("http://dualstack-arestapi.amap.com/v3/geocode/regeo");
                ffVar.c("http://restsdk.amap.com/v3/geocode/regeo");
                if (this.e) {
                    ffVar.a(bl.c.HTTPS);
                }
                return new String(bg.a(ffVar).a, com.igexin.push.g.s.b);
            } catch (Throwable th) {
                fj.a(th, "LocNetManager", "post");
                return null;
            }
        } catch (Throwable unused) {
        }
    }

    public final void a(long j, boolean z, int i) {
        try {
            this.e = z;
            this.d = Long.valueOf(j).intValue();
            this.f = i;
        } catch (Throwable th) {
            fj.a(th, "LocNetManager", "setOption");
        }
    }
}
