package com.mobile.auth.gatewayauth.manager.compat;

import com.igexin.push.core.b.n;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.model.TokenRet;

/* JADX INFO: loaded from: classes.dex */
public class a implements ResultCodeProcessor {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private String a(String str) {
        if (str == null) {
            return ResultCode.CODE_GET_TOKEN_FAIL;
        }
        try {
            byte b = -1;
            int iHashCode = str.hashCode();
            if (iHashCode != 1656378) {
                if (iHashCode != 1715960) {
                    if (iHashCode != 1335041987) {
                        switch (iHashCode) {
                            case 1335041957:
                                if (str.equals(Constant.CODE_ERROR_START_AUTH_PAGE_FAIL)) {
                                    b = 2;
                                }
                                break;
                            case 1335041958:
                                if (str.equals(Constant.CODE_ERROR_GET_CONFIG_FAIL)) {
                                    b = 4;
                                }
                                break;
                            case 1335041959:
                                if (str.equals(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL)) {
                                    b = 5;
                                }
                                break;
                            case 1335041960:
                                if (str.equals(Constant.CODE_ERROR_NO_PERMISSION_FAIL)) {
                                    b = 6;
                                }
                                break;
                            case 1335041961:
                                if (str.equals(Constant.CODE_ERROR_NO_SIM_FAIL)) {
                                    b = 7;
                                }
                                break;
                            case 1335041962:
                                if (str.equals(Constant.CODE_ERROR_NO_MOBILE_NETWORK_FAIL)) {
                                    b = 8;
                                }
                                break;
                            case 1335041963:
                                if (str.equals(Constant.CODE_ERROR_OPERATOR_UNKNOWN_FAIL)) {
                                    b = 9;
                                }
                                break;
                            case 1335041964:
                                if (str.equals(Constant.CODE_ERROR_UNKNOWN_FAIL)) {
                                    b = 10;
                                }
                                break;
                            case 1335041965:
                                if (str.equals(Constant.CODE_ERROR_FUNCTION_DEMOTE)) {
                                    b = n.l;
                                }
                                break;
                            default:
                                switch (iHashCode) {
                                    case 1340651407:
                                        if (str.equals(Constant.CODE_ERROR_USER_CANCEL)) {
                                            b = 3;
                                        }
                                        break;
                                    case 1340651408:
                                        if (str.equals(Constant.CODE_ERROR_USER_SWITCH)) {
                                            b = 13;
                                        }
                                        break;
                                }
                                break;
                        }
                    } else if (str.equals(Constant.CODE_ERROR_FUNCTION_LIMIT)) {
                        b = 12;
                    }
                } else if (str.equals(Constant.CODE_GET_TOKEN_SUCCESS)) {
                    b = 0;
                }
            } else if (str.equals(Constant.CODE_START_AUTH_PAGE_SUCCESS)) {
                b = 1;
            }
            switch (b) {
                case 0:
                    return "600000";
                case 1:
                    return ResultCode.CODE_START_AUTHPAGE_SUCCESS;
                case 2:
                    return ResultCode.CODE_ERROR_START_AUTHPAGE_FAIL;
                case 3:
                    return ResultCode.CODE_ERROR_USER_CANCEL;
                case 4:
                    return ResultCode.CODE_ERROR_GET_CONFIG_FAIL;
                case 5:
                    return ResultCode.CODE_ERROR_PHONE_UNSAFE_FAIL;
                case 6:
                    return ResultCode.CODE_ERROR_NO_PERMISSION_FAIL;
                case 7:
                    return ResultCode.CODE_ERROR_NO_SIM_FAIL;
                case 8:
                    return ResultCode.CODE_ERROR_NO_MOBILE_NETWORK_FAIL;
                case 9:
                    return ResultCode.CODE_ERROR_OPERATOR_UNKNOWN_FAIL;
                case 10:
                    return ResultCode.CODE_ERROR_UNKNOWN_FAIL;
                case 11:
                    return ResultCode.CODE_ERROR_FUNCTION_DEMOTE;
                case 12:
                    return ResultCode.CODE_ERROR_FUNCTION_LIMIT;
                case 13:
                    return ResultCode.CODE_ERROR_USER_SWITCH;
                default:
                    return str;
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
    }

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public String convertCode(String str) {
        try {
            return a(str);
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

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public TokenRet convertErrorInfo(String str, String str2, String str3) {
        try {
            TokenRet tokenRet = new TokenRet();
            tokenRet.setCode(a(str));
            tokenRet.setMsg(str2);
            if (Constant.VENDOR_CUCC.equals(str3)) {
                tokenRet.setVendorName(Constant.VENDOR_CUXZ);
            } else {
                tokenRet.setVendorName(str3);
            }
            return tokenRet;
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

    @Override // com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor
    public String getApiLevel() {
        try {
            return String.valueOf(1);
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
