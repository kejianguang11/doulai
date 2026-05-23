package com.alipay.sdk.auth;

/* JADX INFO: loaded from: classes.dex */
final class d implements Runnable {
    final /* synthetic */ AuthActivity a;

    d(AuthActivity authActivity) {
        this.a = authActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AuthActivity.g(this.a);
    }
}
