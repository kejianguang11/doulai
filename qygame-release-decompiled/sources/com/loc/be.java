package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public final class be implements Closeable {
    public static final Charset a = Charset.forName("US-ASCII");
    private final InputStream b;
    private final Charset c;
    private byte[] d;
    private int e;
    private int f;

    public be(InputStream inputStream, Charset charset) {
        this(inputStream, charset, (byte) 0);
    }

    private be(InputStream inputStream, Charset charset, byte b) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        }
        if (!charset.equals(a)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.b = inputStream;
        this.c = charset;
        this.d = new byte[8192];
    }

    private void b() throws IOException {
        int i = this.b.read(this.d, 0, this.d.length);
        if (i == -1) {
            throw new EOFException();
        }
        this.e = 0;
        this.f = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String a() throws IOException {
        int i;
        int i2;
        synchronized (this.b) {
            if (this.d == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.e >= this.f) {
                b();
            }
            for (int i3 = this.e; i3 != this.f; i3++) {
                if (this.d[i3] == 10) {
                    if (i3 != this.e) {
                        i2 = i3 - 1;
                        if (this.d[i2] != 13) {
                            i2 = i3;
                        }
                    }
                    String str = new String(this.d, this.e, i2 - this.e, this.c.name());
                    this.e = i3 + 1;
                    return str;
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((this.f - this.e) + 80) { // from class: com.loc.be.1
                @Override // java.io.ByteArrayOutputStream
                public final String toString() {
                    try {
                        return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + (-1)] != 13) ? this.count : this.count - 1, be.this.c.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1: while (true) {
                byteArrayOutputStream.write(this.d, this.e, this.f - this.e);
                this.f = -1;
                b();
                i = this.e;
                while (i != this.f) {
                    if (this.d[i] == 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.e) {
                byteArrayOutputStream.write(this.d, this.e, i - this.e);
            }
            this.e = i + 1;
            return byteArrayOutputStream.toString();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        synchronized (this.b) {
            if (this.d != null) {
                this.d = null;
                this.b.close();
            }
        }
    }
}
