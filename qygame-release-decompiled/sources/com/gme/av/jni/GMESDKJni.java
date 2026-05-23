package com.gme.av.jni;

import com.gme.TMG.ITMGContext;
import com.gme.TMG.ITMGType;
import com.gme.av.sdk.AVError;
import com.gme.av.utils.EventHelper;
import com.gme.av.utils.GMELibLoader;
import com.gme.liteav.base.annotations.JNINamespace;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::gme")
public class GMESDKJni {
    public static final String TAG = "GMESDKJni";
    private ITMGContext.ITMGDelegate mDelegate;
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mJniReadLock = this.mReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock mJniWriteLock = this.mReadWriteLock.writeLock();
    private long mNativeGMESDKJni = nativeCreateContext(this);
    private final GMESDKAudioJni mAudioJni = new GMESDKAudioJni(nativeCreateAudioCtrl(this.mNativeGMESDKJni));
    private final GMESDKAudioEffectJni mAudioEffectJni = new GMESDKAudioEffectJni(nativeCreateAudioEffectCtrl(this.mNativeGMESDKJni));
    private final GMESDKRoomJni mRoomJni = new GMESDKRoomJni(nativeCreateRoom(this.mNativeGMESDKJni));
    private final GMESDKPTTJni mPttJni = new GMESDKPTTJni(nativeCreatePTT(this.mNativeGMESDKJni));

    static {
        GMELibLoader.loadSdkLibrary();
    }

    private void destroyOther() {
        this.mAudioJni.destroy();
        this.mAudioEffectJni.destroy();
        this.mRoomJni.destroy();
    }

    public static byte[] genAuthBuffer(int i, String str, String str2, String str3) {
        return nativeGenAuthBuffer(i, str, str2, str3);
    }

    private static native int nativeCheckMicPermission(long j);

    private static native long nativeCreateAudioCtrl(long j);

    private static native long nativeCreateAudioEffectCtrl(long j);

    private static native long nativeCreateContext(GMESDKJni gMESDKJni);

    private static native long nativeCreatePTT(long j);

    private static native long nativeCreateRoom(long j);

    private static native int nativeDestroyContext(long j);

    private static native int nativeEnterRoom(long j, String str, int i, byte[] bArr);

    private static native int nativeExitRoom(long j);

    private static native byte[] nativeGenAuthBuffer(int i, String str, String str2, String str3);

    private static native String nativeGetAdvanceParam(long j, String str);

    private static native String nativeGetLogPath(long j);

    private static native String nativeGetSDKVersion(long j);

    private static native int nativeInit(long j, String str, String str2);

    private static native boolean nativeIsRoomEntered(long j);

    private static native int nativePause(long j);

    private static native int nativePoll(long j);

    private static native int nativeResume(long j);

    private static native int nativeSetAdvanceParams(long j, String str, String str2);

    private static native void nativeSetAppVersion(long j, String str);

    private static native int nativeSetAudioRole(long j, int i);

    private static native int nativeSetLogLevel(long j, int i, int i2);

    private static native int nativeSetLogPath(long j, String str);

    private static native int nativeSetRangeAudioMode(long j, int i);

    private static native int nativeSetRecvMixStreamCount(long j, int i);

    private static native void nativeSetRegion(long j, String str);

    private static native int nativeSetScene(long j, int i);

    private static native int nativeSetTeamID(long j, int i);

    private static native int nativeShowDebugView(long j, boolean z);

    private static native int nativeUnInit(long j);

    public int checkMicPermission() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeCheckMicPermission(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return ITMGType.ITMG_MIC_PERMISSION.ITMG_PERMISSION_NotDetermined.ordinal();
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int destroy() {
        destroyOther();
        this.mAudioJni.destroy();
        this.mJniWriteLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                nativeDestroyContext(this.mNativeGMESDKJni);
                this.mNativeGMESDKJni = 0L;
            }
            this.mJniWriteLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } catch (Throwable th) {
            this.mJniWriteLock.unlock();
            throw th;
        }
    }

    public int enterRoom(String str, int i, byte[] bArr) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeEnterRoom(this.mNativeGMESDKJni, str, i, bArr);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int exitRoom() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeExitRoom(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public String getAdvanceParam(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeGetAdvanceParam(this.mNativeGMESDKJni, str);
            }
            this.mJniReadLock.unlock();
            return null;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public GMESDKAudioEffectJni getAudioEffectJni() {
        return this.mAudioEffectJni;
    }

    public GMESDKAudioJni getAudioJni() {
        return this.mAudioJni;
    }

    public String getLogPath() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeGetLogPath(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return null;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public GMESDKPTTJni getPTTJni() {
        return this.mPttJni;
    }

    public GMESDKRoomJni getRoomJni() {
        return this.mRoomJni;
    }

    public String getSDKVersion() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeGetSDKVersion(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return null;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int init(String str, String str2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeInit(this.mNativeGMESDKJni, str, str2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isRoomEntered() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeIsRoomEntered(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public void onEvent(int i, String str) {
        if (this.mDelegate != null) {
            this.mDelegate.OnEvent(EventHelper.idToEvent(i), EventHelper.parserEvent(str));
        }
    }

    public int pause() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativePause(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int poll() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativePoll(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int resume() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeResume(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setAdvanceParams(String str, String str2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetAdvanceParams(this.mNativeGMESDKJni, str, str2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public void setAppVersion(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                nativeSetAppVersion(this.mNativeGMESDKJni, str);
            }
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setAudioRole(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetAudioRole(this.mNativeGMESDKJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public void setDelegate(ITMGContext.ITMGDelegate iTMGDelegate) {
        this.mDelegate = iTMGDelegate;
    }

    public int setLogLevel(int i, int i2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetLogLevel(this.mNativeGMESDKJni, i, i2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setLogPath(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetLogPath(this.mNativeGMESDKJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setRangeAudioMode(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetRangeAudioMode(this.mNativeGMESDKJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setRangeAudioTeamID(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetTeamID(this.mNativeGMESDKJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setRecvMixStreamCount(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetRecvMixStreamCount(this.mNativeGMESDKJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public void setRegion(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                nativeSetRegion(this.mNativeGMESDKJni, str);
            }
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setScene(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeSetScene(this.mNativeGMESDKJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int showDebugView(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeShowDebugView(this.mNativeGMESDKJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int unInit() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKJni != 0) {
                return nativeUnInit(this.mNativeGMESDKJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }
}
