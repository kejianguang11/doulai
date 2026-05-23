package org.cocos2dx.okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class RealBufferedSource implements BufferedSource {
    boolean a;
    public final Buffer buffer = new Buffer();
    public final Source source;

    RealBufferedSource(Source source) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        this.source = source;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable, org.cocos2dx.okio.Source
    public void close() throws IOException {
        if (this.a) {
            return;
        }
        this.a = true;
        this.source.close();
        this.buffer.clear();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean exhausted() throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        return this.buffer.exhausted() && this.source.read(this.buffer, 8192L) == -1;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(byte b) throws IOException {
        return indexOf(b, 0L, Long.MAX_VALUE);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(byte b, long j) throws IOException {
        return indexOf(b, j, Long.MAX_VALUE);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(byte b, long j, long j2) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", Long.valueOf(j), Long.valueOf(j2)));
        }
        while (j < j2) {
            long jIndexOf = this.buffer.indexOf(b, j, j2);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long j3 = this.buffer.size;
            if (j3 >= j2 || this.source.read(this.buffer, 8192L) == -1) {
                break;
            }
            j = Math.max(j, j3);
        }
        return -1L;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0L);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(ByteString byteString, long j) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOf = this.buffer.indexOf(byteString, j);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long j2 = this.buffer.size;
            if (this.source.read(this.buffer, 8192L) == -1) {
                return -1L;
            }
            j = Math.max(j, (j2 - ((long) byteString.size())) + 1);
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOfElement(ByteString byteString) throws IOException {
        return indexOfElement(byteString, 0L);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOfElement = this.buffer.indexOfElement(byteString, j);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            long j2 = this.buffer.size;
            if (this.source.read(this.buffer, 8192L) == -1) {
                return -1L;
            }
            j = Math.max(j, j2);
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: org.cocos2dx.okio.RealBufferedSource.1
            @Override // java.io.InputStream
            public int available() throws IOException {
                if (RealBufferedSource.this.a) {
                    throw new IOException("closed");
                }
                return (int) Math.min(RealBufferedSource.this.buffer.size, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (RealBufferedSource.this.a) {
                    throw new IOException("closed");
                }
                if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.readByte() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                if (RealBufferedSource.this.a) {
                    throw new IOException("closed");
                }
                Util.checkOffsetAndCount(bArr.length, i, i2);
                if (RealBufferedSource.this.buffer.size == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.read(bArr, i, i2);
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.a;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString) throws IOException {
        return rangeEquals(j, byteString, 0, byteString.size());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || i < 0 || i2 < 0 || byteString.size() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            long j2 = ((long) i3) + j;
            if (!request(1 + j2) || this.buffer.getByte(j2) != byteString.getByte(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        if (this.buffer.size == 0 && this.source.read(this.buffer, 8192L) == -1) {
            return -1;
        }
        return this.buffer.read(byteBuffer);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int read(byte[] bArr, int i, int i2) throws IOException {
        long j = i2;
        Util.checkOffsetAndCount(bArr.length, i, j);
        if (this.buffer.size == 0 && this.source.read(this.buffer, 8192L) == -1) {
            return -1;
        }
        return this.buffer.read(bArr, i, (int) Math.min(j, this.buffer.size));
    }

    @Override // org.cocos2dx.okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size == 0 && this.source.read(this.buffer, 8192L) == -1) {
            return -1L;
        }
        return this.buffer.read(buffer, Math.min(j, this.buffer.size));
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long j = 0;
        while (this.source.read(this.buffer, 8192L) != -1) {
            long jCompleteSegmentByteCount = this.buffer.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j += jCompleteSegmentByteCount;
                sink.write(this.buffer, jCompleteSegmentByteCount);
            }
        }
        if (this.buffer.size() <= 0) {
            return j;
        }
        long size = j + this.buffer.size();
        sink.write(this.buffer, this.buffer.size());
        return size;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte readByte() throws IOException {
        require(1L);
        return this.buffer.readByte();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte[] readByteArray() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteArray();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte[] readByteArray(long j) throws IOException {
        require(j);
        return this.buffer.readByteArray(j);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public ByteString readByteString() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteString();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public ByteString readByteString(long j) throws IOException {
        require(j);
        return this.buffer.readByteString(j);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readDecimalLong() throws IOException {
        byte b;
        require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request(i2)) {
                break;
            }
            b = this.buffer.getByte(i);
            if ((b < 48 || b > 57) && !(i == 0 && b == 45)) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", Byte.valueOf(b)));
        }
        return this.buffer.readDecimalLong();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void readFully(Buffer buffer, long j) throws IOException {
        try {
            require(j);
            this.buffer.readFully(buffer, j);
        } catch (EOFException e) {
            buffer.writeAll(this.buffer);
            throw e;
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void readFully(byte[] bArr) throws IOException {
        try {
            require(bArr.length);
            this.buffer.readFully(bArr);
        } catch (EOFException e) {
            int i = 0;
            while (this.buffer.size > 0) {
                int i2 = this.buffer.read(bArr, i, (int) this.buffer.size);
                if (i2 == -1) {
                    throw new AssertionError();
                }
                i += i2;
            }
            throw e;
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readHexadecimalUnsignedLong() throws IOException {
        byte b;
        require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request(i2)) {
                break;
            }
            b = this.buffer.getByte(i);
            if ((b < 48 || b > 57) && ((b < 97 || b > 102) && (b < 65 || b > 70))) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", Byte.valueOf(b)));
        }
        return this.buffer.readHexadecimalUnsignedLong();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readInt() throws IOException {
        require(4L);
        return this.buffer.readInt();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readIntLe() throws IOException {
        require(4L);
        return this.buffer.readIntLe();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readLong() throws IOException {
        require(8L);
        return this.buffer.readLong();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readLongLe() throws IOException {
        require(8L);
        return this.buffer.readLongLe();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public short readShort() throws IOException {
        require(2L);
        return this.buffer.readShort();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public short readShortLe() throws IOException {
        require(2L);
        return this.buffer.readShortLe();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readString(long j, Charset charset) throws IOException {
        require(j);
        if (charset != null) {
            return this.buffer.readString(j, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readString(Charset charset) throws IOException {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.buffer.writeAll(this.source);
        return this.buffer.readString(charset);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readUtf8();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8(long j) throws IOException {
        require(j);
        return this.buffer.readUtf8(j);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readUtf8CodePoint() throws IOException {
        long j;
        require(1L);
        byte b = this.buffer.getByte(0L);
        if ((b & 224) == 192) {
            j = 2;
        } else {
            if ((b & 240) != 224) {
                if ((b & 248) == 240) {
                    j = 4;
                }
                return this.buffer.readUtf8CodePoint();
            }
            j = 3;
        }
        require(j);
        return this.buffer.readUtf8CodePoint();
    }

    @Override // org.cocos2dx.okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws IOException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return this.buffer.readUtf8Line(jIndexOf);
        }
        if (this.buffer.size != 0) {
            return readUtf8(this.buffer.size);
        }
        return null;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8LineStrict() throws IOException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8LineStrict(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("limit < 0: " + j);
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        long jIndexOf = indexOf((byte) 10, 0L, j2);
        if (jIndexOf != -1) {
            return this.buffer.readUtf8Line(jIndexOf);
        }
        if (j2 < Long.MAX_VALUE && request(j2) && this.buffer.getByte(j2 - 1) == 13 && request(1 + j2) && this.buffer.getByte(j2) == 10) {
            return this.buffer.readUtf8Line(j2);
        }
        Buffer buffer = new Buffer();
        this.buffer.copyTo(buffer, 0L, Math.min(32L, this.buffer.size()));
        throw new EOFException("\\n not found: limit=" + Math.min(this.buffer.size(), j) + " content=" + buffer.readByteString().hex() + (char) 8230);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean request(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        while (this.buffer.size < j) {
            if (this.source.read(this.buffer, 8192L) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void require(long j) throws IOException {
        if (!request(j)) {
            throw new EOFException();
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int select(Options options) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        do {
            int iSelectPrefix = this.buffer.selectPrefix(options, true);
            if (iSelectPrefix == -1) {
                return -1;
            }
            if (iSelectPrefix != -2) {
                this.buffer.skip(options.byteStrings[iSelectPrefix].size());
                return iSelectPrefix;
            }
        } while (this.source.read(this.buffer, 8192L) != -1);
        return -1;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void skip(long j) throws IOException {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            if (this.buffer.size == 0 && this.source.read(this.buffer, 8192L) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, this.buffer.size());
            this.buffer.skip(jMin);
            j -= jMin;
        }
    }

    @Override // org.cocos2dx.okio.Source
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        return "buffer(" + this.source + ")";
    }
}
