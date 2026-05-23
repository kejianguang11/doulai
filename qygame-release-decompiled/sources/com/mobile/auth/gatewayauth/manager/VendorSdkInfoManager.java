package com.mobile.auth.gatewayauth.manager;

import android.text.TextUtils;
import android.util.SparseArray;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.e;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.jsoner.JsonType;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class VendorSdkInfoManager {
    private SparseArray<VendorConfig> a = new com.mobile.auth.gatewayauth.manager.base.a(3);
    private SparseArray<VendorConfig> b = new com.mobile.auth.gatewayauth.manager.base.a(3);
    private String c;
    private String d;
    private d e;
    private SystemManager f;
    private com.mobile.auth.o.a g;

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager$4, reason: invalid class name */
    class AnonymousClass4 extends JsonType<VendorConfig> {
        AnonymousClass4() {
        }
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public VendorSdkInfoManager(d dVar, SystemManager systemManager) {
        this.e = dVar;
        this.g = this.e.a();
        this.f = systemManager;
    }

    static /* synthetic */ SparseArray a(VendorSdkInfoManager vendorSdkInfoManager) {
        try {
            return vendorSdkInfoManager.b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private MonitorStruct a(String str) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setSessionId(str);
            monitorStruct.setRequestId(this.e.e());
            monitorStruct.setAction(Constant.ACTION_QUERY_VENDOR_LIST);
            return monitorStruct;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ void a(VendorSdkInfoManager vendorSdkInfoManager, MonitorStruct monitorStruct) {
        try {
            vendorSdkInfoManager.a(monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(VendorSdkInfoManager vendorSdkInfoManager, String str) {
        try {
            vendorSdkInfoManager.storeVendorConfigsBySceneCodeToDisk(str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(final MonitorStruct monitorStruct) {
        try {
            ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        VendorSdkInfoManager.d(VendorSdkInfoManager.this).a(VendorSdkInfoManager.c(VendorSdkInfoManager.this).a(monitorStruct), 2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String[] strArr) {
        if (strArr == null) {
            return;
        }
        try {
            if (strArr.length >= 8) {
                this.c = strArr[6];
                this.e.a(this.c);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String[] strArr, SparseArray<VendorConfig> sparseArray) {
        if (strArr != null) {
            try {
                if (strArr.length >= 6 && sparseArray != null) {
                    for (int i = 0; i < 3; i++) {
                        VendorConfig vendorConfig = new VendorConfig();
                        int i2 = i * 2;
                        vendorConfig.setVendorAccessId(strArr[i2]);
                        vendorConfig.setVendorAccessSecret(strArr[i2 + 1]);
                        switch (i) {
                            case 0:
                                vendorConfig.setVendorKey(Constant.VENDOR_CMCC);
                                sparseArray.put(1, vendorConfig);
                                break;
                            case 1:
                                vendorConfig.setVendorKey(Constant.VENDOR_CUCC);
                                sparseArray.put(2, vendorConfig);
                                break;
                            case 2:
                                vendorConfig.setVendorKey(Constant.VENDOR_CTCC);
                                sparseArray.put(3, vendorConfig);
                                break;
                        }
                    }
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    static /* synthetic */ String b(VendorSdkInfoManager vendorSdkInfoManager) {
        try {
            return vendorSdkInfoManager.c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private void b(String[] strArr) {
        if (strArr == null) {
            return;
        }
        try {
            if (strArr.length >= 10) {
                this.d = strArr[9];
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ d c(VendorSdkInfoManager vendorSdkInfoManager) {
        try {
            return vendorSdkInfoManager.e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a d(VendorSdkInfoManager vendorSdkInfoManager) {
        try {
            return vendorSdkInfoManager.g;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @SafeProtector
    private native void loadVendorConfigsBySceneCodeFromDisk(String str);

    @SafeProtector
    private native void storeVendorConfigsBySceneCodeToDisk(String str);

    public VendorConfig a(int i) {
        try {
            VendorConfig vendorConfig = this.b.get(i);
            return vendorConfig != null ? vendorConfig : this.a.get(i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String a(boolean z) {
        if (z) {
            return "SceneCode";
        }
        try {
            return this.c == null ? UUID.randomUUID().toString() : this.c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a(String str, final RequestCallback<Void, String> requestCallback, e eVar) {
        if (requestCallback == null || eVar == null) {
            return;
        }
        try {
            final MonitorStruct monitorStructA = a(str);
            eVar.a(new e.a() { // from class: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager.2
                @Override // com.mobile.auth.gatewayauth.manager.e.a
                public void a(String str2, String str3) {
                    try {
                        monitorStructA.setEndTime(System.currentTimeMillis());
                        monitorStructA.setSuccess(false);
                        monitorStructA.setTopTraceId(str3);
                        monitorStructA.setFailRet(str2);
                        VendorSdkInfoManager.a(VendorSdkInfoManager.this, monitorStructA);
                        requestCallback.onError(str2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.e.a
                public void a(String str2, String str3, SparseArray<VendorConfig> sparseArray) {
                    if (sparseArray != null) {
                        try {
                            if (sparseArray.size() > 0) {
                                synchronized (VendorSdkInfoManager.this) {
                                    for (int i = 0; i < sparseArray.size(); i++) {
                                        VendorSdkInfoManager.a(VendorSdkInfoManager.this).put(sparseArray.keyAt(i), sparseArray.valueAt(i));
                                    }
                                }
                                VendorSdkInfoManager.a(VendorSdkInfoManager.this, VendorSdkInfoManager.b(VendorSdkInfoManager.this));
                                monitorStructA.setEndTime(System.currentTimeMillis());
                                monitorStructA.setSuccess(true);
                                monitorStructA.setTopTraceId(str3);
                                VendorSdkInfoManager.a(VendorSdkInfoManager.this, monitorStructA);
                                requestCallback.onSuccess(null);
                                return;
                            }
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                                return;
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                                return;
                            }
                        }
                    }
                    a(str2, str3);
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            return !TextUtils.isEmpty(this.c);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public boolean a(final String str, final e eVar) {
        try {
            if (!b()) {
                try {
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                final CountDownLatch countDownLatch = new CountDownLatch(1);
                                VendorSdkInfoManager.this.a(str, new RequestCallback<Void, String>() { // from class: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager.1.1
                                    public void a(String str2) {
                                        try {
                                            countDownLatch.countDown();
                                        } catch (Throwable th) {
                                            try {
                                                ExceptionProcessor.processException(th);
                                            } catch (Throwable th2) {
                                                ExceptionProcessor.processException(th2);
                                            }
                                        }
                                    }

                                    public void a(Void r1) {
                                        try {
                                            countDownLatch.countDown();
                                        } catch (Throwable th) {
                                            try {
                                                ExceptionProcessor.processException(th);
                                            } catch (Throwable th2) {
                                                ExceptionProcessor.processException(th2);
                                            }
                                        }
                                    }

                                    @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                                    public /* synthetic */ void onError(String str2) {
                                        try {
                                            a(str2);
                                        } catch (Throwable th) {
                                            try {
                                                ExceptionProcessor.processException(th);
                                            } catch (Throwable th2) {
                                                ExceptionProcessor.processException(th2);
                                            }
                                        }
                                    }

                                    @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                                    public /* synthetic */ void onSuccess(Void r1) {
                                        try {
                                            a(r1);
                                        } catch (Throwable th) {
                                            try {
                                                ExceptionProcessor.processException(th);
                                            } catch (Throwable th2) {
                                                ExceptionProcessor.processException(th2);
                                            }
                                        }
                                    }
                                }, eVar);
                                try {
                                    countDownLatch.await(3000L, TimeUnit.MILLISECONDS);
                                } catch (InterruptedException unused) {
                                }
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    }).get(3000L, TimeUnit.MILLISECONDS);
                } catch (Exception unused) {
                }
            }
            return b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public boolean b() {
        try {
            if (this.b.size() <= 0) {
                if (this.a.size() <= 0) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public String c() {
        try {
            return this.c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public boolean d() {
        return false;
    }

    @SafeProtector
    public native void setLocalVendorSdkInfo(String str);
}
