package org.cocos2dx.lib;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class Cocos2dxAudioFocusManager {
    private static final int AUDIOFOCUS_GAIN = 0;
    private static final int AUDIOFOCUS_LOST = 1;
    private static final int AUDIOFOCUS_LOST_TRANSIENT = 2;
    private static final int AUDIOFOCUS_LOST_TRANSIENT_CAN_DUCK = 3;
    private static final String TAG = "AudioFocusManager";
    private static AudioManager.OnAudioFocusChangeListener sAfChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            Runnable runnable;
            Log.d(Cocos2dxAudioFocusManager.TAG, "onAudioFocusChange: " + i + ", thread: " + Thread.currentThread().getName());
            if (i == -1) {
                Log.d(Cocos2dxAudioFocusManager.TAG, "Pause music by AUDIOFOCUS_LOSS");
                runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxAudioFocusManager.nativeOnAudioFocusChange(1);
                    }
                };
            } else if (i == -2) {
                Log.d(Cocos2dxAudioFocusManager.TAG, "Pause music by AUDIOFOCUS_LOSS_TRANSILENT");
                runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxAudioFocusManager.nativeOnAudioFocusChange(2);
                    }
                };
            } else if (i == -3) {
                Log.d(Cocos2dxAudioFocusManager.TAG, "Lower the volume, keep playing by AUDIOFOCUS_LOSS_TRANSILENT_CAN_DUCK");
                runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxAudioFocusManager.nativeOnAudioFocusChange(3);
                    }
                };
            } else {
                if (i != 1) {
                    return;
                }
                Log.d(Cocos2dxAudioFocusManager.TAG, "Resume music by AUDIOFOCUS_GAIN");
                runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxAudioFocusManager.nativeOnAudioFocusChange(0);
                    }
                };
            }
            Cocos2dxHelper.runOnGLThread(runnable);
        }
    };

    Cocos2dxAudioFocusManager() {
    }

    static boolean a(Context context) {
        if (((AudioManager) context.getSystemService("audio")).requestAudioFocus(sAfChangeListener, 3, 1) == 1) {
            Log.d(TAG, "requestAudioFocus succeed");
            return true;
        }
        Log.e(TAG, "requestAudioFocus failed!");
        return false;
    }

    static void b(Context context) {
        if (((AudioManager) context.getSystemService("audio")).abandonAudioFocus(sAfChangeListener) == 1) {
            Log.d(TAG, "abandonAudioFocus succeed!");
        } else {
            Log.e(TAG, "abandonAudioFocus failed!");
        }
        Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxAudioFocusManager.2
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxAudioFocusManager.nativeOnAudioFocusChange(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeOnAudioFocusChange(int i);
}
