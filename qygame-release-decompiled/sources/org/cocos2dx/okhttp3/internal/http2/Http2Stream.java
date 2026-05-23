package org.cocos2dx.okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.cocos2dx.okhttp3.Headers;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okhttp3.internal.http2.Header;
import org.cocos2dx.okio.AsyncTimeout;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.BufferedSource;
import org.cocos2dx.okio.Sink;
import org.cocos2dx.okio.Source;
import org.cocos2dx.okio.Timeout;

/* JADX INFO: loaded from: classes.dex */
public final class Http2Stream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    final Http2Connection connection;
    private boolean hasResponseHeaders;
    private Header.Listener headersListener;
    final int id;
    final FramingSink sink;
    private final FramingSource source;
    long unacknowledgedBytesRead = 0;
    private final Deque<Headers> headersQueue = new ArrayDeque();
    final StreamTimeout readTimeout = new StreamTimeout();
    final StreamTimeout writeTimeout = new StreamTimeout();
    ErrorCode errorCode = null;

    final class FramingSink implements Sink {
        private static final long EMIT_BUFFER_SIZE = 16384;
        static final /* synthetic */ boolean c = !Http2Stream.class.desiredAssertionStatus();
        boolean a;
        boolean b;
        private final Buffer sendBuffer = new Buffer();

        FramingSink() {
        }

        private void emitFrame(boolean z) throws IOException {
            long jMin;
            synchronized (Http2Stream.this) {
                Http2Stream.this.writeTimeout.enter();
                while (Http2Stream.this.bytesLeftInWriteWindow <= 0 && !this.b && !this.a && Http2Stream.this.errorCode == null) {
                    try {
                        Http2Stream.this.waitForIo();
                    } finally {
                    }
                }
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                Http2Stream.this.checkOutNotClosed();
                jMin = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                Http2Stream.this.bytesLeftInWriteWindow -= jMin;
            }
            Http2Stream.this.writeTimeout.enter();
            try {
                Http2Stream.this.connection.writeData(Http2Stream.this.id, z && jMin == this.sendBuffer.size(), this.sendBuffer, jMin);
            } finally {
            }
        }

        @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (!c && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            synchronized (Http2Stream.this) {
                if (this.a) {
                    return;
                }
                if (!Http2Stream.this.sink.b) {
                    if (this.sendBuffer.size() > 0) {
                        while (this.sendBuffer.size() > 0) {
                            emitFrame(true);
                        }
                    } else {
                        Http2Stream.this.connection.writeData(Http2Stream.this.id, true, null, 0L);
                    }
                }
                synchronized (Http2Stream.this) {
                    this.a = true;
                }
                Http2Stream.this.connection.flush();
                Http2Stream.this.cancelStreamIfNecessary();
            }
        }

        @Override // org.cocos2dx.okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (!c && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            synchronized (Http2Stream.this) {
                Http2Stream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitFrame(false);
                Http2Stream.this.connection.flush();
            }
        }

        @Override // org.cocos2dx.okio.Sink
        public Timeout timeout() {
            return Http2Stream.this.writeTimeout;
        }

        @Override // org.cocos2dx.okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            if (!c && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            this.sendBuffer.write(buffer, j);
            while (this.sendBuffer.size() >= EMIT_BUFFER_SIZE) {
                emitFrame(false);
            }
        }
    }

    private final class FramingSource implements Source {
        static final /* synthetic */ boolean c = !Http2Stream.class.desiredAssertionStatus();
        boolean a;
        boolean b;
        private final long maxByteCount;
        private final Buffer receiveBuffer = new Buffer();
        private final Buffer readBuffer = new Buffer();

        FramingSource(long j) {
            this.maxByteCount = j;
        }

        private void updateConnectionFlowControl(long j) {
            if (!c && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            Http2Stream.this.connection.updateConnectionFlowControl(j);
        }

        void a(BufferedSource bufferedSource, long j) throws IOException {
            boolean z;
            boolean z2;
            long size;
            if (!c && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            while (j > 0) {
                synchronized (Http2Stream.this) {
                    z = this.b;
                    z2 = this.readBuffer.size() + j > this.maxByteCount;
                }
                if (z2) {
                    bufferedSource.skip(j);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (z) {
                    bufferedSource.skip(j);
                    return;
                }
                long j2 = bufferedSource.read(this.receiveBuffer, j);
                if (j2 == -1) {
                    throw new EOFException();
                }
                j -= j2;
                synchronized (Http2Stream.this) {
                    if (this.a) {
                        size = this.receiveBuffer.size();
                        this.receiveBuffer.clear();
                    } else {
                        boolean z3 = this.readBuffer.size() == 0;
                        this.readBuffer.writeAll(this.receiveBuffer);
                        if (z3) {
                            Http2Stream.this.notifyAll();
                        }
                        size = 0;
                    }
                }
                if (size > 0) {
                    updateConnectionFlowControl(size);
                }
            }
        }

        @Override // org.cocos2dx.okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            ArrayList arrayList;
            Header.Listener listener;
            synchronized (Http2Stream.this) {
                this.a = true;
                size = this.readBuffer.size();
                this.readBuffer.clear();
                arrayList = null;
                if (Http2Stream.this.headersQueue.isEmpty() || Http2Stream.this.headersListener == null) {
                    listener = null;
                } else {
                    arrayList = new ArrayList(Http2Stream.this.headersQueue);
                    Http2Stream.this.headersQueue.clear();
                    listener = Http2Stream.this.headersListener;
                }
                Http2Stream.this.notifyAll();
            }
            if (size > 0) {
                updateConnectionFlowControl(size);
            }
            Http2Stream.this.cancelStreamIfNecessary();
            if (listener != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    listener.onHeaders((Headers) it.next());
                }
            }
        }

        @Override // org.cocos2dx.okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            ErrorCode errorCode;
            long j2;
            Headers headers;
            Header.Listener listener;
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            }
            while (true) {
                synchronized (Http2Stream.this) {
                    Http2Stream.this.readTimeout.enter();
                    try {
                        errorCode = Http2Stream.this.errorCode != null ? Http2Stream.this.errorCode : null;
                        if (this.a) {
                            throw new IOException("stream closed");
                        }
                        if (Http2Stream.this.headersQueue.isEmpty() || Http2Stream.this.headersListener == null) {
                            if (this.readBuffer.size() > 0) {
                                j2 = this.readBuffer.read(buffer, Math.min(j, this.readBuffer.size()));
                                Http2Stream.this.unacknowledgedBytesRead += j2;
                                if (errorCode == null && Http2Stream.this.unacknowledgedBytesRead >= Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2) {
                                    Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.id, Http2Stream.this.unacknowledgedBytesRead);
                                    Http2Stream.this.unacknowledgedBytesRead = 0L;
                                }
                            } else if (this.b || errorCode != null) {
                                j2 = -1;
                            } else {
                                Http2Stream.this.waitForIo();
                                Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                            }
                            headers = null;
                            listener = null;
                        } else {
                            headers = (Headers) Http2Stream.this.headersQueue.removeFirst();
                            listener = Http2Stream.this.headersListener;
                            j2 = -1;
                        }
                        if (headers == null || listener == null) {
                            break;
                        }
                        listener.onHeaders(headers);
                    } finally {
                    }
                }
            }
            if (j2 != -1) {
                updateConnectionFlowControl(j2);
                return j2;
            }
            if (errorCode == null) {
                return -1L;
            }
            throw new StreamResetException(errorCode);
        }

        @Override // org.cocos2dx.okio.Source
        public Timeout timeout() {
            return Http2Stream.this.readTimeout;
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }

        @Override // org.cocos2dx.okio.AsyncTimeout
        protected IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override // org.cocos2dx.okio.AsyncTimeout
        protected void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
        }
    }

    Http2Stream(int i, Http2Connection http2Connection, boolean z, boolean z2, @Nullable Headers headers) {
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        }
        this.id = i;
        this.connection = http2Connection;
        this.bytesLeftInWriteWindow = http2Connection.peerSettings.getInitialWindowSize();
        this.source = new FramingSource(http2Connection.okHttpSettings.getInitialWindowSize());
        this.sink = new FramingSink();
        this.source.b = z2;
        this.sink.b = z;
        if (headers != null) {
            this.headersQueue.add(headers);
        }
        if (isLocallyInitiated() && headers != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
        }
        if (!isLocallyInitiated() && headers == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.b && this.sink.b) {
                return false;
            }
            this.errorCode = errorCode;
            notifyAll();
            this.connection.removeStream(this.id);
            return true;
        }
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if (j > 0) {
            notifyAll();
        }
    }

    void cancelStreamIfNecessary() throws IOException {
        boolean z;
        boolean zIsOpen;
        synchronized (this) {
            z = !this.source.b && this.source.a && (this.sink.b || this.sink.a);
            zIsOpen = isOpen();
        }
        if (z) {
            close(ErrorCode.CANCEL);
        } else {
            if (zIsOpen) {
                return;
            }
            this.connection.removeStream(this.id);
        }
    }

    void checkOutNotClosed() throws IOException {
        if (this.sink.a) {
            throw new IOException("stream closed");
        }
        if (this.sink.b) {
            throw new IOException("stream finished");
        }
        if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (closeInternal(errorCode)) {
            this.connection.writeSynReset(this.id, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    public Http2Connection getConnection() {
        return this.connection;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public int getId() {
        return this.id;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.hasResponseHeaders && !isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }

    public Source getSource() {
        return this.source;
    }

    public boolean isLocallyInitiated() {
        return this.connection.client == ((this.id & 1) == 1);
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.b || this.source.a) && (this.sink.b || this.sink.a)) {
            if (this.hasResponseHeaders) {
                return false;
            }
        }
        return true;
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    void receiveData(BufferedSource bufferedSource, int i) throws IOException {
        this.source.a(bufferedSource, i);
    }

    void receiveFin() {
        boolean zIsOpen;
        synchronized (this) {
            this.source.b = true;
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.connection.removeStream(this.id);
    }

    void receiveHeaders(List<Header> list) {
        boolean zIsOpen;
        synchronized (this) {
            this.hasResponseHeaders = true;
            this.headersQueue.add(Util.toHeaders(list));
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.connection.removeStream(this.id);
    }

    synchronized void receiveRstStream(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    public synchronized void setHeadersListener(Header.Listener listener) {
        this.headersListener = listener;
        if (!this.headersQueue.isEmpty() && listener != null) {
            notifyAll();
        }
    }

    public synchronized Headers takeHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.headersQueue.isEmpty() && this.errorCode == null) {
            try {
                waitForIo();
            } catch (Throwable th) {
                this.readTimeout.exitAndThrowIfTimedOut();
                throw th;
            }
        }
        this.readTimeout.exitAndThrowIfTimedOut();
        if (this.headersQueue.isEmpty()) {
            throw new StreamResetException(this.errorCode);
        }
        return this.headersQueue.removeFirst();
    }

    void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    public void writeHeaders(List<Header> list, boolean z) throws IOException {
        boolean z2;
        boolean z3;
        if (list == null) {
            throw new NullPointerException("headers == null");
        }
        synchronized (this) {
            this.hasResponseHeaders = true;
            if (z) {
                z2 = false;
            } else {
                this.sink.b = true;
                z2 = true;
            }
            z3 = z2;
        }
        if (!z2) {
            synchronized (this.connection) {
                z2 = this.connection.bytesLeftInWriteWindow == 0;
            }
        }
        this.connection.writeSynReply(this.id, z3, list);
        if (z2) {
            this.connection.flush();
        }
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }
}
