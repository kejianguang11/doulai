package com.alipay.sdk.protocol;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public enum a {
    None(com.igexin.push.a.i),
    WapPay("js://wappay"),
    Update("js://update");

    private String d;

    a(String str) {
        this.d = str;
    }

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return None;
        }
        a aVar = None;
        for (a aVar2 : values()) {
            if (str.startsWith(aVar2.d)) {
                return aVar2;
            }
        }
        return aVar;
    }
}
