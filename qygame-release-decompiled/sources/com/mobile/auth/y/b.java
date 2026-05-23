package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static String a = "";
    private static String b = "";
    private static String c = "";
    private static String d = "";
    private static long e = 0;
    private static long f = 0;
    private static String g = "CU";

    public static void a(long j) {
        try {
            f = j;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(String str) {
        try {
            g = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b(long j) {
        try {
            e = j;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b(String str) {
        try {
            c = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void c(String str) {
        try {
            b = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void d(String str) {
        try {
            d = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static Boolean e(String str) {
        if (str != null) {
            try {
                if (str.length() != 0 && str.trim().length() != 0 && !"null".equals(str) && !str.equals("")) {
                    return Boolean.TRUE;
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        return Boolean.FALSE;
    }
}
