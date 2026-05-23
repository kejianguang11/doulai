package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.f;
import com.mobile.auth.u.e;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public class d extends Request<e> {
    private String a;

    public d(f fVar, Callback<e> callback, long j, String str, a.b bVar) {
        super(callback, new com.mobile.auth.p.e(fVar, str, bVar, j), ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.LIST, j, e.class);
        this.a = bVar.b();
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return this.a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
