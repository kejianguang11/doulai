package com.ta.utdid2.a.a;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static String a() {
        String str = g.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = g.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? b() : str;
    }

    private static String b() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getImei(Context context) {
        String deviceId = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();
                }
            } catch (Exception unused) {
            }
        }
        if (f.isEmpty(deviceId)) {
            deviceId = a();
        }
        return f.isEmpty(deviceId) ? getUniqueID() : deviceId;
    }

    public static String getImsi(Context context) {
        String subscriberId = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    subscriberId = telephonyManager.getSubscriberId();
                }
            } catch (Exception unused) {
            }
        }
        return f.isEmpty(subscriberId) ? getUniqueID() : subscriberId;
    }

    public static String getUniqueID() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNanoTime = (int) System.nanoTime();
        int iNextInt = new Random().nextInt();
        int iNextInt2 = new Random().nextInt();
        byte[] bytes = c.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = c.getBytes(iNanoTime);
        byte[] bytes3 = c.getBytes(iNextInt);
        byte[] bytes4 = c.getBytes(iNextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return b.encodeToString(bArr, 2);
    }
}
