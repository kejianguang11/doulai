package org.cocos2dx.okhttp3.internal.cache2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.ByteString;
import org.cocos2dx.okio.Source;
import org.cocos2dx.okio.Timeout;

/* JADX INFO: loaded from: classes.dex */
final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    static final ByteString a = ByteString.encodeUtf8("OkHttp cache v1\n");
    static final ByteString b = ByteString.encodeUtf8("OkHttp DIRTY :(\n");
    RandomAccessFile c;
    Thread d;
    Source e;
    long g;
    boolean h;
    final long j;
    int k;
    private final ByteString metadata;
    final Buffer f = new Buffer();
    final Buffer i = new Buffer();

    class RelaySource implements Source {
        private FileOperator fileOperator;
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        RelaySource() {
            this.fileOperator = new FileOperator(Relay.this.c.getChannel());
        }

        @Override // org.cocos2dx.okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            RandomAccessFile randomAccessFile = null;
            this.fileOperator = null;
            synchronized (Relay.this) {
                Relay relay = Relay.this;
                relay.k--;
                if (Relay.this.k == 0) {
                    RandomAccessFile randomAccessFile2 = Relay.this.c;
                    Relay.this.c = null;
                    randomAccessFile = randomAccessFile2;
                }
            }
            if (randomAccessFile != null) {
                Util.closeQuietly(randomAccessFile);
            }
        }

        @Override // org.cocos2dx.okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            long j2;
            char c;
            if (this.fileOperator == null) {
                throw new IllegalStateException("closed");
            }
            synchronized (Relay.this) {
                while (true) {
                    long j3 = this.sourcePos;
                    j2 = Relay.this.g;
                    if (j3 != j2) {
                        long size = j2 - Relay.this.i.size();
                        if (this.sourcePos >= size) {
                            long jMin = Math.min(j, j2 - this.sourcePos);
                            Relay.this.i.copyTo(buffer, this.sourcePos - size, jMin);
                            this.sourcePos += jMin;
                            return jMin;
                        }
                        c = 2;
                    } else if (!Relay.this.h) {
                        if (Relay.this.d == null) {
                            Relay.this.d = Thread.currentThread();
                            c = 1;
                            break;
                        }
                        this.timeout.waitUntilNotified(Relay.this);
                    } else {
                        return -1L;
                    }
                }
                if (c == 2) {
                    long jMin2 = Math.min(j, j2 - this.sourcePos);
                    this.fileOperator.read(this.sourcePos + 32, buffer, jMin2);
                    this.sourcePos += jMin2;
                    return jMin2;
                }
                try {
                    long j4 = Relay.this.e.read(Relay.this.f, Relay.this.j);
                    if (j4 == -1) {
                        Relay.this.a(j2);
                        synchronized (Relay.this) {
                            Relay.this.d = null;
                            Relay.this.notifyAll();
                        }
                        return -1L;
                    }
                    long jMin3 = Math.min(j4, j);
                    Relay.this.f.copyTo(buffer, 0L, jMin3);
                    this.sourcePos += jMin3;
                    this.fileOperator.write(j2 + 32, Relay.this.f.clone(), j4);
                    synchronized (Relay.this) {
                        Relay.this.i.write(Relay.this.f, j4);
                        if (Relay.this.i.size() > Relay.this.j) {
                            Relay.this.i.skip(Relay.this.i.size() - Relay.this.j);
                        }
                        Relay.this.g += j4;
                    }
                    synchronized (Relay.this) {
                        Relay.this.d = null;
                        Relay.this.notifyAll();
                    }
                    return jMin3;
                } catch (Throwable th) {
                    synchronized (Relay.this) {
                        Relay.this.d = null;
                        Relay.this.notifyAll();
                        throw th;
                    }
                }
            }
        }

        @Override // org.cocos2dx.okio.Source
        public Timeout timeout() {
            return this.timeout;
        }
    }

    private Relay(RandomAccessFile randomAccessFile, Source source, long j, ByteString byteString, long j2) {
        this.c = randomAccessFile;
        this.e = source;
        this.h = source == null;
        this.g = j;
        this.metadata = byteString;
        this.j = j2;
    }

    public static Relay edit(File file, Source source, ByteString byteString, long j) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        Relay relay = new Relay(randomAccessFile, source, 0L, byteString, j);
        randomAccessFile.setLength(0L);
        relay.writeHeader(b, -1L, -1L);
        return relay;
    }

    public static Relay read(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
        Buffer buffer = new Buffer();
        fileOperator.read(0L, buffer, 32L);
        if (!buffer.readByteString(a.size()).equals(a)) {
            throw new IOException("unreadable cache file");
        }
        long j = buffer.readLong();
        long j2 = buffer.readLong();
        Buffer buffer2 = new Buffer();
        fileOperator.read(j + 32, buffer2, j2);
        return new Relay(randomAccessFile, null, j, buffer2.readByteString(), 0L);
    }

    private void writeHeader(ByteString byteString, long j, long j2) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(byteString);
        buffer.writeLong(j);
        buffer.writeLong(j2);
        if (buffer.size() != 32) {
            throw new IllegalArgumentException();
        }
        new FileOperator(this.c.getChannel()).write(0L, buffer, 32L);
    }

    private void writeMetadata(long j) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(this.metadata);
        new FileOperator(this.c.getChannel()).write(32 + j, buffer, this.metadata.size());
    }

    void a(long j) throws IOException {
        writeMetadata(j);
        this.c.getChannel().force(false);
        writeHeader(a, j, this.metadata.size());
        this.c.getChannel().force(false);
        synchronized (this) {
            this.h = true;
        }
        Util.closeQuietly(this.e);
        this.e = null;
    }

    public ByteString metadata() {
        return this.metadata;
    }

    public Source newSource() {
        synchronized (this) {
            if (this.c == null) {
                return null;
            }
            this.k++;
            return new RelaySource();
        }
    }
}
