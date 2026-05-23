package com.mobile.auth.gatewayauth.manager;

import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public interface RequestCallback<T, K> {
    void onError(K k);

    void onSuccess(T t);
}
