package com.alipay.sdk.auth;

import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
final class g implements DialogInterface.OnClickListener {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.a.cancel();
        AuthActivity.this.g = false;
        h.a(AuthActivity.this, AuthActivity.this.d + "?resultCode=150");
        AuthActivity.this.finish();
    }
}
