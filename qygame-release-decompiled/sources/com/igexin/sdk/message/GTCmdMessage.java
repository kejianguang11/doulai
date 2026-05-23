package com.igexin.sdk.message;

/* JADX INFO: loaded from: classes.dex */
public class GTCmdMessage extends BaseMessage {
    private int action;

    public GTCmdMessage() {
    }

    public GTCmdMessage(int i) {
        this.action = i;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int i) {
        this.action = i;
    }
}
