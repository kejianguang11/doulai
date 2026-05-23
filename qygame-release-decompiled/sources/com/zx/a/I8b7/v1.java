package com.zx.a.I8b7;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class v1 implements Closeable {
    public final s1 a;
    public final int b;
    public final String c;
    public final Map<String, List<String>> d;
    public final w1 e;

    public static class a {
        public s1 a;
        public int b;
        public String c;
        public Map<String, List<String>> d;
        public w1 e;

        public a() {
            this.b = -1;
            this.d = new HashMap();
        }

        public a(v1 v1Var) {
            this.b = -1;
            this.a = v1Var.a;
            this.b = v1Var.b;
            this.c = v1Var.c;
            this.d = new HashMap(v1Var.d);
            this.e = v1Var.e;
        }

        public v1 a() {
            if (this.a == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.b >= 0) {
                if (this.c != null) {
                    return new v1(this);
                }
                throw new IllegalStateException("message == null");
            }
            StringBuilder sbA = j3.a("code < 0: ");
            sbA.append(this.b);
            throw new IllegalStateException(sbA.toString());
        }
    }

    public v1(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = new HashMap(aVar.d);
        this.e = aVar.e;
    }

    public String a(String str) {
        List<String> list = this.d.get(str);
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("; ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        w1 w1Var = this.e;
        if (w1Var == null) {
            throw new IllegalStateException("response is not eligible for a body and must not be closed");
        }
        w1Var.close();
    }
}
