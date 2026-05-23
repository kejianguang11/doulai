package com.gme.liteav.base;

import android.content.Context;
import android.os.Build;
import android.system.Os;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public abstract class PathUtils {
    private static final int APP_FILES_DIRECTORY = 5;
    private static final int CACHE_DIRECTORY = 2;
    private static final int DATA_DIRECTORY = 0;
    private static final int EXTERNAL_FILES_DIRECTORY = 4;
    private static final int LOG_DIRECTORY = 3;
    private static final int NUM_DIRECTORIES = 6;
    private static final String TAG = "PathUtils";
    private static final int THUMBNAIL_DIRECTORY = 1;
    private static final String THUMBNAIL_DIRECTORY_NAME = "textures";
    private static String sCacheSubDirectory;
    private static String sDataDirectorySuffix;
    private static FutureTask<String[]> sDirPathFetchTask;
    static final /* synthetic */ boolean a = !PathUtils.class.desiredAssertionStatus();
    private static final AtomicBoolean sInitializationStarted = new AtomicBoolean();

    static class a {
        private static final String[] a = PathUtils.getOrComputeDirectoryPaths();
    }

    private PathUtils() {
    }

    private static void chmod(String str, int i) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        try {
            Os.chmod(str, i);
        } catch (Exception unused) {
            Log.e(TAG, "Failed to set permissions for path \"" + str + "\"", new Object[0]);
        }
    }

    public static String getAppFilesDirectory() {
        return getDirectoryPath(5);
    }

    public static String getCacheDirectory() {
        if (a || sDirPathFetchTask != null) {
            return getDirectoryPath(2);
        }
        throw new AssertionError("setDataDirectorySuffix must be called first.");
    }

    public static String getDataDirectory() {
        if (a || sDirPathFetchTask != null) {
            return getDirectoryPath(0);
        }
        throw new AssertionError("setDataDirectorySuffix must be called first.");
    }

    private static String getDirectoryPath(int i) {
        try {
            return a.a[i];
        } catch (Throwable th) {
            Log.e(TAG, "Failed to get directory path:".concat(String.valueOf(i)), th);
            return null;
        }
    }

    public static String getExternalFilesDirectory() {
        return getDirectoryPath(4);
    }

    public static String getLogDirectory() {
        if (a || sDirPathFetchTask != null) {
            return getDirectoryPath(3);
        }
        throw new AssertionError("setDataDirectorySuffix must be called first.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0025 A[Catch: InterruptedException | ExecutionException -> 0x0032, InterruptedException | ExecutionException -> 0x0032, TRY_ENTER, TryCatch #0 {InterruptedException | ExecutionException -> 0x0032, blocks: (B:3:0x0001, B:5:0x000a, B:7:0x0012, B:7:0x0012, B:16:0x0021, B:16:0x0021, B:18:0x0025, B:18:0x0025, B:19:0x0028, B:19:0x0028, B:20:0x0029, B:20:0x0029), top: B:24:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0021 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] getOrComputeDirectoryPaths() {
        Throwable th;
        Throwable th2;
        try {
            if (!sDirPathFetchTask.cancel(false)) {
                return sDirPathFetchTask.get();
            }
            b bVarA = b.a();
            try {
                String[] privateDataDirectorySuffixInternal = setPrivateDataDirectorySuffixInternal();
                bVarA.close();
                return privateDataDirectorySuffixInternal;
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    th = th3;
                    th2 = th4;
                    if (th != null) {
                        bVarA.close();
                        throw th2;
                    }
                    try {
                        bVarA.close();
                        throw th2;
                    } catch (Throwable unused) {
                        throw th2;
                    }
                }
            }
        } catch (InterruptedException | ExecutionException unused2) {
            return null;
        }
    }

    public static String getThumbnailCacheDirectory() {
        if (a || sDirPathFetchTask != null) {
            return getDirectoryPath(1);
        }
        throw new AssertionError("setDataDirectorySuffix must be called first.");
    }

    public static void setPrivateDataDirectorySuffix(String str) {
        setPrivateDataDirectorySuffix(str, null);
    }

    public static synchronized void setPrivateDataDirectorySuffix(String str, String str2) {
        if (!sInitializationStarted.getAndSet(true)) {
            if (!a && ContextUtils.getApplicationContext() == null) {
                throw new AssertionError();
            }
            sDataDirectorySuffix = str;
            sCacheSubDirectory = str2;
            sDirPathFetchTask = new FutureTask<>(com.gme.liteav.base.a.a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] setPrivateDataDirectorySuffixInternal() {
        String[] strArr = new String[6];
        Context applicationContext = ContextUtils.getApplicationContext();
        strArr[0] = applicationContext.getDir(sDataDirectorySuffix, 0).getPath();
        chmod(strArr[0], 448);
        strArr[1] = applicationContext.getDir(THUMBNAIL_DIRECTORY_NAME, 0).getPath();
        if (applicationContext.getCacheDir() != null) {
            if (sCacheSubDirectory == null) {
                strArr[2] = applicationContext.getCacheDir().getPath();
            } else {
                strArr[2] = new File(applicationContext.getCacheDir(), sCacheSubDirectory).getPath();
            }
        }
        strArr[5] = applicationContext.getFilesDir().getAbsolutePath();
        File externalFilesDir = applicationContext.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            strArr[3] = externalFilesDir.getAbsolutePath() + "/log/liteav";
            strArr[4] = externalFilesDir.getAbsolutePath();
        }
        return strArr;
    }
}
