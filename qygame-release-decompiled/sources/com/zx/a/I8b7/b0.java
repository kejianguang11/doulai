package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b0 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;
    public final /* synthetic */ a0 c;

    public b0(a0 a0Var, int i, String str) {
        this.c = a0Var;
        this.a = i;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.c.b.length() >= 10) {
                v2.a("error list length > MAX_COUNT " + this.c.b.length());
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", this.a);
            jSONObject.put("msg", this.b);
            this.c.b.put(jSONObject);
            v2.a("error add:" + jSONObject);
            if (q3.I) {
                v2.a("error save:" + this.c.b.toString());
                p2 p2Var = p2.a.a;
                z3 z3Var = p2Var.a;
                String string = this.c.b.toString();
                z3Var.getClass();
                p2Var.a.a(321, string, true);
            }
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
