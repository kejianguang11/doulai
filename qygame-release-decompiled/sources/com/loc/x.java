package com.loc;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.loc.w;
import com.mobile.auth.BuildConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class x {
    static String a;
    private static final String[] b = {"arm64-v8a", "x86_64"};
    private static final String[] c = {"arm", "x86"};

    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            sb.append("=");
        }
        a = sb.toString();
    }

    public static w a() throws k {
        return new w.a("collection", "1.0", "AMap_collection_1.0").a(new String[]{"com.amap.api.collection"}).a();
    }

    public static String a(long j) {
        return a(j, "yyyyMMdd HH:mm:ss:SSS");
    }

    public static String a(long j, String str) {
        try {
            return new SimpleDateFormat(str, Locale.CHINA).format(new Date(j));
        } catch (Throwable th) {
            ak.a(th, "ut", "ctt");
            return null;
        }
    }

    public static String a(Context context) {
        String[] strArr;
        String str = "";
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 28) {
            try {
                ApplicationInfo applicationInfo = context.getApplicationInfo();
                Field declaredField = Class.forName(ApplicationInfo.class.getName()).getDeclaredField("primaryCpuAbi");
                declaredField.setAccessible(true);
                str = (String) declaredField.get(applicationInfo);
            } catch (Throwable th) {
                ak.a(th, "ut", "gct");
            }
        }
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                String[] strArr2 = (String[]) Build.class.getDeclaredField("SUPPORTED_ABIS").get(null);
                if (strArr2 != null && strArr2.length > 0) {
                    str = strArr2[0];
                }
                if (!TextUtils.isEmpty(str) && Arrays.asList(b).contains(str)) {
                    String str2 = context.getApplicationInfo().nativeLibraryDir;
                    if (!TextUtils.isEmpty(str2)) {
                        if (Arrays.asList(c).contains(str2.substring(str2.lastIndexOf(File.separator) + 1)) && (strArr = (String[]) Build.class.getDeclaredField("SUPPORTED_32_BIT_ABIS").get(null)) != null && strArr.length > 0) {
                            str = strArr[0];
                        }
                    }
                }
            } catch (Throwable th2) {
                ak.a(th2, "ut", "gct_p");
            }
        }
        return TextUtils.isEmpty(str) ? Build.CPU_ABI : str;
    }

    public static String a(Throwable th) throws Throwable {
        StringWriter stringWriter;
        PrintWriter printWriter;
        try {
            stringWriter = new StringWriter();
            try {
                printWriter = new PrintWriter(stringWriter);
                try {
                    try {
                        th.printStackTrace(printWriter);
                    } catch (Throwable th2) {
                        th = th2;
                        th.printStackTrace();
                        if (stringWriter != null) {
                            try {
                                stringWriter.close();
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                            }
                        }
                        if (printWriter != null) {
                            try {
                                printWriter.close();
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                        }
                        return null;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
                printWriter = null;
            }
        } catch (Throwable th7) {
            th = th7;
            stringWriter = null;
            printWriter = null;
        }
        while (true) {
            th = th.getCause();
            if (th == null) {
                break;
            }
            th.printStackTrace(printWriter);
            th = th5;
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (Throwable th8) {
                    th8.printStackTrace();
                }
            }
            if (printWriter == null) {
                throw th;
            }
            try {
                printWriter.close();
                throw th;
            } catch (Throwable th9) {
                th9.printStackTrace();
                throw th;
            }
        }
        String string = stringWriter.toString();
        try {
            stringWriter.close();
        } catch (Throwable th10) {
            th10.printStackTrace();
        }
        try {
            printWriter.close();
        } catch (Throwable th11) {
            th11.printStackTrace();
        }
        return string;
    }

    public static String a(Map<String, String> map) {
        String value;
        if (map.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (z) {
                    z = false;
                    stringBuffer.append(entry.getKey());
                    stringBuffer.append("=");
                    value = entry.getValue();
                } else {
                    stringBuffer.append(com.alipay.sdk.sys.a.b);
                    stringBuffer.append(entry.getKey());
                    stringBuffer.append("=");
                    value = entry.getValue();
                }
                stringBuffer.append(value);
            }
        } catch (Throwable th) {
            ak.a(th, "ut", "abP");
        }
        return stringBuffer.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        try {
            return new String(bArr, com.alipay.sdk.sys.a.m);
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr);
        }
    }

    public static Method a(Class cls, String str, Class<?>... clsArr) {
        try {
            return cls.getDeclaredMethod(c(str), clsArr);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Calendar a(String str, String str2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, Locale.CHINA);
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(simpleDateFormat.parse(str));
            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), calendar2.get(11), calendar2.get(12), calendar2.get(13));
            return calendar;
        } catch (ParseException e) {
            ak.a(e, "ut", "ctt");
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00a9 A[PHI: r5
      0x00a9: PHI (r5v1 java.lang.String) = 
      (r5v0 java.lang.String)
      (r5v2 java.lang.String)
      (r5v2 java.lang.String)
      (r5v2 java.lang.String)
      (r5v2 java.lang.String)
     binds: [B:33:0x00a9, B:20:0x0086, B:25:0x009a, B:27:0x00a0, B:17:0x0066] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(Context context, String str, String str2, JSONObject jSONObject) {
        String strC;
        String string = "";
        String strE = l.e(context);
        String strA = s.a(strE);
        String str3 = "";
        String strA2 = l.a(context);
        try {
            if (jSONObject.has("info")) {
                string = jSONObject.getString("info");
                str3 = "请在高德开放平台官网中搜索\"" + string + "\"相关内容进行解决";
            }
        } catch (Throwable unused) {
        }
        if ("INVALID_USER_SCODE".equals(string)) {
            String string2 = jSONObject.has("sec_code") ? jSONObject.getString("sec_code") : "";
            String string3 = jSONObject.has("sec_code_debug") ? jSONObject.getString("sec_code_debug") : "";
            if (strA.equals(string2) || strA.equals(string3)) {
                strC = c("C6K+35Zyo6auY5b635byA5pS+5bmz5Y+w5a6Y572R5Lit5pCc57Si") + "\"请求内容过长导致业务调用失败\"相关内容进行解决";
            } else {
                strC = str3;
            }
        } else if ("INVALID_USER_KEY".equals(string)) {
            String string4 = jSONObject.has("key") ? jSONObject.getString("key") : "";
            if (string4.length() > 0 && !strA2.equals(string4)) {
                strC = c("C6K+35Zyo6auY5b635byA5pS+5bmz5Y+w5a6Y572R5LiK5Y+R6LW35oqA5pyv5ZKo6K+i5bel5Y2V4oCUPui0puWPt+S4jktleemXrumimO+8jOWSqOivoklOVkFMSURfVVNFUl9LRVnlpoLkvZXop6PlhrM=");
            }
        }
        Log.i("authErrLog", a);
        Log.i("authErrLog", "                                   鉴权错误信息                                  ");
        Log.i("authErrLog", a);
        f("SHA1Package:".concat(String.valueOf(strE)));
        f("key:".concat(String.valueOf(strA2)));
        f("csid:".concat(String.valueOf(str)));
        f("gsid:".concat(String.valueOf(str2)));
        f("json:" + jSONObject.toString());
        Log.i("authErrLog", "                                                                               ");
        Log.i("authErrLog", strC);
        Log.i("authErrLog", a);
    }

    public static void a(ByteArrayOutputStream byteArrayOutputStream, byte b2, byte[] bArr) {
        try {
            byteArrayOutputStream.write(new byte[]{b2});
            int i = b2 & 255;
            if (i < 255 && i > 0) {
                byteArrayOutputStream.write(bArr);
            } else if (i == 255) {
                byteArrayOutputStream.write(bArr, 0, 255);
            }
        } catch (IOException e) {
            ak.a(e, "ut", "wFie");
        }
    }

    public static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            try {
                byteArrayOutputStream.write(new byte[]{0});
                return;
            } catch (IOException e) {
                ak.a(e, "ut", "wsf");
                return;
            }
        }
        int length = str.length();
        if (length > 255) {
            length = 255;
        }
        a(byteArrayOutputStream, (byte) length, a(str));
    }

    public static boolean a(Context context, String str) {
        if (context == null || context.checkCallingOrSelfPermission(str) != 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23 && context.getApplicationInfo().targetSdkVersion >= 23) {
            try {
                if (((Integer) context.getClass().getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() != 0) {
                    return false;
                }
            } catch (Throwable th) {
                ak.a(th, "ut", "cpm");
            }
        }
        return true;
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return jSONObject != null && jSONObject.has(str);
    }

    public static byte[] a(int i) {
        return new byte[]{(byte) (i / 256), (byte) (i % 256)};
    }

    public static byte[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        try {
            return str.getBytes(com.alipay.sdk.sys.a.m);
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    public static w b() throws k {
        return new w.a("co", "1.0.0", "AMap_co_1.0.0").a(new String[]{"com.amap.co", "com.amap.opensdk.co", "com.amap.location"}).a();
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        String strC = p.c(a(str));
        try {
            return ((char) ((strC.length() % 26) + 65)) + strC;
        } catch (Throwable th) {
            ak.a(th, "ut", "tsfb64");
            return "";
        }
    }

    public static String b(Map<String, String> map) {
        String string;
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(com.alipay.sdk.sys.a.b);
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            string = sb.toString();
        } else {
            string = null;
        }
        return e(string);
    }

    public static boolean b(Context context) {
        return aj.a(context);
    }

    public static byte[] b(byte[] bArr) {
        try {
            return h(bArr);
        } catch (Throwable th) {
            ak.a(th, "ut", "gZp");
            return new byte[0];
        }
    }

    public static String c(String str) {
        return str.length() < 2 ? "" : p.a(str.substring(1));
    }

    public static byte[] c() {
        try {
            String[] strArrSplit = new StringBuffer("16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87").reverse().toString().split(com.igexin.push.core.b.an);
            byte[] bArr = new byte[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                bArr[i] = Byte.parseByte(strArrSplit[i]);
            }
            String[] strArrSplit2 = new StringBuffer(new String(p.b(new String(bArr)))).reverse().toString().split(com.igexin.push.core.b.an);
            byte[] bArr2 = new byte[strArrSplit2.length];
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                bArr2[i2] = Byte.parseByte(strArrSplit2[i2]);
            }
            return bArr2;
        } catch (Throwable th) {
            ak.a(th, "ut", "gIV");
            return new byte[16];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.ByteArrayOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v4 */
    public static byte[] c(byte[] bArr) throws Throwable {
        ?? r2;
        ZipOutputStream zipOutputStream;
        byte[] byteArray;
        Object obj;
        if (bArr != null) {
            ?? length = bArr.length;
            try {
                if (length != 0) {
                    try {
                        length = new ByteArrayOutputStream();
                        try {
                            zipOutputStream = new ZipOutputStream(length);
                            try {
                                zipOutputStream.putNextEntry(new ZipEntry(BuildConfig.FLAVOR_type));
                                zipOutputStream.write(bArr);
                                zipOutputStream.closeEntry();
                                zipOutputStream.finish();
                                byteArray = length.toByteArray();
                                try {
                                    zipOutputStream.close();
                                    obj = zipOutputStream;
                                } catch (Throwable th) {
                                    ak.a(th, "ut", "zp1");
                                    obj = "ut";
                                }
                                try {
                                    length.close();
                                    length = length;
                                    r2 = obj;
                                } catch (Throwable th2) {
                                    ak.a(th2, "ut", "zp2");
                                    length = "ut";
                                    r2 = "zp2";
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                ak.a(th, "ut", "zp");
                                Object obj2 = zipOutputStream;
                                if (zipOutputStream != null) {
                                    try {
                                        zipOutputStream.close();
                                        obj2 = zipOutputStream;
                                    } catch (Throwable th4) {
                                        ak.a(th4, "ut", "zp1");
                                        obj2 = "ut";
                                    }
                                }
                                if (length != 0) {
                                    try {
                                        length.close();
                                    } catch (Throwable th5) {
                                        length = "ut";
                                        obj2 = "zp2";
                                        ak.a(th5, "ut", "zp2");
                                    }
                                }
                                byteArray = null;
                                length = length;
                                r2 = obj2;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            zipOutputStream = null;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        length = 0;
                        r2 = 0;
                    }
                    return byteArray;
                }
            } catch (Throwable th8) {
                th = th8;
            }
        }
        return null;
    }

    static PublicKey d() throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(p.b("MIICnjCCAgegAwIBAgIJAJ0Pdzos7ZfYMA0GCSqGSIb3DQEBBQUAMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjAeFw0xMzA4MTUwNzU2NTVaFw0yMzA4MTMwNzU2NTVaMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA8eWAyHbFPoFPfdx5AD+D4nYFq4dbJ1p7SIKt19Oz1oivF/6H43v5Fo7s50pD1UF8+Qu4JoUQxlAgOt8OCyQ8DYdkaeB74XKb1wxkIYg/foUwN1CMHPZ9O9ehgna6K4EJXZxR7Y7XVZnbjHZIVn3VpPU/Rdr2v37LjTw+qrABJxMCAwEAAaNQME4wHQYDVR0OBBYEFOM/MLGP8xpVFuVd+3qZkw7uBvOTMB8GA1UdIwQYMBaAFOM/MLGP8xpVFuVd+3qZkw7uBvOTMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEA4LY3g8aAD8JkxAOqUXDDyLuCCGOc2pTIhn0TwMNaVdH4hZlpTeC/wuRD5LJ0z3j+IQ0vLvuQA5uDjVyEOlBrvVIGwSem/1XGUo13DfzgAJ5k1161S5l+sFUo5TxpHOXr8Z5nqJMjieXmhnE/I99GFyHpQmw4cC6rhYUhdhtg+Zk="));
        } catch (Throwable th) {
            th = th;
            byteArrayInputStream = null;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(c("IWC41MDk"));
            KeyFactory keyFactory = KeyFactory.getInstance(c("EUlNB"));
            Certificate certificateGenerateCertificate = certificateFactory.generateCertificate(byteArrayInputStream);
            if (certificateGenerateCertificate != null && keyFactory != null) {
                PublicKey publicKeyGeneratePublic = keyFactory.generatePublic(new X509EncodedKeySpec(certificateGenerateCertificate.getPublicKey().getEncoded()));
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
                return publicKeyGeneratePublic;
            }
            try {
                byteArrayInputStream.close();
            } catch (Throwable th3) {
                th3.printStackTrace();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th5) {
                    th5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] d(String str) {
        if (str.length() % 2 != 0) {
            str = "0".concat(String.valueOf(str));
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static byte[] d(byte[] bArr) {
        try {
            return h(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return new byte[0];
        }
    }

    private static String e(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
            Arrays.sort(strArrSplit);
            StringBuffer stringBuffer = new StringBuffer();
            for (String str2 : strArrSplit) {
                stringBuffer.append(str2);
                stringBuffer.append(com.alipay.sdk.sys.a.b);
            }
            String string = stringBuffer.toString();
            if (string.length() > 1) {
                return (String) string.subSequence(0, string.length() - 1);
            }
        } catch (Throwable th) {
            ak.a(th, "ut", "sPa");
        }
        return str;
    }

    static String e(byte[] bArr) {
        try {
            return g(bArr);
        } catch (Throwable th) {
            ak.a(th, "ut", "h2s");
            return null;
        }
    }

    static String f(byte[] bArr) {
        try {
            return g(bArr);
        } catch (Throwable th) {
            ak.a(th, "ut", "csb2h");
            return null;
        }
    }

    private static void f(String str) {
        int i;
        while (true) {
            if (str.length() < 78) {
                break;
            }
            Log.i("authErrLog", "|" + str.substring(0, 78) + "|");
            str = str.substring(78);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append(str);
        for (i = 0; i < 78 - str.length(); i++) {
            sb.append(" ");
        }
        sb.append("|");
        Log.i("authErrLog", sb.toString());
    }

    public static String g(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null) {
            return null;
        }
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(String.valueOf(hexString));
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] h(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream;
        GZIPOutputStream gZIPOutputStream2 = null;
        gZIPOutputStream2 = null;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        if (bArr == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(bArr);
                    gZIPOutputStream.finish();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    gZIPOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        th = th2;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        gZIPOutputStream2 = gZIPOutputStream;
                        if (gZIPOutputStream2 != null) {
                            gZIPOutputStream2.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                gZIPOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream = null;
        }
    }
}
