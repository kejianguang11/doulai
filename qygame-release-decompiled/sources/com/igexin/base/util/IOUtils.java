package com.igexin.base.util;

import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class IOUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
