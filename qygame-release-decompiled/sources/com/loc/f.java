package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

/* JADX INFO: loaded from: classes.dex */
public final class f {
    e a;
    Context b;
    Messenger c = null;

    public f(Context context) {
        this.a = null;
        this.b = null;
        this.b = context.getApplicationContext();
        this.a = new e(this.b);
    }

    public final IBinder a(Intent intent) {
        this.a.b(intent);
        this.a.a(intent);
        this.c = new Messenger(this.a.b());
        return this.c.getBinder();
    }

    public final void a() {
        try {
            e.d();
            this.a.j = fq.b();
            this.a.k = fq.a();
            this.a.a();
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "onCreate");
        }
    }

    public final void b() {
        try {
            if (this.a != null) {
                this.a.b().sendEmptyMessage(11);
            }
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "onDestroy");
        }
    }
}
