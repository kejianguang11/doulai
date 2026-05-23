package com.igexin.push.g;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.getui.gtc.api.GtcManager;
import com.getui.gtc.dim.Caller;
import com.igexin.push.core.e.f.AnonymousClass35;
import com.igexin.sdk.message.Label;
import java.util.Random;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    public static final String a = "g_";
    private static final String b = "FenceLabelsUtils";
    private static final String c = "com.huawei.hms.contentprovider";
    private static final String d = "com.huawei.hms.location.features";
    private static final String e = "content://com.huawei.hms.contentprovider/com.huawei.hms.location.features";
    private static final String f = "content://com.huawei.hms.contentprovider";
    private static final String g = "hms_cp_bundle_key";
    private static final String h = "calling_package";
    private static final String i = "app_id";
    private static final String j = "mt_labels";
    private static final String k = "setting_type";
    private static final String l = "location_fence_settings";

    public static void a(Context context, String str, String str2) {
        try {
            if (!TextUtils.isEmpty(com.igexin.push.core.e.aN)) {
                com.igexin.c.a.c.a.b(b, "gt label already set");
                return;
            }
            JSONArray jSONArray = new JSONArray();
            String str3 = String.format("%03d", Integer.valueOf(new Random().nextInt(100) + 1));
            Label label = new Label();
            label.setTagId("g_all");
            label.setTagName("g_all");
            label.setEndTime(Long.MAX_VALUE);
            Label label2 = new Label();
            label2.setTagId(a.concat(String.valueOf(str3)));
            label2.setTagName(a.concat(String.valueOf(str3)));
            label2.setEndTime(Long.MAX_VALUE);
            jSONArray.put(label.getJsonObject());
            jSONArray.put(label2.getJsonObject());
            String string = jSONArray.toString();
            Bundle bundle = new Bundle();
            bundle.putString(g, e);
            bundle.putString(h, str);
            bundle.putString(i, str2);
            bundle.putInt(k, 2);
            bundle.putString(j, string);
            Bundle bundleCall = context.getContentResolver().call(Uri.parse(f), l, (String) null, bundle);
            if (bundleCall != null) {
                int i2 = bundleCall.getInt("statusCode", -1);
                StringBuilder sb = new StringBuilder(" statusCode ");
                sb.append(i2);
                sb.append(" ; ");
                sb.append(i2 == 0 ? "success" : "fail");
                com.igexin.c.a.c.a.b(b, sb.toString());
                if (i2 == 0) {
                    com.igexin.push.core.e.f.a().f(string);
                    if (d.b("3.2.18.0")) {
                        return;
                    }
                    GtcManager.getInstance().onHWSmartFenceLabelChanged(com.igexin.push.core.e.l, Caller.PUSH, com.igexin.push.core.e.A, string);
                }
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        try {
            if (!TextUtils.isEmpty(com.igexin.push.core.e.aN)) {
                JSONArray jSONArray = new JSONArray(str3);
                JSONArray jSONArray2 = new JSONArray(com.igexin.push.core.e.aN);
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    jSONArray.put(jSONArray2.optJSONObject(i2));
                }
                str3 = jSONArray.toString();
            }
            Bundle bundle = new Bundle();
            bundle.putString(g, e);
            bundle.putString(h, str);
            bundle.putString(i, str2);
            bundle.putInt(k, 2);
            bundle.putString(j, str3);
            Bundle bundleCall = context.getContentResolver().call(Uri.parse(f), l, (String) null, bundle);
            if (bundleCall != null) {
                int i3 = bundleCall.getInt("statusCode", -1);
                StringBuilder sb = new StringBuilder(" statusCode ");
                sb.append(i3);
                sb.append(" ; ");
                sb.append(i3 == 0 ? "success" : "fail");
                com.igexin.c.a.c.a.b(b, sb.toString());
                if (i3 == 0) {
                    com.igexin.push.core.l.a().b("0");
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) com.igexin.push.core.e.f.a().new AnonymousClass35(str3), false, true);
                    if (d.b("3.2.18.0")) {
                        return;
                    }
                    GtcManager.getInstance().onHWSmartFenceLabelChanged(com.igexin.push.core.e.l, Caller.PUSH, com.igexin.push.core.e.A, str3);
                    return;
                }
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(b, th.toString());
        }
        com.igexin.push.core.l.a().b("40005");
    }
}
