package com.gme.liteav.audio2;

import android.media.AudioTimestamp;
import android.media.AudioTrack;
import android.os.Process;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::audio")
public class LiteavAudioTrack3 {
    private static final int DEFAULT_LATENCY_MS = 160;
    private static final int HARDWARE_LATENCY_MS = 20;
    private static final long LATENCY_THRESHOLD_MS = 1000;
    private static final long NANOS_PER_MS = 1000000;
    private static final long NANOS_PER_SECOND = 1000000000;
    private static final String TAG = "LiteavAudioTrack3";
    private AudioTrack mAudioTrack;
    private byte[] mPlayBuffer;
    private int mSampleRate = 0;
    private int mBufferSize = 0;
    private int mSystemOSVersion = 0;
    private int mWriteFrameIndex = 0;
    private int mBytesPerFrame = 0;

    private AudioTrack createStartedAudioTrack(int i, int i2, int i3, int i4) {
        AudioTrack audioTrack;
        try {
            audioTrack = new AudioTrack(i4, i, i2, 2, i3, 1);
            try {
                if (audioTrack.getState() != 1) {
                    throw new RuntimeException("AudioTrack is not initialized.");
                }
                this.mWriteFrameIndex = 0;
                audioTrack.play();
                Log.i(TAG, "create AudioTrack success. sampleRate: %d, channelConfig: %d, bufferSize: %d, streamType: %s", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), streamTypeToString(i4));
                return audioTrack;
            } catch (Throwable unused) {
                Log.w(TAG, "create AudioTrack failed. sampleRate: %d, channelConfig: %d, bufferSize: %d, streamType: %s", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), streamTypeToString(i4));
                destroyAudioTrack(audioTrack);
                return null;
            }
        } catch (Throwable unused2) {
            audioTrack = null;
        }
    }

    private void destroyAudioTrack(AudioTrack audioTrack) {
        if (audioTrack == null) {
            return;
        }
        try {
            if (audioTrack.getPlayState() == 3) {
                audioTrack.stop();
                audioTrack.flush();
            }
            audioTrack.release();
        } catch (Throwable th) {
            Log.e(TAG, "stop AudioTrack failed.", th);
        }
    }

    private int getLatencyByTimestamp() {
        AudioTimestamp audioTimestamp = new AudioTimestamp();
        if (!this.mAudioTrack.getTimestamp(audioTimestamp)) {
            Log.w(TAG, "fail to get AudioTrack timestamp", new Object[0]);
            return 160;
        }
        int iLengthBytesToNano = ((int) (((audioTimestamp.nanoTime + lengthBytesToNano(((long) this.mWriteFrameIndex) - audioTimestamp.framePosition)) - System.nanoTime()) / 1000000)) + 20;
        if (iLengthBytesToNano <= 0 || iLengthBytesToNano >= 1000) {
            return 160;
        }
        return iLengthBytesToNano;
    }

    private long lengthBytesToNano(long j) {
        return (j * NANOS_PER_SECOND) / ((long) this.mSampleRate);
    }

    private static String streamTypeToString(int i) {
        switch (i) {
            case 0:
                return "STREAM_VOICE_CALL";
            case 1:
                return "STREAM_SYSTEM";
            case 2:
                return "STREAM_RING";
            case 3:
                return "STREAM_MUSIC";
            case 4:
                return "STREAM_ALARM";
            case 5:
                return "STREAM_NOTIFICATION";
            default:
                return "STREAM_INVALID";
        }
    }

    public int getBufferSize() {
        return this.mBufferSize;
    }

    public int getPlayoutLatencyMs() {
        if (this.mAudioTrack == null || this.mSystemOSVersion < 23) {
            return 160;
        }
        try {
            return getLatencyByTimestamp();
        } catch (Throwable th) {
            Log.w(TAG, "get latency exception " + th.getMessage(), new Object[0]);
            return 160;
        }
    }

    public int getUnderrunCount() {
        if (this.mAudioTrack == null) {
            return 0;
        }
        try {
            if (this.mSystemOSVersion >= 24) {
                return this.mAudioTrack.getUnderrunCount();
            }
            return 0;
        } catch (Throwable th) {
            Log.w(TAG, "get under run count exception " + th.getMessage(), new Object[0]);
            return 0;
        }
    }

    public int startPlayout(int i, int i2, int i3, int i4) {
        this.mBytesPerFrame = i3 * 2;
        this.mSampleRate = i2;
        int i5 = i3 == 1 ? 4 : 12;
        int minBufferSize = AudioTrack.getMinBufferSize(i2, i5, 2);
        if (minBufferSize <= 0) {
            Log.e(TAG, "AudioTrack.getMinBufferSize return error: ".concat(String.valueOf(minBufferSize)), new Object[0]);
            return -2;
        }
        int[] iArr = {i, 0, 3, 1};
        for (int i6 = 0; i6 < 4 && this.mAudioTrack == null; i6++) {
            int i7 = iArr[i6];
            for (int i8 = 1; i8 <= 2 && this.mAudioTrack == null; i8++) {
                this.mBufferSize = minBufferSize * i8;
                if (this.mBufferSize >= i4 * 4 || i8 >= 2) {
                    this.mAudioTrack = createStartedAudioTrack(i2, i5, this.mBufferSize, i7);
                }
            }
        }
        if (this.mAudioTrack == null) {
            return -1;
        }
        this.mSystemOSVersion = LiteavSystemInfo.getSystemOSVersionInt();
        Process.setThreadPriority(-19);
        return 0;
    }

    public void stopPlayout() {
        destroyAudioTrack(this.mAudioTrack);
        this.mAudioTrack = null;
    }

    public int write(ByteBuffer byteBuffer, int i, int i2) {
        int iWrite;
        if (this.mAudioTrack == null) {
            return -1;
        }
        byteBuffer.position(i);
        if (this.mSystemOSVersion >= 21) {
            iWrite = this.mAudioTrack.write(byteBuffer, i2, 1);
        } else {
            if (this.mPlayBuffer == null || this.mPlayBuffer.length < i2) {
                this.mPlayBuffer = new byte[i2];
            }
            byteBuffer.get(this.mPlayBuffer, 0, i2);
            iWrite = this.mAudioTrack.write(this.mPlayBuffer, 0, i2);
        }
        if (iWrite < 0) {
            Log.e(TAG, "write audio data to AudioTrack failed. ".concat(String.valueOf(iWrite)), new Object[0]);
            return -1;
        }
        this.mWriteFrameIndex += iWrite / this.mBytesPerFrame;
        return iWrite;
    }
}
