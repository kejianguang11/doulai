package com.ali.security;

import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963 {
    private static AtomicBoolean isLoadLibrary = new AtomicBoolean(false);

    public static void SLoad(String str) {
        if (isLoadLibrary.compareAndSet(false, true)) {
            System.loadLibrary(str);
            isLoadLibrary.set(false);
        }
    }
}
