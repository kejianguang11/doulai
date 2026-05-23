package androidx.core.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.igexin.push.core.b;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes.dex */
public class ContextCompat {
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    private static final class LegacyServiceMapHolder {
        static final HashMap<Class<?>, String> a = new HashMap<>();

        static {
            if (Build.VERSION.SDK_INT >= 22) {
                a.put(SubscriptionManager.class, "telephony_subscription_service");
                a.put(UsageStatsManager.class, "usagestats");
            }
            if (Build.VERSION.SDK_INT >= 21) {
                a.put(AppWidgetManager.class, "appwidget");
                a.put(BatteryManager.class, "batterymanager");
                a.put(CameraManager.class, "camera");
                a.put(JobScheduler.class, "jobscheduler");
                a.put(LauncherApps.class, "launcherapps");
                a.put(MediaProjectionManager.class, "media_projection");
                a.put(MediaSessionManager.class, "media_session");
                a.put(RestrictionsManager.class, "restrictions");
                a.put(TelecomManager.class, "telecom");
                a.put(TvInputManager.class, "tv_input");
            }
            if (Build.VERSION.SDK_INT >= 19) {
                a.put(AppOpsManager.class, "appops");
                a.put(CaptioningManager.class, "captioning");
                a.put(ConsumerIrManager.class, "consumer_ir");
                a.put(PrintManager.class, "print");
            }
            if (Build.VERSION.SDK_INT >= 18) {
                a.put(BluetoothManager.class, "bluetooth");
            }
            if (Build.VERSION.SDK_INT >= 17) {
                a.put(DisplayManager.class, "display");
                a.put(UserManager.class, "user");
            }
            if (Build.VERSION.SDK_INT >= 16) {
                a.put(InputManager.class, "input");
                a.put(MediaRouter.class, "media_router");
                a.put(NsdManager.class, "servicediscovery");
            }
            a.put(AccessibilityManager.class, "accessibility");
            a.put(AccountManager.class, "account");
            a.put(ActivityManager.class, "activity");
            a.put(AlarmManager.class, NotificationCompat.CATEGORY_ALARM);
            a.put(AudioManager.class, "audio");
            a.put(ClipboardManager.class, "clipboard");
            a.put(ConnectivityManager.class, "connectivity");
            a.put(DevicePolicyManager.class, "device_policy");
            a.put(DownloadManager.class, "download");
            a.put(DropBoxManager.class, "dropbox");
            a.put(InputMethodManager.class, "input_method");
            a.put(KeyguardManager.class, "keyguard");
            a.put(LayoutInflater.class, "layout_inflater");
            a.put(LocationManager.class, "location");
            a.put(NfcManager.class, "nfc");
            a.put(NotificationManager.class, b.n);
            a.put(PowerManager.class, "power");
            a.put(SearchManager.class, "search");
            a.put(SensorManager.class, "sensor");
            a.put(StorageManager.class, "storage");
            a.put(TelephonyManager.class, "phone");
            a.put(TextServicesManager.class, "textservices");
            a.put(UiModeManager.class, "uimode");
            a.put(UsbManager.class, "usb");
            a.put(Vibrator.class, "vibrator");
            a.put(WallpaperManager.class, "wallpaper");
            a.put(WifiP2pManager.class, "wifip2p");
            a.put(WifiManager.class, "wifi");
            a.put(WindowManager.class, "window");
        }

        private LegacyServiceMapHolder() {
        }
    }

    private static class MainHandlerExecutor implements Executor {
        private final Handler mHandler;

        MainHandlerExecutor(@NonNull Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            if (this.mHandler.post(runnable)) {
                return;
            }
            throw new RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }

    protected ContextCompat() {
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String str) {
        if (str != null) {
            return context.checkPermission(str, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Nullable
    public static Context createDeviceProtectedStorageContext(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext();
        }
        return null;
    }

    private static synchronized File createFilesDir(File file) {
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        if (file.exists()) {
            return file;
        }
        Log.w(TAG, "Unable to create files subdir " + file.getPath());
        return null;
    }

    public static File getCodeCacheDir(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 21 ? context.getCodeCacheDir() : createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int i) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColor(i) : context.getResources().getColor(i);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int i) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColorStateList(i) : context.getResources().getColorStateList(i);
    }

    @Nullable
    public static File getDataDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getDataDir();
        }
        String str = context.getApplicationInfo().dataDir;
        if (str != null) {
            return new File(str);
        }
        return null;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(i);
        }
        if (Build.VERSION.SDK_INT < 16) {
            synchronized (sLock) {
                if (sTempValue == null) {
                    sTempValue = new TypedValue();
                }
                context.getResources().getValue(i, sTempValue, true);
                i = sTempValue.resourceId;
            }
        }
        return context.getResources().getDrawable(i);
    }

    @NonNull
    public static File[] getExternalCacheDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? context.getExternalCacheDirs() : new File[]{context.getExternalCacheDir()};
    }

    @NonNull
    public static File[] getExternalFilesDirs(@NonNull Context context, @Nullable String str) {
        return Build.VERSION.SDK_INT >= 19 ? context.getExternalFilesDirs(str) : new File[]{context.getExternalFilesDir(str)};
    }

    public static Executor getMainExecutor(Context context) {
        return Build.VERSION.SDK_INT >= 28 ? context.getMainExecutor() : new MainHandlerExecutor(new Handler(context.getMainLooper()));
    }

    @Nullable
    public static File getNoBackupFilesDir(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 21 ? context.getNoBackupFilesDir() : createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    @NonNull
    public static File[] getObbDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? context.getObbDirs() : new File[]{context.getObbDir()};
    }

    @Nullable
    public static <T> T getSystemService(@NonNull Context context, @NonNull Class<T> cls) {
        if (Build.VERSION.SDK_INT >= 23) {
            return (T) context.getSystemService(cls);
        }
        String systemServiceName = getSystemServiceName(context, cls);
        if (systemServiceName != null) {
            return (T) context.getSystemService(systemServiceName);
        }
        return null;
    }

    @Nullable
    public static String getSystemServiceName(@NonNull Context context, @NonNull Class<?> cls) {
        return Build.VERSION.SDK_INT >= 23 ? context.getSystemServiceName(cls) : LegacyServiceMapHolder.a.get(cls);
    }

    public static boolean isDeviceProtectedStorage(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.isDeviceProtectedStorage();
        }
        return false;
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intentArr) {
        return startActivities(context, intentArr, null);
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivities(intentArr, bundle);
            return true;
        }
        context.startActivities(intentArr);
        return true;
    }

    public static void startActivity(@NonNull Context context, @NonNull Intent intent, @Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivity(intent, bundle);
        } else {
            context.startActivity(intent);
        }
    }

    public static void startForegroundService(@NonNull Context context, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}
