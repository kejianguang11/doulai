package com.igexin.push.core;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class a extends Thread {
    protected abstract void a();

    protected abstract String b();

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Thread.currentThread().setName(b());
        if (Looper.myLooper() == null) {
            Looper.prepare();
            Looper.loop();
        }
    }
}
