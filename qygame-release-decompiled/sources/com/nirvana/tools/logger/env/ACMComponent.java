package com.nirvana.tools.logger.env;

import com.nirvana.tools.logger.model.ACMLimitConfig;

/* JADX INFO: loaded from: classes.dex */
public interface ACMComponent {
    void clearLimitConfig();

    void setLimitConfig(ACMLimitConfig aCMLimitConfig);

    void setUploadEnabled(boolean z);

    void uploadFailed();
}
