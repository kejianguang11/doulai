package com.ta.utdid2.device;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private long a = 0;
    private long b = 0;

    long a() {
        return this.a;
    }

    void a(long j) {
        this.b = j;
    }

    void a(String str) {
        this.c = str;
    }

    void b(long j) {
        this.a = j;
    }

    void b(String str) {
        this.d = str;
    }

    void c(String str) {
        this.e = str;
    }

    void d(String str) {
        this.f = str;
    }

    public String getDeviceId() {
        return this.e;
    }

    public String getImei() {
        return this.c;
    }

    public String getImsi() {
        return this.d;
    }

    public String getUtdid() {
        return this.f;
    }
}
