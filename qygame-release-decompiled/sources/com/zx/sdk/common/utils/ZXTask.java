package com.zx.sdk.common.utils;

import com.igexin.sdk.PushConsts;
import com.zx.a.I8b7.h2;
import com.zx.a.I8b7.k3;
import com.zx.a.I8b7.w3;

/* JADX INFO: loaded from: classes.dex */
public class ZXTask implements Runnable {
    private a errorCallback;
    private Runnable r;

    public interface a {
    }

    public ZXTask(Runnable runnable, a aVar) {
        this.r = runnable;
        this.errorCallback = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Runnable runnable = this.r;
            if (runnable != null) {
                runnable.run();
            }
        } catch (Throwable th) {
            a aVar = this.errorCallback;
            if (aVar != null) {
                ((w3) aVar).a.c.onMessage("MESSAGE_ON_ZXID_RECEIVED", h2.a(PushConsts.GET_SDKSERVICEPID, th.getMessage()));
                StringBuilder sb = new StringBuilder();
                sb.append("ZXCore start failed: ");
                k3.a(th, sb);
            }
        }
    }
}
