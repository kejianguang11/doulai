package com.gme.av.jni;

import com.gme.TMG.ITMGAudioCtrl;
import com.gme.TMG.ITMGType;
import com.gme.av.sdk.AVError;
import com.gme.liteav.base.annotations.JNINamespace;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::gme")
public class GMESDKAudioJni {
    private long mNativeGMESDKAudioJni;
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mJniReadLock = this.mReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock mJniWriteLock = this.mReadWriteLock.writeLock();
    private ITMGAudioCtrl.ITMGAudioFrameCallback mAudioFrameCallback = null;

    static class AudioFrame {
        private ITMGType.TMGAudioFrame a;

        public AudioFrame(ITMGType.TMGAudioFrame tMGAudioFrame) {
            this.a = tMGAudioFrame;
        }

        public int getChannel() {
            return this.a.channel;
        }

        public byte[] getData() {
            return this.a.data;
        }

        public int getSampleRate() {
            return this.a.sample_rate;
        }

        public long getTimestamp() {
            return this.a.timestamp;
        }
    }

    public GMESDKAudioJni(long j) {
        this.mNativeGMESDKAudioJni = j;
    }

    private static native int nativeAddAudioBlackList(long j, String str);

    private static native int nativeDestroy(long j);

    private static native int nativeEnableAudioCaptureDevice(long j, boolean z);

    private static native int nativeEnableAudioPlayDevice(long j, boolean z);

    private static native int nativeEnableAudioRecv(long j, boolean z);

    private static native int nativeEnableAudioSend(long j, boolean z);

    private static native int nativeEnableCustomAudioCapture(long j, boolean z);

    private static native int nativeEnableCustomAudioRendering(long j, boolean z);

    private static native int nativeEnableLoopBack(long j, boolean z);

    private static native int nativeEnableSpatializer(long j, boolean z);

    private static native int nativeGetCustomAudioRenderingFrame(long j, AudioFrame audioFrame);

    private static native int nativeGetMicLevel(long j);

    private static native int nativeGetMicVolume(long j);

    private static native int nativeGetRecvStreamLevel(long j, String str);

    private static native int nativeGetSendStreamLevel(long j);

    private static native int nativeGetSpeakerLevel(long j);

    private static native int nativeGetSpeakerVolume(long j);

    private static native int nativeGetSpeakerVolumeByUserID(long j, String str);

    private static native boolean nativeIsAudioCaptureDeviceEnabled(long j);

    private static native boolean nativeIsAudioPlayDeviceEnabled(long j);

    private static native boolean nativeIsAudioRecvEnabled(long j);

    private static native boolean nativeIsAudioSendEnabled(long j);

    private static native boolean nativeIsEnableSpatializer(long j);

    private static native boolean nativeIsUserIDInAudioBlackList(long j, String str);

    private static native int nativeRemoveAudioBlackList(long j, String str);

    private static native int nativeSendCustomAudioData(long j, AudioFrame audioFrame);

    private static native int nativeSetAudioFrameCallback(long j, GMESDKAudioJni gMESDKAudioJni, boolean z);

    private static native int nativeSetAudioRoute(long j, int i);

    private static native int nativeSetLoopBackVolume(long j, int i);

    private static native int nativeSetMicVolume(long j, int i);

    private static native int nativeSetSpeakerVolume(long j, int i);

    private static native int nativeSetSpeakerVolumeByUserID(long j, String str, int i);

    private static native int nativeStopTrackingVolume(long j);

    private static native int nativeTrackingVolume(long j, float f);

    public int EnableCustomAudioCapture(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableCustomAudioCapture(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int EnableCustomAudioRendering(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableCustomAudioRendering(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int EnableSpatializer(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableSpatializer(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int GetCustomAudioRenderingFrame(ITMGType.TMGAudioFrame tMGAudioFrame) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetCustomAudioRenderingFrame(this.mNativeGMESDKAudioJni, new AudioFrame(tMGAudioFrame));
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int GetMicLevel() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetMicLevel(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int GetRecvStreamLevel(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetRecvStreamLevel(this.mNativeGMESDKAudioJni, str);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int GetSendStreamLevel() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetSendStreamLevel(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int GetSpeakerLevel() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetSpeakerLevel(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int InitSpatializer(String str) {
        return 0;
    }

    public boolean IsEnableSpatializer() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsEnableSpatializer(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int SendCustomAudioData(ITMGType.TMGAudioFrame tMGAudioFrame) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSendCustomAudioData(this.mNativeGMESDKAudioJni, new AudioFrame(tMGAudioFrame));
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int SetAudioRoute(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSetAudioRoute(this.mNativeGMESDKAudioJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int StopTrackingVolume() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeStopTrackingVolume(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int TrackingVolume(float f) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeTrackingVolume(this.mNativeGMESDKAudioJni, f);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int addAudioBlackList(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeAddAudioBlackList(this.mNativeGMESDKAudioJni, str);
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
            if (this.mNativeGMESDKAudioJni == 0) {
                this.mJniWriteLock.unlock();
                return AVError.AV_ERR_CONTEXT_NOT_START;
            }
            int iNativeDestroy = nativeDestroy(this.mNativeGMESDKAudioJni);
            this.mNativeGMESDKAudioJni = 0L;
            return iNativeDestroy;
        } finally {
            this.mJniWriteLock.unlock();
        }
    }

    public int enableAudioCaptureDevice(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableAudioCaptureDevice(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int enableAudioPlayDevice(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableAudioPlayDevice(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int enableAudioRecv(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableAudioRecv(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int enableAudioSend(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableAudioSend(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int enableLoopBack(boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeEnableLoopBack(this.mNativeGMESDKAudioJni, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getMicVolume() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetMicVolume(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getSpeakerVolume() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetSpeakerVolume(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getSpeakerVolumeByUserID(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeGetSpeakerVolumeByUserID(this.mNativeGMESDKAudioJni, str);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isAudioCaptureDeviceEnabled() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsAudioCaptureDeviceEnabled(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isAudioPlayDeviceEnabled() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsAudioPlayDeviceEnabled(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isAudioRecvEnabled() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsAudioRecvEnabled(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isAudioSendEnabled() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsAudioSendEnabled(this.mNativeGMESDKAudioJni);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isUserIDInAudioBlackList(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeIsUserIDInAudioBlackList(this.mNativeGMESDKAudioJni, str);
            }
            this.mJniReadLock.unlock();
            return false;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int removeAudioBlackList(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeRemoveAudioBlackList(this.mNativeGMESDKAudioJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public void setAudioFrameCallback(ITMGAudioCtrl.ITMGAudioFrameCallback iTMGAudioFrameCallback) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                nativeSetAudioFrameCallback(this.mNativeGMESDKAudioJni, this, iTMGAudioFrameCallback != null);
                this.mAudioFrameCallback = iTMGAudioFrameCallback;
            }
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setLoopBackVolume(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSetLoopBackVolume(this.mNativeGMESDKAudioJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setMicVolume(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSetMicVolume(this.mNativeGMESDKAudioJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setSpeakerVolume(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSetSpeakerVolume(this.mNativeGMESDKAudioJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setSpeakerVolumeByUserID(String str, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioJni != 0) {
                return nativeSetSpeakerVolumeByUserID(this.mNativeGMESDKAudioJni, str, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }
}
