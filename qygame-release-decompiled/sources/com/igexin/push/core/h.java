package com.igexin.push.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.igexin.sdk.PushConsts;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Application.ActivityLifecycleCallbacks {
    private static final String a = "GALC";
    private AtomicLong b = new AtomicLong(0);
    private volatile int c;

    /* JADX INFO: renamed from: com.igexin.push.core.h$1, reason: invalid class name */
    final class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context a;

        AnonymousClass1(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.currentThread().getName();
                com.igexin.c.a.c.a.a("GALC|>>>>>> FG threadName=" + Thread.currentThread().getName(), new Object[0]);
                if (com.igexin.push.g.k.a(this.a) || System.currentTimeMillis() - h.this.b.get() <= 8000) {
                    return;
                }
                Context context = this.a;
                com.igexin.push.core.a.b.d();
                Intent intent = new Intent(context, (Class<?>) com.igexin.push.core.a.b.a(this.a));
                intent.putExtra("action", PushConsts.ACTION_SERVICE_ONRESUME);
                ServiceManager.getInstance().b(this.a, intent);
                com.igexin.c.a.c.a.a("GALC|on fg, start>>>>>>", new Object[0]);
                h.this.b.set(System.currentTimeMillis());
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    private void a(Activity activity) {
        Context applicationContext = activity.getApplicationContext();
        activity.getComponentName().getClassName();
        int i = this.c;
        com.igexin.c.a.c.a.a("GALC|" + activity.getComponentName().getClassName() + " onAStart " + this.c, new Object[0]);
        if (this.c == 0) {
            com.igexin.b.a.a().a("GTALCallback").execute(new AnonymousClass1(applicationContext));
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        if (activity == null) {
            return;
        }
        Context applicationContext = activity.getApplicationContext();
        activity.getComponentName().getClassName();
        int i = this.c;
        com.igexin.c.a.c.a.a("GALC|" + activity.getComponentName().getClassName() + " onAStart " + this.c, new Object[0]);
        if (this.c == 0) {
            com.igexin.b.a.a().a("GTALCallback").execute(new AnonymousClass1(applicationContext));
        }
        this.c++;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        if (activity == null) {
            return;
        }
        this.c--;
        this.c = Math.max(this.c, 0);
        com.igexin.c.a.c.a.b(a, "|" + activity.getComponentName().getClassName() + " onAStopp " + this.c);
        if (this.c == 0) {
            com.igexin.c.a.c.a.a("GALC|>>>>>> on bg", new Object[0]);
        }
    }
}
