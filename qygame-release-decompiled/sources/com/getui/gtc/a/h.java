package com.getui.gtc.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.getui.gtc.BuildConfig;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.e.c;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Runnable {
    private final Intent a;

    public h(Intent intent) {
        this.a = intent;
    }

    public static String a() {
        String str = com.getui.gtc.c.b.a;
        String packageName = GtcProvider.context().getPackageName();
        if (!packageName.equals(str)) {
            return str;
        }
        try {
            Bundle bundle = CommonUtil.getAppInfoForSelf(GtcProvider.context()).metaData;
            String string = bundle.getString("GETUI_APPID");
            if (!TextUtils.isEmpty(string)) {
                return string;
            }
            String string2 = bundle.getString("GETUI_APP_ID");
            if (!TextUtils.isEmpty(string2)) {
                return string2;
            }
            String string3 = bundle.getString(com.igexin.push.core.b.b);
            if (!TextUtils.isEmpty(string3)) {
                return string3;
            }
            String string4 = bundle.getString("GI_APPID");
            if (!TextUtils.isEmpty(string4)) {
                return string4;
            }
            String string5 = bundle.getString("GI_APP_ID");
            if (!TextUtils.isEmpty(string5)) {
                return string5;
            }
            String string6 = bundle.getString("GS_APPID");
            if (!TextUtils.isEmpty(string6)) {
                return string6;
            }
            String string7 = bundle.getString("GS_APP_ID");
            if (!TextUtils.isEmpty(string7)) {
                return string7;
            }
            String string8 = bundle.getString("GY_APPID");
            if (!TextUtils.isEmpty(string8)) {
                return string8;
            }
            String string9 = bundle.getString("GY_APP_ID");
            if (!TextUtils.isEmpty(string9)) {
                return string9;
            }
            String string10 = bundle.getString("com.sdk.plus.appid");
            if (!TextUtils.isEmpty(string10)) {
                return string10;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    private void a(JSONArray jSONArray) {
        try {
            String str = com.getui.gtc.c.b.d;
            if (TextUtils.isEmpty(str)) {
                str = c.a.a.a.d;
            }
            String string = c.a.a.a.e;
            int i = 1;
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(string)) {
                StringBuilder sb = new StringBuilder();
                sb.append("random_");
                String strA = com.getui.gtc.i.a.a.a(UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + "-" + GtcProvider.context().getPackageName());
                sb.append(strA);
                char cCharAt = strA.charAt(strA.length() - 1);
                if (cCharAt < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(cCharAt));
                string = sb.toString();
                com.getui.gtc.e.d dVar = c.a.a.a;
                if (dVar.a(21, string)) {
                    dVar.e = string;
                }
                com.getui.gtc.i.c.a.a("HW-SF create randomId = " + string + " because no gtcId");
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            String strA2 = a();
            String str2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
            try {
                String str3 = c.a.a.a.f;
                com.getui.gtc.i.c.a.a("HW-SF num from db = ".concat(String.valueOf(str3)));
                if (!TextUtils.isEmpty(str3)) {
                    JSONObject jSONObject = new JSONObject(str3);
                    if (str2.equals(jSONObject.optString("day"))) {
                        int iOptInt = jSONObject.optInt("num", 0);
                        if (iOptInt > 0) {
                            i = iOptInt + 1;
                        }
                    }
                }
            } catch (Throwable th) {
                com.getui.gtc.i.c.a.b(th);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("day", str2);
            jSONObject2.put("num", i);
            String string2 = jSONObject2.toString();
            com.getui.gtc.e.d dVar2 = c.a.a.a;
            if (dVar2.a(22, string2)) {
                dVar2.f = string2;
            }
            com.getui.gtc.i.c.a.a("HW-SF save num to db  = ".concat(String.valueOf(string2)));
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("gtcId", str);
            jSONObject3.put("randomId", string);
            jSONObject3.put("appId", strA2);
            jSONObject3.put("createTime", jCurrentTimeMillis);
            jSONObject3.put("num", i);
            jSONObject3.put("brand", Build.BRAND);
            jSONObject3.put("model", Build.MODEL);
            jSONObject3.put("manufacturer", Build.MANUFACTURER);
            jSONObject3.put("systemVersion", Build.VERSION.RELEASE);
            jSONObject3.put("sdkVersion", BuildConfig.VERSION_NAME);
            jSONObject3.put("cid", b());
            jSONObject3.put("oneId", c());
            Bundle extras = this.a.getExtras();
            if (extras != null) {
                JSONObject jSONObject4 = new JSONObject();
                for (String str4 : extras.keySet()) {
                    jSONObject4.put(str4, extras.get(str4));
                }
                jSONObject3.put("scene", jSONObject4);
            }
            com.getui.gtc.i.c.a.a("HW-SF cur data = " + jSONObject3.toString());
            jSONArray.put(jSONObject3);
        } catch (Throwable th2) {
            com.getui.gtc.i.c.a.b(th2);
        }
    }

    public static String b() {
        try {
            Class<?> cls = Class.forName("com.igexin.sdk.PushManager");
            Object objInvoke = cls.getDeclaredMethod("getClientid", Context.class).invoke(cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]), GtcProvider.context());
            return objInvoke != null ? (String) objInvoke : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String c() {
        try {
            Class<?> cls = Class.forName("com.getui.oneid.OneIDManager");
            Object objInvoke = cls.getDeclaredMethod("getOneResponse", new Class[0]).invoke(cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            if (objInvoke == null) {
                return "";
            }
            Object objInvoke2 = Class.forName("com.getui.oneid.OneResponse").getDeclaredMethod("getOneAID", new Class[0]).invoke(objInvoke, new Object[0]);
            return objInvoke2 instanceof String ? (String) objInvoke2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String d() {
        try {
            String string = CommonUtil.getAppInfoForSelf(GtcProvider.context()).metaData.getString("GTC_HW_BS");
            return !TextUtils.isEmpty(string) ? string : "https://b-qj.gepush.com/doslogcenter/json/hwscene/v1/type";
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a(th);
            return "https://b-qj.gepush.com/doslogcenter/json/hwscene/v1/type";
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        JSONArray jSONArray;
        try {
            String str = c.a.a.a.g;
            com.getui.gtc.i.c.a.a("HW-SF his data from db = ".concat(String.valueOf(str)));
            JSONArray jSONArray2 = null;
            if (!TextUtils.isEmpty(str)) {
                try {
                    jSONArray = new JSONArray(str);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    com.getui.gtc.i.c.a.a("HW-SF his data length = " + jSONArray.length());
                    jSONArray2 = jSONArray;
                } catch (Throwable th2) {
                    th = th2;
                    jSONArray2 = jSONArray;
                    com.getui.gtc.i.c.a.b(th);
                }
            }
            if (jSONArray2 == null || jSONArray2.length() >= 200) {
                jSONArray2 = new JSONArray();
            }
            if (this.a != null) {
                a(jSONArray2);
                c.a.a.a.a(jSONArray2.toString());
            } else {
                com.getui.gtc.i.c.a.a("HW-SF interval start check his data");
                if (jSONArray2.length() == 0) {
                    return;
                }
            }
            String strD = d();
            for (int i = 0; i < jSONArray2.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray2.getJSONObject(i);
                    jSONObject.put("reportTime", System.currentTimeMillis());
                    com.getui.gtc.h.a.a(jSONObject, strD);
                } catch (Throwable th3) {
                    com.getui.gtc.i.c.a.a(th3);
                }
            }
            c.a.a.a.a("");
        } catch (Throwable th4) {
            com.getui.gtc.i.c.a.b(th4);
        }
    }
}
