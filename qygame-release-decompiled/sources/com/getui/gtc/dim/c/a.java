package com.getui.gtc.dim.c;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Base64;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.getui.gtc.base.annotation.MutableMethod;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dim.c.d;
import com.igexin.assist.util.AssistUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final Map<String, String> c = new HashMap<String, String>() { // from class: com.getui.gtc.dim.c.a.1
        {
            put(AssistUtils.BRAND_HW, "ro.build.version.emui");
            put(AssistUtils.BRAND_HON, "ro.build.version.magic#ro.build.version.emui");
            put(AssistUtils.BRAND_XIAOMI, "ro.build.version.incremental");
            put("redmi", "ro.build.version.incremental");
            put("blackshark", "ro.build.version.incremental");
            put("samsang", "ro.build.version.incremental");
            put(AssistUtils.BRAND_VIVO, "ro.vivo.os.version");
            put(AssistUtils.BRAND_OPPO, "ro.build.version.opporom#ro.build.version.oplusrom");
            put(AssistUtils.BRAND_MZ, "ro.build.display.id");
            put("lenovo", "ro.build.version.incremental");
            put("smartisan", "ro.modversion");
            put("htc", "ro.build.sense.version");
            put("oneplus", "ro.rom.version");
            put("yunos", "ro.cta.yunos.version");
            put("360", "ro.build.uiversion");
            put("nubia", "ro.build.rom.internal.id");
        }
    };
    public static final Map<String, String> a = new HashMap();
    private static final Map<String, String> d = new HashMap<String, String>() { // from class: com.getui.gtc.dim.c.a.2
        {
            put(AssistUtils.BRAND_HW, "com.android.permission.GET_INSTALLED_APP");
            put(AssistUtils.BRAND_HON, "com.android.permission.GET_INSTALLED_APPS");
        }
    };
    public static final Map<String, String> b = new HashMap();

    /* JADX INFO: renamed from: com.getui.gtc.dim.c.a$a, reason: collision with other inner class name */
    class ServiceConnectionC0014a implements ServiceConnection {
        boolean a = false;
        final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue<>(1);

        ServiceConnectionC0014a() {
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.b.put(iBinder);
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.a(th);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    class b implements IInterface {
        private final IBinder a;

        public b(IBinder iBinder) {
            this.a = iBinder;
        }

        public final String a() throws RemoteException {
            String string;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    string = parcelObtain2.readString();
                } catch (Exception e) {
                    com.getui.gtc.dim.e.b.a(e);
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    string = null;
                }
                return string;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this.a;
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static Location a(Context context, String str) {
        try {
            com.getui.gtc.dim.e.c.a(context, "network".equals(str) ? "android.permission.ACCESS_COARSE_LOCATION" : "android.permission.ACCESS_FINE_LOCATION", true);
            return ((LocationManager) context.getSystemService("location")).getLastKnownLocation(str);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    @MutableMethod
    @SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a() throws Throwable {
        Process processA;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Throwable th;
        StringBuilder sb;
        try {
            try {
                sb = new StringBuilder();
                processA = com.getui.gtc.dim.e.c.a(new String(Base64.decode("aXAgYWRkcg==", 0)));
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable unused) {
                    }
                }
                if (processA != null) {
                    throw th;
                }
                try {
                    processA.destroy();
                    throw th;
                } catch (Throwable unused2) {
                    throw th;
                }
            }
            try {
                bufferedReader2 = new BufferedReader(new InputStreamReader(processA.getInputStream()));
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        if (Pattern.matches("^\\d+: ((wlan\\d+)|(eth\\d+)): .*", line)) {
                            String strSubstring = line.substring(line.indexOf(": ") + 2);
                            sb.append(sb.length() == 0 ? "" : com.igexin.push.core.b.an);
                            sb.append(strSubstring.substring(0, strSubstring.indexOf(": ")));
                            sb.append("#");
                            String line2 = bufferedReader2.readLine();
                            if (line2 != null) {
                                sb.append(line2.substring(line2.indexOf("link/ether ") + 11, line2.indexOf(" brd")));
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        com.getui.gtc.dim.e.b.a(th);
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (Throwable unused3) {
                            }
                        }
                        if (processA != null) {
                            return "";
                        }
                        try {
                            processA.destroy();
                            return "";
                        } catch (Throwable unused4) {
                            return "";
                        }
                    }
                }
                String string = sb.toString();
                try {
                    bufferedReader2.close();
                } catch (Throwable unused5) {
                }
                if (processA != null) {
                    try {
                        processA.destroy();
                    } catch (Throwable unused6) {
                    }
                }
                return string;
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
                th = th;
                if (bufferedReader != null) {
                }
                if (processA != null) {
                }
            }
        } catch (Throwable th5) {
            th = th5;
            processA = null;
            bufferedReader = null;
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String a(int i, Context context) {
        String deviceId;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new RuntimeException("can not get imei above 29");
            }
            if (AssistUtils.BRAND_VIVO.equalsIgnoreCase(b()) && Build.VERSION.SDK_INT < 26) {
                throw new RuntimeException("do not get imei from vivo below 29");
            }
            if (Build.VERSION.SDK_INT < 23) {
                return "";
            }
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (telephonyManager == null || i < 0 || (deviceId = telephonyManager.getDeviceId(i)) == null) ? "" : deviceId;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String a(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new RuntimeException("can not get imei above 29");
            }
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            return !TextUtils.isEmpty(deviceId) ? deviceId : "";
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String a(Context context, boolean z) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("can not get oaid at main thread");
            }
            d.a();
            if (d.a != null && context != null) {
                d.b = context.getApplicationContext();
                if (d.b()) {
                    d.c = d.a.c(d.b);
                }
            }
            String strC = d.c ? d.c() : null;
            if (!"HONOR".equals(d.d)) {
                return strC;
            }
            String strB = b(context, z);
            if (strB == null) {
                strB = "";
            }
            return strB + '#' + strC;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String b() {
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String b(int i, Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new RuntimeException("can not get imsi above 29");
            }
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            Object objA = com.getui.gtc.dim.e.c.a(i, "getSubscriberId", context);
            return objA != null ? (String) objA : "";
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String b(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new RuntimeException("can not get imsi above 29");
            }
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            return !TextUtils.isEmpty(subscriberId) ? subscriberId : "";
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String b(Context context, String str) {
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_WIFI_STATE", true);
            if (!com.getui.gtc.dim.e.c.c(context)) {
                return "2##";
            }
            int i = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getDhcpInfo().gateway;
            String strB = com.getui.gtc.dim.e.c.b((i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255));
            return "1#" + str.replace("\"", "") + "#" + strB;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    private static String b(Context context, boolean z) {
        if (!z) {
            try {
                if (d.i.c()) {
                    com.getui.gtc.dim.e.b.a("support honor oaid");
                    return "";
                }
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.a(th);
                return "";
            }
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not get oaid at main thread");
        }
        d.h hVar = new d.h();
        if (!hVar.a(context)) {
            return "";
        }
        hVar.c(context);
        return hVar.b(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068  */
    @MutableMethod
    @SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int c(int i, Context context) throws Throwable {
        int subscriptionId;
        Cursor cursorQuery;
        int i2 = -1;
        Cursor cursor = null;
        try {
            try {
                com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
                try {
                    subscriptionId = Build.VERSION.SDK_INT >= 22 ? SubscriptionManager.from(context).getActiveSubscriptionInfoForSimSlotIndex(i).getSubscriptionId() : -1;
                } catch (Throwable unused) {
                }
            } catch (Throwable th) {
                th = th;
            }
            if (subscriptionId != -1) {
                cursorQuery = null;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return subscriptionId;
            }
            try {
                throw new RuntimeException("invalid subId");
            } catch (Throwable unused2) {
                i2 = subscriptionId;
                cursorQuery = context.getContentResolver().query(Uri.parse("content://telephony/siminfo"), new String[]{APEZProvider.FILEID, "sim_id"}, "sim_id = ?", new String[]{String.valueOf(i)}, null);
                if (cursorQuery != null) {
                    try {
                        subscriptionId = cursorQuery.moveToFirst() ? cursorQuery.getInt(cursorQuery.getColumnIndex(APEZProvider.FILEID)) : i2;
                        if (cursorQuery != null) {
                        }
                    } catch (Throwable th2) {
                        cursor = cursorQuery;
                        th = th2;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                return subscriptionId;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @MutableMethod
    public static String c() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String c(Context context) {
        String str;
        str = "";
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            str = TextUtils.isEmpty(simSerialNumber) ? "" : simSerialNumber;
            if (!TextUtils.isEmpty(str)) {
                if (str.length() < 20) {
                    return "";
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
        }
        return str;
    }

    @MutableMethod
    public static String d() {
        try {
            String strB = b();
            if (!TextUtils.isEmpty(strB)) {
                String lowerCase = strB.toLowerCase();
                if (a.containsKey(lowerCase)) {
                    return com.getui.gtc.dim.e.c.a(a.get(lowerCase), "");
                }
                if (c.containsKey(lowerCase)) {
                    return com.getui.gtc.dim.e.c.a(c.get(lowerCase), "");
                }
            }
            String strE = e();
            if (TextUtils.isEmpty(strE)) {
                return "";
            }
            String lowerCase2 = strE.toLowerCase();
            return a.containsKey(lowerCase2) ? com.getui.gtc.dim.e.c.a(a.get(lowerCase2), "") : c.containsKey(lowerCase2) ? com.getui.gtc.dim.e.c.a(c.get(lowerCase2), "") : "";
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String d(int i, Context context) {
        String str;
        str = "";
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            Object objA = com.getui.gtc.dim.e.c.a(i, "getSimSerialNumber", context);
            str = objA != null ? (String) objA : "";
            if (!TextUtils.isEmpty(str)) {
                if (str.length() < 20) {
                    return "";
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
        }
        return str;
    }

    @MutableMethod
    public static String d(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String e() {
        try {
            return Build.MANUFACTURER;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String e(Context context) {
        try {
            if (CommonUtil.isMainThread()) {
                throw new RuntimeException("cannot get advertisingId from main thread");
            }
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            ServiceConnectionC0014a serviceConnectionC0014a = new ServiceConnectionC0014a();
            if (!context.bindService(intent, serviceConnectionC0014a, 1)) {
                throw new IOException("Google Play connection failed");
            }
            try {
                if (serviceConnectionC0014a.a) {
                    throw new IllegalStateException();
                }
                serviceConnectionC0014a.a = true;
                return new b(serviceConnectionC0014a.b.poll(3000L, TimeUnit.MILLISECONDS)).a();
            } finally {
                context.unbindService(serviceConnectionC0014a);
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String f() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String f(Context context) {
        Object objInvoke;
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.READ_PHONE_STATE", true);
            if (Build.VERSION.SDK_INT < 26) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                objInvoke = cls.getMethod("get", String.class).invoke(cls, "ro.serialno");
            } else {
                if (Build.VERSION.SDK_INT >= 29) {
                    throw new RuntimeException("can not get serialnumber above 29");
                }
                Class<?> cls2 = Class.forName("android.os.Build");
                objInvoke = cls2.getMethod("getSerial", new Class[0]).invoke(cls2, new Object[0]);
            }
            return (String) objInvoke;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return null;
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String g(Context context) {
        byte[] hardwareAddress;
        String string = "";
        try {
            if (Build.VERSION.SDK_INT < 23) {
                com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_WIFI_STATE", true);
                return ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
            }
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if ("wlan0".equalsIgnoreCase(networkInterfaceNextElement.getName()) && (hardwareAddress = networkInterfaceNextElement.getHardwareAddress()) != null && hardwareAddress.length != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : hardwareAddress) {
                        sb.append(String.format("%02X:", Byte.valueOf(b2)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    string = sb.toString();
                }
            }
            return string;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static List<PackageInfo> g() throws Throwable {
        Process processA;
        BufferedReader bufferedReader = null;
        try {
            try {
                if (Build.VERSION.SDK_INT >= 33) {
                    throw new RuntimeException("can not get al by pm above 33");
                }
                ArrayList arrayList = new ArrayList();
                processA = com.getui.gtc.dim.e.c.a(new String(Base64.decode("cG0gbGlzdCBwYWNrYWdlcw==", 0)));
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(processA.getInputStream()));
                    while (true) {
                        try {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line != null) {
                                    try {
                                        arrayList.add(com.getui.gtc.dim.e.d.a(line.split(":")[1], 0));
                                    } catch (Throwable unused) {
                                    }
                                } else {
                                    try {
                                        break;
                                    } catch (Throwable unused2) {
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = bufferedReader2;
                            com.getui.gtc.dim.e.b.a(th);
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable unused3) {
                                }
                            }
                            if (processA != null) {
                                try {
                                    processA.destroy();
                                } catch (Throwable unused4) {
                                }
                            }
                            return Collections.emptyList();
                        }
                    }
                    bufferedReader2.close();
                    if (processA != null) {
                        try {
                            processA.destroy();
                        } catch (Throwable unused5) {
                        }
                    }
                    return arrayList;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
            processA = null;
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (Throwable unused6) {
            }
        }
        if (processA == null) {
            throw th;
        }
        try {
            processA.destroy();
            throw th;
        } catch (Throwable unused7) {
            throw th;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String h(Context context) {
        try {
            String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            if (TextUtils.isEmpty(simOperator)) {
                return "";
            }
            byte b2 = -1;
            int iHashCode = simOperator.hashCode();
            if (iHashCode != 49679479) {
                if (iHashCode != 49679502) {
                    switch (iHashCode) {
                        case 49679470:
                            if (simOperator.equals("46000")) {
                                b2 = 0;
                            }
                            break;
                        case 49679471:
                            if (simOperator.equals("46001")) {
                                b2 = 4;
                            }
                            break;
                        case 49679472:
                            if (simOperator.equals("46002")) {
                                b2 = 1;
                            }
                            break;
                        case 49679473:
                            if (simOperator.equals("46003")) {
                                b2 = 7;
                            }
                            break;
                        case 49679474:
                            if (simOperator.equals("46004")) {
                                b2 = 2;
                            }
                            break;
                        case 49679475:
                            if (simOperator.equals("46005")) {
                                b2 = 8;
                            }
                            break;
                        case 49679476:
                            if (simOperator.equals("46006")) {
                                b2 = 5;
                            }
                            break;
                        case 49679477:
                            if (simOperator.equals("46007")) {
                                b2 = 3;
                            }
                            break;
                    }
                } else if (simOperator.equals("46011")) {
                    b2 = 9;
                }
            } else if (simOperator.equals("46009")) {
                b2 = 6;
            }
            switch (b2) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return "中国移动";
                case 4:
                case 5:
                case 6:
                    return "中国联通";
                case 7:
                case 8:
                case 9:
                    return "中国电信";
                default:
                    return simOperator;
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static List<PackageInfo> h() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("can not get al by us at main thread");
            }
            ArrayList arrayList = new ArrayList();
            for (int i = com.igexin.push.config.c.d; i <= 19999; i++) {
                PackageInfo packageInfoA = com.getui.gtc.dim.e.d.a(i);
                if (packageInfoA != null) {
                    arrayList.add(packageInfoA);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return Collections.emptyList();
        }
    }

    @Deprecated
    public static String i() {
        return "";
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String i(Context context) {
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_NETWORK_STATE", true);
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                throw new IllegalStateException("getSystemService: CONNECTIVITY_SERVICE failed");
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                throw new IllegalStateException("getActiveNetworkInfo failed");
            }
            if (!activeNetworkInfo.isAvailable()) {
                throw new IllegalStateException("no available activeNetwork");
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return "WIFI";
            }
            int subtype = activeNetworkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                subtype = telephonyManager.getNetworkType();
            }
            if (subtype == 20) {
                return "5G";
            }
            switch (subtype) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    return "NULL";
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "NULL";
        }
    }

    @MutableMethod
    public static String j(Context context) {
        try {
            if (!com.getui.gtc.dim.e.c.a(context)) {
                throw new IllegalStateException("network not connected");
            }
            boolean zB = com.getui.gtc.dim.e.c.b(context);
            boolean zC = com.getui.gtc.dim.e.c.c(context);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if ((zB && networkInterfaceNextElement.getName().toLowerCase().contains("rmnet")) || (zC && networkInterfaceNextElement.getName().toLowerCase().contains("wlan0"))) {
                    List<InterfaceAddress> interfaceAddresses = networkInterfaceNextElement.getInterfaceAddresses();
                    ArrayList arrayList3 = new ArrayList();
                    Iterator<InterfaceAddress> it = interfaceAddresses.iterator();
                    while (it.hasNext()) {
                        InetAddress address = it.next().getAddress();
                        if (!address.isLoopbackAddress()) {
                            arrayList3.add(address.getHostAddress());
                        }
                    }
                    if (zB) {
                        arrayList.addAll(arrayList3);
                    }
                    if (zC) {
                        arrayList2.addAll(arrayList3);
                    }
                }
            }
            if (zB) {
                StringBuilder sb = new StringBuilder();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    sb.append((String) it2.next());
                    sb.append(com.igexin.push.core.b.an);
                }
                if (sb.toString().endsWith(com.igexin.push.core.b.an)) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                return sb.toString();
            }
            if (!zC) {
                return "";
            }
            StringBuilder sb2 = new StringBuilder();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                sb2.append((String) it3.next());
                sb2.append(com.igexin.push.core.b.an);
            }
            if (sb2.toString().endsWith(com.igexin.push.core.b.an)) {
                sb2.deleteCharAt(sb2.length() - 1);
            }
            return sb2.toString();
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    public static String k(Context context) {
        try {
            if (!com.getui.gtc.dim.e.c.a(context)) {
                throw new IllegalStateException("network not connected");
            }
            boolean zB = com.getui.gtc.dim.e.c.b(context);
            boolean zC = com.getui.gtc.dim.e.c.c(context);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                String lowerCase = networkInterfaceNextElement.getName().toLowerCase();
                if ((zB && (lowerCase.contains("rmnet") || lowerCase.contains("ccmni"))) || (zC && lowerCase.contains("wlan0"))) {
                    List<InterfaceAddress> interfaceAddresses = networkInterfaceNextElement.getInterfaceAddresses();
                    ArrayList arrayList3 = new ArrayList();
                    boolean z = false;
                    Iterator<InterfaceAddress> it = interfaceAddresses.iterator();
                    while (it.hasNext()) {
                        InetAddress address = it.next().getAddress();
                        if (!address.isLoopbackAddress()) {
                            if (address instanceof Inet6Address) {
                                arrayList3.add(address.getHostAddress());
                            } else if (address instanceof Inet4Address) {
                                z = true;
                            }
                        }
                    }
                    if (z) {
                        if (zB) {
                            arrayList.addAll(arrayList3);
                        }
                        if (zC) {
                            arrayList2.addAll(arrayList3);
                        }
                    }
                }
            }
            if (zB) {
                StringBuilder sb = new StringBuilder();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    sb.append((String) it2.next());
                    sb.append(com.igexin.push.core.b.an);
                }
                if (sb.toString().endsWith(com.igexin.push.core.b.an)) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                return sb.toString();
            }
            if (!zC) {
                return "";
            }
            StringBuilder sb2 = new StringBuilder();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                sb2.append((String) it3.next());
                sb2.append(com.igexin.push.core.b.an);
            }
            if (sb2.toString().endsWith(com.igexin.push.core.b.an)) {
                sb2.deleteCharAt(sb2.length() - 1);
            }
            return sb2.toString();
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return "";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static WifiInfo l(Context context) {
        try {
            com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_WIFI_STATE", true);
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                try {
                    Field declaredField = WifiInfo.class.getDeclaredField("mIpAddress");
                    declaredField.setAccessible(true);
                    declaredField.set(connectionInfo, null);
                } catch (Throwable th) {
                    com.getui.gtc.dim.e.b.a(th);
                }
            }
            return connectionInfo;
        } catch (Throwable th2) {
            com.getui.gtc.dim.e.b.a(th2);
            return null;
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static List<ScanResult> m(Context context) {
        try {
            if (CommonUtil.isMainThread()) {
                throw new IllegalStateException("cannot get wifi list from the main thread");
            }
            com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_FINE_LOCATION", true);
            List<ScanResult> scanResults = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getScanResults();
            if (scanResults == null || scanResults.size() <= 0) {
                return null;
            }
            return scanResults;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [int] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.lang.StringBuilder] */
    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String n(Context context) {
        int systemId;
        int i;
        int baseStationId;
        ?? r7;
        boolean z;
        ?? r72;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_FINE_LOCATION", true);
            } else if (!com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_COARSE_LOCATION") && !com.getui.gtc.dim.e.c.a(context, "android.permission.ACCESS_FINE_LOCATION")) {
                throw new IllegalStateException("permission coarse/fine location not granted");
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            List list = null;
            if (telephonyManager.getSimState() == 5) {
                String networkOperator = telephonyManager.getNetworkOperator();
                if (networkOperator == null || networkOperator.length() < 3) {
                    systemId = 0;
                    i = 0;
                } else {
                    i = Integer.parseInt(networkOperator.substring(0, 3));
                    systemId = Integer.parseInt(networkOperator.substring(3));
                }
                try {
                    CellLocation cellLocation = telephonyManager.getCellLocation();
                    z = cellLocation instanceof GsmCellLocation;
                    try {
                        if (z) {
                            int lac = ((GsmCellLocation) cellLocation).getLac();
                            baseStationId = ((GsmCellLocation) cellLocation).getCid();
                            r72 = lac;
                        } else if (cellLocation instanceof CdmaCellLocation) {
                            int networkId = ((CdmaCellLocation) cellLocation).getNetworkId();
                            if (systemId == 0) {
                                systemId = ((CdmaCellLocation) cellLocation).getSystemId();
                            }
                            baseStationId = ((CdmaCellLocation) cellLocation).getBaseStationId();
                            r72 = networkId;
                        } else {
                            baseStationId = 0;
                            r72 = 0;
                        }
                    } catch (Throwable th) {
                        th = th;
                        com.getui.gtc.dim.e.b.a(th);
                        baseStationId = 0;
                        r72 = z;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z = false;
                }
                if (Build.VERSION.SDK_INT >= 29) {
                    r7 = r72;
                } else {
                    list = (List) telephonyManager.getClass().getMethod("getNeighboringCellInfo", new Class[0]).invoke(telephonyManager, new Object[0]);
                    r7 = r72;
                }
            } else {
                systemId = 0;
                i = 0;
                baseStationId = 0;
                r7 = 0;
            }
            ?? sb = new StringBuilder();
            sb.append(i);
            sb.append("|");
            sb.append(systemId);
            sb.append("|");
            sb.append(r7);
            sb.append("|");
            sb.append(baseStationId);
            sb.append("|");
            for (int i2 = 0; list != null && i2 < list.size(); i2++) {
                sb.append(((NeighboringCellInfo) list.get(i2)).getCid());
                if (i2 < list.size() - 1) {
                    sb.append(com.igexin.push.core.b.an);
                }
            }
            return sb.toString();
        } catch (Throwable th3) {
            com.getui.gtc.dim.e.b.a(th3);
            return "0|0|0|0|";
        }
    }

    @MutableMethod
    @SuppressLint({"MissingPermission"})
    public static String o(Context context) {
        int i;
        int mcc;
        int mnc;
        int tac;
        long ci;
        int i2;
        try {
            if (Build.VERSION.SDK_INT < 17) {
                throw new RuntimeException("device version < 4.2, won't make sense");
            }
            int i3 = 1;
            if (!CommonUtil.hasPermission(context, Build.VERSION.SDK_INT >= 29 && context.getApplicationInfo().targetSdkVersion >= 29 ? "android.permission.ACCESS_FINE_LOCATION" : "android.permission.ACCESS_COARSE_LOCATION", true)) {
                throw new RuntimeException("not has location permission");
            }
            List<CellInfo> allCellInfo = ((TelephonyManager) context.getSystemService("phone")).getAllCellInfo();
            if (allCellInfo != null && !allCellInfo.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                long jLongValue = 0;
                int systemId = 0;
                int networkId = 0;
                int i4 = 0;
                int mcc2 = 0;
                for (CellInfo cellInfo : allCellInfo) {
                    if (cellInfo.isRegistered()) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
                            mcc2 = cellIdentity.getMcc();
                            systemId = cellIdentity.getMnc();
                            networkId = cellIdentity.getLac();
                            jLongValue = cellIdentity.getCid();
                            i4 = i3;
                        } else {
                            if (cellInfo instanceof CellInfoCdma) {
                                CellIdentityCdma cellIdentity2 = ((CellInfoCdma) cellInfo).getCellIdentity();
                                systemId = cellIdentity2.getSystemId();
                                networkId = cellIdentity2.getNetworkId();
                                jLongValue = cellIdentity2.getBasestationId();
                                i2 = 2;
                            } else {
                                if (Build.VERSION.SDK_INT >= 18 && (cellInfo instanceof CellInfoWcdma)) {
                                    CellIdentityWcdma cellIdentity3 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                                    mcc = cellIdentity3.getMcc();
                                    mnc = cellIdentity3.getMnc();
                                    tac = cellIdentity3.getLac();
                                    ci = cellIdentity3.getCid();
                                    i2 = 4;
                                } else if (cellInfo instanceof CellInfoLte) {
                                    CellIdentityLte cellIdentity4 = ((CellInfoLte) cellInfo).getCellIdentity();
                                    mcc = cellIdentity4.getMcc();
                                    mnc = cellIdentity4.getMnc();
                                    tac = cellIdentity4.getTac();
                                    ci = cellIdentity4.getCi();
                                    i2 = 3;
                                } else if ("android.telephony.CellInfoNr".equals(cellInfo.getClass().getName())) {
                                    try {
                                        Method method = Class.forName("android.telephony.CellInfoNr").getMethod("getCellIdentity", new Class[0]);
                                        Class<?> cls = Class.forName("android.telephony.CellIdentityNr");
                                        Method method2 = cls.getMethod("getMccString", new Class[0]);
                                        Method method3 = cls.getMethod("getMncString", new Class[0]);
                                        Method method4 = cls.getMethod("getTac", new Class[0]);
                                        i = systemId;
                                        try {
                                            Method method5 = cls.getMethod("getNci", new Class[0]);
                                            Object objInvoke = method.invoke(cellInfo, new Object[0]);
                                            String str = (String) method2.invoke(objInvoke, new Object[0]);
                                            String str2 = (String) method3.invoke(objInvoke, new Object[0]);
                                            int i5 = Integer.parseInt(str);
                                            try {
                                                int i6 = Integer.parseInt(str2);
                                                try {
                                                    int iIntValue = ((Integer) method4.invoke(objInvoke, new Object[0])).intValue();
                                                    try {
                                                        jLongValue = ((Long) method5.invoke(objInvoke, new Object[0])).longValue();
                                                        i4 = 6;
                                                        networkId = iIntValue;
                                                        systemId = i6;
                                                        mcc2 = i5;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        networkId = iIntValue;
                                                        i = i6;
                                                        mcc2 = i5;
                                                        com.getui.gtc.dim.e.b.b(th);
                                                        systemId = i;
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                            }
                                        } catch (Throwable th4) {
                                            th = th4;
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        i = systemId;
                                    }
                                }
                                mcc2 = mcc;
                                systemId = mnc;
                                networkId = tac;
                                jLongValue = ci;
                            }
                            i4 = i2;
                        }
                        if (sb.length() != 0) {
                            sb.append(com.igexin.push.core.b.an);
                        }
                        sb.append(mcc2);
                        sb.append("|");
                        sb.append(systemId);
                        sb.append("|");
                        sb.append(networkId);
                        sb.append("|");
                        sb.append(jLongValue);
                        sb.append("|");
                        sb.append(i4);
                        i3 = 1;
                    }
                }
                return sb.length() > 0 ? sb.toString() : "0|0|0|0|0";
            }
            return "0|0|0|0|0";
        } catch (Throwable th6) {
            com.getui.gtc.dim.e.b.a(th6);
            return "0|0|0|0|0";
        }
    }

    @MutableMethod
    public static List<PackageInfo> p(Context context) {
        try {
            ArrayList arrayList = new ArrayList();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (listQueryIntentActivities.size() > 0) {
                Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
                while (it.hasNext()) {
                    try {
                        arrayList.add(com.getui.gtc.dim.e.d.a(it.next().activityInfo.packageName, 0));
                    } catch (Throwable unused) {
                    }
                }
            }
            return arrayList;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return Collections.emptyList();
        }
    }

    @MutableMethod
    public static List<PackageInfo> q(Context context) {
        String str;
        try {
            String lowerCase = b().toLowerCase();
            if (b.containsKey(lowerCase)) {
                str = b.get(lowerCase);
            } else {
                if (!d.containsKey(lowerCase)) {
                    throw new RuntimeException("not support brand: ".concat(String.valueOf(lowerCase)));
                }
                str = d.get(lowerCase);
            }
            com.getui.gtc.dim.e.c.a(context, str, false);
            PackageManager packageManager = context.getPackageManager();
            return (List) packageManager.getClass().getDeclaredMethod(new String(Base64.decode("Z2V0SW5zdGFsbGVkUGFja2FnZXM=", 0)), Integer.TYPE).invoke(packageManager, 5);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return Collections.emptyList();
        }
    }

    @MutableMethod
    public static List<PackageInfo> r(Context context) {
        String[] list;
        File parentFile;
        try {
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            throw new RuntimeException("can not get localDirs above 29");
        }
        File externalCacheDir = context.getExternalCacheDir();
        File parentFile2 = null;
        if (externalCacheDir != null && (parentFile = externalCacheDir.getParentFile()) != null) {
            parentFile2 = parentFile.getParentFile();
        }
        if (parentFile2 != null && parentFile2.isDirectory() && (list = parentFile2.list(new FilenameFilter() { // from class: com.getui.gtc.dim.c.a.3
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                try {
                    if (file.isDirectory()) {
                        return str.contains(".");
                    }
                    return false;
                } catch (Throwable unused) {
                    return false;
                }
            }
        })) != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : list) {
                try {
                    arrayList.add(com.getui.gtc.dim.e.d.a(str, 0));
                } catch (Throwable unused) {
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }
}
