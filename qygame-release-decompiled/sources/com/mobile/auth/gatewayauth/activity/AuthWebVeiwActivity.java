package com.mobile.auth.gatewayauth.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.igexin.push.core.b;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.d;
import com.mobile.auth.gatewayauth.utils.k;
import com.mobile.auth.gatewayauth.utils.l;
import com.mobile.auth.o.a;
import com.nirvana.tools.core.AppUtils;

/* JADX INFO: loaded from: classes.dex */
public class AuthWebVeiwActivity extends Activity {
    private WebView a;
    private String b;
    private String c;
    private ProgressBar d;
    private TextView e;
    private RelativeLayout f;
    private AuthUIConfig g;
    private ImageButton h;

    private int a(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    static /* synthetic */ ProgressBar a(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.d;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ TextView b(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.e;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ String c(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ AuthUIConfig d(AuthWebVeiwActivity authWebVeiwActivity) {
        try {
            return authWebVeiwActivity.g;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        AuthUIConfig authUIConfigR;
        AuthUIConfig authUIConfig;
        TextView textView;
        int navTextSize;
        try {
            this.b = getIntent().getStringExtra(Constant.PROTOCOL_WEB_VIEW_URL);
            this.c = getIntent().getStringExtra("name");
            int intExtra = getIntent().getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, 0);
            setRequestedOrientation(getIntent().getIntExtra(Constant.PROTOCOL_WEB_VIEW_ORIENTATION, 1));
            super.onCreate(bundle);
            d dVarA = d.a(intExtra);
            if (dVarA == null) {
                a.a(getApplicationContext()).e("UIManager is null!|ID:", String.valueOf(intExtra));
                authUIConfigR = d.a;
            } else {
                authUIConfigR = dVarA.r();
            }
            this.g = authUIConfigR;
            if (dVarA.f() && Build.VERSION.SDK_INT >= 28) {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.layoutInDisplayCutoutMode = 1;
                getWindow().setAttributes(attributes);
            }
            setContentView(AppUtils.getResID(this, "authsdk_dialog_layout", "layout"));
            int webViewStatusBarColor = this.g.getWebViewStatusBarColor();
            if (AuthUIConfig.DEFAULT_WEB_STATUS_BAR_COLOR == webViewStatusBarColor) {
                webViewStatusBarColor = this.g.getWebNavColor();
            }
            d.a(this.g, webViewStatusBarColor, this);
            this.e = (TextView) findViewById(AppUtils.getResID(this, "authsdk_title_tv", b.C));
            this.f = (RelativeLayout) findViewById(AppUtils.getResID(this, "authsdk_title_rl", b.C));
            if (l.a(this.g.getBottomNavBarColor())) {
                this.f.setY(a((Context) this));
            }
            this.h = (ImageButton) findViewById(AppUtils.getResID(this, "authsdk_back_btn", b.C));
            this.f.setBackgroundColor(this.g.getWebNavColor());
            this.e.setTextColor(this.g.getWebNavTextColor());
            if (this.g.getWebNavTextSize() != -1) {
                authUIConfig = this.g;
                textView = this.e;
                navTextSize = this.g.getWebNavTextSize();
            } else {
                authUIConfig = this.g;
                textView = this.e;
                navTextSize = this.g.getNavTextSize();
            }
            authUIConfig.setTextSize(textView, navTextSize);
            this.h.setBackgroundColor(0);
            this.h.setScaleType(this.g.getNavReturnScaleType());
            this.h.setPadding(0, 0, 0, 0);
            Drawable webNavReturnImgDrawable = this.g.getWebNavReturnImgDrawable();
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = k.c(this, this.g.getWebNavReturnImgPath());
            }
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = this.g.getNavReturnImgDrawable();
            }
            if (webNavReturnImgDrawable == null) {
                webNavReturnImgDrawable = k.a(this, this.g.getNavReturnImgPath(), "authsdk_return_bg");
            }
            this.h.setImageDrawable(webNavReturnImgDrawable);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.h.getLayoutParams();
            layoutParams.width = AppUtils.dp2px(this, this.g.getNavReturnImgWidth());
            layoutParams.height = AppUtils.dp2px(this, this.g.getNavReturnImgHeight());
            this.h.setOnClickListener(new View.OnClickListener() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    try {
                        AuthWebVeiwActivity.this.finish();
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
            if (dVarA.b()) {
                getWindow().getDecorView().setSystemUiVisibility(3074);
                if (!this.g.isStatusBarHidden()) {
                    this.f.setY(a((Context) this));
                }
            }
            this.d = (ProgressBar) findViewById(AppUtils.getResID(this, "authsdk_progressBar", b.C));
            if (this.g.isWebHiddeProgress()) {
                this.d.setVisibility(8);
            } else {
                this.d.setVisibility(0);
            }
            this.a = (WebView) findViewById(AppUtils.getResID(this, "authsdk_webview", b.C));
            if (l.a(this.g.getBottomNavBarColor())) {
                this.a.setY(this.f.getHeight() + this.f.getY());
            }
            if (dVarA.b() && !this.g.isStatusBarHidden()) {
                this.a.setY(this.f.getHeight() + a((Context) this));
            }
            this.a.setWebChromeClient(new WebChromeClient() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.2
                @Override // android.webkit.WebChromeClient
                public void onProgressChanged(WebView webView, int i) {
                    TextView textViewB;
                    try {
                        if (i != 100) {
                            if (AuthWebVeiwActivity.d(AuthWebVeiwActivity.this).isWebHiddeProgress()) {
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(8);
                                return;
                            } else {
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(0);
                                AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setProgress(i);
                                return;
                            }
                        }
                        AuthWebVeiwActivity.a(AuthWebVeiwActivity.this).setVisibility(8);
                        String title = webView.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            textViewB = AuthWebVeiwActivity.b(AuthWebVeiwActivity.this);
                        } else if (!TextUtils.isEmpty(AuthWebVeiwActivity.c(AuthWebVeiwActivity.this))) {
                            AuthWebVeiwActivity.b(AuthWebVeiwActivity.this).setText(AuthWebVeiwActivity.c(AuthWebVeiwActivity.this));
                            return;
                        } else {
                            textViewB = AuthWebVeiwActivity.b(AuthWebVeiwActivity.this);
                            title = "服务协议";
                        }
                        textViewB.setText(title);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
            this.a.setWebViewClient(new WebViewClient() { // from class: com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity.3
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    return false;
                }
            });
            this.a.setVerticalScrollBarEnabled(false);
            this.a.setHorizontalScrollBarEnabled(false);
            WebSettings settings = this.a.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setDomStorageEnabled(true);
            settings.setSavePassword(false);
            settings.setAllowFileAccess(false);
            settings.setJavaScriptEnabled(this.g.isWebSupportedJavascript());
            this.a.setVerticalScrollbarOverlay(false);
            settings.setUseWideViewPort(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setSupportZoom(true);
            settings.setCacheMode(this.g.getWebCacheMode());
            this.a.loadUrl(this.b);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            if (this.a != null) {
                this.a.removeAllViews();
                this.a.destroy();
                this.a = null;
            }
            super.onDestroy();
            this.g = null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
