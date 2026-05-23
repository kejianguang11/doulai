package com.zx.a.I8b7;

import com.zx.module.base.Listener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class a1 implements Listener {
    public final Map<String, Set<b1>> a = new HashMap();

    public synchronized void a(String str, b1 b1Var) {
        if (!this.a.containsKey(str)) {
            this.a.put(str, new HashSet());
        }
        this.a.get(str).add(b1Var);
    }

    @Override // com.zx.module.base.Listener
    public void onMessage(String str, String str2) {
        Set<b1> set = this.a.get(str);
        if (set != null) {
            Iterator<b1> it = set.iterator();
            while (it.hasNext()) {
                it.next().a(str2);
            }
        }
    }
}
