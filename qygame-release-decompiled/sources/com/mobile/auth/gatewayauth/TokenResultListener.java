package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
@SafeProtector
public interface TokenResultListener {
    void onTokenFailed(String str);

    void onTokenSuccess(String str);
}
