package com.igexin.sdk.message;

import android.text.TextUtils;
import com.igexin.c.a.c.a;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Label implements Serializable {
    private long endTime = 0;
    private String extraData;
    private String tagId;
    private String tagName;
    private String tagVersion;

    public long getEndTime() {
        return this.endTime;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public JSONObject getJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tagId", this.tagId);
            if (!TextUtils.isEmpty(this.tagName)) {
                jSONObject.put("tagName", this.tagName);
            }
            if (!TextUtils.isEmpty(this.tagVersion)) {
                jSONObject.put("tagVersion", this.tagVersion);
            }
            jSONObject.put("endTime", this.endTime);
            if (!TextUtils.isEmpty(this.extraData)) {
                jSONObject.put("extraData", this.extraData);
            }
        } catch (JSONException e) {
            a.a(e);
        }
        return jSONObject;
    }

    public String getTagId() {
        return this.tagId;
    }

    public String getTagName() {
        return this.tagName;
    }

    public String getTagVersion() {
        return this.tagVersion;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public void setExtraData(String str) {
        this.extraData = str;
    }

    public void setTagId(String str) {
        this.tagId = str;
    }

    public void setTagName(String str) {
        this.tagName = str;
    }

    public void setTagVersion(String str) {
        this.tagVersion = str;
    }
}
