package com.getui.gtc.base.log.c;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.getui.gtc.base.log.ILogDestination;
import com.getui.gtc.base.log.ILogFormatter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ILogFormatter {
    public String a;
    public int b;
    private ILogDestination c;

    public b() {
        this(new com.getui.gtc.base.log.b.b());
    }

    public b(ILogDestination iLogDestination) {
        this.a = "";
        this.b = 8;
        this.c = (ILogDestination) com.getui.gtc.base.log.e.a.a(iLogDestination);
    }

    private String a() {
        try {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[this.b];
            String className = stackTraceElement.getClassName();
            return String.format("%s.%s", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName());
        } catch (Throwable unused) {
            return "";
        }
    }

    private String a(String str, Throwable th) throws JSONException {
        if (th != null && str != null) {
            str = str + " : " + a(th);
        }
        if (th != null && str == null) {
            str = a(th);
        }
        if (TextUtils.isEmpty(str)) {
            str = "Empty/NULL log message";
        }
        String strTrim = str.trim();
        if (strTrim.startsWith("{") && strTrim.endsWith(h.d)) {
            try {
                strTrim = new JSONObject(strTrim).toString(2);
            } catch (Throwable unused) {
            }
        }
        if (strTrim.startsWith("[") && strTrim.endsWith("]")) {
            try {
                strTrim = new JSONArray(strTrim).toString(2);
            } catch (Throwable unused2) {
            }
        }
        String strB = b();
        if (th != null) {
            return strTrim;
        }
        return strTrim + " " + strB;
    }

    private static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter((OutputStream) byteArrayOutputStream, false);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return byteArrayOutputStream.toString();
    }

    private String b() {
        try {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[this.b];
            String className = stackTraceElement.getClassName();
            return String.format("(%s:%d)", className.substring(className.lastIndexOf(".") + 1) + ".java", Integer.valueOf(stackTraceElement.getLineNumber()));
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // com.getui.gtc.base.log.ILogFormatter
    public final void log(int i, String str, String str2, Throwable th) throws JSONException {
        String strA = a();
        if (TextUtils.isEmpty(str)) {
            str = this.a;
        }
        if (!TextUtils.isEmpty(str)) {
            strA = str + "-" + strA;
        }
        this.c.log(i, strA, a(str2, th));
    }
}
