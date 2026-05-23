package com.loc;

import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class eo extends AMapLocation {
    protected String d;
    boolean e;
    String f;
    private String g;
    private String h;
    private int i;
    private String j;
    private int k;
    private String l;
    private JSONObject m;
    private String n;
    private String o;
    private String p;

    public eo(String str) {
        super(str);
        this.d = "";
        this.g = null;
        this.h = "";
        this.j = "";
        this.k = 0;
        this.l = "new";
        this.m = null;
        this.n = "";
        this.e = true;
        this.f = String.valueOf(AMapLocationClientOption.GeoLanguage.DEFAULT);
        this.o = "";
        this.p = null;
    }

    private void i(String str) {
        this.n = str;
    }

    private void j(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] strArrSplit = str.split("\\*");
        int length = strArrSplit.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str2 = strArrSplit[i];
            if (!TextUtils.isEmpty(str2)) {
                String[] strArrSplit2 = str2.split(com.igexin.push.core.b.an);
                setLongitude(fq.c(strArrSplit2[0]));
                setLatitude(fq.c(strArrSplit2[1]));
                setAccuracy(fq.e(strArrSplit2[2]));
                break;
            }
            i++;
        }
        this.o = str;
    }

    public final String a() {
        return this.g;
    }

    public final void a(int i) {
        this.k = i;
    }

    public final void a(String str) {
        this.g = str;
    }

    public final void a(JSONObject jSONObject) {
        this.m = jSONObject;
    }

    public final void a(boolean z) {
        this.e = z;
    }

    public final String b() {
        return this.h;
    }

    public final void b(String str) {
        this.h = str;
    }

    public final void b(JSONObject jSONObject) {
        try {
            fj.a(this, jSONObject);
            e(jSONObject.optString("type", this.l));
            d(jSONObject.optString("retype", this.j));
            j(jSONObject.optString("cens", this.o));
            g(jSONObject.optString("desc", this.d));
            c(jSONObject.optString("coord", String.valueOf(this.i)));
            i(jSONObject.optString("mcell", this.n));
            a(jSONObject.optBoolean("isReversegeo", this.e));
            f(jSONObject.optString("geoLanguage", this.f));
            if (fq.a(jSONObject, "poiid")) {
                setBuildingId(jSONObject.optString("poiid"));
            }
            if (fq.a(jSONObject, PushConsts.KEY_SERVICE_PIT)) {
                setBuildingId(jSONObject.optString(PushConsts.KEY_SERVICE_PIT));
            }
            if (fq.a(jSONObject, "floor")) {
                setFloor(jSONObject.optString("floor"));
            }
            if (fq.a(jSONObject, "flr")) {
                setFloor(jSONObject.optString("flr"));
            }
        } catch (Throwable th) {
            fj.a(th, "AmapLoc", "AmapLoc");
        }
    }

    public final int c() {
        return this.i;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void c(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = -1;
        } else if (str.equals("0")) {
            i = 0;
        } else if (str.equals("1")) {
            i = 1;
        }
        this.i = i;
        super.setCoordType(this.i == 0 ? AMapLocation.COORD_TYPE_WGS84 : AMapLocation.COORD_TYPE_GCJ02);
    }

    public final String d() {
        return this.j;
    }

    public final void d(String str) {
        this.j = str;
    }

    public final String e() {
        return this.l;
    }

    public final void e(String str) {
        this.l = str;
    }

    public final JSONObject f() {
        return this.m;
    }

    public final void f(String str) {
        this.f = str;
    }

    public final String g() {
        return this.n;
    }

    public final void g(String str) {
        this.d = str;
    }

    public final eo h() {
        String strG = g();
        if (TextUtils.isEmpty(strG)) {
            return null;
        }
        String[] strArrSplit = strG.split(com.igexin.push.core.b.an);
        if (strArrSplit.length != 3) {
            return null;
        }
        eo eoVar = new eo("");
        eoVar.setProvider(getProvider());
        eoVar.setLongitude(fq.c(strArrSplit[0]));
        eoVar.setLatitude(fq.c(strArrSplit[1]));
        eoVar.setAccuracy(fq.d(strArrSplit[2]));
        eoVar.setCityCode(getCityCode());
        eoVar.setAdCode(getAdCode());
        eoVar.setCountry(getCountry());
        eoVar.setProvince(getProvince());
        eoVar.setCity(getCity());
        eoVar.setTime(getTime());
        eoVar.e(e());
        eoVar.c(String.valueOf(c()));
        if (fq.a(eoVar)) {
            return eoVar;
        }
        return null;
    }

    public final void h(String str) {
        this.p = str;
    }

    public final boolean i() {
        return this.e;
    }

    public final String j() {
        return this.f;
    }

    public final String k() {
        return this.p;
    }

    public final int l() {
        return this.k;
    }

    @Override // com.amap.api.location.AMapLocation
    public final JSONObject toJson(int i) {
        try {
            JSONObject json = super.toJson(i);
            switch (i) {
                case 1:
                    json.put("retype", this.j);
                    json.put("cens", this.o);
                    json.put("coord", this.i);
                    json.put("mcell", this.n);
                    json.put("desc", this.d);
                    json.put("address", getAddress());
                    if (this.m != null && fq.a(json, "offpct")) {
                        json.put("offpct", this.m.getString("offpct"));
                        break;
                    }
                case 2:
                case 3:
                    json.put("type", this.l);
                    json.put("isReversegeo", this.e);
                    json.put("geoLanguage", this.f);
                    return json;
                default:
                    return json;
            }
        } catch (Throwable th) {
            fj.a(th, "AmapLoc", "toStr");
            return null;
        }
    }

    @Override // com.amap.api.location.AMapLocation
    public final String toStr() {
        return toStr(1);
    }

    @Override // com.amap.api.location.AMapLocation
    public final String toStr(int i) {
        JSONObject json;
        try {
            json = toJson(i);
            json.put("nb", this.p);
        } catch (Throwable th) {
            fj.a(th, "AMapLocation", "toStr part2");
            json = null;
        }
        if (json == null) {
            return null;
        }
        return json.toString();
    }
}
