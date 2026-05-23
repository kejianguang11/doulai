package com.nirvana.tools.logger.upload.inteceptor;

/* JADX INFO: loaded from: classes.dex */
public class EnableInterceptor implements BaseInterceptor {
    private boolean mEnabled;

    @Override // com.nirvana.tools.logger.upload.inteceptor.BaseInterceptor
    public boolean isAllowUploading() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }
}
