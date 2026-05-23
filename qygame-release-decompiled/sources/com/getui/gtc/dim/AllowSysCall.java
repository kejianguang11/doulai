package com.getui.gtc.dim;

/* JADX INFO: loaded from: classes.dex */
public enum AllowSysCall {
    NOT_ALLOW(0),
    ONLY_ALLOW_FORE_CALL(1),
    ALL_ALLOW(2);

    private final int value;

    AllowSysCall(int i) {
        this.value = i;
    }

    public static AllowSysCall valueOf(int i) {
        switch (i) {
            case 0:
                return NOT_ALLOW;
            case 1:
                return ONLY_ALLOW_FORE_CALL;
            default:
                return ALL_ALLOW;
        }
    }

    public final int getValue() {
        return this.value;
    }
}
