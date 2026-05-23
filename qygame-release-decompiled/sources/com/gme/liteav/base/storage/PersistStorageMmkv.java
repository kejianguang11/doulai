package com.gme.liteav.base.storage;

import com.gme.liteav.base.annotations.JNINamespace;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class PersistStorageMmkv {
    private long mNativePersistStorageMmkv;

    public PersistStorageMmkv(String str) {
        this.mNativePersistStorageMmkv = 0L;
        this.mNativePersistStorageMmkv = nativeCreatePersistStorageMmkv(str);
    }

    private static native void nativeClear(long j, String str);

    private static native void nativeCommit(long j);

    private static native long nativeCreatePersistStorageMmkv(String str);

    private static native void nativeDestroyPersistStorageMmkv(long j);

    private static native String[] nativeGetAllKeys(long j);

    private static native Integer nativeGetInt32(long j, String str);

    private static native Long nativeGetLong(long j, String str);

    private static native String nativeGetString(long j, String str);

    private static native void nativePutInt32(long j, String str, int i);

    private static native void nativePutLong(long j, String str, long j2);

    private static native void nativePutString(long j, String str, String str2);

    public void clear(String str) {
        if (this.mNativePersistStorageMmkv == 0) {
            return;
        }
        try {
            nativeClear(this.mNativePersistStorageMmkv, str);
        } catch (Throwable unused) {
        }
    }

    public void commit() {
        if (this.mNativePersistStorageMmkv == 0) {
            return;
        }
        try {
            nativeCommit(this.mNativePersistStorageMmkv);
        } catch (Throwable unused) {
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.mNativePersistStorageMmkv != 0) {
            nativeDestroyPersistStorageMmkv(this.mNativePersistStorageMmkv);
        }
    }

    public String[] getAllKeys() {
        if (this.mNativePersistStorageMmkv == 0) {
            return new String[0];
        }
        try {
            String[] strArrNativeGetAllKeys = nativeGetAllKeys(this.mNativePersistStorageMmkv);
            return strArrNativeGetAllKeys == null ? new String[0] : strArrNativeGetAllKeys;
        } catch (Throwable unused) {
            return new String[0];
        }
    }

    public Integer getInt(String str) {
        if (this.mNativePersistStorageMmkv == 0) {
            return null;
        }
        try {
            return nativeGetInt32(this.mNativePersistStorageMmkv, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public Long getLong(String str) {
        if (this.mNativePersistStorageMmkv == 0) {
            return null;
        }
        try {
            return nativeGetLong(this.mNativePersistStorageMmkv, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public String getString(String str) {
        if (this.mNativePersistStorageMmkv == 0) {
            return null;
        }
        try {
            return nativeGetString(this.mNativePersistStorageMmkv, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public void put(String str, int i) {
        if (this.mNativePersistStorageMmkv == 0) {
            return;
        }
        try {
            nativePutInt32(this.mNativePersistStorageMmkv, str, i);
        } catch (Throwable unused) {
        }
    }

    public void put(String str, long j) {
        if (this.mNativePersistStorageMmkv == 0) {
            return;
        }
        try {
            nativePutLong(this.mNativePersistStorageMmkv, str, j);
        } catch (Throwable unused) {
        }
    }

    public void put(String str, String str2) {
        if (this.mNativePersistStorageMmkv == 0) {
            return;
        }
        try {
            nativePutString(this.mNativePersistStorageMmkv, str, str2);
        } catch (Throwable unused) {
        }
    }
}
