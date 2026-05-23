package com.getui.gtc.h;

import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.http.GtHttpClient;
import com.getui.gtc.base.http.Interceptor;
import com.getui.gtc.base.http.LoggerInterceptor;
import com.getui.gtc.base.http.Response;
import com.getui.gtc.base.util.NetworkUtil;
import com.getui.gtc.i.c.a;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    public static GtHttpClient a = new GtHttpClient.Builder().addInterceptor(new LoggerInterceptor(a.C0021a.a.a)).addInterceptor(new Interceptor() { // from class: com.getui.gtc.h.d.1
        @Override // com.getui.gtc.base.http.Interceptor
        public final Response intercept(Interceptor.Chain chain) throws IOException {
            if (NetworkUtil.isNetWorkAvailable(GtcProvider.context())) {
                return chain.proceed(chain.request());
            }
            throw new IllegalStateException("network is not available");
        }
    }).build();
}
