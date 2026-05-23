package com.igexin.push.core.b;

import com.igexin.push.extension.mod.BaseActionBean;

/* JADX INFO: loaded from: classes.dex */
public final class i extends BaseActionBean {
    private String a;

    @Override // com.igexin.push.extension.mod.BaseActionBean
    public final String getActionId() {
        return this.a;
    }

    @Override // com.igexin.push.extension.mod.BaseActionBean
    public final void setActionId(String str) {
        this.a = str;
    }
}
