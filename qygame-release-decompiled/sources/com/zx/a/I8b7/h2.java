package com.zx.a.I8b7;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h2 {
    public static String a(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            jSONObject.put(com.igexin.push.core.b.Z, str);
        } catch (Exception unused) {
        }
        return jSONObject.toString();
    }
}
