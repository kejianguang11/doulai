package com.igexin.push.f.a;

import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class e extends com.igexin.c.a.d.f {
    public static final int a = -2147483638;
    private static final String c = "HttpTask";
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

    public e(d dVar) {
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
                a(this.f, (byte[]) null);
                this.f = this.f;
                byte[] bArrA = a(this.f);
                if (bArrA != null) {
                    return b(this.f, bArrA);
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
        byte[] bArrA;
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
                a(this.f, bArr);
                this.f = this.f;
                bArrA = a(bArr, this.f);
            } catch (Throwable th) {
                th = th;
                r1 = bArr;
                com.igexin.c.a.b.g.a((Closeable) r1);
                g();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            com.igexin.c.a.b.g.a((Closeable) r1);
            g();
            throw th;
        }
        if (bArrA == null) {
            a aVar = new a(true, null);
            com.igexin.c.a.b.g.a((Closeable) null);
            g();
            return aVar;
        }
        this.f.connect();
        dataOutputStream = new DataOutputStream(this.f.getOutputStream());
        try {
            dataOutputStream.write(bArrA, 0, bArrA.length);
            dataOutputStream.flush();
            byte[] bArrA2 = a(this.f);
            if (bArrA2 != null) {
                a aVarB = b(this.f, bArrA2);
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

    private static void a(HttpURLConnection httpURLConnection, byte[] bArr) throws Exception {
        if (httpURLConnection == null) {
            return;
        }
        byte[] bArr2 = new byte[0];
        if (bArr == null) {
            bArr = bArr2;
        }
        httpURLConnection.addRequestProperty("GT_C_T", "1");
        httpURLConnection.addRequestProperty("GT_C_K", new String(com.igexin.push.g.g.c()));
        httpURLConnection.addRequestProperty("GT_C_V", com.igexin.push.g.g.f());
        String strValueOf = String.valueOf(System.currentTimeMillis());
        String strA = com.igexin.push.g.g.a(strValueOf, bArr);
        httpURLConnection.addRequestProperty("GT_T", strValueOf);
        httpURLConnection.addRequestProperty("GT_C_S", strA);
    }

    private void a(byte[] bArr) {
        try {
            this.b.a(bArr);
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
        }
    }

    private static byte[] a(HttpURLConnection httpURLConnection) throws Exception {
        InputStream inputStream;
        try {
            inputStream = httpURLConnection.getInputStream();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (httpURLConnection.getResponseCode() == 200) {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            com.igexin.c.a.b.g.a(inputStream);
                            return byteArray;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                }
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
                com.igexin.c.a.b.g.a(inputStream);
                throw th;
            }
        } catch (Exception unused2) {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
        com.igexin.c.a.b.g.a(inputStream);
        return null;
    }

    private static byte[] a(byte[] bArr, HttpURLConnection httpURLConnection) {
        String requestProperty;
        try {
            if (!httpURLConnection.getRequestProperties().containsKey("GT_C_S") || (requestProperty = httpURLConnection.getRequestProperty("GT_C_S")) == null) {
                return null;
            }
            return com.igexin.push.g.g.a(bArr, com.igexin.push.g.g.c(requestProperty.getBytes()));
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return null;
        }
    }

    private a b(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            if (!(this.b.l && com.igexin.push.g.a.a())) {
                return new a(false, bArr);
            }
            String headerField = httpURLConnection.getHeaderField("GT_ERR");
            if (headerField != null && headerField.equals("0")) {
                String headerField2 = httpURLConnection.getHeaderField("GT_T");
                if (headerField2 == null) {
                    com.igexin.c.a.c.a.a(c, "GT_T = null");
                    com.igexin.c.a.c.a.a("HttpTask|GT_T = null", new Object[0]);
                    return new a(true, null);
                }
                String headerField3 = httpURLConnection.getHeaderField("GT_C_S");
                if (headerField3 == null) {
                    com.igexin.c.a.c.a.a(c, "GT_C_S = null");
                    com.igexin.c.a.c.a.a("HttpTask|GT_C_S = null", new Object[0]);
                    return new a(true, null);
                }
                byte[] bArrB = com.igexin.push.g.g.b(bArr, com.igexin.push.g.g.c(headerField2.getBytes()));
                String strA = com.igexin.push.g.g.a(headerField2, bArrB);
                if (strA != null && strA.equals(headerField3)) {
                    return new a(false, bArrB);
                }
                com.igexin.c.a.c.a.a(c, "signature = null or error");
                com.igexin.c.a.c.a.a("HttpTask|signature = null or error", new Object[0]);
                return new a(true, null);
            }
            com.igexin.c.a.c.a.a(c, "GT_ERR = ".concat(String.valueOf(headerField)));
            com.igexin.c.a.c.a.a("HttpTask|GT_ERR = ".concat(String.valueOf(headerField)), new Object[0]);
            return new a(true, null);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return new a(true, null);
        }
    }

    private HttpURLConnection b(String str) throws Exception {
        this.f = (HttpURLConnection) new URL(str).openConnection();
        this.f.setConnectTimeout(20000);
        this.f.setReadTimeout(20000);
        this.f.setRequestMethod("GET");
        this.f.setDoInput(true);
        a(this.f, (byte[]) null);
        return this.f;
    }

    private HttpURLConnection b(String str, byte[] bArr) throws Exception {
        this.f = (HttpURLConnection) new URL(str).openConnection();
        this.f.setDoInput(true);
        this.f.setDoOutput(true);
        this.f.setRequestMethod("POST");
        this.f.setUseCaches(false);
        this.f.setInstanceFollowRedirects(true);
        this.f.setRequestProperty("Content-Type", "application/octet-stream");
        this.f.setConnectTimeout(20000);
        this.f.setReadTimeout(20000);
        a(this.f, bArr);
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
        if (this.b == null || this.b.f == null || (this.b.g != null && this.b.g.length > com.igexin.push.config.d.A * 1024)) {
            g();
            com.igexin.c.a.c.a.a(c, "run return ###");
            com.igexin.c.a.c.a.a("HttpTask|run return ###", new Object[0]);
            return;
        }
        if (this.b.g != null && this.b.g.length > 0) {
            this.b.g = com.igexin.c.a.b.g.a(this.b.g);
        }
        for (int i = 0; i < 3; i++) {
            a aVarA = this.b.g == null ? a(this.b.f) : a(this.b.f, this.b.g);
            if (aVarA.a) {
                com.igexin.c.a.c.a.a(c, "http server resp decode header error");
            } else if (aVarA.b != null) {
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
