package com.loc;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class dv extends dr implements Serializable {
    public int j;
    public int k;
    public int l;
    public int m;

    public dv() {
        this.j = 0;
        this.k = 0;
        this.l = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public dv(boolean z, boolean z2) {
        super(z, z2);
        this.j = 0;
        this.k = 0;
        this.l = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    @Override // com.loc.dr
    /* JADX INFO: renamed from: a */
    public final dr clone() {
        dv dvVar = new dv(this.h, this.i);
        dvVar.a(this);
        dvVar.j = this.j;
        dvVar.k = this.k;
        dvVar.l = this.l;
        dvVar.m = this.m;
        return dvVar;
    }

    @Override // com.loc.dr
    public final String toString() {
        return "AmapCellWcdma{lac=" + this.j + ", cid=" + this.k + ", psc=" + this.l + ", uarfcn=" + this.m + ", mcc='" + this.a + "', mnc='" + this.b + "', signalStrength=" + this.c + ", asuLevel=" + this.d + ", lastUpdateSystemMills=" + this.e + ", lastUpdateUtcMills=" + this.f + ", age=" + this.g + ", main=" + this.h + ", newApi=" + this.i + '}';
    }
}
