package androidx.core.content;

import android.content.LocusId;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class LocusIdCompat {
    private final String mId;
    private final LocusId mWrapped;

    @RequiresApi(29)
    private static class Api29Impl {
        private Api29Impl() {
        }

        @NonNull
        static LocusId a(@NonNull String str) {
            return new LocusId(str);
        }

        @NonNull
        static String a(@NonNull LocusId locusId) {
            return locusId.getId();
        }
    }

    public LocusIdCompat(@NonNull String str) {
        this.mId = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
        this.mWrapped = Build.VERSION.SDK_INT >= 29 ? Api29Impl.a(str) : null;
    }

    @NonNull
    private String getSanitizedId() {
        return this.mId.length() + "_chars";
    }

    @NonNull
    @RequiresApi(29)
    public static LocusIdCompat toLocusIdCompat(@NonNull LocusId locusId) {
        Preconditions.checkNotNull(locusId, "locusId cannot be null");
        return new LocusIdCompat((String) Preconditions.checkStringNotEmpty(Api29Impl.a(locusId), "id cannot be empty"));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocusIdCompat locusIdCompat = (LocusIdCompat) obj;
        return this.mId == null ? locusIdCompat.mId == null : this.mId.equals(locusIdCompat.mId);
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        return 31 + (this.mId == null ? 0 : this.mId.hashCode());
    }

    @NonNull
    @RequiresApi(29)
    public LocusId toLocusId() {
        return this.mWrapped;
    }

    @NonNull
    public String toString() {
        return "LocusIdCompat[" + getSanitizedId() + "]";
    }
}
