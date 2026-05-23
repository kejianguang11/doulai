package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "alipay_cashier_statistic_record";
    private static c b;

    public static void a(Context context) {
        if (b != null) {
            return;
        }
        b = new c(context);
    }

    public static synchronized void a(Context context, String str) {
        String strReplace;
        String strReplace2;
        String str2;
        if (b == null) {
            return;
        }
        c cVar = b;
        if (TextUtils.isEmpty(cVar.Q)) {
            str2 = "";
        } else {
            String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
            if (strArrSplit != null) {
                strReplace = null;
                strReplace2 = null;
                for (String str3 : strArrSplit) {
                    String[] strArrSplit2 = str3.split("=");
                    if (strArrSplit2 != null && strArrSplit2.length == 2) {
                        if (strArrSplit2[0].equalsIgnoreCase(c.F)) {
                            strArrSplit2[1].replace("\"", "");
                        } else if (strArrSplit2[0].equalsIgnoreCase(c.G)) {
                            strReplace = strArrSplit2[1].replace("\"", "");
                        } else if (strArrSplit2[0].equalsIgnoreCase(c.H)) {
                            strReplace2 = strArrSplit2[1].replace("\"", "");
                        }
                    }
                }
            } else {
                strReplace = null;
                strReplace2 = null;
            }
            String strA = c.a(strReplace2);
            String strA2 = c.a(strReplace);
            cVar.J = String.format("%s,%s,-,%s,-,-,-", strA, strA2, c.a(strA2));
            str2 = String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", cVar.I, cVar.J, cVar.K, cVar.L, cVar.M, cVar.N, cVar.O, cVar.P, cVar.Q, cVar.R);
        }
        new Thread(new b(context, str2)).start();
        b = null;
    }

    public static void a(String str, String str2, String str3) {
        if (b == null) {
            return;
        }
        b.a(str, str2, str3);
    }

    public static void a(String str, String str2, Throwable th) {
        if (b == null) {
            return;
        }
        b.a(str, str2, th);
    }

    private static void a(String str, String str2, Throwable th, String str3) {
        if (b == null) {
            return;
        }
        b.a(str, str2, c.a(th), str3);
    }

    public static void a(String str, Throwable th) {
        if (b == null || th.getClass() == null) {
            return;
        }
        b.a(str, th.getClass().getSimpleName(), th);
    }

    private static void b(Context context, String str) {
        new Thread(new b(context, str)).start();
    }
}
