package com.loc;

import android.os.SystemClock;
import android.util.LongSparseArray;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class dm {
    private static volatile dm g;
    private static Object h = new Object();
    private Object e = new Object();
    private Object f = new Object();
    private LongSparseArray<a> a = new LongSparseArray<>();
    private LongSparseArray<a> b = new LongSparseArray<>();
    private LongSparseArray<a> c = new LongSparseArray<>();
    private LongSparseArray<a> d = new LongSparseArray<>();

    private static class a {
        int a;
        long b;
        boolean c;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    private dm() {
    }

    public static dm a() {
        if (g == null) {
            synchronized (h) {
                if (g == null) {
                    g = new dm();
                }
            }
        }
        return g;
    }

    private static short a(LongSparseArray<a> longSparseArray, long j) {
        synchronized (longSparseArray) {
            a aVar = longSparseArray.get(j);
            if (aVar == null) {
                return (short) 0;
            }
            short sMax = (short) Math.max(1L, Math.min(32767L, (b() - aVar.b) / 1000));
            if (!aVar.c) {
                sMax = (short) (-sMax);
            }
            return sMax;
        }
    }

    private static void a(List<dl> list, LongSparseArray<a> longSparseArray, LongSparseArray<a> longSparseArray2) {
        long jB = b();
        byte b = 0;
        if (longSparseArray.size() == 0) {
            for (dl dlVar : list) {
                a aVar = new a(b);
                aVar.a = dlVar.b();
                aVar.b = jB;
                aVar.c = false;
                longSparseArray2.put(dlVar.a(), aVar);
            }
            return;
        }
        for (dl dlVar2 : list) {
            long jA = dlVar2.a();
            a aVar2 = longSparseArray.get(jA);
            if (aVar2 == null) {
                aVar2 = new a(b);
            } else {
                if (aVar2.a != dlVar2.b()) {
                }
                longSparseArray2.put(jA, aVar2);
            }
            aVar2.a = dlVar2.b();
            aVar2.b = jB;
            aVar2.c = true;
            longSparseArray2.put(jA, aVar2);
        }
    }

    private static long b() {
        return SystemClock.elapsedRealtime();
    }

    final short a(long j) {
        return a(this.a, j);
    }

    final void a(List<dl> list) {
        if (list.isEmpty()) {
            return;
        }
        synchronized (this.e) {
            a(list, this.a, this.b);
            LongSparseArray<a> longSparseArray = this.a;
            this.a = this.b;
            this.b = longSparseArray;
            this.b.clear();
        }
    }

    final short b(long j) {
        return a(this.c, j);
    }

    final void b(List<dl> list) {
        if (list.isEmpty()) {
            return;
        }
        synchronized (this.f) {
            a(list, this.c, this.d);
            LongSparseArray<a> longSparseArray = this.c;
            this.c = this.d;
            this.d = longSparseArray;
            this.d.clear();
        }
    }
}
