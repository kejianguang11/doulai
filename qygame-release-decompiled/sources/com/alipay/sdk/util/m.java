package com.alipay.sdk.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/* JADX INFO: loaded from: classes.dex */
final class m implements DownloadListener {
    final /* synthetic */ Context a;

    m(Context context) {
        this.a = context;
    }

    @Override // android.webkit.DownloadListener
    public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setFlags(268435456);
            this.a.startActivity(intent);
        } catch (Throwable unused) {
        }
    }
}
