package com.nirvana.tools.core;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class ComponentSdkCore {
    protected static Context sApplicationContext;

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static void register(Context context) {
        sApplicationContext = context;
    }
}
