package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    private static final boolean DEBUG = false;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private static Method sGetMaxAvailableHeightMethod;
    private static Method sSetClipToWindowEnabledMethod;
    private static Method sSetEpicenterBoundsMethod;
    DropDownListView c;
    int d;
    final ResizePopupRunnable e;
    final Handler f;
    PopupWindow g;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    private final ListSelectorHider mHideSelector;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    private int mPromptPosition;
    private View mPromptView;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    private class ListSelectorHider implements Runnable {
        ListSelectorHider() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver extends DataSetObserver {
        PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener implements AbsListView.OnScrollListener {
        PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i != 1 || ListPopupWindow.this.isInputMethodNotNeeded() || ListPopupWindow.this.g.getContentView() == null) {
                return;
            }
            ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
            ListPopupWindow.this.e.run();
        }
    }

    private class PopupTouchInterceptor implements View.OnTouchListener {
        PopupTouchInterceptor() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && ListPopupWindow.this.g != null && ListPopupWindow.this.g.isShowing() && x >= 0 && x < ListPopupWindow.this.g.getWidth() && y >= 0 && y < ListPopupWindow.this.g.getHeight()) {
                ListPopupWindow.this.f.postDelayed(ListPopupWindow.this.e, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
            return false;
        }
    }

    private class ResizePopupRunnable implements Runnable {
        ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ListPopupWindow.this.c == null || !ViewCompat.isAttachedToWindow(ListPopupWindow.this.c) || ListPopupWindow.this.c.getCount() <= ListPopupWindow.this.c.getChildCount() || ListPopupWindow.this.c.getChildCount() > ListPopupWindow.this.d) {
                return;
            }
            ListPopupWindow.this.g.setInputMethodMode(2);
            ListPopupWindow.this.show();
        }
    }

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                sSetClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException unused) {
                Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            } catch (NoSuchMethodException unused2) {
                Log.i(TAG, "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            try {
                sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
            } catch (NoSuchMethodException unused3) {
                Log.i(TAG, "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
            }
        }
    }

    public ListPopupWindow(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.d = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mPromptPosition = 0;
        this.e = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = context;
        this.f = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2);
        this.mDropDownHorizontalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.mDropDownVerticalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.g = new AppCompatPopupWindow(context, attributeSet, i, i2);
        this.g.setInputMethodMode(1);
    }

    private int buildDropDown() {
        int measuredHeight;
        int i;
        int iMakeMeasureSpec;
        int i2;
        View view;
        int i3;
        int i4;
        if (this.c == null) {
            Context context = this.mContext;
            this.mShowDropDownRunnable = new Runnable() { // from class: androidx.appcompat.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public void run() {
                    View anchorView = ListPopupWindow.this.getAnchorView();
                    if (anchorView == null || anchorView.getWindowToken() == null) {
                        return;
                    }
                    ListPopupWindow.this.show();
                }
            };
            this.c = a(context, !this.mModal);
            if (this.mDropDownListHighlight != null) {
                this.c.setSelector(this.mDropDownListHighlight);
            }
            this.c.setAdapter(this.mAdapter);
            this.c.setOnItemClickListener(this.mItemClickListener);
            this.c.setFocusable(true);
            this.c.setFocusableInTouchMode(true);
            this.c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view2, int i5, long j) {
                    DropDownListView dropDownListView;
                    if (i5 == -1 || (dropDownListView = ListPopupWindow.this.c) == null) {
                        return;
                    }
                    dropDownListView.setListSelectionHidden(false);
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.c.setOnScrollListener(this.mScrollListener);
            if (this.mItemSelectedListener != null) {
                this.c.setOnItemSelectedListener(this.mItemSelectedListener);
            }
            DropDownListView dropDownListView = this.c;
            View view2 = this.mPromptView;
            if (view2 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.mPromptPosition) {
                    case 0:
                        linearLayout.addView(view2);
                        linearLayout.addView(dropDownListView, layoutParams);
                        break;
                    case 1:
                        linearLayout.addView(dropDownListView, layoutParams);
                        linearLayout.addView(view2);
                        break;
                    default:
                        Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                        break;
                }
                if (this.mDropDownWidth >= 0) {
                    i3 = this.mDropDownWidth;
                    i4 = Integer.MIN_VALUE;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                view2.measure(View.MeasureSpec.makeMeasureSpec(i3, i4), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                measuredHeight = view2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view = linearLayout;
            } else {
                measuredHeight = 0;
                view = dropDownListView;
            }
            this.g.setContentView(view);
        } else {
            View view3 = this.mPromptView;
            if (view3 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                measuredHeight = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                measuredHeight = 0;
            }
        }
        Drawable background = this.g.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            i = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
            }
        } else {
            this.mTempRect.setEmpty();
            i = 0;
        }
        int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.g.getInputMethodMode() == 2);
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return maxAvailableHeight + i;
        }
        switch (this.mDropDownWidth) {
            case -2:
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE);
                break;
            case -1:
                i2 = this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right);
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
                break;
            default:
                i2 = this.mDropDownWidth;
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
                break;
        }
        int iMeasureHeightOfChildrenCompat = this.c.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1);
        if (iMeasureHeightOfChildrenCompat > 0) {
            measuredHeight += i + this.c.getPaddingTop() + this.c.getPaddingBottom();
        }
        return iMeasureHeightOfChildrenCompat + measuredHeight;
    }

    private int getMaxAvailableHeight(View view, int i, boolean z) {
        if (Build.VERSION.SDK_INT > 23) {
            return this.g.getMaxAvailableHeight(view, i, z);
        }
        if (sGetMaxAvailableHeightMethod != null) {
            try {
                return ((Integer) sGetMaxAvailableHeightMethod.invoke(this.g, view, Integer.valueOf(i), Boolean.valueOf(z))).intValue();
            } catch (Exception unused) {
                Log.i(TAG, "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.g.getMaxAvailableHeight(view, i);
    }

    private static boolean isConfirmKey(int i) {
        return i == 66 || i == 23;
    }

    private void removePromptView() {
        if (this.mPromptView != null) {
            ViewParent parent = this.mPromptView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mPromptView);
            }
        }
    }

    private void setPopupClipToScreenEnabled(boolean z) {
        if (Build.VERSION.SDK_INT > 28) {
            this.g.setIsClippedToScreen(z);
        } else if (sSetClipToWindowEnabledMethod != null) {
            try {
                sSetClipToWindowEnabledMethod.invoke(this.g, Boolean.valueOf(z));
            } catch (Exception unused) {
                Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    @NonNull
    DropDownListView a(Context context, boolean z) {
        return new DropDownListView(context, z);
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.c;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.requestLayout();
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view) { // from class: androidx.appcompat.widget.ListPopupWindow.1
            @Override // androidx.appcompat.widget.ForwardingListener
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        this.g.dismiss();
        removePromptView();
        this.g.setContentView(null);
        this.c = null;
        this.f.removeCallbacks(this.e);
    }

    @Nullable
    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.g.getAnimationStyle();
    }

    @Nullable
    public Drawable getBackground() {
        return this.g.getBackground();
    }

    @Nullable
    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public int getInputMethodMode() {
        return this.g.getInputMethodMode();
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    @Nullable
    public ListView getListView() {
        return this.c;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    @Nullable
    public Object getSelectedItem() {
        if (isShowing()) {
            return this.c.getSelectedItem();
        }
        return null;
    }

    public long getSelectedItemId() {
        if (isShowing()) {
            return this.c.getSelectedItemId();
        }
        return Long.MIN_VALUE;
    }

    public int getSelectedItemPosition() {
        if (isShowing()) {
            return this.c.getSelectedItemPosition();
        }
        return -1;
    }

    @Nullable
    public View getSelectedView() {
        if (isShowing()) {
            return this.c.getSelectedView();
        }
        return null;
    }

    public int getSoftInputMode() {
        return this.g.getSoftInputMode();
    }

    public int getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) {
            return this.mDropDownVerticalOffset;
        }
        return 0;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public boolean isInputMethodNotNeeded() {
        return this.g.getInputMethodMode() == 2;
    }

    public boolean isModal() {
        return this.mModal;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.g.isShowing();
    }

    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        int count;
        if (isShowing() && i != 62 && (this.c.getSelectedItemPosition() >= 0 || !isConfirmKey(i))) {
            int selectedItemPosition = this.c.getSelectedItemPosition();
            boolean z = !this.g.isAboveAnchor();
            ListAdapter listAdapter = this.mAdapter;
            int i2 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (listAdapter != null) {
                boolean zAreAllItemsEnabled = listAdapter.areAllItemsEnabled();
                int iLookForSelectablePosition = zAreAllItemsEnabled ? 0 : this.c.lookForSelectablePosition(0, true);
                count = zAreAllItemsEnabled ? listAdapter.getCount() - 1 : this.c.lookForSelectablePosition(listAdapter.getCount() - 1, false);
                i2 = iLookForSelectablePosition;
            } else {
                count = Integer.MIN_VALUE;
            }
            if ((z && i == 19 && selectedItemPosition <= i2) || (!z && i == 20 && selectedItemPosition >= count)) {
                clearListSelection();
                this.g.setInputMethodMode(1);
                show();
                return true;
            }
            this.c.setListSelectionHidden(false);
            if (this.c.onKeyDown(i, keyEvent)) {
                this.g.setInputMethodMode(2);
                this.c.requestFocusFromTouch();
                show();
                if (i != 23 && i != 66) {
                    switch (i) {
                    }
                    return true;
                }
                return true;
            }
            if (z && i == 20) {
                if (selectedItemPosition == count) {
                    return true;
                }
            } else if (!z && i == 19 && selectedItemPosition == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyPreIme(int i, @NonNull KeyEvent keyEvent) {
        if (i != 4 || !isShowing()) {
            return false;
        }
        View view = this.mDropDownAnchorView;
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            KeyEvent.DispatcherState keyDispatcherState = view.getKeyDispatcherState();
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        }
        if (keyEvent.getAction() != 1) {
            return false;
        }
        KeyEvent.DispatcherState keyDispatcherState2 = view.getKeyDispatcherState();
        if (keyDispatcherState2 != null) {
            keyDispatcherState2.handleUpEvent(keyEvent);
        }
        if (!keyEvent.isTracking() || keyEvent.isCanceled()) {
            return false;
        }
        dismiss();
        return true;
    }

    public boolean onKeyUp(int i, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.c.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean zOnKeyUp = this.c.onKeyUp(i, keyEvent);
        if (zOnKeyUp && isConfirmKey(i)) {
            dismiss();
        }
        return zOnKeyUp;
    }

    public boolean performItemClick(int i) {
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener == null) {
            return true;
        }
        DropDownListView dropDownListView = this.c;
        this.mItemClickListener.onItemClick(dropDownListView, dropDownListView.getChildAt(i - dropDownListView.getFirstVisiblePosition()), i, dropDownListView.getAdapter().getItemId(i));
        return true;
    }

    public void postShow() {
        this.f.post(this.mShowDropDownRunnable);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
        if (this.c != null) {
            this.c.setAdapter(this.mAdapter);
        }
    }

    public void setAnchorView(@Nullable View view) {
        this.mDropDownAnchorView = view;
    }

    public void setAnimationStyle(@StyleRes int i) {
        this.g.setAnimationStyle(i);
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.g.setBackgroundDrawable(drawable);
    }

    public void setContentWidth(int i) {
        Drawable background = this.g.getBackground();
        if (background == null) {
            setWidth(i);
        } else {
            background.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + i;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setDropDownAlwaysVisible(boolean z) {
        this.mDropDownAlwaysVisible = z;
    }

    public void setDropDownGravity(int i) {
        this.mDropDownGravity = i;
    }

    public void setEpicenterBounds(@Nullable Rect rect) {
        this.mEpicenterBounds = rect != null ? new Rect(rect) : null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setForceIgnoreOutsideTouch(boolean z) {
        this.mForceIgnoreOutsideTouch = z;
    }

    public void setHeight(int i) {
        if (i < 0 && -2 != i && -1 != i) {
            throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
        }
        this.mDropDownHeight = i;
    }

    public void setHorizontalOffset(int i) {
        this.mDropDownHorizontalOffset = i;
    }

    public void setInputMethodMode(int i) {
        this.g.setInputMethodMode(i);
    }

    public void setListSelector(Drawable drawable) {
        this.mDropDownListHighlight = drawable;
    }

    public void setModal(boolean z) {
        this.mModal = z;
        this.g.setFocusable(z);
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.g.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOverlapAnchor(boolean z) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = z;
    }

    public void setPromptPosition(int i) {
        this.mPromptPosition = i;
    }

    public void setPromptView(@Nullable View view) {
        boolean zIsShowing = isShowing();
        if (zIsShowing) {
            removePromptView();
        }
        this.mPromptView = view;
        if (zIsShowing) {
            show();
        }
    }

    public void setSelection(int i) {
        DropDownListView dropDownListView = this.c;
        if (!isShowing() || dropDownListView == null) {
            return;
        }
        dropDownListView.setListSelectionHidden(false);
        dropDownListView.setSelection(i);
        if (dropDownListView.getChoiceMode() != 0) {
            dropDownListView.setItemChecked(i, true);
        }
    }

    public void setSoftInputMode(int i) {
        this.g.setSoftInputMode(i);
    }

    public void setVerticalOffset(int i) {
        this.mDropDownVerticalOffset = i;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int i) {
        this.mDropDownWidth = i;
    }

    public void setWindowLayoutType(int i) {
        this.mDropDownWindowLayoutType = i;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() {
        int iBuildDropDown = buildDropDown();
        boolean zIsInputMethodNotNeeded = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.g, this.mDropDownWindowLayoutType);
        if (this.g.isShowing()) {
            if (ViewCompat.isAttachedToWindow(getAnchorView())) {
                int width = this.mDropDownWidth == -1 ? -1 : this.mDropDownWidth == -2 ? getAnchorView().getWidth() : this.mDropDownWidth;
                if (this.mDropDownHeight == -1) {
                    if (!zIsInputMethodNotNeeded) {
                        iBuildDropDown = -1;
                    }
                    if (zIsInputMethodNotNeeded) {
                        this.g.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        this.g.setHeight(0);
                    } else {
                        this.g.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        this.g.setHeight(-1);
                    }
                } else if (this.mDropDownHeight != -2) {
                    iBuildDropDown = this.mDropDownHeight;
                }
                this.g.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
                this.g.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, width < 0 ? -1 : width, iBuildDropDown < 0 ? -1 : iBuildDropDown);
                return;
            }
            return;
        }
        int width2 = this.mDropDownWidth == -1 ? -1 : this.mDropDownWidth == -2 ? getAnchorView().getWidth() : this.mDropDownWidth;
        if (this.mDropDownHeight == -1) {
            iBuildDropDown = -1;
        } else if (this.mDropDownHeight != -2) {
            iBuildDropDown = this.mDropDownHeight;
        }
        this.g.setWidth(width2);
        this.g.setHeight(iBuildDropDown);
        setPopupClipToScreenEnabled(true);
        this.g.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
        this.g.setTouchInterceptor(this.mTouchInterceptor);
        if (this.mOverlapAnchorSet) {
            PopupWindowCompat.setOverlapAnchor(this.g, this.mOverlapAnchor);
        }
        if (Build.VERSION.SDK_INT > 28) {
            this.g.setEpicenterBounds(this.mEpicenterBounds);
        } else if (sSetEpicenterBoundsMethod != null) {
            try {
                sSetEpicenterBoundsMethod.invoke(this.g, this.mEpicenterBounds);
            } catch (Exception e) {
                Log.e(TAG, "Could not invoke setEpicenterBounds on PopupWindow", e);
            }
        }
        PopupWindowCompat.showAsDropDown(this.g, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.c.setSelection(-1);
        if (!this.mModal || this.c.isInTouchMode()) {
            clearListSelection();
        }
        if (this.mModal) {
            return;
        }
        this.f.post(this.mHideSelector);
    }
}
