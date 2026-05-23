package com.mobile.auth.v;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.igexin.push.g.s;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static ConcurrentHashMap<String, HostnameVerifier> a = new ConcurrentHashMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01bc A[Catch: Throwable -> 0x01b8, TryCatch #26 {Throwable -> 0x01b8, blocks: (B:102:0x01b4, B:106:0x01bc, B:108:0x01c1, B:110:0x01c6, B:112:0x01cb), top: B:148:0x01b4, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c1 A[Catch: Throwable -> 0x01b8, TryCatch #26 {Throwable -> 0x01b8, blocks: (B:102:0x01b4, B:106:0x01bc, B:108:0x01c1, B:110:0x01c6, B:112:0x01cb), top: B:148:0x01b4, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01c6 A[Catch: Throwable -> 0x01b8, TryCatch #26 {Throwable -> 0x01b8, blocks: (B:102:0x01b4, B:106:0x01bc, B:108:0x01c1, B:110:0x01c6, B:112:0x01cb), top: B:148:0x01b4, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01cb A[Catch: Throwable -> 0x01b8, TRY_LEAVE, TryCatch #26 {Throwable -> 0x01b8, blocks: (B:102:0x01b4, B:106:0x01bc, B:108:0x01c1, B:110:0x01c6, B:112:0x01cb), top: B:148:0x01b4, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01de A[Catch: Throwable -> 0x01da, TryCatch #0 {Throwable -> 0x01da, blocks: (B:118:0x01d6, B:122:0x01de, B:124:0x01e3, B:126:0x01e8, B:128:0x01ed), top: B:138:0x01d6, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01e3 A[Catch: Throwable -> 0x01da, TryCatch #0 {Throwable -> 0x01da, blocks: (B:118:0x01d6, B:122:0x01de, B:124:0x01e3, B:126:0x01e8, B:128:0x01ed), top: B:138:0x01d6, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01e8 A[Catch: Throwable -> 0x01da, TryCatch #0 {Throwable -> 0x01da, blocks: (B:118:0x01d6, B:122:0x01de, B:124:0x01e3, B:126:0x01e8, B:128:0x01ed), top: B:138:0x01d6, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01ed A[Catch: Throwable -> 0x01da, TRY_LEAVE, TryCatch #0 {Throwable -> 0x01da, blocks: (B:118:0x01d6, B:122:0x01de, B:124:0x01e3, B:126:0x01e8, B:128:0x01ed), top: B:138:0x01d6, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0186 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x018e A[Catch: Throwable -> 0x018a, TryCatch #5 {Throwable -> 0x018a, blocks: (B:84:0x0186, B:88:0x018e, B:90:0x0193, B:92:0x0198, B:94:0x019d), top: B:140:0x0186, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0193 A[Catch: Throwable -> 0x018a, TryCatch #5 {Throwable -> 0x018a, blocks: (B:84:0x0186, B:88:0x018e, B:90:0x0193, B:92:0x0198, B:94:0x019d), top: B:140:0x0186, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0198 A[Catch: Throwable -> 0x018a, TryCatch #5 {Throwable -> 0x018a, blocks: (B:84:0x0186, B:88:0x018e, B:90:0x0193, B:92:0x0198, B:94:0x019d), top: B:140:0x0186, outer: #24 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x019d A[Catch: Throwable -> 0x018a, TRY_LEAVE, TryCatch #5 {Throwable -> 0x018a, blocks: (B:84:0x0186, B:88:0x018e, B:90:0x0193, B:92:0x0198, B:94:0x019d), top: B:140:0x0186, outer: #24 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v25, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r10v27 */
    /* JADX WARN: Type inference failed for: r10v28 */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r10v31 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r1v28, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v32 */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v5, types: [javax.net.ssl.SSLContext] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r5v12, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.security.SecureRandom] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r5v8, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r5v9, types: [javax.net.ssl.HttpsURLConnection] */
    /* JADX WARN: Type inference failed for: r9v0, types: [int] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v34, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v36 */
    /* JADX WARN: Type inference failed for: r9v37 */
    /* JADX WARN: Type inference failed for: r9v38 */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v40 */
    /* JADX WARN: Type inference failed for: r9v41 */
    /* JADX WARN: Type inference failed for: r9v42 */
    /* JADX WARN: Type inference failed for: r9v43 */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v46 */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v8, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(c cVar, int i, int i2) throws IOException {
        OutputStream outputStream;
        byte[] bytes;
        ?? secureRandom;
        f fVar;
        ?? bufferedReader;
        ?? r9;
        ?? r10;
        ?? r1;
        OutputStream outputStream2;
        ?? r5;
        ?? r92;
        ?? r102;
        ?? r12;
        OutputStream outputStream3;
        ?? r52;
        ?? r93;
        Object obj;
        ?? r94;
        Object obj2;
        try {
            String strBuildPopRequestParamas = cVar.buildPopRequestParamas();
            outputStream = null;
            bytes = new byte[0];
            if (strBuildPopRequestParamas != null) {
                bytes = strBuildPopRequestParamas.getBytes(s.b);
            }
            int i3 = Build.VERSION.SDK_INT;
            ?? r13 = i3;
            if (i3 < 21) {
                try {
                    ?? sSLContext = SSLContext.getInstance("TLS");
                    secureRandom = new SecureRandom();
                    sSLContext.init((KeyManager[]) null, null, secureRandom);
                    SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
                    fVar = new f(socketFactory);
                    bufferedReader = socketFactory;
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                    r13 = e;
                    fVar = null;
                    bufferedReader = r13;
                } catch (NoSuchAlgorithmException e2) {
                    e2.printStackTrace();
                    r13 = e2;
                    fVar = null;
                    bufferedReader = r13;
                }
            } else {
                fVar = null;
                bufferedReader = r13;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        try {
            try {
                URL url = new URL(cVar.getBaseUrl());
                secureRandom = (HttpsURLConnection) url.openConnection();
                try {
                    secureRandom.setDoOutput(true);
                    secureRandom.setDoInput(true);
                    secureRandom.setUseCaches(false);
                    secureRandom.setHostnameVerifier(b(cVar.getBaseUrl()));
                    secureRandom.setRequestMethod(cVar.getRequestMethod());
                    secureRandom.setConnectTimeout(i);
                    secureRandom.setReadTimeout(i2);
                    secureRandom.setRequestProperty(com.alipay.sdk.cons.c.f, url.getHost());
                    secureRandom.setRequestProperty("Accept", "application/json");
                    secureRandom.setRequestProperty("x-acs-action", cVar.getAction());
                    secureRandom.setRequestProperty("x-sdk-invoke-type", "common");
                    secureRandom.setRequestProperty("x-sdk-client", "Java/2.0.0");
                    secureRandom.setRequestProperty("traceparent", UUID.randomUUID().toString());
                    secureRandom.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    secureRandom.setRequestProperty("User-Agent", "AlibabaCloud (Linux; amd64) Java/1.8.0_152-b187 Core/4.5.26 HTTPClient/ApacheHttpClient");
                    secureRandom.setRequestProperty("x-acs-version", "2017-05-25");
                    if (fVar != null) {
                        secureRandom.setSSLSocketFactory(fVar);
                    }
                    secureRandom.connect();
                    OutputStream outputStream4 = secureRandom.getOutputStream();
                    try {
                        outputStream4.write(bytes);
                        i = secureRandom.getResponseCode() == 200 ? secureRandom.getInputStream() : secureRandom.getErrorStream();
                        try {
                            i2 = new InputStreamReader((InputStream) i, s.b);
                            try {
                                bufferedReader = new BufferedReader(i2);
                                try {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        stringBuffer.append(line);
                                        ExceptionProcessor.processException(th);
                                        return null;
                                    }
                                    String str = new String(stringBuffer);
                                    if (i != 0) {
                                        try {
                                            i.close();
                                        } catch (Throwable th3) {
                                            th3.printStackTrace();
                                        }
                                    }
                                    i2.close();
                                    bufferedReader.close();
                                    if (outputStream4 != null) {
                                        outputStream4.close();
                                    }
                                    if (secureRandom != 0) {
                                        secureRandom.disconnect();
                                    }
                                    return str;
                                } catch (SocketTimeoutException e3) {
                                    outputStream3 = outputStream4;
                                    e = e3;
                                    r12 = bufferedReader;
                                    r52 = secureRandom;
                                    r92 = i;
                                    r102 = i2;
                                    e.printStackTrace();
                                    String stackTraceString = Log.getStackTraceString(e);
                                    if (r92 != 0) {
                                    }
                                    if (r102 != 0) {
                                    }
                                    if (r12 != 0) {
                                    }
                                    if (outputStream3 != null) {
                                    }
                                    if (r52 != 0) {
                                    }
                                    return stackTraceString;
                                } catch (IOException e4) {
                                    outputStream2 = outputStream4;
                                    e = e4;
                                    r1 = bufferedReader;
                                    r5 = secureRandom;
                                    r9 = i;
                                    r10 = i2;
                                    e.printStackTrace();
                                    String stackTraceString2 = Log.getStackTraceString(e);
                                    if (r9 != 0) {
                                    }
                                    if (r10 != 0) {
                                    }
                                    if (r1 != 0) {
                                    }
                                    if (outputStream2 != null) {
                                    }
                                    if (r5 != 0) {
                                    }
                                    return stackTraceString2;
                                } catch (Throwable th4) {
                                    outputStream = outputStream4;
                                    th = th4;
                                    if (i != 0) {
                                    }
                                    if (i2 != 0) {
                                    }
                                    if (bufferedReader != 0) {
                                    }
                                    if (outputStream != null) {
                                    }
                                    if (secureRandom != 0) {
                                    }
                                    throw th;
                                }
                            } catch (SocketTimeoutException e5) {
                                outputStream3 = outputStream4;
                                e = e5;
                                r12 = 0;
                                r52 = secureRandom;
                                r92 = i;
                                r102 = i2;
                            } catch (IOException e6) {
                                outputStream2 = outputStream4;
                                e = e6;
                                r1 = 0;
                                r5 = secureRandom;
                                r9 = i;
                                r10 = i2;
                            } catch (Throwable th5) {
                                outputStream = outputStream4;
                                th = th5;
                                bufferedReader = 0;
                            }
                        } catch (SocketTimeoutException e7) {
                            outputStream3 = outputStream4;
                            e = e7;
                            obj2 = null;
                            r94 = i;
                            r12 = obj2;
                            r52 = secureRandom;
                            r92 = r94;
                            r102 = obj2;
                            e.printStackTrace();
                            String stackTraceString3 = Log.getStackTraceString(e);
                            if (r92 != 0) {
                                try {
                                    r92.close();
                                } catch (Throwable th6) {
                                    th6.printStackTrace();
                                    return stackTraceString3;
                                }
                            }
                            if (r102 != 0) {
                                r102.close();
                            }
                            if (r12 != 0) {
                                r12.close();
                            }
                            if (outputStream3 != null) {
                                outputStream3.close();
                            }
                            if (r52 != 0) {
                                r52.disconnect();
                            }
                            return stackTraceString3;
                        } catch (IOException e8) {
                            outputStream2 = outputStream4;
                            e = e8;
                            obj = null;
                            r93 = i;
                            r1 = obj;
                            r5 = secureRandom;
                            r9 = r93;
                            r10 = obj;
                            e.printStackTrace();
                            String stackTraceString22 = Log.getStackTraceString(e);
                            if (r9 != 0) {
                                try {
                                    r9.close();
                                } catch (Throwable th7) {
                                    th7.printStackTrace();
                                    return stackTraceString22;
                                }
                            }
                            if (r10 != 0) {
                                r10.close();
                            }
                            if (r1 != 0) {
                                r1.close();
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                            }
                            if (r5 != 0) {
                                r5.disconnect();
                            }
                            return stackTraceString22;
                        } catch (Throwable th8) {
                            outputStream = outputStream4;
                            th = th8;
                            i2 = 0;
                            i = i;
                            bufferedReader = i2;
                            if (i != 0) {
                                try {
                                    i.close();
                                } catch (Throwable th9) {
                                    th9.printStackTrace();
                                    throw th;
                                }
                            }
                            if (i2 != 0) {
                                i2.close();
                            }
                            if (bufferedReader != 0) {
                                bufferedReader.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (secureRandom != 0) {
                                secureRandom.disconnect();
                            }
                            throw th;
                        }
                    } catch (SocketTimeoutException e9) {
                        outputStream3 = outputStream4;
                        e = e9;
                        r94 = 0;
                        obj2 = null;
                    } catch (IOException e10) {
                        outputStream2 = outputStream4;
                        e = e10;
                        r93 = 0;
                        obj = null;
                    } catch (Throwable th10) {
                        outputStream = outputStream4;
                        th = th10;
                        i = 0;
                        i2 = 0;
                    }
                } catch (SocketTimeoutException e11) {
                    e = e11;
                    r92 = 0;
                    r102 = 0;
                    r12 = 0;
                    outputStream3 = null;
                    r52 = secureRandom;
                } catch (IOException e12) {
                    e = e12;
                    r9 = 0;
                    r10 = 0;
                    r1 = 0;
                    outputStream2 = null;
                    r5 = secureRandom;
                } catch (Throwable th11) {
                    th = th11;
                    i = 0;
                    i2 = 0;
                    bufferedReader = 0;
                    outputStream = null;
                }
            } catch (Throwable th12) {
                th = th12;
            }
        } catch (SocketTimeoutException e13) {
            e = e13;
            r92 = 0;
            r102 = 0;
            r12 = 0;
            outputStream3 = null;
            r52 = 0;
        } catch (IOException e14) {
            e = e14;
            r9 = 0;
            r10 = 0;
            r1 = 0;
            outputStream2 = null;
            r5 = 0;
        } catch (Throwable th13) {
            th = th13;
            i = 0;
            i2 = 0;
            bufferedReader = 0;
            outputStream = null;
            secureRandom = 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x013f A[Catch: Throwable -> 0x013b, TryCatch #1 {Throwable -> 0x013b, blocks: (B:103:0x0137, B:107:0x013f, B:109:0x0144, B:111:0x0149, B:113:0x014e), top: B:125:0x0137, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0144 A[Catch: Throwable -> 0x013b, TryCatch #1 {Throwable -> 0x013b, blocks: (B:103:0x0137, B:107:0x013f, B:109:0x0144, B:111:0x0149, B:113:0x014e), top: B:125:0x0137, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0149 A[Catch: Throwable -> 0x013b, TryCatch #1 {Throwable -> 0x013b, blocks: (B:103:0x0137, B:107:0x013f, B:109:0x0144, B:111:0x0149, B:113:0x014e), top: B:125:0x0137, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x014e A[Catch: Throwable -> 0x013b, TRY_LEAVE, TryCatch #1 {Throwable -> 0x013b, blocks: (B:103:0x0137, B:107:0x013f, B:109:0x0144, B:111:0x0149, B:113:0x014e), top: B:125:0x0137, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0137 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00f2 A[Catch: Throwable -> 0x00ee, TryCatch #14 {Throwable -> 0x00ee, blocks: (B:69:0x00ea, B:73:0x00f2, B:75:0x00f7, B:77:0x00fc, B:79:0x0101), top: B:130:0x00ea, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00f7 A[Catch: Throwable -> 0x00ee, TryCatch #14 {Throwable -> 0x00ee, blocks: (B:69:0x00ea, B:73:0x00f2, B:75:0x00f7, B:77:0x00fc, B:79:0x0101), top: B:130:0x00ea, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00fc A[Catch: Throwable -> 0x00ee, TryCatch #14 {Throwable -> 0x00ee, blocks: (B:69:0x00ea, B:73:0x00f2, B:75:0x00f7, B:77:0x00fc, B:79:0x0101), top: B:130:0x00ea, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0101 A[Catch: Throwable -> 0x00ee, TRY_LEAVE, TryCatch #14 {Throwable -> 0x00ee, blocks: (B:69:0x00ea, B:73:0x00f2, B:75:0x00f7, B:77:0x00fc, B:79:0x0101), top: B:130:0x00ea, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x011d A[Catch: Throwable -> 0x0119, TryCatch #23 {Throwable -> 0x0119, blocks: (B:87:0x0115, B:91:0x011d, B:93:0x0122, B:95:0x0127, B:97:0x012c), top: B:133:0x0115, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0122 A[Catch: Throwable -> 0x0119, TryCatch #23 {Throwable -> 0x0119, blocks: (B:87:0x0115, B:91:0x011d, B:93:0x0122, B:95:0x0127, B:97:0x012c), top: B:133:0x0115, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0127 A[Catch: Throwable -> 0x0119, TryCatch #23 {Throwable -> 0x0119, blocks: (B:87:0x0115, B:91:0x011d, B:93:0x0122, B:95:0x0127, B:97:0x012c), top: B:133:0x0115, outer: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x012c A[Catch: Throwable -> 0x0119, TRY_LEAVE, TryCatch #23 {Throwable -> 0x0119, blocks: (B:87:0x0115, B:91:0x011d, B:93:0x0122, B:95:0x0127, B:97:0x012c), top: B:133:0x0115, outer: #22 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.net.URL] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.mobile.auth.v.c] */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v26, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v28 */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v30 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35 */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [int] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v23, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v27 */
    /* JADX WARN: Type inference failed for: r6v28 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(c cVar, int i, int i2, int i3) throws IOException {
        ?? url;
        ?? BuildPopRequestParamas;
        byte[] bytes;
        ?? bufferedReader;
        ?? r5;
        Object obj;
        ?? r52;
        Object obj2;
        ?? r53;
        Object obj3;
        try {
            url = new URL(cVar.getBaseUrl());
            BuildPopRequestParamas = cVar.buildPopRequestParamas();
            bytes = new byte[0];
            if (BuildPopRequestParamas != 0) {
                bytes = BuildPopRequestParamas.getBytes(s.b);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        try {
            try {
                BuildPopRequestParamas = (HttpURLConnection) url.openConnection();
                try {
                    BuildPopRequestParamas.setDoOutput(true);
                    BuildPopRequestParamas.setDoInput(true);
                    BuildPopRequestParamas.setUseCaches(false);
                    BuildPopRequestParamas.setRequestMethod(cVar.getRequestMethod());
                    BuildPopRequestParamas.setConnectTimeout(i);
                    BuildPopRequestParamas.setReadTimeout(i2);
                    BuildPopRequestParamas.setRequestProperty("Host", url.getHost());
                    BuildPopRequestParamas.setRequestProperty("Accept", "text/xml,text/javascript");
                    BuildPopRequestParamas.setRequestProperty("User-Agent", "top-sdk-java");
                    BuildPopRequestParamas.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    BuildPopRequestParamas.connect();
                    cVar = BuildPopRequestParamas.getOutputStream();
                } catch (SocketTimeoutException e) {
                    e = e;
                    r53 = 0;
                    obj3 = null;
                } catch (IOException e2) {
                    e = e2;
                    r52 = 0;
                    obj2 = null;
                } catch (Throwable th3) {
                    th = th3;
                    r5 = 0;
                    obj = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
            try {
                cVar.write(bytes);
                i = BuildPopRequestParamas.getInputStream();
                try {
                    i2 = new InputStreamReader((InputStream) i, s.b);
                    try {
                        bufferedReader = new BufferedReader(i2);
                        try {
                            StringBuffer stringBuffer = new StringBuffer();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                stringBuffer.append(line);
                                ExceptionProcessor.processException(th);
                                return null;
                            }
                            String str = new String(stringBuffer);
                            if (i != 0) {
                                try {
                                    i.close();
                                } catch (Throwable th5) {
                                    th5.printStackTrace();
                                }
                            }
                            i2.close();
                            bufferedReader.close();
                            if (cVar != 0) {
                                cVar.close();
                            }
                            if (BuildPopRequestParamas != 0) {
                                BuildPopRequestParamas.disconnect();
                            }
                            return str;
                        } catch (SocketTimeoutException e3) {
                            e = e3;
                            String stackTraceString = Log.getStackTraceString(e);
                            if (i != 0) {
                                try {
                                    i.close();
                                } catch (Throwable th6) {
                                    th6.printStackTrace();
                                    return stackTraceString;
                                }
                            }
                            if (i2 != 0) {
                                i2.close();
                            }
                            if (bufferedReader != 0) {
                                bufferedReader.close();
                            }
                            if (cVar != 0) {
                                cVar.close();
                            }
                            if (BuildPopRequestParamas != 0) {
                                BuildPopRequestParamas.disconnect();
                            }
                            return stackTraceString;
                        } catch (IOException e4) {
                            e = e4;
                            String stackTraceString2 = Log.getStackTraceString(e);
                            if (i != 0) {
                                try {
                                    i.close();
                                } catch (Throwable th7) {
                                    th7.printStackTrace();
                                    return stackTraceString2;
                                }
                            }
                            if (i2 != 0) {
                                i2.close();
                            }
                            if (bufferedReader != 0) {
                                bufferedReader.close();
                            }
                            if (cVar != 0) {
                                cVar.close();
                            }
                            if (BuildPopRequestParamas != 0) {
                                BuildPopRequestParamas.disconnect();
                            }
                            return stackTraceString2;
                        }
                    } catch (SocketTimeoutException e5) {
                        e = e5;
                        bufferedReader = 0;
                    } catch (IOException e6) {
                        e = e6;
                        bufferedReader = 0;
                    } catch (Throwable th8) {
                        th = th8;
                        url = 0;
                        if (i != 0) {
                            try {
                                i.close();
                            } catch (Throwable th9) {
                                th9.printStackTrace();
                                throw th;
                            }
                        }
                        if (i2 != 0) {
                            i2.close();
                        }
                        if (url != 0) {
                            url.close();
                        }
                        if (cVar != 0) {
                            cVar.close();
                        }
                        if (BuildPopRequestParamas != 0) {
                            BuildPopRequestParamas.disconnect();
                        }
                        throw th;
                    }
                } catch (SocketTimeoutException e7) {
                    e = e7;
                    i2 = 0;
                    cVar = cVar;
                    i = i;
                    bufferedReader = i2;
                    String stackTraceString3 = Log.getStackTraceString(e);
                    if (i != 0) {
                    }
                    if (i2 != 0) {
                    }
                    if (bufferedReader != 0) {
                    }
                    if (cVar != 0) {
                    }
                    if (BuildPopRequestParamas != 0) {
                    }
                    return stackTraceString3;
                } catch (IOException e8) {
                    e = e8;
                    i2 = 0;
                    cVar = cVar;
                    i = i;
                    bufferedReader = i2;
                    String stackTraceString22 = Log.getStackTraceString(e);
                    if (i != 0) {
                    }
                    if (i2 != 0) {
                    }
                    if (bufferedReader != 0) {
                    }
                    if (cVar != 0) {
                    }
                    if (BuildPopRequestParamas != 0) {
                    }
                    return stackTraceString22;
                } catch (Throwable th10) {
                    th = th10;
                    i2 = 0;
                    cVar = cVar;
                    i = i;
                    url = i2;
                    if (i != 0) {
                    }
                    if (i2 != 0) {
                    }
                    if (url != 0) {
                    }
                    if (cVar != 0) {
                    }
                    if (BuildPopRequestParamas != 0) {
                    }
                    throw th;
                }
            } catch (SocketTimeoutException e9) {
                e = e9;
                obj3 = null;
                r53 = cVar;
                i2 = obj3;
                cVar = r53;
                i = obj3;
                bufferedReader = i2;
                String stackTraceString32 = Log.getStackTraceString(e);
                if (i != 0) {
                }
                if (i2 != 0) {
                }
                if (bufferedReader != 0) {
                }
                if (cVar != 0) {
                }
                if (BuildPopRequestParamas != 0) {
                }
                return stackTraceString32;
            } catch (IOException e10) {
                e = e10;
                obj2 = null;
                r52 = cVar;
                i2 = obj2;
                cVar = r52;
                i = obj2;
                bufferedReader = i2;
                String stackTraceString222 = Log.getStackTraceString(e);
                if (i != 0) {
                }
                if (i2 != 0) {
                }
                if (bufferedReader != 0) {
                }
                if (cVar != 0) {
                }
                if (BuildPopRequestParamas != 0) {
                }
                return stackTraceString222;
            } catch (Throwable th11) {
                th = th11;
                obj = null;
                r5 = cVar;
                i2 = obj;
                cVar = r5;
                i = obj;
                url = i2;
                if (i != 0) {
                }
                if (i2 != 0) {
                }
                if (url != 0) {
                }
                if (cVar != 0) {
                }
                if (BuildPopRequestParamas != 0) {
                }
                throw th;
            }
        } catch (SocketTimeoutException e11) {
            e = e11;
            cVar = 0;
            i = 0;
            i2 = 0;
            bufferedReader = 0;
            BuildPopRequestParamas = 0;
        } catch (IOException e12) {
            e = e12;
            cVar = 0;
            i = 0;
            i2 = 0;
            bufferedReader = 0;
            BuildPopRequestParamas = 0;
        } catch (Throwable th12) {
            th = th12;
            cVar = 0;
            i = 0;
            i2 = 0;
            url = 0;
            BuildPopRequestParamas = 0;
        }
    }

    public static String a(String str) {
        try {
            try {
                return new URL(str).getHost();
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return null;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return null;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HostnameVerifier b(String str) {
        try {
            String strA = a(str);
            if (a != null && !TextUtils.isEmpty(strA) && a.containsKey(strA)) {
                return a.get(strA);
            }
            HostnameVerifier hostnameVerifier = new HostnameVerifier() { // from class: com.mobile.auth.v.a.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str2, SSLSession sSLSession) {
                    try {
                        return HttpsURLConnection.getDefaultHostnameVerifier().verify(str2, sSLSession);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                            return false;
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                            return false;
                        }
                    }
                }
            };
            if (a == null) {
                a = new ConcurrentHashMap<>();
            }
            a.put(strA, hostnameVerifier);
            return hostnameVerifier;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
