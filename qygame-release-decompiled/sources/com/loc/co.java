package com.loc;

import android.os.SystemClock;
import com.loc.cr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class co extends cn {
    public co() {
        super(2048);
    }

    private int a(long j, List<dy> list) {
        b(list);
        int size = list.size();
        if (size <= 0) {
            return -1;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            dy dyVar = list.get(i);
            iArr[i] = dk.a(this.a, dyVar.a == j && dyVar.a != -1, dyVar.a, (short) dyVar.c, this.a.a(dyVar.b), dyVar.g, (short) dyVar.d);
        }
        return dj.a(this.a, dj.a(this.a, iArr));
    }

    private int a(cr.a aVar) {
        int i;
        int iA;
        cp cpVar;
        int i2;
        int i3;
        int i4;
        int iA2;
        byte b;
        byte b2;
        a(aVar.f);
        int size = aVar.f.size();
        int[] iArr = new int[size];
        for (int i5 = 0; i5 < size; i5++) {
            dr drVar = aVar.f.get(i5);
            if (drVar instanceof dt) {
                dt dtVar = (dt) drVar;
                iA2 = !dtVar.i ? de.a(this.a, dtVar.j, dtVar.k, dtVar.c, dtVar.l) : de.a(this.a, dtVar.b(), dtVar.c(), dtVar.j, dtVar.k, dtVar.c, dtVar.m, dtVar.n, dtVar.d, dtVar.l);
                i4 = -1;
                b = 1;
            } else {
                if (drVar instanceof du) {
                    b2 = 3;
                    du duVar = (du) drVar;
                    iA2 = df.a(this.a, duVar.b(), duVar.c(), duVar.j, duVar.k, duVar.l, duVar.c, duVar.m, duVar.d);
                } else if (drVar instanceof ds) {
                    ds dsVar = (ds) drVar;
                    iA2 = !dsVar.i ? cy.a(this.a, dsVar.j, dsVar.k, dsVar.l, dsVar.m, dsVar.n, dsVar.c) : cy.a(this.a, dsVar.j, dsVar.k, dsVar.l, dsVar.m, dsVar.n, dsVar.c, dsVar.d);
                    b = 2;
                    i4 = -1;
                } else if (drVar instanceof dv) {
                    b2 = 4;
                    dv dvVar = (dv) drVar;
                    iA2 = di.a(this.a, dvVar.b(), dvVar.c(), dvVar.j, dvVar.k, dvVar.l, dvVar.c, dvVar.m, dvVar.d);
                } else {
                    i4 = -1;
                    iA2 = -1;
                    b = 0;
                }
                b = b2;
                i4 = -1;
            }
            if (iA2 == i4) {
                return i4;
            }
            iArr[i5] = db.a(this.a, drVar.h ? (byte) 1 : (byte) 0, drVar.i ? (byte) 1 : (byte) 0, (short) drVar.g, b, iA2);
        }
        int iA3 = this.a.a(aVar.b);
        int iA4 = cz.a(this.a, iArr);
        int size2 = aVar.g.size();
        int[] iArr2 = new int[size2];
        for (int i6 = 0; i6 < size2; i6++) {
            dr drVar2 = aVar.g.get(i6);
            long jElapsedRealtime = (SystemClock.elapsedRealtime() - drVar2.e) / 1000;
            if (jElapsedRealtime > 32767 || jElapsedRealtime < 0) {
                jElapsedRealtime = 32767;
            }
            if (drVar2 instanceof dt) {
                dt dtVar2 = (dt) drVar2;
                cpVar = this.a;
                i2 = dtVar2.j;
                i3 = dtVar2.k;
            } else if (drVar2 instanceof du) {
                du duVar2 = (du) drVar2;
                cpVar = this.a;
                i2 = duVar2.j;
                i3 = duVar2.k;
            } else {
                if (drVar2 instanceof ds) {
                    ds dsVar2 = (ds) drVar2;
                    iA = dg.a(this.a, dsVar2.j, dsVar2.k, dsVar2.l, (short) jElapsedRealtime);
                    i = 2;
                } else if (drVar2 instanceof dv) {
                    dv dvVar2 = (dv) drVar2;
                    cpVar = this.a;
                    i2 = dvVar2.j;
                    i3 = dvVar2.k;
                } else {
                    i = 0;
                    iA = 0;
                }
                iArr2[i6] = da.a(this.a, (byte) i, iA);
            }
            iA = dh.a(cpVar, i2, i3, (short) jElapsedRealtime);
            i = 1;
            iArr2[i6] = da.a(this.a, (byte) i, iA);
        }
        return cz.a(this.a, iA3, aVar.a, iA4, cz.b(this.a, iArr2));
    }

    private int a(dx dxVar) {
        return dd.a(this.a, dxVar.c, dxVar.k, (int) (dxVar.e * 1000000.0d), (int) (dxVar.d * 1000000.0d), (int) dxVar.f, (int) dxVar.i, (int) dxVar.g, (short) dxVar.h, dxVar.l);
    }

    private static void a(List<dr> list) {
        int i;
        int i2;
        if (list == null || list.size() == 0) {
            return;
        }
        for (dr drVar : list) {
            if (drVar instanceof dt) {
                dt dtVar = (dt) drVar;
                i = dtVar.j;
                i2 = dtVar.k;
            } else if (drVar instanceof du) {
                du duVar = (du) drVar;
                i = duVar.j;
                i2 = duVar.k;
            } else if (drVar instanceof dv) {
                dv dvVar = (dv) drVar;
                i = dvVar.j;
                i2 = dvVar.k;
            } else if (drVar instanceof ds) {
                ds dsVar = (ds) drVar;
                i = dsVar.k;
                i2 = dsVar.l;
            }
            drVar.g = dn.a(dn.a(i, i2));
        }
    }

    private static void b(List<dy> list) {
        for (dy dyVar : list) {
            dyVar.g = dn.b(dyVar.a);
        }
    }

    public final byte[] a(dx dxVar, cr.a aVar, long j, List<dy> list) {
        super.a();
        try {
            int iA = a(dxVar);
            int iA2 = -1;
            int iA3 = (aVar == null || aVar.f == null || aVar.f.size() <= 0) ? -1 : a(aVar);
            if (list != null && list.size() > 0) {
                iA2 = a(j, list);
            }
            cw.a(this.a);
            cw.a(this.a, iA);
            if (iA3 > 0) {
                cw.c(this.a, iA3);
            }
            if (iA2 > 0) {
                cw.b(this.a, iA2);
            }
            this.a.c(cw.b(this.a));
            return this.a.c();
        } catch (Throwable th) {
            eb.a(th);
            return null;
        }
    }
}
