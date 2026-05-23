package com.igexin.push.config;

import android.os.Bundle;
import com.igexin.push.g.o;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    public static final String a = "MetaDataConfig";

    private static void a() {
        try {
            Bundle bundle = o.b(com.igexin.push.core.e.l).metaData;
            if (bundle == null || !bundle.keySet().contains("PUSH_DOMAIN")) {
                return;
            }
            com.igexin.c.a.c.a.b(a, "PUSH_DOMAIN:" + bundle.getString("PUSH_DOMAIN"));
            String string = bundle.getString("PUSH_DOMAIN");
            SDKUrlConfig.setXfrAddressIps(new String[]{"socket://xfr." + string + ":5224"});
            StringBuilder sb = new StringBuilder("XFR_ADDRESS_IPS:");
            sb.append(SDKUrlConfig.getXfrAddress()[0]);
            com.igexin.c.a.c.a.b(a, sb.toString());
            SDKUrlConfig.XFR_ADDRESS_BAK = new String[]{"socket://xfr_bak." + string + ":5224"};
            StringBuilder sb2 = new StringBuilder("XFR_ADDRESS_IPS_BAK:");
            sb2.append(SDKUrlConfig.XFR_ADDRESS_BAK[0]);
            com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
            SDKUrlConfig.BI_ADDRESS_IPS = new String[]{"https://bi." + string + "/api.php"};
            StringBuilder sb3 = new StringBuilder("BI_ADDRESS_IPS:");
            sb3.append(SDKUrlConfig.BI_ADDRESS_IPS[0]);
            com.igexin.c.a.c.a.b(a, sb3.toString());
            SDKUrlConfig.CONFIG_ADDRESS_IPS = new String[]{"https://config." + string + "/api.php"};
            StringBuilder sb4 = new StringBuilder("CONFIG_ADDRESS_IPS:");
            sb4.append(SDKUrlConfig.CONFIG_ADDRESS_IPS[0]);
            com.igexin.c.a.c.a.b(a, sb4.toString());
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a(e.toString(), new Object[0]);
        }
    }

    private static void a(String str) {
        SDKUrlConfig.setXfrAddressIps(new String[]{"socket://xfr." + str + ":5224"});
        StringBuilder sb = new StringBuilder("XFR_ADDRESS_IPS:");
        sb.append(SDKUrlConfig.getXfrAddress()[0]);
        com.igexin.c.a.c.a.b(a, sb.toString());
        SDKUrlConfig.XFR_ADDRESS_BAK = new String[]{"socket://xfr_bak." + str + ":5224"};
        StringBuilder sb2 = new StringBuilder("XFR_ADDRESS_IPS_BAK:");
        sb2.append(SDKUrlConfig.XFR_ADDRESS_BAK[0]);
        com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
        SDKUrlConfig.BI_ADDRESS_IPS = new String[]{"https://bi." + str + "/api.php"};
        StringBuilder sb3 = new StringBuilder("BI_ADDRESS_IPS:");
        sb3.append(SDKUrlConfig.BI_ADDRESS_IPS[0]);
        com.igexin.c.a.c.a.b(a, sb3.toString());
        SDKUrlConfig.CONFIG_ADDRESS_IPS = new String[]{"https://config." + str + "/api.php"};
        StringBuilder sb4 = new StringBuilder("CONFIG_ADDRESS_IPS:");
        sb4.append(SDKUrlConfig.CONFIG_ADDRESS_IPS[0]);
        com.igexin.c.a.c.a.b(a, sb4.toString());
    }
}
