package com.gme.liteav.extensions.codec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class AacMediaCodecWrapper {
    final String a;
    MediaCodec b;
    MediaFormat c;
    int d = 0;
    private final int e;
    private final MediaCodec.BufferInfo f;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {
        public static final int a = 1;
        public static final int b = 2;
        private static final /* synthetic */ int[] c = {a, b};
    }

    public AacMediaCodecWrapper(int i) {
        this.e = i;
        this.a = i == a.a ? "HardwareAacEncoder" : "HardwareAacDecoder";
        this.f = new MediaCodec.BufferInfo();
    }

    private ByteBuffer b() {
        try {
            int iDequeueOutputBuffer = this.b.dequeueOutputBuffer(this.f, TimeUnit.MILLISECONDS.toMicros(5L));
            if (iDequeueOutputBuffer == -1) {
                return null;
            }
            if (iDequeueOutputBuffer == -3) {
                Log.i(this.a, "codec output buffers changed.", new Object[0]);
                return null;
            }
            if (iDequeueOutputBuffer == -2) {
                this.c = this.b.getOutputFormat();
                Log.i(this.a, "codec output format changed: " + this.c, new Object[0]);
                return null;
            }
            if (iDequeueOutputBuffer < 0) {
                Log.e(this.a, "unexpected result from dequeueOutputBuffer: ".concat(String.valueOf(iDequeueOutputBuffer)), new Object[0]);
                return null;
            }
            ByteBuffer outputBuffer = LiteavSystemInfo.getSystemOSVersionInt() >= 21 ? this.b.getOutputBuffer(iDequeueOutputBuffer) : this.b.getOutputBuffers()[iDequeueOutputBuffer];
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(this.f.size);
            byteBufferAllocateDirect.put(outputBuffer);
            this.b.releaseOutputBuffer(iDequeueOutputBuffer, false);
            if (this.d > 0) {
                this.d--;
            }
            return byteBufferAllocateDirect;
        } catch (Exception e) {
            Log.e(this.a, "dequeueOutputBuffer failed. ".concat(String.valueOf(e)), new Object[0]);
            return null;
        }
    }

    public final void a() {
        if (this.b == null) {
            return;
        }
        try {
            this.b.stop();
        } catch (Exception e) {
            Log.e(this.a, "codec stop failed.".concat(String.valueOf(e)), new Object[0]);
        }
        try {
            this.b.release();
        } catch (Exception e2) {
            Log.e(this.a, "codec release failed.".concat(String.valueOf(e2)), new Object[0]);
        }
        this.b = null;
        this.d = 0;
    }

    public final boolean a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            return false;
        }
        try {
            int i = this.e == a.a ? 1 : 0;
            if (this.b == null) {
                this.b = i != 0 ? MediaCodec.createEncoderByType("audio/mp4a-latm") : MediaCodec.createDecoderByType("audio/mp4a-latm");
            }
            this.b.configure(mediaFormat, (Surface) null, (MediaCrypto) null, i);
            this.b.start();
            return true;
        } catch (IOException e) {
            Log.e(this.a, "create codec failed. ".concat(String.valueOf(e)), new Object[0]);
            a();
            return false;
        }
    }

    public ByteBuffer processFrame(ByteBuffer byteBuffer) {
        if (this.b != null && byteBuffer != null) {
            try {
                ByteBuffer[] inputBuffers = this.b.getInputBuffers();
                if (inputBuffers == null || inputBuffers.length <= 0) {
                    Log.e(this.a, "get invalid input buffers.", new Object[0]);
                } else {
                    int iDequeueInputBuffer = this.b.dequeueInputBuffer(TimeUnit.MILLISECONDS.toMicros(5L));
                    if (iDequeueInputBuffer >= 0) {
                        int iRemaining = byteBuffer.remaining();
                        inputBuffers[iDequeueInputBuffer].put(byteBuffer);
                        this.b.queueInputBuffer(iDequeueInputBuffer, 0, iRemaining, 0L, 0);
                        this.d++;
                    }
                }
            } catch (Exception e) {
                Log.e(this.a, "feedData failed. ".concat(String.valueOf(e)), new Object[0]);
            }
            int i = (this.e != a.b || this.d > 2) ? 3 : 1;
            for (int i2 = 0; i2 < i; i2++) {
                ByteBuffer byteBufferB = b();
                if (byteBufferB != null) {
                    return byteBufferB;
                }
            }
        }
        return null;
    }
}
