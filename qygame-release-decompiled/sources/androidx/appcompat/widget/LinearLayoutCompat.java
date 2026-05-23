package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface DividerMode {
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.LinearLayoutCompat, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    private void forceUniformHeight(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View viewB = b(i3);
            if (viewB.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i4 = layoutParams.width;
                    layoutParams.width = viewB.getMeasuredWidth();
                    measureChildWithMargins(viewB, i2, 0, iMakeMeasureSpec, 0);
                    layoutParams.width = i4;
                }
            }
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View viewB = b(i3);
            if (viewB.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = viewB.getMeasuredHeight();
                    measureChildWithMargins(viewB, iMakeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    int a(View view) {
        return 0;
    }

    int a(View view, int i) {
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x02ec A[PHI: r10
      0x02ec: PHI (r10v19 int) = (r10v16 int), (r10v20 int) binds: [B:135:0x02ea, B:131:0x02df] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x033c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(int i, int i2) {
        int i3;
        int i4;
        int iCombineMeasuredStates;
        int i5;
        int iMax;
        float f;
        int i6;
        int i7;
        boolean z;
        int i8;
        int iMax2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int iMax3;
        int i14;
        int i15;
        View view;
        int iMax4;
        boolean z2;
        int iMax5;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i16 = this.mBaselineAlignedChildIndex;
        boolean z3 = this.mUseLargestChild;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int iA = 0;
        boolean z4 = false;
        boolean z5 = false;
        float f2 = 0.0f;
        boolean z6 = true;
        while (true) {
            int i22 = 8;
            int i23 = i20;
            if (iA >= virtualChildCount) {
                int i24 = i17;
                int i25 = i19;
                int i26 = i21;
                int i27 = virtualChildCount;
                int i28 = mode2;
                int iMax6 = i18;
                if (this.mTotalLength > 0) {
                    i3 = i27;
                    if (c(i3)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                } else {
                    i3 = i27;
                }
                if (z3) {
                    i4 = i28;
                    if (i4 == Integer.MIN_VALUE || i4 == 0) {
                        this.mTotalLength = 0;
                        int iA2 = 0;
                        while (iA2 < i3) {
                            View viewB = b(iA2);
                            if (viewB == null) {
                                iMax2 = this.mTotalLength + d(iA2);
                            } else if (viewB.getVisibility() == i22) {
                                iA2 += a(viewB, iA2);
                                iA2++;
                                i22 = 8;
                            } else {
                                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                                int i29 = this.mTotalLength;
                                iMax2 = Math.max(i29, i29 + i25 + layoutParams.topMargin + layoutParams.bottomMargin + b(viewB));
                            }
                            this.mTotalLength = iMax2;
                            iA2++;
                            i22 = 8;
                        }
                    }
                } else {
                    i4 = i28;
                }
                this.mTotalLength += getPaddingTop() + getPaddingBottom();
                int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), i2, 0);
                int i30 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
                if (z4 || (i30 != 0 && f2 > 0.0f)) {
                    if (this.mWeightSum > 0.0f) {
                        f2 = this.mWeightSum;
                    }
                    this.mTotalLength = 0;
                    float f3 = f2;
                    int i31 = 0;
                    int i32 = i26;
                    iCombineMeasuredStates = i24;
                    while (i31 < i3) {
                        View viewB2 = b(i31);
                        if (viewB2.getVisibility() == 8) {
                            i6 = i4;
                            f = f3;
                        } else {
                            LayoutParams layoutParams2 = (LayoutParams) viewB2.getLayoutParams();
                            float f4 = layoutParams2.weight;
                            if (f4 > 0.0f) {
                                int measuredHeight = (int) ((i30 * f4) / f3);
                                float f5 = f3 - f4;
                                int i33 = i30 - measuredHeight;
                                int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + layoutParams2.leftMargin + layoutParams2.rightMargin, layoutParams2.width);
                                if (layoutParams2.height == 0) {
                                    i8 = 1073741824;
                                    if (i4 == 1073741824) {
                                        if (measuredHeight <= 0) {
                                            measuredHeight = 0;
                                        }
                                        viewB2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, viewB2.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                                        i30 = i33;
                                        f = f5;
                                    }
                                } else {
                                    i8 = 1073741824;
                                }
                                measuredHeight = viewB2.getMeasuredHeight() + measuredHeight;
                                if (measuredHeight < 0) {
                                }
                                viewB2.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight, i8));
                                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, viewB2.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                                i30 = i33;
                                f = f5;
                            } else {
                                f = f3;
                            }
                            int i34 = iCombineMeasuredStates;
                            int i35 = layoutParams2.leftMargin + layoutParams2.rightMargin;
                            int measuredWidth = viewB2.getMeasuredWidth() + i35;
                            iMax6 = Math.max(iMax6, measuredWidth);
                            if (mode != 1073741824) {
                                i6 = i4;
                                i7 = -1;
                                z = layoutParams2.width == -1;
                                if (!z) {
                                    i35 = measuredWidth;
                                }
                                int iMax7 = Math.max(i32, i35);
                                boolean z7 = !z6 && layoutParams2.width == i7;
                                int i36 = this.mTotalLength;
                                this.mTotalLength = Math.max(i36, i36 + viewB2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin + b(viewB2));
                                z6 = z7;
                                i32 = iMax7;
                                iCombineMeasuredStates = i34;
                            } else {
                                i6 = i4;
                                i7 = -1;
                            }
                            if (!z) {
                            }
                            int iMax72 = Math.max(i32, i35);
                            if (z6) {
                                int i362 = this.mTotalLength;
                                this.mTotalLength = Math.max(i362, i362 + viewB2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin + b(viewB2));
                                z6 = z7;
                                i32 = iMax72;
                                iCombineMeasuredStates = i34;
                            }
                        }
                        i31++;
                        f3 = f;
                        i4 = i6;
                    }
                    i5 = i;
                    this.mTotalLength += getPaddingTop() + getPaddingBottom();
                    iMax = i32;
                } else {
                    iMax = Math.max(i26, i23);
                    if (z3 && i4 != 1073741824) {
                        for (int i37 = 0; i37 < i3; i37++) {
                            View viewB3 = b(i37);
                            if (viewB3 != null && viewB3.getVisibility() != 8 && ((LayoutParams) viewB3.getLayoutParams()).weight > 0.0f) {
                                viewB3.measure(View.MeasureSpec.makeMeasureSpec(viewB3.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i25, 1073741824));
                            }
                        }
                    }
                    iCombineMeasuredStates = i24;
                    i5 = i;
                }
                if (z6 || mode == 1073741824) {
                    iMax = iMax6;
                }
                setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i5, iCombineMeasuredStates), iResolveSizeAndState);
                if (z5) {
                    forceUniformWidth(i3, i2);
                    return;
                }
                return;
            }
            View viewB4 = b(iA);
            if (viewB4 == null) {
                this.mTotalLength += d(iA);
                i12 = virtualChildCount;
                i13 = mode2;
                i20 = i23;
            } else {
                int i38 = i17;
                if (viewB4.getVisibility() == 8) {
                    iA += a(viewB4, iA);
                    i12 = virtualChildCount;
                    i13 = mode2;
                    i20 = i23;
                    i17 = i38;
                } else {
                    if (c(iA)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams3 = (LayoutParams) viewB4.getLayoutParams();
                    float f6 = f2 + layoutParams3.weight;
                    if (mode2 == 1073741824 && layoutParams3.height == 0 && layoutParams3.weight > 0.0f) {
                        int i39 = this.mTotalLength;
                        this.mTotalLength = Math.max(i39, layoutParams3.topMargin + i39 + layoutParams3.bottomMargin);
                        iMax4 = i19;
                        view = viewB4;
                        i14 = i21;
                        i12 = virtualChildCount;
                        i13 = mode2;
                        z4 = true;
                        iMax3 = i23;
                        i10 = i38;
                        i11 = i18;
                        i15 = iA;
                    } else {
                        int i40 = i18;
                        if (layoutParams3.height != 0 || layoutParams3.weight <= 0.0f) {
                            i9 = Integer.MIN_VALUE;
                        } else {
                            layoutParams3.height = -2;
                            i9 = 0;
                        }
                        i10 = i38;
                        int i41 = i9;
                        i11 = i40;
                        int i42 = i19;
                        i12 = virtualChildCount;
                        i13 = mode2;
                        iMax3 = i23;
                        i14 = i21;
                        i15 = iA;
                        a(viewB4, iA, i, 0, i2, f6 == 0.0f ? this.mTotalLength : 0);
                        if (i41 != Integer.MIN_VALUE) {
                            layoutParams3.height = i41;
                        }
                        int measuredHeight2 = viewB4.getMeasuredHeight();
                        int i43 = this.mTotalLength;
                        view = viewB4;
                        this.mTotalLength = Math.max(i43, i43 + measuredHeight2 + layoutParams3.topMargin + layoutParams3.bottomMargin + b(view));
                        iMax4 = z3 ? Math.max(measuredHeight2, i42) : i42;
                    }
                    if (i16 >= 0 && i16 == i15 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i15 < i16 && layoutParams3.weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode == 1073741824 || layoutParams3.width != -1) {
                        z2 = false;
                    } else {
                        z2 = true;
                        z5 = true;
                    }
                    int i44 = layoutParams3.leftMargin + layoutParams3.rightMargin;
                    int measuredWidth2 = view.getMeasuredWidth() + i44;
                    int iMax8 = Math.max(i11, measuredWidth2);
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i10, view.getMeasuredState());
                    boolean z8 = z6 && layoutParams3.width == -1;
                    if (layoutParams3.weight > 0.0f) {
                        if (!z2) {
                            i44 = measuredWidth2;
                        }
                        iMax3 = Math.max(iMax3, i44);
                        iMax5 = i14;
                    } else {
                        if (!z2) {
                            i44 = measuredWidth2;
                        }
                        iMax5 = Math.max(i14, i44);
                    }
                    int iA3 = a(view, i15) + i15;
                    i19 = iMax4;
                    z6 = z8;
                    i20 = iMax3;
                    f2 = f6;
                    i21 = iMax5;
                    i17 = iCombineMeasuredStates2;
                    iA = iA3;
                    i18 = iMax8;
                }
            }
            iA++;
            mode2 = i13;
            virtualChildCount = i12;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i3 - i;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.mGravity & 112;
        int i10 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int paddingTop = i9 != 16 ? i9 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength : (((i4 - i2) - this.mTotalLength) / 2) + getPaddingTop();
        int iA = 0;
        while (iA < virtualChildCount) {
            View viewB = b(iA);
            if (viewB == null) {
                paddingTop += d(iA);
            } else {
                if (viewB.getVisibility() != 8) {
                    int measuredWidth = viewB.getMeasuredWidth();
                    int measuredHeight = viewB.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                    int i11 = layoutParams.gravity;
                    if (i11 < 0) {
                        i11 = i10;
                    }
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(i11, ViewCompat.getLayoutDirection(this)) & 7;
                    if (absoluteGravity == 1) {
                        i5 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + layoutParams.leftMargin;
                    } else if (absoluteGravity != 5) {
                        i6 = layoutParams.leftMargin + paddingLeft;
                        int i12 = i6;
                        if (c(iA)) {
                            paddingTop += this.mDividerHeight;
                        }
                        int i13 = paddingTop + layoutParams.topMargin;
                        setChildFrame(viewB, i12, i13 + a(viewB), measuredWidth, measuredHeight);
                        int iB = i13 + measuredHeight + layoutParams.bottomMargin + b(viewB);
                        iA += a(viewB, iA);
                        paddingTop = iB;
                        i7 = 1;
                    } else {
                        i5 = paddingRight - measuredWidth;
                    }
                    i6 = i5 - layoutParams.rightMargin;
                    int i122 = i6;
                    if (c(iA)) {
                    }
                    int i132 = paddingTop + layoutParams.topMargin;
                    setChildFrame(viewB, i122, i132 + a(viewB), measuredWidth, measuredHeight);
                    int iB2 = i132 + measuredHeight + layoutParams.bottomMargin + b(viewB);
                    iA += a(viewB, iA);
                    paddingTop = iB2;
                    i7 = 1;
                }
                iA += i7;
            }
            i7 = 1;
            iA += i7;
        }
    }

    void a(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View viewB = b(i);
            if (viewB != null && viewB.getVisibility() != 8 && c(i)) {
                a(canvas, (viewB.getTop() - ((LayoutParams) viewB.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (c(virtualChildCount)) {
            View viewB2 = b(virtualChildCount - 1);
            a(canvas, viewB2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : viewB2.getBottom() + ((LayoutParams) viewB2.getLayoutParams()).bottomMargin);
        }
    }

    void a(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    void a(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    int b(View view) {
        return 0;
    }

    View b(int i) {
        return getChildAt(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:169:0x03bf A[PHI: r4
      0x03bf: PHI (r4v32 int) = (r4v28 int), (r4v33 int) binds: [B:168:0x03bd, B:164:0x03b2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0471  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void b(int i, int i2) {
        int[] iArr;
        int i3;
        int i4;
        int i5;
        int iMax;
        int i6;
        int i7;
        float f;
        int i8;
        int baseline;
        int i9;
        int i10;
        byte b;
        int i11;
        int i12;
        boolean z;
        boolean z2;
        View view;
        int iMax2;
        int i13;
        boolean z3;
        int measuredHeight;
        int baseline2;
        int iMax3;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr2 = this.mMaxAscent;
        int[] iArr3 = this.mMaxDescent;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z4 = this.mBaselineAligned;
        boolean z5 = this.mUseLargestChild;
        int i14 = 1073741824;
        boolean z6 = mode == 1073741824;
        int iA = 0;
        int iMax4 = 0;
        int i15 = 0;
        boolean z7 = false;
        int iMax5 = 0;
        int iMax6 = 0;
        int i16 = 0;
        boolean z8 = false;
        boolean z9 = true;
        float f2 = 0.0f;
        while (true) {
            iArr = iArr3;
            if (iA >= virtualChildCount) {
                break;
            }
            View viewB = b(iA);
            if (viewB == null) {
                this.mTotalLength += d(iA);
            } else if (viewB.getVisibility() == 8) {
                iA += a(viewB, iA);
            } else {
                if (c(iA)) {
                    this.mTotalLength += this.mDividerWidth;
                }
                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                float f3 = f2 + layoutParams.weight;
                if (mode == i14 && layoutParams.width == 0 && layoutParams.weight > 0.0f) {
                    if (z6) {
                        iMax3 = this.mTotalLength + layoutParams.leftMargin + layoutParams.rightMargin;
                    } else {
                        int i17 = this.mTotalLength;
                        iMax3 = Math.max(i17, layoutParams.leftMargin + i17 + layoutParams.rightMargin);
                    }
                    this.mTotalLength = iMax3;
                    if (z4) {
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        viewB.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                        i12 = iA;
                        z = z5;
                        z2 = z4;
                        view = viewB;
                    } else {
                        i12 = iA;
                        z = z5;
                        z2 = z4;
                        view = viewB;
                        z7 = true;
                        i13 = 1073741824;
                        if (mode2 == i13 && layoutParams.height == -1) {
                            z3 = true;
                            z8 = true;
                        } else {
                            z3 = false;
                        }
                        int i18 = layoutParams.topMargin + layoutParams.bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i18;
                        int iCombineMeasuredStates = View.combineMeasuredStates(i16, view.getMeasuredState());
                        if (z2 && (baseline2 = view.getBaseline()) != -1) {
                            int i19 = ((((layoutParams.gravity >= 0 ? this.mGravity : layoutParams.gravity) & 112) >> 4) & (-2)) >> 1;
                            iArr2[i19] = Math.max(iArr2[i19], baseline2);
                            iArr[i19] = Math.max(iArr[i19], measuredHeight - baseline2);
                        }
                        int iMax7 = Math.max(i15, measuredHeight);
                        boolean z10 = !z9 && layoutParams.height == -1;
                        if (layoutParams.weight <= 0.0f) {
                            if (!z3) {
                                i18 = measuredHeight;
                            }
                            iMax6 = Math.max(iMax6, i18);
                        } else {
                            int i20 = iMax6;
                            if (z3) {
                                measuredHeight = i18;
                            }
                            iMax5 = Math.max(iMax5, measuredHeight);
                            iMax6 = i20;
                        }
                        int i21 = i12;
                        i15 = iMax7;
                        i16 = iCombineMeasuredStates;
                        z9 = z10;
                        iA = a(view, i21) + i21;
                        f2 = f3;
                        iA++;
                        iArr3 = iArr;
                        z5 = z;
                        z4 = z2;
                        i14 = 1073741824;
                    }
                } else {
                    if (layoutParams.width != 0 || layoutParams.weight <= 0.0f) {
                        b = -2;
                        i11 = Integer.MIN_VALUE;
                    } else {
                        b = -2;
                        layoutParams.width = -2;
                        i11 = 0;
                    }
                    i12 = iA;
                    int i22 = i11;
                    z = z5;
                    z2 = z4;
                    a(viewB, i12, i, f3 == 0.0f ? this.mTotalLength : 0, i2, 0);
                    if (i22 != Integer.MIN_VALUE) {
                        layoutParams.width = i22;
                    }
                    int measuredWidth = viewB.getMeasuredWidth();
                    if (z6) {
                        view = viewB;
                        iMax2 = this.mTotalLength + layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin + b(view);
                    } else {
                        view = viewB;
                        int i23 = this.mTotalLength;
                        iMax2 = Math.max(i23, i23 + measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin + b(view));
                    }
                    this.mTotalLength = iMax2;
                    if (z) {
                        iMax4 = Math.max(measuredWidth, iMax4);
                    }
                }
                i13 = 1073741824;
                if (mode2 == i13) {
                    z3 = false;
                    int i182 = layoutParams.topMargin + layoutParams.bottomMargin;
                    measuredHeight = view.getMeasuredHeight() + i182;
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i16, view.getMeasuredState());
                    if (z2) {
                        int i192 = ((((layoutParams.gravity >= 0 ? this.mGravity : layoutParams.gravity) & 112) >> 4) & (-2)) >> 1;
                        iArr2[i192] = Math.max(iArr2[i192], baseline2);
                        iArr[i192] = Math.max(iArr[i192], measuredHeight - baseline2);
                    }
                    int iMax72 = Math.max(i15, measuredHeight);
                    if (z9) {
                        if (layoutParams.weight <= 0.0f) {
                        }
                        int i212 = i12;
                        i15 = iMax72;
                        i16 = iCombineMeasuredStates2;
                        z9 = z10;
                        iA = a(view, i212) + i212;
                        f2 = f3;
                    }
                }
                iA++;
                iArr3 = iArr;
                z5 = z;
                z4 = z2;
                i14 = 1073741824;
            }
            z = z5;
            z2 = z4;
            iA++;
            iArr3 = iArr;
            z5 = z;
            z4 = z2;
            i14 = 1073741824;
        }
        boolean z11 = z5;
        boolean z12 = z4;
        int iMax8 = i15;
        int i24 = iMax5;
        int i25 = iMax6;
        int i26 = i16;
        if (this.mTotalLength > 0 && c(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) {
            i3 = i26;
        } else {
            i3 = i26;
            iMax8 = Math.max(iMax8, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        }
        if (z11 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            int iA2 = 0;
            while (iA2 < virtualChildCount) {
                View viewB2 = b(iA2);
                if (viewB2 == null) {
                    this.mTotalLength += d(iA2);
                } else if (viewB2.getVisibility() == 8) {
                    iA2 += a(viewB2, iA2);
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) viewB2.getLayoutParams();
                    if (z6) {
                        this.mTotalLength += layoutParams2.leftMargin + iMax4 + layoutParams2.rightMargin + b(viewB2);
                    } else {
                        int i27 = this.mTotalLength;
                        i10 = iMax8;
                        this.mTotalLength = Math.max(i27, i27 + iMax4 + layoutParams2.leftMargin + layoutParams2.rightMargin + b(viewB2));
                        iA2++;
                        iMax8 = i10;
                    }
                }
                i10 = iMax8;
                iA2++;
                iMax8 = i10;
            }
        }
        int iMax9 = iMax8;
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), i, 0);
        int i28 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (z7 || (i28 != 0 && f2 > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f2 = this.mWeightSum;
            }
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            this.mTotalLength = 0;
            int i29 = i24;
            int iMax10 = -1;
            int iCombineMeasuredStates3 = i3;
            float f4 = f2;
            int i30 = 0;
            while (i30 < virtualChildCount) {
                View viewB3 = b(i30);
                if (viewB3 == null || viewB3.getVisibility() == 8) {
                    i6 = i28;
                    i7 = virtualChildCount;
                } else {
                    LayoutParams layoutParams3 = (LayoutParams) viewB3.getLayoutParams();
                    float f5 = layoutParams3.weight;
                    if (f5 > 0.0f) {
                        int measuredWidth2 = (int) ((i28 * f5) / f4);
                        float f6 = f4 - f5;
                        int i31 = i28 - measuredWidth2;
                        i7 = virtualChildCount;
                        int childMeasureSpec = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + layoutParams3.topMargin + layoutParams3.bottomMargin, layoutParams3.height);
                        if (layoutParams3.width == 0) {
                            i9 = 1073741824;
                            if (mode == 1073741824) {
                                if (measuredWidth2 <= 0) {
                                    measuredWidth2 = 0;
                                }
                                viewB3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i9), childMeasureSpec);
                                iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, viewB3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                                f4 = f6;
                                i6 = i31;
                            }
                        } else {
                            i9 = 1073741824;
                        }
                        measuredWidth2 = viewB3.getMeasuredWidth() + measuredWidth2;
                        if (measuredWidth2 < 0) {
                        }
                        viewB3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i9), childMeasureSpec);
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, viewB3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                        f4 = f6;
                        i6 = i31;
                    } else {
                        i6 = i28;
                        i7 = virtualChildCount;
                    }
                    if (z6) {
                        this.mTotalLength += viewB3.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin + b(viewB3);
                        f = f4;
                    } else {
                        int i32 = this.mTotalLength;
                        f = f4;
                        this.mTotalLength = Math.max(i32, viewB3.getMeasuredWidth() + i32 + layoutParams3.leftMargin + layoutParams3.rightMargin + b(viewB3));
                    }
                    boolean z13 = mode2 != 1073741824 && layoutParams3.height == -1;
                    int i33 = layoutParams3.topMargin + layoutParams3.bottomMargin;
                    int measuredHeight2 = viewB3.getMeasuredHeight() + i33;
                    iMax10 = Math.max(iMax10, measuredHeight2);
                    if (!z13) {
                        i33 = measuredHeight2;
                    }
                    int iMax11 = Math.max(i29, i33);
                    if (z9) {
                        i8 = -1;
                        boolean z14 = layoutParams3.height == -1;
                        if (!z12 && (baseline = viewB3.getBaseline()) != i8) {
                            int i34 = ((((layoutParams3.gravity < 0 ? this.mGravity : layoutParams3.gravity) & 112) >> 4) & (-2)) >> 1;
                            iArr2[i34] = Math.max(iArr2[i34], baseline);
                            iArr[i34] = Math.max(iArr[i34], measuredHeight2 - baseline);
                        }
                        i29 = iMax11;
                        z9 = z14;
                        f4 = f;
                    } else {
                        i8 = -1;
                    }
                    if (!z12) {
                        i29 = iMax11;
                        z9 = z14;
                        f4 = f;
                    }
                }
                i30++;
                i28 = i6;
                virtualChildCount = i7;
            }
            i4 = virtualChildCount;
            i5 = i2;
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            iMax9 = (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? iMax10 : Math.max(iMax10, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            i3 = iCombineMeasuredStates3;
            iMax = i29;
        } else {
            iMax = Math.max(i24, i25);
            if (z11 && mode != 1073741824) {
                for (int i35 = 0; i35 < virtualChildCount; i35++) {
                    View viewB4 = b(i35);
                    if (viewB4 != null && viewB4.getVisibility() != 8 && ((LayoutParams) viewB4.getLayoutParams()).weight > 0.0f) {
                        viewB4.measure(View.MeasureSpec.makeMeasureSpec(iMax4, 1073741824), View.MeasureSpec.makeMeasureSpec(viewB4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i4 = virtualChildCount;
            i5 = i2;
        }
        if (!z9 && mode2 != 1073741824) {
            iMax9 = iMax;
        }
        setMeasuredDimension(iResolveSizeAndState | (i3 & ViewCompat.MEASURED_STATE_MASK), View.resolveSizeAndState(Math.max(iMax9 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i5, i3 << 16));
        if (z8) {
            forceUniformHeight(i4, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x010a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void b(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z;
        int i10;
        int i11;
        int i12;
        int i13;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i14 = i4 - i2;
        int paddingBottom = i14 - getPaddingBottom();
        int paddingBottom2 = (i14 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i15 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i16 = this.mGravity & 112;
        boolean z2 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i15, ViewCompat.getLayoutDirection(this));
        boolean z3 = true;
        int paddingLeft = absoluteGravity != 1 ? absoluteGravity != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength : (((i3 - i) - this.mTotalLength) / 2) + getPaddingLeft();
        if (zIsLayoutRtl) {
            i5 = virtualChildCount - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        int iA = 0;
        while (iA < virtualChildCount) {
            int i17 = i5 + (i6 * iA);
            View viewB = b(i17);
            if (viewB == null) {
                paddingLeft += d(i17);
                z = z3;
                i7 = paddingTop;
                i8 = virtualChildCount;
                i9 = i16;
            } else if (viewB.getVisibility() != 8) {
                int measuredWidth = viewB.getMeasuredWidth();
                int measuredHeight = viewB.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                if (z2) {
                    i10 = iA;
                    i8 = virtualChildCount;
                    int baseline = layoutParams.height != -1 ? viewB.getBaseline() : -1;
                    i11 = layoutParams.gravity;
                    if (i11 < 0) {
                        i11 = i16;
                    }
                    i12 = i11 & 112;
                    i9 = i16;
                    if (i12 != 16) {
                        z = true;
                        i13 = ((((paddingBottom2 - measuredHeight) / 2) + paddingTop) + layoutParams.topMargin) - layoutParams.bottomMargin;
                    } else if (i12 != 48) {
                        if (i12 != 80) {
                            i13 = paddingTop;
                        } else {
                            int measuredHeight2 = (paddingBottom - measuredHeight) - layoutParams.bottomMargin;
                            if (baseline != -1) {
                                measuredHeight2 -= iArr2[2] - (viewB.getMeasuredHeight() - baseline);
                            }
                            i13 = measuredHeight2;
                        }
                        z = true;
                    } else {
                        int i18 = layoutParams.topMargin + paddingTop;
                        if (baseline != -1) {
                            z = true;
                            i18 += iArr[1] - baseline;
                        } else {
                            z = true;
                        }
                        i13 = i18;
                    }
                    if (c(i17)) {
                        paddingLeft += this.mDividerWidth;
                    }
                    int i19 = layoutParams.leftMargin + paddingLeft;
                    i7 = paddingTop;
                    setChildFrame(viewB, i19 + a(viewB), i13, measuredWidth, measuredHeight);
                    int iB = i19 + measuredWidth + layoutParams.rightMargin + b(viewB);
                    iA = i10 + a(viewB, i17);
                    paddingLeft = iB;
                    iA++;
                    z3 = z;
                    virtualChildCount = i8;
                    i16 = i9;
                    paddingTop = i7;
                } else {
                    i10 = iA;
                    i8 = virtualChildCount;
                }
                i11 = layoutParams.gravity;
                if (i11 < 0) {
                }
                i12 = i11 & 112;
                i9 = i16;
                if (i12 != 16) {
                }
                if (c(i17)) {
                }
                int i192 = layoutParams.leftMargin + paddingLeft;
                i7 = paddingTop;
                setChildFrame(viewB, i192 + a(viewB), i13, measuredWidth, measuredHeight);
                int iB2 = i192 + measuredWidth + layoutParams.rightMargin + b(viewB);
                iA = i10 + a(viewB, i17);
                paddingLeft = iB2;
                iA++;
                z3 = z;
                virtualChildCount = i8;
                i16 = i9;
                paddingTop = i7;
            } else {
                i7 = paddingTop;
                i8 = virtualChildCount;
                i9 = i16;
                z = true;
            }
            iA++;
            z3 = z;
            virtualChildCount = i8;
            i16 = i9;
            paddingTop = i7;
        }
    }

    void b(Canvas canvas) {
        int right;
        int left;
        int paddingRight;
        int virtualChildCount = getVirtualChildCount();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < virtualChildCount; i++) {
            View viewB = b(i);
            if (viewB != null && viewB.getVisibility() != 8 && c(i)) {
                LayoutParams layoutParams = (LayoutParams) viewB.getLayoutParams();
                b(canvas, zIsLayoutRtl ? viewB.getRight() + layoutParams.rightMargin : (viewB.getLeft() - layoutParams.leftMargin) - this.mDividerWidth);
            }
        }
        if (c(virtualChildCount)) {
            View viewB2 = b(virtualChildCount - 1);
            if (viewB2 != null) {
                LayoutParams layoutParams2 = (LayoutParams) viewB2.getLayoutParams();
                if (zIsLayoutRtl) {
                    left = viewB2.getLeft();
                    paddingRight = layoutParams2.leftMargin;
                    right = (left - paddingRight) - this.mDividerWidth;
                } else {
                    right = viewB2.getRight() + layoutParams2.rightMargin;
                }
            } else if (zIsLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth();
                paddingRight = getPaddingRight();
                right = (left - paddingRight) - this.mDividerWidth;
            }
            b(canvas, right);
        }
    }

    void b(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    protected boolean c(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    int d(int i) {
        return 0;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.mBaselineAlignedChildIndex);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return bottom + ((LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            a(canvas);
        } else {
            b(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ACCESSIBILITY_CLASS_NAME);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            a(i, i2, i3, i4);
        } else {
            b(i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            a(i, i2);
        } else {
            b(i, i2);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.mBaselineAlignedChildIndex = i;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= GravityCompat.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.mGravity) != i2) {
            this.mGravity = i2 | (this.mGravity & (-8388616));
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        if ((this.mGravity & 112) != i2) {
            this.mGravity = i2 | (this.mGravity & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
