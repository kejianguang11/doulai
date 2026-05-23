package com.mobile.auth.y;

import android.content.Context;
import android.net.Network;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    public static boolean a = false;
    public static boolean b = false;

    class a implements HostnameVerifier {
        a() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            try {
                if (!TextUtils.isEmpty(str) && sSLSession != null) {
                    try {
                        return u.a(str, ((X509Certificate) sSLSession.getPeerCertificates()[0]).getSubjectDN().getName());
                    } catch (SSLPeerUnverifiedException unused) {
                        t.b();
                        return false;
                    }
                }
                return false;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return false;
            }
        }
    }

    private static String a(InputStream inputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        byte[] bArr;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    try {
                        bArr = new byte[1024];
                    } catch (Exception unused) {
                        t.b();
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused2) {
                                return null;
                            }
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        } catch (Exception unused3) {
            byteArrayOutputStream = null;
        } catch (Throwable th4) {
            byteArrayOutputStream = null;
            th = th4;
        }
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, i);
            th = th2;
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception unused4) {
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
        String str = new String(byteArrayOutputStream.toByteArray());
        try {
            byteArrayOutputStream.close();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception unused5) {
        }
        return str;
    }

    private static String a(String str) {
        try {
            if (!str.contains(":")) {
                return str;
            }
            return "[" + str + "]";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final String a(Context context, String str, HashMap<String, String> map, Object obj) {
        HttpsURLConnection httpsURLConnection;
        o oVar;
        String strReplaceFirst = str;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String host = "";
            String queryParameter = "seqAndroidEmpty";
            try {
                host = new URL(strReplaceFirst).getHost();
            } catch (MalformedURLException unused) {
                t.b();
            }
            if (host.contains(e.d()) && b && a) {
                if (!TextUtils.isEmpty(u.a)) {
                    strReplaceFirst = strReplaceFirst.replaceFirst(e.d(), a(u.a));
                }
                b = false;
            }
            String strReplaceFirst2 = strReplaceFirst;
            try {
                if (Build.VERSION.SDK_INT < 21) {
                    if (v.b(context) == 1) {
                        new r();
                        r.a(context, host);
                    }
                    SSLContext sSLContext = SSLContext.getInstance("TLS");
                    sSLContext.init(null, null, new SecureRandom());
                    oVar = new o(sSLContext.getSocketFactory());
                } else {
                    oVar = null;
                }
                URL url = new URL(strReplaceFirst2);
                httpsURLConnection = (HttpsURLConnection) ((obj == null || Build.VERSION.SDK_INT < 21) ? url.openConnection() : ((Network) obj).openConnection(url));
            } catch (Exception e) {
                e = e;
                httpsURLConnection = null;
            }
            try {
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(false);
                httpsURLConnection.setUseCaches(false);
                httpsURLConnection.setInstanceFollowRedirects(false);
                httpsURLConnection.setReadTimeout(com.igexin.push.config.c.d);
                httpsURLConnection.setConnectTimeout(com.igexin.push.config.c.d);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setHostnameVerifier(new a());
                p.a();
                httpsURLConnection.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                if (oVar != null) {
                    t.c("TAG\tsocketFactory!=null\n");
                    httpsURLConnection.setSSLSocketFactory(oVar);
                }
                if (map != null) {
                    for (String str2 : map.keySet()) {
                        httpsURLConnection.setRequestProperty(str2, map.get(str2));
                    }
                }
                httpsURLConnection.addRequestProperty("Connection", "close");
                t.c("TAG\thttpsURLConnection.connect();\n");
                httpsURLConnection.connect();
                t.c("connect cost:" + (System.currentTimeMillis() - jCurrentTimeMillis));
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                int responseCode = httpsURLConnection.getResponseCode();
                t.c("response cost:" + (System.currentTimeMillis() - jCurrentTimeMillis2));
                String string = httpsURLConnection.getURL().toString();
                if (string.contains("ret_url") && "seqAndroidEmpty".equalsIgnoreCase("seqAndroidEmpty")) {
                    queryParameter = Uri.parse(new String(Base64.decode(Uri.parse(string).getQueryParameter("ret_url"), 0))).getQueryParameter("seq");
                    t.c("seq = " + queryParameter + "\nstatusCode = " + responseCode);
                }
                if (responseCode == 200) {
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    String str3 = new String(a(inputStream));
                    httpsURLConnection.disconnect();
                    inputStream.close();
                    if (!TextUtils.isEmpty(str3)) {
                        JSONObject jSONObject = new JSONObject(str3);
                        if (TextUtils.isEmpty(jSONObject.optString("seq"))) {
                            jSONObject.put("seq", queryParameter);
                        }
                        return jSONObject.toString();
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", 410012);
                    jSONObject2.put("msg", "outputStr isEmpty");
                    jSONObject2.put("seq", queryParameter);
                    jSONObject2.put(com.alipay.sdk.packet.d.k, "requestUrl:".concat(String.valueOf(strReplaceFirst2)));
                    return jSONObject2.toString();
                }
                if (responseCode != 302) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("code", 410010);
                    jSONObject3.put("msg", "https statusCode NOK ".concat(String.valueOf(responseCode)));
                    jSONObject3.put(com.alipay.sdk.packet.d.k, "requestUrl:".concat(String.valueOf(strReplaceFirst2)));
                    return jSONObject3.toString();
                }
                String str4 = new String(httpsURLConnection.getHeaderField("Location"));
                t.c("statusCode == 302, redirectUrl is \n".concat(String.valueOf(str4)));
                t.c("System.currentTimeMillis() is  \n" + System.currentTimeMillis());
                httpsURLConnection.getInputStream().close();
                httpsURLConnection.disconnect();
                if (TextUtils.isEmpty(str4)) {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("code", 410013);
                    jSONObject4.put("msg", "无跳转地址");
                    jSONObject4.put(com.alipay.sdk.packet.d.k, host);
                    return jSONObject4.toString();
                }
                if (str4.startsWith(com.alipay.sdk.cons.b.a)) {
                    return a(context, str4, map, obj);
                }
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("code", 410013);
                jSONObject5.put("msg", "无法跳转HTTP地址");
                jSONObject5.put(com.alipay.sdk.packet.d.k, host);
                return jSONObject5.toString();
            } catch (Exception e2) {
                e = e2;
                try {
                    httpsURLConnection.getInputStream().close();
                    httpsURLConnection.disconnect();
                } catch (Exception unused2) {
                }
                int iE = v.e();
                t.c("\n■★■★■★■★■★■★■★■★■★■\n iRetry = >" + iE + " \n   e-->" + e + "\n ■★■★■★■★■★■★■★■★■★■\n");
                t.b();
                String message = e.getMessage();
                if (message == null || iE >= v.d()) {
                    try {
                        t.c("catch (Exception e) is  ".concat(String.valueOf(e)));
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put("code", 410011);
                        jSONObject6.put("msg", "https异常 : ".concat(String.valueOf(message)));
                        jSONObject6.put(com.alipay.sdk.packet.d.k, "requestUrl->".concat(String.valueOf(strReplaceFirst2)));
                        return jSONObject6.toString();
                    } catch (Exception unused3) {
                        return null;
                    }
                }
                int iF = v.f();
                if (message.contains("resolve host")) {
                    if (host.contains(e.d()) || host.contains(e.e())) {
                        t.c("resolve host error: retry->" + iF + " times \ne_getMessage=" + message);
                        if (b && a && !TextUtils.isEmpty(u.a)) {
                            strReplaceFirst2 = strReplaceFirst2.replaceFirst(e.d(), a(u.a));
                        }
                        return a(context, strReplaceFirst2, map, obj);
                    }
                }
                if (message.contains("Failed to connect")) {
                    t.c("Failed to connect error: retry->" + iF + " times \ne_getMessage=" + message);
                    return a(context, strReplaceFirst2, map, obj);
                }
                t.c("other  error: retry->" + iF + " times \ne_getMessage=" + message);
                return a(context, strReplaceFirst2, map, obj);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
