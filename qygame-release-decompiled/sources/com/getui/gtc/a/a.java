package com.getui.gtc.a;

import android.content.Intent;
import android.text.TextUtils;
import com.getui.gtc.base.util.ScheduleQueue;
import com.getui.gtc.dim.Caller;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final AtomicBoolean a = new AtomicBoolean(false);

    public static String a(String str) {
        return TextUtils.isEmpty(str) ? "" : str.contains("|") ? str.replace("|", "$") : str;
    }

    public static void a() {
        if (a.getAndSet(true)) {
            return;
        }
        ScheduleQueue.getInstance().addSchedule(new h(null), 0L, 300000L);
        ScheduleQueue.getInstance().addSchedule(new i(), 0L, 300000L);
        b[] bVarArr = {new d(), new e(), new f(), new g()};
        for (int i = 0; i < 4; i++) {
            ScheduleQueue.getInstance().addSchedule(bVarArr[i], com.igexin.push.config.c.i);
        }
    }

    public static void a(int i) {
        if (i == 256) {
            c.a();
        }
    }

    public static void a(Intent intent) {
        ScheduleQueue.getInstance().addSchedule(new h(intent));
    }

    public static void a(Caller caller, String str, String str2) {
        ScheduleQueue.getInstance().addSchedule(new i(caller, str, str2));
    }
}
