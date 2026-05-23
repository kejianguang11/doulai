package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.loc.m;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fi {
    private static volatile boolean h = false;
    private static boolean i = true;
    private static int j = 1000;
    private static int k = 200;
    private static boolean l = false;
    private static int m = 20;
    private static int n = 0;
    private static volatile int o = 0;
    private static boolean p = true;
    private static boolean q = false;
    private static int r = -1;
    private static long s;
    private static ArrayList<String> t = new ArrayList<>();
    private static ArrayList<String> u = new ArrayList<>();
    private static volatile boolean v = false;
    private static boolean w = true;
    private static long x = 300000;
    private static boolean y = false;
    private static double z = 0.618d;
    private static boolean A = true;
    private static int B = 80;
    static long a = 3600000;
    private static boolean C = false;
    private static boolean D = true;
    private static boolean E = false;
    public static volatile long b = 0;
    static boolean c = true;
    private static boolean F = true;
    private static long G = -1;
    private static boolean H = true;
    private static int I = 1;
    private static boolean J = false;
    private static int K = 5;
    private static boolean L = false;
    private static String M = "CMjAzLjEwNy4xLjEvMTU0MDgxL2Q";
    private static long N = 0;
    public static boolean d = false;
    public static boolean e = false;
    public static int f = 20480;
    public static int g = 10800000;

    public static void a(final Context context) {
        if (h) {
            return;
        }
        h = true;
        m.a(context, fj.c(), fj.d(), new m.a() { // from class: com.loc.fi.1
            @Override // com.loc.m.a
            public final void a(m.b bVar) throws Throwable {
                fi.a(context, bVar);
            }
        });
    }

    private static void a(Context context, JSONObject jSONObject, SharedPreferences.Editor editor) {
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("13S");
            if (jSONObjectOptJSONObject != null) {
                try {
                    a = jSONObjectOptJSONObject.optInt("at", 123) * 60 * 1000;
                    fp.a(editor, "13S_at", a);
                } catch (Throwable th) {
                    fj.a(th, "AuthUtil", "requestSdkAuthInterval");
                }
                d(jSONObjectOptJSONObject, editor);
                try {
                    D = m.a(jSONObjectOptJSONObject.optString("nla"), true);
                    fp.a(editor, "13S_nla", D);
                } catch (Throwable unused) {
                }
                try {
                    F = m.a(jSONObjectOptJSONObject.optString("asw"), true);
                    fp.a(editor, "asw", F);
                } catch (Throwable unused2) {
                }
                try {
                    JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("mlpl");
                    if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0 || context == null) {
                        E = false;
                        fp.a(editor, "13S_mlpl");
                    } else {
                        fp.a(editor, "13S_mlpl", x.b(jSONArrayOptJSONArray.toString()));
                        E = a(context, jSONArrayOptJSONArray);
                    }
                } catch (Throwable unused3) {
                }
            }
        } catch (Throwable th2) {
            fj.a(th2, "AuthUtil", "loadConfigAbleStatus");
        }
    }

    private static void a(m.b bVar, SharedPreferences.Editor editor) {
        try {
            m.b.a aVar = bVar.g;
            if (aVar != null) {
                i = aVar.a;
                fp.a(editor, "exception", i);
                JSONObject jSONObject = aVar.c;
                if (jSONObject != null) {
                    j = jSONObject.optInt("fn", j);
                    int iOptInt = jSONObject.optInt("mpn", k);
                    k = iOptInt;
                    if (iOptInt > 500) {
                        k = 500;
                    }
                    if (k < 30) {
                        k = 30;
                    }
                    l = m.a(jSONObject.optString("igu"), false);
                    m = jSONObject.optInt("ms", m);
                    o = jSONObject.optInt("rot", 0);
                    n = jSONObject.optInt("pms", 0);
                }
                bq.a(j, l, m, n);
                bs.a(l, n);
                fp.a(editor, "fn", j);
                fp.a(editor, "mpn", k);
                fp.a(editor, "igu", l);
                fp.a(editor, "ms", m);
                fp.a(editor, "rot", o);
                fp.a(editor, "pms", n);
            }
        } catch (Throwable th) {
            fj.a(th, "AuthUtil", "loadConfigDataUploadException");
        }
    }

    private static void a(JSONObject jSONObject, SharedPreferences.Editor editor) {
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("11G");
            if (jSONObjectOptJSONObject != null) {
                boolean zA = m.a(jSONObjectOptJSONObject.optString("able"), true);
                w = zA;
                if (zA) {
                    x = jSONObjectOptJSONObject.optInt(com.igexin.push.core.d.d.d, 300) * 1000;
                }
                y = m.a(jSONObjectOptJSONObject.optString("fa"), false);
                z = Math.min(1.0d, Math.max(0.2d, jSONObjectOptJSONObject.optDouble("ms", 0.618d)));
                fp.a(editor, com.igexin.push.core.b.ab, w);
                fp.a(editor, "ct", x);
                fp.a(editor, "11G_fa", y);
                fp.a(editor, "11G_ms", String.valueOf(z));
            }
        } catch (Throwable th) {
            fj.a(th, "AuthUtil", "loadConfigDataCacheAble");
        }
    }

    public static boolean a() {
        return i;
    }

    public static boolean a(long j2) {
        if (w) {
            return x < 0 || fq.a() - j2 < x;
        }
        return false;
    }

    static boolean a(Context context, m.b bVar) throws Throwable {
        SharedPreferences.Editor editorA;
        SharedPreferences.Editor editor = null;
        try {
            editorA = fp.a(context, "pref");
            try {
                a(bVar, editorA);
                c(context);
                JSONObject jSONObject = bVar.f;
                if (jSONObject == null) {
                    if (editorA != null) {
                        try {
                            fp.a(editorA);
                        } catch (Throwable unused) {
                        }
                    }
                    return true;
                }
                a(context, jSONObject, editorA);
                a(jSONObject, editorA);
                c(jSONObject, editorA);
                e(jSONObject, editorA);
                g(jSONObject, editorA);
                f(jSONObject, editorA);
                h(jSONObject, editorA);
                b(jSONObject, editorA);
                if (editorA != null) {
                    try {
                        fp.a(editorA);
                    } catch (Throwable unused2) {
                    }
                }
                return true;
            } catch (Throwable unused3) {
                editor = editorA;
                if (editor == null) {
                    return false;
                }
                try {
                    fp.a(editor);
                    return false;
                } catch (Throwable unused4) {
                    return false;
                }
            }
        } catch (Throwable th) {
            th = th;
            editorA = null;
        }
    }

    private static boolean a(Context context, JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() > 0 && context != null) {
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        if (fq.b(context, jSONArray.getString(i2))) {
                            return true;
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return false;
    }

    public static int b() {
        return k;
    }

    public static void b(Context context) {
        if (v) {
            return;
        }
        v = true;
        try {
            i = fp.a(context, "pref", "exception", i);
            c(context);
        } catch (Throwable th) {
            fj.a(th, "AuthUtil", "loadLastAbleState p1");
        }
        try {
            j = fp.a(context, "pref", "fn", j);
            k = fp.a(context, "pref", "mpn", k);
            l = fp.a(context, "pref", "igu", l);
            m = fp.a(context, "pref", "ms", m);
            o = fp.a(context, "pref", "rot", 0);
            n = fp.a(context, "pref", "pms", 0);
            bq.a(j, l, m, n);
            bs.a(l, n);
        } catch (Throwable th2) {
            fj.a(th2, "AuthUtil", "loadLastAbleState p2");
        }
        try {
            w = fp.a(context, "pref", com.igexin.push.core.b.ab, w);
            x = fp.a(context, "pref", "ct", x);
            y = fp.a(context, "pref", "11G_fa", y);
            z = Double.valueOf(fp.a(context, "pref", "11G_ms", String.valueOf(z))).doubleValue();
            z = Math.max(0.2d, z);
        } catch (Throwable th3) {
            fj.a(th3, "AuthUtil", "loadLastAbleState p3");
        }
        try {
            c = fp.a(context, "pref", "fr", c);
        } catch (Throwable th4) {
            fj.a(th4, "AuthUtil", "loadLastAbleState p4");
        }
        try {
            F = fp.a(context, "pref", "asw", F);
        } catch (Throwable th5) {
            fj.a(th5, "AuthUtil", "loadLastAbleState p5");
        }
        try {
            G = fp.a(context, "pref", "awsi", G);
        } catch (Throwable th6) {
            fj.a(th6, "AuthUtil", "loadLastAbleState p6");
        }
        try {
            H = fp.a(context, "pref", "15ua", H);
            I = fp.a(context, "pref", "15un", I);
            N = fp.a(context, "pref", "15ust", N);
        } catch (Throwable th7) {
            fj.a(th7, "AuthUtil", "loadLastAbleState p7");
        }
        try {
            J = fp.a(context, "pref", "ok9", J);
            K = fp.a(context, "pref", "ok10", K);
            M = fp.a(context, "pref", "ok11", M);
        } catch (Throwable th8) {
            fj.a(th8, "AuthUtil", "loadLastAbleState p8");
        }
        try {
            d = fp.a(context, "pref", "17ya", false);
            e = fp.a(context, "pref", "17ym", false);
            g = fp.a(context, "pref", "17yi", 2) * 60 * 60 * 1000;
            f = fp.a(context, "pref", "17yx", 100) * 1024;
        } catch (Throwable th9) {
            fj.a(th9, "AuthUtil", "loadLastAbleState p9");
        }
        try {
            b = fq.b();
            a = fp.a(context, "pref", "13S_at", a);
            D = fp.a(context, "pref", "13S_nla", D);
            A = fp.a(context, "pref", "13J_able", A);
            B = fp.a(context, "pref", "13J_c", B);
        } catch (Throwable th10) {
            fj.a(th10, "AuthUtil", "loadLastAbleState p10");
        }
        m.b(context);
        try {
            String strA = fp.a(context, "pref", "13S_mlpl", (String) null);
            if (!TextUtils.isEmpty(strA)) {
                E = a(context, new JSONArray(x.c(strA)));
            }
        } catch (Throwable th11) {
            fj.a(th11, "AuthUtil", "loadLastAbleState p11");
        }
        try {
            boolean zA = fp.a(context, "pref", "197a", false);
            String strA2 = fp.a(context, "pref", "197dv", "");
            String strA3 = fp.a(context, "pref", "197tv", "");
            if (zA && fj.a.equals(strA2)) {
                for (String str : fj.b) {
                    if (str.equals(strA3)) {
                        fj.a = strA3;
                    }
                }
            }
        } catch (Throwable th12) {
            fj.a(th12, "AuthUtil", "loadLastAbleState p12");
        }
    }

    private static void b(JSONObject jSONObject, SharedPreferences.Editor editor) {
        if (jSONObject == null) {
            return;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("197");
            if (jSONObject2 != null) {
                boolean zA = m.a(jSONObject2.optString("able"), false);
                fp.a(editor, "197a", zA);
                if (zA) {
                    fp.a(editor, "197dv", jSONObject2.optString(com.alipay.sdk.sys.a.h, ""));
                    fp.a(editor, "197tv", jSONObject2.optString("tv", ""));
                } else {
                    fp.a(editor, "197dv", "");
                    fp.a(editor, "197tv", "");
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static int c() {
        if (o < 0) {
            o = 0;
        }
        return o;
    }

    public static void c(Context context) {
        try {
            w wVarC = fj.c();
            wVarC.a(i);
            an.a(context, wVarC);
        } catch (Throwable unused) {
        }
    }

    private static void c(JSONObject jSONObject, SharedPreferences.Editor editor) {
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("13J");
            if (jSONObjectOptJSONObject != null) {
                boolean zA = m.a(jSONObjectOptJSONObject.optString("able"), true);
                A = zA;
                if (zA) {
                    B = jSONObjectOptJSONObject.optInt(com.igexin.push.core.d.d.d, B);
                }
                fp.a(editor, "13J_able", A);
                fp.a(editor, "13J_c", B);
            }
        } catch (Throwable th) {
            fj.a(th, "AuthUtil", "loadConfigDataGpsGeoAble");
        }
    }

    public static long d() {
        return x;
    }

    private static void d(JSONObject jSONObject, SharedPreferences.Editor editor) {
        if (jSONObject == null) {
            return;
        }
        try {
            c = m.a(jSONObject.optString("re"), true);
            fp.a(editor, "fr", c);
        } catch (Throwable th) {
            fj.a(th, "AuthUtil", "checkReLocationAble");
        }
    }

    private static void e(JSONObject jSONObject, SharedPreferences.Editor editor) {
        JSONArray jSONArrayOptJSONArray;
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("15O");
            if (jSONObjectOptJSONObject != null) {
                if (m.a(jSONObjectOptJSONObject.optString("able"), false) && ((jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("fl")) == null || jSONArrayOptJSONArray.length() <= 0 || jSONArrayOptJSONArray.toString().contains(Build.MANUFACTURER))) {
                    G = jSONObjectOptJSONObject.optInt("iv", 30) * 1000;
                } else {
                    G = -1L;
                }
                fp.a(editor, "awsi", G);
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean e() {
        return w;
    }

    private static void f(JSONObject jSONObject, SharedPreferences.Editor editor) {
        if (jSONObject == null) {
            return;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("17Y");
            if (jSONObject2 != null) {
                d = m.a(jSONObject2.optString("able"), false);
                fp.a(editor, "17ya", d);
                e = m.a(jSONObject2.optString("mup"), false);
                fp.a(editor, "17ym", e);
                int iOptInt = jSONObject2.optInt("max", 20);
                if (iOptInt > 0) {
                    fp.a(editor, "17yx", iOptInt);
                    f = iOptInt * 1024;
                }
                int iOptInt2 = jSONObject2.optInt("inv", 3);
                if (iOptInt2 > 0) {
                    fp.a(editor, "17yi", iOptInt2);
                    g = iOptInt2 * 60 * 60 * 1000;
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean f() {
        return y;
    }

    public static double g() {
        return z;
    }

    private static void g(JSONObject jSONObject, SharedPreferences.Editor editor) {
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("15U");
            if (jSONObjectOptJSONObject != null) {
                boolean zA = m.a(jSONObjectOptJSONObject.optString("able"), true);
                int iOptInt = jSONObjectOptJSONObject.optInt("yn", I);
                N = jSONObjectOptJSONObject.optLong("sysTime", N);
                fp.a(editor, "15ua", zA);
                fp.a(editor, "15un", iOptInt);
                fp.a(editor, "15ust", N);
            }
        } catch (Throwable unused) {
        }
    }

    private static void h(JSONObject jSONObject, SharedPreferences.Editor editor) {
        int i2;
        if (jSONObject == null) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("17J");
            if (jSONObjectOptJSONObject != null) {
                boolean zA = m.a(jSONObjectOptJSONObject.optString("able"), false);
                J = zA;
                fp.a(editor, "ok9", zA);
                if (zA) {
                    String strOptString = jSONObjectOptJSONObject.optString(com.alipay.sdk.app.statistic.c.d);
                    M = jSONObjectOptJSONObject.optString("ht");
                    fp.a(editor, "ok11", M);
                    m.a(strOptString, false);
                    L = m.a(jSONObjectOptJSONObject.optString("nr"), false);
                    String strOptString2 = jSONObjectOptJSONObject.optString("tm");
                    if (TextUtils.isEmpty(strOptString2) || (i2 = Integer.parseInt(strOptString2)) <= 0 || i2 >= 20) {
                        return;
                    }
                    K = i2;
                    fp.a(editor, "ok10", K);
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean h() {
        return A;
    }

    public static int i() {
        return B;
    }

    public static boolean j() {
        return D;
    }

    public static boolean k() {
        return E;
    }

    public static boolean l() {
        return c;
    }

    public static boolean m() {
        return F;
    }

    public static long n() {
        return G;
    }

    public static boolean o() {
        return L;
    }

    public static boolean p() {
        return J;
    }

    public static String q() {
        return x.c(M);
    }

    public static boolean r() {
        return H && I > 0;
    }

    public static int s() {
        return I;
    }

    public static long t() {
        return N;
    }
}
