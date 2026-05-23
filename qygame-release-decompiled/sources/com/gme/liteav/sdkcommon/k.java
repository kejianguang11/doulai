package com.gme.liteav.sdkcommon;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class k implements View.OnClickListener {
    private final g a;

    private k(g gVar) {
        this.a = gVar;
    }

    public static View.OnClickListener a(g gVar) {
        return new k(gVar);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.a.a(false);
    }
}
