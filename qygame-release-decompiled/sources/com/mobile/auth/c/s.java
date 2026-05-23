package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class s {
    private static final String a = "s";
    private static byte[] b = {68, 64, 94, 49, 69, 35, 50, 83};
    private static final Charset c = Charset.forName(com.alipay.sdk.sys.a.m);

    public static String a(byte[] bArr) {
        try {
            int length = bArr.length;
            byte[] bArr2 = new byte[length];
            for (int i = 0; i < length; i++) {
                bArr2[i] = bArr[i];
                for (byte b2 : b) {
                    bArr2[i] = (byte) (b2 ^ bArr2[i]);
                }
            }
            return new String(bArr2);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
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
