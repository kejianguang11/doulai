package org.cocos2dx.javascript.gme;

import android.content.Intent;
import com.gme.TMG.ITMGType;

/* JADX INFO: loaded from: classes.dex */
public interface TMGDispatcherBase {
    void OnEvent(ITMGType.ITMG_MAIN_EVENT_TYPE itmg_main_event_type, Intent intent);
}
