package com.gme.liteav.base.http;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class e implements Runnable {
    private final HttpClientAndroid a;

    private e(HttpClientAndroid httpClientAndroid) {
        this.a = httpClientAndroid;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid) {
        return new e(httpClientAndroid);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpClientAndroid.a(this.a);
    }
}
