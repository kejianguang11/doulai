package com.mobile.auth.i;

import com.mobile.auth.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f extends g {
    private b a;
    private a b;

    public static class a {
        private JSONObject a;

        public JSONObject a() {
            return this.a;
        }

        public void a(JSONObject jSONObject) {
            this.a = jSONObject;
        }
    }

    public static class b extends g {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

        @Override // com.mobile.auth.i.g
        public String a() {
            return this.d;
        }

        @Override // com.mobile.auth.i.g
        protected String a(String str) {
            return this.e + this.d + this.c + this.b + "@Fdiwmxy7CBDDQNUI";
        }

        @Override // com.mobile.auth.i.g
        public JSONObject b() {
            return null;
        }

        public void b(String str) {
            this.e = str;
        }

        public String c() {
            return this.e;
        }

        public void c(String str) {
            this.d = str;
        }

        public String d() {
            return this.a;
        }

        public void d(String str) {
            this.a = str;
        }

        public String e() {
            return this.b;
        }

        public void e(String str) {
            this.b = str;
        }

        public String f() {
            return this.c;
        }

        public void f(String str) {
            this.c = str;
        }
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.a.d;
    }

    @Override // com.mobile.auth.i.g
    protected String a(String str) {
        return null;
    }

    public void a(a aVar) {
        this.b = aVar;
    }

    public void a(b bVar) {
        this.a = bVar;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject2.put("sign", this.a.d());
            jSONObject2.put("msgid", this.a.e());
            jSONObject2.put("systemtime", this.a.f());
            jSONObject2.put("appid", this.a.a());
            jSONObject2.put("version", this.a.c());
            jSONObject.put("header", jSONObject2);
            jSONObject3.put(BuildConfig.FLAVOR_type, this.b.a());
            jSONObject.put("body", jSONObject3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
