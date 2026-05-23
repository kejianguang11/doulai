package com.zx.a.I8b7;

import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public class s2 {
    public final z a;
    public final List<p0> b;
    public final SSLSocketFactory c;
    public final HostnameVerifier d;
    public final boolean e;
    public final int f;
    public final int g;

    public static final class a {
        public SSLSocketFactory c;
        public final List<p0> b = new ArrayList();
        public z a = new z();
        public boolean e = true;
        public int f = 7000;
        public int g = 7000;
        public HostnameVerifier d = r2.a;
    }

    public s2(a aVar) {
        this.a = aVar.a;
        List<p0> listA = f2.a(aVar.b);
        this.b = listA;
        this.c = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
        this.g = aVar.g;
        if (listA.contains(null)) {
            throw new IllegalStateException("Null interceptor: " + listA);
        }
    }
}
