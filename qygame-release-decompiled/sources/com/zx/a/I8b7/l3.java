package com.zx.a.I8b7;

import android.content.Context;
import android.text.TextUtils;
import com.zx.a.I8b7.x;
import com.zx.module.annotation.Java2C;
import com.zx.module.base.Callback;
import com.zx.module.base.ZXModule;
import com.zx.module.context.ContextHolder;
import com.zx.module.exception.ZXModuleInvokeException;
import com.zx.sdk.api.SAIDCallback;
import com.zx.sdk.api.ZXIDListener;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l3 {
    public static final AtomicBoolean e = new AtomicBoolean(false);
    public ZXModule a = null;
    public final a1 b;
    public final u2 c;
    public final t2 d;

    public class a implements ContextHolder {
        public final /* synthetic */ Context a;

        public a(l3 l3Var, Context context) {
            this.a = context;
        }

        @Override // com.zx.module.context.ContextHolder
        public Object getContext() {
            return this.a;
        }
    }

    public class b implements Callback {
        public final /* synthetic */ SAIDCallback a;

        public b(l3 l3Var, SAIDCallback sAIDCallback) {
            this.a = sAIDCallback;
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("code");
                if (i == 0) {
                    this.a.onSuccess(jSONObject.getString(com.alipay.sdk.packet.d.k));
                } else {
                    this.a.onFailed(i, jSONObject.optString("msg"));
                }
            } catch (Throwable th) {
                v2.a(th);
                SAIDCallback sAIDCallback = this.a;
                if (sAIDCallback != null) {
                    sAIDCallback.onFailed(com.igexin.push.config.c.d, th.getMessage());
                }
            }
        }
    }

    public class c implements Callback {
        public final /* synthetic */ com.zx.sdk.api.Callback a;

        public c(l3 l3Var, com.zx.sdk.api.Callback callback) {
            this.a = callback;
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("code");
                if (i == 0) {
                    this.a.onSuccess(jSONObject.getString(com.alipay.sdk.packet.d.k));
                } else {
                    this.a.onFailed(i, jSONObject.optString(com.alipay.sdk.packet.d.k));
                }
            } catch (Throwable th) {
                v2.a(th);
                com.zx.sdk.api.Callback callback = this.a;
                if (callback != null) {
                    callback.onFailed(com.igexin.push.config.c.d, th.getMessage());
                }
            }
        }
    }

    public class d implements Callback {
        public final /* synthetic */ com.zx.sdk.api.Callback a;

        public d(l3 l3Var, com.zx.sdk.api.Callback callback) {
            this.a = callback;
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("code");
                if (i == 0) {
                    this.a.onSuccess(jSONObject.getString(com.alipay.sdk.packet.d.k));
                } else {
                    this.a.onFailed(i, jSONObject.optString(com.alipay.sdk.packet.d.k));
                }
            } catch (Throwable th) {
                v2.a(th);
                com.zx.sdk.api.Callback callback = this.a;
                if (callback != null) {
                    callback.onFailed(com.igexin.push.config.c.d, th.getMessage());
                }
            }
        }
    }

    public static class e {
        public static final l3 a = new l3();
    }

    public l3() {
        a1 a1Var = new a1();
        this.b = a1Var;
        u2 u2Var = new u2();
        this.c = u2Var;
        t2 t2Var = new t2();
        this.d = t2Var;
        a1Var.a("MESSAGE_ON_ZXID_CHANGED", u2Var);
        a1Var.a("MESSAGE_ON_ZXID_RECEIVED", t2Var);
        try {
            a(q3.a);
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXModule init failed: "));
        }
    }

    @Java2C.Method2C
    public native String a(String str, String str2, String str3, String str4, String str5, String str6, SAIDCallback sAIDCallback) throws JSONException, ZXModuleInvokeException;

    public void a() throws m2 {
        try {
            this.a.start();
        } catch (Exception e2) {
            StringBuilder sbA = j3.a("Raised exception in start: ");
            sbA.append(e2.getMessage());
            throw new m2(sbA.toString(), e2);
        }
    }

    public void a(Context context) throws m2 {
        try {
            if (e.getAndSet(true)) {
                return;
            }
            this.a = x.a.a.a(context);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("version", "3.3.5.47903");
            this.a.invoke("setSDKVersion", jSONObject.toString());
            this.a.onCreate(new a(this, context));
            this.a.setMessageListener(this.b);
        } catch (Exception e2) {
            e.set(false);
            StringBuilder sbA = j3.a("Raised exception while initializing: ");
            sbA.append(e2.getMessage());
            throw new m2(sbA.toString(), e2);
        }
    }

    @Java2C.Method2C
    public native void a(String str, com.zx.sdk.api.Callback callback) throws JSONException, ZXModuleInvokeException;

    public void a(String str, ZXIDListener zXIDListener) throws m2 {
        if (zXIDListener != null) {
            try {
                t2 t2Var = this.d;
                t2Var.getClass();
                if (!TextUtils.isEmpty(str)) {
                    LinkedList<ZXIDListener> linkedList = t2Var.a.get(str);
                    if (linkedList == null) {
                        linkedList = new LinkedList<>();
                    }
                    linkedList.add(zXIDListener);
                    t2Var.a.put(str, linkedList);
                }
            } catch (Exception e2) {
                v2.a(e2);
                StringBuilder sbA = j3.a("Raised exception while getZXID: nested exception is ");
                sbA.append(e2.getMessage());
                throw new m2(sbA.toString(), e2);
            }
        }
        a();
    }

    @Java2C.Method2C
    public native void a(boolean z) throws JSONException, ZXModuleInvokeException;

    @Java2C.Method2C
    public native void b(String str, com.zx.sdk.api.Callback callback) throws JSONException, ZXModuleInvokeException;
}
