package com.nirvana.tools.logger.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.logger.model.ACMLimitConfig;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UTSharedPreferencesHelper {
    private static final String ALITX_LOGGER_DATA = "ALITX_LOGGER_DATA";
    private static final String AUTH_LIMIT_CONFIG_KEY = "AUTH_LIMIT_CONFIG_KEY";
    public static final String AUTH_LIMIT_SLS_KEY = "AUTH_LIMIT_SLS_KEY";

    public static synchronized void clearLimitCount(Context context) {
        put(context, ALITX_LOGGER_DATA, "AUTH_LIMIT_SLS_KEY", "");
        put(context, ALITX_LOGGER_DATA, AUTH_LIMIT_CONFIG_KEY, "");
    }

    public static void clearUTData(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(ALITX_LOGGER_DATA, 0).edit();
        editorEdit.clear();
        editorEdit.apply();
    }

    public static boolean contains(Context context, String str, String str2) {
        return context.getSharedPreferences(str, 0).contains(str2);
    }

    public static <T> T get(Context context, String str, String str2, T t) {
        try {
            if (contains(context, str, str2)) {
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
            }
        } catch (Exception unused) {
        }
        return t;
    }

    public static <T> void put(Context context, String str, String str2, T t) {
        try {
            context.getSharedPreferences(str, 0).edit().putString(str2, EncodeUtil.encode(t.toString())).commit();
        } catch (Exception unused) {
        }
    }

    public static synchronized ACMLimitConfig readLimitConfig(Context context) {
        ACMLimitConfig aCMLimitConfigFromJson;
        String str = (String) get(context, ALITX_LOGGER_DATA, AUTH_LIMIT_CONFIG_KEY, "");
        if (TextUtils.isEmpty(str)) {
            aCMLimitConfigFromJson = null;
        } else {
            try {
                aCMLimitConfigFromJson = ACMLimitConfig.fromJson(str);
            } catch (Exception unused) {
                return null;
            }
        }
        return aCMLimitConfigFromJson;
    }

    public static synchronized int readLimitCount(Context context, String str, String str2) {
        String str3 = (String) get(context, ALITX_LOGGER_DATA, str, "");
        Map<String, Integer> mapJson2MapForStringInteger = TextUtils.isEmpty(str3) ? null : JSONUtils.json2MapForStringInteger(str3);
        if (mapJson2MapForStringInteger == null || mapJson2MapForStringInteger.isEmpty() || !mapJson2MapForStringInteger.containsKey(str2)) {
            return 0;
        }
        return mapJson2MapForStringInteger.get(str2).intValue();
    }

    public static synchronized int readSLSLimitCount(Context context, String str) {
        return readLimitCount(context, "AUTH_LIMIT_SLS_KEY", str);
    }

    public static synchronized void saveLimitCount(Context context, String str, String str2) {
        int iValueOf;
        String str3 = (String) get(context, ALITX_LOGGER_DATA, str, "");
        Map<String, Integer> mapJson2MapForStringInteger = TextUtils.isEmpty(str3) ? null : JSONUtils.json2MapForStringInteger(str3);
        if (mapJson2MapForStringInteger == null || mapJson2MapForStringInteger.isEmpty() || !mapJson2MapForStringInteger.containsKey(str2)) {
            mapJson2MapForStringInteger = new HashMap<>();
            iValueOf = 1;
        } else {
            iValueOf = Integer.valueOf(mapJson2MapForStringInteger.get(str2).intValue() + 1);
        }
        mapJson2MapForStringInteger.put(str2, iValueOf);
        put(context, ALITX_LOGGER_DATA, str, new JSONObject(mapJson2MapForStringInteger).toString());
    }

    public static synchronized void saveSLSLimitCount(Context context, String str) {
        saveLimitCount(context, "AUTH_LIMIT_SLS_KEY", str);
    }

    public static synchronized void writeLimitConfig(Context context, ACMLimitConfig aCMLimitConfig) {
        put(context, ALITX_LOGGER_DATA, AUTH_LIMIT_CONFIG_KEY, aCMLimitConfig.toJsonString());
    }
}
