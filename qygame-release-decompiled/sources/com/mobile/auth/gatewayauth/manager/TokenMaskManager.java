package com.mobile.auth.gatewayauth.manager;

import android.text.TextUtils;
import android.util.LruCache;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.Cache;
import com.mobile.auth.gatewayauth.manager.base.CacheKey;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.utils.TokenGenerator;
import com.mobile.auth.gatewayauth.utils.g;
import com.nirvana.tools.cache.CacheHandler;
import com.nirvana.tools.cache.CacheManager;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TokenMaskManager {
    private b a;
    private SystemManager b;
    private d c;
    private VendorSdkInfoManager d;
    private f e;
    private TokenGenerator f;
    private com.mobile.auth.o.a g;
    private volatile Map<String, LoginPhoneInfo> h = new ConcurrentHashMap();
    private volatile Cache<LoginPhoneInfo> i = null;
    private volatile LruCache<String, Cache<String>> j = new LruCache<>(10);
    private volatile LruCache<String, Cache<String>> k = new LruCache<>(10);
    private CacheHandler l;
    private CacheHandler m;
    private CacheHandler n;
    private CacheHandler o;
    private CacheManager p;

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$10, reason: invalid class name */
    class AnonymousClass10 implements RequestCallback<com.mobile.auth.u.d, com.mobile.auth.gatewayauth.manager.base.b> {
        final /* synthetic */ CacheKey a;
        final /* synthetic */ String b;
        final /* synthetic */ RequestCallback c;
        final /* synthetic */ String d;
        final /* synthetic */ CacheKey e;

        AnonymousClass10(CacheKey cacheKey, String str, RequestCallback requestCallback, String str2, CacheKey cacheKey2) {
            this.a = cacheKey;
            this.b = str;
            this.c = requestCallback;
            this.d = str2;
            this.e = cacheKey2;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            if (bVar == null) {
                try {
                    bVar = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return;
                    }
                }
            }
            this.c.onError(bVar);
        }

        public void a(com.mobile.auth.u.d dVar) {
            try {
                LoginPhoneInfo loginPhoneInfoBuild = LoginPhoneInfo.newLoginPhoneInfo().protocolName(dVar.c().e()).protocolUrl(dVar.c().f()).phoneNumber(dVar.c().b()).build();
                TokenMaskManager.a(TokenMaskManager.this, this.a, loginPhoneInfoBuild, this.b);
                this.c.onSuccess(com.mobile.auth.gatewayauth.manager.base.b.a().a(false).a(loginPhoneInfoBuild).a());
                dVar.b().a(Math.min(System.currentTimeMillis() + com.igexin.push.core.b.J, dVar.b().f()));
                if (TextUtils.isEmpty(dVar.b().d())) {
                    return;
                }
                TokenMaskManager.a(TokenMaskManager.this, this.d, this.b, this.e, dVar.b().d(), dVar.b().f());
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
        public /* synthetic */ void onSuccess(com.mobile.auth.u.d dVar) {
            try {
                a(dVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$11, reason: invalid class name */
    class AnonymousClass11 extends Callback<com.mobile.auth.u.d> {
        final /* synthetic */ RequestCallback a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass11(ThreadStrategy threadStrategy, long j, RequestCallback requestCallback) {
            super(threadStrategy, j);
            this.a = requestCallback;
        }

        public void a(com.mobile.auth.u.d dVar) {
            try {
                if (dVar.a()) {
                    this.a.onSuccess(dVar);
                    return;
                }
                com.mobile.auth.gatewayauth.manager.base.b bVarB = dVar.b();
                if (bVarB == null) {
                    bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                }
                this.a.onError(bVarB);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.d) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$12, reason: invalid class name */
    class AnonymousClass12 extends com.mobile.auth.p.d {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass12(f fVar, String str, long j, String str2, String str3, String str4) {
            super(fVar, str, j, str2);
            this.a = str3;
            this.b = str4;
        }

        @Override // com.mobile.auth.p.d
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.a((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.a).b(this.b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$13, reason: invalid class name */
    class AnonymousClass13 extends Callback<com.mobile.auth.u.d> {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ CacheKey c;
        final /* synthetic */ RequestCallback d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass13(ThreadStrategy threadStrategy, long j, String str, String str2, CacheKey cacheKey, RequestCallback requestCallback) {
            super(threadStrategy, j);
            this.a = str;
            this.b = str2;
            this.c = cacheKey;
            this.d = requestCallback;
        }

        public void a(com.mobile.auth.u.d dVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken from network!");
                if (dVar.a()) {
                    dVar.b().a(Math.min(System.currentTimeMillis() + com.igexin.push.core.b.J, dVar.b().f()));
                    TokenMaskManager.a(TokenMaskManager.this, this.a, this.b, this.c, dVar.b().d(), dVar.b().f());
                    this.d.onSuccess("false");
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = dVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.d.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.d) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$14, reason: invalid class name */
    class AnonymousClass14 extends com.mobile.auth.p.d {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass14(f fVar, String str, long j, String str2, String str3, String str4) {
            super(fVar, str, j, str2);
            this.a = str3;
            this.b = str4;
        }

        @Override // com.mobile.auth.p.d
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.b((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.a).b(this.b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$15, reason: invalid class name */
    class AnonymousClass15 extends Callback<com.mobile.auth.u.d> {
        final /* synthetic */ String a;
        final /* synthetic */ RequestCallback b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass15(ThreadStrategy threadStrategy, long j, String str, RequestCallback requestCallback) {
            super(threadStrategy, j);
            this.a = str;
            this.b = requestCallback;
        }

        public void a(com.mobile.auth.u.d dVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken from network!");
                if (dVar.a()) {
                    dVar.b().a(TokenMaskManager.a(TokenMaskManager.this, dVar.b().d(), this.a, true));
                    this.b.onSuccess(dVar.b());
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = dVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.b.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.d) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$16, reason: invalid class name */
    class AnonymousClass16 extends com.mobile.auth.p.d {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass16(f fVar, String str, long j, String str2, String str3, String str4) {
            super(fVar, str, j, str2);
            this.a = str3;
            this.b = str4;
        }

        @Override // com.mobile.auth.p.d
        public void a(RequestCallback requestCallback, a aVar) {
            try {
                aVar.b((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, a.b.a().a(this.a).b(this.b).a());
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$2, reason: invalid class name */
    class AnonymousClass2 extends Callback<com.mobile.auth.u.e> {
        final /* synthetic */ String a;
        final /* synthetic */ RequestCallback b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ThreadStrategy threadStrategy, long j, String str, RequestCallback requestCallback) {
            super(threadStrategy, j);
            this.a = str;
            this.b = requestCallback;
        }

        public void a(com.mobile.auth.u.e eVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update VerifyToken from network!");
                if (eVar.a()) {
                    eVar.b().a(TokenMaskManager.a(TokenMaskManager.this, eVar.b().d(), this.a, false));
                    this.b.onSuccess(eVar.b());
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = eVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.b.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.e) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$3, reason: invalid class name */
    class AnonymousClass3 extends Callback<com.mobile.auth.u.e> {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ CacheKey c;
        final /* synthetic */ RequestCallback d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(ThreadStrategy threadStrategy, long j, String str, String str2, CacheKey cacheKey, RequestCallback requestCallback) {
            super(threadStrategy, j);
            this.a = str;
            this.b = str2;
            this.c = cacheKey;
            this.d = requestCallback;
        }

        public void a(com.mobile.auth.u.e eVar) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update VerifyToken from network!");
                if (eVar.a()) {
                    eVar.b().a(Math.min(System.currentTimeMillis() + com.igexin.push.core.b.J, eVar.b().f()));
                    TokenMaskManager.b(TokenMaskManager.this, this.a, this.b, this.c, eVar.b().d(), eVar.b().f());
                    this.d.onSuccess("false");
                } else {
                    com.mobile.auth.gatewayauth.manager.base.b bVarB = eVar.b();
                    if (bVarB == null) {
                        bVarB = com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_UNKNOWN_FAIL, "未知异常");
                    }
                    this.d.onError(bVarB);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.nirvana.tools.requestqueue.Callback
        public /* synthetic */ void onResult(Response response) {
            try {
                a((com.mobile.auth.u.e) response);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$4, reason: invalid class name */
    class AnonymousClass4 extends JsonType<LoginPhoneInfo> {
        AnonymousClass4() {
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$5, reason: invalid class name */
    class AnonymousClass5 extends JsonType<String> {
        AnonymousClass5() {
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$6, reason: invalid class name */
    class AnonymousClass6 extends JsonType<String> {
        AnonymousClass6() {
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        AnonymousClass7() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TokenMaskManager.g(TokenMaskManager.this).save(TokenMaskManager.f(TokenMaskManager.this).encryptContent(TokenMaskManager.e(TokenMaskManager.this).toJson().toString()));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$8, reason: invalid class name */
    class AnonymousClass8 implements Runnable {
        final /* synthetic */ JSONObject a;

        AnonymousClass8(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TokenMaskManager.h(TokenMaskManager.this).save(TokenMaskManager.f(TokenMaskManager.this).encryptContent(this.a.toString()));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.TokenMaskManager$9, reason: invalid class name */
    class AnonymousClass9 implements RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> {
        AnonymousClass9() {
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                com.mobile.auth.o.a aVarD = TokenMaskManager.d(TokenMaskManager.this);
                String[] strArr = new String[2];
                strArr[0] = "Update LoginToken failed when update mask!";
                strArr[1] = bVar == null ? "" : bVar.toString();
                aVarD.e(strArr);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void a(String str) {
            try {
                TokenMaskManager.d(TokenMaskManager.this).a("Update LoginToken success when update mask!");
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
        public /* synthetic */ void onSuccess(String str) {
            try {
                a(str);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public TokenMaskManager(b bVar, SystemManager systemManager, d dVar, f fVar, VendorSdkInfoManager vendorSdkInfoManager) {
        this.a = bVar;
        this.b = systemManager;
        this.c = dVar;
        this.g = this.c.a();
        this.d = vendorSdkInfoManager;
        this.e = fVar;
        this.f = new TokenGenerator(this.g, this.b, this.d);
        this.p = CacheManager.getInstance(this.b.e());
        b();
        g.a(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.TokenMaskManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    TokenMaskManager.a(TokenMaskManager.this);
                    TokenMaskManager.b(TokenMaskManager.this);
                    TokenMaskManager.c(TokenMaskManager.this);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }
        });
    }

    static /* synthetic */ String a(TokenMaskManager tokenMaskManager, String str, String str2, boolean z) {
        try {
            return tokenMaskManager.a(str, str2, z);
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

    private native String a(String str, CacheKey cacheKey, long j);

    private native String a(String str, String str2, CacheKey cacheKey, long j);

    private native String a(String str, String str2, boolean z);

    static /* synthetic */ void a(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(TokenMaskManager tokenMaskManager, CacheKey cacheKey, LoginPhoneInfo loginPhoneInfo, String str) {
        try {
            tokenMaskManager.a(cacheKey, loginPhoneInfo, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(TokenMaskManager tokenMaskManager, String str, String str2, CacheKey cacheKey, String str3, long j) {
        try {
            tokenMaskManager.b(str, str2, cacheKey, str3, j);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private native synchronized void a(CacheKey cacheKey, LoginPhoneInfo loginPhoneInfo, String str);

    private native synchronized void a(String str, String str2, CacheKey cacheKey, String str3, long j);

    private native boolean a(String str, String str2, long j);

    private native synchronized boolean a(String str, String str2, LruCache<String, Cache<String>> lruCache, long j);

    private native void b();

    static /* synthetic */ void b(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void b(TokenMaskManager tokenMaskManager, String str, String str2, CacheKey cacheKey, String str3, long j) {
        try {
            tokenMaskManager.a(str, str2, cacheKey, str3, j);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private native synchronized void b(String str, String str2, CacheKey cacheKey, String str3, long j);

    private native boolean b(CacheKey cacheKey);

    private native boolean b(String str, String str2, long j);

    private native synchronized void c();

    static /* synthetic */ void c(TokenMaskManager tokenMaskManager) {
        try {
            tokenMaskManager.e();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a d(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.g;
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

    private native synchronized void d();

    static /* synthetic */ Cache e(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.i;
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

    private native synchronized void e();

    static /* synthetic */ SystemManager f(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.b;
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

    private native synchronized void f();

    static /* synthetic */ CacheHandler g(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.o;
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

    private native synchronized void g();

    static /* synthetic */ CacheHandler h(TokenMaskManager tokenMaskManager) {
        try {
            return tokenMaskManager.n;
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

    private native synchronized void h();

    @SafeProtector
    private native synchronized String popToken(String str, CacheKey cacheKey, CacheHandler cacheHandler, LruCache<String, Cache<String>> lruCache, long j);

    @SafeProtector
    private native void requestMask(long j, String str, RequestCallback<com.mobile.auth.u.d, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, int i, String str3, String str4);

    public native LoginPhoneInfo a(CacheKey cacheKey);

    public native synchronized void a();

    public native void a(long j, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, long j2, String str2, String str3, String str4);

    public native void a(long j, String str, RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, long j2, int i, String str3, String str4);

    public native void b(long j, String str, RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, long j2, String str2, String str3, String str4);

    public native void b(long j, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, String str2, long j2, int i, String str3, String str4);

    @SafeProtector
    public native void updateMask(long j, String str, RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, CacheKey cacheKey, int i, String str2, String str3, String str4);
}
