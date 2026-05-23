package androidx.core.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ShortcutInfoCompat {
    private static final String EXTRA_LOCUS_ID = "extraLocusId";
    private static final String EXTRA_LONG_LIVED = "extraLongLived";
    private static final String EXTRA_PERSON_ = "extraPerson_";
    private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
    Context a;
    String b;
    String c;
    Intent[] d;
    ComponentName e;
    CharSequence f;
    CharSequence g;
    CharSequence h;
    IconCompat i;
    boolean j;
    Person[] k;
    Set<String> l;

    @Nullable
    LocusIdCompat m;
    boolean n;
    int o;
    PersistableBundle p;
    long q;
    UserHandle r;
    boolean s;
    boolean t;
    boolean u;
    boolean v;
    boolean w;
    boolean x = true;
    boolean y;
    int z;

    public static class Builder {
        private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat();
        private boolean mIsConversation;

        @RequiresApi(25)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull Context context, @NonNull ShortcutInfo shortcutInfo) {
            ShortcutInfoCompat shortcutInfoCompat;
            int disabledReason;
            this.mInfo.a = context;
            this.mInfo.b = shortcutInfo.getId();
            this.mInfo.c = shortcutInfo.getPackage();
            Intent[] intents = shortcutInfo.getIntents();
            this.mInfo.d = (Intent[]) Arrays.copyOf(intents, intents.length);
            this.mInfo.e = shortcutInfo.getActivity();
            this.mInfo.f = shortcutInfo.getShortLabel();
            this.mInfo.g = shortcutInfo.getLongLabel();
            this.mInfo.h = shortcutInfo.getDisabledMessage();
            if (Build.VERSION.SDK_INT >= 28) {
                shortcutInfoCompat = this.mInfo;
                disabledReason = shortcutInfo.getDisabledReason();
            } else {
                shortcutInfoCompat = this.mInfo;
                disabledReason = shortcutInfo.isEnabled() ? 0 : 3;
            }
            shortcutInfoCompat.z = disabledReason;
            this.mInfo.l = shortcutInfo.getCategories();
            this.mInfo.k = ShortcutInfoCompat.a(shortcutInfo.getExtras());
            this.mInfo.r = shortcutInfo.getUserHandle();
            this.mInfo.q = shortcutInfo.getLastChangedTimestamp();
            if (Build.VERSION.SDK_INT >= 30) {
                this.mInfo.s = shortcutInfo.isCached();
            }
            this.mInfo.t = shortcutInfo.isDynamic();
            this.mInfo.u = shortcutInfo.isPinned();
            this.mInfo.v = shortcutInfo.isDeclaredInManifest();
            this.mInfo.w = shortcutInfo.isImmutable();
            this.mInfo.x = shortcutInfo.isEnabled();
            this.mInfo.y = shortcutInfo.hasKeyFieldsOnly();
            this.mInfo.m = ShortcutInfoCompat.a(shortcutInfo);
            this.mInfo.o = shortcutInfo.getRank();
            this.mInfo.p = shortcutInfo.getExtras();
        }

        public Builder(@NonNull Context context, @NonNull String str) {
            this.mInfo.a = context;
            this.mInfo.b = str;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull ShortcutInfoCompat shortcutInfoCompat) {
            this.mInfo.a = shortcutInfoCompat.a;
            this.mInfo.b = shortcutInfoCompat.b;
            this.mInfo.c = shortcutInfoCompat.c;
            this.mInfo.d = (Intent[]) Arrays.copyOf(shortcutInfoCompat.d, shortcutInfoCompat.d.length);
            this.mInfo.e = shortcutInfoCompat.e;
            this.mInfo.f = shortcutInfoCompat.f;
            this.mInfo.g = shortcutInfoCompat.g;
            this.mInfo.h = shortcutInfoCompat.h;
            this.mInfo.z = shortcutInfoCompat.z;
            this.mInfo.i = shortcutInfoCompat.i;
            this.mInfo.j = shortcutInfoCompat.j;
            this.mInfo.r = shortcutInfoCompat.r;
            this.mInfo.q = shortcutInfoCompat.q;
            this.mInfo.s = shortcutInfoCompat.s;
            this.mInfo.t = shortcutInfoCompat.t;
            this.mInfo.u = shortcutInfoCompat.u;
            this.mInfo.v = shortcutInfoCompat.v;
            this.mInfo.w = shortcutInfoCompat.w;
            this.mInfo.x = shortcutInfoCompat.x;
            this.mInfo.m = shortcutInfoCompat.m;
            this.mInfo.n = shortcutInfoCompat.n;
            this.mInfo.y = shortcutInfoCompat.y;
            this.mInfo.o = shortcutInfoCompat.o;
            if (shortcutInfoCompat.k != null) {
                this.mInfo.k = (Person[]) Arrays.copyOf(shortcutInfoCompat.k, shortcutInfoCompat.k.length);
            }
            if (shortcutInfoCompat.l != null) {
                this.mInfo.l = new HashSet(shortcutInfoCompat.l);
            }
            if (shortcutInfoCompat.p != null) {
                this.mInfo.p = shortcutInfoCompat.p;
            }
        }

        @NonNull
        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty(this.mInfo.f)) {
                throw new IllegalArgumentException("Shortcut must have a non-empty label");
            }
            if (this.mInfo.d == null || this.mInfo.d.length == 0) {
                throw new IllegalArgumentException("Shortcut must have an intent");
            }
            if (this.mIsConversation) {
                if (this.mInfo.m == null) {
                    this.mInfo.m = new LocusIdCompat(this.mInfo.b);
                }
                this.mInfo.n = true;
            }
            return this.mInfo;
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName componentName) {
            this.mInfo.e = componentName;
            return this;
        }

        @NonNull
        public Builder setAlwaysBadged() {
            this.mInfo.j = true;
            return this;
        }

        @NonNull
        public Builder setCategories(@NonNull Set<String> set) {
            this.mInfo.l = set;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence charSequence) {
            this.mInfo.h = charSequence;
            return this;
        }

        @NonNull
        public Builder setExtras(@NonNull PersistableBundle persistableBundle) {
            this.mInfo.p = persistableBundle;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat iconCompat) {
            this.mInfo.i = iconCompat;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] intentArr) {
            this.mInfo.d = intentArr;
            return this;
        }

        @NonNull
        public Builder setIsConversation() {
            this.mIsConversation = true;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusIdCompat) {
            this.mInfo.m = locusIdCompat;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence charSequence) {
            this.mInfo.g = charSequence;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setLongLived() {
            this.mInfo.n = true;
            return this;
        }

        @NonNull
        public Builder setLongLived(boolean z) {
            this.mInfo.n = z;
            return this;
        }

        @NonNull
        public Builder setPerson(@NonNull Person person) {
            return setPersons(new Person[]{person});
        }

        @NonNull
        public Builder setPersons(@NonNull Person[] personArr) {
            this.mInfo.k = personArr;
            return this;
        }

        @NonNull
        public Builder setRank(int i) {
            this.mInfo.o = i;
            return this;
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence charSequence) {
            this.mInfo.f = charSequence;
            return this;
        }
    }

    ShortcutInfoCompat() {
    }

    @Nullable
    @RequiresApi(25)
    static LocusIdCompat a(@NonNull ShortcutInfo shortcutInfo) {
        if (Build.VERSION.SDK_INT < 29) {
            return getLocusIdFromExtra(shortcutInfo.getExtras());
        }
        if (shortcutInfo.getLocusId() == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(shortcutInfo.getLocusId());
    }

    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static List<ShortcutInfoCompat> a(@NonNull Context context, @NonNull List<ShortcutInfo> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<ShortcutInfo> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new Builder(context, it.next()).build());
        }
        return arrayList;
    }

    @VisibleForTesting
    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static Person[] a(@NonNull PersistableBundle persistableBundle) {
        if (persistableBundle == null || !persistableBundle.containsKey(EXTRA_PERSON_COUNT)) {
            return null;
        }
        int i = persistableBundle.getInt(EXTRA_PERSON_COUNT);
        Person[] personArr = new Person[i];
        int i2 = 0;
        while (i2 < i) {
            StringBuilder sb = new StringBuilder();
            sb.append(EXTRA_PERSON_);
            int i3 = i2 + 1;
            sb.append(i3);
            personArr[i2] = Person.fromPersistableBundle(persistableBundle.getPersistableBundle(sb.toString()));
            i2 = i3;
        }
        return personArr;
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private PersistableBundle buildLegacyExtrasBundle() {
        if (this.p == null) {
            this.p = new PersistableBundle();
        }
        if (this.k != null && this.k.length > 0) {
            this.p.putInt(EXTRA_PERSON_COUNT, this.k.length);
            int i = 0;
            while (i < this.k.length) {
                PersistableBundle persistableBundle = this.p;
                StringBuilder sb = new StringBuilder();
                sb.append(EXTRA_PERSON_);
                int i2 = i + 1;
                sb.append(i2);
                persistableBundle.putPersistableBundle(sb.toString(), this.k[i].toPersistableBundle());
                i = i2;
            }
        }
        if (this.m != null) {
            this.p.putString(EXTRA_LOCUS_ID, this.m.getId());
        }
        this.p.putBoolean(EXTRA_LONG_LIVED, this.n);
        return this.p;
    }

    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private static LocusIdCompat getLocusIdFromExtra(@Nullable PersistableBundle persistableBundle) {
        String string;
        if (persistableBundle == null || (string = persistableBundle.getString(EXTRA_LOCUS_ID)) == null) {
            return null;
        }
        return new LocusIdCompat(string);
    }

    Intent a(Intent intent) throws PackageManager.NameNotFoundException {
        intent.putExtra("android.intent.extra.shortcut.INTENT", this.d[this.d.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.f.toString());
        if (this.i != null) {
            Drawable activityIcon = null;
            if (this.j) {
                PackageManager packageManager = this.a.getPackageManager();
                if (this.e != null) {
                    try {
                        activityIcon = packageManager.getActivityIcon(this.e);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                if (activityIcon == null) {
                    activityIcon = this.a.getApplicationInfo().loadIcon(packageManager);
                }
            }
            this.i.addToShortcutIntent(intent, activityIcon, this.a);
        }
        return intent;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.e;
    }

    @Nullable
    public Set<String> getCategories() {
        return this.l;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.h;
    }

    public int getDisabledReason() {
        return this.z;
    }

    @Nullable
    public PersistableBundle getExtras() {
        return this.p;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public IconCompat getIcon() {
        return this.i;
    }

    @NonNull
    public String getId() {
        return this.b;
    }

    @NonNull
    public Intent getIntent() {
        return this.d[this.d.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        return (Intent[]) Arrays.copyOf(this.d, this.d.length);
    }

    public long getLastChangedTimestamp() {
        return this.q;
    }

    @Nullable
    public LocusIdCompat getLocusId() {
        return this.m;
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.g;
    }

    @NonNull
    public String getPackage() {
        return this.c;
    }

    public int getRank() {
        return this.o;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.f;
    }

    @Nullable
    public UserHandle getUserHandle() {
        return this.r;
    }

    public boolean hasKeyFieldsOnly() {
        return this.y;
    }

    public boolean isCached() {
        return this.s;
    }

    public boolean isDeclaredInManifest() {
        return this.v;
    }

    public boolean isDynamic() {
        return this.t;
    }

    public boolean isEnabled() {
        return this.x;
    }

    public boolean isImmutable() {
        return this.w;
    }

    public boolean isPinned() {
        return this.u;
    }

    @RequiresApi(25)
    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder intents = new ShortcutInfo.Builder(this.a, this.b).setShortLabel(this.f).setIntents(this.d);
        if (this.i != null) {
            intents.setIcon(this.i.toIcon(this.a));
        }
        if (!TextUtils.isEmpty(this.g)) {
            intents.setLongLabel(this.g);
        }
        if (!TextUtils.isEmpty(this.h)) {
            intents.setDisabledMessage(this.h);
        }
        if (this.e != null) {
            intents.setActivity(this.e);
        }
        if (this.l != null) {
            intents.setCategories(this.l);
        }
        intents.setRank(this.o);
        if (this.p != null) {
            intents.setExtras(this.p);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            if (this.k != null && this.k.length > 0) {
                android.app.Person[] personArr = new android.app.Person[this.k.length];
                for (int i = 0; i < personArr.length; i++) {
                    personArr[i] = this.k[i].toAndroidPerson();
                }
                intents.setPersons(personArr);
            }
            if (this.m != null) {
                intents.setLocusId(this.m.toLocusId());
            }
            intents.setLongLived(this.n);
        } else {
            intents.setExtras(buildLegacyExtrasBundle());
        }
        return intents.build();
    }
}
