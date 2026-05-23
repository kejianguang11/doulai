package com.zx.a.I8b7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.zx.a.I8b7.a0;
import com.zx.a.I8b7.j1;
import com.zx.a.I8b7.j2;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s2;
import com.zx.a.I8b7.v2;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class q3 {
    public static String A = null;
    public static String B = null;
    public static String C = null;
    public static String D = null;
    public static String E = null;
    public static String F = null;
    public static String G = null;
    public static Context a = null;
    public static String b = "";
    public static String c = "";
    public static String d = "";
    public static String e = null;
    public static String f = "";
    public static String g = "";
    public static String h = null;
    public static String i = null;
    public static String j = null;
    public static String k = "{}";
    public static boolean r;
    public static SecretKey x;
    public static IvParameterSpec y;
    public static String z;
    public static volatile JSONArray l = new JSONArray();
    public static String m = "{}";
    public static String n = "";
    public static String o = "";
    public static int p = 0;
    public static String q = "ANDROID-V3";
    public static HashMap<String, String> s = new HashMap<>();
    public static int t = 1;
    public static int u = 1;
    public static int v = -1;
    public static long w = 0;
    public static JSONObject H = new JSONObject();
    public static volatile boolean I = false;
    public static final Set<String> J = Collections.newSetFromMap(new ConcurrentHashMap());
    public static final Set<String> K = Collections.newSetFromMap(new ConcurrentHashMap());
    public static Bundle L = null;

    public static String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("zid", i);
            jSONObject.put("ext", k);
        } catch (JSONException e2) {
            v2.a(e2);
        }
        return jSONObject.toString();
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String[] strArrSplit = str.split("-");
        return strArrSplit.length == 2 ? strArrSplit[0] : str;
    }

    public static void a(Context context) throws Exception {
        String string;
        Context applicationContext = context.getApplicationContext();
        a = applicationContext;
        g = applicationContext.getPackageName();
        z1.c(a);
        f = z1.a(a);
        StringBuilder sbA = j3.a("initAppId: ");
        sbA.append(f);
        v2.a(sbA.toString());
        b(a);
        if (TextUtils.isEmpty(h)) {
            b();
        } else {
            String strB = r.b(b4.b() + Build.MODEL, "SHA256");
            String[] strArrSplit = h.split("-");
            if (strArrSplit.length < 2) {
                StringBuilder sbA2 = j3.a("ZXID 检测到老版本LID:");
                sbA2.append(h);
                t.a(sbA2.toString());
                h += "-" + strB;
                p2 p2Var = p2.a.a;
                z3 z3Var = p2Var.a;
                String str = h;
                z3Var.getClass();
                if (!TextUtils.equals(str, h)) {
                    h = str;
                    p2Var.a.a(0, str, true);
                }
                StringBuilder sbA3 = j3.a("ZXID 兼容老版本LID后重新生成LID:");
                sbA3.append(h);
                string = sbA3.toString();
            } else if (TextUtils.equals(strB, strArrSplit[1])) {
                string = "ZXID LID校验通过!";
            } else {
                z3 z3Var2 = p2.a.a.a;
                if (z3Var2.b == null) {
                    z3Var2.b = z3Var2.d();
                }
                try {
                    SQLiteDatabase sQLiteDatabase = z3Var2.b;
                    StringBuilder sb = new StringBuilder();
                    sb.append("key in(");
                    sb.append(0 + com.igexin.push.core.b.an + 1 + com.igexin.push.core.b.an + 53 + com.igexin.push.core.b.an + 3 + com.igexin.push.core.b.an + 4 + com.igexin.push.core.b.an + 6 + com.igexin.push.core.b.an + 11 + com.igexin.push.core.b.an + 12 + com.igexin.push.core.b.an + 15 + com.igexin.push.core.b.an + 21 + com.igexin.push.core.b.an + 22 + com.igexin.push.core.b.an + 23 + com.igexin.push.core.b.an + 321 + com.igexin.push.core.b.an + 24 + com.igexin.push.core.b.an + 25 + com.igexin.push.core.b.an + 26 + com.igexin.push.core.b.an + 19 + com.igexin.push.core.b.an + 13 + com.igexin.push.core.b.an + 14);
                    sb.append(")");
                    sQLiteDatabase.delete("zx_table", sb.toString(), null);
                    h = "";
                    i = "";
                    k = "";
                    m = "{}";
                    p = 0;
                    q = "ANDROID-V3";
                    r = false;
                    z = "";
                    A = "";
                    B = "";
                    F = "";
                    t = 1;
                    C = "";
                    v2.a("ZXID清理数据成功");
                } catch (Exception e2) {
                    StringBuilder sbA4 = j3.a("清理本地数据error:");
                    sbA4.append(e2.getMessage());
                    v2.b(sbA4.toString());
                }
                b();
                string = "ZXID LID校验不通过";
            }
            t.a(string);
        }
        s2 s2Var = k0.a;
        try {
            s2.a aVar = new s2.a();
            aVar.b.add(new u0(v2.a.a.a, 5));
            aVar.b.add(new i0());
            SSLSocketFactory sSLSocketFactoryC = k0.c();
            if (sSLSocketFactoryC == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            aVar.c = sSLSocketFactoryC;
            k0.a = new s2(aVar);
        } catch (Throwable th) {
            v2.a(th);
            th.printStackTrace();
        }
    }

    public static void a(z3 z3Var) {
        if (y == null) {
            IvParameterSpec ivParameterSpecG = z3Var.g();
            y = ivParameterSpecG;
            if (ivParameterSpecG == null) {
                byte[] bArrGenerateSeed = new SecureRandom().generateSeed(16);
                String str = new String(Base64.encode(bArrGenerateSeed, 0), StandardCharsets.UTF_8);
                z3Var.a(10, str + "", false);
                v2.a("ZXID saveIvParameter ivStr:" + str);
                y = new IvParameterSpec(bArrGenerateSeed);
            }
        }
        if (x == null) {
            SecretKey secretKeyI = z3Var.i();
            x = secretKeyI;
            if (secretKeyI == null) {
                try {
                    SecureRandom secureRandom = r.a;
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(128);
                    SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
                    x = secretKeyGenerateKey;
                    z3Var.a(secretKeyGenerateKey.getEncoded());
                } catch (NoSuchAlgorithmException e2) {
                    v2.a(e2);
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void b() {
        String str = UUID.randomUUID().toString().replaceAll("-", "") + "-" + r.b(b4.b() + Build.MODEL, "SHA256");
        p2 p2Var = p2.a.a;
        p2Var.a.getClass();
        if (!TextUtils.equals(str, h)) {
            h = str;
            p2Var.a.a(0, str, true);
        }
        t.a("ZXID 生成LID:" + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x03a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void b(Context context) {
        String string;
        String str;
        a = context.getApplicationContext();
        z3 z3Var = p2.a.a.a;
        a(z3Var);
        z3Var.getClass();
        boolean z2 = false;
        Cursor cursorQuery = null;
        try {
            if (z3Var.b == null) {
                z3Var.b = z3Var.b();
            }
            try {
                cursorQuery = z3Var.b().query("zx_table", new String[]{"key", "value"}, null, null, null, null, null);
            } catch (Exception e2) {
                v2.b("query ex = " + e2.toString());
            }
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("key"));
                    try {
                        string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        if ((!TextUtils.isEmpty(string) && i2 == 11) || i2 == 12 || i2 == 0 || i2 == 1 || i2 == 53 || i2 == 16 || i2 == 63 || i2 == 30 || i2 == 28 || i2 == 64 || i2 == 15 || i2 == 21 || i2 == 22 || i2 == 23 || i2 == 321 || i2 == 18 || i2 == 13 || i2 == 19) {
                            string = new String(r.a("AES/CBC/PKCS5Padding", x, y, Base64.decode(string, 0)), StandardCharsets.UTF_8);
                        }
                    } catch (Throwable th) {
                        v2.b("ZXTable解密失败,Key:" + i2 + ",error:" + th.getMessage());
                    }
                    if (i2 == 0) {
                        h = string;
                        str = "read lid = " + h;
                    } else if (i2 == 1) {
                        i = string;
                        str = "read zid = " + i;
                    } else if (i2 == 3) {
                        p = Integer.parseInt(string);
                        str = "read syncId = " + p;
                    } else if (i2 == 4) {
                        q = string;
                        str = "read configVersion = " + q;
                    } else if (i2 == 28) {
                        n = string;
                        str = "read lastReportExtList = " + n;
                    } else if (i2 == 30) {
                        m = string;
                    } else if (i2 == 53) {
                        j = string;
                        str = "read LAST_REPORT_SDK_VERSION = " + j;
                    } else if (i2 == 321) {
                        v2.a("read err = " + string);
                        a0 a0Var = a0.b.a;
                        a0Var.getClass();
                        try {
                            if (!TextUtils.isEmpty(string)) {
                                a0Var.b = a0Var.a(new JSONArray(string), a0Var.b, 10);
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            v2.a(e);
                        }
                    } else if (i2 == 63) {
                        l = new JSONArray(string);
                        str = "read reqBZ = " + l;
                    } else if (i2 != 64) {
                        switch (i2) {
                            case 6:
                                r = Boolean.parseBoolean(string);
                                str = "read isInitialized = " + r;
                                break;
                            case 7:
                                v = Integer.parseInt(string);
                                str = "read permission = " + v;
                                break;
                            case 8:
                                w = Long.parseLong(string);
                                str = "read lastRequestTime = " + w;
                                break;
                            default:
                                switch (i2) {
                                    case 11:
                                        z = string;
                                        str = "read fieldConfigJSON = " + z;
                                        break;
                                    case 12:
                                        A = string;
                                        str = "read reportConfigJSON = " + A;
                                        break;
                                    case 13:
                                        F = string;
                                        str = "read localLv1JSON = " + F;
                                        break;
                                    case 14:
                                        t = Integer.parseInt(string);
                                        break;
                                    case 15:
                                        B = string;
                                        str = "read cryptoConfigJSON = " + B;
                                        break;
                                    case 16:
                                        k = string;
                                        break;
                                    default:
                                        switch (i2) {
                                            case 18:
                                                H = new JSONObject(string);
                                                break;
                                            case 19:
                                                E = string;
                                                v2.a("read invokeConfigJSON = " + E);
                                                c();
                                                break;
                                            case 20:
                                                u = Integer.parseInt(string);
                                                str = "read allowPermissionDialog = " + u;
                                                break;
                                            case 21:
                                                C = string;
                                                str = "read appConfigJSON = " + C;
                                                break;
                                            case 22:
                                                D = string;
                                                str = "read commonConfigJSON = " + D;
                                                break;
                                            case 23:
                                                v2.a("read events = " + string);
                                                a0 a0Var2 = a0.b.a;
                                                a0Var2.getClass();
                                                try {
                                                    if (!TextUtils.isEmpty(string)) {
                                                        a0Var2.a = a0Var2.a(new JSONArray(string), a0Var2.a, 100);
                                                    }
                                                } catch (JSONException e4) {
                                                    e = e4;
                                                    v2.a(e);
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                    } else {
                        o = string;
                        str = "read lastReportTB2MD5 = " + o;
                    }
                    v2.a(str);
                }
            }
        } catch (Throwable th2) {
            try {
                v2.a(th2);
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }
        if (cursorQuery != null) {
        }
        I = true;
        j1 j1Var = j1.b.a;
        j1Var.getClass();
        boolean z3 = new JSONObject(m).has("zxc2");
        if (!z3) {
            try {
                if (new JSONObject(m).has("zxc3")) {
                    z2 = true;
                }
            } catch (Throwable unused) {
            }
            if (z2) {
                try {
                    if (!j1Var.c.getAndSet(true)) {
                        v2.a("zx rt start");
                        long jA = j1Var.a();
                        j1Var.g = j1Var.a();
                        j1Var.a(jA);
                        j1Var.b();
                        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new i1(j1Var, jA), 1200L, 1000L, TimeUnit.MILLISECONDS);
                    }
                } catch (Throwable th3) {
                    v2.a(th3);
                }
            }
        }
        JSONArray jSONArray = j2.b;
        j2.b.a.b();
    }

    public static void c() {
        try {
            if (E != null) {
                JSONObject jSONObject = new JSONObject(E);
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("internal");
                JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("external");
                if (jSONArrayOptJSONArray != null) {
                    Set<String> set = J;
                    set.clear();
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                        set.add(jSONArrayOptJSONArray.getString(i2));
                    }
                }
                if (jSONArrayOptJSONArray2 != null) {
                    Set<String> set2 = K;
                    set2.clear();
                    for (int i3 = 0; i3 < jSONArrayOptJSONArray2.length(); i3++) {
                        set2.add(jSONArrayOptJSONArray2.getString(i3));
                    }
                }
            }
        } catch (JSONException e2) {
            v2.a(e2);
        }
    }
}
