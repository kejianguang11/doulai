package com.getui.gtc.a;

import android.os.Build;
import android.text.TextUtils;
import com.getui.gtc.BuildConfig;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dim.Caller;
import com.getui.gtc.e.c;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class i implements Runnable {
    private final String a;
    private final String b;
    private final String c;
    private final boolean d;

    public i() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = true;
    }

    public i(Caller caller, String str, String str2) {
        this.a = caller != null ? caller.name() : "";
        this.b = str;
        this.c = str2;
        this.d = false;
    }

    private static String a() {
        try {
            String string = CommonUtil.getAppInfoForSelf(GtcProvider.context()).metaData.getString("GTC_HW_LS");
            return !TextUtils.isEmpty(string) ? string : "https://b-qj.gepush.com/doslogcenter/json/hwscene/v1/label";
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a(th);
            return "https://b-qj.gepush.com/doslogcenter/json/hwscene/v1/label";
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        JSONArray jSONArray;
        try {
            String str = c.a.a.a.h;
            com.getui.gtc.i.c.a.a("HW-SF-LABEL his data from db = ".concat(String.valueOf(str)));
            JSONArray jSONArray2 = null;
            if (!TextUtils.isEmpty(str)) {
                try {
                    jSONArray = new JSONArray(str);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    com.getui.gtc.i.c.a.a("HW-SF-LABEL his data length = " + jSONArray.length());
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
            if (this.d) {
                com.getui.gtc.i.c.a.a("HW-SF-LABEL interval start check his data");
                if (jSONArray2.length() == 0) {
                    return;
                }
            } else {
                try {
                    String str2 = com.getui.gtc.c.b.d;
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String strA = h.a();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("caller", this.a);
                    jSONObject.put("cid", TextUtils.isEmpty(this.b) ? h.b() : this.b);
                    jSONObject.put("labels", this.c);
                    jSONObject.put("oneId", h.c());
                    jSONObject.put("gtcId", str2);
                    jSONObject.put("appId", strA);
                    jSONObject.put("createTime", jCurrentTimeMillis);
                    jSONObject.put("brand", Build.BRAND);
                    jSONObject.put("model", Build.MODEL);
                    jSONObject.put("manufacturer", Build.MANUFACTURER);
                    jSONObject.put("systemVersion", Build.VERSION.RELEASE);
                    jSONObject.put("sdkVersion", BuildConfig.VERSION_NAME);
                    com.getui.gtc.i.c.a.a("HW-SF-LABEL cur data = " + jSONObject.toString());
                    jSONArray2.put(jSONObject);
                } catch (Throwable th3) {
                    com.getui.gtc.i.c.a.b(th3);
                }
                c.a.a.a.b(jSONArray2.toString());
            }
            String strA2 = a();
            for (int i = 0; i < jSONArray2.length(); i++) {
                try {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                    jSONObject2.put("reportTime", System.currentTimeMillis());
                    com.getui.gtc.h.a.a(jSONObject2, strA2);
                } catch (Throwable th4) {
                    com.getui.gtc.i.c.a.a(th4);
                }
            }
            c.a.a.a.b("");
        } catch (Throwable th5) {
            com.getui.gtc.i.c.a.b(th5);
        }
    }
}
