package com.alipay.security.mobile.module.http.model;

/* JADX INFO: loaded from: classes.dex */
public final class c extends a {
    public static final int c = 1;
    public static final int d = 2;
    public static final int e = 3;
    public static final String f = "APPKEY_ERROR";
    public static final String g = "SUCCESS";
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p = "";

    private String a() {
        return this.p;
    }

    private void a(String str) {
        this.p = str;
    }

    private int b() {
        return this.a ? com.alipay.security.mobile.module.a.a.a(this.h) ? 2 : 1 : f.equals(this.b) ? 3 : 2;
    }

    private void b(String str) {
        this.h = str;
    }

    private void c(String str) {
        this.i = str;
    }

    private boolean c() {
        return "1".equals(this.j);
    }

    private String d() {
        return this.k == null ? "0" : this.k;
    }

    private void d(String str) {
        this.j = str;
    }

    private String e() {
        return this.h;
    }

    private void e(String str) {
        this.k = str;
    }

    private String f() {
        return this.i;
    }

    private void f(String str) {
        this.l = str;
    }

    private String g() {
        return this.j;
    }

    private void g(String str) {
        this.n = str;
    }

    private String h() {
        return this.l;
    }

    private void h(String str) {
        this.m = str;
    }

    private String i() {
        return this.n;
    }

    private void i(String str) {
        this.o = str;
    }

    private String j() {
        return this.m;
    }

    private String k() {
        return this.o;
    }
}
