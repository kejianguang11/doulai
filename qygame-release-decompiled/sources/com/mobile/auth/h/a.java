package com.mobile.auth.h;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.push.g.s;
import com.mobile.auth.gatewayauth.Constant;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class a implements b {
    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x020e  */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v13, types: [java.lang.CharSequence, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4, types: [java.lang.CharSequence, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6, types: [java.lang.CharSequence, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r16v0, types: [com.mobile.auth.h.a] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v20, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.Closeable] */
    @Override // com.mobile.auth.h.b
    @TargetApi(21)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(com.mobile.auth.j.c cVar, com.mobile.auth.k.c cVar2, com.cmic.sso.sdk.a aVar) throws Throwable {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        ?? sb;
        int responseCode;
        ?? inputStream;
        int i;
        com.mobile.auth.k.a aVarA;
        URLConnection uRLConnectionOpenConnection;
        byte[] bArr;
        com.mobile.auth.l.c.b("ConnectionInterceptor", "请求地址: " + cVar.a());
        try {
            URL url = new URL(cVar.a());
            if (cVar.h() != null) {
                com.mobile.auth.l.c.b("ConnectionInterceptor", "开始wifi下取号");
                uRLConnectionOpenConnection = cVar.h().openConnection(url);
            } else {
                com.mobile.auth.l.c.b("ConnectionInterceptor", "使用当前网络环境发送请求");
                uRLConnectionOpenConnection = url.openConnection();
            }
            httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            try {
                try {
                    Map<String, String> mapC = cVar.c();
                    if (mapC != null) {
                        for (String str : mapC.keySet()) {
                            httpURLConnection.addRequestProperty(str, mapC.get(str));
                        }
                    }
                    if ((httpURLConnection instanceof HttpsURLConnection) && Build.VERSION.SDK_INT < 20) {
                        com.mobile.auth.l.c.b("ConnectionInterceptor", "5.0以下启动tls 1.2");
                        ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(new com.mobile.auth.g.b(HttpsURLConnection.getDefaultSSLSocketFactory()));
                    }
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setInstanceFollowRedirects(false);
                    httpURLConnection.setConnectTimeout(Constant.DEFAULT_TIMEOUT);
                    httpURLConnection.setReadTimeout(Constant.DEFAULT_TIMEOUT);
                    httpURLConnection.setDefaultUseCaches(false);
                    String strE = cVar.e();
                    httpURLConnection.setRequestMethod(strE);
                    httpURLConnection.setDoOutput(true);
                    if (cVar instanceof com.mobile.auth.j.b) {
                        httpURLConnection.connect();
                        try {
                            ((com.mobile.auth.j.b) cVar).a(aVar);
                        } catch (Exception e) {
                            e = e;
                            outputStream = null;
                            sb = outputStream;
                            responseCode = -1;
                            inputStream = sb;
                            e.printStackTrace();
                            com.mobile.auth.l.c.a("ConnectionInterceptor", "请求失败: " + cVar.a());
                            aVar.a().a.add(e);
                            i = e instanceof EOFException ? 200050 : 102102;
                            a(outputStream);
                            a(inputStream);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + i);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("responseResult: ");
                            sb2.append(TextUtils.isEmpty(sb) ? "" : sb.toString());
                            com.mobile.auth.l.c.b("ConnectionInterceptor", sb2.toString());
                            if (i != 200) {
                                switch (i) {
                                    case 301:
                                    case 302:
                                        break;
                                    default:
                                        aVarA = com.mobile.auth.k.a.a(i);
                                        break;
                                }
                                cVar2.a(aVarA);
                                return;
                            }
                            cVar2.a((com.mobile.auth.k.b) null);
                            return;
                        }
                    }
                    if (strE.endsWith("POST")) {
                        outputStream = httpURLConnection.getOutputStream();
                        try {
                            outputStream.write(cVar.d().getBytes(s.b));
                            outputStream.flush();
                        } catch (Exception e2) {
                            e = e2;
                            sb = 0;
                            responseCode = -1;
                            inputStream = sb;
                            e.printStackTrace();
                            com.mobile.auth.l.c.a("ConnectionInterceptor", "请求失败: " + cVar.a());
                            aVar.a().a.add(e);
                            if (e instanceof EOFException) {
                            }
                            a(outputStream);
                            a(inputStream);
                            if (httpURLConnection != null) {
                            }
                            com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + i);
                            StringBuilder sb22 = new StringBuilder();
                            sb22.append("responseResult: ");
                            sb22.append(TextUtils.isEmpty(sb) ? "" : sb.toString());
                            com.mobile.auth.l.c.b("ConnectionInterceptor", sb22.toString());
                            if (i != 200) {
                            }
                            cVar2.a((com.mobile.auth.k.b) null);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            sb = 0;
                            responseCode = -1;
                            inputStream = sb;
                            a(outputStream);
                            a(inputStream);
                            if (httpURLConnection != null) {
                            }
                            com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + responseCode);
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("responseResult: ");
                            sb3.append(!TextUtils.isEmpty(sb) ? "" : sb.toString());
                            com.mobile.auth.l.c.b("ConnectionInterceptor", sb3.toString());
                            if (responseCode == 200) {
                            }
                            throw th;
                        }
                    } else {
                        outputStream = null;
                    }
                    responseCode = httpURLConnection.getResponseCode();
                    try {
                        inputStream = httpURLConnection.getInputStream();
                        try {
                            bArr = new byte[2048];
                            sb = new StringBuilder();
                        } catch (Exception e3) {
                            e = e3;
                            sb = 0;
                        } catch (Throwable th2) {
                            th = th2;
                            sb = 0;
                            a(outputStream);
                            a(inputStream);
                            if (httpURLConnection != null) {
                            }
                            com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + responseCode);
                            StringBuilder sb32 = new StringBuilder();
                            sb32.append("responseResult: ");
                            sb32.append(!TextUtils.isEmpty(sb) ? "" : sb.toString());
                            com.mobile.auth.l.c.b("ConnectionInterceptor", sb32.toString());
                            if (responseCode == 200) {
                            }
                            throw th;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        inputStream = 0;
                        sb = 0;
                    } catch (Throwable th3) {
                        th = th3;
                        inputStream = 0;
                        sb = 0;
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Throwable th4) {
                th = th4;
                outputStream = null;
                sb = outputStream;
                responseCode = -1;
                inputStream = sb;
                a(outputStream);
                a(inputStream);
                if (httpURLConnection != null) {
                }
                com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + responseCode);
                StringBuilder sb322 = new StringBuilder();
                sb322.append("responseResult: ");
                sb322.append(!TextUtils.isEmpty(sb) ? "" : sb.toString());
                com.mobile.auth.l.c.b("ConnectionInterceptor", sb322.toString());
                if (responseCode == 200) {
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            httpURLConnection = null;
            outputStream = null;
        } catch (Throwable th5) {
            th = th5;
            httpURLConnection = null;
            outputStream = null;
        }
        while (true) {
            try {
                try {
                    int i2 = inputStream.read(bArr);
                    if (i2 <= 0) {
                        break;
                    } else {
                        sb.append(new String(bArr, 0, i2, s.b));
                    }
                } catch (Exception e7) {
                    e = e7;
                    e.printStackTrace();
                    com.mobile.auth.l.c.a("ConnectionInterceptor", "请求失败: " + cVar.a());
                    aVar.a().a.add(e);
                    if (e instanceof EOFException) {
                    }
                    a(outputStream);
                    a(inputStream);
                    if (httpURLConnection != null) {
                    }
                    com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + i);
                    StringBuilder sb222 = new StringBuilder();
                    sb222.append("responseResult: ");
                    sb222.append(TextUtils.isEmpty(sb) ? "" : sb.toString());
                    com.mobile.auth.l.c.b("ConnectionInterceptor", sb222.toString());
                    if (i != 200) {
                    }
                    cVar2.a((com.mobile.auth.k.b) null);
                    return;
                }
                cVar2.a(aVarA);
                return;
            } catch (Throwable th6) {
                th = th6;
                a(outputStream);
                a(inputStream);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + responseCode);
                StringBuilder sb3222 = new StringBuilder();
                sb3222.append("responseResult: ");
                sb3222.append(!TextUtils.isEmpty(sb) ? "" : sb.toString());
                com.mobile.auth.l.c.b("ConnectionInterceptor", sb3222.toString());
                if (responseCode == 200) {
                    switch (responseCode) {
                        case 301:
                        case 302:
                            cVar2.a((com.mobile.auth.k.b) null);
                            break;
                        default:
                            cVar2.a(com.mobile.auth.k.a.a(responseCode));
                            break;
                    }
                }
                throw th;
            }
        }
        com.mobile.auth.k.b bVar = new com.mobile.auth.k.b(responseCode, httpURLConnection.getHeaderFields(), sb.toString());
        a(outputStream);
        a(inputStream);
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        com.mobile.auth.l.c.b("ConnectionInterceptor", "responseCode: " + responseCode);
        StringBuilder sb4 = new StringBuilder();
        sb4.append("responseResult: ");
        sb4.append(TextUtils.isEmpty(sb) ? "" : sb.toString());
        com.mobile.auth.l.c.b("ConnectionInterceptor", sb4.toString());
        if (responseCode != 200) {
            switch (responseCode) {
                case 301:
                case 302:
                    break;
                default:
                    aVarA = com.mobile.auth.k.a.a(responseCode);
                    break;
            }
            cVar2.a(aVarA);
            return;
        }
        cVar2.a(bVar);
    }
}
