package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.nirvana.tools.core.ExecutorManager;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    protected volatile String a;
    protected volatile String b;
    protected volatile long c = com.igexin.push.config.c.s;
    protected Context d;
    protected d e;
    protected String f;
    protected String g;
    protected com.mobile.auth.o.a h;

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.a$a, reason: collision with other inner class name */
    public static class C0044a {
        private String a;
        private String b;
        private long c;
        private String d;
        private String e;

        /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.a$a$a, reason: collision with other inner class name */
        public static final class C0045a {
            private String a;
            private String b;
            private long c;
            private String d;
            private String e;

            private C0045a() {
            }

            static /* synthetic */ String a(C0045a c0045a) {
                try {
                    return c0045a.a;
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

            static /* synthetic */ String b(C0045a c0045a) {
                try {
                    return c0045a.b;
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

            static /* synthetic */ long c(C0045a c0045a) {
                try {
                    return c0045a.c;
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return -1L;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return -1L;
                    }
                }
            }

            static /* synthetic */ String d(C0045a c0045a) {
                try {
                    return c0045a.d;
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

            static /* synthetic */ String e(C0045a c0045a) {
                try {
                    return c0045a.e;
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

            public C0045a a(long j) {
                try {
                    this.c = j;
                    return this;
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

            public C0045a a(String str) {
                try {
                    this.a = str;
                    return this;
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

            public C0044a a() {
                try {
                    return new C0044a(this);
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

            public C0045a b(String str) {
                try {
                    this.b = str;
                    return this;
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

            public C0045a c(String str) {
                try {
                    this.d = str;
                    return this;
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

            public C0045a d(String str) {
                try {
                    this.e = str;
                    return this;
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

        private C0044a(C0045a c0045a) {
            this.a = C0045a.a(c0045a);
            this.b = C0045a.b(c0045a);
            this.c = C0045a.c(c0045a);
            this.d = C0045a.d(c0045a);
            this.e = C0045a.e(c0045a);
        }

        public static C0045a a() {
            try {
                return new C0045a();
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

        public String b() {
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

        public String c() {
            try {
                return this.b;
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

        public long d() {
            try {
                return this.c;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1L;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1L;
                }
            }
        }

        public String e() {
            try {
                return this.d;
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

        public String f() {
            try {
                return this.e;
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

    public static class b {
        String a;
        String b;

        /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.a$b$a, reason: collision with other inner class name */
        public static final class C0046a {
            private String a;
            private String b;

            private C0046a() {
            }

            static /* synthetic */ String a(C0046a c0046a) {
                try {
                    return c0046a.a;
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

            static /* synthetic */ String b(C0046a c0046a) {
                try {
                    return c0046a.b;
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

            public C0046a a(String str) {
                try {
                    this.a = str;
                    return this;
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

            public b a() {
                try {
                    return new b(this);
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

            public C0046a b(String str) {
                try {
                    this.b = str;
                    return this;
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

        private b(C0046a c0046a) {
            this.a = C0046a.a(c0046a);
            this.b = C0046a.b(c0046a);
        }

        public static C0046a a() {
            try {
                return new C0046a();
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

        public String b() {
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

        public String c() {
            try {
                return this.b;
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

    public a(Context context, d dVar, String str, String str2) {
        this.d = context;
        this.e = dVar;
        this.f = str;
        this.g = str2;
        this.h = dVar.a();
    }

    static /* synthetic */ void a(a aVar, String str, String str2, String str3) {
        try {
            aVar.a(str, str2, str3);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, String str2, String str3) {
        try {
            com.mobile.auth.gatewayauth.detectnet.f.a(str, str2, str3, this.d, this.g, this.f);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String a() {
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

    public void a(long j) {
        try {
            if (j >= com.igexin.push.config.c.s) {
                this.c = j;
            } else {
                this.c = com.igexin.push.config.c.s;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public final synchronized void a(final RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            d(new RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.1
                public void a(C0044a c0044a) {
                    try {
                        requestCallback.onSuccess(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a.a(a.this, bVar2.b(), bVar.a, bVar.b);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    protected void a(RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            a(str, str2, str3, false, str4, monitorStruct);
            requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a().d(com.mobile.auth.gatewayauth.utils.a.a(str, str2)).a(b(str, str5)).b(str2).c(str3).a());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str, String str2) {
        try {
            this.a = str;
            this.b = str2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    protected void a(String str, String str2, String str3, boolean z, String str4, final MonitorStruct monitorStruct) {
        if (monitorStruct != null) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                monitorStruct.setCarrierSdkCode(str);
                monitorStruct.setSuccess(z);
                monitorStruct.setEndTime(jCurrentTimeMillis);
                if (!z) {
                    monitorStruct.setCarrierSdkMsg(str2);
                    monitorStruct.setFailRet(com.mobile.auth.gatewayauth.utils.a.a(str, str2));
                    monitorStruct.setCarrierFailedResultData(str3);
                }
                monitorStruct.setUrgency(1);
                monitorStruct.setVendorKey(str4);
                if (monitorStruct.getAction().indexOf("logintoken") > -1 || monitorStruct.getAction().indexOf("getoken") > -1 || monitorStruct.getAction().indexOf("logincode") > -1) {
                    com.mobile.auth.gatewayauth.utils.c.g(this.d);
                }
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.a.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            monitorStruct.setPrivateIp(com.mobile.auth.gatewayauth.utils.c.h(a.this.d));
                            a.this.h.a(a.this.e.a(monitorStruct), monitorStruct.getUrgency());
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public abstract void a(boolean z);

    public String b() {
        try {
            return this.b;
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

    protected abstract String b(String str, String str2);

    public final synchronized void b(final RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            e(new RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.2
                public void a(C0044a c0044a) {
                    try {
                        requestCallback.onSuccess(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a.a(a.this, bVar2.b(), bVar.a, bVar.b);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    public abstract void c();

    public final synchronized void c(final RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, final b bVar) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            f(new RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.manager.a.3
                public void a(C0044a c0044a) {
                    try {
                        requestCallback.onSuccess(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                        a.a(a.this, bVar2.b(), bVar.a, bVar.b);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(com.mobile.auth.gatewayauth.manager.base.b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            }, bVar);
            return;
        }
        requestCallback.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO));
    }

    public abstract void d(RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);

    public abstract void e(RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);

    public abstract void f(RequestCallback<C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, b bVar);
}
