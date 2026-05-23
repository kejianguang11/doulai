package com.loc;

import android.text.TextUtils;
import com.amap.api.location.AMapLocation;

/* JADX INFO: loaded from: classes.dex */
public final class en {
    eo a = null;
    long b = 0;
    long c = 0;
    private boolean h = true;
    int d = 0;
    long e = 0;
    AMapLocation f = null;
    long g = 0;

    private eo b(eo eoVar) {
        int i;
        if (fq.a(eoVar)) {
            if (!this.h || !fi.a(eoVar.getTime())) {
                i = this.d;
            } else if (eoVar.getLocationType() == 5 || eoVar.getLocationType() == 6) {
                i = 4;
            }
            eoVar.setLocationType(i);
        }
        return eoVar;
    }

    public final AMapLocation a(AMapLocation aMapLocation) {
        if (!fq.a(aMapLocation)) {
            return aMapLocation;
        }
        long jB = fq.b() - this.g;
        this.g = fq.b();
        if (jB > com.igexin.push.config.c.s) {
            return aMapLocation;
        }
        if (this.f == null) {
            this.f = aMapLocation;
            return aMapLocation;
        }
        if (1 != this.f.getLocationType() && !"gps".equalsIgnoreCase(this.f.getProvider())) {
            this.f = aMapLocation;
            return aMapLocation;
        }
        if (this.f.getAltitude() == aMapLocation.getAltitude() && this.f.getLongitude() == aMapLocation.getLongitude()) {
            this.f = aMapLocation;
            return aMapLocation;
        }
        long jAbs = Math.abs(aMapLocation.getTime() - this.f.getTime());
        if (com.igexin.push.config.c.k < jAbs) {
            this.f = aMapLocation;
            return aMapLocation;
        }
        if (fq.a(aMapLocation, this.f) > (((this.f.getSpeed() + aMapLocation.getSpeed()) * jAbs) / 2000.0f) + ((this.f.getAccuracy() + aMapLocation.getAccuracy()) * 2.0f) + 3000.0f) {
            return this.f;
        }
        this.f = aMapLocation;
        return aMapLocation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0133, code lost:
    
        if ((r9 - r20.c) > com.igexin.push.config.c.k) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final eo a(eo eoVar) {
        eo eoVarB = eoVar;
        if (fq.b() - this.e > com.igexin.push.config.c.k) {
            this.a = eoVarB;
            this.e = fq.b();
        } else {
            this.e = fq.b();
            if (!fq.a(this.a) || !fq.a(eoVar)) {
                this.b = fq.b();
                this.a = eoVarB;
            } else {
                if (eoVar.getTime() == this.a.getTime() && eoVar.getAccuracy() < 300.0f) {
                    return eoVarB;
                }
                if (!"gps".equals(eoVar.getProvider()) && eoVar.c() == this.a.c() && (eoVar.getBuildingId() == null || eoVar.getBuildingId().equals(this.a.getBuildingId()) || TextUtils.isEmpty(eoVar.getBuildingId()))) {
                    this.d = eoVar.getLocationType();
                    float fA = fq.a(eoVarB, this.a);
                    float accuracy = this.a.getAccuracy();
                    float accuracy2 = eoVar.getAccuracy();
                    float f = accuracy2 - accuracy;
                    long jB = fq.b();
                    long j = jB - this.b;
                    boolean z = accuracy <= 100.0f && accuracy2 > 299.0f;
                    boolean z2 = accuracy > 299.0f && accuracy2 > 299.0f;
                    if (z || z2) {
                        if (this.c == 0) {
                            this.c = jB;
                        }
                        eoVarB = b(this.a);
                        this.a = eoVarB;
                    } else {
                        if (accuracy2 >= 100.0f || accuracy <= 299.0f) {
                            if (accuracy2 <= 299.0f) {
                                this.c = 0L;
                            }
                            if (fA >= 10.0f || fA <= 0.1d || accuracy2 <= 5.0f) {
                                if (f < 300.0f || j >= com.igexin.push.config.c.k) {
                                }
                                this.a = eoVarB;
                            } else {
                                if (f < -300.0f && accuracy / accuracy2 >= 2.0f) {
                                    this.b = jB;
                                }
                                this.a = eoVarB;
                            }
                            eoVarB = b(this.a);
                            this.a = eoVarB;
                        }
                        this.b = jB;
                        this.a = eoVarB;
                        this.c = 0L;
                    }
                }
            }
        }
        return this.a;
    }

    public final void a() {
        this.a = null;
        this.b = 0L;
        this.c = 0L;
        this.f = null;
        this.g = 0L;
    }

    public final void a(boolean z) {
        this.h = z;
    }
}
