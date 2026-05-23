package com.loc;

import android.content.Context;
import android.os.Looper;
import java.lang.Thread;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class an extends ak implements Thread.UncaughtExceptionHandler {
    private static ExecutorService e;
    private static WeakReference<Context> g;
    private Context d;
    private static Set<Integer> f = Collections.synchronizedSet(new HashSet());
    private static final ThreadFactory h = new ThreadFactory() { // from class: com.loc.an.2
        private final AtomicInteger a = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "pama#" + this.a.getAndIncrement()) { // from class: com.loc.an.2.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        super.run();
                    } catch (Throwable unused) {
                    }
                }
            };
        }
    };

    private an(Context context) {
        this.d = context;
        try {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            if (this.b == null) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
                return;
            }
            String string = this.b.toString();
            if (!string.startsWith("com.amap.apis.utils.core.dynamiccore") && (string.indexOf("com.amap.api") != -1 || string.indexOf("com.loc") != -1)) {
                this.c = false;
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static synchronized an a(Context context, w wVar) throws k {
        if (wVar == null) {
            throw new k("sdk info is null");
        }
        if (wVar.a() == null || "".equals(wVar.a())) {
            throw new k("sdk name is invalid");
        }
        try {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (!f.add(Integer.valueOf(wVar.hashCode()))) {
            return (an) ak.a;
        }
        if (ak.a == null) {
            ak.a = new an(context);
        } else {
            ak.a.c = false;
        }
        ak.a.a(wVar, ak.a.c);
        return (an) ak.a;
    }

    public static void a(w wVar, String str, k kVar) {
        if (kVar != null) {
            a(wVar, str, kVar.c(), kVar.d(), kVar.e(), kVar.b());
        }
    }

    public static void a(w wVar, String str, String str2, String str3, String str4) {
        a(wVar, str, str2, str3, "", str4);
    }

    public static void a(w wVar, String str, String str2, String str3, String str4, String str5) {
        try {
            if (ak.a != null) {
                ak.a.a(wVar, "path:" + str + ",type:" + str2 + ",gsid:" + str3 + ",csid:" + str4 + ",code:" + str5, "networkError");
            }
        } catch (Throwable unused) {
        }
    }

    public static synchronized void b() {
        try {
            if (e != null) {
                e.shutdown();
            }
            bd.a();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            if (ak.a != null && Thread.getDefaultUncaughtExceptionHandler() == ak.a && ak.a.b != null) {
                Thread.setDefaultUncaughtExceptionHandler(ak.a.b);
            }
            ak.a = null;
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public static void b(w wVar, String str, String str2) {
        try {
            if (ak.a != null) {
                ak.a.a(wVar, str, str2);
            }
        } catch (Throwable unused) {
        }
    }

    public static void b(Throwable th, String str, String str2) {
        try {
            if (ak.a != null) {
                ak.a.a(th, 1, str, str2);
            }
        } catch (Throwable unused) {
        }
    }

    public static void c() {
        if (g != null && g.get() != null) {
            al.a(g.get());
        } else if (ak.a != null) {
            ak.a.a();
        }
    }

    @Override // com.loc.ak
    protected final void a() {
        al.a(this.d);
    }

    @Override // com.loc.ak
    protected final void a(w wVar, String str, String str2) {
        ao.a(wVar, this.d, str2, str);
    }

    @Override // com.loc.ak
    protected final void a(final w wVar, final boolean z) {
        try {
            cj.a().b(new ck() { // from class: com.loc.an.1
                @Override // com.loc.ck
                public final void a() {
                    try {
                        synchronized (Looper.getMainLooper()) {
                            al.a(wVar);
                        }
                        if (z) {
                            ao.a(an.this.d);
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.loc.ak
    protected final void a(Throwable th, int i, String str, String str2) {
        ao.a(this.d, th, i, str, str2);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        if (th == null) {
            return;
        }
        a(th, 0, null, null);
        if (this.b != null) {
            try {
                Thread.setDefaultUncaughtExceptionHandler(this.b);
            } catch (Throwable unused) {
            }
            this.b.uncaughtException(thread, th);
        }
    }
}
