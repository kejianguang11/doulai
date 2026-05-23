package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.j;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static final String a = "e";
    private static int b;
    private static Map<String, b> c = new HashMap();
    private static List<String> d = new ArrayList();

    public static synchronized b a(String str) {
        b bVar;
        try {
            bVar = c.containsKey(str) ? c.get(str) : null;
            if (bVar == null) {
                bVar = new b(str);
                c.put(str, bVar);
            }
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return new b(str);
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
        return bVar;
    }

    static /* synthetic */ void a(Context context) {
        try {
            b(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(final Context context, String str) {
        try {
            synchronized (e.class) {
                if (c.containsKey(str)) {
                    d.add(c.get(str).toString());
                    c.remove(str);
                }
                if (b != 1 && !d.isEmpty()) {
                    b = 1;
                    new Timer().schedule(new TimerTask() { // from class: com.mobile.auth.b.e.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            try {
                                e.a(context);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    }, 8000L);
                }
            }
        } catch (Throwable th) {
            try {
                th.printStackTrace();
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                }
            }
        }
    }

    public static void a(String str, String str2, String str3) {
        int i;
        int i2 = -1;
        String strOptString = "";
        try {
            try {
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return;
                }
            }
        } catch (Exception e) {
            e = e;
        }
        if (TextUtils.isEmpty(str2)) {
            i = i2;
        } else {
            JSONObject jSONObject = new JSONObject(str2);
            i = jSONObject.getInt(j.c);
            try {
                strOptString = jSONObject.optString("msg");
            } catch (Exception e2) {
                i2 = i;
                e = e2;
                e.printStackTrace();
                i = i2;
            }
        }
        if (i == 0) {
            a(str).a(i).f(strOptString);
        } else {
            a(str).a(i).f(strOptString).e(str3);
        }
    }

    private static void b(Context context) {
        if (context == null) {
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            synchronized (e.class) {
                arrayList.addAll(d);
                b = 0;
                d.clear();
            }
            if (arrayList.isEmpty()) {
                return;
            }
            d.a(context, arrayList);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                }
            }
        }
    }

    public static void b(Context context, String str) {
        try {
            d.a(context, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
