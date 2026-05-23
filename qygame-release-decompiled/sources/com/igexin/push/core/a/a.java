package com.igexin.push.core.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    protected boolean a;

    private void d() {
        if (this.a) {
            return;
        }
        this.a = true;
    }

    private boolean e() {
        return this.a;
    }

    public abstract void a();

    public boolean a(Object obj) {
        return true;
    }

    public abstract void b();

    public boolean c() {
        return true;
    }
}
