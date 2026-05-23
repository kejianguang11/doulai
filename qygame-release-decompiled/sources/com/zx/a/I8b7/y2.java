package com.zx.a.I8b7;

import com.zx.a.I8b7.p2;
import com.zx.sdk.api.PermissionCallback;

/* JADX INFO: loaded from: classes.dex */
public class y2 implements Runnable {
    public final /* synthetic */ PermissionCallback a;

    public y2(b3 b3Var, PermissionCallback permissionCallback) {
        this.a = permissionCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            p2.a.a.a.c(1);
            t.a("用户已授权获取卓信ID");
            try {
                b3.a().a(q3.a);
            } catch (Exception e) {
                t.b(e.getMessage());
            }
            this.a.onAuthorized();
        } catch (Throwable th) {
            k3.a(th, j3.a("卓信ID授权失败 error: "));
        }
    }
}
