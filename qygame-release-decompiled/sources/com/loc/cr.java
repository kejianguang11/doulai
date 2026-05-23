package com.loc;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class cr {
    private dr a;
    private dr b;
    private dx c;
    private a d = new a();
    private final List<dr> e = new ArrayList(3);

    public static class a {
        public byte a;
        public String b;
        public dr c;
        public dr d;
        public dr e;
        public List<dr> f = new ArrayList();
        public List<dr> g = new ArrayList();

        public static boolean a(dr drVar, dr drVar2) {
            if (drVar == null || drVar2 == null) {
                return (drVar == null) == (drVar2 == null);
            }
            if ((drVar instanceof dt) && (drVar2 instanceof dt)) {
                dt dtVar = (dt) drVar;
                dt dtVar2 = (dt) drVar2;
                return dtVar.j == dtVar2.j && dtVar.k == dtVar2.k;
            }
            if ((drVar instanceof ds) && (drVar2 instanceof ds)) {
                ds dsVar = (ds) drVar;
                ds dsVar2 = (ds) drVar2;
                return dsVar.l == dsVar2.l && dsVar.k == dsVar2.k && dsVar.j == dsVar2.j;
            }
            if ((drVar instanceof du) && (drVar2 instanceof du)) {
                du duVar = (du) drVar;
                du duVar2 = (du) drVar2;
                return duVar.j == duVar2.j && duVar.k == duVar2.k;
            }
            if ((drVar instanceof dv) && (drVar2 instanceof dv)) {
                dv dvVar = (dv) drVar;
                dv dvVar2 = (dv) drVar2;
                if (dvVar.j == dvVar2.j && dvVar.k == dvVar2.k) {
                    return true;
                }
            }
            return false;
        }

        public final void a() {
            this.a = (byte) 0;
            this.b = "";
            this.c = null;
            this.d = null;
            this.e = null;
            this.f.clear();
            this.g.clear();
        }

        public final void a(byte b, String str, List<dr> list) {
            a();
            this.a = b;
            this.b = str;
            if (list != null) {
                this.f.addAll(list);
                for (dr drVar : this.f) {
                    if (!drVar.i && drVar.h) {
                        this.d = drVar;
                    } else if (drVar.i && drVar.h) {
                        this.e = drVar;
                    }
                }
            }
            this.c = this.d == null ? this.e : this.d;
        }

        public final String toString() {
            return "CellInfo{radio=" + ((int) this.a) + ", operator='" + this.b + "', mainCell=" + this.c + ", mainOldInterCell=" + this.d + ", mainNewInterCell=" + this.e + ", cells=" + this.f + ", historyMainCellList=" + this.g + '}';
        }
    }

    private void a(a aVar) {
        synchronized (this.e) {
            for (dr drVar : aVar.f) {
                if (drVar != null && drVar.h) {
                    dr drVarClone = drVar.clone();
                    drVarClone.e = SystemClock.elapsedRealtime();
                    a(drVarClone);
                }
            }
            this.d.g.clear();
            this.d.g.addAll(this.e);
        }
    }

    private void a(dr drVar) {
        if (drVar == null) {
            return;
        }
        int size = this.e.size();
        if (size != 0) {
            long jMin = Long.MAX_VALUE;
            int i = 0;
            int i2 = -1;
            int i3 = -1;
            while (true) {
                if (i >= size) {
                    i2 = i3;
                    break;
                }
                dr drVar2 = this.e.get(i);
                if (!drVar.equals(drVar2)) {
                    jMin = Math.min(jMin, drVar2.e);
                    if (jMin == drVar2.e) {
                        i3 = i;
                    }
                    i++;
                } else if (drVar.c != drVar2.c) {
                    drVar2.e = drVar.c;
                    drVar2.c = drVar.c;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (size >= 3) {
                if (drVar.e <= jMin || i2 >= size) {
                    return;
                }
                this.e.remove(i2);
                this.e.add(drVar);
                return;
            }
        }
        this.e.add(drVar);
    }

    private boolean a(dx dxVar) {
        return dxVar.a(this.c) > ((double) ((dxVar.g > 10.0f ? 1 : (dxVar.g == 10.0f ? 0 : -1)) > 0 ? 2000.0f : (dxVar.g > 2.0f ? 1 : (dxVar.g == 2.0f ? 0 : -1)) > 0 ? 500.0f : 100.0f));
    }

    final a a(dx dxVar, boolean z, byte b, String str, List<dr> list) {
        if (z) {
            this.d.a();
            return null;
        }
        this.d.a(b, str, list);
        if (this.d.c == null) {
            return null;
        }
        if (!(this.c == null || a(dxVar) || !a.a(this.d.d, this.a) || !a.a(this.d.e, this.b))) {
            return null;
        }
        this.a = this.d.d;
        this.b = this.d.e;
        this.c = dxVar;
        dn.a(this.d.f);
        a(this.d);
        return this.d;
    }
}
