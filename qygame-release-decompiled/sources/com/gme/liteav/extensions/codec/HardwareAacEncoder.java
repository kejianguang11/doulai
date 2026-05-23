package com.gme.liteav.extensions.codec;

import android.media.MediaFormat;
import com.gme.liteav.extensions.codec.AacMediaCodecWrapper;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class HardwareAacEncoder {
    private final AacMediaCodecWrapper a = new AacMediaCodecWrapper(AacMediaCodecWrapper.a.a);

    public ByteBuffer encode(ByteBuffer byteBuffer) {
        return this.a.processFrame(byteBuffer);
    }

    public boolean init(int i, int i2, int i3) {
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, i2);
        mediaFormatCreateAudioFormat.setInteger("bitrate", i3);
        mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
        return this.a.a(mediaFormatCreateAudioFormat);
    }

    public void unInit() {
        this.a.a();
    }
}
