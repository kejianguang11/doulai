package com.gme.liteav.audio2;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.projection.MediaProjection;
import android.os.Process;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::audio")
public class SystemLoopbackRecorder2 {
    private static final String TAG = "SystemLoopbackRecorder2";
    private static final Object mLock = new Object();
    private static MediaProjection mMediaProjection = null;
    private static volatile long mNativeSystemLoopbackRecorder;

    static class Recorder {
        private AudioRecord a;
        private AudioManager b;

        public Recorder() {
            Context applicationContext = ContextUtils.getApplicationContext();
            ContextUtils.getApplicationContext();
            this.b = (AudioManager) applicationContext.getSystemService("audio");
        }

        private static AudioRecord a(MediaProjection mediaProjection, int i, int i2, int i3) {
            AudioRecord audioRecordBuild;
            Throwable th;
            AudioPlaybackCaptureConfiguration.Builder builder = new AudioPlaybackCaptureConfiguration.Builder(mediaProjection);
            builder.addMatchingUsage(1);
            builder.addMatchingUsage(14);
            AudioPlaybackCaptureConfiguration audioPlaybackCaptureConfigurationBuild = builder.build();
            if (audioPlaybackCaptureConfigurationBuild == null) {
                return null;
            }
            int i4 = i2 == 1 ? 16 : 12;
            AudioFormat audioFormatBuild = new AudioFormat.Builder().setEncoding(2).setSampleRate(i).setChannelMask(i4).build();
            int minBufferSize = AudioRecord.getMinBufferSize(i, i4, 2);
            AudioRecord audioRecord = null;
            for (int i5 = 1; i5 <= 2 && audioRecord == null; i5++) {
                int i6 = minBufferSize * i5;
                if (i6 >= i3 * 4 || i5 >= 2) {
                    try {
                        audioRecordBuild = new AudioRecord.Builder().setAudioFormat(audioFormatBuild).setBufferSizeInBytes(i6).setAudioPlaybackCaptureConfig(audioPlaybackCaptureConfigurationBuild).build();
                        try {
                        } catch (Throwable th2) {
                            th = th2;
                            Log.w(SystemLoopbackRecorder2.TAG, "Create record error " + th.getMessage(), new Object[0]);
                            a(audioRecordBuild);
                        }
                    } catch (Throwable th3) {
                        audioRecordBuild = audioRecord;
                        th = th3;
                    }
                    if (audioRecordBuild.getState() != 1) {
                        Log.e(SystemLoopbackRecorder2.TAG, "Audio record state error", new Object[0]);
                        a(audioRecordBuild);
                        audioRecord = null;
                    } else {
                        audioRecordBuild.startRecording();
                        Log.i(SystemLoopbackRecorder2.TAG, "Create audio record success", new Object[0]);
                        audioRecord = audioRecordBuild;
                    }
                }
            }
            return audioRecord;
        }

        private void a(int i) {
            try {
                if (this.b != null) {
                    this.b.setMode(i);
                }
            } catch (Throwable th) {
                Log.e(SystemLoopbackRecorder2.TAG, "Set audio mode exception " + th.getMessage(), new Object[0]);
            }
        }

        private static void a(AudioRecord audioRecord) {
            if (audioRecord == null) {
                return;
            }
            try {
                if (audioRecord.getRecordingState() == 3) {
                    audioRecord.stop();
                }
                audioRecord.release();
            } catch (Throwable th) {
                Log.e(SystemLoopbackRecorder2.TAG, "Destroy AudioRecord failed." + th.getMessage(), new Object[0]);
            }
        }

        public int read(ByteBuffer byteBuffer, int i) {
            if (this.a == null) {
                return -1;
            }
            byteBuffer.position(0);
            int i2 = this.a.read(byteBuffer, i);
            if (i2 > 0) {
                return i2;
            }
            Log.e(SystemLoopbackRecorder2.TAG, "Read failed ".concat(String.valueOf(i2)), new Object[0]);
            return -1;
        }

        public int startRecording(MediaProjection mediaProjection, int i, int i2, int i3, int i4) {
            if (i4 == 0) {
                try {
                    if (this.b != null) {
                        this.b.setAllowedCapturePolicy(3);
                    }
                } catch (Throwable th) {
                    Log.e(SystemLoopbackRecorder2.TAG, "ForbidCaptureAudioFromCurrentApp error " + th.getMessage(), new Object[0]);
                }
            }
            int mode = this.b != null ? this.b.getMode() : 0;
            a(0);
            this.a = a(mediaProjection, i, i2, i3);
            a(mode);
            if (this.a == null) {
                return -1;
            }
            Process.setThreadPriority(-19);
            return 0;
        }

        public void stopRecording() {
            a(this.a);
            this.a = null;
        }
    }

    public SystemLoopbackRecorder2(long j) {
        mNativeSystemLoopbackRecorder = j;
    }

    private static native void nativeSetMediaProjectionSession(long j, MediaProjection mediaProjection);

    public static void notifyMediaProjectionState(MediaProjection mediaProjection) {
        StringBuilder sb = new StringBuilder("Received MediaProjection state ");
        sb.append(mediaProjection != null);
        Log.i(TAG, sb.toString(), new Object[0]);
        synchronized (mLock) {
            mMediaProjection = mediaProjection;
            setMediaProjectionSession();
        }
    }

    public static void setMediaProjectionSession() {
        if (mMediaProjection == null) {
            Log.i(TAG, "MediaProjection is null.", new Object[0]);
        } else if (mNativeSystemLoopbackRecorder != 0) {
            nativeSetMediaProjectionSession(mNativeSystemLoopbackRecorder, mMediaProjection);
        }
    }

    public MediaProjection getMediaProjection() {
        return mMediaProjection;
    }

    public void releaseNativeSystemLoopbackRecorder() {
        mNativeSystemLoopbackRecorder = 0L;
    }
}
