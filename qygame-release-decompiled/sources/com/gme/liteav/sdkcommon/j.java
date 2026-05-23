package com.gme.liteav.sdkcommon;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class j implements View.OnClickListener {
    private final g a;
    private final Button b;

    private j(g gVar, Button button) {
        this.a = gVar;
        this.b = button;
    }

    public static View.OnClickListener a(g gVar, Button button) {
        return new j(gVar, button);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        WindowManager.LayoutParams layoutParams;
        int i;
        g gVar = this.a;
        if (gVar.m) {
            gVar.b.height = gVar.n;
            if (gVar.b.y + gVar.b.height > gVar.a.heightPixels) {
                layoutParams = gVar.b;
                i = gVar.a.heightPixels - gVar.b.y;
            }
            gVar.m = !gVar.m;
            gVar.f.updateViewLayout(gVar.g, gVar.b);
            ViewGroup.LayoutParams layoutParams2 = gVar.k.getLayoutParams();
            layoutParams2.height = gVar.b();
            gVar.k.setLayoutParams(layoutParams2);
            gVar.d.post(l.a(gVar));
        }
        layoutParams = gVar.b;
        i = gVar.n / 2;
        layoutParams.height = i;
        gVar.m = !gVar.m;
        gVar.f.updateViewLayout(gVar.g, gVar.b);
        ViewGroup.LayoutParams layoutParams22 = gVar.k.getLayoutParams();
        layoutParams22.height = gVar.b();
        gVar.k.setLayoutParams(layoutParams22);
        gVar.d.post(l.a(gVar));
    }
}
