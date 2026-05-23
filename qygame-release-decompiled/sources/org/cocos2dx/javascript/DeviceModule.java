package org.cocos2dx.javascript;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import com.igexin.push.a;
import com.qygame.qzdlyx.mobile.ModifyImageActivity;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

/* JADX INFO: loaded from: classes.dex */
public class DeviceModule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static String IMEI = "";
    private static AppActivity app = null;
    public static float batteryLevel = 1.0f;
    private static Context context = null;
    public static String copyStr = "";

    public static void copyToClipboard(final String str) {
        try {
            app.runOnUiThread(new Runnable() { // from class: org.cocos2dx.javascript.DeviceModule.2
                @Override // java.lang.Runnable
                public void run() {
                    ((ClipboardManager) DeviceModule.app.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
                }
            });
        } catch (Exception e) {
            Log.d("cocos2dx", "copyToClipboard error");
            e.printStackTrace();
        }
    }

    public static String getAndroidID() {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getAppVerCode() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppVersion() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static float getBatteryLevel() {
        return batteryLevel;
    }

    public static String getChannelId() {
        Bundle bundle;
        try {
            bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
            bundle = null;
        }
        return "" + bundle.getInt("CHANNEL_ID");
    }

    public static String getClipContent() throws ExecutionException, InterruptedException {
        String copyString = getCopyString();
        Log.d("cocos2dx拷贝", copyString);
        return copyString;
    }

    private static String getCopyString() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new Callable<String>() { // from class: org.cocos2dx.javascript.DeviceModule.3
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                CharSequence text;
                ClipboardManager clipboardManager = (ClipboardManager) DeviceModule.app.getSystemService("clipboard");
                return (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType("text/plain") && (text = clipboardManager.getPrimaryClip().getItemAt(0).getText()) != null) ? text.toString() : "";
            }
        });
        app.runOnUiThread(new Thread(futureTask));
        return (String) futureTask.get();
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static String getMacAddress() {
        return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public static String getNetworkStatus() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return a.i;
        }
        int type = activeNetworkInfo.getType();
        return type == 0 ? "wwan" : type == 1 ? "wifi" : a.i;
    }

    public static String getOsName() {
        return Build.BRAND;
    }

    public static String getRoomId() {
        String strGroup = "";
        try {
            Matcher matcher = Pattern.compile("【(.*)】").matcher(getCopyString());
            while (matcher.find()) {
                strGroup = matcher.group(1);
            }
            Log.d("getRoomId", strGroup);
        } catch (Throwable unused) {
        }
        return strGroup;
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getUserToken() {
        return md5(getAndroidID());
    }

    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                switch (hexString.length()) {
                    case 0:
                        hexString = "00";
                        break;
                    case 1:
                        stringBuffer.append("0");
                        break;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void mobileShake(int i) {
        ((Vibrator) context.getSystemService("vibrator")).vibrate(i);
    }

    public static void onSelectPhotoCallback(Boolean bool, String str) {
        String str2;
        if (bool.booleanValue()) {
            str2 = ("gg.device.selectPhotoCallback(true, '" + str) + "');";
        } else {
            str2 = "gg.device.selectPhotoCallback(false, '')";
        }
        runJsCode(str2);
    }

    public static void openCarema(String str) {
    }

    public static void runJsCode(final String str) {
        app.runOnGLThread(new Runnable() { // from class: org.cocos2dx.javascript.DeviceModule.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxJavascriptJavaBridge.evalString(str);
            }
        });
    }

    public static void saveFileToPhoto(String str) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), str, "shotscreen", (String) null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(str)));
    }

    public static void selectPhoto(String str) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) ModifyImageActivity.class);
        intent.putExtra(ModifyImageActivity.selectPhoto, "choosePhotoImage");
        intent.putExtra("imageName", str);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void setContext(Context context2) {
        context = context2;
        app = (AppActivity) context2;
    }
}
