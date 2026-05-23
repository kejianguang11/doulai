package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ff extends bh {
    Map<String, String> h;
    String i;
    String j;
    byte[] k;
    byte[] l;
    boolean m;
    String n;
    Map<String, String> o;
    boolean p;
    private String q;

    public ff(Context context, w wVar) {
        super(context, wVar);
        this.h = null;
        this.q = "";
        this.i = "";
        this.j = "";
        this.k = null;
        this.l = null;
        this.m = false;
        this.n = null;
        this.o = null;
        this.p = false;
    }

    @Override // com.loc.bl
    public final Map<String, String> a() {
        return this.h;
    }

    public final void a(Map<String, String> map) {
        this.o = map;
    }

    @Override // com.loc.bh
    public final byte[] a_() {
        return this.k;
    }

    @Override // com.loc.bl
    public final String b() {
        return this.i;
    }

    public final void b(String str) {
        this.n = str;
    }

    public final void b(Map<String, String> map) {
        this.h = map;
    }

    public final void b(boolean z) {
        this.m = z;
    }

    public final void b(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bArr != null) {
                    try {
                        byteArrayOutputStream.write(a(bArr));
                        byteArrayOutputStream.write(bArr);
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream2 = byteArrayOutputStream;
                        th.printStackTrace();
                        if (byteArrayOutputStream2 != null) {
                            try {
                                byteArrayOutputStream2.close();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                }
                this.l = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = byteArrayOutputStream2;
        }
    }

    @Override // com.loc.bh
    public final byte[] b_() {
        return this.l;
    }

    @Override // com.loc.r, com.loc.bl
    public final String c() {
        return this.j;
    }

    public final void c(String str) {
        this.i = str;
    }

    public final void c(boolean z) {
        this.p = z;
    }

    public final void c(byte[] bArr) {
        this.k = bArr;
    }

    @Override // com.loc.bh, com.loc.bl
    public final Map<String, String> d() {
        return this.o;
    }

    public final void d(String str) {
        this.j = str;
    }

    public final void e(String str) {
        if (TextUtils.isEmpty(str)) {
            this.q = "";
        } else {
            this.q = str;
        }
    }

    @Override // com.loc.bl
    public final String g() {
        return this.q;
    }

    @Override // com.loc.bl
    public final String h() {
        return "loc";
    }

    @Override // com.loc.bh
    public final boolean i() {
        return this.m;
    }

    @Override // com.loc.bh
    public final String j() {
        return this.n;
    }

    @Override // com.loc.bh
    protected final boolean k() {
        return this.p;
    }
}
