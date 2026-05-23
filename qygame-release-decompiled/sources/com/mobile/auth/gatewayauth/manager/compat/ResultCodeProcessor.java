package com.mobile.auth.gatewayauth.manager.compat;

import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.model.TokenRet;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public interface ResultCodeProcessor {
    String convertCode(String str);

    TokenRet convertErrorInfo(String str, String str2, String str3);

    String getApiLevel();
}
