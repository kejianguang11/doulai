package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.c.f;
import com.mobile.auth.c.r;
import com.mobile.auth.c.s;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static final String a = "d";
    private static final byte[] b = {15, 31, 94, 10, 90, 15, 91, 24, 10, 30, 88, 7, 89, 10, 95, 30};

    static /* synthetic */ String a(Context context, Queue queue) {
        try {
            return b(context, (Queue<String>) queue);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ Queue a(Context context, List list, int i) {
        try {
            return c(context, list, i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ void a(Context context) {
        try {
            c(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void a(Context context, int i) {
        try {
            try {
                com.mobile.auth.c.d.a(context, "key_c_l_l_v", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(Context context, String str) {
        byte b2;
        try {
            int iHashCode = str.hashCode();
            int i = -1;
            if (iHashCode != 64897) {
                if (iHashCode != 78159) {
                    b2 = (iHashCode == 66247144 && str.equals("ERROR")) ? (byte) 1 : (byte) -1;
                } else if (str.equals("OFF")) {
                    b2 = 2;
                }
            } else if (str.equals("ALL")) {
                b2 = 0;
            }
            switch (b2) {
                case 0:
                default:
                    i = 0;
                    break;
                case 1:
                    break;
                case 2:
                    i = -2;
                    break;
            }
            a(context, i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Context context, List<String> list) {
        try {
            int iB = b(context);
            if (iB == -2) {
                return;
            }
            b(context, list, iB);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(Context context, Queue queue, int i) {
        try {
            b(context, (Queue<String>) queue, i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static int b(Context context) {
        try {
            return com.mobile.auth.c.d.b(context, "key_c_l_l_v", 0);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return 0;
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return -1;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return -1;
                }
            }
        }
    }

    private static String b(Context context, String str) {
        try {
            return a.a(context, "https://api-e189.21cn.com/gw/client/accountMsg.do", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String b(Context context, Queue<String> queue) {
        String strA;
        try {
            JSONArray jSONArray = new JSONArray();
            String string = jSONArray.toString();
            if (!queue.isEmpty()) {
                Iterator<String> it = queue.iterator();
                while (it.hasNext()) {
                    try {
                        jSONArray.put(new JSONObject(it.next()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (jSONArray.length() <= 0) {
                return "";
            }
            String string2 = jSONArray.toString();
            if (!TextUtils.isEmpty(string2)) {
                try {
                    strA = com.mobile.auth.c.c.a(f.a(string2, s.a(b)).getBytes());
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    string = URLEncoder.encode(strA, com.alipay.sdk.sys.a.m);
                } catch (Exception e3) {
                    e = e3;
                    string = strA;
                    e.printStackTrace();
                }
            }
            return b(context, string);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static void b(final Context context, final List<String> list, final int i) {
        try {
            r.a().a(new Runnable() { // from class: com.mobile.auth.b.d.1
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    try {
                        Queue queueA = d.a(context, list, i);
                        if (queueA.isEmpty()) {
                            return;
                        }
                        String strA = d.a(context, queueA);
                        JSONObject jSONObject = null;
                        int i2 = -1;
                        try {
                            if (!TextUtils.isEmpty(strA)) {
                                JSONObject jSONObject2 = new JSONObject(strA);
                                try {
                                    i2 = jSONObject2.getInt("code");
                                    jSONObject = jSONObject2;
                                } catch (Exception e) {
                                    e = e;
                                    jSONObject = jSONObject2;
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                        }
                        if (jSONObject == null || i2 != 0) {
                            d.a(context, queueA, i);
                        } else {
                            d.a(context);
                            queueA.clear();
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
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void b(Context context, Queue<String> queue, int i) {
        JSONObject jSONObject;
        try {
            String strA = "";
            JSONArray jSONArray = new JSONArray();
            if (queue != null && !queue.isEmpty()) {
                Iterator<String> it = queue.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    try {
                        jSONObject = new JSONObject(it.next());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (i != -1 || jSONObject.getInt("rt") != 0) {
                        jSONArray.put(jSONObject);
                        i2++;
                        if (i2 > 10) {
                            break;
                        }
                    }
                }
            }
            if (jSONArray.length() > 0) {
                try {
                    strA = f.a(jSONArray.toString(), s.a(b));
                } catch (Exception e2) {
                    strA = null;
                    e2.printStackTrace();
                }
            }
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            c.a(context, strA);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static synchronized Queue<String> c(Context context, List<String> list, int i) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        try {
            concurrentLinkedQueue = new ConcurrentLinkedQueue();
            String strA = c.a(context);
            if (!TextUtils.isEmpty(strA)) {
                try {
                    JSONArray jSONArray = new JSONArray(f.a(strA, s.a(b)));
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length && i2 <= 10; i2++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        if (jSONObject != null) {
                            concurrentLinkedQueue.add(jSONObject.toString());
                        }
                    }
                    c.a(context, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (i == -1) {
                for (String str : list) {
                    try {
                        if (new JSONObject(str).getInt("rt") != 0) {
                            concurrentLinkedQueue.add(str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } else if (i == 0) {
                concurrentLinkedQueue.addAll(list);
            }
            while (concurrentLinkedQueue.size() > 10) {
                concurrentLinkedQueue.poll();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return concurrentLinkedQueue;
    }

    private static void c(Context context) {
        try {
            c.a(context, "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
