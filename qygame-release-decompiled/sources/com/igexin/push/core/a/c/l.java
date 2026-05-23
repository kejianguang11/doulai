package com.igexin.push.core.a.c;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.igexin.push.core.b.s;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import com.mobile.auth.gatewayauth.Constant;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class l implements PushMessageInterface {
    private static final String a = com.igexin.push.config.c.a;

    public static void a(s sVar, String str) {
        int iIndexOf;
        String str2 = sVar.a;
        if (str2 == null || (iIndexOf = str2.indexOf(str)) == -1) {
            return;
        }
        String strSubstring = "";
        String strSubstring2 = null;
        int iIndexOf2 = str2.indexOf(com.alipay.sdk.sys.a.b);
        if (iIndexOf2 == -1) {
            strSubstring = str2.substring(0, iIndexOf - 1);
            String strSubstring3 = str2.substring(iIndexOf);
            if (strSubstring3.contains("=")) {
                strSubstring2 = strSubstring3.substring(strSubstring3.indexOf("=") + 1);
            }
        } else {
            int i = iIndexOf - 1;
            if (str2.charAt(i) == '?') {
                strSubstring = str2.substring(0, iIndexOf) + str2.substring(iIndexOf2 + 1);
                String strSubstring4 = str2.substring(iIndexOf, iIndexOf2);
                if (strSubstring4.contains("=")) {
                    strSubstring2 = strSubstring4.substring(strSubstring4.indexOf("=") + 1);
                }
            } else if (str2.charAt(i) == '&') {
                String strSubstring5 = str2.substring(0, i);
                String strSubstring6 = str2.substring(iIndexOf);
                String strSubstring7 = "";
                int iIndexOf3 = strSubstring6.indexOf(com.alipay.sdk.sys.a.b);
                if (iIndexOf3 != -1) {
                    strSubstring7 = strSubstring6.substring(iIndexOf3);
                    strSubstring6 = strSubstring6.substring(0, iIndexOf3);
                }
                strSubstring2 = strSubstring6.substring(strSubstring6.indexOf("=") + 1);
                strSubstring = strSubstring5 + strSubstring7;
            }
        }
        sVar.a = strSubstring;
        sVar.d = strSubstring2;
    }

    private static void a(String str, Context context) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            s sVar = new s();
            sVar.a = str;
            a(sVar, com.igexin.push.core.b.A);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setFlags(268435456);
            intent.setPackage(sVar.d);
            intent.setData(Uri.parse(sVar.a()));
            context.startActivity(intent);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        s sVar = (s) baseActionBean;
        a(sVar, com.igexin.push.core.b.A);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setFlags(268435456);
        intent.setPackage(sVar.d);
        intent.setData(Uri.parse(sVar.a()));
        try {
            com.igexin.push.core.e.l.startActivity(intent);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
        sVar.a();
        if (baseActionBean.getDoActionId().equals("")) {
            return true;
        }
        com.igexin.push.core.a.b.d();
        com.igexin.push.core.a.b.a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), baseActionBean.getDoActionId());
        return true;
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final BaseActionBean parseAction(JSONObject jSONObject) {
        try {
            if (!jSONObject.has(Constant.PROTOCOL_WEB_VIEW_URL) || !jSONObject.has("do") || !jSONObject.has("actionid")) {
                return null;
            }
            String string = jSONObject.getString(Constant.PROTOCOL_WEB_VIEW_URL);
            if (string.equals("")) {
                return null;
            }
            s sVar = new s();
            sVar.setType(com.igexin.push.core.b.u);
            sVar.setActionId(jSONObject.getString("actionid"));
            sVar.setDoActionId(jSONObject.getString("do"));
            sVar.a = string;
            if (jSONObject.has("is_withcid") && "true".equals(jSONObject.getString("is_withcid"))) {
                sVar.b = true;
            }
            if (jSONObject.has("is_withnettype") && "true".equals(jSONObject.getString("is_withnettype"))) {
                sVar.c = true;
            }
            return sVar;
        } catch (JSONException e) {
            com.igexin.c.a.c.a.a(e);
            return null;
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final PushMessageInterface.ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        return PushMessageInterface.ActionPrepareState.success;
    }
}
