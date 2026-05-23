package com.mobile.auth.gatewayauth.network;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.jsoner.Jsoner;
import com.nirvana.tools.jsoner.JsonerTag;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PrivateRespone implements Jsoner {

    @JsonerTag(keyName = "Code")
    private String Code;

    @JsonerTag(keyName = "Data")
    private PrivateKeyRespone Data;

    @JsonerTag(keyName = "Message")
    private String Message;

    @JsonerTag(keyName = "RequestId")
    private String RequestId;

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
            if (jSONObject != null) {
                setData((PrivateKeyRespone) JSONUtils.fromJson(jSONObject.optJSONObject("Data"), (JsonType) new JsonType<PrivateKeyRespone>() { // from class: com.mobile.auth.gatewayauth.network.PrivateRespone.1
                }, (List<Field>) null));
            }
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
            return this.Code;
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

    public PrivateKeyRespone getData() {
        try {
            return this.Data;
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
            return this.Message;
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

    public String getRequestId() {
        try {
            return this.RequestId;
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
            this.Code = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setData(PrivateKeyRespone privateKeyRespone) {
        try {
            this.Data = privateKeyRespone;
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
            this.Message = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setRequestId(String str) {
        try {
            this.RequestId = str;
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
            return JSONUtils.toJson(this, null);
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
