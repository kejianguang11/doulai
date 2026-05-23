package com.mobile.auth.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static SharedPreferences a(Context context) {
        try {
            return context.getSharedPreferences(b(context), 0);
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

    public static void a(Context context, String str, int i) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        a(context).edit().putInt(str, i).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public static void a(Context context, String str, String str2) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        a(context).edit().putString(str, str2).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public static boolean a(Context context, String str, long j) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        return a(context).edit().putLong(str, j).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
        return false;
    }

    public static int b(Context context, String str, int i) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        return a(context).getInt(str, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
        return i;
    }

    public static long b(Context context, String str, long j) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        return a(context).getLong(str, j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
        return j;
    }

    /* JADX WARN: Unreachable blocks removed: 7, instructions: 7 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:8:0x0009
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private static java.lang.String b(android.content.Context r1) {
        /*
            java.lang.String r1 = "ct_account_api_sdk"
            return r1
        L3:
            r1 = move-exception
            r0 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r1)     // Catch: java.lang.Throwable -> L9
            return r0
        L9:
            r1 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.c.d.b(android.content.Context):java.lang.String");
    }

    public static String b(Context context, String str, String str2) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        return a(context).getString(str, str2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        return str2;
    }
}
