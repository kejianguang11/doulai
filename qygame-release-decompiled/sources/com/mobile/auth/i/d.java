package com.mobile.auth.i;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d extends g {
    private final String a;
    private final String b;
    private final String c;
    private String d = "authz";
    private String e;

    public d(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return this.a;
    }

    @Override // com.mobile.auth.i.g
    protected String a(String str) {
        return null;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.b);
            jSONObject.put(com.alipay.sdk.packet.d.k, this.c);
            jSONObject.put("userCapaid", this.e);
            jSONObject.put("funcType", this.d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void b(String str) {
        this.d = str;
    }

    public void c(String str) {
        this.e = str;
    }
}
