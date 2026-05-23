package com.igexin.sdk.message;

/* JADX INFO: loaded from: classes.dex */
public class SetFenceLabelMessage extends GTCmdMessage {
    private String code;

    public SetFenceLabelMessage() {
    }

    public SetFenceLabelMessage(String str, int i) {
        super(i);
        this.code = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }
}
