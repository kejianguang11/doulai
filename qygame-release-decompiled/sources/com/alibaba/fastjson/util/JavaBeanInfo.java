package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;
    public boolean kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    /* JADX WARN: Removed duplicated region for block: B:44:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JavaBeanInfo(Class<?> cls, Class<?> cls2, Constructor<?> constructor, Constructor<?> constructor2, Method method, Method method2, JSONType jSONType, List<FieldInfo> list) {
        int length;
        JSONField jSONField;
        this.clazz = cls;
        this.builderClass = cls2;
        this.defaultConstructor = constructor;
        this.creatorConstructor = constructor2;
        this.factoryMethod = method;
        this.parserFeatures = TypeUtils.getParserFeatures(cls);
        this.buildMethod = method2;
        this.jsonType = jSONType;
        if (jSONType != null) {
            String strTypeName = jSONType.typeName();
            String strTypeKey = jSONType.typeKey();
            this.typeKey = strTypeKey.length() <= 0 ? null : strTypeKey;
            this.typeName = strTypeName.length() == 0 ? cls.getName() : strTypeName;
            String[] strArrOrders = jSONType.orders();
            this.orders = strArrOrders.length == 0 ? null : strArrOrders;
        } else {
            this.typeName = cls.getName();
            this.typeKey = null;
            this.orders = null;
        }
        this.fields = new FieldInfo[list.size()];
        list.toArray(this.fields);
        FieldInfo[] fieldInfoArr = new FieldInfo[this.fields.length];
        boolean z = false;
        if (this.orders != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            for (FieldInfo fieldInfo : this.fields) {
                linkedHashMap.put(fieldInfo.name, fieldInfo);
            }
            int i = 0;
            for (String str : this.orders) {
                FieldInfo fieldInfo2 = (FieldInfo) linkedHashMap.get(str);
                if (fieldInfo2 != null) {
                    fieldInfoArr[i] = fieldInfo2;
                    linkedHashMap.remove(str);
                    i++;
                }
            }
            Iterator it = linkedHashMap.values().iterator();
            while (it.hasNext()) {
                fieldInfoArr[i] = (FieldInfo) it.next();
                i++;
            }
        } else {
            System.arraycopy(this.fields, 0, fieldInfoArr, 0, this.fields.length);
            Arrays.sort(fieldInfoArr);
        }
        this.sortedFields = Arrays.equals(this.fields, fieldInfoArr) ? this.fields : fieldInfoArr;
        if (constructor != null) {
            length = constructor.getParameterTypes().length;
        } else {
            if (method == null) {
                this.defaultConstructorParameterSize = 0;
                if (constructor2 == null) {
                    this.creatorConstructorParameterTypes = constructor2.getParameterTypes();
                    this.kotlin = TypeUtils.isKotlin(cls);
                    if (!this.kotlin) {
                        if (this.creatorConstructorParameterTypes.length == this.fields.length) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= this.creatorConstructorParameterTypes.length) {
                                    z = true;
                                    break;
                                } else if (this.creatorConstructorParameterTypes[i2] != this.fields[i2].fieldClass) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        if (z) {
                            return;
                        }
                        this.creatorConstructorParameters = ASMUtils.lookupParameterNames(constructor2);
                        return;
                    }
                    this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
                    try {
                        this.kotlinDefaultConstructor = cls.getConstructor(new Class[0]);
                    } catch (Throwable unused) {
                    }
                    Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor2);
                    for (int i3 = 0; i3 < this.creatorConstructorParameters.length && i3 < parameterAnnotations.length; i3++) {
                        Annotation[] annotationArr = parameterAnnotations[i3];
                        int length2 = annotationArr.length;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length2) {
                                jSONField = null;
                                break;
                            }
                            Annotation annotation = annotationArr[i4];
                            if (annotation instanceof JSONField) {
                                jSONField = (JSONField) annotation;
                                break;
                            }
                            i4++;
                        }
                        if (jSONField != null) {
                            String strName = jSONField.name();
                            if (strName.length() > 0) {
                                this.creatorConstructorParameters[i3] = strName;
                            }
                        }
                    }
                    return;
                }
                return;
            }
            length = method.getParameterTypes().length;
        }
        this.defaultConstructorParameterSize = length;
        if (constructor2 == null) {
        }
    }

    static boolean add(List<FieldInfo> list, FieldInfo fieldInfo) {
        for (int size = list.size() - 1; size >= 0; size--) {
            FieldInfo fieldInfo2 = list.get(size);
            if (fieldInfo2.name.equals(fieldInfo.name) && (!fieldInfo2.getOnly || fieldInfo.getOnly)) {
                if (fieldInfo2.fieldClass.isAssignableFrom(fieldInfo.fieldClass)) {
                    list.set(size, fieldInfo);
                    return true;
                }
                if (fieldInfo2.compareTo(fieldInfo) >= 0) {
                    return false;
                }
                list.set(size, fieldInfo);
                return true;
            }
        }
        list.add(fieldInfo);
        return true;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(cls, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z, boolean z2) {
        return build(cls, type, propertyNamingStrategy, z, z2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:355:0x0712, code lost:
    
        if (java.lang.Character.isUpperCase(r0.charAt(4)) != false) goto L359;
     */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x05b3  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0747  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0777  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x077b  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x07f3  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0837  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x0901 A[PHI: r6
      0x0901: PHI (r6v3 java.lang.reflect.Field[]) = (r6v2 java.lang.reflect.Field[]), (r6v4 java.lang.reflect.Field[]) binds: [B:426:0x08ff, B:431:0x0919] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:437:0x0966  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x081a A[EDGE_INSN: B:463:0x081a->B:386:0x081a BREAK  A[LOOP:4: B:287:0x059c->B:385:0x080a], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList;
        PropertyNamingStrategy propertyNamingStrategy2;
        Method[] methodArr;
        Constructor<?> creatorConstructor;
        Field[] fieldArr;
        JSONType jSONType;
        Class<?> cls2;
        int i;
        int length;
        int i2;
        int i3;
        int length2;
        int i4;
        JSONField jSONField;
        String strTranslate;
        Field[] fieldArr2;
        JSONField jSONField2;
        Field[] fieldArr3;
        int i5;
        int i6;
        int i7;
        Method[] methodArr2;
        int i8;
        int i9;
        int i10;
        Method[] methodArr3;
        int i11;
        int iOf;
        int i12;
        String strDecapitalize;
        Field[] fieldArr4;
        Field field;
        boolean z4;
        int i13;
        int i14;
        Field[] fieldArr5;
        int i15;
        JSONField jSONField3;
        PropertyNamingStrategy propertyNamingStrategy3;
        Class<?> cls3;
        Method method;
        int i16;
        int i17;
        Method[] methodArr4;
        String str;
        Field[] fieldArr6;
        JSONType jSONType2;
        Class<?> cls4;
        int i18;
        int i19;
        int i20;
        String str2;
        StringBuilder sb;
        String str3;
        char cCharAt;
        int i21;
        String[] strArr;
        Constructor<?> constructor;
        int i22;
        String[] strArrLookupParameterNames;
        String[] strArrLookupParameterNames2;
        JSONField jSONField4;
        int iOf2;
        int i23;
        int i24;
        JSONField jSONField5;
        String strName;
        int i25;
        int i26;
        int iOf3;
        String str4;
        String[] strArr2;
        JSONField jSONField6;
        String strName2;
        Field field2;
        int iOrdinal;
        int iOf4;
        int iOf5;
        String[] strArr3;
        PropertyNamingStrategy propertyNamingStrategyNaming;
        boolean z5 = z3;
        JSONType jSONType3 = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
        PropertyNamingStrategy propertyNamingStrategy4 = (jSONType3 == null || (propertyNamingStrategyNaming = jSONType3.naming()) == null || propertyNamingStrategyNaming == PropertyNamingStrategy.CamelCase) ? propertyNamingStrategy : propertyNamingStrategyNaming;
        Class<?> builderClass = getBuilderClass(cls, jSONType3);
        Field[] declaredFields = cls.getDeclaredFields();
        Method[] methods = cls.getMethods();
        boolean zIsKotlin = TypeUtils.isKotlin(cls);
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        Constructor<?> defaultConstructor = (!zIsKotlin || declaredConstructors.length == 1) ? builderClass == null ? getDefaultConstructor(cls, declaredConstructors) : getDefaultConstructor(builderClass, builderClass.getDeclaredConstructors()) : null;
        Method method2 = null;
        Method factoryMethod = null;
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
                computeFields(cls, type, propertyNamingStrategy4, arrayList2, superclass.getDeclaredFields());
            }
            return new JavaBeanInfo(cls, builderClass, defaultConstructor, null, null, null, jSONType3, arrayList2);
        }
        boolean z6 = cls.isInterface() || Modifier.isAbstract(cls.getModifiers());
        if ((defaultConstructor == null && builderClass == null) || z6) {
            creatorConstructor = getCreatorConstructor(declaredConstructors);
            if (creatorConstructor == null || z6) {
                arrayList = arrayList2;
                propertyNamingStrategy2 = propertyNamingStrategy4;
                methodArr = methods;
                factoryMethod = getFactoryMethod(cls, methodArr, z5);
                if (factoryMethod != null) {
                    TypeUtils.setAccessible(factoryMethod);
                    Class<?>[] parameterTypes = factoryMethod.getParameterTypes();
                    if (parameterTypes.length > 0) {
                        Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(factoryMethod);
                        String[] strArrLookupParameterNames3 = null;
                        int i27 = 0;
                        while (i27 < parameterTypes.length) {
                            Annotation[] annotationArr = parameterAnnotations[i27];
                            int length3 = annotationArr.length;
                            int i28 = 0;
                            while (true) {
                                if (i28 >= length3) {
                                    jSONField5 = null;
                                    break;
                                }
                                Annotation annotation = annotationArr[i28];
                                if (annotation instanceof JSONField) {
                                    jSONField5 = (JSONField) annotation;
                                    break;
                                }
                                i28++;
                            }
                            if (jSONField5 == null && (!z5 || !TypeUtils.isJacksonCreator(factoryMethod))) {
                                throw new JSONException("illegal json creator");
                            }
                            if (jSONField5 != null) {
                                strName = jSONField5.name();
                                int iOrdinal2 = jSONField5.ordinal();
                                int iOf6 = SerializerFeature.of(jSONField5.serialzeFeatures());
                                iOf3 = Feature.of(jSONField5.parseFeatures());
                                i25 = iOrdinal2;
                                i26 = iOf6;
                            } else {
                                strName = null;
                                i25 = 0;
                                i26 = 0;
                                iOf3 = 0;
                            }
                            if (strName == null || strName.length() == 0) {
                                if (strArrLookupParameterNames3 == null) {
                                    strArrLookupParameterNames3 = ASMUtils.lookupParameterNames(factoryMethod);
                                }
                                str4 = strArrLookupParameterNames3[i27];
                                strArr2 = strArrLookupParameterNames3;
                            } else {
                                strArr2 = strArrLookupParameterNames3;
                                str4 = strName;
                            }
                            add(arrayList, new FieldInfo(str4, cls, parameterTypes[i27], factoryMethod.getGenericParameterTypes()[i27], TypeUtils.getField(cls, str4, declaredFields), i25, i26, iOf3));
                            i27++;
                            parameterTypes = parameterTypes;
                            strArrLookupParameterNames3 = strArr2;
                            z5 = z3;
                        }
                        return new JavaBeanInfo(cls, builderClass, null, null, factoryMethod, null, jSONType3, arrayList);
                    }
                } else if (!z6) {
                    String name = cls.getName();
                    if (!zIsKotlin || declaredConstructors.length <= 0) {
                        int length4 = declaredConstructors.length;
                        String[] strArr4 = null;
                        int i29 = 0;
                        while (true) {
                            if (i29 >= length4) {
                                i21 = 0;
                                strArr = strArr4;
                                break;
                            }
                            constructor = declaredConstructors[i29];
                            Class<?>[] parameterTypes2 = constructor.getParameterTypes();
                            if (name.equals("org.springframework.security.web.authentication.WebAuthenticationDetails") && parameterTypes2.length == 2 && parameterTypes2[0] == String.class && parameterTypes2[1] == String.class) {
                                constructor.setAccessible(true);
                                strArrLookupParameterNames2 = ASMUtils.lookupParameterNames(constructor);
                                break;
                            }
                            if (name.equals("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken") && parameterTypes2.length == 3 && parameterTypes2[0] == Object.class && parameterTypes2[1] == Object.class && parameterTypes2[2] == Collection.class) {
                                constructor.setAccessible(true);
                                strArrLookupParameterNames2 = new String[]{"principal", "credentials", "authorities"};
                                break;
                            }
                            if (name.equals("org.springframework.security.core.authority.SimpleGrantedAuthority")) {
                                i22 = 1;
                                if (parameterTypes2.length == 1) {
                                    i21 = 0;
                                    if (parameterTypes2[0] == String.class) {
                                        strArr = new String[]{"authority"};
                                        creatorConstructor = constructor;
                                        break;
                                    }
                                }
                                if (((constructor.getModifiers() & i22) == 0 ? 1 : i21) != 0 && (strArrLookupParameterNames = ASMUtils.lookupParameterNames(constructor)) != null && strArrLookupParameterNames.length != 0 && (creatorConstructor == null || strArr4 == null || strArrLookupParameterNames.length > strArr4.length)) {
                                    creatorConstructor = constructor;
                                    strArr4 = strArrLookupParameterNames;
                                }
                                i29++;
                            } else {
                                i22 = 1;
                            }
                            i21 = 0;
                            if (((constructor.getModifiers() & i22) == 0 ? 1 : i21) != 0) {
                                creatorConstructor = constructor;
                                strArr4 = strArrLookupParameterNames;
                            }
                            i29++;
                        }
                        strArr = strArrLookupParameterNames2;
                        creatorConstructor = constructor;
                    } else {
                        String[] koltinConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
                        Constructor<?> koltinConstructor = TypeUtils.getKoltinConstructor(declaredConstructors, koltinConstructorParameters);
                        TypeUtils.setAccessible(koltinConstructor);
                        creatorConstructor = koltinConstructor;
                        strArr = koltinConstructorParameters;
                    }
                    i21 = 0;
                    Class<?>[] parameterTypes3 = strArr != null ? creatorConstructor.getParameterTypes() : null;
                    if (strArr == null || parameterTypes3.length != strArr.length) {
                        throw new JSONException("default constructor not found. " + cls);
                    }
                    Annotation[][] parameterAnnotations2 = TypeUtils.getParameterAnnotations(creatorConstructor);
                    int i30 = i21;
                    while (i30 < parameterTypes3.length) {
                        Annotation[] annotationArr2 = parameterAnnotations2[i30];
                        String str5 = strArr[i30];
                        int length5 = annotationArr2.length;
                        int i31 = i21;
                        while (true) {
                            if (i31 >= length5) {
                                jSONField4 = null;
                                break;
                            }
                            Annotation annotation2 = annotationArr2[i31];
                            if (annotation2 instanceof JSONField) {
                                jSONField4 = (JSONField) annotation2;
                                break;
                            }
                            i31++;
                        }
                        Class<?> cls5 = parameterTypes3[i30];
                        Type type2 = creatorConstructor.getGenericParameterTypes()[i30];
                        Field field3 = TypeUtils.getField(cls, str5, declaredFields);
                        if (field3 != null && jSONField4 == null) {
                            jSONField4 = (JSONField) TypeUtils.getAnnotation(field3, JSONField.class);
                        }
                        if (jSONField4 != null) {
                            String strName3 = jSONField4.name();
                            if (strName3.length() != 0) {
                                str5 = strName3;
                            }
                            int iOrdinal3 = jSONField4.ordinal();
                            int iOf7 = SerializerFeature.of(jSONField4.serialzeFeatures());
                            iOf2 = Feature.of(jSONField4.parseFeatures());
                            i23 = iOf7;
                            i24 = iOrdinal3;
                        } else if ("org.springframework.security.core.userdetails.User".equals(name) && "password".equals(str5)) {
                            iOf2 = Feature.InitStringFieldAsEmpty.mask;
                            i24 = 0;
                            i23 = 0;
                        } else {
                            i24 = 0;
                            i23 = 0;
                            iOf2 = 0;
                        }
                        add(arrayList, new FieldInfo(str5, cls, cls5, type2, field3, i24, i23, iOf2));
                        i30++;
                        strArr = strArr;
                        parameterTypes3 = parameterTypes3;
                        name = name;
                        i21 = 0;
                    }
                    if (!zIsKotlin && !cls.getName().equals("javax.servlet.http.Cookie")) {
                        return new JavaBeanInfo(cls, builderClass, null, creatorConstructor, null, null, jSONType3, arrayList);
                    }
                }
            } else {
                TypeUtils.setAccessible(creatorConstructor);
                Class<?>[] parameterTypes4 = creatorConstructor.getParameterTypes();
                if (parameterTypes4.length > 0) {
                    Annotation[][] parameterAnnotations3 = TypeUtils.getParameterAnnotations(creatorConstructor);
                    String[] strArrLookupParameterNames4 = null;
                    int i32 = 0;
                    while (i32 < parameterTypes4.length) {
                        Annotation[] annotationArr3 = parameterAnnotations3[i32];
                        int length6 = annotationArr3.length;
                        int i33 = 0;
                        while (true) {
                            if (i33 >= length6) {
                                jSONField6 = null;
                                break;
                            }
                            Annotation annotation3 = annotationArr3[i33];
                            if (annotation3 instanceof JSONField) {
                                jSONField6 = (JSONField) annotation3;
                                break;
                            }
                            i33++;
                        }
                        Class<?> cls6 = parameterTypes4[i32];
                        Type type3 = creatorConstructor.getGenericParameterTypes()[i32];
                        if (jSONField6 != null) {
                            field2 = TypeUtils.getField(cls, jSONField6.name(), declaredFields);
                            iOrdinal = jSONField6.ordinal();
                            iOf4 = SerializerFeature.of(jSONField6.serialzeFeatures());
                            iOf5 = Feature.of(jSONField6.parseFeatures());
                            strName2 = jSONField6.name();
                        } else {
                            strName2 = null;
                            field2 = null;
                            iOrdinal = 0;
                            iOf4 = 0;
                            iOf5 = 0;
                        }
                        if (strName2 == null || strName2.length() == 0) {
                            if (strArrLookupParameterNames4 == null) {
                                strArrLookupParameterNames4 = ASMUtils.lookupParameterNames(creatorConstructor);
                            }
                            strName2 = strArrLookupParameterNames4[i32];
                        }
                        if (field2 == null) {
                            if (strArrLookupParameterNames4 == null) {
                                strArrLookupParameterNames4 = zIsKotlin ? TypeUtils.getKoltinConstructorParameters(cls) : ASMUtils.lookupParameterNames(creatorConstructor);
                            }
                            Field field4 = field2;
                            if (strArrLookupParameterNames4.length > i32) {
                                field2 = TypeUtils.getField(cls, strArrLookupParameterNames4[i32], declaredFields);
                            } else {
                                strArr3 = strArrLookupParameterNames4;
                                field2 = field4;
                                ArrayList arrayList3 = arrayList2;
                                add(arrayList3, new FieldInfo(strName2, cls, cls6, type3, field2, iOrdinal, iOf4, iOf5));
                                i32++;
                                arrayList2 = arrayList3;
                                methods = methods;
                                strArrLookupParameterNames4 = strArr3;
                                propertyNamingStrategy4 = propertyNamingStrategy4;
                            }
                        }
                        strArr3 = strArrLookupParameterNames4;
                        ArrayList arrayList32 = arrayList2;
                        add(arrayList32, new FieldInfo(strName2, cls, cls6, type3, field2, iOrdinal, iOf4, iOf5));
                        i32++;
                        arrayList2 = arrayList32;
                        methods = methods;
                        strArrLookupParameterNames4 = strArr3;
                        propertyNamingStrategy4 = propertyNamingStrategy4;
                    }
                }
                arrayList = arrayList2;
                propertyNamingStrategy2 = propertyNamingStrategy4;
                methodArr = methods;
            }
        } else {
            arrayList = arrayList2;
            propertyNamingStrategy2 = propertyNamingStrategy4;
            methodArr = methods;
            creatorConstructor = null;
        }
        if (defaultConstructor != null) {
            TypeUtils.setAccessible(defaultConstructor);
        }
        if (builderClass != null) {
            JSONPOJOBuilder jSONPOJOBuilder = (JSONPOJOBuilder) TypeUtils.getAnnotation(builderClass, JSONPOJOBuilder.class);
            String strWithPrefix = jSONPOJOBuilder != null ? jSONPOJOBuilder.withPrefix() : null;
            if (strWithPrefix == null) {
                strWithPrefix = "with";
            }
            String str6 = strWithPrefix;
            Method[] methods2 = builderClass.getMethods();
            int length7 = methods2.length;
            int i34 = 0;
            while (i34 < length7) {
                Method method3 = methods2[i34];
                if (!Modifier.isStatic(method3.getModifiers()) && method3.getReturnType().equals(builderClass)) {
                    JSONField superMethodAnnotation = (JSONField) TypeUtils.getAnnotation(method3, JSONField.class);
                    if (superMethodAnnotation == null) {
                        superMethodAnnotation = TypeUtils.getSuperMethodAnnotation(cls, method3);
                    }
                    JSONField jSONField7 = superMethodAnnotation;
                    if (jSONField7 == null) {
                        i16 = i34;
                        i17 = length7;
                        methodArr4 = methods2;
                        str = str6;
                        fieldArr6 = declaredFields;
                        jSONType2 = jSONType3;
                        cls4 = builderClass;
                        i18 = 0;
                        i19 = 0;
                        i20 = 0;
                    } else if (jSONField7.deserialize()) {
                        int iOrdinal4 = jSONField7.ordinal();
                        int iOf8 = SerializerFeature.of(jSONField7.serialzeFeatures());
                        int iOf9 = Feature.of(jSONField7.parseFeatures());
                        if (jSONField7.name().length() != 0) {
                            i16 = i34;
                            i17 = length7;
                            methodArr4 = methods2;
                            fieldArr6 = declaredFields;
                            jSONType2 = jSONType3;
                            cls4 = builderClass;
                            add(arrayList, new FieldInfo(jSONField7.name(), method3, null, cls, type, iOrdinal4, iOf8, iOf9, jSONField7, null, null));
                            str3 = str6;
                        } else {
                            i16 = i34;
                            i17 = length7;
                            methodArr4 = methods2;
                            str = str6;
                            fieldArr6 = declaredFields;
                            jSONType2 = jSONType3;
                            cls4 = builderClass;
                            i18 = iOrdinal4;
                            i19 = iOf8;
                            i20 = iOf9;
                        }
                    } else {
                        i16 = i34;
                        i17 = length7;
                        methodArr4 = methods2;
                        str3 = str6;
                        fieldArr6 = declaredFields;
                        jSONType2 = jSONType3;
                        cls4 = builderClass;
                    }
                    String name2 = method3.getName();
                    if (name2.startsWith("set") && name2.length() > 3) {
                        sb = new StringBuilder(name2.substring(3));
                    } else if (str.length() == 0) {
                        sb = new StringBuilder(name2);
                    } else {
                        str2 = str;
                        if (name2.startsWith(str2) && name2.length() > str2.length()) {
                            sb = new StringBuilder(name2.substring(str2.length()));
                            cCharAt = sb.charAt(0);
                            if (str2.length() != 0 || Character.isUpperCase(cCharAt)) {
                                sb.setCharAt(0, Character.toLowerCase(cCharAt));
                                str3 = str2;
                                add(arrayList, new FieldInfo(sb.toString(), method3, null, cls, type, i18, i19, i20, jSONField7, null, null));
                            }
                        }
                        str3 = str2;
                    }
                    str2 = str;
                    cCharAt = sb.charAt(0);
                    if (str2.length() != 0) {
                    }
                    sb.setCharAt(0, Character.toLowerCase(cCharAt));
                    str3 = str2;
                    add(arrayList, new FieldInfo(sb.toString(), method3, null, cls, type, i18, i19, i20, jSONField7, null, null));
                }
                i34 = i16 + 1;
                builderClass = cls4;
                str6 = str3;
                length7 = i17;
                methods2 = methodArr4;
                declaredFields = fieldArr6;
                jSONType3 = jSONType2;
            }
            fieldArr = declaredFields;
            jSONType = jSONType3;
            cls2 = builderClass;
            if (cls2 != null) {
                JSONPOJOBuilder jSONPOJOBuilder2 = (JSONPOJOBuilder) TypeUtils.getAnnotation(cls2, JSONPOJOBuilder.class);
                String strBuildMethod = jSONPOJOBuilder2 != null ? jSONPOJOBuilder2.buildMethod() : null;
                if (strBuildMethod == null || strBuildMethod.length() == 0) {
                    strBuildMethod = "build";
                }
                i = 0;
                try {
                    method = cls2.getMethod(strBuildMethod, new Class[0]);
                } catch (NoSuchMethodException | SecurityException unused) {
                    method = null;
                }
                if (method == null) {
                    try {
                        method = cls2.getMethod("create", new Class[0]);
                    } catch (NoSuchMethodException | SecurityException unused2) {
                    }
                }
                if (method == null) {
                    throw new JSONException("buildMethod not found.");
                }
                TypeUtils.setAccessible(method);
                method2 = method;
            }
            length = methodArr.length;
            i2 = i;
            while (true) {
                i3 = 4;
                if (i2 < length) {
                    break;
                }
                Method method4 = methodArr[i2];
                int iOrdinal5 = 0;
                int iOf10 = 0;
                String name3 = method4.getName();
                if (Modifier.isStatic(method4.getModifiers())) {
                    i9 = i2;
                    i10 = length;
                    i14 = i;
                    cls3 = cls2;
                    methodArr3 = methodArr;
                    propertyNamingStrategy3 = propertyNamingStrategy2;
                    fieldArr5 = fieldArr;
                } else {
                    Class<?> returnType = method4.getReturnType();
                    if ((returnType.equals(Void.TYPE) || returnType.equals(method4.getDeclaringClass())) && method4.getDeclaringClass() != Object.class) {
                        Class<?>[] parameterTypes5 = method4.getParameterTypes();
                        if (parameterTypes5.length != 0) {
                            if (parameterTypes5.length > 2) {
                                i9 = i2;
                                i10 = length;
                                i14 = i;
                                cls3 = cls2;
                                methodArr3 = methodArr;
                            } else {
                                JSONField jSONField8 = (JSONField) TypeUtils.getAnnotation(method4, JSONField.class);
                                if (jSONField8 != null && parameterTypes5.length == 2 && parameterTypes5[i] == String.class && parameterTypes5[1] == Object.class) {
                                    i9 = i2;
                                    i10 = length;
                                    methodArr3 = methodArr;
                                    i11 = i;
                                    add(arrayList, new FieldInfo("", method4, null, cls, type, 0, 0, 0, jSONField8, null, null));
                                } else {
                                    i9 = i2;
                                    i10 = length;
                                    methodArr3 = methodArr;
                                    i11 = i;
                                    if (parameterTypes5.length != 1) {
                                        cls3 = cls2;
                                        i14 = i11;
                                        propertyNamingStrategy3 = propertyNamingStrategy2;
                                        fieldArr5 = fieldArr;
                                    } else {
                                        JSONField superMethodAnnotation2 = jSONField8 == null ? TypeUtils.getSuperMethodAnnotation(cls, method4) : jSONField8;
                                        if (superMethodAnnotation2 != null || name3.length() >= 4) {
                                            if (superMethodAnnotation2 == null) {
                                                iOf = 0;
                                            } else if (superMethodAnnotation2.deserialize()) {
                                                iOrdinal5 = superMethodAnnotation2.ordinal();
                                                iOf10 = SerializerFeature.of(superMethodAnnotation2.serialzeFeatures());
                                                iOf = Feature.of(superMethodAnnotation2.parseFeatures());
                                                if (superMethodAnnotation2.name().length() != 0) {
                                                    add(arrayList, new FieldInfo(superMethodAnnotation2.name(), method4, null, cls, type, iOrdinal5, iOf10, iOf, superMethodAnnotation2, null, null));
                                                }
                                                cls3 = cls2;
                                                propertyNamingStrategy3 = propertyNamingStrategy2;
                                                fieldArr5 = fieldArr;
                                                i14 = 0;
                                            }
                                            if ((superMethodAnnotation2 != null || name3.startsWith("set")) && cls2 == null) {
                                                char cCharAt2 = name3.charAt(3);
                                                if (Character.isUpperCase(cCharAt2) || cCharAt2 > 512) {
                                                    i12 = 3;
                                                    strDecapitalize = TypeUtils.compatibleWithJavaBean ? TypeUtils.decapitalize(name3.substring(i12)) : Character.toLowerCase(name3.charAt(3)) + name3.substring(4);
                                                    fieldArr4 = fieldArr;
                                                    field = TypeUtils.getField(cls, strDecapitalize, fieldArr4);
                                                    if (field == null) {
                                                        i13 = 0;
                                                        if (parameterTypes5[0] == Boolean.TYPE) {
                                                            StringBuilder sb2 = new StringBuilder();
                                                            sb2.append("is");
                                                            sb2.append(Character.toUpperCase(strDecapitalize.charAt(0)));
                                                            z4 = true;
                                                            sb2.append(strDecapitalize.substring(1));
                                                            field = TypeUtils.getField(cls, sb2.toString(), fieldArr4);
                                                        } else {
                                                            z4 = true;
                                                        }
                                                    } else {
                                                        z4 = true;
                                                        i13 = 0;
                                                    }
                                                    if (field != null) {
                                                        JSONField jSONField9 = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                                                        if (jSONField9 != null) {
                                                            if (jSONField9.deserialize()) {
                                                                iOrdinal5 = jSONField9.ordinal();
                                                                iOf10 = SerializerFeature.of(jSONField9.serialzeFeatures());
                                                                iOf = Feature.of(jSONField9.parseFeatures());
                                                                if (jSONField9.name().length() != 0) {
                                                                    i14 = i13;
                                                                    fieldArr5 = fieldArr4;
                                                                    add(arrayList, new FieldInfo(jSONField9.name(), method4, field, cls, type, iOrdinal5, iOf10, iOf, superMethodAnnotation2, jSONField9, null));
                                                                }
                                                            } else {
                                                                i14 = i13;
                                                                fieldArr5 = fieldArr4;
                                                            }
                                                            cls3 = cls2;
                                                            propertyNamingStrategy3 = propertyNamingStrategy2;
                                                        }
                                                        i14 = i13;
                                                        fieldArr5 = fieldArr4;
                                                        i15 = iOf;
                                                        jSONField3 = jSONField9;
                                                    } else {
                                                        i14 = i13;
                                                        fieldArr5 = fieldArr4;
                                                        i15 = iOf;
                                                        jSONField3 = null;
                                                    }
                                                    if (propertyNamingStrategy2 != null) {
                                                        propertyNamingStrategy3 = propertyNamingStrategy2;
                                                        strDecapitalize = propertyNamingStrategy3.translate(strDecapitalize);
                                                    } else {
                                                        propertyNamingStrategy3 = propertyNamingStrategy2;
                                                    }
                                                    cls3 = cls2;
                                                    add(arrayList, new FieldInfo(strDecapitalize, method4, field, cls, type, iOrdinal5, iOf10, i15, superMethodAnnotation2, jSONField3, null));
                                                } else if (cCharAt2 == '_') {
                                                    strDecapitalize = name3.substring(4);
                                                    fieldArr4 = fieldArr;
                                                    field = TypeUtils.getField(cls, strDecapitalize, fieldArr4);
                                                    if (field == null) {
                                                    }
                                                    if (field != null) {
                                                    }
                                                    if (propertyNamingStrategy2 != null) {
                                                    }
                                                    cls3 = cls2;
                                                    add(arrayList, new FieldInfo(strDecapitalize, method4, field, cls, type, iOrdinal5, iOf10, i15, superMethodAnnotation2, jSONField3, null));
                                                } else if (cCharAt2 == 'f') {
                                                    strDecapitalize = name3.substring(3);
                                                    fieldArr4 = fieldArr;
                                                    field = TypeUtils.getField(cls, strDecapitalize, fieldArr4);
                                                    if (field == null) {
                                                    }
                                                    if (field != null) {
                                                    }
                                                    if (propertyNamingStrategy2 != null) {
                                                    }
                                                    cls3 = cls2;
                                                    add(arrayList, new FieldInfo(strDecapitalize, method4, field, cls, type, iOrdinal5, iOf10, i15, superMethodAnnotation2, jSONField3, null));
                                                } else {
                                                    i12 = 3;
                                                    if (name3.length() >= 5) {
                                                    }
                                                    cls3 = cls2;
                                                    propertyNamingStrategy3 = propertyNamingStrategy2;
                                                    fieldArr5 = fieldArr;
                                                    i14 = 0;
                                                }
                                            } else {
                                                cls3 = cls2;
                                                propertyNamingStrategy3 = propertyNamingStrategy2;
                                                fieldArr5 = fieldArr;
                                                i14 = 0;
                                            }
                                        }
                                    }
                                }
                                cls3 = cls2;
                                i14 = i11;
                            }
                            propertyNamingStrategy3 = propertyNamingStrategy2;
                            fieldArr5 = fieldArr;
                        }
                    }
                }
                i2 = i9 + 1;
                propertyNamingStrategy2 = propertyNamingStrategy3;
                i = i14;
                length = i10;
                methodArr = methodArr3;
                fieldArr = fieldArr5;
                cls2 = cls3;
            }
            int i35 = i;
            Class<?> cls7 = cls2;
            PropertyNamingStrategy propertyNamingStrategy5 = propertyNamingStrategy2;
            Field[] fieldArr7 = fieldArr;
            int i36 = 3;
            computeFields(cls, type, propertyNamingStrategy5, arrayList, cls.getFields());
            Method[] methods3 = cls.getMethods();
            length2 = methods3.length;
            i4 = i35;
            while (i4 < length2) {
                Method method5 = methods3[i4];
                String name4 = method5.getName();
                if (name4.length() >= i3 && !Modifier.isStatic(method5.getModifiers()) && cls7 == null && name4.startsWith("get") && Character.isUpperCase(name4.charAt(i36)) && method5.getParameterTypes().length == 0 && ((Collection.class.isAssignableFrom(method5.getReturnType()) || Map.class.isAssignableFrom(method5.getReturnType()) || AtomicBoolean.class == method5.getReturnType() || AtomicInteger.class == method5.getReturnType() || AtomicLong.class == method5.getReturnType()) && ((jSONField = (JSONField) TypeUtils.getAnnotation(method5, JSONField.class)) == null || !jSONField.deserialize()))) {
                    if (jSONField == null || jSONField.name().length() <= 0) {
                        strTranslate = Character.toLowerCase(name4.charAt(i36)) + name4.substring(i3);
                        fieldArr2 = fieldArr7;
                        Field field5 = TypeUtils.getField(cls, strTranslate, fieldArr2);
                        if (field5 != null && (jSONField2 = (JSONField) TypeUtils.getAnnotation(field5, JSONField.class)) != null && !jSONField2.deserialize()) {
                            fieldArr3 = fieldArr2;
                            i5 = i4;
                            i6 = i3;
                            i7 = length2;
                            methodArr2 = methods3;
                            i8 = i36;
                        }
                    } else {
                        strTranslate = jSONField.name();
                        fieldArr2 = fieldArr7;
                    }
                    if (propertyNamingStrategy5 != null) {
                        strTranslate = propertyNamingStrategy5.translate(strTranslate);
                    }
                    String str7 = strTranslate;
                    if (getField(arrayList, str7) == null) {
                        fieldArr3 = fieldArr2;
                        i5 = i4;
                        i6 = i3;
                        i7 = length2;
                        methodArr2 = methods3;
                        i8 = i36;
                        add(arrayList, new FieldInfo(str7, method5, null, cls, type, 0, 0, 0, jSONField, null, null));
                    }
                } else {
                    i5 = i4;
                    i6 = i3;
                    i7 = length2;
                    methodArr2 = methods3;
                    i8 = i36;
                    fieldArr3 = fieldArr7;
                }
                i4 = i5 + 1;
                methods3 = methodArr2;
                i3 = i6;
                length2 = i7;
                i36 = i8;
                fieldArr7 = fieldArr3;
            }
            Field[] fieldArr8 = fieldArr7;
            if (arrayList.size() == 0) {
                if (TypeUtils.isXmlField(cls) ? true : z) {
                    for (Class<?> superclass2 = cls; superclass2 != null; superclass2 = superclass2.getSuperclass()) {
                        computeFields(cls, type, propertyNamingStrategy5, arrayList, fieldArr8);
                    }
                }
            }
            return new JavaBeanInfo(cls, cls7, defaultConstructor, creatorConstructor, factoryMethod, method2, jSONType, arrayList);
        }
        fieldArr = declaredFields;
        jSONType = jSONType3;
        cls2 = builderClass;
        i = 0;
        length = methodArr.length;
        i2 = i;
        while (true) {
            i3 = 4;
            if (i2 < length) {
            }
            i2 = i9 + 1;
            propertyNamingStrategy2 = propertyNamingStrategy3;
            i = i14;
            length = i10;
            methodArr = methodArr3;
            fieldArr = fieldArr5;
            cls2 = cls3;
        }
        int i352 = i;
        Class<?> cls72 = cls2;
        PropertyNamingStrategy propertyNamingStrategy52 = propertyNamingStrategy2;
        Field[] fieldArr72 = fieldArr;
        int i362 = 3;
        computeFields(cls, type, propertyNamingStrategy52, arrayList, cls.getFields());
        Method[] methods32 = cls.getMethods();
        length2 = methods32.length;
        i4 = i352;
        while (i4 < length2) {
        }
        Field[] fieldArr82 = fieldArr72;
        if (arrayList.size() == 0) {
        }
        return new JavaBeanInfo(cls, cls72, defaultConstructor, creatorConstructor, factoryMethod, method2, jSONType, arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void computeFields(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, List<FieldInfo> list, Field[] fieldArr) {
        int i;
        int i2;
        int i3;
        for (Field field : fieldArr) {
            int modifiers = field.getModifiers();
            if ((modifiers & 8) == 0) {
                boolean z = true;
                if ((modifiers & 16) != 0) {
                    Class<?> type2 = field.getType();
                    if (Map.class.isAssignableFrom(type2) || Collection.class.isAssignableFrom(type2) || AtomicLong.class.equals(type2) || AtomicInteger.class.equals(type2) || AtomicBoolean.class.equals(type2)) {
                        Iterator<FieldInfo> it = list.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (it.next().name.equals(field.getName())) {
                                    break;
                                }
                            } else {
                                z = false;
                                break;
                            }
                        }
                        if (!z) {
                            String name = field.getName();
                            JSONField jSONField = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                            if (jSONField == null) {
                                i = 0;
                                i2 = 0;
                                i3 = 0;
                            } else if (jSONField.deserialize()) {
                                int iOrdinal = jSONField.ordinal();
                                int iOf = SerializerFeature.of(jSONField.serialzeFeatures());
                                int iOf2 = Feature.of(jSONField.parseFeatures());
                                if (jSONField.name().length() != 0) {
                                    name = jSONField.name();
                                }
                                i = iOrdinal;
                                i2 = iOf;
                                i3 = iOf2;
                            }
                            if (propertyNamingStrategy != null) {
                                name = propertyNamingStrategy.translate(name);
                            }
                            add(list, new FieldInfo(name, null, field, cls, type, i, i2, i3, null, jSONField, null));
                        }
                    }
                }
            }
        }
    }

    public static Class<?> getBuilderClass(JSONType jSONType) {
        return getBuilderClass(null, jSONType);
    }

    public static Class<?> getBuilderClass(Class<?> cls, JSONType jSONType) {
        Class<?> clsBuilder;
        if (cls != null && cls.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (jSONType == null || (clsBuilder = jSONType.builder()) == Void.class) {
            return null;
        }
        return clsBuilder;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructorArr) {
        boolean z;
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) != null) {
                if (constructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                constructor = constructor2;
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructorArr) {
            Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor3);
            if (parameterAnnotations.length != 0) {
                int length = parameterAnnotations.length;
                int i = 0;
                while (true) {
                    z = true;
                    if (i >= length) {
                        break;
                    }
                    Annotation[] annotationArr = parameterAnnotations[i];
                    int length2 = annotationArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            z = false;
                            break;
                        }
                        if (annotationArr[i2] instanceof JSONField) {
                            break;
                        }
                        i2++;
                    }
                    if (!z) {
                        z = false;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    continue;
                } else {
                    if (constructor != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    constructor = constructor3;
                }
            }
        }
        if (constructor != null) {
        }
        return constructor;
    }

    static Constructor<?> getDefaultConstructor(Class<?> cls, Constructor<?>[] constructorArr) {
        Constructor<?> constructor = null;
        if (Modifier.isAbstract(cls.getModifiers())) {
            return null;
        }
        int length = constructorArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor2 = constructorArr[i];
            if (constructor2.getParameterTypes().length == 0) {
                constructor = constructor2;
                break;
            }
            i++;
        }
        if (constructor != null || !cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
            return constructor;
        }
        for (Constructor<?> constructor3 : constructorArr) {
            Class<?>[] parameterTypes = constructor3.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(cls.getDeclaringClass())) {
                return constructor3;
            }
        }
        return constructor;
    }

    private static Method getFactoryMethod(Class<?> cls, Method[] methodArr, boolean z) {
        Method method = null;
        for (Method method2 : methodArr) {
            if (Modifier.isStatic(method2.getModifiers()) && cls.isAssignableFrom(method2.getReturnType()) && ((JSONCreator) TypeUtils.getAnnotation(method2, JSONCreator.class)) != null) {
                if (method != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                method = method2;
            }
        }
        if (method == null && z) {
            for (Method method3 : methodArr) {
                if (TypeUtils.isJacksonCreator(method3)) {
                    return method3;
                }
            }
        }
        return method;
    }

    private static FieldInfo getField(List<FieldInfo> list, String str) {
        for (FieldInfo fieldInfo : list) {
            if (fieldInfo.name.equals(str)) {
                return fieldInfo;
            }
            Field field = fieldInfo.field;
            if (field != null && fieldInfo.getAnnotation() != null && field.getName().equals(str)) {
                return fieldInfo;
            }
        }
        return null;
    }
}
