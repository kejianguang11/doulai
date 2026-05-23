package com.mobile.auth.gatewayauth.sdktools.upload.pns.model;

import com.igexin.push.core.d.d;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadMonitorDTO extends BaseUploadDTO {
    private static final long serialVersionUID = -7916879376930002080L;
    private String action;
    private String apiLevel;
    private Map<String, String> c;

    @Override // com.mobile.auth.gatewayauth.sdktools.upload.pns.model.BaseUploadDTO, com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            super.fromJson(jSONObject);
            setC(JSONUtils.json2MapForStringString(jSONObject.optJSONObject(d.d)));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String getAction() {
        try {
            return this.action;
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

    public String getApiLevel() {
        try {
            return this.apiLevel;
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

    public Map<String, String> getC() {
        try {
            return this.c;
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

    public UploadMonitorDTO setAction(String str) {
        try {
            this.action = str;
            return this;
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

    public UploadMonitorDTO setApiLevel(String str) {
        try {
            this.apiLevel = str;
            return this;
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

    public UploadMonitorDTO setC(Map<String, String> map) {
        try {
            this.c = map;
            return this;
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

    @Override // com.mobile.auth.gatewayauth.sdktools.upload.pns.model.BaseUploadDTO, com.nirvana.tools.jsoner.Jsoner
    public JSONObject toJson() {
        try {
            JSONObject json = super.toJson();
            try {
                json.put(d.d, new JSONObject(this.c));
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

    @Override // com.mobile.auth.gatewayauth.sdktools.upload.pns.model.BaseUploadDTO
    public String toString() {
        try {
            return super.toString() + "UploadMonitorDTO{action='" + this.action + "', apiLevel='" + this.apiLevel + "', c=" + this.c + '}';
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
