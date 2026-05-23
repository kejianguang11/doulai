package com.gcloudsdk.apollo.apollovoice.httpclient;

import android.content.Context;
import android.os.Build;
import com.gcloudsdk.apollo.ApolloVoiceLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes.dex */
public class HttpsUtils {
    private static String PATTERN_IP = "(\\d*\\.){3}\\d*";
    private static Context ctx;

    public static boolean connnectWithIP(String str) {
        Pattern patternCompile = Pattern.compile(PATTERN_IP);
        String host = getHost(str);
        if (host == null || !patternCompile.matcher(host).find()) {
            return false;
        }
        ApolloVoiceLog.LogI("the url connect with ip: " + str);
        return true;
    }

    private static String getHost(String str) {
        try {
            return new URL(str).getHost();
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public static HostnameVerifier getHostNameVerify(String str) {
        String host = getHost(str);
        if (host == null || host.length() == 0) {
            return null;
        }
        ApolloVoiceLog.LogI("url: " + str + " host: " + host);
        return new FastHostnameVerifier(host);
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0149: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:106:0x0149 */
    /* JADX WARN: Removed duplicated region for block: B:118:0x014c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static SSLSocketFactory getSocketFactory(String str) throws Throwable {
        InputStream inputStreamOpen;
        InputStream inputStream;
        Certificate certificateGenerateCertificate;
        SSLContext sSLContext;
        CertificateFactory certificateFactory;
        InputStream inputStream2 = null;
        if (ctx == null) {
            ApolloVoiceLog.LogE("getSocketFactory but the context is null");
            return null;
        }
        if (str == null || str.length() == 0) {
            ApolloVoiceLog.LogE("The parameter cerName is null or empty.");
            return null;
        }
        ApolloVoiceLog.LogI("Load CA from an InputStream.");
        try {
            try {
                certificateFactory = Build.VERSION.SDK_INT >= 28 ? CertificateFactory.getInstance("X.509") : CertificateFactory.getInstance("X.509", "BC");
                inputStreamOpen = ctx.getResources().getAssets().open(str);
                try {
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (IOException e2) {
                            ApolloVoiceLog.LogE("Close inputStream error.");
                            e2.printStackTrace();
                        }
                    }
                    return null;
                } catch (NoSuchProviderException e3) {
                    e = e3;
                    e.printStackTrace();
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (IOException e4) {
                            ApolloVoiceLog.LogE("Close inputStream error.");
                            e4.printStackTrace();
                        }
                    }
                    certificateGenerateCertificate = null;
                }
            } catch (Throwable th) {
                th = th;
                inputStream2 = inputStream;
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e5) {
                        ApolloVoiceLog.LogE("Close inputStream error.");
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            inputStreamOpen = null;
        } catch (NoSuchProviderException e7) {
            e = e7;
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            if (inputStream2 != null) {
            }
            throw th;
        }
        if (inputStreamOpen == null) {
            ApolloVoiceLog.LogE("Read certificate from InputStream failed! " + str);
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e8) {
                    ApolloVoiceLog.LogE("Close inputStream error.");
                    e8.printStackTrace();
                }
            }
            return null;
        }
        certificateGenerateCertificate = certificateFactory.generateCertificate(inputStreamOpen);
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (IOException e9) {
                ApolloVoiceLog.LogE("Close inputStream error.");
                e9.printStackTrace();
            }
        }
        ApolloVoiceLog.LogI("Create a KeyStore contains our trusted CA.");
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            if (keyStore == null) {
                ApolloVoiceLog.LogE("The keyStore is null.");
                return null;
            }
            if (certificateGenerateCertificate == null) {
                return null;
            }
            if (str.contains(".")) {
                str = str.split("\\.")[0];
            }
            try {
                keyStore.load(null, null);
                keyStore.setCertificateEntry(str, certificateGenerateCertificate);
                ApolloVoiceLog.LogE("Create a TrustManager that trusts the CA in our KeyStore.");
                try {
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(keyStore);
                    ApolloVoiceLog.LogI("Create a SSLContext that uses our TrustManager.");
                    try {
                        sSLContext = SSLContext.getInstance("TLS");
                        try {
                            sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
                        } catch (KeyManagementException e10) {
                            e = e10;
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e11) {
                            e = e11;
                            e.printStackTrace();
                        }
                    } catch (KeyManagementException e12) {
                        e = e12;
                        sSLContext = null;
                    } catch (NoSuchAlgorithmException e13) {
                        e = e13;
                        sSLContext = null;
                    }
                    if (sSLContext != null) {
                        return sSLContext.getSocketFactory();
                    }
                    return null;
                } catch (NullPointerException e14) {
                    e14.printStackTrace();
                    return null;
                } catch (KeyStoreException e15) {
                    e15.printStackTrace();
                    return null;
                } catch (NoSuchAlgorithmException e16) {
                    e16.printStackTrace();
                    return null;
                }
            } catch (IOException e17) {
                ApolloVoiceLog.LogE("Load KeyStore error.");
                e17.printStackTrace();
                return null;
            } catch (KeyStoreException e18) {
                ApolloVoiceLog.LogE("SetCertificateEntry error.");
                e18.printStackTrace();
                return null;
            } catch (NoSuchAlgorithmException e19) {
                ApolloVoiceLog.LogE("Load KeyStore error.");
                e19.printStackTrace();
                return null;
            }
        } catch (KeyStoreException e20) {
            ApolloVoiceLog.LogE("Get keyStoreType error.");
            e20.printStackTrace();
            return null;
        }
    }

    public static void setContext(Context context) {
        ctx = context;
    }
}
