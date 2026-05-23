package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.R;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
class DropDownListView extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    ResolveHoverRunnable a;
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private Field mIsChildViewEnabled;
    private boolean mListSelectionHidden;
    private int mMotionPosition;
    private ListViewAutoScrollHelper mScrollHelper;
    private int mSelectionBottomPadding;
    private int mSelectionLeftPadding;
    private int mSelectionRightPadding;
    private int mSelectionTopPadding;
    private GateKeeperDrawable mSelector;
    private final Rect mSelectorRect;

    private static class GateKeeperDrawable extends DrawableWrapper {
        private boolean mEnabled;

        GateKeeperDrawable(Drawable drawable) {
            super(drawable);
            this.mEnabled = true;
        }

        void a(boolean z) {
            this.mEnabled = z;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void setHotspot(float f, float f2) {
            if (this.mEnabled) {
                super.setHotspot(f, f2);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            if (this.mEnabled) {
                super.setHotspotBounds(i, i2, i3, i4);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public boolean setState(int[] iArr) {
            if (this.mEnabled) {
                return super.setState(iArr);
            }
            return false;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public boolean setVisible(boolean z, boolean z2) {
            if (this.mEnabled) {
                return super.setVisible(z, z2);
            }
            return false;
        }
    }

    private class ResolveHoverRunnable implements Runnable {
        ResolveHoverRunnable() {
        }

        public void cancel() {
            DropDownListView.this.a = null;
            DropDownListView.this.removeCallbacks(this);
        }

        public void post() {
            DropDownListView.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            DropDownListView.this.a = null;
            DropDownListView.this.drawableStateChanged();
        }
    }

    DropDownListView(@NonNull Context context, boolean z) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.mSelectorRect = new Rect();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mHijackFocus = z;
        setCacheColorHint(0);
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        drawableStateChanged();
        View childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.setPressed(false);
        }
        if (this.mClickAnimation != null) {
            this.mClickAnimation.cancel();
            this.mClickAnimation = null;
        }
    }

    private void clickPressedItem(View view, int i) {
        performItemClick(view, i, getItemIdAtPosition(i));
    }

    private void drawSelectorCompat(Canvas canvas) {
        Drawable selector;
        if (this.mSelectorRect.isEmpty() || (selector = getSelector()) == null) {
            return;
        }
        selector.setBounds(this.mSelectorRect);
        selector.draw(canvas);
    }

    private void positionSelectorCompat(int i, View view) {
        Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        try {
            boolean z = this.mIsChildViewEnabled.getBoolean(this);
            if (view.isEnabled() != z) {
                this.mIsChildViewEnabled.set(this, Boolean.valueOf(!z));
                if (i != -1) {
                    refreshDrawableState();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void positionSelectorLikeFocusCompat(int i, View view) {
        Drawable selector = getSelector();
        boolean z = (selector == null || i == -1) ? false : true;
        if (z) {
            selector.setVisible(false, false);
        }
        positionSelectorCompat(i, view);
        if (z) {
            Rect rect = this.mSelectorRect;
            float fExactCenterX = rect.exactCenterX();
            float fExactCenterY = rect.exactCenterY();
            selector.setVisible(getVisibility() == 0, false);
            DrawableCompat.setHotspot(selector, fExactCenterX, fExactCenterY);
        }
    }

    private void positionSelectorLikeTouchCompat(int i, View view, float f, float f2) {
        positionSelectorLikeFocusCompat(i, view);
        Drawable selector = getSelector();
        if (selector == null || i == -1) {
            return;
        }
        DrawableCompat.setHotspot(selector, f, f2);
    }

    private void setPressedItem(View view, int i, float f, float f2) {
        View childAt;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(f, f2);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        if (this.mMotionPosition != -1 && (childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition())) != null && childAt != view && childAt.isPressed()) {
            childAt.setPressed(false);
        }
        this.mMotionPosition = i;
        float left = f - view.getLeft();
        float top = f2 - view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(left, top);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        positionSelectorLikeTouchCompat(i, view, f, f2);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    private void setSelectorEnabled(boolean z) {
        if (this.mSelector != null) {
            this.mSelector.a(z);
        }
    }

    private boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState;
    }

    private void updateSelectorStateCompat() {
        Drawable selector = getSelector();
        if (selector != null && touchModeDrawsInPressedStateCompat() && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        drawSelectorCompat(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.a != null) {
            return;
        }
        super.drawableStateChanged();
        setSelectorEnabled(true);
        updateSelectorStateCompat();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    public int lookForSelectablePosition(int i, boolean z) {
        int iMin;
        ListAdapter adapter = getAdapter();
        if (adapter != null && !isInTouchMode()) {
            int count = adapter.getCount();
            if (!getAdapter().areAllItemsEnabled()) {
                if (z) {
                    iMin = Math.max(0, i);
                    while (iMin < count && !adapter.isEnabled(iMin)) {
                        iMin++;
                    }
                } else {
                    iMin = Math.min(i, count - 1);
                    while (iMin >= 0 && !adapter.isEnabled(iMin)) {
                        iMin--;
                    }
                }
                if (iMin < 0 || iMin >= count) {
                    return -1;
                }
                return iMin;
            }
            if (i >= 0 && i < count) {
                return i;
            }
        }
        return -1;
    }

    public int measureHeightOfChildrenCompat(int i, int i2, int i3, int i4, int i5) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int i6 = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int measuredHeight = i6;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        View view = null;
        while (i7 < count) {
            int itemViewType = adapter.getItemViewType(i7);
            if (itemViewType != i8) {
                view = null;
                i8 = itemViewType;
            }
            view = adapter.getView(i7, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            view.measure(i, layoutParams.height > 0 ? View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i7 > 0) {
                measuredHeight += dividerHeight;
            }
            measuredHeight += view.getMeasuredHeight();
            if (measuredHeight >= i4) {
                return (i5 < 0 || i7 <= i5 || i9 <= 0 || measuredHeight == i4) ? i4 : i9;
            }
            if (i5 >= 0 && i7 >= i5) {
                i9 = measuredHeight;
            }
            i7++;
        }
        return measuredHeight;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.a = null;
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onForwardedEvent(MotionEvent motionEvent, int i) {
        boolean z;
        int iFindPointerIndex;
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        switch (actionMasked) {
            case 1:
                z = false;
                iFindPointerIndex = motionEvent.findPointerIndex(i);
                if (iFindPointerIndex < 0) {
                    int x = (int) motionEvent.getX(iFindPointerIndex);
                    int y = (int) motionEvent.getY(iFindPointerIndex);
                    int iPointToPosition = pointToPosition(x, y);
                    if (iPointToPosition != -1) {
                        View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                        setPressedItem(childAt, iPointToPosition, x, y);
                        if (actionMasked == 1) {
                            clickPressedItem(childAt, iPointToPosition);
                        }
                        z2 = false;
                        z = true;
                    } else {
                        z2 = true;
                    }
                } else {
                    z2 = false;
                    z = false;
                }
                break;
            case 2:
                z = true;
                iFindPointerIndex = motionEvent.findPointerIndex(i);
                if (iFindPointerIndex < 0) {
                }
                break;
            case 3:
                break;
            default:
                z2 = false;
                z = true;
                break;
        }
        if (!z || z2) {
            clearPressedItem();
        }
        if (z) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new ListViewAutoScrollHelper(this);
            }
            this.mScrollHelper.setEnabled(true);
            this.mScrollHelper.onTouch(this, motionEvent);
        } else if (this.mScrollHelper != null) {
            this.mScrollHelper.setEnabled(false);
        }
        return z;
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.a == null) {
            this.a = new ResolveHoverRunnable();
            this.a.post();
        }
        boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (iPointToPosition != -1 && iPointToPosition != getSelectedItemPosition()) {
                View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop());
                }
                updateSelectorStateCompat();
            }
        } else {
            setSelection(-1);
        }
        return zOnHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mMotionPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (this.a != null) {
            this.a.cancel();
        }
        return super.onTouchEvent(motionEvent);
    }

    void setListSelectionHidden(boolean z) {
        this.mListSelectionHidden = z;
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        this.mSelector = drawable != null ? new GateKeeperDrawable(drawable) : null;
        super.setSelector(this.mSelector);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.mSelectionLeftPadding = rect.left;
        this.mSelectionTopPadding = rect.top;
        this.mSelectionRightPadding = rect.right;
        this.mSelectionBottomPadding = rect.bottom;
    }
}
