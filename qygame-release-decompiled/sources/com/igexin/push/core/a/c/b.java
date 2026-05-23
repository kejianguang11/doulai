package com.igexin.push.core.a.c;

import com.getui.gtc.api.GtcManager;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b implements PushMessageInterface {
    private static final String a = "CleanExtAction";

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        if (pushTaskBean != null && baseActionBean != null) {
            com.igexin.push.core.b.g gVar = (com.igexin.push.core.b.g) baseActionBean;
            if (gVar.a != null && gVar.a.length > 0) {
                Arrays.toString(gVar.a);
                GtcManager.getInstance().removeExt(com.igexin.push.core.b.j, gVar.a);
            }
        }
        if ("".equals(baseActionBean.getDoActionId())) {
            return true;
        }
        com.igexin.push.core.a.b.d();
        com.igexin.push.core.a.b.a(pushTaskBean.getTaskId(), pushTaskBean.getMessageId(), baseActionBean.getDoActionId());
        return true;
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final BaseActionBean parseAction(JSONObject jSONObject) {
        if (!jSONObject.has("ids")) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(jSONObject.getString("ids"));
            if (jSONArray.length() <= 0) {
                return null;
            }
            int[] iArr = new int[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                iArr[i] = jSONArray.getInt(i);
            }
            com.igexin.push.core.b.g gVar = new com.igexin.push.core.b.g();
            gVar.setType(com.igexin.push.core.b.w);
            gVar.a = iArr;
            gVar.setActionId(jSONObject.getString("actionid"));
            gVar.setDoActionId(jSONObject.getString("do"));
            return gVar;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return null;
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public final PushMessageInterface.ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        return PushMessageInterface.ActionPrepareState.success;
    }
}
