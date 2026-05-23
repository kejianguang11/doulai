package com.nirvana.tools.logger.model;

/* JADX INFO: loaded from: classes.dex */
public class ACMMonitorRecord extends ACMRecord {
    public static final int MONITOR_URGENCY_DELAYED = 2;
    public static final int MONITOR_URGENCY_REAL_TIME = 1;
    private int urgency;

    public ACMMonitorRecord() {
    }

    public ACMMonitorRecord(int i) {
        if (i != 1 && i != 2) {
            i = 2;
        }
        this.urgency = i;
    }

    public int getUrgency() {
        return this.urgency;
    }

    public void setUrgency(int i) {
        this.urgency = i;
    }
}
