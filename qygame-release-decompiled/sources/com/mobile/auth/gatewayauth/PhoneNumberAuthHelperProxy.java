package com.mobile.auth.gatewayauth;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.TokenMaskManager;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;
import com.mobile.auth.gatewayauth.manager.base.CacheKey;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.manager.f;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.network.PrivateKeyRespone;
import com.mobile.auth.gatewayauth.network.PrivateRespone;
import com.mobile.auth.gatewayauth.network.RequestState;
import com.mobile.auth.gatewayauth.network.RequestUtil;
import com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper;
import com.mobile.auth.gatewayauth.utils.EncryptUtils;
import com.mobile.auth.gatewayauth.utils.g;
import com.nirvana.tools.core.ComponentSdkCore;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.logger.UaidTracker;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class PhoneNumberAuthHelperProxy {

    @AuthNumber
    public static final int SERVICE_TYPE_AUTH = 1;

    @AuthNumber
    public static final int SERVICE_TYPE_LOGIN = 2;
    protected static volatile PhoneNumberAuthHelperProxy a;
    private TokenResultListener b;
    private SystemManager c;
    private com.mobile.auth.gatewayauth.manager.b d;
    private VendorSdkInfoManager e;
    private TokenMaskManager f;
    private com.mobile.auth.gatewayauth.manager.d g;
    private f h;
    private Future<?> i;
    private com.mobile.auth.o.a j;
    private String k;
    private volatile String m;
    private final ResultCodeProcessor l = new com.mobile.auth.gatewayauth.manager.compat.a();
    private Handler n = new Handler(Looper.getMainLooper()) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.12
        @Override // android.os.Handler
        public void dispatchMessage(@NonNull Message message) {
            try {
                super.dispatchMessage(message);
                if (message.what == 0) {
                    PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).a("", new RequestCallback<Void, String>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.12.1
                        public void a(String str) {
                            try {
                                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("GetVendorList failed!", str);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }

                        public void a(Void r1) {
                            try {
                                PhoneNumberAuthHelperProxy.c(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this));
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }

                        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                        public /* synthetic */ void onError(String str) {
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

                        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                        public /* synthetic */ void onSuccess(Void r1) {
                            try {
                                a(r1);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    }, com.mobile.auth.gatewayauth.manager.e.a(PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).e(), PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this), PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this), PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this)));
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    };

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$19, reason: invalid class name */
    class AnonymousClass19 implements Runnable {
        final /* synthetic */ RequestCallback a;

        AnonymousClass19(RequestCallback requestCallback) {
            this.a = requestCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.a.onError(com.mobile.auth.gatewayauth.manager.base.b.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"));
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$20, reason: invalid class name */
    class AnonymousClass20 implements RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> {
        final /* synthetic */ c a;
        final /* synthetic */ MonitorStruct b;
        final /* synthetic */ ResultCodeProcessor c;
        final /* synthetic */ RequestCallback d;

        AnonymousClass20(c cVar, MonitorStruct monitorStruct, ResultCodeProcessor resultCodeProcessor, RequestCallback requestCallback) {
            this.a = cVar;
            this.b = monitorStruct;
            this.c = resultCodeProcessor;
            this.d = requestCallback;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.a.d()) {
                    this.b.setCache(String.valueOf(bVar.e()));
                    LoginPhoneInfo loginPhoneInfoH = bVar.h();
                    this.b.setPhoneNumber(loginPhoneInfoH.getPhoneNumber());
                    PhoneNumberAuthHelperProxy.this.a(loginPhoneInfoH.getPhoneNumber());
                    this.b.setAuthSdkCode(this.c.convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                    this.d.onSuccess(loginPhoneInfoH);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.a.d()) {
                    this.b.setCache("false");
                    this.b.setCarrierFailedResultData(bVar.d());
                    PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("justGetLoginPhone failed!", bVar.i());
                    this.d.onError(bVar);
                }
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
                b(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ TokenResultListener b;
        final /* synthetic */ ResultCodeProcessor c;
        final /* synthetic */ MonitorStruct d;
        final /* synthetic */ String e;

        AnonymousClass7(String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2) {
            this.a = str;
            this.b = tokenResultListener;
            this.c = resultCodeProcessor;
            this.d = monitorStruct;
            this.e = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), this.a, this.b, this.c, this.d, this.e);
                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("justGetLoginToken Timeout!");
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy$8, reason: invalid class name */
    class AnonymousClass8 implements RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b> {
        final /* synthetic */ c a;
        final /* synthetic */ MonitorStruct b;
        final /* synthetic */ String c;
        final /* synthetic */ TokenResultListener d;
        final /* synthetic */ ResultCodeProcessor e;
        final /* synthetic */ String f;

        AnonymousClass8(c cVar, MonitorStruct monitorStruct, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2) {
            this.a = cVar;
            this.b = monitorStruct;
            this.c = str;
            this.d = tokenResultListener;
            this.e = resultCodeProcessor;
            this.f = str2;
        }

        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.a.d()) {
                    this.b.setCache(String.valueOf(bVar.e()));
                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, this.c, this.d, this.e, bVar.d(), this.b, this.f);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
            try {
                if (this.a.d()) {
                    this.b.setCache("false");
                    this.b.setCarrierFailedResultData(bVar.d());
                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), this.c, this.d, this.e, this.b, this.f);
                }
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
                b(bVar);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
        public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        a = null;
    }

    private PhoneNumberAuthHelperProxy(Context context, TokenResultListener tokenResultListener) {
        this.b = tokenResultListener;
        ComponentSdkCore.register(context.getApplicationContext());
        a(context.getApplicationContext());
    }

    static /* synthetic */ SystemManager a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.c;
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

    private void a(Context context) {
        try {
            this.g = new com.mobile.auth.gatewayauth.manager.d(context);
            this.j = this.g.a();
            this.c = new SystemManager(context, this.j);
            this.e = new VendorSdkInfoManager(this.g, this.c);
            this.d = new com.mobile.auth.gatewayauth.manager.b(context, this.e, this.g);
            this.h = new f(this.c, this.g);
            this.g.a(this.h);
            this.f = new TokenMaskManager(this.d, this.c, this.g, this.h, this.e);
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).getSimCacheKey(false, null);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            if (c(this.c.e())) {
                this.i = e();
            }
            d();
            UaidTracker.getInstance().setKey(EncryptUtils.getSecret5().substring(4, 10) + EncryptUtils.getSecret6().substring(1, 7), EncryptUtils.getSecret5().substring(15) + EncryptUtils.getSecret6().substring(38, 54), EncryptUtils.getSecret6().substring(70, 76) + EncryptUtils.getSecret6().substring(86, 92));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(Context context, boolean z) {
        try {
            com.mobile.auth.v.e privateKey = RequestUtil.getPrivateKey(context, this.e.c(), this.g.j());
            if (!privateKey.b() || TextUtils.isEmpty(privateKey.a())) {
                return;
            }
            try {
                PrivateRespone privateRespone = (PrivateRespone) JSONUtils.fromJson(new JSONObject(privateKey.a()), (JsonType) new JsonType<PrivateRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.33
                }, (List<Field>) null);
                if ("OK".equals(privateRespone.getCode())) {
                    PrivateKeyRespone data = privateRespone.getData();
                    if (data.getExpiredTime() <= System.currentTimeMillis()) {
                        return;
                    }
                    UTSharedPreferencesHelper.saveAuthSDKPrivateKey(context, com.mobile.auth.gatewayauth.utils.security.a.a(data.toJson().toString().getBytes()));
                    RequestState.getInstance().setKeyRespone(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, int i, ResultCodeProcessor resultCodeProcessor, boolean z, TokenResultListener tokenResultListener) {
        try {
            phoneNumberAuthHelperProxy.justGetToken(i, resultCodeProcessor, z, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j, PreLoginResultListener preLoginResultListener) {
        try {
            phoneNumberAuthHelperProxy.justPreVerify(j, preLoginResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j, PreLoginResultListener preLoginResultListener, ResultCodeProcessor resultCodeProcessor, boolean z, boolean z2) {
        try {
            phoneNumberAuthHelperProxy.justPreLogin(j, preLoginResultListener, resultCodeProcessor, z, z2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, long j, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor) {
        try {
            phoneNumberAuthHelperProxy.justGetLoginToken(j, tokenResultListener, resultCodeProcessor);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, Context context, boolean z) {
        try {
            phoneNumberAuthHelperProxy.a(context, z);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            phoneNumberAuthHelperProxy.a(str, str2, str3, str4, tokenResultListener, resultCodeProcessor, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z, boolean z2, String str, String str2, MonitorStruct monitorStruct, TokenResultListener tokenResultListener) {
        try {
            phoneNumberAuthHelperProxy.a(z, z2, str, str2, monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            this.g.h();
            if (monitorStruct.getAction().equals(this.c.c(str4))) {
                a(false, false, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            } else {
                a(false, true, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(final String str, final boolean z, final MonitorStruct monitorStruct, final boolean z2) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.26
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (monitorStruct != null) {
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            monitorStruct.setSuccess(z);
                            monitorStruct.setEndTime(jCurrentTimeMillis);
                            if (!z) {
                                monitorStruct.setFailRet(str);
                            }
                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).a(monitorStruct), monitorStruct.getUrgency());
                        }
                        if (z2) {
                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).b();
                        }
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

    private void a(final boolean z, final String str, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.25
                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                protected void onException(Throwable th) {
                    try {
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("TokenResultListener callback exception!", ExecutorManager.getErrorInfoFromException(th));
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }

                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                protected void safeRun() {
                    try {
                        if (z) {
                            tokenResultListener.onTokenSuccess(str);
                        } else {
                            tokenResultListener.onTokenFailed(str);
                        }
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

    private void a(boolean z, boolean z2, String str, String str2, MonitorStruct monitorStruct, TokenResultListener tokenResultListener) {
        if (tokenResultListener != null) {
            try {
                a(z, str2, tokenResultListener);
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
        if (monitorStruct != null && !Constant.ACTION_SDK_LIFE_BODY_VERIFY.equals(monitorStruct.getAction())) {
            a(str, z, monitorStruct, z2);
        }
    }

    static /* synthetic */ boolean a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2, MonitorStruct monitorStruct, String str3) {
        try {
            return phoneNumberAuthHelperProxy.a(z, str, tokenResultListener, resultCodeProcessor, str2, monitorStruct, str3);
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

    static /* synthetic */ boolean a(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, boolean z, String str, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2, TokenResultListener tokenResultListener, String str3) {
        try {
            return phoneNumberAuthHelperProxy.a(z, str, resultCodeProcessor, monitorStruct, str2, tokenResultListener, str3);
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

    private boolean a(final String str, String str2, final String str3) {
        try {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            boolean z = (str3 == null || str3.equals(str2)) ? false : true;
            final long jCurrentTimeMillis2 = System.currentTimeMillis();
            final boolean z2 = z;
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.27
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).a(str3, Constant.ACTION_SDK_CROSS_CARRIER_CHANGE, UStruct.newUStruct().isCarrierChanged(String.valueOf(z2)).requestId(PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).e()).sessionId(str).startTime(jCurrentTimeMillis).endTime(jCurrentTimeMillis2).build(), ""), 2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            return z;
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

    private boolean a(boolean z, String str, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str2, MonitorStruct monitorStruct, String str3) {
        try {
            this.g.h();
            if (!TextUtils.isEmpty(str2)) {
                a(str2, str, monitorStruct, resultCodeProcessor, tokenResultListener);
                return true;
            }
            this.j.e("GetLoginToken from cache is null!");
            if (z) {
                monitorStruct.setAuthSdkCode(Constant.CODE_ERROR_UNKNOWN_FAIL);
                a(false, true, Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常"), str, monitorStruct, tokenResultListener, resultCodeProcessor, str3);
            }
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

    private boolean a(boolean z, String str, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str2, TokenResultListener tokenResultListener, String str3) {
        try {
            this.g.i();
            if (!TextUtils.isEmpty(str2)) {
                a(str2, str, monitorStruct, resultCodeProcessor, tokenResultListener);
                return true;
            }
            this.j.e("GetVerifyToken from cache is null!");
            if (z) {
                a(false, true, Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "未知异常"), str, monitorStruct, tokenResultListener, resultCodeProcessor, str3);
            }
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

    static /* synthetic */ VendorSdkInfoManager b(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.e;
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

    static /* synthetic */ void b(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy, String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            phoneNumberAuthHelperProxy.b(str, str2, str3, str4, tokenResultListener, resultCodeProcessor, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void b(String str, String str2, String str3, String str4, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, MonitorStruct monitorStruct, String str5) {
        try {
            this.g.i();
            if (monitorStruct.getAction().equals(this.c.d(str4))) {
                a(false, false, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            } else {
                a(false, true, str, str2, str3, str4, monitorStruct, tokenResultListener, resultCodeProcessor, str5);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private boolean b(final Context context) {
        try {
            String authSDKPrivateKey = UTSharedPreferencesHelper.readAuthSDKPrivateKey(context);
            if (TextUtils.isEmpty(authSDKPrivateKey)) {
                this.j.a("local pritekey is empty");
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.23
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context, true);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                return false;
            }
            try {
                PrivateKeyRespone privateKeyRespone = (PrivateKeyRespone) JSONUtils.fromJson(new JSONObject(new String(com.mobile.auth.gatewayauth.utils.security.a.a(authSDKPrivateKey))), (JsonType) new JsonType<PrivateKeyRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.28
                }, (List<Field>) null);
                if (privateKeyRespone == null) {
                    this.j.a("parse local privatekey is empty");
                    RequestState.getInstance().setUseRequest(true);
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.29
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context, true);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    });
                    return false;
                }
                RequestState.getInstance().setKeyRespone(privateKeyRespone);
                if (RequestState.getInstance().checkTokenValied(30)) {
                    return true;
                }
                this.j.a("local privatekey expired not enough");
                long expiredTime = privateKeyRespone.getExpiredTime();
                long jCurrentTimeMillis = System.currentTimeMillis();
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.30
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context, true);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                if (expiredTime - jCurrentTimeMillis > 0) {
                    return true;
                }
                this.j.a("local privatekey has expired");
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                this.j.a("parse local privatekey error");
                RequestState.getInstance().setUseRequest(true);
                ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.31
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, context, true);
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                });
                return false;
            }
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

    static /* synthetic */ f c(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.h;
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

    private boolean c(Context context) {
        try {
            String authSDKPrivateKey = UTSharedPreferencesHelper.readAuthSDKPrivateKey(context);
            if (!TextUtils.isEmpty(authSDKPrivateKey)) {
                try {
                    PrivateKeyRespone privateKeyRespone = (PrivateKeyRespone) JSONUtils.fromJson(new JSONObject(new String(com.mobile.auth.gatewayauth.utils.security.a.a(authSDKPrivateKey))), (JsonType) new JsonType<PrivateKeyRespone>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.32
                    }, (List<Field>) null);
                    if (privateKeyRespone == null) {
                        return false;
                    }
                    RequestState.getInstance().setKeyRespone(privateKeyRespone);
                    return RequestState.getInstance().checkTokenValied(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.j.a("parse local privatekey error");
                }
            }
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

    static /* synthetic */ com.mobile.auth.o.a d(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.j;
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

    private static boolean d() {
        try {
            return Class.forName("com.nirvana.tools.logger.UaidTracker") != null;
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

    static /* synthetic */ com.mobile.auth.gatewayauth.manager.b e(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.d;
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

    private Future<?> e() {
        try {
            return ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        final CountDownLatch countDownLatch = new CountDownLatch(1);
                        PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).a(new RequestCallback<ConfigRule, Void>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.2.1
                            public void a(ConfigRule configRule) {
                                try {
                                    countDownLatch.countDown();
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            public void a(Void r1) {
                                try {
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
                            public /* synthetic */ void onError(Void r1) {
                                try {
                                    a(r1);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                            public /* synthetic */ void onSuccess(ConfigRule configRule) {
                                try {
                                    a(configRule);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }
                        });
                        try {
                            countDownLatch.await(3000L, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ com.mobile.auth.gatewayauth.manager.d f(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.g;
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

    static /* synthetic */ ResultCodeProcessor g(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.l;
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

    @AuthNumber
    public static PhoneNumberAuthHelperProxy getInstance(Context context, TokenResultListener tokenResultListener) {
        try {
            if (a == null && context != null) {
                synchronized (PhoneNumberAuthHelperProxy.class) {
                    if (a == null) {
                        a = new PhoneNumberAuthHelperProxy(context, tokenResultListener);
                    }
                }
            }
            a.setAuthListener(tokenResultListener);
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

    /* JADX WARN: Unreachable blocks removed: 7, instructions: 7 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:8:0x0009
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    @com.mobile.auth.gatewayauth.annotations.AuthNumber
    public static java.lang.String getVersion() {
        /*
            java.lang.String r0 = "2.13.12"
            return r0
        L3:
            r0 = move-exception
            r1 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)     // Catch: java.lang.Throwable -> L9
            return r1
        L9:
            r0 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.getVersion():java.lang.String");
    }

    static /* synthetic */ Future h(PhoneNumberAuthHelperProxy phoneNumberAuthHelperProxy) {
        try {
            return phoneNumberAuthHelperProxy.i;
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

    @SafeProtector
    private native void justGetLoginPhone(MonitorStruct monitorStruct, String str, int i, ResultCodeProcessor resultCodeProcessor, boolean z, RequestCallback<LoginPhoneInfo, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, String str2, String str3, int i2);

    @SafeProtector
    private native void justGetLoginToken(long j, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor);

    @SafeProtector
    private void justGetToken(int i, final ResultCodeProcessor resultCodeProcessor, boolean z, final TokenResultListener tokenResultListener) {
        String str;
        try {
            final String strJ = this.g.j();
            String strD = this.g.d();
            int i2 = i <= 0 ? Constant.DEFAULT_TIMEOUT : i;
            long j = i2;
            final String strC = this.c.c();
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setApiLevel(resultCodeProcessor.getApiLevel());
            monitorStruct.putApiParam("timeout", String.valueOf(i2));
            monitorStruct.setSessionId(strD);
            monitorStruct.setRequestId(strJ);
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setUrgency(1);
            monitorStruct.setVendorKey(strC);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.c.e(), true));
            monitorStruct.setAction(this.c.g(strC));
            final c cVar = new c(j, new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("justGetToken Timeout!");
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            cVar.a();
            TokenRet tokenRetA = this.c.a(resultCodeProcessor, false, strC);
            com.mobile.auth.gatewayauth.utils.e.a().a(strJ, "checkEnv", System.currentTimeMillis());
            if (tokenRetA != null) {
                if (cVar.d()) {
                    monitorStruct.setAuthSdkCode(tokenRetA.getCode());
                    b(tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                    this.j.e("justGetToken env is not safe!", tokenRetA.toJsonString());
                    return;
                }
                return;
            }
            com.mobile.auth.gatewayauth.manager.a aVarA = this.h.a(strC);
            if (aVarA == null) {
                if (cVar.d()) {
                    b(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                    this.j.e("justGetToken errorCode = ", ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "; errorMsg = ", "无法判运营商");
                    return;
                }
                return;
            }
            if (this.d.b()) {
                if (cVar.d()) {
                    b(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                }
                this.j.e("justGetToken errorCode = ", Constant.CODE_ERROR_FUNCTION_DEMOTE, "; errorMsg = ", "系统维护，功能不可用", "; action = ", monitorStruct.getAction());
                return;
            }
            CacheKey simCacheKey = this.c.getSimCacheKey(!z, strC);
            com.mobile.auth.gatewayauth.utils.e.a().a(strJ, "cacheKey", System.currentTimeMillis());
            String strA = this.e.a(!z);
            if (z && !this.e.a()) {
                this.j.e("justGetToken SceneCode = null");
                if (cVar.d()) {
                    b(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                    return;
                }
                return;
            }
            if (z) {
                str = strD;
            } else {
                str = strD;
                if (!this.e.a(str, com.mobile.auth.gatewayauth.manager.e.a(this.c.e(), this.e, this.d, this.j))) {
                    this.j.e("justGetToken SceneCode = null");
                    if (cVar.d()) {
                        b(Constant.CODE_ERROR_GET_CONFIG_FAIL, "获取运营商配置信息失败", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_GET_CONFIG_FAIL, "获取运营商配置信息失败"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        return;
                    }
                    return;
                }
                this.h.a(this.e);
            }
            aVarA.a(j);
            this.f.a(j, strC, new RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.10
                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        if (cVar.d()) {
                            monitorStruct.setCache(String.valueOf(bVar.e()));
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, strC, resultCodeProcessor, monitorStruct, bVar.d(), tokenResultListener, strJ);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        if (cVar.d()) {
                            monitorStruct.setCache("false");
                            monitorStruct.setCarrierFailedResultData(bVar.d());
                            PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        }
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
                        b(bVar);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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
            }, simCacheKey, com.igexin.push.config.c.i, strA, strJ, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @SafeProtector
    private void justPreLogin(long j, final PreLoginResultListener preLoginResultListener, final ResultCodeProcessor resultCodeProcessor, boolean z, boolean z2) {
        String str;
        int i;
        try {
            final String strJ = this.g.j();
            String strF = this.g.f();
            long j2 = j <= 0 ? 5000L : j;
            final String strC = this.c.c();
            final String strB = this.c.b(strC);
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setApiLevel(resultCodeProcessor.getApiLevel());
            monitorStruct.putApiParam("timeout", String.valueOf(j2));
            monitorStruct.setSessionId(strF);
            monitorStruct.setRequestId(strJ);
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setVendorKey(strC);
            monitorStruct.setAction(this.c.c(strC));
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.c.e(), true));
            if (preLoginResultListener == null) {
                a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null"), strC, (TokenResultListener) null, resultCodeProcessor, monitorStruct, strJ);
                this.j.e("accelerateLoginPage errorMsg = PreLoginResultListener is null");
                return;
            }
            final TokenResultListener tokenResultListener = new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.21
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str2) {
                    try {
                        preLoginResultListener.onTokenFailed(strB, str2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str2) {
                    try {
                        preLoginResultListener.onTokenSuccess(strB);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            final c cVar = new c(j2, new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("justPreLogin errorCode = ", ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "; errorMsg = ", "请求超时", "; action = ", monitorStruct.getAction());
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            cVar.a();
            TokenRet tokenRetA = this.c.a(resultCodeProcessor, false, strC);
            if (tokenRetA != null && cVar.d()) {
                a(tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                this.j.e("justPreLogin errorCode = ", tokenRetA.getCode(), "; errorMsg = ", tokenRetA.getMsg(), "; action = ", monitorStruct.getAction());
                return;
            }
            com.mobile.auth.gatewayauth.manager.a aVarA = this.h.a(strC);
            if (aVarA == null) {
                if (cVar.d()) {
                    a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                }
                this.j.e("justPreLogin errorCode = ", ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "; errorMsg = ", "无法判运营商");
                return;
            }
            if (this.d.c()) {
                if (cVar.d()) {
                    i = 6;
                    a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用"), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                } else {
                    i = 6;
                }
                com.mobile.auth.o.a aVar = this.j;
                String[] strArr = new String[i];
                strArr[0] = "justPreLogin errorCode = ";
                strArr[1] = Constant.CODE_ERROR_FUNCTION_DEMOTE;
                strArr[2] = "; errorMsg = ";
                strArr[3] = "系统维护，功能不可用";
                strArr[4] = "; action = ";
                strArr[5] = monitorStruct.getAction();
                aVar.e(strArr);
                return;
            }
            if (z2) {
                if (cVar.d()) {
                    a(ResultCode.CODE_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, ResultCode.MSG_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE, ResultCode.MSG_ERROR_CALL_PRE_LOGIN_IN_AUTH_PAGE), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                }
                this.j.e("Auth page has been showing!");
                return;
            }
            if (z && !this.e.a()) {
                this.j.e("justGetToken SceneCode = null");
                if (cVar.d()) {
                    a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO), strB, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                    return;
                }
                return;
            }
            if (z) {
                str = strF;
            } else {
                str = strF;
                if (!this.e.a(str, com.mobile.auth.gatewayauth.manager.e.a(this.c.e(), this.e, this.d, this.j))) {
                    this.j.e("justGetToken SceneCode = null");
                    if (cVar.d()) {
                        a(Constant.CODE_ERROR_GET_CONFIG_FAIL, "获取运营商配置信息失败", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_GET_CONFIG_FAIL, "获取运营商配置信息失败"), strB, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        return;
                    }
                    return;
                }
                this.h.a(this.e);
            }
            aVarA.a(j2);
            this.f.updateMask(j2, strC, new RequestCallback<com.mobile.auth.gatewayauth.manager.base.b, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.24
                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        if (cVar.d()) {
                            monitorStruct.setCache(String.valueOf(bVar.e()));
                            monitorStruct.setAuthSdkCode(resultCodeProcessor.convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, false, "", strB, monitorStruct, tokenResultListener);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void b(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        if (cVar.d()) {
                            monitorStruct.setCache("false");
                            monitorStruct.setCarrierFailedResultData(bVar.d());
                            PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), strC, tokenResultListener, resultCodeProcessor, monitorStruct, strJ);
                        }
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
                        b(bVar);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(com.mobile.auth.gatewayauth.manager.base.b bVar) {
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
            }, this.c.getSimCacheKey(!z, strC), 6, this.e.a(!z), strJ, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @SafeProtector
    private void justPreVerify(long j, final PreLoginResultListener preLoginResultListener) {
        int i;
        int i2;
        long j2 = j <= 0 ? 5000L : j;
        try {
            final String strJ = this.g.j();
            String strG = this.g.g();
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setApiLevel(this.l.getApiLevel());
            final String strC = this.c.c();
            final String strB = this.c.b(strC);
            monitorStruct.putApiParam("timeout", String.valueOf(j2));
            monitorStruct.setSessionId(strG);
            monitorStruct.setRequestId(strJ);
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setVendorKey(strC);
            monitorStruct.setAction(this.c.d(strC));
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.c.e(), true));
            if (preLoginResultListener == null) {
                b(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_UNKNOWN_FAIL, "PreLoginResultListener is null"), strC, null, this.l, monitorStruct, strJ);
                this.j.e("accelerateVerify errorMsg = PreLoginResultListener is null");
                return;
            }
            final TokenResultListener tokenResultListener = new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.15
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str) {
                    try {
                        preLoginResultListener.onTokenFailed(strB, str);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str) {
                    try {
                        preLoginResultListener.onTokenSuccess(strB);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            final c cVar = new c(j2, new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "请求超时"), strC, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), monitorStruct, strJ);
                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("justPreVerify errorCode = ", ResultCode.CODE_ERROR_FUNCTION_TIME_OUT, "; errorMsg = ", "请求超时", "; action = ", monitorStruct.getAction());
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
            cVar.a();
            TokenRet tokenRetA = this.c.a(this.l, false, strC);
            if (tokenRetA != null) {
                if (cVar.d()) {
                    i2 = 6;
                    b(tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, tokenResultListener, this.l, monitorStruct, strJ);
                } else {
                    i2 = 6;
                }
                com.mobile.auth.o.a aVar = this.j;
                String[] strArr = new String[i2];
                strArr[0] = "justPreVerify errorCode = ";
                strArr[1] = tokenRetA.getCode();
                strArr[2] = "; errorMsg = ";
                strArr[3] = tokenRetA.getMsg();
                strArr[4] = "; action = ";
                strArr[5] = monitorStruct.getAction();
                aVar.e(strArr);
                return;
            }
            com.mobile.auth.gatewayauth.manager.a aVarA = this.h.a(strC);
            if (aVarA == null) {
                if (cVar.d()) {
                    b(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商"), strC, tokenResultListener, this.l, monitorStruct, strJ);
                }
                this.j.e("justPreVerify errorCode = ", ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "; errorMsg = ", "无法判运营商");
                return;
            }
            if (!this.d.b()) {
                if (this.e.a()) {
                    aVarA.a(j2);
                    this.f.b(j2, strC, new RequestCallback<String, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.17
                        public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                            try {
                                if (cVar.d()) {
                                    monitorStruct.setCache("false");
                                    monitorStruct.setCarrierFailedResultData(bVar.d());
                                    PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this, bVar.b(), bVar.c(), bVar.g(), strC, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), monitorStruct, strJ);
                                }
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
                                if (cVar.d()) {
                                    monitorStruct.setCache(str);
                                    monitorStruct.setAuthSdkCode(PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertCode(Constant.CODE_GET_TOKEN_SUCCESS));
                                    PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, true, false, "", strB, monitorStruct, tokenResultListener);
                                }
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
                    }, this.c.getSimCacheKey(false, strC), com.mobile.auth.gatewayauth.manager.c.a(strC), this.e.a(false), strJ, strG);
                    return;
                } else {
                    this.j.e("justPreVerify SceneCode = null");
                    if (cVar.d()) {
                        b(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, com.mobile.auth.gatewayauth.utils.a.a(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO), strB, tokenResultListener, this.l, monitorStruct, strJ);
                        return;
                    }
                    return;
                }
            }
            if (cVar.d()) {
                i = 6;
                b(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", com.mobile.auth.gatewayauth.utils.a.a(Constant.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用"), strC, tokenResultListener, this.l, monitorStruct, strJ);
            } else {
                i = 6;
            }
            com.mobile.auth.o.a aVar2 = this.j;
            String[] strArr2 = new String[i];
            strArr2[0] = "justPreVerify errorCode = ";
            strArr2[1] = Constant.CODE_ERROR_FUNCTION_DEMOTE;
            strArr2[2] = "; errorMsg = ";
            strArr2[3] = "系统维护，功能不可用";
            strArr2[4] = "; action = ";
            strArr2[5] = monitorStruct.getAction();
            aVar2.e(strArr2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    protected SystemManager a() {
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

    void a(final long j, final TokenResultListener tokenResultListener, final ResultCodeProcessor resultCodeProcessor) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(tokenResultListener) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.6
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                protected void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, j, tokenResultListener, resultCodeProcessor);
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

    public synchronized void a(String str) {
        try {
            this.m = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    protected void a(String str, String str2, MonitorStruct monitorStruct, ResultCodeProcessor resultCodeProcessor, TokenResultListener tokenResultListener) {
        if (tokenResultListener == null) {
            return;
        }
        try {
            TokenRet tokenRetConvertErrorInfo = resultCodeProcessor.convertErrorInfo(Constant.CODE_GET_TOKEN_SUCCESS, "获取token成功", str2);
            tokenRetConvertErrorInfo.setToken(str);
            tokenRetConvertErrorInfo.setRequestId(monitorStruct.getRequestId());
            monitorStruct.setPerformanceTrace(com.mobile.auth.gatewayauth.utils.e.a().a(monitorStruct.getRequestId()));
            monitorStruct.setAccessCode(str);
            monitorStruct.setAuthSdkCode(tokenRetConvertErrorInfo.getCode());
            a(true, true, "", tokenRetConvertErrorInfo.toJsonString(), monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    protected void a(boolean z, boolean z2, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, TokenResultListener tokenResultListener, ResultCodeProcessor resultCodeProcessor, String str5) {
        try {
            TokenRet tokenRetConvertErrorInfo = resultCodeProcessor.convertErrorInfo(str, str2, str4);
            if (monitorStruct != null) {
                monitorStruct.setPerformanceTrace(com.mobile.auth.gatewayauth.utils.e.a().a(str5));
                monitorStruct.setAuthSdkCode(tokenRetConvertErrorInfo.getCode());
                tokenRetConvertErrorInfo.setCarrierFailedResultData(monitorStruct.getCarrierFailedResultData());
            }
            tokenRetConvertErrorInfo.setRequestId(str5);
            a(z, z2, str3, tokenRetConvertErrorInfo.toJsonString(), monitorStruct, tokenResultListener);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void accelerateLoginPage(final int i, final PreLoginResultListener preLoginResultListener, final boolean z) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(this.b) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.11
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                protected void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, i, preLoginResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), true, z);
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

    @AuthNumber
    public void accelerateVerify(final int i, final PreLoginResultListener preLoginResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.13
                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenFailed(String str) {
                    try {
                        if (preLoginResultListener != null) {
                            preLoginResultListener.onTokenFailed("", str);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                public void onTokenSuccess(String str) {
                }
            }) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.14
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                protected void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, i, preLoginResultListener);
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

    protected com.mobile.auth.gatewayauth.manager.d b() {
        try {
            return this.g;
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

    protected com.mobile.auth.o.a c() {
        try {
            return this.j;
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

    @AuthNumber
    public void checkEnvAvailable(@IntRange(from = 1, to = 2) final int i, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.4
                /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:76:0x02f2 A[Catch: Throwable -> 0x035d, TryCatch #1 {Throwable -> 0x035d, blocks: (B:3:0x0002, B:10:0x0030, B:12:0x003b, B:17:0x009b, B:19:0x00a6, B:27:0x0108, B:29:0x0113, B:35:0x0169, B:37:0x0174, B:48:0x01ee, B:50:0x01f9, B:63:0x02a9, B:74:0x02e7, B:76:0x02f2, B:80:0x0322, B:82:0x032d, B:83:0x035c, B:56:0x0255, B:58:0x0260), top: B:91:0x0002 }] */
                /* JADX WARN: Removed duplicated region for block: B:82:0x032d A[Catch: Throwable -> 0x035d, TryCatch #1 {Throwable -> 0x035d, blocks: (B:3:0x0002, B:10:0x0030, B:12:0x003b, B:17:0x009b, B:19:0x00a6, B:27:0x0108, B:29:0x0113, B:35:0x0169, B:37:0x0174, B:48:0x01ee, B:50:0x01f9, B:63:0x02a9, B:74:0x02e7, B:76:0x02f2, B:80:0x0322, B:82:0x032d, B:83:0x035c, B:56:0x0255, B:58:0x0260), top: B:91:0x0002 }] */
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                protected void a() {
                    TokenRet tokenRet;
                    char c;
                    int i2;
                    TokenRet tokenRetConvertErrorInfo;
                    try {
                        String strC = PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).c();
                        String strJ = PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).j();
                        try {
                            try {
                                try {
                                    if (i != 1 && i != 2) {
                                        TokenRet tokenRetConvertErrorInfo2 = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo("600025", ResultCode.MSG_ERROR_INVALID_PARAM, strC);
                                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                        if (tokenRetConvertErrorInfo2 != null) {
                                            PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                            return;
                                        }
                                        return;
                                    }
                                    TokenRet tokenRetA = PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).a(PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), true, strC);
                                    try {
                                        if (tokenRetA != null) {
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", tokenRetA.getCode(), "; errorMsg = ", tokenRetA.getMsg());
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                            if (tokenRetA != null) {
                                                PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                return;
                                            }
                                            return;
                                        }
                                        if (!PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).a()) {
                                            TokenRet tokenRetConvertErrorInfo3 = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, strC);
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, "; errorMsg = ", ResultCode.MSG_ERROR_ANALYZE_SDK_INFO);
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                            if (tokenRetConvertErrorInfo3 != null) {
                                                PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                return;
                                            }
                                            return;
                                        }
                                        if (PhoneNumberAuthHelperProxy.c(PhoneNumberAuthHelperProxy.this).a(strC) == null) {
                                            TokenRet tokenRetConvertErrorInfo4 = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", strC);
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable failed! Unsupported Vendor!");
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                            if (tokenRetConvertErrorInfo4 != null) {
                                                PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo4.getCode(), tokenRetConvertErrorInfo4.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo4.getCode(), tokenRetConvertErrorInfo4.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                return;
                                            }
                                            return;
                                        }
                                        PhoneNumberAuthHelperProxy.b(PhoneNumberAuthHelperProxy.this).b();
                                        if (PhoneNumberAuthHelperProxy.h(PhoneNumberAuthHelperProxy.this) != null) {
                                            PhoneNumberAuthHelperProxy.h(PhoneNumberAuthHelperProxy.this).get();
                                        }
                                        if (i == 1) {
                                            if (PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).b()) {
                                                TokenRet tokenRetConvertErrorInfo5 = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", strC);
                                                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "; errorMsg = ", "系统维护，功能不可用");
                                                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                                if (tokenRetConvertErrorInfo5 != null) {
                                                    PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo5.getCode(), tokenRetConvertErrorInfo5.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo5.getCode(), tokenRetConvertErrorInfo5.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                    return;
                                                }
                                                return;
                                            }
                                        } else if (PhoneNumberAuthHelperProxy.e(PhoneNumberAuthHelperProxy.this).c()) {
                                            TokenRet tokenRetConvertErrorInfo6 = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "系统维护，功能不可用", strC);
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).e("checkEnvAvailable errorCode = ", ResultCode.CODE_ERROR_FUNCTION_DEMOTE, "; errorMsg = ", "系统维护，功能不可用");
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                            if (tokenRetConvertErrorInfo6 != null) {
                                                PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo6.getCode(), tokenRetConvertErrorInfo6.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo6.getCode(), tokenRetConvertErrorInfo6.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                return;
                                            }
                                            return;
                                        }
                                        c = 1;
                                        i2 = 2;
                                        try {
                                            PhoneNumberAuthHelperProxy.this.a(true, false, ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS, ResultCode.MSG_ERROR_ENV_CHECK_SUCCESS, "", strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                        } catch (Exception e) {
                                            e = e;
                                            e.printStackTrace();
                                            tokenRetConvertErrorInfo = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e), strC);
                                            try {
                                                com.mobile.auth.o.a aVarD = PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this);
                                                String[] strArr = new String[i2];
                                                strArr[0] = "checkEnvAvailable exception:";
                                                strArr[c] = tokenRetConvertErrorInfo.toJsonString();
                                                aVarD.e(strArr);
                                                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                                if (tokenRetConvertErrorInfo == null) {
                                                    PhoneNumberAuthHelperProxy.this.a(false, false, tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                                }
                                            } catch (Throwable th) {
                                                th = th;
                                                tokenRet = tokenRetConvertErrorInfo;
                                                PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                                if (tokenRet != null) {
                                                }
                                                throw th;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        c = 1;
                                        i2 = 2;
                                        e.printStackTrace();
                                        tokenRetConvertErrorInfo = PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this).convertErrorInfo(ResultCode.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e), strC);
                                        com.mobile.auth.o.a aVarD2 = PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this);
                                        String[] strArr2 = new String[i2];
                                        strArr2[0] = "checkEnvAvailable exception:";
                                        strArr2[c] = tokenRetConvertErrorInfo.toJsonString();
                                        aVarD2.e(strArr2);
                                        PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                        if (tokenRetConvertErrorInfo == null) {
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                                    if (tokenRet != null) {
                                        PhoneNumberAuthHelperProxy.this.a(false, false, tokenRet.getCode(), tokenRet.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRet.getCode(), tokenRet.getMsg()), strC, null, tokenResultListener, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), strJ);
                                    }
                                    throw th;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                c = 1;
                                i2 = 2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            tokenRet = null;
                            PhoneNumberAuthHelperProxy.d(PhoneNumberAuthHelperProxy.this).c();
                            if (tokenRet != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th4) {
                        try {
                            ExceptionProcessor.processException(th4);
                        } catch (Throwable th5) {
                            ExceptionProcessor.processException(th5);
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

    /* JADX WARN: Not initialized variable reg: 17, insn: 0x00ea: MOVE (r16 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY]), block:B:23:0x00ea */
    /* JADX WARN: Not initialized variable reg: 17, insn: 0x00ef: MOVE (r1 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY]), block:B:25:0x00ef */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x026c  */
    @AuthNumber
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean checkEnvAvailable() throws Throwable {
        char c;
        int i;
        TokenRet tokenRetA;
        TokenRet tokenRet;
        TokenRet tokenRet2;
        try {
            String strC = this.c.c();
            String strJ = this.g.j();
            TokenRet tokenRet3 = null;
            try {
                tokenRetA = this.c.a(this.l, true, strC);
                if (tokenRetA != null) {
                    if (tokenRetA != null) {
                        a(false, false, tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, null, null, this.l, strJ);
                        this.j.e("checkEnvAvailable code = ", tokenRetA.getCode(), "; msg = ", tokenRetA.getMsg());
                    } else {
                        a(true, false, "", "", "", "", null, null, this.l, strJ);
                    }
                    return false;
                }
                c = 1;
                i = 4;
                try {
                    try {
                        if (!this.e.a()) {
                            TokenRet tokenRetConvertErrorInfo = this.l.convertErrorInfo(ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, strC);
                            this.j.e("checkEnvAvailable failed! Has no sceneCode!");
                            if (tokenRetConvertErrorInfo != null) {
                                a(false, false, tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo.getCode(), tokenRetConvertErrorInfo.getMsg()), strC, null, null, this.l, strJ);
                                this.j.e("checkEnvAvailable code = ", tokenRetConvertErrorInfo.getCode(), "; msg = ", tokenRetConvertErrorInfo.getMsg());
                            } else {
                                a(true, false, "", "", "", "", null, null, this.l, strJ);
                            }
                            return false;
                        }
                        this.e.b();
                        if (this.h.a(strC) != null) {
                            if (tokenRetA != null) {
                                a(false, false, tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, null, null, this.l, strJ);
                                this.j.e("checkEnvAvailable code = ", tokenRetA.getCode(), "; msg = ", tokenRetA.getMsg());
                            } else {
                                a(true, false, "", "", "", "", null, null, this.l, strJ);
                            }
                            return true;
                        }
                        TokenRet tokenRetConvertErrorInfo2 = this.l.convertErrorInfo(ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL, "无法判运营商", strC);
                        this.j.e("checkEnvAvailable failed! Unsupported Vendor!");
                        if (tokenRetConvertErrorInfo2 != null) {
                            a(false, false, tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo2.getCode(), tokenRetConvertErrorInfo2.getMsg()), strC, null, null, this.l, strJ);
                            this.j.e("checkEnvAvailable code = ", tokenRetConvertErrorInfo2.getCode(), "; msg = ", tokenRetConvertErrorInfo2.getMsg());
                        } else {
                            a(true, false, "", "", "", "", null, null, this.l, strJ);
                        }
                        return false;
                    } catch (Exception e) {
                        e = e;
                        tokenRet3 = tokenRet2;
                        try {
                            e.printStackTrace();
                            TokenRet tokenRetConvertErrorInfo3 = this.l.convertErrorInfo(ResultCode.CODE_ERROR_UNKNOWN_FAIL, ExecutorManager.getErrorInfoFromException(e), strC);
                            if (tokenRetConvertErrorInfo3 != null) {
                                a(false, false, tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetConvertErrorInfo3.getCode(), tokenRetConvertErrorInfo3.getMsg()), strC, null, null, this.l, strJ);
                                com.mobile.auth.o.a aVar = this.j;
                                String[] strArr = new String[i];
                                strArr[0] = "checkEnvAvailable code = ";
                                strArr[c] = tokenRetConvertErrorInfo3.getCode();
                                strArr[2] = "; msg = ";
                                strArr[3] = tokenRetConvertErrorInfo3.getMsg();
                                aVar.e(strArr);
                            } else {
                                a(true, false, "", "", "", "", null, null, this.l, strJ);
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            tokenRetA = tokenRet3;
                            if (tokenRetA != null) {
                                a(false, false, tokenRetA.getCode(), tokenRetA.getMsg(), com.mobile.auth.gatewayauth.utils.a.a(tokenRetA.getCode(), tokenRetA.getMsg()), strC, null, null, this.l, strJ);
                                com.mobile.auth.o.a aVar2 = this.j;
                                String[] strArr2 = new String[i];
                                strArr2[0] = "checkEnvAvailable code = ";
                                strArr2[c] = tokenRetA.getCode();
                                strArr2[2] = "; msg = ";
                                strArr2[3] = tokenRetA.getMsg();
                                aVar2.e(strArr2);
                            } else {
                                a(true, false, "", "", "", "", null, null, this.l, strJ);
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        tokenRetA = tokenRet;
                        if (tokenRetA != null) {
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    tokenRet3 = tokenRetA;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e3) {
                e = e3;
                c = 1;
                i = 4;
            } catch (Throwable th4) {
                th = th4;
                c = 1;
                i = 4;
                tokenRetA = tokenRet3;
                if (tokenRetA != null) {
                }
                throw th;
            }
        } catch (Throwable th5) {
            try {
                ExceptionProcessor.processException(th5);
                return false;
            } catch (Throwable th6) {
                ExceptionProcessor.processException(th6);
                return false;
            }
        }
    }

    @AuthNumber
    public void clearPreInfo() {
        try {
            this.f.a();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public String getCurrentCarrierName() {
        try {
            return this.c.d();
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

    @AuthNumber
    public String getLoginMaskPhone(int i, final String str, final OnLoginPhoneListener onLoginPhoneListener, final boolean z, boolean z2, final String str2) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            String strF = this.g.f();
            monitorStruct.setStartTime(System.currentTimeMillis());
            justGetLoginPhone(monitorStruct, str, i, this.l, z2, new RequestCallback<LoginPhoneInfo, com.mobile.auth.gatewayauth.manager.base.b>() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.18
                public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
                    try {
                        PhoneNumberAuthHelperProxy.f(PhoneNumberAuthHelperProxy.this).h();
                        PhoneNumberAuthHelperProxy.this.a(false, z, bVar.b(), bVar.c(), bVar.g(), str, monitorStruct, new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.18.3
                            @Override // com.mobile.auth.gatewayauth.TokenResultListener
                            public void onTokenFailed(String str3) {
                                try {
                                    onLoginPhoneListener.onGetFailed(str3);
                                } catch (Throwable th) {
                                    try {
                                        ExceptionProcessor.processException(th);
                                    } catch (Throwable th2) {
                                        ExceptionProcessor.processException(th2);
                                    }
                                }
                            }

                            @Override // com.mobile.auth.gatewayauth.TokenResultListener
                            public void onTokenSuccess(String str3) {
                            }
                        }, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), str2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(final LoginPhoneInfo loginPhoneInfo) {
                    try {
                        loginPhoneInfo.setVendor(PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).b(str));
                        if (com.mobile.auth.gatewayauth.utils.c.e(PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).e())) {
                            PhoneNumberAuthHelperProxy.this.a(true, z, "600000", "获取掩码成功", "", str, monitorStruct, null, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), str2);
                            ExecutorManager.getInstance().postMain(new ExecutorManager.SafeRunnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.18.1
                                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                                protected void onException(Throwable th) {
                                }

                                @Override // com.nirvana.tools.core.ExecutorManager.SafeRunnable
                                protected void safeRun() {
                                    try {
                                        onLoginPhoneListener.onGetLoginPhone(loginPhoneInfo);
                                    } catch (Throwable th) {
                                        try {
                                            ExceptionProcessor.processException(th);
                                        } catch (Throwable th2) {
                                            ExceptionProcessor.processException(th2);
                                        }
                                    }
                                }
                            });
                        } else {
                            PhoneNumberAuthHelperProxy.this.a(false, z, ResultCode.CODE_ERROR_NO_MOBILE_NETWORK_FAIL, ResultCode.MSG_ERROR_NO_MOBILE_NETWORK_FAIL, "", str, monitorStruct, new TokenResultListener() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.18.2
                                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                                public void onTokenFailed(String str3) {
                                    try {
                                        onLoginPhoneListener.onGetFailed(str3);
                                    } catch (Throwable th) {
                                        try {
                                            ExceptionProcessor.processException(th);
                                        } catch (Throwable th2) {
                                            ExceptionProcessor.processException(th2);
                                        }
                                    }
                                }

                                @Override // com.mobile.auth.gatewayauth.TokenResultListener
                                public void onTokenSuccess(String str3) {
                                }
                            }, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), str2);
                        }
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
                public /* synthetic */ void onSuccess(LoginPhoneInfo loginPhoneInfo) {
                    try {
                        a(loginPhoneInfo);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, str2, strF, 6);
            return str2;
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

    @AuthNumber
    public PnsReporter getReporter() {
        try {
            return this.g.b();
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

    @AuthNumber
    public void getVerifyToken(final int i, final TokenResultListener tokenResultListener) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new g.a(tokenResultListener) { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.5
                @Override // com.mobile.auth.gatewayauth.utils.g.a
                protected void a() {
                    try {
                        PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, i, PhoneNumberAuthHelperProxy.g(PhoneNumberAuthHelperProxy.this), true, tokenResultListener);
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

    @AuthNumber
    public void prohibitUseUtdid() {
        try {
            if (this.g != null) {
                this.g.n();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void setAuthListener(TokenResultListener tokenResultListener) {
        try {
            this.b = tokenResultListener;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @AuthNumber
    public void setAuthSDKInfo(String str) {
        try {
            this.j.a("setAuthSDKInfo secretInfo = ", str);
            this.c.a(str);
            this.e.setLocalVendorSdkInfo(str);
            if (!this.h.a(this.e)) {
                this.j.e("VendorSdkFactor update local VendorConfig failed!");
                return;
            }
            com.mobile.auth.gatewayauth.utils.c.g(this.c.e());
            if (b(this.c.e())) {
                if (RequestState.getInstance().checkTokenValied(1)) {
                    this.j.e();
                } else {
                    if (RequestState.getInstance().isUseRequest() || RequestState.getInstance().checkTokenValied(1)) {
                        return;
                    }
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.PhoneNumberAuthHelperProxy.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this, PhoneNumberAuthHelperProxy.a(PhoneNumberAuthHelperProxy.this).e(), true);
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
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
