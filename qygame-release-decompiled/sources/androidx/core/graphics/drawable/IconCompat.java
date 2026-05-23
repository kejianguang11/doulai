package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final String TAG = "IconCompat";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    static final PorterDuff.Mode b = PorterDuff.Mode.SRC_IN;
    Object a;
    PorterDuff.Mode c;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public byte[] mData;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt2;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Parcelable mParcelable;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mString1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ColorStateList mTintList;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mTintModeStr;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int mType;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface IconType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public IconCompat() {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.c = b;
        this.mTintModeStr = null;
    }

    private IconCompat(int i) {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.c = b;
        this.mTintModeStr = null;
        this.mType = i;
    }

    @VisibleForTesting
    static Bitmap a(Bitmap bitmap, boolean z) {
        int iMin = (int) (Math.min(bitmap.getWidth(), bitmap.getHeight()) * DEFAULT_VIEW_PORT_SCALE);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMin, iMin, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(3);
        float f = iMin;
        float f2 = 0.5f * f;
        float f3 = ICON_DIAMETER_FACTOR * f2;
        if (z) {
            float f4 = BLUR_FACTOR * f;
            paint.setColor(0);
            paint.setShadowLayer(f4, 0.0f, f * KEY_SHADOW_OFFSET_FACTOR, 1023410176);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.setShadowLayer(f4, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((-(bitmap.getWidth() - iMin)) / 2, (-(bitmap.getHeight() - iMin)) / 2);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(f2, f2, f3, paint);
        canvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0075  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static IconCompat createFromBundle(@NonNull Bundle bundle) {
        Object parcelable;
        int i = bundle.getInt("type");
        IconCompat iconCompat = new IconCompat(i);
        iconCompat.mInt1 = bundle.getInt("int1");
        iconCompat.mInt2 = bundle.getInt("int2");
        iconCompat.mString1 = bundle.getString("string1");
        if (bundle.containsKey("tint_list")) {
            iconCompat.mTintList = (ColorStateList) bundle.getParcelable("tint_list");
        }
        if (bundle.containsKey("tint_mode")) {
            iconCompat.c = PorterDuff.Mode.valueOf(bundle.getString("tint_mode"));
        }
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    parcelable = bundle.getParcelable("obj");
                    break;
                case 2:
                case 4:
                case 6:
                    parcelable = bundle.getString("obj");
                    break;
                case 3:
                    parcelable = bundle.getByteArray("obj");
                    break;
                default:
                    Log.w(TAG, "Unknown type " + i);
                    return null;
            }
        }
        iconCompat.a = parcelable;
        return iconCompat;
    }

    @Nullable
    @RequiresApi(23)
    public static IconCompat createFromIcon(@NonNull Context context, @NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        int type = getType(icon);
        if (type == 2) {
            String resPackage = getResPackage(icon);
            try {
                return createWithResource(getResources(context, resPackage), resPackage, getResId(icon));
            } catch (Resources.NotFoundException unused) {
                throw new IllegalArgumentException("Icon resource cannot be found");
            }
        }
        if (type == 4) {
            return createWithContentUri(getUri(icon));
        }
        if (type == 6) {
            return createWithAdaptiveBitmapContentUri(getUri(icon));
        }
        IconCompat iconCompat = new IconCompat(-1);
        iconCompat.a = icon;
        return iconCompat;
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIcon(@NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        int type = getType(icon);
        if (type == 2) {
            return createWithResource(null, getResPackage(icon), getResId(icon));
        }
        if (type == 4) {
            return createWithContentUri(getUri(icon));
        }
        if (type == 6) {
            return createWithAdaptiveBitmapContentUri(getUri(icon));
        }
        IconCompat iconCompat = new IconCompat(-1);
        iconCompat.a = icon;
        return iconCompat;
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIconOrNullIfZeroResId(@NonNull Icon icon) {
        if (getType(icon) == 2 && getResId(icon) == 0) {
            return null;
        }
        return createFromIcon(icon);
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(5);
        iconCompat.a = bitmap;
        return iconCompat;
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull Uri uri) {
        if (uri != null) {
            return createWithAdaptiveBitmapContentUri(uri.toString());
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat iconCompat = new IconCompat(6);
        iconCompat.a = str;
        return iconCompat;
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.a = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri != null) {
            return createWithContentUri(uri.toString());
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    public static IconCompat createWithContentUri(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat iconCompat = new IconCompat(4);
        iconCompat.a = str;
        return iconCompat;
    }

    public static IconCompat createWithData(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        IconCompat iconCompat = new IconCompat(3);
        iconCompat.a = bArr;
        iconCompat.mInt1 = i;
        iconCompat.mInt2 = i2;
        return iconCompat;
    }

    public static IconCompat createWithResource(Context context, @DrawableRes int i) {
        if (context != null) {
            return createWithResource(context.getResources(), context.getPackageName(), i);
        }
        throw new IllegalArgumentException("Context must not be null.");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createWithResource(Resources resources, String str, @DrawableRes int i) {
        if (str == null) {
            throw new IllegalArgumentException("Package must not be null.");
        }
        if (i == 0) {
            throw new IllegalArgumentException("Drawable resource ID must not be 0");
        }
        IconCompat iconCompat = new IconCompat(2);
        iconCompat.mInt1 = i;
        if (resources != null) {
            try {
                iconCompat.a = resources.getResourceName(i);
            } catch (Resources.NotFoundException unused) {
                throw new IllegalArgumentException("Icon resource cannot be found");
            }
        } else {
            iconCompat.a = str;
        }
        iconCompat.mString1 = str;
        return iconCompat;
    }

    @DrawableRes
    @IdRes
    @RequiresApi(23)
    private static int getResId(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Unable to get icon resource", e);
            return 0;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static String getResPackage(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage();
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Unable to get icon package", e);
            return null;
        }
    }

    private static Resources getResources(Context context, String str) {
        if ("android".equals(str)) {
            return Resources.getSystem();
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8192);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, String.format("Unable to find pkg=%s for icon", str), e);
            return null;
        }
    }

    @RequiresApi(23)
    private static int getType(@NonNull Icon icon) {
        String str;
        StringBuilder sb;
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getType();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getType", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            e = e;
            str = TAG;
            sb = new StringBuilder();
            sb.append("Unable to get icon type ");
            sb.append(icon);
            Log.e(str, sb.toString(), e);
            return -1;
        } catch (NoSuchMethodException e2) {
            e = e2;
            str = TAG;
            sb = new StringBuilder();
            sb.append("Unable to get icon type ");
            sb.append(icon);
            Log.e(str, sb.toString(), e);
            return -1;
        } catch (InvocationTargetException e3) {
            e = e3;
            str = TAG;
            sb = new StringBuilder();
            sb.append("Unable to get icon type ");
            sb.append(icon);
            Log.e(str, sb.toString(), e);
            return -1;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static Uri getUri(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getUri();
        }
        try {
            return (Uri) icon.getClass().getMethod("getUri", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Unable to get icon uri", e);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Drawable loadDrawableInner(Context context) {
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), (Bitmap) this.a);
            case 2:
                String resPackage = getResPackage();
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName();
                }
                try {
                    return ResourcesCompat.getDrawable(getResources(context, resPackage), this.mInt1, context.getTheme());
                } catch (RuntimeException e) {
                    Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.mInt1), this.a), e);
                }
                break;
            case 3:
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[]) this.a, this.mInt1, this.mInt2));
            case 4:
                InputStream uriInputStream = getUriInputStream(context);
                if (uriInputStream != null) {
                    return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream));
                }
                return null;
            case 5:
                return new BitmapDrawable(context.getResources(), a((Bitmap) this.a, false));
            case 6:
                InputStream uriInputStream2 = getUriInputStream(context);
                if (uriInputStream2 != null) {
                    return Build.VERSION.SDK_INT >= 26 ? new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream2))) : new BitmapDrawable(context.getResources(), a(BitmapFactory.decodeStream(uriInputStream2), false));
                }
                return null;
            default:
                return null;
        }
    }

    private static String typeToString(int i) {
        switch (i) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable, @NonNull Context context) {
        Bitmap bitmapA;
        checkResource(context);
        int i = this.mType;
        if (i != 5) {
            switch (i) {
                case 1:
                    bitmapA = (Bitmap) this.a;
                    if (drawable != null) {
                        bitmapA = bitmapA.copy(bitmapA.getConfig(), true);
                    }
                    break;
                case 2:
                    try {
                        Context contextCreatePackageContext = context.createPackageContext(getResPackage(), 0);
                        if (drawable == null) {
                            intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(contextCreatePackageContext, this.mInt1));
                            return;
                        }
                        Drawable drawable2 = ContextCompat.getDrawable(contextCreatePackageContext, this.mInt1);
                        if (drawable2.getIntrinsicWidth() <= 0 || drawable2.getIntrinsicHeight() <= 0) {
                            int launcherLargeIconSize = ((ActivityManager) contextCreatePackageContext.getSystemService("activity")).getLauncherLargeIconSize();
                            bitmapA = Bitmap.createBitmap(launcherLargeIconSize, launcherLargeIconSize, Bitmap.Config.ARGB_8888);
                        } else {
                            bitmapA = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        }
                        drawable2.setBounds(0, 0, bitmapA.getWidth(), bitmapA.getHeight());
                        drawable2.draw(new Canvas(bitmapA));
                    } catch (PackageManager.NameNotFoundException e) {
                        throw new IllegalArgumentException("Can't find package " + this.a, e);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            }
        } else {
            bitmapA = a((Bitmap) this.a, true);
        }
        if (drawable != null) {
            int width = bitmapA.getWidth();
            int height = bitmapA.getHeight();
            drawable.setBounds(width / 2, height / 2, width, height);
            drawable.draw(new Canvas(bitmapA));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", bitmapA);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void checkResource(@NonNull Context context) {
        if (this.mType != 2 || this.a == null) {
            return;
        }
        String str = (String) this.a;
        if (str.contains(":")) {
            String str2 = str.split(":", -1)[1];
            String str3 = str2.split("/", -1)[0];
            String str4 = str2.split("/", -1)[1];
            String str5 = str.split(":", -1)[0];
            if ("0_resource_name_obfuscated".equals(str4)) {
                Log.i(TAG, "Found obfuscated resource, not trying to update resource id for it");
                return;
            }
            String resPackage = getResPackage();
            int identifier = getResources(context, resPackage).getIdentifier(str4, str3, str5);
            if (this.mInt1 != identifier) {
                Log.i(TAG, "Id has changed for " + resPackage + " " + str);
                this.mInt1 = identifier;
            }
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bitmap getBitmap() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            if (this.a instanceof Bitmap) {
                return (Bitmap) this.a;
            }
            return null;
        }
        if (this.mType == 1) {
            return (Bitmap) this.a;
        }
        if (this.mType == 5) {
            return a((Bitmap) this.a, true);
        }
        throw new IllegalStateException("called getBitmap() on " + this);
    }

    @IdRes
    public int getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResId((Icon) this.a);
        }
        if (this.mType == 2) {
            return this.mInt1;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    @NonNull
    public String getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResPackage((Icon) this.a);
        }
        if (this.mType == 2) {
            return TextUtils.isEmpty(this.mString1) ? ((String) this.a).split(":", -1)[0] : this.mString1;
        }
        throw new IllegalStateException("called getResPackage() on " + this);
    }

    public int getType() {
        return (this.mType != -1 || Build.VERSION.SDK_INT < 23) ? this.mType : getType((Icon) this.a);
    }

    @NonNull
    public Uri getUri() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getUri((Icon) this.a);
        }
        if (this.mType == 4 || this.mType == 6) {
            return Uri.parse((String) this.a);
        }
        throw new IllegalStateException("called getUri() on " + this);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public InputStream getUriInputStream(@NonNull Context context) {
        String str;
        StringBuilder sb;
        String str2;
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if (DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT.equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                e = e;
                str = TAG;
                sb = new StringBuilder();
                str2 = "Unable to load image from URI: ";
            }
        } else {
            try {
                return new FileInputStream(new File((String) this.a));
            } catch (FileNotFoundException e2) {
                e = e2;
                str = TAG;
                sb = new StringBuilder();
                str2 = "Unable to load image from path: ";
            }
        }
        sb.append(str2);
        sb.append(uri);
        Log.w(str, sb.toString(), e);
        return null;
    }

    @Nullable
    public Drawable loadDrawable(@NonNull Context context) {
        checkResource(context);
        if (Build.VERSION.SDK_INT >= 23) {
            return toIcon(context).loadDrawable(context);
        }
        Drawable drawableLoadDrawableInner = loadDrawableInner(context);
        if (drawableLoadDrawableInner != null && (this.mTintList != null || this.c != b)) {
            drawableLoadDrawableInner.mutate();
            DrawableCompat.setTintList(drawableLoadDrawableInner, this.mTintList);
            DrawableCompat.setTintMode(drawableLoadDrawableInner, this.c);
        }
        return drawableLoadDrawableInner;
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPostParceling() {
        Object obj;
        this.c = PorterDuff.Mode.valueOf(this.mTintModeStr);
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    if (this.mParcelable == null) {
                        this.a = this.mData;
                        this.mType = 3;
                        this.mInt1 = 0;
                        this.mInt2 = this.mData.length;
                        return;
                    }
                    break;
                case 2:
                case 4:
                case 6:
                    this.a = new String(this.mData, Charset.forName("UTF-16"));
                    if (this.mType == 2 && this.mString1 == null) {
                        this.mString1 = ((String) this.a).split(":", -1)[0];
                        return;
                    }
                    return;
                case 3:
                    obj = this.mData;
                    this.a = obj;
                default:
                    return;
            }
        } else if (this.mParcelable == null) {
            throw new IllegalArgumentException("Invalid icon");
        }
        obj = this.mParcelable;
        this.a = obj;
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPreParceling(boolean z) {
        byte[] byteArray;
        String string;
        this.mTintModeStr = this.c.name();
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    if (z) {
                        Bitmap bitmap = (Bitmap) this.a;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                        this.mData = byteArray;
                        return;
                    }
                    break;
                case 2:
                    string = (String) this.a;
                    byteArray = string.getBytes(Charset.forName("UTF-16"));
                    this.mData = byteArray;
                    return;
                case 3:
                    byteArray = (byte[]) this.a;
                    this.mData = byteArray;
                    return;
                case 4:
                case 6:
                    string = this.a.toString();
                    byteArray = string.getBytes(Charset.forName("UTF-16"));
                    this.mData = byteArray;
                    return;
                default:
                    return;
            }
        } else if (z) {
            throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        }
        this.mParcelable = (Parcelable) this.a;
    }

    public IconCompat setTint(@ColorInt int i) {
        return setTintList(ColorStateList.valueOf(i));
    }

    public IconCompat setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public IconCompat setTintMode(PorterDuff.Mode mode) {
        this.c = mode;
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0066  */
    @NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Bundle toBundle() {
        String str;
        Parcelable parcelable;
        Bundle bundle = new Bundle();
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    str = "obj";
                    parcelable = (Bitmap) this.a;
                    break;
                case 2:
                case 4:
                case 6:
                    bundle.putString("obj", (String) this.a);
                    bundle.putInt("type", this.mType);
                    bundle.putInt("int1", this.mInt1);
                    bundle.putInt("int2", this.mInt2);
                    bundle.putString("string1", this.mString1);
                    if (this.mTintList != null) {
                        bundle.putParcelable("tint_list", this.mTintList);
                    }
                    if (this.c != b) {
                        bundle.putString("tint_mode", this.c.name());
                    }
                    return bundle;
                case 3:
                    bundle.putByteArray("obj", (byte[]) this.a);
                    bundle.putInt("type", this.mType);
                    bundle.putInt("int1", this.mInt1);
                    bundle.putInt("int2", this.mInt2);
                    bundle.putString("string1", this.mString1);
                    if (this.mTintList != null) {
                    }
                    if (this.c != b) {
                    }
                    return bundle;
                default:
                    throw new IllegalArgumentException("Invalid icon");
            }
        } else {
            str = "obj";
            parcelable = (Parcelable) this.a;
        }
        bundle.putParcelable(str, parcelable);
        bundle.putInt("type", this.mType);
        bundle.putInt("int1", this.mInt1);
        bundle.putInt("int2", this.mInt2);
        bundle.putString("string1", this.mString1);
        if (this.mTintList != null) {
        }
        if (this.c != b) {
        }
        return bundle;
    }

    @NonNull
    @RequiresApi(23)
    @Deprecated
    public Icon toIcon() {
        return toIcon(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00bd  */
    @NonNull
    @RequiresApi(23)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Icon toIcon(@Nullable Context context) {
        Bitmap bitmapA;
        Icon iconCreateWithResource;
        Bitmap bitmapDecodeStream;
        Bitmap bitmapDecodeStream2;
        int i = this.mType;
        if (i == -1) {
            return (Icon) this.a;
        }
        switch (i) {
            case 1:
                bitmapA = (Bitmap) this.a;
                iconCreateWithResource = Icon.createWithBitmap(bitmapA);
                if (this.mTintList != null) {
                    iconCreateWithResource.setTintList(this.mTintList);
                }
                if (this.c != b) {
                    iconCreateWithResource.setTintMode(this.c);
                }
                return iconCreateWithResource;
            case 2:
                iconCreateWithResource = Icon.createWithResource(getResPackage(), this.mInt1);
                if (this.mTintList != null) {
                }
                if (this.c != b) {
                }
                return iconCreateWithResource;
            case 3:
                iconCreateWithResource = Icon.createWithData((byte[]) this.a, this.mInt1, this.mInt2);
                if (this.mTintList != null) {
                }
                if (this.c != b) {
                }
                return iconCreateWithResource;
            case 4:
                iconCreateWithResource = Icon.createWithContentUri((String) this.a);
                if (this.mTintList != null) {
                }
                if (this.c != b) {
                }
                return iconCreateWithResource;
            case 5:
                if (Build.VERSION.SDK_INT >= 26) {
                    bitmapDecodeStream2 = (Bitmap) this.a;
                    iconCreateWithResource = Icon.createWithAdaptiveBitmap(bitmapDecodeStream2);
                    if (this.mTintList != null) {
                    }
                    if (this.c != b) {
                    }
                    return iconCreateWithResource;
                }
                bitmapDecodeStream = (Bitmap) this.a;
                bitmapA = a(bitmapDecodeStream, false);
                iconCreateWithResource = Icon.createWithBitmap(bitmapA);
                if (this.mTintList != null) {
                }
                if (this.c != b) {
                }
                return iconCreateWithResource;
            case 6:
                if (Build.VERSION.SDK_INT >= 30) {
                    iconCreateWithResource = Icon.createWithAdaptiveBitmapContentUri(getUri());
                    if (this.mTintList != null) {
                    }
                    if (this.c != b) {
                    }
                    return iconCreateWithResource;
                }
                if (context == null) {
                    throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + getUri());
                }
                InputStream uriInputStream = getUriInputStream(context);
                if (uriInputStream == null) {
                    throw new IllegalStateException("Cannot load adaptive icon from uri: " + getUri());
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    bitmapDecodeStream2 = BitmapFactory.decodeStream(uriInputStream);
                    iconCreateWithResource = Icon.createWithAdaptiveBitmap(bitmapDecodeStream2);
                    if (this.mTintList != null) {
                    }
                    if (this.c != b) {
                    }
                    return iconCreateWithResource;
                }
                bitmapDecodeStream = BitmapFactory.decodeStream(uriInputStream);
                bitmapA = a(bitmapDecodeStream, false);
                iconCreateWithResource = Icon.createWithBitmap(bitmapA);
                if (this.mTintList != null) {
                }
                if (this.c != b) {
                }
                return iconCreateWithResource;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    @NonNull
    public String toString() {
        int height;
        if (this.mType == -1) {
            return String.valueOf(this.a);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=");
        sb.append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=");
                sb.append(((Bitmap) this.a).getWidth());
                sb.append("x");
                height = ((Bitmap) this.a).getHeight();
                sb.append(height);
                break;
            case 2:
                sb.append(" pkg=");
                sb.append(this.mString1);
                sb.append(" id=");
                sb.append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=");
                sb.append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=");
                    height = this.mInt2;
                    sb.append(height);
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=");
                sb.append(this.a);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.c != b) {
            sb.append(" mode=");
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
