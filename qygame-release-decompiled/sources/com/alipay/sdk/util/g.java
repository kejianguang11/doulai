package com.alipay.sdk.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.alipay.android.app.IRemoteServiceCallback;

/* JADX INFO: loaded from: classes.dex */
final class g extends IRemoteServiceCallback.Stub {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final boolean isHideLoadingScreen() throws RemoteException {
        return false;
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final void payEnd(boolean z, String str) throws RemoteException {
    }

    @Override // com.alipay.android.app.IRemoteServiceCallback
    public final void startActivity(String str, String str2, int i, Bundle bundle) throws RemoteException {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            bundle.putInt("CallingPid", i);
            intent.putExtras(bundle);
        } catch (Exception unused) {
        }
        intent.setClassName(str, str2);
        if (this.a.a != null) {
            this.a.a.startActivity(intent);
        }
        this.a.f.b();
    }
}
