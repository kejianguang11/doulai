package com.mobile.auth.gatewayauth.model.cucctoken;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import com.nirvana.tools.jsoner.Jsoner;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CUCCTokenRB implements Jsoner {
    public static final int CUCC_RB_SUCCESS = 0;
    private int resultCode;
    private ResultData resultData;
    private String resultMsg;
    private String traceId;

    public static CUCCTokenRB fromJson(String str) {
        try {
            try {
                if (!TextUtils.isEmpty(str)) {
                    JSONObject jSONObject = new JSONObject(str);
                    CUCCTokenRB cUCCTokenRB = (CUCCTokenRB) JSONUtils.fromJson(jSONObject, (JsonType) new JsonType<CUCCTokenRB>() { // from class: com.mobile.auth.gatewayauth.model.cucctoken.CUCCTokenRB.1
                    }, (List<Field>) null);
                    cUCCTokenRB.setResultData((ResultData) JSONUtils.fromJson(jSONObject.optJSONObject("resultData"), (JsonType) new JsonType<ResultData>() { // from class: com.mobile.auth.gatewayauth.model.cucctoken.CUCCTokenRB.2
                    }, (List<Field>) null));
                    return cUCCTokenRB;
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return null;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            JSONUtils.fromJson(jSONObject, this, (List<Field>) null);
            if (jSONObject != null) {
                setResultData((ResultData) JSONUtils.fromJson(jSONObject.optJSONObject("resultData"), (JsonType) new JsonType<ResultData>() { // from class: com.mobile.auth.gatewayauth.model.cucctoken.CUCCTokenRB.3
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

    public int getResultCode() {
        try {
            return this.resultCode;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public ResultData getResultData() {
        try {
            return this.resultData;
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

    public String getResultMsg() {
        try {
            return this.resultMsg;
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

    public String getTraceId() {
        try {
            return this.traceId;
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

    public void setResultCode(int i) {
        try {
            this.resultCode = i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setResultData(ResultData resultData) {
        try {
            this.resultData = resultData;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setResultMsg(String str) {
        try {
            this.resultMsg = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setTraceId(String str) {
        try {
            this.traceId = str;
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
                json.put("resultData", this.resultData == null ? new JSONObject() : this.resultData.toJson());
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

    public String toString() {
        try {
            return "CUCCTokenRB{resultCode=" + this.resultCode + ", resultMsg='" + this.resultMsg + "', resultData=" + this.resultData + '}';
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
