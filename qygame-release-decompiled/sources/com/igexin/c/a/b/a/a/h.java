package com.igexin.c.a.b.a.a;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    private final BufferedInputStream a;

    public h(InputStream inputStream) throws IOException {
        this.a = new BufferedInputStream(inputStream);
    }

    private h(Socket socket) throws IOException {
        this.a = new BufferedInputStream(socket.getInputStream());
    }

    private int a() throws IOException {
        return this.a.read();
    }

    private int a(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            i4 = this.a.read(bArr, i + i3, i2 - i3);
            if (i4 <= 0) {
                break;
            }
            i3 += i4;
        }
        return i4;
    }

    private int b(byte[] bArr) throws IOException {
        return this.a.read(bArr);
    }

    private void b() throws IOException {
        this.a.close();
    }

    public final int a(byte[] bArr) throws IOException {
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            i2 = this.a.read(bArr, i, length - i);
            if (i2 <= 0) {
                throw new IOException("read = -1, end of stream !");
            }
            i += i2;
        }
        return i2;
    }
}
