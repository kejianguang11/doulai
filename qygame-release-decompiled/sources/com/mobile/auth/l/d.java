package com.mobile.auth.l;

import android.text.TextUtils;
import com.igexin.push.g.s;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return a(str.getBytes(s.b));
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return q.a(messageDigest.digest());
        } catch (Exception unused) {
            return "";
        }
    }
}
