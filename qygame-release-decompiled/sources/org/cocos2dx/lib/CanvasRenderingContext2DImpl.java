package org.cocos2dx.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class CanvasRenderingContext2DImpl {
    private static final String TAG = "CanvasContext2D";
    private static final int TEXT_ALIGN_CENTER = 1;
    private static final int TEXT_ALIGN_LEFT = 0;
    private static final int TEXT_ALIGN_RIGHT = 2;
    private static final int TEXT_BASELINE_BOTTOM = 2;
    private static final int TEXT_BASELINE_MIDDLE = 1;
    private static final int TEXT_BASELINE_TOP = 0;
    private static float _sApproximatingOblique = -0.25f;
    private static WeakReference<Context> sContext;
    private static HashMap<String, Typeface> sTypefaceCache = new HashMap<>();
    private Bitmap mBitmap;
    private Paint mLinePaint;
    private Path mLinePath;
    private TextPaint mTextPaint;
    private Canvas mCanvas = new Canvas();
    private int mTextAlign = 0;
    private int mTextBaseline = 2;
    private int mFillStyleR = 0;
    private int mFillStyleG = 0;
    private int mFillStyleB = 0;
    private int mFillStyleA = 255;
    private int mStrokeStyleR = 0;
    private int mStrokeStyleG = 0;
    private int mStrokeStyleB = 0;
    private int mStrokeStyleA = 255;
    private String mFontName = "Arial";
    private float mFontSize = 40.0f;
    private float mLineWidth = 0.0f;
    private boolean mIsBoldFont = false;
    private boolean mIsItalicFont = false;
    private boolean mIsObliqueFont = false;
    private boolean mIsSmallCapsFontVariant = false;
    private String mLineCap = "butt";
    private String mLineJoin = "miter";

    private class Point {
        public float x;
        public float y;

        Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        Point(Point point) {
            this.x = point.x;
            this.y = point.y;
        }
    }

    private class Size {
        public float height;
        public float width;

        Size(float f, float f2) {
            this.width = f;
            this.height = f2;
        }
    }

    private CanvasRenderingContext2DImpl() {
    }

    private void _fillImageData(byte[] bArr, float f, float f2, float f3, float f4) {
        Log.d(TAG, "_fillImageData: ");
        int i = (int) (f * f2);
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 4;
            iArr[i2] = (((bArr[i3 + 3] & 255) & 255) << 24) | (((bArr[i3 + 0] & 255) & 255) << 16) | (((bArr[i3 + 1] & 255) & 255) << 8) | (bArr[i3 + 2] & 255 & 255);
        }
        int i4 = (int) f;
        this.mBitmap.setPixels(iArr, 0, i4, (int) f3, (int) f4, i4, (int) f2);
    }

    private void beginPath() {
        if (this.mLinePath == null) {
            this.mLinePath = new Path();
        }
        this.mLinePath.reset();
    }

    private void clearRect(float f, float f2, float f3, float f4) {
        int i = (int) (f3 * f4);
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = 0;
        }
        int i3 = (int) f3;
        this.mBitmap.setPixels(iArr, 0, i3, (int) f, (int) f2, i3, (int) f4);
    }

    private static void clearTypefaceCache() {
        sTypefaceCache.clear();
    }

    private void closePath() {
        this.mLinePath.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Point convertDrawPoint(Point point, String str) {
        float f;
        float f2;
        float f3;
        float f4;
        Point point2 = new Point(point);
        Size sizeMeasureTextReturnSize = measureTextReturnSize(str);
        if (this.mTextAlign != 1) {
            if (this.mTextAlign == 2) {
                f = point2.x;
                f2 = sizeMeasureTextReturnSize.width;
            }
            if (this.mTextBaseline == 0) {
                if (this.mTextBaseline == 1) {
                    f3 = point2.y;
                    f4 = sizeMeasureTextReturnSize.height / 2.0f;
                }
                return point2;
            }
            f3 = point2.y;
            f4 = sizeMeasureTextReturnSize.height;
            point2.y = f3 + f4;
            return point2;
        }
        f = point2.x;
        f2 = sizeMeasureTextReturnSize.width / 2.0f;
        point2.x = f - f2;
        if (this.mTextBaseline == 0) {
        }
        point2.y = f3 + f4;
        return point2;
    }

    private void createTextPaintIfNeeded() {
        if (this.mTextPaint == null) {
            this.mTextPaint = newPaint(this.mFontName, (int) this.mFontSize, this.mIsBoldFont, this.mIsItalicFont, this.mIsObliqueFont, this.mIsSmallCapsFontVariant);
        }
    }

    static void destroy() {
        sContext = null;
    }

    private void fill() {
        if (this.mLinePaint == null) {
            this.mLinePaint = new Paint();
        }
        if (this.mLinePath == null) {
            this.mLinePath = new Path();
        }
        this.mLinePaint.setARGB(this.mFillStyleA, this.mFillStyleR, this.mFillStyleG, this.mFillStyleB);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mCanvas.drawPath(this.mLinePath, this.mLinePaint);
        this.mLinePaint.setStrokeWidth(0.0f);
        setStrokeCap(this.mLinePaint);
        setStrokeJoin(this.mLinePaint);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mCanvas.drawPath(this.mLinePath, this.mLinePaint);
        this.mLinePaint.setStrokeWidth(this.mLineWidth);
    }

    private void fillRect(float f, float f2, float f3, float f4) {
        int i = ((this.mFillStyleA & 255) << 24) | ((this.mFillStyleR & 255) << 16) | ((this.mFillStyleG & 255) << 8) | (this.mFillStyleB & 255);
        int i2 = (int) (f3 * f4);
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = i;
        }
        int i4 = (int) f3;
        this.mBitmap.setPixels(iArr, 0, i4, (int) f, (int) f2, i4, (int) f4);
    }

    private void fillText(String str, float f, float f2, float f3) {
        createTextPaintIfNeeded();
        this.mTextPaint.setARGB(this.mFillStyleA, this.mFillStyleR, this.mFillStyleG, this.mFillStyleB);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        scaleX(this.mTextPaint, str, f3);
        Point pointConvertDrawPoint = convertDrawPoint(new Point(f, f2), str);
        this.mCanvas.drawText(str, pointConvertDrawPoint.x, pointConvertDrawPoint.y, this.mTextPaint);
    }

    private byte[] getDataRef() {
        if (this.mBitmap == null) {
            Log.e(TAG, "getDataRef return null");
            return null;
        }
        byte[] bArr = new byte[this.mBitmap.getWidth() * this.mBitmap.getHeight() * 4];
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.nativeOrder());
        this.mBitmap.copyPixelsToBuffer(byteBufferWrap);
        return bArr;
    }

    static void init(Context context) {
        sContext = new WeakReference<>(context);
    }

    private void lineTo(float f, float f2) {
        this.mLinePath.lineTo(f, f2);
    }

    private static void loadTypeface(String str, String str2) {
        if (sTypefaceCache.containsKey(str)) {
            return;
        }
        Typeface typefaceCreateFromAsset = null;
        try {
            if (str2.startsWith("/")) {
                typefaceCreateFromAsset = Typeface.createFromFile(str2);
            } else if (sContext.get() != null) {
                if (str2.startsWith("@assets/")) {
                    str2 = str2.substring("@assets/".length());
                }
                typefaceCreateFromAsset = Typeface.createFromAsset(sContext.get().getAssets(), str2);
            }
            if (typefaceCreateFromAsset != null) {
                sTypefaceCache.put(str, typefaceCreateFromAsset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float measureText(String str) {
        createTextPaintIfNeeded();
        return this.mTextPaint.measureText(str);
    }

    private Size measureTextReturnSize(String str) {
        createTextPaintIfNeeded();
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        return new Size(measureText(str), fontMetrics.descent - fontMetrics.ascent);
    }

    private void moveTo(float f, float f2) {
        this.mLinePath.moveTo(f, f2);
    }

    private static TextPaint newPaint(String str, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        String str2;
        Typeface typefaceCreate;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(i);
        textPaint.setAntiAlias(true);
        textPaint.setSubpixelText(true);
        if (z) {
            str2 = str + "-Bold";
            textPaint.setFakeBoldText(true);
        } else {
            str2 = str;
        }
        if (z2) {
            str2 = str2 + "-Italic";
        }
        if (sTypefaceCache.containsKey(str2)) {
            typefaceCreate = sTypefaceCache.get(str2);
        } else {
            typefaceCreate = Typeface.create(str, (z && z2) ? 3 : z ? 1 : z2 ? 2 : 0);
        }
        textPaint.setTypeface(typefaceCreate);
        if (z3) {
            textPaint.setTextSkewX(_sApproximatingOblique);
        }
        if (z4 && Build.VERSION.SDK_INT >= 21) {
            Cocos2dxReflectionHelper.invokeInstanceMethod(textPaint, "setFontFeatureSettings", new Class[]{String.class}, new Object[]{"smcp"});
        }
        return textPaint;
    }

    private void recreateBuffer(float f, float f2) {
        if (this.mBitmap != null) {
            this.mBitmap.recycle();
        }
        this.mBitmap = Bitmap.createBitmap((int) Math.ceil(f), (int) Math.ceil(f2), Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(this.mBitmap);
    }

    private void rect(float f, float f2, float f3, float f4) {
        beginPath();
        moveTo(f, f2);
        float f5 = f4 + f2;
        lineTo(f, f5);
        float f6 = f + f3;
        lineTo(f6, f5);
        lineTo(f6, f2);
        closePath();
    }

    private void restoreContext() {
        if (this.mCanvas.getSaveCount() > 1) {
            this.mCanvas.restore();
        }
    }

    private void saveContext() {
        this.mCanvas.save();
    }

    private void scaleX(TextPaint textPaint, String str, float f) {
        if (f < Float.MIN_VALUE) {
            return;
        }
        float fMeasureText = measureText(str);
        if (fMeasureText - f < Float.MIN_VALUE) {
            return;
        }
        textPaint.setTextScaleX(f / fMeasureText);
    }

    private void setFillStyle(float f, float f2, float f3, float f4) {
        this.mFillStyleR = (int) (f * 255.0f);
        this.mFillStyleG = (int) (f2 * 255.0f);
        this.mFillStyleB = (int) (f3 * 255.0f);
        this.mFillStyleA = (int) (f4 * 255.0f);
    }

    private void setLineCap(String str) {
        this.mLineCap = str;
    }

    private void setLineJoin(String str) {
        this.mLineJoin = str;
    }

    private void setLineWidth(float f) {
        this.mLineWidth = f;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setStrokeCap(Paint paint) {
        byte b;
        Paint.Cap cap;
        String str = this.mLineCap;
        int iHashCode = str.hashCode();
        if (iHashCode != -894674659) {
            if (iHashCode != 3035667) {
                b = (iHashCode == 108704142 && str.equals("round")) ? (byte) 1 : (byte) -1;
            } else if (str.equals("butt")) {
                b = 0;
            }
        } else if (str.equals("square")) {
            b = 2;
        }
        switch (b) {
            case 0:
                cap = Paint.Cap.BUTT;
                break;
            case 1:
                cap = Paint.Cap.ROUND;
                break;
            case 2:
                cap = Paint.Cap.SQUARE;
                break;
            default:
                return;
        }
        paint.setStrokeCap(cap);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setStrokeJoin(Paint paint) {
        byte b;
        Paint.Join join;
        String str = this.mLineJoin;
        int iHashCode = str.hashCode();
        if (iHashCode != 93630586) {
            if (iHashCode != 103906565) {
                b = (iHashCode == 108704142 && str.equals("round")) ? (byte) 1 : (byte) -1;
            } else if (str.equals("miter")) {
                b = 2;
            }
        } else if (str.equals("bevel")) {
            b = 0;
        }
        switch (b) {
            case 0:
                join = Paint.Join.BEVEL;
                break;
            case 1:
                join = Paint.Join.ROUND;
                break;
            case 2:
                join = Paint.Join.MITER;
                break;
            default:
                return;
        }
        paint.setStrokeJoin(join);
    }

    private void setStrokeStyle(float f, float f2, float f3, float f4) {
        this.mStrokeStyleR = (int) (f * 255.0f);
        this.mStrokeStyleG = (int) (f2 * 255.0f);
        this.mStrokeStyleB = (int) (f3 * 255.0f);
        this.mStrokeStyleA = (int) (f4 * 255.0f);
    }

    private void setTextAlign(int i) {
        this.mTextAlign = i;
    }

    private void setTextBaseline(int i) {
        this.mTextBaseline = i;
    }

    private void stroke() {
        if (this.mLinePaint == null) {
            this.mLinePaint = new Paint();
            this.mLinePaint.setAntiAlias(true);
        }
        if (this.mLinePath == null) {
            this.mLinePath = new Path();
        }
        this.mLinePaint.setARGB(this.mStrokeStyleA, this.mStrokeStyleR, this.mStrokeStyleG, this.mStrokeStyleB);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setStrokeWidth(this.mLineWidth);
        setStrokeCap(this.mLinePaint);
        setStrokeJoin(this.mLinePaint);
        this.mCanvas.drawPath(this.mLinePath, this.mLinePaint);
    }

    private void strokeText(String str, float f, float f2, float f3) {
        createTextPaintIfNeeded();
        this.mTextPaint.setARGB(this.mStrokeStyleA, this.mStrokeStyleR, this.mStrokeStyleG, this.mStrokeStyleB);
        this.mTextPaint.setStyle(Paint.Style.STROKE);
        this.mTextPaint.setStrokeWidth(this.mLineWidth);
        scaleX(this.mTextPaint, str, f3);
        Point pointConvertDrawPoint = convertDrawPoint(new Point(f, f2), str);
        this.mCanvas.drawText(str, pointConvertDrawPoint.x, pointConvertDrawPoint.y, this.mTextPaint);
    }

    private void updateFont(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mFontName = str;
        this.mFontSize = f;
        this.mIsBoldFont = z;
        this.mIsItalicFont = z2;
        this.mIsObliqueFont = z3;
        this.mIsSmallCapsFontVariant = z4;
        this.mTextPaint = null;
    }
}
