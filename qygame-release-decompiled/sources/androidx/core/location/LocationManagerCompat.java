package androidx.core.location;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class LocationManagerCompat {
    private static final long PRE_N_LOOPER_TIMEOUT_S = 4;
    private static Field sContextField;

    @GuardedBy("sGnssStatusListeners")
    private static final SimpleArrayMap<Object, Object> sGnssStatusListeners = new SimpleArrayMap<>();

    @RequiresApi(28)
    private static class Api28Impl {
        private Api28Impl() {
        }

        public static boolean isLocationEnabled(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }
    }

    @RequiresApi(30)
    private static class GnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback a;

        GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.a = callback;
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(int i) {
            this.a.onFirstFix(i);
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            this.a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            this.a.onStarted();
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            this.a.onStopped();
        }
    }

    private static class GpsStatusTransport implements GpsStatus.Listener {
        final GnssStatusCompat.Callback a;

        @Nullable
        volatile Executor b;
        private final LocationManager mLocationManager;

        GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.a = callback;
        }

        @Override // android.location.GpsStatus.Listener
        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        public void onGpsStatusChanged(int i) {
            Runnable runnable;
            Runnable runnable2;
            final Executor executor = this.b;
            if (executor == null) {
            }
            switch (i) {
                case 1:
                    runnable = new Runnable() { // from class: androidx.core.location.LocationManagerCompat.GpsStatusTransport.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (GpsStatusTransport.this.b != executor) {
                                return;
                            }
                            GpsStatusTransport.this.a.onStarted();
                        }
                    };
                    executor.execute(runnable);
                    break;
                case 2:
                    runnable = new Runnable() { // from class: androidx.core.location.LocationManagerCompat.GpsStatusTransport.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (GpsStatusTransport.this.b != executor) {
                                return;
                            }
                            GpsStatusTransport.this.a.onStopped();
                        }
                    };
                    executor.execute(runnable);
                    break;
                case 3:
                    GpsStatus gpsStatus = this.mLocationManager.getGpsStatus(null);
                    if (gpsStatus != null) {
                        final int timeToFirstFix = gpsStatus.getTimeToFirstFix();
                        runnable2 = new Runnable() { // from class: androidx.core.location.LocationManagerCompat.GpsStatusTransport.3
                            @Override // java.lang.Runnable
                            public void run() {
                                if (GpsStatusTransport.this.b != executor) {
                                    return;
                                }
                                GpsStatusTransport.this.a.onFirstFix(timeToFirstFix);
                            }
                        };
                        executor.execute(runnable2);
                    }
                    break;
                case 4:
                    GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus(null);
                    if (gpsStatus2 != null) {
                        final GnssStatusCompat gnssStatusCompatWrap = GnssStatusCompat.wrap(gpsStatus2);
                        runnable2 = new Runnable() { // from class: androidx.core.location.LocationManagerCompat.GpsStatusTransport.4
                            @Override // java.lang.Runnable
                            public void run() {
                                if (GpsStatusTransport.this.b != executor) {
                                    return;
                                }
                                GpsStatusTransport.this.a.onSatelliteStatusChanged(gnssStatusCompatWrap);
                            }
                        };
                        executor.execute(runnable2);
                    }
                    break;
            }
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.b == null);
            this.b = executor;
        }

        public void unregister() {
            this.b = null;
        }
    }

    private static class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        InlineHandlerExecutor(@NonNull Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                runnable.run();
            } else {
                if (this.mHandler.post((Runnable) Preconditions.checkNotNull(runnable))) {
                    return;
                }
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    @RequiresApi(24)
    private static class PreRGnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback a;

        @Nullable
        volatile Executor b;

        PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.a = callback;
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(final int i) {
            final Executor executor = this.b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.PreRGnssStatusTransport.3
                @Override // java.lang.Runnable
                public void run() {
                    if (PreRGnssStatusTransport.this.b != executor) {
                        return;
                    }
                    PreRGnssStatusTransport.this.a.onFirstFix(i);
                }
            });
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(final GnssStatus gnssStatus) {
            final Executor executor = this.b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.PreRGnssStatusTransport.4
                @Override // java.lang.Runnable
                public void run() {
                    if (PreRGnssStatusTransport.this.b != executor) {
                        return;
                    }
                    PreRGnssStatusTransport.this.a.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
                }
            });
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            final Executor executor = this.b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.PreRGnssStatusTransport.1
                @Override // java.lang.Runnable
                public void run() {
                    if (PreRGnssStatusTransport.this.b != executor) {
                        return;
                    }
                    PreRGnssStatusTransport.this.a.onStarted();
                }
            });
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            final Executor executor = this.b;
            if (executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.core.location.LocationManagerCompat.PreRGnssStatusTransport.2
                @Override // java.lang.Runnable
                public void run() {
                    if (PreRGnssStatusTransport.this.b != executor) {
                        return;
                    }
                    PreRGnssStatusTransport.this.a.onStopped();
                }
            });
        }

        public void register(Executor executor) {
            Preconditions.checkArgument(executor != null, "invalid null executor");
            Preconditions.checkState(this.b == null);
            this.b = executor;
        }

        public void unregister() {
            this.b = null;
        }
    }

    private LocationManagerCompat() {
    }

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.isLocationEnabled(locationManager);
        }
        if (Build.VERSION.SDK_INT <= 19) {
            try {
                if (sContextField == null) {
                    sContextField = LocationManager.class.getDeclaredField("mContext");
                }
                sContextField.setAccessible(true);
                return Build.VERSION.SDK_INT == 19 ? Settings.Secure.getInt(((Context) sContextField.get(locationManager)).getContentResolver(), "location_mode", 0) != 0 : !TextUtils.isEmpty(Settings.Secure.getString(r0.getContentResolver(), "location_providers_allowed"));
            } catch (ClassCastException | IllegalAccessException | NoSuchFieldException | SecurityException unused) {
            }
        }
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x011a A[Catch: all -> 0x00f7, TryCatch #8 {all -> 0x00f7, blocks: (B:54:0x00a4, B:84:0x00fa, B:85:0x0110, B:87:0x0112, B:89:0x011a, B:91:0x0122, B:92:0x0128, B:93:0x0129, B:94:0x012e, B:95:0x012f, B:96:0x0135), top: B:107:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x012f A[Catch: all -> 0x00f7, TryCatch #8 {all -> 0x00f7, blocks: (B:54:0x00a4, B:84:0x00fa, B:85:0x0110, B:87:0x0112, B:89:0x011a, B:91:0x0122, B:92:0x0128, B:93:0x0129, B:94:0x012e, B:95:0x012f, B:96:0x0135), top: B:107:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0138 A[Catch: all -> 0x0157, TRY_ENTER, TryCatch #9 {, blocks: (B:45:0x0070, B:47:0x007a, B:49:0x0083, B:51:0x009a, B:60:0x00c8, B:61:0x00cf, B:64:0x00d3, B:65:0x00da, B:98:0x0138, B:99:0x013f, B:52:0x009e, B:100:0x0140, B:101:0x0156, B:48:0x0080), top: B:108:0x0070 }] */
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean registerGnssStatusCallback(final LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 30) {
            synchronized (sGnssStatusListeners) {
                GnssStatus.Callback gnssStatusTransport = (GnssStatusTransport) sGnssStatusListeners.get(callback);
                if (gnssStatusTransport == null) {
                    gnssStatusTransport = new GnssStatusTransport(callback);
                }
                if (!locationManager.registerGnssStatusCallback(executor, gnssStatusTransport)) {
                    return false;
                }
                sGnssStatusListeners.put(callback, gnssStatusTransport);
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Preconditions.checkArgument(handler != null);
            synchronized (sGnssStatusListeners) {
                PreRGnssStatusTransport preRGnssStatusTransport = (PreRGnssStatusTransport) sGnssStatusListeners.get(callback);
                if (preRGnssStatusTransport == null) {
                    preRGnssStatusTransport = new PreRGnssStatusTransport(callback);
                } else {
                    preRGnssStatusTransport.unregister();
                }
                preRGnssStatusTransport.register(executor);
                if (!locationManager.registerGnssStatusCallback(preRGnssStatusTransport, handler)) {
                    return false;
                }
                sGnssStatusListeners.put(callback, preRGnssStatusTransport);
                return true;
            }
        }
        Preconditions.checkArgument(handler != null);
        synchronized (sGnssStatusListeners) {
            final GpsStatusTransport gpsStatusTransport = (GpsStatusTransport) sGnssStatusListeners.get(callback);
            if (gpsStatusTransport == null) {
                gpsStatusTransport = new GpsStatusTransport(locationManager, callback);
            } else {
                gpsStatusTransport.unregister();
            }
            gpsStatusTransport.register(executor);
            FutureTask futureTask = new FutureTask(new Callable<Boolean>() { // from class: androidx.core.location.LocationManagerCompat.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
                public Boolean call() {
                    return Boolean.valueOf(locationManager.addGpsStatusListener(gpsStatusTransport));
                }
            });
            if (Looper.myLooper() == handler.getLooper()) {
                futureTask.run();
            } else if (!handler.post(futureTask)) {
                throw new IllegalStateException(handler + " is shutting down");
            }
            try {
                try {
                    long nanos = TimeUnit.SECONDS.toNanos(4L);
                    long jNanoTime = System.nanoTime() + nanos;
                    boolean z2 = false;
                    while (((Boolean) futureTask.get(nanos, TimeUnit.NANOSECONDS)).booleanValue()) {
                        try {
                            try {
                                sGnssStatusListeners.put(callback, gpsStatusTransport);
                                if (z2) {
                                    Thread.currentThread().interrupt();
                                }
                                return true;
                            } catch (ExecutionException e) {
                                e = e;
                                if (!(e.getCause() instanceof RuntimeException)) {
                                    throw ((RuntimeException) e.getCause());
                                }
                                if (e.getCause() instanceof Error) {
                                    throw ((Error) e.getCause());
                                }
                                throw new IllegalStateException(e);
                            } catch (TimeoutException e2) {
                                e = e2;
                                throw new IllegalStateException(handler + " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread", e);
                            } catch (Throwable th) {
                                th = th;
                                z = true;
                                if (z) {
                                    Thread.currentThread().interrupt();
                                }
                                throw th;
                            }
                        } catch (InterruptedException unused) {
                            nanos = jNanoTime - System.nanoTime();
                            z2 = true;
                        } catch (ExecutionException e3) {
                            e = e3;
                            if (!(e.getCause() instanceof RuntimeException)) {
                            }
                        } catch (TimeoutException e4) {
                            e = e4;
                            throw new IllegalStateException(handler + " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread", e);
                        } catch (Throwable th2) {
                            th = th2;
                            z = z2;
                            if (z) {
                            }
                            throw th;
                        }
                    }
                    if (z2) {
                        Thread.currentThread().interrupt();
                    }
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (ExecutionException e5) {
                e = e5;
            } catch (TimeoutException e6) {
                e = e6;
            }
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback, @NonNull Handler handler) {
        return Build.VERSION.SDK_INT >= 30 ? registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback) : registerGnssStatusCallback(locationManager, new InlineHandlerExecutor(handler), callback);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, null, executor, callback);
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            looperMyLooper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looperMyLooper), executor, callback);
    }

    public static void unregisterGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            synchronized (sGnssStatusListeners) {
                GnssStatus.Callback callback2 = (GnssStatusTransport) sGnssStatusListeners.remove(callback);
                if (callback2 != null) {
                    locationManager.unregisterGnssStatusCallback(callback2);
                }
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            synchronized (sGnssStatusListeners) {
                PreRGnssStatusTransport preRGnssStatusTransport = (PreRGnssStatusTransport) sGnssStatusListeners.remove(callback);
                if (preRGnssStatusTransport != null) {
                    preRGnssStatusTransport.unregister();
                    locationManager.unregisterGnssStatusCallback(preRGnssStatusTransport);
                }
            }
            return;
        }
        synchronized (sGnssStatusListeners) {
            GpsStatusTransport gpsStatusTransport = (GpsStatusTransport) sGnssStatusListeners.remove(callback);
            if (gpsStatusTransport != null) {
                gpsStatusTransport.unregister();
                locationManager.removeGpsStatusListener(gpsStatusTransport);
            }
        }
    }
}
