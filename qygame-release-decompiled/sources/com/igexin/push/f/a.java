package com.igexin.push.f;

import com.igexin.push.core.n;
import com.igexin.push.extension.mod.PushTaskBean;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class a implements com.igexin.push.f.b.c {
    private static final long b = 360000;
    private static a c = null;
    private static String d = "CheckCondition";
    private long a = 0;

    public static a a() {
        if (c == null) {
            synchronized (a.class) {
                if (c == null) {
                    c = new a();
                }
            }
        }
        return c;
    }

    public static void c() {
        n nVarA = n.a();
        com.igexin.c.a.c.a.a("PushMessageExecutor|--------checkConditionStatus the pushMessageMap from db because log gkt...", new Object[0]);
        try {
            if (com.igexin.push.g.c.a(System.currentTimeMillis())) {
                com.igexin.c.a.c.a.b("PushMessageExecutor", "message in silent time , ignored...");
                return;
            }
            if (nVarA.d()) {
                return;
            }
            for (Map.Entry<String, PushTaskBean> entry : com.igexin.push.core.e.ah.entrySet()) {
                try {
                    entry.getKey();
                    PushTaskBean value = entry.getValue();
                    if (value != null && value.getStatus() == com.igexin.push.core.b.af) {
                        String taskId = value.getTaskId();
                        Map<String, String> conditionMap = value.getConditionMap();
                        if (conditionMap == null) {
                            return;
                        }
                        if (nVarA.a(conditionMap, taskId, value)) {
                            n.a(taskId, value.getMessageId());
                        }
                    }
                } catch (Exception e) {
                    com.igexin.c.a.c.a.a(e);
                    com.igexin.c.a.c.a.a("PushMessageExecutor|" + e.toString(), new Object[0]);
                }
            }
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("PushMessageExecutor|" + e2.toString(), new Object[0]);
        }
    }

    private static boolean e() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (com.igexin.push.core.e.S <= 0) {
            com.igexin.push.core.e.S = jCurrentTimeMillis - 60000;
            return true;
        }
        if (jCurrentTimeMillis - com.igexin.push.core.e.S <= 60000) {
            return false;
        }
        com.igexin.push.core.e.S = jCurrentTimeMillis;
        return true;
    }

    @Override // com.igexin.push.f.b.c
    public final void a(long j) {
        this.a = j;
    }

    public final void a(boolean z) {
        if (!z || e()) {
            b();
        }
    }

    @Override // com.igexin.push.f.b.c
    public final void b() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.f.a.1
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                n.a().c();
            }
        }, false, true);
    }

    @Override // com.igexin.push.f.b.c
    public final boolean d() {
        return System.currentTimeMillis() - this.a > b;
    }
}
