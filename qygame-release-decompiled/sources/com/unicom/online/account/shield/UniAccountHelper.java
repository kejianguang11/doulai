package com.unicom.online.account.shield;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.y.a;
import com.mobile.auth.y.b;
import com.mobile.auth.y.d;
import com.mobile.auth.y.e;
import com.mobile.auth.y.k;
import com.mobile.auth.y.l;
import com.mobile.auth.y.m;
import com.mobile.auth.y.p;
import com.mobile.auth.y.q;
import com.mobile.auth.y.t;
import com.mobile.auth.y.u;
import com.mobile.auth.y.v;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UniAccountHelper {
    public static final int CU_GET_TOKEN_IT = 2;
    private static final int SUCCESS = 100;
    private static volatile UniAccountHelper s_instance;
    private Context mContext = null;

    private UniAccountHelper() {
    }

    static /* synthetic */ boolean access$000(UniAccountHelper uniAccountHelper) {
        try {
            return uniAccountHelper.getUseCacheFlag();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    static /* synthetic */ Context access$100(UniAccountHelper uniAccountHelper) {
        try {
            return uniAccountHelper.mContext;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private void cuPreGetToken(int i, final int i2, final String str, final ResultListener resultListener) {
        boolean z;
        try {
            if (this.mContext == null) {
                initFail(i2, resultListener, "sdk未初始化");
                return;
            }
            if (!e.d(this.mContext.getApplicationContext())) {
                initFail(i2, resultListener, "数据网络未开启");
                return;
            }
            if (getUseCacheFlag()) {
                e.a();
                String strA = e.a(this.mContext, "type".concat(String.valueOf(i2)), str);
                if (b.e(strA).booleanValue()) {
                    try {
                        JSONObject jSONObject = new JSONObject(strA);
                        int i3 = jSONObject.getInt("resultCode");
                        jSONObject.getInt("type");
                        long j = jSONObject.getJSONObject("resultData").getLong("exp");
                        if (i3 == 100 && j > System.currentTimeMillis()) {
                            resultListener.onResult(strA);
                            return;
                        }
                    } catch (Exception unused) {
                    }
                }
                e.a();
                e.e(this.mContext);
            }
            e.a();
            if (!e.a(this.mContext)) {
                initFail(i2, resultListener, "操作频繁,请稍后再试");
                return;
            }
            if (!str.equals("cuPreGetToken")) {
                initFail(i2, resultListener, "sdk参数错误");
                return;
            }
            if (i2 != 2) {
                initFail(i2, resultListener, "sdk type 参数错误");
                return;
            }
            e eVarA = e.a();
            d dVar = new d() { // from class: com.unicom.online.account.shield.UniAccountHelper.1
                @Override // com.mobile.auth.y.d
                public void onResult(String str2) {
                    try {
                        try {
                            JSONObject jSONObject2 = new JSONObject(str2);
                            b.d(jSONObject2.optString("seq"));
                            int i4 = jSONObject2.getInt("resultCode");
                            jSONObject2.getString("resultMsg");
                            if (i4 == 100) {
                                JSONObject jSONObject3 = jSONObject2.getJSONObject("resultData");
                                b.b(jSONObject3.optString("fakeMobile"));
                                b.c(jSONObject3.optString("accessCode"));
                                b.b(jSONObject3.getLong("exp"));
                                b.a(System.currentTimeMillis());
                                String strOptString = jSONObject2.optString("operator");
                                if (!TextUtils.isEmpty(strOptString)) {
                                    b.a(strOptString);
                                }
                                if (UniAccountHelper.access$000(UniAccountHelper.this)) {
                                    e.a();
                                    e.e(UniAccountHelper.access$100(UniAccountHelper.this));
                                    e.a();
                                    e.a(UniAccountHelper.access$100(UniAccountHelper.this), "type" + i2, str, jSONObject2.toString());
                                }
                                e.a();
                                e.b(UniAccountHelper.access$100(UniAccountHelper.this));
                            } else {
                                e.a();
                                e.c(UniAccountHelper.access$100(UniAccountHelper.this));
                            }
                            resultListener.onResult(jSONObject2.toString());
                        } catch (JSONException unused2) {
                            a.a();
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            };
            if (eVarA.a == null || TextUtils.isEmpty(u.c()) || TextUtils.isEmpty(u.d())) {
                e.a(i2, dVar, "sdk未初始化");
                z = false;
            } else {
                v.b();
                v.c("cuPreGetToken");
                v.c();
                u.b(i);
                z = true;
            }
            if (z) {
                k kVar = new k();
                Context context = eVarA.a;
                kVar.b = new l();
                kVar.b.a = dVar;
                try {
                    kVar.a.schedule(new Runnable() { // from class: com.mobile.auth.y.k.1
                        final /* synthetic */ int a;

                        public AnonymousClass1(final int i22) {
                            i = i22;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                synchronized (k.this) {
                                    if (k.this.b != null) {
                                        k.this.b.a(i, 410000, "请求超时", "", "");
                                        k.this.b = null;
                                        k.a(k.this);
                                    }
                                }
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    }, i, TimeUnit.MILLISECONDS);
                    k.AnonymousClass2 anonymousClass2 = new m() { // from class: com.mobile.auth.y.k.2
                        public AnonymousClass2() {
                        }

                        @Override // com.mobile.auth.y.m
                        public final void a(int i4, int i5, String str2) {
                            String str3;
                            try {
                                synchronized (k.this) {
                                    if (k.this.b == null) {
                                        return;
                                    }
                                    if (i5 == 1) {
                                        try {
                                            JSONObject jSONObject2 = new JSONObject(str2);
                                            int iOptInt = jSONObject2.optInt("code");
                                            String strOptString = jSONObject2.optString("msg");
                                            String strOptString2 = jSONObject2.optString(com.alipay.sdk.packet.d.k);
                                            String strOptString3 = jSONObject2.optString("seq");
                                            if (iOptInt == 100) {
                                                String strA2 = v.a();
                                                String strDecode = URLDecoder.decode(v.b(strOptString2, strA2.substring(0, 16), strA2.substring(16, 32)), com.alipay.sdk.sys.a.m);
                                                if (TextUtils.isEmpty(strDecode)) {
                                                    t.b("\nmsg=" + strOptString + "\ndata=" + strOptString2 + "\nseq=" + strOptString3 + "\n");
                                                    k.this.b.a(i4, 410002, "数据异常", strOptString2, strOptString3);
                                                } else {
                                                    t.b("\nmsg=" + strOptString + "\ncontent=" + strDecode + "\nseq=" + strOptString3 + "\n");
                                                    l lVar = k.this.b;
                                                    try {
                                                        if (lVar.a != null) {
                                                            JSONObject jSONObject3 = new JSONObject();
                                                            jSONObject3.put("resultCode", 100);
                                                            jSONObject3.put("resultMsg", strOptString);
                                                            jSONObject3.put("seq", strOptString3);
                                                            if (TextUtils.isEmpty(strDecode)) {
                                                                jSONObject3.put("resultData", "");
                                                            } else {
                                                                try {
                                                                    jSONObject3.put("resultData", new JSONObject(strDecode));
                                                                } catch (JSONException unused2) {
                                                                    jSONObject3.put("resultData", strDecode);
                                                                }
                                                            }
                                                            lVar.a.onResult(jSONObject3.toString());
                                                            lVar.a = null;
                                                        }
                                                    } catch (Exception unused3) {
                                                        t.b();
                                                    }
                                                }
                                            } else {
                                                if (iOptInt != -2 || TextUtils.isEmpty(u.f())) {
                                                    str3 = strOptString;
                                                } else {
                                                    str3 = strOptString + "apn is " + u.f();
                                                }
                                                t.b("\nmsg=" + str3 + "\ndata=" + strOptString2 + "\nseq=" + strOptString3 + "\n");
                                                k.this.b.a(i4, iOptInt, str3, strOptString2, strOptString3);
                                            }
                                        } catch (Exception e) {
                                            t.b("\nresponse=" + str2 + "\n");
                                            k.this.b.a(i4, 410002, "异常" + e.getMessage(), str2, "");
                                        }
                                    } else {
                                        t.b("\nresponse=" + str2 + "\n");
                                        k.this.b.a(i4, i5, str2, "", "seqAndroidEmpty");
                                    }
                                    k.this.b = null;
                                    k.a(k.this);
                                }
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    };
                    t.c("\n■★■★■★■★■★■★■★■★■★■\nrequestPreCheck()\n■★■★■★■★■★■★■★■★■★■\n");
                    try {
                        int iB = v.b(context.getApplicationContext());
                        u.c(iB);
                        t.c("-1=NULL; 0=流量; 1=双开; 2=WIFI; networkType = ".concat(String.valueOf(iB)));
                        if (iB != 1) {
                            if (iB == 0) {
                                kVar.a(context, i22, null, anonymousClass2);
                                return;
                            } else {
                                anonymousClass2.a(i22, 410004, "数据网络未开启");
                                return;
                            }
                        }
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        q qVarA = q.a();
                        k.AnonymousClass3 anonymousClass3 = new q.a() { // from class: com.mobile.auth.y.k.3
                            final /* synthetic */ long a;
                            final /* synthetic */ Context b;
                            final /* synthetic */ int c;
                            final /* synthetic */ m d;

                            public AnonymousClass3(long jCurrentTimeMillis2, Context context2, final int i22, m anonymousClass22) {
                                j = jCurrentTimeMillis2;
                                context = context2;
                                i = i22;
                                mVar = anonymousClass22;
                            }

                            @Override // com.mobile.auth.y.q.a
                            public final void a(boolean z2, Object obj) {
                                try {
                                    if (!z2) {
                                        mVar.a(i, 410003, "无法切换至数据网络");
                                    } else {
                                        t.c("selectDataChannel:".concat(String.valueOf(System.currentTimeMillis() - j)));
                                        k.this.a(context, i, obj, mVar);
                                    }
                                } catch (Throwable th) {
                                    ExceptionProcessor.processException(th);
                                }
                            }
                        };
                        if (Build.VERSION.SDK_INT >= 21) {
                            qVarA.a(context2, anonymousClass3);
                        } else {
                            anonymousClass3.a(true, null);
                        }
                    } catch (Exception e) {
                        t.b();
                        anonymousClass22.a(i22, 410005, "网络判断异常" + e.getMessage());
                    }
                } catch (Exception unused2) {
                    t.b();
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private String getHostName() {
        try {
            e.a();
            return e.d();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static UniAccountHelper getInstance() {
        try {
            if (s_instance == null) {
                synchronized (UniAccountHelper.class) {
                    if (s_instance == null) {
                        s_instance = new UniAccountHelper();
                    }
                }
            }
            return s_instance;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private String getSdkVersion() {
        try {
            e.a();
            return e.b();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private boolean getUseCacheFlag() {
        try {
            return p.a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    private void initFail(int i, ResultListener resultListener, String str) {
        try {
            a.a("type:" + i + "\nmsg:" + str);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultCode", 410021);
                jSONObject.put("resultMsg", str);
                jSONObject.put("resultData", "");
                jSONObject.put("seq", "");
                if (resultListener != null) {
                    resultListener.onResult(jSONObject.toString());
                }
            } catch (Exception unused) {
                a.a();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private UniAccountHelper initHostName(String str) {
        try {
            e.a();
            if (e.b(str)) {
                return s_instance;
            }
            a.a("初始化参数错误");
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private UniAccountHelper setUseCacheFlag(boolean z) {
        try {
            p.a = z;
            return s_instance;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public String cuDebugInfo(String str) {
        try {
            return this.mContext == null ? "sdk 未初始化, context 为空" : e.a().a(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public void cuGetToken(int i, ResultListener resultListener) {
        try {
            cuPreGetToken(i, 2, "cuPreGetToken", resultListener);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public UniAccountHelper init(Context context, String str) {
        try {
            return init(context, str, false);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006e A[Catch: Exception -> 0x0074, Throwable -> 0x007f, TRY_LEAVE, TryCatch #1 {Exception -> 0x0074, blocks: (B:12:0x0028, B:14:0x002e, B:17:0x0035, B:18:0x006e), top: B:31:0x0028, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UniAccountHelper init(Context context, String str, boolean z) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str)) {
                    this.mContext = context.getApplicationContext();
                    e eVarA = e.a();
                    v.b();
                    v.c("cuPreGetToken");
                    v.c();
                    if (context != null) {
                        try {
                            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str)) {
                                t.e("初始化参数不能为空");
                            } else {
                                p.a = false;
                                eVarA.a = context.getApplicationContext();
                                u.b(str);
                                u.c(str);
                                u.f(v.c(eVarA.a));
                                u.e();
                                t.c("backupIp=" + u.a);
                                e.b("ali.wosms.cn");
                                e.f();
                                e.c();
                            }
                        } catch (Exception unused) {
                            t.b();
                        }
                    }
                    b.a = str;
                    setLogEnable(false);
                    return s_instance;
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        a.a("初始化参数不能为空");
        return null;
    }

    public void releaseNetwork() {
        try {
            e.a();
            e.g();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public UniAccountHelper setApiKey(Context context, String str) {
        try {
            return setAppid(context, str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public UniAccountHelper setAppid(Context context, String str) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    this.mContext = context.getApplicationContext();
                    e eVarA = e.a();
                    try {
                        eVarA.a = context.getApplicationContext();
                        u.b(str);
                        u.f(v.c(eVarA.a));
                    } catch (Exception unused) {
                        t.b();
                    }
                    b.a = str;
                    return s_instance;
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        a.a("初始化参数不能为空");
        return null;
    }

    public UniAccountHelper setCertFingerType(String str) {
        try {
            if (!str.equalsIgnoreCase("MD5") && !str.equalsIgnoreCase("SHA1") && !str.equalsIgnoreCase("SHA256") && !str.equalsIgnoreCase("sm3")) {
                return null;
            }
            p.b = str.toLowerCase();
            return s_instance;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public void setLogEnable(boolean z) {
        try {
            a.a(z);
            e.a();
            e.a(z);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
