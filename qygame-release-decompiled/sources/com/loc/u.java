package com.loc;

import com.loc.t;

/* JADX INFO: loaded from: classes.dex */
public final class u {
    public final t.c a;
    public final String b;

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0011. Please report as an issue. */
    u(t.c cVar, w wVar) {
        String str;
        Object[] objArr;
        String str2;
        this.a = cVar;
        String str3 = "";
        switch (cVar) {
            case ShowUnknowCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请设置隐私权政策是否弹窗告知用户";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case ShowNoShowCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请确保隐私权政策已弹窗告知用户";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case InfoUnknowCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请确保设置隐私权政策是否包含高德开平隐私权政策";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case InfoNotContainCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请确保隐私权政策已经包含高德开平隐私权政策";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case AgreeUnknowCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请确保设置隐私权政策是否取得用户同意";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case AgreeNotAgreeCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能前请确保隐私权政策已取得用户同意";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case InvaildUserKeyCode:
                str = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n使用%s SDK 功能使用前请确保已经正确设置apiKey，如有疑问请在高德开放平台官网中搜索【INVALID_USER_KEY】相关内容进行解决。";
                objArr = new Object[]{wVar.a()};
                str3 = String.format(str, objArr);
                break;
            case IllegalArgument:
                str2 = "***确保调用SDK任何接口前先调用更新隐私合规updatePrivacyShow、updatePrivacyAgree两个接口并且参数值都为true，若未正确设置有崩溃风险***\n参数非法，context 或 sdkInfo为空";
                str3 = String.format(str2, new Object[0]);
                break;
            case SuccessCode:
                str2 = "设置隐私政策成功";
                str3 = String.format(str2, new Object[0]);
                break;
        }
        this.b = str3;
    }
}
