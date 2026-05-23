package com.mobile.auth.h;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class c implements b {
    private b a;
    private com.mobile.auth.k.c b;
    private final com.mobile.auth.g.a c = new com.mobile.auth.g.a();

    public void a(b bVar) {
        this.a = bVar;
    }

    @Override // com.mobile.auth.h.b
    public void a(com.mobile.auth.j.c cVar, com.mobile.auth.k.c cVar2, com.cmic.sso.sdk.a aVar) {
        b(cVar, cVar2, aVar);
    }

    public void b(final com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, final com.cmic.sso.sdk.a aVar) {
        if (this.a != null) {
            this.b = new com.mobile.auth.k.c() { // from class: com.mobile.auth.h.c.1
                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.a aVar2) {
                    if (!cVar.j()) {
                        cVar2.a(aVar2);
                        return;
                    }
                    com.mobile.auth.l.c.a("RetryAndRedirectInterceptor", "retry: " + cVar.a());
                    c.this.b(cVar, cVar2, aVar);
                }

                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.b bVar) {
                    com.mobile.auth.j.c cVarB;
                    if (bVar.d()) {
                        cVarB = c.this.c.a(cVar, bVar, aVar);
                    } else {
                        if (TextUtils.isEmpty(c.this.c.a())) {
                            cVar2.a(bVar);
                            return;
                        }
                        cVarB = c.this.c.b(cVar, bVar, aVar);
                    }
                    c.this.b(cVarB, cVar2, aVar);
                }
            };
            if (cVar.g()) {
                this.a.a(cVar, this.b, aVar);
            } else {
                cVar2.a(com.mobile.auth.k.a.a(200025));
            }
        }
    }
}
