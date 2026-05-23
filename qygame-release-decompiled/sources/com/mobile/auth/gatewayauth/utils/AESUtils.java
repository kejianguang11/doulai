package com.mobile.auth.gatewayauth.utils;

import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import java.io.UnsupportedEncodingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public class AESUtils {
    private static final String CipherMode = "AES/CBC/PKCS7Padding";

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    private static native IvParameterSpec createIV(String str);

    private static SecretKeySpec createKey(String str) {
        byte[] bytes;
        if (str == null) {
            str = "";
        }
        try {
            try {
                bytes = str.getBytes(com.alipay.sdk.sys.a.m);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                bytes = null;
            }
            return new SecretKeySpec(bytes, "AES");
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

    public static native String decrypt(String str, String str2);

    public static native byte[] decryptBase642Byte(String str, String str2, String str3);

    public static native String decryptBase642String(String str, String str2, String str3);

    public static native byte[] decryptByte2Byte(byte[] bArr, String str, String str2);

    public static native String decryptByte2String(byte[] bArr, String str, String str2);

    public static native byte[] decryptString2Byte(String str, String str2, String str3);

    public static String encrypt(String str, String str2) {
        try {
            try {
                return encryptString2Base64(str, str2, "0000000000000000");
            } catch (Exception e) {
                i.a(e);
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

    public static native String encryptByte2Base64(byte[] bArr, String str, String str2);

    public static native byte[] encryptByte2Byte(byte[] bArr, String str, String str2);

    public static native String encryptByte2String(byte[] bArr, String str, String str2);

    public static native String encryptString2Base64(String str, String str2, String str3);

    public static native byte[] encryptString2Byte(String str, String str2, String str3);

    public static native String encryptString2String(String str, String str2, String str3);
}
