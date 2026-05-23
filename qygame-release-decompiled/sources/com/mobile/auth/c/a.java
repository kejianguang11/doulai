package com.mobile.auth.c;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static final String a = "a";
    private static byte[] b = "0000000000000000".getBytes();
    private static byte[] c = "vrf5g7h0tededwx3".getBytes();

    public static String a(String str, String str2) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(b);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = str.getBytes(com.igexin.push.g.s.b);
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return q.a(cipher.doFinal(bytes));
        } catch (Throwable th) {
            try {
                Log.w(a, "encryptAesNew error", th);
                return null;
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

    public static String b(String str, String str2) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(b);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] bArrDoFinal = cipher.doFinal(q.a(str));
            if (bArrDoFinal != null) {
                return new String(bArrDoFinal);
            }
            Log.i(a, "Aes decrypt result is empty");
            return "";
        } catch (Throwable th) {
            try {
                Log.w(a, "decryptAesNew error", th);
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
