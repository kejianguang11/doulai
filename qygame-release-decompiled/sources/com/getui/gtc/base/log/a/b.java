package com.getui.gtc.base.log.a;

import com.getui.gtc.base.log.ILogController;
import com.getui.gtc.base.log.ILogFormatter;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ILogController {
    public boolean a;
    private ILogFormatter b;

    public b() {
        this(new com.getui.gtc.base.log.c.b());
    }

    public b(ILogFormatter iLogFormatter) {
        this.a = true;
        this.b = (ILogFormatter) com.getui.gtc.base.log.e.a.a(iLogFormatter);
    }

    @Override // com.getui.gtc.base.log.ILogController
    public final boolean isLoggable(int i, String str) {
        int i2 = i & 240;
        if (i2 == 0 || i2 == 16) {
            return this.a;
        }
        return false;
    }

    @Override // com.getui.gtc.base.log.ILogController
    public final void log(int i, String str, String str2, Throwable th) {
        if ((i & 240) != 0) {
            i &= 15;
        }
        this.b.log(i, str, str2, th);
    }
}
