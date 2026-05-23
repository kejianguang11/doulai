package com.nirvana.tools.requestqueue;

import android.text.TextUtils;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public abstract class Request<T extends Response> {
    private static final long MIN_TIMEOUT_MILLS = 500;
    private TimeoutCallable<T> mAction;
    private Callback<T> mCallback;
    private CallbackStrategy mCallbackStrategy;
    private ExecuteStrategy mExecuteStrategy;
    private String mID;
    private String mResponseType;
    private ThreadStrategy mThreadStrategy;
    private long mTimeoutMills;

    public Request(Callback<T> callback, TimeoutCallable<T> timeoutCallable, ThreadStrategy threadStrategy, ExecuteStrategy executeStrategy, CallbackStrategy callbackStrategy, long j, Class<T> cls) {
        this.mTimeoutMills = 500L;
        this.mCallback = callback;
        this.mAction = timeoutCallable;
        this.mThreadStrategy = threadStrategy;
        this.mExecuteStrategy = executeStrategy;
        this.mCallbackStrategy = callbackStrategy;
        if (j > this.mTimeoutMills) {
            this.mTimeoutMills = j;
        }
        this.mResponseType = cls.getName();
    }

    public TimeoutCallable<T> getAction() {
        return this.mAction;
    }

    public Callback<T> getCallback() {
        return this.mCallback;
    }

    public CallbackStrategy getCallbackStrategy() {
        return this.mCallbackStrategy;
    }

    public ExecuteStrategy getExecuteStrategy() {
        return this.mExecuteStrategy;
    }

    public String getID() {
        if (TextUtils.isEmpty(this.mID)) {
            this.mID = getKey() + this.mResponseType;
        }
        return this.mID;
    }

    protected abstract String getKey();

    public ThreadStrategy getThreadStrategy() {
        return this.mThreadStrategy;
    }

    public long getTimeout() {
        return this.mTimeoutMills;
    }

    public void setTimeout(long j) {
        this.mTimeoutMills = j;
    }
}
