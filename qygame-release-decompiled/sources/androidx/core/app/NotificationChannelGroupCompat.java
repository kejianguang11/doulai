package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationChannelGroupCompat {
    final String a;
    CharSequence b;
    String c;
    private boolean mBlocked;
    private List<NotificationChannelCompat> mChannels;

    public static class Builder {
        final NotificationChannelGroupCompat a;

        public Builder(@NonNull String str) {
            this.a = new NotificationChannelGroupCompat(str);
        }

        @NonNull
        public NotificationChannelGroupCompat build() {
            return this.a;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.a.c = str;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.a.b = charSequence;
            return this;
        }
    }

    @RequiresApi(28)
    NotificationChannelGroupCompat(@NonNull NotificationChannelGroup notificationChannelGroup) {
        this(notificationChannelGroup, Collections.emptyList());
    }

    @RequiresApi(26)
    NotificationChannelGroupCompat(@NonNull NotificationChannelGroup notificationChannelGroup, @NonNull List<NotificationChannel> list) {
        List<NotificationChannelCompat> channelsCompat;
        this(notificationChannelGroup.getId());
        this.b = notificationChannelGroup.getName();
        if (Build.VERSION.SDK_INT >= 28) {
            this.c = notificationChannelGroup.getDescription();
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mBlocked = notificationChannelGroup.isBlocked();
            channelsCompat = getChannelsCompat(notificationChannelGroup.getChannels());
        } else {
            channelsCompat = getChannelsCompat(list);
        }
        this.mChannels = channelsCompat;
    }

    NotificationChannelGroupCompat(@NonNull String str) {
        this.mChannels = Collections.emptyList();
        this.a = (String) Preconditions.checkNotNull(str);
    }

    @RequiresApi(26)
    private List<NotificationChannelCompat> getChannelsCompat(List<NotificationChannel> list) {
        ArrayList arrayList = new ArrayList();
        for (NotificationChannel notificationChannel : list) {
            if (this.a.equals(notificationChannel.getGroup())) {
                arrayList.add(new NotificationChannelCompat(notificationChannel));
            }
        }
        return arrayList;
    }

    NotificationChannelGroup a() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(this.a, this.b);
        if (Build.VERSION.SDK_INT >= 28) {
            notificationChannelGroup.setDescription(this.c);
        }
        return notificationChannelGroup;
    }

    @NonNull
    public List<NotificationChannelCompat> getChannels() {
        return this.mChannels;
    }

    @Nullable
    public String getDescription() {
        return this.c;
    }

    @NonNull
    public String getId() {
        return this.a;
    }

    @Nullable
    public CharSequence getName() {
        return this.b;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.a).setName(this.b).setDescription(this.c);
    }
}
