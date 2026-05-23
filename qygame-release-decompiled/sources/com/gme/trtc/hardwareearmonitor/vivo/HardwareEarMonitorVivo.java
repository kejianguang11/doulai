package com.gme.trtc.hardwareearmonitor.vivo;

import android.media.AudioManager;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorVivo {
    private AudioManager mAudioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    private long mNativeHardwareEarMonitorHandle;

    public HardwareEarMonitorVivo(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
    }

    public static HardwareEarMonitorVivo create(long j) {
        return new HardwareEarMonitorVivo(j);
    }

    public String getParameters(String str) {
        try {
            return this.mAudioManager.getParameters(str);
        } catch (Throwable unused) {
            return "";
        }
    }

    public boolean setAudioParams(String str) {
        try {
            this.mAudioManager.setParameters(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
