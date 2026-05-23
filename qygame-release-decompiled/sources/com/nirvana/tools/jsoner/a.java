package com.nirvana.tools.jsoner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
final class a {
    private ConcurrentHashMap<String, b> b = new ConcurrentHashMap<>();
    List<Field> a = new ArrayList();

    public a(Class cls) {
        while (!Object.class.equals(cls)) {
            Collections.addAll(this.a, cls.getDeclaredFields());
            cls = cls.getSuperclass();
        }
        Iterator<Field> it = this.a.iterator();
        while (it.hasNext()) {
            if (Modifier.isFinal(it.next().getModifiers())) {
                it.remove();
            }
        }
    }

    public final b a(String str) {
        return this.b.get(str);
    }

    public final void a(String str, b bVar) {
        this.b.put(str, bVar);
    }
}
