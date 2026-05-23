package com.loc;

import android.os.SystemClock;
import android.text.TextUtils;
import com.loc.bl;
import com.mobile.auth.gatewayauth.Constant;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class bg {
    public static int a = 0;
    public static String b = "";
    public static HashMap<String, String> c;
    public static HashMap<String, String> d;
    public static HashMap<String, String> e;
    private static bg f;

    public interface a {
        URLConnection a();
    }

    public bg() {
        m.e();
    }

    private static int a(bl blVar, long j) {
        try {
            d(blVar);
            long jElapsedRealtime = 0;
            if (j != 0) {
                jElapsedRealtime = SystemClock.elapsedRealtime() - j;
            }
            int iN = blVar.n();
            if (blVar.p() != bl.a.FIX && blVar.p() != bl.a.SINGLE) {
                long j2 = iN;
                if (jElapsedRealtime < j2) {
                    long j3 = j2 - jElapsedRealtime;
                    if (j3 >= 1000) {
                        return (int) j3;
                    }
                }
                return Math.min(1000, blVar.n());
            }
            return iN;
        } catch (Throwable unused) {
            return Constant.DEFAULT_TIMEOUT;
        }
    }

    public static bg a() {
        if (f == null) {
            f = new bg();
        }
        return f;
    }

    public static bm a(bl blVar) throws k {
        return a(blVar, blVar.s());
    }

    private static bm a(bl blVar, bl.b bVar, int i) throws k {
        try {
            d(blVar);
            blVar.a(bVar);
            blVar.c(i);
            return new bj().a(blVar);
        } catch (k e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new k("未知的错误");
        }
    }

    @Deprecated
    private static bm a(bl blVar, boolean z) throws k {
        long jElapsedRealtime;
        k e2;
        d(blVar);
        blVar.a(z ? bl.c.HTTPS : bl.c.HTTP);
        bm bmVarA = null;
        boolean z2 = false;
        if (b(blVar)) {
            boolean zC = c(blVar);
            try {
                jElapsedRealtime = SystemClock.elapsedRealtime();
            } catch (k e3) {
                jElapsedRealtime = 0;
                e2 = e3;
            }
            try {
                bmVarA = a(blVar, b(blVar, zC), d(blVar, zC));
            } catch (k e4) {
                e2 = e4;
                z2 = true;
                if ((e2.f() == 21 && blVar.p() == bl.a.INTERRUPT_IO) || !zC) {
                    throw e2;
                }
            }
        } else {
            jElapsedRealtime = 0;
        }
        if (bmVarA != null && bmVarA.a != null && bmVarA.a.length > 0) {
            return bmVarA;
        }
        try {
            return a(blVar, c(blVar, z2), a(blVar, jElapsedRealtime));
        } catch (k e5) {
            throw e5;
        }
    }

    private static bl.b b(bl blVar, boolean z) {
        if (blVar.p() == bl.a.FIX) {
            return bl.b.FIX_NONDEGRADE;
        }
        if (blVar.p() != bl.a.SINGLE && z) {
            return bl.b.FIRST_NONDEGRADE;
        }
        return bl.b.NEVER_GRADE;
    }

    private static boolean b(bl blVar) throws k {
        d(blVar);
        try {
            String strC = blVar.c();
            if (TextUtils.isEmpty(strC)) {
                return false;
            }
            String host = new URL(strC).getHost();
            if (!TextUtils.isEmpty(blVar.g())) {
                host = blVar.g();
            }
            return m.d(host);
        } catch (Throwable unused) {
            return true;
        }
    }

    private static bl.b c(bl blVar, boolean z) {
        return blVar.p() == bl.a.FIX ? z ? bl.b.FIX_DEGRADE_BYERROR : bl.b.FIX_DEGRADE_ONLY : z ? bl.b.DEGRADE_BYERROR : bl.b.DEGRADE_ONLY;
    }

    private static boolean c(bl blVar) throws k {
        d(blVar);
        try {
            if (!b(blVar)) {
                return true;
            }
            if (blVar.b().equals(blVar.c()) || blVar.p() == bl.a.SINGLE) {
                return false;
            }
            if (!m.h) {
                return false;
            }
        } catch (Throwable unused) {
        }
        return true;
    }

    private static int d(bl blVar, boolean z) {
        try {
            d(blVar);
            int iN = blVar.n();
            int i = m.e;
            if (blVar.p() != bl.a.FIX) {
                if (blVar.p() != bl.a.SINGLE && iN >= i && z) {
                    return i;
                }
            }
            return iN;
        } catch (Throwable unused) {
            return Constant.DEFAULT_TIMEOUT;
        }
    }

    private static void d(bl blVar) throws k {
        if (blVar == null) {
            throw new k("requeust is null");
        }
        if (blVar.b() == null || "".equals(blVar.b())) {
            throw new k("request url is empty");
        }
    }
}
