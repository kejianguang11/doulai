package com.alipay.sdk.widget;

/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a.f == null || !this.a.f.isShowing()) {
            return;
        }
        try {
            this.a.l.removeMessages(1);
            this.a.f.dismiss();
        } catch (Exception unused) {
        }
    }
}
