package com.loc;

import android.content.Context;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class bt {
    public static bn a(WeakReference<bn> weakReference) {
        if (weakReference == null || weakReference.get() == null) {
            weakReference = new WeakReference<>(new bn());
        }
        return weakReference.get();
    }

    public static String a() {
        return x.a(System.currentTimeMillis());
    }

    public static String a(Context context, w wVar) {
        StringBuilder sb = new StringBuilder();
        try {
            String strF = o.f(context);
            sb.append("\"sim\":\"");
            sb.append(strF);
            sb.append("\",\"sdkversion\":\"");
            sb.append(wVar.c());
            sb.append("\",\"product\":\"");
            sb.append(wVar.a());
            sb.append("\",\"ed\":\"");
            sb.append(wVar.d());
            sb.append("\",\"nt\":\"");
            sb.append(o.d(context));
            sb.append("\",\"np\":\"");
            sb.append(o.b(context));
            sb.append("\",\"mnc\":\"");
            sb.append(o.c(context));
            sb.append("\",\"ant\":\"");
            sb.append(o.e(context));
            sb.append("\"");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return sb.toString();
    }

    public static String a(String str, String str2, int i, String str3, String str4) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(",\"timestamp\":\"");
        stringBuffer.append(str2);
        stringBuffer.append("\",\"et\":\"");
        stringBuffer.append(i);
        stringBuffer.append("\",\"classname\":\"");
        stringBuffer.append(str3);
        stringBuffer.append("\",");
        stringBuffer.append("\"detail\":\"");
        stringBuffer.append(str4);
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }

    public static void a(Context context, bn bnVar, String str, int i, int i2, String str2) {
        bnVar.a = al.c(context, str);
        bnVar.d = i;
        bnVar.b = i2;
        bnVar.c = str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.loc.bd] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.loc.bd$b] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.loc.bd$b] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.loc.bd$b] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v9 */
    static byte[] a(bd bdVar, String str) throws Throwable {
        InputStream inputStreamA;
        Throwable th;
        byte[] bArr = new byte[0];
        try {
            try {
                bdVar = bdVar.a(str);
                if (bdVar == 0) {
                    if (bdVar != 0) {
                        try {
                            bdVar.close();
                        } catch (Throwable th2) {
                            th2.printStackTrace();
                        }
                    }
                    return bArr;
                }
                try {
                    inputStreamA = bdVar.a();
                    if (inputStreamA == null) {
                        if (inputStreamA != null) {
                            try {
                                inputStreamA.close();
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                            }
                        }
                        if (bdVar != 0) {
                            try {
                                bdVar.close();
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                        }
                        return bArr;
                    }
                    try {
                        byte[] bArr2 = new byte[inputStreamA.available()];
                        try {
                            inputStreamA.read(bArr2);
                            if (inputStreamA != null) {
                                try {
                                    inputStreamA.close();
                                } catch (Throwable th5) {
                                    th5.printStackTrace();
                                }
                            }
                            if (bdVar != 0) {
                                try {
                                    bdVar.close();
                                } catch (Throwable th6) {
                                    th6.printStackTrace();
                                }
                            }
                            return bArr2;
                        } catch (Throwable th7) {
                            th = th7;
                            bArr = bArr2;
                            an.b(th, "sui", "rdS");
                            if (inputStreamA != null) {
                                try {
                                    inputStreamA.close();
                                } catch (Throwable th8) {
                                    th8.printStackTrace();
                                }
                            }
                            if (bdVar != 0) {
                                try {
                                    bdVar.close();
                                } catch (Throwable th9) {
                                    th9.printStackTrace();
                                }
                            }
                            return bArr;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    inputStreamA = null;
                }
            } catch (Throwable th12) {
                th = th12;
            }
        } catch (Throwable th13) {
            th = th13;
            bdVar = 0;
            str = 0;
        }
    }
}
