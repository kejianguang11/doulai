package com.igexin.push.core.a.b;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class f extends a {
    private static final String a = com.igexin.push.config.c.a + "_QueryTagResultAction";

    @Override // com.igexin.push.core.a.b.a
    public final boolean a(Object obj, JSONObject jSONObject) {
        com.igexin.c.a.c.a.a(a + "|query tag result resp data = " + jSONObject, new Object[0]);
        try {
            if (!jSONObject.has("action") || !jSONObject.getString("action").equals("query_tag_result")) {
                return true;
            }
            String string = jSONObject.getString("tags");
            com.igexin.push.core.l.a().a(jSONObject.getString("sn"), jSONObject.getString("error_code"), jSONObject.getString("tags"));
            if (TextUtils.isEmpty(string)) {
                string = com.igexin.push.a.i;
            }
            com.igexin.push.core.e.f.a().e(string);
            return true;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return true;
        }
    }
}
