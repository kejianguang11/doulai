package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public class b extends Request<com.mobile.auth.u.d> {
    private String a;

    public b(com.mobile.auth.p.d dVar, Callback<com.mobile.auth.u.d> callback, long j, String str) {
        super(callback, dVar, ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.LIST, j, com.mobile.auth.u.d.class);
        this.a = str;
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
