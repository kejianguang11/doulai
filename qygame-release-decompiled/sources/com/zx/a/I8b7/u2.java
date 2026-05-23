package com.zx.a.I8b7;

import com.zx.sdk.api.ZXIDChangedListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class u2 extends b1 {
    public final ConcurrentHashMap<String, LinkedList<ZXIDChangedListener>> a = new ConcurrentHashMap<>();

    @Override // com.zx.a.I8b7.b1
    public void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("code");
            for (String str2 : this.a.keySet()) {
                for (ZXIDChangedListener zXIDChangedListener : this.a.get(str2)) {
                    if (i == 0) {
                        zXIDChangedListener.onChange(a(str2, jSONObject.getString(com.alipay.sdk.packet.d.k)));
                    }
                }
                this.a.remove(str2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
