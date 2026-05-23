package com.igexin.push.a;

import android.app.Activity;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a a;

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public static b a(Activity activity) {
        String stringExtra = activity.getIntent().getStringExtra("action");
        if (TextUtils.isEmpty(stringExtra)) {
            activity.finish();
            return null;
        }
        byte b = -1;
        int iHashCode = stringExtra.hashCode();
        if (iHashCode != 106852524) {
            if (iHashCode == 1845633938 && stringExtra.equals("com.igexin.action.notification.click")) {
                b = 0;
            }
        } else if (stringExtra.equals(com.igexin.push.core.b.r)) {
            b = 1;
        }
        switch (b) {
            case 0:
                break;
            case 1:
                break;
            default:
                activity.finish();
                break;
        }
        return null;
    }
}
