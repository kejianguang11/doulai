package com.nirvana.tools.logger;

import android.content.Context;
import com.nirvana.tools.logger.cache.ACMMonitorCacheManager;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.nirvana.tools.logger.cache.db.DbException;
import com.nirvana.tools.logger.env.ACMComponent;
import com.nirvana.tools.logger.env.ACMComponentImpl;
import com.nirvana.tools.logger.executor.ReentrantSingleThreadExecutor;
import com.nirvana.tools.logger.model.ACMLimitConfig;
import com.nirvana.tools.logger.model.ACMMonitorRecord;
import com.nirvana.tools.logger.upload.ACMMonitorUploadManager;
import com.nirvana.tools.logger.upload.ACMUpload;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ACMMonitor implements ACMComponent {
    private static final AtomicInteger MONITOR_COUNT = new AtomicInteger();
    public static final int UPLOAD_TYPE_MANUAL = 1;
    public static final int UPLOAD_TYPE_TIMER = 2;
    private ACMMonitorCacheManager mCacheManager;
    private ACMComponentImpl mEnv;
    private ACMMonitorUploadManager mUploadManager;

    public ACMMonitor(Context context, ACMUpload<ACMMonitorRecord> aCMUpload) {
        this(context, aCMUpload, null);
    }

    public ACMMonitor(Context context, ACMUpload<ACMMonitorRecord> aCMUpload, String str) {
        String str2;
        ReentrantSingleThreadExecutor reentrantSingleThreadExecutor = new ReentrantSingleThreadExecutor("ACMMonitor" + MONITOR_COUNT.getAndAdd(1));
        if (str == null) {
            str2 = DBHelpTool.TABLE_NAME_MONITOR;
        } else {
            str2 = str + "_alitx_monitor";
        }
        this.mCacheManager = new ACMMonitorCacheManager(context.getApplicationContext(), reentrantSingleThreadExecutor, str2, str);
        this.mUploadManager = new ACMMonitorUploadManager(context.getApplicationContext(), this.mCacheManager, aCMUpload, reentrantSingleThreadExecutor);
        this.mEnv = new ACMComponentImpl(this.mUploadManager);
    }

    private void cacheMonitor(String str, int i) {
        ACMMonitorRecord aCMMonitorRecord = new ACMMonitorRecord(i);
        aCMMonitorRecord.setContent(str);
        try {
            this.mCacheManager.cacheRecord(aCMMonitorRecord);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void cacheMonitorRecords(List<ACMMonitorRecord> list) {
        try {
            this.mCacheManager.cacheRecords(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void clearLimitConfig() {
        this.mEnv.clearLimitConfig();
    }

    public void deleteRecordsByFlag(int i) {
        this.mUploadManager.deleteRecordsByFlag(i);
    }

    public void monitor(String str, int i) {
        cacheMonitor(str, i);
    }

    public void monitor(Map<String, String> map, int i) {
        cacheMonitor(new JSONObject(map).toString(), i);
    }

    public void monitorRecords(List<ACMMonitorRecord> list) {
        cacheMonitorRecords(list);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setLimitConfig(ACMLimitConfig aCMLimitConfig) {
        this.mEnv.setLimitConfig(aCMLimitConfig);
    }

    public void setMaxUploadRetry(long j) {
        this.mUploadManager.setMaxUploadRetry(j);
    }

    public void setMaxUploadSize(int i) {
        this.mUploadManager.setMaxUploadSize(i);
    }

    public void setRetryCount(int i) {
        this.mUploadManager.setRetryCount(i);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setUploadEnabled(boolean z) {
        this.mEnv.setUploadEnabled(z);
    }

    public void setUploadType(int i) {
        if (i == 1 || i == 2) {
            this.mUploadManager.setUploadType(i);
        }
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void uploadFailed() {
        this.mEnv.uploadFailed();
    }

    public void uploadManual() {
        this.mUploadManager.uploadManual();
    }
}
