package com.nirvana.tools.requestqueue;

import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public abstract class Callback<T extends Response> {
    protected long mExpiredTime = 0;
    protected ThreadStrategy mThreadStrategy;
    protected long mThreshold;

    public Callback(ThreadStrategy threadStrategy, long j) {
        this.mThreadStrategy = null;
        this.mThreshold = 0L;
        this.mThreadStrategy = threadStrategy;
        this.mThreshold = j;
    }

    long getExpiredTime() {
        return this.mExpiredTime;
    }

    ThreadStrategy getThreadStrategy() {
        return this.mThreadStrategy;
    }

    long getThreshold() {
        return this.mThreshold;
    }

    protected abstract void onResult(T t);

    void setExpiredTime(long j) {
        this.mExpiredTime = j;
    }
}
