package com.gcloudsdk.apollo.apollovoice.httpclient;

import com.gcloudsdk.apollo.ApolloVoiceLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class URLRequest {
    private byte[] body;
    private long delegate;
    private String getFilePath;
    private String method;
    private String postFilePath;
    private URL reqConnURL;
    private URLResponse response;
    private int timeout;
    private HttpURLConnection urlConn;
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
    private final int HTTP_OK = 0;
    private final int HTTP_CANNOT_FIND_FILE = -100;
    private final int HTTP_OPEN_FILE_SECURITY_EXCEPTION = -101;
    private final int HTTP_OPEN_FILE_OTERH_EXCEPTION = -102;
    private final int HTTP_UNKNOWN_HOST_EXCEPTION = -103;
    private final int HTTP_TIMEOUT_EXCEPTION = -104;
    private final int HTTP_POST_OTHER_EXCEPTION = -105;
    private final int HTTP_NO_HEADER_ERROR = -106;
    private final int HTTP_RESPONSE_ERROR_404 = -107;
    private final int HTTP_RESPONSE_FILE_NOT_FOUND_EXCEPTION = -108;
    private final int HTTP_RESPONSE_OTHER_EXCEPTION = -109;
    private final int HTTP_WRITE_FILE_ACCESS_EXCEPTION = -110;
    private final int HTTP_WRITE_FILE_SECURITY_EXCEPTION = -111;
    private final int HTTP_WRITE_FILE_OTHER_EXCEPTION = -112;
    private final int HTTP_READ_AND_WRITE_DATA_EXCEPTION = -113;

    class RequestTask implements Runnable {
        private String filepath;

        public RequestTask(String str) {
            this.filepath = str;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:355:0x055a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:357:0x05d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:362:0x05e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:368:0x0589 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:372:0x0593 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:392:0x0126 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:397:0x0130 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:409:0x05cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:413:0x0550 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:423:0x05c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:473:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:475:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:477:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:479:? A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r2v1, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r2v11 */
        /* JADX WARN: Type inference failed for: r2v18 */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r6v0 */
        /* JADX WARN: Type inference failed for: r6v1, types: [java.io.InputStream] */
        /* JADX WARN: Type inference failed for: r6v4 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws Throwable {
            FileInputStream fileInputStream;
            BufferedOutputStream bufferedOutputStream;
            Map<String, List<String>> headerFields;
            BufferedInputStream bufferedInputStream;
            ?? r2 = 0;
            fileOutputStream = null;
            FileOutputStream fileOutputStream = null;
            bufferedOutputStream = null;
            bufferedOutputStream = null;
            bufferedOutputStream = null;
            r2 = 0;
            BufferedOutputStream bufferedOutputStream2 = null;
            BufferedOutputStream bufferedOutputStream3 = null;
            BufferedOutputStream bufferedOutputStream4 = null;
            try {
                try {
                    try {
                        if (this.filepath == "" || URLRequest.this.method != "POST") {
                            fileInputStream = null;
                        } else {
                            try {
                                fileInputStream = new FileInputStream(this.filepath);
                            } catch (FileNotFoundException e) {
                                ApolloVoiceLog.LogE("Post File,open file cannot find file");
                                e.printStackTrace();
                                URLRequest.this.response.status = -100;
                                URLRequest.this.response.statusMsg = "open file cannot find file";
                                URLRequest.this.response2cpp(10);
                                return;
                            } catch (SecurityException e2) {
                                ApolloVoiceLog.LogE("Post File,open file security exception");
                                e2.printStackTrace();
                                URLRequest.this.response.status = -101;
                                URLRequest.this.response.statusMsg = "open file security exception";
                                URLRequest.this.response2cpp(10);
                                return;
                            } catch (Exception e3) {
                                ApolloVoiceLog.LogE("Post File,open file other exception");
                                e3.printStackTrace();
                                URLRequest.this.response.status = -102;
                                URLRequest.this.response.statusMsg = "open file other exception";
                                URLRequest.this.response2cpp(10);
                                return;
                            }
                        }
                        try {
                            ?? r6 = 1500;
                            if (URLRequest.this.body == null) {
                                if (this.filepath == "" || URLRequest.this.method != "POST" || fileInputStream == null) {
                                    if (URLRequest.this.urlConn == null) {
                                        ApolloVoiceLog.LogE("urlConn is null");
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                                return;
                                            } catch (Exception e4) {
                                                e4.printStackTrace();
                                                return;
                                            }
                                        }
                                        return;
                                    }
                                    URLRequest.this.urlConn.connect();
                                    bufferedOutputStream = null;
                                } else {
                                    byte[] bArr = new byte[1500];
                                    BufferedOutputStream bufferedOutputStream5 = new BufferedOutputStream(URLRequest.this.urlConn.getOutputStream());
                                    while (true) {
                                        try {
                                            int i = fileInputStream.read(bArr);
                                            if (i == -1) {
                                                break;
                                            }
                                            bufferedOutputStream5.write(bArr, 0, i);
                                            bufferedOutputStream5.flush();
                                        } catch (SocketTimeoutException e5) {
                                            e = e5;
                                            bufferedOutputStream3 = bufferedOutputStream5;
                                            ApolloVoiceLog.LogE("Post File,timeout exception");
                                            e.printStackTrace();
                                            URLRequest.this.response.status = -104;
                                            URLRequest.this.response.statusMsg = "timeout exception";
                                            URLRequest.this.response2cpp(2);
                                            if (bufferedOutputStream3 != null) {
                                                try {
                                                    bufferedOutputStream3.close();
                                                } catch (Exception e6) {
                                                    e6.printStackTrace();
                                                }
                                            }
                                            if (fileInputStream == null) {
                                                try {
                                                    fileInputStream.close();
                                                    return;
                                                } catch (Exception e7) {
                                                    e7.printStackTrace();
                                                    return;
                                                }
                                            }
                                            return;
                                        } catch (UnknownHostException e8) {
                                            e = e8;
                                            bufferedOutputStream4 = bufferedOutputStream5;
                                            ApolloVoiceLog.LogE("Post File,unknown host exception");
                                            e.printStackTrace();
                                            URLRequest.this.response.status = -103;
                                            URLRequest.this.response.statusMsg = "unknown host exception";
                                            URLRequest.this.response2cpp(3);
                                            if (bufferedOutputStream4 != null) {
                                                try {
                                                    bufferedOutputStream4.close();
                                                } catch (Exception e9) {
                                                    e9.printStackTrace();
                                                }
                                            }
                                            if (fileInputStream == null) {
                                                try {
                                                    fileInputStream.close();
                                                    return;
                                                } catch (Exception e10) {
                                                    e10.printStackTrace();
                                                    return;
                                                }
                                            }
                                            return;
                                        } catch (Exception e11) {
                                            e = e11;
                                            bufferedOutputStream2 = bufferedOutputStream5;
                                            ApolloVoiceLog.LogE("Post File,other exception");
                                            e.printStackTrace();
                                            URLRequest.this.response.status = -105;
                                            URLRequest.this.response.statusMsg = "other exception";
                                            URLRequest.this.response2cpp(1);
                                            if (bufferedOutputStream2 != null) {
                                                try {
                                                    bufferedOutputStream2.close();
                                                } catch (Exception e12) {
                                                    e12.printStackTrace();
                                                }
                                            }
                                            if (fileInputStream == null) {
                                                try {
                                                    fileInputStream.close();
                                                    return;
                                                } catch (Exception e13) {
                                                    e13.printStackTrace();
                                                    return;
                                                }
                                            }
                                            return;
                                        } catch (Throwable th) {
                                            th = th;
                                            r2 = bufferedOutputStream5;
                                            if (r2 != 0) {
                                                try {
                                                    r2.close();
                                                } catch (Exception e14) {
                                                    e14.printStackTrace();
                                                }
                                            }
                                            if (fileInputStream != null) {
                                                throw th;
                                            }
                                            try {
                                                fileInputStream.close();
                                                throw th;
                                            } catch (Exception e15) {
                                                e15.printStackTrace();
                                                throw th;
                                            }
                                        }
                                    }
                                    bufferedOutputStream = bufferedOutputStream5;
                                }
                                if (bufferedOutputStream != null) {
                                }
                                if (fileInputStream != null) {
                                }
                                headerFields = URLRequest.this.urlConn.getHeaderFields();
                                if (headerFields != null) {
                                }
                                ApolloVoiceLog.LogE("headerFields == null || headerFields.entrySet() == null");
                                URLRequest.this.response.status = -106;
                                URLRequest.this.response.statusMsg = "no header error";
                                URLRequest.this.response2cpp(5);
                                return;
                            }
                            bufferedOutputStream = new BufferedOutputStream(URLRequest.this.urlConn.getOutputStream());
                            try {
                                bufferedOutputStream.write(URLRequest.this.body);
                                bufferedOutputStream.flush();
                                if (bufferedOutputStream != null) {
                                    try {
                                        bufferedOutputStream.close();
                                    } catch (Exception e16) {
                                        e16.printStackTrace();
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception e17) {
                                        e17.printStackTrace();
                                    }
                                }
                                try {
                                    headerFields = URLRequest.this.urlConn.getHeaderFields();
                                } catch (Exception e18) {
                                    e18.printStackTrace();
                                    headerFields = null;
                                }
                                if (headerFields != null || headerFields.entrySet() == null) {
                                    ApolloVoiceLog.LogE("headerFields == null || headerFields.entrySet() == null");
                                    URLRequest.this.response.status = -106;
                                    URLRequest.this.response.statusMsg = "no header error";
                                    URLRequest.this.response2cpp(5);
                                    return;
                                }
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
                                    if (key == null) {
                                        URLRequest.this.response.version = str.split("\\ ")[0];
                                    } else {
                                        URLRequest.this.response.headers.put(key, str);
                                    }
                                }
                                try {
                                    URLRequest.this.response.httpCode = URLRequest.this.urlConn.getResponseCode();
                                } catch (Exception e19) {
                                    e19.printStackTrace();
                                    ApolloVoiceLog.LogE("getResponseCode exception.");
                                }
                                try {
                                    URLRequest.this.response.httpCodeMsg = URLRequest.this.urlConn.getResponseMessage();
                                } catch (Exception e20) {
                                    e20.printStackTrace();
                                    ApolloVoiceLog.LogE("getResponseMessage exception.");
                                }
                                ApolloVoiceLog.LogI("getResponse code=" + URLRequest.this.response.httpCode + ",msg=" + URLRequest.this.response.httpCodeMsg);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                byte[] bArr2 = new byte[1500];
                                try {
                                    try {
                                        try {
                                            try {
                                                bufferedInputStream = new BufferedInputStream(URLRequest.this.urlConn.getInputStream());
                                            } catch (Throwable th2) {
                                                th = th2;
                                                r6 = 0;
                                                try {
                                                    byteArrayOutputStream.close();
                                                } catch (Exception e21) {
                                                    e21.printStackTrace();
                                                }
                                                if (0 != 0) {
                                                    try {
                                                        r2.close();
                                                    } catch (Exception e22) {
                                                        e22.printStackTrace();
                                                    }
                                                }
                                                if (r6 != 0) {
                                                    try {
                                                        r6.close();
                                                    } catch (Exception e23) {
                                                        e23.printStackTrace();
                                                    }
                                                }
                                                try {
                                                    if (URLRequest.this.urlConn == null) {
                                                        throw th;
                                                    }
                                                    URLRequest.this.urlConn.disconnect();
                                                    throw th;
                                                } catch (Exception e24) {
                                                    e24.printStackTrace();
                                                    throw th;
                                                }
                                            }
                                        } catch (Exception e25) {
                                            e = e25;
                                            bufferedInputStream = null;
                                        }
                                        try {
                                            if (this.filepath != "" && URLRequest.this.method == "GET") {
                                                try {
                                                    File file = new File(this.filepath);
                                                    file.createNewFile();
                                                    fileOutputStream = new FileOutputStream(file);
                                                } catch (FileNotFoundException e26) {
                                                    ApolloVoiceLog.LogE("Write File,Create File Error");
                                                    e26.printStackTrace();
                                                    URLRequest.this.response.status = -110;
                                                    URLRequest.this.response.statusMsg = "access file cannot find file exception";
                                                    URLRequest.this.response2cpp(8);
                                                    try {
                                                        byteArrayOutputStream.close();
                                                    } catch (Exception e27) {
                                                        e27.printStackTrace();
                                                    }
                                                    try {
                                                        bufferedInputStream.close();
                                                    } catch (Exception e28) {
                                                        e28.printStackTrace();
                                                    }
                                                    try {
                                                        if (URLRequest.this.urlConn != null) {
                                                            URLRequest.this.urlConn.disconnect();
                                                            return;
                                                        }
                                                        return;
                                                    } catch (Exception e29) {
                                                        e29.printStackTrace();
                                                        return;
                                                    }
                                                } catch (SecurityException e30) {
                                                    ApolloVoiceLog.LogE("Write File,access file security exception");
                                                    e30.printStackTrace();
                                                    URLRequest.this.response.status = -111;
                                                    URLRequest.this.response.statusMsg = "access file security exception";
                                                    URLRequest.this.response2cpp(8);
                                                    try {
                                                        byteArrayOutputStream.close();
                                                    } catch (Exception e31) {
                                                        e31.printStackTrace();
                                                    }
                                                    try {
                                                        bufferedInputStream.close();
                                                    } catch (Exception e32) {
                                                        e32.printStackTrace();
                                                    }
                                                    try {
                                                        if (URLRequest.this.urlConn != null) {
                                                            URLRequest.this.urlConn.disconnect();
                                                            return;
                                                        }
                                                        return;
                                                    } catch (Exception e33) {
                                                        e33.printStackTrace();
                                                        return;
                                                    }
                                                } catch (Exception e34) {
                                                    ApolloVoiceLog.LogE("Write File,access file other exception");
                                                    e34.printStackTrace();
                                                    URLRequest.this.response.status = -112;
                                                    URLRequest.this.response.statusMsg = "access file other exception";
                                                    URLRequest.this.response2cpp(8);
                                                    try {
                                                        byteArrayOutputStream.close();
                                                    } catch (Exception e35) {
                                                        e35.printStackTrace();
                                                    }
                                                    try {
                                                        bufferedInputStream.close();
                                                    } catch (Exception e36) {
                                                        e36.printStackTrace();
                                                    }
                                                    try {
                                                        if (URLRequest.this.urlConn != null) {
                                                            URLRequest.this.urlConn.disconnect();
                                                            return;
                                                        }
                                                        return;
                                                    } catch (Exception e37) {
                                                        e37.printStackTrace();
                                                        return;
                                                    }
                                                }
                                            }
                                            while (true) {
                                                int i2 = bufferedInputStream.read(bArr2);
                                                if (i2 == -1) {
                                                    break;
                                                }
                                                if (fileOutputStream == null) {
                                                    byteArrayOutputStream.write(bArr2, 0, i2);
                                                    byteArrayOutputStream.flush();
                                                } else if (fileOutputStream != null) {
                                                    fileOutputStream.write(bArr2, 0, i2);
                                                    fileOutputStream.flush();
                                                }
                                            }
                                            URLRequest.this.response.body = byteArrayOutputStream.toByteArray();
                                            ApolloVoiceLog.LogE("Java body size is " + URLRequest.this.response.body.length);
                                            try {
                                                byteArrayOutputStream.close();
                                            } catch (Exception e38) {
                                                e38.printStackTrace();
                                            }
                                            if (fileOutputStream != null) {
                                                try {
                                                    fileOutputStream.close();
                                                } catch (Exception e39) {
                                                    e39.printStackTrace();
                                                }
                                            }
                                            try {
                                                bufferedInputStream.close();
                                            } catch (Exception e40) {
                                                e40.printStackTrace();
                                            }
                                            try {
                                                if (URLRequest.this.urlConn != null) {
                                                    URLRequest.this.urlConn.disconnect();
                                                }
                                            } catch (Exception e41) {
                                                e41.printStackTrace();
                                            }
                                            URLRequest.this.response.status = 0;
                                            URLRequest.this.response.statusMsg = "http ok";
                                            URLRequest.this.response2cpp(0);
                                        } catch (Exception e42) {
                                            e = e42;
                                            e.printStackTrace();
                                            URLRequest.this.response.status = -113;
                                            URLRequest.this.response.statusMsg = "read http data and write data to file exception";
                                            URLRequest.this.response2cpp(6);
                                            try {
                                                byteArrayOutputStream.close();
                                            } catch (Exception e43) {
                                                e43.printStackTrace();
                                            }
                                            if (0 != 0) {
                                                try {
                                                    r2.close();
                                                } catch (Exception e44) {
                                                    e44.printStackTrace();
                                                }
                                            }
                                            if (bufferedInputStream != null) {
                                                try {
                                                    bufferedInputStream.close();
                                                } catch (Exception e45) {
                                                    e45.printStackTrace();
                                                }
                                            }
                                            try {
                                                if (URLRequest.this.urlConn != null) {
                                                    URLRequest.this.urlConn.disconnect();
                                                }
                                            } catch (Exception e46) {
                                                e46.printStackTrace();
                                            }
                                        }
                                    } catch (FileNotFoundException e47) {
                                        if (URLRequest.this.response.httpCode == 404) {
                                            URLRequest.this.response.status = -107;
                                            URLRequest.this.response.statusMsg = "getInputStream 404.";
                                            URLRequest.this.response2cpp(3);
                                            try {
                                                byteArrayOutputStream.close();
                                            } catch (Exception e48) {
                                                e48.printStackTrace();
                                            }
                                            try {
                                                if (URLRequest.this.urlConn != null) {
                                                    URLRequest.this.urlConn.disconnect();
                                                    return;
                                                }
                                                return;
                                            } catch (Exception e49) {
                                                e49.printStackTrace();
                                                return;
                                            }
                                        }
                                        URLRequest.this.response.status = -108;
                                        URLRequest.this.response.statusMsg = "getInputStream file not found exception.";
                                        e47.printStackTrace();
                                        URLRequest.this.response2cpp(1);
                                        try {
                                            byteArrayOutputStream.close();
                                        } catch (Exception e50) {
                                            e50.printStackTrace();
                                        }
                                        try {
                                            if (URLRequest.this.urlConn != null) {
                                                URLRequest.this.urlConn.disconnect();
                                            }
                                        } catch (Exception e51) {
                                            e51.printStackTrace();
                                        }
                                    } catch (Exception e52) {
                                        URLRequest.this.response.status = -109;
                                        URLRequest.this.response.statusMsg = "getInputStream other exception.";
                                        e52.printStackTrace();
                                        URLRequest.this.response2cpp(1);
                                        try {
                                            byteArrayOutputStream.close();
                                        } catch (Exception e53) {
                                            e53.printStackTrace();
                                        }
                                        try {
                                            if (URLRequest.this.urlConn != null) {
                                                URLRequest.this.urlConn.disconnect();
                                            }
                                        } catch (Exception e54) {
                                            e54.printStackTrace();
                                        }
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            } catch (SocketTimeoutException e55) {
                                e = e55;
                                bufferedOutputStream3 = bufferedOutputStream;
                                ApolloVoiceLog.LogE("Post File,timeout exception");
                                e.printStackTrace();
                                URLRequest.this.response.status = -104;
                                URLRequest.this.response.statusMsg = "timeout exception";
                                URLRequest.this.response2cpp(2);
                                if (bufferedOutputStream3 != null) {
                                }
                                if (fileInputStream == null) {
                                }
                            } catch (UnknownHostException e56) {
                                e = e56;
                                bufferedOutputStream4 = bufferedOutputStream;
                                ApolloVoiceLog.LogE("Post File,unknown host exception");
                                e.printStackTrace();
                                URLRequest.this.response.status = -103;
                                URLRequest.this.response.statusMsg = "unknown host exception";
                                URLRequest.this.response2cpp(3);
                                if (bufferedOutputStream4 != null) {
                                }
                                if (fileInputStream == null) {
                                }
                            } catch (Exception e57) {
                                e = e57;
                                bufferedOutputStream2 = bufferedOutputStream;
                                ApolloVoiceLog.LogE("Post File,other exception");
                                e.printStackTrace();
                                URLRequest.this.response.status = -105;
                                URLRequest.this.response.statusMsg = "other exception";
                                URLRequest.this.response2cpp(1);
                                if (bufferedOutputStream2 != null) {
                                }
                                if (fileInputStream == null) {
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                r2 = bufferedOutputStream;
                                if (r2 != 0) {
                                }
                                if (fileInputStream != null) {
                                }
                            }
                        } catch (SocketTimeoutException e58) {
                            e = e58;
                        } catch (UnknownHostException e59) {
                            e = e59;
                        } catch (Exception e60) {
                            e = e60;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (Exception e61) {
                    e = e61;
                    fileInputStream = null;
                }
            } catch (SocketTimeoutException e62) {
                e = e62;
                fileInputStream = null;
            } catch (UnknownHostException e63) {
                e = e63;
                fileInputStream = null;
            } catch (Throwable th6) {
                th = th6;
                fileInputStream = null;
            }
        }
    }

    public static native void response(int i, long j, int i2, String str, int i3, String str2, String str3, String str4, byte[] bArr, String[] strArr);

    public void addHead(String str, String str2) {
        this.urlConn.setRequestProperty(str, str2);
    }

    public void getFile(String str) {
        setMethod("GET");
        sendRequest(str);
    }

    public int initWithURL(String str, int i) {
        this.response = new URLResponse();
        this.response.URL = str;
        this.method = "GET";
        this.timeout = i;
        try {
            this.reqConnURL = new URL(this.response.URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
            if (HttpsUtils.connnectWithIP(str)) {
                if (this.urlConn instanceof HttpsURLConnection) {
                    try {
                        ((HttpsURLConnection) this.urlConn).setHostnameVerifier(HttpsUtils.getHostNameVerify(str));
                    } catch (IllegalArgumentException e2) {
                        ApolloVoiceLog.LogE("Maybe HttpsUtils's HostnameVerifier or SSLSocketFactory is null!");
                        e2.printStackTrace();
                    }
                } else {
                    ApolloVoiceLog.LogI("urlConn is not an instance of HttpsURLConnection.");
                }
            }
            try {
                this.urlConn.setRequestMethod(this.method);
                this.urlConn.setReadTimeout(i);
                if (this.method == "POST") {
                    this.urlConn.setDoOutput(true);
                    this.urlConn.setUseCaches(false);
                }
                this.urlConn.setConnectTimeout(i);
                return 0;
            } catch (ProtocolException e3) {
                e3.printStackTrace();
                return -1;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return -1;
        }
    }

    public void postFile(String str) {
        setMethod("POST");
        sendRequest(str);
    }

    public void response2cpp(int i) {
        int i2;
        long j;
        int i3;
        String str;
        int i4;
        String str2;
        String str3;
        String str4;
        byte[] bArr;
        String[] strArr;
        ApolloVoiceLog.LogI("url[" + this.response.URL + "]response2cpp with result :" + i);
        if (i != 0) {
            long j2 = this.delegate;
            int i5 = this.response.status;
            String str5 = this.response.statusMsg;
            int i6 = this.response.httpCode;
            i2 = i;
            j = j2;
            i3 = i5;
            str = str5;
            i4 = i6;
            str2 = this.response.httpCodeMsg;
            str3 = this.response.URL;
            str4 = "";
            bArr = null;
            strArr = null;
        } else {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry<String, String> entry : this.response.headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null && key != null) {
                    arrayList.add(key);
                    arrayList.add(value);
                }
            }
            long j3 = this.delegate;
            int i7 = this.response.status;
            String str6 = this.response.statusMsg;
            int i8 = this.response.httpCode;
            String str7 = this.response.httpCodeMsg;
            String str8 = this.response.URL;
            String str9 = this.response.version;
            i2 = i;
            j = j3;
            i3 = i7;
            str = str6;
            i4 = i8;
            str2 = str7;
            str3 = str8;
            str4 = str9;
            bArr = this.response.body;
            strArr = (String[]) arrayList.toArray(new String[0]);
        }
        response(i2, j, i3, str, i4, str2, str3, str4, bArr, strArr);
    }

    public void sendRequest() {
        sendRequest("");
    }

    public void sendRequest(String str) {
        new Thread(new RequestTask(str), "GVoiceRequest").start();
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
        }
    }
}
