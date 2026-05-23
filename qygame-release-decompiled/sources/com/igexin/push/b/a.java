package com.igexin.push.b;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.c.a.b.e;
import com.igexin.c.a.d.f;
import com.igexin.push.core.d;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a extends f {
    public static int a = 0;
    public static final int b = -980948;
    public static final int h = -2147483639;
    private static final String i = "com.igexin.push.b.a";
    protected SQLiteDatabase c;
    protected Cursor d;
    List<com.igexin.push.core.e.a> e;
    List<com.igexin.push.core.e.a> f;
    boolean g;

    public a() {
        super(1);
        this.e = new LinkedList();
        this.f = new LinkedList();
    }

    private void b(boolean z) {
        this.g = z;
    }

    public final void a(com.igexin.push.core.e.a aVar) {
        this.e.add(aVar);
    }

    public final void b(com.igexin.push.core.e.a aVar) {
        this.f.add(aVar);
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void b_() throws Exception {
        super.b_();
        this.c = d.a.a.i.getWritableDatabase();
        this.c.setVersion(7);
        Iterator<com.igexin.push.core.e.a> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().a(this.c);
        }
        for (com.igexin.push.core.e.a aVar : this.e) {
            if (this.g) {
                aVar.c(this.c);
            } else {
                aVar.b(this.c);
            }
        }
        e.a().a((f) new d() { // from class: com.igexin.push.b.a.1
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                for (com.igexin.push.core.e.a aVar2 : a.this.f) {
                    aVar2.a(this.d);
                    if (a.this.g) {
                        aVar2.c(this.d);
                    } else {
                        aVar2.b(this.d);
                    }
                }
            }
        }, false, true);
        e.a().a(new c());
        e.a().b();
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return -2147483639;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d() {
        super.d();
        this.o = true;
        this.L = true;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d_() {
        super.d_();
        if (this.d != null) {
            try {
                this.d.close();
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
    }

    @Override // com.igexin.c.a.d.f
    public final void e() {
    }

    @Override // com.igexin.c.a.d.f
    public final void f() {
    }
}
