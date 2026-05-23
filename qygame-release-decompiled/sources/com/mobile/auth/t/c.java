package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class c extends Request<com.mobile.auth.u.a> {
    public c(Callback<com.mobile.auth.u.a> callback, com.mobile.auth.p.a aVar) {
        super(callback, aVar, ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.LIST, com.igexin.push.config.c.s, com.mobile.auth.u.a.class);
    }

    public static synchronized String a() {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return UUID.randomUUID().toString();
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return a();
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
