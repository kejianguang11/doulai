package com.alipay.sdk.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.util.l;
import com.mobile.auth.gatewayauth.Constant;

/* JADX INFO: loaded from: classes.dex */
public class H5PayActivity extends Activity {
    private WebView a;
    private WebViewClient b;

    private void b() {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
    }

    public void a() {
        Object obj = PayTask.a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        a();
        super.finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        String strA;
        if (!this.a.canGoBack()) {
            strA = i.a();
        } else {
            if (!((b) this.b).c) {
                return;
            }
            j jVarA = j.a(j.NETWORK_ERROR.h);
            strA = i.a(jVarA.h, jVarA.i, "");
        }
        i.a = strA;
        finish();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            String string = extras.getString(Constant.PROTOCOL_WEB_VIEW_URL);
            if (!l.b(string)) {
                finish();
                return;
            }
            try {
                this.a = l.a(this, string, extras.getString("cookie"));
                this.b = new b(this);
                this.a.setWebViewClient(this.b);
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, "GetInstalledAppEx", th);
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.a != null) {
            this.a.removeAllViews();
            ((ViewGroup) this.a.getParent()).removeAllViews();
            try {
                this.a.destroy();
            } catch (Throwable unused) {
            }
            this.a = null;
        }
        if (this.b != null) {
            b bVar = (b) this.b;
            bVar.b = null;
            bVar.a = null;
        }
    }
}
