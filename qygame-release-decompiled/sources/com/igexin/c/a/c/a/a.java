package com.igexin.c.a.c.a;

import com.igexin.sdk.IUserLoggerInterface;

/* JADX INFO: loaded from: classes.dex */
public final class a implements b {
    public static final String a = "[GT-PUSH] ";
    private IUserLoggerInterface b;
    private final StringBuffer c = new StringBuffer();

    private void b() {
        if (this.c.length() > 0) {
            if (this.c.toString().contains("\n")) {
                for (String str : this.c.toString().split("\n")) {
                    if (this.b != null) {
                        this.b.log(a.concat(String.valueOf(str)));
                    }
                }
            } else {
                this.b.log(a + this.c.toString());
            }
            this.c.setLength(0);
        }
    }

    @Override // com.igexin.c.a.c.a.b
    public final void a() {
        if (this.b != null) {
            b();
        }
    }

    @Override // com.igexin.c.a.c.a.b
    public final void a(IUserLoggerInterface iUserLoggerInterface) {
        if (iUserLoggerInterface != null) {
            this.b = iUserLoggerInterface;
        }
    }

    @Override // com.igexin.c.a.c.a.b
    public final void a(String str) {
        if (this.b != null) {
            b();
            this.b.log(a.concat(String.valueOf(str)));
            return;
        }
        if (this.c.length() + str.length() < 5120) {
            StringBuffer stringBuffer = this.c;
            stringBuffer.append(str);
            stringBuffer.append("\n");
        } else {
            if (this.c.length() > 5120 || this.c.length() + "Warning! the log cache is too long to show the full content,we suggest you call initialize and setDebugLogger in a short time interval.".length() <= 5120) {
                return;
            }
            StringBuffer stringBuffer2 = this.c;
            stringBuffer2.append("Warning! the log cache is too long to show the full content,we suggest you call initialize and setDebugLogger in a short time interval.");
            stringBuffer2.append("\n");
        }
    }

    @Override // com.igexin.c.a.c.a.b
    public final void b(String str) {
        if (this.c.length() + str.length() < 5120) {
            StringBuffer stringBuffer = this.c;
            stringBuffer.append(str);
            stringBuffer.append("\n");
        } else {
            if (this.c.length() > 5120 || this.c.length() + "Warning! the log cache is too long to show the full content,we suggest you call initialize and setDebugLogger in a short time interval.".length() <= 5120) {
                return;
            }
            StringBuffer stringBuffer2 = this.c;
            stringBuffer2.append("Warning! the log cache is too long to show the full content,we suggest you call initialize and setDebugLogger in a short time interval.");
            stringBuffer2.append("\n");
        }
    }
}
