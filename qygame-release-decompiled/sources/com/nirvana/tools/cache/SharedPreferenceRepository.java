package com.nirvana.tools.cache;

import android.content.Context;
import com.nirvana.tools.core.UTSharedPreferencesHelper;

/* JADX INFO: loaded from: classes.dex */
public class SharedPreferenceRepository extends CacheRepository<SharedPreferenceTemplate> {
    private Context mContext;

    public SharedPreferenceRepository(SharedPreferenceTemplate sharedPreferenceTemplate, Context context) {
        super(sharedPreferenceTemplate);
        this.mContext = context;
    }

    @Override // com.nirvana.tools.cache.CacheRepository
    void clear() {
        UTSharedPreferencesHelper.clearInfo(this.mContext, getTemplate().getFileName(), getTemplate().getKeyName());
    }

    @Override // com.nirvana.tools.cache.CacheRepository
    String read() {
        return (String) UTSharedPreferencesHelper.get(this.mContext, getTemplate().getFileName(), getTemplate().getKeyName(), "");
    }

    @Override // com.nirvana.tools.cache.CacheRepository
    void write(String str) {
        UTSharedPreferencesHelper.put(this.mContext, getTemplate().getFileName(), getTemplate().getKeyName(), str);
    }
}
