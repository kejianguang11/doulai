package com.mobile.auth.u;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.a;
import com.nirvana.tools.requestqueue.TimeoutResponse;

/* JADX INFO: loaded from: classes.dex */
public class d extends TimeoutResponse {
    private a.C0044a a;
    private boolean b;
    private com.mobile.auth.gatewayauth.manager.base.b c;

    public d(boolean z) {
        super(z);
    }

    public void a(a.C0044a c0044a) {
        try {
            this.a = c0044a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
        try {
            this.c = bVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(boolean z) {
        try {
            this.b = z;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            return this.b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public com.mobile.auth.gatewayauth.manager.base.b b() {
        try {
            return this.c;
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

    public a.C0044a c() {
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

    @Override // com.nirvana.tools.requestqueue.TimeoutResponse
    public boolean isResultTimeout() {
        return false;
    }
}
