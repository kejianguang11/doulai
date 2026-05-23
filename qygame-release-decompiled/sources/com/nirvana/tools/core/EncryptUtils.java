package com.nirvana.tools.core;

import android.util.Base64;
import android.util.Log;
import com.alipay.sdk.sys.a;
import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class EncryptUtils {
    private static final String CipherMode = "AES/CBC/PKCS7Padding";
    public static final String IV_PARAMETER_SPEC = "0000000000000000";

    private static IvParameterSpec createIV(String str) {
        byte[] bytes;
        if (str == null) {
            str = "";
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        while (true) {
            stringBuffer.append(str);
            if (stringBuffer.length() >= 16) {
                break;
            }
            str = " ";
        }
        try {
            bytes = stringBuffer.substring(0, 16).getBytes(a.m);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new IvParameterSpec(bytes);
    }

    private static SecretKeySpec createKey(String str) {
        byte[] bytes;
        if (str == null) {
            str = "";
        }
        try {
            bytes = str.getBytes(a.m);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new SecretKeySpec(bytes, "AES");
    }

    public static String decrypt(String str, String str2) {
        try {
            return decryptBase642String(str, str2, "0000000000000000");
        } catch (Exception e) {
            Log.e("decrypt", e.getMessage());
            return null;
        }
    }

    public static byte[] decryptBase642Byte(String str, String str2, String str3) {
        byte[] bArrDecode;
        try {
            bArrDecode = Base64.decode(str, 0);
        } catch (Exception e) {
            e.printStackTrace();
            bArrDecode = null;
        }
        return decryptByte2Byte(bArrDecode, str2, str3);
    }

    public static String decryptBase642String(String str, String str2, String str3) {
        return decryptByte2String(Base64.decode(str, 0), str2, str3);
    }

    public static byte[] decryptByte2Byte(byte[] bArr, String str, String str2) {
        try {
            SecretKeySpec secretKeySpecCreateKey = createKey(str);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(2, secretKeySpecCreateKey, createIV(str2));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptByte2String(byte[] bArr, String str, String str2) {
        return new String(decryptByte2Byte(bArr, str, str2));
    }

    public static byte[] decryptString2Byte(String str, String str2, String str3) {
        byte[] bytes;
        try {
            bytes = str.getBytes(a.m);
        } catch (Exception e) {
            e.printStackTrace();
            bytes = null;
        }
        return decryptByte2Byte(bytes, str2, str3);
    }

    public static String encrypt(String str, String str2) {
        try {
            return encryptString2Base64(str, str2, "0000000000000000");
        } catch (Exception e) {
            Log.e("encrypt", e.getMessage());
            return null;
        }
    }

    public static String encryptByte2Base64(byte[] bArr, String str, String str2) {
        return new String(Base64.encode(encryptByte2Byte(bArr, str, str2), 0));
    }

    public static byte[] encryptByte2Byte(byte[] bArr, String str, String str2) {
        try {
            SecretKeySpec secretKeySpecCreateKey = createKey(str);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(1, secretKeySpecCreateKey, createIV(str2));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptByte2String(byte[] bArr, String str, String str2) {
        return new String(encryptByte2Byte(bArr, str, str2));
    }

    public static String encryptString2Base64(String str, String str2, String str3) {
        byte[] bytes;
        try {
            bytes = str.getBytes(a.m);
        } catch (Exception e) {
            e.printStackTrace();
            bytes = null;
        }
        return new String(Base64.encode(encryptByte2Byte(bytes, str2, str3), 0));
    }

    public static byte[] encryptString2Byte(String str, String str2, String str3) {
        byte[] bytes;
        try {
            bytes = str.getBytes(a.m);
        } catch (Exception e) {
            e.printStackTrace();
            bytes = null;
        }
        return encryptByte2Byte(bytes, str2, str3);
    }

    public static String encryptString2String(String str, String str2, String str3) {
        byte[] bytes;
        try {
            bytes = str.getBytes(a.m);
        } catch (Exception e) {
            e.printStackTrace();
            bytes = null;
        }
        return new String(encryptByte2Byte(bytes, str2, str3));
    }
}
