package com.igexin.push.core;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.assist.sdk.AssistPushManager;
import com.igexin.push.core.d;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import com.igexin.push.g.q;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.main.FeedbackImpl;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class c extends Handler {
    private static String a = "com.igexin.push.core.c";
    private boolean b;

    public c(Looper looper) {
        super(looper);
        this.b = false;
    }

    private static void a() {
        if (e.u) {
            com.igexin.c.a.c.a.b(a, "userPresent sendHeartbeat");
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.f();
        } else {
            if (e.u || e.O <= com.igexin.push.config.c.i) {
                return;
            }
            com.igexin.push.f.b.e.g().a("userPresent", (int) ((Math.random() * 100.0d) + 150.0d));
        }
    }

    private static void a(Intent intent) throws Throwable {
        String stringExtra = intent.getStringExtra("action");
        if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE)) {
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.a(intent);
            return;
        }
        if (stringExtra.equals(PushConsts.ACTION_SERVICE_ONRESUME)) {
            com.igexin.c.a.c.a.a(a + "|handle onresume ~~~", new Object[0]);
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.b("on fg");
            return;
        }
        if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE)) {
            com.igexin.push.core.a.b.d();
            com.igexin.push.core.a.b.b(intent);
            AssistPushManager.getInstance().turnOnPush(e.l);
            return;
        }
        if (stringExtra.equals(PushConsts.ACTION_BROADCAST_PUSHMANAGER)) {
            String stringExtra2 = intent.getStringExtra(q.e);
            if (TextUtils.isEmpty(e.i) || e.i.equals(stringExtra2)) {
                Bundle bundleExtra = intent.getBundleExtra("bundle");
                com.igexin.push.core.a.b.d();
                com.igexin.push.core.a.b.a(bundleExtra);
                return;
            }
            com.igexin.c.a.c.a.a("safeCode not match!!" + e.i + b.an + stringExtra2, new Object[0]);
            com.igexin.c.a.c.a.d.a().a("safeCode not match!!" + e.i + b.an + stringExtra2);
            return;
        }
        if (stringExtra.equals(PushConsts.ACTION_BROADCAST_USER_PRESENT)) {
            if (e.u) {
                com.igexin.c.a.c.a.b(a, "userPresent sendHeartbeat");
                com.igexin.push.core.a.b.d();
                com.igexin.push.core.a.b.f();
                return;
            } else {
                if (e.u || e.O <= com.igexin.push.config.c.i) {
                    return;
                }
                com.igexin.push.f.b.e.g().a("userPresent", (int) ((Math.random() * 100.0d) + 150.0d));
                return;
            }
        }
        if (stringExtra.equals("com.igexin.action.notification.click")) {
            Intent intent2 = (Intent) intent.getParcelableExtra("broadcast_intent");
            if (intent2 != null) {
                com.igexin.push.core.f.a.a();
                com.igexin.push.core.f.a.a(intent2);
                return;
            }
            return;
        }
        if (stringExtra.equals(b.M)) {
            HashMap map = (HashMap) intent.getSerializableExtra("push_action");
            com.igexin.c.a.c.a.a(a + "| handle other push action broadcast", new Object[0]);
            n.a().a.putAll(map);
            com.igexin.push.f.a.a();
            com.igexin.push.f.a.c();
            return;
        }
        if (!stringExtra.equals("com.igexin.action.notification.delete")) {
            if (stringExtra.equals(PushConsts.ACTION_BROADCAST_UPLOAD_TYPE253)) {
                String stringExtra3 = intent.getStringExtra(b.C);
                String stringExtra4 = intent.getStringExtra("aid");
                com.igexin.push.core.c.a.a();
                com.igexin.push.core.c.a.a(stringExtra3, stringExtra4);
                return;
            }
            return;
        }
        if (intent.getBooleanExtra("isSummary", false)) {
            String stringExtra5 = intent.getStringExtra("groupId");
            e.aj.remove(stringExtra5);
            e.ak.remove(stringExtra5);
            return;
        }
        PushTaskBean pushTaskBean = new PushTaskBean();
        pushTaskBean.setAppid(intent.getStringExtra("appid"));
        pushTaskBean.setMessageId(intent.getStringExtra("messageid"));
        String stringExtra6 = intent.getStringExtra("taskid");
        pushTaskBean.setTaskId(stringExtra6);
        pushTaskBean.setId(intent.getStringExtra(b.C));
        pushTaskBean.setAppKey(intent.getStringExtra(com.alipay.sdk.sys.a.f));
        com.igexin.push.core.e.c.a();
        com.igexin.push.core.e.c.a(stringExtra6, b.ak, intent.getIntExtra("redisplayFreq", 0));
        int i = Integer.parseInt(intent.getStringExtra("feedbackid")) + 30040;
        pushTaskBean.setCurrentActionid(i);
        FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean, String.valueOf(i), "notifyStyle:" + intent.getStringExtra("notifyStyle"));
        com.igexin.push.core.a.b.d();
        String strA = com.igexin.push.core.a.b.a(intent.getStringExtra("taskid"), intent.getStringExtra("messageid"));
        com.igexin.c.a.c.a.a(a + "|notification delete = " + strA, new Object[0]);
        try {
            e.ah.remove(strA);
            com.igexin.c.a.c.a.a(a + "|del notification, pushMessageMap remove = " + strA, new Object[0]);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a("EndAction|" + e.toString(), new Object[0]);
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message == null) {
            return;
        }
        int i = message.what;
        if (message.obj == null) {
            return;
        }
        try {
            if (message.what != b.Q) {
                if (message.what == b.R) {
                    com.igexin.push.core.a.b.d();
                    Intent intent = (Intent) message.obj;
                    if (intent == null || intent.getAction() == null) {
                        return;
                    }
                    try {
                        String action = intent.getAction();
                        if (PushConsts.ACTION_BROADCAST_NETWORK_CHANGE.equals(action)) {
                            com.igexin.push.core.a.b.e();
                            return;
                        }
                        if (b.L.equals(action)) {
                            n.a().a(intent);
                            return;
                        }
                        if (b.N.equals(action)) {
                            if (com.igexin.push.config.d.c != 0) {
                                com.igexin.push.f.f.a().c();
                                return;
                            }
                            return;
                        } else if (!"android.intent.action.SCREEN_ON".equals(action)) {
                            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                                e.y = 0;
                                return;
                            }
                            return;
                        } else {
                            e.y = 1;
                            com.igexin.push.f.a.a().a(true);
                            if (Build.VERSION.SDK_INT >= 26) {
                                com.igexin.push.core.a.b.b("screen on");
                                return;
                            }
                            return;
                        }
                    } catch (Throwable th) {
                        com.igexin.c.a.c.a.a(th);
                        return;
                    }
                }
                if (message.what != b.U) {
                    if (message.what == b.S) {
                        Bundle bundle = (Bundle) message.obj;
                        n.a().b(bundle.getString("taskid"), bundle.getString("messageid"), bundle.getString("actionid"));
                        return;
                    }
                    if (message.what == b.V) {
                        if (this.b) {
                            return;
                        }
                        d unused = d.a.a;
                        d.c();
                        this.b = true;
                        return;
                    }
                    if (message.what != b.T || "false".equals(e.I)) {
                        return;
                    }
                    com.igexin.push.d.c.n nVar = new com.igexin.push.d.c.n();
                    nVar.c = 128;
                    nVar.f = message.obj;
                    nVar.g = message.getData().getByteArray(AssistPushConsts.MSG_TYPE_PAYLOAD);
                    new com.igexin.push.core.a.b.d().a(nVar);
                    return;
                }
                Bundle bundle2 = (Bundle) message.obj;
                String string = bundle2.getString("taskid");
                String string2 = bundle2.getString("messageid");
                n nVarA = n.a();
                if (string2 == null || string == null) {
                    return;
                }
                try {
                    com.igexin.push.core.a.b.d();
                    String strA = com.igexin.push.core.a.b.a(string, string2);
                    PushTaskBean pushTaskBean = e.ah.get(strA);
                    if (pushTaskBean == null) {
                        return;
                    }
                    if (pushTaskBean.getStatus() == b.ag) {
                        com.igexin.c.a.c.a.b("PushMessageExecutor", " has execute ".concat(String.valueOf(strA)));
                        return;
                    }
                    pushTaskBean.setStatus(b.ag);
                    com.igexin.c.a.c.a.b("PushMessageExecutor", " do processActionExecute ".concat(String.valueOf(strA)));
                    if (nVarA.b(string, string2) != PushMessageInterface.ActionPrepareState.success) {
                        pushTaskBean.setStatus(b.af);
                        return;
                    }
                    com.igexin.push.core.e.c.a();
                    com.igexin.push.core.e.c.a(b.ag, string);
                    pushTaskBean.setStatus(b.ag);
                    if (nVarA.a(string, string2, "1")) {
                        return;
                    }
                    com.igexin.push.core.e.c.a();
                    com.igexin.push.core.e.c.a(b.af, string);
                    pushTaskBean.setStatus(b.af);
                    return;
                } catch (Throwable th2) {
                    com.igexin.c.a.c.a.a(th2);
                    return;
                }
            }
            Intent intent2 = (Intent) message.obj;
            if (intent2.hasExtra("action")) {
                String stringExtra = intent2.getStringExtra("action");
                if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE)) {
                    com.igexin.push.core.a.b.d();
                    com.igexin.push.core.a.b.a(intent2);
                    return;
                }
                if (stringExtra.equals(PushConsts.ACTION_SERVICE_ONRESUME)) {
                    com.igexin.c.a.c.a.a(a + "|handle onresume ~~~", new Object[0]);
                    com.igexin.push.core.a.b.d();
                    com.igexin.push.core.a.b.b("on fg");
                    return;
                }
                if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE)) {
                    com.igexin.push.core.a.b.d();
                    com.igexin.push.core.a.b.b(intent2);
                    AssistPushManager.getInstance().turnOnPush(e.l);
                    return;
                }
                if (stringExtra.equals(PushConsts.ACTION_BROADCAST_PUSHMANAGER)) {
                    String stringExtra2 = intent2.getStringExtra(q.e);
                    if (TextUtils.isEmpty(e.i) || e.i.equals(stringExtra2)) {
                        Bundle bundleExtra = intent2.getBundleExtra("bundle");
                        com.igexin.push.core.a.b.d();
                        com.igexin.push.core.a.b.a(bundleExtra);
                        return;
                    }
                    com.igexin.c.a.c.a.a("safeCode not match!!" + e.i + b.an + stringExtra2, new Object[0]);
                    com.igexin.c.a.c.a.d.a().a("safeCode not match!!" + e.i + b.an + stringExtra2);
                    return;
                }
                if (stringExtra.equals(PushConsts.ACTION_BROADCAST_USER_PRESENT)) {
                    if (e.u) {
                        com.igexin.c.a.c.a.b(a, "userPresent sendHeartbeat");
                        com.igexin.push.core.a.b.d();
                        com.igexin.push.core.a.b.f();
                        return;
                    } else {
                        if (e.u || e.O <= com.igexin.push.config.c.i) {
                            return;
                        }
                        com.igexin.push.f.b.e.g().a("userPresent", (int) ((Math.random() * 100.0d) + 150.0d));
                        return;
                    }
                }
                if (stringExtra.equals("com.igexin.action.notification.click")) {
                    Intent intent3 = (Intent) intent2.getParcelableExtra("broadcast_intent");
                    if (intent3 != null) {
                        com.igexin.push.core.f.a.a();
                        com.igexin.push.core.f.a.a(intent3);
                        return;
                    }
                    return;
                }
                if (stringExtra.equals(b.M)) {
                    HashMap map = (HashMap) intent2.getSerializableExtra("push_action");
                    com.igexin.c.a.c.a.a(a + "| handle other push action broadcast", new Object[0]);
                    n.a().a.putAll(map);
                    com.igexin.push.f.a.a();
                    com.igexin.push.f.a.c();
                    return;
                }
                if (!stringExtra.equals("com.igexin.action.notification.delete")) {
                    if (stringExtra.equals(PushConsts.ACTION_BROADCAST_UPLOAD_TYPE253)) {
                        String stringExtra3 = intent2.getStringExtra(b.C);
                        String stringExtra4 = intent2.getStringExtra("aid");
                        com.igexin.push.core.c.a.a();
                        com.igexin.push.core.c.a.a(stringExtra3, stringExtra4);
                        return;
                    }
                    return;
                }
                if (intent2.getBooleanExtra("isSummary", false)) {
                    String stringExtra5 = intent2.getStringExtra("groupId");
                    e.aj.remove(stringExtra5);
                    e.ak.remove(stringExtra5);
                    return;
                }
                PushTaskBean pushTaskBean2 = new PushTaskBean();
                pushTaskBean2.setAppid(intent2.getStringExtra("appid"));
                pushTaskBean2.setMessageId(intent2.getStringExtra("messageid"));
                String stringExtra6 = intent2.getStringExtra("taskid");
                pushTaskBean2.setTaskId(stringExtra6);
                pushTaskBean2.setId(intent2.getStringExtra(b.C));
                pushTaskBean2.setAppKey(intent2.getStringExtra(com.alipay.sdk.sys.a.f));
                com.igexin.push.core.e.c.a();
                com.igexin.push.core.e.c.a(stringExtra6, b.ak, intent2.getIntExtra("redisplayFreq", 0));
                int i2 = Integer.parseInt(intent2.getStringExtra("feedbackid")) + 30040;
                pushTaskBean2.setCurrentActionid(i2);
                FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean2, String.valueOf(i2), "notifyStyle:" + intent2.getStringExtra("notifyStyle"));
                com.igexin.push.core.a.b.d();
                String strA2 = com.igexin.push.core.a.b.a(intent2.getStringExtra("taskid"), intent2.getStringExtra("messageid"));
                com.igexin.c.a.c.a.a(a + "|notification delete = " + strA2, new Object[0]);
                try {
                    e.ah.remove(strA2);
                    com.igexin.c.a.c.a.a(a + "|del notification, pushMessageMap remove = " + strA2, new Object[0]);
                    return;
                } catch (Exception e) {
                    com.igexin.c.a.c.a.a(e);
                    com.igexin.c.a.c.a.a("EndAction|" + e.toString(), new Object[0]);
                    return;
                }
            }
            return;
        } catch (Throwable th3) {
            com.igexin.c.a.c.a.a(th3);
        }
        com.igexin.c.a.c.a.a(th3);
    }
}
