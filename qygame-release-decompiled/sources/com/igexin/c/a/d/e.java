package com.igexin.c.a.d;

import com.igexin.c.a.d.f;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class e<E extends f> {
    static final /* synthetic */ boolean h = !e.class.desiredAssertionStatus();
    private static final String i = "ScheduleQueue";
    final TreeSet<E> c;
    int e;
    g f;
    private long j;
    final transient ReentrantLock a = new ReentrantLock();
    final transient Condition b = this.a.newCondition();
    final AtomicInteger d = new AtomicInteger(0);
    public final AtomicLong g = new AtomicLong(-1);

    public e(Comparator<? super E> comparator, g gVar) {
        this.c = new TreeSet<>(comparator);
        this.f = gVar;
    }

    private E b() {
        try {
            return this.c.first();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    private E c() {
        E e = (E) b();
        if (e == null) {
            return null;
        }
        if (this.c.remove(e)) {
            return e;
        }
        com.igexin.c.a.c.a.a(i, "Queue Poll Error@");
        return null;
    }

    private E d() {
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            f fVarB = b();
            if (fVarB != null) {
                if (fVarB.a(TimeUnit.MILLISECONDS) > 0) {
                    fVarB.N |= 134217728;
                } else {
                    fVarB.N &= 1090519038;
                }
                if (fVarB.N >= 0) {
                    E e = (E) c();
                    if (!h && e == null) {
                        throw new AssertionError();
                    }
                    if (!e()) {
                        this.b.signalAll();
                    }
                    return e;
                }
            }
            reentrantLock.unlock();
            return null;
        } finally {
            reentrantLock.unlock();
        }
    }

    private boolean e() {
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            return this.c.isEmpty();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void f() {
        this.c.clear();
    }

    public final int a(E e, long j, TimeUnit timeUnit) {
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            if (!this.c.contains(e)) {
                reentrantLock.unlock();
                return -1;
            }
            this.c.remove(e);
            e.w = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(j, timeUnit);
            e.getClass().getSimpleName();
            e.hashCode();
            e.a(TimeUnit.SECONDS);
            return a(e) ? 1 : -2;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final E a() throws InterruptedException {
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lockInterruptibly();
        while (true) {
            try {
                f fVarB = b();
                if (fVarB != null) {
                    long jA = fVarB.a(TimeUnit.NANOSECONDS);
                    boolean z = fVarB.m || fVarB.n;
                    if (jA <= 0 || z) {
                        break;
                    }
                    fVarB.getClass().getSimpleName();
                    fVarB.hashCode();
                    TimeUnit.SECONDS.convert(jA, TimeUnit.NANOSECONDS);
                    this.g.set(fVarB.w);
                    boolean z2 = this.f.D;
                    fVarB.getClass().getName();
                    fVarB.hashCode();
                    if (this.f.D) {
                        this.f.a(fVarB.w);
                    }
                    this.b.awaitNanos(jA);
                } else {
                    this.d.set(1);
                    this.e = 0;
                    this.b.await();
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        E e = (E) c();
        if (!h && e == null) {
            throw new AssertionError();
        }
        if (!e()) {
            this.b.signalAll();
        }
        if (this.j > 0) {
            System.currentTimeMillis();
        }
        this.g.set(-1L);
        return e;
    }

    public final boolean a(E e) {
        if (e == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            f fVarB = b();
            int i2 = this.e + 1;
            this.e = i2;
            e.x = i2;
            if (!this.c.add(e)) {
                e.x--;
                return false;
            }
            e.N++;
            e.N &= 1090519038;
            if (fVarB == null || this.c.comparator().compare(e, fVarB) < 0) {
                this.b.signalAll();
            }
            return true;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("ScheduleQueue|offer|error", new Object[0]);
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final boolean a(Class cls) {
        if (cls == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            ArrayList arrayList = new ArrayList();
            cls.getName();
            for (E e : this.c) {
                if (e.getClass() == cls) {
                    arrayList.add(e);
                }
            }
            cls.getName();
            arrayList.size();
            this.c.removeAll(arrayList);
            reentrantLock.unlock();
            return true;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final boolean b(E e) {
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            if (this.c.contains(e) && this.c.remove(e)) {
                return a(e);
            }
            reentrantLock.unlock();
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final boolean c(E e) {
        if (e == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.a;
        reentrantLock.lock();
        try {
            e.getClass().getName();
            if (!this.c.contains(e) || !this.c.remove(e)) {
                return false;
            }
            e.getClass().getName();
            e.hashCode();
            reentrantLock.unlock();
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }
}
