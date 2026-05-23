package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.utils.k;
import com.mobile.auth.gatewayauth.utils.l;
import com.nirvana.tools.core.AppUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public class PrivacyDialogActivity extends Activity {
    private static final int DP_MODE = 1073741824;
    public static final String EXIST = "exist";
    private static final int MODE_MASK = -1073741824;
    private static final int MODE_SHIFT = 30;
    public static final String STOP_LOADING = "stop_loading";
    private int dailogWidth;
    private TextView mAgreeBtn;
    private RelativeLayout mBodyRL;
    private RelativeLayout mBtnRL;
    private RelativeLayout mMainBackground;
    private RelativeLayout mMainRelativeLayout;
    private com.mobile.auth.o.a mPnsLogger;
    private com.mobile.auth.x.a mProgressDialog;
    private String mProtocol = "";
    private List<com.mobile.auth.gatewayauth.ui.b> mProtocolConfigs = new ArrayList(3);
    private RelativeLayout mProtocolRL;
    private TextView mProtocolTV;
    private RelativeLayout mTitleRL;
    private AuthUIConfig mUIConfig;
    private d mUIManager;
    private int mUIManagerID;
    private String mVendorClick;
    private String mVendorKey;
    private String mVendorProtocol;

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        final /* synthetic */ LinkedHashMap a;
        final /* synthetic */ String b;

        AnonymousClass1(LinkedHashMap linkedHashMap, String str) {
            this.a = linkedHashMap;
            this.b = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CustomInterface customInterface;
            try {
                AuthRegisterViewConfig authRegisterViewConfig = (AuthRegisterViewConfig) this.a.get(this.b);
                if (authRegisterViewConfig == null || (customInterface = authRegisterViewConfig.getCustomInterface()) == null) {
                    return;
                }
                customInterface.onClick(PrivacyDialogActivity.this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        final /* synthetic */ LinkedHashMap a;
        final /* synthetic */ String b;

        AnonymousClass2(LinkedHashMap linkedHashMap, String str) {
            this.a = linkedHashMap;
            this.b = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CustomInterface customInterface;
            try {
                AuthRegisterViewConfig authRegisterViewConfig = (AuthRegisterViewConfig) this.a.get(this.b);
                if (authRegisterViewConfig == null || (customInterface = authRegisterViewConfig.getCustomInterface()) == null) {
                    return;
                }
                customInterface.onClick(PrivacyDialogActivity.this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        final /* synthetic */ LinkedHashMap a;
        final /* synthetic */ String b;

        AnonymousClass3(LinkedHashMap linkedHashMap, String str) {
            this.a = linkedHashMap;
            this.b = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CustomInterface customInterface;
            try {
                AuthRegisterViewConfig authRegisterViewConfig = (AuthRegisterViewConfig) this.a.get(this.b);
                if (authRegisterViewConfig == null || (customInterface = authRegisterViewConfig.getCustomInterface()) == null) {
                    return;
                }
                customInterface.onClick(PrivacyDialogActivity.this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$4, reason: invalid class name */
    class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).e(PrivacyDialogActivity.access$000(PrivacyDialogActivity.this));
                PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).f(PrivacyDialogActivity.access$000(PrivacyDialogActivity.this));
                PrivacyDialogActivity.access$200(PrivacyDialogActivity.this, false, ResultCode.CODE_AUTH_PRIVACY_CLOSE, ResultCode.MSG_AUTH_PRIVACY_CLOSE);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$6, reason: invalid class name */
    class AnonymousClass6 implements View.OnTouchListener {
        AnonymousClass6() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$7, reason: invalid class name */
    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).f(PrivacyDialogActivity.access$000(PrivacyDialogActivity.this));
                PrivacyDialogActivity.access$200(PrivacyDialogActivity.this, true, ResultCode.CODE_AUTH_PRIVACY_CLOSE, ResultCode.MSG_AUTH_PRIVACY_CLOSE);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.PrivacyDialogActivity$9, reason: invalid class name */
    class AnonymousClass9 extends ClickableSpan {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;

        AnonymousClass9(String str, String str2, int i) {
            this.a = str;
            this.b = str2;
            this.c = i;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            try {
                PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).b(PrivacyDialogActivity.access$000(PrivacyDialogActivity.this), this.a, this.b, false);
                PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).a(this.a, this.b);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            try {
                super.updateDrawState(textPaint);
                textPaint.setColor(this.c);
                if (PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).isPrivacyAlertProtocolNameUseUnderLine()) {
                    textPaint.setUnderlineText(true);
                } else {
                    textPaint.setUnderlineText(false);
                }
                if (PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).getPrivacyAlertProtocolNameTypeface() != null) {
                    textPaint.setTypeface(PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).getPrivacyAlertProtocolNameTypeface());
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    static /* synthetic */ String access$000(PrivacyDialogActivity privacyDialogActivity) {
        try {
            return privacyDialogActivity.mVendorKey;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ d access$100(PrivacyDialogActivity privacyDialogActivity) {
        try {
            return privacyDialogActivity.mUIManager;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ void access$200(PrivacyDialogActivity privacyDialogActivity, boolean z, String str, String str2) {
        try {
            privacyDialogActivity.finishAuthPage(z, str, str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    static /* synthetic */ AuthUIConfig access$300(PrivacyDialogActivity privacyDialogActivity) {
        try {
            return privacyDialogActivity.mUIConfig;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private SpannableString dealProtocol(String str, List<com.mobile.auth.gatewayauth.ui.b> list) {
        try {
            SpannableString spannableString = new SpannableString(str);
            ClickableSpan vendorProtocol = getVendorProtocol(this.mVendorProtocol, this.mVendorClick, !l.a(this.mUIConfig.getPrivacyAlertOperatorColor()) ? this.mUIConfig.getPrivacyAlertOperatorColor() : this.mUIConfig.getPrivacyAlertContentColor() != 0 ? this.mUIConfig.getPrivacyAlertContentColor() : this.mUIConfig.getProtocolOneColor());
            for (com.mobile.auth.gatewayauth.ui.b bVar : list) {
                ClickableSpan protocol = getProtocol(bVar.b(), bVar.c(), bVar.d());
                int iIndexOf = str.indexOf(bVar.b());
                spannableString.setSpan(protocol, iIndexOf, bVar.b().length() + iIndexOf, 34);
            }
            spannableString.setSpan(vendorProtocol, str.indexOf(this.mVendorProtocol), str.indexOf(this.mVendorProtocol) + this.mVendorProtocol.length(), 34);
            return spannableString;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private void finishAuthPage(boolean z, String str, String str2) {
        try {
            Intent intent = getIntent();
            intent.putExtra("HasAgree", !z);
            intent.putExtra("code", str);
            intent.putExtra("msg", str2);
            setResult(-1, intent);
            this.mUIManager.d(this);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private native ClickableSpan getProtocol(String str, String str2, int i);

    private ClickableSpan getVendorProtocol(final String str, final String str2, final int i) {
        try {
            return new ClickableSpan() { // from class: com.mobile.auth.gatewayauth.PrivacyDialogActivity.8
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    try {
                        PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).b(PrivacyDialogActivity.access$000(PrivacyDialogActivity.this), str, str2, true);
                        PrivacyDialogActivity.access$100(PrivacyDialogActivity.this).a(str, str2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    try {
                        super.updateDrawState(textPaint);
                        textPaint.setColor(i);
                        if (PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).isPrivacyAlertProtocolNameUseUnderLine()) {
                            textPaint.setUnderlineText(true);
                        } else {
                            textPaint.setUnderlineText(false);
                        }
                        if (PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).getPrivacyAlertProtocolNameTypeface() != null) {
                            textPaint.setTypeface(PrivacyDialogActivity.access$300(PrivacyDialogActivity.this).getPrivacyAlertProtocolNameTypeface());
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            };
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @SafeProtector
    private native void init();

    @SafeProtector
    private native RelativeLayout initBodyView();

    @SafeProtector
    private native void initBtnLayoutDynamicView();

    private View initBtnLayoutView() {
        try {
            this.mBtnRL = new RelativeLayout(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            if (this.mUIConfig.getPrivacyAlertCornerRadiusArray() == null || this.mUIConfig.getPrivacyAlertCornerRadiusArray().length < 4) {
                this.mBtnRL.setBackgroundColor(this.mUIConfig.getPrivacyAlertBackgroundColor());
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(0);
                gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, transferCorner(this.mUIConfig.getPrivacyAlertCornerRadiusArray()[2]), transferCorner(this.mUIConfig.getPrivacyAlertCornerRadiusArray()[2]), transferCorner(this.mUIConfig.getPrivacyAlertCornerRadiusArray()[3]), transferCorner(this.mUIConfig.getPrivacyAlertCornerRadiusArray()[3])});
                gradientDrawable.setColor(this.mUIConfig.getPrivacyAlertBackgroundColor());
                setBackground(this.mBtnRL, gradientDrawable);
            }
            layoutParams.addRule(3, this.mBodyRL.getId());
            this.mBtnRL.setLayoutParams(layoutParams);
            k.a(this.mBtnRL, initBtnView(), 0);
            initBtnLayoutDynamicView();
            return this.mBtnRL;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private native View initBtnView();

    @SafeProtector
    private native void initContentLayoutDynamicView();

    @SafeProtector
    private void initIntentData() {
        try {
            Intent intent = getIntent();
            this.mVendorKey = intent.getStringExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY);
            this.mUIManagerID = intent.getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, 0);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @SafeProtector
    private native RelativeLayout initProtocolView();

    @SafeProtector
    private native void initTitleLayoutDynamicView();

    @SafeProtector
    private native RelativeLayout initTitleView();

    @SafeProtector
    private native void initView();

    @SafeProtector
    private native void initXMLDynamicView();

    private boolean isTouchPointInView(View view, int i, int i2) {
        if (view == null) {
            return false;
        }
        try {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i3 = iArr[0];
            int i4 = iArr[1];
            return i2 >= i4 && i2 <= view.getMeasuredHeight() + i4 && i >= i3 && i <= view.getMeasuredWidth() + i3;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    @SafeProtector
    private native void removeDynamicView();

    private native void removeDynamicXmlView();

    private native void restAllChildViews(View view);

    private void restAllChildViewsByName(String str, View view) {
        AuthUIConfig authUIConfig;
        TextView textView;
        int iMakeTextSizeSpec;
        AuthUIConfig authUIConfig2;
        Button button;
        int iMakeTextSizeSpec2;
        AuthUIConfig authUIConfig3;
        TextView textView2;
        int iMakeTextSizeSpec3;
        try {
            if (!(view instanceof ViewGroup)) {
                if (view instanceof TextView) {
                    float textSize = ((TextView) view).getTextSize();
                    float fA = textSize / a.a();
                    if (textSize == 0.0f) {
                        return;
                    }
                    int iG = this.mUIManager.g(str);
                    if (iG != 0) {
                        authUIConfig = this.mUIConfig;
                        textView = (TextView) view;
                        iMakeTextSizeSpec = makeTextSizeSpec(iG, 1073741824);
                    } else {
                        int iPx2dp = px2dp(fA);
                        this.mUIManager.a(str, iPx2dp);
                        authUIConfig = this.mUIConfig;
                        textView = (TextView) view;
                        iMakeTextSizeSpec = makeTextSizeSpec(iPx2dp, 1073741824);
                    }
                } else {
                    if (!(view instanceof Button)) {
                        return;
                    }
                    float textSize2 = ((Button) view).getTextSize();
                    float fA2 = textSize2 / a.a();
                    if (textSize2 == 0.0f) {
                        return;
                    }
                    int iG2 = this.mUIManager.g(str);
                    if (iG2 != 0) {
                        authUIConfig = this.mUIConfig;
                        textView = (Button) view;
                        iMakeTextSizeSpec = makeTextSizeSpec(iG2, 1073741824);
                    } else {
                        int iPx2dp2 = px2dp(fA2);
                        this.mUIManager.a(str, iPx2dp2);
                        authUIConfig = this.mUIConfig;
                        textView = (Button) view;
                        iMakeTextSizeSpec = makeTextSizeSpec(iPx2dp2, 1073741824);
                    }
                }
                authUIConfig.setTextSize(textView, iMakeTextSizeSpec);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof TextView) {
                    float textSize3 = ((TextView) childAt).getTextSize();
                    float fA3 = textSize3 / a.a();
                    if (textSize3 != 0.0f) {
                        int iG3 = this.mUIManager.g(str);
                        if (iG3 != 0) {
                            authUIConfig3 = this.mUIConfig;
                            textView2 = (TextView) childAt;
                            iMakeTextSizeSpec3 = makeTextSizeSpec(iG3, 1073741824);
                        } else {
                            int iPx2dp3 = px2dp(fA3);
                            this.mUIManager.a(str, iPx2dp3);
                            authUIConfig3 = this.mUIConfig;
                            textView2 = (TextView) childAt;
                            iMakeTextSizeSpec3 = makeTextSizeSpec(iPx2dp3, 1073741824);
                        }
                        authUIConfig3.setTextSize(textView2, iMakeTextSizeSpec3);
                    }
                } else if (view instanceof Button) {
                    float textSize4 = ((Button) view).getTextSize();
                    float fA4 = textSize4 / a.a();
                    if (textSize4 != 0.0f) {
                        int iG4 = this.mUIManager.g(str);
                        if (iG4 != 0) {
                            authUIConfig2 = this.mUIConfig;
                            button = (Button) view;
                            iMakeTextSizeSpec2 = makeTextSizeSpec(iG4, 1073741824);
                        } else {
                            int iPx2dp4 = px2dp(fA4);
                            this.mUIManager.a(str, iPx2dp4);
                            authUIConfig2 = this.mUIConfig;
                            button = (Button) view;
                            iMakeTextSizeSpec2 = makeTextSizeSpec(iPx2dp4, 1073741824);
                        }
                        authUIConfig2.setTextSize(button, iMakeTextSizeSpec2);
                    }
                } else if (childAt instanceof ViewGroup) {
                    restAllChildViewsByName("" + childAt.getId(), childAt);
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void setBackground(View view, Drawable drawable) {
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void setDialogBackGroundAlpha(float f) {
        try {
            getWindow().setDimAmount(f);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private float transferCorner(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        try {
            return AppUtils.dp2px(this, f);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1.0f;
        }
    }

    public native void cancelPrivacyDialog();

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (this.mUIConfig.isTapPrivacyAlertMaskCloseAlert() && motionEvent.getAction() == 1 && !isTouchPointInView(this.mMainRelativeLayout, x, y)) {
                this.mUIManager.f(this.mVendorKey);
                finishAuthPage(true, ResultCode.CODE_AUTH_PRIVACY_CLOSE, ResultCode.MSG_AUTH_PRIVACY_CLOSE);
            }
            return super.dispatchTouchEvent(motionEvent);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public int getProtroColor(int i) {
        try {
            return this.mUIConfig.getPrivacyAlertContentColor() == 0 ? i : this.mUIConfig.getPrivacyAlertContentColor();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public int getUIManagerID() {
        try {
            return this.mUIManagerID;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public void hideLoadingDialog() {
        try {
            if (this.mUIConfig == null || this.mUIConfig.isHiddenLoading() || this.mUIManager == null || this.mProgressDialog == null || !this.mProgressDialog.isShowing()) {
                return;
            }
            this.mProgressDialog.dismiss();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public int makeTextSizeSpec(int i, int i2) {
        return (1073741823 & i) | (MODE_MASK & i2);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        try {
            this.mUIManager.f(this.mVendorKey);
            finishAuthPage(true, ResultCode.CODE_AUTH_PRIVACY_CLOSE, ResultCode.MSG_AUTH_PRIVACY_CLOSE);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected native void onCreate(Bundle bundle);

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            hideLoadingDialog();
            removeDynamicXmlView();
            removeDynamicView();
            if (this.mUIManager != null) {
                this.mUIManager.c((Activity) null);
            }
            this.mUIManager = null;
            this.mUIConfig = null;
            super.onDestroy();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(@NonNull Bundle bundle) {
        try {
            super.onRestoreInstanceState(bundle);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        try {
            bundle.putString(Constant.LOGIN_ACTIVITY_VENDOR_KEY, this.mVendorKey);
            bundle.putInt(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.mUIManagerID);
            super.onSaveInstanceState(bundle);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        try {
            Intent intent = getIntent();
            intent.putExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY, this.mVendorKey);
            intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.mUIManagerID);
            super.onStop();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public int px2dp(float f) {
        try {
            return (int) ((f / getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception unused) {
            return (int) f;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public void showLoadingDialog() {
        try {
            if (this.mUIConfig.isHiddenLoading()) {
                return;
            }
            this.mPnsLogger.a("PrivacyDialogActivity showLoadingDialog = ", String.valueOf(this.mProgressDialog), "; isShowLoadingDialog = true");
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new com.mobile.auth.x.a(this, this.mUIConfig);
                this.mProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.mobile.auth.gatewayauth.PrivacyDialogActivity.5
                    @Override // android.content.DialogInterface.OnShowListener
                    public void onShow(DialogInterface dialogInterface) {
                    }
                });
            }
            this.mProgressDialog.setCancelable(true);
            this.mProgressDialog.show();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
