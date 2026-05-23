package com.mobile.auth.gatewayauth.utils;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.nirvana.tools.core.ExecutorManager;
import java.lang.Thread;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public class g {

    public static abstract class a implements Runnable {
        private Thread.UncaughtExceptionHandler a;

        protected a() {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.gatewayauth.utils.g.a.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    try {
                        i.c("未知异常:" + ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }
            };
        }

        protected a(final TokenResultListener tokenResultListener) {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.gatewayauth.utils.g.a.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, final Throwable th) {
                    try {
                        g.a(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.utils.g.a.2.1
                            @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                            protected void onException(Throwable th2) {
                            }

                            @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                            protected void safeRun() {
                                try {
                                    if (tokenResultListener != null) {
                                        tokenResultListener.onTokenFailed(g.a(th));
                                    } else {
                                        i.a(th);
                                    }
                                } catch (Throwable th2) {
                                    try {
                                        ExceptionProcessor.processException(th2);
                                    } catch (Throwable th3) {
                                        ExceptionProcessor.processException(th3);
                                    }
                                }
                            }
                        });
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }
            };
        }

        protected abstract void a();

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.currentThread().setUncaughtExceptionHandler(this.a);
                a();
                Thread.currentThread().setUncaughtExceptionHandler(null);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public static String a(Throwable th) {
        try {
            TokenRet tokenRet = new TokenRet();
            tokenRet.setCode(com.mobile.auth.gatewayauth.utils.a.a ? ResultCode.CODE_ERROR_UNKNOWN_FAIL : Constant.CODE_ERROR_UNKNOWN_FAIL);
            tokenRet.setMsg("未知异常:" + ExecutorManager.getErrorInfoFromException(th));
            return tokenRet.toJsonString();
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    public static Future<?> a(Runnable runnable) {
        try {
            return ExecutorManager.getInstance().scheduleFuture(runnable);
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

    public static void a(ExecutorManager.SafeRunnable safeRunnable) {
        try {
            ExecutorManager.getInstance().postMain(safeRunnable);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
