package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.os.Debug;
import android.os.Looper;
import android.text.TextUtils;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.base.Cache;
import com.mobile.auth.gatewayauth.manager.base.CacheKey;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.utils.Checker;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.gatewayauth.utils.security.CheckProxy;
import com.mobile.auth.gatewayauth.utils.security.CheckRoot;
import com.mobile.auth.gatewayauth.utils.security.EmulatorDetector;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.core.CryptUtil;
import com.nirvana.tools.core.ExecutorManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public class SystemManager {
    private final Context a;
    private String b;
    private String c;
    private final com.mobile.auth.o.a d;
    private volatile boolean e = true;
    private Future<?> f;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public SystemManager(final Context context, com.mobile.auth.o.a aVar) {
        this.a = context.getApplicationContext();
        this.f = ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.mobile.auth.gatewayauth.manager.SystemManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SystemManager.a(SystemManager.this, PackageUtils.getPackageName(context));
                    SystemManager.b(SystemManager.this, PackageUtils.getSign(context));
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }
        });
        this.d = aVar;
    }

    private TokenRet a(ResultCodeProcessor resultCodeProcessor, String str) {
        try {
            if (!com.mobile.auth.gatewayauth.utils.c.f(this.a)) {
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_NO_SIM_FAIL, "SIM卡无法检测", str);
            }
            if (com.mobile.auth.gatewayauth.utils.c.e(this.a)) {
                return null;
            }
            return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_NO_MOBILE_NETWORK_FAIL, "移动网络未开启", str);
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

    static /* synthetic */ String a(SystemManager systemManager, String str) {
        try {
            systemManager.b = str;
            return str;
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

    static /* synthetic */ String b(SystemManager systemManager, String str) {
        try {
            systemManager.c = str;
            return str;
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

    public TokenRet a(ResultCodeProcessor resultCodeProcessor, boolean z, String str) {
        try {
            TokenRet tokenRetCheckEnvSafe = checkEnvSafe(resultCodeProcessor, str);
            return (tokenRetCheckEnvSafe == null && z) ? a(resultCodeProcessor, str) : tokenRetCheckEnvSafe;
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

    public synchronized String a() {
        try {
            if (this.f != null) {
                try {
                    this.f.get();
                    this.f = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.b;
    }

    public void a(String str) {
        try {
            if ((FeatureManager.getInstance().get("whiteFileCheck") != null && "false".equals(FeatureManager.getInstance().get("whiteFileCheck").toString())) || TextUtils.isEmpty(str)) {
                return;
            }
            InputStream inputStream = null;
            try {
                try {
                    String strMd5Hex = CryptUtil.md5Hex(str);
                    if (TextUtils.isEmpty(strMd5Hex)) {
                        this.e = true;
                        return;
                    }
                    InputStream inputStreamOpen = this.a.getAssets().open(strMd5Hex);
                    try {
                        this.e = false;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception unused) {
                        inputStream = inputStreamOpen;
                        this.e = true;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        inputStream = inputStreamOpen;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused2) {
            }
        } catch (Throwable th3) {
            try {
                ExceptionProcessor.processException(th3);
            } catch (Throwable th4) {
                ExceptionProcessor.processException(th4);
            }
        }
    }

    protected synchronized <T> boolean a(String str, Cache<T> cache, long j) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (cache != null && cache.getKey() != null && cache.getKey().equals(str) && cache.getExpiredTime() - j > jCurrentTimeMillis) {
                return true;
            }
            if (cache != null) {
                this.d.a("ExpiredTime:", String.valueOf(cache.getExpiredTime()), "|threshold:", String.valueOf(j), "|currTime:", String.valueOf(jCurrentTimeMillis));
            }
            return false;
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

    public synchronized String b() {
        try {
            if (this.f != null) {
                try {
                    this.f.get();
                    this.f = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.c;
    }

    public String b(String str) {
        if (str == null) {
            return "unknown";
        }
        try {
            byte b = -1;
            int iHashCode = str.hashCode();
            if (iHashCode != -1350608857) {
                if (iHashCode != 95009260) {
                    if (iHashCode == 880617272 && str.equals(Constant.VENDOR_CMCC)) {
                        b = 0;
                    }
                } else if (str.equals(Constant.VENDOR_CUCC)) {
                    b = 1;
                }
            } else if (str.equals(Constant.VENDOR_CTCC)) {
                b = 2;
            }
            switch (b) {
                case 0:
                    return Constant.CMCC;
                case 1:
                    return Constant.CUCC;
                case 2:
                    return Constant.CTCC;
                default:
                    return "unknown";
            }
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

    public String c() {
        try {
            return com.mobile.auth.gatewayauth.utils.c.b(this.a);
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

    public String c(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_PRE_LOGIN_CODE;
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
    public TokenRet checkEnvSafe(ResultCodeProcessor resultCodeProcessor, String str) {
        try {
            try {
                if (this.e) {
                    String strC = Checker.c();
                    if (!TextUtils.isEmpty(strC) && !"0".equals(strC)) {
                        return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the app is attached, please use safe phone!", str);
                    }
                }
                String strIsDeviceRooted = CheckRoot.isDeviceRooted();
                if (!TextUtils.isEmpty(strIsDeviceRooted)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the phone is root, " + strIsDeviceRooted, str);
                }
                if (Thread.currentThread() == Looper.getMainLooper().getThread() && EmulatorDetector.isEmulator(this.a)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:Emulator is detected, please use real phone!", str);
                }
                if (CheckProxy.isDevicedProxy(this.a)) {
                    return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the phone is proxy, please do not proxy!", str);
                }
                if (!Debug.isDebuggerConnected() || i.a()) {
                    return null;
                }
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "手机终端不安全:the app is debuggerConnected, please do not debug!", str);
            } catch (Exception e) {
                return resultCodeProcessor.convertErrorInfo(Constant.CODE_ERROR_PHONE_UNSAFE_FAIL, "无法判运营商: " + e.getMessage(), str);
            }
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

    public String d() {
        try {
            return com.mobile.auth.gatewayauth.utils.c.c(this.a);
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

    public String d(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_PRE_AUTH_CODE;
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
    protected native synchronized String decryptContent(String str);

    public Context e() {
        try {
            return this.a;
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

    public String e(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_LOGIN_CODE;
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
    protected native synchronized String encryptContent(String str);

    public String f(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_LOGIN_TOKEN;
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

    public String g(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_SDK_AUTH_TOKEN;
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
    public native CacheKey getSimCacheKey(boolean z, String str);

    @SafeProtector
    public native CacheKey getVendorCacheKey(String str);

    public String h(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_LOGIN;
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

    public String i(String str) {
        try {
            return Constant.ACTION_SDK + b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_PRIVACYALERT;
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

    public String j(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_RETURN;
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

    public String k(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_CLICK_PRIVACYALERT_PRIVACY;
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

    public String l(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_PROTOCOL;
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

    public String m(String str) {
        try {
            return b(str).toLowerCase() + Constant.ACTION_AUTH_PAGE_START;
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
}
