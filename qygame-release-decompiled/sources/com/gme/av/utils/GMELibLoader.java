package com.gme.av.utils;

import android.text.TextUtils;
import android.util.Log;
import com.gme.av.BuildConfig;

/* JADX INFO: loaded from: classes.dex */
public final class GMELibLoader {
    public static final String TAG = "GMELibLoader";
    private static final Object mLoadLock = new Object();
    private static boolean mHasLoaded = false;
    private static String mLibraryPath = "";

    public static String getLibraryPath() {
        return mLibraryPath;
    }

    public static boolean isLoadLibrary() {
        return mHasLoaded;
    }

    private static boolean loadLibrary(String str) {
        try {
            if (!TextUtils.isEmpty(mLibraryPath) ? loadLibrary(mLibraryPath, str) : false) {
                return true;
            }
            Log.w(TAG, "load library " + str + " from system path ");
            System.loadLibrary(str);
            return true;
        } catch (Error | Exception e) {
            Log.w(TAG, "load library : " + e.toString());
            return false;
        }
    }

    public static boolean loadLibrary(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Log.w(TAG, "load library " + str2 + " from path " + str);
            System.load(str + "/lib" + str2 + ".so");
            return true;
        } catch (Error | Exception e) {
            Log.w(TAG, "load library : " + e.toString());
            return false;
        }
    }

    public static int loadSdkLibrary() {
        synchronized (mLoadLock) {
            if (!mHasLoaded) {
                Log.w(TAG, "load library txsoundtouch ".concat(String.valueOf(loadLibrary("txsoundtouch"))));
                Log.w(TAG, "load library txffmpeg ".concat(String.valueOf(loadLibrary("txffmpeg"))));
                boolean zLoadLibrary = loadLibrary(BuildConfig.NativeSoName);
                Log.w(TAG, "load library gmesdk ".concat(String.valueOf(zLoadLibrary)));
                mHasLoaded = zLoadLibrary;
            }
        }
        return 0;
    }

    public static void setLibraryPath(String str) {
        Log.w(TAG, "setLibraryPath ".concat(String.valueOf(str)));
        mLibraryPath = str;
    }
}
