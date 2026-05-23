package com.gme.liteav.base.system;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;
import androidx.appcompat.widget.ActivityChooserView;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.util.LiteavLog;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class LiteavSystemInfo {
    private static final int APP_SYSTEM_METHOD_DEFAULT_GET_INTERVAL_MS = 1000;
    private static final String EXT_KEY_APP_BACKGROUND = "isAppBackground";
    private static final String EXT_KEY_APP_NAME = "appName";
    private static final String EXT_KEY_APP_PACKAGE_NAME = "appPackageName";
    private static final String EXT_KEY_APP_VERSION = "appVersion";
    private static final String EXT_KEY_BUILD_BOARD = "buildBoard";
    private static final String EXT_KEY_BUILD_BRAND = "buildBrand";
    private static final String EXT_KEY_BUILD_HARDWARE = "buildHardware";
    private static final String EXT_KEY_BUILD_MANUFACTURER = "buildManufacturer";
    private static final String EXT_KEY_BUILD_MODEL = "buildModel";
    private static final String EXT_KEY_BUILD_VERSION = "buildVersion";
    private static final String EXT_KEY_BUILD_VERSION_INT = "buildVersionInt";
    private static final int NETWORK_TYPE_2G = 4;
    private static final int NETWORK_TYPE_3G = 3;
    private static final int NETWORK_TYPE_4G = 2;
    private static final int NETWORK_TYPE_5G = 6;
    private static final int NETWORK_TYPE_UNKNOWN = 0;
    private static final int NETWORK_TYPE_WIFI = 1;
    private static final int NETWORK_TYPE_WIRED = 5;
    private static final String TAG = "LiteavBaseSystemInfo";
    private static final com.gme.liteav.base.util.k<String> sModel = new com.gme.liteav.base.util.k<>(i.a());
    private static final com.gme.liteav.base.util.k<String> sBrand = new com.gme.liteav.base.util.k<>(j.a());
    private static final com.gme.liteav.base.util.k<String> sManufacturer = new com.gme.liteav.base.util.k<>(k.a());
    private static final com.gme.liteav.base.util.k<String> sHardware = new com.gme.liteav.base.util.k<>(l.a());
    private static final com.gme.liteav.base.util.k<String> sSystemOSVersion = new com.gme.liteav.base.util.k<>(m.a());
    private static final com.gme.liteav.base.util.k<Integer> sSystemOSVersionInt = new com.gme.liteav.base.util.k<>(n.a());
    private static final com.gme.liteav.base.util.k<String> sBoard = new com.gme.liteav.base.util.k<>(o.a());
    private static final com.gme.liteav.base.util.k<String> sAppPackageName = new com.gme.liteav.base.util.k<>(p.a());
    private static final com.gme.liteav.base.util.k<String> sAppName = new com.gme.liteav.base.util.k<>(d.a());
    private static final com.gme.liteav.base.util.k<String> sAppVersion = new com.gme.liteav.base.util.k<>(e.a());
    private static final com.gme.liteav.base.util.k<String> sUUID = new com.gme.liteav.base.util.k<>(f.a());
    private static final com.gme.liteav.base.util.k<String[]> sCpuABIs = new com.gme.liteav.base.util.k<>(g.a());
    private static int sLastNetworkType = 0;
    private static final com.gme.liteav.base.a.a sNetworkTypeThrottler = new com.gme.liteav.base.a.a();
    private static int sLastGateway = 0;
    private static final com.gme.liteav.base.a.a sGatewayThrottler = new com.gme.liteav.base.a.a();
    private static boolean sLastMicPermission = false;
    private static final com.gme.liteav.base.a.a sMicPermissionThrottler = new com.gme.liteav.base.a.a();
    private static boolean sLastIsBackground = false;
    private static final com.gme.liteav.base.util.k<List<Pair<ActivityManager.RunningServiceInfo, ServiceInfo>>> sForegroundServices = new com.gme.liteav.base.util.k<>(h.a());

    static /* synthetic */ String[] a() throws Exception {
        return Build.VERSION.SDK_INT >= 21 ? Build.SUPPORTED_ABIS : new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    public static synchronized int getAppBackgroundState() {
        try {
            return com.gme.liteav.base.util.e.a().b() ? 1 : 0;
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAppBackgroundState failed.", th);
            return 0;
        }
    }

    public static String getAppName() {
        try {
            return sAppName.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAppName failed.", th);
            return "";
        }
    }

    public static String getAppPackageName() {
        try {
            return sAppPackageName.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAppPackageName failed.", th);
            return "";
        }
    }

    public static synchronized int getAppThreadSize() {
        ThreadGroup threadGroup;
        try {
            threadGroup = Thread.currentThread().getThreadGroup();
            while (threadGroup.getParent() != null) {
                threadGroup = threadGroup.getParent();
            }
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAppThreadSize failed.", th);
            return 1;
        }
        return threadGroup.activeCount();
    }

    public static String getAppVersion() {
        try {
            return sAppVersion.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAppVersion failed.", th);
            return "";
        }
    }

    public static synchronized boolean getAudioRecordPermission() {
        try {
            if (sMicPermissionThrottler.a()) {
                sLastMicPermission = getAudioRecordPermissionFromSystem();
            }
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getAudioRecordPermission failed.", th);
            return false;
        }
        return sLastMicPermission;
    }

    private static boolean getAudioRecordPermissionFromSystem() {
        Context applicationContext = ContextUtils.getApplicationContext();
        return applicationContext != null && applicationContext.checkPermission("android.permission.RECORD_AUDIO", Process.myPid(), Process.myUid()) == 0;
    }

    public static String getBoard() {
        return sBoard.a();
    }

    public static String getBrand() {
        return sBrand.a();
    }

    public static String getDeviceUuid() {
        try {
            return sUUID.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getDeviceUuid failed.", th);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Pair<ActivityManager.RunningServiceInfo, ServiceInfo>> getForegroundServices() {
        Context applicationContext;
        ArrayList arrayList = new ArrayList();
        try {
            applicationContext = ContextUtils.getApplicationContext();
        } catch (Throwable th) {
            Log.e(TAG, "Get foreground services failed. ", th);
        }
        if (applicationContext == null) {
            Log.e(TAG, "Context is null.", new Object[0]);
            return arrayList;
        }
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) applicationContext.getSystemService("activity")).getRunningServices(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (runningServices == null) {
            return arrayList;
        }
        PackageManager packageManager = applicationContext.getPackageManager();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            arrayList.add(Pair.create(runningServiceInfo, packageManager.getServiceInfo(runningServiceInfo.service, 128)));
        }
        return arrayList;
    }

    public static synchronized int getGateway() {
        try {
            if (sGatewayThrottler.a()) {
                sLastGateway = getGatewayFromSystem();
            }
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getGateway failed.", th);
            return 0;
        }
        return sLastGateway;
    }

    private static int getGatewayFromSystem() {
        Context applicationContext = ContextUtils.getApplicationContext();
        if (applicationContext == null) {
            return 0;
        }
        try {
            return ((WifiManager) applicationContext.getSystemService("wifi")).getDhcpInfo().gateway;
        } catch (Throwable th) {
            Log.e(TAG, "getGateway error " + th.getMessage(), new Object[0]);
            return 0;
        }
    }

    public static String getHardware() {
        try {
            return sHardware.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getHardware failed.", th);
            return "";
        }
    }

    public static String getManufacturer() {
        try {
            return sManufacturer.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getManufacturer failed.", th);
            return "";
        }
    }

    public static String getModel() {
        try {
            return sModel.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getModel failed.", th);
            return "";
        }
    }

    public static synchronized int getNetworkType() {
        try {
            if (sNetworkTypeThrottler.a()) {
                sLastNetworkType = getNetworkTypeFromSystem();
            }
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getNetworkType failed.", th);
            return 0;
        }
        return sLastNetworkType;
    }

    private static int getNetworkTypeFromSystem() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        Context applicationContext = ContextUtils.getApplicationContext();
        if (applicationContext == null || (connectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity")) == null) {
            return 0;
        }
        try {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception unused) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return 0;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 0;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) applicationContext.getSystemService("phone");
            if (telephonyManager == null) {
                return 0;
            }
            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    break;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    break;
                case 13:
                    break;
                default:
                    if (getSystemOSVersionInt() < 29 || networkType != 20) {
                    }
                    break;
            }
            return 0;
        } catch (Exception unused2) {
        }
        return 2;
    }

    public static synchronized String getProperty(String str) {
        String property;
        try {
            property = System.getProperty(str);
        } catch (Throwable th) {
            th = th;
            property = null;
        }
        try {
            Log.i(TAG, "Get " + str + " property is " + property, new Object[0]);
        } catch (Throwable th2) {
            th = th2;
            Log.e(TAG, "Get property failed. ".concat(String.valueOf(th)), new Object[0]);
        }
        return property;
    }

    public static int[] getScreenSizeInPixels() {
        try {
            int[] iArr = {0, 0};
            Context applicationContext = ContextUtils.getApplicationContext();
            if (applicationContext == null) {
                Log.e(TAG, "Context is null.", new Object[0]);
                return iArr;
            }
            WindowManager windowManager = (WindowManager) applicationContext.getSystemService("window");
            if (windowManager == null) {
                Log.e(TAG, "WindowManager is null.", new Object[0]);
                return iArr;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            iArr[0] = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
            iArr[1] = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
            return iArr;
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getScreenSizeInPixels failed.", th);
            return null;
        }
    }

    public static String[] getSupportABIs() {
        try {
            return sCpuABIs.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getSupportABIs failed.", th);
            return null;
        }
    }

    public static String getSystemOSVersion() {
        try {
            return sSystemOSVersion.a();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getSystemOSVersion failed.", th);
            return "";
        }
    }

    public static int getSystemOSVersionInt() {
        try {
            return sSystemOSVersionInt.a().intValue();
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "getSystemOSVersionInt failed.", th);
            return 0;
        }
    }

    public static synchronized String getSystemProperty(String str) {
        String str2;
        str2 = null;
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
            if (objInvoke != null) {
                String str3 = (String) objInvoke;
                try {
                    Log.i(TAG, "Get " + str + " property is " + str3, new Object[0]);
                    str2 = str3;
                } catch (Throwable th) {
                    th = th;
                    str2 = str3;
                    Log.e(TAG, "Get system property failed. ".concat(String.valueOf(th)), new Object[0]);
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return str2;
    }

    public static boolean isForegroundServiceRunning(int i) {
        try {
            boolean zB = com.gme.liteav.base.util.e.a().b();
            if (zB != sLastIsBackground) {
                sLastIsBackground = zB;
                sForegroundServices.a(getForegroundServices());
            }
            List<Pair<ActivityManager.RunningServiceInfo, ServiceInfo>> listA = sForegroundServices.a();
            if (listA == null) {
                return false;
            }
            for (Pair<ActivityManager.RunningServiceInfo, ServiceInfo> pair : listA) {
                if (((ActivityManager.RunningServiceInfo) pair.first).foreground && (getSystemOSVersionInt() < 29 || i == 0 || (((ServiceInfo) pair.second).getForegroundServiceType() & i) != 0)) {
                    return true;
                }
            }
        } catch (Throwable th) {
            Log.e(TAG, "Get foreground service running failed. ", th);
        }
        return false;
    }

    public static boolean isVPNActive() {
        ConnectivityManager connectivityManager;
        Network activeNetwork;
        NetworkCapabilities networkCapabilities;
        try {
            Context applicationContext = ContextUtils.getApplicationContext();
            if (applicationContext == null || (connectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity")) == null || Build.VERSION.SDK_INT < 23 || (activeNetwork = connectivityManager.getActiveNetwork()) == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
                return false;
            }
            return networkCapabilities.hasTransport(4);
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "isVPNActive failed.", th);
            return false;
        }
    }

    public static synchronized void listenAppBackgroundState() {
        try {
            com.gme.liteav.base.util.e.a().a(c.a());
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "listenAppBackgroundState failed.", th);
        }
    }

    private static native void nativeOnAppBackgroundStateChanged(int i);

    public static void onAppBackgroundStateChanged(boolean z) {
        nativeOnAppBackgroundStateChanged(z ? 1 : 0);
    }

    public static boolean setExtID(String str, String str2) {
        com.gme.liteav.base.util.k<String> kVar;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        switch (str) {
            case "buildModel":
                kVar = sModel;
                kVar.a(str2);
                break;
            case "buildBrand":
                kVar = sBrand;
                kVar.a(str2);
                break;
            case "buildManufacturer":
                kVar = sManufacturer;
                kVar.a(str2);
                break;
            case "buildHardware":
                kVar = sHardware;
                kVar.a(str2);
                break;
            case "buildVersion":
                kVar = sSystemOSVersion;
                kVar.a(str2);
                break;
            case "buildVersionInt":
                try {
                    sSystemOSVersionInt.a(Integer.valueOf(Integer.parseInt(str2)));
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                break;
            case "buildBoard":
                kVar = sBoard;
                kVar.a(str2);
                break;
            case "appName":
                kVar = sAppName;
                kVar.a(str2);
                break;
            case "appPackageName":
                kVar = sAppPackageName;
                kVar.a(str2);
                break;
            case "appVersion":
                kVar = sAppVersion;
                kVar.a(str2);
                break;
            case "isAppBackground":
                try {
                    com.gme.liteav.base.util.e.a(Integer.parseInt(str2) == 1);
                    break;
                } catch (Exception e2) {
                    Log.e(TAG, "set app background state failed. ".concat(String.valueOf(e2)), new Object[0]);
                    return false;
                }
                break;
        }
        return false;
        return true;
    }

    public static void setSDKWorking(boolean z) {
        try {
            com.gme.liteav.base.util.e.a().a = z;
        } catch (Throwable th) {
            LiteavLog.e("LiteavSystemInfo", "setSDKWorking failed.", th);
        }
    }
}
