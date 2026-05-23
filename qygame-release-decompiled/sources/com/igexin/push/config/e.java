package com.igexin.push.config;

import com.igexin.push.core.b.n;
import com.igexin.push.g.k;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static String a = "FileConfig";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0052 A[Catch: all -> 0x00ce, Exception -> 0x00e4, TryCatch #16 {Exception -> 0x00e4, all -> 0x00ce, blocks: (B:23:0x0045, B:25:0x0052, B:28:0x007e, B:29:0x0085, B:30:0x0086), top: B:99:0x0045 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f A[Catch: all -> 0x00c1, Exception -> 0x00c7, TryCatch #18 {Exception -> 0x00c7, all -> 0x00c1, blocks: (B:32:0x0099, B:34:0x009f, B:36:0x00a7), top: B:95:0x0099 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00b0 A[EDGE_INSN: B:83:0x00b0->B:38:0x00b0 BREAK  A[LOOP:0: B:95:0x0099->B:104:0x0099], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.content.res.AssetManager] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a() {
        ?? fileInputStream;
        BufferedReader bufferedReader;
        String line;
        ?? Open = com.igexin.push.core.e.g + ".properties";
        BufferedReader bufferedReader2 = null;
        try {
            try {
                Open = com.igexin.push.core.e.l.getResources().getAssets().open(Open);
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
            try {
                a((InputStream) Open);
            } catch (Exception unused) {
                if (Open != 0) {
                    Open.close();
                    Open = Open;
                }
                if (!new File(k.a).exists()) {
                }
                fileInputStream = new FileInputStream(k.a);
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader((InputStream) fileInputStream, com.alipay.sdk.sys.a.m));
                    while (true) {
                        try {
                            line = bufferedReader.readLine();
                            if (line != null) {
                            }
                        } catch (Exception unused2) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e2) {
                                    com.igexin.c.a.c.a.a(e2);
                                }
                            }
                            if (fileInputStream != 0) {
                                try {
                                    fileInputStream.close();
                                    return;
                                } catch (Exception e3) {
                                    com.igexin.c.a.c.a.a(e3);
                                    return;
                                }
                            }
                            return;
                        } catch (Throwable th) {
                            bufferedReader2 = bufferedReader;
                            Open = fileInputStream;
                            th = th;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e4) {
                                    com.igexin.c.a.c.a.a(e4);
                                }
                            }
                            if (Open == 0) {
                                throw th;
                            }
                            try {
                                Open.close();
                                throw th;
                            } catch (Exception e5) {
                                com.igexin.c.a.c.a.a(e5);
                                throw th;
                            }
                        }
                    }
                    bufferedReader.close();
                    try {
                        fileInputStream.close();
                    } catch (Exception e6) {
                        com.igexin.c.a.c.a.a(e6);
                        return;
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    Open = fileInputStream;
                }
            } catch (Throwable th3) {
                th = th3;
                if (Open != 0) {
                    try {
                        Open.close();
                    } catch (Exception e7) {
                        com.igexin.c.a.c.a.a(e7);
                    }
                }
                throw th;
            }
        } catch (Exception unused4) {
            Open = 0;
        } catch (Throwable th4) {
            th = th4;
            Open = 0;
        }
        if (Open != 0) {
            Open.close();
            Open = Open;
        }
        try {
            if (!new File(k.a).exists()) {
                k.a = k.b(com.igexin.push.core.e.l) + com.igexin.push.core.e.g + ".properties";
                if (!new File(k.a).exists()) {
                    throw new RuntimeException("extraConfigPath no exists");
                }
            }
            fileInputStream = new FileInputStream(k.a);
            bufferedReader = new BufferedReader(new InputStreamReader((InputStream) fileInputStream, com.alipay.sdk.sys.a.m));
            while (true) {
                line = bufferedReader.readLine();
                if (line != null) {
                    try {
                        break;
                    } catch (IOException e8) {
                        com.igexin.c.a.c.a.a(e8);
                    }
                } else if (!line.startsWith("#")) {
                    int length = line.split("=").length;
                }
            }
            bufferedReader.close();
            fileInputStream.close();
        } catch (Exception unused5) {
            fileInputStream = Open;
        } catch (Throwable th5) {
            th = th5;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void a(InputStream inputStream) throws Throwable {
        BufferedReader bufferedReader;
        byte b;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, com.alipay.sdk.sys.a.m));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            try {
                                bufferedReader.close();
                                return;
                            } catch (Exception e) {
                                com.igexin.c.a.c.a.a(e);
                                return;
                            }
                        }
                        if (!line.startsWith("#")) {
                            String[] strArrSplit = line.split("=");
                            if (strArrSplit.length >= 2) {
                                String strTrim = strArrSplit[0].trim();
                                String strTrim2 = strArrSplit[1].trim();
                                switch (strTrim.hashCode()) {
                                    case -1784363506:
                                        b = !strTrim.equals("sdk.readlocalcell.enable") ? (byte) -1 : (byte) 6;
                                        break;
                                    case -1734610495:
                                        if (strTrim.equals("sdk.enter.backup.detect.failed.cnt")) {
                                            b = 13;
                                            break;
                                        }
                                        break;
                                    case -1286040506:
                                        if (strTrim.equals("sdk.detect.ip.expired.time")) {
                                            b = 15;
                                            break;
                                        }
                                        break;
                                    case -1050591911:
                                        if (strTrim.equals("sdk.feature.setsilenttime.enable")) {
                                            b = 9;
                                            break;
                                        }
                                        break;
                                    case -1004501973:
                                        if (strTrim.equals("sdk.config_address")) {
                                            b = 1;
                                            break;
                                        }
                                        break;
                                    case -416668775:
                                        if (strTrim.equals("sdk.feature.setsockettimeout.enable")) {
                                            b = n.l;
                                            break;
                                        }
                                        break;
                                    case -367623287:
                                        if (strTrim.equals("sdk.address.id")) {
                                            b = 18;
                                            break;
                                        }
                                        break;
                                    case -52474114:
                                        if (strTrim.equals("sdk.feature.sendmessage.enable")) {
                                            b = 7;
                                            break;
                                        }
                                        break;
                                    case 85426222:
                                        if (strTrim.equals("sdk.cm_address_backup")) {
                                            b = 4;
                                            break;
                                        }
                                        break;
                                    case 178406040:
                                        if (strTrim.equals("sdk.stay.backup.time")) {
                                            b = 12;
                                            break;
                                        }
                                        break;
                                    case 275980049:
                                        if (strTrim.equals("sdk.login.failed.cnt")) {
                                            b = 14;
                                            break;
                                        }
                                        break;
                                    case 352273926:
                                        if (strTrim.equals("sdk.feature.setheartbeatinterval.enable")) {
                                            b = 10;
                                            break;
                                        }
                                        break;
                                    case 914256432:
                                        if (strTrim.equals("sdk.bi_address")) {
                                            b = 2;
                                            break;
                                        }
                                        break;
                                    case 1188929677:
                                        if (strTrim.equals("sdk.feature.settag.enable")) {
                                            b = 8;
                                            break;
                                        }
                                        break;
                                    case 1457933893:
                                        if (strTrim.equals("sdk.log_address")) {
                                            b = 3;
                                            break;
                                        }
                                        break;
                                    case 1488582065:
                                        if (strTrim.equals("sdk.address.key")) {
                                            b = 17;
                                            break;
                                        }
                                        break;
                                    case 1603576119:
                                        if (strTrim.equals("sdk.domainbackup.enable")) {
                                            b = 5;
                                            break;
                                        }
                                        break;
                                    case 1676315519:
                                        if (strTrim.equals("sdk.detect.interval.time")) {
                                            b = 16;
                                            break;
                                        }
                                        break;
                                    case 2077859667:
                                        if (strTrim.equals("sdk.cm_address")) {
                                            b = 0;
                                            break;
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                switch (b) {
                                    case 0:
                                        SDKUrlConfig.setXfrAddressIps(strTrim2.split(com.igexin.push.core.b.an));
                                        break;
                                    case 1:
                                        SDKUrlConfig.CONFIG_ADDRESS_IPS = strTrim2.split(com.igexin.push.core.b.an);
                                        break;
                                    case 2:
                                        SDKUrlConfig.BI_ADDRESS_IPS = strTrim2.split(com.igexin.push.core.b.an);
                                        break;
                                    case 3:
                                        SDKUrlConfig.LOG_ADDRESS_IPS = strTrim2.split(com.igexin.push.core.b.an);
                                        break;
                                    case 4:
                                        SDKUrlConfig.XFR_ADDRESS_BAK = strTrim2.split(com.igexin.push.core.b.an);
                                        break;
                                    case 5:
                                        d.g = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 6:
                                        d.h = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 7:
                                        d.j = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 8:
                                        d.k = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 9:
                                        d.l = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 10:
                                        d.m = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 11:
                                        d.n = Boolean.parseBoolean(strTrim2);
                                        break;
                                    case 12:
                                        d.r = Long.parseLong(strTrim2) * 1000;
                                        break;
                                    case 13:
                                        d.s = Integer.parseInt(strTrim2);
                                        break;
                                    case 14:
                                        d.t = Integer.parseInt(strTrim2);
                                        break;
                                    case 15:
                                        d.u = Long.parseLong(strTrim2) * 1000;
                                        break;
                                    case 16:
                                        d.v = Long.parseLong(strTrim2) * 1000;
                                        break;
                                    case 17:
                                        com.igexin.push.g.g.a = strTrim2;
                                        break;
                                    case 18:
                                        com.igexin.push.g.g.b = strTrim2;
                                        break;
                                }
                                com.igexin.c.a.c.a.a(a + "|loadConfigFromFile, config line:" + line, new Object[0]);
                            }
                        }
                    } catch (Exception unused) {
                        bufferedReader2 = bufferedReader;
                        com.igexin.c.a.c.a.a(a + "｜no config file found.", new Object[0]);
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                                return;
                            } catch (Exception e2) {
                                com.igexin.c.a.c.a.a(e2);
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e3) {
                                com.igexin.c.a.c.a.a(e3);
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (Exception unused2) {
        }
    }

    public static void a(Boolean bool) {
        try {
            k.l();
            if (new File(k.e).exists()) {
                b(bool);
                return;
            }
            byte[] bytes = "sdk.debug=".concat(String.valueOf(bool)).getBytes();
            if (bytes != null) {
                k.a(bytes, k.e);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public static void a(boolean z, boolean z2) {
        try {
            k.l();
            com.igexin.push.core.d.c.a().a(com.igexin.push.core.d.c.a, Boolean.valueOf(z));
            com.igexin.push.core.d.c.a().a(com.igexin.push.core.d.c.b, Boolean.valueOf(z2));
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public static int b() {
        try {
            k.l();
            Boolean boolA = com.igexin.push.core.d.c.a().a(com.igexin.push.core.d.c.a);
            int i = boolA == null ? -1 : boolA.booleanValue() ? 1 : 0;
            com.igexin.c.a.c.a.a(a + "|getGuardMeFromFile gm= " + i, new Object[0]);
            return i;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return -1;
        }
    }

    private static void b(Boolean bool) throws Throwable {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        String str;
        BufferedReader bufferedReader2 = null;
        try {
            fileInputStream = new FileInputStream(k.e);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, com.alipay.sdk.sys.a.m));
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.startsWith("#")) {
                            sb.append(line);
                            str = "\n";
                        } else {
                            String[] strArrSplit = line.split("=");
                            if (strArrSplit.length < 2) {
                                sb.append(line);
                                str = "\n";
                            } else {
                                String strTrim = strArrSplit[0].trim();
                                strArrSplit[1].trim();
                                if (!strTrim.equals("sdk.debug")) {
                                    sb.append(line);
                                    str = "\n";
                                }
                            }
                        }
                        sb.append(str);
                    }
                    sb.append("sdk.debug=".concat(String.valueOf(bool)));
                    byte[] bytes = sb.toString().getBytes();
                    if (bytes != null) {
                        k.a(bytes, k.e);
                    }
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        com.igexin.c.a.c.a.a(e);
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                        com.igexin.c.a.c.a.a(e2);
                    }
                } catch (Exception unused) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e3) {
                            com.igexin.c.a.c.a.a(e3);
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e4) {
                            com.igexin.c.a.c.a.a(e4);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            com.igexin.c.a.c.a.a(e5);
                        }
                    }
                    if (fileInputStream == null) {
                        throw th;
                    }
                    try {
                        fileInputStream.close();
                        throw th;
                    } catch (Exception e6) {
                        com.igexin.c.a.c.a.a(e6);
                        throw th;
                    }
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Exception unused3) {
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            bufferedReader = null;
        }
    }

    public static int c() {
        try {
            Boolean boolA = com.igexin.push.core.d.c.a().a(com.igexin.push.core.d.c.b);
            int i = boolA == null ? -1 : boolA.booleanValue() ? 1 : 0;
            com.igexin.c.a.c.a.a(a + "|getGuardOthersFromFile gm= " + i, new Object[0]);
            return i;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return -1;
        }
    }
}
