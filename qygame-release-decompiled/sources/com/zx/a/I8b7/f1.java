package com.zx.a.I8b7;

import android.text.TextUtils;
import android.util.Base64;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.crypto.SecretKey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f1 {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    /* JADX WARN: Removed duplicated region for block: B:24:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void b() throws Exception {
        JSONArray jSONArray;
        String strA = p2.a.a.a.a(25);
        if (TextUtils.isEmpty(strA)) {
            jSONArray = null;
        } else {
            try {
                JSONArray jSONArray2 = new JSONArray(strA);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray2.length(); i++) {
                    try {
                        arrayList.add(jSONArray2.getString(i));
                    } catch (JSONException unused) {
                    }
                }
                Collections.sort(arrayList, new g2());
                jSONArray = new JSONArray();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    try {
                        String str = (String) arrayList.get(i2);
                        if (b4.c(str) != null || b4.b(str)) {
                            jSONArray.put(str);
                        }
                    } catch (Exception unused2) {
                        v2.b("iaps data error");
                    }
                }
                if (TextUtils.equals(p2.a.a.a.a(26), jSONArray.toString())) {
                }
            } catch (JSONException unused3) {
            }
        }
        if (jSONArray == null) {
            v2.a("laps 和上次一样本次不上报");
            return;
        }
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(a());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/app/depAnalysis");
        aVarA.b = "POST";
        z0 z0VarB = z0.b("application/json; charset=utf-8");
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("lid", q3.a(q3.h));
        jSONObject2.put("zid", q3.i);
        jSONObject.put("ctx", jSONObject2);
        jSONObject.put("sdkInfo", k0.d());
        jSONObject.put("deviceInfo", k0.b());
        jSONObject.put("apps", new String(Base64.encode(r.a(jSONArray.toString(), a, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8));
        aVarA.d = u1.a(z0VarB, new String(Base64.encode(r.a(jSONObject.toString(), a, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8));
        aVarA.e = "request postIAPS api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        v1 v1VarA = new k1(s2Var, s1Var).a();
        if (v1VarA.b == 200) {
            p2 p2Var = p2.a.a;
            z3 z3Var = p2Var.a;
            String string = jSONArray.toString();
            z3Var.getClass();
            if (TextUtils.isEmpty(string)) {
                return;
            }
            p2Var.a.a(26, string, true);
            return;
        }
        throw new RuntimeException("response errCode: " + v1VarA.a("Udid-Error-Code") + ", errMsg: " + v1VarA.a("Udid-Error-Message"));
    }
}
