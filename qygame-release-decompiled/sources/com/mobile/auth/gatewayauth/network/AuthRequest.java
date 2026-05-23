package com.mobile.auth.gatewayauth.network;

import com.alipay.sdk.packet.d;
import com.mobile.auth.v.c;
import com.nirvana.tools.jsoner.JsonerTag;

/* JADX INFO: loaded from: classes.dex */
public class AuthRequest extends c {

    @JsonerTag(keyName = d.e)
    private String Version = "2017-05-25";

    @JsonerTag(keyName = "Format")
    private String Format = "JSON";
}
