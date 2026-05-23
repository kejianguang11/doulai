package com.nirvana.tools.cache;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CacheHandler {
    private static final String KEY_CONTENT = "content";
    private static final String KEY_VERSION = "version";
    private CacheRepository mRepository;

    CacheHandler(CacheRepository cacheRepository) {
        this.mRepository = null;
        this.mRepository = cacheRepository;
    }

    public String load() {
        if (this.mRepository != null) {
            String str = this.mRepository.read();
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (this.mRepository.getTemplate().getCacheVersion() == jSONObject.optInt(KEY_VERSION, -1)) {
                        return jSONObject.optString("content");
                    }
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean save(String str) {
        if (this.mRepository != null) {
            if (TextUtils.isEmpty(str)) {
                this.mRepository.clear();
                return true;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("content", str);
                jSONObject.put(KEY_VERSION, this.mRepository.getTemplate().getCacheVersion());
                this.mRepository.write(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
