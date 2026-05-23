package com.zx.a.I8b7;

import com.zx.a.I8b7.a4;
import com.zx.a.I8b7.p2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a0 {
    public JSONArray a = new JSONArray();
    public JSONArray b = new JSONArray();

    public class a implements Runnable {
        public final /* synthetic */ String a;
        public final /* synthetic */ String b;
        public final /* synthetic */ String c;

        public a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (a0.this.a.length() >= 100) {
                    v2.a("events length > MAX_COUNT " + a0.this.a.length());
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ts", jCurrentTimeMillis);
                jSONObject.put("callId", this.a);
                jSONObject.put("action", this.b);
                jSONObject.put("params", this.c);
                a0.this.a.put(jSONObject);
                v2.a("events add:" + jSONObject.toString());
                if (q3.I) {
                    v2.a("events save:" + a0.this.a.toString());
                    p2 p2Var = p2.a.a;
                    z3 z3Var = p2Var.a;
                    String string = a0.this.a.toString();
                    z3Var.getClass();
                    p2Var.a.a(23, string, true);
                }
            } catch (Throwable th) {
                v2.a(th);
            }
        }
    }

    public static final class b {
        public static final a0 a = new a0();
    }

    public final JSONArray a(JSONArray jSONArray, JSONArray jSONArray2, int i) throws JSONException {
        JSONArray jSONArray3 = new JSONArray();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            if (jSONArray3.length() >= i) {
                return jSONArray3;
            }
            jSONArray3.put(jSONArray.get(i2));
        }
        for (int i3 = 0; i3 < jSONArray2.length() && jSONArray3.length() < i; i3++) {
            jSONArray3.put(jSONArray2.get(i3));
        }
        return jSONArray3;
    }

    public final void a(Runnable runnable) {
        try {
            a4.f.a.b.execute(runnable);
        } catch (Throwable th) {
            v2.a(th);
        }
    }

    public void a(String str, String str2, String str3) {
        try {
            a(new a(str, str2, str3));
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
