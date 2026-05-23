package com.loc;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class dn {

    public static class a implements dl {
        private int a;
        private int b;
        private int c;

        a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        @Override // com.loc.dl
        public final long a() {
            return dn.a(this.a, this.b);
        }

        @Override // com.loc.dl
        public final int b() {
            return this.c;
        }
    }

    public static class b implements dl {
        private long a;
        private int b;

        b(long j, int i) {
            this.a = j;
            this.b = i;
        }

        @Override // com.loc.dl
        public final long a() {
            return this.a;
        }

        @Override // com.loc.dl
        public final int b() {
            return this.b;
        }
    }

    public static long a(int i, int i2) {
        return (((long) i2) & 4294967295L) | ((((long) i) & 4294967295L) << 32);
    }

    public static synchronized short a(long j) {
        return dm.a().a(j);
    }

    public static synchronized void a(List<dr> list) {
        a aVar;
        if (list != null) {
            if (!list.isEmpty()) {
                ArrayList arrayList = new ArrayList(list.size());
                for (dr drVar : list) {
                    if (drVar instanceof dt) {
                        dt dtVar = (dt) drVar;
                        aVar = new a(dtVar.j, dtVar.k, dtVar.c);
                    } else if (drVar instanceof du) {
                        du duVar = (du) drVar;
                        aVar = new a(duVar.j, duVar.k, duVar.c);
                    } else if (drVar instanceof dv) {
                        dv dvVar = (dv) drVar;
                        aVar = new a(dvVar.j, dvVar.k, dvVar.c);
                    } else if (drVar instanceof ds) {
                        ds dsVar = (ds) drVar;
                        aVar = new a(dsVar.k, dsVar.l, dsVar.c);
                    }
                    arrayList.add(aVar);
                }
                dm.a().a(arrayList);
            }
        }
    }

    public static synchronized short b(long j) {
        return dm.a().b(j);
    }

    public static synchronized void b(List<dy> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                ArrayList arrayList = new ArrayList(list.size());
                for (dy dyVar : list) {
                    arrayList.add(new b(dyVar.a, dyVar.c));
                }
                dm.a().b(arrayList);
            }
        }
    }
}
