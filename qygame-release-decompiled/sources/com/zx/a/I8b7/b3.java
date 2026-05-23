package com.zx.a.I8b7;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.zx.a.I8b7.a4;
import com.zx.a.I8b7.l3;
import com.zx.a.I8b7.o3;
import com.zx.module.annotation.Java2C;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class b3 {
    public static final AtomicBoolean a = new AtomicBoolean(false);

    public class a implements Runnable {
        public final /* synthetic */ Context a;

        public a(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                q3.b(this.a);
                try {
                    b3.a().a();
                } catch (Throwable th) {
                    Log.i("core info Except", "can ignore," + th.getMessage());
                }
            } catch (Throwable th2) {
                k3.a(th2, j3.a("ZXCore init failed: "));
                b3.a.set(false);
            }
        }
    }

    public static final class b {
        public static final b3 a = new b3();
    }

    public static l3 a() {
        Handler handler = o3.a;
        o3 o3Var = o3.a.a;
        if (o3Var.b()) {
            throw new RuntimeException("请先调用 ZXManager.checkPermission() 检查用户是否已授权");
        }
        if (!o3Var.a()) {
            throw new RuntimeException("用户未授权");
        }
        AtomicBoolean atomicBoolean = l3.e;
        return l3.e.a;
    }

    public static void a(Context context) {
        try {
            if (a.getAndSet(true)) {
                return;
            }
            a4.f.a.a.execute(new a(context));
        } catch (Throwable th) {
            a.set(false);
            t.b("ZXManager.init failed:" + th);
        }
    }

    public static final b3 b() {
        if (a.get()) {
            return b.a;
        }
        throw new IllegalStateException("ZXManager not init, should init firstly");
    }

    @Java2C.Method2C
    public native String a(String str, String str2) throws Throwable;
}
