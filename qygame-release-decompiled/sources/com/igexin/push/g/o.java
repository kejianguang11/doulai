package com.igexin.push.g;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaDrm;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import com.getui.gtc.api.GtcManager;
import com.getui.gtc.api.OnDycEnableChangedListener;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dim.Caller;
import com.getui.gtc.dim.DimManager;
import com.getui.gtc.dim.DimRequest;
import com.getui.gtc.dim.bean.GtWifiInfo;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.assist.util.AssistUtils;
import com.igexin.push.g.f;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class o {
    public static final String a = "PhoneInfoUtils";
    public static final String b = "";
    static boolean c = false;
    private static volatile PackageInfo d;
    private static String e;

    private static Location A() {
        try {
            return (Location) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.C).caller(Caller.PUSH).build());
        } catch (Throwable unused) {
            return null;
        }
    }

    public static int a(Context context) {
        try {
            return c(context).applicationInfo.targetSdkVersion;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return 0;
        }
    }

    private static String a(String str, String str2) {
        String str3 = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop " + str).getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return str3;
                }
                str3 = str3 + line;
            }
        } catch (Exception unused) {
            return str2;
        }
    }

    public static List<PackageInfo> a() {
        List<PackageInfo> list;
        try {
            list = (List) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.O).caller(Caller.PUSH).build());
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            list = null;
        }
        return list == null ? Collections.emptyList() : list;
    }

    public static ApplicationInfo b(Context context) {
        try {
            return c(context).applicationInfo;
        } catch (PackageManager.NameNotFoundException e2) {
            com.igexin.c.a.c.a.a(e2);
            return null;
        }
    }

    public static Pair<String, String> b() {
        try {
            if (!com.igexin.push.config.d.X || d.b("3.1.12.0")) {
                com.igexin.c.a.c.a.b(a, "use wf");
                WifiInfo wifiInfo = (WifiInfo) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.F).caller(Caller.PUSH).build());
                if (wifiInfo == null) {
                    return null;
                }
                return Pair.create(wifiInfo.getSSID(), wifiInfo.getBSSID());
            }
            com.igexin.c.a.c.a.b(a, "use gt wf");
            GtWifiInfo json = GtWifiInfo.parseJson((String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.I).caller(Caller.PUSH).build()));
            if (json == null) {
                return null;
            }
            return Pair.create(json.getSSID(), json.getBSSID());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return null;
        }
    }

    private static PackageInfo c(Context context) throws PackageManager.NameNotFoundException {
        if (d != null) {
            com.igexin.c.a.c.a.b(a, "getSelfPackageInfo cache");
            return d;
        }
        synchronized (o.class) {
            if (d == null) {
                d = context.getPackageManager().getPackageInfo(context.getPackageName(), 128);
                com.igexin.c.a.c.a.b(a, "getSelfPackageInfo");
            }
        }
        return d;
    }

    public static String c() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.j).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static String d() {
        return Build.BRAND;
    }

    public static String e() {
        return Build.MODEL;
    }

    public static String f() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.f).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static String g() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.b).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static String h() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.q).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static String i() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.r).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static List<ScanResult> j() {
        try {
            return (List) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.G).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return null;
        }
    }

    public static String k() {
        try {
            return Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0];
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return "";
        }
    }

    public static boolean l() {
        try {
            if (!com.igexin.push.config.d.G.contains("*")) {
                return Arrays.asList(com.igexin.push.config.d.G.toUpperCase().split(com.igexin.push.core.b.an)).contains(Build.BRAND.toUpperCase());
            }
            com.igexin.c.a.c.a.a("PhoneInfoUtils|delAlarm all", new Object[0]);
            return true;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("PhoneInfoUtils|delAlarm " + com.igexin.push.config.d.G + " err " + e2.toString(), new Object[0]);
            return false;
        }
    }

    public static String m() {
        String str;
        try {
            str = (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.l).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            str = null;
        }
        if (!TextUtils.isEmpty(str) && !str.equals(com.igexin.push.core.e.h)) {
            com.igexin.push.core.e.h = str;
        }
        return str;
    }

    public static String n() {
        try {
            return c(com.igexin.push.core.e.l).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    public static long o() {
        try {
            return c(com.igexin.push.core.e.l).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            com.igexin.c.a.c.a.a(e2);
            return 0L;
        }
    }

    public static String p() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.H).caller(Caller.PUSH).build());
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a("PhoneInfoUtils|".concat(String.valueOf(th)), new Object[0]);
            return "";
        }
    }

    public static String q() {
        byte[] propertyByteArray;
        try {
            if (Build.VERSION.SDK_INT < 23 || (propertyByteArray = new MediaDrm(new UUID(-1301668207276963122L, -6645017420763422227L)).getPropertyByteArray("deviceUniqueId")) == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b2 : propertyByteArray) {
                sb.append(String.format("%02x", Byte.valueOf(b2)));
            }
            return sb.toString();
        } catch (Error | Exception unused) {
            return "";
        }
    }

    public static String r() {
        String str = "";
        try {
            Context context = com.igexin.push.core.e.l;
            if (!CommonUtil.hasPermission(context, "android.permission.READ_PHONE_STATE", false)) {
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            switch (telephonyManager != null ? Build.VERSION.SDK_INT >= 24 ? telephonyManager.getDataNetworkType() : telephonyManager.getNetworkType() : 0) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return com.igexin.push.config.c.H;
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
                    return AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM;
                case 13:
                case 18:
                case 19:
                    return AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_MZ;
                case 20:
                    str = "5";
                    break;
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        return str;
    }

    public static void s() {
        if (d.b("3.2.16.0")) {
            return;
        }
        GtcManager.getInstance().addOnDycEnableChangedListener(com.igexin.push.core.e.l, new OnDycEnableChangedListener.Stub() { // from class: com.igexin.push.g.o.1
            @Override // com.getui.gtc.api.OnDycEnableChangedListener
            public final void onDycEnableChanged(final Map map) throws RemoteException {
                if (map == null || o.c) {
                    return;
                }
                o.c = true;
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.d() { // from class: com.igexin.push.g.o.1.1
                    @Override // com.igexin.push.f.d
                    public final void b() {
                        try {
                            if (Objects.equals(map.get(f.a.l), Boolean.TRUE)) {
                                if (com.igexin.push.core.e.u) {
                                    com.igexin.push.core.a.b.d().i();
                                } else {
                                    com.igexin.push.core.e.aM = true;
                                }
                            }
                            if (Objects.equals(map.get(f.a.O), Boolean.TRUE)) {
                                com.igexin.push.core.c.a.a().a(false);
                            }
                        } catch (Throwable th) {
                            com.igexin.c.a.c.a.a(th);
                        }
                    }
                }, false, true);
            }
        });
    }

    public static String t() {
        try {
            Class<?> clsA = p.a("com.getui.oneid.OneIDManager");
            Object objInvoke = p.a(clsA, "getInstance", new Class[0]).invoke(null, new Object[0]);
            Class<?> clsA2 = p.a("com.getui.oneid.OneResponse");
            Method methodA = p.a(clsA, "getOneResponse", new Class[0]);
            methodA.setAccessible(true);
            Object objInvoke2 = methodA.invoke(objInvoke, new Object[0]);
            if (objInvoke2 == null) {
                return "";
            }
            Method methodA2 = p.a(clsA2, "getOneAID", new Class[0]);
            methodA2.setAccessible(true);
            Object objInvoke3 = methodA2.invoke(objInvoke2, new Object[0]);
            return objInvoke3 instanceof String ? (String) objInvoke3 : "";
        } catch (Throwable th) {
            com.igexin.c.a.c.a.b("getOneId error", th.toString());
            return "";
        }
    }

    private static String u() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.e).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    private static String v() {
        if (!TextUtils.isEmpty(e)) {
            return e;
        }
        try {
            String str = Build.BRAND;
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String lowerCase = str.toLowerCase();
            HashMap map = new HashMap();
            map.put(AssistUtils.BRAND_HW, "ro.build.version.emui");
            map.put("blackshark", "ro.build.version.incremental");
            map.put("redmi", "ro.build.version.incremental");
            map.put(AssistUtils.BRAND_XIAOMI, "ro.build.version.incremental");
            map.put("samsang", "ro.build.version.incremental");
            map.put(AssistUtils.BRAND_VIVO, "ro.vivo.os.version");
            map.put(AssistUtils.BRAND_OPPO, "ro.build.version.opporom");
            map.put(AssistUtils.BRAND_MZ, "ro.build.display.id");
            map.put("lenovo", "ro.build.version.incremental");
            map.put("smartisan", "ro.modversion");
            map.put("htc", "ro.build.sense.version");
            map.put("oneplus", "ro.rom.version");
            map.put("yunos", "ro.cta.yunos.version");
            map.put("360", "ro.build.uiversion");
            map.put("nubia", "ro.build.rom.internal.id");
            if (!map.containsKey(lowerCase)) {
                return "";
            }
            String strA = a((String) map.get(lowerCase), "");
            e = strA;
            return strA;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return "";
        }
    }

    private static boolean w() {
        return Build.VERSION.SDK_INT > 28;
    }

    private static String x() {
        return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.u).caller(Caller.PUSH).build());
    }

    private static String y() {
        try {
            return (String) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.J).caller(Caller.PUSH).build());
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return "";
        }
    }

    private static Location z() {
        try {
            return (Location) DimManager.getInstance().get(new DimRequest.Builder().key(f.a.B).caller(Caller.PUSH).build());
        } catch (Throwable unused) {
            return null;
        }
    }
}
