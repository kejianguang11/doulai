package com.nirvana.tools.cache;

import com.nirvana.tools.cache.RepositoryTemplate;

/* JADX INFO: loaded from: classes.dex */
public abstract class CacheRepository<T extends RepositoryTemplate> {
    private T mTemplate;

    public CacheRepository(T t) {
        this.mTemplate = t;
    }

    abstract void clear();

    public T getTemplate() {
        return this.mTemplate;
    }

    abstract String read();

    abstract void write(String str);
}
