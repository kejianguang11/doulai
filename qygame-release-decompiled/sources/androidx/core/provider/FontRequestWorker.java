package androidx.core.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import com.igexin.push.config.c;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
class FontRequestWorker {
    static final LruCache<String, Typeface> a = new LruCache<>(16);
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = RequestExecutor.a("fonts-androidx", 10, c.d);
    static final Object b = new Object();

    @GuardedBy("LOCK")
    static final SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> c = new SimpleArrayMap<>();

    static final class TypefaceResult {
        final Typeface a;
        final int b;

        TypefaceResult(int i) {
            this.a = null;
            this.b = i;
        }

        @SuppressLint({"WrongConstant"})
        TypefaceResult(@NonNull Typeface typeface) {
            this.a = typeface;
            this.b = 0;
        }

        @SuppressLint({"WrongConstant"})
        boolean a() {
            return this.b == 0;
        }
    }

    private FontRequestWorker() {
    }

    static Typeface a(@NonNull final Context context, @NonNull final FontRequest fontRequest, final int i, @Nullable Executor executor, @NonNull final CallbackWithHandler callbackWithHandler) {
        final String strCreateCacheId = createCacheId(fontRequest, i);
        Typeface typeface = a.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.a(new TypefaceResult(typeface));
            return typeface;
        }
        Consumer<TypefaceResult> consumer = new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.2
            @Override // androidx.core.util.Consumer
            public void accept(TypefaceResult typefaceResult) {
                callbackWithHandler.a(typefaceResult);
            }
        };
        synchronized (b) {
            ArrayList<Consumer<TypefaceResult>> arrayList = c.get(strCreateCacheId);
            if (arrayList != null) {
                arrayList.add(consumer);
                return null;
            }
            ArrayList<Consumer<TypefaceResult>> arrayList2 = new ArrayList<>();
            arrayList2.add(consumer);
            c.put(strCreateCacheId, arrayList2);
            Callable<TypefaceResult> callable = new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public TypefaceResult call() {
                    return FontRequestWorker.a(strCreateCacheId, context, fontRequest, i);
                }
            };
            if (executor == null) {
                executor = DEFAULT_EXECUTOR_SERVICE;
            }
            RequestExecutor.a(executor, callable, new Consumer<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.4
                @Override // androidx.core.util.Consumer
                public void accept(TypefaceResult typefaceResult) {
                    synchronized (FontRequestWorker.b) {
                        ArrayList<Consumer<TypefaceResult>> arrayList3 = FontRequestWorker.c.get(strCreateCacheId);
                        if (arrayList3 == null) {
                            return;
                        }
                        FontRequestWorker.c.remove(strCreateCacheId);
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            arrayList3.get(i2).accept(typefaceResult);
                        }
                    }
                }
            });
            return null;
        }
    }

    static Typeface a(@NonNull final Context context, @NonNull final FontRequest fontRequest, @NonNull CallbackWithHandler callbackWithHandler, final int i, int i2) {
        final String strCreateCacheId = createCacheId(fontRequest, i);
        Typeface typeface = a.get(strCreateCacheId);
        if (typeface != null) {
            callbackWithHandler.a(new TypefaceResult(typeface));
            return typeface;
        }
        if (i2 == -1) {
            TypefaceResult typefaceResultA = a(strCreateCacheId, context, fontRequest, i);
            callbackWithHandler.a(typefaceResultA);
            return typefaceResultA.a;
        }
        try {
            TypefaceResult typefaceResult = (TypefaceResult) RequestExecutor.a(DEFAULT_EXECUTOR_SERVICE, new Callable<TypefaceResult>() { // from class: androidx.core.provider.FontRequestWorker.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public TypefaceResult call() {
                    return FontRequestWorker.a(strCreateCacheId, context, fontRequest, i);
                }
            }, i2);
            callbackWithHandler.a(typefaceResult);
            return typefaceResult.a;
        } catch (InterruptedException unused) {
            callbackWithHandler.a(new TypefaceResult(-3));
            return null;
        }
    }

    @NonNull
    static TypefaceResult a(@NonNull String str, @NonNull Context context, @NonNull FontRequest fontRequest, int i) {
        Typeface typeface = a.get(str);
        if (typeface != null) {
            return new TypefaceResult(typeface);
        }
        try {
            FontsContractCompat.FontFamilyResult fontFamilyResultA = FontProvider.a(context, fontRequest, (CancellationSignal) null);
            int fontFamilyResultStatus = getFontFamilyResultStatus(fontFamilyResultA);
            if (fontFamilyResultStatus != 0) {
                return new TypefaceResult(fontFamilyResultStatus);
            }
            Typeface typefaceCreateFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResultA.getFonts(), i);
            if (typefaceCreateFromFontInfo == null) {
                return new TypefaceResult(-3);
            }
            a.put(str, typefaceCreateFromFontInfo);
            return new TypefaceResult(typefaceCreateFromFontInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult(-1);
        }
    }

    static void a() {
        a.evictAll();
    }

    private static String createCacheId(@NonNull FontRequest fontRequest, int i) {
        return fontRequest.a() + "-" + i;
    }

    @SuppressLint({"WrongConstant"})
    private static int getFontFamilyResultStatus(@NonNull FontsContractCompat.FontFamilyResult fontFamilyResult) {
        int i = 1;
        if (fontFamilyResult.getStatusCode() != 0) {
            return fontFamilyResult.getStatusCode() != 1 ? -3 : -2;
        }
        FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
        if (fonts != null && fonts.length != 0) {
            i = 0;
            for (FontsContractCompat.FontInfo fontInfo : fonts) {
                int resultCode = fontInfo.getResultCode();
                if (resultCode != 0) {
                    if (resultCode < 0) {
                        return -3;
                    }
                    return resultCode;
                }
            }
        }
        return i;
    }
}
