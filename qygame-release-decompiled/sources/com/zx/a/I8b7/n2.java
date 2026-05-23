package com.zx.a.I8b7;

import android.content.Context;
import com.igexin.sdk.PushConsts;
import com.zx.a.I8b7.a4;
import com.zx.module.base.Callback;
import com.zx.module.base.Listener;
import com.zx.module.base.ZXModule;
import com.zx.module.context.ContextHolder;
import com.zx.module.exception.ZXModuleInvokeException;
import com.zx.module.exception.ZXModuleOnCreateException;
import com.zx.module.exception.ZXModuleOnDestroyException;
import com.zx.module.exception.ZXModuleStartException;
import com.zx.sdk.common.utils.ZXTask;
import java.lang.reflect.Method;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class n2 implements ZXModule {
    public t3 a;
    public final l2 b = new l2();

    @Override // com.zx.module.base.ZXModule
    public String getModuleIdentifier() {
        return "core-n";
    }

    @Override // com.zx.module.base.ZXModule
    public String getModuleVersion() {
        return "3.3.5.47903";
    }

    @Override // com.zx.module.base.ZXModule
    public String invoke(String str, String str2) throws ZXModuleInvokeException {
        l2 l2Var = this.b;
        l2Var.getClass();
        try {
            String strSubstring = r.b(str, "SHA256").substring(0, 16);
            if (!((HashSet) l2.b).contains(strSubstring)) {
                return l2Var.a(str + " not in invokableMethods", 3);
            }
            Method declaredMethod = l2.class.getDeclaredMethod("f" + strSubstring, String.class);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(l2Var, str2);
        } catch (Exception e) {
            v2.a(e);
            throw new ZXModuleInvokeException("Cannot invoke " + str + ", nested exception is " + e.getMessage(), e);
        }
    }

    @Override // com.zx.module.base.ZXModule
    public String invokeAsync(String str, String str2, Callback callback) throws ZXModuleInvokeException {
        l2 l2Var = this.b;
        l2Var.getClass();
        try {
            String strSubstring = r.b(str, "SHA256").substring(0, 16);
            if (!((HashSet) l2.b).contains(strSubstring)) {
                String strA = l2Var.a(str + " not in invokableMethods", 3);
                callback.callback(strA);
                return strA;
            }
            v2.a("开始执行invokeAsync: method:" + str + "; " + str2 + ":cb");
            StringBuilder sb = new StringBuilder();
            sb.append("f");
            sb.append(strSubstring);
            Method declaredMethod = l2.class.getDeclaredMethod(sb.toString(), String.class, Callback.class);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(l2Var, str2, callback);
        } catch (Exception e) {
            StringBuilder sbA = j3.a("开始执行invokeAsync:");
            sbA.append(e.getMessage());
            v2.b(sbA.toString());
            throw new ZXModuleInvokeException("Cannot invokeAsync " + str + ", nested exception is " + e.getMessage(), e);
        }
    }

    @Override // com.zx.module.base.ZXModule
    public void onCreate(ContextHolder contextHolder) throws ZXModuleOnCreateException {
        y3 y3Var = new y3();
        this.a = y3Var;
        Context context = (Context) contextHolder.getContext();
        try {
            if (!y3Var.b.getAndSet(true)) {
                a4.f.a.a.execute(new x3(y3Var, context));
            }
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXCore init failed: "));
            y3Var.b.set(false);
        }
        this.b.a = this.a;
    }

    @Override // com.zx.module.base.ZXModule
    public void onDestroy() throws ZXModuleOnDestroyException {
    }

    @Override // com.zx.module.base.ZXModule
    public void setMessageListener(Listener listener) {
        y3 y3Var = (y3) this.a;
        y3Var.getClass();
        y3Var.c = new u3(y3Var, listener);
    }

    @Override // com.zx.module.base.ZXModule
    public void start() throws ZXModuleStartException {
        y3 y3Var = (y3) this.a;
        if (y3Var.a.compareAndSet(false, true)) {
            try {
                a4.f.a.a.execute(new ZXTask(new v3(y3Var), new w3(y3Var)));
            } catch (Throwable th) {
                y3Var.c.onMessage("MESSAGE_ON_ZXID_RECEIVED", h2.a(PushConsts.GET_SDKONLINESTATE, th.getMessage()));
                StringBuilder sb = new StringBuilder();
                sb.append("ZXCore start failed: ");
                k3.a(th, sb);
            }
        }
    }
}
