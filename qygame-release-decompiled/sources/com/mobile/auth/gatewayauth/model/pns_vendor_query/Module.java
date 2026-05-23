package com.mobile.auth.gatewayauth.model.pns_vendor_query;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.Jsoner;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Module implements Jsoner {
    private LimitedInfo limited_info;

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
            LimitedInfo limitedInfo = new LimitedInfo();
            if (jSONObject != null) {
                limitedInfo.fromJson(jSONObject.optJSONObject("limited_info"));
            }
            setLimited_info(limitedInfo);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public LimitedInfo getLimited_info() {
        try {
            return this.limited_info;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void setLimited_info(LimitedInfo limitedInfo) {
        try {
            this.limited_info = limitedInfo;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.nirvana.tools.jsoner.Jsoner
    public JSONObject toJson() {
        try {
            JSONObject json = JSONUtils.toJson(this, null);
            try {
                json.put("limited_info", this.limited_info == null ? new JSONObject() : this.limited_info.toJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
