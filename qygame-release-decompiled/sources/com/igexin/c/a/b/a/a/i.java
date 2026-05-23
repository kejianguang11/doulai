package com.igexin.c.a.b.a.a;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    final BufferedOutputStream a;

    public i(OutputStream outputStream) throws IOException {
        this.a = new BufferedOutputStream(outputStream);
    }

    private i(Socket socket) throws IOException {
        this.a = new BufferedOutputStream(socket.getOutputStream());
    }

    private void a() throws IOException {
        this.a.close();
    }

    private void a(byte[] bArr) throws IOException {
        this.a.write(bArr, 0, bArr.length);
        this.a.flush();
    }

    private void a(byte[] bArr, int i, int i2) throws IOException {
        this.a.write(bArr, i, i2);
        this.a.flush();
    }
}
