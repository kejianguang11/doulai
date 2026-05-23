package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class q {
    private volatile b a = new b(0);
    private av b = new av("HttpsDecisionUtil");

    private static class a {
        static q a = new q();
    }

    private static class b {
        protected boolean a;
        private int b;
        private final boolean c;
        private boolean d;

        private b() {
            this.b = 0;
            this.a = true;
            this.c = true;
            this.d = false;
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void a(Context context) {
            if (context != null && this.b <= 0 && Build.VERSION.SDK_INT >= 4) {
                this.b = context.getApplicationContext().getApplicationInfo().targetSdkVersion;
            }
        }

        public final void a(boolean z) {
            this.a = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0025  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0030 A[RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean a() {
            boolean z;
            if (!this.d) {
                boolean z2 = Build.VERSION.SDK_INT >= 28;
                if (this.a) {
                    if (!((this.b <= 0 ? 28 : this.b) >= 28)) {
                        z = false;
                    }
                    if (z2) {
                        if (!z2 && z) {
                        }
                    }
                } else {
                    z = true;
                    if (!z2 && z) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public static q a() {
        return a.a;
    }

    public static String a(String str) {
        if (!TextUtils.isEmpty(str) && !str.startsWith(com.alipay.sdk.cons.b.a)) {
            try {
                Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
                builderBuildUpon.scheme(com.alipay.sdk.cons.b.a);
                return builderBuildUpon.build().toString();
            } catch (Throwable unused) {
            }
        }
        return str;
    }

    public static void b(Context context) {
        b(context, true);
    }

    private static void b(Context context, boolean z) {
        SharedPreferences.Editor editorA = av.a(context, "open_common");
        av.a(editorA, "a3", z);
        av.a(editorA);
    }

    private static boolean c() {
        return Build.VERSION.SDK_INT == 19;
    }

    public final void a(Context context) {
        if (this.a == null) {
            this.a = new b((byte) 0);
        }
        this.a.a(av.a(context, "open_common", "a3", true));
        this.a.a(context);
        y.a(context).a();
    }

    final void a(Context context, boolean z) {
        if (this.a == null) {
            this.a = new b((byte) 0);
        }
        b(context, z);
        this.a.a(z);
    }

    public final boolean a(boolean z) {
        if (c()) {
            return false;
        }
        return z || b();
    }

    public final boolean b() {
        if (this.a == null) {
            this.a = new b((byte) 0);
        }
        return this.a.a();
    }
}
