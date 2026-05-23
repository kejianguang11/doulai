package org.cocos2dx.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxWebView extends WebView {
    private static final String TAG = Cocos2dxWebViewHelper.class.getSimpleName();
    private String mJSScheme;
    private int mViewTag;

    class Cocos2dxWebViewClient extends WebViewClient {
        Cocos2dxWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, final String str) {
            super.onPageFinished(webView, str);
            ((Cocos2dxActivity) Cocos2dxWebView.this.getContext()).runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebView.Cocos2dxWebViewClient.2
                @Override // java.lang.Runnable
                public void run() {
                    Cocos2dxWebViewHelper._didFinishLoading(Cocos2dxWebView.this.mViewTag, str);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, final String str2) {
            super.onReceivedError(webView, i, str, str2);
            ((Cocos2dxActivity) Cocos2dxWebView.this.getContext()).runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebView.Cocos2dxWebViewClient.3
                @Override // java.lang.Runnable
                public void run() {
                    Cocos2dxWebViewHelper._didFailLoading(Cocos2dxWebView.this.mViewTag, str2);
                }
            });
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, final String str) {
            Cocos2dxActivity cocos2dxActivity = (Cocos2dxActivity) Cocos2dxWebView.this.getContext();
            try {
                URI uriCreate = URI.create(str);
                if (uriCreate != null && uriCreate.getScheme().equals(Cocos2dxWebView.this.mJSScheme)) {
                    cocos2dxActivity.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebView.Cocos2dxWebViewClient.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Cocos2dxWebViewHelper._onJsCallback(Cocos2dxWebView.this.mViewTag, str);
                        }
                    });
                    return true;
                }
            } catch (Exception unused) {
                Log.d(Cocos2dxWebView.TAG, "Failed to create URI from url");
            }
            boolean[] zArr = {true};
            CountDownLatch countDownLatch = new CountDownLatch(1);
            cocos2dxActivity.runOnGLThread(new ShouldStartLoadingWorker(countDownLatch, zArr, Cocos2dxWebView.this.mViewTag, str));
            try {
                countDownLatch.await();
            } catch (InterruptedException unused2) {
                Log.d(Cocos2dxWebView.TAG, "'shouldOverrideUrlLoading' failed");
            }
            return zArr[0];
        }
    }

    public Cocos2dxWebView(Context context) {
        this(context, -1);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public Cocos2dxWebView(Context context, int i) {
        super(context);
        this.mViewTag = i;
        this.mJSScheme = "";
        setFocusable(true);
        setFocusableInTouchMode(true);
        getSettings().setSupportZoom(false);
        getSettings().setDomStorageEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        try {
            getClass().getMethod("removeJavascriptInterface", String.class).invoke(this, "searchBoxJavaBridge_");
        } catch (Exception unused) {
            Log.d(TAG, "This API level do not support `removeJavascriptInterface`");
        }
        setWebViewClient(new Cocos2dxWebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    public void setJavascriptInterfaceScheme(String str) {
        if (str == null) {
            str = "";
        }
        this.mJSScheme = str;
    }

    public void setScalesPageToFit(boolean z) {
        getSettings().setSupportZoom(z);
    }

    public void setWebViewRect(int i, int i2, int i3, int i4) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.leftMargin = i;
        layoutParams.topMargin = i2;
        layoutParams.width = i3;
        layoutParams.height = i4;
        setLayoutParams(layoutParams);
    }
}
