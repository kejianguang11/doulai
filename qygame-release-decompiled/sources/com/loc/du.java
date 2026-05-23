package com.loc;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class du extends dr implements Serializable {
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;

    public du() {
        this.j = 0;
        this.k = 0;
        this.l = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.n = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public du(boolean z) {
        super(z, true);
        this.j = 0;
        this.k = 0;
        this.l = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.m = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.n = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    @Override // com.loc.dr
    /* JADX INFO: renamed from: a */
    public final dr clone() {
        du duVar = new du(this.h);
        duVar.a(this);
        duVar.j = this.j;
        duVar.k = this.k;
        duVar.l = this.l;
        duVar.m = this.m;
        duVar.n = this.n;
        return duVar;
    }

    @Override // com.loc.dr
    public final String toString() {
        return "AmapCellLte{tac=" + this.j + ", ci=" + this.k + ", pci=" + this.l + ", earfcn=" + this.m + ", timingAdvance=" + this.n + ", mcc='" + this.a + "', mnc='" + this.b + "', signalStrength=" + this.c + ", asuLevel=" + this.d + ", lastUpdateSystemMills=" + this.e + ", lastUpdateUtcMills=" + this.f + ", age=" + this.g + ", main=" + this.h + ", newApi=" + this.i + '}';
    }
}
