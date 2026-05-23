package com.loc;

import android.os.SystemClock;
import com.loc.cr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class cs {
    private static volatile cs g;
    private static Object h = new Object();
    private long c;
    private dx d;
    private dx f = new dx();
    private cr a = new cr();
    private ct b = new ct();
    private co e = new co();

    public static class a {
        public dx a;
        public List<dy> b;
        public long c;
        public long d;
        public boolean e;
        public long f;
        public byte g;
        public String h;
        public List<dr> i;
        public boolean j;
    }

    private cs() {
    }

    public static cs a() {
        if (g == null) {
            synchronized (h) {
                if (g == null) {
                    g = new cs();
                }
            }
        }
        return g;
    }

    public final cu a(a aVar) {
        cu cuVar = null;
        if (aVar == null) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.d == null || aVar.a.a(this.d) >= 10.0d) {
            cr.a aVarA = this.a.a(aVar.a, aVar.j, aVar.g, aVar.h, aVar.i);
            List<dy> listA = this.b.a(aVar.a, aVar.b, aVar.e, aVar.d, jCurrentTimeMillis);
            if (aVarA != null || listA != null) {
                dp.a(this.f, aVar.a, aVar.f, jCurrentTimeMillis);
                cuVar = new cu(0, this.e.a(this.f, aVarA, aVar.c, listA));
            }
            this.d = aVar.a;
            this.c = jElapsedRealtime;
        }
        return cuVar;
    }
}
