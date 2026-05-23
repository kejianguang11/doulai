package com.mobile.auth.gatewayauth.model.pns_vendor_query;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.Jsoner;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Result implements Jsoner {
    private String code;
    private String message;
    private Module module;

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
            Module module = new Module();
            if (jSONObject != null) {
                module.fromJson(jSONObject.optJSONObject("module"));
            }
            setModule(module);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String getCode() {
        try {
            return this.code;
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

    public String getMessage() {
        try {
            return this.message;
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

    public Module getModule() {
        try {
            return this.module;
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

    public void setCode(String str) {
        try {
            this.code = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setMessage(String str) {
        try {
            this.message = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setModule(Module module) {
        try {
            this.module = module;
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
                json.put("module", this.module == null ? new JSONObject() : this.module.toJson());
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
