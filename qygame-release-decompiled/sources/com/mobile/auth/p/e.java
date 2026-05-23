package com.mobile.auth.p;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.f;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.requestqueue.TimeoutCallable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class e implements TimeoutCallable<com.mobile.auth.u.e> {
    private f a;
    private a.b b;
    private String c;
    private long d;

    public e(f fVar, String str, a.b bVar, long j) {
        this.a = fVar;
        this.b = bVar;
        this.c = str;
        this.d = j;
    }

    public com.mobile.auth.u.e a() {
        try {
            com.mobile.auth.u.e eVar = new com.mobile.auth.u.e(true);
            eVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            return eVar;
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

    public com.mobile.auth.u.e b() {
        try {
            com.mobile.auth.gatewayauth.utils.e.a().a(this.b.b(), "doRequest", System.currentTimeMillis());
            com.mobile.auth.gatewayauth.manager.a aVarA = this.a.a(this.c);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final com.mobile.auth.u.e eVar = new com.mobile.auth.u.e(false);
            aVarA.c(new RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.p.e.1
                public void a(a.C0044a c0044a) {
                    try {
                        eVar.a(true);
                        eVar.a(com.mobile.auth.gatewayauth.manager.base.b.a().c(c0044a.c()).a(c0044a.d()).a());
                        countDownLatch.countDown();
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
                        eVar.a(bVar);
                        countDownLatch.countDown();
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        a(bVar);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(a.C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, this.b);
            try {
                long j = this.d;
                long j2 = com.igexin.push.config.c.s;
                if (j > com.igexin.push.config.c.s) {
                    j2 = this.d;
                }
                countDownLatch.await(j2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                eVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(Constant.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e)));
            }
            return eVar;
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

    @Override // java.util.concurrent.Callable
    public /* synthetic */ Object call() throws Exception {
        try {
            return b();
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

    @Override // com.nirvana.tools.requestqueue.TimeoutCallable
    public /* synthetic */ com.mobile.auth.u.e onTimeout() {
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
