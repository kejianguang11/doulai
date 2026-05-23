package com.alipay.sdk.app.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static final String A = "BindWaitTimeoutEx";
    public static final String B = "CheckClientExistEx";
    public static final String C = "CheckClientSignEx";
    public static final String D = "GetInstalledAppEx";
    public static final String E = "GetInstalledAppEx";
    public static final String F = "partner";
    public static final String G = "out_trade_no";
    public static final String H = "trade_no";
    public static final String a = "net";
    public static final String b = "biz";
    public static final String c = "cp";
    public static final String d = "auth";
    public static final String e = "third";
    public static final String f = "FormatResultEx";
    public static final String g = "GetApdidEx";
    public static final String h = "GetApdidNull";
    public static final String i = "GetApdidTimeout";
    public static final String j = "GetUtdidEx";
    public static final String k = "GetPackageInfoEx";
    public static final String l = "NotIncludeSignatures";
    public static final String m = "GetInstalledPackagesEx";
    public static final String n = "GetPublicKeyFromSignEx";
    public static final String o = "H5PayNetworkError";
    public static final String p = "H5AuthNetworkError";
    public static final String q = "SSLError";
    public static final String r = "H5PayDataAnalysisError";
    public static final String s = "H5AuthDataAnalysisError";
    public static final String t = "PublicKeyUnmatch";
    public static final String u = "ClientBindFailed";
    public static final String v = "TriDesEncryptError";
    public static final String w = "TriDesDecryptError";
    public static final String x = "ClientBindException";
    public static final String y = "SaveTradeTokenError";
    public static final String z = "ClientBindServiceFailed";
    String I;
    String J;
    String K;
    String L;
    String M;
    String N;
    String O;
    String P;
    String Q = "";
    String R;

    public c(Context context) {
        context = context != null ? context.getApplicationContext() : context;
        this.I = String.format("123456789,%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
        this.K = a(context);
        this.L = String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", a(com.alipay.sdk.cons.a.f), a(com.alipay.sdk.cons.a.g));
        this.M = String.format("%s,%s,-,-,-", a(com.alipay.sdk.tid.b.a().a), a(com.alipay.sdk.sys.b.a().c()));
        this.N = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", a(com.alipay.sdk.util.a.d(context)), "android", a(Build.VERSION.RELEASE), a(Build.MODEL), "-", a(com.alipay.sdk.util.a.a(context).a()), a(com.alipay.sdk.util.a.b(context).p), "gw", a(com.alipay.sdk.util.a.a(context).b()));
        this.O = "-";
        this.P = "-";
        this.R = "-";
    }

    private static String a(Context context) {
        String str = "-";
        String str2 = "-";
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                String packageName = applicationContext.getPackageName();
                try {
                    str2 = applicationContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
                } catch (Throwable unused) {
                }
                str = packageName;
            } catch (Throwable unused2) {
            }
        }
        return String.format("%s,%s,-,-,-", str, str2);
    }

    static String a(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace("[", "【").replace("]", "】").replace("(", "（").replace(")", "）").replace(com.igexin.push.core.b.an, "，").replace("-", "=").replace("^", "~");
    }

    static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString() + " 》 ");
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    private void a(String str, String str2, Throwable th, String str3) {
        a(str, str2, a(th), str3);
    }

    private boolean a() {
        return TextUtils.isEmpty(this.Q);
    }

    @SuppressLint({"SimpleDateFormat"})
    private static String b() {
        return String.format("123456789,%s", new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
    }

    private static String b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", a(com.alipay.sdk.util.a.d(context)), "android", a(Build.VERSION.RELEASE), a(Build.MODEL), "-", a(com.alipay.sdk.util.a.a(context).a()), a(com.alipay.sdk.util.a.b(context).p), "gw", a(com.alipay.sdk.util.a.a(context).b()));
    }

    private String b(String str) {
        String strReplace;
        if (TextUtils.isEmpty(this.Q)) {
            return "";
        }
        String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
        String str2 = null;
        if (strArrSplit != null) {
            strReplace = null;
            String strReplace2 = null;
            for (String str3 : strArrSplit) {
                String[] strArrSplit2 = str3.split("=");
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(F)) {
                        strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(G)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(H)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    }
                }
            }
            str2 = strReplace2;
        } else {
            strReplace = null;
        }
        String strA = a(str2);
        String strA2 = a(strReplace);
        this.J = String.format("%s,%s,-,%s,-,-,-", strA, strA2, a(strA2));
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", this.I, this.J, this.K, this.L, this.M, this.N, this.O, this.P, this.Q, this.R);
    }

    private static String c() {
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", a(com.alipay.sdk.cons.a.f), a(com.alipay.sdk.cons.a.g));
    }

    private static String c(String str) {
        String strReplace;
        String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
        String str2 = null;
        if (strArrSplit != null) {
            strReplace = null;
            String strReplace2 = null;
            for (String str3 : strArrSplit) {
                String[] strArrSplit2 = str3.split("=");
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(F)) {
                        strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(G)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(H)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    }
                }
            }
            str2 = strReplace2;
        } else {
            strReplace = null;
        }
        String strA = a(str2);
        String strA2 = a(strReplace);
        return String.format("%s,%s,-,%s,-,-,-", strA, strA2, a(strA2));
    }

    private static String d() {
        return String.format("%s,%s,-,-,-", a(com.alipay.sdk.tid.b.a().a), a(com.alipay.sdk.sys.b.a().c()));
    }

    public final void a(String str, String str2, String str3) {
        a(str, str2, str3, "-");
    }

    public final void a(String str, String str2, String str3, String str4) {
        String str5 = "";
        if (!TextUtils.isEmpty(this.Q)) {
            str5 = "^";
        }
        this.Q += (str5 + String.format("%s,%s,%s,%s", str, str2, a(str3), str4));
    }

    public final void a(String str, String str2, Throwable th) {
        a(str, str2, a(th));
    }
}
