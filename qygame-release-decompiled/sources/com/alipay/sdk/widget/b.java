package com.alipay.sdk.widget;

import com.alipay.sdk.widget.a.AlertDialogC0003a;

/* JADX INFO: loaded from: classes.dex */
final class b implements Runnable {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a.f == null) {
            this.a.f = this.a.new AlertDialogC0003a(this.a.g);
            this.a.f.setCancelable(this.a.e);
        }
        try {
            if (this.a.f.isShowing()) {
                return;
            }
            this.a.f.show();
            this.a.l.sendEmptyMessageDelayed(1, 15000L);
        } catch (Exception unused) {
        }
    }
}
