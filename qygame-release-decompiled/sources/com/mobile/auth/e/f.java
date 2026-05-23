package com.mobile.auth.e;

import android.text.TextUtils;
import android.util.Log;
import com.igexin.assist.sdk.AssistPushConsts;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f {
    static JSONObject a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", "103000");
            jSONObject.put("desc", "true");
            jSONObject.put("securityphone", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static JSONObject a(String str, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        String str2;
        String str3;
        String[] strArr = {"未知", "移动", "联通", "电信"};
        try {
            String strB = aVar.b("operatortype", "0");
            if (!"0".equals(strB) && !TextUtils.isEmpty(strB)) {
                str2 = "operatorType";
                str3 = strArr[Integer.parseInt(strB)];
            } else if ("103000".equals(str)) {
                str2 = "operatorType";
                str3 = strArr[1];
            } else {
                str2 = "operatorType";
                str3 = strArr[0];
            }
            jSONObject.put(str2, str3);
        } catch (Exception e) {
            Log.e("AuthnResult", "JSONException", e);
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (Exception e) {
            Log.e("AuthnResult", "Exception", e);
        }
        return jSONObject;
    }

    static JSONObject a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        String str3;
        String str4;
        String str5;
        JSONObject jSONObject2 = new JSONObject();
        try {
            int i = Integer.parseInt(aVar.b("authType", "0"));
            int iC = aVar.c("networktype");
            if (i != 3) {
                str3 = "0";
                str4 = "其他";
            } else if (iC == 3) {
                str4 = "WIFI下网关鉴权";
                str3 = "1";
            } else {
                str4 = "网关鉴权";
                str3 = com.igexin.push.config.c.H;
            }
            jSONObject2.put("resultCode", str);
            jSONObject2.put("authType", str3);
            jSONObject2.put("authTypeDes", str4);
            if ("103000".equals(str)) {
                if (1 == aVar.c("logintype")) {
                    jSONObject2.put("openId", aVar.b("openId"));
                    jSONObject2.put("securityphone", aVar.b("securityphone"));
                }
                jSONObject2.put(AssistPushConsts.MSG_TYPE_TOKEN, jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN));
                str5 = "tokenExpiresIn";
                str2 = jSONObject.optString("tokenExpiresIn");
            } else {
                str5 = "desc";
            }
            jSONObject2.put(str5, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        com.mobile.auth.l.c.b("AuthnResult", "返回参数:" + jSONObject2.toString());
        return jSONObject2;
    }

    static JSONObject b(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
