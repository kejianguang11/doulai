package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.graphics.drawable.DrawableContainer;
import androidx.appcompat.graphics.drawable.StateListDrawable;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedAPI"})
public class AnimatedStateListDrawableCompat extends StateListDrawable implements TintAwareDrawable {
    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TRANSITION = "transition";
    private static final String ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String LOGTAG = "AnimatedStateListDrawableCompat";
    private static final String TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes";
    private boolean mMutated;
    private AnimatedStateListState mState;
    private Transition mTransition;
    private int mTransitionFromIndex;
    private int mTransitionToIndex;

    private static class AnimatableTransition extends Transition {
        private final Animatable mA;

        AnimatableTransition(Animatable animatable) {
            super();
            this.mA = animatable;
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void start() {
            this.mA.start();
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void stop() {
            this.mA.stop();
        }
    }

    static class AnimatedStateListState extends StateListDrawable.StateListState {
        private static final long REVERSED_BIT = 4294967296L;
        private static final long REVERSIBLE_FLAG_BIT = 8589934592L;
        LongSparseArray<Long> a;
        SparseArrayCompat<Integer> b;

        AnimatedStateListState(@Nullable AnimatedStateListState animatedStateListState, @NonNull AnimatedStateListDrawableCompat animatedStateListDrawableCompat, @Nullable Resources resources) {
            SparseArrayCompat<Integer> sparseArrayCompat;
            super(animatedStateListState, animatedStateListDrawableCompat, resources);
            if (animatedStateListState != null) {
                this.a = animatedStateListState.a;
                sparseArrayCompat = animatedStateListState.b;
            } else {
                this.a = new LongSparseArray<>();
                sparseArrayCompat = new SparseArrayCompat<>();
            }
            this.b = sparseArrayCompat;
        }

        private static long generateTransitionKey(int i, int i2) {
            return ((long) i2) | (((long) i) << 32);
        }

        int a(int i) {
            if (i < 0) {
                return 0;
            }
            return this.b.get(i, 0).intValue();
        }

        int a(int i, int i2) {
            return (int) this.a.get(generateTransitionKey(i, i2), -1L).longValue();
        }

        int a(int i, int i2, @NonNull Drawable drawable, boolean z) {
            int iAddChild = super.addChild(drawable);
            long jGenerateTransitionKey = generateTransitionKey(i, i2);
            long j = z ? REVERSIBLE_FLAG_BIT : 0L;
            long j2 = iAddChild;
            this.a.append(jGenerateTransitionKey, Long.valueOf(j2 | j));
            if (z) {
                this.a.append(generateTransitionKey(i2, i), Long.valueOf(REVERSED_BIT | j2 | j));
            }
            return iAddChild;
        }

        int a(@NonNull int[] iArr) {
            int iB = super.b(iArr);
            return iB >= 0 ? iB : super.b(StateSet.WILD_CARD);
        }

        int a(@NonNull int[] iArr, @NonNull Drawable drawable, int i) {
            int iA = super.a(iArr, drawable);
            this.b.put(iA, Integer.valueOf(i));
            return iA;
        }

        @Override // androidx.appcompat.graphics.drawable.StateListDrawable.StateListState, androidx.appcompat.graphics.drawable.DrawableContainer.DrawableContainerState
        void a() {
            this.a = this.a.m0clone();
            this.b = this.b.m1clone();
        }

        boolean b(int i, int i2) {
            return (this.a.get(generateTransitionKey(i, i2), -1L).longValue() & REVERSED_BIT) != 0;
        }

        boolean c(int i, int i2) {
            return (this.a.get(generateTransitionKey(i, i2), -1L).longValue() & REVERSIBLE_FLAG_BIT) != 0;
        }

        @Override // androidx.appcompat.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new AnimatedStateListDrawableCompat(this, null);
        }

        @Override // androidx.appcompat.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new AnimatedStateListDrawableCompat(this, resources);
        }
    }

    private static class AnimatedVectorDrawableTransition extends Transition {
        private final AnimatedVectorDrawableCompat mAvd;

        AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat animatedVectorDrawableCompat) {
            super();
            this.mAvd = animatedVectorDrawableCompat;
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void start() {
            this.mAvd.start();
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void stop() {
            this.mAvd.stop();
        }
    }

    private static class AnimationDrawableTransition extends Transition {
        private final ObjectAnimator mAnim;
        private final boolean mHasReversibleFlag;

        AnimationDrawableTransition(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super();
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i = z ? numberOfFrames - 1 : 0;
            int i2 = z ? 0 : numberOfFrames - 1;
            FrameInterpolator frameInterpolator = new FrameInterpolator(animationDrawable, z);
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2);
            if (Build.VERSION.SDK_INT >= 18) {
                objectAnimatorOfInt.setAutoCancel(true);
            }
            objectAnimatorOfInt.setDuration(frameInterpolator.a());
            objectAnimatorOfInt.setInterpolator(frameInterpolator);
            this.mHasReversibleFlag = z2;
            this.mAnim = objectAnimatorOfInt;
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public boolean canReverse() {
            return this.mHasReversibleFlag;
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void reverse() {
            this.mAnim.reverse();
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void start() {
            this.mAnim.start();
        }

        @Override // androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        public void stop() {
            this.mAnim.cancel();
        }
    }

    private static class FrameInterpolator implements TimeInterpolator {
        private int[] mFrameTimes;
        private int mFrames;
        private int mTotalDuration;

        FrameInterpolator(AnimationDrawable animationDrawable, boolean z) {
            a(animationDrawable, z);
        }

        int a() {
            return this.mTotalDuration;
        }

        int a(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.mFrames = numberOfFrames;
            if (this.mFrameTimes == null || this.mFrameTimes.length < numberOfFrames) {
                this.mFrameTimes = new int[numberOfFrames];
            }
            int[] iArr = this.mFrameTimes;
            int i = 0;
            for (int i2 = 0; i2 < numberOfFrames; i2++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i2) - 1 : i2);
                iArr[i2] = duration;
                i += duration;
            }
            this.mTotalDuration = i;
            return i;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            int i = (int) ((f * this.mTotalDuration) + 0.5f);
            int i2 = this.mFrames;
            int[] iArr = this.mFrameTimes;
            int i3 = 0;
            while (i3 < i2 && i >= iArr[i3]) {
                i -= iArr[i3];
                i3++;
            }
            return (i3 / i2) + (i3 < i2 ? i / this.mTotalDuration : 0.0f);
        }
    }

    private static abstract class Transition {
        private Transition() {
        }

        public boolean canReverse() {
            return false;
        }

        public void reverse() {
        }

        public abstract void start();

        public abstract void stop();
    }

    public AnimatedStateListDrawableCompat() {
        this(null, null);
    }

    AnimatedStateListDrawableCompat(@Nullable AnimatedStateListState animatedStateListState, @Nullable Resources resources) {
        super(null);
        this.mTransitionToIndex = -1;
        this.mTransitionFromIndex = -1;
        a(new AnimatedStateListState(animatedStateListState, this, resources));
        onStateChange(getState());
        jumpToCurrentState();
    }

    @Nullable
    public static AnimatedStateListDrawableCompat create(@NonNull Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        int next;
        try {
            Resources resources = context.getResources();
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return createFromXmlInner(context, resources, xml, attributeSetAsAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException | XmlPullParserException e) {
            Log.e(LOGTAG, "parser error", e);
            return null;
        }
    }

    public static AnimatedStateListDrawableCompat createFromXmlInner(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName();
        if (name.equals("animated-selector")) {
            AnimatedStateListDrawableCompat animatedStateListDrawableCompat = new AnimatedStateListDrawableCompat();
            animatedStateListDrawableCompat.inflate(context, resources, xmlPullParser, attributeSet, theme);
            return animatedStateListDrawableCompat;
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name);
    }

    private void inflateChildElements(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next == 3) {
                return;
            }
            if (next == 2 && depth2 <= depth) {
                if (xmlPullParser.getName().equals(ELEMENT_ITEM)) {
                    parseItem(context, resources, xmlPullParser, attributeSet, theme);
                } else if (xmlPullParser.getName().equals(ELEMENT_TRANSITION)) {
                    parseTransition(context, resources, xmlPullParser, attributeSet, theme);
                }
            }
        }
    }

    private void init() {
        onStateChange(getState());
    }

    private int parseItem(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableItem);
        int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_id, 0);
        int resourceId2 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_drawable, -1);
        Drawable drawable = resourceId2 > 0 ? ResourceManagerInternal.get().getDrawable(context, resourceId2) : null;
        typedArrayObtainAttributes.recycle();
        int[] iArrA = a(attributeSet);
        if (drawable == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR);
            }
            drawable = xmlPullParser.getName().equals("vector") ? VectorDrawableCompat.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
        }
        if (drawable != null) {
            return this.mState.a(iArrA, drawable, resourceId);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR);
    }

    private int parseTransition(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableTransition);
        int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_fromId, -1);
        int resourceId2 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_toId, -1);
        int resourceId3 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_drawable, -1);
        Drawable drawable = resourceId3 > 0 ? ResourceManagerInternal.get().getDrawable(context, resourceId3) : null;
        boolean z = typedArrayObtainAttributes.getBoolean(R.styleable.AnimatedStateListDrawableTransition_android_reversible, false);
        typedArrayObtainAttributes.recycle();
        if (drawable == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR);
            }
            drawable = xmlPullParser.getName().equals("animated-vector") ? AnimatedVectorDrawableCompat.createFromXmlInner(context, resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
        }
        if (drawable == null) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR);
        }
        if (resourceId != -1 && resourceId2 != -1) {
            return this.mState.a(resourceId, resourceId2, drawable, z);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_FROM_TO_ID);
    }

    private boolean selectTransition(int i) {
        int iD;
        int iA;
        Transition animatableTransition;
        Transition transition = this.mTransition;
        if (transition == null) {
            iD = d();
        } else {
            if (i == this.mTransitionToIndex) {
                return true;
            }
            if (i == this.mTransitionFromIndex && transition.canReverse()) {
                transition.reverse();
                this.mTransitionToIndex = this.mTransitionFromIndex;
                this.mTransitionFromIndex = i;
                return true;
            }
            iD = this.mTransitionToIndex;
            transition.stop();
        }
        this.mTransition = null;
        this.mTransitionFromIndex = -1;
        this.mTransitionToIndex = -1;
        AnimatedStateListState animatedStateListState = this.mState;
        int iA2 = animatedStateListState.a(iD);
        int iA3 = animatedStateListState.a(i);
        if (iA3 == 0 || iA2 == 0 || (iA = animatedStateListState.a(iA2, iA3)) < 0) {
            return false;
        }
        boolean zC = animatedStateListState.c(iA2, iA3);
        a(iA);
        Object current = getCurrent();
        if (current instanceof AnimationDrawable) {
            animatableTransition = new AnimationDrawableTransition((AnimationDrawable) current, animatedStateListState.b(iA2, iA3), zC);
        } else {
            if (!(current instanceof AnimatedVectorDrawableCompat)) {
                if (current instanceof Animatable) {
                    animatableTransition = new AnimatableTransition((Animatable) current);
                }
                return false;
            }
            animatableTransition = new AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat) current);
        }
        animatableTransition.start();
        this.mTransition = animatableTransition;
        this.mTransitionFromIndex = iD;
        this.mTransitionToIndex = i;
        return true;
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedStateListState animatedStateListState = this.mState;
        if (Build.VERSION.SDK_INT >= 21) {
            animatedStateListState.f |= typedArray.getChangingConfigurations();
        }
        animatedStateListState.setVariablePadding(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, animatedStateListState.k));
        animatedStateListState.setConstantSize(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_constantSize, animatedStateListState.n));
        animatedStateListState.setEnterFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, animatedStateListState.C));
        animatedStateListState.setExitFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, animatedStateListState.D));
        setDither(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_dither, animatedStateListState.z));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public AnimatedStateListState c() {
        return new AnimatedStateListState(this.mState, this, null);
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer
    void a(@NonNull DrawableContainer.DrawableContainerState drawableContainerState) {
        super.a(drawableContainerState);
        if (drawableContainerState instanceof AnimatedStateListState) {
            this.mState = (AnimatedStateListState) drawableContainerState;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable
    public /* bridge */ /* synthetic */ void addState(int[] iArr, Drawable drawable) {
        super.addState(iArr, drawable);
    }

    public void addState(@NonNull int[] iArr, @NonNull Drawable drawable, int i) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable must not be null");
        }
        this.mState.a(iArr, drawable, i);
        onStateChange(getState());
    }

    public <T extends Drawable & Animatable> void addTransition(int i, int i2, @NonNull T t, boolean z) {
        if (t == null) {
            throw new IllegalArgumentException("Transition drawable must not be null");
        }
        this.mState.a(i, i2, t, z);
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ void applyTheme(@NonNull Resources.Theme theme) {
        super.applyTheme(theme);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ boolean canApplyTheme() {
        return super.canApplyTheme();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @NonNull
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void getHotspotBounds(@NonNull Rect rect) {
        super.getHotspotBounds(rect);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ void getOutline(@NonNull Outline outline) {
        super.getOutline(outline);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean getPadding(@NonNull Rect rect) {
        return super.getPadding(rect);
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable
    public void inflate(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableCompat);
        setVisible(typedArrayObtainAttributes.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        a(resources);
        typedArrayObtainAttributes.recycle();
        inflateChildElements(context, resources, xmlPullParser, attributeSet, theme);
        init();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ void invalidateDrawable(@NonNull Drawable drawable) {
        super.invalidateDrawable(drawable);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean isAutoMirrored() {
        return super.isAutoMirrored();
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        if (this.mTransition != null) {
            this.mTransition.stop();
            this.mTransition = null;
            a(this.mTransitionToIndex);
            this.mTransitionToIndex = -1;
            this.mTransitionFromIndex = -1;
        }
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState.a();
            this.mMutated = true;
        }
        return this;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean onLayoutDirectionChanged(int i) {
        return super.onLayoutDirectionChanged(i);
    }

    @Override // androidx.appcompat.graphics.drawable.StateListDrawable, androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int iA = this.mState.a(iArr);
        boolean z = iA != d() && (selectTransition(iA) || a(iA));
        Drawable current = getCurrent();
        return current != null ? z | current.setState(iArr) : z;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
        super.scheduleDrawable(drawable, runnable, j);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setAutoMirrored(boolean z) {
        super.setAutoMirrored(z);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setDither(boolean z) {
        super.setDither(z);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer
    public /* bridge */ /* synthetic */ void setEnterFadeDuration(int i) {
        super.setEnterFadeDuration(i);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer
    public /* bridge */ /* synthetic */ void setExitFadeDuration(int i) {
        super.setExitFadeDuration(i);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public /* bridge */ /* synthetic */ void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public /* bridge */ /* synthetic */ void setTintMode(@NonNull PorterDuff.Mode mode) {
        super.setTintMode(mode);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (this.mTransition != null && (visible || z2)) {
            if (z) {
                this.mTransition.start();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        super.unscheduleDrawable(drawable, runnable);
    }
}
