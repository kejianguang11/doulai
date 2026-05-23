package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    static fa b;
    static ar e;
    static long g;
    String a = null;
    fa c = null;
    fa d = null;
    long f = 0;
    boolean h = false;
    private Context i;

    public j(Context context) {
        this.i = context.getApplicationContext();
    }

    private void e() {
        if (b == null || fq.b() - g > com.igexin.push.config.c.g) {
            fa faVarF = f();
            g = fq.b();
            if (faVarF == null || !fq.a(faVarF.a())) {
                return;
            }
            b = faVarF;
        }
    }

    private fa f() {
        fa faVar;
        byte[] bArrB;
        byte[] bArrB2;
        String str = null;
        if (this.i == null) {
            return null;
        }
        a();
        try {
        } catch (Throwable th) {
            th = th;
            faVar = null;
        }
        if (e == null) {
            return null;
        }
        List listA = e.a("_id=1", fa.class);
        if (listA == null || listA.size() <= 0) {
            faVar = null;
        } else {
            faVar = (fa) listA.get(0);
            try {
                byte[] bArrB3 = p.b(faVar.c());
                String str2 = (bArrB3 == null || bArrB3.length <= 0 || (bArrB2 = ey.b(bArrB3, this.a)) == null || bArrB2.length <= 0) ? null : new String(bArrB2, com.alipay.sdk.sys.a.m);
                byte[] bArrB4 = p.b(faVar.b());
                if (bArrB4 != null && bArrB4.length > 0 && (bArrB = ey.b(bArrB4, this.a)) != null && bArrB.length > 0) {
                    str = new String(bArrB, com.alipay.sdk.sys.a.m);
                }
                faVar.a(str);
                str = str2;
            } catch (Throwable th2) {
                th = th2;
                fj.a(th, "LastLocationManager", "readLastFix");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            AMapLocation aMapLocation = new AMapLocation("");
            fj.a(aMapLocation, new JSONObject(str));
            if (fq.b(aMapLocation)) {
                faVar.a(aMapLocation);
            }
        }
        return faVar;
        fj.a(th, "LastLocationManager", "readLastFix");
        return faVar;
    }

    public final AMapLocation a(AMapLocation aMapLocation, String str, long j) {
        if (aMapLocation == null || aMapLocation.getErrorCode() == 0 || aMapLocation.getLocationType() == 1 || aMapLocation.getErrorCode() == 7) {
            return aMapLocation;
        }
        try {
            e();
        } catch (Throwable th) {
            th = th;
        }
        if (b != null && b.a() != null) {
            boolean zA = false;
            if (TextUtils.isEmpty(str)) {
                long jB = fq.b() - b.d();
                if (jB >= 0 && jB <= j) {
                    zA = true;
                }
                aMapLocation.setTrustedLevel(3);
            } else {
                zA = fq.a(b.b(), str);
                aMapLocation.setTrustedLevel(2);
            }
            if (zA) {
                AMapLocation aMapLocationA = b.a();
                try {
                    aMapLocationA.setLocationType(9);
                    aMapLocationA.setFixLastLocation(true);
                    aMapLocationA.setLocationDetail(aMapLocation.getLocationDetail());
                    return aMapLocationA;
                } catch (Throwable th2) {
                    aMapLocation = aMapLocationA;
                    th = th2;
                    fj.a(th, "LastLocationManager", "fixLastLocation");
                    return aMapLocation;
                }
            }
            return aMapLocation;
        }
        return aMapLocation;
    }

    public final void a() {
        if (this.h) {
            return;
        }
        try {
            if (this.a == null) {
                this.a = ey.a("MD5", o.v(this.i));
            }
            if (e == null) {
                e = new ar(this.i, ar.a((Class<? extends aq>) fb.class));
            }
        } catch (Throwable th) {
            fj.a(th, "LastLocationManager", "<init>:DBOperation");
        }
        this.h = true;
    }

    public final boolean a(AMapLocation aMapLocation, String str) {
        if (this.i != null && aMapLocation != null && fq.a(aMapLocation) && aMapLocation.getLocationType() != 2 && !aMapLocation.isMock() && !aMapLocation.isFixLastLocation()) {
            fa faVar = new fa();
            faVar.a(aMapLocation);
            if (aMapLocation.getLocationType() == 1) {
                faVar.a((String) null);
            } else {
                faVar.a(str);
            }
            try {
                b = faVar;
                g = fq.b();
                this.c = faVar;
                if (this.d != null && fq.a(this.d.a(), faVar.a()) <= 500.0f) {
                    return false;
                }
                if (fq.b() - this.f > com.igexin.push.config.c.k) {
                    return true;
                }
            } catch (Throwable th) {
                fj.a(th, "LastLocationManager", "setLastFix");
            }
        }
        return false;
    }

    public final AMapLocation b() {
        e();
        if (b != null && fq.a(b.a())) {
            return b.a();
        }
        return null;
    }

    public final void c() {
        try {
            d();
            this.f = 0L;
            this.h = false;
            this.c = null;
            this.d = null;
        } catch (Throwable th) {
            fj.a(th, "LastLocationManager", "destroy");
        }
    }

    public final void d() {
        String strB;
        try {
            a();
            if (this.c != null && fq.a(this.c.a()) && e != null && this.c != this.d && this.c.d() == 0) {
                String str = this.c.a().toStr();
                String strB2 = this.c.b();
                this.d = this.c;
                String strB3 = null;
                if (TextUtils.isEmpty(str)) {
                    strB = null;
                } else {
                    strB = p.b(ey.a(str.getBytes(com.alipay.sdk.sys.a.m), this.a));
                    if (!TextUtils.isEmpty(strB2)) {
                        strB3 = p.b(ey.a(strB2.getBytes(com.alipay.sdk.sys.a.m), this.a));
                    }
                }
                if (TextUtils.isEmpty(strB)) {
                    return;
                }
                fa faVar = new fa();
                faVar.b(strB);
                faVar.a(fq.b());
                faVar.a(strB3);
                e.a(faVar, "_id=1");
                this.f = fq.b();
                if (b != null) {
                    b.a(fq.b());
                }
            }
        } catch (Throwable th) {
            fj.a(th, "LastLocationManager", "saveLastFix");
        }
    }
}
