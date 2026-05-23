package androidx.viewpager.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@ViewPager.DecorView
public class PagerTitleStrip extends ViewGroup {
    private static final float SIDE_ALPHA = 0.6f;
    private static final int TEXT_SPACING = 16;
    ViewPager a;
    TextView b;
    TextView c;
    TextView d;
    float e;
    int f;
    private int mGravity;
    private int mLastKnownCurrentPage;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    private int mScaledTextSpacing;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;
    private static final int[] ATTRS = {R.attr.textAppearance, R.attr.textSize, R.attr.textColor, R.attr.gravity};
    private static final int[] TEXT_ATTRS = {R.attr.textAllCaps};

    private class PageListener extends DataSetObserver implements ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener {
        private int mScrollState;

        PageListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.a(pagerAdapter, pagerAdapter2);
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.a.getAdapter());
            PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.e >= 0.0f ? PagerTitleStrip.this.e : 0.0f, true);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            this.mScrollState = i;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            if (f > 0.5f) {
                i++;
            }
            PagerTitleStrip.this.a(i, f, false);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (this.mScrollState == 0) {
                PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.a.getAdapter());
                PagerTitleStrip.this.a(PagerTitleStrip.this.a.getCurrentItem(), PagerTitleStrip.this.e >= 0.0f ? PagerTitleStrip.this.e : 0.0f, true);
            }
        }
    }

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        public CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            if (transformation != null) {
                return transformation.toString().toUpperCase(this.mLocale);
            }
            return null;
        }
    }

    public PagerTitleStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTitleStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastKnownCurrentPage = -1;
        this.e = -1.0f;
        this.mPageListener = new PageListener();
        TextView textView = new TextView(context);
        this.b = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.c = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.d = textView3;
        addView(textView3);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        boolean z = false;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.b, resourceId);
            TextViewCompat.setTextAppearance(this.c, resourceId);
            TextViewCompat.setTextAppearance(this.d, resourceId);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, dimensionPixelSize);
        }
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            int color = typedArrayObtainStyledAttributes.getColor(2, 0);
            this.b.setTextColor(color);
            this.c.setTextColor(color);
            this.d.setTextColor(color);
        }
        this.mGravity = typedArrayObtainStyledAttributes.getInteger(3, 80);
        typedArrayObtainStyledAttributes.recycle();
        this.f = this.c.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        this.b.setEllipsize(TextUtils.TruncateAt.END);
        this.c.setEllipsize(TextUtils.TruncateAt.END);
        this.d.setEllipsize(TextUtils.TruncateAt.END);
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, TEXT_ATTRS);
            z = typedArrayObtainStyledAttributes2.getBoolean(0, false);
            typedArrayObtainStyledAttributes2.recycle();
        }
        if (z) {
            setSingleLineAllCaps(this.b);
            setSingleLineAllCaps(this.c);
            setSingleLineAllCaps(this.d);
        } else {
            this.b.setSingleLine();
            this.c.setSingleLine();
            this.d.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod(new SingleLineAllCapsTransform(textView.getContext()));
    }

    void a(int i, float f, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (i != this.mLastKnownCurrentPage) {
            a(i, this.a.getAdapter());
        } else if (!z && f == this.e) {
            return;
        }
        this.mUpdatingPositions = true;
        int measuredWidth = this.b.getMeasuredWidth();
        int measuredWidth2 = this.c.getMeasuredWidth();
        int measuredWidth3 = this.d.getMeasuredWidth();
        int i6 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i7 = paddingRight + i6;
        int i8 = (width - (paddingLeft + i6)) - i7;
        float f2 = 0.5f + f;
        if (f2 > 1.0f) {
            f2 -= 1.0f;
        }
        int i9 = ((width - i7) - ((int) (i8 * f2))) - i6;
        int i10 = measuredWidth2 + i9;
        int baseline = this.b.getBaseline();
        int baseline2 = this.c.getBaseline();
        int baseline3 = this.d.getBaseline();
        int iMax = Math.max(Math.max(baseline, baseline2), baseline3);
        int i11 = iMax - baseline;
        int i12 = iMax - baseline2;
        int i13 = iMax - baseline3;
        int iMax2 = Math.max(Math.max(this.b.getMeasuredHeight() + i11, this.c.getMeasuredHeight() + i12), this.d.getMeasuredHeight() + i13);
        int i14 = this.mGravity & 112;
        if (i14 == 16) {
            i2 = (((height - paddingTop) - paddingBottom) - iMax2) / 2;
        } else {
            if (i14 != 80) {
                i3 = i11 + paddingTop;
                i4 = i12 + paddingTop;
                i5 = paddingTop + i13;
                this.c.layout(i9, i4, i10, this.c.getMeasuredHeight() + i4);
                int iMin = Math.min(paddingLeft, (i9 - this.mScaledTextSpacing) - measuredWidth);
                this.b.layout(iMin, i3, measuredWidth + iMin, this.b.getMeasuredHeight() + i3);
                int iMax3 = Math.max((width - paddingRight) - measuredWidth3, i10 + this.mScaledTextSpacing);
                this.d.layout(iMax3, i5, iMax3 + measuredWidth3, this.d.getMeasuredHeight() + i5);
                this.e = f;
                this.mUpdatingPositions = false;
            }
            i2 = (height - paddingBottom) - iMax2;
        }
        i3 = i11 + i2;
        i4 = i12 + i2;
        i5 = i2 + i13;
        this.c.layout(i9, i4, i10, this.c.getMeasuredHeight() + i4);
        int iMin2 = Math.min(paddingLeft, (i9 - this.mScaledTextSpacing) - measuredWidth);
        this.b.layout(iMin2, i3, measuredWidth + iMin2, this.b.getMeasuredHeight() + i3);
        int iMax32 = Math.max((width - paddingRight) - measuredWidth3, i10 + this.mScaledTextSpacing);
        this.d.layout(iMax32, i5, iMax32 + measuredWidth3, this.d.getMeasuredHeight() + i5);
        this.e = f;
        this.mUpdatingPositions = false;
    }

    void a(int i, PagerAdapter pagerAdapter) {
        int count = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        CharSequence pageTitle = null;
        this.b.setText((i < 1 || pagerAdapter == null) ? null : pagerAdapter.getPageTitle(i - 1));
        this.c.setText((pagerAdapter == null || i >= count) ? null : pagerAdapter.getPageTitle(i));
        int i2 = i + 1;
        if (i2 < count && pagerAdapter != null) {
            pageTitle = pagerAdapter.getPageTitle(i2);
        }
        this.d.setText(pageTitle);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((getWidth() - getPaddingLeft()) - getPaddingRight()) * 0.8f)), Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.b.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.c.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.d.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        this.mLastKnownCurrentPage = i;
        if (!this.mUpdatingPositions) {
            a(i, this.e, false);
        }
        this.mUpdatingText = false;
    }

    void a(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(pagerAdapter2);
        }
        if (this.a != null) {
            this.mLastKnownCurrentPage = -1;
            this.e = -1.0f;
            a(this.a.getCurrentItem(), pagerAdapter2);
            requestLayout();
        }
    }

    int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager viewPager = (ViewPager) parent;
        PagerAdapter adapter = viewPager.getAdapter();
        viewPager.a(this.mPageListener);
        viewPager.addOnAdapterChangeListener(this.mPageListener);
        this.a = viewPager;
        a(this.mWatchingAdapter != null ? this.mWatchingAdapter.get() : null, adapter);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.a != null) {
            a(this.a.getAdapter(), (PagerAdapter) null);
            this.a.a((ViewPager.OnPageChangeListener) null);
            this.a.removeOnAdapterChangeListener(this.mPageListener);
            this.a = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.a != null) {
            a(this.mLastKnownCurrentPage, this.e >= 0.0f ? this.e : 0.0f, true);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int iMax;
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int size = View.MeasureSpec.getSize(i);
        int childMeasureSpec2 = getChildMeasureSpec(i, (int) (size * 0.2f), -2);
        this.b.measure(childMeasureSpec2, childMeasureSpec);
        this.c.measure(childMeasureSpec2, childMeasureSpec);
        this.d.measure(childMeasureSpec2, childMeasureSpec);
        if (View.MeasureSpec.getMode(i2) == 1073741824) {
            iMax = View.MeasureSpec.getSize(i2);
        } else {
            iMax = Math.max(getMinHeight(), this.c.getMeasuredHeight() + paddingTop);
        }
        setMeasuredDimension(size, View.resolveSizeAndState(iMax, i2, this.c.getMeasuredState() << 16));
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mUpdatingText) {
            return;
        }
        super.requestLayout();
    }

    public void setGravity(int i) {
        this.mGravity = i;
        requestLayout();
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.mNonPrimaryAlpha = ((int) (f * 255.0f)) & 255;
        int i = (this.mNonPrimaryAlpha << 24) | (this.f & ViewCompat.MEASURED_SIZE_MASK);
        this.b.setTextColor(i);
        this.d.setTextColor(i);
    }

    public void setTextColor(@ColorInt int i) {
        this.f = i;
        this.c.setTextColor(i);
        int i2 = (this.mNonPrimaryAlpha << 24) | (this.f & ViewCompat.MEASURED_SIZE_MASK);
        this.b.setTextColor(i2);
        this.d.setTextColor(i2);
    }

    public void setTextSize(int i, float f) {
        this.b.setTextSize(i, f);
        this.c.setTextSize(i, f);
        this.d.setTextSize(i, f);
    }

    public void setTextSpacing(int i) {
        this.mScaledTextSpacing = i;
        requestLayout();
    }
}
