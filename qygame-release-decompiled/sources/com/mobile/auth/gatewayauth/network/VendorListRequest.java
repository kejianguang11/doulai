package com.mobile.auth.gatewayauth.network;

import com.mobile.auth.BuildConfig;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.jsoner.JsonerTag;

/* JADX INFO: loaded from: classes.dex */
public class VendorListRequest extends AuthRequest {

    @JsonerTag(keyName = "AccessKeyId")
    private String AccessKeyId;

    @JsonerTag(keyName = "AppKey")
    private String AppKey = BuildConfig.APP_KEY;

    @JsonerTag(keyName = "SecurityToken")
    private String SecurityToken;

    @JsonerTag(keyName = "TerminalInfo")
    private String TerminalInfo;

    public String getAccessKeyId() {
        try {
            return this.AccessKeyId;
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

    public String getSecurityToken() {
        try {
            return this.SecurityToken;
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

    public String getTerminalInfo() {
        try {
            return this.TerminalInfo;
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

    public void setAccessKeyId(String str) {
        try {
            this.AccessKeyId = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setSecurityToken(String str) {
        try {
            this.SecurityToken = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void setTerminalInfo(String str) {
        try {
            this.TerminalInfo = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
