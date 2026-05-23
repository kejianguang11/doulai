package com.alipay.security.mobile.module.b;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class c implements FileFilter {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return Pattern.matches("cpu[0-9]+", file.getName());
    }
}
