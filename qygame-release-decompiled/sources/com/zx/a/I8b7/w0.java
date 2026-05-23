package com.zx.a.I8b7;

import com.zx.module.base.Callback;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class w0 {

    public class a implements Callback {
        public final /* synthetic */ String[] a;
        public final /* synthetic */ CountDownLatch b;

        public a(String[] strArr, CountDownLatch countDownLatch) {
            this.a = strArr;
            this.b = countDownLatch;
        }

        @Override // com.zx.module.base.Callback
        public void callback(String str) {
            try {
                this.a[0] = str;
                this.b.countDown();
            } catch (Throwable unused) {
            }
        }
    }

    public static final String a() {
        String[] strArr = {""};
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Thread thread = new Thread(new x0(new a(strArr, countDownLatch)));
            thread.setUncaughtExceptionHandler(new y0());
            thread.start();
            countDownLatch.await(1L, TimeUnit.SECONDS);
        } catch (Throwable unused) {
        }
        return strArr[0];
    }
}
