package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.a.a.f;
import java.util.zip.Adler32;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static a a;
    static final Object b = new Object();

    static long a(a aVar) {
        if (aVar == null) {
            return 0L;
        }
        String str = String.format("%s%s%s%s%s", aVar.getUtdid(), aVar.getDeviceId(), Long.valueOf(aVar.a()), aVar.getImsi(), aVar.getImei());
        if (f.isEmpty(str)) {
            return 0L;
        }
        Adler32 adler32 = new Adler32();
        adler32.reset();
        adler32.update(str.getBytes());
        return adler32.getValue();
    }

    private static a a(Context context) {
        if (context == null) {
            return null;
        }
        synchronized (b) {
            String value = c.a(context).getValue();
            if (f.isEmpty(value)) {
                return null;
            }
            if (value.endsWith("\n")) {
                value = value.substring(0, value.length() - 1);
            }
            a aVar = new a();
            long jCurrentTimeMillis = System.currentTimeMillis();
            String imei = com.ta.utdid2.a.a.d.getImei(context);
            String imsi = com.ta.utdid2.a.a.d.getImsi(context);
            aVar.c(imei);
            aVar.a(imei);
            aVar.b(jCurrentTimeMillis);
            aVar.b(imsi);
            aVar.d(value);
            aVar.a(a(aVar));
            return aVar;
        }
    }

    public static synchronized a b(Context context) {
        if (a != null) {
            return a;
        }
        if (context == null) {
            return null;
        }
        a aVarA = a(context);
        a = aVarA;
        return aVarA;
    }
}
