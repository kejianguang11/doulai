package com.alipay.android.phone.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: classes.dex */
final class f implements ConnectionKeepAliveStrategy {
    final /* synthetic */ d a;

    f(d dVar) {
        this.a = dVar;
    }

    @Override // org.apache.http.conn.ConnectionKeepAliveStrategy
    public final long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return com.igexin.push.config.c.g;
    }
}
