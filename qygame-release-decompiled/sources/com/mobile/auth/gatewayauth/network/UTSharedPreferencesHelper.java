package com.mobile.auth.gatewayauth.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.core.EncodeUtil;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UTSharedPreferencesHelper {
    public static final String AUTH_APP_INFO = "AUTH_APP_INFO";
    private static final String AUTH_CUCC_PL_KEY = "AUTH_CUCC_PL_KEY";
    public static final String AUTH_FLAG_CLOSE_GET_CONFIG_KEY = "AUTH_FLAG_CLOSE_GET_CONFIG_KEY";
    public static final String AUTH_FLAG_LIMIT_GET_CONFIG_KEY = "AUTH_FLAG_LIMIT_GET_CONFIG_KEY";
    public static final String AUTH_LIMIT_AUTH_TOKEN_KEY = "AUTH_LIMIT_AUTH_TOKEN_KEY";
    public static final String AUTH_LIMIT_GET_CONFIG_KEY = "AUTH_LIMIT_GET_CONFIG_KEY";
    public static final String AUTH_LIMIT_LOGIN_PAGE_KEY = "AUTH_LIMIT_LOGIN_PAGE_KEY";
    public static final String AUTH_LIMIT_LOGIN_PHONE_KEY = "AUTH_LIMIT_LOGIN_PHONE_KEY";
    public static final String AUTH_LIMIT_LOGIN_TOKEN_KEY = "AUTH_LIMIT_LOGIN_TOKEN_KEY";
    public static final String AUTH_LIMIT_SLS_KEY = "AUTH_LIMIT_SLS_KEY";
    public static final String AUTH_LIMIT_VENDOR_LIST_KEY = "AUTH_LIMIT_VENDOR_LIST_KEY";
    private static final String AUTH_PL_DATA_KEY = "AUTH_PL_DATA_KEY";
    public static final String AUTH_PRIVATE_KEY = "AUTH_PRIVATE_KEY";
    private static final String AUTH_SDK_CONFIG_KEY = "AUTH_SDK_CONFIG_KEY";
    private static final String AUTH_UT_DATA = "AUTH_UT_DATA";
    private static final String AUTH_UT_DATA_KEY = "AUTH_UT_DATA_KEY";
    public static final String FILE_VENDOR_CONFIG_KEY = "FILE_VENDOR_CONFIG_KEY";
    public static final String LIFE_BODY_VERIFY_CID_KEY = "LIFE_BODY_VERIFY_CID_KEY";
    public static final String LOGIN_TOKEN_KEY = "LOGIN_TOKEN_KEY";
    public static final String MASK_IMSI_KEY = "MASK_IMSI_KEY";
    public static final String MASK_LOCAL_IP_KEY = "MASK_LOCAL_IP_KEY";
    public static final String VERIFY_TOKEN_KEY = "VERIFY_TOKEN_KEY";
    public static final String WIFI_SETUP_FLAG = "wifi_setup_flag";

    public static synchronized void clearAppInfo(Context context, String str) {
        try {
            clearInfo(context, "AUTH_APP_INFO", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void clearCUCCPreLoginCount(Context context) {
        try {
            put(context, AUTH_UT_DATA, AUTH_CUCC_PL_KEY, "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void clearInfo(Context context, String str, String str2) {
        try {
            context.getSharedPreferences(str, 0).edit().remove(str2).commit();
        } catch (Exception unused) {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void clearLimitCount(Context context) {
        try {
            put(context, AUTH_UT_DATA, "AUTH_LIMIT_SLS_KEY", "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_VENDOR_LIST_KEY, "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_GET_CONFIG_KEY, "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_AUTH_TOKEN_KEY, "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_LOGIN_TOKEN_KEY, "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_LOGIN_PHONE_KEY, "");
            put(context, AUTH_UT_DATA, AUTH_LIMIT_LOGIN_PAGE_KEY, "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void clearUTData(Context context) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(AUTH_UT_DATA, 0).edit();
            editorEdit.clear();
            editorEdit.apply();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean contains(Context context, String str, String str2) {
        try {
            return context.getSharedPreferences(str, 0).contains(str2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static <T> T get(Context context, String str, String str2, T t) {
        try {
            if (!contains(context, str, str2)) {
                return t;
            }
            String strDecode = EncodeUtil.decode(context.getSharedPreferences(str, 0).getString(str2, ""));
            if (t instanceof Integer) {
                return (T) Integer.valueOf(strDecode);
            }
            if (t instanceof Boolean) {
                return (T) Boolean.valueOf(strDecode);
            }
            if (t instanceof Long) {
                return (T) Long.valueOf(strDecode);
            }
            if (t instanceof String) {
                return (T) String.valueOf(strDecode);
            }
            throw new Exception("unsupported type");
        } catch (Exception unused) {
            return t;
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

    public static synchronized <T> T getAppInfo(Context context, String str, T t) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (T) get(context, "AUTH_APP_INFO", str, t);
    }

    public static <T> void put(Context context, String str, String str2, T t) {
        try {
            try {
                context.getSharedPreferences(str, 0).edit().putString(str2, EncodeUtil.encode(t.toString())).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized <T> void putAppInfo(Context context, String str, T t) {
        try {
            put(context, "AUTH_APP_INFO", str, t);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized String readAuthSDKPrivateKey(Context context) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (String) get(context, "AUTH_APP_INFO", "AUTH" + PackageUtils.getPackageName(context) + PackageUtils.getSign(context), "");
    }

    public static synchronized int readAuthTokenLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_AUTH_TOKEN_KEY, str);
    }

    public static synchronized int readConfigLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_GET_CONFIG_KEY, str);
    }

    public static synchronized int readLimitCount(Context context, String str, String str2) {
        try {
            String str3 = (String) get(context, AUTH_UT_DATA, str, "");
            Map<String, Integer> mapJson2MapForStringInteger = TextUtils.isEmpty(str3) ? null : JSONUtils.json2MapForStringInteger(str3);
            if (mapJson2MapForStringInteger == null || mapJson2MapForStringInteger.isEmpty() || !mapJson2MapForStringInteger.containsKey(str2)) {
                return 0;
            }
            return mapJson2MapForStringInteger.get(str2).intValue();
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

    public static synchronized int readLoginPageLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_LOGIN_PAGE_KEY, str);
    }

    public static synchronized int readLoginPhoneLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_LOGIN_PHONE_KEY, str);
    }

    public static synchronized int readLoginTokenLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_LOGIN_TOKEN_KEY, str);
    }

    public static synchronized ConfigRule readSDKConfig(Context context) {
        ConfigRule configRuleFromJson;
        try {
            String str = (String) get(context, AUTH_UT_DATA, AUTH_SDK_CONFIG_KEY, "");
            if (TextUtils.isEmpty(str)) {
                configRuleFromJson = null;
            } else {
                try {
                    configRuleFromJson = ConfigRule.fromJson(str);
                } catch (Exception unused) {
                    return null;
                }
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
        return configRuleFromJson;
    }

    public static synchronized boolean readSDKConfigCloseFlag(Context context) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
        return ((Boolean) get(context, AUTH_UT_DATA, AUTH_FLAG_CLOSE_GET_CONFIG_KEY, false)).booleanValue();
    }

    public static synchronized String readSDKConfigLimitFlag(Context context) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (String) get(context, AUTH_UT_DATA, AUTH_FLAG_LIMIT_GET_CONFIG_KEY, "");
    }

    public static synchronized int readSLSLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, "AUTH_LIMIT_SLS_KEY", str);
    }

    public static List<MonitorStruct> readUTInfo(Context context) {
        try {
            String str = (String) get(context, AUTH_UT_DATA, AUTH_UT_DATA_KEY, "");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return JSONUtils.parseJsonArray2JsonerList(str, new JsonType<MonitorStruct>() { // from class: com.mobile.auth.gatewayauth.network.UTSharedPreferencesHelper.1
            });
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

    public static synchronized int readVendorLimitCount(Context context, String str) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return readLimitCount(context, AUTH_LIMIT_VENDOR_LIST_KEY, str);
    }

    public static synchronized void saveAuthSDKPrivateKey(Context context, String str) {
        try {
            put(context, "AUTH_APP_INFO", "AUTH" + PackageUtils.getPackageName(context) + PackageUtils.getSign(context), str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveAuthTokenLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_AUTH_TOKEN_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveConfigLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_GET_CONFIG_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveLimitCount(Context context, String str, String str2) {
        int iValueOf;
        try {
            String str3 = (String) get(context, AUTH_UT_DATA, str, "");
            Map<String, Integer> mapJson2MapForStringInteger = TextUtils.isEmpty(str3) ? null : JSONUtils.json2MapForStringInteger(str3);
            if (mapJson2MapForStringInteger == null || mapJson2MapForStringInteger.isEmpty() || !mapJson2MapForStringInteger.containsKey(str2)) {
                mapJson2MapForStringInteger = new HashMap<>();
                iValueOf = 1;
            } else {
                iValueOf = Integer.valueOf(mapJson2MapForStringInteger.get(str2).intValue() + 1);
            }
            mapJson2MapForStringInteger.put(str2, iValueOf);
            put(context, AUTH_UT_DATA, str, new JSONObject(mapJson2MapForStringInteger).toString());
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveLoginPageLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_LOGIN_PAGE_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveLoginPhoneLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_LOGIN_PHONE_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveLoginTokenLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_LOGIN_TOKEN_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveSDKConfig(Context context, String str) {
        try {
            put(context, AUTH_UT_DATA, AUTH_SDK_CONFIG_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveSDKConfigCloseFlag(Context context, boolean z) {
        try {
            put(context, AUTH_UT_DATA, AUTH_FLAG_CLOSE_GET_CONFIG_KEY, Boolean.valueOf(z));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveSDKConfigLimitFlag(Context context, String str) {
        try {
            put(context, AUTH_UT_DATA, AUTH_FLAG_LIMIT_GET_CONFIG_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveSLSLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, "AUTH_LIMIT_SLS_KEY", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveUTInfo(Context context, MonitorStruct monitorStruct) {
        try {
            List uTInfo = readUTInfo(context);
            if (uTInfo == null) {
                uTInfo = new ArrayList();
            }
            uTInfo.add(monitorStruct);
            put(context, AUTH_UT_DATA, AUTH_UT_DATA_KEY, JSONUtils.jsonerList2JsonArray(uTInfo));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void saveUTInfos(Context context, String str) {
        try {
            put(context, AUTH_UT_DATA, AUTH_UT_DATA_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static synchronized void saveVendorLimitCount(Context context, String str) {
        try {
            saveLimitCount(context, AUTH_LIMIT_VENDOR_LIST_KEY, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
