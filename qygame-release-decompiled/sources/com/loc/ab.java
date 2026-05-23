package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* JADX INFO: loaded from: classes.dex */
public final class ab {
    public static final String a = x.c("SYmFja3Vwcw");
    public static final String b = x.c("SLmFkaXU");
    public static final String c = x.c("JIw");

    private static String a(Context context) {
        File externalFilesDir;
        if (Build.VERSION.SDK_INT >= 31 || (context.getApplicationInfo().targetSdkVersion >= 30 && Build.VERSION.SDK_INT >= 30)) {
            externalFilesDir = context.getApplicationContext().getExternalFilesDir("");
        } else {
            StorageManager storageManager = Build.VERSION.SDK_INT >= 9 ? (StorageManager) context.getSystemService("storage") : null;
            try {
                Class<?> cls = Class.forName(x.c("SYW5kcm9pZC5vcy5zdG9yYWdlLlN0b3JhZ2VWb2x1bWU"));
                Method method = storageManager.getClass().getMethod(x.c("FZ2V0Vm9sdW1lTGlzdA"), new Class[0]);
                Method method2 = cls.getMethod(x.c("ZZ2V0UGF0aA"), new Class[0]);
                Method method3 = cls.getMethod(x.c("AaXNSZW1vdmFibGUK"), new Class[0]);
                Object objInvoke = method.invoke(storageManager, new Object[0]);
                int length = Array.getLength(objInvoke);
                for (int i = 0; i < length; i++) {
                    Object obj = Array.get(objInvoke, i);
                    String str = (String) method2.invoke(obj, new Object[0]);
                    if (!((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                        return str;
                    }
                }
                return null;
            } catch (Throwable unused) {
                externalFilesDir = Environment.getExternalStorageDirectory();
            }
        }
        return externalFilesDir.getAbsolutePath();
    }

    public static synchronized void a(Context context, String str, String str2) {
        FileChannel fileChannel;
        RandomAccessFile randomAccessFile;
        FileChannel channel;
        if (Build.VERSION.SDK_INT >= 19 && (context == null || context.checkCallingOrSelfPermission(x.c("EYW5kcm9pZC5wZXJtaXNzaW9uLldSSVRFX0VYVEVSTkFMX1NUT1JBR0U=")) != 0 || context.checkCallingOrSelfPermission(x.c("EYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfRVhURVJOQUxfU1RPUkFHRQ==")) != 0)) {
            return;
        }
        String strA = a(context);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        String str3 = str + c + str2;
        File file = new File(strA + File.separator + a);
        File file2 = new File(file, b);
        FileLock fileLock = null;
        try {
            if (!file.exists() || file.isDirectory()) {
                file.mkdirs();
            }
            file2.createNewFile();
            randomAccessFile = new RandomAccessFile(file2, "rws");
            try {
                channel = randomAccessFile.getChannel();
                try {
                    FileLock fileLockTryLock = channel.tryLock();
                    if (fileLockTryLock != null) {
                        try {
                            channel.write(ByteBuffer.wrap(str3.getBytes(com.alipay.sdk.sys.a.m)));
                        } catch (Throwable th) {
                            th = th;
                            fileLock = fileLockTryLock;
                            Throwable th2 = th;
                            fileChannel = channel;
                            th = th2;
                            if (fileLock != null) {
                                try {
                                    fileLock.release();
                                } catch (IOException unused) {
                                }
                            }
                            if (fileChannel != null) {
                                try {
                                    fileChannel.close();
                                } catch (IOException unused2) {
                                }
                            }
                            if (randomAccessFile == null) {
                                throw th;
                            }
                            try {
                                randomAccessFile.close();
                                throw th;
                            } catch (Throwable unused3) {
                                throw th;
                            }
                        }
                    }
                    if (fileLockTryLock != null) {
                        try {
                            fileLockTryLock.release();
                        } catch (IOException unused4) {
                        }
                    }
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException unused5) {
                        }
                    }
                    try {
                        randomAccessFile.close();
                    } catch (Throwable unused6) {
                    }
                } catch (Throwable unused7) {
                }
            } catch (Throwable unused8) {
                channel = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
            randomAccessFile = null;
        }
    }
}
