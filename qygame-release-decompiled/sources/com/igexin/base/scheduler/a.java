package com.igexin.base.scheduler;

import android.text.TextUtils;
import com.igexin.base.scheduler.b;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class a implements b.InterfaceC0028b, c {
    private Map<BaseTask, ScheduledFuture> b = new ConcurrentHashMap();
    private final Set<String> c = new HashSet();
    private final Map<String, LinkedBlockingQueue<BaseTask>> d = new HashMap();
    private b a = new b();

    public a() {
        this.a.a = this;
    }

    private ScheduledFuture b(BaseTask baseTask) {
        ScheduledFuture<?> scheduledFutureSchedule;
        ScheduledFuture scheduledFuture = this.b.get(baseTask);
        if (scheduledFuture != null) {
            if (scheduledFuture.cancel(false)) {
                this.b.remove(baseTask);
            }
            BlockingQueue<Runnable> queue = this.a.getQueue();
            if (queue != null) {
                queue.remove(scheduledFuture);
            }
        }
        long initDelay = baseTask.getInitDelay();
        if (baseTask.isPeriodic()) {
            long period = baseTask.getPeriod();
            scheduledFutureSchedule = period > 0 ? this.a.scheduleAtFixedRate(baseTask, initDelay, period, TimeUnit.MILLISECONDS) : this.a.scheduleWithFixedDelay(baseTask, initDelay, -period, TimeUnit.MILLISECONDS);
        } else {
            scheduledFutureSchedule = this.a.schedule(baseTask, initDelay, TimeUnit.MILLISECONDS);
        }
        this.b.put(baseTask, scheduledFutureSchedule);
        return scheduledFutureSchedule;
    }

    @Override // com.igexin.base.scheduler.b.InterfaceC0028b
    public final synchronized void a(BaseTask baseTask) {
        try {
            this.b.remove(baseTask);
            String groupName = baseTask.getGroupName();
            if (!TextUtils.isEmpty(groupName)) {
                LinkedBlockingQueue<BaseTask> linkedBlockingQueue = this.d.get(groupName);
                if (linkedBlockingQueue != null && linkedBlockingQueue.size() > 0) {
                    b(linkedBlockingQueue.poll());
                    return;
                }
                this.c.remove(groupName);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.igexin.base.scheduler.c
    public final void execute(BaseTask baseTask) {
        try {
            baseTask.onRunTask();
            baseTask.done();
        } catch (Throwable th) {
            baseTask.onException(th);
        }
    }

    @Override // com.igexin.base.scheduler.c
    public final synchronized void submit(BaseTask baseTask) {
        try {
            String groupName = baseTask.getGroupName();
            boolean zOffer = false;
            if (!TextUtils.isEmpty(groupName)) {
                if (this.c.contains(groupName)) {
                    if (this.d.get(groupName) == null) {
                        this.d.put(groupName, new LinkedBlockingQueue<>());
                    }
                    zOffer = this.d.get(groupName).offer(baseTask);
                } else {
                    this.c.add(groupName);
                }
            }
            if (zOffer) {
                return;
            }
            b(baseTask);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
