package com.getui.gtc.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.getui.gtc.api.GtcIdCallback;
import com.getui.gtc.api.OnDycEnableChangedListener;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.ProcessSwitchContract;
import com.getui.gtc.base.publish.Broker;
import com.getui.gtc.base.publish.Subscriber;
import com.getui.gtc.base.util.BundleCompat;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.base.util.ScheduleQueue;
import com.getui.gtc.d.a;
import com.getui.gtc.dim.Caller;
import com.getui.gtc.dim.DimManager;
import com.getui.gtc.f.b;
import com.getui.gtc.i.c.a;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class GtcManager implements Subscriber {
    private static String methodName;

    static class InstanceHolder {
        private static final GtcManager instance = new GtcManager();

        private InstanceHolder() {
        }
    }

    private GtcManager() {
    }

    private void checkSdkInfo(SdkInfo sdkInfo) {
        if (TextUtils.isEmpty(sdkInfo.getModuleName())) {
            a.c("moduleName not set for sdkinfo");
            throw new RuntimeException("moduleName not set for sdkinfo");
        }
        if (TextUtils.isEmpty(sdkInfo.getAppid())) {
            a.c("appid not set for sdkinfo");
            throw new RuntimeException("appid not set for sdkinfo");
        }
        if (TextUtils.isEmpty(sdkInfo.getVersion())) {
            a.c("version not set for sdkinfo");
            throw new RuntimeException("version not set for sdkinfo");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(ProcessSwitchContract.CLASS_NAME, getClass().getName());
        bundle.putString(ProcessSwitchContract.GET_INSTANCE, methodName);
        return bundle;
    }

    private static Caller getFromTrace() {
        String className;
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int i = -1;
            String name = GtcManager.class.getName();
            for (int i2 = 2; i2 < stackTrace.length; i2++) {
                if (!stackTrace[i2].getClassName().equals(name)) {
                    if (i > 0) {
                        break;
                    }
                } else {
                    i = i2;
                }
            }
            className = stackTrace[i + 1].getClassName();
        } catch (Throwable th) {
            a.c(th);
        }
        if (className.startsWith("com.igexin")) {
            return Caller.PUSH;
        }
        if (className.startsWith("com.g.gysdk")) {
            return Caller.GY;
        }
        if (className.startsWith("com.getui.gs")) {
            return Caller.IDO;
        }
        if (className.startsWith("com.sdk.plus")) {
            return Caller.WUS;
        }
        return Caller.UNKNOWN;
    }

    public static GtcManager getInstance() {
        methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return InstanceHolder.instance;
    }

    public void addOnDycEnableChangedListener(Context context, OnDycEnableChangedListener.Stub stub) {
        if (CommonUtil.isGtcProcess()) {
            b.a().a(stub);
            return;
        }
        GtcProvider.setContext(context);
        Bundle bundleCreateBundle = createBundle();
        bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-5-1");
        BundleCompat.putBinder(bundleCreateBundle, "gtc-5-2", stub);
        Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
        if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
            a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
        }
    }

    public ClassLoader getClassLoader(Bundle bundle) {
        return com.getui.gtc.g.b.a(bundle);
    }

    @Deprecated
    public void init(Context context, GtcIdCallback.Stub stub) {
        initialize(context, stub);
    }

    @Deprecated
    public String initialize(Context context, GtcIdCallback.Stub stub) {
        return initialize(context, getFromTrace(), stub);
    }

    public String initialize(Context context, Caller caller, GtcIdCallback.Stub stub) {
        if (CommonUtil.isGtcProcess()) {
            DimManager.getInstance().set("dim-2-2-3-1", "dim-2-2-3-1", caller != null ? caller.name() : null);
            return a.C0011a.a.a(stub);
        }
        GtcProvider.setContext(context);
        Bundle bundleCreateBundle = createBundle();
        bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-1-1");
        bundleCreateBundle.putString("gtc-1-3", caller != null ? caller.name() : null);
        BundleCompat.putBinder(bundleCreateBundle, "gtc-1-2", stub);
        Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
        Object obj = bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION);
        if (obj instanceof Throwable) {
            com.getui.gtc.i.c.a.a("initialize", (Throwable) obj);
        }
        return bundleSubscribe.getString(ProcessSwitchContract.METHOD_RETURN);
    }

    public boolean loadBundle(Context context, Bundle bundle) {
        if (context != null) {
            GtcProvider.setContext(context.getApplicationContext());
        }
        return com.getui.gtc.g.b.a(context, bundle);
    }

    public void loadSdk(SdkInfo sdkInfo) {
        checkSdkInfo(sdkInfo);
        if (CommonUtil.isGtcProcess()) {
            a.C0011a.a.a(sdkInfo);
            return;
        }
        Bundle bundleCreateBundle = createBundle();
        bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-2-1");
        bundleCreateBundle.putParcelable("gtc-2-2", sdkInfo);
        Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
        if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
            com.getui.gtc.i.c.a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
        }
    }

    public void onHWSmartFenceBind(final Context context, final Intent intent) {
        ScheduleQueue.getInstance().addSchedule(new Runnable() { // from class: com.getui.gtc.api.GtcManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (CommonUtil.isGtcProcess()) {
                    com.getui.gtc.a.a.a(intent);
                    return;
                }
                GtcProvider.setContext(context);
                Bundle bundleCreateBundle = GtcManager.this.createBundle();
                bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-6-1");
                bundleCreateBundle.putParcelable("gtc-6-2", intent);
                Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
                if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
                    com.getui.gtc.i.c.a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
                }
            }
        });
    }

    public void onHWSmartFenceLabelChanged(final Context context, final Caller caller, final String str, final String str2) {
        ScheduleQueue.getInstance().addSchedule(new Runnable() { // from class: com.getui.gtc.api.GtcManager.2
            @Override // java.lang.Runnable
            public void run() {
                if (CommonUtil.isGtcProcess()) {
                    com.getui.gtc.a.a.a(caller, str, str2);
                    return;
                }
                GtcProvider.setContext(context);
                Bundle bundleCreateBundle = GtcManager.this.createBundle();
                bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-7-1");
                bundleCreateBundle.putString("gtc-7-2", caller != null ? caller.name() : null);
                bundleCreateBundle.putString("gtc-7-3", str);
                bundleCreateBundle.putString("gtc-7-4", str2);
                Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
                if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
                    com.getui.gtc.i.c.a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
                }
            }
        });
    }

    @Override // com.getui.gtc.base.publish.Subscriber
    public void receive(Bundle bundle, Bundle bundle2) {
        ArrayList arrayList = new ArrayList();
        try {
            try {
                Throwable th = (Throwable) bundle2.getSerializable(ProcessSwitchContract.METHOD_EXCEPTION);
                if (th != null) {
                    arrayList.add(th);
                }
                String string = bundle.getString(ProcessSwitchContract.METHOD_NAME);
                if (TextUtils.isEmpty(string)) {
                    throw new RuntimeException("methodName missed");
                }
                switch (string) {
                    case "gtc-1-1":
                        DimManager.getInstance().set("dim-2-2-3-1", "dim-2-2-3-1", bundle.getString("gtc-1-3"));
                        bundle2.putString(ProcessSwitchContract.METHOD_RETURN, a.C0011a.a.a(GtcIdCallback.Stub.asInterface(BundleCompat.getBinder(bundle, "gtc-1-2"))));
                        break;
                    case "gtc-2-1":
                        a.C0011a.a.a((SdkInfo) bundle.getParcelable("gtc-2-2"));
                        break;
                    case "gtc-3-1":
                        bundle.getString("gtc-3-2");
                        a.C0011a.a.a(bundle.getIntArray("gtc-3-3"));
                        break;
                    case "gtc-4-1":
                        a.C0011a.a.a(bundle.getInt("gtc-4-2"));
                        break;
                    case "gtc-5-1":
                        b.a().a(OnDycEnableChangedListener.Stub.asInterface(BundleCompat.getBinder(bundle, "gtc-5-2")));
                        break;
                    case "gtc-6-1":
                        com.getui.gtc.a.a.a((Intent) bundle.getParcelable("gtc-6-2"));
                        break;
                    case "gtc-7-1":
                        String string2 = bundle.getString("gtc-7-2");
                        com.getui.gtc.a.a.a(TextUtils.isEmpty(string2) ? null : Caller.valueOf(string2), bundle.getString("gtc-7-3"), bundle.getString("gtc-7-4"));
                        break;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    com.getui.gtc.i.c.a.b((Throwable) it.next());
                }
            } catch (Throwable th2) {
                arrayList.add(th2);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    com.getui.gtc.i.c.a.b((Throwable) it2.next());
                }
            }
        } catch (Throwable th3) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                com.getui.gtc.i.c.a.b((Throwable) it3.next());
            }
            throw th3;
        }
    }

    public void removeExt(String str, int[] iArr) {
        if (CommonUtil.isGtcProcess()) {
            a.C0011a.a.a(iArr);
            return;
        }
        Bundle bundleCreateBundle = createBundle();
        bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-3-1");
        bundleCreateBundle.putString("gtc-3-2", str);
        bundleCreateBundle.putIntArray("gtc-3-3", iArr);
        Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
        if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
            com.getui.gtc.i.c.a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
        }
    }

    public void startType(int i) {
        if (CommonUtil.isGtcProcess()) {
            a.C0011a.a.a(i);
            return;
        }
        Bundle bundleCreateBundle = createBundle();
        bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "gtc-4-1");
        bundleCreateBundle.putInt("gtc-4-2", i);
        Bundle bundleSubscribe = Broker.getInstance().subscribe(bundleCreateBundle);
        if (bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION) != null) {
            com.getui.gtc.i.c.a.c((Throwable) bundleSubscribe.get(ProcessSwitchContract.METHOD_EXCEPTION));
        }
    }
}
