package com.zx.a.I8b7;

import android.net.Network;
import com.zx.a.I8b7.q2;
import com.zx.module.annotation.Java2C;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f implements Runnable {

    public class a implements q2.b {
        public a() {
        }

        @Java2C.Method2C
        private native void b(Network network);

        @Override // com.zx.a.I8b7.q2.b
        public void a() {
            b(null);
        }

        @Override // com.zx.a.I8b7.q2.b
        public void a(int i, String str) {
        }

        @Override // com.zx.a.I8b7.q2.b
        public void a(Network network) {
            b(network);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native JSONObject a(int i) throws JSONException;

    @Override // java.lang.Runnable
    public void run() {
        try {
            q2.c.a.a(new a());
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
