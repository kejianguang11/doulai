package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
@SafeProtector
public interface PreLoginResultListener {
    void onTokenFailed(String str, String str2);

    void onTokenSuccess(String str);
}
