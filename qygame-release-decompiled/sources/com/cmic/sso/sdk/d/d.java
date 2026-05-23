package com.cmic.sso.sdk.d;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.l.f;
import com.mobile.auth.l.k;
import com.mobile.auth.l.m;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private com.cmic.sso.sdk.a a;

    private static void a(b bVar, com.cmic.sso.sdk.a aVar) {
        if (bVar == null || aVar == null) {
            return;
        }
        bVar.b(aVar.b("appid", ""));
        bVar.f(m.a());
        bVar.i(aVar.b("interfaceType", ""));
        bVar.h(aVar.b("interfaceCode", ""));
        bVar.g(aVar.b("interfaceElasped", ""));
        bVar.l(aVar.b("timeOut"));
        bVar.s(aVar.b("traceId"));
        bVar.v(aVar.b("networkClass"));
        bVar.n(aVar.b("simCardNum"));
        bVar.o(aVar.b("operatortype"));
        bVar.p(m.b());
        bVar.q(m.c());
        bVar.y(String.valueOf(aVar.b("networktype", 0)));
        bVar.t(aVar.b("starttime"));
        bVar.w(aVar.b("endtime"));
        bVar.m(String.valueOf(aVar.b("systemEndTime", 0L) - aVar.b("systemStartTime", 0L)));
        bVar.d(aVar.b("imsiState"));
        bVar.z(k.b("AID", ""));
        bVar.A(aVar.b("operatortype"));
        bVar.B(aVar.b("scripType"));
        com.mobile.auth.l.c.a("SendLog", "traceId" + aVar.b("traceId"));
    }

    private void a(JSONObject jSONObject) {
        com.mobile.auth.j.a.a().a(jSONObject, this.a, new com.mobile.auth.j.d() { // from class: com.cmic.sso.sdk.d.d.1
            @Override // com.mobile.auth.j.d
            public void a(String str, String str2, JSONObject jSONObject2) {
                String str3;
                long jCurrentTimeMillis;
                Object objValueOf;
                com.mobile.auth.d.a aVarB = d.this.a.b();
                HashMap map = new HashMap();
                if (!str.equals("103000")) {
                    if (aVarB.l() != 0 && aVarB.k() != 0) {
                        int iA = k.a("logFailTimes", 0) + 1;
                        if (iA >= aVarB.k()) {
                            map.put("logFailTimes", 0);
                            str3 = "logCloseTime";
                            jCurrentTimeMillis = System.currentTimeMillis();
                        } else {
                            str3 = "logFailTimes";
                            objValueOf = Integer.valueOf(iA);
                            map.put(str3, objValueOf);
                        }
                    }
                    k.a(map);
                }
                map.put("logFailTimes", 0);
                str3 = "logCloseTime";
                jCurrentTimeMillis = 0;
                objValueOf = Long.valueOf(jCurrentTimeMillis);
                map.put(str3, objValueOf);
                k.a(map);
            }
        });
    }

    public void a(Context context, String str, com.cmic.sso.sdk.a aVar) {
        try {
            b bVarA = aVar.a();
            String strB = f.b(context);
            bVarA.e(str);
            bVarA.x(aVar.b("loginMethod", ""));
            bVarA.r(aVar.b("isCacheScrip", false) ? "scrip" : "pgw");
            bVarA.j(f.a(context));
            if (TextUtils.isEmpty(strB)) {
                strB = "";
            }
            bVarA.k(strB);
            bVarA.c(aVar.b("hsaReadPhoneStatePermission", false) ? "1" : "0");
            a(bVarA, aVar);
            JSONArray jSONArray = null;
            if (bVarA.a.size() > 0) {
                jSONArray = new JSONArray();
                for (Throwable th : bVarA.a) {
                    StringBuffer stringBuffer = new StringBuffer();
                    JSONObject jSONObject = new JSONObject();
                    for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                        stringBuffer.append("\n");
                        stringBuffer.append(stackTraceElement.toString());
                    }
                    jSONObject.put(com.igexin.push.core.b.Z, th.toString());
                    jSONObject.put("stack", stringBuffer.toString());
                    jSONArray.put(jSONObject);
                }
                bVarA.a.clear();
            }
            if (jSONArray != null && jSONArray.length() > 0) {
                bVarA.a(jSONArray);
            }
            com.mobile.auth.l.c.a("SendLog", "登录日志");
            a(bVarA.b(), aVar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void a(JSONObject jSONObject, com.cmic.sso.sdk.a aVar) {
        this.a = aVar;
        a(jSONObject);
    }
}
