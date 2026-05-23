package com.zx.a.I8b7;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class k1 {
    public s1 a;
    public s2 b;
    public boolean c;

    public final class a implements Runnable {
        /* JADX WARN: Code restructure failed: missing block: B:12:?, code lost:
        
            throw null;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            try {
                throw null;
            } catch (Throwable th) {
                new Exception(th.getMessage(), th.getCause());
                throw null;
            }
        }
    }

    public k1(s2 s2Var, s1 s1Var) {
        this.b = s2Var;
        this.a = s1Var;
    }

    public v1 a() throws Exception {
        synchronized (this) {
            if (this.c) {
                throw new IllegalStateException("Already Executed");
            }
            this.c = true;
        }
        try {
            z zVar = this.b.a;
            synchronized (zVar) {
                zVar.d.add(this);
            }
            return b();
        } finally {
            z zVar2 = this.b.a;
            zVar2.a(zVar2.d, this, false);
        }
    }

    public v1 b() throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.b.b);
        this.a.getClass();
        arrayList.add(new e());
        arrayList.add(new q(this.b));
        arrayList.add(new k());
        s1 s1Var = this.a;
        if (arrayList.size() <= 0) {
            throw new AssertionError();
        }
        l1 l1Var = new l1(arrayList, null, 1, s1Var);
        p0 p0Var = (p0) arrayList.get(0);
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
