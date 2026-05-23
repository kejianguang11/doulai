package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class o {
    private static final String a = "o";

    public static String a(int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.util.j.c, i);
            jSONObject.put("msg", str);
            return jSONObject.toString();
        } catch (Throwable th) {
            try {
                com.mobile.auth.a.a.a(a, "Json parse error", th);
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
