package com.zx.a.I8b7;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class r0 implements m0 {
    @Override // com.zx.a.I8b7.m0
    public void a(int i, String str, String str2) {
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
