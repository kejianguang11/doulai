package com.mobile.auth.c;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static final String a = e.class.getCanonicalName();
    private static String b = "";

    public static String a() {
        try {
            String string = UUID.randomUUID().toString();
            try {
                string = UUID.nameUUIDFromBytes((string + System.currentTimeMillis() + Math.random()).getBytes(StandardCharsets.UTF_8)).toString();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return !TextUtils.isEmpty(string) ? string.replace("-", "") : string;
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    public static String a(Context context) {
        try {
            if (TextUtils.isEmpty(b)) {
                b = b(context);
                if (TextUtils.isEmpty(b)) {
                    b = c(context);
                    a(context, b);
                }
            }
            return b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String a(String str) {
        try {
            char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                byte[] bArrA = p.a(str);
                int length = bArrA.length;
                char[] cArr2 = new char[length * 2];
                int i = 0;
                for (int i2 = 0; i2 < length / 2; i2++) {
                    byte b2 = bArrA[i2];
                    int i3 = i + 1;
                    cArr2[i] = cArr[(b2 >>> 4) & 15];
                    i = i3 + 1;
                    cArr2[i3] = cArr[b2 & 15];
                }
                return new String(new String(cArr2).trim().getBytes(Charset.forName("ISO-8859-1")), Charset.forName(com.alipay.sdk.sys.a.m));
            } catch (Exception unused) {
                return null;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static void a(Context context, String str) {
        try {
            if (!TextUtils.isEmpty(str) && context != null) {
                d.a(context, "key_d_i_u", str);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static String b(Context context) {
        try {
            return d.b(context, "key_d_i_u", "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String c(Context context) {
        try {
            String string = UUID.randomUUID().toString();
            return TextUtils.isEmpty(string) ? "default" : a(string + "default");
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "default";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
