package com.mobile.auth.gatewayauth.manager;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private SystemManager a;
    private a b;
    private a c;
    private a d;
    private d e;

    public f(SystemManager systemManager, d dVar) {
        this.a = systemManager;
        this.e = dVar;
        this.b = new com.mobile.auth.q.d(this.a.e(), this.e);
        this.c = new com.mobile.auth.s.a(this.a.e(), this.e);
        this.d = new com.mobile.auth.r.a(this.a.e(), this.e);
    }

    public a a(String str) {
        byte b = -1;
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b = 1;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b = 2;
            }
            switch (b) {
                case 0:
                    return this.b;
                case 1:
                    return this.c;
                case 2:
                    return this.d;
                default:
                    return null;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public List<a> a() {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.b);
            arrayList.add(this.c);
            arrayList.add(this.d);
            return arrayList;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public boolean a(VendorSdkInfoManager vendorSdkInfoManager) {
        int i;
        try {
            VendorConfig vendorConfigA = vendorSdkInfoManager.a(1);
            VendorConfig vendorConfigA2 = vendorSdkInfoManager.a(2);
            VendorConfig vendorConfigA3 = vendorSdkInfoManager.a(3);
            if (vendorConfigA != null) {
                this.b.a(vendorConfigA.getVendorAccessId(), vendorConfigA.getVendorAccessSecret());
                i = 1;
            } else {
                i = 0;
            }
            if (vendorConfigA2 != null) {
                this.c.a(vendorConfigA2.getVendorAccessId(), vendorConfigA2.getVendorAccessSecret());
                i++;
            }
            if (vendorConfigA3 != null) {
                this.d.a(vendorConfigA3.getVendorAccessId(), vendorConfigA3.getVendorAccessSecret());
                i++;
            }
            return i == 3;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }
}
