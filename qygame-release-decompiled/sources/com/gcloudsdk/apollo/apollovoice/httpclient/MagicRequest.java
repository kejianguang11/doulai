package com.gcloudsdk.apollo.apollovoice.httpclient;

import com.alipay.sdk.sys.a;
import com.gcloudsdk.apollo.ApolloVoiceLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class MagicRequest {
    private static String LOGTAG = "GCloudVoice";
    private final int AV_HTTP_STATUS_SUCC = 0;
    private final int AV_HTTP_STATUS_FAIL = 1;
    private final int AV_HTTP_STATUS_TIMEOUT = 2;
    private final int AV_HTTP_STATUS_INVALIED_HOST = 3;
    private final int AV_HTTP_STATUS_INVALIED_URL = 4;
    private final int AV_HTTP_STATUS_NOHEADERS = 5;
    private final int AV_HTTP_STATUS_READBODY = 6;
    private final int AV_HTTP_STATUS_SEND_INCOMPLETE = 7;
    private final int AV_HTTP_STATUS_GET_CREATEFILE = 8;
    private final int AV_HTTP_STATUS_GET_WRITEFILE = 9;
    private final int AV_HTTP_STATUS_POST_READFILE = 10;
    private boolean mInit = false;
    private long mDelegate = 0;
    private int mHttpCnt = 0;
    ExecutorService a = null;

    class MagicHTTPTask {
        public String mContent;
        public int mEnd;
        public int mSeq;
        public String mSession;
        public int mTimeout;
        public String mToken;
        public String mUrl;

        public MagicHTTPTask(String str, String str2, String str3, String str4, int i, int i2, int i3) {
            this.mSession = str;
            this.mUrl = str2;
            this.mToken = str3;
            this.mContent = str4;
            this.mSeq = i;
            this.mEnd = i2;
            this.mTimeout = i3;
        }
    }

    class RunTask implements Runnable {
        private MagicHTTPTask mTask;

        public RunTask(MagicHTTPTask magicHTTPTask) {
            this.mTask = magicHTTPTask;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:101:0x0172  */
        /* JADX WARN: Type inference failed for: r3v10, types: [java.io.BufferedOutputStream, java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r3v11, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v14 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v16, types: [java.io.BufferedInputStream, java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r3v21 */
        /* JADX WARN: Type inference failed for: r3v22 */
        /* JADX WARN: Type inference failed for: r3v23 */
        /* JADX WARN: Type inference failed for: r3v24 */
        /* JADX WARN: Type inference failed for: r3v25 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void dealTask() {
            URL url;
            HttpURLConnection httpURLConnection;
            ?? bufferedOutputStream;
            ?? bufferedInputStream;
            ByteArrayOutputStream byteArrayOutputStream;
            ApolloVoiceLog.LogI("rsts http begin, seq=" + this.mTask.mSeq);
            try {
                url = new URL(this.mTask.mUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                url = null;
            }
            int i = 0;
            if (url == null) {
                ApolloVoiceLog.LogE("reqConnURL");
                i = 4;
            } else {
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    httpURLConnection = null;
                }
                ApolloVoiceLog.LogI("After open Connection With URL:" + this.mTask.mUrl);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Connection", "keep-alive");
                        httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
                        httpURLConnection.setRequestProperty("Authorization", this.mTask.mToken);
                        httpURLConnection.setReadTimeout(this.mTask.mTimeout);
                        httpURLConnection.setConnectTimeout(this.mTask.mTimeout);
                        try {
                            bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                            try {
                                try {
                                    bufferedOutputStream.write(this.mTask.mContent.getBytes(a.m));
                                    bufferedOutputStream.flush();
                                    try {
                                        bufferedOutputStream.close();
                                        bufferedInputStream = bufferedOutputStream;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        bufferedInputStream = e3;
                                    }
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    i = 7;
                                }
                            } finally {
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            i = 9;
                        }
                        try {
                            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] bArr = new byte[4096];
                            while (true) {
                                try {
                                    try {
                                        int i2 = bufferedInputStream.read(bArr);
                                        if (i2 == -1) {
                                            break;
                                        } else {
                                            byteArrayOutputStream.write(bArr, 0, i2);
                                        }
                                    } finally {
                                    }
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                    i = 6;
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (Exception e8) {
                                        e8.printStackTrace();
                                    }
                                    try {
                                        bufferedInputStream.close();
                                    } catch (Exception e9) {
                                        e9.printStackTrace();
                                    }
                                    if (httpURLConnection != null) {
                                        try {
                                            httpURLConnection.disconnect();
                                        } catch (Exception e10) {
                                            e = e10;
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            byteArrayOutputStream.flush();
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception e11) {
                                e11.printStackTrace();
                            }
                            try {
                                bufferedInputStream.close();
                            } catch (Exception e12) {
                                e12.printStackTrace();
                            }
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Exception e13) {
                                    e = e13;
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e14) {
                            e14.printStackTrace();
                            i = 1;
                            bufferedOutputStream = bufferedInputStream;
                            byteArrayOutputStream = null;
                        }
                    } catch (ProtocolException e15) {
                        e15.printStackTrace();
                        i = 10;
                    }
                    int i3 = i;
                    MagicRequest.a(MagicRequest.this);
                    MagicRequest.response(MagicRequest.this.mDelegate, i3, this.mTask.mSession, this.mTask.mSeq, this.mTask.mEnd, byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null);
                }
                ApolloVoiceLog.LogI("urlConn == null");
                i = 3;
            }
            byteArrayOutputStream = null;
            int i32 = i;
            MagicRequest.a(MagicRequest.this);
            MagicRequest.response(MagicRequest.this.mDelegate, i32, this.mTask.mSession, this.mTask.mSeq, this.mTask.mEnd, byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null);
        }

        @Override // java.lang.Runnable
        public void run() {
            dealTask();
        }
    }

    static /* synthetic */ int a(MagicRequest magicRequest) {
        int i = magicRequest.mHttpCnt - 1;
        magicRequest.mHttpCnt = i;
        return i;
    }

    public static native void response(long j, int i, String str, int i2, int i3, byte[] bArr);

    public int addTask(String str, String str2, String str3, String str4, int i, int i2, int i3) {
        if (!this.mInit) {
            return -1;
        }
        if (this.mHttpCnt > 1600) {
            ApolloVoiceLog.LogE("too many https task waiting...");
            return -1;
        }
        MagicHTTPTask magicHTTPTask = new MagicHTTPTask(str, str2, str3, str4, i, i2, i3);
        this.mHttpCnt++;
        this.a.submit(new RunTask(magicHTTPTask));
        return 0;
    }

    public void setDelegate(long j) {
        this.mHttpCnt = 0;
        this.mDelegate = j;
        this.a = Executors.newFixedThreadPool(10);
        this.mInit = true;
    }
}
