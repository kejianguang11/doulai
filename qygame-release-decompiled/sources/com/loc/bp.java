package com.loc;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class bp {
    private Context a;
    private w b;
    private String c;

    public bp(Context context, w wVar, String str) {
        this.a = context.getApplicationContext();
        this.b = wVar;
        this.c = str;
    }

    private static String a(Context context, w wVar, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\"sdkversion\":\"");
            sb.append(wVar.c());
            sb.append("\",\"product\":\"");
            sb.append(wVar.a());
            sb.append("\",\"nt\":\"");
            sb.append(o.d(context));
            sb.append("\",\"details\":");
            sb.append(str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return sb.toString();
    }

    final byte[] a() {
        return x.a(a(this.a, this.b, this.c));
    }
}
