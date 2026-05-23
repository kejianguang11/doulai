package com.igexin.push.f;

import android.database.Cursor;
import com.igexin.push.core.b.k;
import com.igexin.push.core.d;
import com.igexin.push.core.n;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import java.util.PriorityQueue;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e implements com.igexin.push.f.b.c {
    private static volatile e c;
    public String a = "ReDisplayTask";
    public volatile long b = 0;

    public static e a() {
        if (c == null) {
            synchronized (e.class) {
                if (c == null) {
                    c = new e();
                }
            }
        }
        return c;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030 A[Catch: Throwable -> 0x002e, all -> 0x0069, TryCatch #1 {Throwable -> 0x002e, blocks: (B:5:0x0014, B:7:0x001b, B:9:0x0021, B:13:0x0037, B:12:0x0030), top: B:29:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void c() throws Throwable {
        Cursor cursorA;
        Throwable th;
        try {
            cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, new String[0], "status = '1' and notify_status = '1' and redisplay_freq != '0' and redisplay_num <= redisplay_freq  order by expect_redisplay_time asc limit 1");
            if (cursorA != null) {
                try {
                    try {
                        if (cursorA.getCount() == 1 && cursorA.moveToFirst()) {
                            this.b = cursorA.getLong(cursorA.getColumnIndex("expect_redisplay_time"));
                        } else {
                            this.b = Long.MAX_VALUE;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        com.igexin.c.a.c.a.a(th);
                        com.igexin.c.a.c.a.b(this.a, " get next redisplay message fail" + th.toString());
                        if (cursorA != null) {
                            cursorA.close();
                            return;
                        }
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    throw th;
                }
            }
            long j = this.b;
            System.currentTimeMillis();
            if (cursorA != null) {
                cursorA.close();
            }
        } catch (Throwable th4) {
            cursorA = null;
            th = th4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008a A[PHI: r2
      0x008a: PHI (r2v6 android.database.Cursor) = (r2v5 android.database.Cursor), (r2v9 android.database.Cursor) binds: [B:18:0x0088, B:11:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PriorityQueue<k> e() throws Throwable {
        Cursor cursorA;
        Throwable th;
        PriorityQueue<k> priorityQueue = new PriorityQueue<>();
        try {
            cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, new String[0], "status = '1' and notify_status = '1' and redisplay_freq != '0' and redisplay_num <= redisplay_freq and expect_redisplay_time <= " + (System.currentTimeMillis() / 1000));
            if (cursorA != null) {
                while (cursorA.moveToNext()) {
                    try {
                        try {
                            priorityQueue.offer(new k(cursorA.getBlob(cursorA.getColumnIndex("msgextra")), new String(com.igexin.c.b.a.c(cursorA.getBlob(cursorA.getColumnIndex("info")))), cursorA.getLong(cursorA.getColumnIndex("expect_redisplay_time"))));
                        } catch (Throwable th2) {
                            th = th2;
                            com.igexin.c.a.c.a.a(th);
                            com.igexin.c.a.c.a.b(this.a, "get redisplay message" + th.toString());
                            if (cursorA != null) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (cursorA != null) {
                            cursorA.close();
                        }
                        throw th;
                    }
                }
            }
        } catch (Throwable th4) {
            th = th4;
            cursorA = null;
            if (cursorA != null) {
            }
            throw th;
        }
        if (cursorA != null) {
            cursorA.close();
        }
        return priorityQueue;
    }

    @Override // com.igexin.push.f.b.c
    public final void a(long j) {
    }

    @Override // com.igexin.push.f.b.c
    public final void b() {
        if (com.igexin.push.g.c.a(System.currentTimeMillis())) {
            com.igexin.c.a.c.a.b(this.a, "message in silent time period, ignored...");
        } else {
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new d() { // from class: com.igexin.push.f.e.1
                /* JADX WARN: Removed duplicated region for block: B:17:0x0087 A[Catch: Throwable -> 0x00ab, PHI: r7
                  0x0087: PHI (r7v3 com.igexin.push.extension.mod.PushTaskBean) = (r7v2 com.igexin.push.extension.mod.PushTaskBean), (r7v6 com.igexin.push.extension.mod.PushTaskBean) binds: [B:6:0x0059, B:14:0x007f] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Throwable -> 0x00ab, blocks: (B:5:0x003a, B:7:0x005b, B:9:0x006f, B:15:0x0081, B:16:0x0083, B:17:0x0087, B:19:0x0095, B:20:0x0098, B:22:0x00a0, B:23:0x00a3), top: B:28:0x003a }] */
                @Override // com.igexin.push.f.d
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                protected final void b() throws Throwable {
                    e eVar;
                    PriorityQueue priorityQueueE = e.this.e();
                    String unused = e.this.a;
                    priorityQueueE.size();
                    com.igexin.c.a.c.a.a(e.this.a + " | priorityQueue size = " + priorityQueueE.size(), new Object[0]);
                    while (true) {
                        k kVar = (k) priorityQueueE.poll();
                        if (kVar == null) {
                            return;
                        }
                        try {
                            JSONObject jSONObjectA = kVar.a();
                            String string = jSONObjectA.getString("taskid");
                            String string2 = jSONObjectA.getString("messageid");
                            com.igexin.push.core.a.b.d();
                            String strA = com.igexin.push.core.a.b.a(string, string2);
                            PushTaskBean pushTaskBean = com.igexin.push.core.e.ah.get(strA);
                            if (pushTaskBean == null) {
                                n.a().a(jSONObjectA, kVar.f, false);
                                pushTaskBean = com.igexin.push.core.e.ah.get(strA);
                                if (pushTaskBean == null || n.a().b(string, string2) != PushMessageInterface.ActionPrepareState.success) {
                                    eVar = e.this;
                                } else if (n.a().a(pushTaskBean.getConditionMap(), string, pushTaskBean)) {
                                    String actionIdByType = pushTaskBean.getActionIdByType(com.igexin.push.core.b.n);
                                    if (actionIdByType == null) {
                                        eVar = e.this;
                                    } else {
                                        n.a().a(string, string2, actionIdByType);
                                    }
                                } else {
                                    eVar = e.this;
                                }
                                String unused2 = eVar.a;
                            }
                        } catch (Throwable th) {
                            String unused3 = e.this.a;
                            com.igexin.c.a.c.a.a(th);
                        }
                    }
                }
            }, true);
        }
    }

    @Override // com.igexin.push.f.b.c
    public final boolean d() {
        boolean z = System.currentTimeMillis() / 1000 >= this.b;
        long j = this.b;
        com.igexin.c.a.c.a.a(this.a + " | ReDisplayTask isMatch =" + z + "， nextReDisplayTime =" + this.b, new Object[0]);
        return z;
    }
}
