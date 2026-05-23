package com.gme.liteav.audio2;

import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class c extends AudioManager.AudioPlaybackCallback {
    volatile a a;

    interface a {
        void a();
    }

    public c() {
        AudioManager audioManager;
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 26 && (audioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio")) != null) {
            try {
                audioManager.registerAudioPlaybackCallback(this, null);
                Log.i("LiteavAudioPlaybackCallback", "register audio playback callback", new Object[0]);
            } catch (Throwable th) {
                Log.e("LiteavAudioPlaybackCallback", "register audio playback callback exception " + th.getMessage(), new Object[0]);
            }
        }
    }

    @Override // android.media.AudioManager.AudioPlaybackCallback
    public final void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) {
        a aVar = this.a;
        if (aVar == null) {
            return;
        }
        aVar.a();
    }
}
