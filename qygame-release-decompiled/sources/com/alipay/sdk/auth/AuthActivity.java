package com.alipay.sdk.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.alipay.sdk.authjs.a;
import com.alipay.sdk.util.l;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
public class AuthActivity extends Activity {
    private WebView c;
    private String d;
    private com.alipay.sdk.widget.a e;
    private Handler f;
    private boolean g;
    private boolean h;
    private Runnable i = new d(this);

    private class a extends WebChromeClient {
        private a() {
        }

        /* synthetic */ a(AuthActivity authActivity, byte b) {
            this();
        }

        @Override // android.webkit.WebChromeClient
        public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String strMessage = consoleMessage.message();
            if (TextUtils.isEmpty(strMessage)) {
                return super.onConsoleMessage(consoleMessage);
            }
            String strReplaceFirst = strMessage.startsWith("h5container.message: ") ? strMessage.replaceFirst("h5container.message: ", "") : null;
            if (TextUtils.isEmpty(strReplaceFirst)) {
                return super.onConsoleMessage(consoleMessage);
            }
            AuthActivity.b(AuthActivity.this, strReplaceFirst);
            return super.onConsoleMessage(consoleMessage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b extends WebViewClient {
        private b() {
        }

        /* synthetic */ b(AuthActivity authActivity, byte b) {
            this();
        }

        @Override // android.webkit.WebViewClient
        public final void onPageFinished(WebView webView, String str) {
            AuthActivity.g(AuthActivity.this);
            AuthActivity.this.f.removeCallbacks(AuthActivity.this.i);
        }

        @Override // android.webkit.WebViewClient
        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            AuthActivity.d(AuthActivity.this);
            AuthActivity.this.f.postDelayed(AuthActivity.this.i, com.igexin.push.config.c.k);
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            AuthActivity.a(AuthActivity.this);
            super.onReceivedError(webView, i, str, str2);
        }

        @Override // android.webkit.WebViewClient
        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (!AuthActivity.this.g) {
                AuthActivity.this.runOnUiThread(new e(this, sslErrorHandler));
            } else {
                sslErrorHandler.proceed();
                AuthActivity.this.g = false;
            }
        }

        @Override // android.webkit.WebViewClient
        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!str.toLowerCase().startsWith(com.alipay.sdk.cons.a.i.toLowerCase()) && !str.toLowerCase().startsWith(com.alipay.sdk.cons.a.j.toLowerCase())) {
                if (!AuthActivity.a(AuthActivity.this, str)) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                webView.stopLoading();
                return true;
            }
            try {
                l.a aVarA = l.a(AuthActivity.this);
                if (aVarA != null && !aVarA.a()) {
                    if (str.startsWith("intent://platformapi/startapp")) {
                        str = str.replaceFirst(com.alipay.sdk.cons.a.j, com.alipay.sdk.cons.a.i);
                    }
                    AuthActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            } catch (Throwable unused) {
            }
            return true;
        }
    }

    private void a() {
        try {
            if (this.e == null) {
                this.e = new com.alipay.sdk.widget.a(this, com.alipay.sdk.widget.a.a);
            }
            this.e.a();
        } catch (Exception unused) {
            this.e = null;
        }
    }

    static /* synthetic */ void a(AuthActivity authActivity, com.alipay.sdk.authjs.a aVar) {
        if (authActivity.c == null || aVar == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.authjs.a.e, aVar.i);
            jSONObject.put(com.alipay.sdk.authjs.a.g, aVar.k);
            jSONObject.put(com.alipay.sdk.authjs.a.f, aVar.m);
            jSONObject.put(com.alipay.sdk.authjs.a.h, aVar.l);
            authActivity.runOnUiThread(new c(authActivity, String.format("AlipayJSBridge._invokeJS(%s)", jSONObject.toString())));
        } catch (JSONException unused) {
        }
    }

    private void a(com.alipay.sdk.authjs.a aVar) {
        if (this.c == null || aVar == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.alipay.sdk.authjs.a.e, aVar.i);
            jSONObject.put(com.alipay.sdk.authjs.a.g, aVar.k);
            jSONObject.put(com.alipay.sdk.authjs.a.f, aVar.m);
            jSONObject.put(com.alipay.sdk.authjs.a.h, aVar.l);
            runOnUiThread(new c(this, String.format("AlipayJSBridge._invokeJS(%s)", jSONObject.toString())));
        } catch (JSONException unused) {
        }
    }

    static /* synthetic */ boolean a(AuthActivity authActivity) {
        authActivity.h = true;
        return true;
    }

    static /* synthetic */ boolean a(AuthActivity authActivity, String str) {
        if (TextUtils.isEmpty(str) || str.startsWith("http://") || str.startsWith("https://")) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, authActivity.d)) {
                str = str + "?resultCode=150";
            }
            h.a(authActivity, str);
        }
        authActivity.finish();
        return true;
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || str.startsWith("http://") || str.startsWith("https://")) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, this.d)) {
                str = str + "?resultCode=150";
            }
            h.a(this, str);
        }
        finish();
        return true;
    }

    private void b() {
        if (this.e != null) {
            this.e.b();
        }
        this.e = null;
    }

    static /* synthetic */ void b(AuthActivity authActivity, String str) {
        JSONObject jSONObject;
        String string;
        com.alipay.sdk.authjs.d dVar = new com.alipay.sdk.authjs.d(authActivity.getApplicationContext(), new com.alipay.sdk.auth.b(authActivity));
        String str2 = null;
        try {
            jSONObject = new JSONObject(str);
            string = jSONObject.getString(com.alipay.sdk.authjs.a.e);
        } catch (Exception unused) {
        }
        try {
            if (TextUtils.isEmpty(string)) {
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject(com.alipay.sdk.authjs.a.f);
            JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
            String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.g);
            String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.d);
            com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
            aVar.j = string3;
            aVar.k = string2;
            aVar.m = jSONObject3;
            aVar.i = string;
            dVar.a(aVar);
        } catch (Exception unused2) {
            str2 = string;
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            try {
                dVar.a(str2, a.EnumC0002a.d);
            } catch (JSONException unused3) {
            }
        }
    }

    private void b(String str) {
        JSONObject jSONObject;
        String string;
        com.alipay.sdk.authjs.d dVar = new com.alipay.sdk.authjs.d(getApplicationContext(), new com.alipay.sdk.auth.b(this));
        String str2 = null;
        try {
            jSONObject = new JSONObject(str);
            string = jSONObject.getString(com.alipay.sdk.authjs.a.e);
        } catch (Exception unused) {
        }
        try {
            if (TextUtils.isEmpty(string)) {
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject(com.alipay.sdk.authjs.a.f);
            JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
            String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.g);
            String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.d);
            com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
            aVar.j = string3;
            aVar.k = string2;
            aVar.m = jSONObject3;
            aVar.i = string;
            dVar.a(aVar);
        } catch (Exception unused2) {
            str2 = string;
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            try {
                dVar.a(str2, a.EnumC0002a.d);
            } catch (JSONException unused3) {
            }
        }
    }

    static /* synthetic */ void d(AuthActivity authActivity) {
        try {
            if (authActivity.e == null) {
                authActivity.e = new com.alipay.sdk.widget.a(authActivity, com.alipay.sdk.widget.a.a);
            }
            authActivity.e.a();
        } catch (Exception unused) {
            authActivity.e = null;
        }
    }

    static /* synthetic */ void g(AuthActivity authActivity) {
        if (authActivity.e != null) {
            authActivity.e.b();
        }
        authActivity.e = null;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!this.c.canGoBack() || this.h) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append("?resultCode=150");
            h.a(this, sb.toString());
            finish();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                finish();
                return;
            }
            try {
                this.d = extras.getString("redirectUri");
                String string = extras.getString("params");
                if (!l.b(string)) {
                    finish();
                    return;
                }
                super.requestWindowFeature(1);
                this.f = new Handler(getMainLooper());
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                linearLayout.setOrientation(1);
                setContentView(linearLayout, layoutParams);
                this.c = new WebView(this);
                layoutParams.weight = 1.0f;
                byte b2 = 0;
                this.c.setVisibility(0);
                linearLayout.addView(this.c, layoutParams);
                WebSettings settings = this.c.getSettings();
                settings.setUserAgentString(settings.getUserAgentString() + l.f(getApplicationContext()));
                settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
                settings.setSupportMultipleWindows(true);
                settings.setJavaScriptEnabled(true);
                settings.setSavePassword(false);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
                settings.setAllowFileAccess(false);
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                this.c.setVerticalScrollbarOverlay(true);
                this.c.setWebViewClient(new b(this, b2));
                this.c.setWebChromeClient(new a(this, b2));
                this.c.setDownloadListener(new com.alipay.sdk.auth.a(this));
                this.c.loadUrl(string);
                if (Build.VERSION.SDK_INT >= 7) {
                    try {
                        Method method = this.c.getSettings().getClass().getMethod("setDomStorageEnabled", Boolean.TYPE);
                        if (method != null) {
                            method.invoke(this.c.getSettings(), true);
                        }
                    } catch (Exception unused) {
                    }
                }
                try {
                    try {
                        this.c.removeJavascriptInterface("searchBoxJavaBridge_");
                        this.c.removeJavascriptInterface("accessibility");
                        this.c.removeJavascriptInterface("accessibilityTraversal");
                    } catch (Throwable unused2) {
                        Method method2 = this.c.getClass().getMethod("removeJavascriptInterface", new Class[0]);
                        if (method2 != null) {
                            method2.invoke(this.c, "searchBoxJavaBridge_");
                            method2.invoke(this.c, "accessibility");
                            method2.invoke(this.c, "accessibilityTraversal");
                        }
                    }
                } catch (Throwable unused3) {
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    this.c.getSettings().setCacheMode(1);
                }
            } catch (Exception unused4) {
                finish();
            }
        } catch (Exception unused5) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.removeAllViews();
            try {
                this.c.destroy();
            } catch (Throwable unused) {
            }
            this.c = null;
        }
    }
}
