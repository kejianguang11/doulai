package com.gcloudsdk.apollo;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class NetInterfaceHelper {
    private static String LOGTAG = "NetInterfaceHelper";
    private static MsgWorker mMsgWorker;

    public static void init(Context context) {
        if (mMsgWorker != null) {
            return;
        }
        mMsgWorker = new MsgWorker(context);
        new Thread(mMsgWorker).start();
    }

    public static void pushMsg(int i, int i2, int i3, String str) {
        mMsgWorker.sendMessage(new EventMsg(i, i2, i3, str));
    }
}
