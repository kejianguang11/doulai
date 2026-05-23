package com.mobile.auth.d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.d.b;
import com.mobile.auth.l.k;

/* JADX INFO: loaded from: classes.dex */
public class c implements b.a {

    @SuppressLint({"StaticFieldLeak"})
    private static c a;
    private a b;
    private a c;
    private b d;
    private Context e;

    private c(Context context) {
        this.e = context;
        b();
    }

    public static c a(Context context) {
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c(context);
                }
            }
        }
        return a;
    }

    private void b() {
        String strB = k.b("sdk_config_version", "");
        if (TextUtils.isEmpty(strB) || !BuildConfig.CMCC_SDK_VERSION.equals(strB)) {
            this.d = b.a(true);
            this.b = this.d.a();
            if (!TextUtils.isEmpty(strB)) {
                c();
            }
        } else {
            this.d = b.a(false);
            this.b = this.d.b();
        }
        this.d.a(this);
        this.c = this.d.a();
    }

    private void c() {
        com.mobile.auth.l.c.b("UmcConfigManager", "delete localConfig");
        this.d.c();
    }

    public a a() {
        try {
            return this.b.clone();
        } catch (CloneNotSupportedException unused) {
            return this.c;
        }
    }

    public void a(com.cmic.sso.sdk.a aVar) {
        this.d.a(aVar);
    }

    @Override // com.mobile.auth.d.b.a
    public void a(a aVar) {
        this.b = aVar;
    }
}
