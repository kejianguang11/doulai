package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobilesecuritysdk.face.SecurityClientMobile;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.l;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final String d = "virtualImeiAndImsi";
    private static final String e = "virtual_imei";
    private static final String f = "virtual_imsi";
    private static c g;
    public String a;
    public String b = "sdk-and-lite";
    public String c;

    private c() {
    }

    public static synchronized c a() {
        if (g == null) {
            g = new c();
        }
        return g;
    }

    public static String a(Context context) {
        if (context == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            sb.append("(");
            sb.append(packageName);
            sb.append(h.b);
            sb.append(packageInfo.versionCode);
            sb.append(")");
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(Context context, HashMap<String, String> map) {
        String strGetApdid;
        try {
            strGetApdid = SecurityClientMobile.GetApdid(context, map);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.g, th);
            strGetApdid = "";
        }
        if (TextUtils.isEmpty(strGetApdid)) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.h, "apdid == null");
        }
        return strGetApdid;
    }

    private String a(com.alipay.sdk.tid.b bVar) {
        String strA;
        Context context = com.alipay.sdk.sys.b.a().a;
        com.alipay.sdk.util.a aVarA = com.alipay.sdk.util.a.a(context);
        if (TextUtils.isEmpty(this.a)) {
            this.a = "Msp/15.5.5 (" + l.b() + h.b + l.c() + h.b + l.g(context) + h.b + l.i(context) + h.b + l.h(context) + h.b + Float.toString(new TextView(context).getTextSize());
        }
        String str = com.alipay.sdk.util.a.b(context).p;
        String strD = l.d();
        String strA2 = aVarA.a();
        String strB = aVarA.b();
        Context context2 = com.alipay.sdk.sys.b.a().a;
        SharedPreferences sharedPreferences = context2.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(f, null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                String strC = com.alipay.sdk.sys.b.a().c();
                strA = TextUtils.isEmpty(strC) ? b() : strC.substring(3, 18);
            } else {
                strA = com.alipay.sdk.util.a.a(context2).a();
            }
            string = strA;
            sharedPreferences.edit().putString(f, string).commit();
        }
        Context context3 = com.alipay.sdk.sys.b.a().a;
        SharedPreferences sharedPreferences2 = context3.getSharedPreferences(d, 0);
        String string2 = sharedPreferences2.getString(e, null);
        if (TextUtils.isEmpty(string2)) {
            string2 = TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a) ? b() : com.alipay.sdk.util.a.a(context3).b();
            sharedPreferences2.edit().putString(e, string2).commit();
        }
        if (bVar != null) {
            this.c = bVar.b;
        }
        String strReplace = Build.MANUFACTURER.replace(h.b, " ");
        String strReplace2 = Build.MODEL.replace(h.b, " ");
        boolean zB = com.alipay.sdk.sys.b.b();
        String str2 = aVarA.a;
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
        WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : "00";
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(h.b);
        sb.append(str);
        sb.append(h.b);
        sb.append(strD);
        sb.append(h.b);
        sb.append("1");
        sb.append(h.b);
        sb.append(strA2);
        sb.append(h.b);
        sb.append(strB);
        sb.append(h.b);
        sb.append(this.c);
        sb.append(h.b);
        sb.append(strReplace);
        sb.append(h.b);
        sb.append(strReplace2);
        sb.append(h.b);
        sb.append(zB);
        sb.append(h.b);
        sb.append(str2);
        sb.append(";-1;-1;");
        sb.append(this.b);
        sb.append(h.b);
        sb.append(string);
        sb.append(h.b);
        sb.append(string2);
        sb.append(h.b);
        sb.append(ssid);
        sb.append(h.b);
        sb.append(bssid);
        if (bVar != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put(com.alipay.sdk.cons.b.c, bVar.a);
            map.put(com.alipay.sdk.cons.b.g, com.alipay.sdk.sys.b.a().c());
            String strB2 = b(context, map);
            if (!TextUtils.isEmpty(strB2)) {
                sb.append(h.b);
                sb.append(strB2);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String b() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
    }

    private static String b(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    private String c() {
        return this.c;
    }

    private static String c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getSSID() : "-1";
    }

    private static String d() {
        return "1";
    }

    private static String d(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getBSSID() : "00";
    }

    private static String e() {
        return "-1;-1";
    }

    private static String f() {
        Context context = com.alipay.sdk.sys.b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(e, null);
        if (TextUtils.isEmpty(string)) {
            string = TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a) ? b() : com.alipay.sdk.util.a.a(context).b();
            sharedPreferences.edit().putString(e, string).commit();
        }
        return string;
    }

    private static String g() {
        String strA;
        Context context = com.alipay.sdk.sys.b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(f, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
            String strC = com.alipay.sdk.sys.b.a().c();
            strA = TextUtils.isEmpty(strC) ? b() : strC.substring(3, 18);
        } else {
            strA = com.alipay.sdk.util.a.a(context).a();
        }
        String str = strA;
        sharedPreferences.edit().putString(f, str).commit();
        return str;
    }

    public final synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(com.alipay.sdk.sys.b.a().a).edit().putString(com.alipay.sdk.cons.b.i, str).commit();
        com.alipay.sdk.cons.a.c = str;
    }

    public final String b(Context context, HashMap<String, String> map) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new d(this, context, map)).get(3000L, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.i, th);
            return "";
        }
    }
}
