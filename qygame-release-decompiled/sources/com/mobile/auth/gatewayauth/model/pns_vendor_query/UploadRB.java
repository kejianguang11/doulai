package com.mobile.auth.gatewayauth.model.pns_vendor_query;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.Jsoner;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadRB implements Jsoner {
    private PscInfoUploadResponse alibaba_aliqin_psc_info_upload_response;

    public static UploadRB fromJson(String str) {
        try {
            UploadRB uploadRB = new UploadRB();
            try {
                if (!TextUtils.isEmpty(str)) {
                    uploadRB.fromJson(new JSONObject(str));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return uploadRB;
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

    @Override // com.nirvana.tools.jsoner.Jsoner
    public void fromJson(JSONObject jSONObject) {
        try {
            PscInfoUploadResponse pscInfoUploadResponse = new PscInfoUploadResponse();
            if (jSONObject != null) {
                pscInfoUploadResponse.fromJson(jSONObject.optJSONObject("alibaba_aliqin_psc_info_upload_response"));
            }
            setAlibaba_aliqin_psc_info_upload_response(pscInfoUploadResponse);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public PscInfoUploadResponse getAlibaba_aliqin_psc_info_upload_response() {
        try {
            return this.alibaba_aliqin_psc_info_upload_response;
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

    public void setAlibaba_aliqin_psc_info_upload_response(PscInfoUploadResponse pscInfoUploadResponse) {
        try {
            this.alibaba_aliqin_psc_info_upload_response = pscInfoUploadResponse;
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
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.putOpt("alibaba_aliqin_psc_info_upload_response", this.alibaba_aliqin_psc_info_upload_response == null ? new JSONObject() : this.alibaba_aliqin_psc_info_upload_response.toJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject;
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
