package com.zx.a.I8b7;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Base64;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import javax.crypto.SecretKey;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h0 {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    public static void b() throws Exception {
        String str;
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(a());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/module/getCoreModule");
        aVarA.b = "POST";
        z0 z0VarB = z0.b("application/json; charset=utf-8");
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("lid", q3.a(q3.h));
        jSONObject2.put("zid", q3.i);
        jSONObject.put("ctx", jSONObject2);
        jSONObject.put("sdkInfo", k0.d());
        jSONObject.put("deviceInfo", k0.b());
        aVarA.d = u1.a(z0VarB, new String(Base64.encode(r.a(jSONObject.toString(), a, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8));
        aVarA.e = "request getCoreModule api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        v1 v1VarA = new k1(s2Var, s1Var).a();
        if (v1VarA.b != 200) {
            throw new RuntimeException("response errCode: " + v1VarA.a("Udid-Error-Code") + ", errMsg: " + v1VarA.a("Udid-Error-Message"));
        }
        JSONObject jSONObject3 = new JSONObject(r.a(Base64.decode(new JSONObject(v1VarA.e.b()).getString(com.alipay.sdk.packet.d.k), 2), a, "UDID_ENC_AUTHTAG"));
        if (jSONObject3.getBoolean("enable")) {
            JSONObject jSONObject4 = jSONObject3.getJSONObject("module");
            jSONObject4.getString("version");
            String string = jSONObject4.getString("checksum");
            byte[] bArrDecode = Base64.decode(jSONObject4.getString(com.alipay.sdk.packet.d.k), 0);
            if (!TextUtils.equals(string, r.a("SHA256", bArrDecode))) {
                throw new IOException("zx checksum1 exception");
            }
            v2.a("verify checksum finished");
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("mainVersion", q3.b);
            jSONObject5.put("coreVersion", q3.d);
            jSONObject5.put("checksum", string);
            p2.a.a.a.getClass();
            String string2 = jSONObject5.getString("coreVersion");
            String string3 = "";
            try {
                string3 = q3.H.getString("coreVersion");
            } catch (Exception unused) {
            }
            if (!TextUtils.isEmpty(string2) && !TextUtils.equals(string2, string3)) {
                z3 z3Var = p2.a.a.a;
                if (z3Var.b == null) {
                    z3Var.b = z3Var.d();
                }
                try {
                    String str2 = new String(Base64.encode(r.b("AES/CBC/PKCS5Padding", q3.x, q3.y, bArrDecode), 0), StandardCharsets.UTF_8);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("key", (Integer) 17);
                    contentValues.put("value", str2);
                    v2.a("replace resultId = " + z3Var.b.replace("zx_table", null, contentValues));
                } catch (Exception e) {
                    v2.b("ZXID updateDBValue valueID:17,value:" + bArrDecode + ",error:" + e.toString());
                }
                p2.a.a.a.a(18, jSONObject5.toString(), true);
                q3.H = jSONObject5;
            }
            str = "decrypt and checksum finished";
        } else {
            z3 z3Var2 = p2.a.a.a;
            if (z3Var2.b == null) {
                z3Var2.b = z3Var2.d();
            }
            try {
                SQLiteDatabase sQLiteDatabase = z3Var2.b;
                StringBuilder sb = new StringBuilder();
                sb.append("key in(");
                sb.append(17 + com.igexin.push.core.b.an + 18);
                sb.append(")");
                sQLiteDatabase.delete("zx_table", sb.toString(), null);
                q3.H = null;
                v2.a("clearCoreModule success");
            } catch (Exception e2) {
                StringBuilder sbA = j3.a("clearCoreModule error:");
                sbA.append(e2.getMessage());
                v2.b(sbA.toString());
            }
            str = "coreModule enable is false";
        }
        v2.a(str);
    }
}
