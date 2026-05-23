package com.gme.liteav.base.http;

import com.gme.liteav.base.http.HttpClientAndroid;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class a implements Runnable {
    private final HttpClientAndroid a;
    private final HttpClientAndroid.e b;

    private a(HttpClientAndroid httpClientAndroid, HttpClientAndroid.e eVar) {
        this.a = httpClientAndroid;
        this.b = eVar;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid, HttpClientAndroid.e eVar) {
        return new a(httpClientAndroid, eVar);
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        this.a.doRequest(this.b);
    }
}
