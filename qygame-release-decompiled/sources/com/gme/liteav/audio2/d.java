package com.gme.liteav.audio2;

import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class d extends AudioManager.AudioRecordingCallback {
    volatile a a;

    interface a {
        void OnRecordingConfigChanged(List<AudioRecordingConfiguration> list);
    }

    public d() {
        AudioManager audioManager;
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 24 && (audioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio")) != null) {
            try {
                audioManager.registerAudioRecordingCallback(this, null);
                Log.i("LiteavAudioRecordingCallback", "register audio recording callback", new Object[0]);
            } catch (Throwable th) {
                Log.e("LiteavAudioRecordingCallback", "register audio recording callback exception " + th.getMessage(), new Object[0]);
            }
        }
    }

    @Override // android.media.AudioManager.AudioRecordingCallback
    public final void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
        a aVar = this.a;
        if (aVar == null) {
            return;
        }
        aVar.OnRecordingConfigChanged(list);
    }
}
