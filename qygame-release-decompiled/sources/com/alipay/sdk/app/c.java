package com.alipay.sdk.app;

import android.webkit.SslErrorHandler;

/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ SslErrorHandler a;
    final /* synthetic */ b b;

    c(b bVar, SslErrorHandler sslErrorHandler) {
        this.b = bVar;
        this.a = sslErrorHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.widget.e.a(this.b.a, "安全警告", "安全连接证书校验无效，将无法保证访问数据的安全性，可能存在风险，请选择是否继续？", "继续", new d(this), "退出", new e(this));
    }
}
