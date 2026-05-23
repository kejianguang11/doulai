package androidx.lifecycle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class ClassesInfoCache {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static ClassesInfoCache a = new ClassesInfoCache();
    private final Map<Class<?>, CallbackInfo> mCallbackMap = new HashMap();
    private final Map<Class<?>, Boolean> mHasLifecycleMethods = new HashMap();

    static class CallbackInfo {
        final Map<Lifecycle.Event, List<MethodReference>> a = new HashMap();
        final Map<MethodReference, Lifecycle.Event> b;

        CallbackInfo(Map<MethodReference, Lifecycle.Event> map) {
            this.b = map;
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : map.entrySet()) {
                Lifecycle.Event value = entry.getValue();
                List<MethodReference> arrayList = this.a.get(value);
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    this.a.put(value, arrayList);
                }
                arrayList.add(entry.getKey());
            }
        }

        private static void invokeMethodsForEvent(List<MethodReference> list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) {
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.get(size).a(lifecycleOwner, event, obj);
                }
            }
        }

        void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) {
            invokeMethodsForEvent(this.a.get(event), lifecycleOwner, event, obj);
            invokeMethodsForEvent(this.a.get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj);
        }
    }

    static final class MethodReference {
        final int a;
        final Method b;

        MethodReference(int i, Method method) {
            this.a = i;
            this.b = method;
            this.b.setAccessible(true);
        }

        void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) {
            try {
                switch (this.a) {
                    case 0:
                        this.b.invoke(obj, new Object[0]);
                        return;
                    case 1:
                        this.b.invoke(obj, lifecycleOwner);
                        return;
                    case 2:
                        this.b.invoke(obj, lifecycleOwner, event);
                        return;
                    default:
                        return;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException("Failed to call observer method", e2.getCause());
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodReference)) {
                return false;
            }
            MethodReference methodReference = (MethodReference) obj;
            return this.a == methodReference.a && this.b.getName().equals(methodReference.b.getName());
        }

        public int hashCode() {
            return (this.a * 31) + this.b.getName().hashCode();
        }
    }

    ClassesInfoCache() {
    }

    private CallbackInfo createInfo(Class<?> cls, @Nullable Method[] methodArr) {
        int i;
        CallbackInfo callbackInfoB;
        Class<? super Object> superclass = cls.getSuperclass();
        HashMap map = new HashMap();
        if (superclass != null && (callbackInfoB = b(superclass)) != null) {
            map.putAll(callbackInfoB.b);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : b(cls2).b.entrySet()) {
                verifyAndPutHandler(map, entry.getKey(), entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            methodArr = getDeclaredMethods(cls);
        }
        boolean z = false;
        for (Method method : methodArr) {
            OnLifecycleEvent onLifecycleEvent = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (onLifecycleEvent != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else {
                    if (!parameterTypes[0].isAssignableFrom(LifecycleOwner.class)) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                    i = 1;
                }
                Lifecycle.Event eventValue = onLifecycleEvent.value();
                if (parameterTypes.length > 1) {
                    if (!parameterTypes[1].isAssignableFrom(Lifecycle.Event.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (eventValue != Lifecycle.Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                    i = 2;
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                verifyAndPutHandler(map, new MethodReference(i, method), eventValue, cls);
                z = true;
            }
        }
        CallbackInfo callbackInfo = new CallbackInfo(map);
        this.mCallbackMap.put(cls, callbackInfo);
        this.mHasLifecycleMethods.put(cls, Boolean.valueOf(z));
        return callbackInfo;
    }

    private Method[] getDeclaredMethods(Class<?> cls) {
        try {
            return cls.getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
        }
    }

    private void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> map, MethodReference methodReference, Lifecycle.Event event, Class<?> cls) {
        Lifecycle.Event event2 = map.get(methodReference);
        if (event2 == null || event == event2) {
            if (event2 == null) {
                map.put(methodReference, event);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Method " + methodReference.b.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + event2 + ", new value " + event);
    }

    boolean a(Class<?> cls) {
        Boolean bool = this.mHasLifecycleMethods.get(cls);
        if (bool != null) {
            return bool.booleanValue();
        }
        Method[] declaredMethods = getDeclaredMethods(cls);
        for (Method method : declaredMethods) {
            if (((OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class)) != null) {
                createInfo(cls, declaredMethods);
                return true;
            }
        }
        this.mHasLifecycleMethods.put(cls, false);
        return false;
    }

    CallbackInfo b(Class<?> cls) {
        CallbackInfo callbackInfo = this.mCallbackMap.get(cls);
        return callbackInfo != null ? callbackInfo : createInfo(cls, null);
    }
}
