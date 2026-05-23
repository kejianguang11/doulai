package org.cocos2dx.lib;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.cocos2dx.okhttp3.Call;
import org.cocos2dx.okhttp3.Callback;
import org.cocos2dx.okhttp3.OkHttpClient;
import org.cocos2dx.okhttp3.Request;
import org.cocos2dx.okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxDownloader {
    private static ConcurrentHashMap<String, Boolean> _resumingSupport = new ConcurrentHashMap<>();
    private int _countOfMaxProcessingTasks;
    private int _id;
    private String _tempFileNameSuffix;
    private OkHttpClient _httpClient = null;
    private ConcurrentHashMap<Integer, Call> _taskMap = new ConcurrentHashMap<>();
    private Queue<Runnable> _taskQueue = new LinkedList();
    private int _runningTaskCount = 0;

    /* JADX INFO: renamed from: org.cocos2dx.lib.Cocos2dxDownloader$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {
        String a = null;
        String b = null;
        File c = null;
        File d = null;
        long e = 0;
        final /* synthetic */ String f;
        final /* synthetic */ String g;
        final /* synthetic */ Cocos2dxDownloader h;
        final /* synthetic */ String[] i;
        final /* synthetic */ int j;

        AnonymousClass3(String str, String str2, Cocos2dxDownloader cocos2dxDownloader, String[] strArr, int i) {
            this.f = str;
            this.g = str2;
            this.h = cocos2dxDownloader;
            this.i = strArr;
            this.j = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:37:0x00d3 A[LOOP:0: B:35:0x00cc->B:37:0x00d3, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00eb  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            Call callNewCall;
            int i;
            if (this.f.length() > 0) {
                try {
                    this.a = new URI(this.g).getHost();
                    this.c = new File(this.f + this.h._tempFileNameSuffix);
                    if (!this.c.isDirectory()) {
                        File parentFile = this.c.getParentFile();
                        if (parentFile.isDirectory() || parentFile.mkdirs()) {
                            this.d = new File(this.f);
                            if (!this.d.isDirectory()) {
                                long length = this.c.length();
                                this.b = this.a.startsWith("www.") ? this.a.substring(4) : this.a;
                                if (length > 0) {
                                    if (Cocos2dxDownloader._resumingSupport.containsKey(this.b) && ((Boolean) Cocos2dxDownloader._resumingSupport.get(this.b)).booleanValue()) {
                                        this.e = length;
                                    } else {
                                        try {
                                            PrintWriter printWriter = new PrintWriter(this.c);
                                            printWriter.print("");
                                            printWriter.close();
                                        } catch (FileNotFoundException unused) {
                                        }
                                    }
                                }
                                Request.Builder builderUrl = new Request.Builder().url(this.g);
                                for (i = 0; i < this.i.length / 2; i++) {
                                    int i2 = i * 2;
                                    builderUrl.addHeader(this.i[i2], this.i[i2 + 1]);
                                }
                                if (this.e > 0) {
                                    builderUrl.addHeader("RANGE", "bytes=" + this.e + "-");
                                }
                                callNewCall = this.h._httpClient.newCall(builderUrl.build());
                                callNewCall.enqueue(new Callback() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.3.1
                                    @Override // org.cocos2dx.okhttp3.Callback
                                    public void onFailure(Call call, IOException iOException) {
                                        AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, iOException.toString(), null);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:42:0x0102 A[Catch: IOException -> 0x0122, all -> 0x020b, TryCatch #4 {IOException -> 0x0122, blocks: (B:30:0x00ae, B:31:0x00b6, B:33:0x00c5, B:42:0x0102, B:43:0x0116, B:36:0x00d0, B:38:0x00da, B:39:0x00f4), top: B:106:0x00ae }] */
                                    /* JADX WARN: Removed duplicated region for block: B:43:0x0116 A[Catch: IOException -> 0x0122, all -> 0x020b, TRY_LEAVE, TryCatch #4 {IOException -> 0x0122, blocks: (B:30:0x00ae, B:31:0x00b6, B:33:0x00c5, B:42:0x0102, B:43:0x0116, B:36:0x00d0, B:38:0x00da, B:39:0x00f4), top: B:106:0x00ae }] */
                                    @Override // org.cocos2dx.okhttp3.Callback
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public void onResponse(Call call, Response response) throws Throwable {
                                        Throwable th;
                                        InputStream inputStreamByteStream;
                                        FileOutputStream fileOutputStream;
                                        FileOutputStream fileOutputStream2;
                                        String str;
                                        ConcurrentHashMap concurrentHashMap;
                                        String str2;
                                        boolean z;
                                        byte[] bArr = new byte[4096];
                                        FileOutputStream fileOutputStream3 = null;
                                        try {
                                            try {
                                                if (response.code() >= 200 && response.code() <= 206) {
                                                    long jContentLength = response.body().contentLength();
                                                    if (AnonymousClass3.this.f.length() > 0 && !Cocos2dxDownloader._resumingSupport.containsKey(AnonymousClass3.this.b)) {
                                                        if (jContentLength > 0) {
                                                            concurrentHashMap = Cocos2dxDownloader._resumingSupport;
                                                            str2 = AnonymousClass3.this.b;
                                                            z = true;
                                                        } else {
                                                            concurrentHashMap = Cocos2dxDownloader._resumingSupport;
                                                            str2 = AnonymousClass3.this.b;
                                                            z = false;
                                                        }
                                                        concurrentHashMap.put(str2, z);
                                                    }
                                                    long j = AnonymousClass3.this.e;
                                                    inputStreamByteStream = response.body().byteStream();
                                                    try {
                                                        if (AnonymousClass3.this.f.length() > 0) {
                                                            FileOutputStream fileOutputStream4 = AnonymousClass3.this.e > 0 ? new FileOutputStream(AnonymousClass3.this.c, true) : new FileOutputStream(AnonymousClass3.this.c, false);
                                                            while (true) {
                                                                try {
                                                                    int i3 = inputStreamByteStream.read(bArr);
                                                                    if (i3 == -1) {
                                                                        break;
                                                                    }
                                                                    long j2 = i3;
                                                                    long j3 = j + j2;
                                                                    fileOutputStream4.write(bArr, 0, i3);
                                                                    fileOutputStream = fileOutputStream4;
                                                                    try {
                                                                        try {
                                                                            AnonymousClass3.this.h.onProgress(AnonymousClass3.this.j, j2, j3, jContentLength);
                                                                            j = j3;
                                                                            fileOutputStream4 = fileOutputStream;
                                                                        } catch (IOException e) {
                                                                            e = e;
                                                                            e.printStackTrace();
                                                                            AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, e.toString(), null);
                                                                            if (inputStreamByteStream != null) {
                                                                                inputStreamByteStream.close();
                                                                            }
                                                                            if (fileOutputStream != null) {
                                                                                fileOutputStream.close();
                                                                                return;
                                                                            }
                                                                            return;
                                                                        }
                                                                    } catch (Throwable th2) {
                                                                        th = th2;
                                                                        th = th;
                                                                        fileOutputStream3 = fileOutputStream;
                                                                        if (inputStreamByteStream != null) {
                                                                            try {
                                                                                inputStreamByteStream.close();
                                                                            } catch (IOException e2) {
                                                                                Log.e("Cocos2dxDownloader", e2.toString());
                                                                                throw th;
                                                                            }
                                                                        }
                                                                        if (fileOutputStream3 == null) {
                                                                            throw th;
                                                                        }
                                                                        fileOutputStream3.close();
                                                                        throw th;
                                                                    }
                                                                } catch (IOException e3) {
                                                                    e = e3;
                                                                    fileOutputStream = fileOutputStream4;
                                                                } catch (Throwable th3) {
                                                                    th = th3;
                                                                    fileOutputStream = fileOutputStream4;
                                                                }
                                                            }
                                                            fileOutputStream2 = fileOutputStream4;
                                                            fileOutputStream2.flush();
                                                            if (!AnonymousClass3.this.d.exists()) {
                                                                AnonymousClass3.this.c.renameTo(AnonymousClass3.this.d);
                                                                str = null;
                                                                if (str != null) {
                                                                    AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, null, null);
                                                                    AnonymousClass3.this.h.runNextTaskIfExists();
                                                                } else {
                                                                    AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, str, null);
                                                                }
                                                            } else if (AnonymousClass3.this.d.isDirectory()) {
                                                                str = null;
                                                                if (str != null) {
                                                                }
                                                            } else {
                                                                if (!AnonymousClass3.this.d.delete()) {
                                                                    str = "Can't remove old file:" + AnonymousClass3.this.d.getAbsolutePath();
                                                                    if (str != null) {
                                                                    }
                                                                }
                                                                AnonymousClass3.this.c.renameTo(AnonymousClass3.this.d);
                                                                str = null;
                                                                if (str != null) {
                                                                }
                                                            }
                                                        } else {
                                                            int i4 = -1;
                                                            ByteArrayOutputStream byteArrayOutputStream = jContentLength > 0 ? new ByteArrayOutputStream((int) jContentLength) : new ByteArrayOutputStream(4096);
                                                            while (true) {
                                                                int i5 = inputStreamByteStream.read(bArr);
                                                                if (i5 == i4) {
                                                                    break;
                                                                }
                                                                long j4 = i5;
                                                                long j5 = j + j4;
                                                                byteArrayOutputStream.write(bArr, 0, i5);
                                                                AnonymousClass3.this.h.onProgress(AnonymousClass3.this.j, j4, j5, jContentLength);
                                                                j = j5;
                                                                i4 = i4;
                                                            }
                                                            AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, null, byteArrayOutputStream.toByteArray());
                                                            AnonymousClass3.this.h.runNextTaskIfExists();
                                                            fileOutputStream2 = null;
                                                        }
                                                        if (inputStreamByteStream != null) {
                                                            inputStreamByteStream.close();
                                                        }
                                                        if (fileOutputStream2 != null) {
                                                            fileOutputStream2.close();
                                                            return;
                                                        }
                                                        return;
                                                    } catch (IOException e4) {
                                                        e = e4;
                                                        fileOutputStream = null;
                                                    } catch (Throwable th4) {
                                                        th = th4;
                                                    }
                                                }
                                                if (response.code() == 416) {
                                                    File file = new File(AnonymousClass3.this.f + AnonymousClass3.this.h._tempFileNameSuffix);
                                                    if (file.exists() && file.isFile()) {
                                                        file.delete();
                                                    }
                                                }
                                                AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, -2, response.message(), null);
                                            } catch (IOException e5) {
                                                Log.e("Cocos2dxDownloader", e5.toString());
                                            }
                                        } catch (IOException e6) {
                                            e = e6;
                                            inputStreamByteStream = null;
                                            fileOutputStream = null;
                                        } catch (Throwable th5) {
                                            th = th5;
                                            inputStreamByteStream = null;
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e2) {
                    e2.printStackTrace();
                }
                callNewCall = null;
            } else {
                Request.Builder builderUrl2 = new Request.Builder().url(this.g);
                while (i < this.i.length / 2) {
                }
                if (this.e > 0) {
                }
                callNewCall = this.h._httpClient.newCall(builderUrl2.build());
                callNewCall.enqueue(new Callback() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.3.1
                    @Override // org.cocos2dx.okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, iOException.toString(), null);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:42:0x0102 A[Catch: IOException -> 0x0122, all -> 0x020b, TryCatch #4 {IOException -> 0x0122, blocks: (B:30:0x00ae, B:31:0x00b6, B:33:0x00c5, B:42:0x0102, B:43:0x0116, B:36:0x00d0, B:38:0x00da, B:39:0x00f4), top: B:106:0x00ae }] */
                    /* JADX WARN: Removed duplicated region for block: B:43:0x0116 A[Catch: IOException -> 0x0122, all -> 0x020b, TRY_LEAVE, TryCatch #4 {IOException -> 0x0122, blocks: (B:30:0x00ae, B:31:0x00b6, B:33:0x00c5, B:42:0x0102, B:43:0x0116, B:36:0x00d0, B:38:0x00da, B:39:0x00f4), top: B:106:0x00ae }] */
                    @Override // org.cocos2dx.okhttp3.Callback
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public void onResponse(Call call, Response response) throws Throwable {
                        Throwable th;
                        InputStream inputStreamByteStream;
                        FileOutputStream fileOutputStream;
                        FileOutputStream fileOutputStream2;
                        String str;
                        ConcurrentHashMap concurrentHashMap;
                        String str2;
                        boolean z;
                        byte[] bArr = new byte[4096];
                        FileOutputStream fileOutputStream3 = null;
                        try {
                            try {
                                if (response.code() >= 200 && response.code() <= 206) {
                                    long jContentLength = response.body().contentLength();
                                    if (AnonymousClass3.this.f.length() > 0 && !Cocos2dxDownloader._resumingSupport.containsKey(AnonymousClass3.this.b)) {
                                        if (jContentLength > 0) {
                                            concurrentHashMap = Cocos2dxDownloader._resumingSupport;
                                            str2 = AnonymousClass3.this.b;
                                            z = true;
                                        } else {
                                            concurrentHashMap = Cocos2dxDownloader._resumingSupport;
                                            str2 = AnonymousClass3.this.b;
                                            z = false;
                                        }
                                        concurrentHashMap.put(str2, z);
                                    }
                                    long j = AnonymousClass3.this.e;
                                    inputStreamByteStream = response.body().byteStream();
                                    try {
                                        if (AnonymousClass3.this.f.length() > 0) {
                                            FileOutputStream fileOutputStream4 = AnonymousClass3.this.e > 0 ? new FileOutputStream(AnonymousClass3.this.c, true) : new FileOutputStream(AnonymousClass3.this.c, false);
                                            while (true) {
                                                try {
                                                    int i3 = inputStreamByteStream.read(bArr);
                                                    if (i3 == -1) {
                                                        break;
                                                    }
                                                    long j2 = i3;
                                                    long j3 = j + j2;
                                                    fileOutputStream4.write(bArr, 0, i3);
                                                    fileOutputStream = fileOutputStream4;
                                                    try {
                                                        try {
                                                            AnonymousClass3.this.h.onProgress(AnonymousClass3.this.j, j2, j3, jContentLength);
                                                            j = j3;
                                                            fileOutputStream4 = fileOutputStream;
                                                        } catch (IOException e3) {
                                                            e = e3;
                                                            e.printStackTrace();
                                                            AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, e.toString(), null);
                                                            if (inputStreamByteStream != null) {
                                                                inputStreamByteStream.close();
                                                            }
                                                            if (fileOutputStream != null) {
                                                                fileOutputStream.close();
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        th = th;
                                                        fileOutputStream3 = fileOutputStream;
                                                        if (inputStreamByteStream != null) {
                                                            try {
                                                                inputStreamByteStream.close();
                                                            } catch (IOException e22) {
                                                                Log.e("Cocos2dxDownloader", e22.toString());
                                                                throw th;
                                                            }
                                                        }
                                                        if (fileOutputStream3 == null) {
                                                            throw th;
                                                        }
                                                        fileOutputStream3.close();
                                                        throw th;
                                                    }
                                                } catch (IOException e32) {
                                                    e = e32;
                                                    fileOutputStream = fileOutputStream4;
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    fileOutputStream = fileOutputStream4;
                                                }
                                            }
                                            fileOutputStream2 = fileOutputStream4;
                                            fileOutputStream2.flush();
                                            if (!AnonymousClass3.this.d.exists()) {
                                                AnonymousClass3.this.c.renameTo(AnonymousClass3.this.d);
                                                str = null;
                                                if (str != null) {
                                                    AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, null, null);
                                                    AnonymousClass3.this.h.runNextTaskIfExists();
                                                } else {
                                                    AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, str, null);
                                                }
                                            } else if (AnonymousClass3.this.d.isDirectory()) {
                                                str = null;
                                                if (str != null) {
                                                }
                                            } else {
                                                if (!AnonymousClass3.this.d.delete()) {
                                                    str = "Can't remove old file:" + AnonymousClass3.this.d.getAbsolutePath();
                                                    if (str != null) {
                                                    }
                                                }
                                                AnonymousClass3.this.c.renameTo(AnonymousClass3.this.d);
                                                str = null;
                                                if (str != null) {
                                                }
                                            }
                                        } else {
                                            int i4 = -1;
                                            ByteArrayOutputStream byteArrayOutputStream = jContentLength > 0 ? new ByteArrayOutputStream((int) jContentLength) : new ByteArrayOutputStream(4096);
                                            while (true) {
                                                int i5 = inputStreamByteStream.read(bArr);
                                                if (i5 == i4) {
                                                    break;
                                                }
                                                long j4 = i5;
                                                long j5 = j + j4;
                                                byteArrayOutputStream.write(bArr, 0, i5);
                                                AnonymousClass3.this.h.onProgress(AnonymousClass3.this.j, j4, j5, jContentLength);
                                                j = j5;
                                                i4 = i4;
                                            }
                                            AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, 0, null, byteArrayOutputStream.toByteArray());
                                            AnonymousClass3.this.h.runNextTaskIfExists();
                                            fileOutputStream2 = null;
                                        }
                                        if (inputStreamByteStream != null) {
                                            inputStreamByteStream.close();
                                        }
                                        if (fileOutputStream2 != null) {
                                            fileOutputStream2.close();
                                            return;
                                        }
                                        return;
                                    } catch (IOException e4) {
                                        e = e4;
                                        fileOutputStream = null;
                                    } catch (Throwable th4) {
                                        th = th4;
                                    }
                                }
                                if (response.code() == 416) {
                                    File file = new File(AnonymousClass3.this.f + AnonymousClass3.this.h._tempFileNameSuffix);
                                    if (file.exists() && file.isFile()) {
                                        file.delete();
                                    }
                                }
                                AnonymousClass3.this.h.onFinish(AnonymousClass3.this.j, -2, response.message(), null);
                            } catch (IOException e5) {
                                Log.e("Cocos2dxDownloader", e5.toString());
                            }
                        } catch (IOException e6) {
                            e = e6;
                            inputStreamByteStream = null;
                            fileOutputStream = null;
                        } catch (Throwable th5) {
                            th = th5;
                            inputStreamByteStream = null;
                        }
                    }
                });
            }
            if (callNewCall != null) {
                this.h._taskMap.put(Integer.valueOf(this.j), callNewCall);
                return;
            }
            final String str = "Can't create DownloadTask for " + this.g;
            Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.3.2
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass3.this.h.nativeOnFinish(AnonymousClass3.this.h._id, AnonymousClass3.this.j, 0, str, null);
                }
            });
        }
    }

    public static void abort(Cocos2dxDownloader cocos2dxDownloader, final int i) {
        Cocos2dxHelper.getActivity().runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.4
            @Override // java.lang.Runnable
            public void run() {
                for (Map.Entry entry : Cocos2dxDownloader.this._taskMap.entrySet()) {
                    Object key = entry.getKey();
                    Call call = (Call) entry.getValue();
                    if (call != null && Integer.parseInt(key.toString()) == i) {
                        call.cancel();
                        Cocos2dxDownloader.this._taskMap.remove(Integer.valueOf(i));
                        Cocos2dxDownloader.this.runNextTaskIfExists();
                        return;
                    }
                }
            }
        });
    }

    public static void cancelAllRequests(Cocos2dxDownloader cocos2dxDownloader) {
        Cocos2dxHelper.getActivity().runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.5
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = Cocos2dxDownloader.this._taskMap.entrySet().iterator();
                while (it.hasNext()) {
                    Call call = (Call) ((Map.Entry) it.next()).getValue();
                    if (call != null) {
                        call.cancel();
                    }
                }
            }
        });
    }

    public static Cocos2dxDownloader createDownloader(int i, int i2, String str, int i3) {
        Cocos2dxDownloader cocos2dxDownloader = new Cocos2dxDownloader();
        cocos2dxDownloader._id = i;
        cocos2dxDownloader._httpClient = (i2 > 0 ? new OkHttpClient().newBuilder().followRedirects(true).followSslRedirects(true).callTimeout(i2, TimeUnit.SECONDS) : new OkHttpClient().newBuilder().followRedirects(true).followSslRedirects(true)).build();
        cocos2dxDownloader._tempFileNameSuffix = str;
        cocos2dxDownloader._countOfMaxProcessingTasks = i3;
        return cocos2dxDownloader;
    }

    public static void createTask(Cocos2dxDownloader cocos2dxDownloader, int i, String str, String str2, String[] strArr) {
        cocos2dxDownloader.enqueueTask(new AnonymousClass3(str2, str, cocos2dxDownloader, strArr, i));
    }

    private void enqueueTask(Runnable runnable) {
        synchronized (this._taskQueue) {
            if (this._runningTaskCount < this._countOfMaxProcessingTasks) {
                Cocos2dxHelper.getActivity().runOnUiThread(runnable);
                this._runningTaskCount++;
            } else {
                this._taskQueue.add(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinish(final int i, final int i2, final String str, final byte[] bArr) {
        if (this._taskMap.get(Integer.valueOf(i)) == null) {
            return;
        }
        this._taskMap.remove(Integer.valueOf(i));
        this._runningTaskCount--;
        Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.2
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxDownloader.this.nativeOnFinish(Cocos2dxDownloader.this._id, i, i2, str, bArr);
            }
        });
        runNextTaskIfExists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProgress(final int i, final long j, final long j2, final long j3) {
        Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxDownloader.this.nativeOnProgress(Cocos2dxDownloader.this._id, i, j, j2, j3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runNextTaskIfExists() {
        synchronized (this._taskQueue) {
            while (this._runningTaskCount < this._countOfMaxProcessingTasks && this._taskQueue.size() > 0) {
                Cocos2dxHelper.getActivity().runOnUiThread(this._taskQueue.poll());
                this._runningTaskCount++;
            }
        }
    }

    native void nativeOnFinish(int i, int i2, int i3, String str, byte[] bArr);

    native void nativeOnProgress(int i, int i2, long j, long j2, long j3);
}
