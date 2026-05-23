package com.zx.a.I8b7;

import androidx.appcompat.widget.ActivityChooserView;
import com.zx.a.I8b7.k1;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class z {
    public ExecutorService a;
    public final Deque<k1.a> b = new ArrayDeque();
    public final Deque<k1.a> c = new ArrayDeque();
    public final Deque<k1> d = new ArrayDeque();

    public final void a() {
        ExecutorService executorService;
        if (this.c.size() < 64 && !this.b.isEmpty()) {
            Iterator<k1.a> it = this.b.iterator();
            while (it.hasNext()) {
                k1.a next = it.next();
                Iterator<k1.a> it2 = this.c.iterator();
                if (it2.hasNext()) {
                    it2.next().getClass();
                    throw null;
                }
                it.remove();
                this.c.add(next);
                synchronized (this) {
                    if (this.a == null) {
                        this.a = new ThreadPoolExecutor(1, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 60L, TimeUnit.SECONDS, new SynchronousQueue(), new y(this));
                    }
                    executorService = this.a;
                }
                executorService.execute(next);
                if (this.c.size() >= 64) {
                    return;
                }
            }
        }
    }

    public final <T> void a(Deque<T> deque, T t, boolean z) {
        synchronized (this) {
            if (!deque.remove(t)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            if (z) {
                a();
            }
        }
    }
}
