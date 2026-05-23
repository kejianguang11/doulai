package com.gme.liteav.base.http;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class d implements Runnable {
    private final HttpClientAndroid a;
    private final long b;

    private d(HttpClientAndroid httpClientAndroid, long j) {
        this.a = httpClientAndroid;
        this.b = j;
    }

    public static Runnable a(HttpClientAndroid httpClientAndroid, long j) {
        return new d(httpClientAndroid, j);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpClientAndroid.a(this.a, this.b);
    }
}
