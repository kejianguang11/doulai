package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import com.zx.a.I8b7.s1;
import com.zx.a.I8b7.v1;
import com.zx.a.I8b7.w1;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class u0 implements p0 {
    public t0 a;

    public u0(t0 t0Var, int i) {
        this.a = t0Var;
    }

    @Override // com.zx.a.I8b7.p0
    public v1 a(p0.a aVar) throws IOException {
        l1 l1Var = (l1) aVar;
        s1 s1Var = l1Var.c;
        s1.a aVar2 = new s1.a(s1Var);
        StringBuilder sb = new StringBuilder();
        sb.append(s1Var.b + " " + s1Var.a.toString() + " " + s1Var.e + "\n");
        u1 u1Var = s1Var.d;
        if (u1Var != null && ((t1) u1Var).a.a() != null) {
            if (((t1) s1Var.d).b > 2147483647L) {
                StringBuilder sbA = j3.a("request body content length: ");
                sbA.append(((t1) s1Var.d).b);
                sbA.append("\n");
                sb.append(sbA.toString());
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                t1 t1Var = (t1) s1Var.d;
                byteArrayOutputStream.write(t1Var.c, t1Var.d, t1Var.b);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                sb.append(new String(byteArray, StandardCharsets.UTF_8) + "\n");
                aVar2.d = u1.a(((t1) s1Var.d).a, byteArray);
            }
        }
        this.a.a(sb.toString());
        v1 v1VarA = l1Var.a(new s1(aVar2), l1Var.d);
        v1.a aVar3 = new v1.a(v1VarA);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(v1VarA.b + " " + v1VarA.c + " " + s1Var.a.toString() + " " + s1Var.e + "\n");
        w1 w1Var = v1VarA.e;
        if (w1Var != null && ((w1.a) w1Var).a.a() != null) {
            w1 w1Var2 = v1VarA.e;
            if (((w1.a) w1Var2).b > 2147483647L) {
                StringBuilder sbA2 = j3.a("response body content length: ");
                sbA2.append(((w1.a) v1VarA.e).b);
                sbA2.append("\n");
                sb2.append(sbA2.toString());
            } else {
                byte[] bArrA = w1Var2.a();
                sb2.append("response body size: ");
                sb2.append(bArrA.length);
                sb2.append(", ");
                sb2.append(new String(bArrA, StandardCharsets.UTF_8) + "\n");
                aVar3.e = w1.a(((w1.a) v1VarA.e).a, (long) bArrA.length, new ByteArrayInputStream(bArrA));
            }
        }
        this.a.a(sb2.toString());
        return aVar3.a();
    }
}
