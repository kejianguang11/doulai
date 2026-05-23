package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.support.moneta.MonetaCodec;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes.dex */
public class SerializeConfig {
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    private long[] denyClasses;
    private final boolean fieldBased;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectSerializer>> mixInSerializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    protected String typeKey;
    public static final SerializeConfig globalInstance = new SerializeConfig();
    private static boolean awtError = false;
    private static boolean jdk8Error = false;
    private static boolean oracleJdbcError = false;
    private static boolean springfoxError = false;
    private static boolean guavaError = false;
    private static boolean jsonnullError = false;
    private static boolean jsonobjectError = false;
    private static boolean jodaError = false;

    public SerializeConfig() {
        this(8192);
    }

    public SerializeConfig(int i) {
        this(i, false);
    }

    public SerializeConfig(int i, boolean z) {
        this.asm = !ASMUtils.IS_ANDROID;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.denyClasses = new long[]{4165360493669296979L, 4446674157046724083L};
        this.modules = new ArrayList();
        this.fieldBased = z;
        this.serializers = new IdentityHashMap<>(i);
        this.mixInSerializers = new IdentityHashMap<>(16);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        } catch (Throwable unused) {
            this.asm = false;
        }
        initSerializers();
    }

    public SerializeConfig(boolean z) {
        this(8192, z);
    }

    private final JavaBeanSerializer createASMSerializer(SerializeBeanInfo serializeBeanInfo) throws Exception {
        JavaBeanSerializer javaBeanSerializerCreateJavaBeanSerializer = this.asmFactory.createJavaBeanSerializer(serializeBeanInfo);
        for (int i = 0; i < javaBeanSerializerCreateJavaBeanSerializer.sortedGetters.length; i++) {
            Class<?> cls = javaBeanSerializerCreateJavaBeanSerializer.sortedGetters[i].fieldInfo.fieldClass;
            if (cls.isEnum() && !(getObjectWriter(cls) instanceof EnumSerializer)) {
                javaBeanSerializerCreateJavaBeanSerializer.writeDirect = false;
            }
        }
        return javaBeanSerializerCreateJavaBeanSerializer;
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    /* JADX WARN: Code restructure failed: missing block: B:270:0x042e, code lost:
    
        if (r21 != false) goto L271;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:196:0x032e A[Catch: ClassNotFoundException -> 0x0345, TryCatch #2 {ClassNotFoundException -> 0x0345, blocks: (B:194:0x031c, B:196:0x032e, B:198:0x0336), top: B:290:0x031c }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x03a7 A[Catch: ClassNotFoundException -> 0x03be, TryCatch #3 {ClassNotFoundException -> 0x03be, blocks: (B:226:0x0389, B:228:0x03a7, B:230:0x03af), top: B:292:0x0389 }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:354:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x011d  */
    /* JADX WARN: Type inference failed for: r19v0, types: [com.alibaba.fastjson.serializer.SerializeConfig] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v100 */
    /* JADX WARN: Type inference failed for: r2v101 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v32 */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v36 */
    /* JADX WARN: Type inference failed for: r2v37 */
    /* JADX WARN: Type inference failed for: r2v38 */
    /* JADX WARN: Type inference failed for: r2v39 */
    /* JADX WARN: Type inference failed for: r2v40 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v49 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v66, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARN: Type inference failed for: r2v68, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v72, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARN: Type inference failed for: r2v73, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARN: Type inference failed for: r2v77 */
    /* JADX WARN: Type inference failed for: r2v78 */
    /* JADX WARN: Type inference failed for: r2v79 */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARN: Type inference failed for: r2v80 */
    /* JADX WARN: Type inference failed for: r2v81 */
    /* JADX WARN: Type inference failed for: r2v82 */
    /* JADX WARN: Type inference failed for: r2v83 */
    /* JADX WARN: Type inference failed for: r2v84 */
    /* JADX WARN: Type inference failed for: r2v85 */
    /* JADX WARN: Type inference failed for: r2v86 */
    /* JADX WARN: Type inference failed for: r2v87 */
    /* JADX WARN: Type inference failed for: r2v88 */
    /* JADX WARN: Type inference failed for: r2v89 */
    /* JADX WARN: Type inference failed for: r2v90 */
    /* JADX WARN: Type inference failed for: r2v91 */
    /* JADX WARN: Type inference failed for: r2v92 */
    /* JADX WARN: Type inference failed for: r2v93 */
    /* JADX WARN: Type inference failed for: r2v94 */
    /* JADX WARN: Type inference failed for: r2v95 */
    /* JADX WARN: Type inference failed for: r2v96 */
    /* JADX WARN: Type inference failed for: r2v97 */
    /* JADX WARN: Type inference failed for: r2v98 */
    /* JADX WARN: Type inference failed for: r2v99 */
    /* JADX WARN: Type inference failed for: r8v15, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ObjectSerializer getObjectWriter(Class<?> cls, boolean z) {
        ?? CreateJavaBeanSerializer;
        ObjectSerializer objectWriter;
        Class<?> cls2;
        SwaggerJsonSerializer swaggerJsonSerializer;
        int i;
        int i2;
        ClassLoader classLoader;
        ObjectSerializer objectSerializer = get(cls);
        ?? CreateSerializer = objectSerializer;
        if (objectSerializer == null) {
            try {
                for (Object obj : ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader())) {
                    if (obj instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowiredObjectSerializer = (AutowiredObjectSerializer) obj;
                        Iterator<Type> it = autowiredObjectSerializer.getAutowiredFor().iterator();
                        while (it.hasNext()) {
                            put(it.next(), autowiredObjectSerializer);
                        }
                    }
                }
            } catch (ClassCastException unused) {
            }
            CreateSerializer = get(cls);
        }
        if (CreateSerializer == 0 && (classLoader = JSON.class.getClassLoader()) != Thread.currentThread().getContextClassLoader()) {
            try {
                for (Object obj2 : ServiceLoader.load(AutowiredObjectSerializer.class, classLoader)) {
                    if (obj2 instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowiredObjectSerializer2 = (AutowiredObjectSerializer) obj2;
                        Iterator<Type> it2 = autowiredObjectSerializer2.getAutowiredFor().iterator();
                        while (it2.hasNext()) {
                            put(it2.next(), autowiredObjectSerializer2);
                        }
                    }
                }
            } catch (ClassCastException unused2) {
            }
            CreateSerializer = get(cls);
        }
        Iterator<Module> it3 = this.modules.iterator();
        while (it3.hasNext()) {
            CreateSerializer = it3.next().createSerializer(this, cls);
            if (CreateSerializer != 0) {
                put(cls, CreateSerializer);
                return CreateSerializer;
            }
        }
        if (CreateSerializer != 0) {
            return CreateSerializer;
        }
        String name = cls.getName();
        if (Map.class.isAssignableFrom(cls)) {
            CreateJavaBeanSerializer = MapSerializer.instance;
        } else if (List.class.isAssignableFrom(cls)) {
            CreateJavaBeanSerializer = ListSerializer.instance;
        } else if (Collection.class.isAssignableFrom(cls)) {
            CreateJavaBeanSerializer = CollectionCodec.instance;
        } else if (Date.class.isAssignableFrom(cls)) {
            CreateJavaBeanSerializer = DateCodec.instance;
        } else if (JSONAware.class.isAssignableFrom(cls)) {
            CreateJavaBeanSerializer = JSONAwareSerializer.instance;
        } else {
            if (!JSONSerializable.class.isAssignableFrom(cls)) {
                if (!JSONStreamAware.class.isAssignableFrom(cls)) {
                    if (cls.isEnum()) {
                        JSONType jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
                        CreateJavaBeanSerializer = (jSONType == null || !jSONType.serializeEnumAsJavaBean()) ? EnumSerializer.instance : createJavaBeanSerializer(cls);
                    } else {
                        Class<? super Object> superclass = cls.getSuperclass();
                        if (superclass != null && superclass.isEnum()) {
                            JSONType jSONType2 = (JSONType) TypeUtils.getAnnotation(superclass, JSONType.class);
                            if (jSONType2 == null || !jSONType2.serializeEnumAsJavaBean()) {
                            }
                        } else if (cls.isArray()) {
                            Class<?> componentType = cls.getComponentType();
                            ArraySerializer arraySerializer = new ArraySerializer(componentType, getObjectWriter(componentType));
                            put(cls, arraySerializer);
                            CreateJavaBeanSerializer = arraySerializer;
                        } else {
                            Class<?> cls3 = null;
                            if (Throwable.class.isAssignableFrom(cls)) {
                                SerializeBeanInfo serializeBeanInfoBuildBeanInfo = TypeUtils.buildBeanInfo(cls, null, this.propertyNamingStrategy);
                                serializeBeanInfoBuildBeanInfo.features |= SerializerFeature.WriteClassName.mask;
                                JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(serializeBeanInfoBuildBeanInfo);
                                put(cls, javaBeanSerializer);
                                CreateJavaBeanSerializer = javaBeanSerializer;
                            } else if (TimeZone.class.isAssignableFrom(cls) || Map.Entry.class.isAssignableFrom(cls)) {
                                CreateJavaBeanSerializer = MiscCodec.instance;
                            } else if (Appendable.class.isAssignableFrom(cls)) {
                                CreateJavaBeanSerializer = AppendableSerializer.instance;
                            } else if (Charset.class.isAssignableFrom(cls)) {
                                CreateJavaBeanSerializer = ToStringSerializer.instance;
                            } else if (Enumeration.class.isAssignableFrom(cls)) {
                                CreateJavaBeanSerializer = EnumerationSerializer.instance;
                            } else if (Calendar.class.isAssignableFrom(cls) || XMLGregorianCalendar.class.isAssignableFrom(cls)) {
                                CreateJavaBeanSerializer = CalendarCodec.instance;
                            } else if (TypeUtils.isClob(cls)) {
                                CreateJavaBeanSerializer = ClobSeriliazer.instance;
                            } else {
                                if (!TypeUtils.isPath(cls)) {
                                    if (!Iterator.class.isAssignableFrom(cls) && !Node.class.isAssignableFrom(cls)) {
                                        int i3 = 0;
                                        if (name.startsWith("java.awt.") && AwtCodec.support(cls) && !awtError) {
                                            try {
                                            } catch (Throwable unused3) {
                                            }
                                            for (String str : new String[]{"java.awt.Color", "java.awt.Font", "java.awt.Point", "java.awt.Rectangle"}) {
                                                if (str.equals(name)) {
                                                    Class<?> cls4 = Class.forName(str);
                                                    AwtCodec awtCodec = AwtCodec.instance;
                                                    try {
                                                        put(cls4, awtCodec);
                                                        return awtCodec;
                                                    } catch (Throwable unused4) {
                                                        CreateSerializer = awtCodec;
                                                    }
                                                }
                                                awtError = true;
                                            }
                                        }
                                        if (!jdk8Error && (name.startsWith("java.time.") || name.startsWith("java.util.Optional") || name.equals("java.util.concurrent.atomic.LongAdder") || name.equals("java.util.concurrent.atomic.DoubleAdder"))) {
                                            try {
                                                String[] strArr = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                                                ?? length = strArr.length;
                                                for (String str2 : strArr) {
                                                    try {
                                                        if (str2.equals(name)) {
                                                            Class<?> cls5 = Class.forName(str2);
                                                            Jdk8DateCodec jdk8DateCodec = Jdk8DateCodec.instance;
                                                            put(cls5, jdk8DateCodec);
                                                            return jdk8DateCodec;
                                                        }
                                                    } catch (Throwable unused5) {
                                                        CreateSerializer = length;
                                                        jdk8Error = true;
                                                    }
                                                }
                                                for (String str3 : new String[]{"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"}) {
                                                    if (str3.equals(name)) {
                                                        Class<?> cls6 = Class.forName(str3);
                                                        OptionalCodec optionalCodec = OptionalCodec.instance;
                                                        put(cls6, optionalCodec);
                                                        return optionalCodec;
                                                    }
                                                }
                                                for (String str4 : new String[]{"java.util.concurrent.atomic.LongAdder", "java.util.concurrent.atomic.DoubleAdder"}) {
                                                    if (str4.equals(name)) {
                                                        Class<?> cls7 = Class.forName(str4);
                                                        AdderSerializer adderSerializer = AdderSerializer.instance;
                                                        put(cls7, adderSerializer);
                                                        return adderSerializer;
                                                    }
                                                }
                                            } catch (Throwable unused6) {
                                            }
                                        }
                                        if (!oracleJdbcError && name.startsWith("oracle.sql.")) {
                                            try {
                                            } catch (Throwable unused7) {
                                            }
                                            for (String str5 : new String[]{"oracle.sql.DATE", "oracle.sql.TIMESTAMP"}) {
                                                if (str5.equals(name)) {
                                                    Class<?> cls8 = Class.forName(str5);
                                                    DateCodec dateCodec = DateCodec.instance;
                                                    try {
                                                        put(cls8, dateCodec);
                                                        return dateCodec;
                                                    } catch (Throwable unused8) {
                                                        CreateSerializer = dateCodec;
                                                    }
                                                }
                                                oracleJdbcError = true;
                                            }
                                        }
                                        ?? r2 = CreateSerializer;
                                        if (!springfoxError) {
                                            r2 = CreateSerializer;
                                            if (name.equals("springfox.documentation.spring.web.json.Json")) {
                                                try {
                                                    cls2 = Class.forName("springfox.documentation.spring.web.json.Json");
                                                    swaggerJsonSerializer = SwaggerJsonSerializer.instance;
                                                } catch (ClassNotFoundException unused9) {
                                                }
                                                try {
                                                    put(cls2, swaggerJsonSerializer);
                                                    return swaggerJsonSerializer;
                                                } catch (ClassNotFoundException unused10) {
                                                    CreateSerializer = swaggerJsonSerializer;
                                                    springfoxError = true;
                                                    r2 = CreateSerializer;
                                                    if (!guavaError) {
                                                        try {
                                                            while (i < r8) {
                                                            }
                                                        } catch (ClassNotFoundException unused11) {
                                                        }
                                                    }
                                                    ?? r22 = r2;
                                                    if (!jsonnullError) {
                                                    }
                                                    CreateJavaBeanSerializer = r22;
                                                    if (!jsonobjectError) {
                                                    }
                                                    if (!jodaError) {
                                                    }
                                                    if (!"java.nio.HeapByteBuffer".equals(name)) {
                                                    }
                                                    put(cls, objectWriter);
                                                    return objectWriter;
                                                }
                                            }
                                        }
                                        if (!guavaError && name.startsWith("com.google.common.collect.")) {
                                            for (String str6 : new String[]{"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.LinkedHashMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"}) {
                                                if (str6.equals(name)) {
                                                    Class<?> cls9 = Class.forName(str6);
                                                    GuavaCodec guavaCodec = GuavaCodec.instance;
                                                    try {
                                                        put(cls9, guavaCodec);
                                                        return guavaCodec;
                                                    } catch (ClassNotFoundException unused12) {
                                                        r2 = guavaCodec;
                                                    }
                                                }
                                                guavaError = true;
                                            }
                                        }
                                        ?? r222 = r2;
                                        if (!jsonnullError) {
                                            r222 = r2;
                                            if (name.equals("net.sf.json.JSONNull")) {
                                                try {
                                                    Class<?> cls10 = Class.forName("net.sf.json.JSONNull");
                                                    MiscCodec miscCodec = MiscCodec.instance;
                                                    try {
                                                        put(cls10, miscCodec);
                                                        return miscCodec;
                                                    } catch (ClassNotFoundException unused13) {
                                                        r2 = miscCodec;
                                                        jsonnullError = true;
                                                        r222 = r2;
                                                        CreateJavaBeanSerializer = r222;
                                                        if (!jsonobjectError) {
                                                        }
                                                        if (!jodaError) {
                                                            try {
                                                                while (i < r8) {
                                                                }
                                                            } catch (ClassNotFoundException unused14) {
                                                            }
                                                        }
                                                        if (!"java.nio.HeapByteBuffer".equals(name)) {
                                                        }
                                                        put(cls, objectWriter);
                                                        return objectWriter;
                                                    }
                                                } catch (ClassNotFoundException unused15) {
                                                }
                                            }
                                        }
                                        CreateJavaBeanSerializer = r222;
                                        if (!jsonobjectError) {
                                            CreateJavaBeanSerializer = r222;
                                            if (name.equals("org.json.JSONObject")) {
                                                try {
                                                    Class<?> cls11 = Class.forName("org.json.JSONObject");
                                                    JSONObjectCodec jSONObjectCodec = JSONObjectCodec.instance;
                                                    try {
                                                        put(cls11, jSONObjectCodec);
                                                        return jSONObjectCodec;
                                                    } catch (ClassNotFoundException unused16) {
                                                        r222 = jSONObjectCodec;
                                                        jsonobjectError = true;
                                                        CreateJavaBeanSerializer = r222;
                                                        if (!jodaError) {
                                                        }
                                                        if (!"java.nio.HeapByteBuffer".equals(name)) {
                                                        }
                                                        put(cls, objectWriter);
                                                        return objectWriter;
                                                    }
                                                } catch (ClassNotFoundException unused17) {
                                                }
                                            }
                                        }
                                        if (!jodaError && name.startsWith("org.joda.")) {
                                            for (String str7 : new String[]{"org.joda.time.LocalDate", "org.joda.time.LocalDateTime", "org.joda.time.LocalTime", "org.joda.time.Instant", "org.joda.time.DateTime", "org.joda.time.Period", "org.joda.time.Duration", "org.joda.time.DateTimeZone", "org.joda.time.UTCDateTimeZone", "org.joda.time.tz.CachedDateTimeZone", "org.joda.time.tz.FixedDateTimeZone"}) {
                                                if (str7.equals(name)) {
                                                    Class<?> cls12 = Class.forName(str7);
                                                    JodaCodec jodaCodec = JodaCodec.instance;
                                                    try {
                                                        put(cls12, jodaCodec);
                                                        return jodaCodec;
                                                    } catch (ClassNotFoundException unused18) {
                                                        CreateJavaBeanSerializer = jodaCodec;
                                                    }
                                                }
                                                jodaError = true;
                                            }
                                        }
                                        if (!"java.nio.HeapByteBuffer".equals(name)) {
                                            objectWriter = ByteBufferCodec.instance;
                                        } else if ("org.javamoney.moneta.Money".equals(name)) {
                                            objectWriter = MonetaCodec.instance;
                                        } else {
                                            Class<?>[] interfaces = cls.getInterfaces();
                                            if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
                                                put(cls, AnnotationSerializer.instance);
                                                return AnnotationSerializer.instance;
                                            }
                                            if (TypeUtils.isProxy(cls)) {
                                                objectWriter = getObjectWriter(cls.getSuperclass());
                                            } else if (Proxy.isProxyClass(cls)) {
                                                if (interfaces.length != 2) {
                                                    int length2 = interfaces.length;
                                                    Class<?> cls13 = null;
                                                    while (true) {
                                                        if (i3 >= length2) {
                                                            cls3 = cls13;
                                                            break;
                                                        }
                                                        Class<?> cls14 = interfaces[i3];
                                                        if (!cls14.getName().startsWith("org.springframework.aop.")) {
                                                            if (cls13 != null) {
                                                                break;
                                                            }
                                                            cls13 = cls14;
                                                        }
                                                        i3++;
                                                    }
                                                } else {
                                                    cls3 = interfaces[1];
                                                }
                                                if (cls3 != null) {
                                                    objectWriter = getObjectWriter(cls3);
                                                }
                                            }
                                        }
                                        put(cls, objectWriter);
                                        return objectWriter;
                                    }
                                    CreateJavaBeanSerializer = MiscCodec.instance;
                                }
                                CreateJavaBeanSerializer = ToStringSerializer.instance;
                            }
                        }
                    }
                }
                return CreateJavaBeanSerializer != 0 ? get(cls) : CreateJavaBeanSerializer;
            }
            CreateJavaBeanSerializer = JSONSerializableSerializer.instance;
        }
        put(cls, CreateJavaBeanSerializer);
        if (CreateJavaBeanSerializer != 0) {
        }
    }

    private void initSerializers() {
        put(Boolean.class, BooleanCodec.instance);
        put(Character.class, CharacterCodec.instance);
        put(Byte.class, IntegerCodec.instance);
        put(Short.class, IntegerCodec.instance);
        put(Integer.class, IntegerCodec.instance);
        put(Long.class, LongCodec.instance);
        put(Float.class, FloatCodec.instance);
        put(Double.class, DoubleSerializer.instance);
        put(BigDecimal.class, BigDecimalCodec.instance);
        put(BigInteger.class, BigIntegerCodec.instance);
        put(String.class, StringCodec.instance);
        put(byte[].class, PrimitiveArraySerializer.instance);
        put(short[].class, PrimitiveArraySerializer.instance);
        put(int[].class, PrimitiveArraySerializer.instance);
        put(long[].class, PrimitiveArraySerializer.instance);
        put(float[].class, PrimitiveArraySerializer.instance);
        put(double[].class, PrimitiveArraySerializer.instance);
        put(boolean[].class, PrimitiveArraySerializer.instance);
        put(char[].class, PrimitiveArraySerializer.instance);
        put(Object[].class, ObjectArrayCodec.instance);
        put(Class.class, MiscCodec.instance);
        put(SimpleDateFormat.class, MiscCodec.instance);
        put(Currency.class, new MiscCodec());
        put(TimeZone.class, MiscCodec.instance);
        put(InetAddress.class, MiscCodec.instance);
        put(Inet4Address.class, MiscCodec.instance);
        put(Inet6Address.class, MiscCodec.instance);
        put(InetSocketAddress.class, MiscCodec.instance);
        put(File.class, MiscCodec.instance);
        put(Appendable.class, AppendableSerializer.instance);
        put(StringBuffer.class, AppendableSerializer.instance);
        put(StringBuilder.class, AppendableSerializer.instance);
        put(Charset.class, ToStringSerializer.instance);
        put(Pattern.class, ToStringSerializer.instance);
        put(Locale.class, ToStringSerializer.instance);
        put(URI.class, ToStringSerializer.instance);
        put(URL.class, ToStringSerializer.instance);
        put(UUID.class, ToStringSerializer.instance);
        put(AtomicBoolean.class, AtomicCodec.instance);
        put(AtomicInteger.class, AtomicCodec.instance);
        put(AtomicLong.class, AtomicCodec.instance);
        put(AtomicReference.class, ReferenceCodec.instance);
        put(AtomicIntegerArray.class, AtomicCodec.instance);
        put(AtomicLongArray.class, AtomicCodec.instance);
        put(WeakReference.class, ReferenceCodec.instance);
        put(SoftReference.class, ReferenceCodec.instance);
        put(LinkedList.class, CollectionCodec.instance);
    }

    public void addFilter(Class<?> cls, SerializeFilter serializeFilter) {
        Object objectWriter = getObjectWriter(cls);
        if (objectWriter instanceof SerializeFilterable) {
            SerializeFilterable serializeFilterable = (SerializeFilterable) objectWriter;
            if (this == globalInstance || serializeFilterable != MapSerializer.instance) {
                serializeFilterable.addFilter(serializeFilter);
                return;
            }
            MapSerializer mapSerializer = new MapSerializer();
            put(cls, mapSerializer);
            mapSerializer.addFilter(serializeFilter);
        }
    }

    public void clearSerializers() {
        this.serializers.clear();
        initSerializers();
    }

    public void config(Class<?> cls, SerializerFeature serializerFeature, boolean z) {
        int i;
        int i2;
        ObjectSerializer objectWriter = getObjectWriter(cls, false);
        if (objectWriter == null) {
            SerializeBeanInfo serializeBeanInfoBuildBeanInfo = TypeUtils.buildBeanInfo(cls, null, this.propertyNamingStrategy);
            if (z) {
                i2 = serializerFeature.mask | serializeBeanInfoBuildBeanInfo.features;
            } else {
                i2 = (~serializerFeature.mask) & serializeBeanInfoBuildBeanInfo.features;
            }
            serializeBeanInfoBuildBeanInfo.features = i2;
            put(cls, createJavaBeanSerializer(serializeBeanInfoBuildBeanInfo));
            return;
        }
        if (objectWriter instanceof JavaBeanSerializer) {
            SerializeBeanInfo serializeBeanInfo = ((JavaBeanSerializer) objectWriter).beanInfo;
            int i3 = serializeBeanInfo.features;
            if (z) {
                i = serializerFeature.mask | serializeBeanInfo.features;
            } else {
                i = (~serializerFeature.mask) & serializeBeanInfo.features;
            }
            serializeBeanInfo.features = i;
            if (i3 == serializeBeanInfo.features || objectWriter.getClass() == JavaBeanSerializer.class) {
                return;
            }
            put(cls, createJavaBeanSerializer(serializeBeanInfo));
        }
    }

    public void configEnumAsJavaBean(Class<? extends Enum>... clsArr) {
        for (Class<? extends Enum> cls : clsArr) {
            put(cls, createJavaBeanSerializer(cls));
        }
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        Method method;
        JSONType jSONType = serializeBeanInfo.jsonType;
        boolean z = false;
        boolean z2 = this.asm && !this.fieldBased;
        if (jSONType != null) {
            Class<?> clsSerializer = jSONType.serializer();
            if (clsSerializer != Void.class) {
                try {
                    Object objNewInstance = clsSerializer.newInstance();
                    if (objNewInstance instanceof ObjectSerializer) {
                        return (ObjectSerializer) objNewInstance;
                    }
                } catch (Throwable unused) {
                }
            }
            if (!jSONType.asm()) {
                z2 = false;
            }
            if (z2) {
                for (SerializerFeature serializerFeature : jSONType.serialzeFeatures()) {
                    if (SerializerFeature.WriteNonStringValueAsString == serializerFeature || SerializerFeature.WriteEnumUsingToString == serializerFeature || SerializerFeature.NotWriteDefaultValue == serializerFeature || SerializerFeature.BrowserCompatible == serializerFeature) {
                        z2 = false;
                        break;
                    }
                }
            }
            if (z2 && jSONType.serialzeFilters().length != 0) {
                z2 = false;
            }
        }
        Class<?> cls = serializeBeanInfo.beanType;
        if (!Modifier.isPublic(serializeBeanInfo.beanType.getModifiers())) {
            return new JavaBeanSerializer(serializeBeanInfo);
        }
        if ((z2 && this.asmFactory.classLoader.isExternalClass(cls)) || cls == Serializable.class || cls == Object.class) {
            z2 = false;
        }
        if (z2 && !ASMUtils.checkName(cls.getSimpleName())) {
            z2 = false;
        }
        if (z2 && serializeBeanInfo.beanType.isInterface()) {
            z2 = false;
        }
        if (z2) {
            FieldInfo[] fieldInfoArr = serializeBeanInfo.fields;
            int length = fieldInfoArr.length;
            boolean z3 = z2;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = z3;
                    break;
                }
                FieldInfo fieldInfo = fieldInfoArr[i];
                Field field = fieldInfo.field;
                if ((field != null && !field.getType().equals(fieldInfo.fieldClass)) || ((method = fieldInfo.method) != null && !method.getReturnType().equals(fieldInfo.fieldClass))) {
                    break;
                }
                JSONField annotation = fieldInfo.getAnnotation();
                if (annotation != null) {
                    String str = annotation.format();
                    if ((str.length() != 0 && (fieldInfo.fieldClass != String.class || !"trim".equals(str))) || !ASMUtils.checkName(annotation.name()) || annotation.jsonDirect() || annotation.serializeUsing() != Void.class || annotation.unwrapped()) {
                        break;
                    }
                    for (SerializerFeature serializerFeature2 : annotation.serialzeFeatures()) {
                        if (SerializerFeature.WriteNonStringValueAsString == serializerFeature2 || SerializerFeature.WriteEnumUsingToString == serializerFeature2 || SerializerFeature.NotWriteDefaultValue == serializerFeature2 || SerializerFeature.BrowserCompatible == serializerFeature2 || SerializerFeature.WriteClassName == serializerFeature2) {
                            z3 = false;
                            break;
                        }
                    }
                    if (TypeUtils.isAnnotationPresentOneToMany(method) || TypeUtils.isAnnotationPresentManyToMany(method)) {
                        break;
                    }
                }
                i++;
            }
        } else {
            z = z2;
        }
        if (z) {
            try {
                JavaBeanSerializer javaBeanSerializerCreateASMSerializer = createASMSerializer(serializeBeanInfo);
                if (javaBeanSerializerCreateASMSerializer != null) {
                    return javaBeanSerializerCreateASMSerializer;
                }
            } catch (ClassCastException | ClassFormatError | ClassNotFoundException unused2) {
            } catch (OutOfMemoryError e) {
                if (e.getMessage().indexOf("Metaspace") != -1) {
                    throw e;
                }
            } catch (Throwable th) {
                throw new JSONException("create asm serializer error, verson 1.2.60, class " + cls, th);
            }
        }
        return new JavaBeanSerializer(serializeBeanInfo);
    }

    public final ObjectSerializer createJavaBeanSerializer(Class<?> cls) {
        String name = cls.getName();
        if (Arrays.binarySearch(this.denyClasses, TypeUtils.fnv1a_64(name)) < 0) {
            SerializeBeanInfo serializeBeanInfoBuildBeanInfo = TypeUtils.buildBeanInfo(cls, null, this.propertyNamingStrategy, this.fieldBased);
            return (serializeBeanInfoBuildBeanInfo.fields.length == 0 && Iterable.class.isAssignableFrom(cls)) ? MiscCodec.instance : createJavaBeanSerializer(serializeBeanInfoBuildBeanInfo);
        }
        throw new JSONException("not support class : " + name);
    }

    public final ObjectSerializer get(Type type) {
        ObjectSerializer objectSerializer;
        Type mixInAnnotations = JSON.getMixInAnnotations(type);
        if (mixInAnnotations == null) {
            objectSerializer = this.serializers.get(type);
        } else {
            IdentityHashMap<Type, ObjectSerializer> identityHashMap = this.mixInSerializers.get(type);
            if (identityHashMap == null) {
                return null;
            }
            objectSerializer = identityHashMap.get(mixInAnnotations);
        }
        return objectSerializer;
    }

    public ObjectSerializer getObjectWriter(Class<?> cls) {
        return getObjectWriter(cls, true);
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public boolean isAsmEnable() {
        return this.asm;
    }

    public boolean put(Type type, ObjectSerializer objectSerializer) {
        Type mixInAnnotations = JSON.getMixInAnnotations(type);
        if (mixInAnnotations == null) {
            return this.serializers.put(type, objectSerializer);
        }
        IdentityHashMap<Type, ObjectSerializer> identityHashMap = this.mixInSerializers.get(type);
        if (identityHashMap == null) {
            identityHashMap = new IdentityHashMap<>(4);
            this.mixInSerializers.put(type, identityHashMap);
        }
        return identityHashMap.put(mixInAnnotations, objectSerializer);
    }

    public void register(Module module) {
        this.modules.add(module);
    }

    public void setAsmEnable(boolean z) {
        if (ASMUtils.IS_ANDROID) {
            return;
        }
        this.asm = z;
    }

    public void setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
    }

    public void setTypeKey(String str) {
        this.typeKey = str;
    }
}
