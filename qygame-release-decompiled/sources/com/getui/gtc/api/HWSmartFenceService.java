package com.getui.gtc.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public abstract class HWSmartFenceService extends Service {
    public static final String ACTION = "com.huawei.hms.location.action.gt.geofence";

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (intent != null && ACTION.equals(intent.getAction())) {
            GtcManager.getInstance().onHWSmartFenceBind(this, (Intent) intent.clone());
        }
        return onSmartFenceBind(intent);
    }

    public abstract IBinder onSmartFenceBind(Intent intent);
}
