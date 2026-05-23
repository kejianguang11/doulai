package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class AlertController {
    final AppCompatDialog a;
    ListView b;
    Button c;
    Message d;
    Button e;
    Message f;
    Button g;
    Message h;
    NestedScrollView i;
    ListAdapter j;
    int l;
    int m;
    private int mAlertDialogLayout;
    private final int mButtonIconDimen;
    private Drawable mButtonNegativeIcon;
    private CharSequence mButtonNegativeText;
    private Drawable mButtonNeutralIcon;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelSideLayout;
    private Drawable mButtonPositiveIcon;
    private CharSequence mButtonPositiveText;
    private final Context mContext;
    private View mCustomTitleView;
    private Drawable mIcon;
    private ImageView mIconView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private boolean mShowTitle;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private int mViewSpacingTop;
    private final Window mWindow;
    int n;
    int o;
    Handler p;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    int k = -1;
    private int mButtonPanelLayoutHint = 0;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: androidx.appcompat.app.AlertController.1
        /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onClick(View view) {
            Message messageObtain;
            Message message;
            if (view == AlertController.this.c && AlertController.this.d != null) {
                message = AlertController.this.d;
            } else if (view == AlertController.this.e && AlertController.this.f != null) {
                message = AlertController.this.f;
            } else {
                if (view != AlertController.this.g || AlertController.this.h == null) {
                    messageObtain = null;
                    if (messageObtain != null) {
                        messageObtain.sendToTarget();
                    }
                    AlertController.this.p.obtainMessage(1, AlertController.this.a).sendToTarget();
                }
                message = AlertController.this.h;
            }
            messageObtain = Message.obtain(message);
            if (messageObtain != null) {
            }
            AlertController.this.p.obtainMessage(1, AlertController.this.a).sendToTarget();
        }
    };

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void createListView(final AlertController alertController) {
            ListAdapter simpleCursorAdapter;
            AdapterView.OnItemClickListener onItemClickListener;
            final RecycleListView recycleListView = (RecycleListView) this.mInflater.inflate(alertController.l, (ViewGroup) null);
            if (this.mIsMultiChoice) {
                simpleCursorAdapter = this.mCursor == null ? new ArrayAdapter<CharSequence>(this.mContext, alertController.m, R.id.text1, this.mItems) { // from class: androidx.appcompat.app.AlertController.AlertParams.1
                    @Override // android.widget.ArrayAdapter, android.widget.Adapter
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        View view2 = super.getView(i, view, viewGroup);
                        if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[i]) {
                            recycleListView.setItemChecked(i, true);
                        }
                        return view2;
                    }
                } : new CursorAdapter(this.mContext, this.mCursor, false) { // from class: androidx.appcompat.app.AlertController.AlertParams.2
                    private final int mIsCheckedIndex;
                    private final int mLabelIndex;

                    {
                        Cursor cursor = getCursor();
                        this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                    }

                    @Override // android.widget.CursorAdapter
                    public void bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.mLabelIndex));
                        recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                    }

                    @Override // android.widget.CursorAdapter
                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return AlertParams.this.mInflater.inflate(alertController.m, viewGroup, false);
                    }
                };
            } else {
                int i = this.mIsSingleChoice ? alertController.n : alertController.o;
                simpleCursorAdapter = this.mCursor != null ? new SimpleCursorAdapter(this.mContext, i, this.mCursor, new String[]{this.mLabelColumn}, new int[]{R.id.text1}) : this.mAdapter != null ? this.mAdapter : new CheckedItemAdapter(this.mContext, i, R.id.text1, this.mItems);
            }
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.j = simpleCursorAdapter;
            alertController.k = this.mCheckedItem;
            if (this.mOnClickListener == null) {
                if (this.mOnCheckboxClickListener != null) {
                    onItemClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                            if (AlertParams.this.mCheckedItems != null) {
                                AlertParams.this.mCheckedItems[i2] = recycleListView.isItemChecked(i2);
                            }
                            AlertParams.this.mOnCheckboxClickListener.onClick(alertController.a, i2, recycleListView.isItemChecked(i2));
                        }
                    };
                }
                if (this.mOnItemSelectedListener != null) {
                    recycleListView.setOnItemSelectedListener(this.mOnItemSelectedListener);
                }
                if (!this.mIsSingleChoice) {
                    recycleListView.setChoiceMode(1);
                } else if (this.mIsMultiChoice) {
                    recycleListView.setChoiceMode(2);
                }
                alertController.b = recycleListView;
            }
            onItemClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                    AlertParams.this.mOnClickListener.onClick(alertController.a, i2);
                    if (AlertParams.this.mIsSingleChoice) {
                        return;
                    }
                    alertController.a.dismiss();
                }
            };
            recycleListView.setOnItemClickListener(onItemClickListener);
            if (this.mOnItemSelectedListener != null) {
            }
            if (!this.mIsSingleChoice) {
            }
            alertController.b = recycleListView;
        }

        public void apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null, this.mPositiveButtonIcon);
            }
            if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null, this.mNegativeButtonIcon);
            }
            if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null, this.mNeutralButtonIcon);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            if (this.mView == null) {
                if (this.mViewLayoutResId != 0) {
                    alertController.setView(this.mViewLayoutResId);
                }
            } else if (this.mViewSpacingSpecified) {
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            } else {
                alertController.setView(this.mView);
            }
        }
    }

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ((DialogInterface) message.obj).dismiss();
            }
            switch (i) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
                    break;
            }
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.appcompat.R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = typedArrayObtainStyledAttributes.getDimensionPixelOffset(androidx.appcompat.R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.mPaddingTopNoTitle = typedArrayObtainStyledAttributes.getDimensionPixelOffset(androidx.appcompat.R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            if (z2 && z) {
                return;
            }
            setPadding(getPaddingLeft(), z ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z2 ? getPaddingBottom() : this.mPaddingBottomNoButtons);
        }
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.a = appCompatDialog;
        this.mWindow = window;
        this.p = new ButtonHandler(appCompatDialog);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, androidx.appcompat.R.styleable.AlertDialog, androidx.appcompat.R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.l = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_listLayout, 0);
        this.m = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.n = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.o = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AlertDialog_showTitle, true);
        this.mButtonIconDimen = typedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.AlertDialog_buttonIconDimen, 0);
        typedArrayObtainStyledAttributes.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    static void a(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4);
        }
    }

    static boolean a(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (a(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View view, @Nullable View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout != 0 && this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    private void setScrollIndicators(ViewGroup viewGroup, View view, int i, int i2) {
        final View viewFindViewById = this.mWindow.findViewById(androidx.appcompat.R.id.scrollIndicatorUp);
        View viewFindViewById2 = this.mWindow.findViewById(androidx.appcompat.R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, i, i2);
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById);
            }
            if (viewFindViewById2 != null) {
                viewGroup.removeView(viewFindViewById2);
                return;
            }
            return;
        }
        final View view2 = null;
        if (viewFindViewById != null && (i & 1) == 0) {
            viewGroup.removeView(viewFindViewById);
            viewFindViewById = null;
        }
        if (viewFindViewById2 == null || (i & 2) != 0) {
            view2 = viewFindViewById2;
        } else {
            viewGroup.removeView(viewFindViewById2);
        }
        if (viewFindViewById == null && view2 == null) {
            return;
        }
        if (this.mMessage != null) {
            this.i.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: androidx.appcompat.app.AlertController.2
                @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                public void onScrollChange(NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
                    AlertController.a(nestedScrollView, viewFindViewById, view2);
                }
            });
            this.i.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.3
                @Override // java.lang.Runnable
                public void run() {
                    AlertController.a(AlertController.this.i, viewFindViewById, view2);
                }
            });
        } else {
            if (this.b != null) {
                this.b.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: androidx.appcompat.app.AlertController.4
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView absListView, int i3, int i4, int i5) {
                        AlertController.a(absListView, viewFindViewById, view2);
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView absListView, int i3) {
                    }
                });
                this.b.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AlertController.a(AlertController.this.b, viewFindViewById, view2);
                    }
                });
                return;
            }
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById);
            }
            if (view2 != null) {
                viewGroup.removeView(view2);
            }
        }
    }

    private void setupButtons(ViewGroup viewGroup) {
        int i;
        Button button;
        this.c = (Button) viewGroup.findViewById(R.id.button1);
        this.c.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText) && this.mButtonPositiveIcon == null) {
            this.c.setVisibility(8);
            i = 0;
        } else {
            this.c.setText(this.mButtonPositiveText);
            if (this.mButtonPositiveIcon != null) {
                this.mButtonPositiveIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen);
                this.c.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null);
            }
            this.c.setVisibility(0);
            i = 1;
        }
        this.e = (Button) viewGroup.findViewById(R.id.button2);
        this.e.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText) && this.mButtonNegativeIcon == null) {
            this.e.setVisibility(8);
        } else {
            this.e.setText(this.mButtonNegativeText);
            if (this.mButtonNegativeIcon != null) {
                this.mButtonNegativeIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen);
                this.e.setCompoundDrawables(this.mButtonNegativeIcon, null, null, null);
            }
            this.e.setVisibility(0);
            i |= 2;
        }
        this.g = (Button) viewGroup.findViewById(R.id.button3);
        this.g.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText) && this.mButtonNeutralIcon == null) {
            this.g.setVisibility(8);
        } else {
            this.g.setText(this.mButtonNeutralText);
            if (this.mButtonNeutralIcon != null) {
                this.mButtonNeutralIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen);
                this.g.setCompoundDrawables(this.mButtonNeutralIcon, null, null, null);
            }
            this.g.setVisibility(0);
            i |= 4;
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i == 1) {
                button = this.c;
            } else if (i == 2) {
                button = this.e;
            } else if (i == 4) {
                button = this.g;
            }
            centerButton(button);
        }
        if (i != 0) {
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        this.i = (NestedScrollView) this.mWindow.findViewById(androidx.appcompat.R.id.scrollView);
        this.i.setFocusable(false);
        this.i.setNestedScrollingEnabled(false);
        this.mMessageView = (TextView) viewGroup.findViewById(R.id.message);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.setText(this.mMessage);
            return;
        }
        this.mMessageView.setVisibility(8);
        this.i.removeView(this.mMessageView);
        if (this.b == null) {
            viewGroup.setVisibility(8);
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) this.i.getParent();
        int iIndexOfChild = viewGroup2.indexOfChild(this.i);
        viewGroup2.removeViewAt(iIndexOfChild);
        viewGroup2.addView(this.b, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View viewInflate = this.mView != null ? this.mView : this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null;
        boolean z = viewInflate != null;
        if (!z || !a(viewInflate)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!z) {
            viewGroup.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(androidx.appcompat.R.id.custom);
        frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.b != null) {
            ((LinearLayoutCompat.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f;
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(androidx.appcompat.R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView) this.mWindow.findViewById(R.id.icon);
        if (!(!TextUtils.isEmpty(this.mTitle)) || !this.mShowTitle) {
            this.mWindow.findViewById(androidx.appcompat.R.id.title_template).setVisibility(8);
            this.mIconView.setVisibility(8);
            viewGroup.setVisibility(8);
            return;
        }
        this.mTitleView = (TextView) this.mWindow.findViewById(androidx.appcompat.R.id.alertTitle);
        this.mTitleView.setText(this.mTitle);
        if (this.mIconId != 0) {
            this.mIconView.setImageResource(this.mIconId);
        } else if (this.mIcon != null) {
            this.mIconView.setImageDrawable(this.mIcon);
        } else {
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupView() {
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3 = this.mWindow.findViewById(androidx.appcompat.R.id.parentPanel);
        View viewFindViewById4 = viewFindViewById3.findViewById(androidx.appcompat.R.id.topPanel);
        View viewFindViewById5 = viewFindViewById3.findViewById(androidx.appcompat.R.id.contentPanel);
        View viewFindViewById6 = viewFindViewById3.findViewById(androidx.appcompat.R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById3.findViewById(androidx.appcompat.R.id.customPanel);
        setupCustomContent(viewGroup);
        View viewFindViewById7 = viewGroup.findViewById(androidx.appcompat.R.id.topPanel);
        View viewFindViewById8 = viewGroup.findViewById(androidx.appcompat.R.id.contentPanel);
        View viewFindViewById9 = viewGroup.findViewById(androidx.appcompat.R.id.buttonPanel);
        ViewGroup viewGroupResolvePanel = resolvePanel(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupResolvePanel2 = resolvePanel(viewFindViewById8, viewFindViewById5);
        ViewGroup viewGroupResolvePanel3 = resolvePanel(viewFindViewById9, viewFindViewById6);
        setupContent(viewGroupResolvePanel2);
        setupButtons(viewGroupResolvePanel3);
        setupTitle(viewGroupResolvePanel);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (viewGroupResolvePanel == null || viewGroupResolvePanel.getVisibility() == 8) ? 0 : 1;
        boolean z3 = (viewGroupResolvePanel3 == null || viewGroupResolvePanel3.getVisibility() == 8) ? false : true;
        if (!z3 && viewGroupResolvePanel2 != null && (viewFindViewById2 = viewGroupResolvePanel2.findViewById(androidx.appcompat.R.id.textSpacerNoButtons)) != null) {
            viewFindViewById2.setVisibility(0);
        }
        if (z2 != 0) {
            if (this.i != null) {
                this.i.setClipToPadding(true);
            }
            View viewFindViewById10 = (this.mMessage == null && this.b == null) ? null : viewGroupResolvePanel.findViewById(androidx.appcompat.R.id.titleDividerNoCustom);
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        } else if (viewGroupResolvePanel2 != null && (viewFindViewById = viewGroupResolvePanel2.findViewById(androidx.appcompat.R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (this.b instanceof RecycleListView) {
            ((RecycleListView) this.b).setHasDecor(z2, z3);
        }
        if (!z) {
            View view = this.b != null ? this.b : this.i;
            if (view != null) {
                setScrollIndicators(viewGroupResolvePanel2, view, z2 | (z3 ? 2 : 0), 3);
            }
        }
        ListView listView = this.b;
        if (listView == null || this.j == null) {
            return;
        }
        listView.setAdapter(this.j);
        int i = this.k;
        if (i > -1) {
            listView.setItemChecked(i, true);
            listView.setSelection(i);
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(androidx.appcompat.R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public Button getButton(int i) {
        switch (i) {
            case -3:
                return this.g;
            case -2:
                return this.e;
            case -1:
                return this.c;
            default:
                return null;
        }
    }

    public int getIconAttributeResId(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView getListView() {
        return this.b;
    }

    public void installContent() {
        this.a.setContentView(selectContentView());
        setupView();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.i != null && this.i.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.i != null && this.i.executeKeyEvent(keyEvent);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable) {
        if (message == null && onClickListener != null) {
            message = this.p.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case -3:
                this.mButtonNeutralText = charSequence;
                this.h = message;
                this.mButtonNeutralIcon = drawable;
                return;
            case -2:
                this.mButtonNegativeText = charSequence;
                this.f = message;
                this.mButtonNegativeIcon = drawable;
                return;
            case -1:
                this.mButtonPositiveText = charSequence;
                this.d = message;
                this.mButtonPositiveIcon = drawable;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setButtonPanelLayoutHint(int i) {
        this.mButtonPanelLayoutHint = i;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int i) {
        this.mIcon = null;
        this.mIconId = i;
        if (this.mIconView != null) {
            if (i == 0) {
                this.mIconView.setVisibility(8);
            } else {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        if (this.mIconView != null) {
            if (drawable == null) {
                this.mIconView.setVisibility(8);
            } else {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            }
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    public void setView(int i) {
        this.mView = null;
        this.mViewLayoutResId = i;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i;
        this.mViewSpacingTop = i2;
        this.mViewSpacingRight = i3;
        this.mViewSpacingBottom = i4;
    }
}
