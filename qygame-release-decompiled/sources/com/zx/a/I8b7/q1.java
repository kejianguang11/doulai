package com.zx.a.I8b7;

import com.zx.a.I8b7.r3;
import com.zx.module.base.Callback;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class q1 implements Runnable {

    public class a implements Callback {
        public a(q1 q1Var) {
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt("code") == 0) {
                    x1.b(jSONObject.getJSONObject(com.alipay.sdk.packet.d.k).getString("type"), jSONObject.getJSONObject(com.alipay.sdk.packet.d.k).getString("code"));
                }
            } catch (Throwable th) {
                v2.a(th);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            r3.b.a.b(new JSONObject(), new a(this), 2);
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
