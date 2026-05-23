package androidx.appcompat.graphics.drawable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.graphics.drawable.DrawableContainer;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedAPI"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class StateListDrawable extends DrawableContainer {
    private static final boolean DEBUG = false;
    private static final String TAG = "StateListDrawable";
    private boolean mMutated;
    private StateListState mStateListState;

    static class StateListState extends DrawableContainer.DrawableContainerState {
        int[][] L;

        StateListState(StateListState stateListState, StateListDrawable stateListDrawable, Resources resources) {
            super(stateListState, stateListDrawable, resources);
            this.L = stateListState != null ? stateListState.L : new int[c()][];
        }

        int a(int[] iArr, Drawable drawable) {
            int iAddChild = addChild(drawable);
            this.L[iAddChild] = iArr;
            return iAddChild;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableContainer.DrawableContainerState
        void a() {
            int[][] iArr = new int[this.L.length][];
            for (int length = this.L.length - 1; length >= 0; length--) {
                iArr[length] = this.L[length] != null ? (int[]) this.L[length].clone() : null;
            }
            this.L = iArr;
        }

        int b(int[] iArr) {
            int[][] iArr2 = this.L;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (StateSet.stateSetMatches(iArr2[i], iArr)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[][] iArr = new int[i2][];
            System.arraycopy(this.L, 0, iArr, 0, i);
            this.L = iArr;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new StateListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new StateListDrawable(this, resources);
        }
    }

    StateListDrawable() {
        this(null, null);
    }

    StateListDrawable(@Nullable StateListState stateListState) {
        if (stateListState != null) {
            a(stateListState);
        }
    }

    StateListDrawable(StateListState stateListState, Resources resources) {
        a(new StateListState(stateListState, this, resources));
        onStateChange(getState());
    }

    private void inflateChildElements(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        StateListState stateListState = this.mStateListState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next2 == 3) {
                return;
            }
            if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals("item")) {
                TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawableItem);
                int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.StateListDrawableItem_android_drawable, -1);
                Drawable drawable = resourceId > 0 ? ResourceManagerInternal.get().getDrawable(context, resourceId) : null;
                typedArrayObtainAttributes.recycle();
                int[] iArrA = a(attributeSet);
                if (drawable == null) {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next != 2) {
                        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    }
                    drawable = Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
                }
                stateListState.a(iArrA, drawable);
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        StateListState stateListState = this.mStateListState;
        if (Build.VERSION.SDK_INT >= 21) {
            stateListState.f |= typedArray.getChangingConfigurations();
        }
        stateListState.k = typedArray.getBoolean(R.styleable.StateListDrawable_android_variablePadding, stateListState.k);
        stateListState.n = typedArray.getBoolean(R.styleable.StateListDrawable_android_constantSize, stateListState.n);
        stateListState.C = typedArray.getInt(R.styleable.StateListDrawable_android_enterFadeDuration, stateListState.C);
        stateListState.D = typedArray.getInt(R.styleable.StateListDrawable_android_exitFadeDuration, stateListState.D);
        stateListState.z = typedArray.getBoolean(R.styleable.StateListDrawable_android_dither, stateListState.z);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer
    void a(@NonNull DrawableContainer.DrawableContainerState drawableContainerState) {
        super.a(drawableContainerState);
        if (drawableContainerState instanceof StateListState) {
            this.mStateListState = (StateListState) drawableContainerState;
        }
    }

    int[] a(AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        int[] iArr = new int[attributeCount];
        int i = 0;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            int attributeNameResource = attributeSet.getAttributeNameResource(i2);
            if (attributeNameResource != 0 && attributeNameResource != 16842960 && attributeNameResource != 16843161) {
                int i3 = i + 1;
                if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                    attributeNameResource = -attributeNameResource;
                }
                iArr[i] = attributeNameResource;
                i = i3;
            }
        }
        return StateSet.trimStateSet(iArr, i);
    }

    public void addState(int[] iArr, Drawable drawable) {
        if (drawable != null) {
            this.mStateListState.a(iArr, drawable);
            onStateChange(getState());
        }
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.graphics.drawable.DrawableContainer
    /* JADX INFO: renamed from: b */
    public StateListState c() {
        return new StateListState(this.mStateListState, this, null);
    }

    public void inflate(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawable);
        setVisible(typedArrayObtainAttributes.getBoolean(R.styleable.StateListDrawable_android_visible, true), true);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        a(resources);
        typedArrayObtainAttributes.recycle();
        inflateChildElements(context, resources, xmlPullParser, attributeSet, theme);
        onStateChange(getState());
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @NonNull
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mStateListState.a();
            this.mMutated = true;
        }
        return this;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean zOnStateChange = super.onStateChange(iArr);
        int iB = this.mStateListState.b(iArr);
        if (iB < 0) {
            iB = this.mStateListState.b(StateSet.WILD_CARD);
        }
        return a(iB) || zOnStateChange;
    }
}
