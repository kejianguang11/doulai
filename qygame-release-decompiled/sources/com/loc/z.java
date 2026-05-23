package com.loc;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class z {
    public static final String a = x.c("SU2hhcmVkUHJlZmVyZW5jZUFkaXU");
    private static z f;
    private List<String> b;
    private String c;
    private final Context d;
    private final Handler e;

    private static final class a extends Handler {
        private final WeakReference<z> a;

        a(Looper looper, z zVar) {
            super(looper);
            this.a = new WeakReference<>(zVar);
        }

        a(z zVar) {
            this.a = new WeakReference<>(zVar);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            z zVar = this.a.get();
            if (zVar == null || message == null || message.obj == null) {
                return;
            }
            zVar.a((String) message.obj, message.what);
        }
    }

    private z(Context context) {
        this.d = context.getApplicationContext();
        this.e = Looper.myLooper() == null ? new a(Looper.getMainLooper(), this) : new a(this);
    }

    public static z a(Context context) {
        if (f == null) {
            synchronized (z.class) {
                if (f == null) {
                    f = new z(context);
                }
            }
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.loc.z$1] */
    public synchronized void a(final String str, final int i) {
        ContentResolver contentResolver;
        String str2;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread() { // from class: com.loc.z.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    String strB = af.b(str);
                    if (TextUtils.isEmpty(strB)) {
                        return;
                    }
                    if ((i & 1) > 0) {
                        try {
                            if (Build.VERSION.SDK_INT < 23 || Settings.System.canWrite(z.this.d)) {
                                ContentResolver contentResolver2 = z.this.d.getContentResolver();
                                z zVar = z.this;
                                Settings.System.putString(contentResolver2, zVar.c, strB);
                            }
                        } catch (Exception unused) {
                        }
                    }
                    if ((i & 16) > 0) {
                        ab.a(z.this.d, z.this.c, strB);
                    }
                    if ((i & 256) > 0) {
                        SharedPreferences.Editor editorEdit = z.this.d.getSharedPreferences(z.a, 0).edit();
                        editorEdit.putString(z.this.c, strB);
                        if (Build.VERSION.SDK_INT >= 9) {
                            editorEdit.apply();
                        } else {
                            editorEdit.commit();
                        }
                    }
                }
            }.start();
            return;
        }
        String strB = af.b(str);
        if (!TextUtils.isEmpty(strB)) {
            if ((i & 1) > 0) {
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        contentResolver = this.d.getContentResolver();
                        str2 = this.c;
                    } else {
                        contentResolver = this.d.getContentResolver();
                        str2 = this.c;
                    }
                    Settings.System.putString(contentResolver, str2, strB);
                } catch (Exception unused) {
                }
            }
            if ((i & 16) > 0) {
                ab.a(this.d, this.c, strB);
            }
            if ((i & 256) > 0) {
                SharedPreferences.Editor editorEdit = this.d.getSharedPreferences(a, 0).edit();
                editorEdit.putString(this.c, strB);
                if (Build.VERSION.SDK_INT >= 9) {
                    editorEdit.apply();
                    return;
                }
                editorEdit.commit();
            }
        }
    }

    public final void a(String str) {
        this.c = str;
    }

    public final void b(String str) {
        if (this.b != null) {
            this.b.clear();
            this.b.add(str);
        }
        a(str, 273);
    }
}
