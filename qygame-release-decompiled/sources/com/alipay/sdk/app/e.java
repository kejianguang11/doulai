package com.alipay.sdk.app;

import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
final class e implements DialogInterface.OnClickListener {
    final /* synthetic */ c a;

    e(c cVar) {
        this.a = cVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.a.cancel();
        this.a.b.d = false;
        i.a = i.a();
        this.a.b.a.finish();
    }
}
