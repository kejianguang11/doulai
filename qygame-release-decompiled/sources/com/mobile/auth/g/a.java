package com.mobile.auth.g;

import android.text.TextUtils;
import android.util.Log;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.i.d;
import com.mobile.auth.i.g;
import com.mobile.auth.j.c;
import com.mobile.auth.l.q;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private String a;
    private String b;

    private c a(String str, String str2, String str3, g gVar) {
        c cVar = new c(str, gVar, str3, str2);
        if (str3.equals("GET")) {
            cVar.a("Content-Type", "application/x-www-form-urlencoded");
        }
        return cVar;
    }

    public c a(c cVar, com.mobile.auth.k.b bVar, com.cmic.sso.sdk.a aVar) {
        List<String> list;
        Map<String, List<String>> mapB = bVar.b();
        if (TextUtils.isEmpty(this.a) && (list = mapB.get("pplocation")) != null && list.size() > 0) {
            this.a = list.get(0);
        }
        q.b(aVar, String.valueOf(bVar.a()));
        List<String> list2 = mapB.get("Location");
        if (list2 == null || list2.isEmpty()) {
            list2 = mapB.get("Location".toLowerCase());
        }
        if (list2 != null && list2.size() > 0) {
            this.b = list2.get(0);
            if (!TextUtils.isEmpty(this.b)) {
                String strB = aVar.b("operatortype", "0");
                q.a(aVar, com.igexin.push.config.c.H.equals(strB) ? "getUnicomMobile" : AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM.equals(strB) ? "getTelecomMobile" : "NONE");
            }
        }
        Log.d("Location", this.b);
        c cVarA = a(this.b, cVar.f(), "GET", new com.mobile.auth.i.c(cVar.k().a()));
        cVarA.a(cVar.h());
        return cVarA;
    }

    public String a() {
        return this.a;
    }

    public c b(c cVar, com.mobile.auth.k.b bVar, com.cmic.sso.sdk.a aVar) {
        String strB = aVar.b("operatortype", "0");
        q.a(aVar, com.igexin.push.config.c.H.equals(strB) ? "getNewUnicomPhoneNumberNotify" : AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM.equals(strB) ? "getNewTelecomPhoneNumberNotify" : "NONE");
        q.b(aVar, String.valueOf(bVar.a()));
        d dVar = new d(cVar.k().a(), "1.0", bVar.c());
        dVar.c(aVar.b("userCapaid"));
        dVar.b(aVar.c("logintype") != 3 ? "authz" : "pre");
        c cVarA = a(this.a, cVar.f(), "POST", dVar);
        cVarA.a(cVar.h());
        this.a = null;
        return cVarA;
    }
}
