package com.loc;

import android.text.TextUtils;
import com.mobile.auth.BuildConfig;

/* JADX INFO: loaded from: classes.dex */
@as(a = "a")
public final class w {

    @at(a = "a1", b = 6)
    private String a;

    @at(a = "a2", b = 6)
    private String b;

    @at(a = "a6", b = 2)
    private int c;

    @at(a = "a3", b = 6)
    private String d;

    @at(a = "a4", b = 6)
    private String e;

    @at(a = "a5", b = 6)
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String[] l;

    public static class a {
        private String a;
        private String b;
        private String c;
        private String d;
        private boolean e = true;
        private String f = BuildConfig.FLAVOR_feat;
        private String[] g = null;

        public a(String str, String str2, String str3) {
            this.a = str2;
            this.b = str2;
            this.d = str3;
            this.c = str;
        }

        public final a a(String str) {
            this.b = str;
            return this;
        }

        public final a a(String[] strArr) {
            if (strArr != null) {
                this.g = (String[]) strArr.clone();
            }
            return this;
        }

        public final w a() throws k {
            if (this.g != null) {
                return new w(this, (byte) 0);
            }
            throw new k("sdk packages is null");
        }
    }

    private w() {
        this.c = 1;
        this.l = null;
    }

    private w(a aVar) {
        this.c = 1;
        this.l = null;
        this.g = aVar.a;
        this.h = aVar.b;
        this.j = aVar.c;
        this.i = aVar.d;
        this.c = aVar.e ? 1 : 0;
        this.k = aVar.f;
        this.l = aVar.g;
        this.b = x.b(this.h);
        this.a = x.b(this.j);
        this.d = x.b(this.i);
        this.e = x.b(a(this.l));
        this.f = x.b(this.k);
    }

    /* synthetic */ w(a aVar, byte b) {
        this(aVar);
    }

    private static String a(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                sb.append(str);
                sb.append(com.alipay.sdk.util.h.b);
            }
            return sb.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static String[] a(String str) {
        try {
            return str.split(com.alipay.sdk.util.h.b);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final String a() {
        if (TextUtils.isEmpty(this.j) && !TextUtils.isEmpty(this.a)) {
            this.j = x.c(this.a);
        }
        return this.j;
    }

    public final void a(boolean z) {
        this.c = z ? 1 : 0;
    }

    public final String b() {
        return this.g;
    }

    public final String c() {
        if (TextUtils.isEmpty(this.h) && !TextUtils.isEmpty(this.b)) {
            this.h = x.c(this.b);
        }
        return this.h;
    }

    public final String d() {
        if (TextUtils.isEmpty(this.k) && !TextUtils.isEmpty(this.f)) {
            this.k = x.c(this.f);
        }
        if (TextUtils.isEmpty(this.k)) {
            this.k = BuildConfig.FLAVOR_feat;
        }
        return this.k;
    }

    public final boolean e() {
        return this.c == 1;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        try {
            if (this.j.equals(((w) obj).j) && this.g.equals(((w) obj).g)) {
                if (this.h.equals(((w) obj).h)) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public final String[] f() {
        if ((this.l == null || this.l.length == 0) && !TextUtils.isEmpty(this.e)) {
            this.l = a(x.c(this.e));
        }
        return (String[]) this.l.clone();
    }
}
