package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public final class w {
    public static Boolean a(String str) {
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
