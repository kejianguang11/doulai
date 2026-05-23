package com.loc;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.igexin.sdk.PushConsts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public final class a {
    private static boolean A = false;
    Context b;
    fo a = null;
    PendingIntent c = null;
    String d = null;
    GeoFenceListener e = null;
    private Object z = new Object();
    volatile int f = 1;
    ArrayList<GeoFence> g = new ArrayList<>();
    c h = null;
    Object i = new Object();
    Object j = new Object();
    HandlerC0038a k = null;
    b l = null;
    volatile boolean m = false;
    volatile boolean n = false;
    volatile boolean o = false;
    com.loc.b p = null;
    com.loc.c q = null;
    AMapLocationClient r = null;
    volatile AMapLocation s = null;
    long t = 0;
    AMapLocationClientOption u = null;
    int v = 0;
    AMapLocationListener w = new AMapLocationListener() { // from class: com.loc.a.1
        /* JADX WARN: Removed duplicated region for block: B:17:0x005d A[Catch: Throwable -> 0x009f, TryCatch #0 {Throwable -> 0x009f, blocks: (B:2:0x0000, B:5:0x0007, B:8:0x000e, B:10:0x001b, B:12:0x0025, B:17:0x005d, B:19:0x0068, B:21:0x0073, B:22:0x0085, B:24:0x0093, B:13:0x0035), top: B:27:0x0000 }] */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0068 A[Catch: Throwable -> 0x009f, TryCatch #0 {Throwable -> 0x009f, blocks: (B:2:0x0000, B:5:0x0007, B:8:0x000e, B:10:0x001b, B:12:0x0025, B:17:0x005d, B:19:0x0068, B:21:0x0073, B:22:0x0085, B:24:0x0093, B:13:0x0035), top: B:27:0x0000 }] */
        @Override // com.amap.api.location.AMapLocationListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onLocationChanged(AMapLocation aMapLocation) {
            int errorCode;
            boolean z;
            try {
                if (a.this.y || !a.this.o) {
                    return;
                }
                a.this.s = aMapLocation;
                if (aMapLocation != null) {
                    errorCode = aMapLocation.getErrorCode();
                    if (aMapLocation.getErrorCode() == 0) {
                        a.this.t = fq.b();
                        a.this.a(5, (Bundle) null, 0L);
                        z = true;
                        if (!z) {
                            a.this.v = 0;
                            a.this.a(6, (Bundle) null, 0L);
                            return;
                        }
                        Bundle bundle = new Bundle();
                        if (!a.this.m) {
                            a.this.b(7);
                            bundle.putLong("interval", 2000L);
                            a.this.a(8, bundle, 2000L);
                        }
                        a.this.v++;
                        if (a.this.v >= 3) {
                            bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, errorCode);
                            a.this.a(1002, bundle);
                            return;
                        }
                        return;
                    }
                    a.a("定位失败", aMapLocation.getErrorCode(), aMapLocation.getErrorInfo(), "locationDetail:" + aMapLocation.getLocationDetail());
                } else {
                    errorCode = 8;
                }
                z = false;
                if (!z) {
                }
            } catch (Throwable unused) {
            }
        }
    };
    final int x = 3;
    volatile boolean y = false;

    /* JADX INFO: renamed from: com.loc.a$a, reason: collision with other inner class name */
    class HandlerC0038a extends Handler {
        public HandlerC0038a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        a.this.b(message.getData());
                        break;
                    case 1:
                        a.this.c(message.getData());
                        break;
                    case 2:
                        a.this.e(message.getData());
                        break;
                    case 3:
                        a.this.d(message.getData());
                        break;
                    case 4:
                        a.this.f(message.getData());
                        break;
                    case 5:
                        a.this.e();
                        break;
                    case 6:
                        a.this.a(a.this.s);
                        break;
                    case 7:
                        a.this.d();
                        break;
                    case 8:
                        a.this.j(message.getData());
                        break;
                    case 9:
                        a.this.a(message.getData());
                        break;
                    case 10:
                        a.this.c();
                        break;
                    case 11:
                        a.this.h(message.getData());
                        break;
                    case 12:
                        a.this.g(message.getData());
                        break;
                    case 13:
                        a.this.g();
                        break;
                }
            } catch (Throwable unused) {
            }
        }
    }

    static class b extends HandlerThread {
        public b(String str) {
            super(str);
        }

        @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                super.run();
            } catch (Throwable unused) {
            }
        }
    }

    class c extends Handler {
        public c() {
        }

        public c(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                Bundle data = message.getData();
                switch (message.what) {
                    case 1000:
                        a.this.i(data);
                        return;
                    case 1001:
                        try {
                            a.this.b((GeoFence) data.getParcelable("geoFence"));
                            return;
                        } catch (Throwable th) {
                            th.printStackTrace();
                            return;
                        }
                    case 1002:
                        try {
                            a.this.c(data.getInt(GeoFence.BUNDLE_KEY_LOCERRORCODE));
                            return;
                        } catch (Throwable th2) {
                            th2.printStackTrace();
                            return;
                        }
                    default:
                        return;
                }
            } catch (Throwable unused) {
            }
        }
    }

    public a(Context context) {
        this.b = null;
        try {
            this.b = context.getApplicationContext();
            j();
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManger", "<init>");
        }
    }

    private static float a(AMapLocation aMapLocation, List<GeoFence> list) {
        float fMin = Float.MAX_VALUE;
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0 && list != null && !list.isEmpty()) {
            DPoint dPoint = new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            for (GeoFence geoFence : list) {
                if (geoFence.isAble()) {
                    float fA = fq.a(dPoint, geoFence.getCenter());
                    if (fA > geoFence.getMinDis2Center() && fA < geoFence.getMaxDis2Center()) {
                        return 0.0f;
                    }
                    if (fA > geoFence.getMaxDis2Center()) {
                        fMin = Math.min(fMin, fA - geoFence.getMaxDis2Center());
                    }
                    if (fA < geoFence.getMinDis2Center()) {
                        fMin = Math.min(fMin, geoFence.getMinDis2Center() - fA);
                    }
                }
            }
        }
        return fMin;
    }

    static float a(DPoint dPoint, List<DPoint> list) {
        float fMin = Float.MAX_VALUE;
        if (dPoint != null && list != null && !list.isEmpty()) {
            Iterator<DPoint> it = list.iterator();
            while (it.hasNext()) {
                fMin = Math.min(fMin, fq.a(dPoint, it.next()));
            }
        }
        return fMin;
    }

    private int a(List<GeoFence> list) {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            Iterator<GeoFence> it = list.iterator();
            while (it.hasNext()) {
                c(it.next());
            }
            return 0;
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addGeoFenceList");
            a("添加围栏失败", 8, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static Bundle a(GeoFence geoFence, String str, String str2, int i, int i2) {
        Bundle bundle = new Bundle();
        if (str == null) {
            str = "";
        }
        bundle.putString(GeoFence.BUNDLE_KEY_FENCEID, str);
        bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
        bundle.putInt("event", i);
        bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, i2);
        bundle.putParcelable(GeoFence.BUNDLE_KEY_FENCE, geoFence);
        return bundle;
    }

    private GeoFence a(Bundle bundle, boolean z) {
        GeoFence geoFence = new GeoFence();
        ArrayList arrayList = new ArrayList();
        DPoint dPoint = new DPoint();
        if (z) {
            geoFence.setType(1);
            arrayList = bundle.getParcelableArrayList("pointList");
            if (arrayList != null) {
                dPoint = b(arrayList);
            }
            geoFence.setMaxDis2Center(b(dPoint, arrayList));
            geoFence.setMinDis2Center(a(dPoint, arrayList));
        } else {
            geoFence.setType(0);
            dPoint = (DPoint) bundle.getParcelable("centerPoint");
            if (dPoint != null) {
                arrayList.add(dPoint);
            }
            float f = bundle.getFloat("fenceRadius", 1000.0f);
            if (f <= 0.0f) {
                f = 1000.0f;
            }
            geoFence.setRadius(f);
            geoFence.setMinDis2Center(f);
            geoFence.setMaxDis2Center(f);
        }
        geoFence.setActivatesAction(this.f);
        geoFence.setCustomId(bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(arrayList);
        geoFence.setPointList(arrayList2);
        geoFence.setCenter(dPoint);
        geoFence.setPendingIntentAction(this.d);
        geoFence.setExpiration(-1L);
        geoFence.setPendingIntent(this.c);
        StringBuilder sb = new StringBuilder();
        sb.append(com.loc.c.a());
        geoFence.setFenceId(sb.toString());
        if (this.a != null) {
            this.a.a(this.b, 2);
        }
        return geoFence;
    }

    static void a(String str, int i, String str2, String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("===========================================\n");
        stringBuffer.append("              " + str + "                ");
        stringBuffer.append("\n");
        stringBuffer.append("-------------------------------------------\n");
        stringBuffer.append("errorCode:".concat(String.valueOf(i)));
        stringBuffer.append("\n");
        stringBuffer.append("错误信息:".concat(String.valueOf(str2)));
        stringBuffer.append("\n");
        if (strArr.length > 0) {
            for (String str3 : strArr) {
                stringBuffer.append(str3);
                stringBuffer.append("\n");
            }
        }
        stringBuffer.append("===========================================\n");
        Log.i("fenceErrLog", stringBuffer.toString());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean a(int i, String str, String str2, DPoint dPoint) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        switch (i) {
            case 1:
                return !TextUtils.isEmpty(str2);
            case 2:
                if (dPoint == null) {
                    return false;
                }
                if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                    a("添加围栏失败", 0, "经纬度错误，传入的纬度：" + dPoint.getLatitude() + "传入的经度:" + dPoint.getLongitude(), new String[0]);
                    return false;
                }
        }
    }

    private static boolean a(GeoFence geoFence, int i) {
        boolean z = false;
        if ((i & 1) == 1) {
            try {
                if (geoFence.getStatus() == 1) {
                    z = true;
                }
            } catch (Throwable th) {
                fj.a(th, "Utils", "remindStatus");
                return z;
            }
        }
        if ((i & 2) == 2 && geoFence.getStatus() == 2) {
            z = true;
        }
        if ((i & 4) != 4) {
            return z;
        }
        if (geoFence.getStatus() == 3) {
            return true;
        }
        return z;
    }

    private static boolean a(AMapLocation aMapLocation, GeoFence geoFence) {
        boolean z = false;
        try {
            if (fq.a(aMapLocation) && geoFence != null && geoFence.getPointList() != null && !geoFence.getPointList().isEmpty()) {
                switch (geoFence.getType()) {
                    case 0:
                    case 2:
                        if (a(aMapLocation, geoFence.getCenter(), geoFence.getRadius())) {
                            return true;
                        }
                        break;
                    case 1:
                    case 3:
                        Iterator<List<DPoint>> it = geoFence.getPointList().iterator();
                        while (it.hasNext()) {
                            if (b(aMapLocation, it.next())) {
                                z = true;
                            }
                        }
                        break;
                }
            }
            return z;
        } catch (Throwable th) {
            fj.a(th, "Utils", "isInGeoFence");
            return false;
        }
    }

    private static boolean a(AMapLocation aMapLocation, DPoint dPoint, float f) {
        return fq.a(new double[]{dPoint.getLatitude(), dPoint.getLongitude(), aMapLocation.getLatitude(), aMapLocation.getLongitude()}) <= f;
    }

    static float b(DPoint dPoint, List<DPoint> list) {
        float fMax = Float.MIN_VALUE;
        if (dPoint != null && list != null && !list.isEmpty()) {
            Iterator<DPoint> it = list.iterator();
            while (it.hasNext()) {
                fMax = Math.max(fMax, fq.a(dPoint, it.next()));
            }
        }
        return fMax;
    }

    private static DPoint b(List<DPoint> list) {
        DPoint dPoint = new DPoint();
        if (list == null) {
            return dPoint;
        }
        try {
            double latitude = 0.0d;
            double longitude = 0.0d;
            for (DPoint dPoint2 : list) {
                latitude += dPoint2.getLatitude();
                longitude += dPoint2.getLongitude();
            }
            return new DPoint(fq.b(latitude / ((double) list.size())), fq.b(longitude / ((double) list.size())));
        } catch (Throwable th) {
            fj.a(th, "GeoFenceUtil", "getPolygonCenter");
            return dPoint;
        }
    }

    private void b(int i, Bundle bundle) throws Throwable {
        int i2;
        int iD;
        ArrayList<? extends Parcelable> arrayList;
        String strA;
        Bundle bundle2 = new Bundle();
        try {
            try {
                ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                if (bundle == null || bundle.isEmpty()) {
                    iD = 1;
                } else {
                    List<GeoFence> arrayList3 = new ArrayList<>();
                    String string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                    String string2 = bundle.getString("keyWords");
                    String string3 = bundle.getString("city");
                    String string4 = bundle.getString("poiType");
                    DPoint dPoint = (DPoint) bundle.getParcelable("centerPoint");
                    int i3 = bundle.getInt("searchSize", 10);
                    float f = bundle.getFloat("aroundRadius", 3000.0f);
                    if (a(i, string2, string4, dPoint)) {
                        Bundle bundle3 = new Bundle();
                        bundle3.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                        bundle3.putString("pendingIntentAction", this.d);
                        bundle3.putLong("expiration", -1L);
                        bundle3.putInt("activatesAction", this.f);
                        switch (i) {
                            case 1:
                                bundle3.putFloat("fenceRadius", 1000.0f);
                                strA = this.p.a(this.b, "http://restsdk.amap.com/v3/place/text?", string2, string4, string3, String.valueOf(i3));
                                break;
                            case 2:
                                double dB = fq.b(dPoint.getLatitude());
                                double dB2 = fq.b(dPoint.getLongitude());
                                int iIntValue = Float.valueOf(f).intValue();
                                bundle3.putFloat("fenceRadius", 200.0f);
                                strA = this.p.a(this.b, "http://restsdk.amap.com/v3/place/around?", string2, string4, String.valueOf(i3), String.valueOf(dB), String.valueOf(dB2), String.valueOf(iIntValue));
                                break;
                            case 3:
                                strA = this.p.a(this.b, "http://restsdk.amap.com/v3/config/district?", string2);
                                break;
                            default:
                                strA = null;
                                break;
                        }
                        if (strA != null) {
                            int iA = 1 == i ? com.loc.c.a(strA, arrayList3, bundle3) : 0;
                            if (2 == i) {
                                iA = com.loc.c.b(strA, arrayList3, bundle3);
                            }
                            if (3 == i) {
                                iA = this.q.c(strA, arrayList3, bundle3);
                            }
                            if (iA == 10000) {
                                if (arrayList3.isEmpty()) {
                                    iD = 16;
                                } else {
                                    iD = a(arrayList3);
                                    if (iD == 0) {
                                        arrayList = arrayList2;
                                        try {
                                            arrayList.addAll(arrayList3);
                                        } catch (Throwable th) {
                                            th = th;
                                            fj.a(th, "GeoFenceManager", "doAddGeoFenceNearby");
                                            bundle2.putInt("errorCode", 8);
                                        }
                                    }
                                }
                                arrayList = arrayList2;
                            } else {
                                arrayList = arrayList2;
                                iD = d(iA);
                            }
                        } else {
                            arrayList = arrayList2;
                            iD = 4;
                        }
                    } else {
                        arrayList = arrayList2;
                        iD = 1;
                    }
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                }
                bundle2.putInt("errorCode", iD);
            } catch (Throwable th2) {
                th = th2;
                i2 = 1000;
                bundle2.putInt("errorCode", 0);
                a(i2, bundle2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            i2 = 1000;
            bundle2.putInt("errorCode", 0);
            a(i2, bundle2);
            throw th;
        }
        a(1000, bundle2);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean b(AMapLocation aMapLocation, GeoFence geoFence) {
        boolean z = true;
        try {
            if (a(aMapLocation, geoFence)) {
                if (geoFence.getEnterTime() == -1) {
                    if (geoFence.getStatus() != 1) {
                        geoFence.setEnterTime(fq.b());
                        geoFence.setStatus(1);
                    } else {
                        z = false;
                    }
                } else if (geoFence.getStatus() != 3 && fq.b() - geoFence.getEnterTime() > com.igexin.push.config.c.A) {
                    geoFence.setStatus(3);
                }
            } else if (geoFence.getStatus() != 2) {
                try {
                    geoFence.setStatus(2);
                    geoFence.setEnterTime(-1L);
                } catch (Throwable th) {
                    th = th;
                    fj.a(th, "Utils", "isFenceStatusChanged");
                }
            }
        } catch (Throwable th2) {
            th = th2;
            z = false;
        }
        return z;
    }

    private static boolean b(AMapLocation aMapLocation, List<DPoint> list) {
        if (list.size() < 3) {
            return false;
        }
        return fj.a(new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), list);
    }

    private int c(GeoFence geoFence) {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            if (this.g.contains(geoFence)) {
                return 17;
            }
            this.g.add(geoFence);
            return 0;
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addGeoFence2List");
            a("添加围栏失败", 8, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static int d(int i) {
        if (i != 1 && i != 7) {
            switch (i) {
                case 4:
                case 5:
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                            break;
                        default:
                            switch (i) {
                                case com.igexin.push.config.c.d /* 10000 */:
                                    i = 0;
                                    break;
                                case 10001:
                                case PushConsts.GET_CLIENTID /* 10002 */:
                                case PushConsts.GET_SDKONLINESTATE /* 10007 */:
                                case PushConsts.GET_SDKSERVICEPID /* 10008 */:
                                case PushConsts.SET_TAG_RESULT /* 10009 */:
                                case 10012:
                                case PushConsts.GET_DEVICETOKEN /* 10013 */:
                                    i = 7;
                                    break;
                                case 10003:
                                case 10004:
                                case PushConsts.CHECK_CLIENTID /* 10005 */:
                                case PushConsts.THIRDPART_FEEDBACK /* 10006 */:
                                case 10010:
                                case 10011:
                                case PushConsts.ACTION_NOTIFICATION_ENABLE /* 10014 */:
                                case PushConsts.ACTION_POPUP_SHOW /* 10015 */:
                                case PushConsts.ACTION_POPUP_CLICKED /* 10016 */:
                                case 10017:
                                    i = 4;
                                    break;
                                default:
                                    switch (i) {
                                        case 20000:
                                        case PushConsts.SETTAG_ERROR_COUNT /* 20001 */:
                                        case 20002:
                                            i = 1;
                                            break;
                                        case PushConsts.SETTAG_ERROR_REPEAT /* 20003 */:
                                        default:
                                            i = 8;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        if (i != 0) {
            a("添加围栏失败", i, "searchErrCode is ".concat(String.valueOf(i)), new String[0]);
        }
        return i;
    }

    private void d(GeoFence geoFence) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("geoFence", geoFence);
        a(1001, bundle);
    }

    private void j() {
        if (!this.o) {
            this.o = true;
        }
        if (this.n) {
            return;
        }
        try {
            this.h = Looper.myLooper() == null ? new c(this.b.getMainLooper()) : new c();
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManger", "init 1");
        }
        try {
            this.l = new b("fenceActionThread");
            this.l.setPriority(5);
            this.l.start();
            this.k = new HandlerC0038a(this.l.getLooper());
        } catch (Throwable th2) {
            fj.a(th2, "GeoFenceManger", "init 2");
        }
        try {
            this.p = new com.loc.b(this.b);
            this.q = new com.loc.c();
            this.u = new AMapLocationClientOption();
            this.r = new AMapLocationClient(this.b);
            this.u.setLocationCacheEnable(true);
            this.u.setNeedAddress(false);
            this.r.setLocationListener(this.w);
            if (this.a == null) {
                this.a = new fo();
            }
        } catch (Throwable th3) {
            fj.a(th3, "GeoFenceManger", "initBase");
        }
        this.n = true;
        try {
            if (this.d != null && this.c == null) {
                a(this.d);
            }
        } catch (Throwable th4) {
            fj.a(th4, "GeoFenceManger", "init 4");
        }
        if (A) {
            return;
        }
        A = true;
        fo.a(this.b, "O020", (JSONObject) null);
    }

    private boolean k() {
        if (this.g == null || this.g.isEmpty()) {
            return true;
        }
        Iterator<GeoFence> it = this.g.iterator();
        while (it.hasNext()) {
            if (it.next().isAble()) {
                return false;
            }
        }
        return true;
    }

    private void l() {
        try {
            synchronized (this.j) {
                if (this.h != null) {
                    this.h.removeCallbacksAndMessages(null);
                }
                this.h = null;
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "destroyResultHandler");
        }
    }

    private void m() {
        try {
            synchronized (this.i) {
                if (this.k != null) {
                    this.k.removeCallbacksAndMessages(null);
                }
                this.k = null;
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "destroyActionHandler");
        }
    }

    private void n() {
        if (this.y || this.k == null) {
            return;
        }
        if (p()) {
            a(6, (Bundle) null, 0L);
            a(5, (Bundle) null, 0L);
        } else {
            b(7);
            a(7, (Bundle) null, 0L);
        }
    }

    private void o() {
        try {
            if (this.m) {
                b(8);
            }
            if (this.r != null) {
                this.r.stopLocation();
            }
            this.m = false;
        } catch (Throwable unused) {
        }
    }

    private boolean p() {
        return this.s != null && fq.a(this.s) && fq.b() - this.t < com.igexin.push.config.c.i;
    }

    public final PendingIntent a(String str) {
        synchronized (this.z) {
            try {
                Intent intent = new Intent(str);
                intent.setPackage(l.c(this.b));
                this.c = (Build.VERSION.SDK_INT < 31 || this.b.getApplicationInfo().targetSdkVersion < 31) ? PendingIntent.getBroadcast(this.b, 0, intent, 0) : PendingIntent.getBroadcast(this.b, 0, intent, 33554432);
                this.d = str;
            } catch (Throwable th) {
                fj.a(th, "GeoFenceManager", "createPendingIntent");
            }
            if (this.g != null && !this.g.isEmpty()) {
                for (GeoFence geoFence : this.g) {
                    geoFence.setPendingIntent(this.c);
                    geoFence.setPendingIntentAction(this.d);
                }
            }
        }
        return this.c;
    }

    public final void a() {
        try {
            this.o = false;
            a(10, (Bundle) null, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "removeGeoFence");
        }
    }

    public final void a(int i) {
        try {
            j();
            if (i > 7 || i <= 0) {
                i = 1;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("activatesAction", i);
            a(9, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "setActivateAction");
        }
    }

    final void a(int i, Bundle bundle) {
        try {
            synchronized (this.j) {
                if (this.h != null) {
                    Message messageObtainMessage = this.h.obtainMessage();
                    messageObtainMessage.what = i;
                    messageObtainMessage.setData(bundle);
                    this.h.sendMessage(messageObtainMessage);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "sendResultHandlerMessage");
        }
    }

    final void a(int i, Bundle bundle, long j) {
        try {
            synchronized (this.i) {
                if (this.k != null) {
                    Message messageObtainMessage = this.k.obtainMessage();
                    messageObtainMessage.what = i;
                    messageObtainMessage.setData(bundle);
                    this.k.sendMessageDelayed(messageObtainMessage, j);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "sendActionHandlerMessage");
        }
    }

    final void a(Bundle bundle) {
        int i = 1;
        if (bundle != null) {
            try {
                i = bundle.getInt("activatesAction", 1);
            } catch (Throwable th) {
                fj.a(th, "GeoFenceManager", "doSetActivatesAction");
                return;
            }
        }
        if (this.f != i) {
            if (this.g != null && !this.g.isEmpty()) {
                for (GeoFence geoFence : this.g) {
                    geoFence.setStatus(0);
                    geoFence.setEnterTime(-1L);
                }
            }
            n();
        }
        this.f = i;
    }

    public final void a(GeoFenceListener geoFenceListener) {
        try {
            this.e = geoFenceListener;
        } catch (Throwable unused) {
        }
    }

    final void a(AMapLocation aMapLocation) {
        try {
            if (this.y || this.g == null || this.g.isEmpty() || aMapLocation == null || aMapLocation.getErrorCode() != 0) {
                return;
            }
            for (GeoFence geoFence : this.g) {
                if (geoFence.isAble() && b(aMapLocation, geoFence) && a(geoFence, this.f)) {
                    geoFence.setCurrentLocation(aMapLocation);
                    d(geoFence);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doCheckFence");
        }
    }

    public final void a(DPoint dPoint, float f, String str) {
        try {
            j();
            Bundle bundle = new Bundle();
            bundle.putParcelable("centerPoint", dPoint);
            bundle.putFloat("fenceRadius", f);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(0, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addRoundGeoFence");
        }
    }

    public final void a(String str, String str2) {
        try {
            j();
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
            a(4, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addDistricetGeoFence");
        }
    }

    public final void a(String str, String str2, DPoint dPoint, float f, int i, String str3) {
        try {
            j();
            if (f <= 0.0f || f > 50000.0f) {
                f = 3000.0f;
            }
            if (i <= 0) {
                i = 10;
            }
            if (i > 25) {
                i = 25;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString("poiType", str2);
            bundle.putParcelable("centerPoint", dPoint);
            bundle.putFloat("aroundRadius", f);
            bundle.putInt("searchSize", i);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str3);
            a(3, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addNearbyGeoFence");
        }
    }

    public final void a(String str, String str2, String str3, int i, String str4) {
        try {
            j();
            if (i <= 0) {
                i = 10;
            }
            if (i > 25) {
                i = 25;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString("poiType", str2);
            bundle.putString("city", str3);
            bundle.putInt("searchSize", i);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str4);
            a(2, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addKeywordGeoFence");
        }
    }

    public final void a(String str, boolean z) {
        try {
            j();
            Bundle bundle = new Bundle();
            bundle.putString("fid", str);
            bundle.putBoolean("ab", z);
            a(12, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "setGeoFenceAble");
        }
    }

    public final void a(List<DPoint> list, String str) {
        try {
            j();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("pointList", new ArrayList<>(list));
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(1, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "addPolygonGeoFence");
        }
    }

    public final boolean a(GeoFence geoFence) {
        try {
            if (this.g != null && !this.g.isEmpty()) {
                if (!this.g.contains(geoFence)) {
                    return false;
                }
                if (this.g.size() == 1) {
                    this.o = false;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("fc", geoFence);
                a(11, bundle, 0L);
                return true;
            }
            this.o = false;
            a(10, (Bundle) null, 0L);
            return true;
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "removeGeoFence(GeoFence)");
            return false;
        }
    }

    public final List<GeoFence> b() {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            return (ArrayList) this.g.clone();
        } catch (Throwable unused) {
            return new ArrayList();
        }
    }

    final void b(int i) {
        try {
            synchronized (this.i) {
                if (this.k != null) {
                    this.k.removeMessages(i);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "removeActionHandlerMessage");
        }
    }

    final void b(Bundle bundle) {
        String string;
        try {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            int iC = 1;
            if (bundle == null || bundle.isEmpty()) {
                string = "";
            } else {
                DPoint dPoint = (DPoint) bundle.getParcelable("centerPoint");
                string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (dPoint != null) {
                    if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                        a("添加围栏失败", 1, "经纬度错误，传入的纬度：" + dPoint.getLatitude() + "传入的经度:" + dPoint.getLongitude(), new String[0]);
                    } else {
                        GeoFence geoFenceA = a(bundle, false);
                        iC = c(geoFenceA);
                        if (iC == 0) {
                            arrayList.add(geoFenceA);
                        }
                    }
                }
            }
            Bundle bundle2 = new Bundle();
            bundle2.putInt("errorCode", iC);
            bundle2.putParcelableArrayList("resultList", arrayList);
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
            a(1000, bundle2);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doAddGeoFenceRound");
        }
    }

    final void b(GeoFence geoFence) {
        PendingIntent pendingIntent;
        Context context;
        try {
            synchronized (this.z) {
                if (this.b != null) {
                    if (this.c == null && geoFence.getPendingIntent() == null) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtras(a(geoFence, geoFence.getFenceId(), geoFence.getCustomId(), geoFence.getStatus(), 0));
                    if (this.d != null) {
                        intent.setAction(this.d);
                    }
                    intent.setPackage(l.c(this.b));
                    if (geoFence.getPendingIntent() != null) {
                        pendingIntent = geoFence.getPendingIntent();
                        context = this.b;
                    } else {
                        pendingIntent = this.c;
                        context = this.b;
                    }
                    pendingIntent.send(context, 0, intent);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "resultTriggerGeoFence");
        }
    }

    final void c() {
        try {
            if (!this.n) {
                return;
            }
            if (this.g != null) {
                this.g.clear();
                this.g = null;
            }
            if (this.o) {
                return;
            }
            m();
            if (this.r != null) {
                this.r.stopLocation();
                this.r.onDestroy();
            }
            this.r = null;
            if (this.l != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    this.l.quitSafely();
                } else {
                    this.l.quit();
                }
            }
            this.l = null;
            this.p = null;
            synchronized (this.z) {
                if (this.c != null) {
                    this.c.cancel();
                }
                this.c = null;
            }
            l();
            if (this.a != null) {
                this.a.b(this.b);
            }
        } catch (Throwable unused) {
        }
        this.m = false;
        this.n = false;
    }

    final void c(int i) {
        try {
            if (this.b != null) {
                synchronized (this.z) {
                    if (this.c == null) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtras(a((GeoFence) null, (String) null, (String) null, 4, i));
                    this.c.send(this.b, 0, intent);
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "resultRemindLocationError");
        }
    }

    final void c(Bundle bundle) {
        String string;
        GeoFence geoFenceA;
        try {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            int iC = 1;
            if (bundle == null || bundle.isEmpty()) {
                string = "";
            } else {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("pointList");
                string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (parcelableArrayList != null && parcelableArrayList.size() > 2 && (iC = c((geoFenceA = a(bundle, true)))) == 0) {
                    arrayList.add(geoFenceA);
                }
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
            bundle2.putInt("errorCode", iC);
            bundle2.putParcelableArrayList("resultList", arrayList);
            a(1000, bundle2);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doAddGeoFencePolygon");
        }
    }

    final void d() {
        try {
            if (this.r != null) {
                o();
                this.u.setOnceLocation(true);
                this.r.setLocationOption(this.u);
                this.r.startLocation();
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doStartOnceLocation");
        }
    }

    final void d(Bundle bundle) throws Throwable {
        b(2, bundle);
    }

    final void e() {
        try {
            if (!this.y && fq.a(this.s)) {
                float fA = a(this.s, this.g);
                if (fA == Float.MAX_VALUE) {
                    return;
                }
                if (fA < 1000.0f) {
                    b(7);
                    Bundle bundle = new Bundle();
                    bundle.putLong("interval", 2000L);
                    a(8, bundle, 500L);
                    return;
                }
                if (fA < 5000.0f) {
                    o();
                    b(7);
                    a(7, (Bundle) null, com.igexin.push.config.c.i);
                } else {
                    o();
                    b(7);
                    a(7, (Bundle) null, (long) (((fA - 4000.0f) / 100.0f) * 1000.0f));
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doCheckLocationPolicy");
        }
    }

    final void e(Bundle bundle) throws Throwable {
        b(1, bundle);
    }

    public final void f() {
        try {
            j();
            this.y = true;
            a(13, (Bundle) null, 0L);
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "pauseGeoFence");
        }
    }

    final void f(Bundle bundle) throws Throwable {
        b(3, bundle);
    }

    final void g() {
        try {
            b(7);
            b(8);
            if (this.r != null) {
                this.r.stopLocation();
            }
            this.m = false;
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doPauseGeoFence");
        }
    }

    final void g(Bundle bundle) {
        if (bundle != null) {
            try {
                if (bundle.isEmpty()) {
                    return;
                }
                String string = bundle.getString("fid");
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                boolean z = bundle.getBoolean("ab", true);
                if (this.g != null && !this.g.isEmpty()) {
                    Iterator<GeoFence> it = this.g.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        GeoFence next = it.next();
                        if (next.getFenceId().equals(string)) {
                            next.setAble(z);
                            break;
                        }
                    }
                }
                if (z) {
                    n();
                } else if (k()) {
                    g();
                }
            } catch (Throwable th) {
                fj.a(th, "GeoFenceManager", "doSetGeoFenceAble");
            }
        }
    }

    public final void h() {
        try {
            j();
            if (this.y) {
                this.y = false;
                n();
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "resumeGeoFence");
        }
    }

    final void h(Bundle bundle) {
        try {
            if (this.g != null) {
                GeoFence geoFence = (GeoFence) bundle.getParcelable("fc");
                if (this.g.contains(geoFence)) {
                    this.g.remove(geoFence);
                }
                if (this.g.size() <= 0) {
                    c();
                } else {
                    n();
                }
            }
        } catch (Throwable unused) {
        }
    }

    final void i(Bundle bundle) {
        if (bundle != null) {
            try {
                if (bundle.isEmpty()) {
                    return;
                }
                int i = bundle.getInt("errorCode");
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("resultList");
                if (parcelableArrayList == null) {
                    parcelableArrayList = new ArrayList();
                }
                String string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (string == null) {
                    string = "";
                }
                if (this.e != null) {
                    this.e.onGeoFenceCreateFinished((ArrayList) parcelableArrayList.clone(), i, string);
                }
                if (i == 0) {
                    n();
                }
            } catch (Throwable th) {
                fj.a(th, "GeoFenceManager", "resultAddGeoFenceFinished");
            }
        }
    }

    public final boolean i() {
        return this.y;
    }

    final void j(Bundle bundle) {
        try {
            if (this.r != null) {
                long j = 2000;
                if (bundle != null && !bundle.isEmpty()) {
                    j = bundle.getLong("interval", 2000L);
                }
                this.u.setOnceLocation(false);
                this.u.setInterval(j);
                this.r.setLocationOption(this.u);
                if (this.m) {
                    return;
                }
                this.r.stopLocation();
                this.r.startLocation();
                this.m = true;
            }
        } catch (Throwable th) {
            fj.a(th, "GeoFenceManager", "doStartContinueLocation");
        }
    }
}
