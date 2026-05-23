package com.alipay.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.alipay.sdk.app.EnvUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
public final class l {
    public static final int b = 99;
    public static final int c = 73;
    public static final String[] d = {"10.1.5.1013151", "10.1.5.1013148"};
    private static final String e = "com.eg.android.AlipayGphone";
    private static final String f = "com.eg.android.AlipayGphoneRC";

    public static class a {
        public Signature[] a;
        public int b;

        public final boolean a() {
            if (this.a == null || this.a.length <= 0) {
                return false;
            }
            for (int i = 0; i < this.a.length; i++) {
                String strA = l.a(this.a[i].toByteArray());
                if (strA != null && !TextUtils.equals(strA, com.alipay.sdk.cons.a.h)) {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.t, strA);
                    return true;
                }
            }
            return false;
        }
    }

    public static WebView a(Activity activity, String str, String str2) {
        Context applicationContext = activity.getApplicationContext();
        if (!TextUtils.isEmpty(str2)) {
            CookieSyncManager.createInstance(applicationContext).sync();
            CookieManager.getInstance().setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        }
        LinearLayout linearLayout = new LinearLayout(applicationContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.setOrientation(1);
        activity.setContentView(linearLayout, layoutParams);
        WebView webView = new WebView(applicationContext);
        layoutParams.weight = 1.0f;
        webView.setVisibility(0);
        linearLayout.addView(webView, layoutParams);
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + f(applicationContext));
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webView.setVerticalScrollbarOverlay(true);
        webView.setDownloadListener(new m(applicationContext));
        if (Build.VERSION.SDK_INT >= 7) {
            try {
                Method method = webView.getSettings().getClass().getMethod("setDomStorageEnabled", Boolean.TYPE);
                if (method != null) {
                    method.invoke(webView.getSettings(), true);
                }
            } catch (Exception unused) {
            }
        }
        try {
            try {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            } catch (Throwable unused2) {
            }
        } catch (Throwable unused3) {
            Method method2 = webView.getClass().getMethod("removeJavascriptInterface", new Class[0]);
            if (method2 != null) {
                method2.invoke(webView, "searchBoxJavaBridge_");
                method2.invoke(webView, "accessibility");
                method2.invoke(webView, "accessibilityTraversal");
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(2);
        }
        webView.loadUrl(str);
        return webView;
    }

    public static a a(Context context) {
        return a(context, a());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v6, types: [android.content.pm.PackageInfo] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v4, types: [boolean] */
    private static a a(Context context, String str) {
        try {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo((String) str, 192);
                if (a(packageInfo)) {
                    context = packageInfo;
                } else {
                    try {
                        context = c(context, str);
                    } catch (Throwable th) {
                        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.m, th);
                        context = packageInfo;
                    }
                }
            } catch (Throwable th2) {
                if (!a((PackageInfo) null)) {
                    try {
                        c(context, str);
                    } catch (Throwable th3) {
                        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.m, th3);
                    }
                }
                throw th2;
            }
        } catch (Throwable th4) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.k, th4);
            if (a((PackageInfo) null)) {
                context = 0;
            } else {
                try {
                    context = c(context, str);
                } catch (Throwable th5) {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.m, th5);
                    context = 0;
                }
            }
        }
        str = a((PackageInfo) context);
        if (str == 0 || context == 0) {
            return null;
        }
        a aVar = new a();
        aVar.a = ((PackageInfo) context).signatures;
        aVar.b = ((PackageInfo) context).versionCode;
        return aVar;
    }

    public static String a() {
        return EnvUtils.isSandBox() ? f : e;
    }

    public static String a(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            if (iIndexOf <= str.length()) {
                return "";
            }
            int iIndexOf2 = TextUtils.isEmpty(str2) ? 0 : str3.indexOf(str2, iIndexOf);
            return iIndexOf2 <= 0 ? str3.substring(iIndexOf) : str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        try {
            String string = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey().toString();
            if (string.indexOf("modulus") != -1) {
                return string.substring(string.indexOf("modulus") + 8, string.lastIndexOf(com.igexin.push.core.b.an)).trim();
            }
            return null;
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.n, e2);
            return null;
        }
    }

    public static Map<String, String> a(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split(com.alipay.sdk.sys.a.b)) {
            int iIndexOf = str2.indexOf("=", 1);
            map.put(str2.substring(0, iIndexOf), URLDecoder.decode(str2.substring(iIndexOf + 1)));
        }
        return map;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean a(PackageInfo packageInfo) {
        StringBuilder sb;
        String str;
        String string = "";
        boolean z = false;
        if (packageInfo == null) {
            sb = new StringBuilder();
            sb.append("");
            str = "info == null";
        } else if (packageInfo.signatures == null) {
            sb = new StringBuilder();
            sb.append("");
            str = "info.signatures == null";
        } else {
            if (packageInfo.signatures.length > 0) {
                z = true;
                if (!z) {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.d, com.alipay.sdk.app.statistic.c.l, string);
                }
                return z;
            }
            sb = new StringBuilder();
            sb.append("");
            str = "info.signatures.length <= 0";
        }
        sb.append(str);
        string = sb.toString();
        if (!z) {
        }
        return z;
    }

    public static boolean a(WebView webView, String str, Activity activity) {
        String strSubstring;
        String strA;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (str.toLowerCase().startsWith(com.alipay.sdk.cons.a.i.toLowerCase()) || str.toLowerCase().startsWith(com.alipay.sdk.cons.a.j.toLowerCase())) {
            try {
                a aVarA = a(activity);
                if (aVarA != null && !aVarA.a()) {
                    if (str.startsWith("intent://platformapi/startapp")) {
                        str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.cons.a.i);
                    }
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            } catch (Throwable unused) {
            }
            return true;
        }
        if (TextUtils.equals(str, com.alipay.sdk.cons.a.l) || TextUtils.equals(str, com.alipay.sdk.cons.a.m)) {
            com.alipay.sdk.app.i.a = com.alipay.sdk.app.i.a();
            activity.finish();
            return true;
        }
        if (!str.startsWith(com.alipay.sdk.cons.a.k)) {
            webView.loadUrl(str);
            return true;
        }
        try {
            String strSubstring2 = str.substring(str.indexOf(com.alipay.sdk.cons.a.k) + 24);
            int i = Integer.parseInt(strSubstring2.substring(strSubstring2.lastIndexOf(com.alipay.sdk.cons.a.n) + 10));
            if (i == com.alipay.sdk.app.j.SUCCEEDED.h || i == com.alipay.sdk.app.j.PAY_WAITTING.h) {
                if (com.alipay.sdk.cons.a.r) {
                    StringBuilder sb = new StringBuilder();
                    String strDecode = URLDecoder.decode(str);
                    String strDecode2 = URLDecoder.decode(strDecode);
                    String str2 = strDecode2.substring(strDecode2.indexOf(com.alipay.sdk.cons.a.k) + 24, strDecode2.lastIndexOf(com.alipay.sdk.cons.a.n)).split(com.alipay.sdk.cons.a.p)[0];
                    int iIndexOf = strDecode.indexOf(com.alipay.sdk.cons.a.p) + 12;
                    sb.append(str2);
                    sb.append(com.alipay.sdk.cons.a.p);
                    sb.append(strDecode.substring(iIndexOf, strDecode.indexOf(com.alipay.sdk.sys.a.b, iIndexOf)));
                    sb.append(strDecode.substring(strDecode.indexOf(com.alipay.sdk.sys.a.b, iIndexOf)));
                    strSubstring = sb.toString();
                } else {
                    String strDecode3 = URLDecoder.decode(str);
                    strSubstring = strDecode3.substring(strDecode3.indexOf(com.alipay.sdk.cons.a.k) + 24, strDecode3.lastIndexOf(com.alipay.sdk.cons.a.n));
                }
                com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(i);
                strA = com.alipay.sdk.app.i.a(jVarA.h, jVarA.i, strSubstring);
            } else {
                com.alipay.sdk.app.j jVarA2 = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.FAILED.h);
                strA = com.alipay.sdk.app.i.a(jVarA2.h, jVarA2.i, "");
            }
            com.alipay.sdk.app.i.a = strA;
        } catch (Exception unused2) {
            com.alipay.sdk.app.j jVarA3 = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.PARAMS_ERROR.h);
            com.alipay.sdk.app.i.a = com.alipay.sdk.app.i.a(jVarA3.h, jVarA3.i, "");
        }
        activity.runOnUiThread(new n(activity));
        return true;
    }

    private static PackageInfo b(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    private static a b(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        a aVar = new a();
        aVar.a = packageInfo.signatures;
        aVar.b = packageInfo.versionCode;
        return aVar;
    }

    public static String b() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String b(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            int iIndexOf2 = TextUtils.isEmpty(str2) ? 0 : str3.indexOf(str2, iIndexOf);
            return iIndexOf2 <= 0 ? str3.substring(iIndexOf) : str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static boolean b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.alipay.android.app", 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean b(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    private static PackageInfo c(Context context, String str) {
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(192)) {
            if (packageInfo.packageName.equals(str)) {
                return packageInfo;
            }
        }
        return null;
    }

    public static String c() {
        String strF = f();
        int iIndexOf = strF.indexOf("-");
        if (iIndexOf != -1) {
            strF = strF.substring(0, iIndexOf);
        }
        int iIndexOf2 = strF.indexOf("\n");
        if (iIndexOf2 != -1) {
            strF = strF.substring(0, iIndexOf2);
        }
        return "Linux " + strF;
    }

    public static boolean c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode > 73;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.B, th);
            return false;
        }
    }

    @SuppressLint({"InlinedApi"})
    private static boolean c(PackageInfo packageInfo) {
        int i = packageInfo.applicationInfo.flags;
        return (i & 1) == 0 && (i & 128) == 0;
    }

    public static String d() {
        return "-1;-1";
    }

    public static boolean d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            String str = packageInfo.versionName;
            if (!TextUtils.equals(str, d[0])) {
                if (!TextUtils.equals(str, d[1])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String e() {
        double dRandom;
        double d2;
        String strValueOf;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    dRandom = Math.random() * 25.0d;
                    d2 = 65.0d;
                    strValueOf = String.valueOf((char) Math.round(dRandom + d2));
                    sb.append(strValueOf);
                    break;
                case 1:
                    dRandom = Math.random() * 25.0d;
                    d2 = 97.0d;
                    strValueOf = String.valueOf((char) Math.round(dRandom + d2));
                    sb.append(strValueOf);
                    break;
                case 2:
                    strValueOf = String.valueOf(new Random().nextInt(10));
                    sb.append(strValueOf);
                    break;
            }
        }
        return sb.toString();
    }

    public static boolean e(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            if (packageInfo.versionCode < 99) {
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private static String f() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(line);
                if (!matcher.matches() || matcher.groupCount() < 4) {
                    return "Unavailable";
                }
                return matcher.group(1) + "\n" + matcher.group(2) + " " + matcher.group(3) + "\n" + matcher.group(4);
            } catch (Throwable th) {
                bufferedReader.close();
                throw th;
            }
        } catch (IOException unused) {
            return "Unavailable";
        }
    }

    public static String f(Context context) {
        return " (" + b() + h.b + c() + h.b + g(context) + ";;" + h(context) + ")(sdk android)";
    }

    public static String g(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String h(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
    }

    public static String i(Context context) {
        String strA = k.a(context);
        return strA.substring(0, strA.indexOf("://"));
    }

    public static String j(Context context) {
        String strSubstring = "";
        try {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(a())) {
                    strSubstring = strSubstring + "#M";
                } else {
                    if (runningAppProcessInfo.processName.startsWith(a() + ":")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(strSubstring);
                        sb.append("#");
                        sb.append(runningAppProcessInfo.processName.replace(a() + ":", ""));
                        strSubstring = sb.toString();
                    }
                }
            }
        } catch (Throwable unused) {
            strSubstring = "";
        }
        if (strSubstring.length() > 0) {
            strSubstring = strSubstring.substring(1);
        }
        return strSubstring.length() == 0 ? "N" : strSubstring;
    }

    public static String k(Context context) {
        String str;
        try {
            List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                int i2 = packageInfo.applicationInfo.flags;
                if ((i2 & 1) == 0 && (i2 & 128) == 0) {
                    if (packageInfo.packageName.equals(a())) {
                        sb.append(packageInfo.packageName);
                        sb.append(packageInfo.versionCode);
                        str = "-";
                    } else if (!packageInfo.packageName.contains("theme") && !packageInfo.packageName.startsWith("com.google.") && !packageInfo.packageName.startsWith("com.android.")) {
                        sb.append(packageInfo.packageName);
                        str = "-";
                    }
                    sb.append(str);
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, "GetInstalledAppEx", th);
            return "";
        }
    }

    private static DisplayMetrics l(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
