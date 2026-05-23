package org.cocos2dx.lib;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxAccelerometer implements SensorEventListener {
    private static final String TAG = "Cocos2dxAccelerometer";
    private final Sensor mAcceleration;
    private final Sensor mAccelerationIncludingGravity;
    private final Context mContext;
    private final Sensor mGyroscope;
    private final SensorManager mSensorManager;
    private int mSamplingPeriodUs = 1;
    private DeviceMotionEvent mDeviceMotionEvent = new DeviceMotionEvent();

    class Acceleration {
        public float x = 0.0f;
        public float y = 0.0f;
        public float z = 0.0f;

        Acceleration() {
        }
    }

    class DeviceMotionEvent {
        public Acceleration acceleration;
        public Acceleration accelerationIncludingGravity;
        public RotationRate rotationRate;

        DeviceMotionEvent() {
            this.acceleration = Cocos2dxAccelerometer.this.new Acceleration();
            this.accelerationIncludingGravity = Cocos2dxAccelerometer.this.new Acceleration();
            this.rotationRate = Cocos2dxAccelerometer.this.new RotationRate();
        }
    }

    class RotationRate {
        public float alpha = 0.0f;
        public float beta = 0.0f;
        public float gamma = 0.0f;

        RotationRate() {
        }
    }

    public Cocos2dxAccelerometer(Context context) {
        this.mContext = context;
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.mAcceleration = this.mSensorManager.getDefaultSensor(1);
        this.mAccelerationIncludingGravity = this.mSensorManager.getDefaultSensor(10);
        this.mGyroscope = this.mSensorManager.getDefaultSensor(4);
    }

    public static native void onSensorChanged(float f, float f2, float f3, long j);

    public void disable() {
        this.mSensorManager.unregisterListener(this);
    }

    public void enable() {
        this.mSensorManager.registerListener(this, this.mAcceleration, this.mSamplingPeriodUs);
        this.mSensorManager.registerListener(this, this.mAccelerationIncludingGravity, this.mSamplingPeriodUs);
        this.mSensorManager.registerListener(this, this.mGyroscope, this.mSamplingPeriodUs);
    }

    public DeviceMotionEvent getDeviceMotionEvent() {
        return this.mDeviceMotionEvent;
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        Acceleration acceleration;
        float f;
        int type = sensorEvent.sensor.getType();
        if (type == 1) {
            this.mDeviceMotionEvent.accelerationIncludingGravity.x = sensorEvent.values[0];
            this.mDeviceMotionEvent.accelerationIncludingGravity.y = sensorEvent.values[1];
            acceleration = this.mDeviceMotionEvent.accelerationIncludingGravity;
            f = sensorEvent.values[2];
        } else {
            if (type != 10) {
                if (type == 4) {
                    this.mDeviceMotionEvent.rotationRate.alpha = (float) Math.toDegrees(sensorEvent.values[0]);
                    this.mDeviceMotionEvent.rotationRate.beta = (float) Math.toDegrees(sensorEvent.values[1]);
                    this.mDeviceMotionEvent.rotationRate.gamma = (float) Math.toDegrees(sensorEvent.values[2]);
                    return;
                }
                return;
            }
            this.mDeviceMotionEvent.acceleration.x = sensorEvent.values[0];
            this.mDeviceMotionEvent.acceleration.y = sensorEvent.values[1];
            acceleration = this.mDeviceMotionEvent.acceleration;
            f = sensorEvent.values[2];
        }
        acceleration.z = f;
    }

    public void setInterval(float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.mSamplingPeriodUs = (int) (f * 1000000.0f);
        }
        disable();
        enable();
    }
}
