package com.nirvana.tools.cache;

/* JADX INFO: loaded from: classes.dex */
public class RepositoryTemplate {
    private int cacheVersion;
    private boolean needEncrypt;

    public RepositoryTemplate(int i, boolean z) {
        this.cacheVersion = i;
        this.needEncrypt = z;
    }

    public int getCacheVersion() {
        return this.cacheVersion;
    }

    public boolean isNeedEncrypt() {
        return this.needEncrypt;
    }

    public void setCacheVersion(int i) {
        this.cacheVersion = i;
    }

    public void setNeedEncrypt(boolean z) {
        this.needEncrypt = z;
    }
}
