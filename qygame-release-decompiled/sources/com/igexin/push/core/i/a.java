package com.igexin.push.core.i;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    protected Long a = Long.valueOf(System.currentTimeMillis());
    protected Activity b;
    protected String c;

    private void a(Long l) {
        this.a = l;
    }

    private void a(String str) {
        this.c = str;
    }

    private Activity n() {
        return this.b;
    }

    public final Long a() {
        return this.a;
    }

    public final void a(Activity activity) {
        this.b = activity;
    }

    public final String b() {
        return this.c;
    }

    public abstract void c();

    public abstract void d();

    public abstract void e();

    public abstract void f();

    public abstract void g();

    public abstract void h();

    public abstract void i();

    public abstract boolean j();

    public abstract void k();

    public abstract boolean l();

    public abstract void m();
}
