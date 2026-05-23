package com.loc;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.gme.liteav.TXLiteAVCode;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public final class ej {
    static int C = -1;
    private static boolean M = false;
    boolean H;
    private Handler P;
    private ev Q;
    private String R;
    private ek T;
    public static String[] F = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    public static String G = "android.permission.ACCESS_BACKGROUND_LOCATION";
    private static volatile boolean S = false;
    Context a = null;
    ConnectivityManager b = null;
    ex c = null;
    et d = null;
    ez e = null;
    fg f = null;
    ArrayList<dy> g = new ArrayList<>();
    a h = null;
    AMapLocationClientOption i = new AMapLocationClientOption();
    eo j = null;
    long k = 0;
    private int K = 0;
    fh l = null;
    boolean m = false;
    private String L = null;
    fe n = null;
    StringBuilder o = new StringBuilder();
    boolean p = true;
    boolean q = true;
    AMapLocationClientOption.GeoLanguage r = AMapLocationClientOption.GeoLanguage.DEFAULT;
    boolean s = true;
    boolean t = false;
    WifiInfo u = null;
    boolean v = true;
    private String N = null;
    StringBuilder w = null;
    boolean x = false;
    public boolean y = false;
    int z = 12;
    private boolean O = true;
    eq A = null;
    boolean B = false;
    en D = null;
    String E = null;
    IntentFilter I = null;
    LocationManager J = null;

    class a extends BroadcastReceiver {
        a() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            try {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }
                if (!action.equals("android.net.wifi.SCAN_RESULTS")) {
                    if (!action.equals("android.net.wifi.WIFI_STATE_CHANGED") || ej.this.c == null) {
                        return;
                    }
                    ej.this.c.j();
                    return;
                }
                if (ej.this.c != null) {
                    ej.this.c.i();
                }
                try {
                    if (intent.getExtras() == null || !intent.getExtras().getBoolean("resultsUpdated", true) || ej.this.c == null) {
                        return;
                    }
                    ej.this.c.h();
                } catch (Throwable unused) {
                }
            } catch (Throwable th) {
                fj.a(th, "Aps", "onReceive");
            }
        }
    }

    public ej(boolean z) {
        this.H = false;
        this.H = z;
    }

    private static eo a(int i, String str) {
        eo eoVar = new eo("");
        eoVar.setErrorCode(i);
        eoVar.setLocationDetail(str);
        if (i == 15) {
            fo.a((String) null, 2151);
        }
        return eoVar;
    }

    private eo a(eo eoVar, bm bmVar, ei eiVar) {
        if (bmVar != null) {
            try {
                if (bmVar.a != null && bmVar.a.length != 0) {
                    fg fgVar = new fg();
                    String str = new String(bmVar.a, com.alipay.sdk.sys.a.m);
                    if (!str.contains("\"status\":\"0\"")) {
                        if (!str.contains("</body></html>")) {
                            return null;
                        }
                        eoVar.setErrorCode(5);
                        if (this.c == null || !this.c.a(this.b)) {
                            eiVar.f("#0502");
                            this.o.append("请求可能被劫持了#0502");
                            fo.a((String) null, 2052);
                        } else {
                            eiVar.f("#0501");
                            this.o.append("您连接的是一个需要登录的网络，请确认已经登入网络#0501");
                            fo.a((String) null, 2051);
                        }
                        eoVar.setLocationDetail(this.o.toString());
                        return eoVar;
                    }
                    eo eoVarA = fgVar.a(str, this.a, bmVar, eiVar);
                    try {
                        eoVarA.h(this.w.toString());
                        return eoVarA;
                    } catch (Throwable th) {
                        th = th;
                        eoVar = eoVarA;
                        eoVar.setErrorCode(4);
                        fj.a(th, "Aps", "checkResponseEntity");
                        eiVar.f("#0403");
                        this.o.append("check response exception ex is" + th.getMessage() + "#0403");
                        eoVar.setLocationDetail(this.o.toString());
                        return eoVar;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        eoVar.setErrorCode(4);
        this.o.append("网络异常,请求异常#0403");
        eiVar.f("#0403");
        eoVar.h(this.w.toString());
        eoVar.setLocationDetail(this.o.toString());
        if (bmVar != null) {
            fo.a(bmVar.d, 2041);
        }
        return eoVar;
    }

    private StringBuilder a(StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder(700);
        } else {
            sb.delete(0, sb.length());
        }
        sb.append(this.d.m());
        sb.append(this.c.o());
        return sb;
    }

    private boolean a(long j) {
        if (!this.O) {
            this.O = true;
            return false;
        }
        if (fq.b() - j < 800) {
            return (fq.a(this.j) ? fq.a() - this.j.getTime() : 0L) <= com.igexin.push.config.c.i;
        }
        return false;
    }

    @SuppressLint({"NewApi"})
    private eo b(boolean z, ei eiVar) {
        StringBuilder sb;
        StringBuilder sb2;
        String str;
        try {
            if (TextUtils.isEmpty(this.R)) {
                this.R = x.b(o.a(this.a) + com.igexin.push.core.b.an + o.h(this.a));
            }
            StringBuilder sb3 = this.o;
            sb3.append("#id:");
            sb3.append(this.R);
        } catch (Throwable unused) {
        }
        eo eoVar = new eo("");
        try {
            byte[] bArrM = m();
            this.k = fq.b();
            eiVar.a(this.k);
            try {
                fj.c(this.a);
                ff ffVarA = this.n.a(this.a, bArrM, fj.a(), fj.b(), z);
                ffVarA.b();
                String strC = ffVarA.c();
                m.a(this.a);
                boolean z2 = !TextUtils.isEmpty(strC) && strC.contains("dualstack");
                int i = fc.a;
                if (m.a() && m.c() && z2) {
                    i = fc.b;
                }
                String strA = m.b() ? null : fc.a(this.a).a(ffVarA, i);
                eiVar.a(i == fc.b ? "v6" : "v4");
                bm bmVarA = this.n.a(ffVarA);
                long jB = fq.b();
                if (!TextUtils.isEmpty(strA)) {
                    if (bmVarA.f) {
                        fc.a(this.a).a(false, i);
                        fc.a(this.a).a(i);
                    } else {
                        fc.a(this.a).a(true, i);
                    }
                }
                if (bmVarA == null || TextUtils.isEmpty(strA)) {
                    eiVar.d(com.alipay.security.mobile.module.http.model.c.g);
                } else if (bmVarA.f) {
                    eiVar.b(strA);
                    eiVar.c("FAIL");
                    eiVar.d(com.alipay.security.mobile.module.http.model.c.g);
                } else {
                    eiVar.b(strA);
                    eiVar.c(com.alipay.security.mobile.module.http.model.c.g);
                }
                if (this.T != null) {
                    this.T.d();
                }
                eiVar.b(jB);
                String str2 = "";
                if (bmVarA != null) {
                    if (!TextUtils.isEmpty(bmVarA.c)) {
                        this.o.append("#csid:" + bmVarA.c);
                    }
                    str2 = bmVarA.d;
                    eoVar.h(this.w.toString());
                }
                eo eoVarA = a(eoVar, bmVarA, eiVar);
                if (eoVarA != null) {
                    return eoVarA;
                }
                byte[] bArrB = ey.b(bmVarA.a);
                if (bArrB == null) {
                    eoVar.setErrorCode(5);
                    eiVar.f("#0503");
                    this.o.append("解密数据失败#0503");
                    eoVar.setLocationDetail(this.o.toString());
                    fo.a(str2, 2053);
                    return eoVar;
                }
                eo eoVarA2 = this.f.a(eoVar, bArrB, eiVar);
                if (fq.a(eoVarA2)) {
                    c(eoVarA2);
                    eoVarA2.setOffset(this.q);
                    eoVarA2.a(this.p);
                    eoVarA2.f(String.valueOf(this.r));
                    eoVarA2.e("new");
                    eoVarA2.setLocationDetail(this.o.toString());
                    this.E = eoVarA2.a();
                    return eoVarA2;
                }
                this.L = eoVarA2.b();
                fo.a(str2, !TextUtils.isEmpty(this.L) ? 2062 : 2061);
                eoVarA2.setErrorCode(6);
                eiVar.f("#0601");
                StringBuilder sb4 = this.o;
                StringBuilder sb5 = new StringBuilder("location faile retype:");
                sb5.append(eoVarA2.d());
                sb5.append(" rdesc:");
                sb5.append(TextUtils.isEmpty(this.L) ? "" : this.L);
                sb5.append("#0601");
                sb4.append(sb5.toString());
                eoVarA2.h(this.w.toString());
                eoVarA2.setLocationDetail(this.o.toString());
                return eoVarA2;
            } catch (Throwable th) {
                fq.b();
                eiVar.d("FAIL");
                fc.a(this.a).a(false, fc.a);
                fj.a(th, "Aps", "getApsLoc req");
                fo.a("/mobile/binary", th);
                if (fq.d(this.a)) {
                    if (th instanceof k) {
                        k kVar = (k) th;
                        if (kVar.a().contains("网络异常状态码")) {
                            eiVar.f("#0404");
                            StringBuilder sb6 = this.o;
                            sb6.append("网络异常，状态码错误#0404");
                            sb6.append(kVar.f());
                            eo eoVarA3 = a(4, this.o.toString());
                            eoVarA3.h(this.w.toString());
                            return eoVarA3;
                        }
                        if (kVar.f() == 23 || Math.abs((fq.b() - this.k) - this.i.getHttpTimeOut()) < 500) {
                            eiVar.f("#0402");
                            sb2 = this.o;
                            str = "网络异常，连接超时#0402";
                        } else {
                            sb = new StringBuilder("#0403,");
                        }
                    } else {
                        sb = new StringBuilder("#0403,");
                    }
                    sb.append(th.getMessage());
                    eiVar.f(sb.toString());
                    sb2 = this.o;
                    str = "网络异常,请求异常#0403";
                } else {
                    eiVar.f("#0401");
                    sb2 = this.o;
                    str = "网络异常，未连接到网络，请连接网络#0401";
                }
                sb2.append(str);
                eo eoVarA32 = a(4, this.o.toString());
                eoVarA32.h(this.w.toString());
                return eoVarA32;
            }
        } catch (Throwable th2) {
            eiVar.f("#0301");
            this.o.append("get parames error:" + th2.getMessage() + "#0301");
            fo.a((String) null, 2031);
            eo eoVarA4 = a(3, this.o.toString());
            eoVarA4.h(this.w.toString());
            return eoVarA4;
        }
    }

    private void b(Context context) {
        try {
            if (context.checkCallingOrSelfPermission(x.c("EYW5kcm9pZC5wZXJtaXNzaW9uLldSSVRFX1NFQ1VSRV9TRVRUSU5HUw==")) == 0) {
                this.m = true;
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0261 A[PHI: r13
      0x0261: PHI (r13v12 java.lang.StringBuilder) = (r13v9 java.lang.StringBuilder), (r13v13 java.lang.StringBuilder) binds: [B:100:0x02a8, B:92:0x025e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0264 A[PHI: r13
      0x0264: PHI (r13v10 java.lang.StringBuilder) = 
      (r13v9 java.lang.StringBuilder)
      (r13v9 java.lang.StringBuilder)
      (r13v13 java.lang.StringBuilder)
      (r13v13 java.lang.StringBuilder)
     binds: [B:98:0x02a4, B:100:0x02a8, B:90:0x025a, B:92:0x025e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String c(ei eiVar) {
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        String str2;
        StringBuilder sb3;
        String str3;
        StringBuilder sb4;
        String str4;
        String string = "";
        int iH = this.d.h();
        es esVarE = this.d.e();
        es esVarF = this.d.f();
        boolean z = this.g == null || this.g.isEmpty();
        if (esVarE == null && esVarF == null && z) {
            if (this.b == null) {
                this.b = (ConnectivityManager) fq.a(this.a, "connectivity");
            }
            if (fq.c() >= 31) {
                if (fq.a(this.a) && !this.c.l()) {
                    this.z = 18;
                    this.o.append("飞行模式下关闭了WIFI开关，请关闭飞行模式或者打开WIFI开关#1802");
                    fo.a((String) null, 2132);
                    eiVar.f("#1802");
                    return "";
                }
            } else if (fq.a(this.a) && !this.c.k()) {
                this.z = 18;
                this.o.append("飞行模式下关闭了WIFI开关，请关闭飞行模式或者打开WIFI开关#1801");
                fo.a((String) null, 2132);
                eiVar.f("#1801");
                return "";
            }
            if (fq.c() >= 28) {
                if (this.J == null) {
                    this.J = (LocationManager) this.a.getApplicationContext().getSystemService("location");
                }
                if (!((Boolean) fm.a(this.J, "isLocationEnabled", new Object[0])).booleanValue()) {
                    this.z = 12;
                    this.o.append("定位服务没有开启，请在设置中打开定位服务开关#1206");
                    eiVar.f("#1206");
                    fo.a((String) null, 2121);
                    return "";
                }
            }
            if (!fq.e(this.a)) {
                this.z = 12;
                this.o.append("定位权限被禁用,请授予应用定位权限#1201");
                eiVar.f("#1201");
                fo.a((String) null, 2121);
                return "";
            }
            if (fq.c() >= 24 && fq.c() < 28 && Settings.Secure.getInt(this.a.getContentResolver(), "location_mode", 0) == 0) {
                this.z = 12;
                eiVar.f("#1206");
                this.o.append("定位服务没有开启，请在设置中打开定位服务开关#1206");
                fo.a((String) null, 2121);
                return "";
            }
            String strK = this.d.k();
            String strD = this.c.d();
            if (this.c.a(this.b) && strD != null) {
                this.z = 12;
                eiVar.f("#1202");
                this.o.append("获取基站与获取WIFI的权限都被禁用，请在安全软件中打开应用的定位权限#1202");
                fo.a((String) null, 2121);
                return "";
            }
            if (strK != null) {
                this.z = 12;
                if (this.c.k()) {
                    eiVar.f("#1205");
                    sb4 = this.o;
                    str4 = "获取的WIFI列表为空，并且获取基站权限被禁用，请在安全软件中打开应用的定位权限#1205";
                } else {
                    eiVar.f("#1204");
                    sb4 = this.o;
                    str4 = "WIFI开关关闭，并且获取基站权限被禁用，请在安全软件中打开应用的定位权限或者打开WIFI开关#1204";
                }
                sb4.append(str4);
                fo.a((String) null, 2121);
                return "";
            }
            if (!this.c.k() && !this.d.n()) {
                this.z = 19;
                eiVar.f("#1901");
                this.o.append("没有检查到SIM卡，并且WIFI开关关闭，请打开WIFI开关或者插入SIM卡#1901");
                fo.a((String) null, 2133);
                return "";
            }
            if (this.c.k()) {
                eiVar.f("#1302");
                if (this.c.c() != null) {
                    this.o.append("获取到的基站和WIFI信息均为空，请检查是否授予APP定位权限");
                    if (!fq.f(this.a)) {
                        this.o.append("或后台运行没有后台定位权限");
                    }
                    sb3 = this.o;
                    str3 = "#1302";
                } else {
                    this.o.append("获取到的基站和WIFI信息均为空，请移动到有WIFI的区域，若确定当前区域有WIFI，请检查是否授予APP定位权限");
                    if (!fq.f(this.a)) {
                    }
                    sb3 = this.o;
                    str3 = "#1302";
                }
            } else {
                eiVar.f("#1301");
                sb3 = this.o;
                str3 = "获取到的基站为空，并且关闭了WIFI开关，请您打开WIFI开关再发起定位#1301";
            }
            sb3.append(str3);
            this.z = 13;
            fo.a((String) null, 2131);
            return "";
        }
        this.u = this.c.m();
        this.v = ex.a(this.u);
        switch (iH) {
            case 0:
                boolean z2 = !this.g.isEmpty() || this.v;
                boolean z3 = esVarF != null;
                if (!z3) {
                    if (this.v && this.g.isEmpty()) {
                        this.z = 2;
                        eiVar.f("#0201");
                        this.o.append("当前基站为伪基站，并且WIFI权限被禁用，请在安全软件中打开应用的定位权限#0201");
                        fo.a((String) null, 2021);
                    } else if (this.g.size() == 1) {
                        this.z = 2;
                        if (!this.v) {
                            eiVar.f("#0202");
                            this.o.append("当前基站为伪基站，并且搜到的WIFI数量不足，请移动到WIFI比较丰富的区域#0202");
                            fo.a((String) null, 2022);
                        } else if (this.g.get(0).h) {
                            eiVar.f("#0202");
                            this.o.append("当前基站为伪基站，并且搜到的WIFI数量不足，请移动到WIFI比较丰富的区域#0202");
                            fo.a((String) null, 2021);
                        }
                    }
                }
                string = String.format(Locale.US, "#%s#", "network");
                if (z3) {
                    sb2 = new StringBuilder();
                    sb2.append(esVarF.b());
                    str2 = (!this.g.isEmpty() || this.v) ? "cgiwifi" : "cgi";
                    sb2.append("network");
                    sb2.append("#");
                    sb2.append(str2);
                    string = sb2.toString();
                    if (!TextUtils.isEmpty(string)) {
                        if (!string.startsWith("#")) {
                            string = "#" + string;
                        }
                    }
                    break;
                } else {
                    if (z2) {
                        string = string + "wifi";
                    } else if ("network".equals("network")) {
                        string = "";
                        this.z = 2;
                        if (this.c.k()) {
                            eiVar.f("#0204");
                            sb = this.o;
                            str = "当前基站为伪基站,并且没有搜索到WIFI，请移动到WIFI比较丰富的区域#0204";
                        } else {
                            eiVar.f("#0203");
                            sb = this.o;
                            str = "当前基站为伪基站,并且关闭了WIFI开关，请在设置中打开WIFI开关#0203";
                        }
                        sb.append(str);
                        fo.a((String) null, 2022);
                    }
                    if (!TextUtils.isEmpty(string)) {
                    }
                }
                break;
            case 1:
                if (esVarE != null) {
                    sb2 = new StringBuilder();
                    sb2.append(esVarE.a);
                    sb2.append("#");
                    sb2.append(esVarE.b);
                    sb2.append("#");
                    sb2.append(esVarE.c);
                    sb2.append("#");
                    sb2.append(esVarE.d);
                    sb2.append("#");
                    sb2.append("network");
                    sb2.append("#");
                    str2 = (!this.g.isEmpty() || this.v) ? "cgiwifi" : "cgi";
                    sb2.append(str2);
                    string = sb2.toString();
                }
                if (!TextUtils.isEmpty(string)) {
                }
                break;
            case 2:
                if (esVarE != null) {
                    sb2 = new StringBuilder();
                    sb2.append(esVarE.a);
                    sb2.append("#");
                    sb2.append(esVarE.b);
                    sb2.append("#");
                    sb2.append(esVarE.h);
                    sb2.append("#");
                    sb2.append(esVarE.i);
                    sb2.append("#");
                    sb2.append(esVarE.j);
                    sb2.append("#");
                    sb2.append("network");
                    sb2.append("#");
                    if (!this.g.isEmpty() || this.v) {
                    }
                    sb2.append(str2);
                    string = sb2.toString();
                }
                if (!TextUtils.isEmpty(string)) {
                }
                break;
            default:
                this.z = 11;
                fo.a((String) null, TXLiteAVCode.WARNING_VIDEO_DRIVER_VERSION_TOO_LOW);
                eiVar.f("#1101");
                this.o.append("get cgi failure#1101");
                if (!TextUtils.isEmpty(string)) {
                }
                break;
        }
        return "";
    }

    private static void c(eo eoVar) {
        if (eoVar.getErrorCode() == 0 && eoVar.getLocationType() == 0) {
            if ("-5".equals(eoVar.d()) || "1".equals(eoVar.d()) || com.igexin.push.config.c.H.equals(eoVar.d()) || "14".equals(eoVar.d()) || "24".equals(eoVar.d()) || "-1".equals(eoVar.d())) {
                eoVar.setLocationType(5);
            } else {
                eoVar.setLocationType(6);
            }
        }
    }

    private void d(eo eoVar) {
        if (eoVar != null) {
            this.j = eoVar;
        }
    }

    private void i() {
        if (this.n != null) {
            try {
                if (this.i == null) {
                    this.i = new AMapLocationClientOption();
                }
                this.n.a(this.i.getHttpTimeOut(), this.i.getLocationProtocol().equals(AMapLocationClientOption.AMapLocationProtocol.HTTPS), j());
            } catch (Throwable unused) {
            }
        }
    }

    private int j() {
        if (this.i.getGeoLanguage() == null) {
            return 0;
        }
        switch (this.i.getGeoLanguage()) {
        }
        return 0;
    }

    private void k() {
        AMapLocationClientOption.GeoLanguage geoLanguage;
        boolean zIsNeedAddress;
        boolean zIsOffset;
        boolean zIsLocationCacheEnable;
        AMapLocationClientOption.GeoLanguage geoLanguage2 = AMapLocationClientOption.GeoLanguage.DEFAULT;
        try {
            geoLanguage = this.i.getGeoLanguage();
        } catch (Throwable unused) {
            geoLanguage = geoLanguage2;
        }
        try {
            zIsNeedAddress = this.i.isNeedAddress();
            try {
                zIsOffset = this.i.isOffset();
                try {
                    zIsLocationCacheEnable = this.i.isLocationCacheEnable();
                    try {
                        this.t = this.i.isOnceLocationLatest();
                        this.B = this.i.isSensorEnable();
                        if (zIsOffset != this.q || zIsNeedAddress != this.p || zIsLocationCacheEnable != this.s || geoLanguage != this.r) {
                            r();
                        }
                    } catch (Throwable unused2) {
                    }
                } catch (Throwable unused3) {
                    zIsLocationCacheEnable = true;
                }
            } catch (Throwable unused4) {
                zIsOffset = true;
                zIsLocationCacheEnable = zIsOffset;
            }
        } catch (Throwable unused5) {
            zIsNeedAddress = true;
            zIsOffset = true;
            zIsLocationCacheEnable = zIsOffset;
            this.q = zIsOffset;
            this.p = zIsNeedAddress;
            this.s = zIsLocationCacheEnable;
            this.r = geoLanguage;
        }
        this.q = zIsOffset;
        this.p = zIsNeedAddress;
        this.s = zIsLocationCacheEnable;
        this.r = geoLanguage;
    }

    private void l() {
        try {
            if (this.h == null) {
                this.h = new a();
            }
            if (this.I == null) {
                this.I = new IntentFilter();
                this.I.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                this.I.addAction("android.net.wifi.SCAN_RESULTS");
            }
            this.a.registerReceiver(this.h, this.I);
        } catch (Throwable th) {
            fj.a(th, "Aps", "initBroadcastListener");
        }
    }

    private byte[] m() throws Throwable {
        if (this.l == null) {
            this.l = new fh();
        }
        if (this.i == null) {
            this.i = new AMapLocationClientOption();
        }
        this.l.a(this.a, this.i.isNeedAddress(), this.i.isOffset(), this.d, this.c, this.b, this.E, this.Q);
        return this.l.a();
    }

    private boolean n() {
        return this.k == 0 || fq.b() - this.k > 20000;
    }

    private void o() {
        if (this.c == null) {
            return;
        }
        this.c.a(this.m);
    }

    private boolean p() {
        this.g = this.c.e();
        return this.g == null || this.g.size() <= 0;
    }

    private void q() {
        if (this.N != null) {
            this.N = null;
        }
        if (this.w != null) {
            this.w.delete(0, this.w.length());
        }
    }

    private void r() {
        try {
            if (this.e != null) {
                this.e.a();
            }
            d(null);
            this.O = false;
            if (this.D != null) {
                this.D.a();
            }
        } catch (Throwable th) {
            fj.a(th, "Aps", "cleanCache");
        }
    }

    public final eo a(double d, double d2) {
        try {
            String strA = this.n.a(this.a, d, d2);
            if (!strA.contains("\"status\":\"1\"")) {
                return null;
            }
            eo eoVarA = this.f.a(strA);
            eoVarA.setLatitude(d);
            eoVarA.setLongitude(d2);
            return eoVarA;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:50|(1:52)(2:54|(1:56)(9:57|(1:59)|85|60|(2:63|(1:65)(2:66|(1:68)(2:69|(1:71)(1:72))))|73|(3:75|(1:80)(1:79)|81)|82|83))|53|85|60|(2:63|(0)(0))|73|(0)|82|83) */
    /* JADX WARN: Removed duplicated region for block: B:65:0x016e A[Catch: Throwable -> 0x018a, TryCatch #0 {Throwable -> 0x018a, blocks: (B:60:0x015e, B:63:0x0164, B:65:0x016e, B:68:0x0178, B:71:0x0182, B:72:0x0187), top: B:85:0x015e }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final eo a(ei eiVar) throws Throwable {
        float fE;
        long jB;
        d();
        eiVar.e("conitue");
        if (this.a == null) {
            eiVar.f("#0101");
            this.o.append("context is null#0101");
            return a(1, this.o.toString());
        }
        this.K++;
        if (this.K == 1) {
            o();
        }
        if (a(this.k) && fq.a(this.j)) {
            if (this.s && fi.a(this.j.getTime())) {
                this.j.setLocationType(2);
            }
            return this.j;
        }
        if (this.A != null) {
            if (this.B) {
                this.A.a();
            } else {
                this.A.b();
            }
        }
        try {
            this.c.b(this.i.isOnceLocationLatest() || !this.i.isOnceLocation());
            this.g = this.c.e();
        } catch (Throwable th) {
            fj.a(th, "Aps", "getLocation getScanResultsParam");
        }
        try {
            this.d.a(false, p());
        } catch (Throwable th2) {
            fj.a(th2, "Aps", "getLocation getCgiListParam");
        }
        this.N = c(eiVar);
        if (TextUtils.isEmpty(this.N)) {
            return a(this.z, this.o.toString());
        }
        this.w = a(this.w);
        if (this.c.n()) {
            eo eoVarA = a(15, "networkLocation has been mocked!#1502");
            eiVar.f("#1502");
            eoVarA.setMock(true);
            eoVarA.setTrustedLevel(4);
            return eoVarA;
        }
        eo eoVarA2 = this.e.a(this.d, n(), this.j, this.c, this.w, this.N, this.a, false);
        if (fq.a(eoVarA2)) {
            eoVarA2.setTrustedLevel(2);
        } else {
            eoVarA2 = b(true, eiVar);
            if (!fq.a(eoVarA2)) {
                eo eoVarA3 = this.e.a(this.d, false, this.j, this.c, this.w, this.N, this.a, true);
                if (fq.a(eoVarA3)) {
                    eiVar.f("#0001");
                    eoVarA3.setTrustedLevel(2);
                    d(eoVarA3);
                    eoVarA2 = eoVarA3;
                }
                if (this.c != null && eoVarA2 != null) {
                    jB = ex.b();
                    if (jB > 15) {
                        eoVarA2.setTrustedLevel(1);
                    } else if (jB <= 120) {
                        eoVarA2.setTrustedLevel(2);
                    } else if (jB <= 600) {
                        eoVarA2.setTrustedLevel(3);
                    } else {
                        eoVarA2.setTrustedLevel(4);
                    }
                }
                this.e.a(this.N, this.w, eoVarA2, this.a, true);
                fq.a(eoVarA2);
                this.w.delete(0, this.w.length());
                if (eoVarA2 != null) {
                    if (!this.B || this.A == null) {
                        eoVarA2.setAltitude(0.0d);
                        fE = 0.0f;
                        eoVarA2.setBearing(0.0f);
                    } else {
                        eoVarA2.setAltitude(this.A.c());
                        eoVarA2.setBearing(this.A.d());
                        fE = (float) this.A.e();
                    }
                    eoVarA2.setSpeed(fE);
                }
                d(eoVarA2);
                return this.j;
            }
            eoVarA2.e("new");
            this.e.a(this.w.toString());
            this.e.a(this.d.e());
        }
        d(eoVarA2);
        if (this.c != null) {
            jB = ex.b();
            if (jB > 15) {
            }
        }
        this.e.a(this.N, this.w, eoVarA2, this.a, true);
        fq.a(eoVarA2);
        this.w.delete(0, this.w.length());
        if (eoVarA2 != null) {
        }
        d(eoVarA2);
        return this.j;
    }

    public final eo a(eo eoVar) {
        this.D.a(this.s);
        return this.D.a(eoVar);
    }

    public final eo a(boolean z) {
        if (this.c.n()) {
            return a(15, "networkLocation has been mocked!#1502");
        }
        if (TextUtils.isEmpty(this.N)) {
            return a(this.z, this.o.toString());
        }
        eo eoVarA = this.e.a(this.a, this.N, this.w, true, z);
        if (fq.a(eoVarA)) {
            d(eoVarA);
        }
        return eoVarA;
    }

    public final eo a(boolean z, ei eiVar) {
        eiVar.e(z ? "statics" : "first");
        if (this.a == null) {
            eiVar.f("#0101");
            this.o.append("context is null#0101");
            fo.a((String) null, 2011);
            return a(1, this.o.toString());
        }
        if (this.c.n()) {
            eiVar.f("#1502");
            return a(15, "networkLocation has been mocked!#1502");
        }
        b();
        if (TextUtils.isEmpty(this.N)) {
            return a(this.z, this.o.toString());
        }
        eo eoVarB = b(z, eiVar);
        if (fq.a(eoVarB) && !S) {
            this.e.a(this.w.toString());
            this.e.a(this.d.e());
            d(eoVarB);
        }
        S = true;
        return eoVarB;
    }

    public final void a() {
        if (this.d != null) {
            this.d.b();
        }
    }

    public final void a(Context context) {
        try {
            if (this.a != null) {
                return;
            }
            this.D = new en();
            this.a = context.getApplicationContext();
            fq.b(this.a);
            if (this.c == null) {
                this.c = new ex(this.a, (WifiManager) fq.a(this.a, "wifi"), this.P);
            }
            if (this.d == null) {
                this.d = new et(this.a, this.P);
            }
            this.Q = new ev(context, this.P);
            if (this.e == null) {
                this.e = new ez();
            }
            if (this.f == null) {
                this.f = new fg();
            }
        } catch (Throwable th) {
            th.printStackTrace();
            fj.a(th, "Aps", "initBase");
        }
    }

    public final void a(Handler handler) {
        this.P = handler;
    }

    public final void a(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() != 0) {
            return;
        }
        eu euVar = new eu();
        euVar.a = aMapLocation.getLocationType();
        euVar.d = aMapLocation.getTime();
        euVar.e = (int) aMapLocation.getAccuracy();
        euVar.b = aMapLocation.getLatitude();
        euVar.c = aMapLocation.getLongitude();
        if (aMapLocation.getLocationType() == 1) {
            this.Q.a(euVar);
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        this.i = aMapLocationClientOption;
        if (this.i == null) {
            this.i = new AMapLocationClientOption();
        }
        if (this.c != null) {
            ex exVar = this.c;
            this.i.isWifiActiveScan();
            exVar.a(this.i.isWifiScan(), this.i.isMockEnable(), AMapLocationClientOption.isOpenAlwaysScanWifi(), aMapLocationClientOption.getScanWifiInterval());
        }
        i();
        if (this.e != null) {
            this.e.a(this.i);
        }
        if (this.f != null) {
            this.f.a(this.i);
        }
        k();
    }

    public final void a(eo eoVar, int i) {
        if (eoVar != null && eoVar.getErrorCode() == 0) {
            eu euVar = new eu();
            euVar.d = eoVar.getTime();
            euVar.e = (int) eoVar.getAccuracy();
            euVar.b = eoVar.getLatitude();
            euVar.c = eoVar.getLongitude();
            euVar.a = i;
            euVar.g = Integer.parseInt(eoVar.d());
            euVar.h = eoVar.l();
            this.Q.b(euVar);
        }
    }

    public final void b() {
        this.n = fe.a(this.a);
        i();
        if (this.b == null) {
            this.b = (ConnectivityManager) fq.a(this.a, "connectivity");
        }
        if (this.l == null) {
            this.l = new fh();
        }
    }

    public final void b(ei eiVar) {
        try {
            if (this.x) {
                return;
            }
            q();
            if (this.t) {
                l();
            }
            this.c.b(this.t);
            this.g = this.c.e();
            this.d.a(true, p());
            this.N = c(eiVar);
            if (!TextUtils.isEmpty(this.N)) {
                this.w = a(this.w);
            }
        } catch (Throwable th) {
            fj.a(th, "Aps", "initFirstLocateParam");
        }
        this.x = true;
    }

    public final void b(eo eoVar) {
        if (fq.a(eoVar)) {
            this.e.a(this.N, this.w, eoVar, this.a, true);
        }
    }

    public final void c() {
        if (this.A == null) {
            this.A = new eq(this.a);
        }
        l();
        this.c.b(false);
        this.g = this.c.e();
        this.d.a(false, p());
        this.e.a(this.a);
        b(this.a);
        this.y = true;
    }

    public final void d() {
        if (this.o.length() > 0) {
            this.o.delete(0, this.o.length());
        }
    }

    @SuppressLint({"NewApi"})
    public final void e() {
        this.E = null;
        this.x = false;
        this.y = false;
        if (this.e != null) {
            this.e.b(this.a);
        }
        if (this.D != null) {
            this.D.a();
        }
        if (this.f != null) {
            this.f = null;
        }
        if (this.Q != null) {
            this.Q.a(this.H);
        }
        try {
            try {
                if (this.a != null && this.h != null) {
                    this.a.unregisterReceiver(this.h);
                }
            } catch (Throwable th) {
                fj.a(th, "Aps", "destroy");
            }
            if (this.d != null) {
                this.d.a(this.H);
            }
            if (this.c != null) {
                this.c.c(this.H);
            }
            if (this.g != null) {
                this.g.clear();
            }
            if (this.A != null) {
                this.A.f();
            }
            this.j = null;
            this.a = null;
            this.w = null;
            this.J = null;
        } finally {
            this.h = null;
        }
    }

    public final void f() {
        if (this.T != null) {
            this.T.d();
        }
    }

    public final void g() {
        try {
            if (this.a == null) {
                return;
            }
            if (this.T == null) {
                this.T = new ek(this.a);
            }
            this.T.a(this.d, this.c, this.P);
        } catch (Throwable th) {
            an.b(th, "as", "stc");
        }
    }

    public final void h() {
        if (this.T != null) {
            this.T.a();
        }
    }
}
