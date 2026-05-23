package com.amap.api.location;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import com.loc.bg;
import com.loc.d;
import com.loc.fj;
import com.loc.fo;
import com.loc.o;
import com.loc.t;
import com.loc.u;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AMapLocationClient {
    Context a;
    d b;

    public AMapLocationClient(Context context) throws Exception {
        a(context);
        try {
            if (context == null) {
                throw new IllegalArgumentException("Context参数不能为null");
            }
            this.a = context.getApplicationContext();
            this.b = new d(context, null, null);
        } catch (Throwable th) {
            fj.a(th, "AMClt", "ne1");
        }
    }

    public AMapLocationClient(Context context, Intent intent) throws Exception {
        a(context);
        try {
            if (context == null) {
                throw new IllegalArgumentException("Context参数不能为null");
            }
            this.a = context.getApplicationContext();
            this.b = new d(this.a, intent, null);
        } catch (Throwable th) {
            fj.a(th, "AMClt", "ne2");
        }
    }

    public AMapLocationClient(Looper looper, Context context) throws Exception {
        a(context);
        try {
            if (context == null) {
                throw new IllegalArgumentException("Context参数不能为null");
            }
            this.a = context.getApplicationContext();
            this.b = new d(this.a, null, looper);
        } catch (Throwable th) {
            fj.a(th, "AMClt", "ne3");
        }
    }

    private static void a(Context context) throws Exception {
        u uVarA = t.a(context, fj.c());
        if (uVarA.a == t.c.SuccessCode) {
            return;
        }
        Log.e("AMapLocationClient", uVarA.b);
        throw new Exception(uVarA.b);
    }

    public static String getDeviceId(Context context) {
        return o.w(context);
    }

    public static void setApiKey(String str) {
        try {
            AMapLocationClientOption.a = str;
        } catch (Throwable th) {
            fj.a(th, "AMClt", "sKey");
        }
    }

    public static void setHost(String str) {
        if (TextUtils.isEmpty(str)) {
            bg.a = -1;
            str = "";
        } else {
            bg.a = 1;
        }
        bg.b = str;
    }

    public static void updatePrivacyAgree(Context context, boolean z) {
        t.a(context, z, fj.c());
    }

    public static void updatePrivacyShow(Context context, boolean z, boolean z2) {
        t.a(context, z, z2, fj.c());
    }

    public void disableBackgroundLocation(boolean z) {
        try {
            if (this.b != null) {
                this.b.a(z);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "dBackL");
        }
    }

    public void enableBackgroundLocation(int i, Notification notification) {
        try {
            if (this.b != null) {
                this.b.a(i, notification);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "eBackL");
        }
    }

    public AMapLocation getLastKnownLocation() {
        try {
            if (this.b != null) {
                return this.b.e();
            }
            return null;
        } catch (Throwable th) {
            fj.a(th, "AMClt", "gLastL");
            return null;
        }
    }

    public String getVersion() {
        return "6.1.0";
    }

    public boolean isStarted() {
        try {
            if (this.b != null) {
                return this.b.a();
            }
            return false;
        } catch (Throwable th) {
            fj.a(th, "AMClt", "isS");
            return false;
        }
    }

    public void onDestroy() {
        try {
            if (this.b != null) {
                this.b.d();
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "onDy");
        }
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            if (aMapLocationListener == null) {
                throw new IllegalArgumentException("listener参数不能为null");
            }
            if (this.b != null) {
                this.b.a(aMapLocationListener);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "sLocL");
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        try {
            if (aMapLocationClientOption == null) {
                throw new IllegalArgumentException("LocationManagerOption参数不能为null");
            }
            if (this.b != null) {
                this.b.a(aMapLocationClientOption);
            }
            if (aMapLocationClientOption.b) {
                aMapLocationClientOption.b = false;
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(aMapLocationClientOption.c)) {
                    jSONObject.put("amap_loc_scenes_type", aMapLocationClientOption.c);
                }
                fo.a(this.a, "O019", jSONObject);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "sLocnO");
        }
    }

    public void startAssistantLocation(WebView webView) {
        try {
            if (this.b != null) {
                this.b.a(webView);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "sttAssL1");
        }
    }

    public void startLocation() {
        try {
            if (this.b != null) {
                this.b.b();
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "stl");
        }
    }

    public void stopAssistantLocation() {
        try {
            if (this.b != null) {
                this.b.f();
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "stAssL");
        }
    }

    public void stopLocation() {
        try {
            if (this.b != null) {
                this.b.c();
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "stl");
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            if (this.b != null) {
                this.b.b(aMapLocationListener);
            }
        } catch (Throwable th) {
            fj.a(th, "AMClt", "unRL");
        }
    }
}
