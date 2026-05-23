package com.mobile.auth.y;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class v {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static int b = 1;
    private static int c = 0;

    public static String a() {
        try {
            return u.d();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            try {
                return a(a(context, str), str2.toLowerCase());
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(String str) {
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                messageDigest.update(str.getBytes());
                byte[] bArrDigest = messageDigest.digest();
                StringBuffer stringBuffer = new StringBuffer("");
                for (int i = 0; i < bArrDigest.length; i++) {
                    int i2 = bArrDigest[i];
                    if (i2 < 0) {
                        i2 += 256;
                    }
                    if (i2 < 16) {
                        stringBuffer.append("0");
                    }
                    stringBuffer.append(Integer.toHexString(i2));
                }
                return stringBuffer.toString();
            } catch (NoSuchAlgorithmException unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            return i.b(str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private static String a(byte[] bArr, String str) {
        try {
            try {
                byte[] bArrDigest = MessageDigest.getInstance(str).digest(bArr);
                String str2 = "";
                for (int i = 0; i < bArrDigest.length; i++) {
                    if (i != 0) {
                        str2 = str2 + ":";
                    }
                    String hexString = Integer.toHexString(bArrDigest[i] & 255);
                    if (hexString.length() == 1) {
                        str2 = str2 + "0";
                    }
                    str2 = str2 + hexString;
                }
                return str2;
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private static boolean a(ConnectivityManager connectivityManager) {
        try {
            try {
                Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
                declaredMethod.setAccessible(true);
                return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            } catch (Exception unused) {
                t.b();
                return true;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    private static byte[] a(Context context, String str) {
        try {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
                if (packageInfo.packageName.equals(str)) {
                    return packageInfo.signatures[0].toByteArray();
                }
                return null;
            } catch (Exception unused) {
                t.b();
                return null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static int b(Context context) {
        try {
            int iD = d(context);
            u.a(iD);
            return iD;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int b(String str) {
        try {
            try {
                byte[] address = InetAddress.getByName(str).getAddress();
                return (address[0] & 255) | ((address[3] & 255) << 24) | ((address[2] & 255) << 16) | ((address[1] & 255) << 8);
            } catch (UnknownHostException unused) {
                return -1;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static String b(String str, String str2, String str3) {
        try {
            return i.a(str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void b() {
        try {
            t.a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static int c() {
        try {
            c = 0;
            b = 0;
            return 0;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static String c(Context context) {
        try {
            return (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception unused) {
            return "";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void c(String str) {
        try {
            t.b(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static int d() {
        try {
            return b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    private static int d(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            try {
                connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                t.c("android Build.VERSION:" + Build.VERSION.SDK_INT);
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (Exception unused) {
                t.b();
            }
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                if (activeNetworkInfo.getType() == 1) {
                    if (a(connectivityManager)) {
                        t.c("Data and WIFI");
                        return 1;
                    }
                    t.c("Only WIFI");
                    return 2;
                }
                if (activeNetworkInfo.getType() == 0) {
                    t.c("Only Data");
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (TextUtils.isEmpty(extraInfo)) {
                        return 0;
                    }
                    u.d(extraInfo);
                    u.a(u.e(extraInfo));
                    return 0;
                }
                return -1;
            }
            return -1;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int e() {
        try {
            return c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int f() {
        try {
            if (c < 0 || c > b) {
                return b;
            }
            int i = c + 1;
            c = i;
            return i;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static String g() {
        int i;
        try {
            if (u.d != null) {
                c cVar = u.d;
                return c.a();
            }
            JSONObject jSONObject = new JSONObject();
            try {
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = new HashSet();
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                StringBuilder sb3 = new StringBuilder();
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (true) {
                    if (!networkInterfaces.hasMoreElements()) {
                        break;
                    }
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if (!networkInterfaceNextElement.isVirtual() && networkInterfaceNextElement.isUp()) {
                        Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress inetAddressNextElement = inetAddresses.nextElement();
                            if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress() && !inetAddressNextElement.isMulticastAddress() && !inetAddressNextElement.isAnyLocalAddress()) {
                                if (inetAddressNextElement instanceof Inet4Address) {
                                    hashSet.add(inetAddressNextElement.getHostAddress());
                                }
                                if (inetAddressNextElement instanceof Inet6Address) {
                                    String hostAddress = inetAddressNextElement.getHostAddress();
                                    if (hostAddress.contains("%")) {
                                        hostAddress = hostAddress.substring(0, hostAddress.indexOf("%"));
                                    }
                                    hashSet2.add(hostAddress);
                                }
                            }
                        }
                    }
                }
                if (hashSet.size() > 0) {
                    Object[] array = hashSet.toArray();
                    int iMin = Math.min(array.length, 5);
                    for (int i2 = 0; i2 < iMin; i2++) {
                        sb.append((String) array[i2]);
                        if (i2 < iMin - 1) {
                            sb.append("-");
                        }
                    }
                    sb3.append("&private_ip=".concat(String.valueOf(sb)));
                    jSONObject.put("privateIp", sb.toString());
                }
                if (hashSet2.size() > 0) {
                    Object[] array2 = hashSet2.toArray();
                    int iMin2 = Math.min(array2.length, 5);
                    for (i = 0; i < iMin2; i++) {
                        sb2.append((String) array2[i]);
                        if (i < iMin2 - 1) {
                            sb2.append("-");
                        }
                    }
                    sb3.append("&private_ip_v6=".concat(String.valueOf(sb2)));
                    jSONObject.put("privateIp_v6", sb2.toString());
                }
                return sb.length() != 0 ? jSONObject.toString() : "{\"privateIp\":\"0.0.0.0\"}";
            } catch (Exception unused) {
                return "{\"privateIp\":\"0.0.0.0\"}";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
