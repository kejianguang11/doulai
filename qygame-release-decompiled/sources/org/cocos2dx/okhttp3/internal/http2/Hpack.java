package org.cocos2dx.okhttp3.internal.http2;

import androidx.appcompat.widget.ActivityChooserView;
import com.alipay.sdk.cons.b;
import com.alipay.sdk.cons.c;
import com.alipay.sdk.packet.d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.BufferedSource;
import org.cocos2dx.okio.ByteString;
import org.cocos2dx.okio.Okio;
import org.cocos2dx.okio.Source;

/* JADX INFO: loaded from: classes.dex */
final class Hpack {
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    static final Header[] a = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, b.a), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header(d.d, ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header(c.f, ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
    static final Map<ByteString, Integer> b = nameToFirstIndex();

    static final class Reader {
        Header[] a;
        int b;
        int c;
        int d;
        private final List<Header> headerList;
        private final int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        private final BufferedSource source;

        Reader(int i, int i2, Source source) {
            this.headerList = new ArrayList();
            this.a = new Header[8];
            this.b = this.a.length - 1;
            this.c = 0;
            this.d = 0;
            this.headerTableSizeSetting = i;
            this.maxDynamicTableByteCount = i2;
            this.source = Okio.buffer(source);
        }

        Reader(int i, Source source) {
            this(i, i, source);
        }

        private void adjustDynamicTableByteCount() {
            if (this.maxDynamicTableByteCount < this.d) {
                if (this.maxDynamicTableByteCount == 0) {
                    clearDynamicTable();
                } else {
                    evictToRecoverBytes(this.d - this.maxDynamicTableByteCount);
                }
            }
        }

        private void clearDynamicTable() {
            Arrays.fill(this.a, (Object) null);
            this.b = this.a.length - 1;
            this.c = 0;
            this.d = 0;
        }

        private int dynamicTableIndex(int i) {
            return this.b + 1 + i;
        }

        private int evictToRecoverBytes(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.a.length;
                while (true) {
                    length--;
                    if (length < this.b || i <= 0) {
                        break;
                    }
                    i -= this.a[length].hpackSize;
                    this.d -= this.a[length].hpackSize;
                    this.c--;
                    i2++;
                }
                System.arraycopy(this.a, this.b + 1, this.a, this.b + 1 + i2, this.c);
                this.b += i2;
            }
            return i2;
        }

        private ByteString getName(int i) throws IOException {
            Header header;
            if (isStaticHeader(i)) {
                header = Hpack.a[i];
            } else {
                int iDynamicTableIndex = dynamicTableIndex(i - Hpack.a.length);
                if (iDynamicTableIndex < 0 || iDynamicTableIndex >= this.a.length) {
                    throw new IOException("Header index too large " + (i + 1));
                }
                header = this.a[iDynamicTableIndex];
            }
            return header.name;
        }

        private void insertIntoDynamicTable(int i, Header header) {
            this.headerList.add(header);
            int i2 = header.hpackSize;
            if (i != -1) {
                i2 -= this.a[dynamicTableIndex(i)].hpackSize;
            }
            if (i2 > this.maxDynamicTableByteCount) {
                clearDynamicTable();
                return;
            }
            int iEvictToRecoverBytes = evictToRecoverBytes((this.d + i2) - this.maxDynamicTableByteCount);
            if (i == -1) {
                if (this.c + 1 > this.a.length) {
                    Header[] headerArr = new Header[this.a.length * 2];
                    System.arraycopy(this.a, 0, headerArr, this.a.length, this.a.length);
                    this.b = this.a.length - 1;
                    this.a = headerArr;
                }
                int i3 = this.b;
                this.b = i3 - 1;
                this.a[i3] = header;
                this.c++;
            } else {
                this.a[i + dynamicTableIndex(i) + iEvictToRecoverBytes] = header;
            }
            this.d += i2;
        }

        private boolean isStaticHeader(int i) {
            return i >= 0 && i <= Hpack.a.length - 1;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int i) throws IOException {
            if (isStaticHeader(i)) {
                this.headerList.add(Hpack.a[i]);
                return;
            }
            int iDynamicTableIndex = dynamicTableIndex(i - Hpack.a.length);
            if (iDynamicTableIndex >= 0 && iDynamicTableIndex < this.a.length) {
                this.headerList.add(this.a[iDynamicTableIndex]);
                return;
            }
            throw new IOException("Header index too large " + (i + 1));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i) throws IOException {
            insertIntoDynamicTable(-1, new Header(getName(i), b()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoDynamicTable(-1, new Header(Hpack.a(b()), b()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i) throws IOException {
            this.headerList.add(new Header(getName(i), b()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.headerList.add(new Header(Hpack.a(b()), b()));
        }

        int a(int i, int i2) throws IOException {
            int i3 = i & i2;
            if (i3 < i2) {
                return i3;
            }
            int i4 = 0;
            while (true) {
                int i5 = readByte();
                if ((i5 & 128) == 0) {
                    return i2 + (i5 << i4);
                }
                i2 += (i5 & Hpack.PREFIX_7_BITS) << i4;
                i4 += 7;
            }
        }

        void a() throws IOException {
            while (!this.source.exhausted()) {
                int i = this.source.readByte() & 255;
                if (i == 128) {
                    throw new IOException("index == 0");
                }
                if ((i & 128) == 128) {
                    readIndexedHeader(a(i, Hpack.PREFIX_7_BITS) - 1);
                } else if (i == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((i & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(a(i, 63) - 1);
                } else if ((i & 32) == 32) {
                    this.maxDynamicTableByteCount = a(i, 31);
                    if (this.maxDynamicTableByteCount < 0 || this.maxDynamicTableByteCount > this.headerTableSizeSetting) {
                        throw new IOException("Invalid dynamic table size update " + this.maxDynamicTableByteCount);
                    }
                    adjustDynamicTableByteCount();
                } else if (i == 16 || i == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(a(i, 15) - 1);
                }
            }
        }

        ByteString b() throws IOException {
            int i = readByte();
            boolean z = (i & 128) == 128;
            int iA = a(i, Hpack.PREFIX_7_BITS);
            return z ? ByteString.of(Huffman.get().a(this.source.readByteArray(iA))) : this.source.readByteString(iA);
        }

        public List<Header> getAndResetHeaderList() {
            ArrayList arrayList = new ArrayList(this.headerList);
            this.headerList.clear();
            return arrayList;
        }
    }

    static final class Writer {
        private static final int SETTINGS_HEADER_TABLE_SIZE = 4096;
        private static final int SETTINGS_HEADER_TABLE_SIZE_LIMIT = 16384;
        int a;
        int b;
        Header[] c;
        int d;
        int e;
        private boolean emitDynamicTableSizeUpdate;
        int f;
        private final Buffer out;
        private int smallestHeaderTableSizeSetting;
        private final boolean useCompression;

        Writer(int i, boolean z, Buffer buffer) {
            this.smallestHeaderTableSizeSetting = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.c = new Header[8];
            this.d = this.c.length - 1;
            this.e = 0;
            this.f = 0;
            this.a = i;
            this.b = i;
            this.useCompression = z;
            this.out = buffer;
        }

        Writer(Buffer buffer) {
            this(4096, true, buffer);
        }

        private void adjustDynamicTableByteCount() {
            if (this.b < this.f) {
                if (this.b == 0) {
                    clearDynamicTable();
                } else {
                    evictToRecoverBytes(this.f - this.b);
                }
            }
        }

        private void clearDynamicTable() {
            Arrays.fill(this.c, (Object) null);
            this.d = this.c.length - 1;
            this.e = 0;
            this.f = 0;
        }

        private int evictToRecoverBytes(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.c.length;
                while (true) {
                    length--;
                    if (length < this.d || i <= 0) {
                        break;
                    }
                    i -= this.c[length].hpackSize;
                    this.f -= this.c[length].hpackSize;
                    this.e--;
                    i2++;
                }
                System.arraycopy(this.c, this.d + 1, this.c, this.d + 1 + i2, this.e);
                Arrays.fill(this.c, this.d + 1, this.d + 1 + i2, (Object) null);
                this.d += i2;
            }
            return i2;
        }

        private void insertIntoDynamicTable(Header header) {
            int i = header.hpackSize;
            if (i > this.b) {
                clearDynamicTable();
                return;
            }
            evictToRecoverBytes((this.f + i) - this.b);
            if (this.e + 1 > this.c.length) {
                Header[] headerArr = new Header[this.c.length * 2];
                System.arraycopy(this.c, 0, headerArr, this.c.length, this.c.length);
                this.d = this.c.length - 1;
                this.c = headerArr;
            }
            int i2 = this.d;
            this.d = i2 - 1;
            this.c[i2] = header;
            this.e++;
            this.f += i;
        }

        void a(int i) {
            this.a = i;
            int iMin = Math.min(i, 16384);
            if (this.b == iMin) {
                return;
            }
            if (iMin < this.b) {
                this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, iMin);
            }
            this.emitDynamicTableSizeUpdate = true;
            this.b = iMin;
            adjustDynamicTableByteCount();
        }

        void a(int i, int i2, int i3) {
            int i4;
            Buffer buffer;
            if (i < i2) {
                buffer = this.out;
                i4 = i | i3;
            } else {
                this.out.writeByte(i3 | i2);
                i4 = i - i2;
                while (i4 >= 128) {
                    this.out.writeByte(128 | (i4 & Hpack.PREFIX_7_BITS));
                    i4 >>>= 7;
                }
                buffer = this.out;
            }
            buffer.writeByte(i4);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00ab  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00b3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void a(List<Header> list) throws IOException {
            int length;
            int length2;
            if (this.emitDynamicTableSizeUpdate) {
                if (this.smallestHeaderTableSizeSetting < this.b) {
                    a(this.smallestHeaderTableSizeSetting, 31, 32);
                }
                this.emitDynamicTableSizeUpdate = false;
                this.smallestHeaderTableSizeSetting = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                a(this.b, 31, 32);
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Header header = list.get(i);
                ByteString asciiLowercase = header.name.toAsciiLowercase();
                ByteString byteString = header.value;
                Integer num = Hpack.b.get(asciiLowercase);
                if (num != null) {
                    length = num.intValue() + 1;
                    if (length <= 1 || length >= 8) {
                        length2 = length;
                        length = -1;
                    } else if (!Util.equal(Hpack.a[length - 1].value, byteString)) {
                        if (Util.equal(Hpack.a[length].value, byteString)) {
                            length2 = length;
                            length++;
                        }
                    }
                    if (length == -1) {
                        int i2 = this.d + 1;
                        int length3 = this.c.length;
                        while (true) {
                            if (i2 >= length3) {
                                break;
                            }
                            if (Util.equal(this.c[i2].name, asciiLowercase)) {
                                if (Util.equal(this.c[i2].value, byteString)) {
                                    length = Hpack.a.length + (i2 - this.d);
                                    break;
                                } else if (length2 == -1) {
                                    length2 = (i2 - this.d) + Hpack.a.length;
                                }
                            }
                            i2++;
                        }
                    }
                    if (length == -1) {
                        a(length, Hpack.PREFIX_7_BITS, 128);
                    } else {
                        if (length2 == -1) {
                            this.out.writeByte(64);
                            a(asciiLowercase);
                        } else if (!asciiLowercase.startsWith(Header.PSEUDO_PREFIX) || Header.TARGET_AUTHORITY.equals(asciiLowercase)) {
                            a(length2, 63, 64);
                        } else {
                            a(length2, 15, 0);
                            a(byteString);
                        }
                        a(byteString);
                        insertIntoDynamicTable(header);
                    }
                } else {
                    length = -1;
                }
                length2 = length;
                if (length == -1) {
                }
                if (length == -1) {
                }
            }
        }

        void a(ByteString byteString) throws IOException {
            int size;
            int i;
            if (!this.useCompression || Huffman.get().a(byteString) >= byteString.size()) {
                size = byteString.size();
                i = 0;
            } else {
                Buffer buffer = new Buffer();
                Huffman.get().a(byteString, buffer);
                byteString = buffer.readByteString();
                size = byteString.size();
                i = 128;
            }
            a(size, Hpack.PREFIX_7_BITS, i);
            this.out.write(byteString);
        }
    }

    private Hpack() {
    }

    static ByteString a(ByteString byteString) throws IOException {
        int size = byteString.size();
        for (int i = 0; i < size; i++) {
            byte b2 = byteString.getByte(i);
            if (b2 >= 65 && b2 <= 90) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
            }
        }
        return byteString;
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(a.length);
        for (int i = 0; i < a.length; i++) {
            if (!linkedHashMap.containsKey(a[i].name)) {
                linkedHashMap.put(a[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }
}
