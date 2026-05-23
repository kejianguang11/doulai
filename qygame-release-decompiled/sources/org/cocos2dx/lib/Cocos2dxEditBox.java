package org.cocos2dx.lib;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;
import com.mobile.auth.gatewayauth.Constant;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxEditBox {
    private static final int DARK_GREEN = Color.parseColor("#1fa014");
    private static final int DARK_GREEN_PRESS = Color.parseColor("#008e26");
    private static Cocos2dxEditBox sThis = null;
    private Cocos2dxActivity mActivity;
    private RelativeLayout.LayoutParams mButtonParams;
    private Cocos2dxEditText mEditText = null;
    private Button mButton = null;
    private String mButtonTitle = null;
    private boolean mConfirmHold = true;
    private RelativeLayout mButtonLayout = null;
    private int mEditTextID = 1;
    private int mButtonLayoutID = 2;

    class Cocos2dxEditText extends EditText {
        private final String TAG;
        private boolean keyboardVisible;
        private boolean mIsMultiLine;
        private int mLineColor;
        private float mLineWidth;
        private int mOrientation;
        private Paint mPaint;
        private int mScreenHeight;
        private TextWatcher mTextWatcher;
        private int mTopMargin;

        public Cocos2dxEditText(Cocos2dxActivity cocos2dxActivity) {
            super(cocos2dxActivity);
            this.TAG = "Cocos2dxEditBox";
            this.mIsMultiLine = false;
            this.mTextWatcher = null;
            this.mLineColor = Cocos2dxEditBox.DARK_GREEN;
            this.mLineWidth = 2.0f;
            this.keyboardVisible = false;
            this.mTopMargin = 0;
            setBackground(null);
            setTextColor(ViewCompat.MEASURED_STATE_MASK);
            this.mScreenHeight = ((WindowManager) cocos2dxActivity.getSystemService("window")).getDefaultDisplay().getHeight();
            this.mPaint = new Paint();
            this.mPaint.setStrokeWidth(this.mLineWidth);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mLineColor);
            this.mOrientation = getResources().getConfiguration().orientation;
            this.mTextWatcher = new TextWatcher() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.Cocos2dxEditText.1
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    Cocos2dxEditBox.this.onKeyboardInput(editable.toString());
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            };
            registKeyboardVisible();
        }

        private void addListeners() {
            setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.Cocos2dxEditText.2
                @Override // android.widget.TextView.OnEditorActionListener
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (Cocos2dxEditText.this.mIsMultiLine) {
                        return false;
                    }
                    Cocos2dxEditBox.this.hide();
                    return false;
                }
            });
            addTextChangedListener(this.mTextWatcher);
        }

        private void registKeyboardVisible() {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.Cocos2dxEditText.3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    Cocos2dxEditText.this.getWindowVisibleDisplayFrame(rect);
                    if (Cocos2dxEditText.this.getRootView().getHeight() - (rect.bottom - rect.top) > Cocos2dxEditText.this.mScreenHeight / 4) {
                        if (!Cocos2dxEditText.this.keyboardVisible) {
                            Cocos2dxEditText.this.keyboardVisible = true;
                        }
                    } else if (Cocos2dxEditText.this.keyboardVisible) {
                        Cocos2dxEditText.this.keyboardVisible = false;
                        Cocos2dxEditBox.this.hide();
                    }
                    if (Cocos2dxEditText.this.mTopMargin != 0 || rect.bottom == Cocos2dxEditText.this.getRootView().getHeight()) {
                        return;
                    }
                    Cocos2dxEditText.this.setTopMargin(rect.bottom);
                }
            });
        }

        private void removeListeners() {
            setOnEditorActionListener(null);
            removeTextChangedListener(this.mTextWatcher);
        }

        private void setConfirmType(String str) {
            Cocos2dxEditBox cocos2dxEditBox;
            Resources resources;
            int i;
            if (str.contentEquals("done")) {
                setImeOptions(268435462);
                cocos2dxEditBox = Cocos2dxEditBox.this;
                resources = Cocos2dxEditBox.this.mActivity.getResources();
                i = R.string.done;
            } else if (str.contentEquals("next")) {
                setImeOptions(268435461);
                cocos2dxEditBox = Cocos2dxEditBox.this;
                resources = Cocos2dxEditBox.this.mActivity.getResources();
                i = R.string.next;
            } else if (str.contentEquals("search")) {
                setImeOptions(268435459);
                cocos2dxEditBox = Cocos2dxEditBox.this;
                resources = Cocos2dxEditBox.this.mActivity.getResources();
                i = R.string.search;
            } else if (str.contentEquals("go")) {
                setImeOptions(268435458);
                cocos2dxEditBox = Cocos2dxEditBox.this;
                resources = Cocos2dxEditBox.this.mActivity.getResources();
                i = R.string.go;
            } else {
                if (!str.contentEquals("send")) {
                    Cocos2dxEditBox.this.mButtonTitle = null;
                    Log.e("Cocos2dxEditBox", "unknown confirm type " + str);
                    return;
                }
                setImeOptions(268435460);
                cocos2dxEditBox = Cocos2dxEditBox.this;
                resources = Cocos2dxEditBox.this.mActivity.getResources();
                i = R.string.send;
            }
            cocos2dxEditBox.mButtonTitle = resources.getString(i);
        }

        private void setInputType(String str, boolean z) {
            int i;
            if (str.contentEquals("text")) {
                i = z ? 131073 : 1;
            } else if (str.contentEquals(NotificationCompat.CATEGORY_EMAIL)) {
                i = 32;
            } else if (str.contentEquals(Constant.LOGIN_ACTIVITY_NUMBER)) {
                i = 12290;
            } else if (str.contentEquals("phone")) {
                i = 3;
            } else {
                if (!str.contentEquals("password")) {
                    Log.e("Cocos2dxEditBox", "unknown input type " + str);
                    return;
                }
                i = 129;
            }
            setInputType(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTopMargin(int i) {
            this.mTopMargin = i;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) Cocos2dxEditBox.this.mEditText.getLayoutParams();
            layoutParams.topMargin = this.mTopMargin - getHeight();
            setLayoutParams(layoutParams);
            requestLayout();
        }

        public void hide() {
            Cocos2dxEditBox.this.mEditText.setVisibility(4);
            removeListeners();
        }

        @Override // android.widget.TextView, android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            int i = configuration.orientation;
            if (this.mOrientation != i) {
                this.mOrientation = i;
                this.mTopMargin = 0;
            }
        }

        @Override // android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            int paddingBottom = getPaddingBottom() / 2;
            canvas.drawLine(getScrollX(), (getHeight() - paddingBottom) - this.mLineWidth, getScrollX() + getWidth(), (getHeight() - paddingBottom) - this.mLineWidth, this.mPaint);
            super.onDraw(canvas);
        }

        public void show(String str, int i, boolean z, boolean z2, String str2, String str3) {
            this.mIsMultiLine = z;
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
            setText(str);
            setSelection(getText().length() >= str.length() ? str.length() : getText().length());
            setConfirmType(str2);
            setInputType(str3, this.mIsMultiLine);
            setVisibility(0);
            requestFocus();
            addListeners();
        }
    }

    public Cocos2dxEditBox(Cocos2dxActivity cocos2dxActivity, FrameLayout frameLayout) {
        this.mActivity = null;
        sThis = this;
        this.mActivity = cocos2dxActivity;
        addItems(cocos2dxActivity, frameLayout);
    }

    private void addButton(Cocos2dxActivity cocos2dxActivity, RelativeLayout relativeLayout) {
        this.mButton = new Button(cocos2dxActivity);
        this.mButtonParams = new RelativeLayout.LayoutParams(-2, -2);
        this.mButton.setTextColor(-1);
        this.mButton.setBackground(getRoundRectShape());
        this.mButtonLayout = new RelativeLayout(Cocos2dxHelper.getActivity());
        this.mButtonLayout.setVisibility(4);
        this.mButtonLayout.setBackgroundColor(-1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(8, this.mEditTextID);
        layoutParams.addRule(6, this.mEditTextID);
        this.mButtonLayout.addView(this.mButton, this.mButtonParams);
        this.mButtonLayout.setId(this.mButtonLayoutID);
        relativeLayout.addView(this.mButtonLayout, layoutParams);
        this.mButton.setOnClickListener(new View.OnClickListener() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Cocos2dxEditBox.this.onKeyboardConfirm(Cocos2dxEditBox.this.mEditText.getText().toString());
                if (Cocos2dxEditBox.this.mConfirmHold) {
                    return;
                }
                Cocos2dxEditBox.this.hide();
            }
        });
    }

    private void addEditText(Cocos2dxActivity cocos2dxActivity, RelativeLayout relativeLayout) {
        this.mEditText = new Cocos2dxEditText(cocos2dxActivity);
        this.mEditText.setVisibility(4);
        this.mEditText.setBackgroundColor(-1);
        this.mEditText.setId(this.mEditTextID);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(0, this.mButtonLayoutID);
        relativeLayout.addView(this.mEditText, layoutParams);
    }

    private void addItems(Cocos2dxActivity cocos2dxActivity, FrameLayout frameLayout) {
        RelativeLayout relativeLayout = new RelativeLayout(cocos2dxActivity);
        relativeLayout.setFitsSystemWindows(true);
        addEditText(cocos2dxActivity, relativeLayout);
        addButton(cocos2dxActivity, relativeLayout);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        frameLayout.addView(relativeLayout, layoutParams);
    }

    private void closeKeyboard() {
        Cocos2dxActivity cocos2dxActivity = this.mActivity;
        Cocos2dxActivity cocos2dxActivity2 = this.mActivity;
        ((InputMethodManager) cocos2dxActivity.getSystemService("input_method")).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        onKeyboardComplete(this.mEditText.getText().toString());
    }

    public static void complete() {
        sThis.hide();
    }

    private Drawable getRoundRectShape() {
        float f = 7;
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{f, f, f, f, f, f, f, f}, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(roundRectShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(DARK_GREEN);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable();
        shapeDrawable2.setShape(roundRectShape);
        shapeDrawable2.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable2.getPaint().setColor(DARK_GREEN_PRESS);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, shapeDrawable2);
        stateListDrawable.addState(new int[0], shapeDrawable);
        return stateListDrawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        Utils.hideVirtualButton();
        this.mEditText.hide();
        this.mButtonLayout.setVisibility(4);
        closeKeyboard();
        this.mActivity.getGLSurfaceView().requestFocus();
        this.mActivity.getGLSurfaceView().setStopHandleTouchAndKeyEvents(false);
    }

    private static void hideNative() {
        if (sThis != null) {
            sThis.mActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.3
                @Override // java.lang.Runnable
                public void run() {
                    Cocos2dxEditBox.sThis.hide();
                }
            });
        }
    }

    private void onKeyboardComplete(final String str) {
        this.mActivity.getGLSurfaceView().requestFocus();
        this.mActivity.getGLSurfaceView().setStopHandleTouchAndKeyEvents(false);
        this.mActivity.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.5
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxEditBox.onKeyboardCompleteNative(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onKeyboardCompleteNative(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public void onKeyboardConfirm(final String str) {
        this.mActivity.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.6
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxEditBox.onKeyboardConfirmNative(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onKeyboardConfirmNative(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public void onKeyboardInput(final String str) {
        this.mActivity.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.4
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxEditBox.onKeyboardInputNative(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onKeyboardInputNative(String str);

    private void openKeyboard() {
        Cocos2dxActivity cocos2dxActivity = this.mActivity;
        Cocos2dxActivity cocos2dxActivity2 = this.mActivity;
        ((InputMethodManager) cocos2dxActivity.getSystemService("input_method")).showSoftInput(this.mEditText, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show(String str, int i, boolean z, boolean z2, String str2, String str3) {
        this.mConfirmHold = z2;
        this.mEditText.show(str, i, z, z2, str2, str3);
        int paddingBottom = this.mEditText.getPaddingBottom();
        int paddingTop = this.mEditText.getPaddingTop();
        this.mEditText.setPadding(paddingTop, paddingTop, paddingTop, paddingBottom);
        this.mButton.setText(this.mButtonTitle);
        if (TextUtils.isEmpty(this.mButtonTitle)) {
            this.mButton.setPadding(0, 0, 0, 0);
            this.mButtonParams.setMargins(0, 0, 0, 0);
            this.mButtonLayout.setVisibility(4);
        } else {
            int paddingBottom2 = this.mEditText.getPaddingBottom() / 2;
            this.mButton.setPadding(paddingTop, paddingBottom2, paddingTop, paddingBottom2);
            this.mButtonParams.setMargins(0, paddingBottom2, 2, 0);
            this.mButtonLayout.setVisibility(0);
        }
        this.mActivity.getGLSurfaceView().setStopHandleTouchAndKeyEvents(true);
        openKeyboard();
    }

    private static void showNative(final String str, final int i, final boolean z, final boolean z2, final String str2, final String str3) {
        if (sThis != null) {
            sThis.mActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxEditBox.2
                @Override // java.lang.Runnable
                public void run() {
                    Cocos2dxEditBox.sThis.show(str, i, z, z2, str2, str3);
                }
            });
        }
    }
}
