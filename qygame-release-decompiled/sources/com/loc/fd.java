package com.loc;

import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class fd extends r {
    Map<String, String> a = null;
    Map<String, String> b = null;
    String c = "";
    byte[] h = null;
    private String i = null;

    @Override // com.loc.bl
    public final Map<String, String> a() {
        return this.a;
    }

    public final void a(Map<String, String> map) {
        this.a = map;
    }

    public final void a(byte[] bArr) {
        this.h = bArr;
    }

    @Override // com.loc.bl
    public final String b() {
        return this.c;
    }

    public final void b(String str) {
        this.c = str;
    }

    public final void b(Map<String, String> map) {
        this.b = map;
    }

    @Override // com.loc.r, com.loc.bl
    public final String c() {
        return !TextUtils.isEmpty(this.i) ? this.i : super.c();
    }

    public final void c(String str) {
        this.i = str;
    }

    @Override // com.loc.bl
    public final Map<String, String> d() {
        return this.b;
    }

    @Override // com.loc.bl
    public final byte[] e() {
        return this.h;
    }
}
