package com.mobile.auth.gatewayauth;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private Runnable a;
    private AtomicBoolean b;
    private Handler c;
    private long d;
    private Runnable e;
    private volatile boolean f;

    public c(long j, Runnable runnable) {
        this(j, runnable, Looper.getMainLooper());
    }

    public c(long j, Runnable runnable, Looper looper) {
        this.b = new AtomicBoolean(false);
        this.f = false;
        this.d = j;
        this.e = runnable;
        this.c = new Handler(looper);
    }

    static /* synthetic */ boolean a(c cVar) {
        try {
            return cVar.f;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    static /* synthetic */ AtomicBoolean b(c cVar) {
        try {
            return cVar.b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ Runnable c(c cVar) {
        try {
            return cVar.e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a() {
        try {
            if (this.e == null || this.d <= 0) {
                return;
            }
            this.a = new Runnable() { // from class: com.mobile.auth.gatewayauth.c.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        synchronized (c.this) {
                            if (!c.a(c.this)) {
                                c.b(c.this).set(true);
                                c.c(c.this).run();
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            this.c.postDelayed(this.a, this.d);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized void b() {
        try {
            this.f = true;
            if (this.a != null) {
                this.c.removeCallbacks(this.a);
            }
            this.e = null;
            this.a = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean c() {
        try {
            return this.b.get();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public synchronized boolean d() {
        boolean zC;
        try {
            zC = c();
            b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
        return !zC;
    }
}
