package com.mobile.auth.x;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.igexin.push.core.b;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.k;
import com.nirvana.tools.core.AppUtils;

/* JADX INFO: loaded from: classes.dex */
public class a extends Dialog {
    private Context a;
    private Animation b;
    private ImageView c;
    private LinearLayout d;
    private AuthUIConfig e;

    public a(@NonNull Context context, int i) {
        super(context, i);
        this.a = context;
    }

    public a(@NonNull Context context, AuthUIConfig authUIConfig) {
        this(context, AppUtils.getResID(context, "authsdk_loading_dialog", "style"));
        this.e = authUIConfig;
    }

    private void a() {
        try {
            setContentView(AppUtils.getResID(getContext(), "authsdk_loading_dialog_layout", "layout"));
            setCancelable(false);
            this.c = (ImageView) findViewById(AppUtils.getResID(getContext(), "authsdk_iv_loading", b.C));
            this.d = (LinearLayout) findViewById(AppUtils.getResID(getContext(), "authsdk_lly_loading", b.C));
            Drawable loadingImgDrawable = this.e.getLoadingImgDrawable();
            if (loadingImgDrawable == null) {
                Drawable drawableC = k.c(getContext(), this.e.getLoadingImgPath());
                if (drawableC != null) {
                    this.c.setImageDrawable(drawableC);
                }
                this.b = AnimationUtils.loadAnimation(this.a, AppUtils.getResID(getContext(), "authsdk_anim_loading", "anim"));
                this.b.setInterpolator(new LinearInterpolator());
                this.c.startAnimation(this.b);
            } else {
                this.c.setImageDrawable(loadingImgDrawable);
            }
            Drawable loadingBackgroundDrawable = this.e.getLoadingBackgroundDrawable();
            if (loadingBackgroundDrawable == null && (loadingBackgroundDrawable = k.c(getContext(), this.e.getLoadingBackgroundPath())) == null) {
                return;
            }
            LinearLayout linearLayout = this.d;
            linearLayout.setBackgroundDrawable(loadingBackgroundDrawable);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            if (this.b != null) {
                this.b.cancel();
            }
            super.dismiss();
            this.e = null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    @Override // android.app.Dialog
    public void show() {
        try {
            super.show();
            if (this.b != null) {
                this.b.start();
                this.c.startAnimation(this.b);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
