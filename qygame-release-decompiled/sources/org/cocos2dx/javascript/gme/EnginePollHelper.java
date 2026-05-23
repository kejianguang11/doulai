package org.cocos2dx.javascript.gme;

import android.os.Handler;
import com.gme.TMG.ITMGContext;

/* JADX INFO: loaded from: classes.dex */
public class EnginePollHelper {
    private static EnginePollHelper s_enginePollHelper = null;
    private static boolean s_pollEnabled = true;
    private Handler mhandler = new Handler();
    private Runnable mRunnable = new Runnable() { // from class: org.cocos2dx.javascript.gme.EnginePollHelper.1
        @Override // java.lang.Runnable
        public void run() {
            if (EnginePollHelper.s_pollEnabled && ITMGContext.GetInstance(null) != null) {
                ITMGContext.GetInstance(null).Poll();
            }
            EnginePollHelper.this.mhandler.postDelayed(EnginePollHelper.this.mRunnable, 33L);
        }
    };

    EnginePollHelper() {
    }

    public static void createEnginePollHelper() {
        if (s_enginePollHelper == null) {
            s_enginePollHelper = new EnginePollHelper();
            s_enginePollHelper.startTimer();
        }
    }

    public static void destroyEnginePollHelper() {
        if (s_enginePollHelper != null) {
            s_enginePollHelper.stopTimer();
            s_enginePollHelper = null;
        }
    }

    public static void pauseEnginePollHelper() {
        s_pollEnabled = false;
    }

    public static void resumeEnginePollHelper() {
        s_pollEnabled = true;
    }

    private void startTimer() {
        this.mhandler.postDelayed(this.mRunnable, 33L);
    }

    private void stopTimer() {
        this.mhandler.removeCallbacks(this.mRunnable);
    }
}
