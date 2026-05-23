package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(29)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected Typeface a(Context context, InputStream inputStream) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected FontsContractCompat.FontInfo a(FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) {
        try {
            FontFamily.Builder builder = null;
            for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
                try {
                    Font fontBuild = new Font.Builder(resources, fontFileResourceEntry.getResourceId()).setWeight(fontFileResourceEntry.getWeight()).setSlant(fontFileResourceEntry.isItalic() ? 1 : 0).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings()).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(fontBuild);
                    } else {
                        builder.addFont(fontBuild);
                    }
                } catch (IOException unused) {
                }
            }
            if (builder == null) {
                return null;
            }
            return new Typeface.CustomFallbackBuilder(builder.build()).setStyle(new FontStyle((i & 1) != 0 ? 700 : 400, (i & 2) != 0 ? 1 : 0)).build();
        } catch (Exception unused2) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001b A[Catch: IOException -> 0x005b, Exception -> 0x0086, PHI: r3
      0x001b: PHI (r3v5 android.graphics.fonts.FontFamily$Builder) = (r3v3 android.graphics.fonts.FontFamily$Builder), (r3v1 android.graphics.fonts.FontFamily$Builder) binds: [B:15:0x004c, B:8:0x0019] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #1 {Exception -> 0x0086, blocks: (B:3:0x0005, B:5:0x000b, B:6:0x000d, B:9:0x001b, B:11:0x001f, B:13:0x0042, B:14:0x0049, B:19:0x0052, B:23:0x005a, B:22:0x0057, B:27:0x0061, B:31:0x006c, B:34:0x0071), top: B:40:0x0005 }] */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            FontFamily.Builder builder = null;
            for (FontsContractCompat.FontInfo fontInfo : fontInfoArr) {
                try {
                    ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(fontInfo.getUri(), "r", cancellationSignal);
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            Font fontBuild = new Font.Builder(parcelFileDescriptorOpenFileDescriptor).setWeight(fontInfo.getWeight()).setSlant(fontInfo.isItalic() ? 1 : 0).setTtcIndex(fontInfo.getTtcIndex()).build();
                            if (builder == null) {
                                builder = new FontFamily.Builder(fontBuild);
                            } else {
                                builder.addFont(fontBuild);
                            }
                            if (parcelFileDescriptorOpenFileDescriptor != null) {
                            }
                        } catch (Throwable th) {
                            if (parcelFileDescriptorOpenFileDescriptor != null) {
                                try {
                                    parcelFileDescriptorOpenFileDescriptor.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } else if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                } catch (IOException unused) {
                }
            }
            if (builder == null) {
                return null;
            }
            return new Typeface.CustomFallbackBuilder(builder.build()).setStyle(new FontStyle((i & 1) != 0 ? 700 : 400, (i & 2) != 0 ? 1 : 0)).build();
        } catch (Exception unused2) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        try {
            Font fontBuild = new Font.Builder(resources, i).build();
            return new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).build()).setStyle(fontBuild.getStyle()).build();
        } catch (Exception unused) {
            return null;
        }
    }
}
