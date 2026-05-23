package com.mobile.auth.d;

/* JADX INFO: loaded from: classes.dex */
public class a implements Cloneable {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;

    /* JADX INFO: renamed from: com.mobile.auth.d.a$a, reason: collision with other inner class name */
    public static class C0041a {
        private final a a = new a();

        C0041a a(int i) {
            this.a.k = i;
            return this;
        }

        C0041a a(String str) {
            this.a.a = str;
            return this;
        }

        C0041a a(boolean z) {
            this.a.e = z;
            return this;
        }

        public a a() {
            return this.a;
        }

        C0041a b(int i) {
            this.a.l = i;
            return this;
        }

        C0041a b(String str) {
            this.a.b = str;
            return this;
        }

        C0041a b(boolean z) {
            this.a.f = z;
            return this;
        }

        C0041a c(String str) {
            this.a.c = str;
            return this;
        }

        C0041a c(boolean z) {
            this.a.g = z;
            return this;
        }

        C0041a d(String str) {
            this.a.d = str;
            return this;
        }

        C0041a d(boolean z) {
            this.a.h = z;
            return this;
        }

        C0041a e(boolean z) {
            this.a.i = z;
            return this;
        }

        C0041a f(boolean z) {
            this.a.j = z;
            return this;
        }
    }

    private a() {
        this.a = "rcs.cmpassport.com";
        this.b = "rcs.cmpassport.com";
        this.c = "config2.cmpassport.com";
        this.d = "log2.cmpassport.com:9443";
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 3;
        this.l = 1;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }

    public boolean f() {
        return this.f;
    }

    public boolean g() {
        return this.g;
    }

    public boolean h() {
        return this.h;
    }

    public boolean i() {
        return this.i;
    }

    public boolean j() {
        return this.j;
    }

    public int k() {
        return this.k;
    }

    public int l() {
        return this.l;
    }

    /* JADX INFO: renamed from: m, reason: merged with bridge method [inline-methods] */
    public a clone() throws CloneNotSupportedException {
        return (a) super.clone();
    }
}
