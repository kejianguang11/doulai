package com.nirvana.tools.logger.model;

import android.text.TextUtils;
import com.nirvana.tools.jsoner.JSONUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ACMLimitConfig implements Serializable {
    private boolean isLimited;
    private int limitCount;
    private int limitHours;

    public static final class Builder {
        private boolean isLimited;
        private int limitCount;
        private int limitHours;

        private Builder() {
        }

        public final ACMLimitConfig build() {
            return new ACMLimitConfig(this);
        }

        public final Builder isLimited(boolean z) {
            this.isLimited = z;
            return this;
        }

        public final Builder limitCount(int i) {
            this.limitCount = i;
            return this;
        }

        public final Builder limitHours(int i) {
            this.limitHours = i;
            return this;
        }
    }

    public ACMLimitConfig() {
    }

    private ACMLimitConfig(Builder builder) {
        this.isLimited = builder.isLimited;
        this.limitCount = builder.limitCount;
        this.limitHours = builder.limitHours;
    }

    public static ACMLimitConfig fromJson(String str) {
        ACMLimitConfig aCMLimitConfig = new ACMLimitConfig();
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONUtils.fromJson(new JSONObject(str), aCMLimitConfig, (List<Field>) null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aCMLimitConfig;
    }

    public static Builder newACMLimitConfig() {
        return new Builder();
    }

    public int getLimitCount() {
        return this.limitCount;
    }

    public int getLimitHours() {
        return this.limitHours;
    }

    public boolean isLimited() {
        return this.isLimited;
    }

    public void setLimitCount(int i) {
        this.limitCount = i;
    }

    public void setLimitHours(int i) {
        this.limitHours = i;
    }

    public void setLimited(boolean z) {
        this.isLimited = z;
    }

    public String toJsonString() {
        return JSONUtils.toJson(this, null).toString();
    }
}
