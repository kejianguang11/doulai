package com.gme.liteav.base.http;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.core.view.PointerIconCompat;
import com.gme.av.sdk.AVError;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import com.gme.liteav.base.util.HttpDnsUtil;
import com.gme.liteav.base.util.LiteavLog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import org.cocos2dx.okhttp3.internal.http.StatusLine;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class HttpClientAndroid {
    private static final int ERROR_CODE_INVALID_REQUEST = 0;
    private static final String HTTPS_PREFIX = "https://";
    private static final String HTTP_PREFIX = "http://";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final int READ_STREAM_SIZE = 8192;
    private static final int REDIRECT_REQUEST_MAX = 3;
    private static final String TAG = "HttpClientAndroid";
    private static final Object mLock = new Object();
    private HttpURLConnection mConnection;
    private b mHttpConfig;
    private final Handler mHttpHandler;
    private String mLastRequestURL;
    private long mNativeHttpClientAndroidJni;
    private final ConcurrentHashMap<Long, e> mRunningRequestMap = new ConcurrentHashMap<>();
    private final Object mLocker = new Object();
    private volatile c mInternalState = c.NONE;
    private long mTotalReadBytes = 0;
    private long mStartReadTime = 0;
    byte[] a = new byte[8192];
    private boolean mPausedRepeatDownloading = false;
    private d mReallyNetworkChannel = d.DEFAULT;
    private ConnectivityManager.NetworkCallback mNetworkCallback = null;
    private h mRepeatDownloadingStatusCode = h.kUnknownError;
    private ByteBuffer mRepeatByteBuffer = null;

    static class a extends Authenticator {
        String a;
        String b;

        a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // java.net.Authenticator
        protected final PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.a, this.b.toCharArray());
        }
    }

    public static class b {
        int a;
        int b;
        int c;
        boolean d;
        int e;
        int f;
        String g;
        String h;
        String i;
        d j;

        b(int i, int i2, int i3, boolean z, int i4, int i5, String str, String str2, String str3, d dVar) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = z;
            this.e = i4;
            this.f = i5;
            this.g = str;
            this.h = str2;
            this.i = str3;
            this.j = dVar;
        }
    }

    enum c {
        NONE,
        RUNNING_REPEAT,
        RUNNING_ONCE
    }

    public enum d {
        DEFAULT(0),
        WIFI(1),
        CELLULAR(2);

        int e;

        d(int i) {
            this.e = i;
        }

        public static d a(int i) {
            for (d dVar : values()) {
                if (dVar.e == i) {
                    return dVar;
                }
            }
            LiteavLog.i(HttpClientAndroid.TAG, "Invalid value:".concat(String.valueOf(i)));
            return DEFAULT;
        }
    }

    public static class e {
        long a;
        String b;
        String c;
        byte[] d;
        Map<String, String> e;
        int f;
        String g;
        boolean h;
        String i;
        byte[] j;
        byte[] k;

        e(String str, String str2, byte[] bArr, Map<String, String> map, boolean z) {
            this(str, str2, bArr, map, z, "", null, null);
        }

        e(String str, String str2, byte[] bArr, Map<String, String> map, boolean z, String str3, byte[] bArr2, byte[] bArr3) {
            this.h = true;
            this.b = str;
            this.c = str2;
            this.d = bArr;
            this.e = map;
            this.f = 0;
            this.g = "";
            this.h = z;
            this.i = str3;
            this.j = bArr2;
            this.k = bArr3;
        }

        final boolean a() {
            if (TextUtils.isEmpty(this.b)) {
                return false;
            }
            return this.b.startsWith(HttpClientAndroid.HTTP_PREFIX) || this.b.startsWith(HttpClientAndroid.HTTPS_PREFIX);
        }

        final boolean b() {
            return this.d != null && this.d.length > 0;
        }

        final boolean c() {
            return HttpClientAndroid.METHOD_POST.equals(d()) || HttpClientAndroid.METHOD_PUT.equals(d());
        }

        final String d() {
            return TextUtils.isEmpty(this.c) ? "" : HttpClientAndroid.METHOD_POST.equalsIgnoreCase(this.c) ? HttpClientAndroid.METHOD_POST : HttpClientAndroid.METHOD_GET.equalsIgnoreCase(this.c) ? HttpClientAndroid.METHOD_GET : HttpClientAndroid.METHOD_PUT.equalsIgnoreCase(this.c) ? HttpClientAndroid.METHOD_PUT : "";
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Request{requestId=");
            sb.append(this.a);
            sb.append(", url='");
            sb.append(this.b);
            sb.append('\'');
            sb.append(", method='");
            sb.append(this.c);
            sb.append('\'');
            sb.append(", body.size=");
            sb.append(b() ? this.d.length : 0);
            sb.append(", headers=");
            sb.append(this.e);
            sb.append(", autoRedirect=");
            sb.append(this.h);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class f {
        ByteBuffer c;
        h a = h.kUnknownError;
        String b = "";
        int d = 0;
        String e = "";
        Map<String, String> f = null;
        int g = 0;
        int h = 0;
        String i = "";
    }

    public enum g {
        CONNECTED(0),
        DISCONNECTED(1),
        FINISHED(2);

        int e;

        g(int i) {
            this.e = i;
        }
    }

    enum h {
        kHTTP200OK(200),
        kHTTP204NoContent(204),
        kHTTP206PartialContent(206),
        kHTTP301MovedPermanently(301),
        kHTTP302Found(302),
        kHTTP303SeeOther(303),
        kHTTP304NotModified(304),
        kHTTP307TemporaryRedirect(StatusLine.HTTP_TEMP_REDIRECT),
        kHTTP308PermanentRedirect(StatusLine.HTTP_PERM_REDIRECT),
        kHTTP403Forbidden(403),
        kHTTP404NotFound(404),
        kHTTP405MethodNotAllowed(405),
        kHTTP503ServiceUnavailable(503),
        kSystemFileOpenFailed(1001),
        kSystemFileWriteFailed(1002),
        kSystemUnknownHost(1003),
        kSystemConnectHostFailed(1004),
        kSystemCreateSocketFailed(AVError.AV_ERR_TIMEOUT),
        kSystemNetworkDisabled(1006),
        kSystemConnectTimeout(1007),
        kSystemConnectRefused(1008),
        kSystemProtocolError(1009),
        kSystemSSLError(PointerIconCompat.TYPE_ALIAS),
        kUnknownError(1999);

        final int z;

        h(int i) {
            this.z = i;
        }
    }

    public HttpClientAndroid(int i, int i2, int i3, boolean z, int i4, int i5, String str, String str2, String str3, int i6, long j) {
        Handler handler;
        try {
            this.mHttpConfig = new b(i, i2, i3, z, i4, i5, str, str2, str3, d.a(i6));
            this.mNativeHttpClientAndroidJni = j;
            HandlerThread handlerThread = new HandlerThread("HttpClient_" + hashCode());
            handlerThread.start();
            LiteavLog.i(TAG, "Create http client(" + hashCode() + "). [ThreadName:" + handlerThread.getName() + "][ThreadId:" + handlerThread.getId() + "]");
            handler = new Handler(handlerThread.getLooper());
        } catch (Throwable th) {
            LiteavLog.e(TAG, "HttpClientAndroid failed.", th);
            handler = null;
        }
        this.mHttpHandler = handler;
    }

    static /* synthetic */ void a(HttpClientAndroid httpClientAndroid) {
        httpClientAndroid.closeConnectionSafely(httpClientAndroid.mConnection);
        httpClientAndroid.mConnection = null;
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 18) {
            httpClientAndroid.mHttpHandler.getLooper().quitSafely();
        } else {
            httpClientAndroid.mHttpHandler.getLooper().quit();
        }
    }

    static /* synthetic */ void a(HttpClientAndroid httpClientAndroid, long j) {
        f fVar = new f();
        fVar.a = httpClientAndroid.mRepeatDownloadingStatusCode;
        httpClientAndroid.doReadData(j, fVar);
    }

    static /* synthetic */ void a(HttpClientAndroid httpClientAndroid, f fVar, long j) {
        f fVar2 = new f();
        fVar2.a = fVar.a;
        httpClientAndroid.doReadData(j, fVar2);
    }

    static /* synthetic */ void a(HttpClientAndroid httpClientAndroid, Long l) {
        f fVar = new f();
        fVar.a = httpClientAndroid.mRepeatDownloadingStatusCode;
        httpClientAndroid.doReadData(l.longValue(), fVar);
    }

    static /* synthetic */ void b(HttpClientAndroid httpClientAndroid) {
        httpClientAndroid.closeConnectionSafely(httpClientAndroid.mConnection);
        httpClientAndroid.mConnection = null;
    }

    private boolean checkNativeValid() {
        boolean z;
        synchronized (this.mLocker) {
            z = this.mNativeHttpClientAndroidJni != -1;
        }
        return z;
    }

    private boolean checkRequestValid(long j) {
        return this.mRunningRequestMap.containsKey(Long.valueOf(j));
    }

    private void closeConnectionSafely(HttpURLConnection httpURLConnection) {
        if (this.mNetworkCallback != null && LiteavSystemInfo.getSystemOSVersionInt() >= 23) {
            try {
                try {
                    ((ConnectivityManager) ContextUtils.getApplicationContext().getSystemService("connectivity")).unregisterNetworkCallback(this.mNetworkCallback);
                } finally {
                    this.mNetworkCallback = null;
                }
            } catch (Exception e2) {
                LiteavLog.w(TAG, "(" + hashCode() + ")" + Log.getStackTraceString(e2));
            }
        }
        if (httpURLConnection != null) {
            try {
                try {
                    closeIO(httpURLConnection.getInputStream());
                } catch (Exception e3) {
                    e3.printStackTrace();
                    try {
                        httpURLConnection.disconnect();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            } finally {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
        }
    }

    private void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private HttpURLConnection createConnection(e eVar) throws Exception {
        Proxy proxy;
        HttpURLConnection httpURLConnectionCreateConnection;
        String str;
        String str2;
        String strReplace = eVar.b.replace(" ", "%20");
        URL url = new URL(strReplace);
        if (TextUtils.isEmpty(this.mHttpConfig.g) || this.mHttpConfig.f <= 0) {
            proxy = ("127.0.0.1".equals(url.getHost()) || "localhost".equals(url.getHost())) ? Proxy.NO_PROXY : null;
        } else {
            proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(this.mHttpConfig.g, this.mHttpConfig.f));
            Authenticator.setDefault(new a(this.mHttpConfig.h, this.mHttpConfig.i));
        }
        if (proxy != null) {
            httpURLConnectionCreateConnection = createConnection(url, proxy);
        } else if (HttpDnsUtil.verifyCustomHttpDNS(url.getHost())) {
            try {
                String strConvertHttpDNSURL = HttpDnsUtil.convertHttpDNSURL(strReplace, url.getHost());
                if (TextUtils.isEmpty(strConvertHttpDNSURL)) {
                    httpURLConnectionCreateConnection = createConnection(new URL(strReplace), null);
                } else {
                    httpURLConnectionCreateConnection = createConnection(new URL(strConvertHttpDNSURL), null);
                    httpURLConnectionCreateConnection.setRequestProperty("Host", url.getHost());
                    HttpDnsUtil.applySniForHttpsConnection(httpURLConnectionCreateConnection, url.getHost());
                }
            } catch (Exception e2) {
                LiteavLog.w(TAG, "(" + hashCode() + ")createConnectionUseCustomHttpDNS failed. error: " + Log.getStackTraceString(e2));
                httpURLConnectionCreateConnection = createConnection(url, null);
            }
        } else {
            httpURLConnectionCreateConnection = createConnection(url, null);
        }
        httpURLConnectionCreateConnection.setInstanceFollowRedirects(false);
        httpURLConnectionCreateConnection.setConnectTimeout(this.mHttpConfig.a);
        httpURLConnectionCreateConnection.setReadTimeout(this.mHttpConfig.b);
        httpURLConnectionCreateConnection.setRequestProperty("Accept-Encoding", "identity");
        httpURLConnectionCreateConnection.setRequestMethod(eVar.d());
        if (eVar.c()) {
            httpURLConnectionCreateConnection.setDoOutput(true);
        }
        if (this.mHttpConfig.d) {
            str = "Connection";
            str2 = "Keep-Alive";
        } else {
            str = "Connection";
            str2 = "close";
        }
        httpURLConnectionCreateConnection.setRequestProperty(str, str2);
        if (eVar.e != null && !eVar.e.isEmpty()) {
            for (Map.Entry<String, String> entry : eVar.e.entrySet()) {
                httpURLConnectionCreateConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return httpURLConnectionCreateConnection;
    }

    private HttpURLConnection createConnection(URL url, Proxy proxy) throws Exception {
        HttpURLConnection httpURLConnectionCreateConnectionByNetworkType;
        return (LiteavSystemInfo.getSystemOSVersionInt() < 23 || this.mHttpConfig.j == d.DEFAULT || (httpURLConnectionCreateConnectionByNetworkType = createConnectionByNetworkType(url, proxy)) == null) ? openConnection(url, proxy) : httpURLConnectionCreateConnectionByNetworkType;
    }

    private HttpURLConnection createConnectionByNetworkType(final URL url, final Proxy proxy) {
        int i;
        if (this.mHttpConfig.j == d.WIFI) {
            i = 1;
        } else {
            if (this.mHttpConfig.j != d.CELLULAR) {
                return null;
            }
            i = 0;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final HttpURLConnection[] httpURLConnectionArr = {null};
        NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(i).build();
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextUtils.getApplicationContext().getSystemService("connectivity");
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.gme.liteav.base.http.HttpClientAndroid.2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r4v3, types: [java.util.concurrent.CountDownLatch] */
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                HttpClientAndroid.this.mReallyNetworkChannel = HttpClientAndroid.this.mHttpConfig.j;
                LiteavLog.i(HttpClientAndroid.TAG, "(" + HttpClientAndroid.this.hashCode() + ")createConnectionSpecifyNetwork onAvailable.");
                try {
                    try {
                        if (proxy == null) {
                            httpURLConnectionArr[0] = (HttpURLConnection) network.openConnection(url);
                        } else {
                            httpURLConnectionArr[0] = (HttpURLConnection) network.openConnection(url, proxy);
                        }
                    } catch (IOException e2) {
                        LiteavLog.w(HttpClientAndroid.TAG, "(" + HttpClientAndroid.this.hashCode() + ")createConnectionSpecifyNetwork failed. error: " + Log.getStackTraceString(e2));
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                LiteavLog.w(HttpClientAndroid.TAG, "(" + HttpClientAndroid.this.hashCode() + ")createConnectionSpecifyNetwork onLost.");
                countDownLatch.countDown();
            }
        };
        connectivityManager.requestNetwork(networkRequestBuild, this.mNetworkCallback);
        try {
            countDownLatch.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException unused) {
            LiteavLog.w(TAG, "(" + hashCode() + ")createConnectionSpecifyNetwork timeout.");
        }
        if (httpURLConnectionArr[0] != null) {
            LiteavLog.i(TAG, "(" + hashCode() + ")createConnectionSpecifyNetwork success.");
        } else {
            LiteavLog.w(TAG, "(" + hashCode() + ")createConnectionSpecifyNetwork lost or timeout.");
        }
        return httpURLConnectionArr[0];
    }

    private void doCallbackAndResetState(g gVar, long j, f fVar, boolean z) {
        synchronized (this.mLocker) {
            boolean z2 = checkNativeValid() && checkRequestValid(j) && fVar != null;
            boolean z3 = c.RUNNING_REPEAT == this.mInternalState;
            this.mRunningRequestMap.remove(Long.valueOf(j));
            if (this.mRunningRequestMap.size() == 0) {
                this.mInternalState = c.NONE;
            }
            if (z2) {
                nativeOnCallback(this.mNativeHttpClientAndroidJni, z3, gVar.e, j, fVar.a.z, fVar.b, fVar.g, fVar.c, fVar.e, fVar.f, fVar.d, fVar.h, fVar.i, this.mReallyNetworkChannel.e);
            }
        }
        if (z) {
            closeConnectionSafely(this.mConnection);
            this.mConnection = null;
        }
    }

    private boolean doOnCallback(g gVar, long j, f fVar) {
        synchronized (this.mLocker) {
            if (!checkNativeValid() || !checkRequestValid(j) || fVar == null) {
                return false;
            }
            return nativeOnCallback(this.mNativeHttpClientAndroidJni, c.RUNNING_REPEAT == this.mInternalState, gVar.e, j, fVar.a.z, fVar.b, fVar.g, fVar.c, fVar.e, fVar.f, fVar.d, fVar.h, fVar.i, this.mReallyNetworkChannel.e);
        }
    }

    private void doReadData(long j, f fVar) {
        boolean z;
        long jElapsedRealtime;
        int i;
        if (!checkRequestValid(j)) {
            closeConnectionSafely(this.mConnection);
            LiteavLog.w(TAG, "(" + hashCode() + ")Do read data failed. Invalid request id. id:" + j);
            return;
        }
        try {
            InputStream inputStream = this.mConnection.getInputStream();
            synchronized (this.mLocker) {
                z = this.mInternalState == c.RUNNING_ONCE;
            }
            long j2 = 0;
            if (z) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    do {
                        i = inputStream.read(this.a);
                        if (i > 0) {
                            byteArrayOutputStream.write(this.a, 0, i);
                        }
                    } while (i > 0 && checkRequestValid(j));
                    int size = byteArrayOutputStream.size();
                    if (size > 0) {
                        fVar.c = ByteBuffer.allocateDirect(size);
                        fVar.c.put(byteArrayOutputStream.toByteArray(), 0, size);
                        fVar.d = size;
                    }
                    jElapsedRealtime = 0;
                } catch (Throwable th) {
                    th.printStackTrace();
                    LiteavLog.e(TAG, "(" + hashCode() + ")Do read data failed. Catch error when reading.");
                    fVar.a = getStatusCode(th);
                    fVar.b = th.toString();
                    doCallbackAndResetState(g.DISCONNECTED, j, fVar, true);
                    return;
                }
            } else {
                try {
                    int i2 = inputStream.read(this.a);
                    this.mTotalReadBytes += (long) i2;
                    jElapsedRealtime = SystemClock.elapsedRealtime();
                    if (i2 > 0) {
                        if (this.mRepeatByteBuffer == null || this.mRepeatByteBuffer.capacity() < i2) {
                            this.mRepeatByteBuffer = ByteBuffer.allocateDirect(i2);
                        }
                        this.mRepeatByteBuffer.clear();
                        this.mRepeatByteBuffer.put(this.a, 0, i2);
                        fVar.c = this.mRepeatByteBuffer;
                        fVar.d = i2;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    LiteavLog.e(TAG, "(" + hashCode() + ")Do read data failed. Catch error when reading.");
                    fVar.a = getStatusCode(e2);
                    fVar.b = e2.toString();
                    doCallbackAndResetState(g.DISCONNECTED, j, fVar, true);
                    return;
                }
            }
            if (fVar.d == 0 && !z) {
                LiteavLog.w(TAG, "(" + hashCode() + ")Do read data failed. Rsp size is 0.");
                doCallbackAndResetState(g.FINISHED, j, fVar, this.mHttpConfig.d ^ true);
                return;
            }
            if (z) {
                doCallbackAndResetState(g.FINISHED, j, fVar, !this.mHttpConfig.d);
                return;
            }
            this.mPausedRepeatDownloading = doOnCallback(g.CONNECTED, j, fVar);
            this.mRepeatDownloadingStatusCode = fVar.a;
            if (this.mPausedRepeatDownloading) {
                return;
            }
            if (this.mHttpConfig.e > 0) {
                long j3 = jElapsedRealtime - this.mStartReadTime == 0 ? 1L : jElapsedRealtime - this.mStartReadTime;
                if (this.mTotalReadBytes / j3 > this.mHttpConfig.e / 1000) {
                    j2 = ((this.mTotalReadBytes * 1000) / ((long) this.mHttpConfig.e)) - j3;
                }
            }
            this.mHttpHandler.postDelayed(com.gme.liteav.base.http.f.a(this, fVar, j), j2);
        } catch (Exception e3) {
            e3.printStackTrace();
            LiteavLog.e(TAG, "(" + hashCode() + ")Do read data failed. Fail to get InputStream.");
            fVar.a = getStatusCode(e3);
            fVar.b = e3.toString();
            doCallbackAndResetState(g.DISCONNECTED, j, fVar, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequest(e eVar) throws Throwable {
        f fVarInternalRequest = null;
        for (int i = 0; i < 4; i++) {
            fVarInternalRequest = internalRequest(eVar);
            if (fVarInternalRequest == null) {
                return;
            }
            if (!eVar.h || (fVarInternalRequest.a != h.kHTTP301MovedPermanently && fVarInternalRequest.a != h.kHTTP302Found)) {
                break;
            }
            eVar.b = this.mConnection.getHeaderField("Location");
            eVar.f++;
            eVar.g = eVar.b;
        }
        this.mTotalReadBytes = 0L;
        this.mStartReadTime = SystemClock.elapsedRealtime();
        doReadData(eVar.a, fVarInternalRequest);
    }

    public static HashMap getJavaHashMap(String[] strArr, String[] strArr2) {
        if (strArr != null) {
            try {
                if (strArr.length != 0 && strArr2 != null && strArr2.length != 0) {
                    if (strArr.length != strArr2.length) {
                        LiteavLog.w(TAG, "Invalid parameter, keys and values do not match.");
                        return new HashMap();
                    }
                    HashMap map = new HashMap();
                    for (int i = 0; i < strArr.length; i++) {
                        map.put(strArr[i], strArr2[i]);
                    }
                    return map;
                }
            } catch (Throwable th) {
                LiteavLog.e(TAG, "getJavaHashMap failed.", th);
                return null;
            }
        }
        return new HashMap();
    }

    public static String[] getMapKeys(Map<String, String> map) {
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    Set<String> setKeySet = map.keySet();
                    return (String[]) setKeySet.toArray(new String[setKeySet.size()]);
                }
            } catch (Throwable th) {
                LiteavLog.e(TAG, "getMapKeys failed.", th);
                return null;
            }
        }
        return new String[0];
    }

    public static String[] getMapValue(Map<String, String> map, String[] strArr) {
        if (map != null) {
            try {
                if (!map.isEmpty() && strArr != null && strArr.length != 0) {
                    String[] strArr2 = new String[strArr.length];
                    for (int i = 0; i < strArr.length; i++) {
                        strArr2[i] = map.get(strArr[i]);
                    }
                    return strArr2;
                }
            } catch (Throwable th) {
                LiteavLog.e(TAG, "getMapValue failed.", th);
                return null;
            }
        }
        return new String[0];
    }

    private Map<String, String> getResponseHeaders(Map<String, List<String>> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey())) {
                map2.put(entry.getKey(), entry.getValue().get(0));
            }
        }
        return map2;
    }

    private h getStatusCode(int i) {
        h hVar = h.kUnknownError;
        if (i == 200) {
            return h.kHTTP200OK;
        }
        if (i == 204) {
            return h.kHTTP204NoContent;
        }
        if (i == 206) {
            return h.kHTTP206PartialContent;
        }
        if (i == 301) {
            return h.kHTTP301MovedPermanently;
        }
        if (i == 302) {
            return h.kHTTP302Found;
        }
        if (i == 303) {
            return h.kHTTP303SeeOther;
        }
        if (i == 304) {
            return h.kHTTP304NotModified;
        }
        if (i == 307) {
            return h.kHTTP307TemporaryRedirect;
        }
        if (i == 308) {
            return h.kHTTP308PermanentRedirect;
        }
        if (i == 403) {
            return h.kHTTP403Forbidden;
        }
        if (i == 404) {
            return h.kHTTP404NotFound;
        }
        if (i == 405) {
            return h.kHTTP405MethodNotAllowed;
        }
        if (i == 503) {
            return h.kHTTP503ServiceUnavailable;
        }
        Log.w(TAG, "(" + hashCode() + ")Failed to convert status code：" + i, new Object[0]);
        return hVar;
    }

    private h getStatusCode(Throwable th) {
        h hVar = h.kUnknownError;
        if (th instanceof FileNotFoundException) {
            return h.kSystemFileOpenFailed;
        }
        if (th instanceof EOFException) {
            return h.kSystemFileWriteFailed;
        }
        if (th instanceof UnknownHostException) {
            return h.kSystemUnknownHost;
        }
        if (th instanceof NoRouteToHostException) {
            return h.kSystemConnectHostFailed;
        }
        if ((th instanceof SocketException) || (th instanceof MalformedURLException)) {
            return h.kSystemCreateSocketFailed;
        }
        if (th instanceof SocketTimeoutException) {
            return h.kSystemConnectTimeout;
        }
        if (th instanceof ConnectException) {
            return h.kSystemConnectRefused;
        }
        if (th instanceof ProtocolException) {
            return h.kSystemProtocolError;
        }
        if (th instanceof SSLException) {
            return h.kSystemSSLError;
        }
        Log.w(TAG, "(" + hashCode() + ")Failed to convert status code, exception：", th.toString());
        return hVar;
    }

    private f internalRequest(e eVar) throws Throwable {
        boolean z;
        if (!eVar.a()) {
            LiteavLog.e(TAG, "(" + hashCode() + ")Send request failed. Invalid request url(" + eVar.b + ").");
            return null;
        }
        if (!checkRequestValid(eVar.a)) {
            LiteavLog.w(TAG, "(" + hashCode() + ")Do send failed. ignore request when cancelled. request:" + eVar);
            return null;
        }
        f fVar = new f();
        fVar.h = eVar.f;
        fVar.i = eVar.g;
        synchronized (this.mLocker) {
            z = this.mInternalState == c.RUNNING_ONCE;
        }
        if (z && this.mConnection != null && !eVar.b.equals(this.mLastRequestURL)) {
            closeConnectionSafely(this.mConnection);
            this.mConnection = null;
        }
        this.mLastRequestURL = eVar.b;
        try {
            this.mConnection = createConnection(eVar);
            writeRequestBody(eVar);
            try {
                fVar.a = getStatusCode(this.mConnection.getResponseCode());
                fVar.b = this.mConnection.getResponseMessage();
                fVar.e = parseHostAddress(this.mConnection.getURL().getHost());
                fVar.g = this.mConnection.getURL().getPort();
                fVar.f = getResponseHeaders(this.mConnection.getHeaderFields());
                if (checkRequestValid(eVar.a)) {
                    return fVar;
                }
                closeConnectionSafely(this.mConnection);
                LiteavLog.w(TAG, "(" + hashCode() + ")Do send failed. Invalid request, abort request.");
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                LiteavLog.e(TAG, "(" + hashCode() + ")Do send failed. Catch error. ex= " + Log.getStackTraceString(e2));
                fVar.a = getStatusCode(e2);
                fVar.b = e2.toString();
                doCallbackAndResetState(g.DISCONNECTED, eVar.a, fVar, true);
                return null;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            LiteavLog.e(TAG, "(" + hashCode() + ")Do send failed. Fail to create http connection. ex= " + Log.getStackTraceString(e3));
            fVar.a = getStatusCode(e3);
            fVar.b = e3.toString();
            doCallbackAndResetState(g.DISCONNECTED, eVar.a, fVar, true);
            return null;
        }
    }

    private static native boolean nativeOnCallback(long j, boolean z, int i, long j2, int i2, String str, int i3, ByteBuffer byteBuffer, String str2, Map map, int i4, int i5, String str3, int i6);

    private static native void nativeOnUploadProgress(long j, long j2, long j3, long j4);

    private HttpURLConnection openConnection(URL url, Proxy proxy) throws Exception {
        return (HttpURLConnection) (proxy != null ? url.openConnection(proxy) : url.openConnection());
    }

    private String parseHostAddress(String str) {
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (Exception unused) {
            LiteavLog.w(TAG, "(" + hashCode() + ")Parse host error. host:" + str);
            return "";
        }
    }

    private long sendInternal(long j, e eVar, boolean z) {
        String str;
        StringBuilder sb;
        String str2;
        if (eVar == null || !eVar.a()) {
            str = TAG;
            sb = new StringBuilder("(");
            sb.append(hashCode());
            sb.append(")upload file failed. Invalid request url(");
            sb.append(eVar.b);
            str2 = ").";
        } else {
            boolean z2 = true;
            if (!TextUtils.isEmpty(eVar.d())) {
                synchronized (this.mLocker) {
                    if (this.mInternalState == c.NONE) {
                        this.mInternalState = z ? c.RUNNING_REPEAT : c.RUNNING_ONCE;
                    } else if (this.mInternalState != c.RUNNING_ONCE) {
                        z2 = false;
                    }
                    if (z2) {
                        eVar.a = j;
                        this.mRunningRequestMap.put(Long.valueOf(j), eVar);
                        this.mHttpHandler.post(com.gme.liteav.base.http.a.a(this, eVar));
                        return eVar.a;
                    }
                    LiteavLog.e(TAG, "(" + hashCode() + ")Send request failed. Invalid state:" + this.mInternalState);
                    return 0L;
                }
            }
            str = TAG;
            sb = new StringBuilder("(");
            sb.append(hashCode());
            sb.append(")upload file failed. Request method(");
            sb.append(eVar.c);
            str2 = ") is not supported.";
        }
        sb.append(str2);
        LiteavLog.e(str, sb.toString());
        return 0L;
    }

    private void uploadFileByPath(e eVar, OutputStream outputStream) throws Exception {
        FileInputStream fileInputStream;
        File file;
        Object obj;
        if (TextUtils.isEmpty(eVar.i)) {
            return;
        }
        try {
            file = new File(eVar.i);
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            byte[] bArr = new byte[524288];
            long j = 0;
            long length = file.length();
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                Object obj2 = this.mLocker;
                synchronized (obj2) {
                    try {
                        if (!checkRequestValid(eVar.a) || !checkNativeValid()) {
                            break;
                        }
                        j += (long) i;
                        outputStream.write(bArr, 0, i);
                        obj = obj2;
                        try {
                            nativeOnUploadProgress(this.mNativeHttpClientAndroidJni, eVar.a, j, length);
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        obj = obj2;
                    }
                }
            }
            closeIO(fileInputStream);
        } catch (Throwable th4) {
            th = th4;
            closeIO(fileInputStream);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.gme.liteav.base.http.HttpClientAndroid, java.lang.Object] */
    private void writeRequestBody(e eVar) throws Throwable {
        ?? C;
        ?? r1;
        ?? r0 = 0;
        ?? r02 = 0;
        try {
            try {
                C = eVar.c();
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (C != 0 && eVar.b()) {
                OutputStream outputStream = this.mConnection.getOutputStream();
                outputStream.write(eVar.d);
                C = outputStream;
            } else {
                if (!eVar.c() || !(!TextUtils.isEmpty(eVar.i))) {
                    r1 = 0;
                    closeIO(r1);
                }
                OutputStream outputStream2 = this.mConnection.getOutputStream();
                if (eVar.j != null && eVar.j.length > 0) {
                    outputStream2.write(eVar.j);
                }
                uploadFileByPath(eVar, outputStream2);
                C = outputStream2;
                if (eVar.k != null) {
                    C = outputStream2;
                    if (eVar.k.length > 0) {
                        outputStream2.write(eVar.k);
                        C = outputStream2;
                    }
                }
            }
            C.flush();
            r1 = C;
            closeIO(r1);
        } catch (Exception e3) {
            e = e3;
            r02 = C;
            e.printStackTrace();
            LiteavLog.w(TAG, "(" + hashCode() + ")Do write request body failed.");
            closeIO(r02);
        } catch (Throwable th2) {
            th = th2;
            r0 = C;
            closeIO(r0);
            throw th;
        }
    }

    public void cancel(long j) {
        try {
            synchronized (this.mLocker) {
                if (!checkNativeValid()) {
                    LiteavLog.e(TAG, "(" + hashCode() + ")Cancel request failed. Invalid native handle.");
                    return;
                }
                if (this.mRunningRequestMap.size() == 0) {
                    return;
                }
                LiteavLog.i(TAG, "(" + hashCode() + ")Cancel request. request:" + this.mRunningRequestMap.remove(Long.valueOf(j)));
                if (this.mRunningRequestMap.size() == 0) {
                    this.mInternalState = c.NONE;
                }
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "cancel failed.", th);
        }
    }

    public void cancelAll() {
        try {
            synchronized (this.mLocker) {
                if (!checkNativeValid()) {
                    LiteavLog.e(TAG, "(" + hashCode() + ")Cancel all request failed. Invalid native handle.");
                    return;
                }
                if (this.mInternalState == c.NONE) {
                    return;
                }
                this.mInternalState = c.NONE;
                LiteavLog.i(TAG, "(" + hashCode() + ")Cancel all. size:" + this.mRunningRequestMap.size());
                this.mRunningRequestMap.clear();
                this.mHttpHandler.post(com.gme.liteav.base.http.b.a(this));
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "cancelAll failed.", th);
        }
    }

    public void destroy() {
        try {
            synchronized (this.mLocker) {
                this.mRunningRequestMap.clear();
                this.mNativeHttpClientAndroidJni = -1L;
                this.mHttpHandler.post(com.gme.liteav.base.http.e.a(this));
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "destroy failed.", th);
        }
    }

    public void resumeRepeatDownload(long j) {
        try {
            synchronized (this.mLocker) {
                if (!checkNativeValid()) {
                    LiteavLog.e(TAG, "(" + hashCode() + ")Cancel request failed. Invalid native handle.");
                    return;
                }
                if (this.mRunningRequestMap.size() == 0) {
                    return;
                }
                if (this.mInternalState == c.RUNNING_REPEAT && this.mPausedRepeatDownloading) {
                    this.mPausedRepeatDownloading = false;
                    if (j == 0) {
                        Iterator<Long> it = this.mRunningRequestMap.keySet().iterator();
                        while (it.hasNext()) {
                            this.mHttpHandler.post(com.gme.liteav.base.http.c.a(this, it.next()));
                        }
                    } else if (checkRequestValid(j)) {
                        if (this.mRunningRequestMap.get(Long.valueOf(j)) == null) {
                        } else {
                            this.mHttpHandler.post(com.gme.liteav.base.http.d.a(this, j));
                        }
                    }
                }
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "resumeRepeatDownload failed.", th);
        }
    }

    public long send(long j, String str, String str2, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        try {
            if (checkNativeValid()) {
                return sendInternal(j, new e(str, str2, bArr, map, z2), z);
            }
            LiteavLog.e(TAG, "(" + hashCode() + ")Send request failed. Invalid native handle.");
            return 0L;
        } catch (Throwable th) {
            LiteavLog.e(TAG, "send failed.", th);
            return 0L;
        }
    }

    public void updateConfig(final int i, final int i2, final int i3, final boolean z, final int i4, final int i5, final String str, final String str2, final String str3, final int i6, long j) {
        try {
            this.mHttpHandler.post(new Runnable() { // from class: com.gme.liteav.base.http.HttpClientAndroid.1
                @Override // java.lang.Runnable
                public final void run() {
                    HttpClientAndroid.this.mHttpConfig = new b(i, i2, i3, z, i4, i5, str, str2, str3, d.a(i6));
                    HttpClientAndroid.this.mReallyNetworkChannel = d.DEFAULT;
                    if (i4 > 0) {
                        HttpClientAndroid.this.mTotalReadBytes = 0L;
                        HttpClientAndroid.this.mStartReadTime = SystemClock.elapsedRealtime();
                    }
                }
            });
        } catch (Throwable th) {
            LiteavLog.e(TAG, "updateConfig failed.", th);
        }
    }

    public long uploadFile(long j, String str, String str2, byte[] bArr, Map<String, String> map, boolean z, boolean z2, String str3, byte[] bArr2, byte[] bArr3) {
        try {
            if (!checkNativeValid()) {
                LiteavLog.e(TAG, "(" + hashCode() + ")upload file failed. Invalid native handle.");
                return 0L;
            }
            if (!str3.isEmpty()) {
                return sendInternal(j, new e(str, str2, bArr, map, z2, str3, bArr2, bArr3), z);
            }
            LiteavLog.e(TAG, "(" + hashCode() + ")upload file failed. Invalid file path(" + str3 + ").");
            return 0L;
        } catch (Throwable th) {
            LiteavLog.e(TAG, "uploadFile failed.", th);
            return 0L;
        }
    }
}
