package com.zx.a.I8b7;

import android.content.Context;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class s {
    public static Map<Class<? extends b>, b> a = new HashMap();

    public static <T extends c> T a(Class<? extends b> cls, Class<T> cls2) {
        b bVar = (b) ((HashMap) a).get(cls);
        if (bVar == null) {
            StringBuilder sbA = j3.a("db ");
            sbA.append(cls.getSimpleName());
            sbA.append(" has not been initialized");
            throw new RuntimeException(sbA.toString());
        }
        T t = (T) bVar.a.get(cls2);
        if (t != null) {
            return t;
        }
        StringBuilder sbA2 = j3.a("table ");
        sbA2.append(cls2.getSimpleName());
        sbA2.append(" has not been added to db ");
        sbA2.append(bVar.a());
        throw new RuntimeException(sbA2.toString());
    }

    public static void a(Context context, Class<? extends b> cls, Class<? extends c>... clsArr) throws IllegalAccessException, InstantiationException {
        Context applicationContext = context.getApplicationContext();
        b bVarNewInstance = (b) ((HashMap) a).get(cls);
        if (bVarNewInstance == null) {
            bVarNewInstance = cls.newInstance();
            ((HashMap) a).put(cls, bVarNewInstance);
        }
        for (Class<? extends c> cls2 : clsArr) {
            c cVarNewInstance = bVarNewInstance.a.get(cls2);
            if (cVarNewInstance == null) {
                cVarNewInstance = cls2.newInstance();
                bVarNewInstance.a.put(cls2, cVarNewInstance);
            }
            cVarNewInstance.a = bVarNewInstance;
        }
        if (bVarNewInstance.c.getAndSet(true)) {
            return;
        }
        bVarNewInstance.b = new a(bVarNewInstance, applicationContext, bVarNewInstance.a(), null, bVarNewInstance.c());
        Iterator<Class<? extends c>> it = bVarNewInstance.a.keySet().iterator();
        while (it.hasNext()) {
            bVarNewInstance.a.get(it.next()).getClass();
        }
    }
}
