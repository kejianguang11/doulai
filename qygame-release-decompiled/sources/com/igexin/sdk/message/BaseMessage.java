package com.igexin.sdk.message;

import com.igexin.push.core.e;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1;
    private String appid = e.a;
    private String pkgName = e.g;
    private String clientId = e.A;

    public String getAppid() {
        return this.appid;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setAppid(String str) {
        this.appid = str;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }
}
