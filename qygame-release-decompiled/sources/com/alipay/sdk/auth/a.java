package com.alipay.sdk.auth;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/* JADX INFO: loaded from: classes.dex */
final class a implements DownloadListener {
    final /* synthetic */ AuthActivity a;

    a(AuthActivity authActivity) {
        this.a = authActivity;
    }

    @Override // android.webkit.DownloadListener
    public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Throwable unused) {
        }
    }
}
