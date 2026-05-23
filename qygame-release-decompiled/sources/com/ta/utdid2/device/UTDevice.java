package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.a.a.f;

/* JADX INFO: loaded from: classes.dex */
public class UTDevice {
    private static String a(Context context) {
        a aVarB = b.b(context);
        return (aVarB == null || f.isEmpty(aVarB.getUtdid())) ? "ffffffffffffffffffffffff" : aVarB.getUtdid();
    }

    private static String b(Context context) {
        String strD = c.a(context).d();
        return (strD == null || f.isEmpty(strD)) ? "ffffffffffffffffffffffff" : strD;
    }

    @Deprecated
    public static String getUtdid(Context context) {
        return a(context);
    }

    @Deprecated
    public static String getUtdidForUpdate(Context context) {
        return b(context);
    }
}
