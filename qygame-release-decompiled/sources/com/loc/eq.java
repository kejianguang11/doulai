package com.loc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public final class eq implements SensorEventListener {
    SensorManager a;
    Sensor b;
    Sensor c;
    Sensor d;
    private Context s;
    public boolean e = false;
    public double f = 0.0d;
    public float g = 0.0f;
    private float t = 1013.25f;
    private float u = 0.0f;
    Handler h = new Handler();
    double i = 0.0d;
    double j = 0.0d;
    double k = 0.0d;
    double l = 0.0d;
    double[] m = new double[3];
    volatile double n = 0.0d;
    long o = 0;
    long p = 0;
    final int q = 100;
    final int r = 30;

    public eq(Context context) {
        this.s = null;
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        try {
            this.s = context;
            if (this.a == null) {
                this.a = (SensorManager) this.s.getSystemService("sensor");
            }
            try {
                this.b = this.a.getDefaultSensor(6);
            } catch (Throwable unused) {
            }
            try {
                this.c = this.a.getDefaultSensor(11);
            } catch (Throwable unused2) {
            }
            try {
                this.d = this.a.getDefaultSensor(1);
            } catch (Throwable unused3) {
            }
        } catch (Throwable th) {
            fj.a(th, "AMapSensorManager", "<init>");
        }
    }

    private void a(float[] fArr) {
        this.m[0] = (this.m[0] * 0.800000011920929d) + ((double) (fArr[0] * 0.19999999f));
        this.m[1] = (this.m[1] * 0.800000011920929d) + ((double) (fArr[1] * 0.19999999f));
        this.m[2] = (this.m[2] * 0.800000011920929d) + ((double) (fArr[2] * 0.19999999f));
        this.i = ((double) fArr[0]) - this.m[0];
        this.j = ((double) fArr[1]) - this.m[1];
        this.k = ((double) fArr[2]) - this.m[2];
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.o < 100) {
            return;
        }
        double dSqrt = Math.sqrt((this.i * this.i) + (this.j * this.j) + (this.k * this.k));
        this.p++;
        this.o = jCurrentTimeMillis;
        this.n += dSqrt;
        if (this.p >= 30) {
            this.l = this.n / this.p;
            this.n = 0.0d;
            this.p = 0L;
        }
    }

    private void b(float[] fArr) {
        if (fArr != null) {
            this.f = fq.a(SensorManager.getAltitude(this.t, fArr[0]));
        }
    }

    private void c(float[] fArr) {
        if (fArr != null) {
            float[] fArr2 = new float[9];
            SensorManager.getRotationMatrixFromVector(fArr2, fArr);
            SensorManager.getOrientation(fArr2, new float[3]);
            this.u = (float) Math.toDegrees(r3[0]);
            this.u = (float) Math.floor(this.u > 0.0f ? this.u : this.u + 360.0f);
        }
    }

    public final void a() {
        if (this.a == null || this.e) {
            return;
        }
        this.e = true;
        try {
            if (this.b != null) {
                this.a.registerListener(this, this.b, 3, this.h);
            }
        } catch (Throwable th) {
            fj.a(th, "AMapSensorManager", "registerListener mPressure");
        }
        try {
            if (this.c != null) {
                this.a.registerListener(this, this.c, 3, this.h);
            }
        } catch (Throwable th2) {
            fj.a(th2, "AMapSensorManager", "registerListener mRotationVector");
        }
        try {
            if (this.d != null) {
                this.a.registerListener(this, this.d, 3, this.h);
            }
        } catch (Throwable th3) {
            fj.a(th3, "AMapSensorManager", "registerListener mAcceleroMeterVector");
        }
    }

    public final void b() {
        if (this.a == null || !this.e) {
            return;
        }
        this.e = false;
        try {
            if (this.b != null) {
                this.a.unregisterListener(this, this.b);
            }
        } catch (Throwable unused) {
        }
        try {
            if (this.c != null) {
                this.a.unregisterListener(this, this.c);
            }
        } catch (Throwable unused2) {
        }
        try {
            if (this.d != null) {
                this.a.unregisterListener(this, this.d);
            }
        } catch (Throwable unused3) {
        }
    }

    public final double c() {
        return this.f;
    }

    public final float d() {
        return this.u;
    }

    public final double e() {
        return this.l;
    }

    public final void f() {
        try {
            b();
            this.b = null;
            this.c = null;
            this.a = null;
            this.d = null;
            this.e = false;
        } catch (Throwable th) {
            fj.a(th, "AMapSensorManager", "destroy");
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent == null) {
            return;
        }
        try {
            int type = sensorEvent.sensor.getType();
            if (type == 1) {
                if (this.d != null) {
                    a((float[]) sensorEvent.values.clone());
                    return;
                }
                return;
            }
            if (type != 6) {
                if (type != 11) {
                    return;
                }
                try {
                    if (this.c != null) {
                        c((float[]) sensorEvent.values.clone());
                        return;
                    }
                    return;
                } catch (Throwable unused) {
                    return;
                }
            }
            try {
                if (this.b != null) {
                    float[] fArr = (float[]) sensorEvent.values.clone();
                    if (fArr != null) {
                        this.g = fArr[0];
                    }
                    b(fArr);
                }
            } catch (Throwable unused2) {
            }
        } catch (Throwable unused3) {
        }
    }
}
