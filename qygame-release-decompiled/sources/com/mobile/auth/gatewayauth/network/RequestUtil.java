package com.mobile.auth.gatewayauth.network;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.model.LimitConfig;
import com.mobile.auth.gatewayauth.model.popsdkconfig.ConfigData;
import com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigRespone;
import com.mobile.auth.gatewayauth.model.popsdkconfig.UploadData;
import com.mobile.auth.v.e;
import com.nirvana.tools.jsoner.JsonType;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class RequestUtil {
    public static final String PUBLIC_SECKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLShWjAtxJv3g2VPIYOOAv4rnVDdLkdseKm7+KOkCBLV9SKY5oqksFaXcLZ+nRnjnczhze5eGKhevwliUyag6x96GyXI2WagKIoB7Uwl2byl0xB5bNvYzf+x/DKHTSoGJshU6shXWXcjGFq+mUiPhM3WGZoqdY+vvqOWD+tga8XQIDAQAB";
    private static final String SERVEL_URL = "https://dypnsapi.aliyuncs.com/?";

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$1, reason: invalid class name */
    static class AnonymousClass1 extends JsonType<VendorRespone> {
        AnonymousClass1() {
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$2, reason: invalid class name */
    static class AnonymousClass2 extends JsonType<SDKConfigRespone> {
        AnonymousClass2() {
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$3, reason: invalid class name */
    static class AnonymousClass3 extends JsonType<UploadData> {
        AnonymousClass3() {
        }
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    @SafeProtector
    private static native String assembleCustomizeToken(Context context, String str, String str2);

    private static native LimitConfig getConfig(ConfigData configData);

    public static native e getPrivateKey(Context context, String str, String str2);

    public static native String getSDKConfigByPop(String str, String str2);

    public static native String getVendorListByPop(String str, String str2);

    public static native String uploadUserTrackInfoByPop(String str, String str2) throws IOException;
}
