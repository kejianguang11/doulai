package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.utils.i;

/* JADX INFO: loaded from: classes.dex */
public class ExceptionProcessor {
    private static boolean isRecursionException(Throwable th) {
        try {
            int i = 0;
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                if (stackTraceElement.getClassName().equals(ExceptionProcessor.class.getName()) && stackTraceElement.getMethodName().equals("processException")) {
                    i++;
                }
            }
            return i >= 4;
        } catch (Throwable th2) {
            try {
                processException(th2);
                return false;
            } catch (Throwable th3) {
                processException(th3);
                return false;
            }
        }
    }

    public static void processException(Throwable th) {
        i.a(th);
    }
}
