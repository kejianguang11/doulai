package com.cmic.sso.sdk.d;

import com.mobile.auth.BuildConfig;
import com.mobile.auth.i.g;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b extends g {
    private String A;
    private String B;
    private JSONArray o;
    private String y;
    private String z;
    private String b = null;
    private String c = null;
    private String d = null;
    private String e = null;
    private String f = null;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private String k = "";
    private String l = null;
    private String m = null;
    private String n = null;
    private String p = null;
    private String q = null;
    private String r = null;
    private String s = null;
    private String t = null;
    private String u = null;
    private String v = null;
    private String w = null;
    private String x = null;
    public CopyOnWriteArrayList<Throwable> a = new CopyOnWriteArrayList<>();

    public void A(String str) {
        this.A = str;
    }

    public void B(String str) {
        this.B = str;
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return null;
    }

    @Override // com.mobile.auth.i.g
    protected String a(String str) {
        return null;
    }

    void a(JSONArray jSONArray) {
        this.o = jSONArray;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", this.b);
            jSONObject.put("traceId", this.c);
            jSONObject.put("appName", this.d);
            jSONObject.put("appVersion", this.e);
            jSONObject.put("sdkVersion", BuildConfig.CMCC_SDK_VERSION);
            jSONObject.put("clientType", "android");
            jSONObject.put("timeOut", this.f);
            jSONObject.put("requestTime", this.g);
            jSONObject.put("responseTime", this.h);
            jSONObject.put("elapsedTime", this.i);
            jSONObject.put("requestType", this.j);
            jSONObject.put("interfaceType", this.k);
            jSONObject.put("interfaceCode", this.l);
            jSONObject.put("interfaceElasped", this.m);
            jSONObject.put("loginType", this.n);
            jSONObject.put("exceptionStackTrace", this.o);
            jSONObject.put("operatorType", this.p);
            jSONObject.put("networkType", this.q);
            jSONObject.put("networkClass", this.r);
            jSONObject.put("brand", this.s);
            jSONObject.put("reqDevice", this.t);
            jSONObject.put("reqSystem", this.u);
            jSONObject.put("simCardNum", this.v);
            jSONObject.put("imsiState", this.w);
            jSONObject.put("resultCode", this.x);
            jSONObject.put("is_phoneStatePermission", this.y);
            jSONObject.put("AID", this.z);
            jSONObject.put("sysOperType", this.A);
            jSONObject.put("scripType", this.B);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void b(String str) {
        this.b = str;
    }

    public void c(String str) {
        this.y = str;
    }

    public void d(String str) {
        this.w = str;
    }

    public void e(String str) {
        this.x = str;
    }

    public void f(String str) {
        this.s = str;
    }

    public void g(String str) {
        this.m = str;
    }

    public void h(String str) {
        this.l = str;
    }

    public void i(String str) {
        this.k = str;
    }

    public void j(String str) {
        this.d = str;
    }

    public void k(String str) {
        this.e = str;
    }

    public void l(String str) {
        this.f = str;
    }

    public void m(String str) {
        this.i = str;
    }

    void n(String str) {
        this.v = str;
    }

    void o(String str) {
        this.p = str;
    }

    void p(String str) {
        this.t = str;
    }

    void q(String str) {
        this.u = str;
    }

    public void r(String str) {
        this.n = str;
    }

    void s(String str) {
        this.c = str;
    }

    public void t(String str) {
        this.g = str;
    }

    void v(String str) {
        this.r = str;
    }

    public void w(String str) {
        this.h = str;
    }

    public void x(String str) {
        this.j = str;
    }

    void y(String str) {
        this.q = str;
    }

    public void z(String str) {
        this.z = str;
    }
}
