package com.getui.gtc.a;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.e.c;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class f implements b {
    private String a;
    private boolean b = false;
    private long c = com.igexin.push.core.b.J;
    private String[] d = {"com.huawei.appmarket", "com.bbk.launcher2", "net.oneplus.launcher", "com.android.deskclock", "com.heytap.market", "com.oppo.market"};
    private String[] e = {"com.tencent.mm", "com.tencent.mobileqq", "com.eg.android.AlipayGphone", "com.jingdong.app.mall", "com.ss.android.article.news", "com.taobao.taobao", "com.tmall.wireless", "com.sankuai.meituan", "com.xunmeng.pinduoduo", "com.ss.android.ugc.aweme"};

    private String a() {
        try {
            StringBuilder sb = new StringBuilder();
            for (String str : this.d) {
                try {
                    sb.append(str + "#" + com.igexin.push.core.b.an);
                } catch (Throwable unused) {
                    com.getui.gtc.i.c.a.b(str + " not found");
                }
            }
            if (sb.toString().endsWith(com.igexin.push.core.b.an)) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.b(th);
            return "";
        }
    }

    @TargetApi(26)
    private String b() {
        File parentFile;
        File parentFile2;
        if (Build.VERSION.SDK_INT < 26) {
            com.getui.gtc.i.c.a.a("type304 get hot info failed, api<26");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            File externalCacheDir = GtcProvider.context().getExternalCacheDir();
            parentFile = null;
            if (externalCacheDir != null && (parentFile2 = externalCacheDir.getParentFile()) != null) {
                parentFile = parentFile2.getParentFile();
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.b(th);
        }
        if (parentFile == null) {
            return "";
        }
        for (String str : this.e) {
            try {
                BasicFileAttributes attributes = Files.readAttributes(new File(parentFile, str).toPath(), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
                sb.append(str + "#0#" + attributes.creationTime().toMillis() + "#" + attributes.lastAccessTime().toMillis());
                sb.append(com.igexin.push.core.b.an);
            } catch (Throwable th2) {
                com.getui.gtc.i.c.a.b(th2);
            }
        }
        if (sb.toString().endsWith(com.igexin.push.core.b.an)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Map<String, String> mapA = com.getui.gtc.f.c.a(43200000L, (com.getui.gtc.f.e) null);
            if (mapA != null && mapA.size() > 0) {
                try {
                    String str = mapA.get("sdk.gtc.type304.enable");
                    if (str != null) {
                        this.b = Boolean.parseBoolean(str);
                    }
                } catch (Exception e) {
                    com.getui.gtc.i.c.a.b(e);
                }
                try {
                    String str2 = mapA.get("sdk.gtc.type304.interval");
                    if (str2 != null) {
                        this.c = Long.parseLong(str2) * 1000;
                    }
                } catch (Exception e2) {
                    com.getui.gtc.i.c.a.b(e2);
                }
                try {
                    String str3 = mapA.get("sdk.gtc.type304.sys_al");
                    if (!TextUtils.isEmpty(str3) && !com.igexin.push.a.i.equalsIgnoreCase(str3)) {
                        String[] strArrSplit = str3.split(com.igexin.push.core.b.an);
                        if (strArrSplit.length > 0) {
                            this.d = strArrSplit;
                            com.getui.gtc.i.c.a.a("type304 dyc sysApp:" + Arrays.toString(strArrSplit));
                        }
                    }
                } catch (Exception e3) {
                    com.getui.gtc.i.c.a.b(e3);
                }
                try {
                    String str4 = mapA.get("sdk.gtc.type304.hot_al");
                    if (!TextUtils.isEmpty(str4) && !com.igexin.push.a.i.equalsIgnoreCase(str4)) {
                        String[] strArrSplit2 = str4.split(com.igexin.push.core.b.an);
                        if (strArrSplit2.length > 0) {
                            this.e = strArrSplit2;
                            com.getui.gtc.i.c.a.a("type304 dyc hotApp:" + Arrays.toString(strArrSplit2));
                        }
                    }
                } catch (Exception e4) {
                    com.getui.gtc.i.c.a.b(e4);
                }
            }
            if (!this.b) {
                com.getui.gtc.i.c.a.b("type 304 is not enabled");
                return;
            }
            if (CommonUtil.isAppDebugEnable()) {
                com.getui.gtc.i.c.a.b("type 304 is debug, disallow");
                return;
            }
            if (System.currentTimeMillis() - c.a.a.a.p < this.c) {
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            this.a = a.a(simpleDateFormat.format(new Date())) + "|" + a.a(com.getui.gtc.c.b.d) + "|" + a.a(com.getui.gtc.c.b.a) + "|android|" + GtcProvider.context().getPackageName() + "|GTC-3.2.18.0|" + a() + "|" + b();
            try {
                com.getui.gtc.h.a.a(this.a, 304);
                com.getui.gtc.e.d dVar = c.a.a.a;
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (dVar.a(16, jCurrentTimeMillis)) {
                    dVar.p = jCurrentTimeMillis;
                }
            } catch (Exception e5) {
                com.getui.gtc.i.c.a.c("type 304 report error: " + e5.toString());
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a("type 304", th);
        }
    }
}
