package com.alipay.sdk.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.util.l;

/* JADX INFO: loaded from: classes.dex */
public final class b extends WebViewClient {
    Activity a;
    Handler b;
    boolean c;
    private boolean d;
    private com.alipay.sdk.widget.a e;
    private Runnable f = new f(this);

    public b(Activity activity) {
        this.a = activity;
        this.b = new Handler(this.a.getMainLooper());
    }

    private void a() {
        if (this.e == null) {
            this.e = new com.alipay.sdk.widget.a(this.a, com.alipay.sdk.widget.a.a);
            this.e.e = true;
        }
        this.e.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.e != null) {
            this.e.b();
        }
        this.e = null;
    }

    private void c() {
        this.b = null;
        this.a = null;
    }

    private boolean d() {
        return this.c;
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        if (this.b != null) {
            b();
            this.b.removeCallbacks(this.f);
        }
    }

    @Override // android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (this.b != null) {
            if (this.e == null) {
                this.e = new com.alipay.sdk.widget.a(this.a, com.alipay.sdk.widget.a.a);
                this.e.e = true;
            }
            this.e.a();
            this.b.postDelayed(this.f, com.igexin.push.config.c.k);
        }
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedError(WebView webView, int i, String str, String str2) {
        this.c = true;
        super.onReceivedError(webView, i, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.a, com.alipay.sdk.app.statistic.c.q, "证书错误");
        if (!this.d) {
            this.a.runOnUiThread(new c(this, sslErrorHandler));
        } else {
            sslErrorHandler.proceed();
            this.d = false;
        }
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return l.a(webView, str, this.a);
    }
}
