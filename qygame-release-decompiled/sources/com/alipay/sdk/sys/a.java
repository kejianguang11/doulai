package com.alipay.sdk.sys;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alipay.sdk.util.l;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "\"&";
    public static final String b = "&";
    public static final String c = "bizcontext=\"";
    public static final String d = "bizcontext=";
    public static final String e = "\"";
    public static final String f = "appkey";
    public static final String g = "ty";
    public static final String h = "sv";
    public static final String i = "an";
    public static final String j = "setting";
    public static final String k = "av";
    public static final String l = "sdk_start_time";
    public static final String m = "UTF-8";
    private String n;
    private String o;
    private Context p;

    public a(Context context) {
        this.n = "";
        this.o = "";
        this.p = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.n = packageInfo.versionName;
            this.o = packageInfo.packageName;
            this.p = context.getApplicationContext();
        } catch (Exception unused) {
        }
    }

    private static String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (!TextUtils.isEmpty(strArrSplit[i2]) && strArrSplit[i2].startsWith(str3)) {
                return strArrSplit[i2];
            }
        }
        return null;
    }

    private String b(String str, String str2) throws JSONException, UnsupportedEncodingException {
        return str + a("", "") + str2;
    }

    private String b(String str, String str2, String str3) throws JSONException, UnsupportedEncodingException {
        String strSubstring = str.substring(str2.length());
        JSONObject jSONObject = new JSONObject(strSubstring.substring(0, strSubstring.length() - str3.length()));
        if (!jSONObject.has(f)) {
            jSONObject.put(f, com.alipay.sdk.cons.a.d);
        }
        if (!jSONObject.has(g)) {
            jSONObject.put(g, "and_lite");
        }
        if (!jSONObject.has(h)) {
            jSONObject.put(h, com.alipay.sdk.cons.a.g);
        }
        if (!jSONObject.has(i) && (!this.o.contains(j) || !l.e(this.p))) {
            jSONObject.put(i, this.o);
        }
        if (!jSONObject.has(k)) {
            jSONObject.put(k, this.n);
        }
        if (!jSONObject.has(l)) {
            jSONObject.put(l, System.currentTimeMillis());
        }
        return str2 + jSONObject.toString() + str3;
    }

    private static boolean b(String str) {
        return !str.contains(a);
    }

    private String c(String str) {
        String str2;
        try {
            String strA = a(str, b, d);
            if (TextUtils.isEmpty(strA)) {
                str2 = str + b + b(d, "");
            } else {
                int iIndexOf = str.indexOf(strA);
                str2 = str.substring(0, iIndexOf) + b(strA, d, "") + str.substring(iIndexOf + strA.length());
            }
            return str2;
        } catch (Throwable unused) {
            return str;
        }
    }

    private String d(String str) {
        try {
            String strA = a(str, a, c);
            if (TextUtils.isEmpty(strA)) {
                return str + b + b(c, "\"");
            }
            if (!strA.endsWith("\"")) {
                strA = strA + "\"";
            }
            int iIndexOf = str.indexOf(strA);
            return str.substring(0, iIndexOf) + b(strA, c, "\"") + str.substring(iIndexOf + strA.length());
        } catch (Throwable unused) {
            return str;
        }
    }

    public final String a(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith("new_external_info==")) ? str : str.contains(a) ^ true ? c(str) : d(str);
    }

    public final String a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(f, com.alipay.sdk.cons.a.d);
            jSONObject.put(g, "and_lite");
            jSONObject.put(h, com.alipay.sdk.cons.a.g);
            if (!this.o.contains(j) || !l.e(this.p)) {
                jSONObject.put(i, this.o);
            }
            jSONObject.put(k, this.n);
            jSONObject.put(l, System.currentTimeMillis());
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put(str, str2);
            }
            return jSONObject.toString();
        } catch (Throwable unused) {
            return "";
        }
    }
}
