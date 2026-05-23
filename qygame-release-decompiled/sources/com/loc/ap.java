package com.loc;

import android.content.Context;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ap extends r {
    public JSONObject a = null;
    public Context b = null;

    @Override // com.loc.bl
    public final Map<String, String> a() {
        HashMap map = new HashMap();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Accept-Encoding", "gzip");
        map.put("User-Agent", "AMAP SDK Android core 4.2.9");
        map.put("X-INFO", n.a(this.b));
        map.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", "4.2.9", "core"));
        map.put("logversion", "2.1");
        return map;
    }

    @Override // com.loc.bl
    public final String b() {
        return q.a().b() ? "https://restsdk.amap.com/sdk/compliance/params" : "http://restsdk.amap.com/sdk/compliance/params";
    }

    @Override // com.loc.bl
    public final Map<String, String> d() {
        return null;
    }

    @Override // com.loc.bl
    public final byte[] e() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.a != null) {
                Iterator<String> itKeys = this.a.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    stringBuffer.append(next + "=" + URLEncoder.encode(this.a.get(next).toString(), com.igexin.push.g.s.b) + com.alipay.sdk.sys.a.b);
                }
            }
            stringBuffer.append("output=json");
            String strF = l.f(this.b);
            stringBuffer.append("&key=".concat(String.valueOf(strF)));
            String strA = n.a();
            stringBuffer.append("&ts=".concat(String.valueOf(strA)));
            stringBuffer.append("&scode=" + n.a(this.b, strA, "key=".concat(String.valueOf(strF))));
            return stringBuffer.toString().getBytes(com.igexin.push.g.s.b);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    @Override // com.loc.bl
    public final String h() {
        return "core";
    }
}
