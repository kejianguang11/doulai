package com.cmic.sso.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
public class h extends Dialog {
    private WebView a;
    private String b;
    private String c;
    private LinearLayout d;

    public h(Context context, int i, String str, String str2) {
        super(context, i);
        try {
            this.c = str;
            this.b = str2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ViewGroup c() {
        View viewFindViewById;
        try {
            this.d = new LinearLayout(getContext());
            this.d.setOrientation(1);
            this.d.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            a aVarA = com.mobile.auth.e.a.a(getContext()).a();
            int iE = aVarA.e();
            String str = TextUtils.isEmpty(this.c) ? com.cmic.sso.sdk.c.d[aVarA.ap()] : this.c;
            if (iE != -1) {
                RelativeLayout relativeLayoutA = i.a(getContext(), getLayoutInflater().inflate(iE, (ViewGroup) this.d, false), 1118481, 0, str, (View.OnClickListener) null);
                String strF = aVarA.f();
                if (!TextUtils.isEmpty(strF) && (viewFindViewById = relativeLayoutA.findViewById(g.a(getContext(), strF))) != null) {
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.cmic.sso.sdk.view.h.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            h.this.a.stopLoading();
                            h.this.b();
                        }
                    });
                }
                this.d.addView(relativeLayoutA);
            } else {
                this.d.addView(i.a(getContext(), (View) null, 1118481, 2236962, str, new View.OnClickListener() { // from class: com.cmic.sso.sdk.view.h.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        h.this.a.stopLoading();
                        h.this.b();
                    }
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.d;
    }

    private void d() {
        this.a = new WebView(getContext());
        WebSettings settings = this.a.getSettings();
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        this.d.addView(this.a, new LinearLayout.LayoutParams(-1, -1));
        if (Build.VERSION.SDK_INT < 17) {
            this.a.removeJavascriptInterface("searchBoxJavaBridge_");
            this.a.removeJavascriptInterface("accessibility");
            this.a.removeJavascriptInterface("accessibilityTraversal");
        }
        this.a.setWebViewClient(new WebViewClient() { // from class: com.cmic.sso.sdk.view.h.3
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                h.this.a.loadUrl(str);
                return true;
            }
        });
        this.a.loadUrl(this.b);
    }

    protected void a() {
        View decorView;
        requestWindowFeature(1);
        int i = 0;
        getWindow().setFeatureDrawableAlpha(0, 0);
        a aVarA = com.mobile.auth.e.a.a(getContext()).a();
        if (Build.VERSION.SDK_INT >= 21 && aVarA.a() != 0) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(aVarA.a());
            getWindow().setNavigationBarColor(aVarA.a());
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (aVarA.b()) {
                decorView = getWindow().getDecorView();
                i = 8192;
            } else {
                decorView = getWindow().getDecorView();
            }
            decorView.setSystemUiVisibility(i);
        }
        setContentView(c());
    }

    public void b() {
        if (this.a.canGoBack()) {
            this.a.goBack();
        } else {
            dismiss();
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        if (this.a != null) {
            this.a.stopLoading();
        }
    }

    @Override // android.app.Dialog
    public void show() {
        if (this.d == null) {
            a();
        }
        if (this.a == null) {
            d();
        }
        super.show();
    }
}
