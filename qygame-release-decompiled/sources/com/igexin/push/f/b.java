package com.igexin.push.f;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    private static final String c = "ExtensionTask";
    protected long a = 0;
    protected long b = 0;

    private void a(long j) {
        this.a = j;
    }

    private boolean b() {
        getClass().getName();
        System.currentTimeMillis();
        return System.currentTimeMillis() - this.a > this.b;
    }

    public abstract void a();
}
