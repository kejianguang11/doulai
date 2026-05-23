package com.igexin.push.core.a.c;

import android.content.Context;
import android.content.Intent;
import com.igexin.push.core.b.p;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class j implements PushMessageInterface {
    private static final String a = "com.igexin.push.core.a.c.j";

    private static void a(Context context, String str) {
        try {
            Intent launchIntentForPackage = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str);
            if (launchIntentForPackage != null) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setFlags(270532608);
                intent.setComponent(launchIntentForPackage.getComponent());
                context.startActivity(intent);
            }
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043 A[Catch: Exception -> 0x0080, TRY_ENTER, TryCatch #0 {Exception -> 0x0080, blocks: (B:15:0x0043, B:17:0x005e, B:18:0x0067, B:20:0x006d, B:21:0x007c, B:24:0x0082, B:26:0x009b, B:28:0x00a3, B:31:0x00af, B:33:0x00b5, B:34:0x00c5, B:36:0x00c9), top: B:40:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0082 A[Catch: Exception -> 0x0080, TryCatch #0 {Exception -> 0x0080, blocks: (B:15:0x0043, B:17:0x005e, B:18:0x0067, B:20:0x006d, B:21:0x007c, B:24:0x0082, B:26:0x009b, B:28:0x00a3, B:31:0x00af, B:33:0x00b5, B:34:0x00c5, B:36:0x00c9), top: B:40:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00af A[Catch: Exception -> 0x0080, TryCatch #0 {Exception -> 0x0080, blocks: (B:15:0x0043, B:17:0x005e, B:18:0x0067, B:20:0x006d, B:21:0x007c, B:24:0x0082, B:26:0x009b, B:28:0x00a3, B:31:0x00af, B:33:0x00b5, B:34:0x00c5, B:36:0x00c9), top: B:40:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c5 A[Catch: Exception -> 0x0080, TryCatch #0 {Exception -> 0x0080, blocks: (B:15:0x0043, B:17:0x005e, B:18:0x0067, B:20:0x006d, B:21:0x007c, B:24:0x0082, B:26:0x009b, B:28:0x00a3, B:31:0x00af, B:33:0x00b5, B:34:0x00c5, B:36:0x00c9), top: B:40:0x0041 }] */
    @Override // com.igexin.push.extension.mod.PushMessageInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        String str;
        boolean z;
        String taskId;
        String messageId;
        String doActionId;
        if (pushTaskBean != null && baseActionBean != null) {
            p pVar = (p) baseActionBean;
            String str2 = pVar.b;
            boolean z2 = false;
            try {
                if (str2.equals("")) {
                    str2 = com.igexin.push.core.e.a;
                } else {
                    if (!com.igexin.push.core.e.a.equals(pVar.b)) {
                        str = str2;
                        z = false;
                    }
                    com.igexin.c.a.c.a.a("doStartApp|" + z + "|" + str, new Object[0]);
                    if (z) {
                        com.igexin.push.core.l.a().a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), str, (String) null);
                        if (pVar.d.equals("true")) {
                            if (com.igexin.push.g.c.a(pVar.a)) {
                                a(com.igexin.push.core.e.l, ((p) baseActionBean).a);
                                z2 = true;
                            }
                            if (z2) {
                            }
                        } else {
                            z2 = true;
                            if (z2) {
                                if (pVar.c != null) {
                                    com.igexin.push.core.a.b.d();
                                    taskId = pushTaskBean.getTaskId();
                                    messageId = pushTaskBean.getMessageId();
                                    doActionId = pVar.c;
                                    com.igexin.push.core.a.b.a(taskId, messageId, doActionId);
                                }
                            } else if (pVar.getDoActionId() != null) {
                                com.igexin.push.core.a.b.d();
                                taskId = pushTaskBean.getTaskId();
                                messageId = pushTaskBean.getMessageId();
                                doActionId = pVar.getDoActionId();
                                com.igexin.push.core.a.b.a(taskId, messageId, doActionId);
                            }
                        }
                    } else {
                        com.igexin.push.core.l.a().a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), str, (String) null);
                        if (((p) baseActionBean).d.equals("true")) {
                            String str3 = com.igexin.push.core.e.g;
                            a(com.igexin.push.core.e.l, com.igexin.push.core.e.g);
                        }
                        if (pVar.getDoActionId() != null) {
                            com.igexin.push.core.a.b.d();
                            taskId = pushTaskBean.getTaskId();
                            messageId = pushTaskBean.getMessageId();
                            doActionId = pVar.getDoActionId();
                            com.igexin.push.core.a.b.a(taskId, messageId, doActionId);
                        }
                    }
                }
                if (z) {
                }
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
            str = str2;
            z = true;
            com.igexin.c.a.c.a.a("doStartApp|" + z + "|" + str, new Object[0]);
        }
        return true;
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public BaseActionBean parseAction(JSONObject jSONObject) {
        try {
            p pVar = new p();
            pVar.setType(com.igexin.push.core.b.q);
            pVar.setActionId(jSONObject.getString("actionid"));
            pVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("appstartupid")) {
                pVar.a = jSONObject.getJSONObject("appstartupid").getString("android");
            }
            if (jSONObject.has("is_autostart")) {
                pVar.d = jSONObject.getString("is_autostart");
            }
            if (jSONObject.has("appid")) {
                pVar.b = jSONObject.getString("appid");
            }
            if (jSONObject.has("noinstall_action")) {
                pVar.c = jSONObject.getString("noinstall_action");
            }
            return pVar;
        } catch (JSONException e) {
            com.igexin.c.a.c.a.a(e);
            return null;
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public PushMessageInterface.ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        return PushMessageInterface.ActionPrepareState.success;
    }
}
