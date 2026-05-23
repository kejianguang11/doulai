package com.zx.a.I8b7;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.Constant;
import com.zx.a.I8b7.a0;
import com.zx.a.I8b7.a4;
import com.zx.sdk.api.Callback;
import com.zx.sdk.api.PermissionCallback;
import com.zx.sdk.api.SAIDCallback;
import com.zx.sdk.api.ZXApi;
import com.zx.sdk.api.ZXIDChangedListener;
import com.zx.sdk.api.ZXIDListener;

/* JADX INFO: loaded from: classes.dex */
public class k2 implements ZXApi {
    public String a;

    public k2(String str) throws IllegalStateException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalStateException("ZX_APPID not found");
        }
        this.a = str;
    }

    @Override // com.zx.sdk.api.ZXApi
    public void addZXIDChangedListener(ZXIDChangedListener zXIDChangedListener) {
        try {
            a0.b.a.a(this.a, "addZXIDChangedListener", "");
            b3 b3VarB = b3.b();
            String str = this.a;
            b3VarB.getClass();
            a4.f.a.a.execute(new i3(b3VarB, str, zXIDChangedListener));
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.registerListener(listener) failed: "));
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void allowPermissionDialog(boolean z) {
        try {
            a0.b.a.a(this.a, "allowPermissionDialog", "enable=" + z);
            b3 b3VarB = b3.b();
            b3VarB.getClass();
            a4.f.a.a.execute(new g3(b3VarB, z));
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.allowPermissionDialog failed: "));
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void checkPermission(Activity activity, PermissionCallback permissionCallback) {
        try {
            a0.b.a.a(this.a, "checkPermission", "");
            if (permissionCallback == null) {
                return;
            }
            b3 b3VarB = b3.b();
            b3VarB.getClass();
            a4.f.a.a.execute(new a3(b3VarB, permissionCallback, activity));
        } catch (Throwable th) {
            t.b(th.getMessage());
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void getAuthToken(Callback callback) {
        try {
            a0.b.a.a(this.a, "getAuthToken", "");
            if (callback == null) {
                return;
            }
            b3 b3VarB = b3.b();
            String str = this.a;
            b3VarB.getClass();
            a4.f.a.a.execute(new e3(b3VarB, str, callback));
        } catch (Throwable th) {
            t.b(th.getMessage());
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void getOpenID(Callback callback, Context context) {
        try {
            a0.b.a.a(this.a, "getOpenID", "");
            if (callback != null) {
                b3 b3VarB = b3.b();
                b3VarB.getClass();
                a4.f.a.c.execute(new x2(b3VarB, context, callback));
            }
        } catch (Throwable th) {
            if (callback != null) {
                callback.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
            k3.a(th, j3.a("ZXManager.getOpenID(cb) failed: "));
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void getSAID(String str, String str2, String str3, String str4, String str5, SAIDCallback sAIDCallback) {
        try {
            a0.b.a.a(this.a, "getUAID", "");
            if (sAIDCallback != null) {
                b3 b3VarB = b3.b();
                String str6 = this.a;
                b3VarB.getClass();
                a4.f.a.a.execute(new c3(b3VarB, str6, str, str2, str3, str4, str5, sAIDCallback));
            }
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager getSAID onFailed:"));
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void getTag(Callback callback) {
        try {
            a0.b.a.a(this.a, "getTag", "");
            if (callback == null) {
                return;
            }
            b3 b3VarB = b3.b();
            String str = this.a;
            b3VarB.getClass();
            a4.f.a.a.execute(new d3(b3VarB, str, callback));
        } catch (Throwable th) {
            t.b(th.getMessage());
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public String getVersion() {
        a0.b.a.a(this.a, "getVersion", "");
        return "3.3.5.47903";
    }

    @Override // com.zx.sdk.api.ZXApi
    public void getZXID(ZXIDListener zXIDListener) {
        try {
            a0.b.a.a(this.a, "getZXID", "");
            if (zXIDListener != null) {
                b3 b3VarB = b3.b();
                String str = this.a;
                b3VarB.getClass();
                a4.f.a.a.execute(new w2(b3VarB, str, zXIDListener));
            }
        } catch (Throwable th) {
            if (zXIDListener != null) {
                zXIDListener.onFailed(com.igexin.push.config.c.d, th.getMessage());
            }
            k3.a(th, j3.a("ZXManager.getZXID(zxidListener) failed: "));
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void init(Context context) {
        try {
            a0.b.a.a(this.a, "init", "");
            b3.a(context);
        } catch (Throwable th) {
            t.b("ZXManager.init failed:" + th);
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public String invoke(String str, String str2) {
        try {
            a0.b.a.a(this.a, "invoke", "method=" + str + "&argument" + str2);
            return b3.b().a(str, str2);
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.invoke failed: "));
            return null;
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public boolean isAllowPermissionDialog() {
        try {
            a0.b.a.a(this.a, "isAllowPermissionDialog", "");
            b3.b().getClass();
            return q3.u == 1;
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.isAllowPermissionDialog failed: "));
            return false;
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public boolean isEnable() {
        try {
            a0.b.a.a(this.a, Constant.API_PARAMS_KEY_ENABLE, "");
            b3.b().getClass();
            return q3.t == 1;
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.isEnable failed: "));
            return false;
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void setDebug(boolean z) {
        try {
            a0.b.a.a(this.a, "setDebug", "isDebug=" + z);
            b3 b3VarB = b3.b();
            b3VarB.getClass();
            a4.f.a.a.execute(new h3(b3VarB, z));
        } catch (Throwable th) {
            t.b(th.getMessage());
        }
    }

    @Override // com.zx.sdk.api.ZXApi
    public void setEnable(boolean z) {
        try {
            a0.b.a.a(this.a, "setEnable", "enable=" + z);
            b3 b3VarB = b3.b();
            b3VarB.getClass();
            a4.f.a.a.execute(new f3(b3VarB, z));
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.setEnable failed: "));
        }
    }
}
