package com.cmic.sso.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class LoadingImageView extends ImageView {
    private Animation a;
    private LinearInterpolator b;

    public LoadingImageView(Context context) {
        super(context);
        this.a = null;
        this.b = null;
        a();
    }

    public LoadingImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = null;
        this.b = null;
        a();
    }

    public LoadingImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = null;
        this.b = null;
        a();
    }

    protected void a() {
        this.a = AnimationUtils.loadAnimation(getContext(), g.c(getContext(), "umcsdk_anim_loading"));
        this.b = new LinearInterpolator();
        this.a.setInterpolator(this.b);
    }
}
