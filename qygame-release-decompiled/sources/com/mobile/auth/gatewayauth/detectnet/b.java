package com.mobile.auth.gatewayauth.detectnet;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final Pattern a = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern b = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern c = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    public static boolean a(String str) {
        if (str == null) {
            return false;
        }
        try {
            return b.matcher(str).matches();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean b(String str) {
        if (str == null) {
            return false;
        }
        try {
            return c.matcher(str).matches();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean c(String str) {
        if (str == null) {
            return false;
        }
        try {
            if (!a(str)) {
                if (!b(str)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }
}
