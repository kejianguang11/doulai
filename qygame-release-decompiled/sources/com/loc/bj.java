package com.loc;

import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.gcloudsdk.gcloud.voice.GCloudVoiceErrorCode;
import com.gme.liteav.TXLiteAVCode;
import com.loc.bg;
import com.loc.bl;
import com.loc.m;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class bj {
    private static SoftReference<SSLContext> k;
    private static SoftReference<bk> t;
    private boolean a;
    private SSLContext b;
    private Proxy c;
    private String g;
    private bg.a h;
    private d i;
    private boolean l;
    private String m;
    private String n;
    private volatile boolean d = false;
    private long e = -1;
    private long f = 0;
    private String j = "";
    private boolean o = false;
    private boolean p = false;
    private String q = "";
    private String r = "";
    private String s = "";
    private f u = new f();

    public static class a implements Cloneable, Comparable {
        public int a;
        public String b;
        public String c;
        public String d;
        public String e;
        public int f;
        public int g;
        public int h;
        public long i;
        public volatile AtomicInteger j = new AtomicInteger(1);

        public a(c cVar) {
            this.b = cVar.c;
            this.c = cVar.e;
            this.e = cVar.d;
            this.f = cVar.m;
            this.g = cVar.n;
            this.h = cVar.b.a();
            this.d = cVar.a;
            this.i = cVar.f;
            if (this.f == 10) {
                this.a = 0;
            }
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final a clone() {
            try {
                return (a) super.clone();
            } catch (CloneNotSupportedException unused) {
                return null;
            }
        }

        public final String b() {
            StringBuilder sb;
            String str;
            StringBuilder sb2;
            String str2;
            StringBuilder sb3;
            String str3;
            StringBuilder sb4;
            String str4;
            try {
                String str5 = this.f + "#";
                if (TextUtils.isEmpty(this.e)) {
                    sb = new StringBuilder();
                    sb.append(str5);
                    str = "-#";
                } else {
                    sb = new StringBuilder();
                    sb.append(str5);
                    sb.append(this.e);
                    str = "#";
                }
                sb.append(str);
                String str6 = (sb.toString() + this.h + "#") + this.j + "#";
                if (TextUtils.isEmpty(this.b)) {
                    sb2 = new StringBuilder();
                    sb2.append(str6);
                    str2 = "-#";
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(str6);
                    sb2.append(this.b);
                    str2 = "#";
                }
                sb2.append(str2);
                String string = sb2.toString();
                if (this.f == 1) {
                    sb3 = new StringBuilder();
                    sb3.append(string);
                    sb3.append(this.d);
                    str3 = "#";
                } else {
                    sb3 = new StringBuilder();
                    sb3.append(string);
                    str3 = "-#";
                }
                sb3.append(str3);
                String string2 = sb3.toString();
                if (this.f == 1) {
                    sb4 = new StringBuilder();
                    sb4.append(string2);
                    sb4.append(this.i);
                    str4 = "#";
                } else {
                    sb4 = new StringBuilder();
                    sb4.append(string2);
                    str4 = "-#";
                }
                sb4.append(str4);
                String str7 = (sb4.toString() + this.c + "#") + this.g;
                String strB = p.b(bc.a(str7.getBytes(), "YXBtX25ldHdvcmtf".getBytes()));
                StringBuilder sb5 = new StringBuilder("上报异常数据");
                sb5.append(str7);
                sb5.append("加密后：");
                sb5.append(strB);
                bj.a();
                return strB;
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            return this.a - ((a) obj).a;
        }
    }

    public static class b {
        public HttpURLConnection a;
        public int b = this.b;
        public int b = this.b;

        public b(HttpURLConnection httpURLConnection) {
            this.a = httpURLConnection;
        }
    }

    public static class c implements Cloneable {
        public String a = "";
        public bl.b b = bl.b.FIRST_NONDEGRADE;
        public String c = "";
        public String d = "";
        public String e = "";
        public long f = 0;
        public long g = 0;
        public long h = 0;
        public long i = 0;
        public long j = 0;
        public String k = "-";
        public String l = "-";
        public int m = 0;
        public int n = 0;

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final c clone() {
            try {
                return (c) super.clone();
            } catch (CloneNotSupportedException unused) {
                return null;
            }
        }

        protected final String b() {
            String str;
            StringBuilder sb;
            String str2;
            if (TextUtils.isEmpty(this.c)) {
                str = "-#";
            } else {
                str = this.c + "#";
            }
            if (TextUtils.isEmpty(this.d)) {
                sb = new StringBuilder();
                sb.append(str);
                str2 = "-#";
            } else {
                sb = new StringBuilder();
                sb.append(str);
                sb.append(this.d);
                str2 = "#";
            }
            sb.append(str2);
            String str3 = (((sb.toString() + this.b.a() + "#") + this.h + "#") + this.j + "#") + this.f;
            String strB = p.b(bc.a(str3.getBytes(), "YXBtX25ldHdvcmtf".getBytes()));
            StringBuilder sb2 = new StringBuilder("上报耗时数据");
            sb2.append(str3);
            sb2.append("加密后：");
            sb2.append(strB);
            bj.a();
            return strB;
        }

        public final String toString() {
            return "RequestInfo{csid='" + this.a + "', degradeType=" + this.b + ", serverIp='" + this.c + "', path='" + this.d + "', hostname='" + this.e + "', totalTime=" + this.f + ", DNSTime=" + this.g + ", connectionTime=" + this.h + ", writeTime=" + this.i + ", readTime=" + this.j + ", serverTime='" + this.k + "', datasize='" + this.l + "', errorcode=" + this.m + ", errorcodeSub=" + this.n + '}';
        }
    }

    private static class d {
        private Vector<e> a;
        private volatile e b;

        private d() {
            this.a = new Vector<>();
            this.b = new e((byte) 0);
        }

        /* synthetic */ d(byte b) {
            this();
        }

        public final e a(String str) {
            if (TextUtils.isEmpty(str)) {
                return this.b;
            }
            byte b = 0;
            for (int i = 0; i < this.a.size(); i++) {
                e eVar = this.a.get(i);
                if (eVar != null && eVar.a().equals(str)) {
                    return eVar;
                }
            }
            e eVar2 = new e(b);
            eVar2.b(str);
            this.a.add(eVar2);
            return eVar2;
        }
    }

    private static class e implements HostnameVerifier {
        private String a;
        private String b;

        private e() {
        }

        /* synthetic */ e(byte b) {
            this();
        }

        public final String a() {
            return this.b;
        }

        public final void a(String str) {
            String[] strArrSplit;
            if (TextUtils.isEmpty(this.a) || !str.contains(":") || (strArrSplit = str.split(":")) == null || strArrSplit.length <= 0) {
                this.a = str;
            } else {
                this.a = strArrSplit[0];
            }
        }

        public final void b(String str) {
            this.b = str;
        }

        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
            return !TextUtils.isEmpty(this.a) ? this.a.equals(str) : !TextUtils.isEmpty(this.b) ? defaultHostnameVerifier.verify(this.b, sSLSession) : defaultHostnameVerifier.verify(str, sSLSession);
        }
    }

    class f {
        long a = 0;
        long b = 0;
        c c = new c();
        a d;
        c e;
        String f;
        URL g;

        f() {
        }

        public final void a() {
            this.c.h = SystemClock.elapsedRealtime() - this.b;
        }

        public final void a(int i) {
            "----errorcode-----".concat(String.valueOf(i));
            bj.a();
            try {
                this.c.f = SystemClock.elapsedRealtime() - this.a;
                this.c.m = i;
                if (this.c.b.e()) {
                    m.a(false, this.c.e);
                }
                boolean zA = bj.this.a(this.c.e);
                if (zA) {
                    if (bj.this.p && !TextUtils.isEmpty(bj.this.n) && this.c.b.b()) {
                        m.d();
                    }
                    if (this.c.b.c()) {
                        m.a(this.c.b.c(), this.c.e);
                    }
                    m.c(this.e);
                    m.a(false, this.d);
                    m.b(this.c);
                }
                m.a(this.g.toString(), this.c.b.c(), true, zA);
                new StringBuilder("!!!error-").append(this.c.toString());
                bj.a();
            } catch (Throwable unused) {
            }
        }

        public final void a(long j) {
            this.c.l = new DecimalFormat("0.00").format(j / 1024.0f);
        }

        public final void a(bl blVar, URL url) {
            this.g = url;
            this.c.d = url.getPath();
            this.c.e = url.getHost();
            if (!TextUtils.isEmpty(bj.this.n) && blVar.u().b()) {
                this.c.c = this.c.e.replace("[", "").replace("]", "");
                this.c.e = bj.this.n;
            }
            if (blVar.u().b()) {
                blVar.a(this.c.e);
            }
            if (blVar.u().d()) {
                this.f = blVar.x();
            }
        }

        public final void a(bm bmVar) {
            c cVarClone;
            try {
                this.c.f = SystemClock.elapsedRealtime() - this.a;
                if (bmVar != null) {
                    bmVar.f = this.c.b.c();
                }
                if (this.c.b.b() && this.c.f > com.igexin.push.config.c.i) {
                    m.a(false, this.c.e);
                }
                if (this.c.b.d()) {
                    m.a(false, this.f);
                }
                boolean zA = bj.this.a(this.c.e);
                if (zA) {
                    m.c(this.c);
                    m.a(true, this.d);
                    if (this.c.f > m.e && (cVarClone = this.c.clone()) != null) {
                        cVarClone.m = 1;
                        m.b(cVarClone);
                        new StringBuilder("!!!finish&error-").append(cVarClone.toString());
                        bj.a();
                    }
                }
                m.a(this.g.toString(), this.c.b.c(), false, zA);
                new StringBuilder("!!!finish-").append(this.c.toString());
                bj.a();
            } catch (Throwable unused) {
            }
        }

        public final void b() {
            this.c.i = SystemClock.elapsedRealtime() - this.b;
        }

        public final void b(int i) {
            this.c.n = i;
        }

        public final void c() {
            this.c.j = SystemClock.elapsedRealtime() - this.b;
        }

        public final void d() {
            c cVarClone = this.c.clone();
            if (this.c.f > m.e) {
                cVarClone.m = 1;
            }
            m.a(cVarClone);
        }
    }

    bj() {
        m.e();
        try {
            this.g = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        } catch (Throwable th) {
            ak.a(th, "ht", "ic");
        }
    }

    private static int a(Exception exc) {
        if (exc instanceof SSLHandshakeException) {
            return 4101;
        }
        if (exc instanceof SSLKeyException) {
            return 4102;
        }
        if (exc instanceof SSLProtocolException) {
            return GCloudVoiceErrorCode.GCloudVoiceErrno.GCLOUD_VOICE_PARAM_INVALID;
        }
        if (exc instanceof SSLPeerUnverifiedException) {
            return GCloudVoiceErrorCode.GCloudVoiceErrno.GCLOUD_VOICE_OPENFILE_ERR;
        }
        if (exc instanceof ConnectException) {
            return 6101;
        }
        if (exc instanceof SocketException) {
            return 6102;
        }
        if (exc instanceof ConnectTimeoutException) {
            return TXLiteAVCode.WARNING_VIDEO_FRAME_DECODE_FAIL;
        }
        if (exc instanceof SocketTimeoutException) {
            return TXLiteAVCode.WARNING_AUDIO_FRAME_DECODE_FAIL;
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0177 A[Catch: all -> 0x01c4, IOException -> 0x01c9, SocketTimeoutException -> 0x01f7, ConnectTimeoutException -> 0x01fc, TRY_ENTER, TryCatch #18 {SocketTimeoutException -> 0x01f7, ConnectTimeoutException -> 0x01fc, IOException -> 0x01c9, all -> 0x01c4, blocks: (B:3:0x0003, B:5:0x0015, B:7:0x001f, B:9:0x0025, B:10:0x002c, B:11:0x0036, B:13:0x003e, B:15:0x0042, B:17:0x004a, B:22:0x005f, B:24:0x0069, B:26:0x0071, B:27:0x0079, B:31:0x0080, B:32:0x0086, B:36:0x008d, B:38:0x0093, B:43:0x009e, B:103:0x0177, B:104:0x01c3, B:18:0x0050, B:19:0x0058), top: B:162:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0221 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0213 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0205 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x022f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:181:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0099 A[PHI: r0
      0x0099: PHI (r0v6 java.lang.String) = (r0v0 java.lang.String), (r0v12 java.lang.String), (r0v12 java.lang.String) binds: [B:4:0x0013, B:150:0x0099, B:12:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009e A[Catch: all -> 0x01c4, IOException -> 0x01c9, SocketTimeoutException -> 0x01f7, ConnectTimeoutException -> 0x01fc, TRY_ENTER, TRY_LEAVE, TryCatch #18 {SocketTimeoutException -> 0x01f7, ConnectTimeoutException -> 0x01fc, IOException -> 0x01c9, all -> 0x01c4, blocks: (B:3:0x0003, B:5:0x0015, B:7:0x001f, B:9:0x0025, B:10:0x002c, B:11:0x0036, B:13:0x003e, B:15:0x0042, B:17:0x004a, B:22:0x005f, B:24:0x0069, B:26:0x0071, B:27:0x0079, B:31:0x0080, B:32:0x0086, B:36:0x008d, B:38:0x0093, B:43:0x009e, B:103:0x0177, B:104:0x01c3, B:18:0x0050, B:19:0x0058), top: B:162:0x0003 }] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private bm a(b bVar) throws Throwable {
        ?? r2;
        InputStream inputStream;
        PushbackInputStream pushbackInputStream;
        boolean zA;
        InputStream inputStream2;
        char c2;
        String str = "";
        ByteArrayOutputStream byteArrayOutputStream = null;
        gZIPInputStream = null;
        gZIPInputStream = null;
        gZIPInputStream = null;
        InputStream gZIPInputStream = null;
        byteArrayOutputStream = null;
        byteArrayOutputStream = null;
        try {
            try {
                bi.a();
                HttpURLConnection httpURLConnection = bVar.a;
                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                int responseCode = httpURLConnection.getResponseCode();
                if (headerFields == null) {
                    zA = false;
                    if (responseCode == 200) {
                        k kVar = new k("网络异常原因：" + httpURLConnection.getResponseMessage() + " 网络异常状态码：" + responseCode + "  " + str + " " + this.g, str, this.g);
                        kVar.a(httpURLConnection.getResponseMessage());
                        kVar.a(headerFields);
                        this.u.b(responseCode);
                        this.u.a(10);
                        kVar.h();
                        throw kVar;
                    }
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    try {
                        this.u.b = SystemClock.elapsedRealtime();
                        inputStream = httpURLConnection.getInputStream();
                        try {
                            pushbackInputStream = new PushbackInputStream(inputStream, 2);
                            try {
                                byte[] bArr = new byte[2];
                                pushbackInputStream.read(bArr);
                                pushbackInputStream.unread(bArr);
                                gZIPInputStream = (bArr[0] == 31 && bArr[1] == -117) ? new GZIPInputStream(pushbackInputStream) : pushbackInputStream;
                                byte[] bArr2 = new byte[1024];
                                while (true) {
                                    int i = gZIPInputStream.read(bArr2);
                                    if (i == -1) {
                                        break;
                                    }
                                    byteArrayOutputStream2.write(bArr2, 0, i);
                                }
                                this.u.c();
                                an.c();
                                bm bmVar = new bm();
                                bmVar.a = byteArrayOutputStream2.toByteArray();
                                bmVar.b = headerFields;
                                bmVar.c = this.g;
                                bmVar.d = str;
                                bmVar.e = zA;
                                bi.a(httpURLConnection.getURL(), bmVar);
                                this.u.a(bmVar.a.length);
                                try {
                                    byteArrayOutputStream2.close();
                                } catch (Throwable th) {
                                    ak.a(th, "ht", "par");
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th2) {
                                        ak.a(th2, "ht", "par");
                                    }
                                }
                                try {
                                    pushbackInputStream.close();
                                } catch (Throwable th3) {
                                    ak.a(th3, "ht", "par");
                                }
                                try {
                                    gZIPInputStream.close();
                                } catch (Throwable th4) {
                                    ak.a(th4, "ht", "par");
                                }
                                return bmVar;
                            } catch (SocketTimeoutException e2) {
                                e = e2;
                                throw e;
                            } catch (ConnectTimeoutException e3) {
                                e = e3;
                                throw e;
                            } catch (IOException e4) {
                                e = e4;
                                inputStream2 = gZIPInputStream;
                                byteArrayOutputStream = byteArrayOutputStream2;
                                r2 = inputStream2;
                                try {
                                    if (!(e instanceof InterruptedIOException)) {
                                        throw e;
                                    }
                                    k kVar2 = new k("IO 操作异常 - IOException", str, this.g);
                                    if (TextUtils.isEmpty(e.getMessage()) || !e.getMessage().equals("thread interrupted")) {
                                        throw kVar2;
                                    }
                                    kVar2.j();
                                    throw kVar2;
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                r2 = gZIPInputStream;
                                byteArrayOutputStream = byteArrayOutputStream2;
                                if (byteArrayOutputStream != null) {
                                }
                                if (inputStream != null) {
                                }
                                if (pushbackInputStream != null) {
                                }
                                if (r2 != 0) {
                                }
                            }
                        } catch (SocketTimeoutException e5) {
                            e = e5;
                        } catch (ConnectTimeoutException e6) {
                            e = e6;
                        } catch (IOException e7) {
                            e = e7;
                            inputStream2 = null;
                            pushbackInputStream = null;
                        } catch (Throwable th7) {
                            th = th7;
                            r2 = 0;
                            pushbackInputStream = null;
                        }
                    } catch (SocketTimeoutException e8) {
                        e = e8;
                    } catch (ConnectTimeoutException e9) {
                        e = e9;
                    } catch (IOException e10) {
                        e = e10;
                        inputStream2 = null;
                        inputStream = null;
                        pushbackInputStream = null;
                    } catch (Throwable th8) {
                        th = th8;
                        r2 = 0;
                        inputStream = null;
                        pushbackInputStream = null;
                    }
                } else {
                    List<String> list = headerFields.get("gsid");
                    if (list != null && list.size() > 0) {
                        str = list.get(0);
                    }
                    this.u.c.k = b(headerFields);
                    try {
                        if (!TextUtils.isEmpty(this.j)) {
                            if (!this.o) {
                                zA = a(headerFields, true);
                                c2 = 2;
                            } else if (headerFields.containsKey(com.igexin.push.g.q.e)) {
                                zA = a(headerFields, false);
                                c2 = 1;
                            } else {
                                m.b(this.j);
                                zA = false;
                                c2 = 0;
                            }
                            try {
                                if (zA) {
                                    if (this.j.equals("loc")) {
                                        String host = this.s;
                                        if (TextUtils.isEmpty(host)) {
                                            host = httpURLConnection.getURL().getHost();
                                        }
                                        m.a(this.j, c2 == 2, host, host, this.n);
                                    } else {
                                        m.b(this.j, c2 == 2);
                                    }
                                } else if (c2 == 1) {
                                    bi.a(false, this.j);
                                }
                            } catch (Throwable unused) {
                            }
                        }
                    } catch (Throwable unused2) {
                    }
                    if (responseCode == 200) {
                    }
                }
            } catch (Throwable th9) {
                th = th9;
                r2 = bVar;
            }
        } catch (SocketTimeoutException e11) {
            throw e11;
        } catch (ConnectTimeoutException e12) {
            throw e12;
        } catch (IOException e13) {
            e = e13;
            r2 = 0;
            inputStream = null;
            pushbackInputStream = null;
        } catch (Throwable th10) {
            th = th10;
            r2 = 0;
            inputStream = null;
            pushbackInputStream = null;
        }
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th11) {
                ak.a(th11, "ht", "par");
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable th12) {
                ak.a(th12, "ht", "par");
            }
        }
        if (pushbackInputStream != null) {
            try {
                pushbackInputStream.close();
            } catch (Throwable th13) {
                ak.a(th13, "ht", "par");
            }
        }
        if (r2 != 0) {
            throw th;
        }
        try {
            r2.close();
            throw th;
        } catch (Throwable th14) {
            ak.a(th14, "ht", "par");
            throw th;
        }
    }

    static String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            if (sb.length() > 0) {
                sb.append(com.alipay.sdk.sys.a.b);
            }
            sb.append(URLEncoder.encode(key));
            sb.append("=");
            sb.append(URLEncoder.encode(value));
        }
        return sb.toString();
    }

    public static void a() {
    }

    private void a(Map<String, String> map, HttpURLConnection httpURLConnection) {
        String str;
        c cVarG;
        if (map != null) {
            try {
                for (String str2 : map.keySet()) {
                    httpURLConnection.addRequestProperty(str2, map.get(str2));
                }
            } catch (Throwable th) {
                ak.a(th, "ht", "adh");
                return;
            }
        }
        if (bg.d != null) {
            for (String str3 : bg.d.keySet()) {
                httpURLConnection.addRequestProperty(str3, bg.d.get(str3));
            }
        }
        if (!this.m.contains("/v3/iasdkauth") && !TextUtils.isEmpty(this.j) && m.a(this.j)) {
            this.o = true;
            httpURLConnection.addRequestProperty("lct", String.valueOf(m.c(this.j)));
        }
        httpURLConnection.addRequestProperty("csid", this.g);
        if (a(this.u.c.e)) {
            f fVar = this.u;
            if (TextUtils.isEmpty(fVar.c.c)) {
                str = "";
            } else {
                String strB = p.b(bc.a(fVar.c.c.getBytes(), "YXBtX25ldHdvcmtf".getBytes()));
                StringBuilder sb = new StringBuilder("上报本次请求serverIp:");
                sb.append(fVar.c.c);
                sb.append("加密后：");
                sb.append(strB);
                str = strB;
            }
            if (!TextUtils.isEmpty(str)) {
                httpURLConnection.addRequestProperty("sip", str);
            }
            if (m.j && (cVarG = m.g()) != null) {
                httpURLConnection.addRequestProperty("nls", cVarG.b());
                this.u.e = cVarG;
            }
            a aVarF = m.f();
            if (aVarF != null) {
                httpURLConnection.addRequestProperty("nlf", aVarF.b());
                this.u.d = aVarF;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str) {
        if (this.l) {
            return true;
        }
        return (!TextUtils.isEmpty(this.n) && (this.n.contains("rest") || this.n.contains("apilocate"))) || b(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean a(Map<String, List<String>> map, boolean z) {
        boolean z2;
        boolean zA;
        List<String> list;
        try {
            List<String> list2 = map.get(com.igexin.push.g.q.e);
            if (list2 == null || list2.size() <= 0) {
                z2 = false;
                if (!z2) {
                    return false;
                }
                if (!z) {
                    return true;
                }
                if (!map.containsKey("lct") || (list = map.get("lct")) == null || list.size() <= 0) {
                    zA = false;
                    if (zA) {
                        return true;
                    }
                } else {
                    String str = list.get(0);
                    if (!TextUtils.isEmpty(str)) {
                        zA = m.a(this.j, Long.valueOf(str).longValue());
                    }
                    if (zA) {
                    }
                }
            } else {
                String str2 = list2.get(0);
                if (!TextUtils.isEmpty(str2)) {
                    if (str2.contains("#")) {
                        String[] strArrSplit = str2.split("#");
                        if (strArrSplit.length > 1 && "1".equals(strArrSplit[1])) {
                        }
                        z2 = false;
                        if (!z2) {
                        }
                    }
                    z2 = true;
                    if (!z2) {
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    private bk b() {
        try {
            if (t == null || t.get() == null) {
                t = new SoftReference<>(new bk(m.c, this.b));
            }
            bk bkVar = k != null ? t.get() : null;
            return bkVar == null ? new bk(m.c, this.b) : bkVar;
        } catch (Throwable th) {
            an.b(th, "ht", "gsf");
            return null;
        }
    }

    private static String b(Map<String, List<String>> map) {
        try {
            List<String> list = map.get(com.igexin.push.g.q.e);
            if (list == null || list.size() <= 0) {
                return "";
            }
            String str = list.get(0);
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            if (str.contains("#")) {
                String[] strArrSplit = str.split("#");
                if (strArrSplit.length <= 1) {
                    return "";
                }
                str = strArrSplit[0];
            }
            return str;
        } catch (Throwable unused) {
            return "";
        }
    }

    private void b(bl blVar) throws k {
        this.i = new d((byte) 0);
        this.p = blVar.y();
        this.c = blVar.o();
        this.h = blVar.t();
        this.l = blVar.q();
        this.j = blVar.z();
        this.a = q.a().a(blVar.s());
        this.m = blVar.u().b() ? blVar.m() : blVar.l();
        this.m = bi.a(this.m, this.j);
        this.n = blVar.g();
        if ("loc".equals(this.j)) {
            String strL = blVar.l();
            String strM = blVar.m();
            if (!TextUtils.isEmpty(strL)) {
                try {
                    this.r = new URL(strL).getHost();
                } catch (Exception unused) {
                }
            }
            if (TextUtils.isEmpty(strM)) {
                return;
            }
            try {
                if (TextUtils.isEmpty(this.n)) {
                    this.q = new URL(strM).getHost();
                } else {
                    this.q = this.n;
                }
            } catch (Exception unused2) {
            }
        }
    }

    private static boolean b(String str) {
        return str.contains("rest") || str.contains("apilocate");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private b c(bl blVar) throws k, IOException {
        String str;
        boolean z;
        HttpURLConnection httpURLConnection;
        bk bkVarB;
        f fVar = this.u;
        bl.b bVarU = blVar.u();
        fVar.c.a = bj.this.g;
        fVar.c.b = bVarU;
        fVar.a = SystemClock.elapsedRealtime();
        Map<String, String> mapA = blVar.a();
        if (mapA == null) {
            mapA = new HashMap<>();
        }
        e eVarA = this.i.a(this.n);
        int i = bg.a;
        String string = this.m;
        str = "";
        Uri uri = Uri.parse(string);
        String host = uri.getHost();
        switch (i) {
            case 1:
                str = bg.b;
                z = true;
                break;
            case 2:
                str = bg.c != null ? bg.c.get(host) : "";
                z = false;
                break;
            default:
                z = true;
                break;
        }
        if (!TextUtils.isEmpty(str)) {
            blVar.w();
            string = uri.buildUpon().encodedAuthority(str).build().toString();
            if (z && mapA != null) {
                mapA.put("targetHost", host);
                this.s = host;
            }
            if (z && this.a) {
                eVarA.a(str);
            }
        }
        this.m = string;
        URL url = new URL(this.m);
        this.u.a(blVar, url);
        if (b(url.getHost()) && blVar.c_()) {
            bl.b bVarU2 = blVar.u();
            String str2 = this.u.c.e;
            if (a(str2) && ((bVarU2.b() && m.g) || (bVarU2.c() && m.e(str2)))) {
                try {
                    this.u.b = SystemClock.elapsedRealtime();
                    String hostAddress = "";
                    InetAddress[] allByName = InetAddress.getAllByName(this.u.c.e);
                    if (allByName != null && allByName.length > 0 && allByName[0] != null) {
                        boolean z2 = m.a() && m.c();
                        "---canUseIpv6---".concat(String.valueOf(z2));
                        if (z2) {
                            int i2 = 0;
                            while (true) {
                                if (i2 < allByName.length) {
                                    if (allByName[i2] instanceof Inet6Address) {
                                        hostAddress = "[" + allByName[i2].getHostAddress() + "]";
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                        } else {
                            int i3 = 0;
                            while (true) {
                                if (i3 < allByName.length) {
                                    if (allByName[i3] instanceof Inet4Address) {
                                        hostAddress = allByName[i3].getHostAddress();
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                        }
                        if (TextUtils.isEmpty(hostAddress)) {
                            InetAddress inetAddress = allByName[0];
                            String hostAddress2 = inetAddress.getHostAddress();
                            if (inetAddress instanceof Inet6Address) {
                                hostAddress = "[" + hostAddress2 + "]";
                            } else {
                                hostAddress = hostAddress2;
                            }
                        }
                    }
                    f fVar2 = this.u;
                    "---onDNSEnd---ip=".concat(String.valueOf(hostAddress));
                    fVar2.c.c = hostAddress.replace("[", "").replace("]", "");
                    fVar2.c.g = SystemClock.elapsedRealtime() - fVar2.b;
                    if (!TextUtils.isEmpty(hostAddress)) {
                        Uri uri2 = Uri.parse(this.m);
                        String host2 = uri2.getHost();
                        Uri uriBuild = uri2.buildUpon().encodedAuthority(hostAddress).build();
                        this.n = host2;
                        mapA.put(com.alipay.sdk.cons.c.f, host2);
                        if (this.a) {
                            eVarA.b(host2);
                        }
                        this.m = uriBuild.toString();
                    }
                } catch (Throwable unused) {
                }
            }
        }
        if (this.a) {
            this.m = q.a(this.m);
        }
        StringBuilder sb = new StringBuilder("是否降级=");
        sb.append(blVar.u());
        sb.append("-最终url=");
        sb.append(this.m);
        URL url2 = new URL(this.m);
        URLConnection uRLConnectionA = this.h != null ? this.h.a() : null;
        if (uRLConnectionA == null) {
            uRLConnectionA = this.c != null ? url2.openConnection(this.c) : url2.openConnection();
        }
        if (this.a) {
            try {
                if (k == null || k.get() == null) {
                    k = new SoftReference<>(SSLContext.getInstance("TLS"));
                }
            } catch (Throwable unused2) {
            }
            SSLContext sSLContext = k != null ? k.get() : null;
            if (sSLContext == null) {
                try {
                    sSLContext = SSLContext.getInstance("TLS");
                } catch (Throwable th) {
                    ak.a(th, "ht", "ne");
                }
            }
            sSLContext.init(null, null, null);
            this.b = sSLContext;
            httpURLConnection = (HttpsURLConnection) uRLConnectionA;
            if (!m.f.a || (bkVarB = b()) == null) {
                ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(this.b.getSocketFactory());
            } else {
                ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(bkVarB);
                bkVarB.a();
            }
            ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(eVarA);
        } else {
            httpURLConnection = (HttpURLConnection) uRLConnectionA;
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            httpURLConnection.setRequestProperty("Connection", "close");
        }
        int iV = (int) (((long) blVar.v()) - (this.u.c.g / 1000));
        a(mapA, httpURLConnection);
        httpURLConnection.setConnectTimeout(iV);
        httpURLConnection.setReadTimeout(iV);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        return new b(httpURLConnection);
    }

    final bm a(bl blVar) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnection = null;
        dataOutputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            try {
                b(blVar);
                bm bmVarB = bi.b(this.m, this.j);
                if (bmVarB != null) {
                    this.u.d();
                    return bmVarB;
                }
                b bVarC = c(blVar);
                HttpURLConnection httpURLConnection2 = bVarC.a;
                try {
                    this.u.b = SystemClock.elapsedRealtime();
                    httpURLConnection2.connect();
                    this.u.a();
                    byte[] bArrE = blVar.e();
                    if (bArrE == null || bArrE.length == 0) {
                        Map<String, String> mapD = blVar.d();
                        if (bg.e != null) {
                            if (mapD != null) {
                                mapD.putAll(bg.e);
                            } else {
                                mapD = bg.e;
                            }
                        }
                        String strA = a(mapD);
                        if (!TextUtils.isEmpty(strA)) {
                            bArrE = x.a(strA);
                        }
                    }
                    if (bArrE != null && bArrE.length > 0) {
                        try {
                            this.u.b = SystemClock.elapsedRealtime();
                            OutputStream outputStream2 = httpURLConnection2.getOutputStream();
                            try {
                                DataOutputStream dataOutputStream2 = new DataOutputStream(outputStream2);
                                try {
                                    dataOutputStream2.write(bArrE);
                                    dataOutputStream2.close();
                                    if (outputStream2 != null) {
                                        outputStream2.close();
                                    }
                                    this.u.b();
                                } catch (Throwable th) {
                                    outputStream = outputStream2;
                                    th = th;
                                    dataOutputStream = dataOutputStream2;
                                    if (dataOutputStream != null) {
                                        dataOutputStream.close();
                                    }
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                    this.u.b();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                outputStream = outputStream2;
                                th = th2;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            outputStream = null;
                        }
                    }
                    bm bmVarA = a(bVarC);
                    this.u.a(bmVarA);
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                        } catch (Throwable th4) {
                            ak.a(th4, "ht", "mPt");
                        }
                    }
                    this.u.d();
                    return bmVarA;
                } catch (k e2) {
                    e = e2;
                } catch (MalformedURLException e3) {
                    e = e3;
                    e.printStackTrace();
                    this.u.a(8);
                    throw new k("url异常 - MalformedURLException");
                } catch (SocketTimeoutException e4) {
                    e = e4;
                    e.printStackTrace();
                    this.u.b(a(e));
                    this.u.a(2);
                    throw new k("socket 连接超时 - SocketTimeoutException");
                } catch (UnknownHostException e5) {
                    e = e5;
                    e.printStackTrace();
                    this.u.a(5);
                    throw new k("未知主机 - UnKnowHostException");
                } catch (SSLException e6) {
                    e = e6;
                    e.printStackTrace();
                    this.u.b(a(e));
                    this.u.a(4);
                    throw new k("IO 操作异常 - IOException");
                } catch (ConnectTimeoutException e7) {
                    e = e7;
                    e.printStackTrace();
                    this.u.b(a(e));
                    this.u.a(2);
                    throw new k("IO 操作异常 - IOException");
                } catch (InterruptedIOException unused) {
                    this.u.b(7101);
                    this.u.a(7);
                    throw new k("未知的错误");
                } catch (ConnectException e8) {
                    e = e8;
                    e.printStackTrace();
                    this.u.b(a(e));
                    this.u.a(6);
                    throw new k("http连接失败 - ConnectionException");
                } catch (SocketException e9) {
                    e = e9;
                    e.printStackTrace();
                    this.u.b(a(e));
                    this.u.a(6);
                    throw new k("socket 连接异常 - SocketException");
                } catch (IOException e10) {
                    e = e10;
                    e.printStackTrace();
                    this.u.a(7);
                    throw new k("IO 操作异常 - IOException");
                } catch (Throwable th5) {
                    th = th5;
                    ak.a(th, "ht", "mPt");
                    this.u.a(9);
                    throw new k("未知的错误");
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (k e11) {
            e = e11;
        } catch (InterruptedIOException unused2) {
        } catch (ConnectException e12) {
            e = e12;
        } catch (MalformedURLException e13) {
            e = e13;
        } catch (SocketException e14) {
            e = e14;
        } catch (SocketTimeoutException e15) {
            e = e15;
        } catch (UnknownHostException e16) {
            e = e16;
        } catch (SSLException e17) {
            e = e17;
        } catch (ConnectTimeoutException e18) {
            e = e18;
        } catch (IOException e19) {
            e = e19;
        } catch (Throwable th7) {
            th = th7;
        }
        if (!e.i() && e.g() != 10) {
            this.u.a(e.g());
        }
        ak.a(e, "ht", "mPt");
        throw e;
    }
}
