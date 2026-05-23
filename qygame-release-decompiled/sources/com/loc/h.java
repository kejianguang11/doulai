package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.DPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    static AMapLocation j;
    static long k;
    static Object l = new Object();
    static long q = 0;
    static boolean t = false;
    static boolean u = false;
    public static volatile AMapLocation y = null;
    private GnssStatus.Callback F;
    Handler a;
    LocationManager b;
    AMapLocationClientOption c;
    en f;
    private Context z;
    private long A = 0;
    long d = 0;
    boolean e = false;
    private int B = 0;
    int g = 240;
    int h = 80;
    AMapLocation i = null;
    long m = 0;
    float n = 0.0f;
    Object o = new Object();
    Object p = new Object();
    private int C = 0;
    private GpsStatus D = null;
    private GpsStatus.Listener E = null;
    AMapLocationClientOption.GeoLanguage r = AMapLocationClientOption.GeoLanguage.DEFAULT;
    boolean s = true;
    long v = 0;
    int w = 0;
    LocationListener x = null;
    private String G = null;
    private boolean H = false;
    private int I = 0;
    private boolean J = false;

    static class a implements LocationListener {
        private h a;

        a(h hVar) {
            this.a = hVar;
        }

        final void a() {
            this.a = null;
        }

        @Override // android.location.LocationListener
        public final void onLocationChanged(Location location) {
            try {
                Object[] objArr = {"GLS olc", "tid=" + Thread.currentThread().getId()};
                if (this.a != null) {
                    this.a.a(location);
                }
            } catch (Throwable unused) {
            }
        }

        @Override // android.location.LocationListener
        public final void onProviderDisabled(String str) {
            try {
                if (this.a != null) {
                    this.a.a(str);
                }
            } catch (Throwable unused) {
            }
        }

        @Override // android.location.LocationListener
        public final void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public final void onStatusChanged(String str, int i, Bundle bundle) {
            try {
                if (this.a != null) {
                    this.a.a(i);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public h(Context context, Handler handler) {
        this.f = null;
        this.z = context;
        this.a = handler;
        try {
            this.b = (LocationManager) this.z.getSystemService("location");
        } catch (Throwable th) {
            fj.a(th, "GpsLocation", "<init>");
        }
        this.f = new en();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        if (i == 0) {
            try {
                this.d = 0L;
                this.C = 0;
            } catch (Throwable unused) {
            }
        }
    }

    private void a(int i, int i2, String str, long j2) {
        try {
            if (this.a == null || this.c.getLocationMode() != AMapLocationClientOption.AMapLocationMode.Device_Sensors) {
                return;
            }
            Message messageObtain = Message.obtain();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setProvider("gps");
            aMapLocation.setErrorCode(i2);
            aMapLocation.setLocationDetail(str);
            aMapLocation.setLocationType(1);
            messageObtain.obj = aMapLocation;
            messageObtain.what = i;
            this.a.sendMessageDelayed(messageObtain, j2);
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(GnssStatus gnssStatus) {
        int i = 0;
        if (gnssStatus != null) {
            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    int satelliteCount = gnssStatus.getSatelliteCount();
                    int i2 = 0;
                    while (i < satelliteCount) {
                        try {
                            if (gnssStatus.usedInFix(i)) {
                                i2++;
                            }
                            i++;
                        } catch (Throwable th) {
                            th = th;
                            i = i2;
                            fj.a(th, "GpsLocation_Gnss", "GPS_EVENT_SATELLITE_STATUS");
                            this.C = i;
                        }
                    }
                    i = i2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        this.C = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Location location) {
        if (this.a != null) {
            this.a.removeMessages(8);
        }
        if (location == null) {
            return;
        }
        try {
            AMapLocation aMapLocation = new AMapLocation(location);
            if (fq.a(aMapLocation)) {
                aMapLocation.setProvider("gps");
                aMapLocation.setLocationType(1);
                if (!this.e && fq.a(aMapLocation)) {
                    fo.a(this.z, fq.b() - this.A, fj.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                    this.e = true;
                }
                if (fq.a(aMapLocation, this.C)) {
                    aMapLocation.setMock(true);
                    aMapLocation.setTrustedLevel(4);
                    if (!this.c.isMockEnable()) {
                        if (this.w <= 3) {
                            this.w++;
                            return;
                        }
                        fo.a((String) null, 2152);
                        aMapLocation.setErrorCode(15);
                        aMapLocation.setLocationDetail("GpsLocation has been mocked!#1501");
                        aMapLocation.setLatitude(0.0d);
                        aMapLocation.setLongitude(0.0d);
                        aMapLocation.setAltitude(0.0d);
                        aMapLocation.setSpeed(0.0f);
                        aMapLocation.setAccuracy(0.0f);
                        aMapLocation.setBearing(0.0f);
                        aMapLocation.setExtras(null);
                        c(aMapLocation);
                        return;
                    }
                } else {
                    this.w = 0;
                }
                aMapLocation.setSatellites(this.C);
                e(aMapLocation);
                f(aMapLocation);
                h(aMapLocation);
                AMapLocation aMapLocationG = g(aMapLocation);
                a(aMapLocationG);
                b(aMapLocationG);
                synchronized (this.o) {
                    a(aMapLocationG, y);
                }
                try {
                    if (fq.a(aMapLocationG)) {
                        if (this.i != null) {
                            this.m = location.getTime() - this.i.getTime();
                            this.n = fq.a(this.i, aMapLocationG);
                        }
                        synchronized (this.p) {
                            this.i = aMapLocationG.m6clone();
                        }
                        this.G = null;
                        this.H = false;
                        this.I = 0;
                    }
                } catch (Throwable th) {
                    fj.a(th, "GpsLocation", "onLocationChangedLast");
                }
                c(aMapLocationG);
            }
        } catch (Throwable th2) {
            fj.a(th2, "GpsLocation", "onLocationChanged");
        }
    }

    private void a(AMapLocation aMapLocation) {
        if (fq.a(aMapLocation)) {
            this.d = fq.b();
            synchronized (l) {
                k = fq.b();
                j = aMapLocation.m6clone();
            }
            this.B++;
        }
    }

    private void a(AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        if (aMapLocation2 == null || !this.c.isNeedAddress() || fq.a(aMapLocation, aMapLocation2) >= this.g) {
            return;
        }
        fj.a(aMapLocation, aMapLocation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        try {
            if ("gps".equalsIgnoreCase(str)) {
                this.d = 0L;
                this.C = 0;
            }
        } catch (Throwable unused) {
        }
    }

    private static boolean a(LocationManager locationManager) {
        try {
            if (t) {
                return u;
            }
            List<String> allProviders = locationManager.getAllProviders();
            if (allProviders == null || allProviders.size() <= 0) {
                u = false;
            } else {
                u = allProviders.contains("gps");
            }
            t = true;
            return u;
        } catch (Throwable th) {
            new Object[1][0] = "GpsLocation | hasProvider error: " + th.getMessage();
            return u;
        }
    }

    private void b(AMapLocation aMapLocation) {
        if (fq.a(aMapLocation) && this.a != null) {
            long jB = fq.b();
            if (this.c.getInterval() <= 8000 || jB - this.v > this.c.getInterval() - 8000) {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", aMapLocation.getLatitude());
                bundle.putDouble("lon", aMapLocation.getLongitude());
                bundle.putFloat("radius", aMapLocation.getAccuracy());
                bundle.putLong("time", aMapLocation.getTime());
                Message messageObtain = Message.obtain();
                messageObtain.setData(bundle);
                messageObtain.what = 5;
                synchronized (this.o) {
                    if (y == null || fq.a(aMapLocation, y) > this.h) {
                        Handler handler = this.a;
                        handler.sendMessage(messageObtain);
                    }
                }
            }
        }
    }

    private boolean b(String str) {
        try {
            ArrayList<String> arrayListB = fq.b(str);
            ArrayList<String> arrayListB2 = fq.b(this.G);
            if (arrayListB.size() < 8 || arrayListB2.size() < 8) {
                return false;
            }
            return fq.a(this.G, str);
        } catch (Throwable unused) {
            return false;
        }
    }

    private void c(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() != 15 || AMapLocationClientOption.AMapLocationMode.Device_Sensors.equals(this.c.getLocationMode())) {
            if (this.c.getLocationMode().equals(AMapLocationClientOption.AMapLocationMode.Device_Sensors) && this.c.getDeviceModeDistanceFilter() > 0.0f) {
                d(aMapLocation);
            } else if (fq.b() - this.v >= this.c.getInterval() - 200) {
                this.v = fq.b();
                d(aMapLocation);
            }
        }
    }

    private void d(AMapLocation aMapLocation) {
        if (this.a != null) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = aMapLocation;
            messageObtain.what = 2;
            this.a.sendMessage(messageObtain);
        }
    }

    private void e(AMapLocation aMapLocation) {
        try {
            if (!fj.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()) || !this.c.isOffset()) {
                aMapLocation.setOffset(false);
                aMapLocation.setCoordType(AMapLocation.COORD_TYPE_WGS84);
                return;
            }
            DPoint dPointA = fl.a(this.z, new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            aMapLocation.setLatitude(dPointA.getLatitude());
            aMapLocation.setLongitude(dPointA.getLongitude());
            aMapLocation.setOffset(this.c.isOffset());
            aMapLocation.setCoordType(AMapLocation.COORD_TYPE_GCJ02);
        } catch (Throwable unused) {
            aMapLocation.setOffset(false);
            aMapLocation.setCoordType(AMapLocation.COORD_TYPE_WGS84);
        }
    }

    private void f(AMapLocation aMapLocation) {
        try {
            if (this.C >= 4) {
                aMapLocation.setGpsAccuracyStatus(1);
            } else if (this.C == 0) {
                aMapLocation.setGpsAccuracyStatus(-1);
            } else {
                aMapLocation.setGpsAccuracyStatus(0);
            }
        } catch (Throwable unused) {
        }
    }

    private AMapLocation g(AMapLocation aMapLocation) {
        if (!fq.a(aMapLocation) || this.B < 3) {
            return aMapLocation;
        }
        if (aMapLocation.getAccuracy() < 0.0f || aMapLocation.getAccuracy() == Float.MAX_VALUE) {
            aMapLocation.setAccuracy(0.0f);
        }
        if (aMapLocation.getSpeed() < 0.0f || aMapLocation.getSpeed() == Float.MAX_VALUE) {
            aMapLocation.setSpeed(0.0f);
        }
        return this.f.a(aMapLocation);
    }

    private static void h(AMapLocation aMapLocation) {
        if (fq.a(aMapLocation) && fi.r()) {
            long time = aMapLocation.getTime();
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jA = fk.a(time, jCurrentTimeMillis, fi.s());
            if (jA != time) {
                aMapLocation.setTime(jA);
                fo.a(time, jCurrentTimeMillis);
            }
        }
    }

    private void i() {
        if (this.b == null) {
            return;
        }
        try {
            try {
                n();
                this.s = true;
                Looper looperMyLooper = Looper.myLooper();
                if (looperMyLooper == null) {
                    looperMyLooper = this.z.getMainLooper();
                }
                Looper looper = looperMyLooper;
                this.A = fq.b();
                if (!a(this.b)) {
                    new Object[1][0] = "GpsLocation | no GPS_provider";
                    a(8, 14, "no gps provider#1402", 0L);
                    return;
                }
                try {
                    if (fq.a() - q >= 259200000) {
                        if (fq.c(this.z, "WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19MT0NBVElPTl9FWFRSQV9DT01NQU5EUw==")) {
                            this.b.sendExtraCommand("gps", "force_xtra_injection", null);
                            q = fq.a();
                            SharedPreferences.Editor editorA = fp.a(this.z, "pref");
                            fp.a(editorA, "lagt", q);
                            fp.a(editorA);
                            new Object[1][0] = "GpsLocation | sendExtraCommand";
                        } else {
                            fj.a(new Exception("n_alec"), "OPENSDK_GL", "rlu_n_alec");
                        }
                    }
                } catch (Throwable th) {
                    new Object[1][0] = "GpsLocation | sendExtraCommand error: " + th.getMessage();
                }
                if (this.x == null) {
                    this.x = new a(this);
                }
                this.b.requestLocationUpdates("gps", this.c.getInterval(), this.c.getDeviceModeDistanceFilter(), this.x, looper);
                if (Build.VERSION.SDK_INT >= 24) {
                    this.F = new GnssStatus.Callback() { // from class: com.loc.h.1
                        @Override // android.location.GnssStatus.Callback
                        public final void onFirstFix(int i) {
                            h.l();
                        }

                        @Override // android.location.GnssStatus.Callback
                        public final void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                            h.this.a(gnssStatus);
                        }

                        @Override // android.location.GnssStatus.Callback
                        public final void onStarted() {
                            h.j();
                        }

                        @Override // android.location.GnssStatus.Callback
                        public final void onStopped() {
                            h.this.k();
                        }
                    };
                    this.b.registerGnssStatusCallback(this.F);
                } else {
                    this.E = new GpsStatus.Listener() { // from class: com.loc.h.2
                        @Override // android.location.GpsStatus.Listener
                        public final void onGpsStatusChanged(int i) {
                            try {
                                if (h.this.b == null) {
                                }
                                h.this.D = h.this.b.getGpsStatus(h.this.D);
                                switch (i) {
                                    case 1:
                                        h.j();
                                        break;
                                    case 2:
                                        h.this.k();
                                        break;
                                    case 3:
                                        h.l();
                                        break;
                                    case 4:
                                        h.this.m();
                                        break;
                                }
                            } catch (Throwable th2) {
                                new Object[1][0] = "GpsLocation | onGpsStatusChanged error: " + th2.getMessage();
                                fj.a(th2, "GpsLocation", "onGpsStatusChanged");
                            }
                        }
                    };
                    this.b.addGpsStatusListener(this.E);
                    new Object[1][0] = "GpsLocation | addGpsStatusListener";
                }
                a(8, 14, "no enough satellites#1401", this.c.getHttpTimeOut());
            } catch (Throwable th2) {
                new Object[1][0] = "GpsLocation | requestLocationUpdates error: " + th2.getMessage();
                fj.a(th2, "GpsLocation", "requestLocationUpdates part2");
            }
        } catch (SecurityException e) {
            new Object[1][0] = "GpsLocation | no location permission";
            this.s = false;
            fo.a((String) null, 2121);
            a(2, 12, e.getMessage() + "#1201", 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void j() {
        new Object[1][0] = "GpsLocation | status stopped ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        new Object[1][0] = "GpsLocation | status stopped ";
        this.C = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void l() {
        new Object[1][0] = "GpsLocation | first fix ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        Iterable<GpsSatellite> satellites;
        int i = 0;
        try {
            if (this.D != null && (satellites = this.D.getSatellites()) != null) {
                Iterator<GpsSatellite> it = satellites.iterator();
                int maxSatellites = this.D.getMaxSatellites();
                while (it.hasNext() && i < maxSatellites) {
                    if (it.next().usedInFix()) {
                        i++;
                    }
                }
            }
        } catch (Throwable th) {
            fj.a(th, "GpsLocation", "GPS_EVENT_SATELLITE_STATUS");
        }
        this.C = i;
    }

    private void n() {
        if (fq.b() - k > com.igexin.push.config.c.s || !fq.a(j)) {
            return;
        }
        if (this.c.isMockEnable() || !j.isMock()) {
            this.d = fq.b();
            c(j);
        }
    }

    private static boolean o() {
        try {
            return ((Boolean) fm.a(x.c("KY29tLmFtYXAuYXBpLm5hdmkuQU1hcE5hdmk="), x.c("UaXNOYXZpU3RhcnRlZA=="), (Object[]) null, (Class<?>[]) null)).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    private AMapLocation p() {
        float f;
        float f2;
        try {
            if (fq.a(this.i) && fi.j() && o()) {
                JSONObject jSONObject = new JSONObject((String) fm.a(x.c("KY29tLmFtYXAuYXBpLm5hdmkuQU1hcE5hdmk="), x.c("UZ2V0TmF2aUxvY2F0aW9u"), (Object[]) null, (Class<?>[]) null));
                long jOptLong = jSONObject.optLong("time");
                if (!this.J) {
                    this.J = true;
                    fo.a("useNaviLoc", "use NaviLoc");
                }
                if (fq.a() - jOptLong <= 5500) {
                    double dOptDouble = jSONObject.optDouble("lat", 0.0d);
                    double dOptDouble2 = jSONObject.optDouble("lng", 0.0d);
                    float f3 = 0.0f;
                    try {
                        f = Float.parseFloat(jSONObject.optString("accuracy", "0"));
                    } catch (NumberFormatException unused) {
                        f = 0.0f;
                    }
                    double dOptDouble3 = jSONObject.optDouble("altitude", 0.0d);
                    try {
                        f2 = Float.parseFloat(jSONObject.optString("bearing", "0"));
                    } catch (NumberFormatException unused2) {
                        f2 = 0.0f;
                    }
                    try {
                        f3 = (Float.parseFloat(jSONObject.optString("speed", "0")) * 10.0f) / 36.0f;
                    } catch (NumberFormatException unused3) {
                    }
                    AMapLocation aMapLocation = new AMapLocation("lbs");
                    aMapLocation.setLocationType(9);
                    aMapLocation.setLatitude(dOptDouble);
                    aMapLocation.setLongitude(dOptDouble2);
                    aMapLocation.setAccuracy(f);
                    aMapLocation.setAltitude(dOptDouble3);
                    aMapLocation.setBearing(f2);
                    aMapLocation.setSpeed(f3);
                    aMapLocation.setTime(jOptLong);
                    aMapLocation.setCoordType(AMapLocation.COORD_TYPE_GCJ02);
                    if (fq.a(aMapLocation, this.i) <= 300.0f) {
                        synchronized (this.p) {
                            this.i.setLongitude(dOptDouble2);
                            this.i.setLatitude(dOptDouble);
                            this.i.setAccuracy(f);
                            this.i.setBearing(f2);
                            this.i.setSpeed(f3);
                            this.i.setTime(jOptLong);
                            this.i.setCoordType(AMapLocation.COORD_TYPE_GCJ02);
                        }
                        return aMapLocation;
                    }
                }
            }
        } catch (Throwable unused4) {
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AMapLocation a(AMapLocation aMapLocation, String str) {
        long j2;
        int i;
        if (this.i == null) {
            return aMapLocation;
        }
        if ((!this.c.isMockEnable() && this.i.isMock()) || !fq.a(this.i)) {
            return aMapLocation;
        }
        AMapLocation aMapLocationP = p();
        if (aMapLocationP != null && fq.a(aMapLocationP)) {
            aMapLocationP.setTrustedLevel(2);
            return aMapLocationP;
        }
        float speed = this.i.getSpeed();
        if (speed == 0.0f && this.m > 0 && this.m < 8 && this.n > 0.0f) {
            speed = this.n / this.m;
        }
        if (aMapLocation == null || !fq.a(aMapLocation)) {
            j2 = 30000;
        } else if (aMapLocation.getAccuracy() < 200.0f) {
            this.I++;
            if (this.G == null && this.I >= 2) {
                this.H = true;
            }
            j2 = speed > 5.0f ? com.igexin.push.config.c.i : 15000L;
        } else {
            if (!TextUtils.isEmpty(this.G)) {
                this.H = false;
                this.I = 0;
            }
            if (speed > 5.0f) {
                j2 = 20000;
            }
        }
        long jB = fq.b() - this.d;
        if (jB > com.igexin.push.config.c.k) {
            return aMapLocation;
        }
        if (jB < j2) {
            if (this.G == null) {
                i = 2;
                if (this.I >= 2) {
                    this.G = str;
                }
            } else {
                i = 2;
            }
            AMapLocation aMapLocationM6clone = this.i.m6clone();
            aMapLocationM6clone.setTrustedLevel(i);
            return aMapLocationM6clone;
        }
        if (this.H && b(str)) {
            AMapLocation aMapLocationM6clone2 = this.i.m6clone();
            aMapLocationM6clone2.setTrustedLevel(3);
            return aMapLocationM6clone2;
        }
        this.G = null;
        this.I = 0;
        synchronized (this.p) {
            this.i = null;
        }
        this.m = 0L;
        this.n = 0.0f;
        return aMapLocation;
    }

    public final void a() {
        if (this.b == null) {
            return;
        }
        try {
            if (this.x != null) {
                this.b.removeUpdates(this.x);
                ((a) this.x).a();
                this.x = null;
            }
        } catch (Throwable unused) {
        }
        try {
            if (this.E != null) {
                this.b.removeGpsStatusListener(this.E);
            }
        } catch (Throwable unused2) {
        }
        try {
            if (this.F != null) {
                this.b.unregisterGnssStatusCallback(this.F);
            }
        } catch (Throwable unused3) {
        }
        try {
            if (this.a != null) {
                this.a.removeMessages(8);
            }
        } catch (Throwable unused4) {
        }
        this.C = 0;
        this.A = 0L;
        this.v = 0L;
        this.d = 0L;
        this.B = 0;
        this.w = 0;
        this.f.a();
        this.i = null;
        this.m = 0L;
        this.n = 0.0f;
        this.G = null;
        this.J = false;
    }

    public final void a(Bundle bundle) {
        if (bundle != null) {
            try {
                bundle.setClassLoader(AMapLocation.class.getClassLoader());
                this.g = bundle.getInt("I_MAX_GEO_DIS");
                this.h = bundle.getInt("I_MIN_GEO_DIS");
                AMapLocation aMapLocation = (AMapLocation) bundle.getParcelable("loc");
                if (TextUtils.isEmpty(aMapLocation.getAdCode())) {
                    return;
                }
                synchronized (this.o) {
                    y = aMapLocation;
                }
            } catch (Throwable th) {
                fj.a(th, "GpsLocation", "setLastGeoLocation");
            }
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        this.c = aMapLocationClientOption;
        if (this.c == null) {
            this.c = new AMapLocationClientOption();
        }
        try {
            q = fp.a(this.z, "pref", "lagt", q);
        } catch (Throwable unused) {
        }
        i();
    }

    public final void b(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption == null) {
            aMapLocationClientOption = new AMapLocationClientOption();
        }
        this.c = aMapLocationClientOption;
        if (this.c.getLocationMode() != AMapLocationClientOption.AMapLocationMode.Device_Sensors && this.a != null) {
            this.a.removeMessages(8);
        }
        if (this.r != this.c.getGeoLanguage()) {
            synchronized (this.o) {
                y = null;
            }
        }
        this.r = this.c.getGeoLanguage();
    }

    public final boolean b() {
        return fq.b() - this.d <= 2800;
    }

    public final void c() {
        this.w = 0;
    }

    @SuppressLint({"NewApi"})
    public final int d() {
        if (this.b == null || !a(this.b)) {
            return 1;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            int i = Settings.Secure.getInt(this.z.getContentResolver(), "location_mode", 0);
            if (i == 0) {
                return 2;
            }
            if (i == 2) {
                return 3;
            }
        } else if (!this.b.isProviderEnabled("gps")) {
            return 2;
        }
        return !this.s ? 4 : 0;
    }

    public final int e() {
        return this.C;
    }

    public final boolean f() {
        return (this.c == null || this.c.isOnceLocation() || fq.b() - this.d <= 300000) ? false : true;
    }
}
