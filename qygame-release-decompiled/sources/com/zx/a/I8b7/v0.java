package com.zx.a.I8b7;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class v0 {
    public final List<l0> a = new ArrayList();

    public void a(int i, String str, String str2, Throwable th) {
        for (l0 l0Var : this.a) {
            try {
                if (l0Var.a(i, null)) {
                    l0Var.a(i, null, str2, th);
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public void a(l0 l0Var) {
        this.a.add(l0Var);
    }
}
