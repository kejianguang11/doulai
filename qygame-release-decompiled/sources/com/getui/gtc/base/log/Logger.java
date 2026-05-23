package com.getui.gtc.base.log;

import android.content.Context;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.log.a.a;
import com.getui.gtc.base.log.a.b;
import com.getui.gtc.base.util.CommonUtil;

/* JADX INFO: loaded from: classes.dex */
public class Logger {
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static final int INFO = 3;
    public static final int VERBOSE = 1;
    public static final int WARN = 4;
    private a fileLogController;
    private com.getui.gtc.base.log.b.a fileLogDestination;
    private com.getui.gtc.base.log.c.a fileLogFormatter;
    private final com.getui.gtc.base.log.d.a logPrinter;
    private final b logcatLogController;
    private final com.getui.gtc.base.log.c.b logcatLogFormatter;

    public Logger() {
        this(null);
    }

    public Logger(Context context) {
        this.logPrinter = new com.getui.gtc.base.log.d.b();
        if (context != null) {
            GtcProvider.setContext(context);
        }
        Context context2 = GtcProvider.context();
        context2 = context2 == null ? CommonUtil.findAppContext() : context2;
        if (context2 != null) {
            this.fileLogDestination = new com.getui.gtc.base.log.b.a(context2);
            this.fileLogFormatter = new com.getui.gtc.base.log.c.a(this.fileLogDestination);
            this.fileLogController = new a(context2, this.fileLogFormatter);
            this.logPrinter.a(this.fileLogController);
        }
        this.logcatLogFormatter = new com.getui.gtc.base.log.c.b(new com.getui.gtc.base.log.b.b());
        this.logcatLogController = new b(this.logcatLogFormatter);
        this.logPrinter.a(this.logcatLogController);
    }

    public void addController(ILogController iLogController) {
        if (iLogController == null) {
            return;
        }
        this.logPrinter.a(iLogController);
    }

    public void d(String str) {
        this.logPrinter.a(2, null, str, null);
    }

    public void d(String str, String str2) {
        this.logPrinter.a(2, str, str2, null);
    }

    public void d(String str, String str2, Throwable th) {
        this.logPrinter.a(2, str, str2, th);
    }

    public void d(String str, Throwable th) {
        this.logPrinter.a(2, str, null, th);
    }

    public void d(Throwable th) {
        this.logPrinter.a(2, null, null, th);
    }

    public void e(String str) {
        this.logPrinter.a(5, null, str, null);
    }

    public void e(String str, String str2) {
        this.logPrinter.a(5, str, str2, null);
    }

    public void e(String str, String str2, Throwable th) {
        this.logPrinter.a(5, str, str2, th);
    }

    public void e(String str, Throwable th) {
        this.logPrinter.a(5, null, str, th);
    }

    public void e(Throwable th) {
        this.logPrinter.a(5, null, null, th);
    }

    public void filelog(int i, String str, String str2, Throwable th) {
        this.logPrinter.a(i | 32, str, str2, th);
    }

    public void i(String str) {
        this.logPrinter.a(3, null, str, null);
    }

    public void i(String str, String str2) {
        this.logPrinter.a(3, str, str2, null);
    }

    public void i(String str, String str2, Throwable th) {
        this.logPrinter.a(3, str, str2, th);
    }

    public void i(String str, Throwable th) {
        this.logPrinter.a(3, str, null, th);
    }

    public void i(Throwable th) {
        this.logPrinter.a(3, null, null, th);
    }

    public void logcat(int i, String str, String str2, Throwable th) {
        this.logPrinter.a(i | 16, str, str2, th);
    }

    public void removeController(ILogController iLogController) {
        this.logPrinter.b(iLogController);
    }

    public void setFileEnableProperty(String str) {
        if (this.fileLogController != null) {
            this.fileLogController.a(str);
        }
    }

    public void setGlobalTag(String str) {
        this.logcatLogFormatter.a = str;
        if (this.fileLogFormatter != null) {
            this.fileLogFormatter.a = str;
        }
    }

    public void setLogFileNameSuffix(String str) {
        if (this.fileLogDestination != null) {
            this.fileLogDestination.a(str);
        }
    }

    public void setLogcatEnable(boolean z) {
        this.logcatLogController.a = z;
    }

    public void setStackOffset(int i) {
        int i2 = i + 8;
        this.logcatLogFormatter.b = i2;
        if (this.fileLogFormatter != null) {
            this.fileLogFormatter.b = i2;
        }
    }

    public void v(String str) {
        this.logPrinter.a(1, null, str, null);
    }

    public void v(String str, String str2) {
        this.logPrinter.a(1, str, str2, null);
    }

    public void v(String str, String str2, Throwable th) {
        this.logPrinter.a(1, str, str2, th);
    }

    public void v(String str, Throwable th) {
        this.logPrinter.a(1, str, null, th);
    }

    public void v(Throwable th) {
        this.logPrinter.a(1, null, null, th);
    }

    public void w(String str) {
        this.logPrinter.a(4, null, str, null);
    }

    public void w(String str, String str2) {
        this.logPrinter.a(4, str, str2, null);
    }

    public void w(String str, String str2, Throwable th) {
        this.logPrinter.a(4, str, str2, th);
    }

    public void w(String str, Throwable th) {
        this.logPrinter.a(4, null, str, th);
    }

    public void w(Throwable th) {
        this.logPrinter.a(4, null, null, th);
    }
}
