package com.loc;

import android.content.Context;
import android.text.TextUtils;
import androidx.appcompat.widget.ActivityChooserView;

/* JADX INFO: loaded from: classes.dex */
public final class cd extends cg {
    private Context b;
    private boolean d;
    private int e;
    private int f;
    private String a = "iKey";
    private int g = 0;

    public cd(Context context, boolean z, int i, int i2, String str) {
        a(context, z, i, i2, str, 0);
    }

    public cd(Context context, boolean z, int i, int i2, String str, int i3) {
        a(context, z, i, i2, str, i3);
    }

    private void a(Context context, boolean z, int i, int i2, String str, int i3) {
        this.b = context;
        this.d = z;
        this.e = i;
        this.f = i2;
        this.a = str;
        this.g = i3;
    }

    @Override // com.loc.cg
    public final void a(int i) {
        if (o.o(this.b) == 1) {
            return;
        }
        String strA = x.a(System.currentTimeMillis(), "yyyyMMdd");
        String strA2 = al.a(this.b, this.a);
        if (!TextUtils.isEmpty(strA2)) {
            String[] strArrSplit = strA2.split("\\|");
            if (strArrSplit == null || strArrSplit.length < 2) {
                al.b(this.b, this.a);
            } else if (strA.equals(strArrSplit[0])) {
                i += Integer.parseInt(strArrSplit[1]);
            }
        }
        al.a(this.b, this.a, strA + "|" + i);
    }

    @Override // com.loc.cg
    protected final boolean a() {
        if (o.o(this.b) == 1) {
            return true;
        }
        if (!this.d) {
            return false;
        }
        String strA = al.a(this.b, this.a);
        if (TextUtils.isEmpty(strA)) {
            return true;
        }
        String[] strArrSplit = strA.split("\\|");
        if (strArrSplit != null && strArrSplit.length >= 2) {
            return !x.a(System.currentTimeMillis(), "yyyyMMdd").equals(strArrSplit[0]) || Integer.parseInt(strArrSplit[1]) < this.f;
        }
        al.b(this.b, this.a);
        return true;
    }

    @Override // com.loc.cg
    public final int b() {
        int iO = o.o(this.b);
        int i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        if (iO != 1 && this.e > 0) {
            i = this.e;
        } else if (this.g > 0 && this.g < Integer.MAX_VALUE) {
            i = this.g;
        }
        return this.c != null ? Math.max(i, this.c.b()) : i;
    }
}
