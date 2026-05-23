package org.cocos2dx.okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class SegmentedByteString extends ByteString {
    final transient byte[][] a;
    final transient int[] b;

    SegmentedByteString(Buffer buffer, int i) {
        super(null);
        Util.checkOffsetAndCount(buffer.size, 0L, i);
        int i2 = 0;
        Segment segment = buffer.head;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            if (segment.c == segment.b) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += segment.c - segment.b;
            i4++;
            segment = segment.f;
        }
        this.a = new byte[i4][];
        this.b = new int[i4 * 2];
        Segment segment2 = buffer.head;
        int i5 = 0;
        while (i2 < i) {
            this.a[i5] = segment2.a;
            i2 += segment2.c - segment2.b;
            if (i2 > i) {
                i2 = i;
            }
            this.b[i5] = i2;
            this.b[this.a.length + i5] = segment2.b;
            segment2.d = true;
            i5++;
            segment2 = segment2.f;
        }
    }

    private int segment(int i) {
        int iBinarySearch = Arrays.binarySearch(this.b, 0, this.a.length, i + 1);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }

    private ByteString toByteString() {
        return new ByteString(toByteArray());
    }

    private Object writeReplace() {
        return toByteString();
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // org.cocos2dx.okio.ByteString
    public String base64() {
        return toByteString().base64();
    }

    @Override // org.cocos2dx.okio.ByteString
    public String base64Url() {
        return toByteString().base64Url();
    }

    @Override // org.cocos2dx.okio.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(0, byteString, 0, size())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.cocos2dx.okio.ByteString
    public byte getByte(int i) {
        Util.checkOffsetAndCount(this.b[this.a.length - 1], i, 1L);
        int iSegment = segment(i);
        return this.a[iSegment][(i - (iSegment == 0 ? 0 : this.b[iSegment - 1])) + this.b[this.a.length + iSegment]];
    }

    @Override // org.cocos2dx.okio.ByteString
    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int length = this.a.length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            byte[] bArr = this.a[i2];
            int i5 = this.b[length + i2];
            int i6 = this.b[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = (i3 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i4 = i6;
        }
        this.hashCode = i3;
        return i3;
    }

    @Override // org.cocos2dx.okio.ByteString
    public String hex() {
        return toByteString().hex();
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString hmacSha1(ByteString byteString) {
        return toByteString().hmacSha1(byteString);
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString hmacSha256(ByteString byteString) {
        return toByteString().hmacSha256(byteString);
    }

    @Override // org.cocos2dx.okio.ByteString
    public int indexOf(byte[] bArr, int i) {
        return toByteString().indexOf(bArr, i);
    }

    @Override // org.cocos2dx.okio.ByteString
    byte[] internalArray() {
        return toByteArray();
    }

    @Override // org.cocos2dx.okio.ByteString
    public int lastIndexOf(byte[] bArr, int i) {
        return toByteString().lastIndexOf(bArr, i);
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString md5() {
        return toByteString().md5();
    }

    @Override // org.cocos2dx.okio.ByteString
    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        if (i < 0 || i > size() - i3) {
            return false;
        }
        int iSegment = segment(i);
        while (i3 > 0) {
            int i4 = iSegment == 0 ? 0 : this.b[iSegment - 1];
            int iMin = Math.min(i3, ((this.b[iSegment] - i4) + i4) - i);
            if (!byteString.rangeEquals(i2, this.a[iSegment], (i - i4) + this.b[this.a.length + iSegment], iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iSegment++;
        }
        return true;
    }

    @Override // org.cocos2dx.okio.ByteString
    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int iSegment = segment(i);
        while (i3 > 0) {
            int i4 = iSegment == 0 ? 0 : this.b[iSegment - 1];
            int iMin = Math.min(i3, ((this.b[iSegment] - i4) + i4) - i);
            if (!Util.arrayRangeEquals(this.a[iSegment], (i - i4) + this.b[this.a.length + iSegment], bArr, i2, iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iSegment++;
        }
        return true;
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString sha1() {
        return toByteString().sha1();
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString sha256() {
        return toByteString().sha256();
    }

    @Override // org.cocos2dx.okio.ByteString
    public int size() {
        return this.b[this.a.length - 1];
    }

    @Override // org.cocos2dx.okio.ByteString
    public String string(Charset charset) {
        return toByteString().string(charset);
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString substring(int i) {
        return toByteString().substring(i);
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString substring(int i, int i2) {
        return toByteString().substring(i, i2);
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString toAsciiLowercase() {
        return toByteString().toAsciiLowercase();
    }

    @Override // org.cocos2dx.okio.ByteString
    public ByteString toAsciiUppercase() {
        return toByteString().toAsciiUppercase();
    }

    @Override // org.cocos2dx.okio.ByteString
    public byte[] toByteArray() {
        byte[] bArr = new byte[this.b[this.a.length - 1]];
        int length = this.a.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.b[length + i];
            int i4 = this.b[i];
            System.arraycopy(this.a[i], i3, bArr, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr;
    }

    @Override // org.cocos2dx.okio.ByteString
    public String toString() {
        return toByteString().toString();
    }

    @Override // org.cocos2dx.okio.ByteString
    public String utf8() {
        return toByteString().utf8();
    }

    @Override // org.cocos2dx.okio.ByteString
    public void write(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        int length = this.a.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.b[length + i];
            int i4 = this.b[i];
            outputStream.write(this.a[i], i3, i4 - i2);
            i++;
            i2 = i4;
        }
    }

    @Override // org.cocos2dx.okio.ByteString
    void write(Buffer buffer) {
        int length = this.a.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.b[length + i];
            int i4 = this.b[i];
            Segment segment = new Segment(this.a[i], i3, (i3 + i4) - i2, true, false);
            if (buffer.head == null) {
                segment.g = segment;
                segment.f = segment;
                buffer.head = segment;
            } else {
                buffer.head.g.push(segment);
            }
            i++;
            i2 = i4;
        }
        buffer.size += (long) i2;
    }
}
