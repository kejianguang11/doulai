package androidx.core.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class NotificationChannelCompat {
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;

    @NonNull
    final String a;
    CharSequence b;
    int c;
    String d;
    String e;
    boolean f;
    Uri g;
    AudioAttributes h;
    boolean i;
    int j;
    boolean k;
    long[] l;
    String m;
    private boolean mBypassDnd;
    private boolean mCanBubble;
    private boolean mImportantConversation;
    private int mLockscreenVisibility;
    String n;

    public static class Builder {
        private final NotificationChannelCompat mChannel;

        public Builder(@NonNull String str, int i) {
            this.mChannel = new NotificationChannelCompat(str, i);
        }

        @NonNull
        public NotificationChannelCompat build() {
            return this.mChannel;
        }

        @NonNull
        public Builder setConversationId(@NonNull String str, @NonNull String str2) {
            if (Build.VERSION.SDK_INT >= 30) {
                this.mChannel.m = str;
                this.mChannel.n = str2;
            }
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.mChannel.d = str;
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String str) {
            this.mChannel.e = str;
            return this;
        }

        @NonNull
        public Builder setImportance(int i) {
            this.mChannel.c = i;
            return this;
        }

        @NonNull
        public Builder setLightColor(int i) {
            this.mChannel.j = i;
            return this;
        }

        @NonNull
        public Builder setLightsEnabled(boolean z) {
            this.mChannel.i = z;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.mChannel.b = charSequence;
            return this;
        }

        @NonNull
        public Builder setShowBadge(boolean z) {
            this.mChannel.f = z;
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri, @Nullable AudioAttributes audioAttributes) {
            this.mChannel.g = uri;
            this.mChannel.h = audioAttributes;
            return this;
        }

        @NonNull
        public Builder setVibrationEnabled(boolean z) {
            this.mChannel.k = z;
            return this;
        }

        @NonNull
        public Builder setVibrationPattern(@Nullable long[] jArr) {
            this.mChannel.k = jArr != null && jArr.length > 0;
            this.mChannel.l = jArr;
            return this;
        }
    }

    @RequiresApi(26)
    NotificationChannelCompat(@NonNull NotificationChannel notificationChannel) {
        this(notificationChannel.getId(), notificationChannel.getImportance());
        this.b = notificationChannel.getName();
        this.d = notificationChannel.getDescription();
        this.e = notificationChannel.getGroup();
        this.f = notificationChannel.canShowBadge();
        this.g = notificationChannel.getSound();
        this.h = notificationChannel.getAudioAttributes();
        this.i = notificationChannel.shouldShowLights();
        this.j = notificationChannel.getLightColor();
        this.k = notificationChannel.shouldVibrate();
        this.l = notificationChannel.getVibrationPattern();
        if (Build.VERSION.SDK_INT >= 30) {
            this.m = notificationChannel.getParentChannelId();
            this.n = notificationChannel.getConversationId();
        }
        this.mBypassDnd = notificationChannel.canBypassDnd();
        this.mLockscreenVisibility = notificationChannel.getLockscreenVisibility();
        if (Build.VERSION.SDK_INT >= 29) {
            this.mCanBubble = notificationChannel.canBubble();
        }
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImportantConversation = notificationChannel.isImportantConversation();
        }
    }

    NotificationChannelCompat(@NonNull String str, int i) {
        this.f = true;
        this.g = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.j = 0;
        this.a = (String) Preconditions.checkNotNull(str);
        this.c = i;
        if (Build.VERSION.SDK_INT >= 21) {
            this.h = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        }
    }

    NotificationChannel a() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannel notificationChannel = new NotificationChannel(this.a, this.b, this.c);
        notificationChannel.setDescription(this.d);
        notificationChannel.setGroup(this.e);
        notificationChannel.setShowBadge(this.f);
        notificationChannel.setSound(this.g, this.h);
        notificationChannel.enableLights(this.i);
        notificationChannel.setLightColor(this.j);
        notificationChannel.setVibrationPattern(this.l);
        notificationChannel.enableVibration(this.k);
        if (Build.VERSION.SDK_INT >= 30 && this.m != null && this.n != null) {
            notificationChannel.setConversationId(this.m, this.n);
        }
        return notificationChannel;
    }

    public boolean canBubble() {
        return this.mCanBubble;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean canShowBadge() {
        return this.f;
    }

    @Nullable
    public AudioAttributes getAudioAttributes() {
        return this.h;
    }

    @Nullable
    public String getConversationId() {
        return this.n;
    }

    @Nullable
    public String getDescription() {
        return this.d;
    }

    @Nullable
    public String getGroup() {
        return this.e;
    }

    @NonNull
    public String getId() {
        return this.a;
    }

    public int getImportance() {
        return this.c;
    }

    public int getLightColor() {
        return this.j;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    @Nullable
    public CharSequence getName() {
        return this.b;
    }

    @Nullable
    public String getParentChannelId() {
        return this.m;
    }

    @Nullable
    public Uri getSound() {
        return this.g;
    }

    @Nullable
    public long[] getVibrationPattern() {
        return this.l;
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }

    public boolean shouldShowLights() {
        return this.i;
    }

    public boolean shouldVibrate() {
        return this.k;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.a, this.c).setName(this.b).setDescription(this.d).setGroup(this.e).setShowBadge(this.f).setSound(this.g, this.h).setLightsEnabled(this.i).setLightColor(this.j).setVibrationEnabled(this.k).setVibrationPattern(this.l).setConversationId(this.m, this.n);
    }
}
