package com.mobile.auth.gatewayauth;

import com.mobile.auth.gatewayauth.annotations.AuthNumber;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public interface PnsLoggerHandler {
    void debug(String str);

    void error(String str);

    void info(String str);

    void monitor(String str);

    void verbose(String str);

    void warning(String str);
}
