package com.alipay.sdk.app;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    public static String a;

    public static String a() {
        j jVarA = j.a(j.CANCELED.h);
        return a(jVarA.h, jVarA.i, "");
    }

    public static String a(int i, String str, String str2) {
        return "resultStatus={" + i + "};memo={" + str + "};result={" + str2 + com.alipay.sdk.util.h.d;
    }

    private static void a(String str) {
        a = str;
    }

    private static String b() {
        return a;
    }

    private static String c() {
        j jVarA = j.a(j.DOUBLE_REQUEST.h);
        return a(jVarA.h, jVarA.i, "");
    }

    private static String d() {
        j jVarA = j.a(j.PARAMS_ERROR.h);
        return a(jVarA.h, jVarA.i, "");
    }
}
