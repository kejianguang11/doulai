package com.igexin.push.core.b;

import android.os.Build;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushManager;
import com.igexin.push.core.ServiceManager;
import com.igexin.sdk.PushBuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f = PushBuildConfig.sdk_conf_channelid;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public long n;
    public String o;

    public a() {
        if (com.igexin.push.core.e.g != null) {
            this.f += ":" + com.igexin.push.core.e.g;
        }
        this.e = "3.3.10.0";
        this.b = com.igexin.push.core.e.E;
        this.c = com.igexin.push.core.e.D;
        this.d = com.igexin.push.g.o.c();
        this.a = com.igexin.push.core.e.F;
        this.h = "ANDROID";
        this.j = "android" + Build.VERSION.RELEASE;
        this.k = "MDP";
        this.g = com.igexin.push.core.e.H;
        this.n = System.currentTimeMillis();
        this.l = com.igexin.push.core.e.I;
        this.m = com.igexin.push.core.e.G;
        this.o = com.igexin.push.core.e.C;
        if (!com.igexin.assist.sdk.a.a().c() || AssistPushManager.checkSupportDevice(com.igexin.push.core.e.l)) {
            return;
        }
        StringBuilder sb = new StringBuilder("FCM-");
        sb.append(this.m == null ? "" : this.m);
        this.m = sb.toString();
    }

    private static String a(a aVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("model", aVar.a == null ? "" : aVar.a);
        jSONObject.put("sim", aVar.b == null ? "" : aVar.b);
        jSONObject.put("imei", aVar.c == null ? "" : aVar.c);
        jSONObject.put("mac", aVar.d == null ? "" : com.igexin.push.g.o.c());
        jSONObject.put("version", aVar.e == null ? "" : aVar.e);
        jSONObject.put("channelid", aVar.f == null ? "" : aVar.f);
        jSONObject.put("type", "ANDROID");
        jSONObject.put("app", aVar.k == null ? "" : aVar.k);
        StringBuilder sb = new StringBuilder("ANDROID-");
        sb.append(aVar.g == null ? "" : aVar.g);
        jSONObject.put("deviceid", sb.toString());
        jSONObject.put("device_token", aVar.l == null ? "" : aVar.l);
        jSONObject.put("brand", aVar.m == null ? "" : aVar.m);
        jSONObject.put("system_version", aVar.j == null ? "" : aVar.j);
        jSONObject.put("cell", aVar.i == null ? "" : aVar.i);
        jSONObject.put("aid", com.igexin.push.g.o.h());
        jSONObject.put("adid", com.igexin.push.g.o.i());
        jSONObject.put("gtcid", TextUtils.isEmpty(aVar.o) ? "" : aVar.o);
        jSONObject.put("oaid", com.igexin.push.core.e.h == null ? "" : com.igexin.push.core.e.h);
        ServiceManager.getInstance();
        String strE = ServiceManager.e(com.igexin.push.core.e.l);
        if (!com.igexin.push.core.b.ao.equals(strE)) {
            jSONObject.put(com.igexin.push.g.q.a, strE);
        }
        ServiceManager.getInstance();
        jSONObject.put(com.igexin.push.g.q.d, ServiceManager.d(com.igexin.push.core.e.l));
        jSONObject.put("notification_enabled", com.igexin.push.g.c.b(com.igexin.push.core.e.l) ? 1 : 0);
        jSONObject.put("installChannel", com.igexin.c.b.a.b(com.igexin.push.core.e.b, "").replaceAll("\\|", ""));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("action", "addphoneinfo");
        jSONObject2.put(com.igexin.push.core.b.C, String.valueOf(aVar.n));
        jSONObject2.put("info", jSONObject);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("guardMe", String.valueOf(com.igexin.push.config.e.b()));
        jSONObject3.put("guardOthers", String.valueOf(com.igexin.push.config.e.c()));
        jSONObject3.put("oneId", com.igexin.push.g.o.t());
        jSONObject2.put("extra", jSONObject3);
        return jSONObject2.toString();
    }
}
