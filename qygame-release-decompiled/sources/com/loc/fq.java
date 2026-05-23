package com.loc;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.DPoint;
import com.igexin.assist.util.AssistUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fq {
    static WifiManager a;
    private static int b;
    private static String[] c;
    private static String d;

    public static double a(double d2) {
        return b(d2);
    }

    public static float a(float f) {
        return (float) (((long) (((double) f) * 100.0d)) / 100.0d);
    }

    public static float a(AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        return a(new double[]{aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation2.getLatitude(), aMapLocation2.getLongitude()});
    }

    public static float a(DPoint dPoint, DPoint dPoint2) {
        return a(new double[]{dPoint.getLatitude(), dPoint.getLongitude(), dPoint2.getLatitude(), dPoint2.getLongitude()});
    }

    public static float a(double[] dArr) {
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    public static int a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static Object a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            return context.getApplicationContext().getSystemService(str);
        } catch (Throwable th) {
            fj.a(th, "Utils", "getServ");
            return null;
        }
    }

    public static String a(int i) {
        if (i == 33) {
            return "补偿定位失败，未命中缓存";
        }
        switch (i) {
            case 0:
                return "success";
            case 1:
                return "重要参数为空";
            case 2:
                return "WIFI信息不足";
            case 3:
                return "请求参数获取出现异常";
            case 4:
                return "网络连接异常";
            case 5:
                return "解析数据异常";
            case 6:
                return "定位结果错误";
            case 7:
                return "KEY错误";
            case 8:
                return "其他错误";
            case 9:
                return "初始化异常";
            case 10:
                return "定位服务启动失败";
            case 11:
                return "错误的基站信息，请检查是否插入SIM卡";
            case 12:
                return "缺少定位权限";
            case 13:
                return "网络定位失败，请检查设备是否插入sim卡，是否开启移动网络或开启了wifi模块";
            case 14:
                return "GPS 定位失败，由于设备当前 GPS 状态差,建议持设备到相对开阔的露天场所再次尝试";
            case 15:
                return "当前返回位置为模拟软件返回，请关闭模拟软件，或者在option中设置允许模拟";
            default:
                switch (i) {
                    case 18:
                        return "定位失败，飞行模式下关闭了WIFI开关，请关闭飞行模式或者打开WIFI开关";
                    case 19:
                        return "定位失败，没有检查到SIM卡，并且关闭了WIFI开关，请打开WIFI开关或者插入SIM卡";
                    case 20:
                        return "模糊定位失败，具体可查看错误信息/详细信息描述";
                    default:
                        return "其他错误";
                }
        }
    }

    public static String a(long j, String str) {
        SimpleDateFormat simpleDateFormat;
        if (TextUtils.isEmpty(str)) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            simpleDateFormat = new SimpleDateFormat(str, Locale.CHINA);
            try {
                simpleDateFormat.applyPattern(str);
            } catch (Throwable th) {
                th = th;
                fj.a(th, "Utils", "formatUTC");
            }
        } catch (Throwable th2) {
            th = th2;
            simpleDateFormat = null;
        }
        if (j <= 0) {
            j = a();
        }
        return simpleDateFormat == null ? "NULL" : simpleDateFormat.format(Long.valueOf(j));
    }

    public static String a(Context context, TelephonyManager telephonyManager) {
        int networkType = 0;
        if (telephonyManager != null) {
            try {
                if (context.getApplicationInfo().targetSdkVersion < 29 && Build.VERSION.SDK_INT < 30) {
                    networkType = telephonyManager.getNetworkType();
                }
            } catch (Throwable unused) {
            }
        }
        switch (networkType) {
            case 0:
                return "UNKWN";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO_0";
            case 6:
                return "EVDO_A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "IDEN";
            case 12:
                return "EVDO_B";
            case 13:
                return "LTE";
            case 14:
                return "EHRPD";
            case 15:
                return "HSPAP";
            default:
                return "UNKWN";
        }
    }

    public static List<String> a(File file) throws Throwable {
        FileInputStream fileInputStreamB;
        InputStreamReader inputStreamReader;
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        try {
            try {
                fileInputStreamB = b(file);
                try {
                    inputStreamReader = new InputStreamReader(fileInputStreamB, Charset.defaultCharset());
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                        while (true) {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                }
                                arrayList.add(line);
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (inputStreamReader != null) {
                                    inputStreamReader.close();
                                }
                                if (fileInputStreamB != null) {
                                    fileInputStreamB.close();
                                }
                                throw th;
                            }
                        }
                        bufferedReader2.close();
                        inputStreamReader.close();
                        fileInputStreamB.close();
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    inputStreamReader = null;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable unused3) {
            fileInputStreamB = null;
            inputStreamReader = null;
        }
        return arrayList;
    }

    public static void a(File file, String str) throws Throwable {
        FileOutputStream fileOutputStreamC;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                fileOutputStreamC = c(file);
                if (str != null) {
                    try {
                        fileOutputStreamC.write(str.getBytes());
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = fileOutputStreamC;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                return;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        if (fileOutputStreamC != null) {
                            try {
                                fileOutputStreamC.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                try {
                    fileOutputStreamC.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStreamC = fileOutputStream;
        }
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return c() < 17 ? d(context, "android.provider.Settings$System") : d(context, "android.provider.Settings$Global");
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean a(SQLiteDatabase sQLiteDatabase, String str) throws Throwable {
        Cursor cursorQuery;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strReplace = "2.0.201501131131".replace(".", "");
        Cursor cursor = null;
        if (sQLiteDatabase != null) {
            try {
            } catch (Throwable unused) {
                cursorQuery = null;
            }
            if (sQLiteDatabase.isOpen()) {
                cursorQuery = sQLiteDatabase.query("sqlite_master", new String[]{"count(*) as c"}, "type = 'table' AND name = '" + str.trim() + strReplace + "'", null, null, null, null);
                if (cursorQuery != null) {
                    try {
                    } catch (Throwable unused2) {
                        if (cursorQuery != null) {
                        }
                        return z;
                    }
                    if (cursorQuery.moveToFirst()) {
                        z = cursorQuery.getInt(0) > 0;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                }
                return z;
            }
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0011 A[Catch: Throwable -> 0x0040, TRY_ENTER, TryCatch #1 {Throwable -> 0x0040, blocks: (B:3:0x0001, B:12:0x0011, B:14:0x0017, B:19:0x0024, B:21:0x002e, B:23:0x0037), top: B:32:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean a(Location location, int i) {
        boolean zIsFromMockProvider;
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                try {
                    zIsFromMockProvider = location.isFromMockProvider();
                } catch (Throwable unused) {
                    zIsFromMockProvider = false;
                }
                if (!zIsFromMockProvider) {
                    return true;
                }
                Bundle extras = location.getExtras();
                if ((extras != null ? extras.getInt("satellites") : 0) <= 0) {
                    return true;
                }
                if (i == 0 && location.getAltitude() == 0.0d && location.getBearing() == 0.0f) {
                    if (location.getSpeed() == 0.0f) {
                        return true;
                    }
                }
            } else {
                zIsFromMockProvider = false;
                if (!zIsFromMockProvider) {
                }
            }
        } catch (Throwable unused2) {
        }
        return false;
    }

    public static boolean a(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            return b(aMapLocation);
        }
        return false;
    }

    public static boolean a(eo eoVar) {
        if (eoVar == null || "8".equals(eoVar.d()) || "5".equals(eoVar.d()) || "6".equals(eoVar.d())) {
            return false;
        }
        return b(eoVar);
    }

    public static boolean a(String str) {
        return (TextUtils.isEmpty(str) || "00:00:00:00:00:00".equals(str) || "02:00:00:00:00:00".equals(str) || str.contains(" :")) ? false : true;
    }

    public static boolean a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            ArrayList<String> arrayListB = b(str);
            String[] strArrSplit = str2.toString().split("#");
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                if (strArrSplit[i3].contains(",nb") || strArrSplit[i3].contains(",access")) {
                    i++;
                    if (arrayListB.contains(strArrSplit[i3])) {
                        i2++;
                    }
                }
            }
            if (i2 * 2 >= ((double) (arrayListB.size() + i)) * 0.618d) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return x.a(jSONObject, str);
    }

    public static byte[] a(int i, byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            bArr = new byte[2];
        }
        bArr[0] = (byte) (i & 255);
        bArr[1] = (byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        return bArr;
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((j >> (i * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr) {
        return x.b(bArr);
    }

    public static String[] a(TelephonyManager telephonyManager) {
        int i;
        String networkOperator = telephonyManager != null ? telephonyManager.getNetworkOperator() : null;
        String[] strArr = {"0", "0"};
        if (!TextUtils.isEmpty(networkOperator) && TextUtils.isDigitsOnly(networkOperator) && networkOperator.length() > 4) {
            strArr[0] = networkOperator.substring(0, 3);
            char[] charArray = networkOperator.substring(3).toCharArray();
            int i2 = 0;
            while (i2 < charArray.length && Character.isDigit(charArray[i2])) {
                i2++;
            }
            strArr[1] = networkOperator.substring(3, i2 + 3);
        }
        try {
            i = Integer.parseInt(strArr[0]);
        } catch (Throwable th) {
            fj.a(th, "Utils", "getMccMnc");
            i = 0;
        }
        if (i == 0) {
            strArr[0] = "0";
        }
        if ("0".equals(strArr[0]) || "0".equals(strArr[1])) {
            return ("0".equals(strArr[0]) && "0".equals(strArr[1]) && c != null) ? c : strArr;
        }
        c = strArr;
        return strArr;
    }

    public static double b(double d2) {
        return ((long) (d2 * 1000000.0d)) / 1000000.0d;
    }

    public static int b(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 2; i2++) {
            i |= (bArr[i2] & 255) << ((1 - i2) * 8);
        }
        return i;
    }

    public static long b() {
        return SystemClock.elapsedRealtime();
    }

    private static FileInputStream b(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (file.canRead()) {
            return new FileInputStream(file);
        }
        throw new IOException("File '" + file + "' cannot be read");
    }

    public static String b(Context context) {
        PackageInfo packageInfo;
        if (!TextUtils.isEmpty(fj.j)) {
            return fj.j;
        }
        if (context == null) {
            return null;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(l.c(context), 64);
        } catch (Throwable th) {
            fj.a(th, "Utils", "getAppName part");
            packageInfo = null;
        }
        try {
            if (TextUtils.isEmpty(fj.k)) {
                fj.k = null;
            }
        } catch (Throwable th2) {
            fj.a(th2, "Utils", "getAppName");
        }
        StringBuilder sb = new StringBuilder();
        if (packageInfo != null) {
            CharSequence charSequenceLoadLabel = packageInfo.applicationInfo != null ? packageInfo.applicationInfo.loadLabel(context.getPackageManager()) : null;
            if (charSequenceLoadLabel != null) {
                sb.append(charSequenceLoadLabel.toString());
            }
            if (!TextUtils.isEmpty(packageInfo.versionName)) {
                sb.append(packageInfo.versionName);
            }
        }
        String strC = l.c(context);
        if (!TextUtils.isEmpty(strC)) {
            sb.append(com.igexin.push.core.b.an);
            sb.append(strC);
        }
        if (!TextUtils.isEmpty(fj.k)) {
            sb.append(com.igexin.push.core.b.an);
            sb.append(fj.k);
        }
        String string = sb.toString();
        fj.j = string;
        return string;
    }

    public static ArrayList<String> b(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            String[] strArrSplit = str.split("#");
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].contains(",nb") || strArrSplit[i].contains(",access")) {
                    arrayList.add(strArrSplit[i]);
                }
            }
        }
        return arrayList;
    }

    public static boolean b(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(str, 256);
        } catch (Throwable unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static boolean b(AMapLocation aMapLocation) {
        double longitude = aMapLocation.getLongitude();
        double latitude = aMapLocation.getLatitude();
        return !(longitude == 0.0d && latitude == 0.0d) && longitude <= 180.0d && latitude <= 90.0d && longitude >= -180.0d && latitude >= -90.0d;
    }

    public static byte[] b(int i, byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            bArr = new byte[4];
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static double c(double d2) {
        return ((long) (d2 * 100.0d)) / 100.0d;
    }

    public static double c(String str) throws NumberFormatException {
        return Double.parseDouble(str);
    }

    public static int c() {
        if (b > 0) {
            return b;
        }
        try {
            try {
                return fm.b("android.os.Build$VERSION", "SDK_INT");
            } catch (Throwable unused) {
                return 0;
            }
        } catch (Throwable unused2) {
            return Integer.parseInt(fm.a("android.os.Build$VERSION", "SDK").toString());
        }
    }

    public static NetworkInfo c(Context context) {
        try {
            return o.p(context);
        } catch (Throwable th) {
            fj.a(th, "Utils", "getNetWorkInfo");
            return null;
        }
    }

    private static FileOutputStream c(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                    throw new IOException("Directory '" + parentFile + "' could not be created");
                }
                file.createNewFile();
            }
        } else {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        return new FileOutputStream(file, false);
    }

    public static boolean c(Context context, String str) {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT < 23 ? !(context == null || context.checkCallingOrSelfPermission(x.c(str)) != 0) : !(context == null || context.checkSelfPermission(x.c(str)) != 0)) {
                z = true;
            }
        } catch (Throwable unused) {
        }
        return z;
    }

    public static float d(String str) throws NumberFormatException {
        return Float.parseFloat(str);
    }

    public static int d() {
        return new Random().nextInt(65536) - 32768;
    }

    public static boolean d(Context context) {
        try {
            NetworkInfo networkInfoC = c(context);
            if (networkInfoC != null) {
                if (networkInfoC.isConnectedOrConnecting()) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private static boolean d(Context context, String str) throws Throwable {
        return ((Integer) fm.a(str, "getInt", new Object[]{context.getContentResolver(), ((String) fm.a(str, "AIRPLANE_MODE_ON")).toString()}, (Class<?>[]) new Class[]{ContentResolver.class, String.class})).intValue() == 1;
    }

    public static int e(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    public static String e() {
        try {
            return p.b("S128DF1572465B890OE3F7A13167KLEI".getBytes(com.alipay.sdk.sys.a.m)).substring(20);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static boolean e(Context context) {
        int iB;
        if (Build.VERSION.SDK_INT < 23 || context.getApplicationInfo().targetSdkVersion < 23) {
            for (String str : ej.F) {
                if (context.checkCallingOrSelfPermission(str) != 0) {
                    return false;
                }
            }
        } else {
            Application application = (Application) context;
            for (String str2 : ej.F) {
                try {
                    iB = fm.b(application.getBaseContext(), "checkSelfPermission", str2);
                } catch (Throwable unused) {
                    iB = 0;
                }
                if (iB != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int f(String str) throws NumberFormatException {
        return Integer.parseInt(str, 16);
    }

    public static boolean f(Context context) {
        int iB;
        if (context.getApplicationInfo().targetSdkVersion < 29 || Build.VERSION.SDK_INT < 29) {
            return true;
        }
        try {
            iB = fm.b(((Application) context).getBaseContext(), "checkSelfPermission", ej.G);
        } catch (Throwable unused) {
            iB = 0;
        }
        return iB == 0;
    }

    public static byte g(String str) throws NumberFormatException {
        return Byte.parseByte(str);
    }

    @SuppressLint({"NewApi"})
    public static boolean g(Context context) {
        boolean zIsWifiEnabled;
        if (context == null) {
            return true;
        }
        if (a == null) {
            a = (WifiManager) a(context, "wifi");
        }
        if (!c(context, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF")) {
            fj.a(new Exception("n_aws"), "OPENSDK_UTS", "iwfal_n_aws");
            zIsWifiEnabled = false;
            return zIsWifiEnabled ? zIsWifiEnabled : zIsWifiEnabled;
        }
        zIsWifiEnabled = a.isWifiEnabled();
        if (zIsWifiEnabled && c() > 17) {
            try {
                return "true".equals(String.valueOf(fm.a(a, "isScanAlwaysAvailable", new Object[0])));
            } catch (Throwable unused) {
                return zIsWifiEnabled;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0032 A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String h(Context context) {
        NetworkInfo networkInfoC = c(context);
        if (networkInfoC == null || !networkInfoC.isConnectedOrConnecting()) {
            return "DISCONNECTED";
        }
        int type = networkInfoC.getType();
        if (type == 1) {
            return "WIFI";
        }
        if (type != 0) {
            return "UNKNOWN";
        }
        String subtypeName = networkInfoC.getSubtypeName();
        switch (networkInfoC.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
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
            case 17:
                return "3G";
            case 13:
                return "4G";
            default:
                if (!"GSM".equalsIgnoreCase(subtypeName)) {
                    if (!"TD-SCDMA".equalsIgnoreCase(subtypeName) && !"WCDMA".equalsIgnoreCase(subtypeName) && !"CDMA2000".equalsIgnoreCase(subtypeName)) {
                        return subtypeName;
                    }
                }
                return "2G";
        }
    }

    private static boolean h(String str) {
        try {
            String str2 = Build.MANUFACTURER;
            String str3 = Build.BRAND;
            if (!str2.equalsIgnoreCase(str)) {
                if (!str3.toLowerCase().contains(str)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String i(Context context) {
        String strK = o.k(context);
        if (TextUtils.isEmpty(strK) || strK.equals("00:00:00:00:00:00")) {
            strK = fp.a(context);
        }
        return TextUtils.isEmpty(strK) ? "00:00:00:00:00:00" : strK;
    }

    public static boolean j(Context context) {
        return Build.VERSION.SDK_INT >= 28 && context.getApplicationInfo().targetSdkVersion >= 28;
    }

    public static boolean k(Context context) {
        ServiceInfo serviceInfo;
        try {
            serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, "com.amap.api.location.APSService"), 128);
        } catch (Throwable unused) {
            serviceInfo = null;
        }
        return serviceInfo != null;
    }

    public static String l(Context context) {
        if (d == null) {
            d = ey.a("MD5", l.c(context));
        }
        return d;
    }

    public static boolean m(Context context) {
        try {
            if (!o(context)) {
                if (!n(context)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean n(Context context) {
        return h(AssistUtils.BRAND_VIVO) && p(context) && q(context);
    }

    private static boolean o(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 31 || context == null || context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                return false;
            }
            return context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean p(Context context) {
        try {
            int i = Build.VERSION.SDK_INT;
            int i2 = context.getApplicationInfo().targetSdkVersion;
            return ((i == 30) && (i2 >= 23)) || ((i == 31) && (i2 <= 30 && i2 >= 23));
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean q(Context context) {
        boolean z = false;
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.vivo.permissionmanager.provider.permission/fuzzy_location_apps"), new String[]{"package_name", "selected_fuzzy"}, "package_name=?", new String[]{context.getPackageName()}, null);
            boolean z2 = false;
            while (cursorQuery != null) {
                try {
                    if (!cursorQuery.moveToNext()) {
                        break;
                    }
                    if (cursorQuery.getString(0) != null && cursorQuery.getInt(1) == 1) {
                        z2 = true;
                    }
                } catch (Throwable unused) {
                    z = z2;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    return z;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return z2;
        } catch (Throwable unused2) {
        }
    }
}
