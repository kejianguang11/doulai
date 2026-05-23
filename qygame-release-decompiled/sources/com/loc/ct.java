package com.loc;

import com.gme.trtc.hardwareearmonitor.honor.HonorResultCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ct {
    private dx b;
    private List<dy> a = new ArrayList();
    private ArrayList<dy> c = new ArrayList<>();

    private static List<dy> a(List<dy> list) {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            dy dyVar = list.get(i);
            map.put(Integer.valueOf(dyVar.c), dyVar);
        }
        arrayList.addAll(map.values());
        return arrayList;
    }

    private boolean a(dx dxVar) {
        float f = 10.0f;
        if (dxVar.g > 10.0f) {
            f = 200.0f;
        } else if (dxVar.g > 2.0f) {
            f = 50.0f;
        }
        return dxVar.a(this.b) > ((double) f);
    }

    private static boolean a(dx dxVar, long j, long j2) {
        return j > 0 && j2 - j < ((long) ((dxVar.g > 10.0f ? 1 : (dxVar.g == 10.0f ? 0 : -1)) >= 0 ? HonorResultCode.ADVANCED_RECORD_SUCCESS : com.alipay.sdk.data.a.a));
    }

    private static boolean a(List<dy> list, List<dy> list2) {
        if (list != null && list2 != null) {
            int size = list.size();
            int size2 = list2.size();
            int i = size + size2;
            if (size <= size2) {
                list2 = list;
                list = list2;
            }
            HashMap map = new HashMap(list.size());
            Iterator<dy> it = list.iterator();
            while (it.hasNext()) {
                map.put(Long.valueOf(it.next().a), 1);
            }
            Iterator<dy> it2 = list2.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                if (((Integer) map.get(Long.valueOf(it2.next().a))) != null) {
                    i2++;
                }
            }
            if (((double) i2) * 2.0d >= ((double) i) * 0.5d) {
                return true;
            }
        }
        return false;
    }

    private List<dy> b(List<dy> list) {
        Collections.sort(list, new Comparator<dy>() { // from class: com.loc.ct.1
            private static int a(dy dyVar, dy dyVar2) {
                return dyVar2.c - dyVar.c;
            }

            @Override // java.util.Comparator
            public final /* synthetic */ int compare(dy dyVar, dy dyVar2) {
                return a(dyVar, dyVar2);
            }
        });
        return list;
    }

    private void b(List<dy> list, List<dy> list2) {
        list.clear();
        if (list2 != null) {
            List<dy> listB = b(a(list2));
            int size = listB.size();
            if (size > 40) {
                size = 40;
            }
            for (int i = 0; i < size; i++) {
                list.add(listB.get(i));
            }
        }
    }

    private boolean b(dx dxVar, List<dy> list, boolean z, long j, long j2) {
        if (!z || !a(dxVar, j, j2) || list == null || list.size() <= 0) {
            return false;
        }
        if (this.b == null) {
            return true;
        }
        boolean zA = a(dxVar);
        return !zA ? !a(list, this.a) : zA;
    }

    final List<dy> a(dx dxVar, List<dy> list, boolean z, long j, long j2) {
        if (!b(dxVar, list, z, j, j2)) {
            return null;
        }
        b(this.c, list);
        this.a.clear();
        this.a.addAll(list);
        this.b = dxVar;
        return this.c;
    }
}
