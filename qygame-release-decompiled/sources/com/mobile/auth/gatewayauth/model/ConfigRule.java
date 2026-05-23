package com.mobile.auth.gatewayauth.model;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ConfigRule {
    private LimitConfig auth_token;
    private DowngradeInfoList downgrade_info_list;
    private LimitConfig get_config;
    private LimitConfig get_vendor_list;
    private String is_auth_demoted;
    private String is_crash_demoted;
    private String is_demoted;
    private String is_login_demoted;
    private String is_sls_demoted;
    private LimitConfig login_page;
    private LimitConfig login_phone;
    private LimitConfig login_token;
    private LimitConfig sls;
    private UploadLog upload_log;

    public static ConfigRule fromJson(String str) {
        try {
            ConfigRule configRule = new ConfigRule();
            try {
                if (!TextUtils.isEmpty(str)) {
                    return fromJson(new JSONObject(str));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return configRule;
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

    public static ConfigRule fromJson(JSONObject jSONObject) {
        try {
            ConfigRule configRule = new ConfigRule();
            if (jSONObject != null) {
                JSONUtils.fromJson(jSONObject, configRule, (List<Field>) null);
                configRule.setAuth_token((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("auth_token"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.1
                }, (List<Field>) null));
                configRule.setGet_config((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("get_config"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.2
                }, (List<Field>) null));
                configRule.setGet_vendor_list((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("get_vendor_list"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.3
                }, (List<Field>) null));
                configRule.setLogin_page((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("login_page"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.4
                }, (List<Field>) null));
                configRule.setLogin_phone((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("login_phone"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.5
                }, (List<Field>) null));
                configRule.setLogin_token((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("login_token"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.6
                }, (List<Field>) null));
                configRule.setSls((LimitConfig) JSONUtils.fromJson(jSONObject.optJSONObject("sls"), (JsonType) new JsonType<LimitConfig>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.7
                }, (List<Field>) null));
                configRule.setUpload_log((UploadLog) JSONUtils.fromJson(jSONObject.optJSONObject("upload_log"), (JsonType) new JsonType<UploadLog>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.8
                }, (List<Field>) null));
                configRule.setDowngradeInfoList((DowngradeInfoList) JSONUtils.fromJson(jSONObject.optJSONObject("downgrade_info_list"), (JsonType) new JsonType<DowngradeInfoList>() { // from class: com.mobile.auth.gatewayauth.model.ConfigRule.9
                }, (List<Field>) null));
            }
            return configRule;
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

    public LimitConfig getAuth_token() {
        try {
            return this.auth_token;
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

    public DowngradeInfoList getDowngradeInfoList() {
        try {
            return this.downgrade_info_list;
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

    public LimitConfig getGet_config() {
        try {
            return this.get_config;
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

    public LimitConfig getGet_vendor_list() {
        try {
            return this.get_vendor_list;
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

    public String getIs_auth_demoted() {
        try {
            return this.is_auth_demoted;
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

    public String getIs_crash_demoted() {
        try {
            return this.is_crash_demoted;
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

    public String getIs_demoted() {
        try {
            return this.is_demoted;
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

    public String getIs_login_demoted() {
        try {
            return this.is_login_demoted;
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

    public String getIs_sls_demoted() {
        try {
            return this.is_sls_demoted;
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

    public LimitConfig getLogin_page() {
        try {
            return this.login_page;
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

    public LimitConfig getLogin_phone() {
        try {
            return this.login_phone;
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

    public LimitConfig getLogin_token() {
        try {
            return this.login_token;
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

    public LimitConfig getSls() {
        try {
            return this.sls;
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

    public UploadLog getUpload_log() {
        try {
            return this.upload_log;
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

    public void setAuth_token(LimitConfig limitConfig) {
        try {
            this.auth_token = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setDowngradeInfoList(DowngradeInfoList downgradeInfoList) {
        try {
            this.downgrade_info_list = downgradeInfoList;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setGet_config(LimitConfig limitConfig) {
        try {
            this.get_config = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setGet_vendor_list(LimitConfig limitConfig) {
        try {
            this.get_vendor_list = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIs_auth_demoted(String str) {
        try {
            this.is_auth_demoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIs_crash_demoted(String str) {
        try {
            this.is_crash_demoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIs_demoted(String str) {
        try {
            this.is_demoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setIs_login_demoted(String str) {
        try {
            this.is_login_demoted = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public ConfigRule setIs_sls_demoted(String str) {
        try {
            this.is_sls_demoted = str;
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

    public void setLogin_page(LimitConfig limitConfig) {
        try {
            this.login_page = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLogin_phone(LimitConfig limitConfig) {
        try {
            this.login_phone = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setLogin_token(LimitConfig limitConfig) {
        try {
            this.login_token = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSls(LimitConfig limitConfig) {
        try {
            this.sls = limitConfig;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public ConfigRule setUpload_log(UploadLog uploadLog) {
        try {
            this.upload_log = uploadLog;
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

    public JSONObject toJson() {
        try {
            JSONObject json = JSONUtils.toJson(this, new ArrayList());
            try {
                json.put("auth_token", this.auth_token == null ? "" : this.auth_token.toJson());
                json.put("get_config", this.get_config == null ? "" : this.get_config.toJson());
                json.put("get_vendor_list", this.get_vendor_list == null ? "" : this.get_vendor_list.toJson());
                json.put("login_page", this.login_page == null ? "" : this.login_page.toJson());
                json.put("login_phone", this.login_phone == null ? "" : this.login_phone.toJson());
                json.put("login_token", this.login_token == null ? "" : this.login_token.toJson());
                json.put("sls", this.sls == null ? "" : this.sls.toJson());
                json.put("upload_log", this.upload_log == null ? "" : this.upload_log.toJson());
                if (this.downgrade_info_list != null) {
                    json.put("downgrade_info_list", this.downgrade_info_list == null ? "" : this.downgrade_info_list.toJson());
                }
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

    public String toJsonString() {
        try {
            JSONObject json = JSONUtils.toJson(this, new ArrayList());
            try {
                json.put("auth_token", this.auth_token == null ? "" : this.auth_token.toJson());
                json.put("get_config", this.get_config == null ? "" : this.get_config.toJson());
                json.put("get_vendor_list", this.get_vendor_list == null ? "" : this.get_vendor_list.toJson());
                json.put("login_page", this.login_page == null ? "" : this.login_page.toJson());
                json.put("login_phone", this.login_phone == null ? "" : this.login_phone.toJson());
                json.put("login_token", this.login_token == null ? "" : this.login_token.toJson());
                json.put("sls", this.sls == null ? "" : this.sls.toJson());
                json.put("upload_log", this.upload_log == null ? "" : this.upload_log.toJson());
                json.put("downgrade_info_list", this.downgrade_info_list == null ? "" : this.downgrade_info_list.toJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json.toString();
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
            return "ConfigRule{auth_token=" + this.auth_token + ", get_config=" + this.get_config + ", get_vendor_list=" + this.get_vendor_list + ", is_auth_demoted='" + this.is_auth_demoted + "', is_demoted='" + this.is_demoted + "', is_login_demoted='" + this.is_login_demoted + "', is_sls_demoted='" + this.is_sls_demoted + "', is_crash_demoted='" + this.is_crash_demoted + "', downgrade_info_list='" + this.downgrade_info_list + "', login_page=" + this.login_page + ", login_phone=" + this.login_phone + ", login_token=" + this.login_token + ", sls=" + this.sls + ", upload_log=" + this.upload_log + '}';
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
