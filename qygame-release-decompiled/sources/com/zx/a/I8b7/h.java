package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;
import com.zx.a.I8b7.s3;
import com.zx.module.base.Callback;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h implements Runnable {

    public class a implements Callback {
        public a(h hVar) {
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                p2 p2Var = p2.a.a;
                p2Var.a.getClass();
                p2Var.a.a(322, str, true);
                v2.a("isp net Err had changed refresh: value");
                v2.a("n x 1 x d n1");
            } catch (Throwable th) {
                v2.a(th);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            s3.c.a.e(new JSONObject(), new a(this), 2);
        } catch (Throwable unused) {
        }
    }
}
