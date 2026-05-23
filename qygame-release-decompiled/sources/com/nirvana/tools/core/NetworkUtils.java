package com.nirvana.tools.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class NetworkUtils {
    public static String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return inetAddressNextElement.getHostAddress();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            Log.e("NetworkUtils", e.getMessage());
            return "";
        }
    }

    public static boolean hasSimCard(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimState() == 5;
    }

    public static boolean isAirModeEnable(Context context) {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 17 ? Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1 : Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1) {
                z = true;
            }
        } catch (Throwable th) {
            Log.e("NetworkUtils", th.getMessage());
        }
        return z;
    }

    public static boolean isMobileNetConnected(Context context) {
        NetworkInfo networkInfo;
        return context != null && (networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0)) != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static boolean isMobileNetworkOpen(Context context) {
        if (!hasSimCard(context) || isAirModeEnable(context)) {
            return false;
        }
        if (isMobileNetConnected(context)) {
            return true;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            Log.e("NetworkUtils", e.getMessage());
            return true;
        }
    }
}
