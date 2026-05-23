package com.loc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ck implements Runnable {
    a e;

    interface a {
        void a(ck ckVar);
    }

    public abstract void a();

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (Thread.interrupted()) {
                return;
            }
            a();
            if (Thread.interrupted() || this.e == null) {
                return;
            }
            this.e.a(this);
        } catch (Throwable th) {
            an.b(th, "ThreadTask", "run");
            th.printStackTrace();
        }
    }
}
