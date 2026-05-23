package com.mobile.auth.d;

import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.loc.al;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.d.a;
import com.mobile.auth.l.k;
import com.mobile.auth.l.n;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static b c;
    private com.mobile.auth.d.a a;
    private a e;
    private volatile boolean d = false;
    private final com.mobile.auth.d.a b = new a.C0041a().a();

    interface a {
        void a(com.mobile.auth.d.a aVar);
    }

    private b(boolean z) {
        this.a = !z ? d() : this.b;
    }

    public static b a(boolean z) {
        if (c == null) {
            synchronized (b.class) {
                if (c == null) {
                    c = new b(z);
                }
            }
        }
        return c;
    }

    private String a(String str, String str2) {
        String str3 = "";
        String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
        int length = strArrSplit.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str4 = strArrSplit[i];
            if (str4.contains(str2)) {
                str3 = str4;
                break;
            }
            i++;
        }
        return !TextUtils.isEmpty(str3) ? str3.substring(str3.lastIndexOf("=") + 1) : str3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONObject jSONObject) {
        k.a aVarB = k.b("sso_config_xf");
        try {
            if (jSONObject.has("client_valid")) {
                aVarB.a("client_valid", System.currentTimeMillis() + (((long) Integer.parseInt(jSONObject.getString("client_valid"))) * 60 * 60 * 1000));
            }
            if (jSONObject.has("Configlist")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("Configlist");
                if (jSONObject2.has("CHANGE_HOST")) {
                    String string = jSONObject2.getString("CHANGE_HOST");
                    if (string.contains("M007")) {
                        String strA = a(string, "M007");
                        if (!TextUtils.isEmpty(strA)) {
                            aVarB.a("logHost", strA);
                        }
                    }
                    if (string.contains("M008")) {
                        String strA2 = a(string, "M008");
                        if (!TextUtils.isEmpty(strA2)) {
                            aVarB.a("https_get_phone_scrip_host", strA2);
                        }
                    }
                    if (string.contains("M009")) {
                        String strA3 = a(string, "M009");
                        if (!TextUtils.isEmpty(strA3)) {
                            aVarB.a("config_host", strA3);
                        }
                    }
                } else {
                    aVarB.a("logHost");
                    aVarB.a("https_get_phone_scrip_host");
                    aVarB.a("config_host");
                }
                a(jSONObject2, "CLOSE_FRIEND_WAPKS", "0", aVarB);
                a(jSONObject2, "CLOSE_LOGS_VERSION", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV4_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV6_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_SDKVERSION_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_APPID_LIST", "0", aVarB);
                if (jSONObject2.has("LOGS_CONTROL")) {
                    String[] strArrSplit = jSONObject2.getString("LOGS_CONTROL").replace(al.g, "").split(com.alipay.sdk.sys.a.b);
                    if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArrSplit[0]) && !TextUtils.isEmpty(strArrSplit[1])) {
                        try {
                            int i = Integer.parseInt(strArrSplit[0]);
                            int i2 = Integer.parseInt(strArrSplit[1]);
                            aVarB.a("maxFailedLogTimes", i);
                            aVarB.a("pauseTime", i2);
                        } catch (Exception unused) {
                            com.mobile.auth.l.c.a("UmcConfigHandle", "解析日志上报限制时间次数异常");
                        }
                    }
                } else {
                    aVarB.a("maxFailedLogTimes");
                    aVarB.a("pauseTime");
                }
            }
            aVarB.b();
        } catch (Exception e) {
            com.mobile.auth.l.c.a("UmcConfigHandle", "配置项异常，配置失效");
            e.printStackTrace();
        }
    }

    private void a(JSONObject jSONObject, String str, String str2, k.a aVar) {
        if (!jSONObject.has(str)) {
            aVar.a(str);
            return;
        }
        String strOptString = jSONObject.optString(str, str2);
        if ("CLOSE_FRIEND_WAPKS".equals(str)) {
            if (TextUtils.isEmpty(strOptString)) {
                return;
            }
            if (!strOptString.contains("CU") && !strOptString.contains(AssistPushConsts.MSG_KEY_CONTENT) && !strOptString.contains("CM")) {
                return;
            }
        } else if (!"0".equals(strOptString) && !"1".equals(strOptString)) {
            return;
        }
        aVar.a(str, jSONObject.optString(str, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.cmic.sso.sdk.a aVar) {
        if (this.d) {
            com.mobile.auth.l.c.a("UmcConfigHandle", "正在获取配置中...");
        } else {
            this.d = true;
            com.mobile.auth.j.a.a().a(false, aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.d.b.1
                @Override // com.mobile.auth.j.d
                public void a(String str, String str2, JSONObject jSONObject) {
                    try {
                        if ("103000".equals(str)) {
                            b.this.a(jSONObject);
                            k.a("sdk_config_version", BuildConfig.CMCC_SDK_VERSION);
                            b.this.a = b.this.d();
                            if (b.this.e != null) {
                                b.this.e.a(b.this.a);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    b.this.d = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.mobile.auth.d.a d() {
        return new a.C0041a().a(d.b(this.b.a())).c(d.a(this.b.c())).b(d.b(this.b.b())).d(d.c(this.b.d())).d(d.a(this.b.h())).e(d.b(this.b.i())).a(d.e(this.b.e())).b(d.d(this.b.f())).c(d.c(this.b.g())).f(d.f(this.b.j())).a(d.a(this.b.k())).b(d.b(this.b.l())).a();
    }

    com.mobile.auth.d.a a() {
        return this.b;
    }

    void a(final com.cmic.sso.sdk.a aVar) {
        if (d.a()) {
            n.a(new n.a() { // from class: com.mobile.auth.d.b.2
                @Override // com.mobile.auth.l.n.a
                protected void a() {
                    com.mobile.auth.l.c.b("UmcConfigHandle", "开始拉取配置..");
                    b.this.b(aVar);
                }
            });
        }
    }

    void a(a aVar) {
        this.e = aVar;
    }

    com.mobile.auth.d.a b() {
        return this.a;
    }

    void c() {
        k.a aVarB = k.b("sso_config_xf");
        aVarB.c();
        aVarB.b();
    }
}
