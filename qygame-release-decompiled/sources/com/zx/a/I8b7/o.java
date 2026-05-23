package com.zx.a.I8b7;

import android.net.Network;
import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.q2;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class o implements q2.b {
    public final /* synthetic */ String a;

    public o(String str) {
        this.a = str;
    }

    @Override // com.zx.a.I8b7.q2.b
    public void a() {
        b(null);
    }

    @Override // com.zx.a.I8b7.q2.b
    public void a(int i, String str) {
    }

    @Override // com.zx.a.I8b7.q2.b
    public void a(Network network) {
        b(network);
    }

    public final void b(Network network) {
        try {
            JSONArray jSONArray = new JSONObject(this.a).getJSONArray(com.igexin.push.core.b.X);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                q3.l.put(network != null ? f0.a(network, jSONObject) : g0.a(jSONObject));
            }
            p2 p2Var = p2.a.a;
            z3 z3Var = p2Var.a;
            JSONArray jSONArray2 = q3.l;
            z3Var.getClass();
            if (jSONArray2 == null) {
                return;
            }
            p2Var.a.a(63, jSONArray2.toString(), true);
            v2.a("reqBZ had changed refresh:" + jSONArray2);
        } catch (Throwable th) {
            v2.a(th.getMessage());
        }
    }
}
