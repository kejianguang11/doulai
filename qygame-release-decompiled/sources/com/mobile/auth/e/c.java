package com.mobile.auth.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.cmic.sso.sdk.b;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.g.s;
import com.mobile.auth.l.h;
import com.mobile.auth.l.k;
import com.mobile.auth.l.l;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {

    @SuppressLint({"StaticFieldLeak"})
    private static c c;
    private final com.mobile.auth.j.a a = com.mobile.auth.j.a.a();
    private final Context b;

    private c(Context context) {
        this.b = context.getApplicationContext();
    }

    public static c a(Context context) {
        if (c == null) {
            synchronized (c.class) {
                if (c == null) {
                    c = new c(context);
                }
            }
        }
        return c;
    }

    private void a(com.cmic.sso.sdk.a aVar) {
        String packageName = this.b.getPackageName();
        String strA = com.mobile.auth.l.d.a(l.a(this.b, packageName));
        aVar.a("apppackage", packageName);
        aVar.a("appsign", strA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(com.cmic.sso.sdk.a aVar, d dVar, String str, String str2, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String strOptString;
        String strOptString2;
        String str3 = str2;
        JSONObject jSONObjectB = jSONObject;
        if (!"103000".equals(str)) {
            if (aVar.c("logintype") == 3) {
                jSONObjectB = f.b(str, str2);
            }
            dVar.a(str, str3, aVar, jSONObjectB);
        }
        String strOptString3 = jSONObjectB.optString("resultdata");
        String string = TextUtils.isEmpty(strOptString3) ? jSONObject.toString() : com.mobile.auth.l.a.b(aVar.a(b.a.a), strOptString3, aVar.a(b.a.b));
        String strOptString4 = null;
        try {
            jSONObject2 = new JSONObject(string);
            try {
                strOptString = jSONObject2.optString("phonescrip");
                try {
                    strOptString2 = jSONObject2.optString("securityphone");
                    try {
                        String strOptString5 = jSONObject2.optString("openId");
                        try {
                            strOptString4 = TextUtils.isEmpty(strOptString5) ? jSONObject2.optString("pcid") : strOptString5;
                            k.a("securityphone", strOptString2);
                        } catch (JSONException e) {
                            e = e;
                            strOptString4 = strOptString5;
                            e.printStackTrace();
                        }
                    } catch (JSONException e2) {
                        e = e2;
                    }
                } catch (JSONException e3) {
                    e = e3;
                    strOptString2 = null;
                }
            } catch (JSONException e4) {
                e = e4;
                strOptString = null;
                strOptString2 = strOptString;
                e.printStackTrace();
                String str4 = strOptString;
                com.mobile.auth.l.c.b("AuthnBusiness", "securityPhone  = " + strOptString2);
                aVar.a("openId", strOptString4);
                aVar.a("phonescrip", str4);
                aVar.a("securityphone", strOptString2);
                if (jSONObject2 != null) {
                }
            }
        } catch (JSONException e5) {
            e = e5;
            jSONObject2 = null;
            strOptString = null;
        }
        String str42 = strOptString;
        com.mobile.auth.l.c.b("AuthnBusiness", "securityPhone  = " + strOptString2);
        aVar.a("openId", strOptString4);
        aVar.a("phonescrip", str42);
        aVar.a("securityphone", strOptString2);
        if (jSONObject2 != null) {
            com.mobile.auth.l.c.a("AuthnBusiness", "返回103000，但是数据解析出错");
            dVar.a(String.valueOf(102223), "数据解析异常", aVar, f.a(String.valueOf(102223), "数据解析异常"));
            return;
        } else {
            h.a(this.b, str42, Long.parseLong(jSONObject2.optString("scripExpiresIn", "0")), aVar.b("scripKey", ""), aVar.b("scripType", ""));
            if (aVar.c("logintype") != 3) {
                dVar.a(str, str3, aVar, jSONObject2);
                return;
            }
            jSONObjectB = f.a(strOptString2);
        }
        str3 = "true";
        dVar.a(str, str3, aVar, jSONObjectB);
    }

    private void b(com.cmic.sso.sdk.a aVar) throws UnsupportedEncodingException {
        byte[] bytes = new byte[0];
        if (aVar.b("use2048PublicKey", false)) {
            com.mobile.auth.l.c.a("AuthnBusiness", "使用2048公钥对应的对称秘钥生成方式");
            bytes = com.mobile.auth.l.a.a();
        } else {
            com.mobile.auth.l.c.a("AuthnBusiness", "使用1024公钥对应的对称秘钥生成方式");
            try {
                bytes = UUID.randomUUID().toString().substring(0, 16).getBytes(s.b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        byte[] bArrA = com.mobile.auth.l.a.a();
        aVar.a(b.a.a, bytes);
        aVar.a(b.a.b, bArrA);
        aVar.a("authType", AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM);
    }

    void a(com.cmic.sso.sdk.a aVar, d dVar) {
        com.mobile.auth.l.c.b("AuthnBusiness", "LoginCheck method start");
        int iC = aVar.c("logintype");
        if (!aVar.b("isCacheScrip", false)) {
            b(aVar, dVar);
            return;
        }
        String strB = aVar.b("securityphone", "");
        if (iC == 3) {
            dVar.a("103000", "true", aVar, f.a(strB));
        } else {
            b(aVar, dVar);
        }
    }

    public void b(final com.cmic.sso.sdk.a aVar, final d dVar) throws UnsupportedEncodingException {
        String str;
        String str2;
        com.mobile.auth.l.c.b("AuthnBusiness", "getScripAndToken start");
        a(aVar);
        if (!aVar.b("isCacheScrip", false)) {
            b(aVar);
        }
        if (aVar.c("logintype") != 1) {
            if (aVar.c("logintype") == 0) {
                str = "userCapaid";
                str2 = "50";
            }
            this.a.a(aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.e.c.1
                @Override // com.mobile.auth.j.d
                public void a(String str3, String str4, JSONObject jSONObject) {
                    c.this.a(aVar, dVar, str3, str4, jSONObject);
                }
            });
        }
        str = "userCapaid";
        str2 = "200";
        aVar.a(str, str2);
        this.a.a(aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.e.c.1
            @Override // com.mobile.auth.j.d
            public void a(String str3, String str4, JSONObject jSONObject) {
                c.this.a(aVar, dVar, str3, str4, jSONObject);
            }
        });
    }
}
