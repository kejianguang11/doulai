package com.cmic.sso.sdk.view;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cmic.sso.sdk.view.f;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.l.n;
import com.mobile.auth.l.q;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LoginAuthActivity extends Activity implements View.OnClickListener {
    protected static final String a = "LoginAuthActivity";
    private com.cmic.sso.sdk.view.a A;
    private int B;
    private int C;
    private boolean D;
    private Dialog E;
    private Handler b;
    private Context c;
    private RelativeLayout d;
    private h e;
    private h f;
    private h g;
    private h h;
    private h i;
    private ArrayList<h> j;
    private ArrayList<String> k;
    private String[] l;
    private com.cmic.sso.sdk.a m;
    private com.mobile.auth.e.c n;
    private CheckBox p;
    private RelativeLayout q;
    private RelativeLayout r;
    private com.mobile.auth.e.b v;
    private RelativeLayout x;
    private String y;
    private String z;
    private String o = "";
    private long s = 0;
    private int t = 0;
    private a u = null;
    private boolean w = true;

    private static class a extends Handler {
        WeakReference<LoginAuthActivity> a;

        a(LoginAuthActivity loginAuthActivity) {
            this.a = new WeakReference<>(loginAuthActivity);
        }

        private void a(Message message) {
            LoginAuthActivity loginAuthActivity = this.a.get();
            if (loginAuthActivity == null || message.what != 1) {
                return;
            }
            loginAuthActivity.c();
            loginAuthActivity.k();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                a(message);
            } catch (Exception e) {
                com.cmic.sso.sdk.d.c.b.add(e);
                e.printStackTrace();
            }
        }
    }

    private static class b extends n.a {
        WeakReference<LoginAuthActivity> a;
        WeakReference<c> b;

        protected b(LoginAuthActivity loginAuthActivity, c cVar) {
            this.a = new WeakReference<>(loginAuthActivity);
            this.b = new WeakReference<>(cVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean b() {
            c cVar = this.b.get();
            if (this.a.get() == null || cVar == null) {
                return false;
            }
            return cVar.a(false);
        }

        @Override // com.mobile.auth.l.n.a
        protected void a() throws UnsupportedEncodingException {
            final LoginAuthActivity loginAuthActivity = this.a.get();
            loginAuthActivity.m.a("logintype", 1);
            com.mobile.auth.l.h.a(true, false);
            loginAuthActivity.n.b(loginAuthActivity.m, new com.mobile.auth.e.d() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.b.1
                @Override // com.mobile.auth.e.d
                public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
                    if (b.this.b()) {
                        long jB = aVar.b("loginTime", 0L);
                        String strB = aVar.b("phonescrip");
                        if (jB != 0) {
                            aVar.a("loginTime", System.currentTimeMillis() - jB);
                        }
                        if (!"103000".equals(str) || TextUtils.isEmpty(strB)) {
                            loginAuthActivity.w = false;
                            com.cmic.sso.sdk.d.a.a("authClickFailed");
                        } else {
                            com.cmic.sso.sdk.d.a.a("authClickSuccess");
                            loginAuthActivity.w = true;
                        }
                        loginAuthActivity.a(str, str2, aVar, jSONObject);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loginAuthActivity.u.sendEmptyMessage(1);
                    }
                }
            });
        }
    }

    private class c implements Runnable {
        private com.cmic.sso.sdk.a b;
        private boolean c;

        c(com.cmic.sso.sdk.a aVar) {
            this.b = aVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean a(boolean z) {
            boolean z2;
            z2 = this.c;
            this.c = z;
            return !z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (a(true)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("resultCode", "102507");
                    jSONObject.put("resultString", "请求超时");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LoginAuthActivity.this.w = false;
                com.cmic.sso.sdk.d.a.a("authClickFailed");
                LoginAuthActivity.this.u.sendEmptyMessage(1);
                long jB = this.b.b("loginTime", 0L);
                if (jB != 0) {
                    this.b.a("loginTime", System.currentTimeMillis() - jB);
                }
                LoginAuthActivity.this.a("102507", "请求超时", this.b, jSONObject);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        com.mobile.auth.e.a aVarA;
        try {
            this.b.removeCallbacksAndMessages(null);
            if ("103000".equals(str)) {
                if (com.mobile.auth.e.a.a(this) == null || com.mobile.auth.l.e.c(aVar.b("traceId")) == null) {
                    return;
                }
                aVar.a("keepListener", true);
                aVarA = com.mobile.auth.e.a.a(this);
            } else {
                if ("200020".equals(str)) {
                    if (com.mobile.auth.e.a.a(this) != null) {
                        if (com.mobile.auth.l.e.c(aVar.b("traceId")) != null) {
                            com.mobile.auth.e.a.a(this).a(str, str2, aVar, jSONObject);
                        }
                        a();
                        return;
                    }
                    return;
                }
                aVar.a("keepListener", true);
                aVarA = com.mobile.auth.e.a.a(this);
            }
            aVarA.a(str, str2, aVar, jSONObject);
        } catch (Exception e) {
            com.mobile.auth.l.c.a(a, "CallbackResult:未知错误");
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        try {
            com.cmic.sso.sdk.d.a.a("authPageOut");
            a("200020", "登录页面关闭", this.m, null);
        } catch (Exception e) {
            com.cmic.sso.sdk.d.c.b.add(e);
            e.printStackTrace();
        }
    }

    private void d() {
        String str;
        this.m = com.mobile.auth.l.e.d(getIntent().getStringExtra("traceId"));
        if (this.m == null) {
            this.m = new com.cmic.sso.sdk.a(0);
        }
        this.v = com.mobile.auth.l.e.c(this.m.b("traceId", ""));
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.b = new Handler(getMainLooper());
        this.u = new a(this);
        this.o = this.m.b("securityphone");
        com.mobile.auth.l.c.b(a, "mSecurityPhone value is " + this.o);
        String strB = this.m.b("operatortype", "");
        com.mobile.auth.l.c.b(a, "operator value is " + strB);
        this.l = this.A.ap() == 1 ? com.cmic.sso.sdk.c.b : this.A.ap() == 2 ? com.cmic.sso.sdk.c.c : com.cmic.sso.sdk.c.a;
        if (strB.equals("1")) {
            this.y = this.l[0];
            str = "http://wap.cmpassport.com/resources/html/contract.html";
        } else if (strB.equals(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM)) {
            this.y = this.l[1];
            str = Constant.CTCC_PROTOCOL_URL;
        } else {
            this.y = this.l[2];
            str = Constant.CUCC_PROTOCOL_URL;
        }
        this.e = new h(this.c, R.style.Theme.Translucent.NoTitleBar, this.y, str);
        this.e.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                    LoginAuthActivity.this.e.b();
                }
                return true;
            }
        });
        this.j = new ArrayList<>();
        this.k = new ArrayList<>();
        if (!TextUtils.isEmpty(this.A.N())) {
            this.f = new h(this.c, R.style.Theme.Translucent.NoTitleBar, this.A.M(), this.A.N());
            this.f.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.2
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.f.b();
                    }
                    return true;
                }
            });
            this.j.add(this.f);
            this.k.add(this.A.M());
        }
        if (!TextUtils.isEmpty(this.A.P())) {
            this.g = new h(this.c, R.style.Theme.Translucent.NoTitleBar, this.A.O(), this.A.P());
            this.g.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.3
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.g.b();
                    }
                    return true;
                }
            });
            this.j.add(this.g);
            this.k.add(this.A.O());
        }
        if (!TextUtils.isEmpty(this.A.R())) {
            this.h = new h(this.c, R.style.Theme.Translucent.NoTitleBar, this.A.Q(), this.A.R());
            this.h.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.4
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.h.b();
                    }
                    return true;
                }
            });
            this.j.add(this.h);
            this.k.add(this.A.Q());
        }
        if (!TextUtils.isEmpty(this.A.T())) {
            this.i = new h(this.c, R.style.Theme.Translucent.NoTitleBar, this.A.S(), this.A.T());
            this.i.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.5
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.i.b();
                    }
                    return true;
                }
            });
            this.j.add(this.i);
            this.k.add(this.A.S());
        }
        j();
        if (this.A.ad()) {
            for (int i = 0; i < this.k.size(); i++) {
                String str2 = String.format("《%s》", this.k.get(i));
                this.z = this.z.replaceFirst(this.k.get(i), str2);
                this.k.set(i, str2);
            }
        }
        f.a().a(new f.a() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.6
            @Override // com.cmic.sso.sdk.view.f.a
            public void a() {
                LoginAuthActivity.this.b.removeCallbacksAndMessages(null);
                if (LoginAuthActivity.this.e != null && LoginAuthActivity.this.e.isShowing()) {
                    LoginAuthActivity.this.e.dismiss();
                }
                if (LoginAuthActivity.this.f != null && LoginAuthActivity.this.f.isShowing()) {
                    LoginAuthActivity.this.f.dismiss();
                }
                LoginAuthActivity.this.a(true);
            }
        });
    }

    private void e() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.r.getLayoutParams();
        if (this.A.p() > 0 || this.A.q() < 0) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.r.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            com.mobile.auth.l.c.b(a, "mPhoneLayout.getMeasuredHeight()=" + this.r.getMeasuredHeight());
            if (this.A.p() <= 0 || (this.B - this.r.getMeasuredHeight()) - i.a(this.c, this.A.p()) <= 0) {
                layoutParams.addRule(12, -1);
            } else {
                com.mobile.auth.l.c.b(a, "numberField_top");
                layoutParams.addRule(10, -1);
                layoutParams.setMargins(0, i.a(this.c, this.A.p()), 0, 0);
            }
        } else if (this.A.q() <= 0 || (this.B - this.r.getMeasuredHeight()) - i.a(this.c, this.A.q()) <= 0) {
            layoutParams.addRule(10, -1);
        } else {
            com.mobile.auth.l.c.b(a, "numberField_bottom");
            layoutParams.addRule(12, -1);
            layoutParams.setMargins(0, 0, 0, i.a(this.c, this.A.q()));
        }
        this.r.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.d.getLayoutParams();
        int iMax = Math.max(this.A.y(), 0);
        int iMax2 = Math.max(this.A.z(), 0);
        if (this.A.A() > 0 || this.A.B() < 0) {
            if (this.A.A() <= 0 || this.B - i.a(this.c, this.A.x() + this.A.A()) <= 0) {
                layoutParams2.addRule(12, -1);
                layoutParams2.setMargins(i.a(this.c, iMax), 0, i.a(this.c, iMax2), 0);
            } else {
                com.mobile.auth.l.c.b(a, "logBtn_top");
                layoutParams2.addRule(10, -1);
                layoutParams2.setMargins(i.a(this.c, iMax), i.a(this.c, this.A.A()), i.a(this.c, iMax2), 0);
            }
        } else if (this.A.B() <= 0 || this.B - i.a(this.c, this.A.x() + this.A.B()) <= 0) {
            layoutParams2.addRule(10, -1);
            layoutParams2.setMargins(i.a(this.c, iMax), 0, i.a(this.c, iMax2), 0);
        } else {
            com.mobile.auth.l.c.b(a, "logBtn_bottom");
            layoutParams2.addRule(12, -1);
            layoutParams2.setMargins(i.a(this.c, iMax), 0, i.a(this.c, iMax2), i.a(this.c, this.A.B()));
        }
        this.d.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.q.getLayoutParams();
        int iZ = this.A.Z() >= 0 ? this.A.I() > 30 ? this.A.Z() : this.A.Z() - (30 - this.A.I()) : this.A.I() > 30 ? 0 : -(30 - this.A.I());
        int iMax3 = Math.max(this.A.aa(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.q.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
        if (this.A.ab() > 0 || this.A.ac() < 0) {
            if (this.A.ab() <= 0 || (this.B - this.q.getMeasuredHeight()) - i.a(this.c, this.A.ab()) <= 0) {
                com.mobile.auth.l.c.b(a, "privacy_bottom=" + iZ);
                layoutParams3.addRule(12, -1);
                layoutParams3.setMargins(i.a(this.c, (float) iZ), 0, i.a(this.c, (float) iMax3), 0);
            } else {
                com.mobile.auth.l.c.b(a, "privacy_top = " + this.q.getMeasuredHeight());
                layoutParams3.addRule(10, -1);
                layoutParams3.setMargins(i.a(this.c, (float) iZ), i.a(this.c, (float) this.A.ab()), i.a(this.c, (float) iMax3), 0);
            }
        } else if (this.A.ac() <= 0 || (this.B - this.q.getMeasuredHeight()) - i.a(this.c, this.A.ac()) <= 0) {
            layoutParams3.addRule(10, -1);
            layoutParams3.setMargins(i.a(this.c, iZ), 0, i.a(this.c, iMax3), 0);
            com.mobile.auth.l.c.b(a, "privacy_top");
        } else {
            com.mobile.auth.l.c.b(a, "privacy_bottom=" + this.q.getMeasuredHeight());
            layoutParams3.addRule(12, -1);
            layoutParams3.setMargins(i.a(this.c, (float) iZ), 0, i.a(this.c, (float) iMax3), i.a(this.c, (float) this.A.ac()));
        }
        this.q.setLayoutParams(layoutParams3);
    }

    private void f() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
            getWindow().addFlags(134217728);
            if (this.A.a() != 0) {
                getWindow().addFlags(Integer.MIN_VALUE);
                getWindow().clearFlags(67108864);
                getWindow().setStatusBarColor(this.A.a());
                getWindow().setNavigationBarColor(this.A.a());
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.A.b()) {
                getWindow().getDecorView().setSystemUiVisibility(8192);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        View viewC = this.A.c();
        if (viewC != null) {
            ViewParent parent = viewC.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(viewC);
            }
            relativeLayout.addView(viewC);
        } else if (this.A.d() != -1) {
            getLayoutInflater().inflate(this.A.d(), relativeLayout);
        }
        setContentView(relativeLayout);
        int requestedOrientation = getRequestedOrientation();
        this.B = i.b(this.c);
        this.C = i.a(this.c);
        boolean z = true;
        if ((requestedOrientation == 1 && this.C > this.B) || (requestedOrientation == 0 && this.C < this.B)) {
            int i = this.C;
            this.C = this.B;
            this.B = i;
        }
        com.mobile.auth.l.c.b(a, "orientation = " + requestedOrientation + "--screenWidth = " + this.C + "--screenHeight = " + this.B);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (this.A.aj() != 0) {
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            getWindowManager().getDefaultDisplay().getSize(new Point());
            attributes.width = i.a(this.c, this.A.aj());
            attributes.height = i.a(this.c, this.A.ak());
            this.C = attributes.width;
            this.B = attributes.height;
            attributes.x = i.a(this.c, this.A.al());
            if (this.A.an() == 1) {
                getWindow().setGravity(80);
            } else {
                attributes.y = i.a(this.c, this.A.am());
            }
            getWindow().setAttributes(attributes);
        }
        relativeLayout.setFitsSystemWindows(this.A.aq());
        relativeLayout.setClipToPadding(true);
        try {
            g();
            relativeLayout.addView(this.r);
            relativeLayout.addView(h());
            relativeLayout.addView(i());
            e();
            this.d.setOnClickListener(this);
            this.x.setOnClickListener(this);
            this.p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.7
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    CheckBox checkBox;
                    LoginAuthActivity loginAuthActivity;
                    String str;
                    boolean z3 = true;
                    if (z2) {
                        LoginAuthActivity.this.d.setEnabled(true);
                        try {
                            LoginAuthActivity.this.p.setBackgroundResource(g.b(LoginAuthActivity.this, LoginAuthActivity.this.A.G()));
                            return;
                        } catch (Exception unused) {
                            checkBox = LoginAuthActivity.this.p;
                            loginAuthActivity = LoginAuthActivity.this;
                            str = "umcsdk_check_image";
                        }
                    } else {
                        RelativeLayout relativeLayout2 = LoginAuthActivity.this.d;
                        if (LoginAuthActivity.this.A.F() == null && TextUtils.isEmpty(LoginAuthActivity.this.A.C())) {
                            z3 = false;
                        }
                        relativeLayout2.setEnabled(z3);
                        try {
                            LoginAuthActivity.this.p.setBackgroundResource(g.b(LoginAuthActivity.this, LoginAuthActivity.this.A.H()));
                            return;
                        } catch (Exception unused2) {
                            checkBox = LoginAuthActivity.this.p;
                            loginAuthActivity = LoginAuthActivity.this;
                            str = "umcsdk_uncheck_image";
                        }
                    }
                    checkBox.setBackgroundResource(g.b(loginAuthActivity, str));
                }
            });
            k();
            try {
                if (this.A.K()) {
                    this.p.setChecked(true);
                    this.p.setBackgroundResource(g.b(this, this.A.G()));
                    this.d.setEnabled(true);
                    return;
                }
                this.p.setChecked(false);
                RelativeLayout relativeLayout2 = this.d;
                if (this.A.F() == null && TextUtils.isEmpty(this.A.C())) {
                    z = false;
                }
                relativeLayout2.setEnabled(z);
                this.p.setBackgroundResource(g.b(this, this.A.H()));
            } catch (Exception unused) {
                this.p.setChecked(false);
            }
        } catch (Exception e) {
            com.cmic.sso.sdk.d.c.b.add(e);
            e.printStackTrace();
            com.mobile.auth.l.c.a(a, e.toString());
            a("200040", "UI资源加载异常", this.m, null);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(1:4)(11:(2:7|(1:9)(1:10))|11|24|12|15|(1:17)|18|26|19|22|23)|5|11|24|12|15|(0)|18|26|19|22|23) */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x006b, code lost:
    
        r0.setTextSize(2, 18.0f);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0096, code lost:
    
        r0.setTextColor(-13421773);
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void g() {
        int i;
        this.r = new RelativeLayout(this);
        this.r.setId(13107);
        this.r.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        TextView textView = new TextView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        textView.setGravity(15);
        int iO = this.A.o();
        if (iO != 0) {
            if (iO > 0) {
                float f = iO;
                if ((this.C - textView.getWidth()) - i.a(this.c, f) > 0) {
                    layoutParams.setMargins(i.a(this.c, f), 0, 0, 0);
                } else {
                    com.mobile.auth.l.c.b(a, "RelativeLayout.ALIGN_PARENT_RIGHT");
                    i = 11;
                }
            }
            textView.setTextSize(2, this.A.l());
            textView.setText(this.o);
            if (this.A.m()) {
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            }
            textView.setId(30583);
            this.r.addView(textView, layoutParams);
            textView.setTextColor(this.A.n());
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.r.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            com.mobile.auth.l.c.b(a, "mPhoneLayout.getMeasuredHeight()=" + this.r.getMeasuredHeight());
        }
        i = 13;
        layoutParams.addRule(i);
        textView.setTextSize(2, this.A.l());
        textView.setText(this.o);
        if (this.A.m()) {
        }
        textView.setId(30583);
        this.r.addView(textView, layoutParams);
        textView.setTextColor(this.A.n());
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.r.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
        com.mobile.auth.l.c.b(a, "mPhoneLayout.getMeasuredHeight()=" + this.r.getMeasuredHeight());
    }

    private RelativeLayout h() {
        this.d = new RelativeLayout(this);
        this.d.setId(17476);
        this.d.setLayoutParams(new RelativeLayout.LayoutParams(i.a(this.c, this.A.w()), i.a(this.c, this.A.x())));
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.A.s());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        textView.setLayoutParams(layoutParams);
        if (this.A.t()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        this.d.addView(textView);
        textView.setText(this.A.r());
        try {
            textView.setTextColor(this.A.u());
        } catch (Exception unused) {
            textView.setTextColor(-1);
        }
        try {
            this.d.setBackgroundResource(g.b(this.c, this.A.v()));
        } catch (Exception e) {
            e.printStackTrace();
            this.d.setBackgroundResource(g.b(this.c, "umcsdk_login_btn_bg"));
        }
        return this.d;
    }

    private RelativeLayout i() {
        this.q = new RelativeLayout(this);
        this.q.setHorizontalGravity(1);
        this.q.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        int I = this.A.I();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i.a(this.c, Math.max(I, 30)), i.a(this.c, Math.max(this.A.J(), 30)));
        if (this.A.ae() == 1) {
            layoutParams.addRule(15, -1);
        }
        this.x = new RelativeLayout(this);
        this.x.setId(34952);
        this.x.setLayoutParams(layoutParams);
        this.p = new CheckBox(this);
        this.p.setChecked(false);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i.a(this.c, this.A.I()), i.a(this.c, this.A.J()));
        layoutParams2.setMargins(i.a(this.c, I > 30 ? 0.0f : 30 - I), 0, 0, 0);
        layoutParams2.addRule(11, -1);
        if (this.A.ae() == 1) {
            layoutParams2.addRule(15, -1);
        }
        this.p.setLayoutParams(layoutParams2);
        this.x.addView(this.p);
        this.q.addView(this.x);
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.A.U());
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.setMargins(i.a(this.c, 5.0f), 0, 0, i.a(this.c, 5.0f));
        layoutParams3.addRule(1, 34952);
        textView.setLayoutParams(layoutParams3);
        this.q.addView(textView);
        textView.setTextColor(this.A.W());
        textView.setText(i.a(this, this.z, this.y, this.e, this.j, this.k));
        textView.setLineSpacing(8.0f, 1.0f);
        textView.setIncludeFontPadding(false);
        if (this.A.V()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (this.A.Y()) {
            textView.setGravity(17);
        }
        textView.setHighlightColor(getResources().getColor(R.color.transparent));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        this.p.setButtonDrawable(new ColorDrawable());
        try {
            this.p.setBackgroundResource(g.b(this, this.A.H()));
        } catch (Exception unused) {
            this.p.setBackgroundResource(g.b(this, "umcsdk_uncheck_image"));
        }
        return this.q;
    }

    private String j() {
        this.z = this.A.L();
        if (this.A.ad()) {
            this.y = String.format("《%s》", this.y);
        }
        if (this.z.contains("$$运营商条款$$")) {
            this.z = this.z.replace("$$运营商条款$$", this.y);
        }
        return this.z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.d.setClickable(true);
        this.p.setClickable(true);
    }

    private void l() {
        this.d.setClickable(false);
        this.p.setClickable(false);
    }

    private void m() {
        try {
            if (this.t >= 5) {
                Toast.makeText(this.c, "网络不稳定,请返回重试其他登录方式", 1).show();
                this.d.setClickable(true);
                return;
            }
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTrace) {
                com.mobile.auth.l.c.a("stack", stackTraceElement.getClassName());
                String className = stackTraceElement.getClassName();
                if (!TextUtils.isEmpty(className) && className.contains("com.cmic.sso.sdk.activity") && !sb.toString().contains(className)) {
                    sb.append(className);
                    sb.append(com.alipay.sdk.util.h.b);
                }
            }
            this.m.a("loginTime", System.currentTimeMillis());
            String strB = this.m.b("traceId", "");
            if (!TextUtils.isEmpty(strB) && com.mobile.auth.l.e.a(strB)) {
                String strC = q.c();
                this.m.a("traceId", strC);
                com.mobile.auth.l.e.a(strC, this.v);
            }
            b();
            l();
            c cVar = new c(this.m);
            this.b.postDelayed(cVar, com.mobile.auth.e.a.a(this).c());
            n.a(new b(this, cVar));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a() {
        this.b.removeCallbacksAndMessages(null);
        if (this.e != null && this.e.isShowing()) {
            this.e.dismiss();
        }
        if (this.f != null && this.f.isShowing()) {
            this.f.dismiss();
        }
        c();
        this.E = null;
        if (this.E != null && this.E.isShowing()) {
            this.E.dismiss();
        }
        this.q.clearAnimation();
        finish();
        if (this.A.ah() == null || this.A.ai() == null) {
            return;
        }
        overridePendingTransition(g.c(this, this.A.ai()), g.c(this, this.A.ah()));
    }

    public void b() {
        com.mobile.auth.l.c.a(a, "loginClickStart");
        try {
            this.D = true;
            if (this.A.E() != null) {
                this.A.E().a(this.c, null);
            } else {
                if (this.E != null) {
                    this.E.show();
                    return;
                }
                this.E = new AlertDialog.Builder(this).create();
                this.E.setCancelable(false);
                this.E.setCanceledOnTouchOutside(false);
                this.E.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.8
                    @Override // android.content.DialogInterface.OnKeyListener
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        return i == 4;
                    }
                });
                RelativeLayout relativeLayout = new RelativeLayout(this.E.getContext());
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                ImageView imageView = new ImageView(this.E.getContext());
                imageView.setImageResource(g.b(this.c, "dialog_loading"));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(80, 80);
                layoutParams.addRule(13, -1);
                relativeLayout.addView(imageView, layoutParams);
                if (this.E.getWindow() != null) {
                    this.E.getWindow().setDimAmount(0.0f);
                }
                this.E.show();
                this.E.setContentView(relativeLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        com.mobile.auth.l.c.a(a, "loginClickStart");
    }

    public void c() {
        try {
            com.mobile.auth.l.c.a(a, "loginClickComplete");
            if (this.A.E() != null && this.D) {
                this.D = false;
                this.A.E().b(this.c, null);
            } else if (this.E != null && this.E.isShowing()) {
                this.E.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id != 17476) {
                if (id == 26214) {
                    a(false);
                    return;
                } else {
                    if (id != 34952) {
                        return;
                    }
                    if (this.p.isChecked()) {
                        this.p.setChecked(false);
                        return;
                    } else {
                        this.p.setChecked(true);
                        return;
                    }
                }
            }
            if (!this.p.isChecked()) {
                if (this.A.as() != null) {
                    this.q.startAnimation(AnimationUtils.loadAnimation(this.c, g.c(this.c, this.A.as())));
                }
                if (this.A.F() != null) {
                    this.A.F().a(this.c, null);
                    return;
                } else if (!TextUtils.isEmpty(this.A.C())) {
                    Toast.makeText(this.c, this.A.C(), 1).show();
                    return;
                }
            }
            this.t++;
            m();
        } catch (Exception e) {
            com.cmic.sso.sdk.d.c.b.add(e);
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            if (bundle != null) {
                finish();
            }
            this.c = this;
            this.A = com.mobile.auth.e.a.a(this.c).a();
            if (this.A != null) {
                if (this.A.ao() != -1) {
                    setTheme(this.A.ao());
                }
                if (this.A.af() != null && this.A.ag() != null) {
                    overridePendingTransition(g.c(this, this.A.af()), g.c(this, this.A.ag()));
                }
            }
            com.cmic.sso.sdk.d.a.a("authPageIn");
            this.s = System.currentTimeMillis();
            this.n = com.mobile.auth.e.c.a(this);
            d();
            f();
        } catch (Exception e) {
            this.m.a().a.add(e);
            com.mobile.auth.l.c.a(a, e.toString());
            e.printStackTrace();
            a("200025", "发生未知错误", this.m, null);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        String str;
        String str2;
        try {
            this.b.removeCallbacksAndMessages(null);
            com.cmic.sso.sdk.d.a.a("timeOnAuthPage", (System.currentTimeMillis() - this.s) + "");
            if (this.p.isChecked()) {
                str = "authPrivacyState";
                str2 = "1";
            } else {
                str = "authPrivacyState";
                str2 = "0";
            }
            com.cmic.sso.sdk.d.a.a(str, str2);
            this.E = null;
            f.a().c();
            this.u.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            com.mobile.auth.l.c.a(a, "LoginAuthActivity clear failed");
            com.cmic.sso.sdk.d.c.b.add(e);
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.isCanceled() || keyEvent.getRepeatCount() != 0) {
            return true;
        }
        if (this.A.D() != null) {
            this.A.D().a();
        }
        if (this.A.aj() != 0 && !this.A.ar()) {
            return true;
        }
        a(false);
        return true;
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            if (this.m != null) {
                this.m.a("loginMethod", "loginAuth");
            }
            com.mobile.auth.e.a.a(this).a("200087", (JSONObject) null);
        } catch (Exception e) {
            this.m.a().a.add(e);
            a("200025", "发生未知错误", this.m, null);
        }
    }
}
