package com.loc;

import android.text.TextUtils;
import com.loc.bg;
import java.net.Proxy;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class bl {
    bg.a g;
    private String h;
    private boolean i;
    private boolean j;
    int d = 20000;
    int e = 20000;
    Proxy f = null;
    private boolean a = false;
    private int b = this.d;
    private boolean c = true;
    private a k = a.NORMAL;
    private b l = b.FIRST_NONDEGRADE;

    public enum a {
        NORMAL(0),
        INTERRUPT_IO(1),
        NEVER(2),
        FIX(3),
        SINGLE(4);

        private int f;

        a(int i) {
            this.f = i;
        }
    }

    public enum b {
        FIRST_NONDEGRADE(0),
        NEVER_GRADE(1),
        DEGRADE_BYERROR(2),
        DEGRADE_ONLY(3),
        FIX_NONDEGRADE(4),
        FIX_DEGRADE_BYERROR(5),
        FIX_DEGRADE_ONLY(6);

        private int h;

        b(int i2) {
            this.h = i2;
        }

        public final int a() {
            return this.h;
        }

        public final boolean b() {
            return this.h == FIRST_NONDEGRADE.h || this.h == NEVER_GRADE.h || this.h == FIX_NONDEGRADE.h;
        }

        public final boolean c() {
            return this.h == DEGRADE_BYERROR.h || this.h == DEGRADE_ONLY.h || this.h == FIX_DEGRADE_BYERROR.h || this.h == FIX_DEGRADE_ONLY.h;
        }

        public final boolean d() {
            return this.h == DEGRADE_BYERROR.h || this.h == FIX_DEGRADE_BYERROR.h;
        }

        public final boolean e() {
            return this.h == NEVER_GRADE.h;
        }
    }

    public enum c {
        HTTP(0),
        HTTPS(1);

        private int c;

        c(int i) {
            this.c = i;
        }
    }

    private static String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            if (map.containsKey("platinfo")) {
                return c(map.get("platinfo"));
            }
            return null;
        } catch (Throwable th) {
            ak.a(th, "ht", "pnfh");
            return null;
        }
    }

    private String b(String str) {
        byte[] bArrE = e();
        if (bArrE == null || bArrE.length == 0) {
            return str;
        }
        Map<String, String> mapD = d();
        if (bg.e != null) {
            if (mapD != null) {
                mapD.putAll(bg.e);
            } else {
                mapD = bg.e;
            }
        }
        if (mapD == null) {
            return str;
        }
        String strA = bj.a(mapD);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("?");
        stringBuffer.append(strA);
        return stringBuffer.toString();
    }

    private static String c(String str) {
        String str2 = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split(com.alipay.sdk.sys.a.b);
                if (strArrSplit.length > 1) {
                    String str3 = "";
                    String str4 = "";
                    int length = strArrSplit.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        String str5 = strArrSplit[i];
                        if (str5.contains("sdkversion")) {
                            str4 = str5;
                        }
                        if (str5.contains("product")) {
                            str3 = str5;
                            break;
                        }
                        i++;
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        String[] strArrSplit2 = str3.split("=");
                        if (strArrSplit2.length > 1) {
                            String strTrim = strArrSplit2[1].trim();
                            try {
                                if (TextUtils.isEmpty(str4) || !TextUtils.isEmpty(ag.a(strTrim))) {
                                    return strTrim;
                                }
                                String[] strArrSplit3 = str4.split("=");
                                if (strArrSplit3.length <= 1) {
                                    return strTrim;
                                }
                                ag.a(strTrim, strArrSplit3[1].trim());
                                return strTrim;
                            } catch (Throwable th) {
                                str2 = strTrim;
                                th = th;
                                ak.a(th, "ht", "pnfp");
                                return str2;
                            }
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return str2;
    }

    public abstract Map<String, String> a();

    public final void a(int i) {
        this.d = i;
    }

    public final void a(a aVar) {
        this.k = aVar;
    }

    public final void a(b bVar) {
        this.l = bVar;
    }

    public final void a(c cVar) {
        this.j = cVar == c.HTTPS;
    }

    public final void a(String str) {
        this.h = str;
    }

    public final void a(Proxy proxy) {
        this.f = proxy;
    }

    public final void a(boolean z) {
        this.i = z;
    }

    public abstract String b();

    public final void b(int i) {
        this.e = i;
    }

    public String c() {
        return b();
    }

    public final void c(int i) {
        this.b = i;
    }

    protected boolean c_() {
        return this.c;
    }

    public abstract Map<String, String> d();

    public byte[] e() {
        return null;
    }

    protected String g() {
        return "";
    }

    public String h() {
        return "";
    }

    final String l() {
        return b(b());
    }

    final String m() {
        return b(c());
    }

    public final int n() {
        return this.d;
    }

    public final Proxy o() {
        return this.f;
    }

    protected final a p() {
        return this.k;
    }

    protected final boolean q() {
        return this.a;
    }

    public final void r() {
        this.a = true;
    }

    protected final boolean s() {
        return this.j;
    }

    public final bg.a t() {
        return this.g;
    }

    protected final b u() {
        return this.l;
    }

    protected final int v() {
        return this.b;
    }

    public final void w() {
        this.c = false;
    }

    protected final String x() {
        return this.h;
    }

    protected final boolean y() {
        return this.i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0030, code lost:
    
        r3 = r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final String z() {
        String strH;
        try {
            strH = h();
            try {
            } catch (Throwable th) {
                th = th;
                ak.a(th, "ht", "pnfr");
            }
        } catch (Throwable th2) {
            th = th2;
            strH = "";
            ak.a(th, "ht", "pnfr");
            return strC;
        }
        String strC = TextUtils.isEmpty(strH) ? this.a ? c(((bh) this).j()) : a(a()) : strH;
        return strC;
    }
}
