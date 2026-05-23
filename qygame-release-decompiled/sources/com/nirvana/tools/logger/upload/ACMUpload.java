package com.nirvana.tools.logger.upload;

import com.nirvana.tools.logger.model.ACMRecord;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ACMUpload<T extends ACMRecord> {
    boolean upload(List<T> list);
}
