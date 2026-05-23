package com.igexin.push.core.a.b;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class l extends a {
    private static final String a = com.igexin.push.config.c.a + "_UnBindAliasResultAction";

    @Override // com.igexin.push.core.a.b.a
    public final boolean a(Object obj, JSONObject jSONObject) {
        com.igexin.c.a.c.a.a(a + "|unbind alias result resp data = " + jSONObject, new Object[0]);
        try {
            if (!jSONObject.has("action") || !jSONObject.getString("action").equals("response_unbind")) {
                return true;
            }
            com.igexin.push.core.l.a().c(jSONObject.getString("sn"), jSONObject.getString(com.alipay.sdk.util.j.c));
            return true;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a(a + "|" + e.toString(), new Object[0]);
            return true;
        }
    }
}
