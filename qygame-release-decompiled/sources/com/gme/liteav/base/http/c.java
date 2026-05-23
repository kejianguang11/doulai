package com.gme.liteav.base.http;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class c implements Runnable {
    private final HttpClientAndroid a;
    private final Long b;

    private c(HttpClientAndroid httpClientAndroid, Long l) {
        this.a = httpClientAndroid;
        this.b = l;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid, Long l) {
        return new c(httpClientAndroid, l);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpClientAndroid.a(this.a, this.b);
    }
}
