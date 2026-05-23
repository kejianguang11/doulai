package com.zx.a.I8b7;

import com.zx.sdk.api.SAIDCallback;

/* JADX INFO: loaded from: classes.dex */
public class c3 implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String e;
    public final /* synthetic */ String f;
    public final /* synthetic */ SAIDCallback g;

    public c3(b3 b3Var, String str, String str2, String str3, String str4, String str5, String str6, SAIDCallback sAIDCallback) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = sAIDCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b3.a().a(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        } catch (Throwable th) {
            SAIDCallback sAIDCallback = this.g;
            if (sAIDCallback != null) {
                sAIDCallback.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
            k3.a(th, j3.a("ZXManager.getSAID() failed: "));
        }
    }
}
