package com.loc;

import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class fk {
    private static long a(long j) {
        return j - b(j);
    }

    private static long a(long j, long j2) {
        long jB = b(j2) + a(j);
        long jAbs = Math.abs(jB - j2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(jB));
        int i = calendar.get(11);
        if (i == 23 && jAbs >= 82800000) {
            jB -= com.igexin.push.core.b.J;
        }
        return (i != 0 || jAbs < 82800000) ? jB : jB + com.igexin.push.core.b.J;
    }

    public static long a(long j, long j2, int i) {
        if (i > 0) {
            try {
                if (Math.abs(j - j2) > ((long) i) * 31536000000L) {
                    return a(j, j2);
                }
            } catch (Throwable unused) {
            }
        }
        return j;
    }

    private static long b(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(j));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }
}
