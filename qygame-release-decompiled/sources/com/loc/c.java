package com.loc;

import android.os.Bundle;
import com.amap.api.fence.DistrictItem;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.PoiItem;
import com.amap.api.location.DPoint;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static long a;

    private static double a(DPoint dPoint, DPoint dPoint2, DPoint dPoint3) {
        double longitude;
        double latitude;
        double longitude2 = dPoint.getLongitude() - dPoint2.getLongitude();
        double latitude2 = dPoint.getLatitude() - dPoint2.getLatitude();
        double longitude3 = dPoint3.getLongitude() - dPoint2.getLongitude();
        double latitude3 = dPoint3.getLatitude() - dPoint2.getLatitude();
        double d = ((longitude2 * longitude3) + (latitude2 * latitude3)) / ((longitude3 * longitude3) + (latitude3 * latitude3));
        boolean z = dPoint2.getLongitude() == dPoint3.getLongitude() && dPoint2.getLatitude() == dPoint3.getLatitude();
        if (d < 0.0d || z) {
            longitude = dPoint2.getLongitude();
            latitude = dPoint2.getLatitude();
        } else if (d > 1.0d) {
            longitude = dPoint3.getLongitude();
            latitude = dPoint3.getLatitude();
        } else {
            double longitude4 = dPoint2.getLongitude() + (longitude3 * d);
            latitude = dPoint2.getLatitude() + (d * latitude3);
            longitude = longitude4;
        }
        return fq.a(new DPoint(dPoint.getLatitude(), dPoint.getLongitude()), new DPoint(latitude, longitude));
    }

    public static int a(String str, List<GeoFence> list, Bundle bundle) {
        int iOptInt;
        JSONArray jSONArrayOptJSONArray;
        int i;
        try {
            JSONObject jSONObject = new JSONObject(str);
            char c = 0;
            int iOptInt2 = jSONObject.optInt("status", 0);
            iOptInt = jSONObject.optInt("infocode", 0);
            if (iOptInt2 == 1 && (jSONArrayOptJSONArray = jSONObject.optJSONArray("pois")) != null) {
                int i2 = 0;
                while (i2 < jSONArrayOptJSONArray.length()) {
                    GeoFence geoFence = new GeoFence();
                    PoiItem poiItem = new PoiItem();
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                    poiItem.setPoiId(jSONObject2.optString(com.igexin.push.core.b.C));
                    poiItem.setPoiName(jSONObject2.optString("name"));
                    poiItem.setPoiType(jSONObject2.optString("type"));
                    poiItem.setTypeCode(jSONObject2.optString("typecode"));
                    poiItem.setAddress(jSONObject2.optString("address"));
                    String strOptString = jSONObject2.optString("location");
                    if (strOptString != null) {
                        String[] strArrSplit = strOptString.split(com.igexin.push.core.b.an);
                        poiItem.setLongitude(Double.parseDouble(strArrSplit[c]));
                        poiItem.setLatitude(Double.parseDouble(strArrSplit[1]));
                        List<List<DPoint>> arrayList = new ArrayList<>();
                        ArrayList arrayList2 = new ArrayList();
                        i = iOptInt;
                        DPoint dPoint = new DPoint(poiItem.getLatitude(), poiItem.getLongitude());
                        arrayList2.add(dPoint);
                        arrayList.add(arrayList2);
                        geoFence.setPointList(arrayList);
                        geoFence.setCenter(dPoint);
                    } else {
                        i = iOptInt;
                    }
                    poiItem.setTel(jSONObject2.optString("tel"));
                    poiItem.setProvince(jSONObject2.optString("pname"));
                    poiItem.setCity(jSONObject2.optString("cityname"));
                    poiItem.setAdname(jSONObject2.optString("adname"));
                    geoFence.setPoiItem(poiItem);
                    StringBuilder sb = new StringBuilder();
                    sb.append(a());
                    geoFence.setFenceId(sb.toString());
                    if (bundle != null) {
                        geoFence.setCustomId(bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID));
                        geoFence.setPendingIntentAction(bundle.getString("pendingIntentAction"));
                        geoFence.setType(2);
                        geoFence.setRadius(bundle.getFloat("fenceRadius"));
                        geoFence.setExpiration(bundle.getLong("expiration"));
                        geoFence.setActivatesAction(bundle.getInt("activatesAction", 1));
                    }
                    if (list != null) {
                        list.add(geoFence);
                    }
                    i2++;
                    iOptInt = i;
                    c = 0;
                }
            }
        } catch (Throwable unused) {
            iOptInt = 5;
        }
        return iOptInt;
    }

    public static synchronized long a() {
        long jB = fq.b();
        if (jB > a) {
            a = jB;
        } else {
            a++;
        }
        return a;
    }

    private List<DPoint> a(List<DPoint> list, float f) {
        if (list == null) {
            return null;
        }
        if (list.size() <= 2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        DPoint dPoint = list.get(0);
        DPoint dPoint2 = list.get(list.size() - 1);
        double d = 0.0d;
        int i = 0;
        for (int i2 = 1; i2 < list.size() - 1; i2++) {
            double dA = a(list.get(i2), dPoint, dPoint2);
            if (dA > d) {
                i = i2;
                d = dA;
            }
        }
        if (d < f) {
            arrayList.add(dPoint);
            arrayList.add(dPoint2);
            return arrayList;
        }
        List<DPoint> listA = a(list.subList(0, i + 1), f);
        List<DPoint> listA2 = a(list.subList(i, list.size()), f);
        arrayList.addAll(listA);
        arrayList.remove(arrayList.size() - 1);
        arrayList.addAll(listA2);
        return arrayList;
    }

    public static int b(String str, List<GeoFence> list, Bundle bundle) {
        return a(str, list, bundle);
    }

    public final int c(String str, List<GeoFence> list, Bundle bundle) {
        int iOptInt;
        JSONArray jSONArrayOptJSONArray;
        int i;
        ArrayList arrayList;
        String str2;
        int i2;
        String str3;
        String str4;
        float f;
        long j;
        boolean z;
        long j2;
        GeoFence geoFence;
        float f2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt2 = jSONObject.optInt("status", 0);
            iOptInt = jSONObject.optInt("infocode", 0);
            String string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
            String string2 = bundle.getString("pendingIntentAction");
            float f3 = bundle.getFloat("fenceRadius");
            long j3 = bundle.getLong("expiration");
            int i3 = bundle.getInt("activatesAction", 1);
            if (iOptInt2 == 1 && (jSONArrayOptJSONArray = jSONObject.optJSONArray("districts")) != null) {
                int i4 = 0;
                while (i4 < jSONArrayOptJSONArray.length()) {
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    GeoFence geoFence2 = new GeoFence();
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i4);
                    String strOptString = jSONObject2.optString("citycode");
                    String strOptString2 = jSONObject2.optString("adcode");
                    String strOptString3 = jSONObject2.optString("name");
                    JSONArray jSONArray = jSONArrayOptJSONArray;
                    String string3 = jSONObject2.getString("center");
                    int i5 = iOptInt;
                    DPoint dPoint = new DPoint();
                    if (string3 != null) {
                        i = i4;
                        String[] strArrSplit = string3.split(com.igexin.push.core.b.an);
                        arrayList = arrayList2;
                        str2 = strOptString3;
                        dPoint.setLatitude(Double.parseDouble(strArrSplit[1]));
                        dPoint.setLongitude(Double.parseDouble(strArrSplit[0]));
                        geoFence2.setCenter(dPoint);
                    } else {
                        i = i4;
                        arrayList = arrayList2;
                        str2 = strOptString3;
                    }
                    geoFence2.setCustomId(string);
                    geoFence2.setPendingIntentAction(string2);
                    geoFence2.setType(3);
                    geoFence2.setRadius(f3);
                    geoFence2.setExpiration(j3);
                    geoFence2.setActivatesAction(i3);
                    StringBuilder sb = new StringBuilder();
                    sb.append(a());
                    geoFence2.setFenceId(sb.toString());
                    String strOptString4 = jSONObject2.optString("polyline");
                    if (strOptString4 != null) {
                        String[] strArrSplit2 = strOptString4.split("\\|");
                        int length = strArrSplit2.length;
                        float fMin = Float.MAX_VALUE;
                        float fMax = Float.MIN_VALUE;
                        int i6 = 0;
                        while (i6 < length) {
                            int i7 = i3;
                            String str5 = strArrSplit2[i6];
                            String[] strArr = strArrSplit2;
                            DistrictItem districtItem = new DistrictItem();
                            String str6 = string;
                            List<DPoint> arrayList4 = new ArrayList<>();
                            districtItem.setCitycode(strOptString);
                            districtItem.setAdcode(strOptString2);
                            String str7 = strOptString2;
                            String str8 = str2;
                            districtItem.setDistrictName(str8);
                            String[] strArrSplit3 = str5.split(com.alipay.sdk.util.h.b);
                            String str9 = string2;
                            int i8 = 0;
                            while (i8 < strArrSplit3.length) {
                                String[] strArr2 = strArrSplit3;
                                String[] strArrSplit4 = strArrSplit3[i8].split(com.igexin.push.core.b.an);
                                float f4 = f3;
                                if (strArrSplit4.length > 1) {
                                    j2 = j3;
                                    geoFence = geoFence2;
                                    f2 = fMin;
                                    arrayList4.add(new DPoint(Double.parseDouble(strArrSplit4[1]), Double.parseDouble(strArrSplit4[0])));
                                } else {
                                    j2 = j3;
                                    geoFence = geoFence2;
                                    f2 = fMin;
                                }
                                i8++;
                                strArrSplit3 = strArr2;
                                f3 = f4;
                                j3 = j2;
                                geoFence2 = geoFence;
                                fMin = f2;
                            }
                            float f5 = f3;
                            long j4 = j3;
                            GeoFence geoFence3 = geoFence2;
                            float f6 = fMin;
                            if (arrayList4.size() > 100.0f) {
                                arrayList4 = a(arrayList4, 100.0f);
                            }
                            arrayList3.add(arrayList4);
                            districtItem.setPolyline(arrayList4);
                            ArrayList arrayList5 = arrayList;
                            arrayList5.add(districtItem);
                            fMax = Math.max(fMax, a.b(dPoint, arrayList4));
                            fMin = Math.min(f6, a.a(dPoint, arrayList4));
                            i6++;
                            arrayList = arrayList5;
                            i3 = i7;
                            strArrSplit2 = strArr;
                            string = str6;
                            strOptString2 = str7;
                            str2 = str8;
                            string2 = str9;
                            f3 = f5;
                            j3 = j4;
                            geoFence2 = geoFence3;
                        }
                        i2 = i3;
                        str3 = string;
                        str4 = string2;
                        f = f3;
                        j = j3;
                        GeoFence geoFence4 = geoFence2;
                        z = false;
                        geoFence4.setMaxDis2Center(fMax);
                        geoFence4.setMinDis2Center(fMin);
                        geoFence4.setDistrictItemList(arrayList);
                        geoFence4.setPointList(arrayList3);
                        list.add(geoFence4);
                    } else {
                        i2 = i3;
                        str3 = string;
                        str4 = string2;
                        f = f3;
                        j = j3;
                        z = false;
                    }
                    i4 = i + 1;
                    jSONArrayOptJSONArray = jSONArray;
                    iOptInt = i5;
                    i3 = i2;
                    string = str3;
                    string2 = str4;
                    f3 = f;
                    j3 = j;
                }
            }
        } catch (Throwable unused) {
            iOptInt = 5;
        }
        return iOptInt;
    }
}
