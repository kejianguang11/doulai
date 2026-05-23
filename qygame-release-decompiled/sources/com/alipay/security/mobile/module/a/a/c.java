package com.alipay.security.mobile.module.a.a;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static String a = new String("idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#");

    public static String a() {
        String str = new String();
        for (int i = 0; i < a.length() - 1; i += 4) {
            str = str + a.charAt(i);
        }
        return str;
    }

    public static String a(String str, String str2) {
        try {
            try {
                PBEKeySpec pBEKeySpecA = a(str);
                byte[] bytes = str2.getBytes();
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpecA).getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                byte[] salt = pBEKeySpecA.getSalt();
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(salt.length + cipher.getOutputSize(bytes.length));
                byteBufferAllocate.put(salt);
                cipher.doFinal(ByteBuffer.wrap(bytes), byteBufferAllocate);
                return b(byteBufferAllocate.array());
            } catch (Exception unused) {
                return null;
            }
        } catch (Exception unused2) {
            return b(a(a(str.getBytes()), str2.getBytes()));
        }
    }

    private static PBEKeySpec a(String str) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, InvocationTargetException {
        Class<?> cls = Class.forName(new String(a.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object objNewInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", bArr.getClass());
        method.setAccessible(true);
        method.invoke(objNewInstance, bArr);
        return new PBEKeySpec(str.toCharArray(), bArr, 10, 128);
    }

    private static byte[] a(byte[] bArr) throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, ClassNotFoundException, InvocationTargetException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        Class<?> cls = Class.forName(new String(a.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object objInvoke = cls.getMethod("getInstance", String.class, String.class).invoke(null, "SHA1PRNG", "Crypto");
        Method method = cls.getMethod("setSeed", bArr.getClass());
        method.setAccessible(true);
        method.invoke(objInvoke, bArr);
        KeyGenerator.class.getMethod("init", Integer.TYPE, cls).invoke(keyGenerator, 128, objInvoke);
        return keyGenerator.generateKey().getEncoded();
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(bArr2);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String b(String str, String str2) {
        String str3;
        byte[] bArrDoFinal;
        try {
            PBEKeySpec pBEKeySpecA = a(str);
            byte[] bArrB = b(str2);
            if (bArrB.length <= 16) {
                bArrDoFinal = null;
            } else {
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(pBEKeySpecA.getPassword(), Arrays.copyOf(bArrB, 16), 10, 128)).getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                bArrDoFinal = cipher.doFinal(bArrB, 16, bArrB.length - 16);
            }
        } catch (Exception unused) {
        }
        if (bArrDoFinal == null) {
            throw new Exception();
        }
        String str4 = new String(bArrDoFinal);
        if (com.alipay.security.mobile.module.a.a.c(str4)) {
            return str4;
        }
        try {
            byte[] bArrA = a(str.getBytes());
            byte[] bArrB2 = b(str2);
            SecretKeySpec secretKeySpec2 = new SecretKeySpec(bArrA, "AES");
            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher2.init(2, secretKeySpec2, new IvParameterSpec(new byte[cipher2.getBlockSize()]));
            str3 = new String(cipher2.doFinal(bArrB2));
        } catch (Exception unused2) {
        }
        if (com.alipay.security.mobile.module.a.a.c(str3)) {
            return str3;
        }
        return null;
    }

    private static String b(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15));
            stringBuffer.append("0123456789ABCDEF".charAt(b & 15));
        }
        return stringBuffer.toString();
    }

    private static byte[] b(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }
}
