package com.zx.a.I8b7;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static Signature[] a(String str) {
        try {
            return m3.a(str, 64).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            v2.a(e);
            return null;
        }
    }
}
