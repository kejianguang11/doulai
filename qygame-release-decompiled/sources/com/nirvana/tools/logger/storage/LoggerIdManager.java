package com.nirvana.tools.logger.storage;

import android.content.Context;
import android.text.TextUtils;
import com.nirvana.tools.core.AppUtils;
import com.nirvana.tools.core.CryptUtil;
import com.nirvana.tools.logger.utils.UTSharedPreferencesHelper;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class LoggerIdManager {
    public static final String AUTH_APP_INFO = "AUTH_APP_INFO";
    private static final String KEY_UNIQUE_ID = "uniqueId";
    private Context mContext;

    public LoggerIdManager(Context context) {
        this.mContext = context;
    }

    private String generateUniqueId(Context context) {
        try {
            return CryptUtil.md5Hex(UUID.randomUUID().toString() + AppUtils.getPackageName(context) + AppUtils.getSign(context) + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUniqueId() {
        String str = (String) UTSharedPreferencesHelper.get(this.mContext, "AUTH_APP_INFO", KEY_UNIQUE_ID, "");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String strGenerateUniqueId = generateUniqueId(this.mContext);
        UTSharedPreferencesHelper.put(this.mContext, "AUTH_APP_INFO", KEY_UNIQUE_ID, strGenerateUniqueId);
        return strGenerateUniqueId;
    }
}
