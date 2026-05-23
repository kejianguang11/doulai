package com.mobile.auth.f;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.l.c;
import com.mobile.auth.l.g;
import com.mobile.auth.l.m;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a a;
    private static long b;
    private C0042a c = null;

    /* JADX INFO: renamed from: com.mobile.auth.f.a$a, reason: collision with other inner class name */
    public static class C0042a {
        private int a = -1;
        private int b = -1;

        public int a() {
            return this.b;
        }
    }

    private a() {
    }

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private void a(Context context, boolean z) {
        if (Build.VERSION.SDK_INT < 22) {
            this.c.a = -1;
            return;
        }
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(context.getApplicationContext());
        if (subscriptionManagerFrom != null) {
            try {
                if (this.c.a == -1 && Build.VERSION.SDK_INT >= 24) {
                    this.c.b = SubscriptionManager.getDefaultDataSubscriptionId();
                    c.b("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配成功: dataSubId = " + this.c.b);
                    return;
                }
            } catch (Exception unused) {
                c.a("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配失败");
            }
            try {
                Object objInvoke = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
                if ((objInvoke instanceof Integer) || (objInvoke instanceof Long)) {
                    this.c.b = ((Integer) objInvoke).intValue();
                    c.b("UMCTelephonyManagement", "android 7.0以下手机getDefaultDataSubId适配成功: dataSubId = " + this.c.b);
                    return;
                }
            } catch (Exception unused2) {
                c.a("UMCTelephonyManagement", "readDefaultDataSubId-->getDefaultDataSubId 反射出错");
            }
            try {
                Object objInvoke2 = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubscriptionId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
                if ((objInvoke2 instanceof Integer) || (objInvoke2 instanceof Long)) {
                    this.c.b = ((Integer) objInvoke2).intValue();
                    c.b("UMCTelephonyManagement", "反射getDefaultDataSubscriptionId适配成功: dataSubId = " + this.c.b);
                }
            } catch (Exception unused3) {
                c.a("UMCTelephonyManagement", "getDefaultDataSubscriptionId-->getDefaultDataSubscriptionId 反射出错");
            }
        }
    }

    private void b(Context context) throws Throwable {
        Cursor cursorQuery;
        c.b("UMCTelephonyManagement", "readSimInfoDbStart");
        Uri uri = Uri.parse("content://telephony/siminfo");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            try {
                cursorQuery = contentResolver.query(uri, new String[]{APEZProvider.FILEID, "sim_id"}, "sim_id>=?", new String[]{"0"}, null);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            int i = cursorQuery.getInt(cursorQuery.getColumnIndex("sim_id"));
                            int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex(APEZProvider.FILEID));
                            if (this.c.a == -1 && this.c.b != -1 && this.c.b == i2) {
                                this.c.a = i;
                                c.b("UMCTelephonyManagement", "通过读取sim db获取数据流量卡的卡槽值：" + i);
                            }
                            if (this.c.a == i) {
                                this.c.b = i2;
                            }
                        } catch (Exception unused) {
                            cursor = cursorQuery;
                            c.a("UMCTelephonyManagement", "readSimInfoDb error");
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            throw th;
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception unused2) {
            }
            c.b("UMCTelephonyManagement", "readSimInfoDbEnd");
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = cursor;
        }
    }

    @SuppressLint({"MissingPermission"})
    private int c(Context context) {
        TelephonyManager telephonyManager;
        if (!g.a(context, "android.permission.READ_PHONE_STATE") || (telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone")) == null) {
            return -1;
        }
        if (!m.d()) {
            return telephonyManager.getDataNetworkType();
        }
        try {
            Method method = telephonyManager.getClass().getMethod("getDataNetworkType", Integer.TYPE);
            c.b("UMCTelephonyManagement", "data dataNetworkType defaultDataSubId = " + this.c.b);
            int iIntValue = ((Integer) method.invoke(telephonyManager, Integer.valueOf(this.c.b))).intValue();
            c.b("UMCTelephonyManagement", "data dataNetworkType ---------" + iIntValue);
            if (iIntValue != 0 || Build.VERSION.SDK_INT < 24) {
                return iIntValue;
            }
            c.b("UMCTelephonyManagement", "data dataNetworkType ---->=N " + iIntValue);
            return telephonyManager.getDataNetworkType();
        } catch (Exception e) {
            c.a("UMCTelephonyManagement", "data dataNetworkType ----反射出错-----");
            e.printStackTrace();
            return -1;
        }
    }

    public String a(Context context) {
        switch (c(context)) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return "1";
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
                return com.igexin.push.config.c.H;
            case 13:
            case 18:
            case 19:
                return AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM;
            case 20:
                return AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_MZ;
            default:
                return "0";
        }
    }

    public void a(Context context, boolean z, boolean z2) throws Throwable {
        long jCurrentTimeMillis = System.currentTimeMillis() - b;
        if (jCurrentTimeMillis >= com.igexin.push.config.c.s || jCurrentTimeMillis <= 0) {
            this.c = new C0042a();
            if (z2) {
                a(context, z);
                if (m.e() && m.d()) {
                    c.b("UMCTelephonyManagement", "华为手机兼容性处理");
                    if (this.c.b == 0 || this.c.b == 1) {
                        if (this.c.a == -1) {
                            this.c.a = this.c.b;
                        }
                        this.c.b = -1;
                    }
                    if ((this.c.a != -1 || this.c.b != -1) && Build.VERSION.SDK_INT >= 21) {
                        b(context);
                    }
                }
                b = System.currentTimeMillis();
            }
        }
    }

    public C0042a b() {
        return this.c == null ? new C0042a() : this.c;
    }
}
