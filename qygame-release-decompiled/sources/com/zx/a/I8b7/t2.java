package com.zx.a.I8b7;

import com.zx.a.I8b7.a0;
import com.zx.sdk.api.ZXIDListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class t2 extends b1 {
    public final ConcurrentHashMap<String, LinkedList<ZXIDListener>> a = new ConcurrentHashMap<>();

    @Override // com.zx.a.I8b7.b1
    public void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("code");
            String strOptString = jSONObject.optString(com.igexin.push.core.b.Z);
            for (String str2 : this.a.keySet()) {
                for (ZXIDListener zXIDListener : this.a.get(str2)) {
                    if (i == 0) {
                        zXIDListener.onSuccess(a(str2, jSONObject.getString(com.alipay.sdk.packet.d.k)));
                    } else {
                        a0 a0Var = a0.b.a;
                        a0Var.getClass();
                        try {
                            a0Var.a(new b0(a0Var, i, strOptString));
                        } catch (Throwable th) {
                            v2.a(th);
                        }
                        zXIDListener.onFailed(i, strOptString);
                    }
                }
                this.a.remove(str2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
