package com.alipay.sdk.data;

import android.content.Context;
import java.util.HashMap;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class d implements Callable<String> {
    final /* synthetic */ Context a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ c c;

    d(c cVar, Context context, HashMap map) {
        this.c = cVar;
        this.a = context;
        this.b = map;
    }

    private String a() throws Exception {
        return c.a(this.a, this.b);
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        return c.a(this.a, this.b);
    }
}
