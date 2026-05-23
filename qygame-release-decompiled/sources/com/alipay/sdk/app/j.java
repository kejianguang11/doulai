package com.alipay.sdk.app;

import com.gme.liteav.TXLiteAVCode;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ResultCode;

/* JADX INFO: loaded from: classes.dex */
public enum j {
    SUCCEEDED(9000, "处理成功"),
    FAILED(4000, "系统繁忙，请稍后再试"),
    CANCELED(TXLiteAVCode.WARNING_IGNORE_UPSTREAM_FOR_AUDIENCE, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(4001, ResultCode.MSG_ERROR_INVALID_PARAM),
    DOUBLE_REQUEST(Constant.DEFAULT_TIMEOUT, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");

    public int h;
    public String i;

    j(int i, String str) {
        this.h = i;
        this.i = str;
    }

    private int a() {
        return this.h;
    }

    public static j a(int i) {
        if (i == 4001) {
            return PARAMS_ERROR;
        }
        if (i == 5000) {
            return DOUBLE_REQUEST;
        }
        if (i == 8000) {
            return PAY_WAITTING;
        }
        if (i == 9000) {
            return SUCCEEDED;
        }
        switch (i) {
            case TXLiteAVCode.WARNING_IGNORE_UPSTREAM_FOR_AUDIENCE /* 6001 */:
                return CANCELED;
            case 6002:
                return NETWORK_ERROR;
            default:
                return FAILED;
        }
    }

    private void a(String str) {
        this.i = str;
    }

    private String b() {
        return this.i;
    }

    private void b(int i) {
        this.h = i;
    }
}
