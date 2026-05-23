package com.mobile.auth.gatewayauth;

import android.content.Context;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public interface UIEventCallback {
    void onEvent(String str, Context context, String str2);
}
