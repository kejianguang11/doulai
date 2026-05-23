package com.igexin.push.core.b;

import android.text.TextUtils;
import com.igexin.push.extension.mod.BaseActionBean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class q extends BaseActionBean {
    private long a;

    private long a() {
        return this.a;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static q a(String str) throws JSONException {
        long j;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        q qVar = new q();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("actionid")) {
            qVar.setActionId(jSONObject.getString("actionid"));
        }
        if (jSONObject.has("type")) {
            qVar.setType(jSONObject.getString("type"));
        }
        if (jSONObject.has("do")) {
            qVar.setDoActionId(jSONObject.getString("do"));
        }
        if (jSONObject.has("delay")) {
            double d = jSONObject.getDouble("delay");
            j = d > 0.0d ? (long) (d * 1000.0d) : 200L;
        }
        qVar.a = j;
        return qVar;
    }

    private void a(long j) {
        this.a = j;
    }
}
