package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import com.zx.a.I8b7.s1;
import com.zx.a.I8b7.v1;
import com.zx.a.I8b7.w1;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: classes.dex */
public class e implements p0 {
    @Override // com.zx.a.I8b7.p0
    public v1 a(p0.a aVar) throws IOException {
        w1 w1Var;
        Map<String, String> map;
        String str;
        l1 l1Var = (l1) aVar;
        s1 s1Var = l1Var.c;
        s1.a aVar2 = new s1.a(s1Var);
        HttpURLConnection httpURLConnection = (HttpURLConnection) s1Var.a.openConnection();
        u1 u1Var = s1Var.d;
        if (u1Var != null) {
            t1 t1Var = (t1) u1Var;
            z0 z0Var = t1Var.a;
            if (z0Var != null) {
                aVar2.c.put("Content-Type", z0Var.a);
            }
            long j = t1Var.b;
            if (j != -1) {
                aVar2.c.put("Content-Length", Long.toString(j));
                map = aVar2.c;
                str = "Transfer-Encoding";
            } else {
                aVar2.c.put("Transfer-Encoding", "chunked");
                map = aVar2.c;
                str = "Content-Length";
            }
            map.remove(str);
        }
        if (s1Var.c.get("Host") == null) {
            aVar2.c.put("Host", s1Var.a.getHost());
        }
        if (s1Var.c.get("Connection") == null) {
            aVar2.c.put("Connection", "Keep-Alive");
        }
        boolean z = false;
        if (s1Var.c.get("Accept-Encoding") == null && s1Var.c.get("Range") == null) {
            z = true;
            aVar2.c.put("Accept-Encoding", "gzip");
        }
        v1 v1VarA = l1Var.a(new s1(aVar2), httpURLConnection);
        v1.a aVar3 = new v1.a(v1VarA);
        aVar3.a = s1Var;
        if (z && "gzip".equalsIgnoreCase(v1VarA.a("Content-Encoding")) && (w1Var = v1VarA.e) != null) {
            aVar3.e = w1.a(((w1.a) w1Var).a, -1L, new GZIPInputStream(((w1.a) v1VarA.e).c));
            aVar3.d.remove("Content-Encoding");
            aVar3.d.remove("Content-Length");
        }
        return aVar3.a();
    }
}
