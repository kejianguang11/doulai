package com.zx.a.I8b7;

import android.text.TextUtils;
import com.zx.a.I8b7.g1;
import com.zx.a.I8b7.p2;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class v3 implements Runnable {
    public final /* synthetic */ y3 a;

    public v3(y3 y3Var) {
        this.a = y3Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        g1 g1Var = g1.a.a;
        try {
            g1Var.a = System.currentTimeMillis();
            g1Var.b = UUID.randomUUID().toString().replaceAll("-", "");
            p2 p2Var = p2.a.a;
            String strA = p2Var.a.a(24);
            if (!TextUtils.isEmpty(strA)) {
                g1Var.c = Integer.parseInt(strA);
            }
            g1Var.c++;
            z3 z3Var = p2Var.a;
            String str = g1Var.c + "";
            z3Var.getClass();
            p2Var.a.a(24, str, true);
            v2.a("process start pts:" + g1Var.a + ", pid:" + g1Var.b + ", rc:" + g1Var.c);
        } catch (Throwable th) {
            v2.a(th);
        }
        if (!this.a.b.get()) {
            throw new IllegalStateException("ZXSdkImpl not init, should init firstly");
        }
        try {
            y3.a(this.a);
        } catch (Throwable th2) {
            this.a.c.onMessage("MESSAGE_ON_ZXID_RECEIVED", h2.a(com.igexin.push.config.c.d, th2.getMessage()));
            StringBuilder sb = new StringBuilder();
            sb.append("ZXCore start failed: ");
            k3.a(th2, sb);
        }
    }
}
