package com.nirvana.tools.cache;

/* JADX INFO: loaded from: classes.dex */
public class SharedPreferenceTemplate extends RepositoryTemplate {
    private String fileName;
    private String keyName;

    public SharedPreferenceTemplate(int i, boolean z, String str, String str2) {
        super(i, z);
        this.fileName = str;
        this.keyName = str2;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setKeyName(String str) {
        this.keyName = str;
    }
}
