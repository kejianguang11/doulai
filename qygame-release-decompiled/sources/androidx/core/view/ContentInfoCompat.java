package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import com.alipay.sdk.util.h;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;

    @NonNull
    final ClipData a;
    final int b;
    final int c;

    @Nullable
    final Uri d;

    @Nullable
    final Bundle e;

    public static final class Builder {

        @NonNull
        ClipData a;
        int b;
        int c;

        @Nullable
        Uri d;

        @Nullable
        Bundle e;

        public Builder(@NonNull ClipData clipData, int i) {
            this.a = clipData;
            this.b = i;
        }

        public Builder(@NonNull ContentInfoCompat contentInfoCompat) {
            this.a = contentInfoCompat.a;
            this.b = contentInfoCompat.b;
            this.c = contentInfoCompat.c;
            this.d = contentInfoCompat.d;
            this.e = contentInfoCompat.e;
        }

        @NonNull
        public ContentInfoCompat build() {
            return new ContentInfoCompat(this);
        }

        @NonNull
        public Builder setClip(@NonNull ClipData clipData) {
            this.a = clipData;
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle bundle) {
            this.e = bundle;
            return this;
        }

        @NonNull
        public Builder setFlags(int i) {
            this.c = i;
            return this;
        }

        @NonNull
        public Builder setLinkUri(@Nullable Uri uri) {
            this.d = uri;
            return this;
        }

        @NonNull
        public Builder setSource(int i) {
            this.b = i;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Source {
    }

    ContentInfoCompat(Builder builder) {
        this.a = (ClipData) Preconditions.checkNotNull(builder.a);
        this.b = Preconditions.checkArgumentInRange(builder.b, 0, 3, "source");
        this.c = Preconditions.checkFlagsArgument(builder.c, 1);
        this.d = builder.d;
        this.e = builder.e;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String a(int i) {
        switch (i) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            default:
                return String.valueOf(i);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static String b(int i) {
        return (i & 1) != 0 ? "FLAG_CONVERT_TO_PLAIN_TEXT" : String.valueOf(i);
    }

    private static ClipData buildClipData(ClipDescription clipDescription, List<ClipData.Item> list) {
        ClipData clipData = new ClipData(new ClipDescription(clipDescription), list.get(0));
        for (int i = 1; i < list.size(); i++) {
            clipData.addItem(list.get(i));
        }
        return clipData;
    }

    @NonNull
    public ClipData getClip() {
        return this.a;
    }

    @Nullable
    public Bundle getExtras() {
        return this.e;
    }

    public int getFlags() {
        return this.c;
    }

    @Nullable
    public Uri getLinkUri() {
        return this.d;
    }

    public int getSource() {
        return this.b;
    }

    @NonNull
    public Pair<ContentInfoCompat, ContentInfoCompat> partition(@NonNull Predicate<ClipData.Item> predicate) {
        if (this.a.getItemCount() == 1) {
            boolean zTest = predicate.test(this.a.getItemAt(0));
            ContentInfoCompat contentInfoCompat = zTest ? this : null;
            if (zTest) {
                this = null;
            }
            return Pair.create(contentInfoCompat, this);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.a.getItemCount(); i++) {
            ClipData.Item itemAt = this.a.getItemAt(i);
            if (predicate.test(itemAt)) {
                arrayList.add(itemAt);
            } else {
                arrayList2.add(itemAt);
            }
        }
        return arrayList.isEmpty() ? Pair.create(null, this) : arrayList2.isEmpty() ? Pair.create(this, null) : Pair.create(new Builder(this).setClip(buildClipData(this.a.getDescription(), arrayList)).build(), new Builder(this).setClip(buildClipData(this.a.getDescription(), arrayList2)).build());
    }

    @NonNull
    public String toString() {
        return "ContentInfoCompat{clip=" + this.a + ", source=" + a(this.b) + ", flags=" + b(this.c) + ", linkUri=" + this.d + ", extras=" + this.e + h.d;
    }
}
