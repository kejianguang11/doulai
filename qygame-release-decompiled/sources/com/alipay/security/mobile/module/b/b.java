package com.alipay.security.mobile.module.b;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import androidx.core.app.NotificationCompat;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static b a = new b();

    private b() {
    }

    private static String A() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return inetAddressNextElement.getHostAddress().toString();
                    }
                }
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static b a() {
        return a;
    }

    private static String a(BluetoothAdapter bluetoothAdapter) {
        try {
            Field declaredField = BluetoothAdapter.class.getDeclaredField("mService");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(bluetoothAdapter);
            if (obj == null) {
                return null;
            }
            Method declaredMethod = obj.getClass().getDeclaredMethod("getAddress", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(obj, new Object[0]);
            if (objInvoke != null && (objInvoke instanceof String)) {
                return (String) objInvoke;
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    public static String a(Context context) {
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        String deviceId = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();
                }
            } catch (Throwable unused) {
            }
        }
        return deviceId == null ? "" : deviceId;
    }

    private static boolean a(Context context, String str) {
        return !(context.getPackageManager().checkPermission(str, context.getPackageName()) == 0);
    }

    public static String b() {
        long availableBlocks;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            availableBlocks = 0;
        }
        return String.valueOf(availableBlocks);
    }

    public static String b(Context context) {
        String subscriberId = "";
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    subscriberId = telephonyManager.getSubscriberId();
                }
            } catch (Throwable unused) {
            }
        }
        return subscriberId == null ? "" : subscriberId;
    }

    public static String c() {
        long blockSize = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(com.alipay.security.mobile.module.a.a.a().getPath());
                blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
            }
        } catch (Throwable unused) {
        }
        return String.valueOf(blockSize);
    }

    public static String c(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Throwable unused) {
            i = 0;
        }
        return i == 1 ? "1" : "0";
    }

    public static String d() {
        return "";
    }

    public static String d(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            int i = audioManager.getRingerMode() == 0 ? 1 : 0;
            int streamVolume = audioManager.getStreamVolume(0);
            int streamVolume2 = audioManager.getStreamVolume(1);
            int streamVolume3 = audioManager.getStreamVolume(2);
            int streamVolume4 = audioManager.getStreamVolume(3);
            int streamVolume5 = audioManager.getStreamVolume(4);
            jSONObject.put("ringermode", String.valueOf(i));
            jSONObject.put("call", String.valueOf(streamVolume));
            jSONObject.put("system", String.valueOf(streamVolume2));
            jSONObject.put("ring", String.valueOf(streamVolume3));
            jSONObject.put("music", String.valueOf(streamVolume4));
            jSONObject.put(NotificationCompat.CATEGORY_ALARM, String.valueOf(streamVolume5));
        } catch (Throwable unused) {
        }
        return jSONObject.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
    
        r0 = r6.substring(r6.indexOf(":") + 1, r6.length()).trim();
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007f A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String e() throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        String strTrim = "0000000000000000";
        LineNumberReader lineNumberReader = null;
        try {
            fileInputStream = new FileInputStream(new File("/proc/cpuinfo"));
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    LineNumberReader lineNumberReader2 = new LineNumberReader(inputStreamReader);
                    int i = 1;
                    while (true) {
                        if (i < 100) {
                            try {
                                String line = lineNumberReader2.readLine();
                                if (line != null) {
                                    if (line.indexOf("Serial") >= 0) {
                                        break;
                                    }
                                    i++;
                                }
                            } catch (Throwable unused) {
                                lineNumberReader = lineNumberReader2;
                                if (lineNumberReader != null) {
                                    try {
                                        lineNumberReader.close();
                                    } catch (Throwable unused2) {
                                    }
                                }
                                if (inputStreamReader != null) {
                                    try {
                                        inputStreamReader.close();
                                    } catch (Throwable unused3) {
                                    }
                                }
                                if (fileInputStream != null) {
                                    break;
                                }
                                if (strTrim != null) {
                                }
                            }
                        }
                    }
                    try {
                        lineNumberReader2.close();
                    } catch (Throwable unused4) {
                    }
                    try {
                        inputStreamReader.close();
                        break;
                    } catch (Throwable unused5) {
                    }
                } catch (Throwable unused6) {
                }
            } catch (Throwable unused7) {
                inputStreamReader = null;
            }
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
            inputStreamReader = null;
        }
        try {
            fileInputStream.close();
        } catch (Throwable unused8) {
        }
        return strTrim != null ? "" : strTrim;
    }

    public static String e(Context context) {
        String networkOperatorName = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    networkOperatorName = telephonyManager.getNetworkOperatorName();
                }
            } catch (Throwable unused) {
            }
        }
        return (networkOperatorName == null || "null".equals(networkOperatorName)) ? "" : networkOperatorName;
    }

    public static String f(Context context) {
        List<Sensor> sensorList;
        String strE = null;
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Sensor sensor : sensorList) {
                        sb.append(sensor.getName());
                        sb.append(sensor.getVersion());
                        sb.append(sensor.getVendor());
                    }
                    strE = com.alipay.security.mobile.module.a.a.e(sb.toString());
                }
            } catch (Throwable unused) {
            }
        }
        return strE == null ? "" : strE;
    }

    public static String g() throws Throwable {
        String strX = x();
        return !com.alipay.security.mobile.module.a.a.a(strX) ? strX : y();
    }

    public static String g(Context context) {
        List<Sensor> sensorList;
        JSONArray jSONArray = new JSONArray();
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                    for (Sensor sensor : sensorList) {
                        if (sensor != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("name", sensor.getName());
                            jSONObject.put("version", sensor.getVersion());
                            jSONObject.put(Constant.LOGIN_ACTIVITY_VENDOR_KEY, sensor.getVendor());
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return jSONArray.toString();
    }

    public static String h() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        FileReader fileReader;
        String[] strArrSplit;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                fileReader = new FileReader("/proc/cpuinfo");
                try {
                    bufferedReader = new BufferedReader(fileReader);
                } catch (Throwable th2) {
                    bufferedReader = null;
                    th = th2;
                }
            } catch (Throwable unused) {
                return "";
            }
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            fileReader = null;
        }
        try {
            strArrSplit = bufferedReader.readLine().split(":\\s+", 2);
        } catch (Throwable unused2) {
            bufferedReader2 = bufferedReader;
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Throwable unused3) {
                }
            }
            if (bufferedReader2 == null) {
                return "";
            }
            bufferedReader2.close();
        }
        if (strArrSplit == null || strArrSplit.length <= 1) {
            try {
                fileReader.close();
            } catch (Throwable unused4) {
            }
            bufferedReader.close();
            return "";
        }
        String str = strArrSplit[1];
        try {
            fileReader.close();
        } catch (Throwable unused5) {
        }
        try {
            bufferedReader.close();
        } catch (Throwable unused6) {
        }
        return str;
    }

    public static String h(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return Integer.toString(displayMetrics.widthPixels) + "*" + Integer.toString(displayMetrics.heightPixels);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String i() throws Throwable {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                fileReader = new FileReader("/proc/meminfo");
                try {
                    bufferedReader = new BufferedReader(fileReader, 8192);
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
            }
            try {
                j = bufferedReader.readLine() != null ? Integer.parseInt(r1.split("\\s+")[1]) : 0L;
                try {
                    fileReader.close();
                } catch (Throwable unused3) {
                }
                bufferedReader.close();
            } catch (Throwable th2) {
                th = th2;
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Throwable unused4) {
                    }
                }
                if (bufferedReader == null) {
                    throw th;
                }
                try {
                    bufferedReader.close();
                    throw th;
                } catch (Throwable unused5) {
                    throw th;
                }
            }
        } catch (Throwable unused6) {
            fileReader = null;
        }
        return String.valueOf(j);
    }

    public static String i(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.widthPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String j() {
        long blockCount;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            blockCount = 0;
        }
        return String.valueOf(blockCount);
    }

    public static String j(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.heightPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String k() {
        long blockSize = 0;
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
            }
        } catch (Throwable unused) {
        }
        return String.valueOf(blockSize);
    }

    public static String k(Context context) {
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress != null) {
                try {
                    if (macAddress.length() != 0 && !"02:00:00:00:00:00".equals(macAddress)) {
                        return macAddress;
                    }
                } catch (Throwable unused) {
                    return macAddress;
                }
            }
            return w();
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String l() {
        String str = "";
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), "gsm.version.baseband", "no message");
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    public static String l(Context context) {
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        String str = "";
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            if (simSerialNumber != null) {
                if (simSerialNumber == null) {
                    return simSerialNumber;
                }
                try {
                    if (simSerialNumber.length() != 0) {
                        return simSerialNumber;
                    }
                } catch (Throwable unused) {
                    return simSerialNumber;
                }
            }
            str = "";
        } catch (Throwable unused2) {
        }
        return str;
    }

    public static String m() {
        String str = "";
        try {
            str = Build.SERIAL;
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    public static String m(Context context) {
        String string;
        try {
            string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            string = "";
        }
        return string == null ? "" : string;
    }

    public static String n() {
        String string = "";
        try {
            string = Locale.getDefault().toString();
        } catch (Throwable unused) {
        }
        return string == null ? "" : string;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001f A[Catch: Throwable -> 0x002f, TryCatch #0 {Throwable -> 0x002f, blocks: (B:8:0x0011, B:10:0x0017, B:12:0x001f), top: B:19:0x0011 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String n(Context context) {
        if (a(context, "android.permission.BLUETOOTH")) {
            return "";
        }
        String strZ = z();
        if (strZ != null) {
            try {
                if (strZ.length() == 0 || "02:00:00:00:00:00".equals(strZ)) {
                    strZ = Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
                }
                if (strZ == null) {
                    return "";
                }
            } catch (Throwable unused) {
            }
        }
        return strZ;
    }

    public static String o() {
        String displayName = "";
        try {
            displayName = TimeZone.getDefault().getDisplayName(false, 0);
        } catch (Throwable unused) {
        }
        return displayName == null ? "" : displayName;
    }

    public static String o(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null ? String.valueOf(telephonyManager.getNetworkType()) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String p() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            StringBuilder sb = new StringBuilder();
            sb.append(jCurrentTimeMillis - (jCurrentTimeMillis % 1000));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String p(Context context) {
        String bssid = "";
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                bssid = wifiManager.getConnectionInfo().getBSSID();
            }
        } catch (Throwable unused) {
        }
        return bssid == null ? "" : bssid;
    }

    public static String q() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SystemClock.elapsedRealtime());
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String q(Context context) {
        try {
            String strT = t(context);
            String strA = A();
            if (!com.alipay.security.mobile.module.a.a.b(strT) || !com.alipay.security.mobile.module.a.a.b(strA)) {
                return "";
            }
            return strT + ":" + A();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String r() {
        try {
            StringBuilder sb = new StringBuilder();
            String[] strArr = {"/dev/qemu_pipe", "/dev/socket/qemud", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"};
            sb.append("00:");
            for (int i = 0; i < 7; i++) {
                sb.append(new File(strArr[i]).exists() ? "1" : "0");
            }
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String r(Context context) {
        try {
            long jMax = 0;
            if (!((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
                return "0:0";
            }
            String[] strArr = {"/data/system/password.key", "/data/system/gesture.key", "/data/system/gatekeeper.password.key", "/data/system/gatekeeper.gesture.key", "/data/system/gatekeeper.pattern.key"};
            for (int i = 0; i < 5; i++) {
                long jLastModified = -1;
                try {
                    jLastModified = new File(strArr[i]).lastModified();
                } catch (Throwable unused) {
                }
                jMax = Math.max(jLastModified, jMax);
            }
            return "1:" + jMax;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String s() {
        String[] strArr = {"dalvik.system.Taint"};
        StringBuilder sb = new StringBuilder();
        sb.append("00");
        sb.append(":");
        for (int i = 0; i <= 0; i++) {
            try {
                Class.forName(strArr[0]);
                sb.append("1");
            } catch (Throwable unused) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static String s(Context context) {
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = intentRegisterReceiver.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1);
            int intExtra2 = intentRegisterReceiver.getIntExtra("status", -1);
            boolean z = intExtra2 == 2 || intExtra2 == 5;
            StringBuilder sb = new StringBuilder();
            sb.append(z ? "1" : "0");
            sb.append(":");
            sb.append(intExtra);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String t() throws Throwable {
        StringBuilder sb = new StringBuilder();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("/system/build.prop", "ro.product.name=sdk");
        linkedHashMap.put("/proc/tty/drivers", "goldfish");
        linkedHashMap.put("/proc/cpuinfo", "goldfish");
        sb.append("00:");
        for (String str : linkedHashMap.keySet()) {
            LineNumberReader lineNumberReader = null;
            char c = '0';
            try {
                LineNumberReader lineNumberReader2 = new LineNumberReader(new InputStreamReader(new FileInputStream(str)));
                while (true) {
                    try {
                        String line = lineNumberReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.toLowerCase().contains((CharSequence) linkedHashMap.get(str))) {
                            c = '1';
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        lineNumberReader = lineNumberReader2;
                        sb.append('0');
                        if (lineNumberReader != null) {
                            try {
                                lineNumberReader.close();
                            } catch (Throwable unused) {
                            }
                        }
                        throw th;
                    }
                }
                sb.append(c);
                try {
                    lineNumberReader2.close();
                } catch (Throwable unused2) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return sb.toString();
    }

    private static String t(Context context) {
        if (a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return "";
        }
        String str = null;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == 1) {
                    return "WIFI";
                }
                if (activeNetworkInfo.getType() == 0) {
                    int subtype = activeNetworkInfo.getSubtype();
                    if (subtype != 4 && subtype != 1 && subtype != 2 && subtype != 7 && subtype != 11) {
                        if (subtype != 3 && subtype != 5 && subtype != 6 && subtype != 8 && subtype != 9 && subtype != 10 && subtype != 12 && subtype != 14 && subtype != 15) {
                            if (subtype == 13) {
                                return "4G";
                            }
                            str = "UNKNOW";
                        }
                        return "3G";
                    }
                    return "2G";
                }
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    public static String u() {
        StringBuilder sb = new StringBuilder();
        sb.append("00:");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("BRAND", "generic");
        linkedHashMap.put("BOARD", "unknown");
        linkedHashMap.put("DEVICE", "generic");
        linkedHashMap.put("HARDWARE", "goldfish");
        linkedHashMap.put("PRODUCT", "sdk");
        linkedHashMap.put("MODEL", "sdk");
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            try {
                String str2 = (String) Build.class.getField(str).get(null);
                String str3 = (String) linkedHashMap.get(str);
                String lowerCase = str2 != null ? str2.toLowerCase() : null;
                if (lowerCase != null && lowerCase.contains(str3)) {
                    c = '1';
                }
            } catch (Throwable unused) {
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String v() {
        StringBuilder sb = new StringBuilder();
        sb.append("00:");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("ro.hardware", "goldfish");
        linkedHashMap.put("ro.kernel.qemu", "1");
        linkedHashMap.put("ro.product.device", "generic");
        linkedHashMap.put("ro.product.model", "sdk");
        linkedHashMap.put("ro.product.brand", "generic");
        linkedHashMap.put("ro.product.name", "sdk");
        linkedHashMap.put("ro.build.fingerprint", "test-keys");
        linkedHashMap.put("ro.product.manufacturer", "unknow");
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            String str2 = (String) linkedHashMap.get(str);
            String strB = com.alipay.security.mobile.module.a.a.b(str, "");
            if (strB != null && strB.contains(str2)) {
                c = '1';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String w() {
        try {
            ArrayList<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (list == null) {
                return "02:00:00:00:00:00";
            }
            for (NetworkInterface networkInterface : list) {
                if (networkInterface != null && networkInterface.getName() != null && networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "02:00:00:00:00:00";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02X:", Integer.valueOf(b & 255)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "02:00:00:00:00:00";
        } catch (Throwable unused) {
            return "02:00:00:00:00:00";
        }
    }

    private static String x() throws Throwable {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            try {
                bufferedReader = new BufferedReader(fileReader, 8192);
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            fileReader = null;
        }
        try {
            String line = bufferedReader.readLine();
            if (!com.alipay.security.mobile.module.a.a.a(line)) {
                String strTrim = line.trim();
                try {
                    bufferedReader.close();
                } catch (Throwable unused3) {
                }
                try {
                    fileReader.close();
                } catch (Throwable unused4) {
                }
                return strTrim;
            }
            try {
                bufferedReader.close();
            } catch (Throwable unused5) {
            }
        } catch (Throwable th) {
            bufferedReader2 = bufferedReader;
            th = th;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Throwable unused6) {
                }
            }
            if (fileReader == null) {
                throw th;
            }
            try {
                fileReader.close();
                throw th;
            } catch (Throwable unused7) {
                throw th;
            }
        }
        try {
            fileReader.close();
            return "";
        } catch (Throwable unused8) {
            return "";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        r1 = r2[1].trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String y() throws Throwable {
        FileReader fileReader;
        BufferedReader bufferedReader;
        String[] strArrSplit;
        String strTrim = "";
        BufferedReader bufferedReader2 = null;
        try {
            try {
                fileReader = new FileReader("/proc/cpuinfo");
                try {
                    bufferedReader = new BufferedReader(fileReader, 8192);
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                if (!com.alipay.security.mobile.module.a.a.a(line) && (strArrSplit = line.split(":")) != null && strArrSplit.length > 1 && strArrSplit[0].contains("BogoMIPS")) {
                                    break;
                                }
                            }
                        } catch (Throwable unused) {
                            bufferedReader2 = bufferedReader;
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (Throwable unused2) {
                                }
                            }
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return strTrim;
                        }
                    }
                    try {
                        fileReader.close();
                    } catch (Throwable unused3) {
                    }
                    bufferedReader.close();
                    break;
                } catch (Throwable unused4) {
                }
            } catch (Throwable unused5) {
            }
        } catch (Throwable unused6) {
            fileReader = null;
        }
        return strTrim;
    }

    private static String z() {
        BluetoothAdapter defaultAdapter;
        String strA = "";
        try {
            defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                try {
                    if (!defaultAdapter.isEnabled()) {
                        return "";
                    }
                } catch (Throwable unused) {
                }
            }
            strA = defaultAdapter.getAddress();
        } catch (Throwable unused2) {
            defaultAdapter = null;
        }
        if (strA == null || strA.endsWith("00:00:00:00:00")) {
            try {
                strA = a(defaultAdapter);
            } catch (Throwable unused3) {
            }
        }
        return strA == null ? "" : strA;
    }

    public final String f() {
        try {
            return String.valueOf(new File("/sys/devices/system/cpu/").listFiles(new c(this)).length);
        } catch (Throwable unused) {
            return "1";
        }
    }
}
