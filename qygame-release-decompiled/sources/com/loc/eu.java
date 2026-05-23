package com.loc;

import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class eu {
    public int a = 0;
    public double b = 0.0d;
    public double c = 0.0d;
    public long d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 63;
    public int h = 0;

    public final String a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put("time", this.d);
            jSONObject.put("lon", this.c);
            jSONObject.put("lat", this.b);
            jSONObject.put("radius", this.e);
            jSONObject.put("locationType", this.a);
            jSONObject.put("reType", this.g);
            jSONObject.put("reSubType", this.h);
        } catch (Throwable unused) {
            jSONObject = null;
        }
        return jSONObject == null ? "" : jSONObject.toString();
    }

    public final void a(JSONObject jSONObject) {
        try {
            this.b = jSONObject.optDouble("lat", this.b);
            this.c = jSONObject.optDouble("lon", this.c);
            this.a = jSONObject.optInt("locationType", this.a);
            this.g = jSONObject.optInt("reType", this.g);
            this.h = jSONObject.optInt("reSubType", this.h);
            this.e = jSONObject.optInt("radius", this.e);
            this.d = jSONObject.optLong("time", this.d);
        } catch (Throwable th) {
            fj.a(th, "CoreUtil", "transformLocation");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            eu euVar = (eu) obj;
            if (this.a == euVar.a && Double.compare(euVar.b, this.b) == 0 && Double.compare(euVar.c, this.c) == 0 && this.d == euVar.d && this.e == euVar.e && this.f == euVar.f && this.g == euVar.g && this.h == euVar.h) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Double.valueOf(this.b), Double.valueOf(this.c), Long.valueOf(this.d), Integer.valueOf(this.e), Integer.valueOf(this.f), Integer.valueOf(this.g), Integer.valueOf(this.h));
    }
}
