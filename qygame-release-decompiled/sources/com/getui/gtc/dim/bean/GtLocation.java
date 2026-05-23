package com.getui.gtc.dim.bean;

import android.location.Location;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.getui.gtc.dim.e.b;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GtLocation implements Parcelable {
    public static final Parcelable.Creator<GtLocation> CREATOR = new Parcelable.Creator<GtLocation>() { // from class: com.getui.gtc.dim.bean.GtLocation.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ GtLocation createFromParcel(Parcel parcel) {
            return new GtLocation(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ GtLocation[] newArray(int i) {
            return new GtLocation[i];
        }
    };
    private float accuracy;
    private double altitude;
    private long elapsedRealtimeNanos;
    private boolean hasAccuracy;
    private double latitude;
    private double longitude;
    private String provider;
    private long time;

    private GtLocation() {
    }

    public GtLocation(Location location) {
        this.hasAccuracy = location.hasAccuracy();
        this.accuracy = location.getAccuracy();
        this.time = location.getTime();
        this.provider = location.getProvider();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        if (Build.VERSION.SDK_INT >= 17) {
            this.elapsedRealtimeNanos = location.getElapsedRealtimeNanos();
        }
        this.altitude = location.getAltitude();
    }

    protected GtLocation(Parcel parcel) {
        this.hasAccuracy = parcel.readByte() != 0;
        this.time = parcel.readLong();
        this.provider = parcel.readString();
        this.longitude = parcel.readDouble();
        this.latitude = parcel.readDouble();
        this.elapsedRealtimeNanos = parcel.readLong();
        this.altitude = parcel.readDouble();
        this.accuracy = parcel.readFloat();
    }

    public static GtLocation parseJson(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            GtLocation gtLocation = new GtLocation();
            gtLocation.hasAccuracy = jSONObject.optBoolean("hasAccuracy", false);
            gtLocation.time = jSONObject.optLong("time", 0L);
            gtLocation.provider = jSONObject.optString("provider", "");
            gtLocation.longitude = jSONObject.optDouble("longitude", 0.0d);
            gtLocation.latitude = jSONObject.optDouble("latitude", 0.0d);
            gtLocation.elapsedRealtimeNanos = jSONObject.optLong("elapsedRealtimeNanos", 0L);
            gtLocation.altitude = jSONObject.optDouble("altitude", 0.0d);
            try {
                gtLocation.accuracy = Float.parseFloat(jSONObject.optString("accuracy", "0"));
            } catch (Throwable unused) {
            }
            return gtLocation;
        } catch (JSONException e) {
            b.b(e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float distanceTo(double d, double d2) {
        double dAtan2;
        double d3;
        double d4 = this.latitude;
        double d5 = (0.017453292519943295d * d2) - (this.longitude * 0.017453292519943295d);
        double dAtan = Math.atan(Math.tan(d4 * 0.017453292519943295d) * 0.996647189328169d);
        double dAtan3 = Math.atan(Math.tan(d * 0.017453292519943295d) * 0.996647189328169d);
        double dCos = Math.cos(dAtan);
        double dCos2 = Math.cos(dAtan3);
        double dSin = Math.sin(dAtan);
        double dSin2 = Math.sin(dAtan3);
        double d6 = dCos * dCos2;
        double d7 = dSin * dSin2;
        int i = 0;
        double d8 = d5;
        double d9 = 0.0d;
        double d10 = 0.0d;
        double d11 = 0.0d;
        while (true) {
            if (i >= 20) {
                dAtan2 = d9;
                d3 = d11;
                break;
            }
            double dCos3 = Math.cos(d8);
            double dSin3 = Math.sin(d8);
            double d12 = dCos2 * dSin3;
            double d13 = (dCos * dSin2) - ((dSin * dCos2) * dCos3);
            double d14 = dSin;
            double dSqrt = Math.sqrt((d12 * d12) + (d13 * d13));
            double d15 = dSin2;
            double d16 = d7 + (dCos3 * d6);
            dAtan2 = Math.atan2(dSqrt, d16);
            double d17 = dSqrt == 0.0d ? 0.0d : (dSin3 * d6) / dSqrt;
            double d18 = 1.0d - (d17 * d17);
            double d19 = d18 == 0.0d ? 0.0d : d16 - ((d7 * 2.0d) / d18);
            double d20 = 0.006739496756586903d * d18;
            d3 = ((d20 / 16384.0d) * (((((320.0d - (175.0d * d20)) * d20) - 768.0d) * d20) + 4096.0d)) + 1.0d;
            double d21 = (d20 / 1024.0d) * ((d20 * (((74.0d - (47.0d * d20)) * d20) - 128.0d)) + 256.0d);
            double d22 = 2.0955066698943685E-4d * d18 * (((4.0d - (d18 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d23 = d19 * d19;
            double d24 = d21 * dSqrt * (d19 + ((d21 / 4.0d) * ((((d23 * 2.0d) - 1.0d) * d16) - ((((d21 / 6.0d) * d19) * (((dSqrt * 4.0d) * dSqrt) - 3.0d)) * ((d23 * 4.0d) - 3.0d)))));
            double d25 = d5 + ((1.0d - d22) * 0.0033528106718309896d * d17 * ((dSqrt * d22 * (d19 + (d22 * d16 * (((2.0d * d19) * d19) - 1.0d)))) + dAtan2));
            if (Math.abs((d25 - d8) / d25) < 1.0E-12d) {
                d10 = d24;
                break;
            }
            i++;
            d8 = d25;
            d9 = dAtan2;
            d10 = d24;
            dSin = d14;
            dSin2 = d15;
            d11 = d3;
        }
        return (float) (d3 * 6356752.3142d * (dAtan2 - d10));
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public long getElapsedRealtimeNanos() {
        return this.elapsedRealtimeNanos;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getProvider() {
        return this.provider;
    }

    public long getTime() {
        return this.time;
    }

    public boolean hasAccuracy() {
        return this.hasAccuracy;
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("hasAccuracy", this.hasAccuracy);
            jSONObject.put("time", this.time);
            jSONObject.put("provider", this.provider);
            jSONObject.put("longitude", this.longitude);
            jSONObject.put("latitude", this.latitude);
            jSONObject.put("elapsedRealtimeNanos", this.elapsedRealtimeNanos);
            jSONObject.put("altitude", this.altitude);
            jSONObject.put("accuracy", String.valueOf(this.accuracy));
            return jSONObject.toString();
        } catch (Throwable th) {
            b.b(th);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.hasAccuracy ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.time);
        parcel.writeString(this.provider);
        parcel.writeDouble(this.longitude);
        parcel.writeDouble(this.latitude);
        parcel.writeLong(this.elapsedRealtimeNanos);
        parcel.writeDouble(this.altitude);
        parcel.writeFloat(this.accuracy);
    }
}
