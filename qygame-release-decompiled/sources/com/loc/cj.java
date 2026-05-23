package com.loc;

import com.loc.ci;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class cj extends cl {
    private static cj c = new cj(new ci.a().a("amap-global-threadPool").a());

    private cj(ci ciVar) {
        try {
            this.a = new ThreadPoolExecutor(ciVar.a(), ciVar.b(), ciVar.d(), TimeUnit.SECONDS, ciVar.c(), ciVar);
            this.a.allowCoreThreadTimeOut(true);
        } catch (Throwable th) {
            an.b(th, "TPool", "ThreadPool");
            th.printStackTrace();
        }
    }

    public static cj a() {
        return c;
    }
}
