package com.zx.a.I8b7;

import com.zx.a.I8b7.a4;
import com.zx.sdk.api.PermissionCallback;

/* JADX INFO: loaded from: classes.dex */
public class e1 implements PermissionCallback {
    public PermissionCallback a;

    public e1(PermissionCallback permissionCallback) {
        this.a = permissionCallback;
    }

    @Override // com.zx.sdk.api.PermissionCallback
    public void onAuthorized() {
        try {
            if (this.a != null) {
                b3 b3VarB = b3.b();
                PermissionCallback permissionCallback = this.a;
                b3VarB.getClass();
                a4.f.a.a.execute(new y2(b3VarB, permissionCallback));
            }
        } catch (Throwable th) {
            v2.a(th);
        }
    }

    @Override // com.zx.sdk.api.PermissionCallback
    public void onUnauthorized() {
        try {
            if (this.a != null) {
                b3 b3VarB = b3.b();
                b3VarB.getClass();
                a4.f.a.a.execute(new z2(b3VarB));
                this.a.onUnauthorized();
            }
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
