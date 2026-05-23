package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class l {
    public d a = null;

    public final void a(int i, int i2, String str, String str2, String str3) {
        try {
            t.c("typeTokenUaid=".concat(String.valueOf(i)));
            try {
                if (this.a == null) {
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultCode", i2);
                jSONObject.put("resultMsg", str);
                jSONObject.put("resultData", str2);
                jSONObject.put("seq", str3);
                this.a.onResult(jSONObject.toString());
                this.a = null;
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
