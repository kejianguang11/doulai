package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class l1 implements p0.a {
    public final int a;
    public final List<p0> b;
    public final s1 c;
    public final HttpURLConnection d;

    public l1(List<p0> list, HttpURLConnection httpURLConnection, int i, s1 s1Var) {
        this.b = list;
        this.d = httpURLConnection;
        this.a = i;
        this.c = s1Var;
    }

    public v1 a(s1 s1Var, HttpURLConnection httpURLConnection) throws IOException {
        if (this.a >= this.b.size()) {
            throw new AssertionError();
        }
        List<p0> list = this.b;
        int i = this.a;
        l1 l1Var = new l1(list, httpURLConnection, i + 1, s1Var);
        p0 p0Var = list.get(i);
        v1 v1VarA = p0Var.a(l1Var);
        if (v1VarA == null) {
            throw new NullPointerException("interceptor " + p0Var + " returned null");
        }
        if (v1VarA.e != null) {
            return v1VarA;
        }
        throw new IllegalStateException("interceptor " + p0Var + " returned a response with no body");
    }
}
