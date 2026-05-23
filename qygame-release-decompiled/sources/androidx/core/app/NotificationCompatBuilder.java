package androidx.core.app;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private final Context mContext;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private final Bundle mExtras = new Bundle();

    NotificationCompatBuilder(NotificationCompat.Builder builder) {
        List<String> listCombineLists;
        Bundle bundle;
        String str;
        this.mBuilderCompat = builder;
        this.mContext = builder.mContext;
        this.mBuilder = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(builder.mContext, builder.I) : new Notification.Builder(builder.mContext);
        Notification notification = builder.Q;
        this.mBuilder.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.f).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.b).setContentText(builder.c).setContentInfo(builder.h).setContentIntent(builder.d).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.e, (notification.flags & 128) != 0).setLargeIcon(builder.g).setNumber(builder.i).setProgress(builder.r, builder.s, builder.t);
        if (Build.VERSION.SDK_INT < 21) {
            this.mBuilder.setSound(notification.sound, notification.audioStreamType);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.mBuilder.setSubText(builder.o).setUsesChronometer(builder.l).setPriority(builder.j);
            Iterator<NotificationCompat.Action> it = builder.mActions.iterator();
            while (it.hasNext()) {
                addAction(it.next());
            }
            if (builder.B != null) {
                this.mExtras.putAll(builder.B);
            }
            if (Build.VERSION.SDK_INT < 20) {
                if (builder.x) {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
                if (builder.u != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, builder.u);
                    if (builder.v) {
                        bundle = this.mExtras;
                        str = NotificationCompatExtras.EXTRA_GROUP_SUMMARY;
                    } else {
                        bundle = this.mExtras;
                        str = NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL;
                    }
                    bundle.putBoolean(str, true);
                }
                if (builder.w != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, builder.w);
                }
            }
            this.mContentView = builder.F;
            this.mBigContentView = builder.G;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.mBuilder.setShowWhen(builder.k);
        }
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21 && (listCombineLists = combineLists(getPeople(builder.mPersonList), builder.mPeople)) != null && !listCombineLists.isEmpty()) {
            this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) listCombineLists.toArray(new String[listCombineLists.size()]));
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setLocalOnly(builder.x).setGroup(builder.u).setGroupSummary(builder.v).setSortKey(builder.w);
            this.mGroupAlertBehavior = builder.N;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setCategory(builder.A).setColor(builder.C).setVisibility(builder.D).setPublicVersion(builder.E).setSound(notification.sound, notification.audioAttributes);
            List listCombineLists2 = Build.VERSION.SDK_INT < 28 ? combineLists(getPeople(builder.mPersonList), builder.mPeople) : builder.mPeople;
            if (listCombineLists2 != null && !listCombineLists2.isEmpty()) {
                Iterator it2 = listCombineLists2.iterator();
                while (it2.hasNext()) {
                    this.mBuilder.addPerson((String) it2.next());
                }
            }
            this.mHeadsUpContentView = builder.H;
            if (builder.a.size() > 0) {
                Bundle bundle2 = builder.getExtras().getBundle("android.car.EXTENSIONS");
                bundle2 = bundle2 == null ? new Bundle() : bundle2;
                Bundle bundle3 = new Bundle(bundle2);
                Bundle bundle4 = new Bundle();
                for (int i = 0; i < builder.a.size(); i++) {
                    bundle4.putBundle(Integer.toString(i), NotificationCompatJellybean.a(builder.a.get(i)));
                }
                bundle2.putBundle("invisible_actions", bundle4);
                bundle3.putBundle("invisible_actions", bundle4);
                builder.getExtras().putBundle("android.car.EXTENSIONS", bundle2);
                this.mExtras.putBundle("android.car.EXTENSIONS", bundle3);
            }
        }
        if (Build.VERSION.SDK_INT >= 23 && builder.S != null) {
            this.mBuilder.setSmallIcon(builder.S);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.mBuilder.setExtras(builder.B).setRemoteInputHistory(builder.q);
            if (builder.F != null) {
                this.mBuilder.setCustomContentView(builder.F);
            }
            if (builder.G != null) {
                this.mBuilder.setCustomBigContentView(builder.G);
            }
            if (builder.H != null) {
                this.mBuilder.setCustomHeadsUpContentView(builder.H);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder.setBadgeIconType(builder.J).setSettingsText(builder.p).setShortcutId(builder.K).setTimeoutAfter(builder.M).setGroupAlertBehavior(builder.N);
            if (builder.z) {
                this.mBuilder.setColorized(builder.y);
            }
            if (!TextUtils.isEmpty(builder.I)) {
                this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
        if (Build.VERSION.SDK_INT >= 28) {
            Iterator<Person> it3 = builder.mPersonList.iterator();
            while (it3.hasNext()) {
                this.mBuilder.addPerson(it3.next().toAndroidPerson());
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mBuilder.setAllowSystemGeneratedContextualActions(builder.O);
            this.mBuilder.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(builder.P));
            if (builder.L != null) {
                this.mBuilder.setLocusId(builder.L.toLocusId());
            }
        }
        if (builder.R) {
            if (this.mBuilderCompat.v) {
                this.mGroupAlertBehavior = 2;
            } else {
                this.mGroupAlertBehavior = 1;
            }
            this.mBuilder.setVibrate(null);
            this.mBuilder.setSound(null);
            notification.defaults &= -2;
            notification.defaults &= -3;
            this.mBuilder.setDefaults(notification.defaults);
            if (Build.VERSION.SDK_INT >= 26) {
                if (TextUtils.isEmpty(this.mBuilderCompat.u)) {
                    this.mBuilder.setGroup(NotificationCompat.GROUP_KEY_SILENT);
                }
                this.mBuilder.setGroupAlertBehavior(this.mGroupAlertBehavior);
            }
        }
    }

    private void addAction(NotificationCompat.Action action) {
        if (Build.VERSION.SDK_INT < 20) {
            if (Build.VERSION.SDK_INT >= 16) {
                this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, action));
                return;
            }
            return;
        }
        IconCompat iconCompat = action.getIconCompat();
        Notification.Action.Builder builder = Build.VERSION.SDK_INT >= 23 ? new Notification.Action.Builder(iconCompat != null ? iconCompat.toIcon() : null, action.getTitle(), action.getActionIntent()) : new Notification.Action.Builder(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.a(action.getRemoteInputs())) {
                builder.addRemoteInput(remoteInput);
            }
        }
        Bundle bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
        }
        bundle.putInt("android.support.action.semanticAction", action.getSemanticAction());
        if (Build.VERSION.SDK_INT >= 28) {
            builder.setSemanticAction(action.getSemanticAction());
        }
        if (Build.VERSION.SDK_INT >= 29) {
            builder.setContextual(action.isContextual());
        }
        bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
        builder.addExtras(bundle);
        this.mBuilder.addAction(builder.build());
    }

    @Nullable
    private static List<String> combineLists(@Nullable List<String> list, @Nullable List<String> list2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            return list;
        }
        ArraySet arraySet = new ArraySet(list.size() + list2.size());
        arraySet.addAll(list);
        arraySet.addAll(list2);
        return new ArrayList(arraySet);
    }

    @Nullable
    private static List<String> getPeople(@Nullable List<Person> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Person> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resolveToLegacyUri());
        }
        return arrayList;
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }

    Context a() {
        return this.mContext;
    }

    protected Notification b() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mBuilder.build();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Notification notificationBuild = this.mBuilder.build();
            if (this.mGroupAlertBehavior != 0) {
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notificationBuild);
                }
                if (notificationBuild.getGroup() != null && (notificationBuild.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notificationBuild);
                }
            }
            return notificationBuild;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notificationBuild2 = this.mBuilder.build();
            if (this.mContentView != null) {
                notificationBuild2.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notificationBuild2.bigContentView = this.mBigContentView;
            }
            if (this.mHeadsUpContentView != null) {
                notificationBuild2.headsUpContentView = this.mHeadsUpContentView;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (notificationBuild2.getGroup() != null && (notificationBuild2.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notificationBuild2);
                }
                if (notificationBuild2.getGroup() != null && (notificationBuild2.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notificationBuild2);
                }
            }
            return notificationBuild2;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notificationBuild3 = this.mBuilder.build();
            if (this.mContentView != null) {
                notificationBuild3.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notificationBuild3.bigContentView = this.mBigContentView;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (notificationBuild3.getGroup() != null && (notificationBuild3.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notificationBuild3);
                }
                if (notificationBuild3.getGroup() != null && (notificationBuild3.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notificationBuild3);
                }
            }
            return notificationBuild3;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            SparseArray<Bundle> sparseArrayBuildActionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (sparseArrayBuildActionExtrasMap != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, sparseArrayBuildActionExtrasMap);
            }
            this.mBuilder.setExtras(this.mExtras);
            Notification notificationBuild4 = this.mBuilder.build();
            if (this.mContentView != null) {
                notificationBuild4.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notificationBuild4.bigContentView = this.mBigContentView;
            }
            return notificationBuild4;
        }
        if (Build.VERSION.SDK_INT < 16) {
            return this.mBuilder.getNotification();
        }
        Notification notificationBuild5 = this.mBuilder.build();
        Bundle extras = NotificationCompat.getExtras(notificationBuild5);
        Bundle bundle = new Bundle(this.mExtras);
        for (String str : this.mExtras.keySet()) {
            if (extras.containsKey(str)) {
                bundle.remove(str);
            }
        }
        extras.putAll(bundle);
        SparseArray<Bundle> sparseArrayBuildActionExtrasMap2 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
        if (sparseArrayBuildActionExtrasMap2 != null) {
            NotificationCompat.getExtras(notificationBuild5).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, sparseArrayBuildActionExtrasMap2);
        }
        if (this.mContentView != null) {
            notificationBuild5.contentView = this.mContentView;
        }
        if (this.mBigContentView != null) {
            notificationBuild5.bigContentView = this.mBigContentView;
        }
        return notificationBuild5;
    }

    public Notification build() {
        Bundle extras;
        RemoteViews remoteViewsMakeHeadsUpContentView;
        RemoteViews remoteViewsMakeBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.n;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews remoteViewsMakeContentView = style != null ? style.makeContentView(this) : null;
        Notification notificationB = b();
        if (remoteViewsMakeContentView != null) {
            notificationB.contentView = remoteViewsMakeContentView;
        } else if (this.mBuilderCompat.F != null) {
            remoteViewsMakeContentView = this.mBuilderCompat.F;
            notificationB.contentView = remoteViewsMakeContentView;
        }
        if (Build.VERSION.SDK_INT >= 16 && style != null && (remoteViewsMakeBigContentView = style.makeBigContentView(this)) != null) {
            notificationB.bigContentView = remoteViewsMakeBigContentView;
        }
        if (Build.VERSION.SDK_INT >= 21 && style != null && (remoteViewsMakeHeadsUpContentView = this.mBuilderCompat.n.makeHeadsUpContentView(this)) != null) {
            notificationB.headsUpContentView = remoteViewsMakeHeadsUpContentView;
        }
        if (Build.VERSION.SDK_INT >= 16 && style != null && (extras = NotificationCompat.getExtras(notificationB)) != null) {
            style.addCompatExtras(extras);
        }
        return notificationB;
    }

    @Override // androidx.core.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }
}
