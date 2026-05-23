package org.cocos2dx.okio;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForwardingSource implements Source {
    private final Source delegate;

    public ForwardingSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = source;
    }

    @Override // org.cocos2dx.okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.delegate.close();
    }

    public final Source delegate() {
        return this.delegate;
    }

    @Override // org.cocos2dx.okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        return this.delegate.read(buffer, j);
    }

    @Override // org.cocos2dx.okio.Source
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
