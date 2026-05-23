package com.mobile.auth.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public class i {
    private static final String a = "i";

    public static h a(Context context, HttpURLConnection httpURLConnection) {
        String str;
        try {
            h hVar = new h();
            try {
                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                List<String> list = headerFields.get("Log-Level");
                if (list != null && !list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        String str2 = list.get(0);
                        if (!TextUtils.isEmpty(str2)) {
                            com.mobile.auth.b.e.b(context, str2);
                        }
                    }
                }
                List<String> list2 = headerFields.get("dm");
                if (list2 != null && !list2.isEmpty() && (str = list2.get(0)) != null && (str.equals("1") || str.equals(com.igexin.push.config.c.H))) {
                    String strH = k.h(context);
                    if (!TextUtils.isEmpty(strH) && !strH.equals(str)) {
                        hVar.b = true;
                    }
                }
                List<String> list3 = headerFields.get("p-ikgx");
                if (list3 != null && !list3.isEmpty()) {
                    String str3 = list3.get(0);
                    if (!TextUtils.isEmpty(str3)) {
                        hVar.a = str3;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return hVar;
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x033e: MOVE (r2 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:146:0x033d */
    /* JADX WARN: Removed duplicated region for block: B:105:0x024b A[Catch: Throwable -> 0x03b3, TRY_ENTER, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0250 A[Catch: Throwable -> 0x03b3, TRY_LEAVE, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02a7 A[Catch: Throwable -> 0x03b3, TRY_ENTER, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02ac A[Catch: Throwable -> 0x03b3, PHI: r2 r5
      0x02ac: PHI (r2v16 ??) = (r2v13 ??), (r2v14 ??), (r2v17 ??) binds: [B:127:0x02aa, B:137:0x02f6, B:169:0x03af] A[DONT_GENERATE, DONT_INLINE]
      0x02ac: PHI (r5v14 java.io.BufferedReader) = (r5v10 java.io.BufferedReader), (r5v11 java.io.BufferedReader), (r5v15 java.io.BufferedReader) binds: [B:127:0x02aa, B:137:0x02f6, B:169:0x03af] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02f3 A[Catch: Throwable -> 0x03b3, TRY_ENTER, TRY_LEAVE, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0301 A[Catch: all -> 0x033c, TRY_ENTER, TryCatch #21 {all -> 0x033c, blocks: (B:58:0x01a8, B:60:0x01ae, B:61:0x01b7, B:64:0x01c7, B:68:0x01d4, B:69:0x01d9, B:71:0x01e4, B:73:0x01ec, B:74:0x01f0, B:144:0x0301, B:147:0x0342, B:150:0x034c, B:152:0x0352, B:154:0x0357, B:156:0x035c), top: B:199:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x035c A[Catch: all -> 0x033c, Throwable -> 0x035f, TRY_LEAVE, TryCatch #3 {Throwable -> 0x035f, blocks: (B:154:0x0357, B:156:0x035c), top: B:190:0x0357 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0361 A[Catch: Throwable -> 0x03b3, TRY_ENTER, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0366 A[Catch: Throwable -> 0x03b3, TRY_LEAVE, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03ac A[Catch: Throwable -> 0x03b3, TRY_ENTER, TRY_LEAVE, TryCatch #18 {Throwable -> 0x03b3, blocks: (B:105:0x024b, B:107:0x0250, B:126:0x02a7, B:128:0x02ac, B:136:0x02f3, B:158:0x0361, B:160:0x0366, B:168:0x03ac), top: B:201:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x03bd A[Catch: Throwable -> 0x03c0, TRY_LEAVE, TryCatch #6 {Throwable -> 0x03c0, blocks: (B:175:0x03b8, B:177:0x03bd), top: B:194:0x03b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0357 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03b8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:? A[Catch: Throwable -> 0x03c1, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Throwable -> 0x03c1, blocks: (B:3:0x0006, B:7:0x0015, B:178:0x03c0, B:10:0x001a, B:6:0x0013), top: B:192:0x0006 }] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v41 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static n a(Context context, String str, String str2, Network network, String str3, String str4, boolean z) {
        Throwable th;
        InputStream inputStream;
        Object obj;
        ?? r2;
        ?? r5;
        Throwable th2;
        InputStream inputStream2;
        IOException iOException;
        InputStream inputStream3;
        UnknownHostException unknownHostException;
        InputStream inputStream4;
        SocketTimeoutException socketTimeoutException;
        InputStream inputStream5;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        HttpsURLConnection httpsURLConnection;
        int responseCode;
        StringBuilder sb;
        InputStream inputStream6;
        ?? r22 = str;
        try {
            n nVar = new n();
            int i = 3000;
            int i2 = com.mobile.auth.a.a.c <= 0 ? 3000 : com.mobile.auth.a.a.c;
            if (com.mobile.auth.a.a.d > 0) {
                i = com.mobile.auth.a.a.d;
            }
            try {
                try {
                    try {
                        try {
                            try {
                                URL url = new URL(r22);
                                httpsURLConnection = (HttpsURLConnection) ((network == null || Build.VERSION.SDK_INT < 21) ? url.openConnection() : network.openConnection(url));
                                httpsURLConnection.setRequestProperty("accept", "*/*");
                                httpsURLConnection.setRequestMethod("POST");
                                httpsURLConnection.setDoOutput(true);
                                httpsURLConnection.setDoInput(true);
                                httpsURLConnection.setConnectTimeout(i2);
                                httpsURLConnection.setReadTimeout(i);
                                httpsURLConnection.setUseCaches(false);
                                httpsURLConnection.setHostnameVerifier(a());
                                if (Build.VERSION.SDK_INT < 21) {
                                    httpsURLConnection.setInstanceFollowRedirects(false);
                                }
                                httpsURLConnection.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                                httpsURLConnection.addRequestProperty("deviceId", str3);
                                try {
                                    httpsURLConnection.addRequestProperty("reqId", str4);
                                    if (TextUtils.isEmpty(str2)) {
                                        httpsURLConnection.connect();
                                    } else {
                                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(httpsURLConnection.getOutputStream()));
                                        dataOutputStream.write(str2.getBytes(com.alipay.sdk.sys.a.m));
                                        dataOutputStream.flush();
                                        dataOutputStream.close();
                                    }
                                    if (Build.VERSION.SDK_INT < 21 && httpsURLConnection.getResponseCode() == 302) {
                                        String headerField = httpsURLConnection.getHeaderField("Location");
                                        if (!TextUtils.isEmpty(headerField)) {
                                            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                                            if (connectivityManager.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) == 0) {
                                                Class.forName("android.net.ConnectivityManager").getMethod("requestRouteToHost", Integer.TYPE, Integer.TYPE).invoke(connectivityManager, 5, Integer.valueOf(j.a(j.b(headerField))));
                                            }
                                        }
                                        URL url2 = new URL(headerField);
                                        httpsURLConnection = (HttpsURLConnection) ((network == null || Build.VERSION.SDK_INT < 21) ? url2.openConnection() : network.openConnection(url2));
                                        httpsURLConnection.setRequestProperty("accept", "*/*");
                                        httpsURLConnection.setRequestMethod("POST");
                                        httpsURLConnection.setDoOutput(true);
                                        httpsURLConnection.setDoInput(true);
                                        httpsURLConnection.setConnectTimeout(i2);
                                        httpsURLConnection.setReadTimeout(i);
                                        httpsURLConnection.setUseCaches(false);
                                        httpsURLConnection.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                                        httpsURLConnection.connect();
                                    }
                                    responseCode = httpsURLConnection.getResponseCode();
                                } catch (SocketTimeoutException e) {
                                    e = e;
                                    socketTimeoutException = e;
                                    inputStream5 = null;
                                    bufferedReader = null;
                                    r22 = inputStream5;
                                    nVar.b = "{\"result\":80005,\"msg\":\"Socket超时异常\"}";
                                    com.mobile.auth.a.a.a(a, "sendRequest SocketTimeoutException-preauth-" + socketTimeoutException.getMessage(), socketTimeoutException);
                                    com.mobile.auth.b.e.a(str4).h("doPost SocketTimeoutException -- " + socketTimeoutException.getMessage());
                                    if (bufferedReader != null) {
                                    }
                                    if (r22 != 0) {
                                    }
                                    return nVar;
                                } catch (UnknownHostException e2) {
                                    e = e2;
                                    unknownHostException = e;
                                    inputStream4 = null;
                                    bufferedReader2 = null;
                                    if (!z) {
                                    }
                                    nVar.e = r22.contains("card.e.189.cn") ? "1" : com.igexin.push.config.c.H;
                                    nVar.d = true;
                                    if (bufferedReader2 != null) {
                                    }
                                    if (inputStream4 != null) {
                                    }
                                    if (bufferedReader2 != null) {
                                    }
                                    if (inputStream4 != null) {
                                    }
                                    return nVar;
                                } catch (IOException e3) {
                                    e = e3;
                                    iOException = e;
                                    inputStream3 = null;
                                    bufferedReader = null;
                                    r22 = inputStream3;
                                    nVar.b = "{\"result\":80007,\"msg\":\"IO异常\"}";
                                    com.mobile.auth.a.a.a(a, "sendRequest IOException-preauth-" + iOException.getMessage(), iOException);
                                    com.mobile.auth.b.e.a(str4).h("doPost IOException -- " + iOException.getMessage());
                                    if (bufferedReader != null) {
                                    }
                                    if (r22 != 0) {
                                    }
                                    return nVar;
                                } catch (Throwable th3) {
                                    th = th3;
                                    th2 = th;
                                    inputStream2 = null;
                                    bufferedReader = null;
                                    r22 = inputStream2;
                                    nVar.b = "{\"result\":80001,\"msg\":\"请求异常\"}";
                                    com.mobile.auth.a.a.a(a, "sendRequest Throwable-preauth-" + th2.getMessage(), th2);
                                    com.mobile.auth.b.e.a(str4).h("doPost Throwable -- " + th2.getMessage());
                                    if (bufferedReader != null) {
                                    }
                                    if (r22 != 0) {
                                    }
                                    return nVar;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                r2 = r22;
                                r5 = bufferedReader;
                                if (r5 != 0) {
                                    try {
                                        r5.close();
                                    } catch (Throwable unused) {
                                        throw th;
                                    }
                                }
                                if (r2 == 0) {
                                    throw th;
                                }
                                r2.close();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            r2 = obj;
                            r5 = 1;
                            if (r5 != 0) {
                            }
                            if (r2 == 0) {
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        inputStream = null;
                    }
                } catch (SocketTimeoutException e4) {
                    e = e4;
                } catch (UnknownHostException e5) {
                    e = e5;
                } catch (IOException e6) {
                    e = e6;
                } catch (Throwable th7) {
                    th = th7;
                }
            } catch (Throwable unused2) {
            }
            if (responseCode == 302) {
                URL url3 = new URL(httpsURLConnection.getHeaderField("Location"));
                HttpsURLConnection httpsURLConnection2 = (HttpsURLConnection) ((network == null || Build.VERSION.SDK_INT < 21) ? url3.openConnection() : network.openConnection(url3));
                httpsURLConnection2.setRequestProperty("accept", "*/*");
                httpsURLConnection2.setRequestMethod("GET");
                httpsURLConnection2.setConnectTimeout(i2);
                httpsURLConnection2.setReadTimeout(i);
                httpsURLConnection2.setUseCaches(false);
                httpsURLConnection2.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                httpsURLConnection2.connect();
            } else {
                long jCurrentTimeMillis = 0;
                if (responseCode == 200) {
                    inputStream4 = httpsURLConnection.getInputStream();
                    try {
                        sb = new StringBuilder();
                        bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream4));
                    } catch (SocketTimeoutException e7) {
                        socketTimeoutException = e7;
                        inputStream5 = inputStream4;
                        bufferedReader = null;
                        r22 = inputStream5;
                        nVar.b = "{\"result\":80005,\"msg\":\"Socket超时异常\"}";
                        com.mobile.auth.a.a.a(a, "sendRequest SocketTimeoutException-preauth-" + socketTimeoutException.getMessage(), socketTimeoutException);
                        com.mobile.auth.b.e.a(str4).h("doPost SocketTimeoutException -- " + socketTimeoutException.getMessage());
                        if (bufferedReader != null) {
                        }
                        if (r22 != 0) {
                        }
                        return nVar;
                    } catch (UnknownHostException e8) {
                        unknownHostException = e8;
                        bufferedReader2 = null;
                        if (!z) {
                        }
                        nVar.e = r22.contains("card.e.189.cn") ? "1" : com.igexin.push.config.c.H;
                        nVar.d = true;
                        if (bufferedReader2 != null) {
                        }
                        if (inputStream4 != null) {
                        }
                        if (bufferedReader2 != null) {
                        }
                        if (inputStream4 != null) {
                        }
                        return nVar;
                    } catch (IOException e9) {
                        iOException = e9;
                        inputStream3 = inputStream4;
                        bufferedReader = null;
                        r22 = inputStream3;
                        nVar.b = "{\"result\":80007,\"msg\":\"IO异常\"}";
                        com.mobile.auth.a.a.a(a, "sendRequest IOException-preauth-" + iOException.getMessage(), iOException);
                        com.mobile.auth.b.e.a(str4).h("doPost IOException -- " + iOException.getMessage());
                        if (bufferedReader != null) {
                        }
                        if (r22 != 0) {
                        }
                        return nVar;
                    } catch (Throwable th8) {
                        th = th8;
                        inputStream = inputStream4;
                        r5 = 0;
                        r2 = inputStream;
                        if (r5 != 0) {
                        }
                        if (r2 == 0) {
                        }
                    }
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                            sb.append("\n");
                        } catch (SocketTimeoutException e10) {
                            socketTimeoutException = e10;
                            r22 = inputStream4;
                            bufferedReader = bufferedReader2;
                            nVar.b = "{\"result\":80005,\"msg\":\"Socket超时异常\"}";
                            com.mobile.auth.a.a.a(a, "sendRequest SocketTimeoutException-preauth-" + socketTimeoutException.getMessage(), socketTimeoutException);
                            com.mobile.auth.b.e.a(str4).h("doPost SocketTimeoutException -- " + socketTimeoutException.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (r22 != 0) {
                                r22.close();
                            }
                        } catch (UnknownHostException e11) {
                            unknownHostException = e11;
                            if (!z) {
                                nVar.b = "{\"result\":80006,\"msg\":\"域名解析异常\"}";
                                com.mobile.auth.a.a.a(a, "sendRequest UnknownHostException-preauth-" + unknownHostException.getMessage(), unknownHostException);
                                com.mobile.auth.b.e.a(str4).h("doPost UnknownHostException -- " + unknownHostException.getMessage());
                            }
                            nVar.e = r22.contains("card.e.189.cn") ? "1" : com.igexin.push.config.c.H;
                            nVar.d = true;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (Throwable unused3) {
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    if (inputStream4 != null) {
                                        inputStream4.close();
                                    }
                                    return nVar;
                                }
                            }
                            if (inputStream4 != null) {
                                inputStream4.close();
                            }
                            if (bufferedReader2 != null) {
                            }
                            if (inputStream4 != null) {
                            }
                        } catch (IOException e12) {
                            iOException = e12;
                            r22 = inputStream4;
                            bufferedReader = bufferedReader2;
                            nVar.b = "{\"result\":80007,\"msg\":\"IO异常\"}";
                            com.mobile.auth.a.a.a(a, "sendRequest IOException-preauth-" + iOException.getMessage(), iOException);
                            com.mobile.auth.b.e.a(str4).h("doPost IOException -- " + iOException.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (r22 != 0) {
                            }
                        } catch (Throwable th9) {
                            th2 = th9;
                            r22 = inputStream4;
                            bufferedReader = bufferedReader2;
                            nVar.b = "{\"result\":80001,\"msg\":\"请求异常\"}";
                            com.mobile.auth.a.a.a(a, "sendRequest Throwable-preauth-" + th2.getMessage(), th2);
                            com.mobile.auth.b.e.a(str4).h("doPost Throwable -- " + th2.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (r22 != 0) {
                            }
                        }
                        return nVar;
                    }
                    nVar.b = sb.toString();
                    h hVarA = a(context, httpsURLConnection);
                    if (hVarA.b && z) {
                        nVar.e = r22.contains("card.e.189.cn") ? "1" : com.igexin.push.config.c.H;
                        nVar.d = true;
                    }
                    com.mobile.auth.b.e.a(str4).g(hVarA.a);
                    if (!z) {
                        if (!r22.contains("id6.me")) {
                            k.a(context, com.igexin.push.config.c.H);
                            jCurrentTimeMillis = System.currentTimeMillis();
                        }
                        k.a(context, jCurrentTimeMillis);
                    }
                    inputStream6 = inputStream4;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    if (inputStream6 != null) {
                        inputStream6.close();
                    }
                    return nVar;
                }
                if (!z) {
                    k.a(context, 0L);
                    nVar.b = "{\"result\":80005,\"msg\":\"Socket超时异常\"}";
                    return nVar;
                }
                nVar.e = r22.contains("card.e.189.cn") ? "1" : com.igexin.push.config.c.H;
                nVar.d = true;
            }
            bufferedReader2 = null;
            inputStream6 = null;
            if (bufferedReader2 != null) {
            }
            if (inputStream6 != null) {
            }
            return nVar;
        } catch (Throwable th10) {
            try {
                ExceptionProcessor.processException(th10);
                return null;
            } catch (Throwable th11) {
                ExceptionProcessor.processException(th11);
                return null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0143 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x014b A[Catch: Exception -> 0x0147, Throwable -> 0x0158, TryCatch #9 {Exception -> 0x0147, blocks: (B:80:0x0143, B:84:0x014b, B:86:0x0150), top: B:102:0x0143, outer: #13 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0150 A[Catch: Exception -> 0x0147, Throwable -> 0x0158, TRY_LEAVE, TryCatch #9 {Exception -> 0x0147, blocks: (B:80:0x0143, B:84:0x014b, B:86:0x0150), top: B:102:0x0143, outer: #13 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(Context context, String str, Network network) {
        InputStreamReader inputStreamReader;
        InputStream inputStream;
        BufferedReader bufferedReader;
        String str2;
        InputStream inputStream2;
        try {
            String str3 = "";
            int i = 3000;
            int i2 = com.mobile.auth.a.a.c <= 0 ? 3000 : com.mobile.auth.a.a.c;
            if (com.mobile.auth.a.a.d > 0) {
                i = com.mobile.auth.a.a.d;
            }
            try {
                URL url = new URL(str);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) ((network == null || Build.VERSION.SDK_INT < 21) ? url.openConnection() : network.openConnection(url));
                httpsURLConnection.setRequestProperty("accept", "*/*");
                httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoOutput(false);
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setConnectTimeout(i2);
                httpsURLConnection.setReadTimeout(i);
                httpsURLConnection.setUseCaches(false);
                httpsURLConnection.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                httpsURLConnection.connect();
                if (httpsURLConnection.getResponseCode() == 302) {
                    URL url2 = new URL(httpsURLConnection.getHeaderField("Location"));
                    httpsURLConnection = (HttpsURLConnection) ((network == null || Build.VERSION.SDK_INT < 21) ? url2.openConnection() : network.openConnection(url2));
                    httpsURLConnection.setRequestProperty("accept", "*/*");
                    httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setDoOutput(false);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setConnectTimeout(i2);
                    httpsURLConnection.setReadTimeout(i);
                    httpsURLConnection.setUseCaches(false);
                    httpsURLConnection.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                    httpsURLConnection.connect();
                }
                if (httpsURLConnection.getResponseCode() == 200) {
                    inputStream2 = httpsURLConnection.getInputStream();
                    try {
                        inputStreamReader = new InputStreamReader(inputStream2);
                        try {
                            bufferedReader = new BufferedReader(inputStreamReader);
                            while (true) {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    str3 = str3 + line;
                                } catch (Throwable th) {
                                    th = th;
                                    Throwable th2 = th;
                                    inputStream = inputStream2;
                                    th = th2;
                                    if (bufferedReader != null) {
                                    }
                                    if (inputStreamReader != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            str2 = "";
                            inputStream = inputStream2;
                            th = th3;
                            bufferedReader = null;
                        }
                    } catch (Throwable th4) {
                        bufferedReader = null;
                        str2 = "";
                        inputStream = inputStream2;
                        th = th4;
                        inputStreamReader = null;
                    }
                } else {
                    inputStream2 = null;
                    inputStreamReader = null;
                    bufferedReader = null;
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return str3;
                    }
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream2 == null) {
                    return str3;
                }
                inputStream2.close();
                return str3;
            } catch (Throwable th5) {
                th = th5;
                inputStreamReader = null;
                inputStream = null;
                bufferedReader = null;
            }
        } catch (Throwable th6) {
            try {
                ExceptionProcessor.processException(th6);
                return null;
            } catch (Throwable th7) {
                ExceptionProcessor.processException(th7);
                return null;
            }
        }
    }

    public static final HostnameVerifier a() {
        try {
            return new HostnameVerifier() { // from class: com.mobile.auth.c.i.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str, SSLSession sSLSession) {
                    try {
                        HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
                        if (!defaultHostnameVerifier.verify("id6.me", sSLSession)) {
                            if (!defaultHostnameVerifier.verify("card.e.189.cn", sSLSession)) {
                                return false;
                            }
                        }
                        return true;
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
