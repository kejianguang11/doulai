package com.getui.gtc.h;

import com.getui.gtc.base.http.MediaType;
import com.getui.gtc.base.http.Request;
import com.getui.gtc.base.http.RequestBody;
import com.getui.gtc.base.http.Response;
import com.getui.gtc.base.http.crypt.GtRASCryptoInterceptor;
import com.getui.gtc.base.http.crypt.PtRASCryptoInterceptor;
import com.getui.gtc.base.util.io.IOUtils;
import com.getui.gtc.server.ServerManager;
import com.mobile.auth.BuildConfig;
import java.io.IOException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static void a(String str, int i) throws Exception {
        while (true) {
            String server = ServerManager.getServer("gtc.bs");
            try {
                Request requestBuild = new Request.Builder().url(String.format("%s/api.php?format=json&t=1", server)).method("POST").body(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), b(str, i))).cryptInterceptor(new GtRASCryptoInterceptor(com.getui.gtc.c.b.i, com.getui.gtc.c.b.h)).tag("type" + i + " task ").build();
                com.getui.gtc.i.c.a.a("type " + i + " data: " + str);
                Response responseExecute = d.a.newCall(requestBuild).execute();
                ServerManager.confirmServer("gtc.bs", server);
                responseExecute.close();
                return;
            } catch (Exception e) {
                com.getui.gtc.i.c.a.b("type " + i + " error : " + e.getMessage());
                if (!(e instanceof IOException) || !ServerManager.switchServer("gtc.bs", server)) {
                    throw e;
                }
                com.getui.gtc.i.c.a.b("type " + i + " failed with server: " + server + ", try again with: " + ServerManager.getServer("gtc.bs"));
            }
        }
        throw e;
    }

    public static void a(JSONObject jSONObject, String str) throws Exception {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(BuildConfig.FLAVOR_type, jSONObject);
        JSONObject jSONObject3 = new JSONObject(d.a.newCall(new Request.Builder().url(str).method("POST").body(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jSONObject2.toString())).cryptInterceptor(new PtRASCryptoInterceptor(com.getui.gtc.c.b.i, com.getui.gtc.c.b.h)).tag("hw scene task").build()).execute().body().string());
        if (jSONObject3.getInt("errno") != 0) {
            throw new IllegalStateException("hw scene task error:".concat(String.valueOf(jSONObject3)));
        }
    }

    private static String b(String str, int i) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("action", "upload_BI");
        jSONObject.put("BIType", String.valueOf(i));
        jSONObject.put("cid", com.getui.gtc.c.b.d);
        jSONObject.put("BIData", new String(IOUtils.encode(str.getBytes(), 0), com.alipay.sdk.sys.a.m));
        return jSONObject.toString();
    }
}
