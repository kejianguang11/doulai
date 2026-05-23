package com.alipay.sdk.util;

import android.content.Context;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    public static final String a = "pref_trade_token";
    public static final String b = ";";
    public static final String c = "result={";
    public static final String d = "}";
    public static final String e = "trade_token=\"";
    public static final String f = "\"";
    public static final String g = "trade_token=";

    private static String a(Context context) {
        return i.b(context, a, "");
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(b);
        String strSubstring = null;
        for (int i = 0; i < strArrSplit.length; i++) {
            if (strArrSplit[i].startsWith(c) && strArrSplit[i].endsWith(d)) {
                String[] strArrSplit2 = strArrSplit[i].substring(8, strArrSplit[i].length() - 1).split(com.alipay.sdk.sys.a.b);
                int i2 = 0;
                while (true) {
                    if (i2 >= strArrSplit2.length) {
                        break;
                    }
                    if (strArrSplit2[i2].startsWith(e) && strArrSplit2[i2].endsWith("\"")) {
                        strSubstring = strArrSplit2[i2].substring(13, strArrSplit2[i2].length() - 1);
                        break;
                    }
                    if (strArrSplit2[i2].startsWith(g)) {
                        strSubstring = strArrSplit2[i2].substring(12);
                        break;
                    }
                    i2++;
                }
            }
        }
        return strSubstring;
    }

    private static void a(Context context, String str) {
        try {
            String str2 = null;
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(b);
                String strSubstring = null;
                for (int i = 0; i < strArrSplit.length; i++) {
                    if (strArrSplit[i].startsWith(c) && strArrSplit[i].endsWith(d)) {
                        String[] strArrSplit2 = strArrSplit[i].substring(8, strArrSplit[i].length() - 1).split(com.alipay.sdk.sys.a.b);
                        int i2 = 0;
                        while (true) {
                            if (i2 >= strArrSplit2.length) {
                                break;
                            }
                            if (strArrSplit2[i2].startsWith(e) && strArrSplit2[i2].endsWith("\"")) {
                                strSubstring = strArrSplit2[i2].substring(13, strArrSplit2[i2].length() - 1);
                                break;
                            } else {
                                if (strArrSplit2[i2].startsWith(g)) {
                                    strSubstring = strArrSplit2[i2].substring(12);
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                }
                str2 = strSubstring;
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            i.a(context, a, str2);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.y, th);
        }
    }
}
