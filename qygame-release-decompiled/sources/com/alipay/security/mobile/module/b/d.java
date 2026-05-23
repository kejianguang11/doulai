package com.alipay.security.mobile.module.b;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static d a = new d();

    private d() {
    }

    public static d a() {
        return a;
    }

    private static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, str, str2);
        } catch (Exception unused) {
            return str2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0055 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean a(Context context) {
        boolean z;
        int length;
        try {
            if (!Build.HARDWARE.contains("goldfish") && !Build.PRODUCT.contains("sdk") && !Build.FINGERPRINT.contains("generic")) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    String deviceId = telephonyManager.getDeviceId();
                    if (deviceId == null || (length = deviceId.length()) == 0) {
                        z = true;
                        if (z) {
                            return true;
                        }
                    } else {
                        for (int i = 0; i < length; i++) {
                            if (!Character.isWhitespace(deviceId.charAt(i)) && deviceId.charAt(i) != '0') {
                                z = false;
                                break;
                            }
                        }
                        z = true;
                        if (z) {
                        }
                    }
                }
                return com.alipay.security.mobile.module.a.a.a(Settings.Secure.getString(context.getContentResolver(), "android_id"));
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String b() {
        return "android";
    }

    public static boolean c() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i = 0; i < 5; i++) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static String d() {
        return Build.BOARD;
    }

    public static String e() {
        return Build.BRAND;
    }

    public static String f() {
        return Build.DEVICE;
    }

    public static String g() {
        return Build.DISPLAY;
    }

    public static String h() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String i() {
        return Build.MANUFACTURER;
    }

    public static String j() {
        return Build.MODEL;
    }

    public static String k() {
        return Build.PRODUCT;
    }

    public static String l() {
        return Build.VERSION.RELEASE;
    }

    public static String m() {
        return Build.VERSION.SDK;
    }

    public static String n() {
        return Build.TAGS;
    }

    public static String o() {
        return a("ro.kernel.qemu", "0");
    }
}
