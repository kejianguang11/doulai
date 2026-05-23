package com.igexin.c.a.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {
    protected String c;
    protected d d;
    protected d e;
    protected boolean f;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {a, b, c};

        private a(String str, int i) {
        }

        private static int[] a() {
            return (int[]) d.clone();
        }
    }

    private d(String str) {
        this.c = str;
    }

    public d(String str, byte b) {
        this.c = str;
        this.f = true;
    }

    private static int a() {
        return a.c;
    }

    private static void a(d dVar, String str, String str2, d dVar2) {
        if (str2 == null) {
            throw new NullPointerException("filter name can't be NULL");
        }
        if (dVar != null) {
            d dVar3 = dVar.e;
            if (dVar.c.equals(str)) {
                dVar2.d = dVar;
                dVar.e = dVar2;
                dVar2.e = dVar3;
                if (dVar3 != null) {
                    dVar3.d = dVar2;
                }
            } else {
                while (dVar3.e != null && !dVar3.c.equals(str)) {
                    dVar3 = dVar3.e;
                }
                if (dVar3.e == null) {
                    dVar3.e = dVar2;
                    dVar2.d = dVar3;
                } else {
                    dVar2.e = dVar3.e;
                    dVar3.e.d = dVar2;
                    dVar2.d = dVar3;
                    dVar3.e = dVar2;
                }
            }
        }
        dVar2.c = str2;
    }

    private static void b(d dVar, String str, String str2, d dVar2) {
        if (str2 == null) {
            throw new NullPointerException("filter name can't be NULL");
        }
        if (dVar != null) {
            d dVar3 = dVar.d;
            if (dVar.c.equals(str)) {
                dVar.d = dVar2;
                dVar2.e = dVar;
                dVar2.d = dVar3;
                dVar3.e = dVar2;
            } else {
                while (dVar3.d != null && !dVar3.c.equals(str)) {
                    dVar3 = dVar3.d;
                }
                if (dVar3.d == null) {
                    dVar3.d = dVar2;
                    dVar2.e = dVar3;
                } else {
                    dVar3.d.e = dVar2;
                    dVar2.d = dVar3.d;
                    dVar2.e = dVar3;
                    dVar3.d = dVar2;
                }
            }
        }
        dVar2.c = str2;
    }

    private static int c() {
        return a.c;
    }

    private String d() {
        return this.c;
    }

    public final Object a(f fVar, Object obj) throws Exception {
        if (obj == null) {
            throw new NullPointerException("Nothing to encode!");
        }
        if (this.d != null) {
            obj = this.d.a(fVar, obj);
        }
        return a(obj);
    }

    public abstract Object a(Object obj) throws Exception;

    protected final void a(d dVar) {
        if (dVar == null) {
            return;
        }
        d dVar2 = dVar.d;
        dVar.d = this;
        this.e = dVar;
        this.d = dVar2;
    }

    public final com.igexin.c.a.d.a.e b(f fVar, Object obj) throws Exception {
        Object objB = b(obj);
        if (this.d != null && objB != null) {
            objB = this.d.b(fVar, objB);
        }
        return (com.igexin.c.a.d.a.e) objB;
    }

    public abstract Object b(Object obj) throws Exception;

    public final void b() {
        if (this.f) {
            return;
        }
        while (this.d != null) {
            d dVar = this.d.d;
            this.d.d = null;
            this.d = dVar;
        }
    }
}
