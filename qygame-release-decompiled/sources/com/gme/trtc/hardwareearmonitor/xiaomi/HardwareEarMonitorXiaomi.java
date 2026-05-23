package com.gme.trtc.hardwareearmonitor.xiaomi;

import android.media.AudioManager;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorXiaomi {
    private AudioManager mAudioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    private long mNativeHardwareEarMonitorHandle;

    public HardwareEarMonitorXiaomi(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
    }

    public static HardwareEarMonitorXiaomi create(long j) {
        return new HardwareEarMonitorXiaomi(j);
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
