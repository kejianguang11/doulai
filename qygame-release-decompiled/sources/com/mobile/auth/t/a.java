package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.TimeoutCallable;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public class a extends Request<com.mobile.auth.u.c> {
    private static final String a = "com.mobile.auth.t.a";

    public a(Callback<com.mobile.auth.u.c> callback, TimeoutCallable<com.mobile.auth.u.c> timeoutCallable, long j, Class<com.mobile.auth.u.c> cls) {
        super(callback, timeoutCallable, ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.COVER, j, cls);
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return a;
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
