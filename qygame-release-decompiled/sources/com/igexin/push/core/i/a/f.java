package com.igexin.push.core.i.a;

/* JADX INFO: loaded from: classes.dex */
public final class f extends b<e> implements l {
    public f(e eVar) {
        super(eVar);
    }

    @Override // com.igexin.push.core.i.a.b, com.igexin.push.core.i.a.l
    public final void b() {
        ((e) this.a).a().prepareToDraw();
    }

    @Override // com.igexin.push.core.i.a.m
    public final Class<e> d() {
        return e.class;
    }

    @Override // com.igexin.push.core.i.a.m
    public final int e() {
        h hVar = ((e) this.a).c.a;
        return hVar.a.m() + hVar.j;
    }

    @Override // com.igexin.push.core.i.a.m
    public final void f() {
        ((e) this.a).stop();
        e eVar = (e) this.a;
        eVar.e = true;
        h hVar = eVar.c.a;
        hVar.b.clear();
        hVar.b();
        hVar.c = false;
        if (hVar.e != null) {
            hVar.e = null;
        }
        if (hVar.g != null) {
            hVar.g = null;
        }
        if (hVar.i != null) {
            hVar.i = null;
        }
        hVar.a.o();
        hVar.f = true;
    }
}
