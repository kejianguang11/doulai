package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    a c;
    private Context d;
    private WebView f;
    Object a = new Object();
    private AMapLocationClient e = null;
    private String g = "AMap.Geolocation.cbk";
    AMapLocationClientOption b = null;
    private volatile boolean h = false;

    class a implements AMapLocationListener {
        a() {
        }

        @Override // com.amap.api.location.AMapLocationListener
        public final void onLocationChanged(AMapLocation aMapLocation) {
            if (i.this.h) {
                i.this.b(i.b(aMapLocation));
            }
        }
    }

    public i(Context context, WebView webView) {
        this.f = null;
        this.c = null;
        this.d = context.getApplicationContext();
        this.f = webView;
        this.c = new a();
    }

    private void a(String str) {
        long jOptLong;
        boolean z;
        int iOptInt;
        AMapLocationClientOption aMapLocationClientOption;
        AMapLocationClientOption.AMapLocationMode aMapLocationMode;
        if (this.b == null) {
            this.b = new AMapLocationClientOption();
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            jOptLong = jSONObject.optLong("to", com.igexin.push.config.c.k);
            try {
                z = jSONObject.optInt("useGPS", 1) == 1;
                try {
                    z = jSONObject.optInt("watch", 0) == 1;
                    iOptInt = jSONObject.optInt("interval", 5);
                    try {
                        String strOptString = jSONObject.optString(com.alipay.sdk.authjs.a.c, null);
                        if (TextUtils.isEmpty(strOptString)) {
                            strOptString = "AMap.Geolocation.cbk";
                        }
                        this.g = strOptString;
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    iOptInt = 5;
                }
            } catch (Throwable unused3) {
                iOptInt = 5;
                z = false;
            }
        } catch (Throwable unused4) {
            jOptLong = 30000;
            z = false;
        }
        try {
            this.b.setHttpTimeOut(jOptLong);
            if (z) {
                aMapLocationClientOption = this.b;
                aMapLocationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
            } else {
                aMapLocationClientOption = this.b;
                aMapLocationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving;
            }
            aMapLocationClientOption.setLocationMode(aMapLocationMode);
            this.b.setOnceLocation(!z);
            if (z) {
                this.b.setInterval(iOptInt * 1000);
            }
        } catch (Throwable unused5) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(AMapLocation aMapLocation) {
        String str;
        Object obj;
        JSONObject jSONObject = new JSONObject();
        if (aMapLocation == null) {
            jSONObject.put("errorCode", -1);
            str = "errorInfo";
            obj = "unknownError";
        } else {
            if (aMapLocation.getErrorCode() != 0) {
                jSONObject.put("errorCode", aMapLocation.getErrorCode());
                jSONObject.put("errorInfo", aMapLocation.getErrorInfo());
                jSONObject.put("locationDetail", aMapLocation.getLocationDetail());
                return jSONObject.toString();
            }
            jSONObject.put("errorCode", 0);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("x", aMapLocation.getLongitude());
            jSONObject2.put("y", aMapLocation.getLatitude());
            jSONObject2.put("precision", aMapLocation.getAccuracy());
            jSONObject2.put("type", aMapLocation.getLocationType());
            jSONObject2.put("country", aMapLocation.getCountry());
            jSONObject2.put("province", aMapLocation.getProvince());
            jSONObject2.put("city", aMapLocation.getCity());
            jSONObject2.put("cityCode", aMapLocation.getCityCode());
            jSONObject2.put("district", aMapLocation.getDistrict());
            jSONObject2.put("adCode", aMapLocation.getAdCode());
            jSONObject2.put("street", aMapLocation.getStreet());
            jSONObject2.put("streetNum", aMapLocation.getStreetNum());
            jSONObject2.put("floor", aMapLocation.getFloor());
            jSONObject2.put("address", aMapLocation.getAddress());
            str = com.alipay.sdk.util.j.c;
            obj = jSONObject2;
        }
        jSONObject.put(str, obj);
        return jSONObject.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void b(final String str) {
        try {
            if (this.f != null) {
                if (Build.VERSION.SDK_INT < 19) {
                    this.f.post(new Runnable() { // from class: com.loc.i.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            i.this.f.loadUrl("javascript:" + i.this.g + "('" + str + "')");
                        }
                    });
                    return;
                }
                this.f.evaluateJavascript("javascript:" + this.g + "('" + str + "')", new ValueCallback<String>() { // from class: com.loc.i.1
                    @Override // android.webkit.ValueCallback
                    public final /* bridge */ /* synthetic */ void onReceiveValue(String str2) {
                    }
                });
            }
        } catch (Throwable th) {
            fj.a(th, "H5LocationClient", "callbackJs()");
        }
    }

    public final void a() {
        if (this.f == null || this.d == null || Build.VERSION.SDK_INT < 17 || this.h) {
            return;
        }
        try {
            this.f.getSettings().setJavaScriptEnabled(true);
            this.f.addJavascriptInterface(this, "AMapAndroidLoc");
            if (!TextUtils.isEmpty(this.f.getUrl())) {
                this.f.reload();
            }
            if (this.e == null) {
                this.e = new AMapLocationClient(this.d);
                this.e.setLocationListener(this.c);
            }
            this.h = true;
        } catch (Throwable unused) {
        }
    }

    public final void b() {
        synchronized (this.a) {
            this.h = false;
            if (this.e != null) {
                this.e.unRegisterLocationListener(this.c);
                this.e.stopLocation();
                this.e.onDestroy();
                this.e = null;
            }
            this.b = null;
        }
    }

    @JavascriptInterface
    public final void getLocation(String str) {
        synchronized (this.a) {
            if (this.h) {
                a(str);
                if (this.e != null) {
                    this.e.setLocationOption(this.b);
                    this.e.stopLocation();
                    this.e.startLocation();
                }
            }
        }
    }

    @JavascriptInterface
    public final void stopLocation() {
        if (this.h && this.e != null) {
            this.e.stopLocation();
        }
    }
}
