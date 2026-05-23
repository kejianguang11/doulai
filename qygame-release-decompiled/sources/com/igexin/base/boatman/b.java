package com.igexin.base.boatman;

import com.igexin.base.boatman.receive.IBoatResult;
import com.igexin.base.boatman.receive.Site;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public final ReentrantLock a = new ReentrantLock();
    public final Map<String, Site> b = new ConcurrentHashMap();
    public final Map<String, List<a>> c = new HashMap();

    final <B, V> void a(Boater<B, V> boater, B b, IBoatResult<V> iBoatResult) {
        Site site = this.b.get(boater.getTag());
        if (site == null) {
            return;
        }
        site.onArrived(b, iBoatResult);
    }

    final boolean a(Boater boater, Object obj) {
        String tag = boater.getTag();
        this.a.lock();
        try {
            List<a> list = this.c.get(tag);
            boolean z = false;
            if (list == null) {
                return false;
            }
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().a == obj) {
                    z = true;
                    it.remove();
                }
            }
            return z;
        } finally {
            this.a.unlock();
        }
    }
}
