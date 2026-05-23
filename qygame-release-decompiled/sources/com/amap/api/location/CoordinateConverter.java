package com.amap.api.location;

import android.content.Context;
import android.text.TextUtils;
import com.loc.fj;
import com.loc.fl;
import com.loc.fo;
import com.loc.fq;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CoordinateConverter {
    private static int b = 0;
    private static int c = 1;
    private static int d = 2;
    private static int e = 4;
    private static int f = 8;
    private static int g = 16;
    private static int h = 32;
    private static int i = 64;
    private Context j;
    private CoordType k = null;
    private DPoint l = null;
    DPoint a = null;

    public enum CoordType {
        BAIDU,
        MAPBAR,
        MAPABC,
        SOSOMAP,
        ALIYUN,
        GOOGLE,
        GPS
    }

    public CoordinateConverter(Context context) {
        this.j = context;
    }

    public static float calculateLineDistance(DPoint dPoint, DPoint dPoint2) {
        try {
            return fq.a(dPoint, dPoint2);
        } catch (Throwable unused) {
            return 0.0f;
        }
    }

    public static boolean isAMapDataAvailable(double d2, double d3) {
        return fj.a(d2, d3);
    }

    public synchronized DPoint convert() throws Exception {
        int i2;
        int i3;
        DPoint dPointA;
        if (this.k == null) {
            throw new IllegalArgumentException("转换坐标类型不能为空");
        }
        if (this.l == null) {
            throw new IllegalArgumentException("转换坐标源不能为空");
        }
        if (this.l.getLongitude() > 180.0d || this.l.getLongitude() < -180.0d) {
            throw new IllegalArgumentException("请传入合理经度");
        }
        if (this.l.getLatitude() > 90.0d || this.l.getLatitude() < -90.0d) {
            throw new IllegalArgumentException("请传入合理纬度");
        }
        boolean z = false;
        String str = null;
        switch (this.k) {
            case BAIDU:
                this.a = fl.a(this.l);
                if ((b & c) == 0) {
                    str = "baidu";
                    i2 = b;
                    i3 = c;
                    b = i2 | i3;
                    z = true;
                }
                break;
            case MAPBAR:
                this.a = fl.b(this.j, this.l);
                if ((b & d) == 0) {
                    str = "mapbar";
                    i2 = b;
                    i3 = d;
                    b = i2 | i3;
                    z = true;
                }
                break;
            case MAPABC:
                if ((b & e) == 0) {
                    str = "mapabc";
                    b |= e;
                    z = true;
                }
                dPointA = this.l;
                this.a = dPointA;
                break;
            case SOSOMAP:
                if ((b & f) == 0) {
                    str = "sosomap";
                    b |= f;
                    z = true;
                }
                dPointA = this.l;
                this.a = dPointA;
                break;
            case ALIYUN:
                if ((b & g) == 0) {
                    str = "aliyun";
                    b |= g;
                    z = true;
                }
                dPointA = this.l;
                this.a = dPointA;
                break;
            case GOOGLE:
                if ((b & h) == 0) {
                    str = "google";
                    b |= h;
                    z = true;
                }
                dPointA = this.l;
                this.a = dPointA;
                break;
            case GPS:
                if ((b & i) == 0) {
                    b |= i;
                    str = "gps";
                    z = true;
                }
                dPointA = fl.a(this.j, this.l);
                this.a = dPointA;
                break;
        }
        if (z) {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("amap_loc_coordinate", str);
            }
            fo.a(this.j, "O021", jSONObject);
        }
        return this.a;
    }

    public synchronized CoordinateConverter coord(DPoint dPoint) throws Exception {
        if (dPoint == null) {
            throw new IllegalArgumentException("传入经纬度对象为空");
        }
        if (dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
            throw new IllegalArgumentException("请传入合理经度");
        }
        if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d) {
            throw new IllegalArgumentException("请传入合理纬度");
        }
        this.l = dPoint;
        return this;
    }

    public synchronized CoordinateConverter from(CoordType coordType) {
        this.k = coordType;
        return this;
    }
}
