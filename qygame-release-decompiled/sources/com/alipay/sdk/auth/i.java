package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class i implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ StringBuilder b;
    final /* synthetic */ APAuthInfo c;

    i(Activity activity, StringBuilder sb, APAuthInfo aPAuthInfo) {
        this.a = activity;
        this.b = sb;
        this.c = aPAuthInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.packet.b bVarA;
        try {
            try {
                bVarA = new com.alipay.sdk.packet.impl.a().a(this.a, this.b.toString());
            } catch (Throwable unused) {
                bVarA = null;
            }
            if (h.c != null) {
                h.c.b();
                h.b();
            }
        } catch (Exception unused2) {
            if (h.c == null) {
                return;
            }
        } catch (Throwable th) {
            if (h.c != null) {
                h.c.b();
            }
            throw th;
        }
        if (bVarA == null) {
            String unused3 = h.d = this.c.getRedirectUri() + "?resultCode=202";
            h.a(this.a, h.d);
            if (h.c != null) {
                h.c.b();
                return;
            }
            return;
        }
        List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(bVarA.a().optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
        int i = 0;
        while (true) {
            if (i >= listA.size()) {
                break;
            }
            if (listA.get(i).a == com.alipay.sdk.protocol.a.WapPay) {
                String unused4 = h.d = listA.get(i).b[0];
                break;
            }
            i++;
        }
        if (!TextUtils.isEmpty(h.d)) {
            Intent intent = new Intent(this.a, (Class<?>) AuthActivity.class);
            intent.putExtra("params", h.d);
            intent.putExtra("redirectUri", this.c.getRedirectUri());
            this.a.startActivity(intent);
            if (h.c == null) {
                return;
            }
            h.c.b();
            return;
        }
        String unused5 = h.d = this.c.getRedirectUri() + "?resultCode=202";
        h.a(this.a, h.d);
        if (h.c != null) {
            h.c.b();
        }
    }
}
