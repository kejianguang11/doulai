package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class a {
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d2 A[Catch: Exception -> 0x00ce, Throwable -> 0x00da, TRY_LEAVE, TryCatch #2 {Exception -> 0x00ce, blocks: (B:51:0x00ca, B:55:0x00d2), top: B:68:0x00ca, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(Context context, String str, String str2) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        HttpURLConnection httpURLConnection;
        InputStream inputStream2;
        StringBuilder sb;
        String string = "";
        try {
            try {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestProperty("accept", "*/*");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(Constant.DEFAULT_TIMEOUT);
                httpURLConnection.setReadTimeout(Constant.DEFAULT_TIMEOUT);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.addRequestProperty("Accept-Charset", com.alipay.sdk.sys.a.m);
                if (TextUtils.isEmpty(str2)) {
                    httpURLConnection.connect();
                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream()));
                    dataOutputStream.write(str2.getBytes(com.alipay.sdk.sys.a.m));
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return null;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return null;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            bufferedReader = null;
        }
        if (httpURLConnection.getResponseCode() == 200) {
            inputStream2 = httpURLConnection.getInputStream();
            try {
                sb = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
            }
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    sb.append("\n");
                } catch (Throwable th5) {
                    th = th5;
                    Throwable th6 = th;
                    inputStream = inputStream2;
                    th = th6;
                    if (bufferedReader != null) {
                    }
                    if (inputStream != null) {
                    }
                    throw th;
                }
                e.printStackTrace();
                return string;
            }
            string = sb.toString();
        } else {
            inputStream2 = null;
            bufferedReader = null;
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
            }
        }
        if (inputStream2 != null) {
            inputStream2.close();
        }
        return string;
    }
}
