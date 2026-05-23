package org.cocos2dx.okio;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForwardingSink implements Sink {
    private final Sink delegate;

    public ForwardingSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = sink;
    }

    @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.delegate.close();
    }

    public final Sink delegate() {
        return this.delegate;
    }

    @Override // org.cocos2dx.okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override // org.cocos2dx.okio.Sink
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    @Override // org.cocos2dx.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        this.delegate.write(buffer, j);
    }
}
