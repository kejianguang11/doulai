package org.cocos2dx.okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* JADX INFO: loaded from: classes.dex */
public final class Okio {
    static final Logger logger = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink blackhole() {
        return new Sink() { // from class: org.cocos2dx.okio.Okio.3
            @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
            }

            @Override // org.cocos2dx.okio.Sink, java.io.Flushable
            public void flush() throws IOException {
            }

            @Override // org.cocos2dx.okio.Sink
            public Timeout timeout() {
                return Timeout.NONE;
            }

            @Override // org.cocos2dx.okio.Sink
            public void write(Buffer buffer, long j) throws IOException {
                buffer.skip(j);
            }
        };
    }

    public static BufferedSink buffer(Sink sink) {
        return new RealBufferedSink(sink);
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink sink(OutputStream outputStream) {
        return sink(outputStream, new Timeout());
    }

    private static Sink sink(final OutputStream outputStream, final Timeout timeout) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (timeout != null) {
            return new Sink() { // from class: org.cocos2dx.okio.Okio.1
                @Override // org.cocos2dx.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    outputStream.close();
                }

                @Override // org.cocos2dx.okio.Sink, java.io.Flushable
                public void flush() throws IOException {
                    outputStream.flush();
                }

                @Override // org.cocos2dx.okio.Sink
                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    return "sink(" + outputStream + ")";
                }

                @Override // org.cocos2dx.okio.Sink
                public void write(Buffer buffer, long j) throws IOException {
                    Util.checkOffsetAndCount(buffer.size, 0L, j);
                    while (j > 0) {
                        timeout.throwIfReached();
                        Segment segment = buffer.head;
                        int iMin = (int) Math.min(j, segment.c - segment.b);
                        outputStream.write(segment.a, segment.b, iMin);
                        segment.b += iMin;
                        long j2 = iMin;
                        j -= j2;
                        buffer.size -= j2;
                        if (segment.b == segment.c) {
                            buffer.head = segment.pop();
                            SegmentPool.a(segment);
                        }
                    }
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getOutputStream() == null) {
            throw new IOException("socket's output stream == null");
        }
        AsyncTimeout asyncTimeoutTimeout = timeout(socket);
        return asyncTimeoutTimeout.sink(sink(socket.getOutputStream(), asyncTimeoutTimeout));
    }

    @IgnoreJRERequirement
    public static Sink sink(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return sink(Files.newOutputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Source source(InputStream inputStream) {
        return source(inputStream, new Timeout());
    }

    private static Source source(final InputStream inputStream, final Timeout timeout) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout != null) {
            return new Source() { // from class: org.cocos2dx.okio.Okio.2
                @Override // org.cocos2dx.okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    inputStream.close();
                }

                @Override // org.cocos2dx.okio.Source
                public long read(Buffer buffer, long j) throws IOException {
                    if (j < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + j);
                    }
                    if (j == 0) {
                        return 0L;
                    }
                    try {
                        timeout.throwIfReached();
                        Segment segmentWritableSegment = buffer.writableSegment(1);
                        int i = inputStream.read(segmentWritableSegment.a, segmentWritableSegment.c, (int) Math.min(j, 8192 - segmentWritableSegment.c));
                        if (i == -1) {
                            return -1L;
                        }
                        segmentWritableSegment.c += i;
                        long j2 = i;
                        buffer.size += j2;
                        return j2;
                    } catch (AssertionError e) {
                        if (Okio.isAndroidGetsocknameError(e)) {
                            throw new IOException(e);
                        }
                        throw e;
                    }
                }

                @Override // org.cocos2dx.okio.Source
                public Timeout timeout() {
                    return timeout;
                }

                public String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getInputStream() == null) {
            throw new IOException("socket's input stream == null");
        }
        AsyncTimeout asyncTimeoutTimeout = timeout(socket);
        return asyncTimeoutTimeout.source(source(socket.getInputStream(), asyncTimeoutTimeout));
    }

    @IgnoreJRERequirement
    public static Source source(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return source(Files.newInputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }

    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout() { // from class: org.cocos2dx.okio.Okio.4
            @Override // org.cocos2dx.okio.AsyncTimeout
            protected IOException newTimeoutException(@Nullable IOException iOException) {
                SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            }

            @Override // org.cocos2dx.okio.AsyncTimeout
            protected void timedOut() {
                Level level;
                StringBuilder sb;
                Logger logger2;
                Throwable th;
                try {
                    socket.close();
                } catch (AssertionError e) {
                    if (!Okio.isAndroidGetsocknameError(e)) {
                        throw e;
                    }
                    Logger logger3 = Okio.logger;
                    level = Level.WARNING;
                    sb = new StringBuilder();
                    th = e;
                    logger2 = logger3;
                    sb.append("Failed to close timed out socket ");
                    sb.append(socket);
                    logger2.log(level, sb.toString(), th);
                } catch (Exception e2) {
                    Logger logger4 = Okio.logger;
                    level = Level.WARNING;
                    sb = new StringBuilder();
                    th = e2;
                    logger2 = logger4;
                    sb.append("Failed to close timed out socket ");
                    sb.append(socket);
                    logger2.log(level, sb.toString(), th);
                }
            }
        };
    }
}
