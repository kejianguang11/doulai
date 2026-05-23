package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import com.zx.a.I8b7.v1;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class k implements p0 {
    @Override // com.zx.a.I8b7.p0
    public v1 a(p0.a aVar) throws IOException {
        l1 l1Var = (l1) aVar;
        s1 s1Var = l1Var.c;
        HttpURLConnection httpURLConnection = l1Var.d;
        if (httpURLConnection.getDoOutput() && s1Var.d != null) {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            t1 t1Var = (t1) s1Var.d;
            outputStream.write(t1Var.c, t1Var.d, t1Var.b);
            f2.a(outputStream);
        }
        int responseCode = httpURLConnection.getResponseCode();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        z0 z0VarB = z0.b("text/json; charset=utf-8");
        if (httpURLConnection.getContentType() != null) {
            z0VarB = z0.b(httpURLConnection.getContentType());
        }
        String responseMessage = httpURLConnection.getResponseMessage();
        v1.a aVar2 = new v1.a();
        aVar2.b = responseCode;
        aVar2.d = new HashMap(headerFields);
        aVar2.c = responseMessage;
        aVar2.e = w1.a(z0VarB, httpURLConnection.getContentLength(), responseCode == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream());
        aVar2.a = s1Var;
        return aVar2.a();
    }
}
