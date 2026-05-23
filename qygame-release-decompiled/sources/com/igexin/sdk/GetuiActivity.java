package com.igexin.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.igexin.push.a.a;
import com.igexin.push.a.b;

/* JADX INFO: loaded from: classes.dex */
public class GetuiActivity extends Activity {
    private b activityAction;

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.activityAction != null) {
            this.activityAction.a(this);
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a.a();
        this.activityAction = a.a(this);
        if (this.activityAction != null) {
            this.activityAction.b(this);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.activityAction != null) {
            this.activityAction.i();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }
}
