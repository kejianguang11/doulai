package com.getui.gtc.base.log.b;

import android.util.Log;
import com.getui.gtc.base.log.ILogDestination;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ILogDestination {
    @Override // com.getui.gtc.base.log.ILogDestination
    public final void log(int i, String str, String str2) {
        switch (i) {
            case 1:
                Log.v(str, str2);
                break;
            case 2:
                Log.d(str, str2);
                break;
            case 3:
                Log.i(str, str2);
                break;
            case 4:
                Log.w(str, str2);
                break;
            case 5:
                Log.e(str, str2);
                break;
        }
    }
}
