package com.zx.a.I8b7;

import android.app.Activity;
import android.os.Handler;
import com.zx.a.I8b7.o3;
import com.zx.sdk.api.PermissionCallback;

/* JADX INFO: loaded from: classes.dex */
public class a3 implements Runnable {
    public final /* synthetic */ PermissionCallback a;
    public final /* synthetic */ Activity b;

    public a3(b3 b3Var, PermissionCallback permissionCallback, Activity activity) {
        this.a = permissionCallback;
        this.b = activity;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            e1 e1Var = new e1(this.a);
            Handler handler = o3.a;
            o3 o3Var = o3.a.a;
            if (o3Var.b()) {
                Activity activity = this.b;
                o3Var.getClass();
                o3.a.post(new n3(o3Var, activity, e1Var));
            } else if (o3Var.a()) {
                e1Var.onAuthorized();
            } else {
                e1Var.onUnauthorized();
            }
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.registerListener(listener) failed: "));
        }
    }
}
