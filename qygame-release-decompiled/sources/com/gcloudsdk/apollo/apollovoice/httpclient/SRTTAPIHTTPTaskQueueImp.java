package com.gcloudsdk.apollo.apollovoice.httpclient;

import com.alipay.sdk.cons.b;
import com.gcloudsdk.apollo.ApolloVoiceLog;
import com.igexin.push.config.c;
import com.mobile.auth.gatewayauth.Constant;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HttpsURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class SRTTAPIHTTPTaskQueueImp {
    public static final int RSTT_HTTP_ERR_FAIL = 1;
    public static final int RSTT_HTTP_ERR_SUCC = 0;
    public static final int RSTT_HTTP_ERR_getInputStream_IOException = 13;
    public static final int RSTT_HTTP_ERR_getInputStream_UnknownServiceException = 12;
    public static final int RSTT_HTTP_ERR_getOutputStream_IOException = 16;
    public static final int RSTT_HTTP_ERR_getResponseCode_IOException = 17;
    public static final int RSTT_HTTP_ERR_new_URL = 10;
    public static final int RSTT_HTTP_ERR_openConnection = 11;
    public static final int RSTT_HTTP_ERR_read_IOException = 14;
    public static final int RSTT_HTTP_ERR_write_IOException = 15;
    private static String apiAddr = "";
    private static String apiKey = "";
    public static ThreadPoolExecutor threadPool;
    private HashMap<Integer, RSTTSession> sessionMap = new HashMap<>();
    public ArrayList<String> tryUrlsList = new ArrayList<>();
    int a = 20;

    class RSTTSession {
        public boolean isValid;
        public long lastFeedTime;
        public String lastUrl;
        public int sessionid;
        public LinkedList<SRTTAPIHTTPTask> taskQueue;
        public int taskid;
        public ArrayList<UrlPair> tryUrls;

        RSTTSession() {
        }

        public void ResetTryUrls() {
            synchronized (this.tryUrls) {
                if (SRTTAPIHTTPTaskQueueImp.this.a < 4) {
                    this.tryUrls.clear();
                    String url = SRTTAPIHTTPTaskQueueImp.this.formatURL(SRTTAPIHTTPTaskQueueImp.apiAddr, true);
                    if (url != null && url.length() > 0) {
                        this.tryUrls.add(SRTTAPIHTTPTaskQueueImp.this.new UrlPair(url, true));
                    }
                    return;
                }
                for (int i = 0; i < this.tryUrls.size(); i++) {
                    this.tryUrls.set(i, SRTTAPIHTTPTaskQueueImp.this.new UrlPair(this.tryUrls.get(i).key, true));
                }
            }
        }

        boolean a() {
            return System.currentTimeMillis() - this.lastFeedTime > c.i;
        }

        public void addTask(int i, int i2, String str, String str2, byte[] bArr) {
            if (!this.isValid) {
                ApolloVoiceLog.LogE("rstt session is invalid,cannot add task");
                return;
            }
            this.lastFeedTime = System.currentTimeMillis();
            SRTTAPIHTTPTask sRTTAPIHTTPTask = new SRTTAPIHTTPTask();
            sRTTAPIHTTPTask.type = i;
            sRTTAPIHTTPTask.body = bArr;
            sRTTAPIHTTPTask.key = str2;
            sRTTAPIHTTPTask.seq = i2;
            sRTTAPIHTTPTask.micType = str;
            try {
                SRTTAPIHTTPTaskQueueImp.threadPool.execute(SRTTAPIHTTPTaskQueueImp.this.new RequestTask(this, sRTTAPIHTTPTask));
                ApolloVoiceLog.LogI("rstt thread feed task");
            } catch (RejectedExecutionException unused) {
                ApolloVoiceLog.LogI("rstt thread queue task");
                synchronized (this.taskQueue) {
                    this.taskQueue.offer(sRTTAPIHTTPTask);
                }
            }
        }

        protected void finalize() {
            ApolloVoiceLog.LogI("rstt session release, id:" + this.sessionid);
        }

        public void init(int i, ArrayList<String> arrayList) {
            this.lastUrl = "";
            this.taskid = 0;
            this.sessionid = i;
            this.isValid = true;
            this.lastFeedTime = 0L;
            this.taskQueue = new LinkedList<>();
            this.tryUrls = new ArrayList<>();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                this.tryUrls.add(SRTTAPIHTTPTaskQueueImp.this.new UrlPair(it.next(), true));
            }
        }

        public String nextUrl() {
            synchronized (this.tryUrls) {
                if (!this.isValid) {
                    return null;
                }
                if (this.lastUrl.length() > 0) {
                    return this.lastUrl;
                }
                if (this.tryUrls.size() <= 0) {
                    return "";
                }
                for (int i = 0; i < this.tryUrls.size(); i++) {
                    if (this.tryUrls.get(i).value) {
                        String str = this.tryUrls.get(i).key;
                        this.tryUrls.set(i, SRTTAPIHTTPTaskQueueImp.this.new UrlPair(str, false));
                        return str;
                    }
                }
                ResetTryUrls();
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return nextUrl();
            }
        }

        public void setLastUrl(String str) {
            synchronized (this.tryUrls) {
                this.lastUrl = str;
            }
        }
    }

    class RequestTask implements Runnable {
        private RSTTSession session;
        private SRTTAPIHTTPTask task;

        public RequestTask(RSTTSession rSTTSession, SRTTAPIHTTPTask sRTTAPIHTTPTask) {
            this.session = null;
            this.task = null;
            this.session = rSTTSession;
            this.task = sRTTAPIHTTPTask;
        }

        /* JADX WARN: Code restructure failed: missing block: B:167:0x02da, code lost:
        
            r17 = r10;
         */
        /* JADX WARN: Code restructure failed: missing block: B:168:0x02dc, code lost:
        
            r18 = r2;
            r21 = r3;
            r19 = r12;
            r20 = r17;
         */
        /* JADX WARN: Removed duplicated region for block: B:197:0x01e8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:250:0x01d1 A[EDGE_INSN: B:250:0x01d1->B:89:0x01d1 BREAK  A[LOOP:1: B:204:0x01c5->B:88:0x01cc], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:253:0x01f1 A[EDGE_INSN: B:253:0x01f1->B:106:0x01f1 BREAK  A[LOOP:0: B:3:0x001f->B:259:0x001f], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:75:0x01a9  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x01cc A[Catch: all -> 0x0207, IOException -> 0x020a, LOOP:1: B:204:0x01c5->B:88:0x01cc, LOOP_END, TryCatch #5 {IOException -> 0x020a, blocks: (B:86:0x01c5, B:88:0x01cc, B:89:0x01d1), top: B:204:0x01c5, outer: #6 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int dealTask() {
            String strNextUrl;
            StringBuilder sb;
            RSTTSession rSTTSession;
            String str;
            String str2;
            String str3;
            BufferedOutputStream bufferedOutputStream;
            int responseCode;
            String str4;
            RSTTSession rSTTSession2;
            String str5;
            BufferedInputStream bufferedInputStream;
            byte[] bArr;
            int i;
            String responseMessage;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.session.ResetTryUrls();
            long jCurrentTimeMillis = System.currentTimeMillis();
            String str6 = "OK";
            int i2 = 200;
            int i3 = 0;
            String strExceptionToString = "";
            String str7 = "No error";
            int i4 = 0;
            while (true) {
                strNextUrl = this.session.nextUrl();
                if (strNextUrl == null || strNextUrl.length() <= 0) {
                    break;
                }
                int i5 = i4 + 1;
                if (i5 > SRTTAPIHTTPTaskQueueImp.this.a) {
                    ApolloVoiceLog.LogE("http try max times:" + SRTTAPIHTTPTaskQueueImp.this.a);
                    break;
                }
                String str8 = str6;
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                if (jCurrentTimeMillis2 > c.i) {
                    ApolloVoiceLog.LogE("rstt try timeout:" + jCurrentTimeMillis2);
                    break;
                }
                try {
                    if (1 == this.task.type) {
                        sb = new StringBuilder();
                        sb.append(strNextUrl);
                        sb.append("?cmd=1&appid=");
                        sb.append(SRTTAPIHTTPTaskQueueImp.apiKey);
                    } else {
                        sb = new StringBuilder();
                        sb.append(strNextUrl);
                        sb.append("?platform=android&record_device=");
                        sb.append(this.task.micType);
                        sb.append("&cmd=6&appid=");
                        sb.append(SRTTAPIHTTPTaskQueueImp.apiKey);
                        sb.append("&voice_id=");
                        sb.append(this.task.key);
                    }
                    String string = sb.toString();
                    ApolloVoiceLog.LogI("rstt url:" + string);
                    try {
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(string).openConnection();
                            if (string.startsWith(b.a) && HttpsUtils.connnectWithIP(string)) {
                                if (httpURLConnection instanceof HttpsURLConnection) {
                                    try {
                                        ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(HttpsUtils.getHostNameVerify(string));
                                    } catch (IllegalArgumentException e) {
                                        ApolloVoiceLog.LogE("Maybe HttpsUtils's HostnameVerifier or SSLSocketFactory is null!");
                                        e.printStackTrace();
                                    }
                                } else {
                                    ApolloVoiceLog.LogI("reqConn is not an instance of HttpsURLConnection.");
                                }
                            }
                            try {
                                httpURLConnection.setRequestMethod("POST");
                            } catch (ProtocolException e2) {
                                e2.printStackTrace();
                            }
                            if (1 == this.task.type) {
                                str2 = "Content-Type";
                                str3 = "text/html";
                            } else {
                                str2 = "Content-Type";
                                str3 = "application/octet-stream";
                            }
                            httpURLConnection.setRequestProperty(str2, str3);
                            httpURLConnection.setRequestProperty("Connection", "keep-alive");
                            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
                            httpURLConnection.setConnectTimeout(Constant.DEFAULT_TIMEOUT);
                            httpURLConnection.setReadTimeout(Constant.DEFAULT_TIMEOUT);
                            if (1 != this.task.type) {
                                try {
                                    bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                                } catch (IOException e3) {
                                    strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e3);
                                    str7 = "getOutputStream failed,IOException";
                                    i3 = 16;
                                    rSTTSession = this.session;
                                    str = "";
                                    rSTTSession.setLastUrl(str);
                                    i4 = i5;
                                    str6 = str8;
                                }
                                try {
                                    try {
                                        bufferedOutputStream.write(this.task.body);
                                        bufferedOutputStream.flush();
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                        }
                                        try {
                                            responseCode = httpURLConnection.getResponseCode();
                                            try {
                                                try {
                                                    responseMessage = httpURLConnection.getResponseMessage();
                                                    if (responseMessage != null) {
                                                        str8 = responseMessage;
                                                    }
                                                } catch (Exception e5) {
                                                    e = e5;
                                                    i2 = responseCode;
                                                    str6 = str8;
                                                    strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e);
                                                    str7 = "unknown exception";
                                                    this.session.setLastUrl("");
                                                    i3 = 1;
                                                    i4 = i5;
                                                }
                                            } catch (IOException e6) {
                                                SRTTAPIHTTPTaskQueueImp.ExceptionToString(e6);
                                            }
                                            str6 = str8;
                                        } catch (IOException e7) {
                                            strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e7);
                                            str7 = "getResponseCode failed,IOException";
                                            i3 = 17;
                                            rSTTSession = this.session;
                                            str = "";
                                            rSTTSession.setLastUrl(str);
                                            i4 = i5;
                                            str6 = str8;
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (IOException e8) {
                                            e8.printStackTrace();
                                        }
                                        throw th;
                                    }
                                } catch (IOException e9) {
                                    strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e9);
                                    i3 = 15;
                                    this.session.setLastUrl("");
                                    try {
                                        bufferedOutputStream.close();
                                    } catch (IOException e10) {
                                        e10.printStackTrace();
                                    }
                                    str7 = "OutputStream write failed,IOException";
                                    i4 = i5;
                                    str6 = str8;
                                }
                                try {
                                    try {
                                        try {
                                            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                                            bArr = new byte[1500];
                                            while (true) {
                                                try {
                                                    try {
                                                        i = bufferedInputStream.read(bArr);
                                                        if (i != -1) {
                                                            break;
                                                        }
                                                        byteArrayOutputStream.write(bArr, 0, i);
                                                    } catch (IOException e11) {
                                                        String strExceptionToString2 = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e11);
                                                        this.session.setLastUrl("");
                                                        try {
                                                            byteArrayOutputStream.close();
                                                        } catch (Exception e12) {
                                                            e12.printStackTrace();
                                                        }
                                                        try {
                                                            bufferedInputStream.close();
                                                        } catch (Exception e13) {
                                                            e13.printStackTrace();
                                                        }
                                                        if (httpURLConnection != null) {
                                                            try {
                                                                httpURLConnection.disconnect();
                                                            } catch (Exception e14) {
                                                                e14.printStackTrace();
                                                            }
                                                        }
                                                        strExceptionToString = strExceptionToString2;
                                                        i3 = 14;
                                                        i4 = i5;
                                                        i2 = responseCode;
                                                        str7 = "InputStream read failed,IOException";
                                                    }
                                                } finally {
                                                }
                                            }
                                            byteArrayOutputStream.flush();
                                            try {
                                                byteArrayOutputStream.close();
                                            } catch (Exception e15) {
                                                e15.printStackTrace();
                                            }
                                            try {
                                                bufferedInputStream.close();
                                            } catch (Exception e16) {
                                                e16.printStackTrace();
                                            }
                                            if (httpURLConnection != null) {
                                                break;
                                            }
                                            try {
                                                httpURLConnection.disconnect();
                                                break;
                                            } catch (Exception e17) {
                                                e17.printStackTrace();
                                            }
                                        } catch (UnknownServiceException e18) {
                                            strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e18);
                                            str4 = "getInputStream failed,UnknownServiceException";
                                            i3 = 12;
                                            rSTTSession2 = this.session;
                                            str5 = "";
                                            rSTTSession2.setLastUrl(str5);
                                            i2 = responseCode;
                                            str7 = str4;
                                            i4 = i5;
                                        }
                                    } catch (Exception e19) {
                                        e = e19;
                                        i2 = responseCode;
                                        strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e);
                                        str7 = "unknown exception";
                                        this.session.setLastUrl("");
                                        i3 = 1;
                                        i4 = i5;
                                    }
                                } catch (IOException e20) {
                                    strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e20);
                                    str4 = "getInputStream failed,IOException";
                                    i3 = 13;
                                    rSTTSession2 = this.session;
                                    str5 = "";
                                    rSTTSession2.setLastUrl(str5);
                                    i2 = responseCode;
                                    str7 = str4;
                                    i4 = i5;
                                }
                            } else {
                                responseCode = httpURLConnection.getResponseCode();
                                responseMessage = httpURLConnection.getResponseMessage();
                                if (responseMessage != null) {
                                }
                                str6 = str8;
                                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                                bArr = new byte[1500];
                                while (true) {
                                    i = bufferedInputStream.read(bArr);
                                    if (i != -1) {
                                    }
                                    byteArrayOutputStream.write(bArr, 0, i);
                                }
                                byteArrayOutputStream.flush();
                                byteArrayOutputStream.close();
                                bufferedInputStream.close();
                                if (httpURLConnection != null) {
                                }
                            }
                        } catch (IOException e21) {
                            strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e21);
                            str7 = "openConnection failed,IOException";
                            i3 = 11;
                            rSTTSession = this.session;
                            str = "";
                        }
                    } catch (MalformedURLException e22) {
                        strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e22);
                        str7 = "new URL failed,MalformedURLException,url=" + string;
                        i3 = 10;
                        rSTTSession = this.session;
                        str = "";
                    }
                } catch (Exception e23) {
                    e = e23;
                    str6 = str8;
                    strExceptionToString = SRTTAPIHTTPTaskQueueImp.ExceptionToString(e);
                    str7 = "unknown exception";
                    this.session.setLastUrl("");
                    i3 = 1;
                    i4 = i5;
                }
            }
            this.session.setLastUrl(strNextUrl);
            String str9 = "No error";
            String str10 = "";
            int i6 = 200;
            String str11 = "OK";
            i3 = 0;
            if (this.session.isValid) {
                SRTTAPIHTTPTaskQueueImp.callback(i3, str9, i6, str11, str10, byteArrayOutputStream.toByteArray(), this.session.sessionid);
            }
            synchronized (this.session.taskQueue) {
                if (i3 != 0) {
                    try {
                        this.session.taskQueue.clear();
                        this.session.isValid = false;
                    } finally {
                    }
                }
            }
            if (!this.session.isValid || this.task.type == 3) {
                synchronized (SRTTAPIHTTPTaskQueueImp.this.sessionMap) {
                    if (SRTTAPIHTTPTaskQueueImp.this.sessionMap.remove(Integer.valueOf(this.session.sessionid)) != null) {
                        ApolloVoiceLog.LogI("rstt session end!!!sessionid:" + this.session.sessionid);
                    }
                }
            }
            return i3;
        }

        /* JADX WARN: Code restructure failed: missing block: B:80:0x0000, code lost:
        
            continue;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            StringBuilder sb;
            int i;
            while (this.session != null && this.task != null) {
                ApolloVoiceLog.LogI("rstt thread:" + Thread.currentThread().getName() + ",run task,sessionid:" + this.session.sessionid + ",taskid:" + this.task.seq);
                switch (this.task.type) {
                    case 1:
                    case 2:
                    case 3:
                        if (dealTask() != 0) {
                            ApolloVoiceLog.LogE("rstt failed, session:" + this.session.sessionid + ",taskid:" + this.task.seq);
                        } else {
                            sb = new StringBuilder();
                            sb.append("rstt done, session:");
                            sb.append(this.session.sessionid);
                            sb.append(",taskid:");
                            i = this.task.seq;
                            sb.append(i);
                            ApolloVoiceLog.LogI(sb.toString());
                        }
                        break;
                    default:
                        sb = new StringBuilder();
                        sb.append("Unknown type: ");
                        i = this.task.type;
                        sb.append(i);
                        ApolloVoiceLog.LogI(sb.toString());
                        break;
                }
                HashSet<Integer> hashSet = new HashSet();
                synchronized (SRTTAPIHTTPTaskQueueImp.this.sessionMap) {
                    hashSet.addAll(SRTTAPIHTTPTaskQueueImp.this.sessionMap.keySet());
                }
                this.task = null;
                while (this.session != null) {
                    synchronized (this.session.taskQueue) {
                        if (!this.session.taskQueue.isEmpty()) {
                            try {
                                this.task = this.session.taskQueue.remove();
                            } catch (Exception e) {
                                e.printStackTrace();
                                this.task = null;
                            }
                        }
                    }
                    if (this.task != null) {
                        break;
                    }
                    if (this.session.a()) {
                        synchronized (SRTTAPIHTTPTaskQueueImp.this.sessionMap) {
                            if (SRTTAPIHTTPTaskQueueImp.this.sessionMap.remove(Integer.valueOf(this.session.sessionid)) != null) {
                                ApolloVoiceLog.LogI("rstt session timeout!!!sessionid:" + this.session.sessionid);
                            }
                        }
                    }
                    this.session = null;
                    for (Integer num : hashSet) {
                        synchronized (SRTTAPIHTTPTaskQueueImp.this.sessionMap) {
                            this.session = (RSTTSession) SRTTAPIHTTPTaskQueueImp.this.sessionMap.get(num);
                        }
                        if (this.session != null) {
                            break;
                        }
                    }
                }
            }
            ApolloVoiceLog.LogI("rstt thread:" + Thread.currentThread().getName() + " run end");
        }
    }

    class UrlPair {
        public String key;
        public boolean value;

        public UrlPair(String str, boolean z) {
            this.key = str;
            this.value = z;
        }
    }

    public static String ExceptionToString(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            ApolloVoiceLog.LogE(string);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static native void callback(int i, String str, int i2, String str2, String str3, byte[] bArr, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public String formatURL(String str, boolean z) {
        if (str != null && str.length() >= 5) {
            return (z && str.startsWith("https:")) ? str : (z || !str.startsWith("http:")) ? (z || !str.startsWith("https:")) ? (z && str.startsWith("http:")) ? b.a.concat(str.substring(4)) : str : "http".concat(str.substring(5)) : str;
        }
        ApolloVoiceLog.LogI("url is null");
        return "";
    }

    private void initTryUrls(String str, String str2) {
        this.tryUrlsList.clear();
        if (str2 == null || str2.length() <= 0) {
            String url = formatURL(str, true);
            if (url != null && url.length() > 0) {
                this.tryUrlsList.add(url);
            }
            String url2 = formatURL(str, false);
            if (url2 != null && url2.length() > 0) {
                this.tryUrlsList.add(url2);
            }
            if (url == null || url.length() <= 0) {
                return;
            }
            this.tryUrlsList.add(url);
            return;
        }
        for (String str3 : str2.split("\\|")) {
            if (str3 != null && str3.length() != 0) {
                this.tryUrlsList.add("https://" + str3 + "/cgi-bin/wxvoicereco");
                this.tryUrlsList.add("http://" + str3 + "/cgi-bin/wxvoicereco");
            }
        }
        String url3 = formatURL(str, false);
        if (url3 != null && url3.length() > 0) {
            this.tryUrlsList.add(url3);
        }
        String url4 = formatURL(str, true);
        if (url4 == null || url4.length() <= 0) {
            return;
        }
        this.tryUrlsList.add(url4);
    }

    public void addTask(int i, String str, String str2, byte[] bArr, int i2) {
        RSTTSession rSTTSession;
        synchronized (this.sessionMap) {
            rSTTSession = this.sessionMap.get(Integer.valueOf(i2));
        }
        if (rSTTSession != null) {
            int i3 = rSTTSession.taskid + 1;
            rSTTSession.taskid = i3;
            rSTTSession.addTask(i, i3, str, str2, bArr);
            return;
        }
        if (i != 1) {
            ApolloVoiceLog.LogE("addTask failed,sessionid:" + i2);
            return;
        }
        if (threadPool == null) {
            threadPool = new ThreadPoolExecutor(0, 5, 1L, TimeUnit.SECONDS, new SynchronousQueue());
        }
        RSTTSession rSTTSession2 = new RSTTSession();
        rSTTSession2.init(i2, this.tryUrlsList);
        int i4 = rSTTSession2.taskid + 1;
        rSTTSession2.taskid = i4;
        rSTTSession2.addTask(i, i4, str, str2, bArr);
        synchronized (this.sessionMap) {
            ApolloVoiceLog.LogE("rstt session count:" + this.sessionMap.size());
            this.sessionMap.put(Integer.valueOf(i2), rSTTSession2);
        }
    }

    public void setAppInfo(String str, String str2, String str3, int i) {
        apiKey = str;
        apiAddr = str2;
        this.a = i;
        initTryUrls(str2, str3);
    }
}
