package com.zx.a.I8b7;

import android.util.Base64;
import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.security.SecureRandom;
import java.util.HashMap;
import javax.crypto.SecretKey;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class y1 {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    @Java2C.Method2C
    private static native String a(JSONObject jSONObject, String str, String str2, String str3) throws Exception;

    public static String b(JSONObject jSONObject, String str, String str2, String str3) throws Exception {
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(a());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/uaid/get");
        aVarA.b = "POST";
        aVarA.d = u1.a(z0.b("application/json; charset=utf-8"), a(jSONObject, str, str2, str3));
        aVarA.e = "said get api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        v1 v1VarA = new k1(s2Var, s1Var).a();
        if (v1VarA.b == 200) {
            return new JSONObject(r.a(Base64.decode(new JSONObject(v1VarA.e.b()).getString(com.alipay.sdk.packet.d.k), 2), a, "UDID_ENC_AUTHTAG")).getString("uaid");
        }
        throw new RuntimeException("response errCode: " + v1VarA.a("Udid-Error-Code") + ", errMsg: " + v1VarA.a("Udid-Error-Message"));
    }
}
