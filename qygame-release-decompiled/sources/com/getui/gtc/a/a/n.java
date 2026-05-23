package com.getui.gtc.a.a;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    private static String a = "dj1om0z0za9kwzxrphkqxsu9oc21tez1";
    private static String b = "PHLa/XQjrIl5cU/kj+C+Ig==";
    private static String c = a.a();

    public static String a() {
        try {
            if (TextUtils.isEmpty(c)) {
                c = a.a();
            }
            return c.a(m.a(a.a(c.getBytes(com.alipay.sdk.sys.a.m)), m.a(c.a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8UA4F9zfelx7qoRjTXEViE8WT60FBHJVl3T3/B+Nmljxiqa7H6GtOnmLFfpTVT+QdgBhxsU097DEBQhX8Z/9rVMp825T10jLefXly84/6p6B9Q0rNYX37zoWD5QT+5JWVgERX9P2o7fCXtlplLjv3dDXbzLdlWwdl53vtnAIidQIDAQAB".getBytes(com.alipay.sdk.sys.a.m)))), 0);
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return "";
        }
    }

    public static String a(String str) {
        try {
            if (TextUtils.isEmpty(c)) {
                c = a.a();
            }
            return a.a(c.getBytes(com.alipay.sdk.sys.a.m), str.getBytes(com.alipay.sdk.sys.a.m), h.a(a.getBytes(com.alipay.sdk.sys.a.m)));
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return null;
        }
    }
}
