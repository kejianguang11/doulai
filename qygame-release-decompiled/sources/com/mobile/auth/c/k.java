package com.mobile.auth.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class k {
    private static int a(int i) {
        int i2 = -101;
        if (i != -101) {
            i2 = -1;
            if (i != -1) {
                switch (i) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                        return 2;
                    case 13:
                    case 18:
                    case 19:
                        return 3;
                    default:
                        return i;
                }
            }
        }
        return i2;
    }

    public static NetworkInfo a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
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

    public static void a(Context context, long j) {
        try {
            try {
                d.a(context, "key_s_p_dm_time", j);
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

    public static void a(Context context, String str) {
        try {
            try {
                d.a(context, "key_s_p_dm", str);
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

    public static boolean b(Context context) {
        try {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA != null) {
                return networkInfoA.isAvailable();
            }
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean c(Context context) {
        try {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA != null) {
                if (networkInfoA.getType() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean d(Context context) {
        if (context == null) {
            return true;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Throwable th) {
            try {
                com.mobile.auth.a.a.a("NetUtil", "isMobileEnable error ", th);
                return true;
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return false;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return false;
                }
            }
        }
    }

    public static String e(Context context) {
        int iK;
        try {
            iK = k(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        if (iK == -101) {
            return "WIFI";
        }
        switch (iK) {
            case -1:
            case 0:
                return "null";
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            default:
                return Integer.toString(iK);
        }
        ExceptionProcessor.processException(th);
        return null;
    }

    public static String f(Context context) {
        try {
            String strE = e(context);
            if (strE != null && strE.equals("WIFI")) {
                if (d(context)) {
                    return "BOTH";
                }
            }
            return strE;
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

    public static String g(Context context) {
        try {
            String strF = f(context);
            if (!TextUtils.isEmpty(strF) && !strF.equals("null")) {
                return strF.equals("2G") ? "10" : strF.equals("3G") ? "11" : strF.equals("4G") ? "12" : strF.equals("WIFI") ? "13" : strF.equals("BOTH") ? "14" : "15";
            }
            return "15";
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

    public static String h(Context context) {
        try {
            if (System.currentTimeMillis() - i(context) > 172800000) {
                return "1";
            }
            try {
                return d.b(context, "key_s_p_dm", "1");
            } catch (Exception e) {
                e.printStackTrace();
                return "1";
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
    }

    public static long i(Context context) {
        Long lValueOf;
        try {
            try {
                lValueOf = Long.valueOf(d.b(context, "key_s_p_dm_time", 0L));
            } catch (Exception e) {
                e.printStackTrace();
                lValueOf = null;
            }
            return lValueOf.longValue();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public static String j(Context context) {
        try {
            String strH = h(context);
            return (TextUtils.isEmpty(strH) || strH.equals("1")) ? "https://id6.me/auth/preauth.do" : strH.equals(com.igexin.push.config.c.H) ? "https://card.e.189.cn/auth/preauth.do" : "https://id6.me/auth/preauth.do";
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

    private static int k(Context context) {
        int subtype = 0;
        try {
            try {
                NetworkInfo networkInfoA = a(context);
                if (networkInfoA != null && networkInfoA.isAvailable() && networkInfoA.isConnected()) {
                    int type = networkInfoA.getType();
                    if (type == 1) {
                        subtype = -101;
                    } else if (type == 0) {
                        subtype = networkInfoA.getSubtype();
                    }
                } else {
                    subtype = -1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return a(subtype);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }
}
