package com.zx.a.I8b7;

import android.text.TextUtils;
import android.util.Base64;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class m {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    @Java2C.Method2C
    private static native String b() throws Exception;

    public static void c() throws Exception {
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(a());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/config/init");
        aVarA.b = "POST";
        aVarA.d = u1.a(z0.b("application/json; charset=utf-8"), b());
        aVarA.e = "request config api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        v1 v1VarA = new k1(s2Var, s1Var).a();
        if (v1VarA.b != 200) {
            throw new RuntimeException("response errCode: " + v1VarA.a("Udid-Error-Code") + ", errMsg: " + v1VarA.a("Udid-Error-Message"));
        }
        JSONObject jSONObject = new JSONObject(r.a(Base64.decode(new JSONObject(v1VarA.e.b()).getString(com.alipay.sdk.packet.d.k), 2), a, "UDID_ENC_AUTHTAG"));
        String string = jSONObject.getString("configVersion");
        p2 p2Var = p2.a.a;
        p2Var.a.getClass();
        if (!TextUtils.equals(string, q3.q)) {
            q3.q = string;
            p2Var.a.a(4, string, false);
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("fieldConfig");
        z3 z3Var = p2Var.a;
        String string2 = jSONObject2.toString();
        z3Var.getClass();
        if (!TextUtils.equals(string2, q3.z)) {
            q3.z = string2;
            p2Var.a.a(11, string2, true);
        }
        JSONObject jSONObject3 = jSONObject.getJSONObject("reportConfig");
        z3 z3Var2 = p2Var.a;
        String string3 = jSONObject3.toString();
        z3Var2.getClass();
        if (!TextUtils.equals(string3, q3.A)) {
            q3.A = string3;
            p2Var.a.a(12, string3, true);
        }
        JSONObject jSONObject4 = jSONObject.getJSONObject("cryptoConfig");
        z3 z3Var3 = p2Var.a;
        String string4 = jSONObject4.toString();
        z3Var3.getClass();
        if (!TextUtils.equals(string4, q3.B)) {
            q3.B = string4;
            p2Var.a.a(15, string4, true);
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("appConfig");
        if (jSONObjectOptJSONObject != null) {
            v2.a("处理 appConfig ");
            try {
                JSONArray jSONArray = jSONObjectOptJSONObject.getJSONArray("list");
                if (jSONArray == null || jSONArray.length() <= 0) {
                    v2.b("appConfig list is empty");
                } else {
                    int length = jSONArray.length();
                    int i = jSONObjectOptJSONObject.getInt("type");
                    if (i == 1) {
                        for (int i2 = 0; i2 < length; i2++) {
                            jSONArray.put(i2, r.a(Base64.decode(jSONArray.getString(i2), 2), a, "UDID_ENC_AUTHTAG"));
                        }
                    } else if (i == 3) {
                        SecretKey secretKeyA = r.a(b, q3.a(q3.h));
                        for (int i3 = 0; i3 < length; i3++) {
                            jSONArray.put(i3, new String(r.a("AES/CBC/PKCS7Padding", secretKeyA, new IvParameterSpec("UDID_ENC_AUTHTAG".getBytes(StandardCharsets.UTF_8)), Base64.decode(jSONArray.getString(i3), 2)), StandardCharsets.UTF_8));
                        }
                    }
                    p2 p2Var2 = p2.a.a;
                    z3 z3Var4 = p2Var2.a;
                    String string5 = jSONObjectOptJSONObject.toString();
                    z3Var4.getClass();
                    if (!TextUtils.equals(string5, q3.C)) {
                        q3.C = string5;
                        p2Var2.a.a(21, string5, true);
                    }
                }
            } catch (Exception e) {
                v2.a(e);
            }
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("commonConfig");
        if (jSONObjectOptJSONObject2 != null) {
            p2 p2Var3 = p2.a.a;
            z3 z3Var5 = p2Var3.a;
            String string6 = jSONObjectOptJSONObject2.toString();
            z3Var5.getClass();
            if (!TextUtils.equals(string6, q3.D)) {
                q3.D = string6;
                p2Var3.a.a(22, string6, true);
            }
        }
        JSONObject jSONObjectOptJSONObject3 = jSONObject.optJSONObject("invokeConfig");
        if (jSONObjectOptJSONObject3 != null) {
            p2 p2Var4 = p2.a.a;
            z3 z3Var6 = p2Var4.a;
            String string7 = jSONObjectOptJSONObject3.toString();
            synchronized (z3Var6) {
                if (!TextUtils.equals(string7, q3.E)) {
                    q3.E = string7;
                    q3.c();
                    p2Var4.a.a(19, q3.E, true);
                }
            }
        }
        JSONObject jSONObject5 = new JSONObject();
        JSONObject jSONObjectOptJSONObject4 = jSONObject.optJSONObject("extConfig");
        if (jSONObjectOptJSONObject4 != null && jSONObjectOptJSONObject4.has("zxc4")) {
            jSONObject5.put("zxc4", true);
        }
        p2 p2Var5 = p2.a.a;
        p2Var5.a.a(jSONObject5);
        if (q3.r) {
            return;
        }
        p2Var5.a.getClass();
        if (true != q3.r) {
            q3.r = true;
            p2Var5.a.a(6, q3.r + "", false);
        }
    }
}
