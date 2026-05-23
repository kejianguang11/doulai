package com.mobile.auth.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.l.g;
import com.mobile.auth.l.h;
import com.mobile.auth.l.j;
import com.mobile.auth.l.k;
import com.mobile.auth.l.m;
import com.mobile.auth.l.n;
import com.mobile.auth.l.o;
import com.mobile.auth.l.q;
import com.mobile.auth.l.r;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e {

    @SuppressLint({"StaticFieldLeak"})
    private static e f;
    protected final c a;
    protected final Context b;
    protected final Handler d;
    protected String e;
    protected long c = 8000;
    private final Object g = new Object();

    protected class a implements Runnable {
        private final com.cmic.sso.sdk.a b;

        a(com.cmic.sso.sdk.a aVar) {
            this.b = aVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            String str2;
            if (r.a(e.this.b).a() || !this.b.b("doNetworkSwitch", false)) {
                str = "200023";
                str2 = "登录超时";
            } else {
                str = "102508";
                str2 = "数据网络切换失败";
            }
            JSONObject jSONObjectA = f.a(str, str2);
            e.this.a(jSONObjectA.optString("resultCode", "200023"), jSONObjectA.optString("resultString", "登录超时"), this.b, jSONObjectA);
        }
    }

    e(Context context) {
        this.b = context.getApplicationContext();
        this.d = new Handler(this.b.getMainLooper());
        this.a = c.a(this.b);
        r.a(this.b);
        k.a(this.b);
        j.a(this.b);
        n.a(new n.a() { // from class: com.mobile.auth.e.e.1
            @Override // com.mobile.auth.l.n.a
            protected void a() {
                String str;
                String str2;
                String strB = k.b("AID", "");
                com.mobile.auth.l.c.b("AuthnHelperCore", "aid = " + strB);
                if (TextUtils.isEmpty(strB)) {
                    e.this.a();
                }
                if (com.mobile.auth.l.b.a(e.this.b, true)) {
                    str = "AuthnHelperCore";
                    str2 = "生成androidkeystore成功";
                } else {
                    str = "AuthnHelperCore";
                    str2 = "生成androidkeystore失败";
                }
                com.mobile.auth.l.c.b(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        String str = "%" + q.b();
        com.mobile.auth.l.c.b("AuthnHelperCore", "generate aid = " + str);
        k.a("AID", str);
    }

    private void a(final Context context, final String str, final com.cmic.sso.sdk.a aVar) {
        n.a(new n.a() { // from class: com.mobile.auth.e.e.5
            @Override // com.mobile.auth.l.n.a
            protected void a() {
                if ("200023".equals(str)) {
                    SystemClock.sleep(8000L);
                }
                new com.cmic.sso.sdk.d.d().a(context, str, aVar);
            }
        });
    }

    public static void a(boolean z) {
        com.mobile.auth.l.c.a(z);
    }

    public static e b(Context context) {
        if (f == null) {
            synchronized (e.class) {
                if (f == null) {
                    f = new e(context);
                }
            }
        }
        return f;
    }

    protected com.cmic.sso.sdk.a a(b bVar) {
        com.cmic.sso.sdk.a aVar = new com.cmic.sso.sdk.a(64);
        String strC = q.c();
        aVar.a(new com.cmic.sso.sdk.d.b());
        aVar.a("traceId", strC);
        com.mobile.auth.l.c.a("traceId", strC);
        if (bVar != null) {
            com.mobile.auth.l.e.a(strC, bVar);
        }
        return aVar;
    }

    public void a(long j) {
        this.c = j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(com.cmic.sso.sdk.a aVar) {
        final a aVar2 = new a(aVar);
        this.d.postDelayed(aVar2, this.c);
        this.a.a(aVar, new d() { // from class: com.mobile.auth.e.e.3
            @Override // com.mobile.auth.e.d
            public void a(String str, String str2, com.cmic.sso.sdk.a aVar3, JSONObject jSONObject) {
                e.this.d.removeCallbacks(aVar2);
                e.this.a(str, str2, aVar3, jSONObject);
            }
        });
    }

    public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        try {
            String strB = aVar.b("traceId");
            final int iB = aVar.b("SDKRequestCode", -1);
            if (com.mobile.auth.l.e.a(strB)) {
                return;
            }
            synchronized (this) {
                final b bVarC = com.mobile.auth.l.e.c(strB);
                if (jSONObject == null || !jSONObject.optBoolean("keepListener", false)) {
                    com.mobile.auth.l.e.b(strB);
                }
                if (bVarC == null) {
                    return;
                }
                aVar.a("systemEndTime", SystemClock.elapsedRealtime());
                aVar.a("endtime", o.a());
                int iC = aVar.c("logintype");
                if (jSONObject == null) {
                    jSONObject = f.a(str, str2);
                }
                final JSONObject jSONObjectA = iC == 3 ? f.a(str, aVar, jSONObject) : f.a(str, str2, aVar, jSONObject);
                jSONObjectA.put("scripExpiresIn", String.valueOf(h.a()));
                this.d.post(new Runnable() { // from class: com.mobile.auth.e.e.4
                    @Override // java.lang.Runnable
                    public void run() {
                        bVarC.a(iB, jSONObjectA);
                    }
                });
                com.mobile.auth.d.c.a(this.b).a(aVar);
                if (!aVar.b().j() && !q.a(aVar.b())) {
                    a(this.b, str, aVar);
                }
                if (com.mobile.auth.l.e.a()) {
                    r.a(this.b).b();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(final String str, final String str2, final b bVar) {
        final com.cmic.sso.sdk.a aVarA = a(bVar);
        n.a(new n.a(this.b, aVarA) { // from class: com.mobile.auth.e.e.2
            @Override // com.mobile.auth.l.n.a
            protected void a() {
                if (e.this.a(aVarA, str, str2, "mobileAuth", 0, bVar)) {
                    e.this.a(aVarA);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean a(com.cmic.sso.sdk.a aVar, String str, String str2, String str3, int i, b bVar) throws Throwable {
        boolean zA;
        String str4;
        String str5;
        com.mobile.auth.d.a aVarA = com.mobile.auth.d.c.a(this.b).a();
        aVar.a(aVarA);
        aVar.a("use2048PublicKey", "rsa2048".equals(this.e));
        aVar.a("systemStartTime", SystemClock.elapsedRealtime());
        aVar.a("starttime", o.a());
        aVar.a("loginMethod", str3);
        aVar.a(com.alipay.sdk.sys.a.f, str2);
        aVar.a("appid", str);
        aVar.a("timeOut", String.valueOf(this.c));
        boolean zA2 = g.a(this.b, "android.permission.READ_PHONE_STATE");
        com.mobile.auth.l.c.a("AuthnHelperCore", "有READ_PHONE_STATE权限？" + zA2);
        aVar.a("hsaReadPhoneStatePermission", zA2);
        boolean zA3 = m.a(this.b);
        com.mobile.auth.f.a.a().a(this.b, zA2, zA3);
        aVar.a("networkClass", com.mobile.auth.f.a.a().a(this.b));
        String strB = j.a().b();
        String strC = j.a().c();
        String strA = j.a().a(strC);
        aVar.a("operator", strC);
        aVar.a("operatortype", strA);
        aVar.a("logintype", i);
        com.mobile.auth.l.c.b("AuthnHelperCore", "subId = " + strB);
        if (!TextUtils.isEmpty(strB)) {
            com.mobile.auth.l.c.a("AuthnHelperCore", "使用subId作为缓存key = " + strB);
            aVar.a("scripType", "subid");
            aVar.a("scripKey", strB);
        } else if (!TextUtils.isEmpty(strC)) {
            com.mobile.auth.l.c.a("AuthnHelperCore", "使用operator作为缓存key = " + strC);
            aVar.a("scripType", "operator");
            aVar.a("scripKey", strC);
        }
        int iA = m.a(this.b, zA3);
        aVar.a("networktype", iA);
        if (!zA3) {
            aVar.a("authType", String.valueOf(0));
            str4 = "200010";
            str5 = "无法识别sim卡或没有sim卡";
        } else if (bVar == null) {
            str4 = "102203";
            str5 = "listener不能为空";
        } else if (!aVarA.g()) {
            if (TextUtils.isEmpty(str == null ? "" : str.trim())) {
                str4 = "102203";
                str5 = "appId 不能为空";
            } else {
                if (TextUtils.isEmpty(str2 == null ? "" : str2.trim())) {
                    str4 = "102203";
                    str5 = "appkey不能为空";
                } else if (iA == 0) {
                    str4 = "102101";
                    str5 = "未检测到网络";
                } else if ((com.igexin.push.config.c.H.equals(strA) && aVarA.f()) || (AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM.equals(strA) && aVarA.e())) {
                    str4 = "200082";
                    str5 = "服务器繁忙，请稍后重试";
                } else {
                    synchronized (this.g) {
                        zA = h.a(aVar);
                        if (zA) {
                            aVar.a("securityphone", k.b("securityphone", ""));
                            if (3 != i) {
                                String strA2 = h.a(this.b);
                                StringBuilder sb = new StringBuilder();
                                sb.append("解密phoneScript ");
                                sb.append(!TextUtils.isEmpty(strA2));
                                com.mobile.auth.l.c.b("AuthnHelperCore", sb.toString());
                                if (TextUtils.isEmpty(strA2)) {
                                    zA = false;
                                } else {
                                    aVar.a("phonescrip", strA2);
                                }
                                h.a(true, false);
                            }
                        }
                        aVar.a("isCacheScrip", zA);
                        com.mobile.auth.l.c.b("AuthnHelperCore", "isCachePhoneScrip = " + zA);
                    }
                    if (iA != 2 || zA) {
                        return true;
                    }
                    str4 = "102103";
                    str5 = "无数据网络";
                }
            }
        }
        a(str4, str5, aVar, null);
        return false;
    }

    public JSONObject c(Context context) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                boolean zA = m.a(this.b);
                com.mobile.auth.f.a.a().a(context, g.a(context, "android.permission.READ_PHONE_STATE"), zA);
                String strA = j.a().a((String) null);
                int iA = m.a(context, zA);
                jSONObject.put("operatortype", strA);
                jSONObject.put("networktype", iA + "");
                com.mobile.auth.l.c.b("AuthnHelperCore", "网络类型: " + iA);
                com.mobile.auth.l.c.b("AuthnHelperCore", "运营商类型: " + strA);
                return jSONObject;
            } catch (Exception unused) {
                jSONObject.put("errorDes", "发生未知错误");
                return jSONObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public void d() {
        try {
            h.a(true, true);
            com.mobile.auth.l.c.b("AuthnHelperCore", "删除scrip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
