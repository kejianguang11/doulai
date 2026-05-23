package androidx.core.app;

import android.app.Person;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.IconCompat;

/* JADX INFO: loaded from: classes.dex */
public class Person {
    private static final String ICON_KEY = "icon";
    private static final String IS_BOT_KEY = "isBot";
    private static final String IS_IMPORTANT_KEY = "isImportant";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String URI_KEY = "uri";

    @Nullable
    CharSequence a;

    @Nullable
    IconCompat b;

    @Nullable
    String c;

    @Nullable
    String d;
    boolean e;
    boolean f;

    public static class Builder {

        @Nullable
        CharSequence a;

        @Nullable
        IconCompat b;

        @Nullable
        String c;

        @Nullable
        String d;
        boolean e;
        boolean f;

        public Builder() {
        }

        Builder(Person person) {
            this.a = person.a;
            this.b = person.b;
            this.c = person.c;
            this.d = person.d;
            this.e = person.e;
            this.f = person.f;
        }

        @NonNull
        public Person build() {
            return new Person(this);
        }

        @NonNull
        public Builder setBot(boolean z) {
            this.e = z;
            return this;
        }

        @NonNull
        public Builder setIcon(@Nullable IconCompat iconCompat) {
            this.b = iconCompat;
            return this;
        }

        @NonNull
        public Builder setImportant(boolean z) {
            this.f = z;
            return this;
        }

        @NonNull
        public Builder setKey(@Nullable String str) {
            this.d = str;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        @NonNull
        public Builder setUri(@Nullable String str) {
            this.c = str;
            return this;
        }
    }

    Person(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromAndroidPerson(@NonNull android.app.Person person) {
        return new Builder().setName(person.getName()).setIcon(person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build();
    }

    @NonNull
    public static Person fromBundle(@NonNull Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(ICON_KEY);
        return new Builder().setName(bundle.getCharSequence("name")).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString(URI_KEY)).setKey(bundle.getString(KEY_KEY)).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromPersistableBundle(@NonNull PersistableBundle persistableBundle) {
        return new Builder().setName(persistableBundle.getString("name")).setUri(persistableBundle.getString(URI_KEY)).setKey(persistableBundle.getString(KEY_KEY)).setBot(persistableBundle.getBoolean(IS_BOT_KEY)).setImportant(persistableBundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @Nullable
    public IconCompat getIcon() {
        return this.b;
    }

    @Nullable
    public String getKey() {
        return this.d;
    }

    @Nullable
    public CharSequence getName() {
        return this.a;
    }

    @Nullable
    public String getUri() {
        return this.c;
    }

    public boolean isBot() {
        return this.e;
    }

    public boolean isImportant() {
        return this.f;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String resolveToLegacyUri() {
        if (this.c != null) {
            return this.c;
        }
        if (this.a == null) {
            return "";
        }
        return "name:" + ((Object) this.a);
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public android.app.Person toAndroidPerson() {
        return new Person.Builder().setName(getName()).setIcon(getIcon() != null ? getIcon().toIcon() : null).setUri(getUri()).setKey(getKey()).setBot(isBot()).setImportant(isImportant()).build();
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("name", this.a);
        bundle.putBundle(ICON_KEY, this.b != null ? this.b.toBundle() : null);
        bundle.putString(URI_KEY, this.c);
        bundle.putString(KEY_KEY, this.d);
        bundle.putBoolean(IS_BOT_KEY, this.e);
        bundle.putBoolean(IS_IMPORTANT_KEY, this.f);
        return bundle;
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PersistableBundle toPersistableBundle() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("name", this.a != null ? this.a.toString() : null);
        persistableBundle.putString(URI_KEY, this.c);
        persistableBundle.putString(KEY_KEY, this.d);
        persistableBundle.putBoolean(IS_BOT_KEY, this.e);
        persistableBundle.putBoolean(IS_IMPORTANT_KEY, this.f);
        return persistableBundle;
    }
}
