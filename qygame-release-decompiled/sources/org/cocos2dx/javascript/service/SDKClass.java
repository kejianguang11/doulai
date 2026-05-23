package org.cocos2dx.javascript.service;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public abstract class SDKClass implements SDKInterface {
    private Context mainActive = null;

    public Context getContext() {
        return this.mainActive;
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void init(Context context) {
        this.mainActive = context;
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onBackPressed() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onDestroy() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onNewIntent(Intent intent) {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onPause() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onRestart() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onRestoreInstanceState(Bundle bundle) {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onResume() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onStart() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void onStop() {
    }

    @Override // org.cocos2dx.javascript.service.SDKInterface
    public void setGLSurfaceView(GLSurfaceView gLSurfaceView) {
    }
}
