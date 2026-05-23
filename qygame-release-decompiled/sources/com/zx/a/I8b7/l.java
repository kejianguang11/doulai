package com.zx.a.I8b7;

import android.util.Base64;
import com.zx.module.annotation.Java2C;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    @Java2C.Method2C
    public static native void a(String str);

    public static String b(String str) throws Exception {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("lid", q3.a(q3.h));
        jSONObject2.put("zid", q3.i);
        jSONObject.put("ctx", jSONObject2);
        jSONObject.put("code", str);
        return new String(Base64.encode(r.a(jSONObject.toString(), a, "UDID_ENC_AUTHTAG"), 2), StandardCharsets.UTF_8);
    }
}
