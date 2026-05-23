package com.mobile.auth.n;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.PnsLoggerHandler;
import com.mobile.auth.gatewayauth.PnsReporter;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.manager.f;
import com.mobile.auth.gatewayauth.utils.i;
import com.nirvana.tools.logger.utils.ConsoleLogUtils;

/* JADX INFO: loaded from: classes.dex */
public class a implements PnsReporter {
    private com.mobile.auth.o.a a;
    private d b;
    private f c;

    public a(com.mobile.auth.o.a aVar, d dVar) {
        this.a = aVar;
        this.b = dVar;
    }

    private static boolean a() {
        try {
            return Class.forName("com.nirvana.tools.logger.utils.ConsoleLogUtils") != null;
        } catch (ClassNotFoundException unused) {
            return false;
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

    public void a(f fVar) {
        try {
            this.c = fVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLogExtension(String str) {
        try {
            this.b.c(str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLoggerEnable(boolean z) {
        try {
            System.currentTimeMillis();
            i.a(z);
            a();
            ConsoleLogUtils.setLoggerEnable(z);
            if (this.c != null) {
                for (com.mobile.auth.gatewayauth.manager.a aVar : this.c.a()) {
                    if (aVar != null) {
                        aVar.a(z);
                    }
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setLoggerHandler(PnsLoggerHandler pnsLoggerHandler) {
        try {
            this.a.a(pnsLoggerHandler);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.PnsReporter
    public void setUploadEnable(boolean z) {
        try {
            System.currentTimeMillis();
            i.b(z);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
