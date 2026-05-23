package com.gcloudsdk.apollo.apollovoice.httpclient;

/* JADX INFO: loaded from: classes.dex */
class SRTTAPIHTTPTaskQueue {
    private static SRTTAPIHTTPTaskQueueImp taskQueue = new SRTTAPIHTTPTaskQueueImp();

    SRTTAPIHTTPTaskQueue() {
    }

    public static synchronized void addTask(int i, String str, String str2, byte[] bArr, int i2) {
        taskQueue.addTask(i, str, str2, bArr, i2);
    }

    public static void init() {
    }

    public static void setAppInfo(String str, String str2, String str3, int i) {
        taskQueue.setAppInfo(str, str2, str3, i);
    }
}
