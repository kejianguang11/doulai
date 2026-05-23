package com.alipay.security.mobile.module.d;

/* JADX INFO: loaded from: classes.dex */
public final class c implements Runnable {
    final /* synthetic */ b a;

    public c(b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.a.a();
        } catch (Exception e) {
            d.a(e);
        }
    }
}
