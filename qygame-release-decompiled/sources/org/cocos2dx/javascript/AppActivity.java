package org.cocos2dx.javascript;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gcloudsdk.gcloud.voice.GCloudVoiceEngine;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.qygame.qzdlyx.mobile.QuickMobileModule;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public class AppActivity extends Cocos2dxActivity {
    public static AppActivity app;
    private BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() { // from class: org.cocos2dx.javascript.AppActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                DeviceModule.batteryLevel = intent.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, 0) / intent.getIntExtra("scale", 100);
            }
        }
    };

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        SDKWrapper.getInstance().onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        SDKWrapper.getInstance().onBackPressed();
        super.onBackPressed();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        SDKWrapper.getInstance().onConfigurationChanged(configuration);
        super.onConfigurationChanged(configuration);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                onNewIntent(intent);
            }
            app = this;
            DeviceModule.setContext(this);
            WeChatModule.setContext(this);
            JSBridgeHelper.setContext(this);
            PermissionModule.setContext(this);
            QuickMobileModule.init(getApplicationContext(), this);
            GCloudVoiceEngine.getInstance().init(getApplicationContext(), this);
            GmeVoiceModule.getInstance().setContext(this);
            SDKWrapper.getInstance().init(this);
            registerReceiver(this.batteryLevelReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity
    public Cocos2dxGLSurfaceView onCreateView() {
        Cocos2dxGLSurfaceView cocos2dxGLSurfaceView = new Cocos2dxGLSurfaceView(this);
        cocos2dxGLSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);
        SDKWrapper.getInstance().setGLSurfaceView(cocos2dxGLSurfaceView, this);
        return cocos2dxGLSurfaceView;
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isTaskRoot()) {
            SDKWrapper.getInstance().onDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SDKWrapper.getInstance().onNewIntent(intent);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        SDKWrapper.getInstance().onPause();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        SDKWrapper.getInstance().onRestart();
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        SDKWrapper.getInstance().onRestoreInstanceState(bundle);
        super.onRestoreInstanceState(bundle);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        SDKWrapper.getInstance().onResume();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        SDKWrapper.getInstance().onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    protected void onStart() {
        SDKWrapper.getInstance().onStart();
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        SDKWrapper.getInstance().onStop();
    }
}
