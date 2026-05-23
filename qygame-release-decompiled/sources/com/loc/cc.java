package com.loc;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class cc extends cg {
    private int a;
    private String b;

    public cc(String str, cg cgVar) {
        super(cgVar);
        this.a = 30;
        this.b = str;
    }

    private static int a(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return file.list().length;
            }
            return 0;
        } catch (Throwable th) {
            an.b(th, "fus", "gfn");
            return 0;
        }
    }

    @Override // com.loc.cg
    protected final boolean a() {
        return a(this.b) >= this.a;
    }
}
