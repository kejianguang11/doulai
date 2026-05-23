package com.alipay.sdk.packet;

import android.text.TextUtils;
import com.alipay.sdk.util.h;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
        if (strArrSplit.length == 0) {
            return "";
        }
        String strE = null;
        String strE2 = null;
        String strE3 = null;
        String strE4 = null;
        for (String str2 : strArrSplit) {
            if (TextUtils.isEmpty(strE)) {
                strE = !str2.contains("biz_type") ? null : e(str2);
            }
            if (TextUtils.isEmpty(strE2)) {
                strE2 = !str2.contains("biz_no") ? null : e(str2);
            }
            if (TextUtils.isEmpty(strE3)) {
                strE3 = (!str2.contains(com.alipay.sdk.app.statistic.c.H) || str2.startsWith(com.alipay.sdk.app.statistic.c.G)) ? null : e(str2);
            }
            if (TextUtils.isEmpty(strE4)) {
                strE4 = !str2.contains("app_userid") ? null : e(str2);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(strE)) {
            sb.append("biz_type=" + strE + h.b);
        }
        if (!TextUtils.isEmpty(strE2)) {
            sb.append("biz_no=" + strE2 + h.b);
        }
        if (!TextUtils.isEmpty(strE3)) {
            sb.append("trade_no=" + strE3 + h.b);
        }
        if (!TextUtils.isEmpty(strE4)) {
            sb.append("app_userid=" + strE4 + h.b);
        }
        String string = sb.toString();
        return string.endsWith(h.b) ? string.substring(0, string.length() - 1) : string;
    }

    private static String b(String str) {
        if (str.contains("biz_type")) {
            return e(str);
        }
        return null;
    }

    private static String c(String str) {
        if (str.contains("biz_no")) {
            return e(str);
        }
        return null;
    }

    private static String d(String str) {
        if (!str.contains(com.alipay.sdk.app.statistic.c.H) || str.startsWith(com.alipay.sdk.app.statistic.c.G)) {
            return null;
        }
        return e(str);
    }

    private static String e(String str) {
        String[] strArrSplit = str.split("=");
        if (strArrSplit.length <= 1) {
            return null;
        }
        String str2 = strArrSplit[1];
        return str2.contains("\"") ? str2.replaceAll("\"", "") : str2;
    }

    private static String f(String str) {
        if (str.contains("app_userid")) {
            return e(str);
        }
        return null;
    }
}
