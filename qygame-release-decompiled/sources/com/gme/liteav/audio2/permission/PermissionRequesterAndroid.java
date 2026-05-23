package com.gme.liteav.audio2.permission;

import android.os.Process;
import com.gme.liteav.audio2.permission.PermissionActivity;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::audio")
public class PermissionRequesterAndroid extends PermissionActivity.a {
    private static final String TAG = "PermissionRequesterAndroid";
    private static final List<String> mRequestedPermissions = new ArrayList();
    private final long mNativePermissionRequesterAndroid;

    public PermissionRequesterAndroid(long j) {
        this.mNativePermissionRequesterAndroid = j;
    }

    private void handleRequestPermissionsResult(String[] strArr) {
        for (String str : strArr) {
            nativeNotifyPermissionsResultFromJava(this.mNativePermissionRequesterAndroid, hasPermission(str));
        }
    }

    private boolean hasPermission(String str) {
        if (str == null || str.isEmpty()) {
            Log.w(TAG, "check permission is null.", new Object[0]);
            return true;
        }
        try {
            if (LiteavSystemInfo.getSystemOSVersionInt() >= 23) {
                return ContextUtils.getApplicationContext().checkPermission(str, Process.myPid(), Process.myUid()) == 0;
            }
            return true;
        } catch (Throwable th) {
            Log.e(TAG, "check permission exception, " + th.getMessage(), new Object[0]);
            return true;
        }
    }

    private static native void nativeNotifyPermissionsResultFromJava(long j, boolean z);

    @Override // com.gme.liteav.audio2.permission.PermissionActivity.a
    public void onRequestPermissionsResult(String[] strArr, int[] iArr) {
        handleRequestPermissionsResult(strArr);
        for (String str : strArr) {
            if (!mRequestedPermissions.contains(str)) {
                mRequestedPermissions.add(str);
            }
        }
    }

    public void requestPermission(String str) {
        if (str == null || str.isEmpty()) {
            Log.w(TAG, "request permission is null.", new Object[0]);
            return;
        }
        if (LiteavSystemInfo.getSystemOSVersionInt() < 23) {
            handleRequestPermissionsResult(new String[]{str});
        } else if (mRequestedPermissions.contains(str)) {
            handleRequestPermissionsResult((String[]) mRequestedPermissions.toArray(new String[0]));
        } else {
            PermissionActivity.a(ContextUtils.getApplicationContext(), new String[]{str}, this);
        }
    }
}
