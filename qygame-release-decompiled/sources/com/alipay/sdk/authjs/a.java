package com.alipay.sdk.authjs;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "CallInfo";
    public static final String b = "call";
    public static final String c = "callback";
    public static final String d = "bundleName";
    public static final String e = "clientId";
    public static final String f = "param";
    public static final String g = "func";
    public static final String h = "msgType";
    public String i;
    public String j;
    public String k;
    public String l;
    public JSONObject m;
    private boolean n = false;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.alipay.sdk.authjs.a$a, reason: collision with other inner class name */
    public static final class EnumC0002a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        public static final int d = 4;
        public static final int e = 5;
        private static final /* synthetic */ int[] f = {a, b, c, d, e};

        private EnumC0002a(String str, int i) {
        }

        public static int[] a() {
            return (int[]) f.clone();
        }
    }

    public a(String str) {
        this.l = str;
    }

    private static String a(int i) {
        switch (b.a[i - 1]) {
            case 1:
                return "function not found";
            case 2:
                return "invalid parameter";
            case 3:
                return "runtime error";
            default:
                return com.igexin.push.a.i;
        }
    }

    private void a(String str) {
        this.i = str;
    }

    private void a(JSONObject jSONObject) {
        this.m = jSONObject;
    }

    private void a(boolean z) {
        this.n = z;
    }

    private boolean a() {
        return this.n;
    }

    private String b() {
        return this.i;
    }

    private void b(String str) {
        this.j = str;
    }

    private String c() {
        return this.j;
    }

    private void c(String str) {
        this.k = str;
    }

    private String d() {
        return this.k;
    }

    private void d(String str) {
        this.l = str;
    }

    private String e() {
        return this.l;
    }

    private JSONObject f() {
        return this.m;
    }

    private String g() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(e, this.i);
        jSONObject.put(g, this.k);
        jSONObject.put(f, this.m);
        jSONObject.put(h, this.l);
        return jSONObject.toString();
    }
}
