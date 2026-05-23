package com.zx.a.I8b7;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.gme.liteav.TXLiteAVCode;
import com.zx.a.I8b7.a0;
import com.zx.a.I8b7.a4;
import com.zx.a.I8b7.j2;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import javax.crypto.SecretKey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class r1 {
    public static String a = "";
    public static String b = "";
    public static SecretKey d;
    public static byte[] e;
    public static LinkedList<String> c = new LinkedList<>();
    public static final SecureRandom f = new SecureRandom();

    public static String a(String str, String str2, JSONObject jSONObject) {
        String strSubstring;
        String str3;
        if (jSONObject == null || !jSONObject.has(str) || TextUtils.isEmpty(str2)) {
            return str2;
        }
        try {
            int i = jSONObject.getInt(str);
            if (i == 1) {
                return new String(Base64.encode(r.a(str2, d, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8);
            }
            if (i == 2) {
                strSubstring = r.b(str2, "MD5").substring(0, 20);
                str3 = "MD5";
            } else {
                if (i != 3) {
                    return str2;
                }
                String strB = r.b(str2, "MD5");
                StringBuilder sb = new StringBuilder();
                sb.append(strB.substring(0, 20));
                PackageManager packageManager = b4.a;
                sb.append(Build.MODEL);
                strSubstring = sb.toString();
                str3 = "MD5";
            }
            return r.b(strSubstring, str3);
        } catch (Exception e2) {
            v2.b("加密脱敏失败:" + str + ",error:" + e2);
            return null;
        }
    }

    public static String a(String str, JSONObject jSONObject) {
        return (jSONObject == null || jSONObject.length() == 0 || jSONObject.isNull(str)) ? "" : jSONObject.optString(str);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static HashMap<String, String> a() throws JSONException {
        JSONObject jSONObject = !TextUtils.isEmpty(q3.z) ? new JSONObject(q3.z) : new JSONObject();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("lv1");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("lv2");
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
            return new HashMap<>();
        }
        if (jSONObjectOptJSONObject == null) {
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                String string = jSONArrayOptJSONArray.getString(i);
                map.put(string, "99".equals(string) ? w0.a() : b4.a(string));
            }
            return map;
        }
        HashMap<String, String> map2 = new HashMap<>();
        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
            String string2 = jSONArrayOptJSONArray.getString(i2);
            String strA = "99".equals(string2) ? w0.a() : b4.a(string2);
            if (!TextUtils.isEmpty(strA) && jSONObjectOptJSONObject.has(string2)) {
                string2.getClass();
                byte b2 = -1;
                int iHashCode = string2.hashCode();
                if (iHashCode != 1823) {
                    switch (iHashCode) {
                        case 1668:
                            if (string2.equals("48")) {
                                b2 = 0;
                            }
                            break;
                        case 1669:
                            if (string2.equals("49")) {
                                b2 = 1;
                            }
                            break;
                    }
                } else if (string2.equals("98")) {
                    b2 = 2;
                }
                switch (b2) {
                    case 0:
                    case 1:
                    case 2:
                        String[] strArrSplit = strA.split("#");
                        HashMap map3 = new HashMap();
                        for (String str : strArrSplit) {
                            String str2 = str.split(com.igexin.push.core.b.an)[0];
                            map3.put(str2, str.substring(str2.length() + 1));
                        }
                        JSONObject jSONObject2 = jSONObjectOptJSONObject.getJSONObject(string2);
                        Iterator<String> itKeys = jSONObject2.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            try {
                                String str3 = string2 + "." + next;
                                String str4 = (String) map3.get(r.a(jSONObject2.getString(next), true));
                                map2.put(str3, str4);
                                if (!TextUtils.isEmpty(str4)) {
                                    c.add(str3);
                                }
                            } catch (Throwable th) {
                                v2.a(th);
                            }
                        }
                        break;
                }
            }
            map2.put(string2, strA);
        }
        return map2;
    }

    public static JSONObject a(JSONArray jSONArray, HashMap<String, String> map, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getString(i);
                String strA = a(string, map.get(string), jSONObject);
                if (strA == null) {
                    strA = "";
                }
                jSONObject2.put(string, strA);
            } catch (JSONException e2) {
                StringBuilder sbA = j3.a("ZXID handleType1 error:");
                sbA.append(e2.getMessage());
                v2.b(sbA.toString());
            }
        }
        return jSONObject2;
    }

    @Java2C.Method2C
    private static native void a(JSONObject jSONObject) throws Throwable;

    public static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            return !TextUtils.isEmpty(q3.F) ? new JSONObject(q3.F) : jSONObject;
        } catch (JSONException e2) {
            StringBuilder sbA = j3.a("ZXID buildOldLv1 error:");
            sbA.append(e2.getMessage());
            v2.b(sbA.toString());
            return jSONObject;
        }
    }

    public static JSONObject b(JSONArray jSONArray, HashMap<String, String> map, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObjectB = b();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getString(i);
                String str = map.get(string);
                if (str == null) {
                    str = "";
                }
                if (!TextUtils.equals(a(string, jSONObjectB), str)) {
                    String strA = a(string, str, jSONObject);
                    if (strA == null) {
                        strA = "";
                    }
                    jSONObject2.put(string, strA);
                }
            } catch (JSONException e2) {
                StringBuilder sbA = j3.a("ZXID handleType2 error:");
                sbA.append(e2.getMessage());
                v2.b(sbA.toString());
            }
        }
        return jSONObject2;
    }

    @Java2C.Method2C
    private static native void b(JSONObject jSONObject) throws Throwable;

    @Java2C.Method2C
    public static native synchronized String c();

    public static JSONObject c(JSONArray jSONArray, HashMap<String, String> map, JSONObject jSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        JSONObject jSONObject2 = new JSONObject();
        StringBuffer stringBuffer2 = new StringBuffer();
        JSONObject jSONObjectB = b();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getString(i);
                String str = map.get(string);
                if (str == null) {
                    str = "";
                }
                String strA = a(string, str, jSONObject);
                if (strA == null) {
                    strA = "";
                }
                jSONObject2.put(string, strA);
                stringBuffer.append(str);
                stringBuffer.append("|");
                stringBuffer2.append(a(string, jSONObjectB));
                stringBuffer2.append("|");
            } catch (JSONException e2) {
                StringBuilder sbA = j3.a("ZXID handleType3 error:");
                sbA.append(e2.getMessage());
                v2.b(sbA.toString());
            }
        }
        for (String str2 : c) {
            try {
                String str3 = map.get(str2);
                if (str3 == null) {
                    str3 = "";
                }
                String strA2 = a(str2, str3, jSONObject);
                if (strA2 == null) {
                    strA2 = "";
                }
                jSONObject2.put(str2, strA2);
                stringBuffer.append(str3);
                stringBuffer.append("|");
                stringBuffer2.append(a(str2, jSONObjectB));
                stringBuffer2.append("|");
            } catch (JSONException e3) {
                StringBuilder sbA2 = j3.a("ZXID handleType3 childIndex error:");
                sbA2.append(e3.getMessage());
                v2.b(sbA2.toString());
            }
        }
        return !TextUtils.equals(r.b(stringBuffer.toString(), "SHA256"), r.b(stringBuffer2.toString(), "SHA256")) ? jSONObject2 : new JSONObject();
    }

    public static JSONObject d() {
        JSONObject jSONObjectA;
        JSONObject jSONObject = new JSONObject();
        try {
            c.clear();
            HashMap<String, String> mapA = a();
            q3.G = new JSONObject(mapA).toString();
            JSONObject jSONObject2 = !TextUtils.isEmpty(q3.A) ? new JSONObject(q3.A) : new JSONObject();
            JSONObject jSONObject3 = !TextUtils.isEmpty(q3.B) ? new JSONObject(q3.B) : new JSONObject();
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject4 = jSONObject2.getJSONObject(next);
                int i = jSONObject4.getInt("type");
                JSONArray jSONArray = jSONObject4.getJSONArray("list");
                if (i == 1) {
                    jSONObjectA = a(jSONArray, mapA, jSONObject3);
                } else if (i == 2) {
                    jSONObjectA = b(jSONArray, mapA, jSONObject3);
                } else if (i == 3) {
                    jSONObjectA = c(jSONArray, mapA, jSONObject3);
                }
                jSONObject.put(next, jSONObjectA);
            }
        } catch (Throwable th) {
            StringBuilder sbA = j3.a("ZXID 获取data参数异常:");
            sbA.append(th.getMessage());
            v2.b(sbA.toString());
        }
        return jSONObject;
    }

    @Java2C.Method2C
    private static native String e();

    @Java2C.Method2C
    private static native String f();

    /* JADX WARN: Removed duplicated region for block: B:28:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void g() throws Exception {
        JSONObject jSONObject;
        ThreadPoolExecutor threadPoolExecutor;
        Runnable n1Var;
        String strA;
        JSONArray jSONArrayOptJSONArray;
        if (e0.a()) {
            return;
        }
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(c());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/channel/report");
        aVarA.b = "POST";
        z0 z0VarB = z0.b("application/json; charset=utf-8");
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("syncId", q3.p);
        jSONObject3.put("lid", q3.a(q3.h));
        jSONObject3.put("zid", q3.i);
        jSONObject2.put("ctx", jSONObject3);
        jSONObject2.put("sdkInfo", k0.d());
        jSONObject2.put("deviceInfo", k0.b());
        a0 a0Var = a0.b.a;
        a0Var.getClass();
        JSONObject jSONObject4 = new JSONObject();
        try {
            jSONObject4.put("list", a0Var.a);
        } catch (Exception e2) {
            v2.a(e2);
        }
        jSONObject2.put("events", jSONObject4);
        a0 a0Var2 = a0.b.a;
        a0Var2.getClass();
        JSONObject jSONObject5 = new JSONObject();
        try {
            jSONObject5.put("list", a0Var2.b);
        } catch (Exception e3) {
            v2.a(e3);
        }
        if (jSONObject5.length() > 0 && (jSONArrayOptJSONArray = jSONObject5.optJSONArray("list")) != null && jSONArrayOptJSONArray.length() > 0) {
            jSONObject2.put(NotificationCompat.CATEGORY_ERROR, jSONObject5);
        }
        try {
            strA = p2.a.a.a.a(322);
        } catch (Throwable unused) {
        }
        if (!TextUtils.isEmpty(strA)) {
            jSONObject = new JSONObject(strA);
            if (jSONObject.length() <= 0) {
                jSONObject = null;
            }
        }
        jSONObject2.put(com.alipay.sdk.app.statistic.c.d, jSONObject);
        JSONObject jSONObject6 = new JSONObject();
        jSONObject6.put("ood", b4.a("62"));
        if ("HONOR".equals(Build.MANUFACTURER.toUpperCase())) {
            String strA2 = c1.a().a(q3.a);
            if (strA2 == null) {
                strA2 = "";
            }
            jSONObject6.put("ood_hw", strA2);
        }
        b(jSONObject6);
        if (q3.l != null && q3.l.length() > 0) {
            jSONObject6.put("reqBZ", q3.l);
        }
        a(jSONObject6);
        try {
            JSONArray jSONArray = j2.b;
            if (j2.b.a.a()) {
                String strA3 = p2.a.a.a.a(TXLiteAVCode.WARNING_IGNORE_UPSTREAM_FOR_AUDIENCE);
                if (!TextUtils.isEmpty(strA3) && !"[]".equals(strA3)) {
                    jSONObject6.put("vb", new JSONArray(strA3));
                }
            }
        } catch (Throwable th) {
            v2.a(th);
        }
        jSONObject2.put("extensionInfo", jSONObject6);
        JSONObject jSONObject7 = new JSONObject();
        try {
            jSONObject7.put(com.alipay.sdk.packet.d.k, d());
            jSONObject7.put("unauthorizedFields", new JSONArray());
        } catch (Throwable th2) {
            StringBuilder sbA = j3.a("ZXID getReportData error:");
            sbA.append(th2.getMessage());
            v2.b(sbA.toString());
        }
        jSONObject2.put("reportData", jSONObject7);
        aVarA.d = u1.a(z0VarB, new String(Base64.encode(r.a(jSONObject2.toString(), d, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8));
        aVarA.e = "request zxid api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        v1 v1VarA = new k1(s2Var, s1Var).a();
        if (v1VarA.b != 200) {
            throw new RuntimeException("response errCode: " + v1VarA.a("Udid-Error-Code") + ", errMsg: " + v1VarA.a("Udid-Error-Message"));
        }
        p2 p2Var = p2.a.a;
        z3 z3Var = p2Var.a;
        long jCurrentTimeMillis = System.currentTimeMillis();
        z3Var.getClass();
        if (jCurrentTimeMillis != q3.w) {
            q3.w = jCurrentTimeMillis;
            p2Var.a.a(8, q3.w + "", false);
            v2.a("lastRequestTime had changed refresh:" + q3.w);
        }
        z3 z3Var2 = p2Var.a;
        String str = q3.b;
        z3Var2.getClass();
        if (!TextUtils.equals(str, q3.j)) {
            q3.j = str;
            p2Var.a.a(53, str, true);
            v2.a("lSdkVersion had changed refresh:" + q3.j);
        }
        if (!TextUtils.isEmpty(a)) {
            z3 z3Var3 = p2Var.a;
            String str2 = a;
            z3Var3.getClass();
            if (!TextUtils.equals(str2, q3.n)) {
                q3.n = str2;
                p2Var.a.a(28, str2, true);
                v2.a("lastReportExtListMD5 had changed refresh:" + q3.n);
            }
            a = "";
        }
        if (!TextUtils.isEmpty(b)) {
            z3 z3Var4 = p2Var.a;
            String str3 = b;
            z3Var4.getClass();
            if (!TextUtils.equals(str3, q3.o)) {
                q3.o = str3;
                p2Var.a.a(64, str3, true);
                v2.a("lastReportTB2MD5 had changed refresh:" + q3.o);
            }
            StringBuilder sbA2 = j3.a("tb2 rp suc!, lastmd5:");
            sbA2.append(b);
            v2.a(sbA2.toString());
            b = "";
        }
        a0 a0Var3 = a0.b.a;
        a0Var3.getClass();
        a0Var3.a(new c0(a0Var3));
        a0Var3.a(new d0(a0Var3));
        p2Var.a.getClass();
        p2Var.a.a(322, "", true);
        p2Var.a.getClass();
        q3.l = new JSONArray();
        p2Var.a.a(63, q3.l.toString(), true);
        a4.f.a.e.execute(new m1());
        JSONArray jSONArray2 = j2.b;
        j2 j2Var = j2.b.a;
        JSONArray jSONArray3 = new JSONArray();
        j2.b = jSONArray3;
        p2Var.a.b(jSONArray3.toString());
        JSONObject jSONObject8 = new JSONObject(v1VarA.e.b());
        p2Var.a.d(jSONObject8.getInt("syncId"));
        JSONObject jSONObject9 = new JSONObject(r.a(Base64.decode(jSONObject8.getString(com.alipay.sdk.packet.d.k), 2), d, "UDID_ENC_AUTHTAG"));
        String string = jSONObject9.getString("zid");
        p2Var.a.getClass();
        if (!TextUtils.equals(string, q3.i)) {
            q3.i = string;
            p2Var.a.a(1, string, true);
            v2.a("zid had changed refresh:" + string);
        }
        JSONObject jSONObjectOptJSONObject = jSONObject9.optJSONObject("aids");
        JSONObject jSONObjectOptJSONObject2 = jSONObject9.optJSONObject("aidsExt");
        if (jSONObjectOptJSONObject2 == null) {
            jSONObjectOptJSONObject2 = new JSONObject();
        }
        if (!TextUtils.isEmpty(q3.f) && jSONObjectOptJSONObject != null) {
            jSONObjectOptJSONObject2.put(q3.f, jSONObjectOptJSONObject);
        }
        JSONObject jSONObject10 = new JSONObject();
        jSONObject10.put("tags", jSONObject9.optJSONArray("tags"));
        jSONObject10.put("aids", jSONObjectOptJSONObject2);
        String strOptString = jSONObject9.optString("taid");
        JSONObject jSONObjectOptJSONObject3 = jSONObject9.optJSONObject("taidExt");
        if (jSONObjectOptJSONObject3 == null) {
            jSONObjectOptJSONObject3 = new JSONObject();
        }
        if (!TextUtils.isEmpty(q3.f) && !TextUtils.isEmpty(strOptString)) {
            jSONObjectOptJSONObject3.put(q3.f, strOptString);
        }
        jSONObject10.put("taids", jSONObjectOptJSONObject3);
        if (jSONObject9.has("openid")) {
            jSONObject10.put("openid", jSONObject9.optString("openid"));
        } else {
            jSONObject10.put("openid", "OPENID_CLOSED");
        }
        jSONObject10.put("ot", jSONObject9.optInt("ot"));
        JSONObject jSONObject11 = new JSONObject();
        if (jSONObject9.has("zxc1")) {
            jSONObject11.put("zxc1", jSONObject9.getString("zxc1"));
        }
        if (jSONObject9.has("zxc2")) {
            jSONObject11.put("zxc2", true);
        }
        if (jSONObject9.has("zxc3")) {
            jSONObject11.put("zxc3", true);
        }
        if (jSONObject9.has("zxc4")) {
            jSONObject11.put("zxc4", true);
        }
        if (jSONObject9.has("zxc5")) {
            jSONObject11.put("zxc5", true);
        }
        if (jSONObject9.has("zxc7")) {
            jSONObject11.put("zxc7", jSONObject9.getString("zxc7"));
            d2.b();
        }
        p2Var.a.a(jSONObject11);
        p2Var.a.getClass();
        String string2 = jSONObject10.toString();
        if (!TextUtils.isEmpty(string2)) {
            q3.k = string2;
            p2Var.a.a(16, string2, true);
            v2.a("ext had changed refresh:" + jSONObject10);
        }
        JSONArray jSONArrayOptJSONArray2 = jSONObject8.optJSONArray("cmds");
        z3 z3Var5 = p2Var.a;
        String str4 = q3.G;
        z3Var5.getClass();
        if (!TextUtils.isEmpty(str4) && !TextUtils.equals(str4, q3.F)) {
            q3.F = str4;
            p2Var.a.a(13, str4, true);
        }
        JSONArray jSONArrayOptJSONArray3 = jSONObject9.optJSONArray("iaps");
        try {
            JSONArray jSONArray4 = new JSONArray();
            if (jSONArrayOptJSONArray3 != null && jSONArrayOptJSONArray3.length() > 0) {
                for (int i = 0; i < jSONArrayOptJSONArray3.length(); i++) {
                    jSONArray4.put(r.a(Base64.decode(jSONArrayOptJSONArray3.getString(i), 2), d, "UDID_ENC_AUTHTAG"));
                }
            }
            p2 p2Var2 = p2.a.a;
            p2Var2.a.getClass();
            p2Var2.a.a(25, jSONArray4.toString(), true);
        } catch (Throwable unused2) {
        }
        if (jSONArrayOptJSONArray2 != null) {
            try {
                if (jSONArrayOptJSONArray2.length() != 0) {
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                        switch (jSONArrayOptJSONArray2.getInt(i2)) {
                            case 1:
                                v2.a("cmd 1 REQUEST_CONFIG ");
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new n1();
                                break;
                            case 2:
                                p2.a.a.a.d(0);
                                continue;
                                break;
                            case 3:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new o1();
                                break;
                            case 4:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new p1();
                                break;
                            case 5:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new q1();
                                break;
                            case 6:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new g();
                                break;
                            case 7:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new h();
                                break;
                            case 8:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new i();
                                break;
                            case 9:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new j();
                                break;
                            case 10:
                                threadPoolExecutor = a4.f.a.d;
                                n1Var = new f();
                                break;
                            default:
                                continue;
                                break;
                        }
                        threadPoolExecutor.execute(n1Var);
                    }
                }
            } catch (Throwable th3) {
                v2.a(th3);
            }
        }
        j2.b.a.b();
    }
}
