package com.igexin.push.core.a.b;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b extends a {
    private static final String a = com.igexin.push.config.c.a + "_BindAliasResultAction";

    @Override // com.igexin.push.core.a.b.a
    public final boolean a(Object obj, JSONObject jSONObject) {
        com.igexin.c.a.c.a.b(a, "bind alias result resp data = ".concat(String.valueOf(jSONObject)));
        try {
            if (!jSONObject.has("action") || !jSONObject.getString("action").equals("response_bind")) {
                return true;
            }
            com.igexin.push.core.l.a().b(jSONObject.getString("sn"), jSONObject.getString(com.alipay.sdk.util.j.c));
            return true;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return true;
        }
    }
}
