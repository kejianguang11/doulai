package com.getui.gtc.dyc;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.getui.gtc.base.ProcessSwitchContract;
import com.getui.gtc.base.publish.Broker;
import com.getui.gtc.base.publish.Subscriber;
import com.getui.gtc.base.util.BundleCompat;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dyc.Callback;
import com.getui.gtc.dyc.b.b;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class a implements Subscriber {
    private static String a;

    /* JADX INFO: renamed from: com.getui.gtc.dyc.a$a, reason: collision with other inner class name */
    static class C0017a {
        private static a a = new a();
    }

    private a() {
    }

    public static a a() {
        a = Thread.currentThread().getStackTrace()[2].getMethodName();
        return C0017a.a;
    }

    private Bundle d() {
        Bundle bundle = new Bundle();
        bundle.putString(ProcessSwitchContract.CLASS_NAME, getClass().getName());
        bundle.putString(ProcessSwitchContract.GET_INSTANCE, a);
        return bundle;
    }

    public Map<String, String> a(final b bVar) {
        if (CommonUtil.isGtcProcess()) {
            return f.a().a(bVar);
        }
        Bundle bundleD = d();
        bundleD.putString(ProcessSwitchContract.METHOD_NAME, "dyc-1-1");
        bundleD.putParcelable("dyc-1-2", bVar);
        if (bVar.i() != null) {
            BundleCompat.putBinder(bundleD, "dyc-1-3", new Callback.a() { // from class: com.getui.gtc.dyc.a.2
                @Override // com.getui.gtc.dyc.Callback
                public void a(Map map, Map map2) throws RemoteException {
                    bVar.i().a(map, map2);
                }

                @Override // com.getui.gtc.dyc.Callback
                public void b(String str) throws RemoteException {
                    bVar.i().b(str);
                }
            });
        }
        return (Map) Broker.getInstance().subscribe(bundleD).get(ProcessSwitchContract.METHOD_RETURN);
    }

    public Map<String, String> a(String str) {
        if (CommonUtil.isGtcProcess()) {
            return f.a().a(str);
        }
        Bundle bundleD = d();
        bundleD.putString(ProcessSwitchContract.METHOD_NAME, "dyc-2-1");
        bundleD.putString("dyc-2-2", str);
        return (Map) Broker.getInstance().subscribe(bundleD).get(ProcessSwitchContract.METHOD_RETURN);
    }

    public void a(String str, Map<String, String> map) {
        if (CommonUtil.isGtcProcess()) {
            f.a().a(str, map);
            return;
        }
        Bundle bundleD = d();
        bundleD.putString(ProcessSwitchContract.METHOD_NAME, "dyc-4-1");
        bundleD.putString("dyc-4-2", str);
        bundleD.putSerializable("dyc-4-3", (HashMap) map);
        Broker.getInstance().subscribe(bundleD);
    }

    public Map<String, Map<String, String>> c() {
        if (CommonUtil.isGtcProcess()) {
            return f.a().c();
        }
        Bundle bundleD = d();
        bundleD.putString(ProcessSwitchContract.METHOD_NAME, "dyc-3-1");
        return (Map) Broker.getInstance().subscribe(bundleD).get(ProcessSwitchContract.METHOD_RETURN);
    }

    @Override // com.getui.gtc.base.publish.Subscriber
    public void receive(Bundle bundle, Bundle bundle2) {
        String str;
        Serializable serializable;
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
                byte b = -1;
                int iHashCode = string.hashCode();
                if (iHashCode != 2112999862) {
                    if (iHashCode != 2113000823) {
                        if (iHashCode != 2113001784) {
                            if (iHashCode == 2113002745 && string.equals("dyc-4-1")) {
                                b = 3;
                            }
                        } else if (string.equals("dyc-3-1")) {
                            b = 2;
                        }
                    } else if (string.equals("dyc-2-1")) {
                        b = 1;
                    }
                } else if (string.equals("dyc-1-1")) {
                    b = 0;
                }
                switch (b) {
                    case 0:
                        b bVar = (b) bundle.getParcelable("dyc-1-2");
                        final Callback callbackA = Callback.a.a(BundleCompat.getBinder(bundle, "dyc-1-3"));
                        if (callbackA != null) {
                            bVar.i(new com.getui.gtc.dyc.b.c() { // from class: com.getui.gtc.dyc.a.1
                                @Override // com.getui.gtc.dyc.b.c
                                public void a(Map<String, String> map, Map<String, String> map2) {
                                    try {
                                        callbackA.a(map, map2);
                                    } catch (RemoteException e) {
                                        com.getui.gtc.dyc.a.a.a.a(e);
                                    }
                                }

                                @Override // com.getui.gtc.dyc.b.c
                                public void b(String str2) {
                                    try {
                                        callbackA.b(str2);
                                    } catch (RemoteException e) {
                                        com.getui.gtc.dyc.a.a.a.a(e);
                                    }
                                }
                            });
                        }
                        Map<String, String> mapA = a(bVar);
                        str = ProcessSwitchContract.METHOD_RETURN;
                        serializable = (Serializable) mapA;
                        bundle2.putSerializable(str, serializable);
                        break;
                    case 1:
                        Map<String, String> mapA2 = a(bundle.getString("dyc-2-2"));
                        str = ProcessSwitchContract.METHOD_RETURN;
                        serializable = (Serializable) mapA2;
                        bundle2.putSerializable(str, serializable);
                        break;
                    case 2:
                        Map<String, Map<String, String>> mapC = c();
                        str = ProcessSwitchContract.METHOD_RETURN;
                        serializable = (Serializable) mapC;
                        bundle2.putSerializable(str, serializable);
                        break;
                    case 3:
                        a(bundle.getString("dyc-4-2"), (HashMap) bundle.getSerializable("dyc-4-3"));
                        break;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    com.getui.gtc.dyc.a.a.a.a((Throwable) it.next());
                }
            } catch (Throwable th2) {
                arrayList.add(th2);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    com.getui.gtc.dyc.a.a.a.a((Throwable) it2.next());
                }
            }
        } catch (Throwable th3) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                com.getui.gtc.dyc.a.a.a.a((Throwable) it3.next());
            }
            throw th3;
        }
    }
}
