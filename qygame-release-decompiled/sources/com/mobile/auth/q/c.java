package com.mobile.auth.q;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c implements com.mobile.auth.e.b {
    private Map<String, String> a = new HashMap();
    private com.mobile.auth.e.b b;

    public c(com.mobile.auth.e.b bVar) {
        this.b = bVar;
    }

    public Map<String, String> a() {
        try {
            return this.a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.mobile.auth.e.b
    public void a(int i, JSONObject jSONObject) {
        try {
            if (this.a != null && !this.a.isEmpty()) {
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                for (Map.Entry<String, String> entry : this.a.entrySet()) {
                    try {
                        jSONObject.put(entry.getKey(), entry.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (this.b != null) {
                this.b.a(i, jSONObject);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
