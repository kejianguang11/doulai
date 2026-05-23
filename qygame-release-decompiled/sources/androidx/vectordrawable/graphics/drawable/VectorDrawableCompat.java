package androidx.vectordrawable.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.igexin.push.core.b;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class VectorDrawableCompat extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    static final PorterDuff.Mode a = PorterDuff.Mode.SRC_IN;
    private boolean mAllowCaching;
    private Drawable.ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;
    private VectorDrawableCompatState mVectorState;

    private static class VClipPath extends VPath {
        VClipPath() {
        }

        VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.l = PathParser.createNodesFromPathData(string2);
            }
            this.n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 2, 0);
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.d);
                updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser);
                typedArrayObtainAttributes.recycle();
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public boolean isClipPath() {
            return true;
        }
    }

    private static class VFullPath extends VPath {
        ComplexColorCompat a;
        float b;
        ComplexColorCompat c;
        float d;
        float e;
        float f;
        float g;
        float h;
        Paint.Cap i;
        Paint.Join j;
        float k;
        private int[] mThemeAttrs;

        VFullPath() {
            this.b = 0.0f;
            this.d = 1.0f;
            this.e = 1.0f;
            this.f = 0.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = Paint.Cap.BUTT;
            this.j = Paint.Join.MITER;
            this.k = 4.0f;
        }

        VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.b = 0.0f;
            this.d = 1.0f;
            this.e = 1.0f;
            this.f = 0.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = Paint.Cap.BUTT;
            this.j = Paint.Join.MITER;
            this.k = 4.0f;
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.a = vFullPath.a;
            this.b = vFullPath.b;
            this.d = vFullPath.d;
            this.c = vFullPath.c;
            this.n = vFullPath.n;
            this.e = vFullPath.e;
            this.f = vFullPath.f;
            this.g = vFullPath.g;
            this.h = vFullPath.h;
            this.i = vFullPath.i;
            this.j = vFullPath.j;
            this.k = vFullPath.k;
        }

        private Paint.Cap getStrokeLineCap(int i, Paint.Cap cap) {
            switch (i) {
                case 0:
                    return Paint.Cap.BUTT;
                case 1:
                    return Paint.Cap.ROUND;
                case 2:
                    return Paint.Cap.SQUARE;
                default:
                    return cap;
            }
        }

        private Paint.Join getStrokeLineJoin(int i, Paint.Join join) {
            switch (i) {
                case 0:
                    return Paint.Join.MITER;
                case 1:
                    return Paint.Join.ROUND;
                case 2:
                    return Paint.Join.BEVEL;
                default:
                    return join;
            }
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.mThemeAttrs = null;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.m = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.l = PathParser.createNodesFromPathData(string2);
                }
                this.c = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
                this.e = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.e);
                this.i = getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.i);
                this.j = getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.j);
                this.k = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.k);
                this.a = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
                this.d = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.d);
                this.b = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.b);
                this.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.g);
                this.h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.h);
                this.f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.f);
                this.n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.n);
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public void applyTheme(Resources.Theme theme) {
            if (this.mThemeAttrs == null) {
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null;
        }

        float getFillAlpha() {
            return this.e;
        }

        @ColorInt
        int getFillColor() {
            return this.c.getColor();
        }

        float getStrokeAlpha() {
            return this.d;
        }

        @ColorInt
        int getStrokeColor() {
            return this.a.getColor();
        }

        float getStrokeWidth() {
            return this.b;
        }

        float getTrimPathEnd() {
            return this.g;
        }

        float getTrimPathOffset() {
            return this.h;
        }

        float getTrimPathStart() {
            return this.f;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.c);
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser, theme);
            typedArrayObtainAttributes.recycle();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean isStateful() {
            return this.c.isStateful() || this.a.isStateful();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean onStateChanged(int[] iArr) {
            return this.a.onStateChanged(iArr) | this.c.onStateChanged(iArr);
        }

        void setFillAlpha(float f) {
            this.e = f;
        }

        void setFillColor(int i) {
            this.c.setColor(i);
        }

        void setStrokeAlpha(float f) {
            this.d = f;
        }

        void setStrokeColor(int i) {
            this.a.setColor(i);
        }

        void setStrokeWidth(float f) {
            this.b = f;
        }

        void setTrimPathEnd(float f) {
            this.g = f;
        }

        void setTrimPathOffset(float f) {
            this.h = f;
        }

        void setTrimPathStart(float f) {
            this.f = f;
        }
    }

    private static class VGroup extends VObject {
        final Matrix a;
        final ArrayList<VObject> b;
        float c;
        final Matrix d;
        int e;
        private String mGroupName;
        private float mPivotX;
        private float mPivotY;
        private float mScaleX;
        private float mScaleY;
        private int[] mThemeAttrs;
        private float mTranslateX;
        private float mTranslateY;

        public VGroup() {
            super();
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.d = new Matrix();
            this.mGroupName = null;
        }

        public VGroup(VGroup vGroup, ArrayMap<String, Object> arrayMap) {
            VPath vClipPath;
            super();
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.d = new Matrix();
            this.mGroupName = null;
            this.c = vGroup.c;
            this.mPivotX = vGroup.mPivotX;
            this.mPivotY = vGroup.mPivotY;
            this.mScaleX = vGroup.mScaleX;
            this.mScaleY = vGroup.mScaleY;
            this.mTranslateX = vGroup.mTranslateX;
            this.mTranslateY = vGroup.mTranslateY;
            this.mThemeAttrs = vGroup.mThemeAttrs;
            this.mGroupName = vGroup.mGroupName;
            this.e = vGroup.e;
            if (this.mGroupName != null) {
                arrayMap.put(this.mGroupName, this);
            }
            this.d.set(vGroup.d);
            ArrayList<VObject> arrayList = vGroup.b;
            for (int i = 0; i < arrayList.size(); i++) {
                VObject vObject = arrayList.get(i);
                if (vObject instanceof VGroup) {
                    this.b.add(new VGroup((VGroup) vObject, arrayMap));
                } else {
                    if (vObject instanceof VFullPath) {
                        vClipPath = new VFullPath((VFullPath) vObject);
                    } else {
                        if (!(vObject instanceof VClipPath)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        vClipPath = new VClipPath((VClipPath) vObject);
                    }
                    this.b.add(vClipPath);
                    if (vClipPath.m != null) {
                        arrayMap.put(vClipPath.m, vClipPath);
                    }
                }
            }
        }

        private void updateLocalMatrix() {
            this.d.reset();
            this.d.postTranslate(-this.mPivotX, -this.mPivotY);
            this.d.postScale(this.mScaleX, this.mScaleY);
            this.d.postRotate(this.c, 0.0f, 0.0f);
            this.d.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null;
            this.c = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "rotation", 5, this.c);
            this.mPivotX = typedArray.getFloat(1, this.mPivotX);
            this.mPivotY = typedArray.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateY", 7, this.mTranslateY);
            String string = typedArray.getString(0);
            if (string != null) {
                this.mGroupName = string;
            }
            updateLocalMatrix();
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.d;
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public float getRotation() {
            return this.c;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.b);
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser);
            typedArrayObtainAttributes.recycle();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean isStateful() {
            for (int i = 0; i < this.b.size(); i++) {
                if (this.b.get(i).isStateful()) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean onStateChanged(int[] iArr) {
            boolean zOnStateChanged = false;
            for (int i = 0; i < this.b.size(); i++) {
                zOnStateChanged |= this.b.get(i).onStateChanged(iArr);
            }
            return zOnStateChanged;
        }

        public void setPivotX(float f) {
            if (f != this.mPivotX) {
                this.mPivotX = f;
                updateLocalMatrix();
            }
        }

        public void setPivotY(float f) {
            if (f != this.mPivotY) {
                this.mPivotY = f;
                updateLocalMatrix();
            }
        }

        public void setRotation(float f) {
            if (f != this.c) {
                this.c = f;
                updateLocalMatrix();
            }
        }

        public void setScaleX(float f) {
            if (f != this.mScaleX) {
                this.mScaleX = f;
                updateLocalMatrix();
            }
        }

        public void setScaleY(float f) {
            if (f != this.mScaleY) {
                this.mScaleY = f;
                updateLocalMatrix();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.mTranslateX) {
                this.mTranslateX = f;
                updateLocalMatrix();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.mTranslateY) {
                this.mTranslateY = f;
                updateLocalMatrix();
            }
        }
    }

    private static abstract class VObject {
        private VObject() {
        }

        public boolean isStateful() {
            return false;
        }

        public boolean onStateChanged(int[] iArr) {
            return false;
        }
    }

    private static abstract class VPath extends VObject {
        protected PathParser.PathDataNode[] l;
        String m;
        int n;
        int o;

        public VPath() {
            super();
            this.l = null;
            this.n = 0;
        }

        public VPath(VPath vPath) {
            super();
            this.l = null;
            this.n = 0;
            this.m = vPath.m;
            this.o = vPath.o;
            this.l = PathParser.deepCopyNodes(vPath.l);
        }

        public void applyTheme(Resources.Theme theme) {
        }

        public boolean canApplyTheme() {
            return false;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.l;
        }

        public String getPathName() {
            return this.m;
        }

        public boolean isClipPath() {
            return false;
        }

        public String nodesToString(PathParser.PathDataNode[] pathDataNodeArr) {
            String str = " ";
            int i = 0;
            while (i < pathDataNodeArr.length) {
                String str2 = str + pathDataNodeArr[i].mType + ":";
                String str3 = str2;
                for (float f : pathDataNodeArr[i].mParams) {
                    str3 = str3 + f + b.an;
                }
                i++;
                str = str3;
            }
            return str;
        }

        public void printVPath(int i) {
            String str = "";
            for (int i2 = 0; i2 < i; i2++) {
                str = str + "    ";
            }
            Log.v("VectorDrawableCompat", str + "current path is :" + this.m + " pathData is " + nodesToString(this.l));
        }

        public void setPathData(PathParser.PathDataNode[] pathDataNodeArr) {
            if (PathParser.canMorph(this.l, pathDataNodeArr)) {
                PathParser.updateNodes(this.l, pathDataNodeArr);
            } else {
                this.l = PathParser.deepCopyNodes(pathDataNodeArr);
            }
        }

        public void toPath(Path path) {
            path.reset();
            if (this.l != null) {
                PathParser.PathDataNode.nodesToPath(this.l, path);
            }
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();
        Paint a;
        Paint b;
        final VGroup c;
        float d;
        float e;
        float f;
        float g;
        int h;
        String i;
        Boolean j;
        final ArrayMap<String, Object> k;
        private int mChangingConfigurations;
        private final Matrix mFinalPathMatrix;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;

        public VPathRenderer() {
            this.mFinalPathMatrix = new Matrix();
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 255;
            this.i = null;
            this.j = null;
            this.k = new ArrayMap<>();
            this.c = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.mFinalPathMatrix = new Matrix();
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 255;
            this.i = null;
            this.j = null;
            this.k = new ArrayMap<>();
            this.c = new VGroup(vPathRenderer.c, this.k);
            this.mPath = new Path(vPathRenderer.mPath);
            this.mRenderPath = new Path(vPathRenderer.mRenderPath);
            this.d = vPathRenderer.d;
            this.e = vPathRenderer.e;
            this.f = vPathRenderer.f;
            this.g = vPathRenderer.g;
            this.mChangingConfigurations = vPathRenderer.mChangingConfigurations;
            this.h = vPathRenderer.h;
            this.i = vPathRenderer.i;
            if (vPathRenderer.i != null) {
                this.k.put(vPathRenderer.i, this);
            }
            this.j = vPathRenderer.j;
        }

        private static float cross(float f, float f2, float f3, float f4) {
            return (f * f4) - (f2 * f3);
        }

        private void drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            vGroup.a.set(matrix);
            vGroup.a.preConcat(vGroup.d);
            canvas.save();
            for (int i3 = 0; i3 < vGroup.b.size(); i3++) {
                VObject vObject = vGroup.b.get(i3);
                if (vObject instanceof VGroup) {
                    drawGroupTree((VGroup) vObject, vGroup.a, canvas, i, i2, colorFilter);
                } else if (vObject instanceof VPath) {
                    drawPath(vGroup, (VPath) vObject, canvas, i, i2, colorFilter);
                }
            }
            canvas.restore();
        }

        private void drawPath(VGroup vGroup, VPath vPath, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            float f = i / this.f;
            float f2 = i2 / this.g;
            float fMin = Math.min(f, f2);
            Matrix matrix = vGroup.a;
            this.mFinalPathMatrix.set(matrix);
            this.mFinalPathMatrix.postScale(f, f2);
            float matrixScale = getMatrixScale(matrix);
            if (matrixScale == 0.0f) {
                return;
            }
            vPath.toPath(this.mPath);
            Path path = this.mPath;
            this.mRenderPath.reset();
            if (vPath.isClipPath()) {
                this.mRenderPath.setFillType(vPath.n == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                canvas.clipPath(this.mRenderPath);
                return;
            }
            VFullPath vFullPath = (VFullPath) vPath;
            if (vFullPath.f != 0.0f || vFullPath.g != 1.0f) {
                float f3 = (vFullPath.f + vFullPath.h) % 1.0f;
                float f4 = (vFullPath.g + vFullPath.h) % 1.0f;
                if (this.mPathMeasure == null) {
                    this.mPathMeasure = new PathMeasure();
                }
                this.mPathMeasure.setPath(this.mPath, false);
                float length = this.mPathMeasure.getLength();
                float f5 = f3 * length;
                float f6 = f4 * length;
                path.reset();
                if (f5 > f6) {
                    this.mPathMeasure.getSegment(f5, length, path, true);
                    this.mPathMeasure.getSegment(0.0f, f6, path, true);
                } else {
                    this.mPathMeasure.getSegment(f5, f6, path, true);
                }
                path.rLineTo(0.0f, 0.0f);
            }
            this.mRenderPath.addPath(path, this.mFinalPathMatrix);
            if (vFullPath.c.willDraw()) {
                ComplexColorCompat complexColorCompat = vFullPath.c;
                if (this.b == null) {
                    this.b = new Paint(1);
                    this.b.setStyle(Paint.Style.FILL);
                }
                Paint paint = this.b;
                if (complexColorCompat.isGradient()) {
                    Shader shader = complexColorCompat.getShader();
                    shader.setLocalMatrix(this.mFinalPathMatrix);
                    paint.setShader(shader);
                    paint.setAlpha(Math.round(vFullPath.e * 255.0f));
                } else {
                    paint.setShader(null);
                    paint.setAlpha(255);
                    paint.setColor(VectorDrawableCompat.a(complexColorCompat.getColor(), vFullPath.e));
                }
                paint.setColorFilter(colorFilter);
                this.mRenderPath.setFillType(vFullPath.n == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                canvas.drawPath(this.mRenderPath, paint);
            }
            if (vFullPath.a.willDraw()) {
                ComplexColorCompat complexColorCompat2 = vFullPath.a;
                if (this.a == null) {
                    this.a = new Paint(1);
                    this.a.setStyle(Paint.Style.STROKE);
                }
                Paint paint2 = this.a;
                if (vFullPath.j != null) {
                    paint2.setStrokeJoin(vFullPath.j);
                }
                if (vFullPath.i != null) {
                    paint2.setStrokeCap(vFullPath.i);
                }
                paint2.setStrokeMiter(vFullPath.k);
                if (complexColorCompat2.isGradient()) {
                    Shader shader2 = complexColorCompat2.getShader();
                    shader2.setLocalMatrix(this.mFinalPathMatrix);
                    paint2.setShader(shader2);
                    paint2.setAlpha(Math.round(vFullPath.d * 255.0f));
                } else {
                    paint2.setShader(null);
                    paint2.setAlpha(255);
                    paint2.setColor(VectorDrawableCompat.a(complexColorCompat2.getColor(), vFullPath.d));
                }
                paint2.setColorFilter(colorFilter);
                paint2.setStrokeWidth(vFullPath.b * fMin * matrixScale);
                canvas.drawPath(this.mRenderPath, paint2);
            }
        }

        private float getMatrixScale(Matrix matrix) {
            float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
            matrix.mapVectors(fArr);
            float fHypot = (float) Math.hypot(fArr[0], fArr[1]);
            float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
            float fCross = cross(fArr[0], fArr[1], fArr[2], fArr[3]);
            float fMax = Math.max(fHypot, fHypot2);
            if (fMax > 0.0f) {
                return Math.abs(fCross) / fMax;
            }
            return 0.0f;
        }

        public void draw(Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            drawGroupTree(this.c, IDENTITY_MATRIX, canvas, i, i2, colorFilter);
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.h;
        }

        public boolean isStateful() {
            if (this.j == null) {
                this.j = Boolean.valueOf(this.c.isStateful());
            }
            return this.j.booleanValue();
        }

        public boolean onStateChanged(int[] iArr) {
            return this.c.onStateChanged(iArr);
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public void setRootAlpha(int i) {
            this.h = i;
        }
    }

    private static class VectorDrawableCompatState extends Drawable.ConstantState {
        int a;
        VPathRenderer b;
        ColorStateList c;
        PorterDuff.Mode d;
        boolean e;
        Bitmap f;
        ColorStateList g;
        PorterDuff.Mode h;
        int i;
        boolean j;
        boolean k;
        Paint l;

        public VectorDrawableCompatState() {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            this.b = new VPathRenderer();
        }

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            this.c = null;
            this.d = VectorDrawableCompat.a;
            if (vectorDrawableCompatState != null) {
                this.a = vectorDrawableCompatState.a;
                this.b = new VPathRenderer(vectorDrawableCompatState.b);
                if (vectorDrawableCompatState.b.b != null) {
                    this.b.b = new Paint(vectorDrawableCompatState.b.b);
                }
                if (vectorDrawableCompatState.b.a != null) {
                    this.b.a = new Paint(vectorDrawableCompatState.b.a);
                }
                this.c = vectorDrawableCompatState.c;
                this.d = vectorDrawableCompatState.d;
                this.e = vectorDrawableCompatState.e;
            }
        }

        public boolean canReuseBitmap(int i, int i2) {
            return i == this.f.getWidth() && i2 == this.f.getHeight();
        }

        public boolean canReuseCache() {
            return !this.k && this.g == this.c && this.h == this.d && this.j == this.e && this.i == this.b.getRootAlpha();
        }

        public void createCachedBitmapIfNeeded(int i, int i2) {
            if (this.f == null || !canReuseBitmap(i, i2)) {
                this.f = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f, (Rect) null, rect, getPaint(colorFilter));
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.a;
        }

        public Paint getPaint(ColorFilter colorFilter) {
            if (!hasTranslucentRoot() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                this.l = new Paint();
                this.l.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public boolean hasTranslucentRoot() {
            return this.b.getRootAlpha() < 255;
        }

        public boolean isStateful() {
            return this.b.isStateful();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public boolean onStateChanged(int[] iArr) {
            boolean zOnStateChanged = this.b.onStateChanged(iArr);
            this.k |= zOnStateChanged;
            return zOnStateChanged;
        }

        public void updateCacheStates() {
            this.g = this.c;
            this.h = this.d;
            this.i = this.b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        public void updateCachedBitmap(int i, int i2) {
            this.f.eraseColor(0);
            this.b.draw(new Canvas(this.f), i, i2, null);
        }
    }

    @RequiresApi(24)
    private static class VectorDrawableDelegateState extends Drawable.ConstantState {
        private final Drawable.ConstantState mDelegateState;

        public VectorDrawableDelegateState(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.mDelegateState.newDrawable();
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.mDelegateState.newDrawable(resources);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = (VectorDrawable) this.mDelegateState.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

    VectorDrawableCompat() {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(@NonNull VectorDrawableCompatState vectorDrawableCompatState) {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = a(this.mTintFilter, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
    }

    static int a(int i, float f) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | (((int) (Color.alpha(i) * f)) << 24);
    }

    @Nullable
    public static VectorDrawableCompat create(@NonNull Resources resources, @DrawableRes int i, @Nullable Resources.Theme theme) {
        int next;
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.d = ResourcesCompat.getDrawable(resources, i, theme);
            vectorDrawableCompat.mCachedConstantStateDelegate = new VectorDrawableDelegateState(vectorDrawableCompat.d.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return createFromXmlInner(resources, (XmlPullParser) xml, attributeSetAsAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException | XmlPullParserException e) {
            Log.e("VectorDrawableCompat", "parser error", e);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    private void inflateInternal(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i;
        int i2;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(vPathRenderer.c);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                VGroup vGroup = (VGroup) arrayDeque.peek();
                if (SHAPE_PATH.equals(name)) {
                    VFullPath vFullPath = new VFullPath();
                    vFullPath.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.b.add(vFullPath);
                    if (vFullPath.getPathName() != null) {
                        vPathRenderer.k.put(vFullPath.getPathName(), vFullPath);
                    }
                    z = false;
                    i = vectorDrawableCompatState.a;
                    i2 = vFullPath.o;
                } else if (SHAPE_CLIP_PATH.equals(name)) {
                    VClipPath vClipPath = new VClipPath();
                    vClipPath.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.b.add(vClipPath);
                    if (vClipPath.getPathName() != null) {
                        vPathRenderer.k.put(vClipPath.getPathName(), vClipPath);
                    }
                    i = vectorDrawableCompatState.a;
                    i2 = vClipPath.o;
                } else if (SHAPE_GROUP.equals(name)) {
                    VGroup vGroup2 = new VGroup();
                    vGroup2.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.b.add(vGroup2);
                    arrayDeque.push(vGroup2);
                    if (vGroup2.getGroupName() != null) {
                        vPathRenderer.k.put(vGroup2.getGroupName(), vGroup2);
                    }
                    i = vectorDrawableCompatState.a;
                    i2 = vGroup2.e;
                }
                vectorDrawableCompatState.a = i2 | i;
            } else if (eventType == 3 && SHAPE_GROUP.equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            throw new XmlPullParserException("no path defined");
        }
    }

    private boolean needMirroring() {
        return Build.VERSION.SDK_INT >= 17 && isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
    }

    private static PorterDuff.Mode parseTintModeCompat(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    private void printGroupTree(VGroup vGroup, int i) {
        String str = "";
        for (int i2 = 0; i2 < i; i2++) {
            str = str + "    ";
        }
        Log.v("VectorDrawableCompat", str + "current group is :" + vGroup.getGroupName() + " rotation is " + vGroup.c);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("matrix is :");
        sb.append(vGroup.getLocalMatrix().toString());
        Log.v("VectorDrawableCompat", sb.toString());
        for (int i3 = 0; i3 < vGroup.b.size(); i3++) {
            VObject vObject = vGroup.b.get(i3);
            if (vObject instanceof VGroup) {
                printGroupTree((VGroup) vObject, i + 1);
            } else {
                ((VPath) vObject).printVPath(i + 1);
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.b;
        vectorDrawableCompatState.d = parseTintModeCompat(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList namedColorStateList = TypedArrayUtils.getNamedColorStateList(typedArray, xmlPullParser, theme, "tint", 1);
        if (namedColorStateList != null) {
            vectorDrawableCompatState.c = namedColorStateList;
        }
        vectorDrawableCompatState.e = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.e);
        vPathRenderer.f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, vPathRenderer.f);
        vPathRenderer.g = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, vPathRenderer.g);
        if (vPathRenderer.f <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (vPathRenderer.g <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        vPathRenderer.d = typedArray.getDimension(3, vPathRenderer.d);
        vPathRenderer.e = typedArray.getDimension(2, vPathRenderer.e);
        if (vPathRenderer.d <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (vPathRenderer.e <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
        }
        vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, vPathRenderer.getAlpha()));
        String string = typedArray.getString(0);
        if (string != null) {
            vPathRenderer.i = string;
            vPathRenderer.k.put(string, vPathRenderer);
        }
    }

    PorterDuffColorFilter a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    Object a(String str) {
        return this.mVectorState.b.k.get(str);
    }

    void a(boolean z) {
        this.mAllowCaching = z;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        if (this.d == null) {
            return false;
        }
        DrawableCompat.canApplyTheme(this.d);
        return false;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.d != null) {
            this.d.draw(canvas);
            return;
        }
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.mColorFilter == null ? this.mTintFilter : this.mColorFilter;
        canvas.getMatrix(this.mTmpMatrix);
        this.mTmpMatrix.getValues(this.mTmpFloats);
        float fAbs = Math.abs(this.mTmpFloats[0]);
        float fAbs2 = Math.abs(this.mTmpFloats[4]);
        float fAbs3 = Math.abs(this.mTmpFloats[1]);
        float fAbs4 = Math.abs(this.mTmpFloats[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iMin = Math.min(2048, (int) (this.mTmpBounds.width() * fAbs));
        int iMin2 = Math.min(2048, (int) (this.mTmpBounds.height() * fAbs2));
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(this.mTmpBounds.left, this.mTmpBounds.top);
        if (needMirroring()) {
            canvas.translate(this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        this.mVectorState.createCachedBitmapIfNeeded(iMin, iMin2);
        if (!this.mAllowCaching) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2);
        } else if (!this.mVectorState.canReuseCache()) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2);
            this.mVectorState.updateCacheStates();
        }
        this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, colorFilter, this.mTmpBounds);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.d != null ? DrawableCompat.getAlpha(this.d) : this.mVectorState.b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        if (this.d != null) {
            return this.d.getChangingConfigurations();
        }
        return this.mVectorState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.d != null ? DrawableCompat.getColorFilter(this.d) : this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.d != null && Build.VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.d.getConstantState());
        }
        this.mVectorState.a = getChangingConfigurations();
        return this.mVectorState;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.d != null ? this.d.getIntrinsicHeight() : (int) this.mVectorState.b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.d != null ? this.d.getIntrinsicWidth() : (int) this.mVectorState.b.d;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.d != null) {
            return this.d.getOpacity();
        }
        return -3;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public float getPixelSize() {
        if (this.mVectorState == null || this.mVectorState.b == null || this.mVectorState.b.d == 0.0f || this.mVectorState.b.e == 0.0f || this.mVectorState.b.g == 0.0f || this.mVectorState.b.f == 0.0f) {
            return 1.0f;
        }
        float f = this.mVectorState.b.d;
        float f2 = this.mVectorState.b.e;
        return Math.min(this.mVectorState.b.f / f, this.mVectorState.b.g / f2);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        if (this.d != null) {
            this.d.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (this.d != null) {
            DrawableCompat.inflate(this.d, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.b = new VPathRenderer();
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.a);
        updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser, theme);
        typedArrayObtainAttributes.recycle();
        vectorDrawableCompatState.a = getChangingConfigurations();
        vectorDrawableCompatState.k = true;
        inflateInternal(resources, xmlPullParser, attributeSet, theme);
        this.mTintFilter = a(this.mTintFilter, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        if (this.d != null) {
            this.d.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.d != null ? DrawableCompat.isAutoMirrored(this.d) : this.mVectorState.e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.d != null ? this.d.isStateful() : super.isStateful() || (this.mVectorState != null && (this.mVectorState.isStateful() || (this.mVectorState.c != null && this.mVectorState.c.isStateful())));
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (this.d != null) {
            this.d.mutate();
            return this;
        }
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (this.d != null) {
            this.d.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        if (this.d != null) {
            return this.d.setState(iArr);
        }
        boolean z = false;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.c != null && vectorDrawableCompatState.d != null) {
            this.mTintFilter = a(this.mTintFilter, vectorDrawableCompatState.c, vectorDrawableCompatState.d);
            invalidateSelf();
            z = true;
        }
        if (!vectorDrawableCompatState.isStateful() || !vectorDrawableCompatState.onStateChanged(iArr)) {
            return z;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j) {
        if (this.d != null) {
            this.d.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.d != null) {
            this.d.setAlpha(i);
        } else if (this.mVectorState.b.getRootAlpha() != i) {
            this.mVectorState.b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.d != null) {
            DrawableCompat.setAutoMirrored(this.d, z);
        } else {
            this.mVectorState.e = z;
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(int i, PorterDuff.Mode mode) {
        super.setColorFilter(i, mode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.d != null) {
            this.d.setColorFilter(colorFilter);
        } else {
            this.mColorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTint(int i) {
        if (this.d != null) {
            DrawableCompat.setTint(this.d, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(ColorStateList colorStateList) {
        if (this.d != null) {
            DrawableCompat.setTintList(this.d, colorStateList);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.c != colorStateList) {
            vectorDrawableCompatState.c = colorStateList;
            this.mTintFilter = a(this.mTintFilter, colorStateList, vectorDrawableCompatState.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.d != null) {
            DrawableCompat.setTintMode(this.d, mode);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.d != mode) {
            vectorDrawableCompatState.d = mode;
            this.mTintFilter = a(this.mTintFilter, vectorDrawableCompatState.c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return this.d != null ? this.d.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        if (this.d != null) {
            this.d.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }
}
