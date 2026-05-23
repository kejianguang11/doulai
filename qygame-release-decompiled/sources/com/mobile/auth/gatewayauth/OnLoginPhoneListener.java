package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.model.LoginPhoneInfo;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public interface OnLoginPhoneListener {
    void onGetFailed(String str);

    void onGetLoginPhone(LoginPhoneInfo loginPhoneInfo);
}
