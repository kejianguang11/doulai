package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public abstract class FieldDeserializer {
    protected BeanContext beanContext;
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public FieldDeserializer(Class<?> cls, FieldInfo fieldInfo) {
        this.clazz = cls;
        this.fieldInfo = fieldInfo;
    }

    public int getFastMatchToken() {
        return 0;
    }

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public void setValue(Object obj, int i) {
        setValue(obj, Integer.valueOf(i));
    }

    public void setValue(Object obj, long j) {
        setValue(obj, Long.valueOf(j));
    }

    public void setValue(Object obj, Object obj2) {
        Collection collection;
        Collection collection2;
        Map map;
        Map map2;
        AtomicBoolean atomicBoolean;
        boolean z;
        AtomicLong atomicLong;
        long j;
        AtomicInteger atomicInteger;
        int i;
        if (obj2 == null && this.fieldInfo.fieldClass.isPrimitive()) {
            return;
        }
        if (this.fieldInfo.fieldClass == String.class && this.fieldInfo.format != null && this.fieldInfo.format.equals("trim")) {
            obj2 = ((String) obj2).trim();
        }
        try {
            Method method = this.fieldInfo.method;
            if (method != null) {
                if (!this.fieldInfo.getOnly) {
                    method.invoke(obj, obj2);
                    return;
                }
                if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                    atomicInteger = (AtomicInteger) method.invoke(obj, new Object[0]);
                    if (atomicInteger != null) {
                        i = ((AtomicInteger) obj2).get();
                        atomicInteger.set(i);
                        return;
                    }
                    return;
                }
                if (this.fieldInfo.fieldClass == AtomicLong.class) {
                    atomicLong = (AtomicLong) method.invoke(obj, new Object[0]);
                    if (atomicLong != null) {
                        j = ((AtomicLong) obj2).get();
                        atomicLong.set(j);
                        return;
                    }
                    return;
                }
                if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                    atomicBoolean = (AtomicBoolean) method.invoke(obj, new Object[0]);
                    if (atomicBoolean != null) {
                        z = ((AtomicBoolean) obj2).get();
                        atomicBoolean.set(z);
                        return;
                    }
                    return;
                }
                if (Map.class.isAssignableFrom(method.getReturnType())) {
                    map = (Map) method.invoke(obj, new Object[0]);
                    if (map != null && map != Collections.emptyMap() && !map.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                        map2 = (Map) obj2;
                        map.putAll(map2);
                        return;
                    }
                    return;
                }
                collection = (Collection) method.invoke(obj, new Object[0]);
                if (collection != null && obj2 != null && collection != Collections.emptySet() && collection != Collections.emptyList() && !collection.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                    collection.clear();
                    collection2 = (Collection) obj2;
                    collection.addAll(collection2);
                }
                return;
            }
            Field field = this.fieldInfo.field;
            if (!this.fieldInfo.getOnly) {
                if (field != null) {
                    field.set(obj, obj2);
                    return;
                }
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                atomicInteger = (AtomicInteger) field.get(obj);
                if (atomicInteger != null) {
                    i = ((AtomicInteger) obj2).get();
                    atomicInteger.set(i);
                    return;
                }
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicLong.class) {
                atomicLong = (AtomicLong) field.get(obj);
                if (atomicLong != null) {
                    j = ((AtomicLong) obj2).get();
                    atomicLong.set(j);
                    return;
                }
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                atomicBoolean = (AtomicBoolean) field.get(obj);
                if (atomicBoolean != null) {
                    z = ((AtomicBoolean) obj2).get();
                    atomicBoolean.set(z);
                    return;
                }
                return;
            }
            if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                map = (Map) field.get(obj);
                if (map != null && map != Collections.emptyMap() && !map.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                    map2 = (Map) obj2;
                    map.putAll(map2);
                    return;
                }
                return;
            }
            collection = (Collection) field.get(obj);
            if (collection != null && obj2 != null && collection != Collections.emptySet() && collection != Collections.emptyList() && !collection.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                collection.clear();
                collection2 = (Collection) obj2;
                collection.addAll(collection2);
            }
        } catch (Exception e) {
            throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e);
        }
    }

    public void setValue(Object obj, String str) {
        setValue(obj, (Object) str);
    }

    public void setValue(Object obj, boolean z) {
        setValue(obj, Boolean.valueOf(z));
    }

    public void setWrappedValue(String str, Object obj) {
        throw new JSONException("TODO");
    }
}
