package com.igexin.push.f.a;

import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public final class g extends com.igexin.c.a.d.f {
    public static final int a = -2147483638;
    private static final String c = "SimpleHttpTask";
    private static final int d = 20000;
    private static final int e = 3;
    public d b;
    private HttpURLConnection f;

    class a {
        boolean a;
        byte[] b;

        public a(boolean z, byte[] bArr) {
            this.a = z;
            this.b = bArr;
        }
    }

    private g(d dVar) {
        super(0);
        this.b = dVar;
    }

    private a a(String str) {
        try {
            try {
                this.f = (HttpURLConnection) new URL(str).openConnection();
                this.f.setConnectTimeout(20000);
                this.f.setReadTimeout(20000);
                this.f.setRequestMethod("GET");
                this.f.setDoInput(true);
                this.f = this.f;
                byte[] bArrA = a(this.f);
                if (bArrA != null) {
                    return b(bArrA);
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
            g();
            return new a(false, null);
        } finally {
            g();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v2 */
    private a a(String str, byte[] bArr) throws Throwable {
        DataOutputStream dataOutputStream;
        ?? r1 = 0;
        try {
            try {
                this.f = (HttpURLConnection) new URL(str).openConnection();
                this.f.setDoInput(true);
                this.f.setDoOutput(true);
                this.f.setRequestMethod("POST");
                this.f.setUseCaches(false);
                this.f.setInstanceFollowRedirects(true);
                this.f.setRequestProperty("Content-Type", "application/octet-stream");
                this.f.setConnectTimeout(20000);
                this.f.setReadTimeout(20000);
                this.f = this.f;
            } catch (Throwable th) {
                th = th;
                r1 = bArr;
                com.igexin.c.a.b.g.a((Closeable) r1);
                g();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            dataOutputStream = null;
        }
        if (bArr == null) {
            a aVar = new a(true, null);
            com.igexin.c.a.b.g.a((Closeable) null);
            g();
            return aVar;
        }
        byte[] bArrB = com.igexin.c.b.a.b(bArr);
        this.f.connect();
        dataOutputStream = new DataOutputStream(this.f.getOutputStream());
        try {
            dataOutputStream.write(bArrB, 0, bArrB.length);
            dataOutputStream.flush();
            byte[] bArrA = a(this.f);
            if (bArrA != null) {
                a aVarB = b(bArrA);
                com.igexin.c.a.b.g.a(dataOutputStream);
                g();
                return aVarB;
            }
        } catch (Throwable th3) {
            th = th3;
            com.igexin.c.a.c.a.a(th);
        }
        com.igexin.c.a.b.g.a(dataOutputStream);
        g();
        return new a(false, null);
    }

    private void a(byte[] bArr) {
        try {
            this.b.a(bArr);
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
        }
    }

    private static byte[] a(HttpURLConnection httpURLConnection) throws Exception {
        InputStream errorStream;
        InputStream inputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (httpURLConnection.getResponseCode() == 200) {
                errorStream = httpURLConnection.getInputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = errorStream.read(bArr);
                        if (i == -1) {
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            com.igexin.c.a.b.g.a(errorStream);
                            return byteArray;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                } catch (Exception unused) {
                } catch (Throwable th) {
                    inputStream = errorStream;
                    th = th;
                    com.igexin.c.a.b.g.a(inputStream);
                    throw th;
                }
            } else {
                errorStream = httpURLConnection.getErrorStream();
            }
        } catch (Exception unused2) {
            errorStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        com.igexin.c.a.b.g.a(errorStream);
        return null;
    }

    private a b(byte[] bArr) {
        try {
            return new a(false, bArr);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return new a(true, null);
        }
    }

    private HttpURLConnection b(String str) throws Exception {
        this.f = (HttpURLConnection) new URL(str).openConnection();
        this.f.setDoInput(true);
        this.f.setDoOutput(true);
        this.f.setRequestMethod("POST");
        this.f.setUseCaches(false);
        this.f.setInstanceFollowRedirects(true);
        this.f.setRequestProperty("Content-Type", "application/octet-stream");
        this.f.setConnectTimeout(20000);
        this.f.setReadTimeout(20000);
        return this.f;
    }

    private HttpURLConnection c(String str) throws Exception {
        this.f = (HttpURLConnection) new URL(str).openConnection();
        this.f.setConnectTimeout(20000);
        this.f.setReadTimeout(20000);
        this.f.setRequestMethod("GET");
        this.f.setDoInput(true);
        return this.f;
    }

    private void g() {
        if (this.f != null) {
            try {
                this.f.disconnect();
                this.f = null;
            } catch (Exception e2) {
                com.igexin.c.a.c.a.a(e2);
            }
        }
    }

    private boolean h() {
        return this.b.l && com.igexin.push.g.a.a();
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void b_() throws Exception {
        super.b_();
        Process.setThreadPriority(10);
        if (this.b == null || this.b.f == null) {
            g();
            com.igexin.c.a.c.a.a(c, "run return ###");
            com.igexin.c.a.c.a.a("SimpleHttpTask|run return ###", new Object[0]);
            return;
        }
        for (int i = 0; i < 3; i++) {
            a aVarA = this.b.g == null ? a(this.b.f) : a(this.b.f, this.b.g);
            if (aVarA.b != null) {
                try {
                    this.b.a(aVarA.b);
                    return;
                } catch (Exception e2) {
                    com.igexin.c.a.c.a.a(e2);
                    return;
                }
            }
            if (i == 2) {
                this.b.a(new Exception("try up to limit"));
                com.igexin.c.a.c.a.a(c, "http request exception, try times = " + (i + 1));
            }
        }
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return -2147483638;
    }

    @Override // com.igexin.c.a.d.f
    public final void e() {
        g();
    }

    @Override // com.igexin.c.a.d.f
    public final void f() {
    }
}
