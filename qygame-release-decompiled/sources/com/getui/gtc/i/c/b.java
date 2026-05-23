package com.getui.gtc.i.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.e.c;
import com.igexin.push.f.b.d;
import com.mobile.auth.gatewayauth.Constant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final AtomicBoolean a = new AtomicBoolean(false);

    private static File a() {
        return new File(CommonUtil.getExternalFilesDir(GtcProvider.context()), GtcProvider.context().getPackageName() + "-online.properties");
    }

    static /* synthetic */ File a(File file) {
        return b(file);
    }

    static /* synthetic */ String a(Context context) {
        return b(context);
    }

    private static JSONObject a(String str) {
        JSONObject jSONObjectA;
        int i;
        boolean z;
        try {
            a.a("update file log config:".concat(String.valueOf(str)));
            jSONObjectA = c.a.a.a.a();
            i = 0;
            z = jSONObjectA != null && jSONObjectA.length() > 0;
        } catch (Throwable th) {
            a.c(th);
        }
        if (TextUtils.isEmpty(str)) {
            if (z) {
                a().delete();
                c.a.a.a.a((JSONObject) null);
                a.a("file log clear old config and properties");
            }
            return null;
        }
        if (z && jSONObjectA.optString("dycConfig").equals(str)) {
            a.a("file log same config");
            return jSONObjectA;
        }
        c.a.a.a.a((JSONObject) null);
        a().delete();
        a.a("file log clear old config and properties");
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length < 8) {
            throw new IllegalStateException("file log dyc error");
        }
        JSONObject jSONObject = new JSONObject();
        String[] strArrSplit2 = strArrSplit[0].split(com.igexin.push.core.b.an);
        String str2 = com.getui.gtc.c.b.d;
        int length = strArrSplit2.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            String str3 = strArrSplit2[i2];
            if (!TextUtils.isEmpty(str3) && str3.equals(str2)) {
                jSONObject.put("gtcid", str2);
                break;
            }
            i2++;
        }
        String[] strArrSplit3 = strArrSplit[1].split(com.igexin.push.core.b.an);
        String strB = b(GtcProvider.context());
        int length2 = strArrSplit3.length;
        while (true) {
            if (i >= length2) {
                break;
            }
            String str4 = strArrSplit3[i];
            if (!TextUtils.isEmpty(str4) && str4.equals(strB)) {
                jSONObject.put("cid", strB);
                break;
            }
            i++;
        }
        if (!jSONObject.has("gtcid") && !jSONObject.has("cid")) {
            a.a("file upload not match cur user");
            return null;
        }
        long time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(strArrSplit[2]).getTime();
        long j = Long.parseLong(strArrSplit[3]);
        if (j <= 0) {
            a.a("file upload interval=" + j + " not valid");
        }
        jSONObject.put("allowMobile", "1".equals(strArrSplit[6]));
        jSONObject.put(Constant.START_TIME, time);
        jSONObject.put("interval", j);
        jSONObject.put("suffixes", strArrSplit[4]);
        jSONObject.put("enableKeys", strArrSplit[5]);
        jSONObject.put(Constant.PROTOCOL_WEB_VIEW_URL, strArrSplit[7]);
        if (a(jSONObject)) {
            jSONObject.put("lastModified", a().lastModified());
        }
        jSONObject.put("dycConfig", str);
        c.a.a.a.a(jSONObject);
        a.a("save file log dyc to db: " + jSONObject.toString());
        return jSONObject;
    }

    public static void a(Map<String, String> map) {
        try {
            if (a.getAndSet(true)) {
                return;
            }
            final JSONObject jSONObjectA = a(map != null ? map.get("sdk.gtc.fileLog.upload") : null);
            if (jSONObjectA != null && jSONObjectA.length() != 0) {
                final long jOptLong = jSONObjectA.optLong(Constant.START_TIME);
                String strOptString = jSONObjectA.optString("gtcid");
                String strOptString2 = jSONObjectA.optString("cid");
                final long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis >= jOptLong && jCurrentTimeMillis <= d.b + jOptLong) {
                    if ((!TextUtils.isEmpty(strOptString) && strOptString.equals(com.getui.gtc.c.b.d)) || (!TextUtils.isEmpty(strOptString2) && strOptString2.equals(b(GtcProvider.context())))) {
                        if (CommonUtil.isAppDebugEnable()) {
                            a.b("file log upload is debug, disallow");
                            return;
                        }
                        boolean zOptBoolean = jSONObjectA.optBoolean("allowMobile");
                        if (!a(zOptBoolean)) {
                            a.b("file log upload network is not allowed, allowMobile:".concat(String.valueOf(zOptBoolean)));
                            return;
                        }
                        File fileA = a();
                        long jOptLong2 = jSONObjectA.optLong("lastModified");
                        if (jOptLong2 == 0 || jOptLong2 != fileA.lastModified()) {
                            if (!a(jSONObjectA)) {
                                return;
                            }
                            jSONObjectA.put("lastModified", fileA.lastModified());
                            c.a.a.a.a(jSONObjectA);
                        }
                        if (jCurrentTimeMillis - jSONObjectA.optLong("reportTime") < jSONObjectA.optLong("interval") * 1000) {
                            a.c("file log report time not expired");
                            return;
                        }
                        Thread thread = new Thread(new Runnable() { // from class: com.getui.gtc.i.c.b.1
                            /*  JADX ERROR: NullPointerException in pass: BlockProcessor
                                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.BlockNode.getPredecessors()" because "to" is null
                                	at jadx.core.dex.visitors.blocks.BlockSplitter.connect(BlockSplitter.java:159)
                                	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.connectSplittersAndHandlers(BlockExceptionHandler.java:480)
                                	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.wrapBlocksWithTryCatch(BlockExceptionHandler.java:381)
                                	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.connectExcHandlers(BlockExceptionHandler.java:90)
                                	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.process(BlockExceptionHandler.java:61)
                                	at jadx.core.dex.visitors.blocks.BlockProcessor.independentBlockTreeMod(BlockProcessor.java:380)
                                	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:57)
                                	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:50)
                                */
                            @Override // java.lang.Runnable
                            public final void run() {
                                /*
                                    Method dump skipped, instruction units count: 890
                                    To view this dump add '--comments-level debug' option
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.getui.gtc.i.c.b.AnonymousClass1.run():void");
                            }
                        });
                        thread.setName("GTC_fileLogUploadThread");
                        thread.start();
                        return;
                    }
                    a().delete();
                    a.a("file log upload gtcid or cid changed");
                    return;
                }
                a.a("current time is not in file log upload time range");
                a().delete();
                return;
            }
            a.a("file log upload no dyc config in db");
        } catch (Throwable th) {
            a.c(th);
        }
    }

    private static boolean a(JSONObject jSONObject) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                long jOptLong = jSONObject.optLong(Constant.START_TIME);
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis >= jOptLong && jCurrentTimeMillis <= jOptLong + d.b) {
                    String[] strArrSplit = jSONObject.optString("enableKeys").split(com.igexin.push.core.b.an);
                    StringBuilder sb = new StringBuilder();
                    for (String str : strArrSplit) {
                        if (!TextUtils.isEmpty(str)) {
                            sb.append(str);
                            sb.append('=');
                            sb.append("true\n");
                        }
                    }
                    if (sb.length() == 0) {
                        return false;
                    }
                    FileOutputStream fileOutputStream2 = new FileOutputStream(a(), false);
                    try {
                        fileOutputStream2.write(sb.toString().getBytes());
                        fileOutputStream2.flush();
                        a.a("file log write enableKeys success.");
                        try {
                            fileOutputStream2.close();
                            return true;
                        } catch (Throwable th) {
                            a.c(th);
                            return true;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        a.c(th);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable th3) {
                                a.c(th3);
                            }
                        }
                        return false;
                    }
                }
                a.a("current time is not in file log upload time range");
                return false;
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    private static boolean a(boolean z) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) GtcProvider.context().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return false;
            }
            if (!z) {
                if (activeNetworkInfo.getType() != 1) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0088 A[Catch: Throwable -> 0x0084, TryCatch #6 {Throwable -> 0x0084, blocks: (B:49:0x0080, B:53:0x0088, B:55:0x008d), top: B:63:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x008d A[Catch: Throwable -> 0x0084, TRY_LEAVE, TryCatch #6 {Throwable -> 0x0084, blocks: (B:49:0x0080, B:53:0x0088, B:55:0x008d), top: B:63:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File b(File file) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        GZIPOutputStream gZIPOutputStream;
        GZIPOutputStream gZIPOutputStream2 = null;
        if (!file.exists()) {
            return null;
        }
        File file2 = new File(file + ".zip");
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
                gZIPOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            fileInputStream = null;
        }
        try {
            gZIPOutputStream = new GZIPOutputStream(fileOutputStream);
            try {
                try {
                    byte[] bArr = new byte[2048];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i != -1) {
                            gZIPOutputStream.write(bArr, 0, i);
                        } else {
                            try {
                                break;
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                            }
                        }
                    }
                    gZIPOutputStream.close();
                    fileOutputStream.close();
                    fileInputStream.close();
                    return file2;
                } catch (Throwable th4) {
                    th = th4;
                    th.printStackTrace();
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Throwable th5) {
                            th5.printStackTrace();
                            return null;
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return null;
                }
            } catch (Throwable th6) {
                th = th6;
                gZIPOutputStream2 = gZIPOutputStream;
                if (gZIPOutputStream2 != null) {
                    try {
                        gZIPOutputStream2.close();
                    } catch (Throwable th7) {
                        th7.printStackTrace();
                        throw th;
                    }
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th8) {
            th = th8;
            if (gZIPOutputStream2 != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileInputStream != null) {
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(Context context) {
        String str = "";
        try {
            Class<?> cls = Class.forName("com.igexin.sdk.PushManager");
            Object objInvoke = cls.getDeclaredMethod("getClientid", Context.class).invoke(cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]), context);
            if (objInvoke != null) {
                str = (String) objInvoke;
            }
        } catch (Throwable th) {
            a.a("reflect cid", th);
        }
        a.a("reflect cid:".concat(String.valueOf(str)));
        return str;
    }
}
