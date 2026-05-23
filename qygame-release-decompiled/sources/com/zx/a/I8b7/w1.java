package com.zx.a.I8b7;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public abstract class w1 implements Closeable {

    public class a extends w1 {
        public final /* synthetic */ z0 a;
        public final /* synthetic */ long b;
        public final /* synthetic */ InputStream c;

        public a(z0 z0Var, long j, InputStream inputStream) {
            this.a = z0Var;
            this.b = j;
            this.c = inputStream;
        }
    }

    public static w1 a(z0 z0Var, long j, InputStream inputStream) {
        if (inputStream != null) {
            return new a(z0Var, j, inputStream);
        }
        throw new NullPointerException("byte stream is null");
    }

    public final byte[] a() throws IOException {
        a aVar = (a) this;
        long j = aVar.b;
        if (j > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + j);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = aVar.c;
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            } catch (Throwable th) {
                f2.a(inputStream);
                throw th;
            }
        }
        f2.a(inputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (j == -1 || j == byteArray.length) {
            return byteArray;
        }
        throw new IOException("Content-Length (" + j + ") and stream length (" + byteArray.length + ") disagree");
    }

    public final String b() throws IOException {
        return new String(a(), StandardCharsets.UTF_8);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        f2.a(((a) this).c);
    }
}
