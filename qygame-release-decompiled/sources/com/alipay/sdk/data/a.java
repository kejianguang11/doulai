package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final int a = 3500;
    public static final String b = "http://h5.m.taobao.com/trade/paySuccess.html?bizOrderId=$OrderId$&";
    public static final int c = 1000;
    public static final int d = 20000;
    public static final String e = "alipay_cashier_dynamic_config";
    public static final String f = "timeout";
    public static final String g = "st_sdk_config";
    public static final String h = "tbreturl";
    private static a k;
    int i = a;
    public String j = b;

    private static /* synthetic */ void a(a aVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", aVar.a());
            jSONObject.put(h, aVar.j);
            i.a(com.alipay.sdk.sys.b.a().a, e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    private static /* synthetic */ void a(a aVar, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(g);
            aVar.i = jSONObjectOptJSONObject.optInt("timeout", a);
            aVar.j = jSONObjectOptJSONObject.optString(h, b).trim();
        } catch (Throwable unused) {
        }
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.i = jSONObject.optInt("timeout", a);
            this.j = jSONObject.optString(h, b).trim();
        } catch (Throwable unused) {
        }
    }

    public static a b() {
        if (k == null) {
            a aVar = new a();
            k = aVar;
            String strB = i.b(com.alipay.sdk.sys.b.a().a, e, null);
            if (!TextUtils.isEmpty(strB)) {
                try {
                    JSONObject jSONObject = new JSONObject(strB);
                    aVar.i = jSONObject.optInt("timeout", a);
                    aVar.j = jSONObject.optString(h, b).trim();
                } catch (Throwable unused) {
                }
            }
        }
        return k;
    }

    private void b(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(g);
            this.i = jSONObjectOptJSONObject.optInt("timeout", a);
            this.j = jSONObjectOptJSONObject.optString(h, b).trim();
        } catch (Throwable unused) {
        }
    }

    private String c() {
        return this.j;
    }

    private void d() {
        String strB = i.b(com.alipay.sdk.sys.b.a().a, e, null);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(strB);
            this.i = jSONObject.optInt("timeout", a);
            this.j = jSONObject.optString(h, b).trim();
        } catch (Throwable unused) {
        }
    }

    private void e() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", a());
            jSONObject.put(h, this.j);
            i.a(com.alipay.sdk.sys.b.a().a, e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    public final int a() {
        if (this.i < 1000 || this.i > 20000) {
            return a;
        }
        new StringBuilder("DynamicConfig::getJumpTimeout >").append(this.i);
        return this.i;
    }

    public final void a(Context context) {
        new Thread(new b(this, context)).start();
    }
}
