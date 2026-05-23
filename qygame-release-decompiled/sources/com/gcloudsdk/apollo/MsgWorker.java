package com.gcloudsdk.apollo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class MsgWorker implements Runnable {
    private static String LOGTAG = "NetInterfaceHelper";
    private Context mContext;
    private boolean mPermissionGranted = false;
    private HashMap<String, Network> mNetworks = new HashMap<>();
    private Vector<EventMsg> mMsgQueue = new Vector<>(128);
    private Vector<String> mDomains = new Vector<>(128);

    public MsgWorker(Context context) {
        this.mContext = context;
    }

    private void dealBind(int i, String str, int i2) {
        Network network;
        Log.i(LOGTAG, "dealBind:" + str + " for " + i);
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (!this.mPermissionGranted) {
            Log.e(LOGTAG, "mPermissionGranted is false");
            return;
        }
        FileDescriptor fileDescriptor = new FileDescriptor();
        try {
            Field declaredField = FileDescriptor.class.getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            try {
                declaredField.setInt(fileDescriptor, i);
                synchronized (this) {
                    network = this.mNetworks.get(str);
                }
                if (network == null) {
                    bindCallback(str, i, 1, i2);
                    return;
                }
                if (Build.VERSION.SDK_INT < 23) {
                    bindCallback(str, i, -3, i2);
                    return;
                }
                try {
                    network.bindSocket(fileDescriptor);
                    bindCallback(str, i, 0, i2);
                } catch (IOException e) {
                    e.printStackTrace();
                    bindCallback(str, i, -2, i2);
                }
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                bindCallback(str, i, -1, i2);
            }
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
            bindCallback(str, i, -1, i2);
        }
    }

    private void dealWarm(String str) {
        HashMap map;
        Log.i(LOGTAG, "dealWarm:" + str);
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (!this.mPermissionGranted) {
            Log.e(LOGTAG, "mPermissionGranted is false");
            return;
        }
        synchronized (this) {
            this.mDomains.add(str);
            map = (HashMap) this.mNetworks.clone();
        }
        for (Map.Entry entry : map.entrySet()) {
            try {
                dnsQueryCallback(str, (String) entry.getKey(), ((Network) entry.getValue()).getByName(str).getHostAddress(), 4);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    private String ifname(int i) {
        switch (i) {
            case 0:
                return "cellular";
            case 1:
                return "wifi";
            case 2:
            default:
                return "unknown";
            case 3:
                return "ethernet";
            case 4:
                return "vpn";
        }
    }

    public native void bindCallback(String str, int i, int i2, int i3);

    public boolean checkManifestPermission() {
        if (this.mContext == null) {
            Log.i(LOGTAG, "checkManifestPermission mContext == null");
            return false;
        }
        try {
            String[] strArr = {"android.permission.INTERNET", "android.permission.CHANGE_NETWORK_STATE", "android.permission.ACCESS_NETWORK_STATE"};
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < strArr.length; i++) {
                if (ContextCompat.checkSelfPermission(this.mContext, strArr[i]) != 0) {
                    arrayList.add(strArr[i]);
                    Log.i(LOGTAG, "checkManifestPermission:" + strArr[i] + " has no permission");
                }
            }
            if (arrayList.isEmpty()) {
                this.mPermissionGranted = true;
            } else {
                this.mPermissionGranted = false;
            }
            return false;
        } catch (Exception unused) {
            this.mPermissionGranted = false;
            return false;
        }
    }

    public native void dnsQueryCallback(String str, String str2, String str3, int i);

    public void initCM() {
        Log.i(LOGTAG, "initCM");
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        checkManifestPermission();
        if (!this.mPermissionGranted) {
            Log.e(LOGTAG, "mPermissionGranted is false");
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(1).addCapability(12).build();
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.gcloudsdk.apollo.MsgWorker.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                MsgWorker.this.setNetwork(1, network);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLosing(Network network, int i) {
                MsgWorker.this.setNetwork(1, null);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                MsgWorker.this.setNetwork(1, null);
            }
        };
        NetworkRequest networkRequestBuild2 = new NetworkRequest.Builder().addTransportType(0).addCapability(12).build();
        ConnectivityManager.NetworkCallback networkCallback2 = new ConnectivityManager.NetworkCallback() { // from class: com.gcloudsdk.apollo.MsgWorker.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                MsgWorker.this.setNetwork(0, network);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLosing(Network network, int i) {
                MsgWorker.this.setNetwork(0, null);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                MsgWorker.this.setNetwork(0, null);
            }
        };
        NetworkRequest networkRequestBuild3 = new NetworkRequest.Builder().addTransportType(3).addCapability(12).build();
        ConnectivityManager.NetworkCallback networkCallback3 = new ConnectivityManager.NetworkCallback() { // from class: com.gcloudsdk.apollo.MsgWorker.3
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                MsgWorker.this.setNetwork(3, network);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLosing(Network network, int i) {
                MsgWorker.this.setNetwork(3, null);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                MsgWorker.this.setNetwork(3, null);
            }
        };
        NetworkRequest networkRequestBuild4 = new NetworkRequest.Builder().addTransportType(4).addCapability(12).build();
        ConnectivityManager.NetworkCallback networkCallback4 = new ConnectivityManager.NetworkCallback() { // from class: com.gcloudsdk.apollo.MsgWorker.4
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                MsgWorker.this.setNetwork(4, network);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLosing(Network network, int i) {
                MsgWorker.this.setNetwork(4, null);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                MsgWorker.this.setNetwork(4, null);
            }
        };
        connectivityManager.requestNetwork(networkRequestBuild, networkCallback);
        connectivityManager.requestNetwork(networkRequestBuild2, networkCallback2);
        connectivityManager.requestNetwork(networkRequestBuild3, networkCallback3);
        connectivityManager.requestNetwork(networkRequestBuild4, networkCallback4);
    }

    /* JADX INFO: Infinite loop detected, blocks: 28, insns: 0 */
    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            if (this.mMsgQueue.isEmpty()) {
                try {
                    Thread.sleep(20L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                EventMsg eventMsgFirstElement = this.mMsgQueue.firstElement();
                this.mMsgQueue.remove(0);
                int i = eventMsgFirstElement.cmd;
                if (i != 255) {
                    switch (i) {
                        case 1:
                            dealWarm(eventMsgFirstElement.strarg);
                            break;
                        case 2:
                            dealBind(eventMsgFirstElement.arg1, eventMsgFirstElement.strarg, eventMsgFirstElement.arg2);
                            break;
                    }
                } else {
                    initCM();
                }
            }
        }
    }

    public void sendMessage(EventMsg eventMsg) {
        this.mMsgQueue.add(eventMsg);
    }

    public void setNetwork(int i, Network network) {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        String strIfname = ifname(i);
        synchronized (this) {
            try {
                if (network == null) {
                    Log.i(LOGTAG, "remove net:" + i);
                    this.mNetworks.remove(strIfname);
                    return;
                }
                Log.i(LOGTAG, "setNetwork  net:" + i + " network:" + network.toString());
                this.mNetworks.put(strIfname, network);
                Object[] array = this.mDomains.toArray();
                if (network != null) {
                    for (Object obj : array) {
                        String str = (String) obj;
                        try {
                            dnsQueryCallback(str, strIfname, network.getByName(str).getHostAddress(), 4);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
