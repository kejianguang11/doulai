package com.igexin.sdk.message;

/* JADX INFO: loaded from: classes.dex */
public class GTTransmitMessage extends GTPushMessage {
    private boolean cidBindToken;
    private boolean isOffline;
    private String messageId;
    private byte[] payload;
    private String payloadId;
    private String taskId;

    public GTTransmitMessage() {
    }

    public GTTransmitMessage(String str, String str2, String str3, byte[] bArr, boolean z, boolean z2) {
        this.taskId = str;
        this.messageId = str2;
        this.payloadId = str3;
        this.payload = bArr;
        this.isOffline = z;
        this.cidBindToken = z2;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public String getPayloadId() {
        return this.payloadId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public boolean isCidBindToken() {
        return this.cidBindToken;
    }

    public boolean isOffline() {
        return this.isOffline;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public void setPayload(byte[] bArr) {
        this.payload = bArr;
    }

    public void setPayloadId(String str) {
        this.payloadId = str;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }
}
