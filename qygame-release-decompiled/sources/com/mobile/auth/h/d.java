package com.mobile.auth.h;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import com.mobile.auth.l.n;
import com.mobile.auth.l.r;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class d implements b {
    private b a;

    public void a(b bVar) {
        this.a = bVar;
    }

    @Override // com.mobile.auth.h.b
    public void a(final com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, final com.cmic.sso.sdk.a aVar) {
        if (!cVar.b()) {
            b(cVar, cVar2, aVar);
            return;
        }
        r rVarA = r.a((Context) null);
        if (Build.VERSION.SDK_INT >= 21) {
            rVarA.a(new r.a() { // from class: com.mobile.auth.h.d.1
                private final AtomicBoolean e = new AtomicBoolean(false);

                @Override // com.mobile.auth.l.r.a
                public void a(final Network network) {
                    if (this.e.getAndSet(true)) {
                        return;
                    }
                    n.a(new n.a(null, aVar) { // from class: com.mobile.auth.h.d.1.1
                        @Override // com.mobile.auth.l.n.a
                        protected void a() {
                            if (network == null) {
                                cVar2.a(com.mobile.auth.k.a.a(102508));
                            } else {
                                com.mobile.auth.l.c.b("WifiChangeInterceptor", "onAvailable");
                                cVar.a(network);
                                d.this.b(cVar, cVar2, aVar);
                            }
                        }
                    });
                }
            });
        } else {
            com.mobile.auth.l.c.a("WifiChangeInterceptor", "低版本不在支持wifi切换");
            cVar2.a(com.mobile.auth.k.a.a(102508));
        }
    }

    public void b(com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, com.cmic.sso.sdk.a aVar) {
        if (this.a != null) {
            this.a.a(cVar, new com.mobile.auth.k.c() { // from class: com.mobile.auth.h.d.2
                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.a aVar2) {
                    cVar2.a(aVar2);
                }

                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.b bVar) {
                    cVar2.a(bVar);
                }
            }, aVar);
        }
    }
}
