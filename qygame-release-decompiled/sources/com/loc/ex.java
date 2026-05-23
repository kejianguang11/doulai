package com.loc;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public final class ex {
    static long d;
    static long e;
    static long f;
    public static long g;
    static long h;
    private ek E;
    WifiManager a;
    Context i;
    ew t;
    public static HashMap<String, Long> w = new HashMap<>(36);
    public static long x = 0;
    static int y = 0;
    public static long A = 0;
    ArrayList<dy> b = new ArrayList<>();
    ArrayList<dy> c = new ArrayList<>();
    boolean j = false;
    StringBuilder k = null;
    boolean l = true;
    boolean m = true;
    boolean n = true;
    private volatile WifiInfo C = null;
    String o = null;
    TreeMap<Integer, dy> p = null;
    public boolean q = true;
    public boolean r = true;
    public boolean s = false;
    String u = "";
    long v = 0;
    ConnectivityManager z = null;
    private long D = com.igexin.push.config.c.k;
    volatile boolean B = false;

    public ex(Context context, WifiManager wifiManager, Handler handler) {
        this.a = wifiManager;
        this.i = context;
        this.t = new ew(context, "wifiAgee", handler);
        this.t.a();
    }

    private void A() {
        try {
            if (fq.c(this.i, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF")) {
                this.r = this.a.isWifiEnabled();
            }
        } catch (Throwable unused) {
        }
    }

    private boolean B() {
        this.q = v();
        A();
        if (!this.q || !this.l) {
            return false;
        }
        if (f != 0) {
            if (fq.b() - f < 4900 || fq.b() - g < 1500) {
                return false;
            }
            int i = ((fq.b() - g) > 4900L ? 1 : ((fq.b() - g) == 4900L ? 0 : -1));
        }
        return true;
    }

    private static boolean a(int i) {
        int iCalculateSignalLevel;
        try {
            iCalculateSignalLevel = WifiManager.calculateSignalLevel(i, 20);
        } catch (ArithmeticException e2) {
            fj.a(e2, "Aps", "wifiSigFine");
            iCalculateSignalLevel = 20;
        }
        return iCalculateSignalLevel > 0;
    }

    public static boolean a(WifiInfo wifiInfo) {
        return (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getSSID()) || !fq.a(wifiInfo.getBSSID())) ? false : true;
    }

    public static long b() {
        return ((fq.b() - x) / 1000) + 1;
    }

    private void d(boolean z) {
        String strValueOf;
        if (this.b == null || this.b.isEmpty()) {
            return;
        }
        if (fq.b() - g > 3600000) {
            g();
        }
        if (this.p == null) {
            this.p = new TreeMap<>(Collections.reverseOrder());
        }
        this.p.clear();
        if (this.s && z) {
            try {
                this.c.clear();
            } catch (Throwable unused) {
            }
        }
        int size = this.b.size();
        this.v = 0L;
        for (int i = 0; i < size; i++) {
            dy dyVar = this.b.get(i);
            if (dyVar.h) {
                this.v = dyVar.f;
            }
            if (fq.a(dyVar != null ? dy.a(dyVar.a) : "") && (size <= 20 || a(dyVar.c))) {
                if (this.s && z) {
                    this.c.add(dyVar);
                }
                if (!TextUtils.isEmpty(dyVar.b)) {
                    strValueOf = "<unknown ssid>".equals(dyVar.b) ? "unkwn" : String.valueOf(i);
                    this.p.put(Integer.valueOf((dyVar.c * 25) + i), dyVar);
                }
                dyVar.b = strValueOf;
                this.p.put(Integer.valueOf((dyVar.c * 25) + i), dyVar);
            }
        }
        this.b.clear();
        Iterator<dy> it = this.p.values().iterator();
        while (it.hasNext()) {
            this.b.add(it.next());
        }
        this.p.clear();
    }

    public static String p() {
        return String.valueOf(fq.b() - g);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0087 A[Catch: Throwable -> 0x0104, SecurityException -> 0x010f, TryCatch #2 {SecurityException -> 0x010f, Throwable -> 0x0104, blocks: (B:4:0x0005, B:6:0x000f, B:8:0x0025, B:10:0x002b, B:12:0x0034, B:13:0x0038, B:15:0x003e, B:16:0x0050, B:18:0x0058, B:23:0x006e, B:25:0x0087, B:27:0x0091, B:29:0x0097, B:31:0x009f, B:33:0x00af, B:37:0x00ba, B:39:0x00dc, B:41:0x00f0, B:42:0x00f2, B:43:0x00fe, B:20:0x0060, B:21:0x0066, B:22:0x0069, B:7:0x0016), top: B:50:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009f A[Catch: Throwable -> 0x0104, SecurityException -> 0x010f, TryCatch #2 {SecurityException -> 0x010f, Throwable -> 0x0104, blocks: (B:4:0x0005, B:6:0x000f, B:8:0x0025, B:10:0x002b, B:12:0x0034, B:13:0x0038, B:15:0x003e, B:16:0x0050, B:18:0x0058, B:23:0x006e, B:25:0x0087, B:27:0x0091, B:29:0x0097, B:31:0x009f, B:33:0x00af, B:37:0x00ba, B:39:0x00dc, B:41:0x00f0, B:42:0x00f2, B:43:0x00fe, B:20:0x0060, B:21:0x0066, B:22:0x0069, B:7:0x0016), top: B:50:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private List<dy> r() {
        List<ScanResult> scanResults;
        long jB;
        int size;
        int i;
        if (this.a != null) {
            try {
                if (fq.c(this.i, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF")) {
                    scanResults = this.a.getScanResults();
                } else {
                    fj.a(new Exception("gst_n_aws"), "OPENSDK_WMW", "gsr_n_aws");
                    scanResults = null;
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    HashMap<String, Long> map = new HashMap<>(36);
                    if (scanResults != null) {
                        for (ScanResult scanResult : scanResults) {
                            map.put(scanResult.BSSID, Long.valueOf(scanResult.timestamp));
                        }
                    }
                    if (w.isEmpty() || !w.equals(map)) {
                        w = map;
                        jB = fq.b();
                    }
                    this.o = null;
                    ArrayList arrayList = new ArrayList();
                    this.u = "";
                    this.C = m();
                    if (a(this.C)) {
                        this.u = this.C.getBSSID();
                    }
                    if (scanResults != null && scanResults.size() > 0) {
                        size = scanResults.size();
                        for (i = 0; i < size; i++) {
                            ScanResult scanResult2 = scanResults.get(i);
                            dy dyVar = new dy(!TextUtils.isEmpty(this.u) && this.u.equals(scanResult2.BSSID));
                            dyVar.b = scanResult2.SSID;
                            dyVar.d = scanResult2.frequency;
                            dyVar.e = scanResult2.timestamp;
                            dyVar.a = dy.a(scanResult2.BSSID);
                            dyVar.c = (short) scanResult2.level;
                            if (Build.VERSION.SDK_INT >= 17) {
                                dyVar.g = (short) ((SystemClock.elapsedRealtime() - (scanResult2.timestamp / 1000)) / 1000);
                                if (dyVar.g < 0) {
                                    dyVar.g = (short) 0;
                                }
                            }
                            dyVar.f = fq.b();
                            arrayList.add(dyVar);
                        }
                    }
                    this.t.a((List) arrayList);
                    return arrayList;
                }
                jB = fq.b();
                x = jB;
                this.o = null;
                ArrayList arrayList2 = new ArrayList();
                this.u = "";
                this.C = m();
                if (a(this.C)) {
                }
                if (scanResults != null) {
                    size = scanResults.size();
                    while (i < size) {
                    }
                }
                this.t.a((List) arrayList2);
                return arrayList2;
            } catch (SecurityException e2) {
                this.o = e2.getMessage();
            } catch (Throwable th) {
                this.o = null;
                fj.a(th, "WifiManagerWrapper", "getScanResults");
            }
        }
        return null;
    }

    private int s() {
        if (this.a != null) {
            return this.a.getWifiState();
        }
        return 4;
    }

    private boolean t() {
        long jB = fq.b() - d;
        if (jB < 4900) {
            return false;
        }
        if (u() && jB < 9900) {
            return false;
        }
        if (y > 1) {
            long j = this.D;
            long jN = com.igexin.push.config.c.k;
            if (j != com.igexin.push.config.c.k) {
                jN = this.D;
            } else if (fi.n() != -1) {
                jN = fi.n();
            }
            if (Build.VERSION.SDK_INT >= 28 && jB < jN) {
                return false;
            }
        }
        if (this.a != null) {
            d = fq.b();
            if (y < 2) {
                y++;
            }
            if (fq.c(this.i, "WYW5kcm9pZC5wZXJtaXNzaW9uLkNIQU5HRV9XSUZJX1NUQVRF")) {
                return this.a.startScan();
            }
            fj.a(new Exception("n_cws"), "OPENSDK_WMW", "wfs_n_cws");
        }
        return false;
    }

    private boolean u() {
        if (this.z == null) {
            this.z = (ConnectivityManager) fq.a(this.i, "connectivity");
        }
        return a(this.z);
    }

    private boolean v() {
        if (this.a == null) {
            return false;
        }
        return fq.g(this.i);
    }

    private void w() {
        if (B()) {
            long jB = fq.b();
            if (jB - e >= com.igexin.push.config.c.i) {
                this.b.clear();
                h = g;
            }
            x();
            if (jB - e >= com.igexin.push.config.c.i) {
                for (int i = 20; i > 0 && g == h; i--) {
                    try {
                        Thread.sleep(150L);
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }

    private void x() {
        if (B()) {
            try {
                if (t()) {
                    f = fq.b();
                }
            } catch (Throwable th) {
                fj.a(th, "WifiManager", "wifiScan");
            }
        }
    }

    private void y() {
        if (h != g) {
            List<dy> listR = null;
            try {
                listR = r();
            } catch (Throwable th) {
                fj.a(th, "WifiManager", "updateScanResult");
            }
            h = g;
            if (listR == null) {
                this.b.clear();
            } else {
                this.b.clear();
                this.b.addAll(listR);
            }
        }
    }

    private void z() {
        int iS;
        try {
        } catch (Throwable unused) {
            return;
        }
        if (this.a == null) {
            return;
        }
        try {
            iS = s();
        } catch (Throwable th) {
            fj.a(th, "OPENSDK_WMW", "cwsc");
            iS = 4;
        }
        if (this.b == null) {
            this.b = new ArrayList<>();
        }
        if (iS != 4) {
            switch (iS) {
                case 0:
                case 1:
                    break;
                default:
                    return;
            }
            return;
        }
        g();
    }

    public final ArrayList<dy> a() {
        if (!this.s) {
            return this.c;
        }
        b(true);
        return this.c;
    }

    public final void a(ek ekVar) {
        this.E = ekVar;
    }

    public final void a(boolean z) {
        Context context = this.i;
        if (!fi.m() || !this.n || this.a == null || context == null || !z || fq.c() <= 17) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (((Integer) fm.a("android.provider.Settings$Global", "getInt", new Object[]{contentResolver, "wifi_scan_always_enabled"}, (Class<?>[]) new Class[]{ContentResolver.class, String.class})).intValue() == 0) {
                fm.a("android.provider.Settings$Global", "putInt", new Object[]{contentResolver, "wifi_scan_always_enabled", 1}, (Class<?>[]) new Class[]{ContentResolver.class, String.class, Integer.TYPE});
            }
        } catch (Throwable th) {
            fj.a(th, "WifiManagerWrapper", "enableWifiAlwaysScan");
        }
    }

    public final void a(boolean z, boolean z2, boolean z3, long j) {
        this.l = z;
        this.m = z2;
        this.n = z3;
        if (j < com.igexin.push.config.c.i) {
            this.D = com.igexin.push.config.c.i;
        } else {
            this.D = j;
        }
    }

    public final boolean a(ConnectivityManager connectivityManager) {
        try {
            if (fq.a(connectivityManager.getActiveNetworkInfo()) == 1) {
                return a(c());
            }
            return false;
        } catch (Throwable th) {
            fj.a(th, "WifiManagerWrapper", "wifiAccess");
            return false;
        }
    }

    public final void b(boolean z) {
        if (z) {
            w();
        } else {
            x();
        }
        boolean z2 = false;
        if (this.B) {
            this.B = false;
            z();
        }
        y();
        if (fq.b() - g > 20000) {
            this.b.clear();
        }
        e = fq.b();
        if (this.b.isEmpty()) {
            g = fq.b();
            List<dy> listR = r();
            if (listR != null) {
                this.b.addAll(listR);
                z2 = true;
            }
        }
        d(z2);
    }

    public final WifiInfo c() {
        try {
            if (this.a == null) {
                return null;
            }
            if (fq.c(this.i, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF")) {
                return this.a.getConnectionInfo();
            }
            fj.a(new Exception("gci_n_aws"), "OPENSDK_WMW", "gci_n_aws");
            return null;
        } catch (Throwable th) {
            fj.a(th, "WifiManagerWrapper", "getConnectionInfo");
            return null;
        }
    }

    public final void c(boolean z) {
        g();
        this.b.clear();
        this.t.a(z);
    }

    public final String d() {
        return this.o;
    }

    public final ArrayList<dy> e() {
        if (this.b == null) {
            return null;
        }
        ArrayList<dy> arrayList = new ArrayList<>();
        if (!this.b.isEmpty()) {
            arrayList.addAll(this.b);
        }
        return arrayList;
    }

    public final void f() {
        try {
            this.s = true;
            List<dy> listR = r();
            if (listR != null) {
                this.b.clear();
                this.b.addAll(listR);
            }
            d(true);
        } catch (Throwable unused) {
        }
    }

    public final void g() {
        this.C = null;
        this.b.clear();
    }

    public final void h() {
        A = System.currentTimeMillis();
        if (this.E != null) {
            this.E.b();
        }
    }

    public final void i() {
        if (this.a != null && fq.b() - g > 4900) {
            g = fq.b();
        }
    }

    public final void j() {
        if (this.a == null) {
            return;
        }
        this.B = true;
    }

    public final boolean k() {
        return this.q;
    }

    public final boolean l() {
        return this.r;
    }

    public final WifiInfo m() {
        this.C = c();
        return this.C;
    }

    public final boolean n() {
        return this.j;
    }

    public final String o() {
        if (this.k == null) {
            this.k = new StringBuilder(700);
        } else {
            this.k.delete(0, this.k.length());
        }
        this.j = false;
        int size = this.b.size();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            String strA = dy.a(this.b.get(i).a);
            if (!this.m && !"<unknown ssid>".equals(this.b.get(i).b)) {
                z = true;
            }
            String str = "nb";
            if (!TextUtils.isEmpty(this.u) && this.u.equals(strA)) {
                str = "access";
                z2 = true;
            }
            this.k.append(String.format(Locale.US, "#%s,%s", strA, str));
        }
        if (this.b.size() == 0) {
            z = true;
        }
        if (!this.m && !z) {
            this.j = true;
        }
        if (!z2 && !TextUtils.isEmpty(this.u)) {
            StringBuilder sb = this.k;
            sb.append("#");
            sb.append(this.u);
            this.k.append(",access");
        }
        return this.k.toString();
    }

    public final long q() {
        return this.v;
    }
}
