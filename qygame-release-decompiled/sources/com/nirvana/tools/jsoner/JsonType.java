package com.nirvana.tools.jsoner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class JsonType<T> {
    private Constructor mConstructor;
    private Class<? super T> mGenericsClz;

    static Type getSuperclassTypeParameter(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return JsonHelper.canonicalize(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
    }

    private void setup() {
        this.mGenericsClz = (Class<? super T>) JsonHelper.getRawType(getSuperclassTypeParameter(getClass()));
        if (this.mGenericsClz == null) {
            throw new IllegalArgumentException("JsonType's generics is not recognizable!");
        }
        try {
            this.mConstructor = this.mGenericsClz.getDeclaredConstructor(new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T newInstance() {
        if (this.mConstructor == null) {
            setup();
        }
        if (this.mConstructor == null) {
            return null;
        }
        try {
            this.mConstructor.setAccessible(true);
            return (T) this.mConstructor.newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
