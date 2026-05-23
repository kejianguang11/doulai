package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.RequestQueue;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private Context a;
    private com.mobile.auth.o.a b;
    private volatile ConfigRule c;
    private VendorSdkInfoManager d;

    public b(Context context, VendorSdkInfoManager vendorSdkInfoManager, d dVar) {
        this.a = context.getApplicationContext();
        this.c = UTSharedPreferencesHelper.readSDKConfig(this.a);
        this.d = vendorSdkInfoManager;
        this.b = dVar.a();
        if (this.c != null) {
            this.b.a(this.c);
        }
    }

    static /* synthetic */ ConfigRule a(b bVar) {
        try {
            return bVar.c;
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

    static /* synthetic */ ConfigRule a(b bVar, ConfigRule configRule) {
        try {
            bVar.c = configRule;
            return configRule;
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

    static /* synthetic */ com.mobile.auth.o.a b(b bVar) {
        try {
            return bVar.b;
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

    static /* synthetic */ Context c(b bVar) {
        try {
            return bVar.a;
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

    private boolean s() {
        try {
            return this.c != null;
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

    private boolean t() {
        try {
            if (Boolean.parseBoolean(this.c.getAuth_token().getIs_limited())) {
                return this.c.getAuth_token().getLimit_time_hour() > 0;
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

    private boolean u() {
        try {
            if (Boolean.parseBoolean(this.c.getLogin_token().getIs_limited())) {
                return this.c.getLogin_token().getLimit_time_hour() > 0;
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

    private boolean v() {
        try {
            if (Boolean.parseBoolean(this.c.getSls().getIs_limited())) {
                return this.c.getSls().getLimit_time_hour() > 0;
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

    private boolean w() {
        try {
            if (Boolean.parseBoolean(this.c.getGet_vendor_list().getIs_limited())) {
                return this.c.getGet_vendor_list().getLimit_time_hour() > 0;
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

    private boolean x() {
        try {
            if (Boolean.parseBoolean(this.c.getLogin_page().getIs_limited())) {
                return this.c.getLogin_page().getLimit_time_hour() > 0;
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

    private boolean y() {
        try {
            if (Boolean.parseBoolean(this.c.getLogin_phone().getIs_limited())) {
                return this.c.getLogin_phone().getLimit_time_hour() > 0;
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

    private boolean z() {
        try {
            if (Boolean.parseBoolean(this.c.getGet_config().getIs_limited())) {
                return this.c.getGet_config().getLimit_time_hour() > 0;
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

    public void a(final RequestCallback<ConfigRule, Void> requestCallback) {
        try {
            if (r()) {
                requestCallback.onError(null);
                return;
            }
            q();
            RequestQueue.getInstance().pushRequest(new com.mobile.auth.t.a(new Callback<com.mobile.auth.u.c>(ThreadStrategy.THREAD, 2000L) { // from class: com.mobile.auth.gatewayauth.manager.b.1
                public void a(com.mobile.auth.u.c cVar) {
                    try {
                        if (cVar.a() == null) {
                            requestCallback.onError(null);
                            return;
                        }
                        ConfigRule configRuleA = cVar.a();
                        if (b.a(b.this) == null || !b.a(b.this).toString().equals(configRuleA.toString())) {
                            b.a(b.this, configRuleA);
                            b.b(b.this).a(b.a(b.this));
                            UTSharedPreferencesHelper.clearLimitCount(b.c(b.this));
                            UTSharedPreferencesHelper.saveSDKConfig(b.c(b.this), b.a(b.this).toJsonString());
                        }
                        requestCallback.onSuccess(b.a(b.this));
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
                        a((com.mobile.auth.u.c) response);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, new com.mobile.auth.p.c(this.a, this.d, this.b), com.igexin.push.config.c.s, com.mobile.auth.u.c.class));
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
            if (s()) {
                return Boolean.parseBoolean(this.c.getIs_demoted());
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

    public boolean a(int i) {
        try {
            switch (i) {
                case 1:
                    return h();
                case 2:
                    return j();
                case 3:
                    return p();
                case 4:
                    return d();
                case 5:
                    return f();
                case 6:
                    return n();
                case 7:
                    return l();
                default:
                    return false;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(int i) {
        try {
            switch (i) {
                case 1:
                    i();
                    break;
                case 2:
                    k();
                    break;
                case 3:
                    q();
                    break;
                case 4:
                    e();
                    break;
                case 5:
                    g();
                    break;
                case 6:
                    o();
                    break;
                case 7:
                    m();
                    break;
                default:
                    return;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean b() {
        try {
            if (!s()) {
                return false;
            }
            if (!a()) {
                if (!Boolean.parseBoolean(this.c.getIs_auth_demoted())) {
                    return false;
                }
            }
            return true;
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

    public boolean c() {
        try {
            if (!s()) {
                return false;
            }
            if (!a()) {
                if (!Boolean.parseBoolean(this.c.getIs_login_demoted())) {
                    return false;
                }
            }
            return true;
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

    public boolean d() {
        try {
            if (s() && t()) {
                return UTSharedPreferencesHelper.readAuthTokenLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getAuth_token().getLimit_time_hour())) >= this.c.getAuth_token().getLimit_count();
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

    public void e() {
        try {
            if (s() && t()) {
                UTSharedPreferencesHelper.saveAuthTokenLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getAuth_token().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean f() {
        try {
            if (s() && u()) {
                return UTSharedPreferencesHelper.readLoginTokenLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_token().getLimit_time_hour())) >= this.c.getLogin_token().getLimit_count();
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

    public void g() {
        try {
            if (s() && u()) {
                UTSharedPreferencesHelper.saveLoginTokenLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_token().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean h() {
        try {
            if (s() && v()) {
                return UTSharedPreferencesHelper.readSLSLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getSls().getLimit_time_hour())) >= this.c.getSls().getLimit_count();
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

    public void i() {
        try {
            if (s() && v()) {
                UTSharedPreferencesHelper.saveSLSLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getSls().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean j() {
        try {
            if (s() && w()) {
                return UTSharedPreferencesHelper.readVendorLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getGet_vendor_list().getLimit_time_hour())) >= this.c.getGet_vendor_list().getLimit_count();
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

    public void k() {
        try {
            if (s() && w()) {
                UTSharedPreferencesHelper.saveVendorLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getGet_vendor_list().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean l() {
        try {
            if (s() && x()) {
                return UTSharedPreferencesHelper.readLoginPageLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_page().getLimit_time_hour())) >= this.c.getLogin_page().getLimit_count();
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

    public void m() {
        try {
            if (s() && x()) {
                UTSharedPreferencesHelper.saveLoginPageLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_page().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean n() {
        try {
            if (s() && y()) {
                return UTSharedPreferencesHelper.readLoginPhoneLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_phone().getLimit_time_hour())) >= this.c.getLogin_phone().getLimit_count();
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

    public void o() {
        try {
            if (s() && y()) {
                UTSharedPreferencesHelper.saveLoginPhoneLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getLogin_phone().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean p() {
        try {
            if (s() && z()) {
                return UTSharedPreferencesHelper.readConfigLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getGet_config().getLimit_time_hour())) >= this.c.getGet_config().getLimit_count();
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

    public void q() {
        try {
            if (s() && z()) {
                UTSharedPreferencesHelper.saveConfigLimitCount(this.a, com.mobile.auth.gatewayauth.utils.a.a(this.c.getGet_config().getLimit_time_hour()));
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean r() {
        try {
            String sDKConfigLimitFlag = UTSharedPreferencesHelper.readSDKConfigLimitFlag(this.a);
            if ((TextUtils.isEmpty(sDKConfigLimitFlag) || !com.mobile.auth.gatewayauth.utils.a.a(sDKConfigLimitFlag)) && !UTSharedPreferencesHelper.readSDKConfigCloseFlag(this.a)) {
                return p();
            }
            return true;
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
}
