package com.igexin.c.a.d;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.NotificationCompat;
import com.igexin.push.d.c.k;
import com.igexin.push.d.c.n;
import com.igexin.push.g.o;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class g extends BroadcastReceiver implements Comparator<f> {
    public static final long E = TimeUnit.SECONDS.toMillis(2);
    protected static final String F = "AlarmTaskSchedule.";
    protected static final String G = "AlarmTaskScheduleBak.";
    protected static final String H = "AlarmNioTaskSchedule.";
    public static final String h = "TaskService";
    public static final String i = "com.igexin.c.a.d.g";
    static final byte j = -1;
    static final byte k = 0;
    static final byte l = 1;
    static final byte m = 2;
    static final byte n = -128;
    static final byte o = 7;
    public PendingIntent A;
    public String B;
    volatile long C;
    public volatile boolean D;
    public boolean t;
    public PowerManager v;
    public AlarmManager w;
    public Intent x;
    public PendingIntent y;
    public Intent z;
    final ReentrantLock u = new ReentrantLock();
    public boolean I = false;
    final HashMap<Long, com.igexin.c.a.d.a.c> q = new HashMap<>(7);
    public final e<f> s = new e<>(this, this);
    final d r = new d();
    public final b p = new b();

    /* JADX INFO: renamed from: com.igexin.c.a.d.g$1, reason: invalid class name */
    public class AnonymousClass1 extends IntentFilter {
        final /* synthetic */ Context a;

        public AnonymousClass1(Context context) {
            this.a = context;
            addAction(g.F + this.a.getPackageName());
            addAction(g.G + this.a.getPackageName());
            addAction("android.intent.action.SCREEN_OFF");
            addAction("android.intent.action.SCREEN_ON");
        }
    }

    final class a {
        volatile int g;
        final ReentrantLock c = new ReentrantLock();
        final BlockingQueue<f> a = new SynchronousQueue();
        final HashMap<Integer, RunnableC0030a> b = new HashMap<>();
        volatile long e = TimeUnit.SECONDS.toNanos(60);
        volatile int f = 0;
        ThreadFactory d = new b();
        volatile int h = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        /* JADX INFO: renamed from: com.igexin.c.a.d.g$a$a, reason: collision with other inner class name */
        final class RunnableC0030a implements Runnable {
            final BlockingQueue<f> a = new LinkedBlockingQueue();
            f b;
            f c;
            volatile int d;
            volatile boolean e;

            public RunnableC0030a(f fVar) {
                this.b = fVar;
            }

            private void a() {
                this.a.clear();
                this.c = null;
            }

            private void a(f fVar) {
                if (this.d == 0) {
                    this.d = fVar.C;
                }
                f fVar2 = fVar;
                boolean z = true;
                while (z) {
                    try {
                        try {
                            fVar2.b_();
                            fVar2.o();
                            if (!fVar2.v) {
                                fVar2.d_();
                            }
                            boolean z2 = fVar2.m;
                            boolean z3 = fVar2.p;
                            long j = fVar2.w;
                        } catch (Exception e) {
                            com.igexin.c.a.c.a.a(e);
                            com.igexin.c.a.c.a.a(g.h + e.toString(), new Object[0]);
                            fVar2.v = true;
                            fVar2.E = e;
                            fVar2.p();
                            fVar2.l();
                            g.this.a((Object) fVar2);
                            g.this.e();
                            if (!fVar2.v) {
                                fVar2.d_();
                            }
                            boolean z4 = fVar2.m;
                            boolean z5 = fVar2.p;
                            long j2 = fVar2.w;
                            if (fVar2.m || !fVar2.p || fVar2.w == 0) {
                            }
                        }
                    } finally {
                    }
                    if (fVar2.m || !fVar2.p || fVar2.w == 0) {
                        fVar2 = null;
                        z = false;
                    }
                }
            }

            private f b() {
                while (this.d != 0) {
                    try {
                        f fVarPoll = this.a.poll(a.this.e, TimeUnit.NANOSECONDS);
                        if (fVarPoll != null) {
                            return fVarPoll;
                        }
                        if (this.a.isEmpty()) {
                            ReentrantLock reentrantLock = a.this.c;
                            reentrantLock.lock();
                            try {
                                if (this.a.isEmpty()) {
                                    a.this.b.remove(Integer.valueOf(this.d));
                                    this.d = 0;
                                    return null;
                                }
                            } finally {
                                reentrantLock.unlock();
                            }
                        } else {
                            continue;
                        }
                    } catch (InterruptedException e) {
                        com.igexin.c.a.c.a.a(e);
                    }
                }
                return null;
            }

            @Override // java.lang.Runnable
            public final void run() {
                boolean zA = true;
                while (zA) {
                    try {
                        try {
                            f fVarB = this.b;
                            this.b = null;
                            while (true) {
                                if (fVarB == null) {
                                    fVarB = b();
                                    if (fVarB == null && (fVarB = a.this.b()) == null) {
                                        zA = a.this.a(this);
                                        if (!zA) {
                                        }
                                    }
                                }
                                this.c = null;
                                if (this.d == 0) {
                                    this.d = fVarB.C;
                                }
                                boolean z = true;
                                f fVar = fVarB;
                                while (z) {
                                    try {
                                        try {
                                            fVar.b_();
                                            fVar.o();
                                            if (!fVar.v) {
                                                fVar.d_();
                                            }
                                            boolean z2 = fVar.m;
                                            boolean z3 = fVar.p;
                                            long j = fVar.w;
                                        } catch (Exception e) {
                                            com.igexin.c.a.c.a.a(e);
                                            com.igexin.c.a.c.a.a(g.h + e.toString(), new Object[0]);
                                            fVar.v = true;
                                            fVar.E = e;
                                            fVar.p();
                                            fVar.l();
                                            g.this.a((Object) fVar);
                                            g.this.e();
                                            if (!fVar.v) {
                                                fVar.d_();
                                            }
                                            boolean z4 = fVar.m;
                                            boolean z5 = fVar.p;
                                            long j2 = fVar.w;
                                            if (fVar.m || !fVar.p || fVar.w == 0) {
                                            }
                                        }
                                    } catch (Throwable th) {
                                        if (!fVar.v) {
                                            fVar.d_();
                                        }
                                        boolean z6 = fVar.m;
                                        boolean z7 = fVar.p;
                                        long j3 = fVar.w;
                                        if (fVar.m || !fVar.p || fVar.w == 0) {
                                            throw th;
                                        }
                                    }
                                    if (fVar.m || !fVar.p || fVar.w == 0) {
                                        z = false;
                                        fVar = null;
                                    }
                                }
                                this.c = fVarB;
                                fVarB = null;
                            }
                            throw th;
                        } catch (Exception e2) {
                            com.igexin.c.a.c.a.a(e2);
                            com.igexin.c.a.c.a.a("TaskService|Worker|run()|error" + e2.toString(), new Object[0]);
                            zA = a.this.a(this);
                            if (!zA) {
                                a();
                            }
                        }
                    } catch (Throwable th2) {
                        if (!a.this.a(this)) {
                            a();
                        }
                        throw th2;
                    }
                }
            }
        }

        final class b implements ThreadFactory {
            final AtomicInteger a = new AtomicInteger(0);

            public b() {
            }

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, "TS-pool-" + this.a.incrementAndGet());
            }
        }

        public a() {
        }

        private void c(f fVar) {
            if (fVar == null) {
                throw new NullPointerException();
            }
            if (fVar.C != 0) {
                ReentrantLock reentrantLock = this.c;
                reentrantLock.lock();
                try {
                    RunnableC0030a runnableC0030a = this.b.get(Integer.valueOf(fVar.C));
                    if (runnableC0030a != null) {
                        runnableC0030a.a.offer(fVar);
                        return;
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }
            if (this.g >= this.f || !a(fVar)) {
                if (!this.a.offer(fVar)) {
                    b(fVar);
                } else if (this.g == 0) {
                    a();
                }
            }
        }

        private void d(f fVar) {
            if (this.g >= this.f || !a(fVar)) {
                if (!this.a.offer(fVar)) {
                    if (!b(fVar)) {
                    }
                } else if (this.g == 0) {
                    a();
                }
            }
        }

        private Thread e(f fVar) {
            RunnableC0030a runnableC0030a = new RunnableC0030a(fVar);
            if (fVar != null && fVar.C != 0) {
                this.b.put(Integer.valueOf(fVar.C), runnableC0030a);
            }
            Thread threadNewThread = this.d.newThread(runnableC0030a);
            if (threadNewThread != null) {
                this.g++;
            }
            return threadNewThread;
        }

        final void a() {
            ReentrantLock reentrantLock = this.c;
            reentrantLock.lock();
            try {
                Thread threadE = null;
                if (this.g < Math.max(this.f, 1) && !this.a.isEmpty()) {
                    threadE = e(null);
                }
                if (threadE != null) {
                    threadE.start();
                }
            } finally {
                reentrantLock.unlock();
            }
        }

        final boolean a(f fVar) {
            ReentrantLock reentrantLock = this.c;
            reentrantLock.lock();
            try {
                Thread threadE = this.g < this.f ? e(fVar) : null;
                if (threadE == null) {
                    return false;
                }
                threadE.start();
                return true;
            } finally {
                reentrantLock.unlock();
            }
        }

        final boolean a(RunnableC0030a runnableC0030a) {
            ReentrantLock reentrantLock = this.c;
            reentrantLock.lock();
            try {
                int i = this.g - 1;
                this.g = i;
                if (i == 0 && !this.a.isEmpty()) {
                    Thread threadE = e(null);
                    if (threadE != null) {
                        threadE.start();
                    }
                } else if (!runnableC0030a.a.isEmpty()) {
                    return true;
                }
                this.b.remove(Integer.valueOf(runnableC0030a.d));
                reentrantLock.unlock();
                return false;
            } finally {
                reentrantLock.unlock();
            }
        }

        final f b() {
            f fVarPoll;
            while (true) {
                try {
                    fVarPoll = this.g > this.f ? this.a.poll(this.e, TimeUnit.NANOSECONDS) : this.a.take();
                } catch (InterruptedException e) {
                    com.igexin.c.a.c.a.a(e);
                }
                if (fVarPoll != null) {
                    return fVarPoll;
                }
                if (this.a.isEmpty()) {
                    return null;
                }
            }
        }

        final boolean b(f fVar) {
            ReentrantLock reentrantLock = this.c;
            reentrantLock.lock();
            try {
                Thread threadE = this.g < this.h ? e(fVar) : null;
                if (threadE == null) {
                    return false;
                }
                threadE.start();
                return true;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public final class b extends Thread {
        volatile boolean a = true;
        long b;
        long c;
        a d;

        public b() {
            setName("TS-processor");
        }

        private static void a() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:
        
            if (r3.g >= r3.f) goto L109;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:
        
            if (r3.a(r4) != false) goto L115;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0081, code lost:
        
            if (r3.a.offer(r4) == false) goto L111;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0085, code lost:
        
            if (r3.g != 0) goto L117;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0087, code lost:
        
            r3.a();
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x008c, code lost:
        
            r3.b(r4);
         */
        /* JADX WARN: Removed duplicated region for block: B:123:0x015f A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0150  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() throws Throwable {
            Process.setThreadPriority(-2);
            e<f> eVar = g.this.s;
            while (true) {
                f fVar = null;
                while (true) {
                    byte b = 1;
                    while (this.a) {
                        switch (b) {
                            case -1:
                                try {
                                    fVar.d();
                                    fVar.getClass().getName();
                                    fVar.hashCode();
                                    boolean z = fVar.p;
                                    long j = fVar.w;
                                    if (fVar.m()) {
                                        if (this.d == null) {
                                            this.d = g.this.new a();
                                        }
                                        fVar.getClass().getName();
                                        a aVar = this.d;
                                        if (fVar == null) {
                                            throw new NullPointerException();
                                        }
                                        if (fVar.C != 0) {
                                            ReentrantLock reentrantLock = aVar.c;
                                            reentrantLock.lock();
                                            try {
                                                a.RunnableC0030a runnableC0030a = aVar.b.get(Integer.valueOf(fVar.C));
                                                if (runnableC0030a == null) {
                                                    reentrantLock.unlock();
                                                } else {
                                                    runnableC0030a.a.offer(fVar);
                                                    reentrantLock.unlock();
                                                }
                                            } catch (Throwable th) {
                                                reentrantLock.unlock();
                                                throw th;
                                            }
                                        }
                                    } else if (fVar.p && fVar.w == 0) {
                                        com.igexin.c.a.c.a.a(g.h, fVar + "|isBlock = false|cycyle = true|doTime = 0, invalid ###########");
                                        com.igexin.c.a.c.a.a("TaskService|" + fVar + "|isBlock = false|cycyle = true|doTime = 0, invalid ###########", new Object[0]);
                                    }
                                    break;
                                } catch (Exception e) {
                                    com.igexin.c.a.c.a.a(e);
                                    com.igexin.c.a.c.a.a("TaskService|TASK_INIT|error|" + e.toString(), new Object[0]);
                                }
                            case 0:
                                try {
                                    try {
                                        fVar.b_();
                                        fVar.o();
                                    } catch (Exception e2) {
                                        com.igexin.c.a.c.a.a(e2);
                                        com.igexin.c.a.c.a.a(g.h, e2.toString());
                                        com.igexin.c.a.c.a.a("TaskService|SERVICE_PROCESSING|error|" + e2.toString(), new Object[0]);
                                        fVar.v = true;
                                        fVar.E = e2;
                                        fVar.p();
                                        fVar.l();
                                        g.this.r.a(fVar);
                                        g.this.f();
                                        if (!fVar.v) {
                                            fVar.d_();
                                        }
                                        if (!fVar.m && !fVar.q) {
                                        }
                                        fVar = null;
                                        b = 1;
                                        try {
                                            f fVarA = eVar.a();
                                            try {
                                                fVarA.getClass().getSimpleName();
                                                break;
                                            } catch (Exception unused) {
                                            }
                                            fVar = fVarA;
                                        } catch (Exception unused2) {
                                        }
                                        if (fVar != null) {
                                        }
                                    }
                                    fVar = null;
                                    b = 1;
                                } finally {
                                    g.this.f();
                                    if (!fVar.v) {
                                        fVar.d_();
                                    }
                                    if (!fVar.m && !fVar.q) {
                                        fVar.D = 0;
                                        eVar.a(fVar);
                                    }
                                }
                            case 1:
                                f fVarA2 = eVar.a();
                                fVarA2.getClass().getSimpleName();
                                fVar = fVarA2;
                                if (fVar != null) {
                                    g.this.f();
                                } else if (fVar.m || fVar.n) {
                                    fVar = null;
                                } else {
                                    b = g.j;
                                }
                                break;
                            case 2:
                                g.this.f();
                                break;
                        }
                    }
                    eVar.c.clear();
                    return;
                }
            }
        }
    }

    protected g() {
        f.H = this;
    }

    private static int a(f fVar, f fVar2) {
        if (fVar.w < fVar2.w) {
            return -1;
        }
        if (fVar.w > fVar2.w) {
            return 1;
        }
        if (fVar.D > fVar2.D) {
            return -1;
        }
        if (fVar.D < fVar2.D) {
            return 1;
        }
        if (fVar.x < fVar2.x) {
            return -1;
        }
        if (fVar.x > fVar2.x) {
            return 1;
        }
        return fVar.hashCode() - fVar2.hashCode();
    }

    private void a() {
        try {
            if (this.y != null) {
                this.w.cancel(this.y);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(h, th.toString());
        }
    }

    private void a(int i2, TimeUnit timeUnit) {
        this.p.b = TimeUnit.MILLISECONDS.convert(i2, timeUnit);
    }

    private void a(Context context) {
        if (this.I) {
            return;
        }
        if (!o.l()) {
            this.v = (PowerManager) context.getSystemService("power");
            this.D = true;
            this.w = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            try {
                if (Build.VERSION.SDK_INT >= 31) {
                    this.t = ((Boolean) AlarmManager.class.getDeclaredMethod("canScheduleExactAlarms", new Class[0]).invoke(this.w, new Object[0])).booleanValue();
                } else {
                    this.t = true;
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(context);
            if (Build.VERSION.SDK_INT > 33) {
                context.registerReceiver(this, anonymousClass1, com.igexin.push.core.e.ac, null, 4);
            } else {
                context.registerReceiver(this, anonymousClass1, com.igexin.push.core.e.ac, null);
            }
            this.B = H + context.getPackageName();
            if (Build.VERSION.SDK_INT > 33) {
                context.registerReceiver(this, new IntentFilter(this.B), com.igexin.push.core.e.ac, null, 4);
            } else {
                context.registerReceiver(this, new IntentFilter(this.B), com.igexin.push.core.e.ac, null);
            }
            int i2 = 134217728;
            if (o.a(context) >= 31 && Build.VERSION.SDK_INT >= 30) {
                i2 = 201326592;
            }
            this.x = new Intent(F + context.getPackageName());
            this.y = PendingIntent.getBroadcast(context, hashCode(), this.x, i2);
            hashCode();
            this.z = new Intent(this.B);
            this.A = PendingIntent.getBroadcast(context, hashCode() + 2, this.z, i2);
            hashCode();
        }
        this.p.start();
        try {
            Thread.yield();
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
        }
        this.I = true;
    }

    private static boolean a(com.igexin.c.a.d.a.e eVar, com.igexin.c.a.d.a.c cVar) {
        int iC = eVar.c();
        if (iC <= Integer.MIN_VALUE || iC >= 0) {
            if (iC < 0 || iC >= Integer.MAX_VALUE) {
                return false;
            }
            return cVar.a(eVar);
        }
        f fVar = (f) eVar;
        boolean zA = fVar.v ? false : cVar.a(eVar);
        if (zA) {
            fVar.d_();
        }
        return zA;
    }

    private boolean a(f fVar) {
        e<f> eVar = this.s;
        return eVar != null && eVar.c(fVar);
    }

    private boolean a(f fVar, boolean z, int i2, long j2, byte b2, Object obj, com.igexin.c.a.d.a.d dVar, int i3, com.igexin.c.a.d.a.g gVar) {
        if (fVar == null) {
            throw new NullPointerException();
        }
        fVar.A = i2;
        fVar.a((int) b2);
        fVar.F = obj;
        fVar.O = dVar;
        fVar.a(j2, TimeUnit.MILLISECONDS);
        fVar.a(i3, gVar);
        return a(fVar, z);
    }

    private boolean a(Class cls) {
        e<f> eVar = this.s;
        return eVar != null && eVar.a(cls);
    }

    @TargetApi(19)
    private void b(long j2) {
        if (o.l()) {
            return;
        }
        com.igexin.c.a.c.a.a("setnioalarm|" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(j2)), new Object[0]);
        if (j2 < 0) {
            j2 = System.currentTimeMillis() + E;
        }
        try {
            if (Build.VERSION.SDK_INT < 19) {
                this.w.set(0, j2, this.A);
                return;
            }
            try {
                if (this.t) {
                    this.w.setExact(0, j2, this.A);
                } else if (Build.VERSION.SDK_INT > 23) {
                    this.w.setAndAllowWhileIdle(0, j2, this.y);
                } else {
                    this.w.set(0, j2, this.A);
                }
            } catch (Exception unused) {
                this.w.set(0, j2, this.A);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(h, th.toString());
        }
    }

    private boolean b() {
        e<f> eVar = this.s;
        if (eVar == null) {
            return false;
        }
        eVar.c.clear();
        return true;
    }

    @TargetApi(19)
    public final void a(long j2) {
        if (this.D) {
            com.igexin.c.a.c.a.a("setalarm|" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(j2)), new Object[0]);
            if (j2 < 0) {
                j2 = System.currentTimeMillis() + E;
            }
            try {
                if (this.y != null) {
                    if (Build.VERSION.SDK_INT < 19) {
                        this.w.set(0, j2, this.y);
                        return;
                    }
                    try {
                        if (this.t) {
                            this.w.setExact(0, j2, this.y);
                        } else if (Build.VERSION.SDK_INT > 23) {
                            this.w.setAndAllowWhileIdle(0, j2, this.y);
                        } else {
                            this.w.set(0, j2, this.y);
                        }
                    } catch (Throwable unused) {
                        this.w.set(0, j2, this.y);
                    }
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(h, th.toString());
                com.igexin.c.a.c.a.a(h + th.toString(), new Object[0]);
            }
        }
    }

    public final boolean a(com.igexin.c.a.d.a.c cVar) {
        ReentrantLock reentrantLock = this.u;
        if (reentrantLock.tryLock()) {
            try {
                if (this.q.containsKey(Long.valueOf(cVar.g()))) {
                    return false;
                }
                this.q.put(Long.valueOf(cVar.g()), cVar);
                reentrantLock.unlock();
                return true;
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
                com.igexin.c.a.c.a.a("TaskService|" + th.toString(), new Object[0]);
                return false;
            } finally {
                reentrantLock.unlock();
            }
            reentrantLock.unlock();
        }
        return false;
    }

    public final boolean a(f fVar, boolean z) {
        if (fVar == null) {
            throw new NullPointerException();
        }
        int iIncrementAndGet = 0;
        if (fVar.q || fVar.m) {
            return false;
        }
        fVar.getClass().getName();
        e<f> eVar = this.s;
        if ((fVar instanceof com.igexin.c.a.b.f) && (((com.igexin.c.a.b.f) fVar).d instanceof com.igexin.push.d.c.o)) {
            if (z) {
                iIncrementAndGet = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            }
        } else if (z) {
            iIncrementAndGet = eVar.d.incrementAndGet();
        }
        fVar.D = iIncrementAndGet;
        return eVar.a(fVar);
    }

    public final boolean a(f fVar, boolean z, boolean z2) {
        if (fVar == null) {
            throw new NullPointerException();
        }
        if (fVar.n) {
            return false;
        }
        if (!z || z2) {
            return a(fVar, z2 && z);
        }
        fVar.d();
        try {
            try {
                fVar.b_();
                fVar.o();
                if (!fVar.v) {
                    fVar.d_();
                }
                return true;
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
                fVar.v = true;
                fVar.E = e;
                fVar.l();
                fVar.p();
                a((Object) fVar);
                e();
                if (!fVar.v) {
                    fVar.d_();
                }
                return false;
            }
        } catch (Throwable th) {
            if (!fVar.v) {
                fVar.d_();
            }
            throw th;
        }
    }

    public final boolean a(Object obj) {
        if (obj == null) {
            return false;
        }
        obj.getClass().getName();
        obj.hashCode();
        try {
            if (obj instanceof n) {
                obj.getClass().getName();
                obj.hashCode();
            }
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
        obj.getClass().getName();
        obj.hashCode();
        com.igexin.c.a.c.a.a("TaskService|responseQueue ++ task = " + obj.getClass().getName() + "@" + obj.hashCode(), new Object[0]);
        if (!(obj instanceof com.igexin.c.a.d.a.e)) {
            throw new ClassCastException("response Obj is not a TaskResult ");
        }
        com.igexin.c.a.d.a.e eVar = (com.igexin.c.a.d.a.e) obj;
        if (eVar.j()) {
            return false;
        }
        eVar.a(false);
        if ((obj instanceof com.igexin.push.d.b.a) || (obj instanceof com.igexin.push.d.b.b)) {
            this.r.a();
            com.igexin.c.a.c.a.a("TaskService|change to primaryQueue", new Object[0]);
        }
        this.r.a(eVar);
        return true;
    }

    @Override // java.util.Comparator
    public /* synthetic */ int compare(f fVar, f fVar2) {
        f fVar3 = fVar;
        f fVar4 = fVar2;
        if (fVar3.w < fVar4.w) {
            return -1;
        }
        if (fVar3.w > fVar4.w) {
            return 1;
        }
        if (fVar3.D > fVar4.D) {
            return -1;
        }
        if (fVar3.D < fVar4.D) {
            return 1;
        }
        if (fVar3.x < fVar4.x) {
            return -1;
        }
        if (fVar3.x > fVar4.x) {
            return 1;
        }
        return fVar3.hashCode() - fVar4.hashCode();
    }

    public final void d() {
        try {
            if (this.A != null) {
                this.w.cancel(this.A);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(h, th.toString());
        }
    }

    protected final void e() {
        if (this.p == null || this.p.isInterrupted()) {
            return;
        }
        this.p.interrupt();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0000 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void f() throws Throwable {
        com.igexin.c.a.d.a.e eVarD;
        boolean z;
        int iC;
        int iC2;
        boolean zA;
        int iC3;
        while (!this.r.c() && (eVarD = this.r.d()) != null) {
            eVarD.a(true);
            ReentrantLock reentrantLock = this.u;
            reentrantLock.lock();
            try {
            } catch (Throwable th) {
                th = th;
                z = false;
            }
            if (!this.q.isEmpty()) {
                long jK = eVarD.k();
                if (jK != 0) {
                    com.igexin.c.a.d.a.c cVar = this.q.get(Long.valueOf(jK));
                    zA = cVar != null ? a(eVarD, cVar) : false;
                } else {
                    Iterator<com.igexin.c.a.d.a.c> it = this.q.values().iterator();
                    z = false;
                    while (true) {
                        try {
                            try {
                                if (!it.hasNext()) {
                                    zA = z;
                                    break;
                                }
                                boolean zA2 = a(eVarD, it.next());
                                if (zA2) {
                                    zA = zA2;
                                    break;
                                }
                                z = zA2;
                            } catch (Throwable th2) {
                                th = th2;
                                if (!z && (iC2 = eVarD.c()) > Integer.MIN_VALUE && iC2 < 0) {
                                    ((f) eVarD).d_();
                                }
                                reentrantLock.unlock();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            com.igexin.c.a.c.a.a(th);
                            com.igexin.c.a.c.a.a(h, th.toString());
                            com.igexin.c.a.c.a.a("TaskService|" + th.toString(), new Object[0]);
                            if (!z && (iC = eVarD.c()) > Integer.MIN_VALUE && iC < 0) {
                            }
                            reentrantLock.unlock();
                            if (!(eVarD instanceof k)) {
                            }
                        }
                    }
                }
                if (!zA && (iC3 = eVarD.c()) > Integer.MIN_VALUE && iC3 < 0) {
                    ((f) eVarD).d_();
                }
            }
            reentrantLock.unlock();
            if (!(eVarD instanceof k)) {
                this.r.b();
                com.igexin.c.a.c.a.a("TaskService|queue -> secondRespQueue", new Object[0]);
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            this.D = true;
            com.igexin.c.a.c.a.a("screenoff", new Object[0]);
            if (this.s.g.get() > 0) {
                a(this.s.g.get());
                return;
            }
            return;
        }
        if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
            this.D = false;
            com.igexin.c.a.c.a.a("screenon", new Object[0]);
            return;
        }
        if (intent.getAction().startsWith(F) || intent.getAction().startsWith(G)) {
            Calendar.getInstance().getTime().toLocaleString();
            com.igexin.c.a.c.a.a("receivealarm|" + this.D, new Object[0]);
            e();
            return;
        }
        if (this.B.equals(intent.getAction())) {
            Calendar calendar = Calendar.getInstance();
            com.igexin.c.a.c.a.b(i, "CPU ON + NioAlarmReceiver:-> cTime; " + calendar.getTime().toLocaleString());
            try {
                com.igexin.c.a.c.a.a(h, " alarm time out #######");
                com.igexin.c.a.c.a.a("TaskService|alarm time out #######", new Object[0]);
                com.igexin.c.a.b.a.a.d.a().f();
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
    }
}
