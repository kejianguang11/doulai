package com.alipay.sdk.packet.impl;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class c extends com.alipay.sdk.packet.d {
    public static final String t = "log_v";

    @Override // com.alipay.sdk.packet.d
    public final com.alipay.sdk.packet.b a(Context context, String str) throws Throwable {
        return a(context, str, "https://mcgw.alipay.com/sdklog.do", true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final String a(String str, JSONObject jSONObject) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final List<Header> a(boolean z, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.a, String.valueOf(z)));
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.d, "application/octet-stream"));
        arrayList.add(new BasicHeader(com.alipay.sdk.packet.d.g, "CBC"));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final JSONObject a() throws JSONException {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final String c() throws JSONException {
        HashMap map = new HashMap();
        map.put(com.alipay.sdk.packet.d.i, "/sdk/log");
        map.put(com.alipay.sdk.packet.d.j, "1.0.0");
        HashMap map2 = new HashMap();
        map2.put(t, "1.0");
        return a((HashMap<String, String>) map, (HashMap<String, String>) map2);
    }
}
