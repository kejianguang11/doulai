package com.alipay.sdk.util;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
final class n implements Runnable {
    final /* synthetic */ Activity a;

    n(Activity activity) {
        this.a = activity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.finish();
    }
}
