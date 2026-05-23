package com.zx.a.I8b7;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e0 {
    public static boolean a() {
        try {
            if (!q3.b.equals(q3.j) || TextUtils.isEmpty(q3.D)) {
                return false;
            }
            if (System.currentTimeMillis() - q3.w >= new JSONObject(q3.D).getLong("frequency") * 1000) {
                return false;
            }
            v2.a("report freq c true");
            return true;
        } catch (Exception e) {
            v2.a(e);
            return false;
        }
    }
}
