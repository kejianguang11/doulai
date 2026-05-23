package com.alipay.sdk.packet.impl;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b extends com.alipay.sdk.packet.d {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final JSONObject a() throws JSONException {
        return com.alipay.sdk.packet.d.a("sdkConfig", "obtain");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alipay.sdk.packet.d
    public final String b() {
        return "5.0.0";
    }
}
