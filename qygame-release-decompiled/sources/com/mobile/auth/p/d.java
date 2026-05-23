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
public abstract class d implements TimeoutCallable<com.mobile.auth.u.d> {
    private f a;
    private String b;
    private long c;
    private String d;

    public d(f fVar, String str, long j, String str2) {
        this.a = fVar;
        this.b = str;
        this.c = j;
        this.d = str2;
    }

    public com.mobile.auth.u.d a() {
        try {
            com.mobile.auth.u.d dVar = new com.mobile.auth.u.d(true);
            dVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            return dVar;
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

    public abstract void a(RequestCallback requestCallback, com.mobile.auth.gatewayauth.manager.a aVar);

    public com.mobile.auth.u.d b() {
        try {
            com.mobile.auth.gatewayauth.utils.e.a().a(this.d, "doRequest", System.currentTimeMillis());
            com.mobile.auth.gatewayauth.manager.a aVarA = this.a.a(this.b);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final com.mobile.auth.u.d dVar = new com.mobile.auth.u.d(false);
            a(new RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.p.d.1
                public void a(a.C0044a c0044a) {
                    try {
                        dVar.a(true);
                        dVar.a(c0044a);
                        dVar.a(com.mobile.auth.gatewayauth.manager.base.b.a().c(c0044a.c()).a(c0044a.d()).a());
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
                        dVar.a(bVar);
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
            }, aVarA);
            try {
                long j = this.c;
                long j2 = com.igexin.push.config.c.s;
                if (j > com.igexin.push.config.c.s) {
                    j2 = this.c;
                }
                countDownLatch.await(j2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                dVar.a(com.mobile.auth.gatewayauth.manager.base.b.a(Constant.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e)));
            }
            return dVar;
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
    public /* synthetic */ com.mobile.auth.u.d onTimeout() {
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
