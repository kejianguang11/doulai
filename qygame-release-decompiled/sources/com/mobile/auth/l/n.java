package com.mobile.auth.l;

import android.content.Context;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class n {
    private static final ExecutorService a = new ThreadPoolExecutor(0, 30, 60, TimeUnit.SECONDS, new SynchronousQueue());

    public static abstract class a implements Runnable {
        private final Thread.UncaughtExceptionHandler a;

        protected a() {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.l.n.a.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    th.printStackTrace();
                }
            };
        }

        protected a(final Context context, final com.cmic.sso.sdk.a aVar) {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.l.n.a.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    aVar.a().a.add(th);
                    com.mobile.auth.e.e.b(context).a("200025", "发生未知错误", aVar, null);
                }
            };
        }

        protected abstract void a();

        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(this.a);
            a();
            Thread.currentThread().setUncaughtExceptionHandler(null);
        }
    }

    public static void a(a aVar) {
        try {
            a.execute(aVar);
        } catch (Exception e) {
            aVar.a.uncaughtException(Thread.currentThread(), e);
        }
    }
}
