package com.igexin.push.core.a.b;

import android.content.Intent;
import android.os.Bundle;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class j extends a {
    @Override // com.igexin.push.core.a.b.a
    public final boolean a(Object obj, JSONObject jSONObject) {
        long j;
        try {
            if (!jSONObject.has("action") || !jSONObject.getString("action").equals("sendmessage_feedback")) {
                return true;
            }
            String string = jSONObject.getString("appid");
            String string2 = jSONObject.getString("taskid");
            String string3 = jSONObject.getString("actionid");
            String string4 = jSONObject.getString(com.alipay.sdk.util.j.c);
            long j2 = jSONObject.getLong(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP);
            com.igexin.c.a.c.a.a("SendMessageFeedbackAction|appid:" + string + "|taskid:" + string2 + "|actionid:" + string3, new Object[0]);
            com.igexin.push.core.l lVarA = com.igexin.push.core.l.a();
            if (com.igexin.push.core.e.a == null || !com.igexin.push.core.e.a.equals(string)) {
                j = j2;
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("action", 10010);
                j = j2;
                bundle.putSerializable(PushConsts.KEY_CMD_MSG, new FeedbackCmdMessage(string2, string3, string4, j2, PushConsts.THIRDPART_FEEDBACK));
                lVarA.a(bundle);
            }
            Intent intentD = com.igexin.push.core.l.d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("action", PushConsts.THIRDPART_FEEDBACK);
            bundle2.putString("appid", string);
            bundle2.putString("taskid", string2);
            bundle2.putString("actionid", string3);
            bundle2.putString(com.alipay.sdk.util.j.c, string4);
            bundle2.putLong(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP, j);
            intentD.putExtras(bundle2);
            com.igexin.push.core.e.l.sendBroadcast(intentD, com.igexin.push.core.e.ac);
            return true;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return true;
        }
    }
}
