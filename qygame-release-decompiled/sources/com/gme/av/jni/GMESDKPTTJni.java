package com.gme.av.jni;

import com.gme.av.sdk.AVError;
import com.gme.liteav.base.annotations.JNINamespace;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::gme")
public class GMESDKPTTJni {
    private long mNativeGMESDKPTTJni;
    private final ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mJniReadLock = this.mReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock mJniWriteLock = this.mReadWriteLock.writeLock();

    public GMESDKPTTJni(long j) {
        this.mNativeGMESDKPTTJni = j;
    }

    private static native int nativeApplyPTTAuthbuffer(long j, byte[] bArr);

    private static native int nativeCancelRecording(long j);

    private static native int nativeDestroy(long j);

    private static native int nativeDownloadRecordedFile(long j, String str, String str2);

    private static native int nativeGetFileSize(long j, String str);

    private static native int nativeGetVoiceFileDuration(long j, String str);

    private static native int nativePlayRecordedFile(long j, String str, int i);

    private static native int nativeSetMaxMessageLength(long j, int i);

    private static native int nativeSetPTTSourceLanguage(long j, String str);

    private static native int nativeSpeechToText(long j, String str, String str2, String str3);

    private static native int nativeStartRecording(long j, String str);

    private static native int nativeStartRecordingWithStreamingRecognition(long j, String str, String str2, String str3);

    private static native int nativeStopPlayFile(long j);

    private static native int nativeStopRecording(long j);

    private static native int nativeTextToSpeech(long j, int i, String str, String str2, String str3, float f);

    private static native int nativeTranslateText(long j, String str, String str2, String str3);

    private static native int nativeUploadRecordedFile(long j, String str);

    public int applyPTTAuthbuffer(byte[] bArr) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeApplyPTTAuthbuffer(this.mNativeGMESDKPTTJni, bArr);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int cancelRecording() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeCancelRecording(this.mNativeGMESDKPTTJni);
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
            if (this.mNativeGMESDKPTTJni == 0) {
                this.mJniWriteLock.unlock();
                return AVError.AV_ERR_CONTEXT_NOT_START;
            }
            int iNativeDestroy = nativeDestroy(this.mNativeGMESDKPTTJni);
            this.mNativeGMESDKPTTJni = 0L;
            return iNativeDestroy;
        } finally {
            this.mJniWriteLock.unlock();
        }
    }

    public int downloadRecordedFile(String str, String str2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeDownloadRecordedFile(this.mNativeGMESDKPTTJni, str, str2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getFileSize(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeGetFileSize(this.mNativeGMESDKPTTJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int getVoiceFileDuration(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeGetVoiceFileDuration(this.mNativeGMESDKPTTJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int playRecordedFile(String str, int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativePlayRecordedFile(this.mNativeGMESDKPTTJni, str, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setMaxMessageLength(int i) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeSetMaxMessageLength(this.mNativeGMESDKPTTJni, i);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int setPTTSourceLanguage(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeSetPTTSourceLanguage(this.mNativeGMESDKPTTJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int speechToText(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeSpeechToText(this.mNativeGMESDKPTTJni, str, "", "");
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int speechToText(String str, String str2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeSpeechToText(this.mNativeGMESDKPTTJni, str, str2, str2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int speechToText(String str, String str2, String str3) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeSpeechToText(this.mNativeGMESDKPTTJni, str, str2, str3);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRecording(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStartRecording(this.mNativeGMESDKPTTJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRecordingWithStreamingRecognition(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStartRecordingWithStreamingRecognition(this.mNativeGMESDKPTTJni, str, "cmn-Hans-CN", "cmn-Hans-CN");
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRecordingWithStreamingRecognition(String str, String str2) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStartRecordingWithStreamingRecognition(this.mNativeGMESDKPTTJni, str, str2, str2);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int startRecordingWithStreamingRecognition(String str, String str2, String str3) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStartRecordingWithStreamingRecognition(this.mNativeGMESDKPTTJni, str, str2, str3);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopPlayFile() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStopPlayFile(this.mNativeGMESDKPTTJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int stopRecording() {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeStopRecording(this.mNativeGMESDKPTTJni);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int textToSpeech(int i, String str, String str2, String str3, float f) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeTextToSpeech(this.mNativeGMESDKPTTJni, i, str, str2, str3, f);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int translateText(String str, String str2, String str3) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeTranslateText(this.mNativeGMESDKPTTJni, str, str2, str3);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }

    public int uploadRecordedFile(String str) {
        this.mJniReadLock.lock();
        try {
            if (this.mNativeGMESDKPTTJni != 0) {
                return nativeUploadRecordedFile(this.mNativeGMESDKPTTJni, str);
            }
            this.mJniReadLock.unlock();
            return AVError.AV_ERR_CONTEXT_NOT_START;
        } finally {
            this.mJniReadLock.unlock();
        }
    }
}
