package com.zx.a.I8b7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.zx.sdk.api.PermissionCallback;

/* JADX INFO: loaded from: classes.dex */
public class n3 implements Runnable {
    public final /* synthetic */ Activity a;
    public final /* synthetic */ PermissionCallback b;

    public class a implements View.OnClickListener {
        public final /* synthetic */ p3 a;

        public a(p3 p3Var) {
            this.a = p3Var;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.a.dismiss();
            n3.this.b.onAuthorized();
        }
    }

    public class b implements View.OnClickListener {
        public final /* synthetic */ p3 a;

        public b(p3 p3Var) {
            this.a = p3Var;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.a.dismiss();
            n3.this.b.onUnauthorized();
        }
    }

    public class c implements View.OnClickListener {
        public final /* synthetic */ p3 a;

        public c(p3 p3Var) {
            this.a = p3Var;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.a.dismiss();
            v2.a("用户点击了解更多");
            n3.this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://zxid.mobileservice.cn/privacy")));
        }
    }

    public n3(o3 o3Var, Activity activity, PermissionCallback permissionCallback) {
        this.a = activity;
        this.b = permissionCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            p3 p3Var = new p3(this.a);
            p3Var.b = new a(p3Var);
            p3Var.a = new b(p3Var);
            p3Var.c = new c(p3Var);
            p3Var.show();
        } catch (Throwable th) {
            k3.a(th, j3.a("卓信ID授权弹窗异常: "));
        }
    }
}
