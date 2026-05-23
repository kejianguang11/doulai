package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontsContractCompat;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class TypefaceCompat {
    private static final LruCache<String, Typeface> sTypefaceCache;
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static class ResourcesCallbackAdapter extends FontsContractCompat.FontRequestCallback {

        @Nullable
        private ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(@Nullable ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRequestFailed(int i) {
            if (this.mFontCallback != null) {
                this.mFontCallback.onFontRetrievalFailed(i);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRetrieved(@NonNull Typeface typeface) {
            if (this.mFontCallback != null) {
                this.mFontCallback.onFontRetrieved(typeface);
            }
        }
    }

    static {
        sTypefaceCompatImpl = Build.VERSION.SDK_INT >= 29 ? new TypefaceCompatApi29Impl() : Build.VERSION.SDK_INT >= 28 ? new TypefaceCompatApi28Impl() : Build.VERSION.SDK_INT >= 26 ? new TypefaceCompatApi26Impl() : (Build.VERSION.SDK_INT < 24 || !TypefaceCompatApi24Impl.isUsable()) ? Build.VERSION.SDK_INT >= 21 ? new TypefaceCompatApi21Impl() : new TypefaceCompatBaseImpl() : new TypefaceCompatApi24Impl();
        sTypefaceCache = new LruCache<>(16);
    }

    private TypefaceCompat() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @VisibleForTesting
    public static void clearCache() {
        sTypefaceCache.evictAll();
    }

    @NonNull
    public static Typeface create(@NonNull Context context, @Nullable Typeface typeface, int i) {
        Typeface bestFontFromFamily;
        if (context != null) {
            return (Build.VERSION.SDK_INT >= 21 || (bestFontFromFamily = getBestFontFromFamily(context, typeface, i)) == null) ? Typeface.create(typeface, i) : bestFontFromFamily;
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromFontInfo(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fontInfoArr, i);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFamilyXml(@NonNull Context context, @NonNull FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, @NonNull Resources resources, int i, int i2, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean z) {
        Typeface typefaceCreateFromFontFamilyFilesResourceEntry;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            Typeface systemFontFamily = getSystemFontFamily(providerResourceEntry.getSystemFontFamilyName());
            if (systemFontFamily != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(systemFontFamily, handler);
                }
                return systemFontFamily;
            }
            typefaceCreateFromFontFamilyFilesResourceEntry = FontsContractCompat.requestFont(context, providerResourceEntry.getRequest(), i2, !z ? fontCallback != null : providerResourceEntry.getFetchStrategy() != 0, z ? providerResourceEntry.getTimeout() : -1, ResourcesCompat.FontCallback.getHandler(handler), new ResourcesCallbackAdapter(fontCallback));
        } else {
            typefaceCreateFromFontFamilyFilesResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, i2);
            if (fontCallback != null) {
                if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
                    fontCallback.callbackSuccessAsync(typefaceCreateFromFontFamilyFilesResourceEntry, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
        }
        if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
            sTypefaceCache.put(createResourceUid(resources, i, i2), typefaceCreateFromFontFamilyFilesResourceEntry);
        }
        return typefaceCreateFromFontFamilyFilesResourceEntry;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface createFromResourcesFontFile(@NonNull Context context, @NonNull Resources resources, int i, String str, int i2) {
        Typeface typefaceCreateFromResourcesFontFile = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, i, str, i2);
        if (typefaceCreateFromResourcesFontFile != null) {
            sTypefaceCache.put(createResourceUid(resources, i, i2), typefaceCreateFromResourcesFontFile);
        }
        return typefaceCreateFromResourcesFontFile;
    }

    private static String createResourceUid(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface findFromCache(@NonNull Resources resources, int i, int i2) {
        return sTypefaceCache.get(createResourceUid(resources, i, i2));
    }

    @Nullable
    private static Typeface getBestFontFromFamily(Context context, Typeface typeface, int i) {
        FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntryA = sTypefaceCompatImpl.a(typeface);
        if (fontFamilyFilesResourceEntryA == null) {
            return null;
        }
        return sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntryA, context.getResources(), i);
    }

    private static Typeface getSystemFontFamily(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        Typeface typefaceCreate = Typeface.create(str, 0);
        Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
        if (typefaceCreate == null || typefaceCreate.equals(typefaceCreate2)) {
            return null;
        }
        return typefaceCreate;
    }
}
