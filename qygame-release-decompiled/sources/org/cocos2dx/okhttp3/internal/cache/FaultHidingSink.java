package org.cocos2dx.okhttp3.internal.cache;

import java.io.IOException;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.ForwardingSink;
import org.cocos2dx.okio.Sink;

/* JADX INFO: loaded from: classes.dex */
class FaultHidingSink extends ForwardingSink {
    private boolean hasErrors;

    FaultHidingSink(Sink sink) {
        super(sink);
    }

    protected void a(IOException iOException) {
    }

    @Override // org.cocos2dx.okio.ForwardingSink, org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.hasErrors) {
            return;
        }
        try {
            super.close();
        } catch (IOException e) {
            this.hasErrors = true;
            a(e);
        }
    }

    @Override // org.cocos2dx.okio.ForwardingSink, org.cocos2dx.okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        if (this.hasErrors) {
            return;
        }
        try {
            super.flush();
        } catch (IOException e) {
            this.hasErrors = true;
            a(e);
        }
    }

    @Override // org.cocos2dx.okio.ForwardingSink, org.cocos2dx.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        if (this.hasErrors) {
            buffer.skip(j);
            return;
        }
        try {
            super.write(buffer, j);
        } catch (IOException e) {
            this.hasErrors = true;
            a(e);
        }
    }
}
