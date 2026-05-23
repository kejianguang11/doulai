package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseArray;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.gme.liteav.TXLiteAVCode;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fo {
    public SparseArray<Long> a = new SparseArray<>();
    public int b = -1;
    public long c = 0;
    String[] d = {"ol", "cl", "gl", "ha", "bs", "ds"};
    public int e = -1;
    public long f = -1;
    private static List<br> i = new ArrayList();
    private static JSONArray j = null;
    static AMapLocation g = null;
    static boolean h = false;

    private static String a(int i2) {
        switch (i2) {
            case 2011:
                return "ContextIsNull";
            case 2021:
                return "OnlyMainWifi";
            case 2022:
                return "OnlyOneWifiButNotMain";
            case 2031:
                return "CreateApsReqException";
            case 2041:
                return "ResponseResultIsNull";
            case 2051:
                return "NeedLoginNetWork\t";
            case 2052:
                return "MaybeIntercepted";
            case 2053:
                return "DecryptResponseException";
            case 2054:
                return "ParserDataException";
            case 2061:
                return "ServerRetypeError";
            case 2062:
                return "ServerLocFail";
            case 2081:
                return "LocalLocException";
            case 2091:
                return "InitException";
            case TXLiteAVCode.WARNING_VIDEO_FRAME_DECODE_FAIL /* 2101 */:
                return "BindAPSServiceException";
            case TXLiteAVCode.WARNING_AUDIO_FRAME_DECODE_FAIL /* 2102 */:
                return "AuthClientScodeFail";
            case 2103:
                return "NotConfigAPSService";
            case TXLiteAVCode.WARNING_VIDEO_DRIVER_VERSION_TOO_LOW /* 2111 */:
                return "ErrorCgiInfo";
            case 2121:
                return "NotLocPermission";
            case 2131:
                return "NoCgiOAndWifiInfo";
            case 2132:
                return "AirPlaneModeAndWifiOff";
            case 2133:
                return "NoCgiAndWifiOff";
            case 2141:
                return "NoEnoughStatellites";
            case 2151:
                return "MaybeMockNetLoc";
            case 2152:
                return "MaybeMockGPSLoc";
            case 2153:
                return "UNSUPPORT_COARSE_LBSLOC";
            case 2154:
                return "UNSUPPORT_CONTINUE_LOC";
            default:
                return "";
        }
    }

    public static void a(long j2, long j3) {
        try {
            if (h) {
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("gpsTime:");
            stringBuffer.append(fq.a(j2, "yyyy-MM-dd HH:mm:ss.SSS"));
            stringBuffer.append(com.igexin.push.core.b.an);
            stringBuffer.append("sysTime:");
            stringBuffer.append(fq.a(j3, "yyyy-MM-dd HH:mm:ss.SSS"));
            stringBuffer.append(com.igexin.push.core.b.an);
            long jT = fi.t();
            String strA = 0 != jT ? fq.a(jT, "yyyy-MM-dd HH:mm:ss.SSS") : "0";
            stringBuffer.append("serverTime:");
            stringBuffer.append(strA);
            a("checkgpstime", stringBuffer.toString());
            if (0 != jT && Math.abs(j2 - jT) < 31536000000L) {
                stringBuffer.append(", correctError");
                a("checkgpstimeerror", stringBuffer.toString());
            }
            stringBuffer.delete(0, stringBuffer.length());
            h = true;
        } catch (Throwable unused) {
        }
    }

    public static synchronized void a(Context context) {
        if (context != null) {
            try {
                if (fi.a()) {
                    if (i != null && i.size() > 0) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.addAll(i);
                        bs.b(arrayList, context);
                        i.clear();
                    }
                    f(context);
                    return;
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "destroy");
            }
        }
    }

    public static void a(Context context, int i2, int i3, long j2, long j3) {
        if (i2 == -1 || i3 == -1) {
            return;
        }
        try {
            a(context, "O012", i2, i3, j2, j3);
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "reportServiceAliveTime");
        }
    }

    public static void a(Context context, long j2, boolean z) {
        if (context != null) {
            try {
                if (fi.a()) {
                    a(context, j2, z, "O015");
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "reportGPSLocUseTime");
            }
        }
    }

    private static void a(Context context, long j2, boolean z, String str) {
        a(context, str, z ? "domestic" : "abroad", Long.valueOf(j2).intValue());
    }

    public static synchronized void a(Context context, AMapLocation aMapLocation) {
        int i2;
        try {
            if (fq.a(aMapLocation)) {
                boolean z = false;
                switch (aMapLocation.getLocationType()) {
                    case 1:
                        i2 = 0;
                        z = true;
                        break;
                    case 2:
                    case 4:
                        i2 = 1;
                        z = true;
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    case 10:
                    default:
                        i2 = 0;
                        break;
                    case 8:
                        i2 = 3;
                        z = true;
                        break;
                    case 9:
                        i2 = 2;
                        z = true;
                        break;
                    case 11:
                        i2 = 4;
                        z = true;
                        break;
                }
                if (z) {
                    int iC = fi.c();
                    if (iC != 0) {
                        if (i2 == 0 || i2 == 4) {
                            if (iC == 2) {
                                return;
                            }
                        } else if (iC == 1) {
                            return;
                        }
                    }
                    if (j == null) {
                        j = new JSONArray();
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("lon", fq.b(aMapLocation.getLongitude()));
                    jSONObject.put("lat", fq.b(aMapLocation.getLatitude()));
                    jSONObject.put("type", i2);
                    jSONObject.put(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP, fq.a());
                    if (aMapLocation.getCoordType().equalsIgnoreCase(AMapLocation.COORD_TYPE_WGS84)) {
                        jSONObject.put("coordType", 1);
                    } else {
                        jSONObject.put("coordType", 2);
                    }
                    if (i2 == 0) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("accuracy", fq.c(aMapLocation.getAccuracy()));
                        jSONObject2.put("altitude", fq.c(aMapLocation.getAltitude()));
                        jSONObject2.put("bearing", fq.c(aMapLocation.getBearing()));
                        jSONObject2.put("speed", fq.c(aMapLocation.getSpeed()));
                        jSONObject.put("extension", jSONObject2);
                    }
                    JSONArray jSONArrayPut = j.put(jSONObject);
                    j = jSONArrayPut;
                    if (jSONArrayPut.length() >= fi.b()) {
                        f(context);
                    }
                }
            }
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "recordOfflineLocLog");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(Context context, AMapLocation aMapLocation, ei eiVar) {
        String str;
        String str2;
        int i2;
        String str3;
        if (aMapLocation == null) {
            return;
        }
        try {
            if (!"gps".equalsIgnoreCase(aMapLocation.getProvider()) && aMapLocation.getLocationType() != 1) {
                String str4 = a(aMapLocation) ? "abroad" : "domestic";
                if (aMapLocation.getErrorCode() != 0) {
                    int errorCode = aMapLocation.getErrorCode();
                    if (errorCode != 11) {
                        switch (errorCode) {
                            case 4:
                            case 5:
                            case 6:
                                str3 = com.alipay.sdk.app.statistic.c.a;
                                break;
                            default:
                                str3 = "cache";
                                break;
                        }
                        i2 = 0;
                        str2 = str3;
                    }
                } else {
                    switch (aMapLocation.getLocationType()) {
                        case 5:
                        case 6:
                            str = com.alipay.sdk.app.statistic.c.a;
                            break;
                        default:
                            str = "cache";
                            break;
                    }
                    str2 = str;
                    i2 = 1;
                }
                a(context, "O016", str2, str4, i2, aMapLocation.getErrorCode(), eiVar);
            }
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "reportBatting");
        }
    }

    private static void a(Context context, String str, int i2, int i3, long j2, long j3) {
        if (context != null) {
            try {
                if (fi.a()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("param_int_first", i2);
                    jSONObject.put("param_int_second", i3);
                    jSONObject.put("param_long_first", j2);
                    jSONObject.put("param_long_second", j3);
                    a(context, str, jSONObject);
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "applyStatisticsEx");
            }
        }
    }

    private static void a(Context context, String str, String str2, int i2) {
        if (context != null) {
            try {
                if (fi.a()) {
                    JSONObject jSONObject = new JSONObject();
                    if (!TextUtils.isEmpty(str2)) {
                        jSONObject.put("param_string_first", str2);
                    }
                    if (!TextUtils.isEmpty(null)) {
                        jSONObject.put("param_string_second", (Object) null);
                    }
                    if (i2 != Integer.MAX_VALUE) {
                        jSONObject.put("param_int_first", i2);
                    }
                    a(context, str, jSONObject);
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "applyStatisticsEx");
            }
        }
    }

    private static void a(Context context, String str, String str2, String str3, int i2, int i3, ei eiVar) {
        if (context != null) {
            try {
                if (fi.a()) {
                    JSONObject jSONObject = new JSONObject();
                    if (!TextUtils.isEmpty(str2)) {
                        jSONObject.put("param_string_first", str2);
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        jSONObject.put("param_string_second", str3);
                    }
                    if (i2 != Integer.MAX_VALUE) {
                        jSONObject.put("param_int_first", i2);
                    }
                    if (i3 != Integer.MAX_VALUE) {
                        jSONObject.put("param_int_second", i3);
                    }
                    if (eiVar != null) {
                        if (!TextUtils.isEmpty(eiVar.d())) {
                            jSONObject.put("dns", eiVar.d());
                        }
                        if (!TextUtils.isEmpty(eiVar.e())) {
                            jSONObject.put("domain", eiVar.e());
                        }
                        if (!TextUtils.isEmpty(eiVar.f())) {
                            jSONObject.put("type", eiVar.f());
                        }
                        if (!TextUtils.isEmpty(eiVar.g())) {
                            jSONObject.put("reason", eiVar.g());
                        }
                        if (!TextUtils.isEmpty(eiVar.c())) {
                            jSONObject.put("ip", eiVar.c());
                        }
                        if (!TextUtils.isEmpty(eiVar.b())) {
                            jSONObject.put("stack", eiVar.b());
                        }
                        if (eiVar.h() > 0) {
                            jSONObject.put("ctime", String.valueOf(eiVar.h()));
                        }
                        if (eiVar.a() > 0) {
                            jSONObject.put("ntime", String.valueOf(eiVar.a()));
                        }
                    }
                    a(context, str, jSONObject);
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "applyStatisticsEx");
            }
        }
    }

    public static synchronized void a(Context context, String str, JSONObject jSONObject) {
        if (context != null) {
            try {
                if (fi.a()) {
                    br brVar = new br(context, "loc", "6.1.0", str);
                    if (jSONObject != null) {
                        brVar.a(jSONObject.toString());
                    }
                    i.add(brVar);
                    if (i.size() >= 30) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.addAll(i);
                        bs.b(arrayList, context);
                        i.clear();
                    }
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "applyStatistics");
            }
        }
    }

    public static void a(AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        try {
            if (g == null) {
                if (!fq.a(aMapLocation)) {
                    g = aMapLocation2;
                    return;
                }
                g = aMapLocation.m6clone();
            }
            if (fq.a(g) && fq.a(aMapLocation2)) {
                AMapLocation aMapLocationM6clone = aMapLocation2.m6clone();
                if (g.getLocationType() != 1 && g.getLocationType() != 9 && !"gps".equalsIgnoreCase(g.getProvider()) && g.getLocationType() != 7 && aMapLocationM6clone.getLocationType() != 1 && aMapLocationM6clone.getLocationType() != 9 && !"gps".equalsIgnoreCase(aMapLocationM6clone.getProvider()) && aMapLocationM6clone.getLocationType() != 7) {
                    long jAbs = Math.abs(aMapLocationM6clone.getTime() - g.getTime()) / 1000;
                    if (jAbs <= 0) {
                        jAbs = 1;
                    }
                    if (jAbs <= 1800) {
                        float fA = fq.a(g, aMapLocationM6clone);
                        float f = fA / jAbs;
                        if (fA > 30000.0f && f > 1000.0f) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(g.getLatitude());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(g.getLongitude());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(g.getAccuracy());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(g.getLocationType());
                            sb.append(com.igexin.push.core.b.an);
                            if (aMapLocation.getTime() != 0) {
                                sb.append(fq.a(g.getTime(), "yyyyMMdd_HH:mm:ss:SS"));
                            } else {
                                sb.append(g.getTime());
                            }
                            sb.append("#");
                            sb.append(aMapLocationM6clone.getLatitude());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(aMapLocationM6clone.getLongitude());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(aMapLocationM6clone.getAccuracy());
                            sb.append(com.igexin.push.core.b.an);
                            sb.append(aMapLocationM6clone.getLocationType());
                            sb.append(com.igexin.push.core.b.an);
                            if (aMapLocationM6clone.getTime() != 0) {
                                sb.append(fq.a(aMapLocationM6clone.getTime(), "yyyyMMdd_HH:mm:ss:SS"));
                            } else {
                                sb.append(aMapLocationM6clone.getTime());
                            }
                            a("bigshiftstatistics", sb.toString());
                            sb.delete(0, sb.length());
                        }
                    }
                }
                g = aMapLocationM6clone;
            }
        } catch (Throwable unused) {
        }
    }

    public static void a(String str, int i2) {
        a(str, String.valueOf(i2), a(i2));
    }

    public static void a(String str, String str2) {
        try {
            an.b(fj.c(), str2, str);
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "reportLog");
        }
    }

    public static void a(String str, String str2, String str3) {
        try {
            an.a(fj.c(), "/mobile/binary", str3, str, str2);
        } catch (Throwable unused) {
        }
    }

    public static void a(String str, Throwable th) {
        try {
            if (th instanceof k) {
                an.a(fj.c(), str, (k) th);
            }
        } catch (Throwable unused) {
        }
    }

    private static boolean a(AMapLocation aMapLocation) {
        return fq.a(aMapLocation) ? !fj.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()) : "http://abroad.apilocate.amap.com/mobile/binary".equals(fj.c);
    }

    public static void b(Context context, long j2, boolean z) {
        if (context != null) {
            try {
                if (fi.a()) {
                    a(context, j2, z, "O024");
                }
            } catch (Throwable th) {
                fj.a(th, "ReportUtil", "reportCoarseLocUseTime");
            }
        }
    }

    private static void f(Context context) {
        try {
            if (j == null || j.length() <= 0) {
                return;
            }
            bq.a(new bp(context, fj.c(), j.toString()), context);
            j = null;
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "writeOfflineLocLog");
        }
    }

    public final void a(Context context, int i2) {
        try {
            if (this.b == i2) {
                return;
            }
            if (this.b != -1 && this.b != i2) {
                this.a.append(this.b, Long.valueOf((fq.b() - this.c) + this.a.get(this.b, 0L).longValue()));
            }
            this.c = fq.b() - fp.a(context, "pref1", this.d[i2], 0L);
            this.b = i2;
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "setLocationType");
        }
    }

    public final void a(Context context, AMapLocationClientOption aMapLocationClientOption) {
        int i2;
        try {
            switch (aMapLocationClientOption.getLocationMode()) {
                case Battery_Saving:
                    i2 = 4;
                    break;
                case Device_Sensors:
                    i2 = 5;
                    break;
                case Hight_Accuracy:
                    i2 = 3;
                    break;
                default:
                    i2 = -1;
                    break;
            }
            if (this.e == i2) {
                return;
            }
            if (this.e != -1 && this.e != i2) {
                this.a.append(this.e, Long.valueOf((fq.b() - this.f) + this.a.get(this.e, 0L).longValue()));
            }
            this.f = fq.b() - fp.a(context, "pref1", this.d[i2], 0L);
            this.e = i2;
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "setLocationMode");
        }
    }

    public final void b(Context context) {
        try {
            long jB = fq.b() - this.c;
            if (this.b != -1) {
                this.a.append(this.b, Long.valueOf(jB + this.a.get(this.b, 0L).longValue()));
            }
            long jB2 = fq.b() - this.f;
            if (this.e != -1) {
                this.a.append(this.e, Long.valueOf(jB2 + this.a.get(this.e, 0L).longValue()));
            }
            SharedPreferences.Editor editorA = fp.a(context, "pref1");
            for (int i2 = 0; i2 < this.d.length; i2++) {
                long jLongValue = this.a.get(i2, 0L).longValue();
                if (jLongValue > 0 && jLongValue > fp.a(context, "pref1", this.d[i2], 0L)) {
                    fp.a(editorA, this.d[i2], jLongValue);
                }
            }
            fp.a(editorA);
        } catch (Throwable th) {
            fj.a(th, "ReportUtil", "saveLocationTypeAndMode");
        }
    }

    public final int c(Context context) {
        try {
            long jA = fp.a(context, "pref1", this.d[2], 0L);
            long jA2 = fp.a(context, "pref1", this.d[0], 0L);
            long jA3 = fp.a(context, "pref1", this.d[1], 0L);
            if (jA == 0 && jA2 == 0 && jA3 == 0) {
                return -1;
            }
            long j2 = jA2 - jA;
            long j3 = jA3 - jA;
            return jA > j2 ? jA > j3 ? 2 : 1 : j2 > j3 ? 0 : 1;
        } catch (Throwable unused) {
            return -1;
        }
    }

    public final int d(Context context) {
        try {
            long jA = fp.a(context, "pref1", this.d[3], 0L);
            long jA2 = fp.a(context, "pref1", this.d[4], 0L);
            long jA3 = fp.a(context, "pref1", this.d[5], 0L);
            if (jA == 0 && jA2 == 0 && jA3 == 0) {
                return -1;
            }
            return jA > jA2 ? jA > jA3 ? 3 : 5 : jA2 > jA3 ? 4 : 5;
        } catch (Throwable unused) {
            return -1;
        }
    }

    public final void e(Context context) {
        try {
            SharedPreferences.Editor editorA = fp.a(context, "pref1");
            for (int i2 = 0; i2 < this.d.length; i2++) {
                fp.a(editorA, this.d[i2], 0L);
            }
            fp.a(editorA);
        } catch (Throwable unused) {
        }
    }
}
