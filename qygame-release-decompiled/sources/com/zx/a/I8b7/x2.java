package com.zx.a.I8b7;

import android.content.Context;
import android.text.TextUtils;
import com.zx.a.I8b7.p2;
import com.zx.sdk.api.Callback;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class x2 implements Runnable {
    public final /* synthetic */ Context a;
    public final /* synthetic */ Callback b;

    public x2(b3 b3Var, Context context, Callback callback) {
        this.a = context;
        this.b = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strA;
        try {
            Context context = this.a;
            if (TextUtils.isEmpty(q3.k) || "{}".equals(q3.k)) {
                q3.a = context.getApplicationContext();
                p2 p2Var = p2.a.a;
                q3.a(p2Var.a);
                strA = p2Var.a.a(16);
                q3.k = strA;
            } else {
                strA = q3.k;
            }
            try {
                if (TextUtils.isEmpty(strA)) {
                    String strA2 = b4.a("62");
                    Callback callback = this.b;
                    if (b4.d.get(strA2) == Boolean.TRUE) {
                        strA2 = "";
                    }
                    callback.onSuccess(strA2);
                    return;
                }
                String strOptString = new JSONObject(strA).optString("openid");
                if ("OPENID_CLOSED".equals(strOptString)) {
                    this.b.onFailed(10001, "未开通");
                    return;
                }
                String strA3 = b4.a("62");
                if (!(b4.d.get(strA3) == Boolean.TRUE)) {
                    strOptString = strA3;
                }
                this.b.onSuccess(strOptString);
            } catch (Throwable th) {
                this.b.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
        } catch (Throwable th2) {
            Callback callback2 = this.b;
            if (callback2 != null) {
                callback2.onFailed(com.igexin.push.config.c.d, th2.getMessage());
            }
            k3.a(th2, j3.a("ZXManager.getZXID(zxidListener) failed: "));
        }
    }
}
