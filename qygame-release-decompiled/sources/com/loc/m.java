package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.loc.bj;
import com.loc.bl;
import com.mobile.auth.gatewayauth.Constant;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class m {
    private static volatile boolean C = false;
    public static int a = -1;
    public static String b = "";
    public static Context c = null;
    private static String k = "6";
    private static String l = "4";
    private static String m = "9";
    private static String n = "8";
    private static volatile boolean o = true;
    private static Vector<e> p = new Vector<>();
    private static Map<String, Integer> q = new HashMap();
    private static String r = null;
    private static long s = 0;
    public static volatile boolean d = false;
    private static volatile ConcurrentHashMap<String, Long> t = new ConcurrentHashMap<>(8);
    private static volatile ConcurrentHashMap<String, Long> u = new ConcurrentHashMap<>(8);
    private static volatile ConcurrentHashMap<String, d> v = new ConcurrentHashMap<>(8);
    private static boolean w = false;
    public static int e = Constant.DEFAULT_TIMEOUT;
    public static boolean f = true;
    public static boolean g = false;
    private static int x = 3;
    public static boolean h = true;
    public static boolean i = false;
    private static int y = 3;
    public static boolean j = false;
    private static ConcurrentHashMap<String, Boolean> z = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Boolean> A = new ConcurrentHashMap<>();
    private static ArrayList<bj.a> B = new ArrayList<>();
    private static Queue<bj.c> D = new LinkedList();

    public interface a {
        void a(b bVar);
    }

    public static class b {

        @Deprecated
        public JSONObject a;

        @Deprecated
        public JSONObject b;
        public String c;
        public int d = -1;
        public long e = 0;
        public JSONObject f;
        public a g;
        public C0040b h;
        private boolean i;

        public static class a {
            public boolean a;
            public boolean b;
            public JSONObject c;
        }

        /* JADX INFO: renamed from: com.loc.m$b$b, reason: collision with other inner class name */
        public static class C0040b {
            public boolean a;
        }
    }

    static class c extends bh {
        private String h;
        private Map<String, String> i;
        private String j;
        private String k;
        private String l;

        c(Context context, w wVar, String str, String str2, String str3, String str4) {
            super(context, wVar);
            this.h = str;
            this.i = null;
            this.j = str2;
            this.k = str3;
            this.l = str4;
            a(bl.c.HTTPS);
            a(bl.a.FIX);
        }

        private static String a(String str, String str2) {
            try {
                return !TextUtils.isEmpty(str2) ? Uri.parse(str).buildUpon().encodedAuthority(str2).build().toString() : str;
            } catch (Throwable unused) {
                return str;
            }
        }

        @Override // com.loc.bl
        public final Map<String, String> a() {
            if (TextUtils.isEmpty(this.l)) {
                return null;
            }
            HashMap map = new HashMap();
            map.put(com.alipay.sdk.cons.c.f, this.l);
            return map;
        }

        @Override // com.loc.bh
        public final byte[] a_() {
            return null;
        }

        @Override // com.loc.bl
        public final String b() {
            return a("https://restsdk.amap.com/v3/iasdkauth", this.j);
        }

        @Override // com.loc.bh
        public final byte[] b_() {
            String strU = o.u(this.a);
            if (!TextUtils.isEmpty(strU)) {
                strU = s.a(new StringBuilder(strU).reverse().toString());
            }
            HashMap map = new HashMap();
            map.put("authkey", TextUtils.isEmpty(this.h) ? "" : this.h);
            map.put("plattype", "android");
            map.put("product", this.b.a());
            map.put("version", this.b.b());
            map.put("output", "json");
            StringBuilder sb = new StringBuilder();
            sb.append(Build.VERSION.SDK_INT);
            map.put("androidversion", sb.toString());
            map.put("deviceId", strU);
            map.put("manufacture", Build.MANUFACTURER);
            if (this.i != null && !this.i.isEmpty()) {
                map.putAll(this.i);
            }
            map.put("abitype", x.a(this.a));
            map.put("ext", this.b.d());
            return x.a(x.a(map));
        }

        @Override // com.loc.r, com.loc.bl
        public final String c() {
            return a("https://dualstack-arestapi.amap.com/v3/iasdkauth", this.k);
        }

        @Override // com.loc.bh
        protected final String f() {
            return "3.0";
        }

        @Override // com.loc.bl
        protected final String g() {
            return !TextUtils.isEmpty(this.l) ? this.l : super.g();
        }
    }

    private static class d {
        w a;
        String b;
        a c;

        private d() {
        }

        /* synthetic */ d(byte b) {
            this();
        }
    }

    public static class e {
        private String a;
        private String b;
        private AtomicInteger c;

        public e(String str, String str2, int i) {
            this.a = str;
            this.b = str2;
            this.c = new AtomicInteger(i);
        }

        public static e b(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                return new e(jSONObject.optString("a"), jSONObject.optString("f"), jSONObject.optInt(al.g));
            } catch (Throwable unused) {
                return null;
            }
        }

        public final int a() {
            if (this.c == null) {
                return 0;
            }
            return this.c.get();
        }

        public final void a(String str) {
            this.b = str;
        }

        public final String b() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("a", this.a);
                jSONObject.put("f", this.b);
                jSONObject.put(al.g, this.c.get());
                return jSONObject.toString();
            } catch (Throwable unused) {
                return "";
            }
        }
    }

    public static class f {
        public static boolean a = true;
        public static boolean b = false;
        public static boolean c = true;
        public static int d = 0;
        public static boolean e = false;
        public static int f;
    }

    public static b a(Context context, w wVar, String str, String str2, String str3, String str4) {
        return b(context, wVar, str, str2, str3, str4);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002f A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static synchronized e a(Context context, String str, String str2) {
        e eVar;
        if (!TextUtils.isEmpty(str)) {
            for (int i2 = 0; i2 < p.size(); i2++) {
                eVar = p.get(i2);
                if (eVar != null && str.equals(eVar.a)) {
                    break;
                }
            }
            eVar = null;
            if (eVar == null) {
                return eVar;
            }
            if (context == null) {
                return null;
            }
            e eVarB = e.b(av.b(context, str2, str, ""));
            String strA = x.a(System.currentTimeMillis(), "yyyyMMdd");
            if (eVarB == null) {
                eVarB = new e(str, strA, 0);
            }
            if (!strA.equals(eVarB.b)) {
                eVarB.a(strA);
                eVarB.c.set(0);
            }
            p.add(eVarB);
            return eVarB;
        }
        eVar = null;
        if (eVar == null) {
        }
    }

    public static void a(Context context) {
        if (context != null) {
            c = context.getApplicationContext();
        }
    }

    private static void a(Context context, w wVar, String str) {
        HashMap map = new HashMap();
        map.put("amap_sdk_auth_fail", "1");
        map.put("amap_sdk_auth_fail_type", str);
        map.put("amap_sdk_name", wVar.a());
        map.put("amap_sdk_version", wVar.c());
        String string = new JSONObject(map).toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            br brVar = new br(context, "core", "2.0", "O001");
            brVar.a(string);
            bs.a(brVar, context);
        } catch (k unused) {
        }
    }

    public static synchronized void a(Context context, w wVar, String str, a aVar) {
        if (context != null) {
            if (wVar != null) {
                try {
                    if (c == null) {
                        c = context.getApplicationContext();
                    }
                    String strA = wVar.a();
                    if (TextUtils.isEmpty(strA)) {
                        return;
                    }
                    a(wVar);
                    if (v == null) {
                        v = new ConcurrentHashMap<>(8);
                    }
                    if (u == null) {
                        u = new ConcurrentHashMap<>(8);
                    }
                    if (t == null) {
                        t = new ConcurrentHashMap<>(8);
                    }
                    if (!v.containsKey(strA)) {
                        d dVar = new d((byte) 0);
                        dVar.a = wVar;
                        dVar.b = str;
                        dVar.c = aVar;
                        v.put(strA, dVar);
                        t.put(strA, Long.valueOf(av.b(c, "open_common", strA)));
                        d(c);
                    }
                } catch (Throwable th) {
                    ak.a(th, "at", "rglc");
                }
            }
        }
    }

    private static void a(Context context, w wVar, String str, b bVar, JSONObject jSONObject) throws JSONException {
        boolean zA;
        b.a aVar = new b.a();
        aVar.a = false;
        aVar.b = false;
        bVar.g = aVar;
        try {
            String[] strArrSplit = str.split(com.alipay.sdk.util.h.b);
            if (strArrSplit != null && strArrSplit.length > 0) {
                for (String str2 : strArrSplit) {
                    if (jSONObject.has(str2)) {
                        bVar.f.putOpt(str2, jSONObject.get(str2));
                    }
                }
            }
        } catch (Throwable th) {
            ak.a(th, "at", "co");
        }
        if (x.a(jSONObject, "16H")) {
            try {
                bVar.i = a(jSONObject.getJSONObject("16H").optString("able"), false);
            } catch (Throwable th2) {
                ak.a(th2, "AuthConfigManager", "load 16H");
            }
        }
        if (x.a(jSONObject, "11K")) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("11K");
                aVar.a = a(jSONObject2.getString("able"), false);
                if (jSONObject2.has("off")) {
                    aVar.c = jSONObject2.getJSONObject("off");
                }
            } catch (Throwable th3) {
                ak.a(th3, "AuthConfigManager", "load 11K");
            }
        }
        if (x.a(jSONObject, "145")) {
            try {
                bVar.a = jSONObject.getJSONObject("145");
            } catch (Throwable th4) {
                ak.a(th4, "AuthConfigManager", "load 145");
            }
        }
        if (x.a(jSONObject, "14D")) {
            try {
                bVar.b = jSONObject.getJSONObject("14D");
            } catch (Throwable th5) {
                ak.a(th5, "AuthConfigManager", "load 14D");
            }
        }
        if (x.a(jSONObject, "151")) {
            try {
                JSONObject jSONObject3 = jSONObject.getJSONObject("151");
                b.C0040b c0040b = new b.C0040b();
                if (jSONObject3 != null) {
                    c0040b.a = a(jSONObject3.optString("able"), false);
                }
                bVar.h = c0040b;
            } catch (Throwable th6) {
                ak.a(th6, "AuthConfigManager", "load 151");
            }
        }
        if (x.a(jSONObject, "17S")) {
            try {
                JSONObject jSONObject4 = jSONObject.getJSONObject("17S");
                if (jSONObject4 != null && (zA = a(jSONObject4.optString("able"), false)) != o) {
                    o = zA;
                    if (context != null) {
                        SharedPreferences.Editor editorA = av.a(context, "open_common");
                        av.a(editorA, "a2", zA);
                        av.a(editorA);
                    }
                }
                if (jSONObject4 != null) {
                    boolean zA2 = a(jSONObject4.optString("static_enable"), true);
                    boolean zA3 = a(jSONObject4.optString("static_ip_direct_enable"), false);
                    int iOptInt = jSONObject4.optInt("static_timeout", 5) * 1000;
                    int iOptInt2 = jSONObject4.optInt("static_retry", 3);
                    boolean zA4 = a(jSONObject4.optString("bgp_enable"), true);
                    boolean zA5 = a(jSONObject4.optString("bgp_ip_direct_enable"), false);
                    int iOptInt3 = jSONObject4.optInt("bgp_retry", 3);
                    boolean zA6 = a(jSONObject4.optString("perf_data_upload_enable"), false);
                    if (zA2 != f || zA3 != g || iOptInt != e || iOptInt2 != x || zA4 != h || zA5 != i || iOptInt3 != y || zA6 != j) {
                        f = zA2;
                        g = zA3;
                        e = iOptInt;
                        x = iOptInt2;
                        h = zA4;
                        i = zA5;
                        y = iOptInt3;
                        j = zA6;
                        if (context != null) {
                            SharedPreferences.Editor editorA2 = av.a(context, "open_common");
                            av.a(editorA2, "a13", zA2);
                            av.a(editorA2, "a6", zA4);
                            av.a(editorA2, "a7", zA3);
                            av.a(editorA2, "a8", iOptInt);
                            av.a(editorA2, "a9", iOptInt2);
                            av.a(editorA2, "a10", zA5);
                            av.a(editorA2, "a11", iOptInt3);
                            av.a(editorA2, "a12", zA6);
                            av.a(editorA2);
                        }
                    }
                    new StringBuilder("static_enable=").append(f);
                    bj.a();
                    new StringBuilder("bgp_enable=").append(h);
                    bj.a();
                    new StringBuilder("static_ip_direct_enable=").append(g);
                    bj.a();
                    new StringBuilder("bgp_ip_direct_enable=").append(i);
                    bj.a();
                    new StringBuilder("perf_data_upload_enable=").append(j);
                    bj.a();
                }
            } catch (Throwable th7) {
                ak.a(th7, "AuthConfigManager", "load 17S");
            }
        }
        if (x.a(jSONObject, "15K")) {
            try {
                JSONObject jSONObject5 = jSONObject.getJSONObject("15K");
                if (jSONObject5 != null) {
                    boolean zA7 = a(jSONObject5.optString("ucf"), f.a);
                    boolean zA8 = a(jSONObject5.optString("fsv2"), f.b);
                    boolean zA9 = a(jSONObject5.optString("usc"), f.c);
                    int iOptInt4 = jSONObject5.optInt("umv", f.d);
                    boolean zA10 = a(jSONObject5.optString("ust"), f.e);
                    int iOptInt5 = jSONObject5.optInt("ustv", f.f);
                    if (zA7 != f.a || zA8 != f.b || zA9 != f.c || iOptInt4 != f.d || zA10 != f.e || iOptInt5 != f.d) {
                        f.a = zA7;
                        f.b = zA8;
                        f.c = zA9;
                        f.d = iOptInt4;
                        f.e = zA10;
                        f.f = iOptInt5;
                        try {
                            SharedPreferences.Editor editorA3 = av.a(context, "open_common");
                            av.a(editorA3, "ucf", f.a);
                            av.a(editorA3, "fsv2", f.b);
                            av.a(editorA3, "usc", f.c);
                            av.a(editorA3, "umv", f.d);
                            av.a(editorA3, "ust", f.e);
                            av.a(editorA3, "ustv", f.f);
                            av.a(editorA3);
                        } catch (Throwable unused) {
                        }
                    }
                }
            } catch (Throwable th8) {
                ak.a(th8, "AuthConfigManager", "load 15K");
            }
        }
        if (x.a(jSONObject, "183")) {
            try {
                bi.a(wVar, jSONObject.getJSONObject("183"));
            } catch (Throwable th9) {
                ak.a(th9, "AuthConfigManager", "load 183");
            }
        }
        if (x.a(jSONObject, "17I")) {
            try {
                JSONObject jSONObject6 = jSONObject.getJSONObject("17I");
                boolean zA11 = a(jSONObject6.optString("na"), false);
                boolean zA12 = a(jSONObject6.optString("aa"), false);
                ag.d = zA11;
                ag.e = zA12;
                SharedPreferences.Editor editorA4 = av.a(context, "open_common");
                av.a(editorA4, "a4", zA11);
                av.a(editorA4, "a5", zA12);
                av.a(editorA4);
            } catch (Throwable th10) {
                ak.a(th10, "AuthConfigManager", "load 17I");
            }
        }
    }

    private static void a(Context context, w wVar, Throwable th) {
        a(context, wVar, th.getMessage());
    }

    public static void a(Context context, String str) {
        l.a(context, str);
    }

    private static void a(Context context, String str, String str2, e eVar) {
        if (eVar == null || TextUtils.isEmpty(eVar.a)) {
            return;
        }
        String strB = eVar.b();
        if (TextUtils.isEmpty(strB) || context == null) {
            return;
        }
        SharedPreferences.Editor editorA = av.a(context, str2);
        editorA.putString(str, strB);
        av.a(editorA);
    }

    public static void a(bj.c cVar) {
        if (cVar == null || c == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("serverip", cVar.c);
        map.put("hostname", cVar.e);
        map.put("path", cVar.d);
        map.put("csid", cVar.a);
        map.put("degrade", String.valueOf(cVar.b.a()));
        map.put("errorcode", String.valueOf(cVar.m));
        map.put("errorsubcode", String.valueOf(cVar.n));
        map.put("connecttime", String.valueOf(cVar.h));
        map.put("writetime", String.valueOf(cVar.i));
        map.put("readtime", String.valueOf(cVar.j));
        map.put("datasize", String.valueOf(cVar.l));
        map.put("totaltime", String.valueOf(cVar.f));
        String string = new JSONObject(map).toString();
        "--埋点--".concat(String.valueOf(string));
        bj.a();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            br brVar = new br(c, "core", "2.0", "O008");
            brVar.a(string);
            bs.a(brVar, c);
        } catch (k unused) {
        }
    }

    private static void a(w wVar) {
        if (wVar != null) {
            try {
                if (TextUtils.isEmpty(wVar.a())) {
                    return;
                }
                String strC = wVar.c();
                if (TextUtils.isEmpty(strC)) {
                    strC = wVar.b();
                }
                if (TextUtils.isEmpty(strC)) {
                    return;
                }
                ag.a(wVar.a(), strC);
            } catch (Throwable unused) {
            }
        }
    }

    private static void a(String str, String str2) {
        e eVarA = a(c, str, str2);
        String strA = x.a(System.currentTimeMillis(), "yyyyMMdd");
        if (!strA.equals(eVarA.b)) {
            eVarA.a(strA);
            eVarA.c.set(0);
        }
        eVarA.c.incrementAndGet();
        a(c, str, str2, eVarA);
    }

    public static synchronized void a(final String str, boolean z2, final String str2, final String str3, final String str4) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (u == null) {
                u = new ConcurrentHashMap<>(8);
            }
            u.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
            if (v == null) {
                return;
            }
            if (v.containsKey(str)) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                if (z2) {
                    bi.a(true, str);
                }
                cj.a().b(new ck() { // from class: com.loc.m.1
                    @Override // com.loc.ck
                    public final void a() {
                        d dVar = (d) m.v.get(str);
                        if (dVar == null) {
                            return;
                        }
                        a aVar = dVar.c;
                        b bVarA = m.a(m.c, dVar.a, dVar.b, str2, str3, str4);
                        if (bVarA == null || aVar == null) {
                            return;
                        }
                        aVar.a(bVarA);
                    }
                });
            }
        } catch (Throwable th) {
            ak.a(th, "at", "lca");
        }
    }

    public static void a(String str, boolean z2, boolean z3, boolean z4) {
        String str2;
        String str3;
        if (TextUtils.isEmpty(str) || c == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put(Constant.PROTOCOL_WEB_VIEW_URL, str);
        map.put("downLevel", String.valueOf(z2));
        map.put("ant", o.o(c) == 0 ? "0" : "1");
        if (z4) {
            str2 = "type";
            str3 = z2 ? m : n;
        } else {
            str2 = "type";
            str3 = z2 ? k : l;
        }
        map.put(str2, str3);
        map.put("status", z3 ? "0" : "1");
        String string = new JSONObject(map).toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            br brVar = new br(c, "core", "2.0", "O002");
            brVar.a(string);
            bs.a(brVar, c);
        } catch (k unused) {
        }
    }

    public static void a(boolean z2, bj.a aVar) {
        if (!C || aVar == null) {
            return;
        }
        synchronized (B) {
            if (z2) {
                try {
                    Iterator<bj.a> it = B.iterator();
                    while (it.hasNext()) {
                        bj.a next = it.next();
                        if (next.b.equals(aVar.b) && next.e.equals(aVar.e) && next.f == aVar.f) {
                            if (next.j == aVar.j) {
                                it.remove();
                            } else {
                                next.j.set(next.j.get() - aVar.j.get());
                            }
                            bj.a();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            C = false;
            Iterator<bj.a> it2 = B.iterator();
            while (true) {
                bj.a();
                if (it2.hasNext()) {
                    bj.a next2 = it2.next();
                    StringBuilder sb = new StringBuilder("----path=");
                    sb.append(next2.e);
                    sb.append("-counts=");
                    sb.append(next2.j);
                    sb.append("-code=");
                    sb.append(next2.f);
                    sb.append("----");
                } else {
                    bj.a();
                }
            }
        }
    }

    public static void a(boolean z2, String str) {
        try {
            "--markHostNameFailed---hostname=".concat(String.valueOf(str));
            bj.a();
            if (f || z2) {
                if ((i || !z2) && !TextUtils.isEmpty(str)) {
                    if (z2) {
                        if (A.get(str) != null) {
                            return;
                        }
                        A.put(str, Boolean.TRUE);
                        a(b(str, "a15"), "open_common");
                        return;
                    }
                    if (z.get(str) != null) {
                        return;
                    }
                    z.put(str, Boolean.TRUE);
                    a(b(str, "a14"), "open_common");
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean a() {
        e eVarA;
        if (c != null) {
            i();
            if (!c()) {
                return false;
            }
            if (b()) {
                return true;
            }
        }
        return o && (eVarA = a(c, "IPV6_CONFIG_NAME", "open_common")) != null && eVarA.a() < 5;
    }

    public static synchronized boolean a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (v == null) {
                return false;
            }
            if (u == null) {
                u = new ConcurrentHashMap<>(8);
            }
            if (v.containsKey(str) && !u.containsKey(str)) {
                u.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                return true;
            }
        } catch (Throwable th) {
            ak.a(th, "at", "cslct");
        }
        return false;
    }

    public static synchronized boolean a(String str, long j2) {
        boolean z2 = false;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (j2 > c(str)) {
                long jLongValue = 0;
                if (u != null && u.containsKey(str)) {
                    jLongValue = u.get(str).longValue();
                }
                if (SystemClock.elapsedRealtime() - jLongValue > com.igexin.push.config.c.k) {
                    z2 = true;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return z2;
    }

    public static boolean a(String str, boolean z2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return z2;
            }
            String[] strArrSplit = URLDecoder.decode(str).split("/");
            return strArrSplit[strArrSplit.length - 1].charAt(4) % 2 == 1;
        } catch (Throwable unused) {
            return z2;
        }
    }

    private static boolean a(InetAddress inetAddress) {
        return inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress();
    }

    /* JADX WARN: Removed duplicated region for block: B:92:0x015a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x015b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static b b(Context context, w wVar, String str, String str2, String str3, String str4) {
        String str5;
        String string;
        bm bmVarA;
        byte[] bArr;
        String str6;
        String str7;
        boolean zIsEmpty;
        StringBuilder sb;
        List<String> list;
        b bVar = new b();
        bVar.f = new JSONObject();
        if (context != null) {
            c = context.getApplicationContext();
        }
        e();
        String strA = null;
        try {
            try {
                a(wVar);
                new bg();
                zIsEmpty = TextUtils.isEmpty(str);
            } catch (IllegalBlockSizeException e2) {
                th = e2;
            }
            try {
                try {
                    if (zIsEmpty) {
                        string = str;
                    } else {
                        try {
                            sb = new StringBuilder();
                            str5 = str;
                        } catch (k e3) {
                            e = e3;
                            str5 = str;
                        } catch (Throwable unused) {
                            str5 = str;
                        }
                        try {
                            sb.append(str5);
                            sb.append(";15K;16H;17I;17S;183");
                            string = sb.toString();
                        } catch (k e4) {
                            e = e4;
                            try {
                                throw e;
                            } catch (k e5) {
                                e = e5;
                                string = str5;
                                bmVarA = null;
                                bArr = null;
                                bVar.c = e.a();
                                a(context, wVar, e.a());
                                an.a(wVar, "/v3/iasdkauth", e);
                                if (bArr == null) {
                                }
                            } catch (Throwable th) {
                                th = th;
                                string = str5;
                                bmVarA = null;
                                bArr = null;
                                an.b(th, "at", "lc");
                                a(context, wVar, th);
                                if (bArr == null) {
                                }
                            }
                        } catch (Throwable unused2) {
                            string = str5;
                            try {
                                throw new k("未知的错误");
                            } catch (k e6) {
                                e = e6;
                                bmVarA = null;
                                bArr = null;
                                bVar.c = e.a();
                                a(context, wVar, e.a());
                                an.a(wVar, "/v3/iasdkauth", e);
                                if (bArr == null) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                bmVarA = null;
                                bArr = null;
                                an.b(th, "at", "lc");
                                a(context, wVar, th);
                                if (bArr == null) {
                                }
                            }
                        }
                    }
                } catch (IllegalBlockSizeException e7) {
                    th = e7;
                    bmVarA = null;
                    bArr = null;
                    a(context, wVar, th);
                    if (bArr == null) {
                    }
                }
            } catch (IllegalBlockSizeException e8) {
                th = e8;
                string = str;
                bmVarA = null;
                bArr = null;
                a(context, wVar, th);
                if (bArr == null) {
                }
            }
        } catch (k e9) {
            e = e9;
            str5 = str;
        } catch (Throwable th3) {
            th = th3;
            str5 = str;
        }
        try {
            d(context);
            bmVarA = bg.a(new c(context, wVar, string, str2, str3, str4));
            if (zIsEmpty) {
                return bVar;
            }
            if (bmVarA != null) {
                try {
                    bArr = bmVarA.a;
                    try {
                        try {
                            Map<String, List<String>> map = bmVarA.b;
                            if (map != null && map.containsKey("lct") && (list = map.get("lct")) != null && list.size() > 0) {
                                String str8 = list.get(0);
                                if (!TextUtils.isEmpty(str8)) {
                                    bVar.e = Long.valueOf(str8).longValue();
                                    if (wVar != null && bVar.e != 0) {
                                        String strA2 = wVar.a();
                                        if (!TextUtils.isEmpty(strA2)) {
                                            b(strA2, bVar.e);
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th4) {
                            try {
                                an.b(th4, "at", "lct");
                            } catch (Throwable th5) {
                                th = th5;
                                an.b(th, "at", "lc");
                                a(context, wVar, th);
                            }
                        }
                    } catch (k e10) {
                        e = e10;
                        bVar.c = e.a();
                        a(context, wVar, e.a());
                        an.a(wVar, "/v3/iasdkauth", e);
                    } catch (IllegalBlockSizeException e11) {
                        th = e11;
                        a(context, wVar, th);
                    }
                } catch (k e12) {
                    e = e12;
                    bArr = null;
                    bVar.c = e.a();
                    a(context, wVar, e.a());
                    an.a(wVar, "/v3/iasdkauth", e);
                } catch (IllegalBlockSizeException e13) {
                    th = e13;
                    bArr = null;
                    a(context, wVar, th);
                } catch (Throwable th6) {
                    th = th6;
                    bArr = null;
                    an.b(th, "at", "lc");
                    a(context, wVar, th);
                }
            } else {
                bArr = null;
            }
            byte[] bArr2 = new byte[16];
            byte[] bArr3 = new byte[bArr.length - 16];
            System.arraycopy(bArr, 0, bArr2, 0, 16);
            System.arraycopy(bArr, 16, bArr3, 0, bArr.length - 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, x.c("EQUVT"));
            Cipher cipher = Cipher.getInstance(x.c("CQUVTL0NCQy9QS0NTNVBhZGRpbmc"));
            cipher.init(2, secretKeySpec, new IvParameterSpec(x.c()));
            strA = x.a(cipher.doFinal(bArr3));
            if (bArr == null) {
                return bVar;
            }
            if (TextUtils.isEmpty(strA)) {
                strA = x.a(bArr);
            }
            if (TextUtils.isEmpty(strA)) {
                a(context, wVar, "result is null");
            }
            try {
                JSONObject jSONObject = new JSONObject(strA);
                if (jSONObject.has("status")) {
                    int i2 = jSONObject.getInt("status");
                    if (i2 == 1) {
                        a = 1;
                    } else if (i2 == 0) {
                        if (bmVarA != null) {
                            str6 = bmVarA.c;
                            str7 = bmVarA.d;
                        } else {
                            str6 = "authcsid";
                            str7 = "authgsid";
                        }
                        x.a(context, str6, str7, jSONObject);
                        a = 0;
                        if (jSONObject.has("info")) {
                            b = jSONObject.getString("info");
                        }
                        an.a(wVar, "/v3/iasdkauth", b, str7, str6, jSONObject.has("infocode") ? jSONObject.getString("infocode") : "");
                        if (a == 0) {
                            bVar.c = b;
                            return bVar;
                        }
                    }
                    try {
                        if (jSONObject.has("ver")) {
                            bVar.d = jSONObject.getInt("ver");
                        }
                    } catch (Throwable th7) {
                        ak.a(th7, "at", "lc");
                    }
                    if (x.a(jSONObject, com.alipay.sdk.util.j.c)) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject(com.alipay.sdk.util.j.c);
                        a(context, wVar, string, bVar, jSONObject2);
                        try {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("15K");
                            boolean zA = a(jSONObject3.optString("isTargetAble"), false);
                            if (a(jSONObject3.optString("able"), false)) {
                                q.a().a(context, zA);
                            } else {
                                q.a();
                                q.b(context);
                            }
                        } catch (Throwable unused3) {
                        }
                    }
                }
            } catch (Throwable th8) {
                ak.a(th8, "at", "lc");
            }
            return bVar;
        } catch (k e14) {
            e = e14;
            str5 = string;
            throw e;
        } catch (Throwable unused4) {
            throw new k("未知的错误");
        }
    }

    private static String b(String str, String str2) {
        return str2 + "_" + s.a(str.getBytes());
    }

    public static void b(Context context) {
        if (context == null) {
            return;
        }
        o = av.a(context, "open_common", "a2", true);
    }

    public static void b(bj.c cVar) {
        synchronized (B) {
            boolean z2 = false;
            for (int i2 = 0; i2 < B.size(); i2++) {
                bj.a aVar = B.get(i2);
                if (cVar.c.equals(aVar.b) && cVar.d.equals(aVar.e) && cVar.m == aVar.f) {
                    if (aVar.f == 1) {
                        aVar.i = ((((long) aVar.j.get()) * aVar.i) + cVar.f) / ((long) (aVar.j.get() + 1));
                    }
                    aVar.j.getAndIncrement();
                    z2 = true;
                }
            }
            if (!z2) {
                B.add(new bj.a(cVar));
            }
            bj.a();
        }
    }

    public static synchronized void b(String str) {
        if (u == null) {
            return;
        }
        if (u.containsKey(str)) {
            u.remove(str);
        }
    }

    private static synchronized void b(String str, long j2) {
        try {
            if (v != null && v.containsKey(str)) {
                if (t == null) {
                    t = new ConcurrentHashMap<>(8);
                }
                t.put(str, Long.valueOf(j2));
                if (c != null) {
                    SharedPreferences.Editor editorA = av.a(c, "open_common");
                    av.a(editorA, str, j2);
                    av.a(editorA);
                }
            }
        } catch (Throwable th) {
            ak.a(th, "at", "ucut");
        }
    }

    public static synchronized void b(String str, boolean z2) {
        a(str, z2, (String) null, (String) null, (String) null);
    }

    public static boolean b() {
        Integer num;
        if (c == null) {
            return false;
        }
        String strT = o.t(c);
        return (TextUtils.isEmpty(strT) || (num = q.get(strT.toUpperCase())) == null || num.intValue() != 2) ? false : true;
    }

    public static synchronized long c(String str) {
        try {
            if (t == null) {
                t = new ConcurrentHashMap<>(8);
            }
            if (t.containsKey(str)) {
                return t.get(str).longValue();
            }
        } catch (Throwable th) {
            ak.a(th, "at", "glcut");
        }
        return 0L;
    }

    private static void c(Context context) {
        if (context == null) {
            return;
        }
        f = av.a(context, "open_common", "a13", true);
        h = av.a(context, "open_common", "a6", true);
        g = av.a(context, "open_common", "a7", false);
        e = av.a(context, "open_common", "a8", Constant.DEFAULT_TIMEOUT);
        x = av.a(context, "open_common", "a9", 3);
        i = av.a(context, "open_common", "a10", false);
        y = av.a(context, "open_common", "a11", 3);
        j = av.a(context, "open_common", "a12", false);
    }

    public static void c(bj.c cVar) {
        if (cVar != null && j) {
            synchronized (D) {
                D.offer(cVar);
                bj.a();
            }
        }
    }

    public static boolean c() {
        Integer num;
        if (c == null) {
            return false;
        }
        String strT = o.t(c);
        return (TextUtils.isEmpty(strT) || (num = q.get(strT.toUpperCase())) == null || num.intValue() < 2) ? false : true;
    }

    public static void d() {
        try {
            e eVarA = a(c, "IPV6_CONFIG_NAME", "open_common");
            String strA = x.a(System.currentTimeMillis(), "yyyyMMdd");
            if (!strA.equals(eVarA.b)) {
                eVarA.a(strA);
                eVarA.c.set(0);
            }
            eVarA.c.incrementAndGet();
            a(c, "IPV6_CONFIG_NAME", "open_common", eVarA);
        } catch (Throwable unused) {
        }
    }

    private static void d(Context context) {
        try {
            if (w) {
                return;
            }
            ag.d = av.a(context, "open_common", "a4", true);
            ag.e = av.a(context, "open_common", "a5", true);
            w = true;
        } catch (Throwable unused) {
        }
    }

    public static boolean d(String str) {
        e eVarA;
        try {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            if (!f) {
                return false;
            }
            if (!(z.get(str) == null)) {
                return false;
            }
            if (c == null || (eVarA = a(c, b(str, "a14"), "open_common")) == null) {
                return true;
            }
            return eVarA.a() < x;
        } catch (Throwable unused) {
            return true;
        }
    }

    public static void e() {
        if (d) {
            return;
        }
        try {
            Context context = c;
            if (context == null) {
                return;
            }
            d = true;
            q.a().a(context);
            b(context);
            c(context);
            f.a = av.a(context, "open_common", "ucf", f.a);
            f.b = av.a(context, "open_common", "fsv2", f.b);
            f.c = av.a(context, "open_common", "usc", f.c);
            f.d = av.a(context, "open_common", "umv", f.d);
            f.e = av.a(context, "open_common", "ust", f.e);
            f.f = av.a(context, "open_common", "ustv", f.f);
        } catch (Throwable unused) {
        }
    }

    public static boolean e(String str) {
        e eVarA;
        try {
            if (TextUtils.isEmpty(str) || !i) {
                return false;
            }
            if (!(A.get(str) == null)) {
                return false;
            }
            if (c == null || (eVarA = a(c, b(str, "a15"), "open_common")) == null) {
                return true;
            }
            if (eVarA.a() < y) {
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static bj.a f() {
        if (C) {
            return null;
        }
        synchronized (B) {
            if (C) {
                return null;
            }
            Collections.sort(B);
            if (B.size() <= 0) {
                return null;
            }
            bj.a aVarClone = B.get(0).clone();
            C = true;
            return aVarClone;
        }
    }

    public static bj.c g() {
        synchronized (D) {
            bj.c cVarPoll = D.poll();
            if (cVarPoll != null) {
                return cVarPoll;
            }
            return null;
        }
    }

    private static void i() {
        Map<String, Integer> map;
        String str;
        Integer numValueOf;
        try {
            if (c != null) {
                String strT = o.t(c);
                if (!TextUtils.isEmpty(r) && !TextUtils.isEmpty(strT) && r.equals(strT) && System.currentTimeMillis() - s < 60000) {
                    return;
                }
                if (!TextUtils.isEmpty(strT)) {
                    r = strT;
                }
            } else if (System.currentTimeMillis() - s < com.igexin.push.config.c.i) {
                return;
            }
            s = System.currentTimeMillis();
            q.clear();
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                    String displayName = networkInterface.getDisplayName();
                    int i2 = 0;
                    Iterator<InterfaceAddress> it = networkInterface.getInterfaceAddresses().iterator();
                    while (it.hasNext()) {
                        InetAddress address = it.next().getAddress();
                        if (address instanceof Inet6Address) {
                            if (!a((Inet6Address) address)) {
                                i2 |= 2;
                            }
                        } else if (address instanceof Inet4Address) {
                            Inet4Address inet4Address = (Inet4Address) address;
                            if (!a(inet4Address) && !inet4Address.getHostAddress().startsWith(x.c("FMTkyLjE2OC40My4"))) {
                                i2 |= 1;
                            }
                        }
                    }
                    if (i2 != 0) {
                        if (displayName != null && displayName.startsWith("wlan")) {
                            map = q;
                            str = "WIFI";
                            numValueOf = Integer.valueOf(i2);
                        } else if (displayName != null && displayName.startsWith("rmnet")) {
                            map = q;
                            str = "MOBILE";
                            numValueOf = Integer.valueOf(i2);
                        }
                        map.put(str, numValueOf);
                    }
                }
            }
        } catch (Throwable th) {
            ak.a(th, "at", "ipstack");
        }
    }
}
