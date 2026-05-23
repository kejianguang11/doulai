package com.gme.liteav.base.http;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class b implements Runnable {
    private final HttpClientAndroid a;

    private b(HttpClientAndroid httpClientAndroid) {
        this.a = httpClientAndroid;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid) {
        return new b(httpClientAndroid);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpClientAndroid.b(this.a);
    }
}
