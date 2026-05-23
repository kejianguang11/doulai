package com.alipay.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final String b = "00:00:00:00:00:00";
    private static a e;
    public String a;
    private String c;
    private String d;

    private a(Context context) {
        try {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
                b(telephonyManager.getDeviceId());
                String subscriberId = telephonyManager.getSubscriberId();
                if (subscriberId != null) {
                    subscriberId = (subscriberId + "000000000000000").substring(0, 15);
                }
                this.c = subscriberId;
                this.a = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
                if (!TextUtils.isEmpty(this.a)) {
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (!TextUtils.isEmpty(this.a)) {
                    return;
                }
            }
            this.a = b;
        } catch (Throwable th) {
            if (TextUtils.isEmpty(this.a)) {
                this.a = b;
            }
            throw th;
        }
    }

    public static a a(Context context) {
        if (e == null) {
            e = new a(context);
        }
        return e;
    }

    private void a(String str) {
        if (str != null) {
            str = (str + "000000000000000").substring(0, 15);
        }
        this.c = str;
    }

    public static d b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return (activeNetworkInfo == null || activeNetworkInfo.getType() != 0) ? (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) ? d.NONE : d.WIFI : d.a(activeNetworkInfo.getSubtype());
        } catch (Exception unused) {
            return d.NONE;
        }
    }

    private void b(String str) {
        if (str != null) {
            byte[] bytes = str.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 48 || bytes[i] > 57) {
                    bytes[i] = 48;
                }
            }
            str = (new String(bytes) + "000000000000000").substring(0, 15);
        }
        this.d = str;
    }

    private String c() {
        String str = b() + "|";
        String strA = a();
        if (TextUtils.isEmpty(strA)) {
            return str + "000000000000000";
        }
        return str + strA;
    }

    public static String c(Context context) {
        String str;
        a aVarA = a(context);
        String str2 = aVarA.b() + "|";
        String strA = aVarA.a();
        if (TextUtils.isEmpty(strA)) {
            str = str2 + "000000000000000";
        } else {
            str = str2 + strA;
        }
        return str.substring(0, 8);
    }

    private String d() {
        return this.a;
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public final String a() {
        if (TextUtils.isEmpty(this.c)) {
            this.c = "000000000000000";
        }
        return this.c;
    }

    public final String b() {
        if (TextUtils.isEmpty(this.d)) {
            this.d = "000000000000000";
        }
        return this.d;
    }
}
