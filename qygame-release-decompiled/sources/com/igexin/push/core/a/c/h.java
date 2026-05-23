package com.igexin.push.core.a.c;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.b.r;
import com.igexin.push.core.b.s;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import com.igexin.push.g.o;
import com.igexin.sdk.GetuiActivity;
import com.igexin.sdk.main.FeedbackImpl;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class h implements PushMessageInterface {
    private static final String a = com.igexin.push.core.b.f + h.class.getName();
    private static final int b = 131;
    private static final String c = "push_small";

    /* JADX INFO: renamed from: com.igexin.push.core.a.c.h$2, reason: invalid class name */
    final class AnonymousClass2 implements Runnable {
        final /* synthetic */ NotificationManager a;
        final /* synthetic */ int b;
        final /* synthetic */ PushTaskBean c;

        AnonymousClass2(NotificationManager notificationManager, int i, PushTaskBean pushTaskBean) {
            this.a = notificationManager;
            this.b = i;
            this.c = pushTaskBean;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                StatusBarNotification[] activeNotifications = this.a.getActiveNotifications();
                boolean z = false;
                if (activeNotifications != null && activeNotifications.length > 0) {
                    int length = activeNotifications.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (activeNotifications[i].getId() == this.b) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                }
                if (z) {
                    return;
                }
                String unused = h.a;
                FeedbackImpl.getInstance().feedbackMessageAction(this.c, "10160", "show notification failed");
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    enum a {
        UNSET(0),
        BIG_IMAGE(1),
        LONG_TEXT(2),
        PURE_IMAGE(3);

        int e;

        a(int i) {
            this.e = i;
        }

        private int a() {
            return this.e;
        }
    }

    private static int a(com.igexin.push.core.b.l lVar, boolean z) {
        int identifier = 0;
        if (!z) {
            if (!TextUtils.isEmpty(com.igexin.push.core.e.aL) && (identifier = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.core.e.aL, "drawable", com.igexin.push.core.e.g)) == 0) {
                identifier = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.core.e.aL, "mipmap", com.igexin.push.core.e.g);
            }
            int identifier2 = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.config.c.w, "drawable", com.igexin.push.core.e.g);
            if (identifier2 == 0) {
                identifier2 = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.config.c.w, "mipmap", com.igexin.push.core.e.g);
            }
            if (TextUtils.isEmpty(lVar.f) || "null".equals(lVar.f)) {
                return identifier > 0 ? identifier : identifier2;
            }
            if (lVar.f.startsWith("@")) {
                String str = lVar.f;
                return str.substring(1, str.length()).endsWith(NotificationCompat.CATEGORY_EMAIL) ? R.drawable.sym_action_email : R.drawable.sym_def_app_icon;
            }
            int identifier3 = com.igexin.push.core.e.l.getResources().getIdentifier(lVar.f, "drawable", com.igexin.push.core.e.g);
            if (identifier3 == 0) {
                identifier3 = com.igexin.push.core.e.l.getResources().getIdentifier(lVar.f, "mipmap", com.igexin.push.core.e.g);
            }
            return identifier3 > 0 ? identifier3 : identifier > 0 ? identifier : identifier2;
        }
        if (!TextUtils.isEmpty(com.igexin.push.core.e.aK)) {
            int identifier4 = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.core.e.aK, "drawable", com.igexin.push.core.e.g);
            if (identifier4 == 0) {
                identifier4 = com.igexin.push.core.e.l.getResources().getIdentifier(com.igexin.push.core.e.aK, "mipmap", com.igexin.push.core.e.g);
            }
            if (identifier4 > 0) {
                return identifier4;
            }
        }
        int identifier5 = com.igexin.push.core.e.l.getResources().getIdentifier(c, "drawable", com.igexin.push.core.e.g);
        if (identifier5 == 0) {
            identifier5 = com.igexin.push.core.e.l.getResources().getIdentifier(c, "mipmap", com.igexin.push.core.e.g);
        }
        if (identifier5 != 0) {
            com.igexin.c.a.c.a.a(a + "|push_small.png is set, use default push_small", new Object[0]);
            return identifier5;
        }
        com.igexin.c.a.c.a.a(a, "|push_small.png is missing");
        com.igexin.c.a.c.a.a(a + "|push_small.png is missing", new Object[0]);
        return com.igexin.push.core.e.l.getApplicationInfo().icon;
    }

    private static int a(String str) {
        int iCharAt = 0;
        for (int i = 0; i != str.length(); i++) {
            iCharAt = (iCharAt * b) + str.charAt(i);
        }
        if (iCharAt == Integer.MIN_VALUE) {
            iCharAt = 1;
        }
        return Math.abs(iCharAt);
    }

    private static Notification a(String str, int i, com.igexin.push.core.b.l lVar) {
        Notification.Builder builder;
        if (TextUtils.isEmpty(str) || com.igexin.push.core.e.aj.containsKey(str) || Build.VERSION.SDK_INT < 24) {
            return null;
        }
        com.igexin.push.core.e.aj.put(str, new HashSet<>());
        PendingIntent pendingIntentB = b(str);
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(com.igexin.push.core.e.l);
            NotificationManager notificationManager = (NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n);
            try {
                Constructor<?> constructor = Class.forName("android.app.NotificationChannel").getConstructor(String.class, CharSequence.class, Integer.TYPE);
                Class<?> cls = notificationManager.getClass();
                if (((Parcelable) cls.getMethod("getNotificationChannel", String.class).invoke(notificationManager, lVar.j)) == null) {
                    cls.getMethod("createNotificationChannel", Class.forName("android.app.NotificationChannel")).invoke(notificationManager, (Parcelable) constructor.newInstance(lVar.j, lVar.k, Integer.valueOf(lVar.l)));
                }
                builder.getClass().getMethod("setChannelId", String.class).invoke(builder, lVar.j);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        } else {
            builder = new Notification.Builder(com.igexin.push.core.e.l);
        }
        Notification notificationBuild = builder.setContentTitle("summary").setContentText("summary").setDeleteIntent(pendingIntentB).setAutoCancel(false).setGroup(str).setSmallIcon(i).setGroupSummary(true).build();
        if (Build.VERSION.SDK_INT < 21 || TextUtils.isEmpty(lVar.w)) {
            return notificationBuild;
        }
        builder.setCategory(lVar.w);
        return notificationBuild;
    }

    private static PendingIntent a(String str, int i, String str2, String str3, int i2, com.igexin.push.core.b.l lVar) {
        Intent intent = new Intent();
        intent.putExtra("taskid", str2);
        intent.putExtra("messageid", str3);
        intent.putExtra("appid", com.igexin.push.core.e.a);
        intent.putExtra("actionid", lVar.getDoActionId());
        intent.putExtra("accesstoken", com.igexin.push.core.e.aC);
        intent.putExtra("notifID", i2);
        StringBuilder sb = new StringBuilder();
        sb.append(lVar.h);
        intent.putExtra("notifyStyle", sb.toString());
        intent.putExtra(com.igexin.push.core.b.C, lVar.y);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(lVar.C);
        intent.putExtra("bigStyle", sb2.toString());
        intent.putExtra("isFloat", false);
        intent.putExtra("checkpackage", com.igexin.push.core.e.l.getPackageName());
        intent.putExtra("feedbackid", lVar.getActionId().substring(lVar.getActionId().length() - 1));
        String str4 = lVar.a;
        if (str4 == null) {
            str4 = "";
        }
        intent.putExtra("title", str4);
        String str5 = lVar.b;
        if (str5 == null) {
            str5 = "";
        }
        intent.putExtra(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT, str5);
        intent.putExtra("redisplayFreq", i);
        intent.putExtra("groupId", str);
        String str6 = lVar.t;
        if (str6 == null) {
            str6 = "";
        }
        intent.putExtra(Constant.PROTOCOL_WEB_VIEW_URL, str6);
        String str7 = lVar.u;
        if (str7 == null) {
            str7 = "";
        }
        intent.putExtra("intentUri", str7);
        String str8 = lVar.v;
        if (str8 == null) {
            str8 = "";
        }
        intent.putExtra(AssistPushConsts.MSG_TYPE_PAYLOAD, str8);
        int i3 = Build.VERSION.SDK_INT >= 23 ? 201326592 : 134217728;
        try {
            Intent intent2 = new Intent(com.igexin.push.core.e.l, (Class<?>) GetuiActivity.class);
            intent2.setFlags(268435456);
            intent2.putExtra("action", "com.igexin.action.notification.click");
            intent2.putExtra("broadcast_intent", intent);
            return PendingIntent.getActivity(com.igexin.push.core.e.l, new Random().nextInt(1000), intent2, i3);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            Intent intent3 = new Intent("com.igexin.action.notification.click");
            intent3.setAction("com.igexin.action.notification.click");
            intent3.putExtra("action", "com.igexin.action.notification.click");
            intent3.putExtra("broadcast_intent", intent);
            return PendingIntent.getBroadcast(com.igexin.push.core.e.l, new Random().nextInt(1000), intent3, i3);
        }
    }

    private static PendingIntent a(String str, int i, String str2, String str3, String str4, com.igexin.push.core.b.l lVar) {
        try {
            Context context = com.igexin.push.core.e.l;
            com.igexin.push.core.a.b.d();
            Intent intent = new Intent(context, (Class<?>) com.igexin.push.core.a.b.a(com.igexin.push.core.e.l));
            intent.putExtra("taskid", str3);
            intent.putExtra("messageid", str4);
            intent.putExtra("appid", com.igexin.push.core.e.a);
            intent.putExtra(com.alipay.sdk.sys.a.f, str2);
            intent.putExtra("actionid", lVar.getDoActionId());
            StringBuilder sb = new StringBuilder();
            sb.append(lVar.h);
            intent.putExtra("notifyStyle", sb.toString());
            intent.putExtra(com.igexin.push.core.b.C, lVar.y);
            intent.putExtra("feedbackid", lVar.getActionId().substring(lVar.getActionId().length() + (-1)));
            intent.putExtra("action", "com.igexin.action.notification.delete");
            intent.putExtra("redisplayFreq", i);
            intent.putExtra("groupId", str);
            int i2 = 134217728;
            if (o.a(com.igexin.push.core.e.l) >= 31 && Build.VERSION.SDK_INT >= 30) {
                i2 = 201326592;
            }
            return PendingIntent.getService(com.igexin.push.core.e.l, new Random().nextInt(1000), intent, i2);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a(a + "|getDelPendingIntent err：" + e.toString(), new Object[0]);
            return null;
        }
    }

    private static Bitmap a(com.igexin.push.core.b.l lVar) {
        Bitmap bitmapA;
        String str = lVar.D;
        if (TextUtils.isEmpty(str)) {
            bitmapA = null;
        } else {
            bitmapA = com.igexin.push.g.m.a(str);
            String str2 = a;
            StringBuilder sb = new StringBuilder("|use net logo bitmap is null = ");
            sb.append(bitmapA == null);
            com.igexin.c.a.c.a.a(str2, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("|use net logo bitmap is null = ");
            sb2.append(bitmapA == null);
            com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
        }
        if (bitmapA == null) {
            return BitmapFactory.decodeResource(com.igexin.push.core.e.l.getResources(), a(lVar, false));
        }
        return bitmapA;
    }

    private static void a(Notification notification) {
        if (com.igexin.push.g.a.b() || Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT >= 24) {
            return;
        }
        try {
            Field field = Class.forName("com.android.internal.R$id").getField("right_icon");
            field.setAccessible(true);
            int i = field.getInt(null);
            if (notification.contentView != null) {
                notification.contentView.setViewVisibility(i, 8);
                notification.bigContentView.setViewVisibility(i, 8);
            }
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
    }

    private static void a(Notification notification, com.igexin.push.core.b.l lVar) {
        notification.defaults = 4;
        notification.ledARGB = -16711936;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 3000;
        notification.flags = 1;
        notification.flags = lVar.e ? notification.flags | 16 : notification.flags | 32;
        if (lVar.c) {
            notification.defaults |= 2;
        }
        if (lVar.d) {
            if (TextUtils.isEmpty(lVar.p)) {
                notification.defaults |= 1;
            } else {
                notification.sound = c(lVar.p);
            }
        }
        if (lVar.o > 0) {
            com.igexin.push.g.d.a(lVar.o, false);
            com.igexin.push.g.d.c(lVar.o, false);
            com.igexin.push.g.d.b(lVar.o, false);
        }
        notification.icon = a(lVar, true);
    }

    private static void a(com.igexin.push.core.b.l lVar, String str, String str2, String str3, String str4) {
        com.igexin.push.core.l.a().a(str, str2, str3, str4, lVar.t, lVar.u, lVar.v);
    }

    @SuppressLint({"WrongConstant"})
    private void a(PushTaskBean pushTaskBean, com.igexin.push.core.b.l lVar, int i) {
        String str;
        Bitmap bitmapDecodeResource;
        Notification.Style styleBigText;
        Bitmap bitmapA;
        int i2 = lVar.r;
        String appKey = pushTaskBean.getAppKey();
        String taskId = pushTaskBean.getTaskId();
        String messageId = pushTaskBean.getMessageId();
        String str2 = lVar.q;
        com.igexin.push.core.e.ai.put(taskId, Integer.valueOf(i));
        com.igexin.push.core.a.b.d();
        PushTaskBean pushTaskBean2 = com.igexin.push.core.e.ah.get(com.igexin.push.core.a.b.a(taskId, messageId));
        if (pushTaskBean2 != null) {
            byte[] msgExtra = pushTaskBean2.getMsgExtra();
            if (msgExtra != null) {
                lVar.v = new String(msgExtra);
            }
            for (BaseActionBean baseActionBean : pushTaskBean2.getActionChains()) {
                if (baseActionBean instanceof s) {
                    String str3 = ((s) baseActionBean).a;
                    if (str3 == null) {
                        str3 = "";
                    }
                    lVar.t = str3;
                }
                if (baseActionBean instanceof r) {
                    String str4 = ((r) baseActionBean).b;
                    if (str4 == null) {
                        str4 = "";
                    }
                    lVar.u = str4;
                }
            }
        }
        int iA = a(lVar, true);
        boolean z = false;
        if (iA != 0 && com.igexin.push.core.e.l.getResources().getDrawable(iA) == null) {
            com.igexin.c.a.c.a.a(a + "|showNotification smallIconId: " + iA + " couldn't find resource", new Object[0]);
            return;
        }
        Notification notificationA = a(str2, iA, lVar);
        PendingIntent pendingIntentA = a(str2, i2, taskId, messageId, i, lVar);
        PendingIntent pendingIntentA2 = a(str2, i2, appKey, taskId, messageId, lVar);
        NotificationManager notificationManager = (NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n);
        Notification.Builder builderB = Build.VERSION.SDK_INT >= 26 ? b(lVar) : new Notification.Builder(com.igexin.push.core.e.l);
        String str5 = lVar.a;
        String str6 = lVar.b;
        String str7 = lVar.D;
        if (TextUtils.isEmpty(str7)) {
            str = messageId;
            bitmapDecodeResource = null;
        } else {
            Bitmap bitmapA2 = com.igexin.push.g.m.a(str7);
            String str8 = a;
            str = messageId;
            StringBuilder sb = new StringBuilder("|use net logo bitmap is null = ");
            sb.append(bitmapA2 == null);
            com.igexin.c.a.c.a.a(str8, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("|use net logo bitmap is null = ");
            sb2.append(bitmapA2 == null);
            z = false;
            com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
            bitmapDecodeResource = bitmapA2;
        }
        if (bitmapDecodeResource == null) {
            bitmapDecodeResource = BitmapFactory.decodeResource(com.igexin.push.core.e.l.getResources(), a(lVar, z));
        }
        Bitmap bitmap = bitmapDecodeResource;
        builderB.setSmallIcon(iA).setTicker(lVar.b).setWhen(System.currentTimeMillis()).setContentTitle(str5).setContentIntent(pendingIntentA).setContentText(str6).setDeleteIntent(pendingIntentA2);
        if (Build.VERSION.SDK_INT >= 21 && !TextUtils.isEmpty(lVar.w)) {
            builderB.setCategory(lVar.w);
        }
        if (bitmap != null) {
            builderB.setLargeIcon(bitmap);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            builderB.setShowWhen(true);
        }
        if (Build.VERSION.SDK_INT >= 24 && !TextUtils.isEmpty(lVar.i)) {
            try {
                builderB.setColor(Color.parseColor(lVar.i));
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (lVar.C == a.BIG_IMAGE.e) {
                String str9 = lVar.E;
                if (!TextUtils.isEmpty(str9) && (bitmapA = com.igexin.push.g.m.a(str9)) != null) {
                    builderB.setPriority(lVar.x);
                    styleBigText = new Notification.BigPictureStyle().bigPicture(bitmapA);
                    builderB.setStyle(styleBigText);
                }
            } else if (lVar.C == a.LONG_TEXT.e) {
                String str10 = lVar.B;
                if (!TextUtils.isEmpty(str10)) {
                    builderB.setPriority(lVar.x);
                    styleBigText = new Notification.BigTextStyle().bigText(str10);
                    builderB.setStyle(styleBigText);
                }
            }
        }
        if (lVar.z && Build.VERSION.SDK_INT >= 21 && (lVar.c || lVar.d)) {
            builderB.setPriority(2);
        }
        if (!TextUtils.isEmpty(str2) && Build.VERSION.SDK_INT >= 24 && com.igexin.push.core.e.aj.containsKey(str2)) {
            builderB.setGroup(str2);
            builderB.setGroupSummary(false);
            HashSet<String> hashSet = com.igexin.push.core.e.aj.get(str2) == null ? new HashSet<>() : com.igexin.push.core.e.aj.get(str2);
            hashSet.add(taskId);
            com.igexin.push.core.e.aj.put(str2, hashSet);
        }
        builderB.setWhen(System.currentTimeMillis());
        Notification notification = builderB.getNotification();
        notification.defaults = 4;
        notification.ledARGB = -16711936;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 3000;
        notification.flags = 1;
        notification.flags = lVar.e ? notification.flags | 16 : notification.flags | 32;
        if (lVar.c) {
            notification.defaults |= 2;
        }
        if (lVar.d) {
            if (TextUtils.isEmpty(lVar.p)) {
                notification.defaults |= 1;
            } else {
                notification.sound = c(lVar.p);
            }
        }
        if (lVar.o > 0) {
            com.igexin.push.g.d.a(lVar.o, false);
            com.igexin.push.g.d.c(lVar.o, false);
            com.igexin.push.g.d.b(lVar.o, false);
        }
        boolean z2 = true;
        notification.icon = a(lVar, true);
        a(notification);
        if (!TextUtils.isEmpty(str2) && notificationA != null) {
            int iA2 = a(str2);
            com.igexin.push.core.e.ak.put(str2, Integer.valueOf(iA2));
            notificationManager.notify(iA2, notificationA);
        }
        com.igexin.c.a.c.a.a(a + "|showNotification notification:" + i, new Object[0]);
        if (i2 > 0) {
            notificationManager.cancel(i);
        }
        notificationManager.notify(i, notification);
        com.igexin.push.core.l.a().a(taskId, str, str5, str6, lVar.t, lVar.u, lVar.v);
        try {
            if (TextUtils.isEmpty(com.igexin.push.config.d.ad) || com.igexin.push.config.d.ad.equals("null") || Build.VERSION.SDK_INT < 23 || com.igexin.push.core.e.J != 1) {
                return;
            }
            String[] strArrSplit = com.igexin.push.config.d.ad.split(com.igexin.push.core.b.an);
            if (!com.igexin.push.config.d.ad.equals("*")) {
                if (strArrSplit == null || strArrSplit.length <= 0) {
                    z2 = false;
                } else {
                    for (String str11 : strArrSplit) {
                        if (str11.equalsIgnoreCase(com.igexin.push.core.e.G)) {
                            break;
                        }
                    }
                    z2 = false;
                }
            }
            if (z2) {
                com.igexin.b.a.a().b().schedule(new AnonymousClass2(notificationManager, i, pushTaskBean), 300L, TimeUnit.MILLISECONDS);
            }
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final String str2, final String str3, final BaseActionBean baseActionBean, final int i) {
        String string;
        StringBuilder sb;
        String str4;
        String str5 = "width=" + com.igexin.push.core.e.k + "&height=" + com.igexin.push.core.e.j;
        if (str.contains(str5)) {
            string = str;
        } else {
            if (str.indexOf("?") > 0) {
                sb = new StringBuilder();
                sb.append(str);
                str4 = com.alipay.sdk.sys.a.b;
            } else {
                sb = new StringBuilder();
                sb.append(str);
                str4 = "?";
            }
            sb.append(str4);
            sb.append(str5);
            string = sb.toString();
        }
        com.igexin.push.core.h.b bVar = new com.igexin.push.core.h.b(string, str, str2, baseActionBean, i, new com.igexin.push.core.h.d() { // from class: com.igexin.push.core.a.c.h.1
            @Override // com.igexin.push.core.h.d
            public final void a() {
                if (((com.igexin.push.core.b.l) baseActionBean).H >= 3) {
                    ((com.igexin.push.core.b.l) baseActionBean).F = true;
                }
                if (((com.igexin.push.core.b.l) baseActionBean).I >= 3) {
                    ((com.igexin.push.core.b.l) baseActionBean).G = true;
                }
                if (!((com.igexin.push.core.b.l) baseActionBean).F || !((com.igexin.push.core.b.l) baseActionBean).G) {
                    h.this.a(str, str2, str3, baseActionBean, i);
                    return;
                }
                if (com.igexin.push.core.e.a(str2) == 0) {
                    com.igexin.push.core.e.c.a();
                    com.igexin.push.core.e.c.a(com.igexin.push.core.b.ag, str2);
                    Map<String, PushTaskBean> map = com.igexin.push.core.e.ah;
                    com.igexin.push.core.a.b.d();
                    PushTaskBean pushTaskBean = map.get(com.igexin.push.core.a.b.a(str2, str3));
                    if (pushTaskBean != null) {
                        pushTaskBean.setStatus(com.igexin.push.core.b.ag);
                    }
                    com.igexin.push.core.a.b.d();
                    com.igexin.push.core.a.b.a(str2, str3, "1");
                }
            }

            @Override // com.igexin.push.core.h.d
            public final void a(BaseActionBean baseActionBean2) {
                if (i == 2) {
                    ((com.igexin.push.core.b.l) baseActionBean).F = true;
                } else if (i == 8) {
                    ((com.igexin.push.core.b.l) baseActionBean).G = true;
                }
                com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) baseActionBean2;
                if (lVar.F && lVar.G && com.igexin.push.core.e.a(str2) == 0) {
                    com.igexin.push.core.e.c.a();
                    com.igexin.push.core.e.c.a(com.igexin.push.core.b.ag, str2);
                    Map<String, PushTaskBean> map = com.igexin.push.core.e.ah;
                    com.igexin.push.core.a.b.d();
                    PushTaskBean pushTaskBean = map.get(com.igexin.push.core.a.b.a(str2, str3));
                    if (pushTaskBean != null) {
                        pushTaskBean.setStatus(com.igexin.push.core.b.ag);
                    }
                    com.igexin.push.core.a.b.d();
                    com.igexin.push.core.a.b.a(str2, str3, "1");
                }
            }
        });
        if (i == 2) {
            ((com.igexin.push.core.b.l) baseActionBean).H++;
        } else if (i == 8) {
            ((com.igexin.push.core.b.l) baseActionBean).I++;
        }
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.a.e(bVar), false, true);
    }

    @TargetApi(26)
    private static Notification.Builder b(com.igexin.push.core.b.l lVar) {
        Notification.Builder builder = new Notification.Builder(com.igexin.push.core.e.l);
        NotificationManager notificationManager = (NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n);
        try {
            Class<?> cls = Class.forName("android.app.NotificationChannel");
            Constructor<?> constructor = cls.getConstructor(String.class, CharSequence.class, Integer.TYPE);
            Class<?> cls2 = notificationManager.getClass();
            if (((Parcelable) cls2.getMethod("getNotificationChannel", String.class).invoke(notificationManager, lVar.j)) == null) {
                Parcelable parcelable = (Parcelable) constructor.newInstance(lVar.j, lVar.k, Integer.valueOf(lVar.l));
                Method method = cls2.getMethod("createNotificationChannel", Class.forName("android.app.NotificationChannel"));
                Method method2 = cls.getMethod("enableVibration", Boolean.TYPE);
                Method method3 = cls.getMethod("setSound", Uri.class, AudioAttributes.class);
                method2.invoke(parcelable, Boolean.valueOf(lVar.c));
                if (!lVar.d) {
                    method3.invoke(parcelable, null, null);
                } else if (!TextUtils.isEmpty(lVar.p)) {
                    method3.invoke(parcelable, c(lVar.p), null);
                }
                method.invoke(notificationManager, parcelable);
            }
            builder.getClass().getMethod("setChannelId", String.class).invoke(builder, lVar.j);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        return builder;
    }

    private static PendingIntent b(String str) {
        try {
            Context context = com.igexin.push.core.e.l;
            com.igexin.push.core.a.b.d();
            Intent intent = new Intent(context, (Class<?>) com.igexin.push.core.a.b.a(com.igexin.push.core.e.l));
            intent.putExtra("isSummary", true);
            intent.putExtra("action", "com.igexin.action.notification.delete");
            intent.putExtra("groupId", str);
            int i = 134217728;
            if (o.a(com.igexin.push.core.e.l) >= 31 && Build.VERSION.SDK_INT >= 30) {
                i = 201326592;
            }
            return PendingIntent.getService(com.igexin.push.core.e.l, new Random().nextInt(1000), intent, i);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return null;
        }
    }

    private static Uri c(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return Uri.parse("android.resource://" + com.igexin.push.core.e.l.getPackageName() + "/raw/" + str.toLowerCase());
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:160:0x038e A[Catch: Throwable -> 0x03a5, TRY_LEAVE, TryCatch #2 {Throwable -> 0x03a5, blocks: (B:139:0x0344, B:141:0x034c, B:143:0x0356, B:145:0x035c, B:147:0x0361, B:160:0x038e, B:151:0x0377, B:153:0x037a, B:155:0x037e, B:158:0x0389), top: B:173:0x0344 }] */
    @Override // com.igexin.push.extension.mod.PushMessageInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean executeAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) throws Throwable {
        int i;
        int i2;
        String str;
        boolean z;
        Bitmap bitmapDecodeResource;
        String str2;
        Notification.Style styleBigText;
        Bitmap bitmapA;
        if (pushTaskBean == null || !(baseActionBean instanceof com.igexin.push.core.b.l)) {
            return true;
        }
        com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) baseActionBean;
        int iA = !lVar.n ? a(pushTaskBean.getTaskId()) : lVar.m;
        try {
            i = Integer.parseInt(lVar.getActionId().substring(lVar.getActionId().length() - 1)) + 30000;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            i = 0;
        }
        int i3 = lVar.r;
        String appKey = pushTaskBean.getAppKey();
        String taskId = pushTaskBean.getTaskId();
        String messageId = pushTaskBean.getMessageId();
        String str3 = lVar.q;
        com.igexin.push.core.e.ai.put(taskId, Integer.valueOf(iA));
        com.igexin.push.core.a.b.d();
        PushTaskBean pushTaskBean2 = com.igexin.push.core.e.ah.get(com.igexin.push.core.a.b.a(taskId, messageId));
        if (pushTaskBean2 != null) {
            byte[] msgExtra = pushTaskBean2.getMsgExtra();
            if (msgExtra != null) {
                lVar.v = new String(msgExtra);
            }
            for (BaseActionBean baseActionBean2 : pushTaskBean2.getActionChains()) {
                if (baseActionBean2 instanceof s) {
                    String str4 = ((s) baseActionBean2).a;
                    if (str4 == null) {
                        str4 = "";
                    }
                    lVar.t = str4;
                }
                if (baseActionBean2 instanceof r) {
                    String str5 = ((r) baseActionBean2).b;
                    if (str5 == null) {
                        str5 = "";
                    }
                    lVar.u = str5;
                }
            }
        }
        int iA2 = a(lVar, true);
        if (iA2 == 0 || com.igexin.push.core.e.l.getResources().getDrawable(iA2) != null) {
            Notification notificationA = a(str3, iA2, lVar);
            PendingIntent pendingIntentA = a(str3, i3, taskId, messageId, iA, lVar);
            PendingIntent pendingIntentA2 = a(str3, i3, appKey, taskId, messageId, lVar);
            NotificationManager notificationManager = (NotificationManager) com.igexin.push.core.e.l.getSystemService(com.igexin.push.core.b.n);
            Notification.Builder builderB = Build.VERSION.SDK_INT >= 26 ? b(lVar) : new Notification.Builder(com.igexin.push.core.e.l);
            String str6 = lVar.a;
            String str7 = lVar.b;
            String str8 = lVar.D;
            if (TextUtils.isEmpty(str8)) {
                i2 = i;
                str = messageId;
                z = false;
                bitmapDecodeResource = null;
            } else {
                Bitmap bitmapA2 = com.igexin.push.g.m.a(str8);
                String str9 = a;
                str = messageId;
                i2 = i;
                StringBuilder sb = new StringBuilder("|use net logo bitmap is null = ");
                sb.append(bitmapA2 == null);
                com.igexin.c.a.c.a.a(str9, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a);
                sb2.append("|use net logo bitmap is null = ");
                sb2.append(bitmapA2 == null);
                z = false;
                com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
                bitmapDecodeResource = bitmapA2;
            }
            if (bitmapDecodeResource == null) {
                bitmapDecodeResource = BitmapFactory.decodeResource(com.igexin.push.core.e.l.getResources(), a(lVar, z));
            }
            Bitmap bitmap = bitmapDecodeResource;
            builderB.setSmallIcon(iA2).setTicker(lVar.b).setWhen(System.currentTimeMillis()).setContentTitle(str6).setContentIntent(pendingIntentA).setContentText(str7).setDeleteIntent(pendingIntentA2);
            if (Build.VERSION.SDK_INT >= 21 && !TextUtils.isEmpty(lVar.w)) {
                builderB.setCategory(lVar.w);
            }
            if (bitmap != null) {
                builderB.setLargeIcon(bitmap);
            }
            if (Build.VERSION.SDK_INT >= 17) {
                builderB.setShowWhen(true);
            }
            if (Build.VERSION.SDK_INT >= 24 && !TextUtils.isEmpty(lVar.i)) {
                try {
                    builderB.setColor(Color.parseColor(lVar.i));
                } catch (Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                }
            }
            if (Build.VERSION.SDK_INT >= 16) {
                if (lVar.C == a.BIG_IMAGE.e) {
                    String str10 = lVar.E;
                    if (!TextUtils.isEmpty(str10) && (bitmapA = com.igexin.push.g.m.a(str10)) != null) {
                        builderB.setPriority(lVar.x);
                        styleBigText = new Notification.BigPictureStyle().bigPicture(bitmapA);
                        builderB.setStyle(styleBigText);
                    }
                } else if (lVar.C == a.LONG_TEXT.e) {
                    String str11 = lVar.B;
                    if (!TextUtils.isEmpty(str11)) {
                        builderB.setPriority(lVar.x);
                        styleBigText = new Notification.BigTextStyle().bigText(str11);
                        builderB.setStyle(styleBigText);
                    }
                }
            }
            if (lVar.z && Build.VERSION.SDK_INT >= 21 && (lVar.c || lVar.d)) {
                builderB.setPriority(2);
            }
            if (TextUtils.isEmpty(str3) || Build.VERSION.SDK_INT < 24) {
                str2 = str3;
            } else {
                str2 = str3;
                if (com.igexin.push.core.e.aj.containsKey(str2)) {
                    builderB.setGroup(str2);
                    builderB.setGroupSummary(false);
                    HashSet<String> hashSet = com.igexin.push.core.e.aj.get(str2) == null ? new HashSet<>() : com.igexin.push.core.e.aj.get(str2);
                    hashSet.add(taskId);
                    com.igexin.push.core.e.aj.put(str2, hashSet);
                }
            }
            builderB.setWhen(System.currentTimeMillis());
            Notification notification = builderB.getNotification();
            notification.defaults = 4;
            notification.ledARGB = -16711936;
            notification.ledOnMS = 1000;
            notification.ledOffMS = 3000;
            notification.flags = 1;
            notification.flags = lVar.e ? notification.flags | 16 : notification.flags | 32;
            if (lVar.c) {
                notification.defaults |= 2;
            }
            if (lVar.d) {
                if (TextUtils.isEmpty(lVar.p)) {
                    notification.defaults |= 1;
                } else {
                    notification.sound = c(lVar.p);
                }
            }
            if (lVar.o > 0) {
                com.igexin.push.g.d.a(lVar.o, false);
                com.igexin.push.g.d.c(lVar.o, false);
                com.igexin.push.g.d.b(lVar.o, false);
            }
            notification.icon = a(lVar, true);
            a(notification);
            if (!TextUtils.isEmpty(str2) && notificationA != null) {
                int iA3 = a(str2);
                com.igexin.push.core.e.ak.put(str2, Integer.valueOf(iA3));
                notificationManager.notify(iA3, notificationA);
            }
            boolean z2 = false;
            com.igexin.c.a.c.a.a(a + "|showNotification notification:" + iA, new Object[0]);
            if (i3 > 0) {
                notificationManager.cancel(iA);
            }
            notificationManager.notify(iA, notification);
            com.igexin.push.core.l.a().a(taskId, str, str6, str7, lVar.t, lVar.u, lVar.v);
            try {
                if (!TextUtils.isEmpty(com.igexin.push.config.d.ad) && !com.igexin.push.config.d.ad.equals("null") && Build.VERSION.SDK_INT >= 23 && com.igexin.push.core.e.J == 1) {
                    String[] strArrSplit = com.igexin.push.config.d.ad.split(com.igexin.push.core.b.an);
                    if (com.igexin.push.config.d.ad.equals("*")) {
                        z2 = true;
                        if (z2) {
                        }
                    } else {
                        if (strArrSplit != null && strArrSplit.length > 0) {
                            for (String str12 : strArrSplit) {
                                if (str12.equalsIgnoreCase(com.igexin.push.core.e.G)) {
                                    z2 = true;
                                    break;
                                }
                            }
                        }
                        if (z2) {
                            com.igexin.b.a.a().b().schedule(new AnonymousClass2(notificationManager, iA, pushTaskBean), 300L, TimeUnit.MILLISECONDS);
                        }
                    }
                }
            } catch (Throwable th2) {
                com.igexin.c.a.c.a.a(th2);
            }
        } else {
            com.igexin.c.a.c.a.a(a + "|showNotification smallIconId: " + iA2 + " couldn't find resource", new Object[0]);
            i2 = i;
        }
        if (i2 != 0) {
            FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean, String.valueOf(i2), "notifyStyle:" + lVar.h);
        }
        com.igexin.push.core.e.c.a();
        com.igexin.push.core.e.c.a(pushTaskBean.getTaskId(), com.igexin.push.core.b.ai, lVar.r);
        pushTaskBean.setPerActionid(Integer.parseInt(lVar.getActionId()));
        pushTaskBean.setCurrentActionid(Integer.parseInt(lVar.getDoActionId()));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00aa  */
    @Override // com.igexin.push.extension.mod.PushMessageInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BaseActionBean parseAction(JSONObject jSONObject) {
        int i;
        int i2;
        try {
            com.igexin.push.core.b.l lVar = new com.igexin.push.core.b.l();
            lVar.setType(com.igexin.push.core.b.n);
            lVar.setActionId(jSONObject.getString("actionid"));
            lVar.setDoActionId(jSONObject.getString("do"));
            if (jSONObject.has("notifyStyle")) {
                try {
                    i = jSONObject.getInt("notifyStyle");
                } catch (Exception e) {
                    com.igexin.c.a.c.a.a(e);
                    i = 0;
                }
            } else {
                i = 0;
            }
            if (jSONObject.has(com.igexin.push.core.b.C)) {
                lVar.y = jSONObject.getString(com.igexin.push.core.b.C);
            }
            if (jSONObject.has("title")) {
                lVar.a = jSONObject.getString("title");
            }
            if (jSONObject.has("text")) {
                lVar.b = jSONObject.getString("text");
            }
            if (TextUtils.isEmpty(lVar.a) && TextUtils.isEmpty(lVar.b) && i != 4) {
                com.igexin.c.a.c.a.a(a, "title and content is empty, not support");
                com.igexin.c.a.c.a.a(a + "|title and content is empty, not support", new Object[0]);
                return null;
            }
            if (jSONObject.has("bigStyle")) {
                try {
                    i2 = jSONObject.getInt("bigStyle");
                } catch (Exception unused) {
                    i2 = 0;
                }
                if (i2 > 3 || i2 <= 0) {
                    i2 = 0;
                }
            }
            lVar.C = i2;
            if (jSONObject.has("logo_url") && jSONObject.getString("logo_url").startsWith("http")) {
                lVar.g = jSONObject.getString("logo_url");
            }
            if (jSONObject.has("logo") && !"".equals(jSONObject.getString("logo"))) {
                String string = jSONObject.getString("logo");
                if (string.lastIndexOf(".png") == -1 && string.lastIndexOf(".jpeg") == -1) {
                    string = "";
                    lVar.f = string;
                } else {
                    int iIndexOf = string.indexOf(".png");
                    if (iIndexOf == -1) {
                        iIndexOf = string.indexOf(".jpeg");
                    }
                    if (iIndexOf != -1) {
                        string = string.substring(0, iIndexOf);
                        if (Pattern.compile("^\\d+$").matcher(string).matches()) {
                            string = "";
                        }
                    }
                    lVar.f = string;
                }
            }
            try {
                if (jSONObject.has("priority")) {
                    int i3 = jSONObject.getInt("priority");
                    if (i3 <= -3 || i3 >= 3) {
                        lVar.x = 0;
                    } else {
                        lVar.x = i3;
                    }
                }
            } catch (Exception unused2) {
                lVar.x = 0;
            }
            if (i2 == 1 && jSONObject.has("big_image_url") && jSONObject.getString("big_image_url").startsWith("http")) {
                lVar.A = jSONObject.getString("big_image_url");
            } else if (i2 == 2 && jSONObject.has("big_text") && !jSONObject.getString("big_text").equals("")) {
                lVar.B = jSONObject.getString("big_text");
            } else if (i2 == 3) {
                com.igexin.c.a.c.a.a(a + "big style = 3 doesn't support", new Object[0]);
                return null;
            }
            if (jSONObject.has("isFloat") && Build.VERSION.SDK_INT >= 11) {
                jSONObject.getBoolean("isFloat");
                lVar.z = jSONObject.getBoolean("isFloat");
            }
            if (jSONObject.has("is_noclear")) {
                lVar.e = !jSONObject.getBoolean("is_noclear");
            }
            if (jSONObject.has("is_novibrate")) {
                lVar.c = !jSONObject.getBoolean("is_novibrate");
            }
            if (jSONObject.has("is_noring")) {
                lVar.d = !jSONObject.getBoolean("is_noring");
            }
            if (jSONObject.has("badgeAddNum")) {
                lVar.o = jSONObject.optInt("badgeAddNum");
            }
            if (jSONObject.has("ringName")) {
                lVar.p = jSONObject.getString("ringName");
            }
            if (jSONObject.has("color")) {
                lVar.i = jSONObject.getString("color");
            }
            if (jSONObject.has("channel")) {
                lVar.j = jSONObject.getString("channel");
            }
            if (jSONObject.has("channelName")) {
                lVar.k = jSONObject.getString("channelName");
            }
            if (jSONObject.has("channelLevel")) {
                lVar.a(jSONObject.getInt("channelLevel"));
            }
            if (lVar.l > 4 || lVar.l < 0) {
                lVar.a(3);
            }
            if (jSONObject.has("category")) {
                lVar.w = jSONObject.optString("category", "");
            }
            if (jSONObject.has("notifyid")) {
                try {
                    lVar.m = Integer.parseInt(jSONObject.optString("notifyid"));
                    lVar.n = true;
                } catch (NumberFormatException unused3) {
                    com.igexin.c.a.c.a.a(a + (" NotificationAction.parseAction() : " + jSONObject.optString("notifyid") + "_"), new Object[0]);
                }
            }
            if (jSONObject.has("group_id")) {
                lVar.q = jSONObject.getString("group_id");
            }
            if (jSONObject.has("redisplay_freq")) {
                lVar.r = jSONObject.getInt("redisplay_freq");
            }
            if (jSONObject.has("redisplay_duration")) {
                lVar.s = jSONObject.getLong("redisplay_duration");
            }
            return lVar;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return null;
        }
    }

    @Override // com.igexin.push.extension.mod.PushMessageInterface
    public PushMessageInterface.ActionPrepareState prepareExecuteAction(PushTaskBean pushTaskBean, BaseActionBean baseActionBean) {
        boolean z;
        if (!(baseActionBean instanceof com.igexin.push.core.b.l)) {
            return PushMessageInterface.ActionPrepareState.stop;
        }
        com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) baseActionBean;
        String str = lVar.g;
        String str2 = lVar.A;
        String taskId = pushTaskBean.getTaskId();
        String messageId = pushTaskBean.getMessageId();
        boolean z2 = true;
        if (str2 != null) {
            String strA = com.igexin.push.g.k.a(str2);
            if (strA.equals("")) {
                lVar.G = false;
                z = true;
            } else {
                lVar.E = strA;
                z = false;
            }
        } else {
            z = false;
        }
        if (str != null) {
            String strA2 = com.igexin.push.g.k.a(str);
            if ("".equals(strA2)) {
                lVar.F = false;
            } else {
                lVar.D = strA2;
                z2 = false;
            }
        } else {
            z2 = false;
        }
        if (!z2 && !z) {
            return PushMessageInterface.ActionPrepareState.success;
        }
        if (z2) {
            a(str, taskId, messageId, baseActionBean, 2);
        }
        if (z) {
            a(str2, taskId, messageId, baseActionBean, 8);
        }
        return PushMessageInterface.ActionPrepareState.wait;
    }
}
