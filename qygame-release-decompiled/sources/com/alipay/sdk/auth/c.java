package com.alipay.sdk.auth;

/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ AuthActivity b;

    c(AuthActivity authActivity, String str) {
        this.b = authActivity;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.b.c.loadUrl("javascript:" + this.a);
        } catch (Exception unused) {
        }
    }
}
