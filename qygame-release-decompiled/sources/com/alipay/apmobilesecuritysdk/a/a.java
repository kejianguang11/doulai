package com.alipay.apmobilesecuritysdk.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.alipay.apmobilesecuritysdk.d.e;
import com.alipay.apmobilesecuritysdk.e.b;
import com.alipay.apmobilesecuritysdk.e.g;
import com.alipay.apmobilesecuritysdk.e.h;
import com.alipay.apmobilesecuritysdk.e.i;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.security.mobile.module.http.model.c;
import com.alipay.security.mobile.module.http.model.d;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private Context a;
    private com.alipay.apmobilesecuritysdk.b.a b = com.alipay.apmobilesecuritysdk.b.a.a();
    private int c = 4;

    public a(Context context) {
        this.a = context;
    }

    public static String a(Context context) {
        String strB = b(context);
        return com.alipay.security.mobile.module.a.a.a(strB) ? h.f(context) : strB;
    }

    public static String a(Context context, String str) {
        try {
            b();
            String strA = i.a(str);
            if (!com.alipay.security.mobile.module.a.a.a(strA)) {
                return strA;
            }
            String strA2 = g.a(context, str);
            i.a(str, strA2);
            return !com.alipay.security.mobile.module.a.a.a(strA2) ? strA2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static boolean a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] strArr = {"2017-01-27 2017-01-28", "2017-11-10 2017-11-11", "2017-12-11 2017-12-12"};
        int iRandom = ((int) (Math.random() * 24.0d * 60.0d * 60.0d)) * 1;
        for (int i = 0; i < 3; i++) {
            try {
                String[] strArrSplit = strArr[i].split(" ");
                if (strArrSplit != null && strArrSplit.length == 2) {
                    Date date = new Date();
                    Date date2 = simpleDateFormat.parse(strArrSplit[0] + " 00:00:00");
                    Date date3 = simpleDateFormat.parse(strArrSplit[1] + " 23:59:59");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date3);
                    calendar.add(13, iRandom);
                    Date time = calendar.getTime();
                    if (date.after(date2) && date.before(time)) {
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private c b(Map<String, String> map) {
        b bVarB;
        b bVarC;
        try {
            Context context = this.a;
            d dVar = new d();
            String strA = com.alipay.security.mobile.module.a.a.a(map, "appName", "");
            String strA2 = com.alipay.security.mobile.module.a.a.a(map, "sessionId", "");
            String strA3 = com.alipay.security.mobile.module.a.a.a(map, "rpcVersion", "");
            String strA4 = a(context, strA);
            String securityToken = UmidSdkWrapper.getSecurityToken(context);
            String strD = h.d(context);
            if (com.alipay.security.mobile.module.a.a.b(strA2)) {
                dVar.c = strA2;
            } else {
                dVar.c = strA4;
            }
            dVar.d = securityToken;
            dVar.e = strD;
            dVar.a = "android";
            String strA5 = "";
            String strA6 = "";
            String strC = "";
            String strC2 = "";
            com.alipay.apmobilesecuritysdk.e.c cVarC = com.alipay.apmobilesecuritysdk.e.d.c(context);
            if (cVarC != null) {
                strA6 = cVarC.a();
                strC = cVarC.c();
            }
            if (com.alipay.security.mobile.module.a.a.a(strA6) && (bVarC = com.alipay.apmobilesecuritysdk.e.a.c(context)) != null) {
                strA6 = bVarC.a();
                strC = bVarC.c();
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b();
            if (cVarB != null) {
                strA5 = cVarB.a();
                strC2 = cVarB.c();
            }
            if (com.alipay.security.mobile.module.a.a.a(strA5) && (bVarB = com.alipay.apmobilesecuritysdk.e.a.b()) != null) {
                strA5 = bVarB.a();
                strC2 = bVarB.c();
            }
            dVar.h = strA6;
            dVar.g = strA5;
            dVar.j = strA3;
            if (com.alipay.security.mobile.module.a.a.a(strA6)) {
                dVar.b = strA5;
                dVar.i = strC2;
            } else {
                dVar.b = strA6;
                dVar.i = strC;
            }
            dVar.f = e.a(context, map);
            return com.alipay.security.mobile.module.http.d.a(this.a, this.b.c()).a(dVar);
        } catch (Throwable th) {
            com.alipay.apmobilesecuritysdk.c.a.a(th);
            return null;
        }
    }

    private static String b(Context context) {
        try {
            String strB = i.b();
            if (!com.alipay.security.mobile.module.a.a.a(strB)) {
                return strB;
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b(context);
            if (cVarB != null) {
                i.a(cVarB);
                String strA = cVarB.a();
                if (com.alipay.security.mobile.module.a.a.b(strA)) {
                    return strA;
                }
            }
            b bVarB = com.alipay.apmobilesecuritysdk.e.a.b(context);
            if (bVarB == null) {
                return "";
            }
            i.a(bVarB);
            String strA2 = bVarB.a();
            return com.alipay.security.mobile.module.a.a.b(strA2) ? strA2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static void b() {
        try {
            String[] strArr = {"device_feature_file_name", "wallet_times", "wxcasxx_v3", "wxcasxx_v4", "wxxzyy_v1"};
            for (int i = 0; i < 5; i++) {
                String str = strArr[i];
                File file = new File(Environment.getExternalStorageDirectory(), ".SystemConfig/" + str);
                if (file.exists() && file.canWrite()) {
                    file.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0231 A[Catch: Exception -> 0x0278, TryCatch #0 {Exception -> 0x0278, blocks: (B:2:0x0000, B:4:0x003b, B:7:0x0045, B:36:0x00cb, B:76:0x0216, B:78:0x0231, B:80:0x0237, B:82:0x023d, B:86:0x0246, B:88:0x024c, B:39:0x00e0, B:41:0x00fa, B:43:0x00fe, B:46:0x0108, B:52:0x0119, B:53:0x0129, B:55:0x0130, B:59:0x0142, B:63:0x015a, B:65:0x019f, B:67:0x01a9, B:69:0x01b1, B:71:0x01c2, B:73:0x01cc, B:75:0x01d4, B:74:0x01d0, B:68:0x01ad, B:62:0x0158, B:10:0x005a, B:12:0x0070, B:15:0x007b, B:17:0x0081, B:20:0x008c, B:23:0x0095, B:26:0x00a2, B:29:0x00af, B:32:0x00bd), top: B:94:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0237 A[Catch: Exception -> 0x0278, TryCatch #0 {Exception -> 0x0278, blocks: (B:2:0x0000, B:4:0x003b, B:7:0x0045, B:36:0x00cb, B:76:0x0216, B:78:0x0231, B:80:0x0237, B:82:0x023d, B:86:0x0246, B:88:0x024c, B:39:0x00e0, B:41:0x00fa, B:43:0x00fe, B:46:0x0108, B:52:0x0119, B:53:0x0129, B:55:0x0130, B:59:0x0142, B:63:0x015a, B:65:0x019f, B:67:0x01a9, B:69:0x01b1, B:71:0x01c2, B:73:0x01cc, B:75:0x01d4, B:74:0x01d0, B:68:0x01ad, B:62:0x0158, B:10:0x005a, B:12:0x0070, B:15:0x007b, B:17:0x0081, B:20:0x008c, B:23:0x0095, B:26:0x00a2, B:29:0x00af, B:32:0x00bd), top: B:94:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0246 A[Catch: Exception -> 0x0278, TryCatch #0 {Exception -> 0x0278, blocks: (B:2:0x0000, B:4:0x003b, B:7:0x0045, B:36:0x00cb, B:76:0x0216, B:78:0x0231, B:80:0x0237, B:82:0x023d, B:86:0x0246, B:88:0x024c, B:39:0x00e0, B:41:0x00fa, B:43:0x00fe, B:46:0x0108, B:52:0x0119, B:53:0x0129, B:55:0x0130, B:59:0x0142, B:63:0x015a, B:65:0x019f, B:67:0x01a9, B:69:0x01b1, B:71:0x01c2, B:73:0x01cc, B:75:0x01d4, B:74:0x01d0, B:68:0x01ad, B:62:0x0158, B:10:0x005a, B:12:0x0070, B:15:0x007b, B:17:0x0081, B:20:0x008c, B:23:0x0095, B:26:0x00a2, B:29:0x00af, B:32:0x00bd), top: B:94:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int a(Map<String, String> map) {
        boolean z;
        int i;
        String str;
        com.alipay.security.mobile.module.http.v2.a aVarA;
        Context context;
        NetworkInfo activeNetworkInfo;
        try {
            com.alipay.apmobilesecuritysdk.c.a.a(this.a, com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.c, ""), com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.g, ""), a(this.a));
            String strA = com.alipay.security.mobile.module.a.a.a(map, "appName", "");
            b();
            b(this.a);
            a(this.a, strA);
            i.a();
            boolean z2 = false;
            if (a() || com.alipay.apmobilesecuritysdk.common.a.a(this.a)) {
                z = com.alipay.security.mobile.module.a.a.a(a(this.a, strA)) || com.alipay.security.mobile.module.a.a.a(b(this.a));
            } else {
                e.a();
                if (!(!com.alipay.security.mobile.module.a.a.a(e.b(this.a, map), i.c()))) {
                    String strA2 = com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.c, "");
                    String strA3 = com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.g, "");
                    if ((com.alipay.security.mobile.module.a.a.b(strA2) && !com.alipay.security.mobile.module.a.a.a(strA2, i.d())) || ((com.alipay.security.mobile.module.a.a.b(strA3) && !com.alipay.security.mobile.module.a.a.a(strA3, i.e())) || !i.a(this.a, strA) || com.alipay.security.mobile.module.a.a.a(a(this.a, strA)) || com.alipay.security.mobile.module.a.a.a(b(this.a)))) {
                    }
                }
            }
            Context context2 = this.a;
            com.alipay.security.mobile.module.b.b.a();
            h.b(context2, String.valueOf(com.alipay.security.mobile.module.b.b.p()));
            if (z) {
                new com.alipay.apmobilesecuritysdk.c.b();
                UmidSdkWrapper.startUmidTaskSync(this.a, com.alipay.apmobilesecuritysdk.b.a.a().b());
                c cVarB = b(map);
                char c = 2;
                if (cVarB != null) {
                    if (cVarB.a) {
                        if (!com.alipay.security.mobile.module.a.a.a(cVarB.h)) {
                            c = 1;
                        }
                    } else if (c.f.equals(cVarB.b)) {
                        c = 3;
                    }
                }
                if (c != 1) {
                    if (c != 3) {
                        if (cVarB != null) {
                            str = "Server error, result:" + cVarB.b;
                        } else {
                            str = "Server error, returned null";
                        }
                        com.alipay.apmobilesecuritysdk.c.a.a(str);
                        if (com.alipay.security.mobile.module.a.a.a(a(this.a, strA))) {
                            i = 4;
                        }
                    } else {
                        i = 1;
                    }
                    this.c = i;
                    aVarA = com.alipay.security.mobile.module.http.d.a(this.a, this.b.c());
                    context = this.a;
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                    if (connectivityManager != null) {
                    }
                    if (activeNetworkInfo != null) {
                        z2 = true;
                    }
                    if (z2) {
                        new Thread(new com.alipay.security.mobile.module.d.c(new com.alipay.security.mobile.module.d.b(context.getFilesDir().getAbsolutePath() + "/log/ap", aVarA))).start();
                    }
                } else {
                    h.a(this.a, "1".equals(cVarB.j));
                    h.d(this.a, cVarB.k == null ? "0" : cVarB.k);
                    h.e(this.a, cVarB.l);
                    h.a(this.a, cVarB.m);
                    h.f(this.a, cVarB.n);
                    h.g(this.a, cVarB.p);
                    i.c(e.b(this.a, map));
                    i.a(strA, cVarB.i);
                    i.b(cVarB.h);
                    i.d(cVarB.o);
                    String strA4 = com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.c, "");
                    if (!com.alipay.security.mobile.module.a.a.b(strA4) || com.alipay.security.mobile.module.a.a.a(strA4, i.d())) {
                        strA4 = i.d();
                    } else {
                        i.e(strA4);
                    }
                    i.e(strA4);
                    String strA5 = com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.g, "");
                    if (!com.alipay.security.mobile.module.a.a.b(strA5) || com.alipay.security.mobile.module.a.a.a(strA5, i.e())) {
                        strA5 = i.e();
                    } else {
                        i.f(strA5);
                    }
                    i.f(strA5);
                    i.a();
                    com.alipay.apmobilesecuritysdk.e.d.a(this.a, i.g());
                    com.alipay.apmobilesecuritysdk.e.d.a();
                    com.alipay.apmobilesecuritysdk.e.a.a(this.a, new b(i.b(), i.c(), i.f()));
                    com.alipay.apmobilesecuritysdk.e.a.a();
                    g.a(this.a, strA, i.a(strA));
                    g.a();
                    h.a(this.a, strA, System.currentTimeMillis());
                }
                i = 0;
                this.c = i;
                aVarA = com.alipay.security.mobile.module.http.d.a(this.a, this.b.c());
                context = this.a;
                ConnectivityManager connectivityManager2 = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager2 != null) {
                }
                if (activeNetworkInfo != null) {
                }
                if (z2) {
                }
            } else {
                i = 0;
                this.c = i;
                aVarA = com.alipay.security.mobile.module.http.d.a(this.a, this.b.c());
                context = this.a;
                ConnectivityManager connectivityManager22 = (ConnectivityManager) context.getSystemService("connectivity");
                activeNetworkInfo = connectivityManager22 != null ? connectivityManager22.getActiveNetworkInfo() : null;
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1) {
                    z2 = true;
                }
                if (z2 && h.c(context)) {
                    new Thread(new com.alipay.security.mobile.module.d.c(new com.alipay.security.mobile.module.d.b(context.getFilesDir().getAbsolutePath() + "/log/ap", aVarA))).start();
                }
            }
        } catch (Exception e) {
            com.alipay.apmobilesecuritysdk.c.a.a(e);
        }
        return this.c;
    }
}
