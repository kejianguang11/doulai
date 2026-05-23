package com.mobile.auth.gatewayauth.manager;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public static long a(String str) {
        byte b = -1;
        try {
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b = 2;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b = 1;
            }
            switch (b) {
                case 0:
                    return 20000L;
                case 1:
                case 2:
                    return com.igexin.push.config.c.A;
                default:
                    return 0L;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }
}
