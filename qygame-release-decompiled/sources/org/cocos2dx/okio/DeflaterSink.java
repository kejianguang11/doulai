package org.cocos2dx.okio;

import java.io.IOException;
import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* JADX INFO: loaded from: classes.dex */
public final class DeflaterSink implements Sink {
    private boolean closed;
    private final Deflater deflater;
    private final BufferedSink sink;

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.sink = bufferedSink;
        this.deflater = deflater;
    }

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    @IgnoreJRERequirement
    private void deflate(boolean z) throws IOException {
        Segment segmentWritableSegment;
        Buffer buffer = this.sink.buffer();
        while (true) {
            segmentWritableSegment = buffer.writableSegment(1);
            int iDeflate = z ? this.deflater.deflate(segmentWritableSegment.a, segmentWritableSegment.c, 8192 - segmentWritableSegment.c, 2) : this.deflater.deflate(segmentWritableSegment.a, segmentWritableSegment.c, 8192 - segmentWritableSegment.c);
            if (iDeflate > 0) {
                segmentWritableSegment.c += iDeflate;
                buffer.size += (long) iDeflate;
                this.sink.emitCompleteSegments();
            } else if (this.deflater.needsInput()) {
                break;
            }
        }
        if (segmentWritableSegment.b == segmentWritableSegment.c) {
            buffer.head = segmentWritableSegment.pop();
            SegmentPool.a(segmentWritableSegment);
        }
    }

    @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        Throwable th = null;
        try {
            finishDeflate();
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            this.deflater.end();
        } catch (Throwable th3) {
            if (th == null) {
                th = th3;
            }
        }
        try {
            this.sink.close();
        } catch (Throwable th4) {
            if (th == null) {
                th = th4;
            }
        }
        this.closed = true;
        if (th != null) {
            Util.sneakyRethrow(th);
        }
    }

    void finishDeflate() throws IOException {
        this.deflater.finish();
        deflate(false);
    }

    @Override // org.cocos2dx.okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        deflate(true);
        this.sink.flush();
    }

    @Override // org.cocos2dx.okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "DeflaterSink(" + this.sink + ")";
    }

    @Override // org.cocos2dx.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        Util.checkOffsetAndCount(buffer.size, 0L, j);
        while (j > 0) {
            Segment segment = buffer.head;
            int iMin = (int) Math.min(j, segment.c - segment.b);
            this.deflater.setInput(segment.a, segment.b, iMin);
            deflate(false);
            long j2 = iMin;
            buffer.size -= j2;
            segment.b += iMin;
            if (segment.b == segment.c) {
                buffer.head = segment.pop();
                SegmentPool.a(segment);
            }
            j -= j2;
        }
    }
}
