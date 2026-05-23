package com.nirvana.tools.requestqueue;

/* JADX INFO: loaded from: classes.dex */
public abstract class TimeoutResponse implements Response {
    private boolean isTimeout;

    public TimeoutResponse(boolean z) {
        this.isTimeout = false;
        this.isTimeout = z;
    }

    public abstract boolean isResultTimeout();

    @Override // com.nirvana.tools.requestqueue.Response
    public final boolean isTimeout() {
        if (this.isTimeout) {
            return true;
        }
        return isResultTimeout();
    }

    public void setTimeout(boolean z) {
        this.isTimeout = z;
    }
}
