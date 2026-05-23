package com.zx.a.I8b7;

import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class y implements ThreadFactory {
    public AtomicInteger a = new AtomicInteger(0);

    public class a implements Thread.UncaughtExceptionHandler {
        public a(y yVar) {
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
        }
    }

    public y(z zVar) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        StringBuilder sbA = j3.a("ZXHttpClient dispatcher's thread");
        sbA.append(this.a.getAndIncrement());
        thread.setName(sbA.toString());
        thread.setUncaughtExceptionHandler(new a(this));
        return thread;
    }
}
