package com.loc;

import androidx.appcompat.widget.ActivityChooserView;

/* JADX INFO: loaded from: classes.dex */
public final class es {
    public int l;
    public boolean n;
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public long e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public int i = 0;
    public int j = 0;
    public int k = -113;
    public short m = 0;
    public int o = 32767;
    public int p = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    public int q = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    public boolean r = true;
    public int s = 99;
    public long t = 0;

    public es(int i, boolean z) {
        this.l = 0;
        this.n = false;
        this.l = i;
        this.n = z;
    }

    private String e() {
        int i = this.l;
        return this.l + "#" + this.a + "#" + this.b + "#0#" + a();
    }

    private String f() {
        return this.l + "#" + this.h + "#" + this.i + "#" + this.j;
    }

    public final long a() {
        return this.l == 5 ? this.e : this.d;
    }

    public final String b() {
        switch (this.l) {
            case 1:
            case 3:
            case 4:
            case 5:
                return e();
            case 2:
                return f();
            default:
                return null;
        }
    }

    public final String c() {
        String strB = b();
        if (strB == null || strB.length() <= 0) {
            return "";
        }
        return (this.r ? 1 : 0) + "#" + strB;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final es clone() {
        es esVar = new es(this.l, this.n);
        esVar.a = this.a;
        esVar.b = this.b;
        esVar.c = this.c;
        esVar.d = this.d;
        esVar.e = this.e;
        esVar.f = this.f;
        esVar.g = this.g;
        esVar.h = this.h;
        esVar.i = this.i;
        esVar.j = this.j;
        esVar.k = this.k;
        esVar.m = this.m;
        esVar.o = this.o;
        esVar.p = this.p;
        esVar.q = this.q;
        esVar.r = this.r;
        esVar.s = this.s;
        esVar.t = this.t;
        return esVar;
    }

    public final boolean equals(Object obj) {
        if (obj != null && (obj instanceof es)) {
            es esVar = (es) obj;
            switch (esVar.l) {
                case 1:
                    if (this.l == 1 && esVar.c == this.c && esVar.d == this.d && esVar.b == this.b) {
                        return true;
                    }
                    break;
                case 2:
                    return this.l == 2 && esVar.j == this.j && esVar.i == this.i && esVar.h == this.h;
                case 3:
                    return this.l == 3 && esVar.c == this.c && esVar.d == this.d && esVar.b == this.b;
                case 4:
                    return this.l == 4 && esVar.c == this.c && esVar.d == this.d && esVar.b == this.b;
                case 5:
                    return this.l == 5 && esVar.c == this.c && esVar.e == this.e && esVar.q == this.q;
                default:
                    return false;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode;
        int i;
        int iHashCode2 = String.valueOf(this.l).hashCode();
        if (this.l == 2) {
            iHashCode = String.valueOf(this.j).hashCode() + String.valueOf(this.i).hashCode();
            i = this.h;
        } else {
            iHashCode = String.valueOf(this.c).hashCode() + String.valueOf(this.d).hashCode();
            i = this.b;
        }
        return iHashCode2 + iHashCode + String.valueOf(i).hashCode();
    }
}
