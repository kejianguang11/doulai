package com.gme.liteav.audio2.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;
import com.gme.liteav.base.util.e;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PermissionActivity extends Activity {
    private static final Map<PermissionActivity, a> a = new HashMap();

    public static abstract class a implements Serializable {
        public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
        }
    }

    static void a(Context context, String[] strArr, a aVar) {
        try {
            Intent intent = new Intent(context, (Class<?>) PermissionActivity.class);
            intent.putExtra("KEY_PERMISSIONS", strArr);
            intent.putExtra("KEY_CALLBACK", aVar);
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (Throwable th) {
            Log.e("PermissionActivity", "start activity failed. ".concat(String.valueOf(th)), new Object[0]);
            try {
                Activity activityC = e.a().c();
                if (activityC == null || Build.VERSION.SDK_INT < 23) {
                    return;
                }
                activityC.requestPermissions(strArr, 1000);
                aVar.onRequestPermissionsResult(strArr, new int[1]);
            } catch (Throwable th2) {
                Log.e("PermissionActivity", "requestPermissions failed. ".concat(String.valueOf(th2)), new Object[0]);
            }
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            return;
        }
        try {
            Intent intent = getIntent();
            a.put(this, (a) intent.getSerializableExtra("KEY_CALLBACK"));
            String[] stringArrayExtra = intent.getStringArrayExtra("KEY_PERMISSIONS");
            if (LiteavSystemInfo.getSystemOSVersionInt() >= 23) {
                requestPermissions(stringArrayExtra, 1000);
            }
        } catch (Throwable th) {
            Log.e("PermissionActivity", "requestPermissions failed. ".concat(String.valueOf(th)), new Object[0]);
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        a aVar = a.get(this);
        if (aVar == null) {
            return;
        }
        aVar.onRequestPermissionsResult(strArr, iArr);
        finish();
    }
}
