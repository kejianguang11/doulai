package com.getui.gtc.a;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.base.util.NetworkUtil;
import com.getui.gtc.base.util.ScheduleQueue;
import com.getui.gtc.dim.Caller;
import com.getui.gtc.dim.DimManager;
import com.getui.gtc.dim.DimRequest;
import com.getui.gtc.e.c;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public final class c implements b {
    private static final AtomicBoolean e = new AtomicBoolean(false);
    public long a;
    public long b;
    private String c;
    private boolean d = false;
    private final AtomicBoolean f = new AtomicBoolean(false);

    private c() {
        this.a = 300000L;
        this.b = com.igexin.push.config.c.s;
        Map<String, String> mapA = com.getui.gtc.f.c.a(43200000L, (com.getui.gtc.f.e) null);
        if (mapA == null || mapA.size() <= 0) {
            return;
        }
        try {
            String str = mapA.get("sdk.gtc.type256.interval");
            if (str != null) {
                this.a = Long.parseLong(str) * 1000;
            }
        } catch (Exception e2) {
            com.getui.gtc.i.c.a.b(e2);
        }
        try {
            String str2 = mapA.get("sdk.gtc.type256.delay");
            if (str2 != null) {
                this.b = Long.parseLong(str2) * 1000;
            }
        } catch (Exception e3) {
            com.getui.gtc.i.c.a.b(e3);
        }
    }

    private static Object a(String str) {
        return DimManager.getInstance().get(new DimRequest.Builder().skipCache(true).key(str).caller(Caller.UNKNOWN).build());
    }

    private static String a(Context context) {
        try {
            if (!NetworkUtil.isNetWorkAvailable(context)) {
                com.getui.gtc.i.c.a.a("Iv6 network not connected.");
                return "no network|-1";
            }
            NetworkInfo networkInfoB = b(context);
            boolean z = false;
            boolean z2 = networkInfoB != null && networkInfoB.getType() == 0;
            if (networkInfoB != null && networkInfoB.getType() == 1) {
                z = true;
            }
            String str = (String) a("dim-2-1-16-2");
            if (str == null) {
                str = "";
            }
            if (z2) {
                com.getui.gtc.i.c.a.a("Phone Iv6 List = ".concat(String.valueOf(str)));
                return str + "|1";
            }
            if (!z) {
                return "error|-1";
            }
            com.getui.gtc.i.c.a.a("Wifi Iv6 List = ".concat(String.valueOf(str)));
            return str + "|2";
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return "error|-1";
        }
    }

    private static String a(ScanResult scanResult) {
        if (scanResult == null) {
            return "";
        }
        try {
            if (scanResult.SSID == null) {
                return "";
            }
            String strReplace = scanResult.SSID.replace("|", "").replace("#", "").replace(com.igexin.push.core.b.an, "");
            long jCurrentTimeMillis = 0;
            if (Build.VERSION.SDK_INT >= 17) {
                jCurrentTimeMillis = System.currentTimeMillis() - (((SystemClock.elapsedRealtimeNanos() / 1000) / 1000) - (scanResult.timestamp / 1000));
            }
            return strReplace + "#" + scanResult.BSSID + "#" + scanResult.level + "#" + scanResult.capabilities + "#" + jCurrentTimeMillis;
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return "";
        }
    }

    private static String a(List<ScanResult> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(a(list.get(i)));
                if (i < list.size() - 1) {
                    sb.append(com.igexin.push.core.b.an);
                }
            }
        }
        return sb.toString();
    }

    public static void a() {
        if (e.getAndSet(true)) {
            return;
        }
        c cVar = new c();
        ScheduleQueue.getInstance().addSchedule(cVar, cVar.b, cVar.a);
    }

    private static NetworkInfo b(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Pair<String, String> b() {
        try {
            String str = (String) a("dim-2-1-19-2");
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(com.igexin.push.core.b.an);
                if (strArrSplit.length > 0) {
                    String str2 = strArrSplit[strArrSplit.length - 1];
                    if (!TextUtils.isEmpty(str2)) {
                        return new Pair<>(str2.substring(0, str2.lastIndexOf(124)), str2.substring(str2.lastIndexOf(124) + 1));
                    }
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a(th);
        }
        return new Pair<>("0|0|0|0", "0");
    }

    private void c() {
        JSONArray jSONArray;
        try {
            String str = c.a.a.a.b;
            try {
                jSONArray = !TextUtils.isEmpty(str) ? new JSONArray(str) : new JSONArray();
            } catch (Throwable unused) {
                jSONArray = new JSONArray();
            }
            if (jSONArray.length() < 100) {
                jSONArray.put(this.c);
            }
            c.a.a.a.g(jSONArray.toString());
            for (int i = 0; i < jSONArray.length(); i++) {
                com.getui.gtc.h.a.a(jSONArray.getString(i), 256);
            }
            com.getui.gtc.e.d dVar = c.a.a.a;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (dVar.a(19, jCurrentTimeMillis)) {
                dVar.q = jCurrentTimeMillis;
            }
            c.a.a.a.g("");
        } catch (Exception e2) {
            com.getui.gtc.i.c.a.c("type 256 report error: " + e2.toString());
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (!this.f.getAndSet(true)) {
                try {
                    String str = com.getui.gtc.f.c.a(43200000L, (com.getui.gtc.f.e) null).get("sdk.gtc.type256.enable");
                    if (str != null) {
                        this.d = Boolean.parseBoolean(str);
                    }
                } catch (Exception e2) {
                    com.getui.gtc.i.c.a.b(e2);
                }
                if (System.currentTimeMillis() - c.a.a.a.q < this.a) {
                    com.getui.gtc.i.c.a.c("type 256 collect time not expired");
                    return;
                }
            }
            if (!this.d) {
                com.getui.gtc.i.c.a.b("type 256 is not enabled");
                return;
            }
            if (CommonUtil.isAppDebugEnable()) {
                com.getui.gtc.i.c.a.b("type 256 is debug, disallow");
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Location location = (Location) a("dim-2-1-17-1");
            if (location == null) {
                location = (Location) a("dim-2-1-17-2");
            }
            long time = 0;
            float accuracy = 0.0f;
            if (location != null) {
                time = location.getTime();
                accuracy = location.getAccuracy();
            }
            StringBuilder sb = new StringBuilder();
            if (location == null) {
                sb.append(com.igexin.push.a.i);
                sb.append("|");
                sb.append("0");
                sb.append("|");
                sb.append("0");
                sb.append("|");
                sb.append("0");
            } else {
                sb.append(location.getProvider());
                sb.append("|");
                sb.append(location.getLongitude());
                sb.append("|");
                sb.append(location.getLatitude());
                sb.append("|");
                sb.append(location.getAltitude());
            }
            String string = sb.toString();
            Pair<String, String> pairB = b();
            String strA = a((List<ScanResult>) a("dim-2-1-18-2"));
            String strA2 = a(GtcProvider.context());
            String str2 = (String) a("dim-2-1-18-3");
            if (str2 == null) {
                str2 = "";
            }
            this.c = simpleDateFormat.format(new Date()) + "|" + com.getui.gtc.c.b.d + "|" + com.getui.gtc.c.b.a + "|" + string + "|" + ((String) pairB.first) + "|" + strA + "|" + time + "|" + accuracy + "||ANDROID|" + strA2 + "|" + str2 + "|" + ((String) pairB.second) + "||";
            com.getui.gtc.i.c.a.a(this.c);
            c();
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a("type 256", th);
        }
    }
}
