package com.loc;

import com.loc.bd;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class bo {
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a9 A[Catch: Throwable -> 0x0097, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Throwable -> 0x0097, blocks: (B:50:0x00a9, B:41:0x0093), top: B:66:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int a(bn bnVar) throws Throwable {
        bd bdVarA;
        ArrayList arrayList;
        byte[] bArrA;
        bd bdVar = null;
        try {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            try {
            } catch (Throwable th2) {
                th = th2;
            }
            if (bnVar.f.c()) {
                bnVar.f.a(true);
                bdVarA = bd.a(new File(bnVar.a), bnVar.b);
                try {
                    try {
                        arrayList = new ArrayList();
                        bArrA = a(bdVarA, bnVar, arrayList);
                    } catch (Throwable th3) {
                        th = th3;
                        bdVar = bdVarA;
                        an.b(th, "leg", "uts");
                        if (bdVar != null) {
                        }
                        return -1;
                    }
                    if (bArrA != null && bArrA.length != 0) {
                        am amVar = new am(bArrA, bnVar.c);
                        bg.a();
                        JSONObject jSONObject = new JSONObject(new String(bg.a(amVar).a));
                        if (jSONObject.has("code") && jSONObject.getInt("code") == 1) {
                            if (bnVar.f != null && bArrA != null) {
                                bnVar.f.a(bArrA.length);
                            }
                            if (bnVar.f.b() < Integer.MAX_VALUE) {
                                a(bdVarA, arrayList);
                            } else {
                                try {
                                    bdVarA.d();
                                } catch (Throwable th4) {
                                    an.b(th4, "ofm", "dlo");
                                }
                            }
                            return bArrA.length;
                        }
                        bdVar = bdVarA;
                        an.b(th, "leg", "uts");
                        if (bdVar != null) {
                            bdVar.close();
                        }
                        return -1;
                    }
                    if (bdVarA != null) {
                        try {
                            bdVarA.close();
                        } catch (Throwable th5) {
                            th5.printStackTrace();
                        }
                    }
                    return -1;
                } catch (Throwable th6) {
                    th = th6;
                    if (bdVarA != null) {
                        try {
                            bdVarA.close();
                        } catch (Throwable th7) {
                            th7.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            if (bdVar != null) {
                bdVar.close();
            }
            return -1;
        } catch (Throwable th8) {
            th = th8;
            bdVarA = bdVar;
        }
    }

    private static void a(bd bdVar, List<String> list) {
        if (bdVar != null) {
            try {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    bdVar.c(it.next());
                }
                bdVar.close();
            } catch (Throwable th) {
                an.b(th, "ofm", "dlo");
            }
        }
    }

    public static void a(String str, byte[] bArr, bn bnVar) throws Throwable {
        bd bdVarA;
        OutputStream outputStream = null;
        try {
            if (a(bnVar.a, str)) {
                return;
            }
            File file = new File(bnVar.a);
            if (!file.exists()) {
                file.mkdirs();
            }
            bdVarA = bd.a(file, bnVar.b);
            try {
                bdVarA.a(bnVar.d);
                byte[] bArrB = bnVar.e.b(bArr);
                bd.a aVarB = bdVarA.b(str);
                OutputStream outputStreamA = aVarB.a();
                try {
                    outputStreamA.write(bArrB);
                    aVarB.b();
                    bdVarA.c();
                    if (outputStreamA != null) {
                        try {
                            outputStreamA.close();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                    if (bdVarA != null) {
                        try {
                            bdVarA.close();
                            return;
                        } catch (Throwable th2) {
                            th2.printStackTrace();
                            return;
                        }
                    }
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = outputStreamA;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
            bdVarA = null;
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (Throwable th6) {
                th6.printStackTrace();
            }
        }
        if (bdVarA == null) {
            throw th;
        }
        try {
            bdVarA.close();
            throw th;
        } catch (Throwable th7) {
            th7.printStackTrace();
            throw th;
        }
    }

    private static boolean a(String str, String str2) {
        try {
            return new File(str, str2 + ".0").exists();
        } catch (Throwable th) {
            an.b(th, "leg", "fet");
            return false;
        }
    }

    private static byte[] a(bd bdVar, bn bnVar, List<String> list) {
        try {
            File fileB = bdVar.b();
            if (fileB != null && fileB.exists()) {
                int length = 0;
                for (String str : fileB.list()) {
                    if (str.contains(".0")) {
                        String str2 = str.split("\\.")[0];
                        byte[] bArrA = bt.a(bdVar, str2);
                        length += bArrA.length;
                        list.add(str2);
                        if (length > bnVar.f.b()) {
                            break;
                        }
                        bnVar.g.b(bArrA);
                    }
                }
                if (length <= 0) {
                    return null;
                }
                return bnVar.g.a();
            }
        } catch (Throwable th) {
            an.b(th, "leg", "gCo");
        }
        return new byte[0];
    }
}
