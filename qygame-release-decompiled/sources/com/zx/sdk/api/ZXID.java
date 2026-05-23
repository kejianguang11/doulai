package com.zx.sdk.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.zx.a.I8b7.v2;
import java.io.Serializable;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ZXID implements Serializable {
    private String aids;
    private long expiredTime;
    private int ot;
    private String tags;
    private String value;
    private String version;
    private String openid = "";
    private String taid = "";

    public JSONObject getAids() {
        JSONObject jSONObject = new JSONObject();
        try {
            return !TextUtils.isEmpty(this.aids) ? new JSONObject(this.aids) : jSONObject;
        } catch (Exception e) {
            v2.a(e);
            return jSONObject;
        }
    }

    public long getExpiredTime() {
        return this.expiredTime;
    }

    public int getOT() {
        return this.ot;
    }

    public String getOpenid() {
        return this.openid;
    }

    public String getTags() {
        return this.tags;
    }

    public String getTaid() {
        return this.taid;
    }

    public String getValue() {
        return this.value;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() / 1000 >= this.expiredTime;
    }

    public void setAids(String str) {
        this.aids = str;
    }

    public void setExpiredTime(long j) {
        this.expiredTime = j;
    }

    public void setOT(int i) {
        this.ot = i;
    }

    public void setOpenid(String str) {
        this.openid = str;
    }

    public void setTags(String str) {
        this.tags = str;
    }

    public void setTaid(String str) {
        this.taid = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    @NonNull
    public String toString() {
        return getValue();
    }
}
