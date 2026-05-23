package com.gcloudsdk.apollo;

/* JADX INFO: loaded from: classes.dex */
public class EventMsg {
    public static final int CMD_BIND = 2;
    public static final int CMD_INIT_CM = 255;
    public static final int CMD_WARM = 1;
    public int arg1;
    public int arg2;
    public int cmd;
    public byte[] data;
    public String strarg;

    EventMsg(int i, int i2, int i3, String str) {
        this.cmd = i;
        this.arg1 = i2;
        this.arg2 = i3;
        this.strarg = str;
    }
}
