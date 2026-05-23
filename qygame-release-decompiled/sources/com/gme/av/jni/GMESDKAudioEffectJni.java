package com.gme.av.jni;

import com.gme.av.sdk.AVError;
import com.gme.liteav.base.annotations.JNINamespace;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::gme")
public class GMESDKAudioEffectJni {
    private long mNativeGMESDKAudioEffectJni;
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mJniReadLock = this.mReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock mJniWriteLock = this.mReadWriteLock.writeLock();

    public GMESDKAudioEffectJni(long j) {
        this.mNativeGMESDKAudioEffectJni = j;
    }

    private static native int nativeDestroy(long j);

    private static native int nativeEnableMusicPlayout(long j, long j2, boolean z);

    private static native int nativeEnableMusicPublish(long j, long j2, boolean z);

    private static native int nativeGetMusicCurrentPosInMS(long j, long j2);

    private static native int nativeGetMusicDurationInMS(long j, long j2);

    private static native int nativeGetMusicPlayoutVolume(long j, long j2);

    private static native int nativeGetMusicPublishVolume(long j, long j2);

    private static native boolean nativeIsMusicPlayEnd(long j, long j2);

    private static native int nativePausePlayMusic(long j, long j2);

    private static native int nativeResumePlayMusic(long j, long j2);

    private static native int nativeSeekMusicToPosInTime(long j, long j2, int i);

    private static native int nativeSetAllMusicVolume(long j, int i);

    private static native int nativeSetKaraokeType(long j, int i);

    private static native int nativeSetMusicPitch(long j, long j2, float f);

    private static native int nativeSetMusicPlayoutVolume(long j, long j2, int i);

    private static native int nativeSetMusicPublishVolume(long j, long j2, int i);

    private static native int nativeSetVoiceType(long j, int i);

    private static native int nativeStartPlayMusic(long j, long j2, String str, int i);

    private static native int nativeStartRecord(long j, String str, int i);

    private static native int nativeStopPlayMusic(long j, long j2);

    private static native int nativeStopRecord(long j);

    public int destroy() {
        this.mJniWriteLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni == 0) {
                this.mJniWriteLock.unlock();
                return AVError.AV_ERR_CONTEXT_NOT_START;
            }
            int iNativeDestroy = nativeDestroy(this.mNativeGMESDKAudioEffectJni);
            this.mNativeGMESDKAudioEffectJni = 0L;
            return iNativeDestroy;
        } finally {
            this.mJniWriteLock.unlock();
        }
    }

    public int enableMusicPlayout(long j, boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeEnableMusicPlayout(this.mNativeGMESDKAudioEffectJni, j, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int enableMusicPublish(long j, boolean z) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeEnableMusicPublish(this.mNativeGMESDKAudioEffectJni, j, z);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getMusicCurrentPosInMS(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeGetMusicCurrentPosInMS(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getMusicDurationInMS(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeGetMusicDurationInMS(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return 0;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getMusicPlayoutVolume(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeGetMusicPlayoutVolume(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getMusicPublishVolume(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeGetMusicPublishVolume(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public boolean isMusicPlayEnd(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeIsMusicPlayEnd(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return true;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int pausePlayMusic(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativePausePlayMusic(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int resumePlayMusic(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeResumePlayMusic(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int seekMusicToPosInTime(long j, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSeekMusicToPosInTime(this.mNativeGMESDKAudioEffectJni, j, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setAllMusicVolume(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetAllMusicVolume(this.mNativeGMESDKAudioEffectJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setKaraokeType(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetKaraokeType(this.mNativeGMESDKAudioEffectJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setMusicPitch(long j, float f) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetMusicPitch(this.mNativeGMESDKAudioEffectJni, j, f);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setMusicPlayoutVolume(long j, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetMusicPlayoutVolume(this.mNativeGMESDKAudioEffectJni, j, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setMusicPublishVolume(long j, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetMusicPublishVolume(this.mNativeGMESDKAudioEffectJni, j, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setVoiceType(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeSetVoiceType(this.mNativeGMESDKAudioEffectJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startPlayMusic(long j, String str, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeStartPlayMusic(this.mNativeGMESDKAudioEffectJni, j, str, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRecord(String str, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeStartRecord(this.mNativeGMESDKAudioEffectJni, str, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopPlayMusic(long j) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeStopPlayMusic(this.mNativeGMESDKAudioEffectJni, j);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopRecord() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKAudioEffectJni != 0) {
                return nativeStopRecord(this.mNativeGMESDKAudioEffectJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }
}
