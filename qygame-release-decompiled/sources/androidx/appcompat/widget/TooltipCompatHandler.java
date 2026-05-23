package androidx.appcompat.widget;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class TooltipCompatHandler implements View.OnAttachStateChangeListener, View.OnHoverListener, View.OnLongClickListener {
    private static final long HOVER_HIDE_TIMEOUT_MS = 15000;
    private static final long HOVER_HIDE_TIMEOUT_SHORT_MS = 3000;
    private static final long LONG_CLICK_HIDE_TIMEOUT_MS = 2500;
    private static final String TAG = "TooltipCompatHandler";
    private static TooltipCompatHandler sActiveHandler;
    private static TooltipCompatHandler sPendingHandler;
    private final View mAnchor;
    private int mAnchorX;
    private int mAnchorY;
    private boolean mFromTouch;
    private final int mHoverSlop;
    private TooltipPopup mPopup;
    private final CharSequence mTooltipText;
    private final Runnable mShowRunnable = new Runnable() { // from class: androidx.appcompat.widget.TooltipCompatHandler.1
        @Override // java.lang.Runnable
        public void run() {
            TooltipCompatHandler.this.a(false);
        }
    };
    private final Runnable mHideRunnable = new Runnable() { // from class: androidx.appcompat.widget.TooltipCompatHandler.2
        @Override // java.lang.Runnable
        public void run() {
            TooltipCompatHandler.this.a();
        }
    };

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mHoverSlop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(this.mAnchor.getContext()));
        clearAnchorPos();
        this.mAnchor.setOnLongClickListener(this);
        this.mAnchor.setOnHoverListener(this);
    }

    private void cancelPendingShow() {
        this.mAnchor.removeCallbacks(this.mShowRunnable);
    }

    private void clearAnchorPos() {
        this.mAnchorX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mAnchorY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    private void scheduleShow() {
        this.mAnchor.postDelayed(this.mShowRunnable, ViewConfiguration.getLongPressTimeout());
    }

    private static void setPendingHandler(TooltipCompatHandler tooltipCompatHandler) {
        if (sPendingHandler != null) {
            sPendingHandler.cancelPendingShow();
        }
        sPendingHandler = tooltipCompatHandler;
        if (sPendingHandler != null) {
            sPendingHandler.scheduleShow();
        }
    }

    public static void setTooltipText(View view, CharSequence charSequence) {
        if (sPendingHandler != null && sPendingHandler.mAnchor == view) {
            setPendingHandler(null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new TooltipCompatHandler(view, charSequence);
            return;
        }
        if (sActiveHandler != null && sActiveHandler.mAnchor == view) {
            sActiveHandler.a();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }

    private boolean updateAnchorPos(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (Math.abs(x - this.mAnchorX) <= this.mHoverSlop && Math.abs(y - this.mAnchorY) <= this.mHoverSlop) {
            return false;
        }
        this.mAnchorX = x;
        this.mAnchorY = y;
        return true;
    }

    void a() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            if (this.mPopup != null) {
                this.mPopup.a();
                this.mPopup = null;
                clearAnchorPos();
                this.mAnchor.removeOnAttachStateChangeListener(this);
            } else {
                Log.e(TAG, "sActiveHandler.mPopup == null");
            }
        }
        if (sPendingHandler == this) {
            setPendingHandler(null);
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable);
    }

    void a(boolean z) {
        long longPressTimeout;
        if (ViewCompat.isAttachedToWindow(this.mAnchor)) {
            setPendingHandler(null);
            if (sActiveHandler != null) {
                sActiveHandler.a();
            }
            sActiveHandler = this;
            this.mFromTouch = z;
            this.mPopup = new TooltipPopup(this.mAnchor.getContext());
            this.mPopup.a(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
            this.mAnchor.addOnAttachStateChangeListener(this);
            if (this.mFromTouch) {
                longPressTimeout = LONG_CLICK_HIDE_TIMEOUT_MS;
            } else {
                longPressTimeout = ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1 ? HOVER_HIDE_TIMEOUT_SHORT_MS : HOVER_HIDE_TIMEOUT_MS) - ((long) ViewConfiguration.getLongPressTimeout());
            }
            this.mAnchor.removeCallbacks(this.mHideRunnable);
            this.mAnchor.postDelayed(this.mHideRunnable, longPressTimeout);
        }
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mAnchor.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                clearAnchorPos();
                a();
            }
        } else if (this.mAnchor.isEnabled() && this.mPopup == null && updateAnchorPos(motionEvent)) {
            setPendingHandler(this);
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        a(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        a();
    }
}
