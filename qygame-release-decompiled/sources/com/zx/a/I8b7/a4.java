package com.zx.a.I8b7;

import java.lang.Thread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class a4 {
    public ThreadPoolExecutor a;
    public ThreadPoolExecutor b;
    public ThreadPoolExecutor c;
    public ThreadPoolExecutor d;
    public ThreadPoolExecutor e;

    public class a implements ThreadFactory {

        /* JADX INFO: renamed from: com.zx.a.I8b7.a4$a$a, reason: collision with other inner class name */
        public class C0058a implements Thread.UncaughtExceptionHandler {
            public C0058a(a aVar) {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                StringBuilder sbA = j3.a("caught an exception from ");
                sbA.append(thread.getName());
                v2.a(sbA.toString(), th);
            }
        }

        public a(a4 a4Var) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZX-Api-Thread");
            thread.setUncaughtExceptionHandler(new C0058a(this));
            return thread;
        }
    }

    public class b implements ThreadFactory {

        public class a implements Thread.UncaughtExceptionHandler {
            public a(b bVar) {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                StringBuilder sbA = j3.a("caught an exception from ");
                sbA.append(thread.getName());
                v2.a(sbA.toString(), th);
            }
        }

        public b(a4 a4Var) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZX-Api-ThreadV2");
            thread.setUncaughtExceptionHandler(new a(this));
            return thread;
        }
    }

    public class c implements ThreadFactory {

        public class a implements Thread.UncaughtExceptionHandler {
            public a(c cVar) {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                StringBuilder sbA = j3.a("caught an exception from ");
                sbA.append(thread.getName());
                v2.a(sbA.toString(), th);
            }
        }

        public c(a4 a4Var) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZX-Api-ThreadV3");
            thread.setUncaughtExceptionHandler(new a(this));
            return thread;
        }
    }

    public class d implements ThreadFactory {

        public class a implements Thread.UncaughtExceptionHandler {
            public a(d dVar) {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                StringBuilder sbA = j3.a("caught an exception from ");
                sbA.append(thread.getName());
                v2.a(sbA.toString(), th);
            }
        }

        public d(a4 a4Var) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZX-Api-ThreadV4");
            thread.setUncaughtExceptionHandler(new a(this));
            return thread;
        }
    }

    public class e implements ThreadFactory {

        public class a implements Thread.UncaughtExceptionHandler {
            public a(e eVar) {
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                StringBuilder sbA = j3.a("caught an exception from ");
                sbA.append(thread.getName());
                v2.a(sbA.toString(), th);
            }
        }

        public e(a4 a4Var) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZX-Api-ThreadV5");
            thread.setUncaughtExceptionHandler(new a(this));
            return thread;
        }
    }

    public static class f {
        public static final a4 a = new a4();
    }

    public a4() {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.a = new ThreadPoolExecutor(1, 1, 0L, timeUnit, new LinkedBlockingQueue(), new a(this));
        this.b = new ThreadPoolExecutor(1, 1, 0L, timeUnit, new LinkedBlockingQueue(), new b(this));
        this.c = new ThreadPoolExecutor(1, 1, 0L, timeUnit, new LinkedBlockingQueue(), new c(this));
        this.d = new ThreadPoolExecutor(1, 1, 0L, timeUnit, new LinkedBlockingQueue(), new d(this));
        this.e = new ThreadPoolExecutor(1, 1, 0L, timeUnit, new LinkedBlockingQueue(), new e(this));
    }
}
