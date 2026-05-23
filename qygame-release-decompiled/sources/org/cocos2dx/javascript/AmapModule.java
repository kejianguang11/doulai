package org.cocos2dx.javascript;

import android.annotation.SuppressLint;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.igexin.push.config.c;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AmapModule {
    private static final int OPEN_SET_REQUEST_CODE = 100;
    private static AppActivity app = null;
    private static AMapLocationClient locationClient = null;
    private static String locationInfo = "";
    private static String[] permissions = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"};
    static AMapLocationListener locationListener = new AMapLocationListener() { // from class: org.cocos2dx.javascript.AmapModule.1
        @Override // com.amap.api.location.AMapLocationListener
        @SuppressLint({"SimpleDateFormat"})
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation == null) {
                return;
            }
            if (aMapLocation.getErrorCode() != 0) {
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                String unused = AmapModule.locationInfo = "";
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("latitude", "" + aMapLocation.getLatitude());
                jSONObject.put("longitude", "" + aMapLocation.getLongitude());
                jSONObject.put("accuracy", "" + aMapLocation.getAccuracy());
                jSONObject.put("address", aMapLocation.getAddress());
                jSONObject.put("country", aMapLocation.getCountry());
                jSONObject.put("province", aMapLocation.getProvince());
                jSONObject.put("city", aMapLocation.getCity());
                jSONObject.put("district", aMapLocation.getDistrict());
                jSONObject.put("street", aMapLocation.getStreet());
                jSONObject.put("streetNum", aMapLocation.getStreetNum());
                jSONObject.put("cityCode", aMapLocation.getCityCode());
                jSONObject.put("adCode", aMapLocation.getAdCode());
                String unused2 = AmapModule.locationInfo = jSONObject.toString();
                Log.d("定位地理信息", AmapModule.locationInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private static AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setGpsFirst(false);
        aMapLocationClientOption.setHttpTimeOut(c.k);
        aMapLocationClientOption.setInterval(2000L);
        aMapLocationClientOption.setNeedAddress(true);
        aMapLocationClientOption.setOnceLocation(true);
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        aMapLocationClientOption.setSensorEnable(false);
        aMapLocationClientOption.setWifiScan(true);
        aMapLocationClientOption.setLocationCacheEnable(true);
        return aMapLocationClientOption;
    }

    public static String getDistance(String str, String str2, String str3, String str4) {
        return "0";
    }

    public static String getLocationInfo() {
        return locationInfo;
    }

    public static void initLocation(AppActivity appActivity) {
        if (locationClient != null) {
            return;
        }
        app = appActivity;
        if (locationClient != null) {
            return;
        }
        try {
            locationClient = new AMapLocationClient(Cocos2dxActivity.getContext());
            locationClient.setLocationOption(getDefaultOption());
            locationClient.setLocationListener(locationListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean lacksPermission(String[] strArr) {
        for (String str : strArr) {
            if (app.checkSelfPermission(str) != 0) {
                return true;
            }
        }
        return false;
    }

    public static void privacyCompliance(AppActivity appActivity, boolean z) {
        app = appActivity;
        AMapLocationClient.updatePrivacyShow(app, true, true);
        AMapLocationClient.updatePrivacyAgree(app, z);
        if (z && lacksPermission(permissions)) {
            app.requestPermissions(permissions, 100);
        }
    }

    public static boolean startLocation() {
        locationClient.startLocation();
        return true;
    }
}
