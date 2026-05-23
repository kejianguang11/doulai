package com.igexin.push.core.a.c;

import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.b.t;
import com.igexin.push.core.d;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class m implements PushMessageInterface {
    private static final String a = com.igexin.push.core.b.f + m.class.getName();

    private static void a(String str) {
        try {
            com.igexin.c.a.c.a.a(a + "|del condition taskid = " + str, new Object[0]);
            d.a.a.i.a(com.igexin.push.core.b.Z, new String[]{"taskid"}, new String[]{str});
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            com.igexin.c.a.c.a.b(a, "del condition" + th.toString());
        }
    }

    private static void a(String str, BaseActionBean baseActionBean) {
        if (baseActionBean == null) {
            return;
        }
        com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) baseActionBean;
        String str2 = lVar.q;
        HashSet<String> hashSet = com.igexin.push.core.e.aj.get(lVar.q);
        Integer num = com.igexin.push.core.e.ak.get(lVar.q);
        if (hashSet != null && !hashSet.isEmpty()) {
            hashSet.remove(str);
        }
        if (TextUtils.isEmpty(str2) || num == null || hashSet == null || !hashSet.isEmpty()) {
            return;
        }
        ((NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n)).cancel(num.intValue());
        com.igexin.push.core.e.aj.remove(str2);
        com.igexin.push.core.e.ak.remove(str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x018d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0173 A[PHI: r5 r9
      0x0173: PHI (r5v8 android.database.Cursor) = (r5v7 android.database.Cursor), (r5v11 android.database.Cursor) binds: [B:64:0x0180, B:56:0x0171] A[DONT_GENERATE, DONT_INLINE]
      0x0173: PHI (r9v2 boolean) = (r9v1 boolean), (r9v15 boolean) binds: [B:64:0x0180, B:56:0x0171] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x026c  */
    @Override // com.igexin.push.extension.mod.PushMessageInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) throws Throwable {
        Cursor cursorA;
        boolean z;
        PushTaskBean pushTaskBean2;
        String str;
        String str2;
        String strA = "";
        t tVar = (t) baseActionBean;
        String str3 = tVar.a;
        NotificationManager notificationManager = (NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n);
        if (TextUtils.isEmpty(str3)) {
            z = false;
        } else {
            try {
                cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, new String[]{"taskid"}, new String[]{str3}, null, "id ASC");
            } catch (Throwable th) {
                th = th;
                cursorA = null;
                if (cursorA != null) {
                }
                throw th;
            }
            if (cursorA != null) {
                try {
                    try {
                        if (cursorA.moveToFirst()) {
                            String string = cursorA.getString(cursorA.getColumnIndex("messageid"));
                            com.igexin.push.core.a.b.d();
                            strA = com.igexin.push.core.a.b.a(str3, string);
                        }
                        if (strA.equals("") || (pushTaskBean2 = com.igexin.push.core.e.ah.get(strA)) == null) {
                            z = false;
                        } else {
                            pushTaskBean2.setStop(true);
                            try {
                                com.igexin.c.a.c.a.a(a + "|del condition taskid = " + str3, new Object[0]);
                                d.a.a.i.a(com.igexin.push.core.b.Z, new String[]{"taskid"}, new String[]{str3});
                            } catch (Throwable th2) {
                                com.igexin.c.a.c.a.a(th2);
                                com.igexin.c.a.c.a.b(a, "del condition" + th2.toString());
                            }
                            String strValueOf = String.valueOf(pushTaskBean2.getPerActionid());
                            if (!strValueOf.equals("0") && pushTaskBean2.getBaseAction(strValueOf).getType().equals(com.igexin.push.core.b.n) && com.igexin.push.core.e.ai.containsKey(str3)) {
                                notificationManager.cancel(com.igexin.push.core.e.ai.get(str3).intValue());
                                com.igexin.push.core.e.ai.remove(str3);
                                try {
                                    BaseActionBean actionByType = pushTaskBean2.getActionByType(com.igexin.push.core.b.n);
                                    if (actionByType != null) {
                                        com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) actionByType;
                                        String str4 = lVar.q;
                                        HashSet<String> hashSet = com.igexin.push.core.e.aj.get(lVar.q);
                                        Integer num = com.igexin.push.core.e.ak.get(lVar.q);
                                        if (hashSet != null && !hashSet.isEmpty()) {
                                            hashSet.remove(str3);
                                        }
                                        if (!TextUtils.isEmpty(str4) && num != null && hashSet != null && hashSet.isEmpty()) {
                                            ((NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n)).cancel(num.intValue());
                                            com.igexin.push.core.e.aj.remove(str4);
                                            com.igexin.push.core.e.ak.remove(str4);
                                        }
                                    }
                                    z = true;
                                } catch (Throwable th3) {
                                    th = th3;
                                    z = true;
                                    com.igexin.c.a.c.a.a(th);
                                    if (cursorA != null) {
                                    }
                                    if (!z) {
                                    }
                                    if (!baseActionBean.getDoActionId().equals("")) {
                                    }
                                    return true;
                                }
                            } else {
                                z = false;
                            }
                            try {
                                com.igexin.push.core.i.a aVarA = com.igexin.push.core.i.b.a().a(Long.valueOf(com.igexin.push.core.g.b));
                                if (aVarA != null && aVarA.b().equals(str3)) {
                                    com.igexin.push.core.i.b bVarA = com.igexin.push.core.i.b.a();
                                    if (aVarA != null) {
                                        bVarA.a(aVarA);
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                com.igexin.c.a.c.a.a(th);
                                if (cursorA != null) {
                                }
                            }
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        if (cursorA != null) {
                            cursorA.close();
                        }
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    z = false;
                    com.igexin.c.a.c.a.a(th);
                    if (cursorA != null) {
                    }
                    if (!z) {
                    }
                    if (!baseActionBean.getDoActionId().equals("")) {
                    }
                    return true;
                }
                if (cursorA != null) {
                    cursorA.close();
                }
            }
        }
        if (!z) {
            try {
            } catch (Throwable th7) {
                com.igexin.c.a.c.a.a(th7);
            }
            if (tVar.b) {
                com.igexin.c.a.c.a.a(a + " | cancelAll()", new Object[0]);
                notificationManager.cancelAll();
                com.igexin.assist.sdk.a aVarA2 = com.igexin.assist.sdk.a.a();
                Context context = com.igexin.push.core.e.l;
                if (aVarA2.b != null && aVarA2.b.isSupport()) {
                    if (aVarA2.b.getBrandCode().equals(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM)) {
                        try {
                            Class.forName("com.xiaomi.mipush.sdk.MiPushClient").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
                        } catch (Throwable th8) {
                            com.igexin.c.a.c.a.a(th8);
                            com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th8.toString(), new Object[0]);
                        }
                        str = com.igexin.assist.sdk.a.a;
                        str2 = " cancelAllAssistNotification() XM ";
                    } else {
                        if (aVarA2.b.getBrandCode().equals(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_MZ)) {
                            try {
                                Class.forName("com.meizu.cloud.pushsdk.PushManager").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
                            } catch (Throwable th9) {
                                com.igexin.c.a.c.a.a(th9);
                                com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th9.toString(), new Object[0]);
                            }
                            str = com.igexin.assist.sdk.a.a;
                            str2 = " cancelAllAssistNotification() MZ ";
                        }
                        com.igexin.c.a.c.a.a(th7);
                    }
                    com.igexin.c.a.c.a.b(str, str2);
                }
                com.igexin.push.core.e.ak.clear();
                com.igexin.push.core.e.aj.clear();
            }
        }
        if (!baseActionBean.getDoActionId().equals("")) {
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), baseActionBean.getDoActionId());
        }
        return true;
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public BaseActionBean parseAction(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("taskid")) {
                return null;
            }
            t tVar = new t();
            tVar.setType(com.igexin.push.core.b.o);
            tVar.setActionId(jSONObject.getString("actionid"));
            tVar.setDoActionId(jSONObject.getString("do"));
            tVar.a = jSONObject.getString("taskid");
            tVar.b = jSONObject.optBoolean("force");
            return tVar;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return null;
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public PushMessageInterface.ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        return PushMessageInterface.ActionPrepareState.success;
    }
}
