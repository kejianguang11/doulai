package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class q implements p0 {
    public final s2 a;

    public q(s2 s2Var) {
        this.a = s2Var;
    }

    @Override // com.zx.a.I8b7.p0
    public v1 a(p0.a aVar) throws IOException {
        l1 l1Var = (l1) aVar;
        s1 s1Var = l1Var.c;
        HttpURLConnection httpURLConnection = l1Var.d;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(s1Var.d != null);
        httpURLConnection.setConnectTimeout(this.a.f);
        httpURLConnection.setReadTimeout(this.a.g);
        httpURLConnection.setInstanceFollowRedirects(this.a.e);
        this.a.getClass();
        httpURLConnection.setUseCaches(false);
        if (com.alipay.sdk.cons.b.a.equalsIgnoreCase(s1Var.a.getProtocol())) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
            SSLSocketFactory sSLSocketFactory = this.a.c;
            if (sSLSocketFactory != null) {
                httpsURLConnection.setSSLSocketFactory(sSLSocketFactory);
            }
            HostnameVerifier hostnameVerifier = this.a.d;
            if (hostnameVerifier != null) {
                httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            }
        }
        Map<String, String> map = s1Var.c;
        if (map != null && map.size() > 0) {
            for (String str : map.keySet()) {
                httpURLConnection.setRequestProperty(str, map.get(str));
            }
        }
        httpURLConnection.connect();
        return l1Var.a(s1Var, httpURLConnection);
    }
}
