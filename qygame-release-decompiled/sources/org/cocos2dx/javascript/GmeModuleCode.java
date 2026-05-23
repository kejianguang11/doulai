package org.cocos2dx.javascript;

/* JADX INFO: loaded from: classes.dex */
public enum GmeModuleCode {
    UpLoadSuccess(1),
    UpLoadFailed(2),
    DownLoadSuccess(3),
    DownLoadFailed(4),
    PlayRecordFinish(5),
    PlayRecordFailed(6),
    ApplyKeyFailed(7),
    StartRecordSuccess(8),
    StartRecordFailed(9);

    public final int code;

    GmeModuleCode(int i) {
        this.code = i;
    }
}
