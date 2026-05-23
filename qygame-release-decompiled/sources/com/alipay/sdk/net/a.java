package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.text.TextUtils;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpParams;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "application/octet-stream;binary/octet-stream";
    public String b;
    private Context c;

    private a(Context context) {
        this(context, null);
    }

    public a(Context context, String str) {
        this.c = context != null ? context.getApplicationContext() : context;
        this.b = str;
    }

    private String a() {
        return this.b;
    }

    private void a(String str) {
        this.b = str;
    }

    private URL b() {
        try {
            return new URL(this.b);
        } catch (Exception unused) {
            return null;
        }
    }

    private HttpHost c() {
        URL urlB;
        if (Build.VERSION.SDK_INT < 11) {
            NetworkInfo networkInfoF = f();
            if (networkInfoF == null || !networkInfoF.isAvailable() || networkInfoF.getType() != 0) {
                return null;
            }
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null) {
                return new HttpHost(defaultHost, defaultPort);
            }
            return null;
        }
        String strG = g();
        if ((strG != null && !strG.contains("wap")) || (urlB = b()) == null) {
            return null;
        }
        com.alipay.sdk.cons.b.a.equalsIgnoreCase(urlB.getProtocol());
        String property = System.getProperty("https.proxyHost");
        String property2 = System.getProperty("https.proxyPort");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        return new HttpHost(property, Integer.parseInt(property2));
    }

    private HttpHost d() {
        NetworkInfo networkInfoF = f();
        if (networkInfoF != null && networkInfoF.isAvailable() && networkInfoF.getType() == 0) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost != null) {
                return new HttpHost(defaultHost, defaultPort);
            }
        }
        return null;
    }

    private HttpHost e() {
        URL urlB;
        String strG = g();
        if ((strG != null && !strG.contains("wap")) || (urlB = b()) == null) {
            return null;
        }
        com.alipay.sdk.cons.b.a.equalsIgnoreCase(urlB.getProtocol());
        String property = System.getProperty("https.proxyHost");
        String property2 = System.getProperty("https.proxyPort");
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        return new HttpHost(property, Integer.parseInt(property2));
    }

    private NetworkInfo f() {
        try {
            return ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    private String g() {
        try {
            NetworkInfo networkInfoF = f();
            return (networkInfoF == null || !networkInfoF.isAvailable()) ? com.igexin.push.a.i : networkInfoF.getType() == 1 ? "wifi" : networkInfoF.getExtraInfo().toLowerCase();
        } catch (Exception unused) {
            return com.igexin.push.a.i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final HttpResponse a(byte[] bArr, List<Header> list) throws Throwable {
        HttpHost httpHost;
        HttpUriRequest httpGet;
        URL urlB;
        new StringBuilder("requestUrl : ").append(this.b);
        b bVarA = b.a();
        if (bVarA == null) {
            return null;
        }
        try {
            HttpParams params = bVarA.c.getParams();
            if (Build.VERSION.SDK_INT >= 11) {
                String strG = g();
                if ((strG == null || strG.contains("wap")) && (urlB = b()) != null) {
                    com.alipay.sdk.cons.b.a.equalsIgnoreCase(urlB.getProtocol());
                    String property = System.getProperty("https.proxyHost");
                    httpHost = !TextUtils.isEmpty(property) ? new HttpHost(property, Integer.parseInt(System.getProperty("https.proxyPort"))) : null;
                }
            } else {
                NetworkInfo networkInfoF = f();
                if (networkInfoF != null && networkInfoF.isAvailable() && networkInfoF.getType() == 0) {
                    String defaultHost = Proxy.getDefaultHost();
                    int defaultPort = Proxy.getDefaultPort();
                    if (defaultHost != null) {
                        httpHost = new HttpHost(defaultHost, defaultPort);
                    }
                }
            }
            if (httpHost != null) {
                params.setParameter("http.route.default-proxy", httpHost);
            }
            if (bArr == null || bArr.length == 0) {
                httpGet = new HttpGet(this.b);
            } else {
                httpGet = new HttpPost(this.b);
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bArr);
                byteArrayEntity.setContentType(a);
                ((HttpPost) httpGet).setEntity(byteArrayEntity);
                httpGet.addHeader("Accept-Charset", com.alipay.sdk.sys.a.m);
                httpGet.addHeader("Connection", "Keep-Alive");
                httpGet.addHeader("Keep-Alive", "timeout=180, max=100");
            }
            if (list != null) {
                Iterator<Header> it = list.iterator();
                while (it.hasNext()) {
                    httpGet.addHeader(it.next());
                }
            }
            HttpResponse httpResponseA = bVarA.a(httpGet);
            Header[] headers = httpResponseA.getHeaders("X-Hostname");
            if (headers != null && headers.length > 0 && headers[0] != null) {
                httpResponseA.getHeaders("X-Hostname")[0].toString();
            }
            Header[] headers2 = httpResponseA.getHeaders("X-ExecuteTime");
            if (headers2 != null && headers2.length > 0 && headers2[0] != null) {
                httpResponseA.getHeaders("X-ExecuteTime")[0].toString();
            }
            return httpResponseA;
        } catch (Throwable th) {
            if (bVarA != null) {
                try {
                    ClientConnectionManager connectionManager = bVarA.c.getConnectionManager();
                    if (connectionManager != null) {
                        connectionManager.shutdown();
                        b.b = null;
                    }
                } catch (Throwable unused) {
                }
            }
            throw th;
        }
    }
}
