package org.cocos2dx.lib;

import android.util.Log;
import com.alipay.sdk.util.h;
import com.igexin.push.a;
import com.igexin.push.core.b;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxHttpURLConnection {
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static String TAG = "Cocos2dxHttpURLConnection";

    static void addRequestHeader(HttpURLConnection httpURLConnection, String str, String str2) {
        httpURLConnection.setRequestProperty(str, str2);
    }

    public static String combinCookies(List<String> list, String str) {
        int i;
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        String str2 = null;
        String str3 = null;
        String strStr2Seconds = null;
        String str4 = "FALSE";
        String str5 = "/";
        String str6 = str;
        while (it.hasNext()) {
            String[] strArrSplit = it.next().split(h.b);
            int length = strArrSplit.length;
            int i2 = 0;
            String str7 = str3;
            String str8 = str2;
            String str9 = str4;
            String str10 = str5;
            String str11 = str6;
            int i3 = 0;
            while (i3 < length) {
                String str12 = strArrSplit[i3];
                int iIndexOf = str12.indexOf("=");
                if (-1 == iIndexOf) {
                    i = i2;
                } else {
                    String[] strArr = new String[2];
                    strArr[i2] = str12.substring(i2, iIndexOf);
                    strArr[1] = str12.substring(iIndexOf + 1);
                    if ("expires".equalsIgnoreCase(strArr[i2].trim())) {
                        strStr2Seconds = str2Seconds(strArr[1].trim());
                    } else if ("path".equalsIgnoreCase(strArr[0].trim())) {
                        str10 = strArr[1];
                    } else if ("secure".equalsIgnoreCase(strArr[0].trim())) {
                        str9 = strArr[1];
                    } else if ("domain".equalsIgnoreCase(strArr[0].trim())) {
                        str11 = strArr[1];
                    } else {
                        i = 0;
                        if (!"version".equalsIgnoreCase(strArr[0].trim()) && !"max-age".equalsIgnoreCase(strArr[0].trim())) {
                            str8 = strArr[0];
                            str7 = strArr[1];
                        }
                    }
                    i = 0;
                }
                i3++;
                i2 = i;
            }
            str6 = str11 == null ? a.i : str11;
            sb.append(str6);
            sb.append('\t');
            sb.append("FALSE");
            sb.append('\t');
            sb.append(str10);
            sb.append('\t');
            sb.append(str9);
            sb.append('\t');
            sb.append(strStr2Seconds);
            sb.append("\t");
            sb.append(str8);
            sb.append("\t");
            sb.append(str7);
            sb.append('\n');
            str5 = str10;
            str4 = str9;
            str2 = str8;
            str3 = str7;
        }
        return sb.toString();
    }

    static int connect(HttpURLConnection httpURLConnection) {
        try {
            httpURLConnection.connect();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "connect" + e.toString());
            return 1;
        }
    }

    static HttpURLConnection createHttpURLConnection(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "createHttpURLConnection:" + e.toString());
            return null;
        }
    }

    static void disconnect(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }

    static int getResponseCode(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getResponseCode:" + e.toString());
            return 0;
        }
    }

    static byte[] getResponseContent(HttpURLConnection httpURLConnection) {
        String str;
        StringBuilder sb;
        String str2;
        InputStream errorStream;
        try {
            errorStream = httpURLConnection.getInputStream();
            String contentEncoding = httpURLConnection.getContentEncoding();
            if (contentEncoding != null) {
                if (contentEncoding.equalsIgnoreCase("gzip")) {
                    errorStream = new GZIPInputStream(httpURLConnection.getInputStream());
                } else if (contentEncoding.equalsIgnoreCase("deflate")) {
                    errorStream = new InflaterInputStream(httpURLConnection.getInputStream());
                }
            }
        } catch (IOException unused) {
            errorStream = httpURLConnection.getErrorStream();
        } catch (Exception e) {
            e = e;
            e.printStackTrace();
            str = TAG;
            sb = new StringBuilder();
            str2 = "1 getResponseContent: ";
            sb.append(str2);
            sb.append(e.toString());
            Log.e(str, sb.toString());
            return null;
        }
        try {
            byte[] bArr = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = errorStream.read(bArr, 0, 1024);
                if (i == -1) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            str = TAG;
            sb = new StringBuilder();
            str2 = "2 getResponseContent:";
            sb.append(str2);
            sb.append(e.toString());
            Log.e(str, sb.toString());
            return null;
        }
    }

    static String getResponseHeaderByIdx(HttpURLConnection httpURLConnection, int i) {
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null) {
            return null;
        }
        int i2 = 0;
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if (i2 == i) {
                String key = entry.getKey();
                if (key == null) {
                    return listToString(entry.getValue(), b.an) + "\n";
                }
                return key + ":" + listToString(entry.getValue(), b.an) + "\n";
            }
            i2++;
        }
        return null;
    }

    static String getResponseHeaderByKey(HttpURLConnection httpURLConnection, String str) {
        Map<String, List<String>> headerFields;
        if (str == null || (headerFields = httpURLConnection.getHeaderFields()) == null) {
            return null;
        }
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if (str.equalsIgnoreCase(entry.getKey())) {
                return "set-cookie".equalsIgnoreCase(str) ? combinCookies(entry.getValue(), httpURLConnection.getURL().getHost()) : listToString(entry.getValue(), b.an);
            }
        }
        return null;
    }

    static int getResponseHeaderByKeyInt(HttpURLConnection httpURLConnection, String str) {
        String headerField = httpURLConnection.getHeaderField(str);
        if (headerField == null) {
            return 0;
        }
        return Integer.parseInt(headerField);
    }

    static String getResponseHeaders(HttpURLConnection httpURLConnection) {
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null) {
            return null;
        }
        String str = "";
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            String key = entry.getKey();
            str = key == null ? str + listToString(entry.getValue(), b.an) + "\n" : str + key + ":" + listToString(entry.getValue(), b.an) + "\n";
        }
        return str;
    }

    static String getResponseMessage(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
            String string = e.toString();
            Log.e(TAG, "getResponseMessage: " + string);
            return string;
        }
    }

    public static String listToString(List<String> list, String str) {
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (String str2 : list) {
            if (z) {
                sb.append(str);
            }
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            z = true;
        }
        return sb.toString();
    }

    static void sendRequest(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if (bArr != null) {
                outputStream.write(bArr);
                outputStream.flush();
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "sendRequest:" + e.toString());
        }
    }

    static void setReadAndConnectTimeout(HttpURLConnection httpURLConnection, int i, int i2) {
        httpURLConnection.setReadTimeout(i);
        httpURLConnection.setConnectTimeout(i2);
    }

    static void setRequestMethod(HttpURLConnection httpURLConnection, String str) {
        try {
            httpURLConnection.setRequestMethod(str);
            if (str.equalsIgnoreCase(POST_METHOD) || str.equalsIgnoreCase(PUT_METHOD)) {
                httpURLConnection.setDoOutput(true);
            }
        } catch (ProtocolException e) {
            Log.e(TAG, "setRequestMethod:" + e.toString());
        }
    }

    static void setVerifySSL(HttpURLConnection httpURLConnection, String str) {
        BufferedInputStream bufferedInputStream;
        if (httpURLConnection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
            try {
                if (str.startsWith("/")) {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
                } else {
                    bufferedInputStream = new BufferedInputStream(Cocos2dxHelper.getActivity().getAssets().open(str.substring("assets/".length())));
                }
                Certificate certificateGenerateCertificate = CertificateFactory.getInstance("X.509").generateCertificate(bufferedInputStream);
                System.out.println("ca=" + ((X509Certificate) certificateGenerateCertificate).getSubjectDN());
                bufferedInputStream.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                keyStore.setCertificateEntry(b.ab, certificateGenerateCertificate);
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
                httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "setVerifySSL:" + e.toString());
            }
        }
    }

    private static String str2Seconds(String str) {
        long timeInMillis;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("EEE, dd-MMM-yy hh:mm:ss zzz", Locale.US).parse(str));
            timeInMillis = calendar.getTimeInMillis() / 1000;
        } catch (ParseException e) {
            Log.e(TAG, "str2Seconds: " + e.toString());
            timeInMillis = 0;
        }
        return Long.toString(timeInMillis);
    }
}
