package com.amap.api.location;

import android.content.Context;
import android.os.Handler;
import com.igexin.push.config.c;
import com.loc.fj;
import com.loc.o;

/* JADX INFO: loaded from: classes.dex */
public class UmidtokenInfo {
    private static AMapLocationClient d;
    static Handler a = new Handler();
    static String b = null;
    private static long e = c.k;
    static boolean c = true;

    static class a implements AMapLocationListener {
        a() {
        }

        @Override // com.amap.api.location.AMapLocationListener
        public final void onLocationChanged(AMapLocation aMapLocation) {
            try {
                if (UmidtokenInfo.d != null) {
                    UmidtokenInfo.a.removeCallbacksAndMessages(null);
                    UmidtokenInfo.d.onDestroy();
                }
            } catch (Throwable th) {
                fj.a(th, "UmidListener", "onLocationChanged");
            }
        }
    }

    public static String getUmidtoken() {
        return b;
    }

    public static void setLocAble(boolean z) {
        c = z;
    }

    public static synchronized void setUmidtoken(Context context, String str) {
        try {
            b = str;
            o.a(str);
            if (d == null && c) {
                a aVar = new a();
                d = new AMapLocationClient(context);
                AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
                aMapLocationClientOption.setOnceLocation(true);
                aMapLocationClientOption.setNeedAddress(false);
                d.setLocationOption(aMapLocationClientOption);
                d.setLocationListener(aVar);
                d.startLocation();
                a.postDelayed(new Runnable() { // from class: com.amap.api.location.UmidtokenInfo.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            if (UmidtokenInfo.d != null) {
                                UmidtokenInfo.d.onDestroy();
                            }
                        } catch (Throwable th) {
                            fj.a(th, "UmidListener", "postDelayed");
                        }
                    }
                }, c.k);
            }
        } catch (Throwable th) {
            fj.a(th, "UmidListener", "setUmidtoken");
        }
    }
}
