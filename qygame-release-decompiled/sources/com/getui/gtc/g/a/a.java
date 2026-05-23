package com.getui.gtc.g.a;

import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Pair;
import com.getui.gtc.base.util.CommonUtil;
import com.igexin.push.GtPushInterface;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    Handler a;
    public GtPushInterface b;

    public a(final b bVar) {
        final HandlerThread handlerThread = new HandlerThread("Plugin Handler Thread");
        handlerThread.start();
        this.a = new Handler(handlerThread.getLooper()) { // from class: com.getui.gtc.g.a.a.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                try {
                } catch (Exception e) {
                    com.getui.gtc.i.c.a.b(e);
                    if (bVar != null) {
                        bVar.a(false);
                    }
                }
                if (message.what < 0) {
                    if (bVar != null) {
                        bVar.a(false);
                    }
                } else {
                    boolean zLoadSdk = a.this.b.loadSdk(message.getData());
                    if (bVar != null) {
                        bVar.a(zLoadSdk);
                    }
                    handlerThread.quit();
                    a.this.a = null;
                }
            }
        };
    }

    public static Pair<ServiceInfo, Class> a(Context context, Class cls) {
        boolean z;
        try {
            ServiceInfo[] serviceInfoArr = CommonUtil.getPackageInfoForSelf(context).services;
            if (serviceInfoArr == null || serviceInfoArr.length <= 0) {
                com.getui.gtc.i.c.a.b("no any service");
            } else {
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    try {
                        Class<?> cls2 = Class.forName(serviceInfo.name);
                        if (cls2 != cls) {
                            Class<?> superclass = cls2;
                            for (int i = 5; superclass != null && cls != null && i > 0; i--) {
                                if (superclass == cls) {
                                    z = true;
                                    break;
                                }
                                if (superclass.getSuperclass() == null) {
                                    break;
                                }
                                superclass = superclass.getSuperclass();
                            }
                            z = false;
                            if (z) {
                                return Pair.create(serviceInfo, cls2);
                            }
                            continue;
                        }
                    } catch (Throwable unused) {
                    }
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.a("findGtImplClassInManifest error", th);
        }
        return Pair.create(null, null);
    }

    public final void a(String str, String str2, String str3, String str4, String str5) {
        Bundle bundle = new Bundle();
        bundle.putString("dp", str);
        bundle.putString("od", str2);
        bundle.putString("cn", str3);
        bundle.putString("ad", str4);
        bundle.putString("gd", str5);
        Message messageObtain = Message.obtain();
        messageObtain.setData(bundle);
        messageObtain.what = 0;
        if (this.a != null) {
            messageObtain.setTarget(this.a);
            messageObtain.sendToTarget();
        }
    }
}
