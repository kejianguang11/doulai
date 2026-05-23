package com.nirvana.tools.cache;

import android.content.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class CacheManager {
    private static volatile CacheManager sInstance;
    private Context mContext;
    private Map<String, CacheHandler> mHandlers = new ConcurrentHashMap();

    public CacheManager(Context context) {
        this.mContext = context;
    }

    public static CacheManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CacheManager.class) {
                if (sInstance == null) {
                    sInstance = new CacheManager(context);
                }
            }
        }
        return sInstance;
    }

    public CacheHandler getCacheHandler(String str) {
        return this.mHandlers.get(str);
    }

    public CacheHandler registerCacheHandler(String str, CacheRepository cacheRepository) {
        if (!this.mHandlers.containsKey(str)) {
            CacheHandler cacheHandler = new CacheHandler(cacheRepository);
            this.mHandlers.put(str, cacheHandler);
            return cacheHandler;
        }
        throw new IllegalStateException("Handler key [" + str + "] has been contained!");
    }

    public <T extends RepositoryTemplate> CacheHandler registerCacheHandler(String str, T t) {
        if (t instanceof SharedPreferenceTemplate) {
            return registerCacheHandler(str, new SharedPreferenceRepository((SharedPreferenceTemplate) t, this.mContext));
        }
        throw new IllegalArgumentException("Unsupported template!");
    }

    public void unRegisterCacheHandler(String str) {
        this.mHandlers.remove(str);
    }
}
