package com.igexin.push.g;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class s {
    public static final String a = "com.igexin.push.g.s";
    public static final String b = "utf-8";
    private static final String c = "POST";
    private static final String d = "GET";
    private static final String e = "GETUI";
    private static final int f = 30000;

    private static String a(InputStream inputStream, String str) throws Exception {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str));
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[256];
            while (true) {
                int i = bufferedReader.read(cArr);
                if (i <= 0) {
                    break;
                }
                stringWriter.write(cArr, 0, i);
            }
            return stringWriter.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return b;
        }
        for (String str2 : str.split(com.alipay.sdk.util.h.b)) {
            String strTrim = str2.trim();
            if (strTrim.startsWith("charset")) {
                String[] strArrSplit = strTrim.split("=", 2);
                return (strArrSplit.length != 2 || TextUtils.isEmpty(strArrSplit[1])) ? b : strArrSplit[1].trim();
            }
        }
        return b;
    }

    private static String a(Map<String, String> map, String str) throws Exception {
        if (map == null || map.isEmpty()) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            str = b;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                if (z) {
                    sb.append(com.alipay.sdk.sys.a.b);
                } else {
                    z = true;
                }
                sb.append(key);
                sb.append("=");
                sb.append(URLEncoder.encode(value, str));
            }
        }
        return sb.toString();
    }

    private static HttpURLConnection a(URL url, String str, String str2) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(str);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout(f);
        httpURLConnection.setReadTimeout(f);
        httpURLConnection.setRequestProperty("User-Agent", e);
        httpURLConnection.setRequestProperty("Content-Type", str2);
        httpURLConnection.setRequestProperty("HOST", url.getHost() + ":" + url.getPort());
        return httpURLConnection;
    }

    private static URL a(String str, String str2) throws Exception {
        StringBuilder sb;
        URL url = new URL(str);
        if (TextUtils.isEmpty(str2)) {
            return url;
        }
        if (TextUtils.isEmpty(url.getQuery())) {
            if (str.endsWith("?")) {
                sb = new StringBuilder();
            } else {
                sb = new StringBuilder();
                sb.append(str);
                str = "?";
            }
        } else if (str.endsWith(com.alipay.sdk.sys.a.b)) {
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder();
            sb.append(str);
            str = com.alipay.sdk.sys.a.b;
        }
        sb.append(str);
        sb.append(str2);
        return new URL(sb.toString());
    }

    private static URL a(String str, Map<String, String> map, String str2) throws Exception {
        return a(str, a(map, str2));
    }

    private static byte[] a(InputStream inputStream) throws Throwable {
        BufferedInputStream bufferedInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream(1024);
                    bArr = new byte[1024];
                } catch (Exception e2) {
                    e = e2;
                    com.igexin.c.a.c.a.a(e);
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e3) {
                            com.igexin.c.a.c.a.a(e3);
                        }
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e4) {
            e = e4;
            bufferedInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = null;
        }
        while (true) {
            int i = bufferedInputStream.read(bArr);
            if (i == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, i);
            th = th;
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e5) {
                    com.igexin.c.a.c.a.a(e5);
                }
            }
            throw th;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            bufferedInputStream.close();
        } catch (IOException e6) {
            com.igexin.c.a.c.a.a(e6);
        }
        return byteArray;
    }

    private static byte[] a(String str, String str2, String str3) throws IOException {
        return ("Content-Disposition:form-data;name=\"" + str + "\"\r\nContent-Type:text/plain\r\n\r\n" + str2).getBytes(str3);
    }

    private static byte[] a(String str, String str2, byte[] bArr, int i, int i2) throws Exception {
        HttpURLConnection httpURLConnectionA;
        OutputStream outputStream;
        OutputStream outputStream2 = null;
        try {
            httpURLConnectionA = a(new URL(str), c, str2);
            try {
                try {
                    httpURLConnectionA.setConnectTimeout(i);
                    httpURLConnectionA.setReadTimeout(i2);
                    try {
                        outputStream = httpURLConnectionA.getOutputStream();
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (IOException e3) {
                    e = e3;
                    com.igexin.c.a.c.a.a(e);
                    throw e;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e4) {
            e = e4;
        } catch (Throwable th2) {
            th = th2;
            httpURLConnectionA = null;
        }
        try {
            outputStream.write(bArr);
            byte[] bArrA = a(httpURLConnectionA);
            if (outputStream != null) {
                outputStream.close();
            }
            if (httpURLConnectionA != null) {
                httpURLConnectionA.disconnect();
            }
            return bArrA;
        } catch (Exception e5) {
            e = e5;
            com.igexin.c.a.c.a.a(e);
            throw e;
        } catch (Throwable th3) {
            th = th3;
            outputStream2 = outputStream;
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (httpURLConnectionA != null) {
                httpURLConnectionA.disconnect();
            }
            throw th;
        }
    }

    private static byte[] a(String str, Map<String, String> map, int i, int i2) throws Exception {
        return a(str, map, b, i, i2);
    }

    private static byte[] a(String str, Map<String, String> map, int i, int i2, String str2) throws Exception {
        HttpURLConnection httpURLConnectionB = b(str, map, i, i2, str2);
        try {
            try {
                return a(httpURLConnectionB);
            } catch (Exception e2) {
                throw e2;
            }
        } finally {
            if (httpURLConnectionB != null) {
                httpURLConnectionB.disconnect();
            }
        }
    }

    private static byte[] a(String str, Map<String, String> map, String str2, int i, int i2) throws Exception {
        String strConcat = "application/x-www-form-urlencoded;charset=".concat(String.valueOf(str2));
        String strA = a(map, str2);
        byte[] bytes = new byte[0];
        if (strA != null) {
            bytes = strA.getBytes(str2);
        }
        return a(str, strConcat, bytes, i, i2);
    }

    private static byte[] a(String str, Map<String, String> map, Map<String, j> map2, int i, int i2) throws Exception {
        return (map2 == null || map2.isEmpty()) ? a(str, map, b, i, i2) : a(str, map, map2, b, i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0149 A[Catch: all -> 0x01de, Exception -> 0x01e0, TryCatch #9 {Exception -> 0x01e0, all -> 0x01de, blocks: (B:7:0x0043, B:8:0x0062, B:10:0x0068, B:11:0x009e, B:12:0x00a6, B:14:0x00ac, B:16:0x00bf, B:18:0x00c3, B:20:0x00cb, B:21:0x00d3, B:23:0x00d9, B:25:0x00df, B:28:0x00e5, B:30:0x00f3, B:32:0x00f7, B:57:0x013e, B:70:0x016c, B:60:0x0149, B:63:0x0154, B:66:0x015f, B:35:0x00ff, B:37:0x0105, B:39:0x010b, B:42:0x0113, B:44:0x011a, B:46:0x011f, B:48:0x0125, B:51:0x012e, B:53:0x0134, B:71:0x016e, B:74:0x018c, B:76:0x01b0, B:77:0x01b5), top: B:118:0x0043 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] a(String str, Map<String, String> map, Map<String, j> map2, String str2, int i, int i2) throws Exception {
        HttpURLConnection httpURLConnectionA;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        OutputStream outputStream2;
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        String string = sb.toString();
        try {
            httpURLConnectionA = a(new URL(str), c, "multipart/form-data;charset=" + str2 + ";boundary=" + string);
            try {
                try {
                    httpURLConnectionA.setConnectTimeout(i);
                    httpURLConnectionA.setReadTimeout(i2);
                    try {
                        outputStream = httpURLConnectionA.getOutputStream();
                    } catch (Exception e2) {
                        e = e2;
                        outputStream2 = null;
                    }
                } catch (Throwable th) {
                    th = th;
                    outputStream = null;
                    if (outputStream != null) {
                    }
                    if (httpURLConnectionA != null) {
                    }
                    throw th;
                }
                try {
                    byte[] bytes = ("\r\n--" + string + "\r\n").getBytes(str2);
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        byte[] bytes2 = ("Content-Disposition:form-data;name=\"" + entry.getKey() + "\"\r\nContent-Type:text/plain\r\n\r\n" + entry.getValue()).getBytes(str2);
                        outputStream.write(bytes);
                        outputStream.write(bytes2);
                    }
                    for (Map.Entry<String, j> entry2 : map2.entrySet()) {
                        j value = entry2.getValue();
                        entry2.getKey();
                        if (value.a == null && value.c != null && value.c.exists()) {
                            value.a = value.c.getName();
                        }
                        String str4 = value.a;
                        if (value.b == null) {
                            byte[] bArrA = value.a();
                            if (bArrA == null || bArrA.length < 10) {
                                str3 = null;
                                value.b = !"JPG".equals(str3) ? "image/jpeg" : "GIF".equals(str3) ? "image/gif" : "PNG".equals(str3) ? "image/png" : "BMP".equals(str3) ? "image/bmp" : "application/octet-stream";
                            } else {
                                if (bArrA[0] == 71 && bArrA[1] == 73 && bArrA[2] == 70) {
                                    str3 = "GIF";
                                } else if (bArrA[1] == 80 && bArrA[2] == 78 && bArrA[3] == 71) {
                                    str3 = "PNG";
                                } else if (bArrA[6] == 74 && bArrA[7] == 70 && bArrA[8] == 73 && bArrA[9] == 70) {
                                    str3 = "JPG";
                                } else if (bArrA[0] == 66 && bArrA[1] == 77) {
                                    str3 = "BMP";
                                }
                                value.b = !"JPG".equals(str3) ? "image/jpeg" : "GIF".equals(str3) ? "image/gif" : "PNG".equals(str3) ? "image/png" : "BMP".equals(str3) ? "image/bmp" : "application/octet-stream";
                            }
                        }
                        String str5 = value.b;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Content-Disposition:form-data;name=\"");
                        sb2.append("dp_data");
                        sb2.append("\";filename=\"");
                        if (TextUtils.isEmpty(str4)) {
                            str4 = "filename";
                        }
                        sb2.append(str4);
                        sb2.append("\"\r\nContent-Type:");
                        sb2.append(str5);
                        sb2.append("\r\n\r\n");
                        byte[] bytes3 = sb2.toString().getBytes(str2);
                        outputStream.write(bytes);
                        outputStream.write(bytes3);
                        byte[] bArrA2 = value.a();
                        if (bArrA2 != null) {
                            outputStream.write(bArrA2);
                        }
                    }
                    outputStream.write(("\r\n--" + string + "--\r\n").getBytes(str2));
                    byte[] bArrA3 = a(httpURLConnectionA);
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (httpURLConnectionA != null) {
                        httpURLConnectionA.disconnect();
                    }
                    return bArrA3;
                } catch (Exception e3) {
                    e = e3;
                    outputStream2 = outputStream;
                    try {
                        com.igexin.c.a.c.a.a(e);
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStream2;
                        if (outputStream != null) {
                        }
                        if (httpURLConnectionA != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (outputStream != null) {
                    }
                    if (httpURLConnectionA != null) {
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                httpURLConnection = httpURLConnectionA;
                try {
                    throw e;
                } catch (Throwable th4) {
                    th = th4;
                    httpURLConnectionA = httpURLConnection;
                    outputStream = null;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (httpURLConnectionA != null) {
                        httpURLConnectionA.disconnect();
                    }
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            httpURLConnectionA = null;
        }
    }

    public static byte[] a(String str, byte[] bArr) throws Exception {
        return a(str, "application/octet-stream", bArr, com.igexin.push.config.c.d, com.igexin.push.config.c.d);
    }

    private static byte[] a(HttpURLConnection httpURLConnection) throws Exception {
        return httpURLConnection.getErrorStream() == null ? a(httpURLConnection.getInputStream()) : b(httpURLConnection).getBytes();
    }

    private static String b(HttpURLConnection httpURLConnection) throws Exception {
        String strA = a(httpURLConnection.getErrorStream(), a(httpURLConnection.getContentType()));
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        throw new IOException(httpURLConnection.getResponseCode() + ":" + httpURLConnection.getResponseMessage());
    }

    private static HttpURLConnection b(String str, Map<String, String> map, int i, int i2, String str2) throws Exception {
        HttpURLConnection httpURLConnectionA = a(a(str, a(map, str2)), d, "application/x-www-form-urlencoded;charset=".concat(String.valueOf(str2)));
        httpURLConnectionA.setConnectTimeout(i);
        httpURLConnectionA.setReadTimeout(i2);
        return httpURLConnectionA;
    }

    private static byte[] b(String str, String str2, String str3) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Content-Disposition:form-data;name=\"");
        sb.append("dp_data");
        sb.append("\";filename=\"");
        if (TextUtils.isEmpty(str)) {
            str = "filename";
        }
        sb.append(str);
        sb.append("\"\r\nContent-Type:");
        sb.append(str2);
        sb.append("\r\n\r\n");
        return sb.toString().getBytes(str3);
    }

    private static byte[] b(String str, Map<String, String> map, int i, int i2) throws Exception {
        return a(str, map, i, i2, b);
    }

    private static byte[] c(String str, Map<String, String> map, int i, int i2) throws Exception {
        HttpURLConnection httpURLConnectionB = b(str, map, i, i2, b);
        try {
            try {
                return a(httpURLConnectionB);
            } catch (Exception e2) {
                throw e2;
            }
        } finally {
            if (httpURLConnectionB != null) {
                httpURLConnectionB.disconnect();
            }
        }
    }
}
