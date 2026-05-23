package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.core.SupportJarUtils;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static String a = "";
    private static volatile String b;
    private static volatile long c;

    public static int a(Context context) {
        try {
            return com.mobile.auth.q.e.a(context, 4);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public static int a(String str) {
        if (str == null) {
            return 4;
        }
        byte b2 = -1;
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b2 = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b2 = 1;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b2 = 2;
            }
            switch (b2) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 3;
                default:
                    return 4;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public static String a() {
        try {
            return a;
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

    public static String a(Context context, boolean z) {
        try {
            if (System.currentTimeMillis() - c > 500 || b == null || !z) {
                b = k(context);
                c = System.currentTimeMillis();
            }
            return b;
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

    public static String b(Context context) {
        try {
            int iA = a(context);
            if (iA == 4) {
                return "unknown";
            }
            switch (iA) {
                case 1:
                    return Constant.VENDOR_CMCC;
                case 2:
                    return Constant.VENDOR_CUCC;
                case 3:
                    return Constant.VENDOR_CTCC;
                default:
                    return "unknown";
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

    public static String b(String str) {
        if (str == null) {
            return "unknown";
        }
        try {
            byte b2 = -1;
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b2 = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b2 = 1;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b2 = 2;
            }
            switch (b2) {
                case 0:
                    return Constant.CMCC;
                case 1:
                    return Constant.CUCC;
                case 2:
                    return Constant.CTCC;
                default:
                    return "unknown";
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

    public static String c(Context context) {
        try {
            int iA = a(context);
            if (iA == 4) {
                return "unknown";
            }
            switch (iA) {
                case 1:
                    return Constant.CMCC;
                case 2:
                    return Constant.CUCC;
                case 3:
                    return Constant.CTCC;
                default:
                    return "unknown";
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

    public static String c(String str) {
        if (str == null) {
            return "unknown";
        }
        try {
            byte b2 = -1;
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b2 = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b2 = 1;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b2 = 2;
            }
            switch (b2) {
                case 0:
                    return "46000";
                case 1:
                    return "46001";
                case 2:
                    return "46003";
                default:
                    return "unknown";
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

    public static boolean d(Context context) {
        NetworkInfo activeNetworkInfo;
        NetworkInfo networkInfo;
        NetworkInfo networkInfo2;
        NetworkInfo.State state;
        NetworkInfo.State state2;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable() && (((networkInfo = connectivityManager.getNetworkInfo(1)) == null || (state2 = networkInfo.getState()) == null || (state2 != NetworkInfo.State.CONNECTED && state2 != NetworkInfo.State.CONNECTING)) && (networkInfo2 = connectivityManager.getNetworkInfo(0)) != null && (state = networkInfo2.getState()) != null)) {
                if (state != NetworkInfo.State.CONNECTED) {
                    if (state == NetworkInfo.State.CONNECTING) {
                    }
                }
                return false;
            }
            return true;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean e(Context context) {
        try {
            if (j(context)) {
                return false;
            }
            if (i(context)) {
                return true;
            }
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
                declaredMethod.setAccessible(true);
                return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            } catch (Exception e) {
                i.a(e);
                return true;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean f(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimState() == 5;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void g(Context context) {
        try {
            a = h(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004e A[Catch: Throwable -> 0x0063, Exception -> 0x0065, TryCatch #2 {Exception -> 0x0065, blocks: (B:2:0x0000, B:3:0x0004, B:5:0x000a, B:7:0x0016, B:9:0x001c, B:17:0x0044, B:18:0x0048, B:20:0x004e, B:22:0x005a, B:24:0x005e, B:12:0x002d, B:14:0x0033), top: B:40:0x0000, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String h(Context context) {
        Enumeration<InetAddress> inetAddresses;
        try {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if (d(context)) {
                        if (networkInterfaceNextElement.getName() == null || networkInterfaceNextElement.getName().toLowerCase().contains("wlan")) {
                            inetAddresses = networkInterfaceNextElement.getInetAddresses();
                            while (inetAddresses.hasMoreElements()) {
                                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                                if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                                    return inetAddressNextElement.getHostAddress();
                                }
                            }
                        }
                    } else if (networkInterfaceNextElement.getName() == null || networkInterfaceNextElement.getName().toLowerCase().contains("rmnet")) {
                        inetAddresses = networkInterfaceNextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                        }
                    }
                }
                return "";
            } catch (Exception e) {
                i.a(e);
                return "";
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

    public static boolean i(Context context) {
        if (context != null) {
            try {
                NetworkInfo networkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getNetworkInfo(0);
                if (networkInfo == null || !networkInfo.isAvailable()) {
                    return false;
                }
                return networkInfo.isConnected();
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
        return false;
    }

    public static boolean j(Context context) {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 17 ? Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1 : Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1) {
                z = true;
            }
        } catch (Throwable th) {
            try {
                i.a(th);
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return z;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return z;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String k(Context context) {
        NetworkInfo networkInfo;
        String strL;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (SupportJarUtils.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") != 0) {
                return SupportJarUtils.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") == 10 ? "NoClass" : "NoInternet";
            }
            if (Build.VERSION.SDK_INT >= 23) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                networkInfo = activeNetwork != null ? connectivityManager.getNetworkInfo(activeNetwork) : null;
            } else {
                NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (allNetworkInfo != null) {
                    for (NetworkInfo networkInfo2 : allNetworkInfo) {
                        if (networkInfo2 != null && networkInfo2.isAvailable() && networkInfo2.isConnected()) {
                            networkInfo = networkInfo2;
                            break;
                        }
                    }
                }
            }
            if (networkInfo == null || !networkInfo.isConnected()) {
                return "NoInternet";
            }
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                strL = e(context) ? "wifi+mobile" : "wifi";
            } else {
                if (!"MOBILE".equalsIgnoreCase(typeName)) {
                    return "NoInternet";
                }
                strL = TextUtils.isEmpty(Proxy.getDefaultHost()) ? l(context) : "wap";
            }
            return strL;
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

    private static String l(Context context) {
        int subtype;
        try {
            subtype = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo().getSubtype();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        if (subtype == 20) {
            return "5g";
        }
        switch (subtype) {
        }
        return null;
    }
}
