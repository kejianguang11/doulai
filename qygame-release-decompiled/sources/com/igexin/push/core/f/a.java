package com.igexin.push.core.f;

import android.app.NotificationManager;
import android.content.Intent;
import android.text.TextUtils;
import com.igexin.push.core.b;
import com.igexin.push.core.e;
import com.igexin.push.core.e.c;
import com.igexin.push.core.n;
import com.igexin.push.extension.mod.PushTaskBean;
import com.igexin.sdk.main.FeedbackImpl;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static final String a = "NotificationExecutor";
    private static a b;

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    public static void a(Intent intent) throws Throwable {
        StringBuilder sb;
        String stringExtra;
        String stringExtra2 = intent.getStringExtra("taskid");
        String stringExtra3 = intent.getStringExtra("groupId");
        HashSet<String> hashSet = e.aj.get(stringExtra3);
        Integer num = e.ak.get(stringExtra3);
        if (hashSet != null && !hashSet.isEmpty()) {
            hashSet.remove(stringExtra2);
        }
        if (!TextUtils.isEmpty(stringExtra3) && num != null && hashSet != null && hashSet.isEmpty()) {
            ((NotificationManager) e.l.getSystemService(b.n)).cancel(num.intValue());
            e.aj.remove(stringExtra3);
            e.ak.remove(stringExtra3);
        }
        String stringExtra4 = intent.getStringExtra("checkpackage");
        String stringExtra5 = intent.getStringExtra("accesstoken");
        if (stringExtra4 == null || stringExtra5 == null || !stringExtra4.equals(e.l.getPackageName()) || !stringExtra5.equals(e.aC)) {
            return;
        }
        intent.putExtra("accesstoken", e.an);
        n.a().a(intent);
        c.a();
        c.a(stringExtra2, b.aj, intent.getIntExtra("redisplayFreq", 0));
        PushTaskBean pushTaskBean = new PushTaskBean();
        pushTaskBean.setAppid(intent.getStringExtra("appid"));
        pushTaskBean.setMessageId(intent.getStringExtra("messageid"));
        pushTaskBean.setTaskId(stringExtra2);
        pushTaskBean.setId(intent.getStringExtra(b.C));
        intent.getStringExtra("bigStyle");
        intent.getStringExtra("notifyStyle");
        try {
            int i = Integer.parseInt(intent.getStringExtra("feedbackid")) + 30010;
            pushTaskBean.setCurrentActionid(i);
            if (intent.getBooleanExtra("isFloat", false)) {
                sb = new StringBuilder("notifyFloat:");
                stringExtra = intent.getStringExtra("bigStyle");
            } else {
                sb = new StringBuilder("notifyStyle:");
                stringExtra = intent.getStringExtra("notifyStyle");
            }
            sb.append(stringExtra);
            FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean, String.valueOf(i), sb.toString());
        } catch (Exception unused) {
        }
    }
}
