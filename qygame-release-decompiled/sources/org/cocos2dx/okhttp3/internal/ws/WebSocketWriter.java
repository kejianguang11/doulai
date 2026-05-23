package org.cocos2dx.okhttp3.internal.ws;

import java.io.IOException;
import java.util.Random;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.BufferedSink;
import org.cocos2dx.okio.ByteString;
import org.cocos2dx.okio.Sink;
import org.cocos2dx.okio.Timeout;

/* JADX INFO: loaded from: classes.dex */
final class WebSocketWriter {
    final boolean a;
    final Random b;
    final BufferedSink c;
    final Buffer d;
    boolean e;
    final Buffer f = new Buffer();
    final FrameSink g = new FrameSink();
    boolean h;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;

    final class FrameSink implements Sink {
        int a;
        long b;
        boolean c;
        boolean d;

        FrameSink() {
        }

        @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.a(this.a, WebSocketWriter.this.f.size(), this.c, true);
            this.d = true;
            WebSocketWriter.this.h = false;
        }

        @Override // org.cocos2dx.okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.a(this.a, WebSocketWriter.this.f.size(), this.c, false);
            this.c = false;
        }

        @Override // org.cocos2dx.okio.Sink
        public Timeout timeout() {
            return WebSocketWriter.this.c.timeout();
        }

        @Override // org.cocos2dx.okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.f.write(buffer, j);
            boolean z = this.c && this.b != -1 && WebSocketWriter.this.f.size() > this.b - 8192;
            long jCompleteSegmentByteCount = WebSocketWriter.this.f.completeSegmentByteCount();
            if (jCompleteSegmentByteCount <= 0 || z) {
                return;
            }
            WebSocketWriter.this.a(this.a, jCompleteSegmentByteCount, this.c, false);
            this.c = false;
        }
    }

    WebSocketWriter(boolean z, BufferedSink bufferedSink, Random random) {
        if (bufferedSink == null) {
            throw new NullPointerException("sink == null");
        }
        if (random == null) {
            throw new NullPointerException("random == null");
        }
        this.a = z;
        this.c = bufferedSink;
        this.d = bufferedSink.buffer();
        this.b = random;
        this.maskKey = z ? new byte[4] : null;
        this.maskCursor = z ? new Buffer.UnsafeCursor() : null;
    }

    private void writeControlFrame(int i, ByteString byteString) throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        int size = byteString.size();
        if (size > 125) {
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        this.d.writeByte(i | 128);
        if (this.a) {
            this.d.writeByte(size | 128);
            this.b.nextBytes(this.maskKey);
            this.d.write(this.maskKey);
            if (size > 0) {
                long size2 = this.d.size();
                this.d.write(byteString);
                this.d.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(size2);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.d.writeByte(size);
            this.d.write(byteString);
        }
        this.c.flush();
    }

    Sink a(int i, long j) {
        if (this.h) {
            throw new IllegalStateException("Another message writer is active. Did you call close()?");
        }
        this.h = true;
        this.g.a = i;
        this.g.b = j;
        this.g.c = true;
        this.g.d = false;
        return this.g;
    }

    void a(int i, long j, boolean z, boolean z2) throws IOException {
        if (this.e) {
            throw new IOException("closed");
        }
        if (!z) {
            i = 0;
        }
        if (z2) {
            i |= 128;
        }
        this.d.writeByte(i);
        int i2 = this.a ? 128 : 0;
        if (j <= 125) {
            this.d.writeByte(((int) j) | i2);
        } else if (j <= 65535) {
            this.d.writeByte(i2 | 126);
            this.d.writeShort((int) j);
        } else {
            this.d.writeByte(i2 | 127);
            this.d.writeLong(j);
        }
        if (this.a) {
            this.b.nextBytes(this.maskKey);
            this.d.write(this.maskKey);
            if (j > 0) {
                long size = this.d.size();
                this.d.write(this.f, j);
                this.d.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(size);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.d.write(this.f, j);
        }
        this.c.emit();
    }

    void a(int i, ByteString byteString) throws IOException {
        ByteString byteString2 = ByteString.EMPTY;
        if (i != 0 || byteString != null) {
            if (i != 0) {
                WebSocketProtocol.validateCloseCode(i);
            }
            Buffer buffer = new Buffer();
            buffer.writeShort(i);
            if (byteString != null) {
                buffer.write(byteString);
            }
            byteString2 = buffer.readByteString();
        }
        try {
            writeControlFrame(8, byteString2);
        } finally {
            this.e = true;
        }
    }

    void a(ByteString byteString) throws IOException {
        writeControlFrame(9, byteString);
    }

    void b(ByteString byteString) throws IOException {
        writeControlFrame(10, byteString);
    }
}
