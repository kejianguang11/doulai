package org.cocos2dx.okio;

import com.igexin.push.config.c;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class Buffer implements Cloneable, ByteChannel, BufferedSink, BufferedSource {
    private static final byte[] DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    static final int REPLACEMENT_CHARACTER = 65533;

    @Nullable
    Segment head;
    long size;

    public static final class UnsafeCursor implements Closeable {
        public Buffer buffer;
        public byte[] data;
        public boolean readWrite;
        private Segment segment;
        public long offset = -1;
        public int start = -1;
        public int end = -1;

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.buffer = null;
            this.segment = null;
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }

        public final long expandBuffer(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("minByteCount <= 0: " + i);
            }
            if (i > 8192) {
                throw new IllegalArgumentException("minByteCount > Segment.SIZE: " + i);
            }
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
            }
            long j = this.buffer.size;
            Segment segmentWritableSegment = this.buffer.writableSegment(i);
            int i2 = 8192 - segmentWritableSegment.c;
            segmentWritableSegment.c = 8192;
            long j2 = i2;
            this.buffer.size = j + j2;
            this.segment = segmentWritableSegment;
            this.offset = j;
            this.data = segmentWritableSegment.a;
            this.start = 8192 - i2;
            this.end = 8192;
            return j2;
        }

        public final int next() {
            if (this.offset != this.buffer.size) {
                return seek(this.offset == -1 ? 0L : this.offset + ((long) (this.end - this.start)));
            }
            throw new IllegalStateException();
        }

        public final long resizeBuffer(long j) {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
            }
            long j2 = this.buffer.size;
            if (j <= j2) {
                if (j < 0) {
                    throw new IllegalArgumentException("newSize < 0: " + j);
                }
                long j3 = j2 - j;
                while (true) {
                    if (j3 <= 0) {
                        break;
                    }
                    Segment segment = this.buffer.head.g;
                    long j4 = segment.c - segment.b;
                    if (j4 > j3) {
                        segment.c = (int) (((long) segment.c) - j3);
                        break;
                    }
                    this.buffer.head = segment.pop();
                    SegmentPool.a(segment);
                    j3 -= j4;
                }
                this.segment = null;
                this.offset = j;
                this.data = null;
                this.start = -1;
                this.end = -1;
            } else if (j > j2) {
                long j5 = j - j2;
                boolean z = true;
                while (j5 > 0) {
                    Segment segmentWritableSegment = this.buffer.writableSegment(1);
                    int iMin = (int) Math.min(j5, 8192 - segmentWritableSegment.c);
                    segmentWritableSegment.c += iMin;
                    j5 -= (long) iMin;
                    if (z) {
                        this.segment = segmentWritableSegment;
                        this.offset = j2;
                        this.data = segmentWritableSegment.a;
                        this.start = segmentWritableSegment.c - iMin;
                        this.end = segmentWritableSegment.c;
                        z = false;
                    }
                }
            }
            this.buffer.size = j;
            return j2;
        }

        public final int seek(long j) {
            if (j < -1 || j > this.buffer.size) {
                throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", Long.valueOf(j), Long.valueOf(this.buffer.size)));
            }
            if (j == -1 || j == this.buffer.size) {
                this.segment = null;
                this.offset = j;
                this.data = null;
                this.start = -1;
                this.end = -1;
                return -1;
            }
            long j2 = 0;
            long j3 = this.buffer.size;
            Segment segmentPush = this.buffer.head;
            Segment segment = this.buffer.head;
            if (this.segment != null) {
                long j4 = this.offset - ((long) (this.start - this.segment.b));
                if (j4 > j) {
                    segment = this.segment;
                    j3 = j4;
                } else {
                    segmentPush = this.segment;
                    j2 = j4;
                }
            }
            if (j3 - j > j - j2) {
                while (j >= ((long) (segmentPush.c - segmentPush.b)) + j2) {
                    j2 += (long) (segmentPush.c - segmentPush.b);
                    segmentPush = segmentPush.f;
                }
            } else {
                j2 = j3;
                segmentPush = segment;
                while (j2 > j) {
                    segmentPush = segmentPush.g;
                    j2 -= (long) (segmentPush.c - segmentPush.b);
                }
            }
            if (this.readWrite && segmentPush.d) {
                Segment segmentB = segmentPush.b();
                if (this.buffer.head == segmentPush) {
                    this.buffer.head = segmentB;
                }
                segmentPush = segmentPush.push(segmentB);
                segmentPush.g.pop();
            }
            this.segment = segmentPush;
            this.offset = j;
            this.data = segmentPush.a;
            this.start = segmentPush.b + ((int) (j - j2));
            this.end = segmentPush.c;
            return this.end - this.start;
        }
    }

    private ByteString digest(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            if (this.head != null) {
                messageDigest.update(this.head.a, this.head.b, this.head.c - this.head.b);
                Segment segment = this.head;
                while (true) {
                    segment = segment.f;
                    if (segment == this.head) {
                        break;
                    }
                    messageDigest.update(segment.a, segment.b, segment.c - segment.b);
                }
            }
            return ByteString.of(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private ByteString hmac(String str, ByteString byteString) {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            if (this.head != null) {
                mac.update(this.head.a, this.head.b, this.head.c - this.head.b);
                Segment segment = this.head;
                while (true) {
                    segment = segment.f;
                    if (segment == this.head) {
                        break;
                    }
                    mac.update(segment.a, segment.b, segment.c - segment.b);
                }
            }
            return ByteString.of(mac.doFinal());
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private boolean rangeEquals(Segment segment, int i, ByteString byteString, int i2, int i3) {
        int i4 = segment.c;
        byte[] bArr = segment.a;
        while (i2 < i3) {
            if (i == i4) {
                Segment segment2 = segment.f;
                byte[] bArr2 = segment2.a;
                i = segment2.b;
                segment = segment2;
                i4 = segment2.c;
                bArr = bArr2;
            }
            if (bArr[i] != byteString.getByte(i2)) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    private void readFrom(InputStream inputStream, long j, boolean z) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (j <= 0 && !z) {
                return;
            }
            Segment segmentWritableSegment = writableSegment(1);
            int i = inputStream.read(segmentWritableSegment.a, segmentWritableSegment.c, (int) Math.min(j, 8192 - segmentWritableSegment.c));
            if (i == -1) {
                if (!z) {
                    throw new EOFException();
                }
                return;
            } else {
                segmentWritableSegment.c += i;
                long j2 = i;
                this.size += j2;
                j -= j2;
            }
        }
    }

    @Override // org.cocos2dx.okio.BufferedSink, org.cocos2dx.okio.BufferedSource
    public Buffer buffer() {
        return this;
    }

    public final void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.size == 0) {
            return buffer;
        }
        buffer.head = this.head.a();
        Segment segment = buffer.head;
        Segment segment2 = buffer.head;
        Segment segment3 = buffer.head;
        segment2.g = segment3;
        segment.f = segment3;
        Segment segment4 = this.head;
        while (true) {
            segment4 = segment4.f;
            if (segment4 == this.head) {
                buffer.size = this.size;
                return buffer;
            }
            buffer.head.g.push(segment4.a());
        }
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable, org.cocos2dx.okio.Sink
    public void close() {
    }

    public final long completeSegmentByteCount() {
        long j = this.size;
        if (j == 0) {
            return 0L;
        }
        Segment segment = this.head.g;
        return (segment.c >= 8192 || !segment.e) ? j : j - ((long) (segment.c - segment.b));
    }

    public final Buffer copyTo(OutputStream outputStream) throws IOException {
        return copyTo(outputStream, 0L, this.size);
    }

    public final Buffer copyTo(OutputStream outputStream, long j, long j2) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, j, j2);
        if (j2 == 0) {
            return this;
        }
        Segment segment = this.head;
        while (j >= segment.c - segment.b) {
            j -= (long) (segment.c - segment.b);
            segment = segment.f;
        }
        while (j2 > 0) {
            int i = (int) (((long) segment.b) + j);
            int iMin = (int) Math.min(segment.c - i, j2);
            outputStream.write(segment.a, i, iMin);
            j2 -= (long) iMin;
            segment = segment.f;
            j = 0;
        }
        return this;
    }

    public final Buffer copyTo(Buffer buffer, long j, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, j, j2);
        if (j2 == 0) {
            return this;
        }
        buffer.size += j2;
        Segment segment = this.head;
        while (j >= segment.c - segment.b) {
            j -= (long) (segment.c - segment.b);
            segment = segment.f;
        }
        while (j2 > 0) {
            Segment segmentA = segment.a();
            segmentA.b = (int) (((long) segmentA.b) + j);
            segmentA.c = Math.min(segmentA.b + ((int) j2), segmentA.c);
            if (buffer.head == null) {
                segmentA.g = segmentA;
                segmentA.f = segmentA;
                buffer.head = segmentA;
            } else {
                buffer.head.g.push(segmentA);
            }
            j2 -= (long) (segmentA.c - segmentA.b);
            segment = segment.f;
            j = 0;
        }
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public BufferedSink emit() {
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer emitCompleteSegments() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if (this.size != buffer.size) {
            return false;
        }
        long j = 0;
        if (this.size == 0) {
            return true;
        }
        Segment segment = this.head;
        Segment segment2 = buffer.head;
        int i = segment.b;
        int i2 = segment2.b;
        while (j < this.size) {
            long jMin = Math.min(segment.c - i, segment2.c - i2);
            int i3 = i2;
            int i4 = i;
            int i5 = 0;
            while (i5 < jMin) {
                int i6 = i4 + 1;
                int i7 = i3 + 1;
                if (segment.a[i4] != segment2.a[i3]) {
                    return false;
                }
                i5++;
                i4 = i6;
                i3 = i7;
            }
            if (i4 == segment.c) {
                segment = segment.f;
                i = segment.b;
            } else {
                i = i4;
            }
            if (i3 == segment2.c) {
                segment2 = segment2.f;
                i2 = segment2.b;
            } else {
                i2 = i3;
            }
            j += jMin;
        }
        return true;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean exhausted() {
        return this.size == 0;
    }

    @Override // org.cocos2dx.okio.BufferedSink, org.cocos2dx.okio.Sink, java.io.Flushable
    public void flush() {
    }

    public final byte getByte(long j) {
        Util.checkOffsetAndCount(this.size, j, 1L);
        if (this.size - j <= j) {
            long j2 = j - this.size;
            Segment segment = this.head;
            do {
                segment = segment.g;
                j2 += (long) (segment.c - segment.b);
            } while (j2 < 0);
            return segment.a[segment.b + ((int) j2)];
        }
        Segment segment2 = this.head;
        while (true) {
            long j3 = segment2.c - segment2.b;
            if (j < j3) {
                return segment2.a[segment2.b + ((int) j)];
            }
            j -= j3;
            segment2 = segment2.f;
        }
    }

    public int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = segment.c;
            for (int i3 = segment.b; i3 < i2; i3++) {
                i = (i * 31) + segment.a[i3];
            }
            segment = segment.f;
        } while (segment != this.head);
        return i;
    }

    public final ByteString hmacSha1(ByteString byteString) {
        return hmac("HmacSHA1", byteString);
    }

    public final ByteString hmacSha256(ByteString byteString) {
        return hmac("HmacSHA256", byteString);
    }

    public final ByteString hmacSha512(ByteString byteString) {
        return hmac("HmacSHA512", byteString);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(byte b) {
        return indexOf(b, 0L, Long.MAX_VALUE);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(byte b, long j) {
        return indexOf(b, j, Long.MAX_VALUE);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    @Override // org.cocos2dx.okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long indexOf(byte b, long j, long j2) {
        Segment segment;
        long j3 = 0;
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", Long.valueOf(this.size), Long.valueOf(j), Long.valueOf(j2)));
        }
        if (j2 > this.size) {
            j2 = this.size;
        }
        if (j == j2 || (segment = this.head) == null) {
            return -1L;
        }
        if (this.size - j >= j) {
            while (true) {
                long j4 = ((long) (segment.c - segment.b)) + j3;
                if (j4 < j) {
                    segment = segment.f;
                    j3 = j4;
                }
            }
            while (j3 < j2) {
                byte[] bArr = segment.a;
                int iMin = (int) Math.min(segment.c, (((long) segment.b) + j2) - j3);
                for (int i = (int) ((((long) segment.b) + j) - j3); i < iMin; i++) {
                    if (bArr[i] == b) {
                        return ((long) (i - segment.b)) + j3;
                    }
                }
                j = ((long) (segment.c - segment.b)) + j3;
                segment = segment.f;
                j3 = j;
            }
            return -1L;
        }
        j3 = this.size;
        while (j3 > j) {
            segment = segment.g;
            j3 -= (long) (segment.c - segment.b);
        }
        while (j3 < j2) {
        }
        return -1L;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0L);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOf(ByteString byteString, long j) throws IOException {
        byte[] bArr;
        Segment segment;
        if (byteString.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment2 = this.head;
        long j3 = -1;
        if (segment2 == null) {
            return -1L;
        }
        if (this.size - j >= j) {
            while (true) {
                long j4 = ((long) (segment2.c - segment2.b)) + j2;
                if (j4 >= j) {
                    break;
                }
                segment2 = segment2.f;
                j2 = j4;
            }
        } else {
            j2 = this.size;
            while (j2 > j) {
                segment2 = segment2.g;
                j2 -= (long) (segment2.c - segment2.b);
            }
        }
        byte b = byteString.getByte(0);
        int size = byteString.size();
        long j5 = (this.size - ((long) size)) + 1;
        long j6 = j;
        long j7 = j2;
        Segment segment3 = segment2;
        while (j7 < j5) {
            byte[] bArr2 = segment3.a;
            int iMin = (int) Math.min(segment3.c, (((long) segment3.b) + j5) - j7);
            int i = (int) ((((long) segment3.b) + j6) - j7);
            while (i < iMin) {
                if (bArr2[i] == b) {
                    bArr = bArr2;
                    segment = segment3;
                    if (rangeEquals(segment3, i + 1, byteString, 1, size)) {
                        return ((long) (i - segment.b)) + j7;
                    }
                } else {
                    bArr = bArr2;
                    segment = segment3;
                }
                i++;
                segment3 = segment;
                bArr2 = bArr;
            }
            Segment segment4 = segment3;
            j6 = ((long) (segment4.c - segment4.b)) + j7;
            segment3 = segment4.f;
            j7 = j6;
            j3 = -1;
        }
        return j3;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0L);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j) {
        int i;
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1L;
        }
        if (this.size - j >= j) {
            while (true) {
                long j3 = ((long) (segment.c - segment.b)) + j2;
                if (j3 >= j) {
                    break;
                }
                segment = segment.f;
                j2 = j3;
            }
        } else {
            j2 = this.size;
            while (j2 > j) {
                segment = segment.g;
                j2 -= (long) (segment.c - segment.b);
            }
        }
        if (byteString.size() == 2) {
            byte b = byteString.getByte(0);
            byte b2 = byteString.getByte(1);
            while (j2 < this.size) {
                byte[] bArr = segment.a;
                i = (int) ((((long) segment.b) + j) - j2);
                int i2 = segment.c;
                while (i < i2) {
                    byte b3 = bArr[i];
                    if (b3 == b || b3 == b2) {
                        return ((long) (i - segment.b)) + j2;
                    }
                    i++;
                }
                j = ((long) (segment.c - segment.b)) + j2;
                segment = segment.f;
                j2 = j;
            }
            return -1L;
        }
        byte[] bArrInternalArray = byteString.internalArray();
        while (j2 < this.size) {
            byte[] bArr2 = segment.a;
            i = (int) ((((long) segment.b) + j) - j2);
            int i3 = segment.c;
            while (i < i3) {
                byte b4 = bArr2[i];
                for (byte b5 : bArrInternalArray) {
                    if (b4 == b5) {
                        return ((long) (i - segment.b)) + j2;
                    }
                }
                i++;
            }
            j = ((long) (segment.c - segment.b)) + j2;
            segment = segment.f;
            j2 = j;
        }
        return -1L;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: org.cocos2dx.okio.Buffer.2
            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(Buffer.this.size, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() {
                if (Buffer.this.size > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) {
                return Buffer.this.read(bArr, i, i2);
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return true;
    }

    public final ByteString md5() {
        return digest("MD5");
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public OutputStream outputStream() {
        return new OutputStream() { // from class: org.cocos2dx.okio.Buffer.1
            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() {
            }

            public String toString() {
                return Buffer.this + ".outputStream()";
            }

            @Override // java.io.OutputStream
            public void write(int i) {
                Buffer.this.writeByte((int) ((byte) i));
            }

            @Override // java.io.OutputStream
            public void write(byte[] bArr, int i, int i2) {
                Buffer.this.write(bArr, i, i2);
            }
        };
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString) {
        return rangeEquals(j, byteString, 0, byteString.size());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) {
        if (j < 0 || i < 0 || i2 < 0 || this.size - j < i2 || byteString.size() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (getByte(((long) i3) + j) != byteString.getByte(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(byteBuffer.remaining(), segment.c - segment.b);
        byteBuffer.put(segment.a, segment.b, iMin);
        segment.b += iMin;
        this.size -= (long) iMin;
        if (segment.b == segment.c) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        }
        return iMin;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int read(byte[] bArr, int i, int i2) {
        Util.checkOffsetAndCount(bArr.length, i, i2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(i2, segment.c - segment.b);
        System.arraycopy(segment.a, segment.b, bArr, i, iMin);
        segment.b += iMin;
        this.size -= (long) iMin;
        if (segment.b == segment.c) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        }
        return iMin;
    }

    @Override // org.cocos2dx.okio.Source
    public long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.size == 0) {
            return -1L;
        }
        if (j > this.size) {
            j = this.size;
        }
        buffer.write(this, j);
        return j;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        long j = this.size;
        if (j > 0) {
            sink.write(this, j);
        }
        return j;
    }

    public final UnsafeCursor readAndWriteUnsafe() {
        return readAndWriteUnsafe(new UnsafeCursor());
    }

    public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = true;
        return unsafeCursor;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int i = segment.b;
        int i2 = segment.c;
        int i3 = i + 1;
        byte b = segment.a[i];
        this.size--;
        if (i3 == i2) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.b = i3;
        }
        return b;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public byte[] readByteArray(long j) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, j);
        if (j <= 2147483647L) {
            byte[] bArr = new byte[(int) j];
            readFully(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public ByteString readByteString(long j) throws EOFException {
        return new ByteString(readByteArray(j));
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ab A[EDGE_INSN: B:48:0x00ab->B:38:0x00ab BREAK  A[LOOP:0: B:5:0x0010->B:50:?], SYNTHETIC] */
    @Override // org.cocos2dx.okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long readDecimalLong() {
        long j = 0;
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        int i = 0;
        long j2 = -7;
        boolean z = false;
        boolean z2 = false;
        do {
            Segment segment = this.head;
            byte[] bArr = segment.a;
            int i2 = segment.b;
            int i3 = segment.c;
            while (i2 < i3) {
                byte b = bArr[i2];
                if (b >= 48 && b <= 57) {
                    int i4 = 48 - b;
                    if (j < -922337203685477580L || (j == -922337203685477580L && i4 < j2)) {
                        Buffer bufferWriteByte = new Buffer().writeDecimalLong(j).writeByte((int) b);
                        if (!z) {
                            bufferWriteByte.readByte();
                        }
                        throw new NumberFormatException("Number too large: " + bufferWriteByte.readUtf8());
                    }
                    j = (j * 10) + ((long) i4);
                } else if (b == 45 && i == 0) {
                    j2--;
                    z = true;
                } else {
                    if (i == 0) {
                        throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(b));
                    }
                    z2 = true;
                    if (i2 != i3) {
                        this.head = segment.pop();
                        SegmentPool.a(segment);
                    } else {
                        segment.b = i2;
                    }
                    if (!z2) {
                        break;
                    }
                }
                i2++;
                i++;
            }
            if (i2 != i3) {
            }
            if (!z2) {
            }
        } while (this.head != null);
        this.size -= (long) i;
        return z ? j : -j;
    }

    public final Buffer readFrom(InputStream inputStream) throws IOException {
        readFrom(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    public final Buffer readFrom(InputStream inputStream, long j) throws IOException {
        if (j >= 0) {
            readFrom(inputStream, j, false);
            return this;
        }
        throw new IllegalArgumentException("byteCount < 0: " + j);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void readFully(Buffer buffer, long j) throws EOFException {
        if (this.size >= j) {
            buffer.write(this, j);
        } else {
            buffer.write(this, this.size);
            throw new EOFException();
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void readFully(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int i2 = read(bArr, i, bArr.length - i);
            if (i2 == -1) {
                throw new EOFException();
            }
            i += i2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a5 A[EDGE_INSN: B:44:0x00a5->B:38:0x00a5 BREAK  A[LOOP:0: B:5:0x000b->B:46:?], SYNTHETIC] */
    @Override // org.cocos2dx.okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long readHexadecimalUnsignedLong() {
        int i;
        int i2;
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        int i3 = 0;
        boolean z = false;
        long j = 0;
        do {
            Segment segment = this.head;
            byte[] bArr = segment.a;
            int i4 = segment.b;
            int i5 = segment.c;
            while (i4 < i5) {
                byte b = bArr[i4];
                if (b < 48 || b > 57) {
                    if (b >= 97 && b <= 102) {
                        i = b - 97;
                    } else if (b >= 65 && b <= 70) {
                        i = b - 65;
                    } else {
                        if (i3 == 0) {
                            throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                        }
                        z = true;
                        if (i4 != i5) {
                            this.head = segment.pop();
                            SegmentPool.a(segment);
                        } else {
                            segment.b = i4;
                        }
                        if (!z) {
                            break;
                        }
                    }
                    i2 = i + 10;
                } else {
                    i2 = b - 48;
                }
                if (((-1152921504606846976L) & j) != 0) {
                    throw new NumberFormatException("Number too large: " + new Buffer().writeHexadecimalUnsignedLong(j).writeByte((int) b).readUtf8());
                }
                j = (j << 4) | ((long) i2);
                i4++;
                i3++;
            }
            if (i4 != i5) {
            }
            if (!z) {
            }
        } while (this.head != null);
        this.size -= (long) i3;
        return j;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readInt() {
        if (this.size < 4) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 4) {
            return (readByte() & 255) | ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8);
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        int i8 = i6 + 1;
        int i9 = i7 | (bArr[i6] & 255);
        this.size -= 4;
        if (i8 == i2) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.b = i8;
        }
        return i9;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readLong() {
        if (this.size < 8) {
            throw new IllegalStateException("size < 8: " + this.size);
        }
        Segment segment = this.head;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (4294967295L & ((long) readInt()));
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        long j = (((long) bArr[i]) & 255) << 56;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        long j2 = j | ((((long) bArr[i3]) & 255) << 48) | ((((long) bArr[i4]) & 255) << 40);
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        long j3 = j2 | ((((long) bArr[i5]) & 255) << 32) | ((((long) bArr[i6]) & 255) << 24);
        int i8 = i7 + 1;
        long j4 = j3 | ((((long) bArr[i7]) & 255) << 16);
        int i9 = i8 + 1;
        long j5 = j4 | ((((long) bArr[i8]) & 255) << 8);
        int i10 = i9 + 1;
        long j6 = (((long) bArr[i9]) & 255) | j5;
        this.size -= 8;
        if (i10 == i2) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.b = i10;
        }
        return j6;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public long readLongLe() {
        return Util.reverseBytesLong(readLong());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public short readShort() {
        if (this.size < 2) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 2) {
            return (short) ((readByte() & 255) | ((readByte() & 255) << 8));
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.size -= 2;
        if (i4 == i2) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.b = i4;
        }
        return (short) i5;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readString(long j, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        if (j == 0) {
            return "";
        }
        Segment segment = this.head;
        if (((long) segment.b) + j > segment.c) {
            return new String(readByteArray(j), charset);
        }
        String str = new String(segment.a, segment.b, (int) j, charset);
        segment.b = (int) (((long) segment.b) + j);
        this.size -= j;
        if (segment.b == segment.c) {
            this.head = segment.pop();
            SegmentPool.a(segment);
        }
        return str;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readString(Charset charset) {
        try {
            return readString(this.size, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final UnsafeCursor readUnsafe() {
        return readUnsafe(new UnsafeCursor());
    }

    public final UnsafeCursor readUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = false;
        return unsafeCursor;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8(long j) throws EOFException {
        return readString(j, Util.UTF_8);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int readUtf8CodePoint() throws EOFException {
        int i;
        int i2;
        int i3;
        if (this.size == 0) {
            throw new EOFException();
        }
        byte b = getByte(0L);
        if ((b & 128) == 0) {
            i = b & 127;
            i3 = 0;
            i2 = 1;
        } else if ((b & 224) == 192) {
            i = b & 31;
            i2 = 2;
            i3 = 128;
        } else if ((b & 240) == 224) {
            i = b & 15;
            i2 = 3;
            i3 = 2048;
        } else {
            if ((b & 248) != 240) {
                skip(1L);
                return REPLACEMENT_CHARACTER;
            }
            i = b & 7;
            i2 = 4;
            i3 = 65536;
        }
        long j = i2;
        if (this.size < j) {
            throw new EOFException("size < " + i2 + ": " + this.size + " (to read code point prefixed 0x" + Integer.toHexString(b) + ")");
        }
        for (int i4 = 1; i4 < i2; i4++) {
            long j2 = i4;
            byte b2 = getByte(j2);
            if ((b2 & 192) != 128) {
                skip(j2);
                return REPLACEMENT_CHARACTER;
            }
            i = (i << 6) | (b2 & 63);
        }
        skip(j);
        return i > 1114111 ? REPLACEMENT_CHARACTER : ((i < 55296 || i > 57343) && i >= i3) ? i : REPLACEMENT_CHARACTER;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws EOFException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return readUtf8Line(jIndexOf);
        }
        if (this.size != 0) {
            return readUtf8(this.size);
        }
        return null;
    }

    String readUtf8Line(long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (getByte(j2) == 13) {
                String utf8 = readUtf8(j2);
                skip(2L);
                return utf8;
            }
        }
        String utf82 = readUtf8(j);
        skip(1L);
        return utf82;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8LineStrict() throws EOFException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public String readUtf8LineStrict(long j) throws EOFException {
        if (j < 0) {
            throw new IllegalArgumentException("limit < 0: " + j);
        }
        long j2 = j != Long.MAX_VALUE ? j + 1 : Long.MAX_VALUE;
        long jIndexOf = indexOf((byte) 10, 0L, j2);
        if (jIndexOf != -1) {
            return readUtf8Line(jIndexOf);
        }
        if (j2 < size() && getByte(j2 - 1) == 13 && getByte(j2) == 10) {
            return readUtf8Line(j2);
        }
        Buffer buffer = new Buffer();
        copyTo(buffer, 0L, Math.min(32L, size()));
        throw new EOFException("\\n not found: limit=" + Math.min(size(), j) + " content=" + buffer.readByteString().hex() + (char) 8230);
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public boolean request(long j) {
        return this.size >= j;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void require(long j) throws EOFException {
        if (this.size < j) {
            throw new EOFException();
        }
    }

    List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.head.c - this.head.b));
        Segment segment = this.head;
        while (true) {
            segment = segment.f;
            if (segment == this.head) {
                return arrayList;
            }
            arrayList.add(Integer.valueOf(segment.c - segment.b));
        }
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public int select(Options options) {
        int iSelectPrefix = selectPrefix(options, false);
        if (iSelectPrefix == -1) {
            return -1;
        }
        try {
            skip(options.byteStrings[iSelectPrefix].size());
            return iSelectPrefix;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:
    
        if (r18 == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005b, code lost:
    
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int selectPrefix(Options options, boolean z) {
        int i;
        int i2;
        Segment segment = this.head;
        int i3 = -2;
        if (segment != null) {
            byte[] bArr = segment.a;
            int i4 = segment.b;
            int i5 = segment.c;
            int[] iArr = options.trie;
            int i6 = i4;
            int i7 = i5;
            int i8 = -1;
            Segment segment2 = segment;
            byte[] bArr2 = bArr;
            int i9 = 0;
            loop0: while (true) {
                int i10 = i9 + 1;
                int i11 = iArr[i9];
                int i12 = i10 + 1;
                int i13 = iArr[i10];
                if (i13 != -1) {
                    i8 = i13;
                }
                if (segment2 == null) {
                    break;
                }
                if (i11 >= 0) {
                    int i14 = i6 + 1;
                    int i15 = bArr2[i6] & 255;
                    int i16 = i12 + i11;
                    while (i12 != i16) {
                        if (i15 == iArr[i12]) {
                            i = iArr[i12 + i11];
                            if (i14 == i7) {
                                Segment segment3 = segment2.f;
                                i2 = segment3.b;
                                byte[] bArr3 = segment3.a;
                                int i17 = segment3.c;
                                if (segment3 == segment) {
                                    i7 = i17;
                                    bArr2 = bArr3;
                                    segment2 = null;
                                } else {
                                    i7 = i17;
                                    bArr2 = bArr3;
                                    segment2 = segment3;
                                }
                            } else {
                                i2 = i14;
                            }
                        } else {
                            i12++;
                        }
                    }
                    return i8;
                }
                int i18 = i12 + (i11 * (-1));
                while (true) {
                    i2 = i6 + 1;
                    int i19 = i12 + 1;
                    if ((bArr2[i6] & 255) != iArr[i12]) {
                        return i8;
                    }
                    boolean z2 = i19 == i18;
                    if (i2 == i7) {
                        Segment segment4 = segment2.f;
                        int i20 = segment4.b;
                        bArr2 = segment4.a;
                        i7 = segment4.c;
                        if (segment4 != segment) {
                            segment2 = segment4;
                            i2 = i20;
                        } else {
                            if (!z2) {
                                break loop0;
                            }
                            i2 = i20;
                            segment2 = null;
                        }
                    }
                    if (z2) {
                        i = iArr[i19];
                        break;
                    }
                    i6 = i2;
                    i12 = i19;
                }
                if (i >= 0) {
                    return i;
                }
                int i21 = -i;
                i6 = i2;
                i9 = i21;
                i3 = -2;
            }
        } else {
            if (z) {
                return -2;
            }
            return options.indexOf(ByteString.EMPTY);
        }
    }

    public final ByteString sha1() {
        return digest("SHA-1");
    }

    public final ByteString sha256() {
        return digest("SHA-256");
    }

    public final ByteString sha512() {
        return digest("SHA-512");
    }

    public final long size() {
        return this.size;
    }

    @Override // org.cocos2dx.okio.BufferedSource
    public void skip(long j) throws EOFException {
        while (j > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j, this.head.c - this.head.b);
            long j2 = iMin;
            this.size -= j2;
            j -= j2;
            this.head.b += iMin;
            if (this.head.b == this.head.c) {
                Segment segment = this.head;
                this.head = segment.pop();
                SegmentPool.a(segment);
            }
        }
    }

    public final ByteString snapshot() {
        if (this.size <= 2147483647L) {
            return snapshot((int) this.size);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
    }

    public final ByteString snapshot(int i) {
        return i == 0 ? ByteString.EMPTY : new SegmentedByteString(this, i);
    }

    @Override // org.cocos2dx.okio.Sink
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        return snapshot().toString();
    }

    Segment writableSegment(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException();
        }
        if (this.head != null) {
            Segment segment = this.head.g;
            return (segment.c + i > 8192 || !segment.e) ? segment.push(SegmentPool.a()) : segment;
        }
        this.head = SegmentPool.a();
        Segment segment2 = this.head;
        Segment segment3 = this.head;
        Segment segment4 = this.head;
        segment3.g = segment4;
        segment2.f = segment4;
        return segment4;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        int iRemaining = byteBuffer.remaining();
        int i = iRemaining;
        while (i > 0) {
            Segment segmentWritableSegment = writableSegment(1);
            int iMin = Math.min(i, 8192 - segmentWritableSegment.c);
            byteBuffer.get(segmentWritableSegment.a, segmentWritableSegment.c, iMin);
            i -= iMin;
            segmentWritableSegment.c += iMin;
        }
        this.size += (long) iRemaining;
        return iRemaining;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer write(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer write(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = i2;
        Util.checkOffsetAndCount(bArr.length, i, j);
        int i3 = i2 + i;
        while (i < i3) {
            Segment segmentWritableSegment = writableSegment(1);
            int iMin = Math.min(i3 - i, 8192 - segmentWritableSegment.c);
            System.arraycopy(bArr, i, segmentWritableSegment.a, segmentWritableSegment.c, iMin);
            i += iMin;
            segmentWritableSegment.c += iMin;
        }
        this.size += j;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public BufferedSink write(Source source, long j) throws IOException {
        while (j > 0) {
            long j2 = source.read(this, j);
            if (j2 == -1) {
                throw new EOFException();
            }
            j -= j2;
        }
        return this;
    }

    @Override // org.cocos2dx.okio.Sink
    public void write(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        }
        Util.checkOffsetAndCount(buffer.size, 0L, j);
        while (j > 0) {
            if (j < buffer.head.c - buffer.head.b) {
                Segment segment = this.head != null ? this.head.g : null;
                if (segment != null && segment.e) {
                    if ((((long) segment.c) + j) - ((long) (segment.d ? 0 : segment.b)) <= 8192) {
                        buffer.head.writeTo(segment, (int) j);
                        buffer.size -= j;
                        this.size += j;
                        return;
                    }
                }
                buffer.head = buffer.head.split((int) j);
            }
            Segment segment2 = buffer.head;
            long j2 = segment2.c - segment2.b;
            buffer.head = segment2.pop();
            if (this.head == null) {
                this.head = segment2;
                Segment segment3 = this.head;
                Segment segment4 = this.head;
                Segment segment5 = this.head;
                segment4.g = segment5;
                segment3.f = segment5;
            } else {
                this.head.g.push(segment2).compact();
            }
            buffer.size -= j2;
            this.size += j2;
            j -= j2;
        }
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = source.read(this, 8192L);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeByte(int i) {
        Segment segmentWritableSegment = writableSegment(1);
        byte[] bArr = segmentWritableSegment.a;
        int i2 = segmentWritableSegment.c;
        segmentWritableSegment.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.size++;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeDecimalLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        boolean z = false;
        int i = 1;
        if (j < 0) {
            j = -j;
            if (j < 0) {
                return writeUtf8("-9223372036854775808");
            }
            z = true;
        }
        if (j >= 100000000) {
            i = j < 1000000000000L ? j < 10000000000L ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= c.i) {
            i = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i = 2;
        }
        if (z) {
            i++;
        }
        Segment segmentWritableSegment = writableSegment(i);
        byte[] bArr = segmentWritableSegment.a;
        int i2 = segmentWritableSegment.c + i;
        while (j != 0) {
            i2--;
            bArr[i2] = DIGITS[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i2 - 1] = 45;
        }
        segmentWritableSegment.c += i;
        this.size += (long) i;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeHexadecimalUnsignedLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        Segment segmentWritableSegment = writableSegment(iNumberOfTrailingZeros);
        byte[] bArr = segmentWritableSegment.a;
        int i = segmentWritableSegment.c;
        for (int i2 = (segmentWritableSegment.c + iNumberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = DIGITS[(int) (15 & j)];
            j >>>= 4;
        }
        segmentWritableSegment.c += iNumberOfTrailingZeros;
        this.size += (long) iNumberOfTrailingZeros;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeInt(int i) {
        Segment segmentWritableSegment = writableSegment(4);
        byte[] bArr = segmentWritableSegment.a;
        int i2 = segmentWritableSegment.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        segmentWritableSegment.c = i5 + 1;
        this.size += 4;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeIntLe(int i) {
        return writeInt(Util.reverseBytesInt(i));
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeLong(long j) {
        Segment segmentWritableSegment = writableSegment(8);
        byte[] bArr = segmentWritableSegment.a;
        int i = segmentWritableSegment.c;
        int i2 = i + 1;
        bArr[i] = (byte) ((j >>> 56) & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((j >>> 48) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((j >>> 40) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((j >>> 32) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((j >>> 24) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((j >>> 16) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((j >>> 8) & 255);
        bArr[i8] = (byte) (j & 255);
        segmentWritableSegment.c = i8 + 1;
        this.size += 8;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeLongLe(long j) {
        return writeLong(Util.reverseBytesLong(j));
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeShort(int i) {
        Segment segmentWritableSegment = writableSegment(2);
        byte[] bArr = segmentWritableSegment.a;
        int i2 = segmentWritableSegment.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i3] = (byte) (i & 255);
        segmentWritableSegment.c = i3 + 1;
        this.size += 2;
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeShortLe(int i) {
        return writeShort((int) Util.reverseBytesShort((short) i));
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeString(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 <= str.length()) {
            if (charset == null) {
                throw new IllegalArgumentException("charset == null");
            }
            if (charset.equals(Util.UTF_8)) {
                return writeUtf8(str, i, i2);
            }
            byte[] bytes = str.substring(i, i2).getBytes(charset);
            return write(bytes, 0, bytes.length);
        }
        throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeString(String str, Charset charset) {
        return writeString(str, 0, str.length(), charset);
    }

    public final Buffer writeTo(OutputStream outputStream) throws IOException {
        return writeTo(outputStream, this.size);
    }

    public final Buffer writeTo(OutputStream outputStream, long j) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, 0L, j);
        Segment segment = this.head;
        while (j > 0) {
            int iMin = (int) Math.min(j, segment.c - segment.b);
            outputStream.write(segment.a, segment.b, iMin);
            segment.b += iMin;
            long j2 = iMin;
            this.size -= j2;
            j -= j2;
            if (segment.b == segment.c) {
                Segment segmentPop = segment.pop();
                this.head = segmentPop;
                SegmentPool.a(segment);
                segment = segmentPop;
            }
        }
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeUtf8(String str) {
        return writeUtf8(str, 0, str.length());
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeUtf8(String str, int i, int i2) {
        int i3;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        }
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 128) {
                Segment segmentWritableSegment = writableSegment(1);
                byte[] bArr = segmentWritableSegment.a;
                int i4 = segmentWritableSegment.c - i;
                int iMin = Math.min(i2, 8192 - i4);
                int i5 = i + 1;
                bArr[i + i4] = (byte) cCharAt;
                while (i5 < iMin) {
                    char cCharAt2 = str.charAt(i5);
                    if (cCharAt2 >= 128) {
                        break;
                    }
                    bArr[i5 + i4] = (byte) cCharAt2;
                    i5++;
                }
                int i6 = (i4 + i5) - segmentWritableSegment.c;
                segmentWritableSegment.c += i6;
                this.size += (long) i6;
                i = i5;
            } else {
                if (cCharAt < 2048) {
                    i3 = (cCharAt >> 6) | 192;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    writeByte((cCharAt >> '\f') | 224);
                    i3 = ((cCharAt >> 6) & 63) | 128;
                } else {
                    int i7 = i + 1;
                    char cCharAt3 = i7 < i2 ? str.charAt(i7) : (char) 0;
                    if (cCharAt > 56319 || cCharAt3 < 56320 || cCharAt3 > 57343) {
                        writeByte(63);
                        i = i7;
                    } else {
                        int i8 = (((cCharAt & 10239) << 10) | (9215 & cCharAt3)) + 65536;
                        writeByte((i8 >> 18) | 240);
                        writeByte(((i8 >> 12) & 63) | 128);
                        writeByte(((i8 >> 6) & 63) | 128);
                        writeByte((i8 & 63) | 128);
                        i += 2;
                    }
                }
                writeByte(i3);
                writeByte((cCharAt & '?') | 128);
                i++;
            }
        }
        return this;
    }

    @Override // org.cocos2dx.okio.BufferedSink
    public Buffer writeUtf8CodePoint(int i) {
        int i2;
        int i3;
        if (i >= 128) {
            if (i < 2048) {
                i3 = (i >> 6) | 192;
            } else {
                if (i < 65536) {
                    if (i < 55296 || i > 57343) {
                        i2 = (i >> 12) | 224;
                    } else {
                        writeByte(63);
                    }
                } else {
                    if (i > 1114111) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
                    }
                    writeByte((i >> 18) | 240);
                    i2 = ((i >> 12) & 63) | 128;
                }
                writeByte(i2);
                i3 = ((i >> 6) & 63) | 128;
            }
            writeByte(i3);
            i = (i & 63) | 128;
            writeByte(i);
        } else {
            writeByte(i);
        }
        return this;
    }
}
