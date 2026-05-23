package com.zx.a.I8b7;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class s1 {
    public URL a;
    public String b;
    public Map<String, String> c;
    public u1 d;
    public String e;

    public static class a {
        public URL a;
        public String b;
        public Map<String, String> c;
        public u1 d;
        public String e;

        public a() {
            this.b = "GET";
            this.c = new HashMap();
            this.e = "";
        }

        public a(s1 s1Var) {
            this.a = s1Var.a;
            this.b = s1Var.b;
            this.d = s1Var.d;
            this.c = s1Var.c;
            this.e = s1Var.e;
        }

        public a a(String str) {
            if (str == null) {
                throw new NullPointerException("url == null");
            }
            try {
                this.a = new URL(str);
                return this;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public s1(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        HashMap map = new HashMap();
        this.c = map;
        map.putAll(aVar.c);
        this.d = aVar.d;
        this.e = aVar.e;
    }
}
