package com.loc;

import android.os.Build;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class ai {
    private static volatile ah a;
    private static Properties b = b();

    private ai() {
    }

    public static ah a() {
        if (a == null) {
            synchronized (ai.class) {
                if (a == null) {
                    try {
                        ah ahVarA = a(Build.MANUFACTURER);
                        if ("".equals(ahVarA.a())) {
                            Iterator it = Arrays.asList(ah.MIUI.a(), ah.Flyme.a(), ah.EMUI.a(), ah.ColorOS.a(), ah.FuntouchOS.a(), ah.SmartisanOS.a(), ah.AmigoOS.a(), ah.Sense.a(), ah.LG.a(), ah.Google.a(), ah.NubiaUI.a()).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    ahVarA = ah.Other;
                                    break;
                                }
                                ah ahVarA2 = a((String) it.next());
                                if (!"".equals(ahVarA2.a())) {
                                    ahVarA = ahVarA2;
                                    break;
                                }
                            }
                        }
                        a = ahVarA;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return a;
    }

    private static ah a(String str) {
        if (str == null || str.length() <= 0) {
            return ah.Other;
        }
        if (str.equals(ah.MIUI.a())) {
            ah ahVar = ah.MIUI;
            if (a(ahVar)) {
                return ahVar;
            }
        } else if (str.equals(ah.Flyme.a())) {
            ah ahVar2 = ah.Flyme;
            if (b(ahVar2)) {
                return ahVar2;
            }
        } else if (str.equals(ah.EMUI.a())) {
            ah ahVar3 = ah.EMUI;
            if (c(ahVar3)) {
                return ahVar3;
            }
        } else if (str.equals(ah.ColorOS.a())) {
            ah ahVar4 = ah.ColorOS;
            if (d(ahVar4)) {
                return ahVar4;
            }
        } else if (str.equals(ah.FuntouchOS.a())) {
            ah ahVar5 = ah.FuntouchOS;
            if (e(ahVar5)) {
                return ahVar5;
            }
        } else if (str.equals(ah.SmartisanOS.a())) {
            ah ahVar6 = ah.SmartisanOS;
            if (f(ahVar6)) {
                return ahVar6;
            }
        } else if (str.equals(ah.AmigoOS.a())) {
            ah ahVar7 = ah.AmigoOS;
            if (g(ahVar7)) {
                return ahVar7;
            }
        } else if (str.equals(ah.EUI.a())) {
            ah ahVar8 = ah.EUI;
            if (h(ahVar8)) {
                return ahVar8;
            }
        } else if (str.equals(ah.Sense.a())) {
            ah ahVar9 = ah.Sense;
            if (i(ahVar9)) {
                return ahVar9;
            }
        } else if (str.equals(ah.LG.a())) {
            ah ahVar10 = ah.LG;
            if (j(ahVar10)) {
                return ahVar10;
            }
        } else if (str.equals(ah.Google.a())) {
            ah ahVar11 = ah.Google;
            if (k(ahVar11)) {
                return ahVar11;
            }
        } else if (str.equals(ah.NubiaUI.a())) {
            ah ahVar12 = ah.NubiaUI;
            if (l(ahVar12)) {
                return ahVar12;
            }
        }
        return ah.Other;
    }

    private static void a(ah ahVar, String str) {
        Matcher matcher = Pattern.compile("([\\d.]+)[^\\d]*").matcher(str);
        if (matcher.find()) {
            try {
                String strGroup = matcher.group(1);
                ahVar.a(strGroup);
                ahVar.a(Integer.parseInt(strGroup.split("\\.")[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean a(ah ahVar) {
        if (TextUtils.isEmpty(b("ro.miui.ui.version.name"))) {
            return false;
        }
        String strB = b("ro.build.version.incremental");
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static String b(String str) {
        String property = b.getProperty("[" + str + "]", null);
        return TextUtils.isEmpty(property) ? c(str) : property.replace("[", "").replace("]", "");
    }

    private static Properties b() {
        Properties properties = new Properties();
        try {
            properties.load(Runtime.getRuntime().exec("getprop").getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static boolean b(ah ahVar) {
        String strB = b("ro.flyme.published");
        String strB2 = b("ro.meizu.setupwizard.flyme");
        if (TextUtils.isEmpty(strB) && TextUtils.isEmpty(strB2)) {
            return false;
        }
        String strB3 = b("ro.build.display.id");
        a(ahVar, strB3);
        ahVar.b(strB3);
        return true;
    }

    private static String c(String str) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ".concat(String.valueOf(str))).getInputStream()), 1024);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                try {
                    bufferedReader.close();
                } catch (IOException unused) {
                }
                return line;
            } catch (IOException unused2) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (IOException unused5) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static boolean c(ah ahVar) {
        String strB = b("ro.build.version.emui");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean d(ah ahVar) {
        String strB = b("ro.build.version.opporom");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean e(ah ahVar) {
        String strB = b("ro.vivo.os.build.display.id");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean f(ah ahVar) {
        String strB = b("ro.smartisan.version");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean g(ah ahVar) {
        String strB = b("ro.build.display.id");
        if (TextUtils.isEmpty(strB) || !strB.matches("amigo([\\d.]+)[a-zA-Z]*")) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean h(ah ahVar) {
        String strB = b("ro.letv.release.version");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean i(ah ahVar) {
        String strB = b("ro.build.sense.version");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean j(ah ahVar) {
        String strB = b("sys.lge.lgmdm_version");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }

    private static boolean k(ah ahVar) {
        if (!"android-google".equals(b("ro.com.google.clientidbase"))) {
            return false;
        }
        String strB = b("ro.build.version.release");
        ahVar.a(Build.VERSION.SDK_INT);
        ahVar.b(strB);
        return true;
    }

    private static boolean l(ah ahVar) {
        String strB = b("ro.build.nubia.rom.code");
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        a(ahVar, strB);
        ahVar.b(strB);
        return true;
    }
}
