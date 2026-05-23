package com.zx.a.I8b7;

import com.zx.a.I8b7.s1;
import com.zx.module.annotation.Java2C;
import java.security.SecureRandom;
import java.util.HashMap;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
public class x1 {
    public static SecretKey a;
    public static byte[] b;
    public static final SecureRandom c = new SecureRandom();

    @Java2C.Method2C
    public static native synchronized String a();

    @Java2C.Method2C
    private static native String a(String str, String str2) throws Exception;

    public static void b(String str, String str2) throws Exception {
        s1.a aVar = new s1.a();
        HashMap<String, String> mapB = k0.b(a());
        aVar.c.clear();
        aVar.c.putAll(mapB);
        s1.a aVarA = aVar.a("https://zxid-m.mobileservice.cn/sdk/uaid/reportAuthToken");
        aVarA.b = "POST";
        aVarA.d = u1.a(z0.b("application/json; charset=utf-8"), a(str, str2));
        aVarA.e = "SAIDCodeRequest get api";
        s2 s2Var = k0.a;
        s1 s1Var = new s1(aVar);
        s2Var.getClass();
        new k1(s2Var, s1Var).a();
    }
}
