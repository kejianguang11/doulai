package com.gme.liteav.base.system;

import android.os.SystemClock;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.storage.PersistStorage;
import java.security.MessageDigest;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
final class q {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* JADX WARN: Removed duplicated region for block: B:14:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(String str) {
        String string;
        if (ContextUtils.getApplicationContext() == null) {
            return "";
        }
        String str2 = null;
        str2 = null;
        PersistStorage persistStorage = new PersistStorage("com.gme.liteav.dev_uuid");
        String string2 = persistStorage.getString("com.gme.liteav.key_dev_uuid");
        if (string2 != null && string2.length() == 26) {
            str2 = string2;
        }
        if (str2 != null) {
            int length = str2.length();
            string = str2;
            if (length == 0) {
                String str3 = "";
                long jCurrentTimeMillis = System.currentTimeMillis();
                long jUptimeMillis = SystemClock.uptimeMillis();
                for (int i = 5; i >= 0; i--) {
                    str3 = str3 + String.format("%02x", Byte.valueOf((byte) (255 & (jCurrentTimeMillis >> (i * 8)))));
                }
                for (int i2 = 3; i2 >= 0; i2--) {
                    str3 = str3 + String.format("%02x", Byte.valueOf((byte) ((jUptimeMillis >> (i2 * 8)) & 255)));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(b(str + UUID.randomUUID().toString()));
                string = sb.toString();
            }
        }
        if (string2 == null || !string2.equals(string)) {
            persistStorage.put("com.gme.liteav.key_dev_uuid", string);
            persistStorage.commit();
        }
        return string;
    }

    private static String b(String str) {
        if (str == null) {
            return "";
        }
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes(com.alipay.sdk.sys.a.m));
            char[] cArr = new char[bArrDigest.length << 1];
            int i = 0;
            for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                int i3 = i + 1;
                cArr[i] = a[(bArrDigest[i2] & 240) >>> 4];
                i = i3 + 1;
                cArr[i3] = a[bArrDigest[i2] & 15];
            }
            return new String(cArr);
        } catch (Exception e) {
            Log.e("UUID", "stringToMd5 failed.", e);
            return "";
        }
    }
}
