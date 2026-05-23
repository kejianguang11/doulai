package com.igexin.push.extension.mod;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface PushMessageInterface {

    public enum ActionPrepareState {
        success,
        wait,
        stop
    }

    boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean);

    BaseActionBean parseAction(JSONObject jSONObject);

    ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean);
}
