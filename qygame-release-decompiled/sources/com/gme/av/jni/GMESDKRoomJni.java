package com.gme.av.jni;

import com.gme.av.sdk.AVError;
import com.gme.liteav.base.annotations.JNINamespace;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::gme")
public class GMESDKRoomJni {
    private long mNativeGMESDKRoomJni;
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mJniReadLock = this.mReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock mJniWriteLock = this.mReadWriteLock.writeLock();

    public GMESDKRoomJni(long j) {
        this.mNativeGMESDKRoomJni = j;
    }

    private static native int nativeChangeRoomType(long j, int i);

    private static native int nativeDestroy(long j);

    private static native String nativeGetRoomID(long j);

    private static native int nativeGetRoomType(long j);

    private static native int nativeSendCustomData(long j, byte[] bArr, int i);

    private static native int nativeSendSEIMsg(long j, String str, int i);

    private static native int nativeStartRoomSharing(long j, String str, String str2, byte[] bArr);

    private static native int nativeStopRoomSharing(long j);

    private static native int nativeStopSendCustomData(long j);

    private static native int nativeSwitchRoom(long j, String str, byte[] bArr);

    private static native int nativeUpdateAudioRecvRange(long j, float f);

    private static native int nativeUpdateOtherPosition(long j, String str, float[] fArr);

    private static native int nativeUpdateSelfPosition(long j, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4);

    private static native int nativeUpdateSpatializerRecvRange(long j, float f);

    public int changeRoomType(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeChangeRoomType(this.mNativeGMESDKRoomJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int destroy() {
        this.mJniWriteLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni == 0) {
                this.mJniWriteLock.unlock();
                return AVError.AV_ERR_CONTEXT_NOT_START;
            }
            int iNativeDestroy = nativeDestroy(this.mNativeGMESDKRoomJni);
            this.mNativeGMESDKRoomJni = 0L;
            return iNativeDestroy;
        } finally {
            this.mJniWriteLock.unlock();
        }
    }

    public String getRoomID() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeGetRoomID(this.mNativeGMESDKRoomJni);
            }
            this.mJniReadLock.unlock();
            return null;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getRoomType() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeGetRoomType(this.mNativeGMESDKRoomJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int sendCustomData(byte[] bArr, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeSendCustomData(this.mNativeGMESDKRoomJni, bArr, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int sendSEIMsg(String str, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeSendSEIMsg(this.mNativeGMESDKRoomJni, str, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRoomSharing(String str, String str2, byte[] bArr) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeStartRoomSharing(this.mNativeGMESDKRoomJni, str, str2, bArr);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopRoomSharing() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeStopRoomSharing(this.mNativeGMESDKRoomJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopSendCustomData() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeStopSendCustomData(this.mNativeGMESDKRoomJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int switchRoom(String str, byte[] bArr) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeSwitchRoom(this.mNativeGMESDKRoomJni, str, bArr);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int updateAudioRecvRange(float f) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeUpdateAudioRecvRange(this.mNativeGMESDKRoomJni, f);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int updateOtherPosition(String str, float[] fArr) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeUpdateOtherPosition(this.mNativeGMESDKRoomJni, str, fArr);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int updateSelfPosition(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeUpdateSelfPosition(this.mNativeGMESDKRoomJni, fArr, fArr2, fArr3, fArr4);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int updateSpatializerRecvRange(float f) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKRoomJni != 0) {
                return nativeUpdateSpatializerRecvRange(this.mNativeGMESDKRoomJni, f);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }
}
