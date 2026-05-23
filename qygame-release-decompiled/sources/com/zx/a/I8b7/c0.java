package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class c0 implements Runnable {
    public final /* synthetic */ a0 a;

    public c0(a0 a0Var) {
        this.a = a0Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a0 a0Var = this.a;
            if (a0Var.a != null) {
                a0Var.a = new JSONArray();
                p2 p2Var = p2.a.a;
                p2Var.a.getClass();
                p2Var.a.a(23, "", true);
            }
        } catch (Throwable unused) {
        }
    }
}
