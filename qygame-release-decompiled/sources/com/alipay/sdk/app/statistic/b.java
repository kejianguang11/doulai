package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;

    b(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.packet.impl.c cVar = new com.alipay.sdk.packet.impl.c();
        try {
            String strB = i.b(this.a, a.a, null);
            if (!TextUtils.isEmpty(strB) && cVar.a(this.a, strB) != null) {
                i.a(this.a, a.a);
            }
        } catch (Throwable unused) {
        }
        try {
            if (TextUtils.isEmpty(this.b)) {
                return;
            }
            cVar.a(this.a, this.b);
        } catch (IOException unused2) {
            i.a(this.a, a.a, this.b);
        } catch (Throwable unused3) {
        }
    }
}
