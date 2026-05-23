package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

/* JADX INFO: loaded from: classes.dex */
abstract class AbsActionBarView extends ViewGroup {
    private static final int FADE_DURATION = 200;
    protected final VisibilityAnimListener a;
    protected final Context b;
    protected ActionMenuView c;
    protected ActionMenuPresenter d;
    protected int e;
    protected ViewPropertyAnimatorCompat f;
    private boolean mEatingHover;
    private boolean mEatingTouch;

    protected class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        int a;
        private boolean mCanceled = false;

        protected VisibilityAnimListener() {
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationCancel(View view) {
            this.mCanceled = true;
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            if (this.mCanceled) {
                return;
            }
            AbsActionBarView.this.f = null;
            AbsActionBarView.super.setVisibility(this.a);
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
            AbsActionBarView.super.setVisibility(0);
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int i) {
            AbsActionBarView.this.f = viewPropertyAnimatorCompat;
            this.a = i;
            return this;
        }
    }

    AbsActionBarView(@NonNull Context context) {
        this(context, null);
    }

    AbsActionBarView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    AbsActionBarView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new VisibilityAnimListener();
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.b = context;
        } else {
            this.b = new ContextThemeWrapper(context, typedValue.resourceId);
        }
    }

    protected static int a(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    protected int a(View view, int i, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    protected int a(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = i2 + ((i3 - measuredHeight) / 2);
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    public void animateToVisibility(int i) {
        setupAnimatorToVisibility(i, 200L).start();
    }

    public boolean canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() {
        if (this.d != null) {
            this.d.dismissPopupMenus();
        }
    }

    public int getAnimatedVisibility() {
        return this.f != null ? this.a.a : getVisibility();
    }

    public int getContentHeight() {
        return this.e;
    }

    public boolean hideOverflowMenu() {
        if (this.d != null) {
            return this.d.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        if (this.d != null) {
            return this.d.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.d != null) {
            return this.d.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        return this.d != null && this.d.isOverflowReserved();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        setContentHeight(typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0));
        typedArrayObtainStyledAttributes.recycle();
        if (this.d != null) {
            this.d.onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.mEatingHover = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public void postShowOverflowMenu() {
        post(new Runnable() { // from class: androidx.appcompat.widget.AbsActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public void setContentHeight(int i) {
        this.e = i;
        requestLayout();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility()) {
            if (this.f != null) {
                this.f.cancel();
            }
            super.setVisibility(i);
        }
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAlpha;
        if (this.f != null) {
            this.f.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            viewPropertyAnimatorCompatAlpha = ViewCompat.animate(this).alpha(1.0f);
        } else {
            viewPropertyAnimatorCompatAlpha = ViewCompat.animate(this).alpha(0.0f);
        }
        viewPropertyAnimatorCompatAlpha.setDuration(j);
        viewPropertyAnimatorCompatAlpha.setListener(this.a.withFinalVisibility(viewPropertyAnimatorCompatAlpha, i));
        return viewPropertyAnimatorCompatAlpha;
    }

    public boolean showOverflowMenu() {
        if (this.d != null) {
            return this.d.showOverflowMenu();
        }
        return false;
    }
}
