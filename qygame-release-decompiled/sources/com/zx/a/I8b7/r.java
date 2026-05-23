package com.zx.a.I8b7;

import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class r {
    public static final SecureRandom a = new SecureRandom();

    public static String a(String str, boolean z) throws Throwable {
        if (!z) {
            str = a("MD5", str.getBytes(StandardCharsets.UTF_8));
        }
        StringBuilder sbA = j3.a(str);
        sbA.append(q3.h);
        byte[] bytes = sbA.toString().getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
        messageDigest.update(bytes);
        String strA = a(messageDigest.digest());
        return strA.substring(0, 16) + strA.substring(strA.length() - 16);
    }

    public static String a(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(bArr);
        return a(messageDigest.digest());
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = bArr[i];
            if (i2 < 0) {
                i2 += 256;
            }
            if (i2 < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i2));
        }
        return sb.toString();
    }

    public static String a(byte[] bArr, SecretKey secretKey, String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new GCMParameterSpec(128, bArr, 0, 12));
        cipher.updateAAD(str.getBytes(StandardCharsets.UTF_8));
        return new String(cipher.doFinal(bArr, 12, bArr.length - 12), StandardCharsets.UTF_8);
    }

    public static SecretKey a(byte[] bArr, String str) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] bArrDoFinal = mac.doFinal(bArr);
            byte[] bArr2 = new byte[16];
            System.arraycopy(bArrDoFinal, 0, bArr2, 0, 16);
            return new SecretKeySpec(bArr2, "AES");
        } catch (Exception e) {
            v2.a(e);
            return null;
        }
    }

    public static byte[] a(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static byte[] a(String str, String str2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(com.alipay.sdk.sys.a.m), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKeySpec);
        return mac.doFinal(str.getBytes(com.alipay.sdk.sys.a.m));
    }

    public static byte[] a(String str, SecretKey secretKey, String str2) throws Exception {
        byte[] bArr = new byte[12];
        a.nextBytes(bArr);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, secretKey, new GCMParameterSpec(128, bArr));
        cipher.updateAAD(str2.getBytes(StandardCharsets.UTF_8));
        byte[] bArrDoFinal = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrDoFinal.length + 12);
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(bArrDoFinal);
        return byteBufferAllocate.array();
    }

    public static byte[] a(String str, SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(str);
        cipher.init(2, secretKey, ivParameterSpec);
        return cipher.doFinal(bArr);
    }

    public static String b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return a(str2, str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            v2.a(e);
            return "";
        }
    }

    public static SecretKey b(byte[] bArr, String str) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return new SecretKeySpec(mac.doFinal(bArr), "AES");
        } catch (Exception e) {
            v2.a(e);
            return null;
        }
    }

    public static byte[] b(String str, SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(str);
        cipher.init(1, secretKey, ivParameterSpec);
        return cipher.doFinal(bArr);
    }

    public static String c(String str, String str2) throws BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str2, 0))));
        return a(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String d(String str, String str2) throws BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str2, 0))));
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 <= 0) {
                    String str3 = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 2), StandardCharsets.UTF_8);
                    try {
                        byteArrayOutputStream.close();
                        return str3;
                    } catch (Throwable unused) {
                        return str3;
                    }
                }
                byte[] bArrDoFinal = i3 > 117 ? cipher.doFinal(bytes, i, 117) : cipher.doFinal(bytes, i, i3);
                byteArrayOutputStream.write(bArrDoFinal, 0, bArrDoFinal.length);
                i2++;
                i = i2 * 117;
            }
        } catch (Throwable unused2) {
            return "";
        }
    }
}
