package org.cocos2dx.okhttp3;

import androidx.appcompat.widget.ActivityChooserView;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.cocos2dx.okhttp3.internal.http.HttpHeaders;

/* JADX INFO: loaded from: classes.dex */
public final class CacheControl {

    @Nullable
    String headerValue;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, TimeUnit.SECONDS).build();

    public static final class Builder {
        boolean immutable;
        int maxAgeSeconds = -1;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean noCache;
        boolean noStore;
        boolean noTransform;
        boolean onlyIfCached;

        public CacheControl build() {
            return new CacheControl(this);
        }

        public Builder immutable() {
            this.immutable = true;
            return this;
        }

        public Builder maxAge(int i, TimeUnit timeUnit) {
            if (i >= 0) {
                long seconds = timeUnit.toSeconds(i);
                this.maxAgeSeconds = seconds > 2147483647L ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("maxAge < 0: " + i);
        }

        public Builder maxStale(int i, TimeUnit timeUnit) {
            if (i >= 0) {
                long seconds = timeUnit.toSeconds(i);
                this.maxStaleSeconds = seconds > 2147483647L ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("maxStale < 0: " + i);
        }

        public Builder minFresh(int i, TimeUnit timeUnit) {
            if (i >= 0) {
                long seconds = timeUnit.toSeconds(i);
                this.minFreshSeconds = seconds > 2147483647L ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("minFresh < 0: " + i);
        }

        public Builder noCache() {
            this.noCache = true;
            return this;
        }

        public Builder noStore() {
            this.noStore = true;
            return this;
        }

        public Builder noTransform() {
            this.noTransform = true;
            return this;
        }

        public Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }
    }

    CacheControl(Builder builder) {
        this.noCache = builder.noCache;
        this.noStore = builder.noStore;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.maxStaleSeconds;
        this.minFreshSeconds = builder.minFreshSeconds;
        this.onlyIfCached = builder.onlyIfCached;
        this.noTransform = builder.noTransform;
        this.immutable = builder.immutable;
    }

    private CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, boolean z8, @Nullable String str) {
        this.noCache = z;
        this.noStore = z2;
        this.maxAgeSeconds = i;
        this.sMaxAgeSeconds = i2;
        this.isPrivate = z3;
        this.isPublic = z4;
        this.mustRevalidate = z5;
        this.maxStaleSeconds = i3;
        this.minFreshSeconds = i4;
        this.onlyIfCached = z6;
        this.noTransform = z7;
        this.immutable = z8;
        this.headerValue = str;
    }

    private String headerValue() {
        StringBuilder sb = new StringBuilder();
        if (this.noCache) {
            sb.append("no-cache, ");
        }
        if (this.noStore) {
            sb.append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            sb.append("max-age=");
            sb.append(this.maxAgeSeconds);
            sb.append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            sb.append("s-maxage=");
            sb.append(this.sMaxAgeSeconds);
            sb.append(", ");
        }
        if (this.isPrivate) {
            sb.append("private, ");
        }
        if (this.isPublic) {
            sb.append("public, ");
        }
        if (this.mustRevalidate) {
            sb.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            sb.append("max-stale=");
            sb.append(this.maxStaleSeconds);
            sb.append(", ");
        }
        if (this.minFreshSeconds != -1) {
            sb.append("min-fresh=");
            sb.append(this.minFreshSeconds);
            sb.append(", ");
        }
        if (this.onlyIfCached) {
            sb.append("only-if-cached, ");
        }
        if (this.noTransform) {
            sb.append("no-transform, ");
        }
        if (this.immutable) {
            sb.append("immutable, ");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CacheControl parse(Headers headers) {
        int i;
        int i2;
        boolean z;
        int iSkipUntil;
        String strTrim;
        int size = headers.size();
        boolean z2 = true;
        String str = null;
        boolean z3 = false;
        boolean z4 = false;
        int seconds = -1;
        int seconds2 = -1;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        int seconds3 = -1;
        int seconds4 = -1;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        while (i < size) {
            String strName = headers.name(i);
            String strValue = headers.value(i);
            if (strName.equalsIgnoreCase("Cache-Control")) {
                if (str == null) {
                    str = strValue;
                }
                for (i2 = 0; i2 < strValue.length(); i2 = iSkipUntil) {
                    int iSkipUntil2 = HttpHeaders.skipUntil(strValue, i2, "=,;");
                    String strTrim2 = strValue.substring(i2, iSkipUntil2).trim();
                    if (iSkipUntil2 == strValue.length() || strValue.charAt(iSkipUntil2) == ',' || strValue.charAt(iSkipUntil2) == ';') {
                        z = true;
                        iSkipUntil = iSkipUntil2 + 1;
                        strTrim = null;
                    } else {
                        int iSkipWhitespace = HttpHeaders.skipWhitespace(strValue, iSkipUntil2 + 1);
                        if (iSkipWhitespace >= strValue.length() || strValue.charAt(iSkipWhitespace) != '\"') {
                            z = true;
                            iSkipUntil = HttpHeaders.skipUntil(strValue, iSkipWhitespace, ",;");
                            strTrim = strValue.substring(iSkipWhitespace, iSkipUntil).trim();
                        } else {
                            int i3 = iSkipWhitespace + 1;
                            int iSkipUntil3 = HttpHeaders.skipUntil(strValue, i3, "\"");
                            strTrim = strValue.substring(i3, iSkipUntil3);
                            z = true;
                            iSkipUntil = iSkipUntil3 + 1;
                        }
                    }
                    if ("no-cache".equalsIgnoreCase(strTrim2)) {
                        z3 = z;
                    } else if ("no-store".equalsIgnoreCase(strTrim2)) {
                        z4 = z;
                    } else {
                        if ("max-age".equalsIgnoreCase(strTrim2)) {
                            seconds = HttpHeaders.parseSeconds(strTrim, -1);
                        } else if ("s-maxage".equalsIgnoreCase(strTrim2)) {
                            seconds2 = HttpHeaders.parseSeconds(strTrim, -1);
                        } else if ("private".equalsIgnoreCase(strTrim2)) {
                            z5 = z;
                        } else if ("public".equalsIgnoreCase(strTrim2)) {
                            z6 = z;
                        } else if ("must-revalidate".equalsIgnoreCase(strTrim2)) {
                            z7 = z;
                        } else if ("max-stale".equalsIgnoreCase(strTrim2)) {
                            seconds3 = HttpHeaders.parseSeconds(strTrim, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
                        } else if ("min-fresh".equalsIgnoreCase(strTrim2)) {
                            seconds4 = HttpHeaders.parseSeconds(strTrim, -1);
                        } else if ("only-if-cached".equalsIgnoreCase(strTrim2)) {
                            z8 = z;
                        } else if ("no-transform".equalsIgnoreCase(strTrim2)) {
                            z9 = z;
                        } else if ("immutable".equalsIgnoreCase(strTrim2)) {
                            z10 = z;
                        }
                    }
                }
            } else {
                i = strName.equalsIgnoreCase("Pragma") ? 0 : i + 1;
            }
            z2 = false;
            while (i2 < strValue.length()) {
            }
        }
        return new CacheControl(z3, z4, seconds, seconds2, z5, z6, z7, seconds3, seconds4, z8, z9, z10, !z2 ? null : str);
    }

    public boolean immutable() {
        return this.immutable;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public boolean noTransform() {
        return this.noTransform;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public String toString() {
        String str = this.headerValue;
        if (str != null) {
            return str;
        }
        String strHeaderValue = headerValue();
        this.headerValue = strHeaderValue;
        return strHeaderValue;
    }
}
