package com.loc;

import com.loc.ck;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public abstract class cl {
    protected ThreadPoolExecutor a;
    private ConcurrentHashMap<ck, Future<?>> c = new ConcurrentHashMap<>();
    protected ck.a b = new ck.a() { // from class: com.loc.cl.1
        @Override // com.loc.ck.a
        public final void a(ck ckVar) {
            cl.this.a(ckVar);
        }
    };

    private synchronized void a(ck ckVar, Future<?> future) {
        try {
            this.c.put(ckVar, future);
        } catch (Throwable th) {
            an.b(th, "TPool", "addQueue");
            th.printStackTrace();
        }
    }

    private synchronized boolean c(ck ckVar) {
        boolean zContainsKey;
        try {
            zContainsKey = this.c.containsKey(ckVar);
        } catch (Throwable th) {
            an.b(th, "TPool", "contain");
            th.printStackTrace();
            zContainsKey = false;
        }
        return zContainsKey;
    }

    protected final synchronized void a(ck ckVar) {
        try {
            this.c.remove(ckVar);
        } catch (Throwable th) {
            an.b(th, "TPool", "removeQueue");
            th.printStackTrace();
        }
    }

    public final Executor b() {
        return this.a;
    }

    public final void b(ck ckVar) {
        if (c(ckVar) || this.a == null || this.a.isShutdown()) {
            return;
        }
        ckVar.e = this.b;
        try {
            Future<?> futureSubmit = this.a.submit(ckVar);
            if (futureSubmit == null) {
                return;
            }
            a(ckVar, futureSubmit);
        } catch (RejectedExecutionException e) {
            an.b(e, "TPool", "addTask");
        }
    }
}
