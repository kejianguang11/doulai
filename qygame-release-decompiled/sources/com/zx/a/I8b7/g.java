package com.zx.a.I8b7;

import android.net.Network;
import android.util.Base64;
import com.zx.a.I8b7.q2;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class g implements Runnable {

    public class a implements q2.b {
        public a(g gVar) {
        }

        @Override // com.zx.a.I8b7.q2.b
        public void a() {
        }

        @Override // com.zx.a.I8b7.q2.b
        public void a(int i, String str) {
        }

        @Override // com.zx.a.I8b7.q2.b
        public void a(Network network) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL("https://zxid-m.mobileservice.cn/sdk/ext/pconfig"));
                k0.a(httpURLConnection);
                byte[] bArr = new byte[16];
                new SecureRandom().nextBytes(bArr);
                r.b(bArr, q3.a(q3.h));
                HashMap<String, String> mapB = k0.b(new String(Base64.encode(bArr, 2), StandardCharsets.UTF_8));
                httpURLConnection.setRequestMethod("POST");
                for (String str : mapB.keySet()) {
                    httpURLConnection.setRequestProperty(str, mapB.get(str));
                }
                httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
                httpURLConnection.setRequestProperty("Charset", com.alipay.sdk.sys.a.m);
                httpURLConnection.connect();
                JSONObject jSONObject = new JSONObject();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), com.alipay.sdk.sys.a.m));
                bufferedWriter.write(jSONObject.toString());
                bufferedWriter.close();
                v2.a(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b());
                httpURLConnection.disconnect();
            } catch (Throwable th) {
                v2.a(th.getMessage());
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            q2.c.a.a(new a(this));
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
