package com.mobile.auth.j;

import android.net.Network;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.i.g;
import com.mobile.auth.l.e;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class c {
    String a;
    private final String b;
    private final Map<String, String> c;
    private final String d;
    private boolean e;
    private final String f;
    private Network g;
    private long h;
    private final String i;
    private int j;
    private final g k;

    public c(String str, g gVar, String str2, String str3) {
        this(str, null, gVar, str2, str3);
    }

    private c(String str, Map<String, String> map, g gVar, String str2, String str3) {
        this.e = false;
        this.b = str;
        this.k = gVar;
        this.c = map == null ? new HashMap<>() : map;
        this.a = gVar == null ? "" : gVar.b().toString();
        this.d = str2;
        this.f = str3;
        this.i = gVar == null ? "" : gVar.a();
        l();
    }

    private void l() {
        this.c.put("sdkVersion", BuildConfig.CMCC_SDK_VERSION);
        this.c.put("Content-Type", "application/json");
        this.c.put("CMCC-EncryptType", "STD");
        this.c.put("traceId", this.f);
        this.c.put("appid", this.i);
        this.c.put("Connection", "close");
    }

    public String a() {
        return this.b;
    }

    void a(long j) {
        this.h = j;
    }

    public void a(Network network) {
        this.g = network;
    }

    public void a(String str, String str2) {
        this.c.put(str, str2);
    }

    void a(boolean z) {
        this.e = z;
    }

    public boolean b() {
        return this.e;
    }

    public Map<String, String> c() {
        return this.c;
    }

    public String d() {
        return this.a;
    }

    public String e() {
        return this.d;
    }

    public String f() {
        return this.f;
    }

    public boolean g() {
        return !e.a(this.f) || this.b.contains("logReport") || this.b.contains("uniConfig");
    }

    public Network h() {
        return this.g;
    }

    long i() {
        return this.h;
    }

    public boolean j() {
        int i = this.j;
        this.j = i + 1;
        return i < 2;
    }

    public g k() {
        return this.k;
    }
}
