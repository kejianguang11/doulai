package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import android.text.TextUtils;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;
import com.mobile.auth.gatewayauth.model.CustomizeToken;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.core.CryptUtil;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.jsoner.JSONUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TokenGenerator {
    private com.mobile.auth.o.a a;
    private SystemManager b;
    private VendorSdkInfoManager c;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public TokenGenerator(com.mobile.auth.o.a aVar, SystemManager systemManager, VendorSdkInfoManager vendorSdkInfoManager) {
        this.a = aVar;
        this.b = systemManager;
        this.c = vendorSdkInfoManager;
    }

    @SafeProtector
    private String assembleCustomizeToken(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            try {
                String packageName = PackageUtils.getPackageName(context);
                CustomizeToken customizeToken = new CustomizeToken();
                HashMap map = new HashMap(3);
                map.put(AssistPushConsts.MSG_TYPE_TOKEN, str2);
                map.put("appid", str3);
                map.put(com.alipay.sdk.sys.a.f, str4);
                customizeToken.setEncryptToken(AESUtils.encryptString2Base64(new JSONObject(map).toString(), str, packageName));
                customizeToken.setKey(EncryptUtils.encrpytAESKey(str6, str));
                customizeToken.setVendorName(str5);
                if (Constant.VENDOR_CUCC.equals(str5)) {
                    customizeToken.setVendorName(Constant.VENDOR_CUXZ);
                } else {
                    customizeToken.setVendorName(str5);
                }
                return CryptUtil.Base64.encode(JSONUtils.toJson(customizeToken, null).toString().getBytes());
            } catch (Exception e) {
                this.a.e(ExecutorManager.getErrorInfoFromException(e));
                return null;
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

    @SafeProtector
    private native String generateCsrf(String str);

    public String a(Context context, String str, String str2, String str3, boolean z, String str4, String str5, String str6, boolean z2, String str7) {
        try {
            return assembleToken(context, null, null, null, str, str2, str3, z, str4, str5, str6, z2, str7);
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0031 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @SafeProtector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String assembleToken(Context context, String str, String str2, String str3, String str4, String str5, String str6, boolean z, String str7, String str8, String str9, boolean z2, String str10) {
        String str11;
        boolean z3;
        int i;
        String strEncode;
        String strGenerateCsrf = "";
        if (z) {
            try {
                strGenerateCsrf = generateCsrf(str5);
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
        String str12 = strGenerateCsrf;
        if (z2) {
            String strG = k.g(context, "rpk");
            if (!TextUtils.isEmpty(strG)) {
                str11 = strG;
                z3 = true;
                if (z3) {
                    try {
                        strEncode = CryptUtil.Base64.encode(EncryptUtils.encryptToken(context, str, str2, str3, this.c.c(), str4, str5, str6, str12, true, str10).getBytes(com.alipay.sdk.sys.a.m));
                        return strEncode;
                    } catch (UnsupportedEncodingException e) {
                        e = e;
                        i = 1;
                        com.mobile.auth.o.a aVar = this.a;
                        String[] strArr = new String[i];
                        strArr[0] = ExecutorManager.getErrorInfoFromException(e);
                        aVar.e(strArr);
                        return null;
                    }
                }
                i = 1;
                try {
                    strEncode = assembleCustomizeToken(context, str4, str5, str7, str8, str9, str11);
                    return strEncode;
                } catch (UnsupportedEncodingException e2) {
                    e = e2;
                    com.mobile.auth.o.a aVar2 = this.a;
                    String[] strArr2 = new String[i];
                    strArr2[0] = ExecutorManager.getErrorInfoFromException(e);
                    aVar2.e(strArr2);
                    return null;
                }
            }
            str11 = strG;
        } else {
            str11 = null;
        }
        z3 = false;
        if (z3) {
        }
    }
}
