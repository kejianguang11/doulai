package org.cocos2dx.javascript;

import android.content.Intent;
import android.net.Uri;
import org.cocos2dx.lib.Cocos2dxActivity;

/* JADX INFO: loaded from: classes.dex */
public class PermissionModule {
    private static AppActivity app;

    public static boolean checkLocationAuth() {
        return app.getPackageManager().checkPermission("android.permission.ACCESS_COARSE_LOCATION", Cocos2dxActivity.getContext().getPackageName()) == 0;
    }

    public static boolean checkNotifyAuth() {
        return true;
    }

    public static boolean checkRecordAuth() {
        return app.getPackageManager().checkPermission("android.permission.RECORD_AUDIO", Cocos2dxActivity.getContext().getPackageName()) == 0;
    }

    public static void openAppAuthSetting() {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", Cocos2dxActivity.getContext().getPackageName(), null));
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void setContext(AppActivity appActivity) {
        app = appActivity;
    }
}
