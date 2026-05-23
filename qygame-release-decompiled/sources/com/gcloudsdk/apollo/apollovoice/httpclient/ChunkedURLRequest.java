package com.gcloudsdk.apollo.apollovoice.httpclient;

import com.gcloudsdk.apollo.ApolloVoiceLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ChunkedURLRequest {
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
    private URL reqConnURL = null;
    private HttpURLConnection urlConn = null;
    private byte[] body = null;
    private long delegate = 0;
    private String method = "";
    private String url = "";
    private int timeout = 0;

    class RequestTask implements Runnable {
        RequestTask() {
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            BufferedOutputStream bufferedOutputStream;
            ChunkedURLRequest chunkedURLRequest;
            int i = 1;
            BufferedOutputStream bufferedOutputStream2 = null;
            Map<String, List<String>> headerFields = null;
            BufferedOutputStream bufferedOutputStream3 = null;
            BufferedOutputStream bufferedOutputStream4 = null;
            BufferedOutputStream bufferedOutputStream5 = null;
            try {
                try {
                    if (ChunkedURLRequest.this.body != null) {
                        bufferedOutputStream = new BufferedOutputStream(ChunkedURLRequest.this.urlConn.getOutputStream());
                        try {
                            bufferedOutputStream.write(ChunkedURLRequest.this.body);
                            bufferedOutputStream.flush();
                        } catch (SocketTimeoutException unused) {
                            bufferedOutputStream4 = bufferedOutputStream;
                            ApolloVoiceLog.LogI("SocketTimeoutException");
                            ChunkedURLRequest.this.response2cpp(2);
                            if (bufferedOutputStream4 != null) {
                                try {
                                    bufferedOutputStream4.close();
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        } catch (UnknownHostException unused2) {
                            bufferedOutputStream5 = bufferedOutputStream;
                            ApolloVoiceLog.LogI("UnknownHost");
                            ChunkedURLRequest.this.response2cpp(3);
                            if (bufferedOutputStream5 != null) {
                                try {
                                    bufferedOutputStream5.close();
                                    return;
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        } catch (Exception e3) {
                            e = e3;
                            bufferedOutputStream2 = bufferedOutputStream;
                            e.printStackTrace();
                            ChunkedURLRequest.this.response2cpp(1);
                            if (bufferedOutputStream2 != null) {
                                try {
                                    bufferedOutputStream2.close();
                                    return;
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            bufferedOutputStream3 = bufferedOutputStream;
                            if (bufferedOutputStream3 != null) {
                                try {
                                    bufferedOutputStream3.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } else if (ChunkedURLRequest.this.urlConn == null) {
                        ApolloVoiceLog.LogE("urlConn is null");
                        return;
                    } else {
                        ChunkedURLRequest.this.urlConn.connect();
                        bufferedOutputStream = null;
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    }
                    try {
                        headerFields = ChunkedURLRequest.this.urlConn.getHeaderFields();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                    if (headerFields == null || headerFields.entrySet() == null) {
                        ApolloVoiceLog.LogE("headerFields == null || headerFields.entrySet() == null");
                        chunkedURLRequest = ChunkedURLRequest.this;
                        i = 5;
                    } else {
                        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
                            String key = entry.getKey();
                            List<String> value = entry.getValue();
                            String str = "";
                            if (value != null) {
                                Iterator<String> it = value.iterator();
                                while (it.hasNext()) {
                                    str = str + it.next();
                                }
                            }
                            if (key != null) {
                                ApolloVoiceLog.LogI(key + ":" + str);
                                ChunkedURLRequest.header(ChunkedURLRequest.this.delegate, key + ":" + str);
                            }
                        }
                        try {
                            ChunkedURLRequest.this.urlConn.getResponseCode();
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                        try {
                            ChunkedURLRequest.this.urlConn.getResponseMessage();
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                        try {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(ChunkedURLRequest.this.urlConn.getInputStream());
                            byte[] bArr = new byte[1500];
                            while (true) {
                                try {
                                    try {
                                        int i2 = bufferedInputStream.read(bArr);
                                        if (i2 != -1) {
                                            ApolloVoiceLog.LogI("len: " + i2);
                                            ChunkedURLRequest.chunkeddata(ChunkedURLRequest.this.delegate, bArr, i2);
                                        } else {
                                            try {
                                                break;
                                            } catch (Exception e10) {
                                                e10.printStackTrace();
                                            }
                                        }
                                    } finally {
                                    }
                                } catch (Exception e11) {
                                    e11.printStackTrace();
                                    ChunkedURLRequest.this.response2cpp(6);
                                    try {
                                        bufferedInputStream.close();
                                    } catch (Exception e12) {
                                        e12.printStackTrace();
                                    }
                                    try {
                                        if (ChunkedURLRequest.this.urlConn != null) {
                                            ChunkedURLRequest.this.urlConn.disconnect();
                                            return;
                                        }
                                        return;
                                    } catch (Exception e13) {
                                        e13.printStackTrace();
                                        return;
                                    }
                                }
                            }
                            bufferedInputStream.close();
                            try {
                                if (ChunkedURLRequest.this.urlConn != null) {
                                    ChunkedURLRequest.this.urlConn.disconnect();
                                }
                            } catch (Exception e14) {
                                e14.printStackTrace();
                            }
                            chunkedURLRequest = ChunkedURLRequest.this;
                            i = 0;
                        } catch (Exception e15) {
                            e15.printStackTrace();
                            chunkedURLRequest = ChunkedURLRequest.this;
                        }
                    }
                    chunkedURLRequest.response2cpp(i);
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (SocketTimeoutException unused3) {
            } catch (UnknownHostException unused4) {
            } catch (Exception e16) {
                e = e16;
            }
        }
    }

    public static native void chunkeddata(long j, byte[] bArr, int i);

    public static native void header(long j, String str);

    public static native void response(long j, int i);

    public void addHead(String str, String str2) {
        try {
            this.urlConn.setRequestProperty(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int initWithURL(String str, int i) {
        this.url = str;
        this.method = "POST";
        this.timeout = i;
        try {
            this.reqConnURL = new URL(this.url);
            if (this.reqConnURL == null) {
                ApolloVoiceLog.LogE("reqConnURL");
                return -1;
            }
            try {
                this.urlConn = (HttpURLConnection) this.reqConnURL.openConnection();
                ApolloVoiceLog.LogI("After open Connection With URL: " + str);
                if (this.urlConn == null) {
                    ApolloVoiceLog.LogI("urlConn == null");
                    return -1;
                }
                try {
                    this.urlConn.setRequestMethod(this.method);
                    try {
                        this.urlConn.setReadTimeout(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        this.urlConn.setConnectTimeout(i);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        if (this.method == "POST") {
                            this.urlConn.setDoOutput(true);
                            this.urlConn.setUseCaches(false);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    return 0;
                } catch (ProtocolException e4) {
                    e4.printStackTrace();
                    return -1;
                } catch (Exception e5) {
                    e5.printStackTrace();
                    return -1;
                }
            } catch (Exception e6) {
                e6.printStackTrace();
                return -1;
            }
        } catch (MalformedURLException e7) {
            e7.printStackTrace();
            return -1;
        } catch (Exception e8) {
            e8.printStackTrace();
            return -1;
        }
    }

    public void quitHttp() {
        try {
            if (this.urlConn != null) {
                this.urlConn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void response2cpp(int i) {
        ApolloVoiceLog.LogI("url[" + this.url + "]response2cpp with result :" + i);
        response(this.delegate, i);
    }

    public void sendRequest() {
        new Thread(new RequestTask(), "GVoiceChunkedRequest").start();
    }

    public void setBody(byte[] bArr) {
        this.body = bArr;
    }

    public void setDelegate(long j) {
        this.delegate = j;
    }

    public void setMethod(String str) {
        this.method = str;
        try {
            this.urlConn.setRequestMethod(str);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
