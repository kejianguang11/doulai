package org.cocos2dx.javascript.gme;

import android.content.Intent;
import com.gme.TMG.ITMGContext;
import com.gme.TMG.ITMGType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class TMGCallbackDispatcher extends ITMGContext.ITMGDelegate {
    private static TMGCallbackDispatcher mInstance;
    private Set<TMGDispatcherBase> mSubDispatcherSet = new HashSet();

    private TMGCallbackDispatcher() {
    }

    public static TMGCallbackDispatcher getInstance() {
        if (mInstance == null) {
            synchronized (TMGCallbackDispatcher.class) {
                if (mInstance == null) {
                    mInstance = new TMGCallbackDispatcher();
                }
            }
        }
        return mInstance;
    }

    @Override // com.gme.TMG.ITMGContext.ITMGDelegate
    public void OnEvent(ITMGType.ITMG_MAIN_EVENT_TYPE itmg_main_event_type, Intent intent) {
        Iterator<TMGDispatcherBase> it = this.mSubDispatcherSet.iterator();
        while (it.hasNext()) {
            it.next().OnEvent(itmg_main_event_type, intent);
        }
    }

    public void registerSubDispatcher(TMGDispatcherBase tMGDispatcherBase) {
        this.mSubDispatcherSet.add(tMGDispatcherBase);
    }

    public void unregisterSubDispatcher(TMGDispatcherBase tMGDispatcherBase) {
        this.mSubDispatcherSet.remove(tMGDispatcherBase);
    }
}
