package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import com.alipay.sdk.util.k;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ a b;

    b(a aVar, Context context) {
        this.b = aVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            com.alipay.sdk.packet.impl.b bVar = new com.alipay.sdk.packet.impl.b();
            Context context = this.a;
            com.alipay.sdk.packet.b bVarA = bVar.a(context, "", k.a(context), true);
            if (bVarA != null) {
                a aVar = this.b;
                String str = bVarA.b;
                if (!TextUtils.isEmpty(str)) {
                    try {
                        JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(a.g);
                        aVar.i = jSONObjectOptJSONObject.optInt("timeout", a.a);
                        aVar.j = jSONObjectOptJSONObject.optString(a.h, a.b).trim();
                    } catch (Throwable unused) {
                    }
                }
                a aVar2 = this.b;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("timeout", aVar2.a());
                jSONObject.put(a.h, aVar2.j);
                i.a(com.alipay.sdk.sys.b.a().a, a.e, jSONObject.toString());
            }
        } catch (Throwable unused2) {
        }
    }
}
