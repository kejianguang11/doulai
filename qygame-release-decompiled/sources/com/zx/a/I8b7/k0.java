package com.zx.a.I8b7;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.igexin.sdk.PushConsts;
import com.zx.a.I8b7.g1;
import com.zx.a.I8b7.p2;
import com.zx.module.annotation.Java2C;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class k0 {
    public static s2 a;
    public static final String[] b;

    static {
        g();
        b = h();
    }

    public static String a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer);
        stringBuffer.append("BE");
        stringBuffer.append("GIN ");
        stringBuffer.append("CE");
        stringBuffer.append("RT");
        stringBuffer.append("IFIC");
        stringBuffer.append("ATE");
        a(stringBuffer);
        stringBuffer.append("\n");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!line.trim().equals("")) {
                    sb.append(line.substring(0, line.length() - 5));
                }
            }
        } catch (Exception e) {
            v2.a(e);
        }
        stringBuffer.append(sb.toString());
        stringBuffer.append("\n");
        a(stringBuffer);
        stringBuffer.append("EN");
        stringBuffer.append("D ");
        stringBuffer.append("CE");
        stringBuffer.append("RTI");
        stringBuffer.append("FIC");
        stringBuffer.append("ATE");
        a(stringBuffer);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public static JSONObject a() throws JSONException {
        String string;
        Exception e;
        MessageDigest messageDigest;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("appPkg", q3.g);
        try {
            Signature[] signatureArrA = d.a(q3.g);
            if (signatureArrA == null) {
                string = "error!";
            } else {
                string = "error!";
                for (Signature signature : signatureArrA) {
                    try {
                        byte[] byteArray = signature.toByteArray();
                        try {
                            messageDigest = MessageDigest.getInstance("MD5");
                        } catch (NoSuchAlgorithmException e2) {
                            v2.a(e2);
                        }
                        if (messageDigest != null) {
                            byte[] bArrDigest = messageDigest.digest(byteArray);
                            StringBuilder sb = new StringBuilder();
                            for (byte b2 : bArrDigest) {
                                sb.append(Integer.toHexString((b2 & 255) | 256).substring(1, 3));
                            }
                            string = sb.toString();
                        } else {
                            string = "error!";
                        }
                    } catch (Exception e3) {
                        e = e3;
                        v2.a(e);
                        jSONObject.put("appSign", string);
                        jSONObject.put("appId", q3.f);
                        return jSONObject;
                    }
                }
            }
        } catch (Exception e4) {
            string = "error!";
            e = e4;
        }
        jSONObject.put("appSign", string);
        jSONObject.put("appId", q3.f);
        return jSONObject;
    }

    public static void a(StringBuffer stringBuffer) {
        for (int i = 0; i < 5; i++) {
            stringBuffer.append("-");
        }
    }

    public static void a(HttpURLConnection httpURLConnection) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(7000);
        httpURLConnection.setReadTimeout(7000);
        if (httpURLConnection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
            httpsURLConnection.setSSLSocketFactory(c());
            httpsURLConnection.setHostnameVerifier(r2.a);
        }
    }

    public static HashMap<String, String> b(String str) {
        HashMap<String, String> map = new HashMap<>();
        map.put("UDID-LID", q3.a(q3.h));
        String str2 = q3.i;
        if (str2 == null) {
            str2 = "";
        }
        map.put("UDID-ZID", str2);
        try {
            String str3 = new String(Base64.encode(a().toString().getBytes(StandardCharsets.UTF_8), 2), StandardCharsets.UTF_8);
            v2.a("ZXID 请求 header 中的 appInfo: " + str3);
            map.put("UDID-APP-INFO", str3);
            String str4 = new String(Base64.encode(e().toString().getBytes(StandardCharsets.UTF_8), 2), StandardCharsets.UTF_8);
            v2.a("ZXID 请求 header 中的 sdkInfoBase: " + str4);
            map.put("UDID-SDK-INFO-BASE", str4);
        } catch (Exception e) {
            StringBuilder sbA = j3.a("ZXID 请求 header 创建异常: ");
            sbA.append(e.getMessage());
            v2.b(sbA.toString());
        }
        map.put("UDID-PROTOCOL", "v3.0.0");
        map.put("UDID-KEY", str);
        return map;
    }

    public static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("os", "Android");
            jSONObject.put("isp", b4.c(q3.a));
            jSONObject.put("brand", Build.MANUFACTURER.toUpperCase());
            jSONObject.put("applicationId", q3.g);
            jSONObject.put("country", Locale.getDefault().getCountry());
            jSONObject.put("language", Locale.getDefault().getLanguage());
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("arch", b4.c());
            jSONObject.put("androidVersion", b4.a("59"));
        } catch (JSONException e) {
            StringBuilder sbA = j3.a("ZXID 构建deviceInfo异常:");
            sbA.append(e.getMessage());
            v2.b(sbA.toString());
        }
        return jSONObject;
    }

    public static SSLSocketFactory c() throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        X509Certificate x509CertificateF = f();
        if (x509CertificateF == null) {
            throw new CertificateException("getCurEnvCA is null");
        }
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry(com.igexin.push.core.b.ab, x509CertificateF);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        int i = Build.VERSION.SDK_INT;
        sSLContext.init(null, (i < 24 || i > 26) ? trustManagerFactory.getTrustManagers() : new TrustManager[]{new j0()}, null);
        return sSLContext.getSocketFactory();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [int] */
    public static JSONObject d() {
        ?? r2;
        ?? E = e();
        try {
            z3 z3Var = p2.a.a.a;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("debug", b4.h() ? 1 : 0);
            jSONObject.put("permission", z3Var.h());
            jSONObject.put("enable", z3Var.f());
            jSONObject.put("showPermissionDialog", z3Var.e());
            E.put("userSettings", jSONObject);
            g1 g1Var = g1.a.a;
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("pts", g1Var.a);
                jSONObject2.put(PushConsts.KEY_SERVICE_PIT, g1Var.b);
                jSONObject2.put("rc", g1Var.c);
            } catch (Exception e) {
                v2.a(e);
            }
            E.put("processInfo", jSONObject2);
            try {
                r2 = z1.b(q3.a).getBoolean("ZX_IS_PRIVACY");
            } catch (PackageManager.NameNotFoundException e2) {
                v2.a(e2);
                r2 = 0;
            }
            E.put("privacy", r2);
            E.put("appIds", z1.a());
        } catch (JSONException e3) {
            StringBuilder sbA = j3.a("ZXID 构建SDKInfo异常:");
            sbA.append(e3.getMessage());
            v2.b(sbA.toString());
        }
        return E;
    }

    public static JSONObject e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", q3.b);
            jSONObject.put("configVersion", q3.q);
            if (TextUtils.equals("core-d", q3.c)) {
                jSONObject.put("versiond", q3.d);
            }
            jSONObject.put("channelId", q3.e);
            jSONObject.put("arch", Build.CPU_ABI);
        } catch (JSONException e) {
            StringBuilder sbA = j3.a("ZXID 构建SDKInfoBase异常:");
            sbA.append(e.getMessage());
            v2.b(sbA.toString());
        }
        return jSONObject;
    }

    public static X509Certificate f() throws CertificateException {
        return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(a(b[0]).getBytes()));
    }

    @Java2C.Method2C
    private static native String[] g();

    @Java2C.Method2C
    private static native String[] h();
}
