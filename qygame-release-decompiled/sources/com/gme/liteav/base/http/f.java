package com.gme.liteav.base.http;

import com.gme.liteav.base.http.HttpClientAndroid;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class f implements Runnable {
    private final HttpClientAndroid a;
    private final HttpClientAndroid.f b;
    private final long c;

    private f(HttpClientAndroid httpClientAndroid, HttpClientAndroid.f fVar, long j) {
        this.a = httpClientAndroid;
        this.b = fVar;
        this.c = j;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid, HttpClientAndroid.f fVar, long j) {
        return new f(httpClientAndroid, fVar, j);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpClientAndroid.a(this.a, this.b, this.c);
    }
}
