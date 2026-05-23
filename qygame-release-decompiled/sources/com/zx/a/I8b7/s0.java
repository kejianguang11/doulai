package com.zx.a.I8b7;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class s0 implements n0 {
    public m0 a;
    public String b = "";
    public int c = 8;

    public s0(m0 m0Var) {
        this.a = (m0) i2.a(m0Var);
    }

    public static String a(Throwable th) {
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter((OutputStream) byteArrayOutputStream, false);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return byteArrayOutputStream.toString();
    }

    @Override // com.zx.a.I8b7.n0
    public void a(int i, String str, String str2, Throwable th) throws JSONException {
        String str3;
        String str4;
        try {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[this.c];
            String className = stackTraceElement.getClassName();
            str3 = String.format("%s.%s", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName());
        } catch (Throwable unused) {
            str3 = "";
        }
        if (TextUtils.isEmpty(str)) {
            str = this.b;
        }
        if (!TextUtils.isEmpty(str)) {
            str3 = str + "-" + str3;
        }
        if (th != null && str2 != null) {
            str2 = str2 + " : " + a(th);
        }
        if (th != null && str2 == null) {
            str2 = a(th);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "Empty/NULL log message";
        }
        String strTrim = str2.trim();
        if (strTrim.startsWith("{") && strTrim.endsWith(com.alipay.sdk.util.h.d)) {
            try {
                strTrim = new JSONObject(strTrim).toString(2);
            } catch (Throwable unused2) {
            }
        }
        if (strTrim.startsWith("[") && strTrim.endsWith("]")) {
            try {
                strTrim = new JSONArray(strTrim).toString(2);
            } catch (Throwable unused3) {
            }
        }
        try {
            StackTraceElement stackTraceElement2 = Thread.currentThread().getStackTrace()[this.c];
            String className2 = stackTraceElement2.getClassName();
            str4 = String.format("(%s:%d)", className2.substring(className2.lastIndexOf(".") + 1) + ".java", Integer.valueOf(stackTraceElement2.getLineNumber()));
        } catch (Throwable unused4) {
            str4 = "";
        }
        if (th == null) {
            strTrim = strTrim + " " + str4;
        }
        this.a.a(i, str3, strTrim);
    }
}
