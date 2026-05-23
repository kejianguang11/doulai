package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class d0 implements Runnable {
    public final /* synthetic */ a0 a;

    public d0(a0 a0Var) {
        this.a = a0Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a0 a0Var = this.a;
            if (a0Var.b != null) {
                a0Var.b = new JSONArray();
                p2 p2Var = p2.a.a;
                p2Var.a.getClass();
                p2Var.a.a(321, "", true);
            }
        } catch (Throwable unused) {
        }
    }
}
