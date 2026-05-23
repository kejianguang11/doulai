package com.igexin.base.a;

import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class c implements a {
    public String e;
    public String f;
    final List<String> a = new CopyOnWriteArrayList();
    public int b = 10;
    public long c = com.igexin.push.config.c.k;
    private final Pattern g = Pattern.compile("(.+)?[$][{](.+)?[}].+");
    private final AtomicBoolean h = new AtomicBoolean(true);
    long d = SystemClock.elapsedRealtime();

    public c() {
        b bVarA = b.a();
        if (bVarA.a.contains(this)) {
            return;
        }
        bVarA.a.add(this);
    }

    final String a(String str) {
        try {
            Matcher matcher = this.g.matcher(str);
            return matcher.find() ? str.replaceFirst("[$][{](.+)?[}]", new SimpleDateFormat(matcher.group(2)).format(new Date())) : str;
        } catch (Throwable th) {
            th.printStackTrace();
            return str;
        }
    }

    @Override // com.igexin.base.a.a
    public final void enableLog(boolean z) {
        this.h.set(z);
    }

    @Override // com.igexin.base.a.a
    public final boolean isEnabled() {
        return this.h.get();
    }

    @Override // com.igexin.base.a.a
    public final void log(String str) {
        if (isEnabled()) {
            this.a.add(str);
        }
    }
}
