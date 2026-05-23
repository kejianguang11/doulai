package com.zx.a.I8b7;

import android.text.TextUtils;
import com.zx.module.base.Listener;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class y3 implements t3 {
    public final AtomicBoolean a = new AtomicBoolean(false);
    public final AtomicBoolean b = new AtomicBoolean(false);
    public Listener c;

    public static void a(y3 y3Var) throws Exception {
        y3Var.getClass();
        String str = q3.i;
        if (!q3.r) {
            m.c();
        }
        r1.g();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 0);
        jSONObject.put(com.alipay.sdk.packet.d.k, q3.a());
        String string = jSONObject.toString();
        y3Var.c.onMessage("MESSAGE_ON_ZXID_RECEIVED", string);
        if (TextUtils.equals(str, q3.i)) {
            return;
        }
        y3Var.c.onMessage("MESSAGE_ON_ZXID_CHANGED", string);
    }
}
