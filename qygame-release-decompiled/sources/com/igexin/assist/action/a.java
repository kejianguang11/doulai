package com.igexin.assist.action;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.igexin.assist.MessageBean;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.b;
import com.igexin.push.g.d;
import com.igexin.push.g.o;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    byte[] a;
    String b;
    String c;
    String d;
    String e;
    String f;
    private String g;

    private byte[] a() {
        return this.a;
    }

    private String b() {
        return this.b;
    }

    private String c() {
        return this.c;
    }

    private String d() {
        return this.d;
    }

    private String e() {
        return this.e;
    }

    private String f() {
        return this.g;
    }

    private String g() {
        return this.f;
    }

    public final void a(MessageBean messageBean) {
        try {
            Context context = messageBean.getContext();
            String stringMessage = messageBean.getStringMessage();
            if (!TextUtils.isEmpty(stringMessage) && context != null) {
                ApplicationInfo applicationInfoB = o.b(context);
                this.d = d.a(applicationInfoB);
                if (TextUtils.isEmpty(this.d)) {
                    this.d = applicationInfoB.metaData.getString(b.b);
                }
                if (TextUtils.isEmpty(this.d)) {
                    this.d = applicationInfoB.metaData.getString("GETUI_APPID");
                }
                if (TextUtils.isEmpty(this.d)) {
                    return;
                }
                this.g = context.getPackageName();
                this.c = (TextUtils.isEmpty(messageBean.getMessageSource()) ? "" : messageBean.getMessageSource()) + UUID.randomUUID().toString();
                String strA = com.igexin.assist.util.a.a(stringMessage, this.d);
                if (TextUtils.isEmpty(strA)) {
                    return;
                }
                JSONObject jSONObject = new JSONObject(strA);
                if (jSONObject.has(AssistPushConsts.MSG_KEY_TASKID)) {
                    this.b = jSONObject.getString(AssistPushConsts.MSG_KEY_TASKID);
                }
                if (jSONObject.has(AssistPushConsts.MSG_KEY_ACTION)) {
                    this.e = jSONObject.getString(AssistPushConsts.MSG_KEY_ACTION);
                }
                if (jSONObject.has(AssistPushConsts.MSG_KEY_CONTENT) && !TextUtils.isEmpty(jSONObject.getString(AssistPushConsts.MSG_KEY_CONTENT))) {
                    this.a = jSONObject.getString(AssistPushConsts.MSG_KEY_CONTENT).getBytes();
                }
                if (jSONObject.has(AssistPushConsts.MSG_KEY_ACTION_CHAINS)) {
                    this.f = jSONObject.getString(AssistPushConsts.MSG_KEY_ACTION_CHAINS);
                    if (TextUtils.isEmpty(this.f)) {
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject(this.f);
                    jSONObject2.put("extra_actionid", b.i);
                    this.f = jSONObject2.toString();
                }
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public final boolean a(boolean z) {
        return (this.a != null || (this.f != null && z)) && (d.a(this.b, this.g, this.d, this.e, this.c) ^ true);
    }
}
