package com.mobile.auth.i;

import com.nirvana.tools.logger.cache.db.DBHelpTool;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h extends a {
    protected String x = "";
    protected String y = "";

    @Override // com.mobile.auth.i.g
    protected String a(String str) {
        return this.b + this.c + this.d + this.e + this.f + this.g + this.h + this.i + this.j + this.m + this.n + str + this.o + this.q + this.r + this.s + this.t + this.u + this.v + this.x + this.y + this.w;
    }

    @Override // com.mobile.auth.i.a
    public void a_(String str) {
        this.v = t(str);
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.a);
            jSONObject.put("sdkver", this.b);
            jSONObject.put("appid", this.c);
            jSONObject.put("imsi", this.d);
            jSONObject.put("operatortype", this.e);
            jSONObject.put("networktype", this.f);
            jSONObject.put("mobilebrand", this.g);
            jSONObject.put("mobilemodel", this.h);
            jSONObject.put("mobilesystem", this.i);
            jSONObject.put("clienttype", this.j);
            jSONObject.put("interfacever", this.k);
            jSONObject.put("expandparams", this.l);
            jSONObject.put("msgid", this.m);
            jSONObject.put(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP, this.n);
            jSONObject.put("subimsi", this.o);
            jSONObject.put("sign", this.p);
            jSONObject.put("apppackage", this.q);
            jSONObject.put("appsign", this.r);
            jSONObject.put("ipv4_list", this.s);
            jSONObject.put("ipv6_list", this.t);
            jSONObject.put("sdkType", this.u);
            jSONObject.put("tempPDR", this.v);
            jSONObject.put("scrip", this.x);
            jSONObject.put("userCapaid", this.y);
            jSONObject.put("funcType", this.w);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return this.a + com.alipay.sdk.sys.a.b + this.b + com.alipay.sdk.sys.a.b + this.c + com.alipay.sdk.sys.a.b + this.d + com.alipay.sdk.sys.a.b + this.e + com.alipay.sdk.sys.a.b + this.f + com.alipay.sdk.sys.a.b + this.g + com.alipay.sdk.sys.a.b + this.h + com.alipay.sdk.sys.a.b + this.i + com.alipay.sdk.sys.a.b + this.j + com.alipay.sdk.sys.a.b + this.k + com.alipay.sdk.sys.a.b + this.l + com.alipay.sdk.sys.a.b + this.m + com.alipay.sdk.sys.a.b + this.n + com.alipay.sdk.sys.a.b + this.o + com.alipay.sdk.sys.a.b + this.p + com.alipay.sdk.sys.a.b + this.q + com.alipay.sdk.sys.a.b + this.r + "&&" + this.s + com.alipay.sdk.sys.a.b + this.t + com.alipay.sdk.sys.a.b + this.u + com.alipay.sdk.sys.a.b + this.v + com.alipay.sdk.sys.a.b + this.x + com.alipay.sdk.sys.a.b + this.y + com.alipay.sdk.sys.a.b + this.w;
    }

    public void v(String str) {
        this.x = t(str);
    }

    public void w(String str) {
        this.y = t(str);
    }
}
