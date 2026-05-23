package com.igexin.push.f.a;

import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class b extends com.igexin.c.a.d.f {
    public static final String a = "com.igexin.push.f.a.b";
    public static final int b = -2147483639;
    private static final int d = 20000;
    public d c;
    private HttpURLConnection e;

    public b(d dVar) {
        super(0);
        this.c = dVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v6 */
    private byte[] a(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        ?? r0 = 0;
        try {
            try {
                this.e = (HttpURLConnection) new URL(str).openConnection();
                this.e.setConnectTimeout(20000);
                this.e.setReadTimeout(20000);
                this.e.setRequestMethod("GET");
                this.e.setDoInput(true);
                inputStream = this.e.getInputStream();
            } catch (Throwable th) {
                r0 = str;
                th = th;
            }
        } catch (Exception e) {
            e = e;
            inputStream = null;
            byteArrayOutputStream2 = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
        try {
            byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                if (this.e.getResponseCode() == 200) {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, i);
                    }
                    byte[] byteArray = byteArrayOutputStream2.toByteArray();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e2) {
                            com.igexin.c.a.c.a.a(e2);
                        }
                    }
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Exception e3) {
                        com.igexin.c.a.c.a.a(e3);
                    }
                    g();
                    return byteArray;
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                        com.igexin.c.a.c.a.a(e4);
                    }
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception e5) {
                    e = e5;
                    com.igexin.c.a.c.a.a(e);
                }
            } catch (Exception e6) {
                e = e6;
                com.igexin.c.a.c.a.a(e);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e7) {
                        com.igexin.c.a.c.a.a(e7);
                    }
                }
                if (byteArrayOutputStream2 != null) {
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Exception e8) {
                        e = e8;
                        com.igexin.c.a.c.a.a(e);
                    }
                }
            }
        } catch (Exception e9) {
            e = e9;
            byteArrayOutputStream2 = null;
        } catch (Throwable th3) {
            r0 = inputStream;
            th = th3;
            byteArrayOutputStream = null;
            if (r0 != 0) {
                try {
                    r0.close();
                } catch (Exception e10) {
                    com.igexin.c.a.c.a.a(e10);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e11) {
                    com.igexin.c.a.c.a.a(e11);
                }
            }
            g();
            throw th;
        }
        g();
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.DataOutputStream] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v9 */
    private byte[] a(String str, byte[] bArr) throws Throwable {
        ?? r9;
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        InputStream inputStream;
        ?? byteArrayOutputStream2;
        Exception e;
        ?? r0 = 0;
        try {
            try {
                this.e = (HttpURLConnection) new URL(str).openConnection();
                this.e.setDoInput(true);
                this.e.setDoOutput(true);
                this.e.setRequestMethod("POST");
                this.e.setUseCaches(false);
                this.e.setInstanceFollowRedirects(true);
                this.e.setRequestProperty("Content-Type", "application/octet-stream");
                this.e.setConnectTimeout(20000);
                this.e.setReadTimeout(20000);
                this.e.connect();
                dataOutputStream = new DataOutputStream(this.e.getOutputStream());
                try {
                    dataOutputStream.write(bArr, 0, bArr.length);
                    dataOutputStream.flush();
                } catch (Exception e2) {
                    e = e2;
                    inputStream = null;
                    byteArrayOutputStream2 = inputStream;
                } catch (Throwable th) {
                    byteArrayOutputStream = null;
                    r0 = dataOutputStream;
                    th = th;
                    r9 = 0;
                }
            } catch (Throwable th2) {
                r0 = str;
                th = th2;
                r9 = bArr;
            }
        } catch (Exception e3) {
            e = e3;
            dataOutputStream = null;
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            r9 = 0;
            byteArrayOutputStream = null;
        }
        if (this.e.getResponseCode() != 200) {
            try {
                dataOutputStream.close();
            } catch (Exception e4) {
                e = e4;
                com.igexin.c.a.c.a.a(e);
            }
            g();
            return null;
        }
        inputStream = this.e.getInputStream();
        try {
            byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                byte[] bArr2 = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr2);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream2.write(bArr2, 0, i);
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                try {
                    dataOutputStream.close();
                } catch (Exception e5) {
                    com.igexin.c.a.c.a.a(e5);
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e6) {
                        com.igexin.c.a.c.a.a(e6);
                    }
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception e7) {
                    com.igexin.c.a.c.a.a(e7);
                }
                g();
                return byteArray;
            } catch (Exception e8) {
                e = e8;
            }
        } catch (Exception e9) {
            e = e9;
            byteArrayOutputStream2 = 0;
        } catch (Throwable th4) {
            r0 = dataOutputStream;
            th = th4;
            byteArrayOutputStream = null;
            r9 = inputStream;
            if (r0 != 0) {
                try {
                    r0.close();
                } catch (Exception e10) {
                    com.igexin.c.a.c.a.a(e10);
                }
            }
            if (r9 != 0) {
                try {
                    r9.close();
                } catch (Exception e11) {
                    com.igexin.c.a.c.a.a(e11);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e12) {
                    com.igexin.c.a.c.a.a(e12);
                }
            }
            g();
            throw th;
        }
        com.igexin.c.a.c.a.a(e);
        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
            } catch (Exception e13) {
                com.igexin.c.a.c.a.a(e13);
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e14) {
                com.igexin.c.a.c.a.a(e14);
            }
        }
        if (byteArrayOutputStream2 != 0) {
            try {
                byteArrayOutputStream2.close();
            } catch (Exception e15) {
                e = e15;
                com.igexin.c.a.c.a.a(e);
            }
        }
        g();
        return null;
    }

    private void g() {
        if (this.e != null) {
            try {
                this.e.disconnect();
                this.e = null;
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.a
    public final void a() {
        super.a();
        g();
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void b_() throws Exception {
        super.b_();
        Process.setThreadPriority(10);
        if (this.c == null || this.c.f == null || (this.c.g != null && this.c.g.length > com.igexin.push.config.d.A * 1024)) {
            l();
            com.igexin.c.a.c.a.a(a, "run return ###");
            com.igexin.c.a.c.a.a(a + "|run return ###", new Object[0]);
            return;
        }
        try {
            byte[] bArrA = this.c.g == null ? a(this.c.f) : a(this.c.f, this.c.g);
            if (bArrA == null) {
                Exception exc = new Exception("Http response ＝＝ null");
                this.c.a(exc);
                throw exc;
            }
            try {
                this.c.a(bArrA);
                com.igexin.c.a.b.e.a().a(this.c);
                com.igexin.c.a.b.e.a().b();
            } catch (Exception e) {
                this.c.a(e);
                throw e;
            }
        } catch (Exception e2) {
            this.c.a(e2);
            throw e2;
        }
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return -2147483639;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d() {
        this.o = true;
    }

    @Override // com.igexin.c.a.d.f
    public final void e() {
        g();
    }

    @Override // com.igexin.c.a.d.f
    public final void f() {
    }
}
