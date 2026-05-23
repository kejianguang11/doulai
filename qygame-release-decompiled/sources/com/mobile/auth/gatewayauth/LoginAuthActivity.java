package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.utils.f;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.gatewayauth.utils.j;
import com.mobile.auth.gatewayauth.utils.k;
import com.mobile.auth.gatewayauth.utils.l;
import com.nirvana.tools.core.AppUtils;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.core.SupportJarUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public class LoginAuthActivity extends Activity {
    private static final int DP_MODE = 1073741824;
    public static final String EXIST = "exist";
    private static final int MODE_MASK = -1073741824;
    private static final int MODE_SHIFT = 30;
    public static final String STOP_LOADING = "stop_loading";
    private int keyboardHeight;
    private String mAccessCode;
    private String mBeginProtocol;
    private RelativeLayout mBodyDYVRL;
    private RelativeLayout mBodyRL;
    private RelativeLayout mLoginRL;
    private TextView mLoginTV;
    private ImageView mLogoIV;
    private RelativeLayout mMainRelativeLayout;
    private TextView mMaskNumberTV;
    private RelativeLayout mNumberDYVRL;
    private String mNumberPhone;
    private RelativeLayout mNumberRL;
    private com.mobile.auth.o.a mPnsLogger;
    private com.mobile.auth.x.a mProgressDialog;
    private String mProtocol;
    private FrameLayout mProtocolCbContainer;
    private RelativeLayout mProtocolRL;
    private CheckBox mProtocolSelectCB;
    private TextView mProtocolTV;
    private RelativeLayout mScrollView;
    private String mSlogan;
    private TextView mSloganTV;
    private TextView mSwitchTV;
    private RelativeLayout mTitleDYVRL;
    private RelativeLayout mTitleRL;
    private AuthUIConfig mUIConfig;
    private d mUIManager;
    private int mUIManagerID;
    private String mVendorClick;
    private String mVendorKey;
    private String mVendorProtocol;
    private View rootView;
    private int rootViewVisiableHeight;
    private long startTime;
    private boolean mIsDialog = false;
    private List<com.mobile.auth.gatewayauth.ui.b> mProtocolConfigs = new ArrayList(3);
    private boolean orientationIsLandscape = false;
    private float space = 0.0f;

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$10, reason: invalid class name */
    class AnonymousClass10 implements View.OnClickListener {
        AnonymousClass10() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                LoginAuthActivity.access$600(LoginAuthActivity.this).setClickable(false);
                boolean zAccess$1000 = LoginAuthActivity.access$1000(LoginAuthActivity.this);
                if (!zAccess$1000) {
                    LoginAuthActivity.access$1100(LoginAuthActivity.this).e("LoginAuthActivity errorCode = ", ResultCode.CODE_ERROR_PHONE_UNSAFE_FAIL, "; errorMsg = 页面非法修改");
                    LoginAuthActivity.access$600(LoginAuthActivity.this).setClickable(true);
                    if (k.a(LoginAuthActivity.this.getApplicationContext())) {
                        Toast.makeText(LoginAuthActivity.this.getApplicationContext(), "页面非法修改！", 1).show();
                        return;
                    }
                }
                if (LoginAuthActivity.access$300(LoginAuthActivity.this).isCheckboxHidden() || LoginAuthActivity.access$1200(LoginAuthActivity.this).isChecked()) {
                    LoginAuthActivity.access$700(LoginAuthActivity.this).a(LoginAuthActivity.access$800(LoginAuthActivity.this), true, zAccess$1000);
                    LoginAuthActivity.this.showLoadingDialog();
                    LoginAuthActivity.access$1100(LoginAuthActivity.this).a("LoginAuthActivity", "; PhoneNumberAuthHelper2 = ", String.valueOf(LoginAuthActivity.access$700(LoginAuthActivity.this)));
                    LoginAuthActivity.access$700(LoginAuthActivity.this).b(LoginAuthActivity.access$700(LoginAuthActivity.this).a());
                    return;
                }
                LoginAuthActivity.this.animateProtocolTV();
                LoginAuthActivity.this.animateCheckBox();
                if (!LoginAuthActivity.access$300(LoginAuthActivity.this).isLogBtnToastHidden()) {
                    Toast.makeText(LoginAuthActivity.this.getApplicationContext(), "请同意服务条款", 1).show();
                }
                LoginAuthActivity.access$700(LoginAuthActivity.this).a(LoginAuthActivity.access$800(LoginAuthActivity.this), false, zAccess$1000);
                if (LoginAuthActivity.access$300(LoginAuthActivity.this).isPrivacyAlertIsNeedShow()) {
                    LoginAuthActivity.access$1300(LoginAuthActivity.this, zAccess$1000);
                } else {
                    LoginAuthActivity.access$600(LoginAuthActivity.this).setClickable(true);
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$12, reason: invalid class name */
    class AnonymousClass12 implements View.OnClickListener {
        AnonymousClass12() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                LoginAuthActivity.access$700(LoginAuthActivity.this).d(LoginAuthActivity.access$800(LoginAuthActivity.this));
                LoginAuthActivity.access$900(LoginAuthActivity.this, true, Constant.CODE_ERROR_USER_SWITCH, "用户切换其他登录方式");
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$13, reason: invalid class name */
    class AnonymousClass13 implements View.OnClickListener {
        AnonymousClass13() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                LoginAuthActivity.access$1200(LoginAuthActivity.this).setChecked(!LoginAuthActivity.access$1200(LoginAuthActivity.this).isChecked());
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$2, reason: invalid class name */
    class AnonymousClass2 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass2() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            try {
                if (z) {
                    LoginAuthActivity.access$700(LoginAuthActivity.this).g(true);
                } else {
                    LoginAuthActivity.access$700(LoginAuthActivity.this).g(false);
                }
                LoginAuthActivity.access$600(LoginAuthActivity.this).setActivated(LoginAuthActivity.access$1200(LoginAuthActivity.this).isChecked());
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$4, reason: invalid class name */
    class AnonymousClass4 extends ClickableSpan {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;

        AnonymousClass4(String str, String str2, int i) {
            this.a = str;
            this.b = str2;
            this.c = i;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            try {
                LoginAuthActivity.access$700(LoginAuthActivity.this).a(LoginAuthActivity.access$800(LoginAuthActivity.this), this.a, this.b, false);
                LoginAuthActivity.access$700(LoginAuthActivity.this).a(this.a, this.b);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            try {
                super.updateDrawState(textPaint);
                textPaint.setColor(this.c);
                if (LoginAuthActivity.access$300(LoginAuthActivity.this).isProtocolNameUseUnderLine()) {
                    textPaint.setUnderlineText(true);
                } else {
                    textPaint.setUnderlineText(false);
                }
                if (LoginAuthActivity.access$300(LoginAuthActivity.this).getProtocolNameTypeface() != null) {
                    textPaint.setTypeface(LoginAuthActivity.access$300(LoginAuthActivity.this).getProtocolNameTypeface());
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$7, reason: invalid class name */
    class AnonymousClass7 implements View.OnClickListener {
        final /* synthetic */ LinkedHashMap a;
        final /* synthetic */ String b;

        AnonymousClass7(LinkedHashMap linkedHashMap, String str) {
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
                customInterface.onClick(LoginAuthActivity.this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$8, reason: invalid class name */
    class AnonymousClass8 implements View.OnClickListener {
        final /* synthetic */ LinkedHashMap a;
        final /* synthetic */ String b;

        AnonymousClass8(LinkedHashMap linkedHashMap, String str) {
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
                customInterface.onClick(LoginAuthActivity.this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.mobile.auth.gatewayauth.LoginAuthActivity$9, reason: invalid class name */
    class AnonymousClass9 implements View.OnClickListener {
        AnonymousClass9() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                if (LoginAuthActivity.access$700(LoginAuthActivity.this).h()) {
                    LoginAuthActivity.access$700(LoginAuthActivity.this).b(LoginAuthActivity.access$800(LoginAuthActivity.this));
                } else {
                    LoginAuthActivity.access$700(LoginAuthActivity.this).a(LoginAuthActivity.access$800(LoginAuthActivity.this));
                    LoginAuthActivity.access$900(LoginAuthActivity.this, true, Constant.CODE_ERROR_USER_CANCEL, Constant.MSG_ERROR_USER_CANCEL);
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        }
    }

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    static /* synthetic */ View access$000(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.rootView;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ int access$100(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.rootViewVisiableHeight;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    static /* synthetic */ boolean access$1000(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.checkAuthPageUILegal();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    static /* synthetic */ int access$102(LoginAuthActivity loginAuthActivity, int i) {
        try {
            loginAuthActivity.rootViewVisiableHeight = i;
            return i;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    static /* synthetic */ com.mobile.auth.o.a access$1100(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mPnsLogger;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ CheckBox access$1200(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mProtocolSelectCB;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ void access$1300(LoginAuthActivity loginAuthActivity, boolean z) {
        try {
            loginAuthActivity.showPrivacyDialog(z);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    static /* synthetic */ void access$1400(LoginAuthActivity loginAuthActivity) {
        try {
            loginAuthActivity.changeStatueBarColor();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    static /* synthetic */ int access$200(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.keyboardHeight;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    static /* synthetic */ int access$202(LoginAuthActivity loginAuthActivity, int i) {
        try {
            loginAuthActivity.keyboardHeight = i;
            return i;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    static /* synthetic */ AuthUIConfig access$300(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mUIConfig;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ RelativeLayout access$400(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mMainRelativeLayout;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ float access$500(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.space;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1.0f;
        }
    }

    static /* synthetic */ float access$502(LoginAuthActivity loginAuthActivity, float f) {
        try {
            loginAuthActivity.space = f;
            return f;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1.0f;
        }
    }

    static /* synthetic */ RelativeLayout access$600(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mLoginRL;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ d access$700(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mUIManager;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ String access$800(LoginAuthActivity loginAuthActivity) {
        try {
            return loginAuthActivity.mVendorKey;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ void access$900(LoginAuthActivity loginAuthActivity, boolean z, String str, String str2) {
        try {
            loginAuthActivity.finishAuthPage(z, str, str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void changeStatueBarColor() {
        try {
            String str = j.a(Long.valueOf((long) (this.mUIConfig.getPrivacyAlertMaskAlpha() * 255.0f)), 2) + j.a(this.mUIConfig.getPrivacyAlertMaskColor()).substring(2);
            d.a(this.mUIConfig, Color.parseColor("#" + str), this);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private boolean checkAuthPageUILegal() {
        try {
            if (l.a(this.mProtocolTV) || l.a(this.mLoginTV) || l.a(this.mMaskNumberTV) || this.mLoginTV == null || l.a(this.mLoginTV.getCurrentTextColor()) || this.mProtocolTV == null || l.a(this.mProtocolTV.getCurrentTextColor()) || this.mMaskNumberTV == null) {
                return false;
            }
            return !l.a(this.mMaskNumberTV.getCurrentTextColor());
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    private SpannableString dealProtocol(String str, List<com.mobile.auth.gatewayauth.ui.b> list) {
        String str2;
        String str3;
        int protocolOneColor;
        try {
            SpannableString spannableString = new SpannableString(str);
            if (l.a(this.mUIConfig.getProtocolOwnColor())) {
                str2 = this.mVendorProtocol;
                str3 = this.mVendorClick;
                protocolOneColor = this.mUIConfig.getProtocolOneColor();
            } else {
                str2 = this.mVendorProtocol;
                str3 = this.mVendorClick;
                protocolOneColor = this.mUIConfig.getProtocolOwnColor();
            }
            ClickableSpan vendorProtocol = getVendorProtocol(str2, str3, protocolOneColor);
            for (com.mobile.auth.gatewayauth.ui.b bVar : list) {
                ClickableSpan protocol = getProtocol(bVar.b(), bVar.c(), bVar.d());
                int iIndexOf = str.indexOf(bVar.b());
                spannableString.setSpan(protocol, iIndexOf, bVar.b().length() + iIndexOf, 34);
            }
            spannableString.setSpan(vendorProtocol, str.indexOf(this.mVendorProtocol), str.indexOf(this.mVendorProtocol) + this.mVendorProtocol.length(), 34);
            if (this.mUIManager.g() && !this.mUIConfig.isCheckboxHidden()) {
                spannableString.setSpan(new ClickableSpan() { // from class: com.mobile.auth.gatewayauth.LoginAuthActivity.5
                    @Override // android.text.style.ClickableSpan
                    public void onClick(@NonNull View view) {
                        try {
                            LoginAuthActivity.access$1200(LoginAuthActivity.this).setChecked(!LoginAuthActivity.access$1200(LoginAuthActivity.this).isChecked());
                        } catch (Throwable th) {
                            ExceptionProcessor.processException(th);
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        try {
                            super.updateDrawState(textPaint);
                            textPaint.setUnderlineText(false);
                            textPaint.setColor(LoginAuthActivity.access$300(LoginAuthActivity.this).getProtocolColor());
                        } catch (Throwable th) {
                            ExceptionProcessor.processException(th);
                        }
                    }
                }, str.indexOf(this.mBeginProtocol), str.indexOf(this.mBeginProtocol) + this.mBeginProtocol.length(), 34);
            }
            return spannableString;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private void finishAuthPage(boolean z, String str, String str2) {
        try {
            if (this.mUIManager != null) {
                this.mUIManager.a(z, str, str2);
            } else {
                this.mPnsLogger.e("Exception finish!");
                finish();
            }
            if (this.mUIConfig.getAuthPageActOut() == null || this.mUIConfig.getActivityIn() == null) {
                return;
            }
            overridePendingTransition(AppUtils.getAnimResID(this, this.mUIConfig.getAuthPageActOut()), AppUtils.getAnimResID(this, this.mUIConfig.getActivityIn()));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private native ClickableSpan getProtocol(String str, String str2, int i);

    private int getResId(String str) {
        try {
            return getResources().getIdentifier(str, "anim", getPackageName());
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    private int getStatusBarHeight(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    private ClickableSpan getVendorProtocol(final String str, final String str2, final int i) {
        try {
            return new ClickableSpan() { // from class: com.mobile.auth.gatewayauth.LoginAuthActivity.3
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    try {
                        LoginAuthActivity.access$700(LoginAuthActivity.this).a(LoginAuthActivity.access$800(LoginAuthActivity.this), str, str2, true);
                        LoginAuthActivity.access$700(LoginAuthActivity.this).a(str, str2);
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    try {
                        super.updateDrawState(textPaint);
                        textPaint.setColor(i);
                        if (LoginAuthActivity.access$300(LoginAuthActivity.this).isProtocolNameUseUnderLine()) {
                            textPaint.setUnderlineText(true);
                        } else {
                            textPaint.setUnderlineText(false);
                        }
                        if (LoginAuthActivity.access$300(LoginAuthActivity.this).getProtocolNameTypeface() != null) {
                            textPaint.setTypeface(LoginAuthActivity.access$300(LoginAuthActivity.this).getProtocolNameTypeface());
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
    private native void initDynamicView();

    @SafeProtector
    private void initIntentData() {
        try {
            Intent intent = getIntent();
            this.mNumberPhone = intent.getStringExtra(Constant.LOGIN_ACTIVITY_NUMBER);
            this.mVendorKey = intent.getStringExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY);
            this.mAccessCode = intent.getStringExtra(Constant.LOGIN_ACTIVITY_ACCESS_CODE);
            this.startTime = intent.getLongExtra(Constant.START_TIME, 0L);
            this.mUIManagerID = intent.getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, 0);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @SafeProtector
    private native RelativeLayout initLoginRL();

    @SafeProtector
    private native ImageView initLogoView();

    @SafeProtector
    private native void initMaskNumberDynamicView();

    @SafeProtector
    private native RelativeLayout initNumberView();

    @SafeProtector
    private native RelativeLayout initProtocolView();

    @SafeProtector
    private native TextView initSloganView();

    @SafeProtector
    private native TextView initSwitchView();

    @SafeProtector
    private native RelativeLayout initTitleView();

    @SafeProtector
    private native void initView();

    @SafeProtector
    private void initXMLDynamicView() {
        try {
            try {
                ArrayList<AuthRegisterXmlConfig> arrayListU = this.mUIManager.u();
                this.mPnsLogger.a("initXMLDynamicView xmlViewMap = ", arrayListU.toString(), "; size = ", String.valueOf(arrayListU.size()));
                for (int i = 0; i < arrayListU.size(); i++) {
                    AuthRegisterXmlConfig authRegisterXmlConfig = arrayListU.get(i);
                    if (authRegisterXmlConfig != null) {
                        com.mobile.auth.gatewayauth.ui.a aVar = new com.mobile.auth.gatewayauth.ui.a(getApplicationContext(), authRegisterXmlConfig.getLayoutResId(), authRegisterXmlConfig.getViewDelegate());
                        restAllChildViews(aVar.b());
                        k.a(this.mMainRelativeLayout, aVar.b());
                    }
                }
                this.mUIManager.a(this.mVendorKey, this.startTime, l.b(this), true);
            } catch (Exception e) {
                this.mPnsLogger.e("initXMLDynamicView exception:", ExecutorManager.getErrorInfoFromException(e));
                this.mUIManager.a(this.mVendorKey, this.startTime, l.b(this), false);
                xmlLoadErrorCB();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

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

    private void removeDynamicXmlView() {
        ArrayList<AuthRegisterXmlConfig> arrayListU;
        try {
            if (this.mUIManager != null && (arrayListU = this.mUIManager.u()) != null && arrayListU.size() != 0) {
                Iterator<AuthRegisterXmlConfig> it = arrayListU.iterator();
                while (it.hasNext()) {
                    try {
                        View rootView = it.next().getViewDelegate().getRootView();
                        rootView.setOnClickListener(null);
                        this.mMainRelativeLayout.removeView(rootView);
                    } catch (Exception e) {
                        i.a(e);
                    }
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @SafeProtector
    private native void removeNumberView();

    private void restAllChildViews(View view) {
        try {
            if (!(view instanceof ViewGroup)) {
                if (view instanceof TextView) {
                    float textSize = ((TextView) view).getTextSize();
                    float fA = textSize / a.a();
                    if (textSize != 0.0f) {
                        this.mUIConfig.setTextSize((TextView) view, makeTextSizeSpec(px2dp(fA), 1073741824));
                        return;
                    }
                    return;
                }
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof TextView) {
                    float textSize2 = ((TextView) childAt).getTextSize();
                    float fA2 = textSize2 / a.a();
                    if (textSize2 != 0.0f) {
                        this.mUIConfig.setTextSize((TextView) childAt, makeTextSizeSpec(px2dp(fA2), 1073741824));
                    }
                } else if (childAt instanceof ViewGroup) {
                    restAllChildViews(childAt);
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

    private void showPrivacyDialog(boolean z) {
        try {
            this.mUIManager.b(this.mVendorKey, false, z);
            Intent intent = new Intent(this, (Class<?>) PrivacyDialogActivity.class);
            intent.putExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY, this.mVendorKey);
            intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.mUIManagerID);
            if (this.mUIConfig.getPrivacyAlertEntryAnimation() == null || this.mUIConfig.getPrivacyAlertExitAnimation() == null) {
                startActivityForResult(intent, 1);
            } else {
                String privacyAlertEntryAnimation = this.mUIConfig.getPrivacyAlertEntryAnimation();
                String privacyAlertExitAnimation = this.mUIConfig.getPrivacyAlertExitAnimation();
                if (!TextUtils.isEmpty(privacyAlertEntryAnimation) && !TextUtils.isEmpty(privacyAlertExitAnimation)) {
                    SupportJarUtils.startActivityForResult(this, intent, 1, privacyAlertEntryAnimation, privacyAlertExitAnimation);
                    ExecutorManager.getInstance().postMain(new Runnable() { // from class: com.mobile.auth.gatewayauth.LoginAuthActivity.11
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                LoginAuthActivity.access$1400(LoginAuthActivity.this);
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    }, AnimationUtils.loadAnimation(this, getResId(privacyAlertEntryAnimation)).getDuration());
                    return;
                }
                SupportJarUtils.startActivityForResult(this, intent, 1, null, null);
            }
            changeStatueBarColor();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void updateDialogWithKeyboard() {
        try {
            this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.mobile.auth.gatewayauth.LoginAuthActivity.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    try {
                        Rect rect = new Rect();
                        LoginAuthActivity.access$000(LoginAuthActivity.this).getWindowVisibleDisplayFrame(rect);
                        int iHeight = rect.height();
                        if (LoginAuthActivity.access$100(LoginAuthActivity.this) == 0) {
                            LoginAuthActivity.access$102(LoginAuthActivity.this, iHeight);
                            return;
                        }
                        if (LoginAuthActivity.access$100(LoginAuthActivity.this) == iHeight) {
                            return;
                        }
                        if (LoginAuthActivity.access$100(LoginAuthActivity.this) - iHeight > 200) {
                            LoginAuthActivity.access$202(LoginAuthActivity.this, LoginAuthActivity.access$100(LoginAuthActivity.this) - iHeight);
                            LoginAuthActivity.access$102(LoginAuthActivity.this, iHeight);
                            if (LoginAuthActivity.access$300(LoginAuthActivity.this).isDialogBottom()) {
                                LoginAuthActivity.access$400(LoginAuthActivity.this).setY(LoginAuthActivity.access$400(LoginAuthActivity.this).getY() - LoginAuthActivity.access$200(LoginAuthActivity.this));
                            } else {
                                float fC = f.c(LoginAuthActivity.this.getApplicationContext()) - (LoginAuthActivity.access$400(LoginAuthActivity.this).getY() + LoginAuthActivity.access$400(LoginAuthActivity.this).getHeight());
                                if (fC < LoginAuthActivity.access$200(LoginAuthActivity.this)) {
                                    LoginAuthActivity.access$502(LoginAuthActivity.this, LoginAuthActivity.access$200(LoginAuthActivity.this) - fC);
                                    LoginAuthActivity.access$400(LoginAuthActivity.this).setY(LoginAuthActivity.access$400(LoginAuthActivity.this).getY() - LoginAuthActivity.access$500(LoginAuthActivity.this));
                                }
                            }
                        }
                        if (iHeight - LoginAuthActivity.access$100(LoginAuthActivity.this) > 200) {
                            if (LoginAuthActivity.access$300(LoginAuthActivity.this).isDialogBottom()) {
                                LoginAuthActivity.access$400(LoginAuthActivity.this).setY(LoginAuthActivity.access$400(LoginAuthActivity.this).getY() + LoginAuthActivity.access$200(LoginAuthActivity.this));
                            } else if (LoginAuthActivity.access$500(LoginAuthActivity.this) != 0.0f) {
                                LoginAuthActivity.access$400(LoginAuthActivity.this).setY(LoginAuthActivity.access$400(LoginAuthActivity.this).getY() + LoginAuthActivity.access$500(LoginAuthActivity.this));
                                LoginAuthActivity.access$502(LoginAuthActivity.this, 0.0f);
                            }
                            LoginAuthActivity.access$102(LoginAuthActivity.this, iHeight);
                        }
                    } catch (Throwable th) {
                        ExceptionProcessor.processException(th);
                    }
                }
            });
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private void xmlLoadErrorCB() {
        try {
            if (this.mUIManager != null) {
                this.mUIManager.q();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void animateCheckBox() {
        try {
            if (TextUtils.isEmpty(this.mUIConfig.getCheckBoxShakePath()) || this.mUIConfig.isCheckboxHidden()) {
                return;
            }
            this.mProtocolSelectCB.startAnimation(AnimationUtils.loadAnimation(this, AppUtils.getAnimResID(this, this.mUIConfig.getCheckBoxShakePath())));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void animateProtocolTV() {
        try {
            if (TextUtils.isEmpty(this.mUIConfig.getProtocolShakePath())) {
                return;
            }
            this.mProtocolTV.startAnimation(AnimationUtils.loadAnimation(this, AppUtils.getAnimResID(this, this.mUIConfig.getProtocolShakePath())));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            if (this.mIsDialog && this.mUIConfig.isTapAuthPageMaskClosePage() && motionEvent.getAction() == 1 && !isTouchPointInView(this.mMainRelativeLayout, rawX, rawY)) {
                this.mUIManager.a(this.mVendorKey);
                finishAuthPage(true, Constant.CODE_ERROR_USER_CANCEL, Constant.MSG_ERROR_USER_CANCEL);
            }
            return super.dispatchTouchEvent(motionEvent);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
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
    @SafeProtector
    protected native void onActivityResult(int i, int i2, Intent intent);

    @Override // android.app.Activity
    public void onBackPressed() {
        try {
            if (this.mUIManager.h()) {
                this.mUIManager.c(this.mVendorKey);
            } else if (!this.mUIManager.e()) {
                super.onBackPressed();
                finishAuthPage(true, Constant.CODE_ERROR_USER_CANCEL, Constant.MSG_ERROR_USER_CANCEL);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected native void onCreate(Bundle bundle);

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            if (this.mUIManager != null) {
                this.mUIManager.E();
            }
            hideLoadingDialog();
            removeDynamicView();
            removeNumberView();
            removeDynamicXmlView();
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
            this.mUIManager.d();
            this.mUIManager.a((Activity) this);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        try {
            super.onResume();
            this.mLoginRL.setClickable(true);
            d.a(this.mUIConfig, this.mUIConfig.getStatusBarColor(), this);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        try {
            bundle.putString(Constant.LOGIN_ACTIVITY_NUMBER, this.mNumberPhone);
            bundle.putString(Constant.LOGIN_ACTIVITY_VENDOR_KEY, this.mVendorKey);
            bundle.putString(Constant.LOGIN_ACTIVITY_ACCESS_CODE, this.mAccessCode);
            bundle.putLong(Constant.START_TIME, this.startTime);
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
            intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, this.mNumberPhone);
            intent.putExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY, this.mVendorKey);
            intent.putExtra(Constant.LOGIN_ACTIVITY_ACCESS_CODE, this.mAccessCode);
            intent.putExtra(Constant.START_TIME, this.startTime);
            intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.mUIManagerID);
            super.onStop();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void openUserPage(Class<?> cls, int i, int i2) {
        try {
            Intent intent = new Intent(this, cls);
            if (i2 > 0) {
                intent.addFlags(i2);
            }
            startActivityForResult(intent, i);
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

    public boolean queryCheckBoxIsChecked() {
        try {
            if (this.mUIConfig.isCheckboxHidden()) {
                return true;
            }
            return this.mProtocolSelectCB.isChecked();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public void setProtocolChecked(boolean z) {
        try {
            if (this.mProtocolSelectCB != null) {
                this.mProtocolSelectCB.setChecked(z);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public void showLoadingDialog() {
        try {
            if (this.mUIConfig.isHiddenLoading()) {
                return;
            }
            this.mPnsLogger.a("LoginAuthActivity showLoadingDialog = ", String.valueOf(this.mProgressDialog), "; isShowLoadingDialog = true");
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new com.mobile.auth.x.a(this, this.mUIConfig);
                this.mProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.mobile.auth.gatewayauth.LoginAuthActivity.6
                    @Override // android.content.DialogInterface.OnShowListener
                    public void onShow(DialogInterface dialogInterface) {
                        try {
                            LoginAuthActivity.access$600(LoginAuthActivity.this).setClickable(true);
                        } catch (Throwable th) {
                            ExceptionProcessor.processException(th);
                        }
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
