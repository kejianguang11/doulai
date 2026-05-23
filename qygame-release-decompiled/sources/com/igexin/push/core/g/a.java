package com.igexin.push.core.g;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<T> {
    public final a<T> a(final a<? super T> aVar) {
        return new a<T>() { // from class: com.igexin.push.core.g.a.1
            @Override // com.igexin.push.core.g.a
            public final void a(T t) {
                a.this.a(t);
                if (aVar != null) {
                    aVar.a(t);
                }
            }
        };
    }

    public abstract void a(T t);
}
