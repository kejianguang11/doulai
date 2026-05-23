package com.alipay.sdk.util;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    public static final String a = "resultStatus";
    public static final String b = "memo";
    public static final String c = "result";

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(h.d));
    }

    private static Map<String, String> a() {
        com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.h);
        HashMap map = new HashMap();
        map.put(a, Integer.toString(jVarA.h));
        map.put(b, jVarA.i);
        map.put(c, "");
        return map;
    }

    public static Map<String, String> a(String str) {
        com.alipay.sdk.app.j jVarA = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.h);
        HashMap map = new HashMap();
        map.put(a, Integer.toString(jVarA.h));
        map.put(b, jVarA.i);
        map.put(c, "");
        try {
            return b(str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.f, th);
            return map;
        }
    }

    private static Map<String, String> b(String str) {
        String[] strArrSplit = str.split(h.b);
        HashMap map = new HashMap();
        for (String str2 : strArrSplit) {
            String strSubstring = str2.substring(0, str2.indexOf("={"));
            String str3 = strSubstring + "={";
            map.put(strSubstring, str2.substring(str2.indexOf(str3) + str3.length(), str2.lastIndexOf(h.d)));
        }
        return map;
    }
}
