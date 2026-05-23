package com.mobile.auth.l;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;

/* JADX INFO: loaded from: classes.dex */
public class j {

    @SuppressLint({"StaticFieldLeak"})
    private static j b;
    private final Context a;

    private j(Context context) {
        this.a = context;
    }

    public static j a() {
        return b;
    }

    public static void a(Context context) {
        b = new j(context);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String b(String str) {
        byte b2;
        int iHashCode = str.hashCode();
        if (iHashCode != 49679479) {
            if (iHashCode != 49679502) {
                switch (iHashCode) {
                    case 49679470:
                        b2 = !str.equals("46000") ? (byte) -1 : (byte) 0;
                        break;
                    case 49679471:
                        if (str.equals("46001")) {
                            b2 = 4;
                            break;
                        }
                        break;
                    case 49679472:
                        if (str.equals("46002")) {
                            b2 = 1;
                            break;
                        }
                        break;
                    case 49679473:
                        if (str.equals("46003")) {
                            b2 = 7;
                            break;
                        }
                        break;
                    case 49679474:
                        if (str.equals("46004")) {
                            b2 = 3;
                            break;
                        }
                        break;
                    case 49679475:
                        if (str.equals("46005")) {
                            b2 = 8;
                            break;
                        }
                        break;
                    case 49679476:
                        if (str.equals("46006")) {
                            b2 = 5;
                            break;
                        }
                        break;
                    case 49679477:
                        if (str.equals("46007")) {
                            b2 = 2;
                            break;
                        }
                        break;
                }
            } else if (str.equals("46011")) {
                b2 = 9;
            }
        } else if (str.equals("46009")) {
            b2 = 6;
        }
        switch (b2) {
            case 0:
            case 1:
            case 2:
            case 3:
                c.a("SIMUtils", "中国移动");
                return "1";
            case 4:
            case 5:
            case 6:
                c.a("SIMUtils", "中国联通");
                return com.igexin.push.config.c.H;
            case 7:
            case 8:
            case 9:
                c.a("SIMUtils", "中国电信");
                return AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM;
            default:
                return "0";
        }
    }

    public String a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = c();
        }
        return b(str);
    }

    public String b() {
        try {
            int iA = com.mobile.auth.f.a.a().b().a();
            return iA >= 0 ? Integer.toString(iA) : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String c() {
        TelephonyManager telephonyManager = (TelephonyManager) this.a.getSystemService("phone");
        if (telephonyManager == null) {
            return "";
        }
        String simOperator = telephonyManager.getSimOperator();
        c.b("SIMUtils", "SysOperator= " + simOperator);
        return simOperator;
    }
}
