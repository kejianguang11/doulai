package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class p {
    public static byte[] a(String str) {
        try {
            byte[] bArr = new byte[0];
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(str.getBytes(com.alipay.sdk.sys.a.m));
                return messageDigest.digest();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return bArr;
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
                return bArr;
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
}
