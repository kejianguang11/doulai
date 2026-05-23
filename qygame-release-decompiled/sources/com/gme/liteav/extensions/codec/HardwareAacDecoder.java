package com.gme.liteav.extensions.codec;

import android.media.MediaFormat;
import android.os.Build;
import com.gme.liteav.base.Log;
import com.gme.liteav.extensions.codec.AacMediaCodecWrapper;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class HardwareAacDecoder {
    private final AacMediaCodecWrapper a = new AacMediaCodecWrapper(AacMediaCodecWrapper.a.b);

    public ByteBuffer decode(ByteBuffer byteBuffer) {
        return this.a.processFrame(byteBuffer);
    }

    public int getCacheSize() {
        return this.a.d;
    }

    public int getOutputChannelCount() {
        MediaFormat mediaFormat = this.a.c;
        if (mediaFormat == null) {
            return -1;
        }
        try {
            return mediaFormat.getInteger("channel-count");
        } catch (Exception e) {
            Log.e("HardwareAacDecoder", "getOutputChannelCount failed. ".concat(String.valueOf(e)), new Object[0]);
            return -1;
        }
    }

    public int getOutputSampleRate() {
        MediaFormat mediaFormat = this.a.c;
        if (mediaFormat == null) {
            return -1;
        }
        try {
            return mediaFormat.getInteger("sample-rate");
        } catch (Exception e) {
            Log.e("HardwareAacDecoder", "getOutputSampleRate failed. ".concat(String.valueOf(e)), new Object[0]);
            return -1;
        }
    }

    public boolean init(int i, int i2, ByteBuffer byteBuffer) {
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, i2);
        mediaFormatCreateAudioFormat.setString("mime", "audio/mp4a-latm");
        mediaFormatCreateAudioFormat.setByteBuffer("csd-0", byteBuffer);
        return this.a.a(mediaFormatCreateAudioFormat);
    }

    public void reset() {
        AacMediaCodecWrapper aacMediaCodecWrapper = this.a;
        if (aacMediaCodecWrapper.b != null) {
            try {
                aacMediaCodecWrapper.b.stop();
                if (Build.VERSION.SDK_INT >= 21) {
                    aacMediaCodecWrapper.b.reset();
                }
            } catch (Exception e) {
                Log.e(aacMediaCodecWrapper.a, "codec stop failed.".concat(String.valueOf(e)), new Object[0]);
                aacMediaCodecWrapper.a();
            }
            aacMediaCodecWrapper.d = 0;
            aacMediaCodecWrapper.c = null;
        }
    }

    public void unInit() {
        this.a.a();
    }
}
