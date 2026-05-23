package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) {
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        HashMap map = null;
        int i = 0;
        while (i < length) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i];
            FieldDeserializer fieldDeserializerCreateFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializerCreateFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializerCreateFieldDeserializer);
            }
            HashMap map2 = map;
            for (String str : fieldInfo.alternateNames) {
                if (map2 == null) {
                    map2 = new HashMap();
                }
                map2.put(str, fieldDeserializerCreateFieldDeserializer);
            }
            i++;
            map = map2;
        }
        this.alterNameFieldDeserializers = map;
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    static boolean isSetFlag(int i, int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int i2 = i / 32;
        int i3 = i % 32;
        if (i2 < iArr.length) {
            if (((1 << i3) & iArr[i2]) != 0) {
                return true;
            }
        }
        return false;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i = jSONLexerBase.token();
        if (i == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i != 14) {
            defaultJSONParser.throwException(i);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i2 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i2)));
            i2++;
            if (jSONLexerBase.token() != 16) {
                break;
            }
            if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i3 = jSONLexerBase.token();
        if (i3 != 15) {
            defaultJSONParser.throwException(i3);
        }
        if (jSONLexerBase.getCurrent() != ',') {
            jSONLexerBase.nextToken(16);
        } else {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
        }
    }

    protected void check(JSONLexer jSONLexer, int i) {
        if (jSONLexer.token() != i) {
            throw new JSONException("syntax error");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) {
        Object[] objArr;
        ParseContext parseContext;
        Object objNewInstance;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize != 0) {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (!(type instanceof Class)) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                String name = ((Class) type).getName();
                String strSubstring = name.substring(0, name.lastIndexOf(36));
                Object obj = context.object;
                String name2 = obj.getClass().getName();
                if (!name2.equals(strSubstring) && (parseContext = context.parent) != null && parseContext.object != null && ("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                    obj = parseContext.object.getClass().getName().equals(strSubstring) ? parseContext.object : null;
                }
                if (obj == null || ((obj instanceof Collection) && ((Collection) obj).isEmpty())) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                objArr = new Object[]{obj};
            } else {
                if (constructor == null) {
                    objNewInstance = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
                    if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                        for (FieldInfo fieldInfo : this.beanInfo.fields) {
                            if (fieldInfo.fieldClass == String.class) {
                                try {
                                    fieldInfo.set(objNewInstance, "");
                                } catch (Exception e) {
                                    throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                                }
                            }
                        }
                    }
                    return objNewInstance;
                }
                objArr = new Object[0];
            }
            objNewInstance = constructor.newInstance(objArr);
            if (defaultJSONParser != null) {
                while (i < r0) {
                }
            }
            return objNewInstance;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    public Object createInstance(Map<String, Object> map, ParserConfig parserConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        Integer num;
        float fFloatValue;
        double dDoubleValue;
        boolean z = true;
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.factoryMethod == null) {
            Object objCreateInstance = createInstance((DefaultJSONParser) null, this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                FieldDeserializer fieldDeserializerSmartMatch = smartMatch(key);
                if (fieldDeserializerSmartMatch != null) {
                    FieldInfo fieldInfo = fieldDeserializerSmartMatch.fieldInfo;
                    Field field = fieldDeserializerSmartMatch.fieldInfo.field;
                    Type type = fieldInfo.fieldType;
                    if (field != null) {
                        Class<?> type2 = field.getType();
                        if (type2 == Boolean.TYPE) {
                            if (value == Boolean.FALSE) {
                                field.setBoolean(objCreateInstance, false);
                            } else if (value == Boolean.TRUE) {
                                field.setBoolean(objCreateInstance, true);
                            }
                        } else if (type2 == Integer.TYPE) {
                            if (value instanceof Number) {
                                field.setInt(objCreateInstance, ((Number) value).intValue());
                            }
                        } else if (type2 == Long.TYPE) {
                            if (value instanceof Number) {
                                field.setLong(objCreateInstance, ((Number) value).longValue());
                            }
                        } else if (type2 == Float.TYPE) {
                            if (value instanceof Number) {
                                fFloatValue = ((Number) value).floatValue();
                            } else if (value instanceof String) {
                                String str = (String) value;
                                fFloatValue = str.length() <= 10 ? TypeUtils.parseFloat(str) : Float.parseFloat(str);
                            }
                            field.setFloat(objCreateInstance, fFloatValue);
                        } else if (type2 == Double.TYPE) {
                            if (value instanceof Number) {
                                dDoubleValue = ((Number) value).doubleValue();
                            } else if (value instanceof String) {
                                String str2 = (String) value;
                                dDoubleValue = str2.length() <= 10 ? TypeUtils.parseDouble(str2) : Double.parseDouble(str2);
                            }
                            field.setDouble(objCreateInstance, dDoubleValue);
                        } else if (value != null && type == value.getClass()) {
                            field.set(objCreateInstance, value);
                        }
                    }
                    String str3 = fieldInfo.format;
                    fieldDeserializerSmartMatch.setValue(objCreateInstance, (str3 == null || type != Date.class) ? type instanceof ParameterizedType ? TypeUtils.cast(value, (ParameterizedType) type, parserConfig) : TypeUtils.cast(value, type, parserConfig) : TypeUtils.castToDate(value, str3));
                }
            }
            if (this.beanInfo.buildMethod == null) {
                return objCreateInstance;
            }
            try {
                return this.beanInfo.buildMethod.invoke(objCreateInstance, new Object[0]);
            } catch (Exception e) {
                throw new JSONException("build object error", e);
            }
        }
        FieldInfo[] fieldInfoArr = this.beanInfo.fields;
        int length = fieldInfoArr.length;
        Object[] objArr = new Object[length];
        HashMap map2 = null;
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo2 = fieldInfoArr[i];
            Object objValueOf = map.get(fieldInfo2.name);
            if (objValueOf == null) {
                Class<?> cls = fieldInfo2.fieldClass;
                if (cls == Integer.TYPE) {
                    objValueOf = 0;
                } else if (cls == Long.TYPE) {
                    objValueOf = 0L;
                } else if (cls == Short.TYPE) {
                    objValueOf = (short) 0;
                } else if (cls == Byte.TYPE) {
                    objValueOf = (byte) 0;
                } else if (cls == Float.TYPE) {
                    objValueOf = Float.valueOf(0.0f);
                } else if (cls == Double.TYPE) {
                    objValueOf = Double.valueOf(0.0d);
                } else if (cls == Character.TYPE) {
                    objValueOf = '0';
                } else if (cls == Boolean.TYPE) {
                    objValueOf = false;
                }
                if (map2 == null) {
                    map2 = new HashMap();
                }
                map2.put(fieldInfo2.name, Integer.valueOf(i));
            }
            objArr[i] = objValueOf;
        }
        if (map2 != null) {
            for (Map.Entry<String, Object> entry2 : map.entrySet()) {
                String key2 = entry2.getKey();
                Object value2 = entry2.getValue();
                FieldDeserializer fieldDeserializerSmartMatch2 = smartMatch(key2);
                if (fieldDeserializerSmartMatch2 != null && (num = (Integer) map2.get(fieldDeserializerSmartMatch2.fieldInfo.name)) != null) {
                    objArr[num.intValue()] = value2;
                }
            }
        }
        if (this.beanInfo.creatorConstructor == null) {
            if (this.beanInfo.factoryMethod == null) {
                return null;
            }
            try {
                return this.beanInfo.factoryMethod.invoke(null, objArr);
            } catch (Exception e2) {
                throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e2);
            }
        }
        if (this.beanInfo.kotlin) {
            int i2 = 0;
            while (true) {
                if (i2 >= objArr.length) {
                    break;
                }
                if (objArr[i2] != null || this.beanInfo.fields == null || i2 >= this.beanInfo.fields.length) {
                    i2++;
                } else if (this.beanInfo.fields[i2].fieldClass != String.class) {
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        if (!z || this.beanInfo.kotlinDefaultConstructor == null) {
            try {
                objNewInstance = this.beanInfo.creatorConstructor.newInstance(objArr);
            } catch (Exception e3) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e3);
            }
        } else {
            try {
                objNewInstance = this.beanInfo.kotlinDefaultConstructor.newInstance(new Object[0]);
                for (int i3 = 0; i3 < objArr.length; i3++) {
                    Object obj = objArr[i3];
                    if (obj != null && this.beanInfo.fields != null && i3 < this.beanInfo.fields.length) {
                        this.beanInfo.fields[i3].set(objNewInstance, obj);
                    }
                }
            } catch (Exception e4) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e4);
            }
        }
        return objNewInstance;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:293:0x037f, code lost:
    
        if (r12.matchStat == (-2)) goto L294;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x051e, code lost:
    
        r2 = getSeeAlso(r13, r37.beanInfo, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x0524, code lost:
    
        if (r2 != null) goto L404;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0526, code lost:
    
        r2 = r13.checkAutoType(r1, com.alibaba.fastjson.util.TypeUtils.getClass(r39), r12.getFeatures());
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x053a, code lost:
    
        r3 = r2;
        r2 = r38.getConfig().getDeserializer(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x0540, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0541, code lost:
    
        r3 = (T) r2.deserialze(r38, r3, r40);
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x0547, code lost:
    
        if ((r2 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x0549, code lost:
    
        r2 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x054b, code lost:
    
        if (r15 == null) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x054d, code lost:
    
        r2.getFieldDeserializer(r15).setValue((java.lang.Object) r3, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0554, code lost:
    
        if (r5 == null) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0556, code lost:
    
        r5.object = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x055a, code lost:
    
        r38.setContext(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x055d, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x056b, code lost:
    
        r15 = r5;
        r35 = r7;
        r2 = r17;
        r33 = 0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:489:0x069f, code lost:
    
        r12.nextToken();
        r20 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x06d2, code lost:
    
        r12.nextToken(16);
        r20 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:695:0x09a6, code lost:
    
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r12.token()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:257:0x031c A[Catch: all -> 0x0391, TryCatch #22 {all -> 0x0391, blocks: (B:321:0x03e4, B:323:0x03ee, B:326:0x03fb, B:334:0x0414, B:336:0x041d, B:338:0x0429, B:339:0x042d, B:341:0x0435, B:343:0x043b, B:345:0x0440, B:346:0x0449, B:348:0x044e, B:351:0x0457, B:353:0x045b, B:354:0x045e, B:356:0x0462, B:357:0x0465, B:358:0x046f, B:360:0x0477, B:361:0x047d, B:363:0x0483, B:365:0x0489, B:366:0x048f, B:367:0x0495, B:368:0x0499, B:371:0x04a1, B:383:0x04ce, B:384:0x04e8, B:386:0x04eb, B:402:0x0526, B:407:0x0549, B:409:0x054d, B:144:0x01f7, B:151:0x0205, B:156:0x0213, B:294:0x0381, B:163:0x0222, B:165:0x0226, B:168:0x022f, B:173:0x0239, B:176:0x0242, B:181:0x024c, B:184:0x0255, B:187:0x025b, B:192:0x0265, B:197:0x026f, B:202:0x0279, B:204:0x027f, B:207:0x028d, B:209:0x0295, B:211:0x0299, B:214:0x02a9, B:221:0x02ba, B:224:0x02c4, B:229:0x02cf, B:232:0x02d8, B:237:0x02e3, B:240:0x02ec, B:243:0x02f3, B:247:0x02ff, B:249:0x0307, B:254:0x0316, B:257:0x031c, B:253:0x0312, B:261:0x0326, B:263:0x0330, B:267:0x033b, B:270:0x0340, B:266:0x0337, B:273:0x0346, B:277:0x0357, B:280:0x035c, B:276:0x0353, B:283:0x0362, B:285:0x036c, B:289:0x0377, B:292:0x037c, B:288:0x0373, B:297:0x0393, B:299:0x039b, B:304:0x03a7, B:307:0x03ae, B:303:0x03a3), top: B:754:0x03e4 }] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x03ae A[Catch: all -> 0x0391, TRY_LEAVE, TryCatch #22 {all -> 0x0391, blocks: (B:321:0x03e4, B:323:0x03ee, B:326:0x03fb, B:334:0x0414, B:336:0x041d, B:338:0x0429, B:339:0x042d, B:341:0x0435, B:343:0x043b, B:345:0x0440, B:346:0x0449, B:348:0x044e, B:351:0x0457, B:353:0x045b, B:354:0x045e, B:356:0x0462, B:357:0x0465, B:358:0x046f, B:360:0x0477, B:361:0x047d, B:363:0x0483, B:365:0x0489, B:366:0x048f, B:367:0x0495, B:368:0x0499, B:371:0x04a1, B:383:0x04ce, B:384:0x04e8, B:386:0x04eb, B:402:0x0526, B:407:0x0549, B:409:0x054d, B:144:0x01f7, B:151:0x0205, B:156:0x0213, B:294:0x0381, B:163:0x0222, B:165:0x0226, B:168:0x022f, B:173:0x0239, B:176:0x0242, B:181:0x024c, B:184:0x0255, B:187:0x025b, B:192:0x0265, B:197:0x026f, B:202:0x0279, B:204:0x027f, B:207:0x028d, B:209:0x0295, B:211:0x0299, B:214:0x02a9, B:221:0x02ba, B:224:0x02c4, B:229:0x02cf, B:232:0x02d8, B:237:0x02e3, B:240:0x02ec, B:243:0x02f3, B:247:0x02ff, B:249:0x0307, B:254:0x0316, B:257:0x031c, B:253:0x0312, B:261:0x0326, B:263:0x0330, B:267:0x033b, B:270:0x0340, B:266:0x0337, B:273:0x0346, B:277:0x0357, B:280:0x035c, B:276:0x0353, B:283:0x0362, B:285:0x036c, B:289:0x0377, B:292:0x037c, B:288:0x0373, B:297:0x0393, B:299:0x039b, B:304:0x03a7, B:307:0x03ae, B:303:0x03a3), top: B:754:0x03e4 }] */
    /* JADX WARN: Removed duplicated region for block: B:427:0x059b  */
    /* JADX WARN: Removed duplicated region for block: B:429:0x05a6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:450:0x05ed  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x066e  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:500:0x06cc A[Catch: all -> 0x09b3, TryCatch #6 {all -> 0x09b3, blocks: (B:497:0x06bf, B:500:0x06cc, B:502:0x06d2, B:485:0x0693, B:495:0x06b7), top: B:726:0x06bf }] */
    /* JADX WARN: Removed duplicated region for block: B:710:0x09cc  */
    /* JADX WARN: Removed duplicated region for block: B:743:0x08a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:757:0x03da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v167, types: [int] */
    /* JADX WARN: Type inference failed for: r1v173 */
    /* JADX WARN: Type inference failed for: r1v174 */
    /* JADX WARN: Type inference failed for: r1v175 */
    /* JADX WARN: Type inference failed for: r1v176 */
    /* JADX WARN: Type inference failed for: r1v177 */
    /* JADX WARN: Type inference failed for: r1v178 */
    /* JADX WARN: Type inference failed for: r1v179 */
    /* JADX WARN: Type inference failed for: r1v53 */
    /* JADX WARN: Type inference failed for: r1v54 */
    /* JADX WARN: Type inference failed for: r1v55 */
    /* JADX WARN: Type inference failed for: r1v56, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v60, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v66 */
    /* JADX WARN: Type inference failed for: r1v67 */
    /* JADX WARN: Type inference failed for: r38v0, types: [com.alibaba.fastjson.parser.DefaultJSONParser] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v34, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v36 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v43 */
    /* JADX WARN: Type inference failed for: r6v46 */
    /* JADX WARN: Type inference failed for: r6v47, types: [int] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v50, types: [java.lang.Class<?>] */
    /* JADX WARN: Type inference failed for: r6v58 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v65 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v83 */
    /* JADX WARN: Type inference failed for: r6v84 */
    /* JADX WARN: Type inference failed for: r6v85 */
    /* JADX WARN: Type inference failed for: r6v86 */
    /* JADX WARN: Type inference failed for: r6v87 */
    /* JADX WARN: Type inference failed for: r6v88 */
    /* JADX WARN: Type inference failed for: r6v89 */
    /* JADX WARN: Type inference failed for: r6v90 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) throws Throwable {
        ?? r6;
        Throwable th;
        ParseContext parseContext;
        ParseContext parseContext2;
        ?? r62;
        int i2;
        String str;
        Object obj3;
        int[] iArr2;
        int i3;
        int i4;
        HashMap map;
        ?? r63;
        int i5;
        boolean z;
        FieldDeserializer fieldDeserializer;
        Class<?> cls;
        FieldInfo fieldInfo;
        JSONField annotation;
        Object obj4;
        Object obj5;
        int[] iArr3;
        Class<?> cls2;
        Object obj6;
        Object objValueOf;
        boolean z2;
        boolean z3;
        Object obj7;
        Throwable th2;
        Object obj8;
        int i6;
        int i7;
        int i8;
        Object obj9;
        int i9;
        char c;
        Object obj10;
        Object obj11;
        ParseContext parseContext3;
        Object obj12;
        Object obj13;
        Throwable th3;
        Object obj14;
        Object obj15;
        String strScanSymbol;
        Object obj16;
        Object obj17;
        ParseContext parseContext4;
        byte b;
        int i10;
        T t;
        Object obj18;
        Object obj19;
        Object[] objArr;
        boolean z4;
        ?? r64;
        Object objNewInstance;
        byte b2;
        Object obj20;
        Object obj21;
        int i11;
        Object obj22;
        Object obj23;
        Object obj24;
        Object obj25;
        char[] cArr;
        char c2;
        Object objScanFieldBigInteger;
        Object obj26;
        Object obj27;
        Object obj28;
        Class<?> cls3;
        if (type == JSON.class || type == JSONObject.class) {
            return (T) defaultJSONParser.parse();
        }
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        ParserConfig config = defaultJSONParser.getConfig();
        int i12 = jSONLexerBase.token();
        int i13 = 16;
        ParseContext context = null;
        if (i12 == 8) {
            jSONLexerBase.nextToken(16);
            return null;
        }
        ParseContext context2 = defaultJSONParser.getContext();
        if (obj2 != null && context2 != null) {
            context2 = context2.parent;
        }
        ParseContext parseContext5 = context2;
        try {
        } catch (Throwable th4) {
            r6 = obj2;
            th = th4;
            parseContext = parseContext5;
        }
        if (i12 == 13) {
            jSONLexerBase.nextToken(16);
            T t2 = obj2 == null ? (T) createInstance((DefaultJSONParser) defaultJSONParser, type) : (T) obj2;
            defaultJSONParser.setContext(parseContext5);
            return t2;
        }
        if (i12 == 14) {
            int i14 = Feature.SupportArrayToBean.mask;
            if (((this.beanInfo.parserFeatures & i14) == 0 && !jSONLexerBase.isEnabled(Feature.SupportArrayToBean) && (i & i14) == 0) ? false : true) {
                T t3 = (T) deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
                defaultJSONParser.setContext(parseContext5);
                return t3;
            }
        }
        try {
        } catch (Throwable th5) {
            r62 = obj2;
            th = th5;
            parseContext = parseContext5;
            parseContext2 = null;
        }
        if (i12 != 12 && i12 != 16) {
            if (jSONLexerBase.isBlankInput()) {
                defaultJSONParser.setContext(parseContext5);
                return null;
            }
            if (i12 == 4) {
                String strStringVal = jSONLexerBase.stringVal();
                if (strStringVal.length() == 0) {
                    jSONLexerBase.nextToken();
                    defaultJSONParser.setContext(parseContext5);
                    return null;
                }
                if (this.beanInfo.jsonType != null) {
                    for (Class<?> cls4 : this.beanInfo.jsonType.seeAlso()) {
                        if (Enum.class.isAssignableFrom(cls4)) {
                            try {
                                T t4 = (T) Enum.valueOf(cls4, strStringVal);
                                defaultJSONParser.setContext(parseContext5);
                                return t4;
                            } catch (IllegalArgumentException unused) {
                                continue;
                            }
                        }
                    }
                }
            } else if (i12 == 5) {
                jSONLexerBase.getCalendar();
            }
            if (i12 == 14 && jSONLexerBase.getCurrent() == ']') {
                jSONLexerBase.next();
                jSONLexerBase.nextToken();
                defaultJSONParser.setContext(parseContext5);
                return null;
            }
            if (this.beanInfo.factoryMethod != null && this.beanInfo.fields.length == 1) {
                try {
                    FieldInfo fieldInfo2 = this.beanInfo.fields[0];
                    if (fieldInfo2.fieldClass == Integer.class) {
                        if (i12 == 2) {
                            int iIntValue = jSONLexerBase.intValue();
                            jSONLexerBase.nextToken();
                            T t5 = (T) createFactoryInstance(config, Integer.valueOf(iIntValue));
                            defaultJSONParser.setContext(parseContext5);
                            return t5;
                        }
                    } else if (fieldInfo2.fieldClass == String.class && i12 == 4) {
                        String strStringVal2 = jSONLexerBase.stringVal();
                        jSONLexerBase.nextToken();
                        T t6 = (T) createFactoryInstance(config, strStringVal2);
                        defaultJSONParser.setContext(parseContext5);
                        return t6;
                    }
                } catch (Exception e) {
                    throw new JSONException(e.getMessage(), e);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("syntax error, expect {, actual ");
            sb.append(jSONLexerBase.tokenName());
            sb.append(", pos ");
            sb.append(jSONLexerBase.pos());
            if (obj instanceof String) {
                sb.append(", fieldName ");
                sb.append(obj);
            }
            sb.append(", fastjson-version ");
            sb.append(JSON.VERSION);
            throw new JSONException(sb.toString());
        }
        try {
            if (defaultJSONParser.resolveStatus == 2) {
                i2 = 0;
                defaultJSONParser.resolveStatus = 0;
            } else {
                i2 = 0;
            }
            str = this.beanInfo.typeKey;
            obj3 = obj2;
            iArr2 = iArr;
            i3 = i2;
            i4 = i3;
            parseContext2 = null;
            map = null;
        } catch (Throwable th6) {
            parseContext = parseContext5;
            r62 = obj2;
            th = th6;
            parseContext2 = null;
        }
        while (true) {
            try {
                if (i4 >= this.sortedFieldDeserializers.length || i3 >= i13) {
                    i5 = i4;
                    z = false;
                    fieldDeserializer = null;
                    cls = null;
                    fieldInfo = null;
                    annotation = null;
                } else {
                    try {
                        FieldDeserializer fieldDeserializer2 = this.sortedFieldDeserializers[i4];
                        fieldInfo = fieldDeserializer2.fieldInfo;
                        i5 = i4;
                        Class<?> cls5 = fieldInfo.fieldClass;
                        annotation = fieldInfo.getAnnotation();
                        if (annotation != null) {
                            cls3 = cls5;
                            if (fieldDeserializer2 instanceof DefaultFieldDeserializer) {
                                z = ((DefaultFieldDeserializer) fieldDeserializer2).customDeserilizer;
                                cls = cls3;
                                fieldDeserializer = fieldDeserializer2;
                            }
                        } else {
                            cls3 = cls5;
                        }
                        cls = cls3;
                        z = false;
                        fieldDeserializer = fieldDeserializer2;
                    } catch (Throwable th7) {
                        th = th7;
                    }
                }
            } catch (Throwable th8) {
                th = th8;
                parseContext = parseContext5;
                r63 = obj3;
            }
            if (fieldDeserializer != null) {
                iArr3 = iArr2;
                try {
                    cArr = fieldInfo.name_chars;
                } catch (Throwable th9) {
                    th2 = th9;
                    obj7 = obj3;
                    th = th2;
                    parseContext = parseContext5;
                    r62 = obj7;
                    if (parseContext2 != null) {
                    }
                    defaultJSONParser.setContext(parseContext);
                    throw th;
                }
                if (z && jSONLexerBase.matchField(cArr)) {
                    obj28 = obj3;
                } else {
                    Object obj29 = obj3;
                    if (cls != Integer.TYPE && cls != Integer.class) {
                        if (cls == Long.TYPE || cls == Long.class) {
                            cls2 = cls;
                            long jScanFieldLong = jSONLexerBase.scanFieldLong(cArr);
                            objValueOf = (jScanFieldLong == 0 && jSONLexerBase.matchStat == 5) ? null : Long.valueOf(jScanFieldLong);
                            if (jSONLexerBase.matchStat > 0) {
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                    obj16 = objValueOf;
                                    i9 = i3;
                                    obj3 = obj15;
                                    strScanSymbol = null;
                                } else {
                                    try {
                                        strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                        if (strScanSymbol == null) {
                                            i9 = i3;
                                            try {
                                                int i15 = jSONLexerBase.token();
                                                obj16 = objValueOf;
                                                if (i15 == 13) {
                                                    jSONLexerBase.nextToken(16);
                                                    obj17 = obj15;
                                                    break;
                                                }
                                                if (i15 == 16) {
                                                    obj11 = obj15;
                                                    if (jSONLexerBase.isEnabled(Feature.AllowArbitraryCommas)) {
                                                        obj10 = obj11;
                                                        c = '\r';
                                                    }
                                                    obj9 = obj10;
                                                    parseContext = parseContext5;
                                                    i7 = i5;
                                                    i6 = i9;
                                                    i8 = 16;
                                                    obj8 = obj9;
                                                    obj13 = obj13;
                                                    obj14 = obj8;
                                                    try {
                                                        i13 = i8;
                                                        i4 = i7 + 1;
                                                        iArr2 = iArr3;
                                                        obj3 = obj14;
                                                        parseContext5 = parseContext;
                                                        i3 = i6;
                                                    } catch (Throwable th10) {
                                                        th3 = th10;
                                                    }
                                                }
                                            } catch (Throwable th11) {
                                                th2 = th11;
                                                obj7 = obj15;
                                                th = th2;
                                                parseContext = parseContext5;
                                                r62 = obj7;
                                            }
                                        } else {
                                            obj16 = objValueOf;
                                            i9 = i3;
                                        }
                                        if ("$ref" == strScanSymbol && parseContext5 != null) {
                                            jSONLexerBase.nextTokenWithColon(4);
                                            int i16 = jSONLexerBase.token();
                                            if (i16 != 4) {
                                                throw new JSONException("illegal ref, " + JSONToken.name(i16));
                                            }
                                            String strStringVal3 = jSONLexerBase.stringVal();
                                            if ("@".equals(strStringVal3)) {
                                                t = (T) parseContext5.object;
                                            } else {
                                                if ("..".equals(strStringVal3)) {
                                                    ParseContext parseContext6 = parseContext5.parent;
                                                    if (parseContext6.object != null) {
                                                        obj18 = parseContext6.object;
                                                        obj19 = obj18;
                                                    } else {
                                                        defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(parseContext6, strStringVal3));
                                                        i10 = 1;
                                                        defaultJSONParser.resolveStatus = i10;
                                                        obj19 = obj15;
                                                    }
                                                } else {
                                                    if ("$".equals(strStringVal3)) {
                                                        ParseContext parseContext7 = parseContext5;
                                                        while (parseContext7.parent != null) {
                                                            parseContext7 = parseContext7.parent;
                                                        }
                                                        if (parseContext7.object != null) {
                                                            obj18 = parseContext7.object;
                                                            obj19 = obj18;
                                                        } else {
                                                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(parseContext7, strStringVal3));
                                                            i10 = 1;
                                                        }
                                                    } else {
                                                        if (strStringVal3.indexOf(92) > 0) {
                                                            StringBuilder sb2 = new StringBuilder();
                                                            int i17 = 0;
                                                            while (i17 < strStringVal3.length()) {
                                                                char cCharAt = strStringVal3.charAt(i17);
                                                                if (cCharAt == '\\') {
                                                                    i17++;
                                                                    cCharAt = strStringVal3.charAt(i17);
                                                                }
                                                                sb2.append(cCharAt);
                                                                i17++;
                                                            }
                                                            strStringVal3 = sb2.toString();
                                                        }
                                                        Object objResolveReference = defaultJSONParser.resolveReference(strStringVal3);
                                                        if (objResolveReference != null) {
                                                            t = (T) objResolveReference;
                                                        } else {
                                                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(parseContext5, strStringVal3));
                                                            i10 = 1;
                                                        }
                                                    }
                                                    defaultJSONParser.resolveStatus = i10;
                                                    obj19 = obj15;
                                                }
                                                t = (T) obj19;
                                            }
                                            jSONLexerBase.nextToken(13);
                                            if (jSONLexerBase.token() != 13) {
                                                throw new JSONException("illegal ref");
                                            }
                                            jSONLexerBase.nextToken(16);
                                            defaultJSONParser.setContext(parseContext5, t, obj);
                                            if (parseContext2 != null) {
                                                parseContext2.object = t;
                                            }
                                            defaultJSONParser.setContext(parseContext5);
                                            return t;
                                        }
                                        if ((str == null || !str.equals(strScanSymbol)) && JSON.DEFAULT_TYPE_KEY != strScanSymbol) {
                                            obj3 = obj15;
                                        }
                                        jSONLexerBase.nextTokenWithColon(4);
                                        if (jSONLexerBase.token() != 4) {
                                            throw new JSONException("syntax error");
                                        }
                                        String strStringVal4 = jSONLexerBase.stringVal();
                                        jSONLexerBase.nextToken(16);
                                        if (!strStringVal4.equals(this.beanInfo.typeName) && !defaultJSONParser.isEnabled(Feature.IgnoreAutoType)) {
                                            break;
                                        }
                                        Object obj30 = obj15;
                                        c = '\r';
                                        obj10 = obj30;
                                        if (jSONLexerBase.token() == 13) {
                                            jSONLexerBase.nextToken();
                                            obj17 = obj30;
                                            break;
                                        }
                                        obj9 = obj10;
                                        parseContext = parseContext5;
                                        i7 = i5;
                                        i6 = i9;
                                        i8 = 16;
                                        obj8 = obj9;
                                        obj13 = obj13;
                                        obj14 = obj8;
                                        i13 = i8;
                                        i4 = i7 + 1;
                                        iArr2 = iArr3;
                                        obj3 = obj14;
                                        parseContext5 = parseContext;
                                        i3 = i6;
                                    } catch (Throwable th12) {
                                        th = th12;
                                        obj3 = obj15;
                                    }
                                    obj4 = obj3;
                                    parseContext = parseContext5;
                                    r63 = obj4;
                                    th = th;
                                    r62 = r63;
                                }
                                if (obj3 == null && map == null) {
                                    try {
                                        Object objCreateInstance = createInstance((DefaultJSONParser) defaultJSONParser, type);
                                        if (objCreateInstance == null) {
                                            try {
                                                parseContext3 = parseContext2;
                                                try {
                                                    map = new HashMap(this.fieldDeserializers.length);
                                                } catch (Throwable th13) {
                                                    th = th13;
                                                    obj21 = objCreateInstance;
                                                    parseContext = parseContext5;
                                                    obj12 = obj21;
                                                    parseContext2 = parseContext3;
                                                    r62 = obj12;
                                                    if (parseContext2 != null) {
                                                    }
                                                    defaultJSONParser.setContext(parseContext);
                                                    throw th;
                                                }
                                            } catch (Throwable th14) {
                                                th = th14;
                                                r62 = objCreateInstance;
                                                parseContext = parseContext5;
                                            }
                                        } else {
                                            parseContext3 = parseContext2;
                                        }
                                        ParseContext context3 = defaultJSONParser.setContext(parseContext5, objCreateInstance, obj);
                                        if (iArr3 == null) {
                                            try {
                                                parseContext3 = context3;
                                                iArr3 = new int[(this.fieldDeserializers.length / 32) + 1];
                                            } catch (Throwable th15) {
                                                th = th15;
                                                parseContext2 = context3;
                                                obj4 = objCreateInstance;
                                                parseContext = parseContext5;
                                                r63 = obj4;
                                            }
                                        } else {
                                            parseContext3 = context3;
                                        }
                                        obj20 = objCreateInstance;
                                    } catch (Throwable th16) {
                                        th = th16;
                                        obj4 = obj3;
                                    }
                                } else {
                                    parseContext3 = parseContext2;
                                    obj20 = obj3;
                                }
                                HashMap map2 = map;
                                if (z2) {
                                    map = map2;
                                    i6 = i9;
                                    String str2 = strScanSymbol;
                                    i7 = i5;
                                    Object obj31 = obj20;
                                    b = 0;
                                    i11 = 13;
                                    parseContext4 = parseContext5;
                                    if (parseField(defaultJSONParser, str2, obj20, type, map, iArr3)) {
                                        obj13 = obj31;
                                        if (jSONLexerBase.token() == 17) {
                                            throw new JSONException("syntax error, unexpect token ':'");
                                        }
                                        i8 = 16;
                                        if (jSONLexerBase.token() != 16) {
                                        }
                                        th = th3;
                                        obj12 = obj13;
                                        parseContext2 = parseContext3;
                                        r62 = obj12;
                                    } else {
                                        try {
                                            if (jSONLexerBase.token() == 13) {
                                                break;
                                            }
                                            parseContext = parseContext4;
                                            i8 = 16;
                                            obj24 = obj31;
                                            obj23 = obj24;
                                            obj14 = obj23;
                                            parseContext2 = parseContext3;
                                            obj13 = obj23;
                                            i13 = i8;
                                            i4 = i7 + 1;
                                            iArr2 = iArr3;
                                            obj3 = obj14;
                                            parseContext5 = parseContext;
                                            i3 = i6;
                                        } catch (Throwable th17) {
                                            th = th17;
                                            r62 = obj31;
                                            parseContext2 = parseContext3;
                                            parseContext = parseContext4;
                                        }
                                    }
                                } else {
                                    if (z3) {
                                        Class<?> cls6 = cls2;
                                        if (obj20 == null) {
                                            map2.put(fieldInfo.name, obj16);
                                        } else {
                                            Object obj32 = obj16;
                                            if (obj32 != null || (cls6 != Integer.TYPE && cls6 != Long.TYPE && cls6 != Float.TYPE && cls6 != Double.TYPE && cls6 != Boolean.TYPE)) {
                                                fieldDeserializer.setValue(obj20, obj32);
                                            }
                                        }
                                        if (iArr3 != null) {
                                            int i18 = i5 / 32;
                                            iArr3[i18] = (1 << (i5 % 32)) | iArr3[i18];
                                        }
                                        if (jSONLexerBase.matchStat == 4) {
                                            map = map2;
                                            obj22 = obj20;
                                            parseContext4 = parseContext5;
                                            b = 0;
                                            break;
                                        }
                                        map = map2;
                                        obj25 = obj20;
                                        parseContext4 = parseContext5;
                                        i7 = i5;
                                        i6 = i9;
                                        i11 = 13;
                                    } else {
                                        try {
                                            fieldDeserializer.parseField(defaultJSONParser, obj20, type, map2);
                                            map = map2;
                                            obj25 = obj20;
                                            parseContext4 = parseContext5;
                                            i7 = i5;
                                            i6 = i9;
                                            i11 = 13;
                                        } catch (Throwable th18) {
                                            th = th18;
                                            obj21 = obj20;
                                            parseContext = parseContext5;
                                            obj12 = obj21;
                                            parseContext2 = parseContext3;
                                            r62 = obj12;
                                            if (parseContext2 != null) {
                                            }
                                            defaultJSONParser.setContext(parseContext);
                                            throw th;
                                        }
                                    }
                                    b = 0;
                                    obj13 = obj25;
                                    try {
                                        i8 = 16;
                                        if (jSONLexerBase.token() != 16) {
                                            parseContext = parseContext4;
                                            obj24 = obj13;
                                            obj23 = obj24;
                                            obj14 = obj23;
                                            parseContext2 = parseContext3;
                                            obj13 = obj23;
                                            i13 = i8;
                                            i4 = i7 + 1;
                                            iArr2 = iArr3;
                                            obj3 = obj14;
                                            parseContext5 = parseContext;
                                            i3 = i6;
                                        } else {
                                            if (jSONLexerBase.token() == i11) {
                                                break;
                                            }
                                            parseContext = parseContext4;
                                            if (jSONLexerBase.token() == 18) {
                                                break;
                                            }
                                            obj23 = obj13;
                                            if (jSONLexerBase.token() == 1) {
                                                break;
                                            }
                                            obj14 = obj23;
                                            parseContext2 = parseContext3;
                                            obj13 = obj23;
                                            i13 = i8;
                                            i4 = i7 + 1;
                                            iArr2 = iArr3;
                                            obj3 = obj14;
                                            parseContext5 = parseContext;
                                            i3 = i6;
                                        }
                                    } catch (Throwable th19) {
                                        th3 = th19;
                                        parseContext = parseContext4;
                                    }
                                    th = th3;
                                    obj12 = obj13;
                                    parseContext2 = parseContext3;
                                    r62 = obj12;
                                }
                            }
                        } else if (cls == String.class) {
                            objScanFieldBigInteger = jSONLexerBase.scanFieldString(cArr);
                            if (jSONLexerBase.matchStat <= 0) {
                                if (jSONLexerBase.matchStat == -2) {
                                    i6 = i3 + 1;
                                    parseContext = parseContext5;
                                    i7 = i5;
                                    i8 = 16;
                                    obj9 = obj29;
                                    obj8 = obj9;
                                    obj13 = obj13;
                                    obj14 = obj8;
                                    i13 = i8;
                                    i4 = i7 + 1;
                                    iArr2 = iArr3;
                                    obj3 = obj14;
                                    parseContext5 = parseContext;
                                    i3 = i6;
                                }
                                objValueOf = objScanFieldBigInteger;
                                cls2 = cls;
                                z2 = false;
                                obj27 = obj29;
                                z3 = false;
                                obj26 = obj27;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                    parseContext3 = parseContext2;
                                    obj20 = obj3;
                                    HashMap map22 = map;
                                    if (z2) {
                                    }
                                }
                            }
                            objValueOf = objScanFieldBigInteger;
                            cls2 = cls;
                            z2 = true;
                            z3 = true;
                            obj26 = obj29;
                            obj15 = obj26;
                            if (z2) {
                            }
                            if (obj3 == null) {
                            }
                        } else if (cls == Date.class && fieldInfo.format == null) {
                            objScanFieldBigInteger = jSONLexerBase.scanFieldDate(cArr);
                            if (jSONLexerBase.matchStat > 0) {
                                objValueOf = objScanFieldBigInteger;
                                cls2 = cls;
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            } else {
                                if (jSONLexerBase.matchStat == -2) {
                                    i6 = i3 + 1;
                                    parseContext = parseContext5;
                                    i7 = i5;
                                    i8 = 16;
                                    obj9 = obj29;
                                    obj8 = obj9;
                                    obj13 = obj13;
                                    obj14 = obj8;
                                    i13 = i8;
                                    i4 = i7 + 1;
                                    iArr2 = iArr3;
                                    obj3 = obj14;
                                    parseContext5 = parseContext;
                                    i3 = i6;
                                }
                                objValueOf = objScanFieldBigInteger;
                                cls2 = cls;
                                z2 = false;
                                obj27 = obj29;
                                z3 = false;
                                obj26 = obj27;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            }
                        } else if (cls == BigDecimal.class) {
                            objScanFieldBigInteger = jSONLexerBase.scanFieldDecimal(cArr);
                            if (jSONLexerBase.matchStat > 0) {
                                objValueOf = objScanFieldBigInteger;
                                cls2 = cls;
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            } else {
                                if (jSONLexerBase.matchStat == -2) {
                                    i6 = i3 + 1;
                                    parseContext = parseContext5;
                                    i7 = i5;
                                    i8 = 16;
                                    obj9 = obj29;
                                    obj8 = obj9;
                                    obj13 = obj13;
                                    obj14 = obj8;
                                    i13 = i8;
                                    i4 = i7 + 1;
                                    iArr2 = iArr3;
                                    obj3 = obj14;
                                    parseContext5 = parseContext;
                                    i3 = i6;
                                }
                                objValueOf = objScanFieldBigInteger;
                                cls2 = cls;
                                z2 = false;
                                obj27 = obj29;
                                z3 = false;
                                obj26 = obj27;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            }
                        } else {
                            if (cls == BigInteger.class) {
                                objScanFieldBigInteger = jSONLexerBase.scanFieldBigInteger(cArr);
                                if (jSONLexerBase.matchStat > 0) {
                                    objValueOf = objScanFieldBigInteger;
                                    cls2 = cls;
                                    z2 = true;
                                    z3 = true;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                } else {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    objValueOf = objScanFieldBigInteger;
                                    cls2 = cls;
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                            } else if (cls == Boolean.TYPE || cls == Boolean.class) {
                                cls2 = cls;
                                objValueOf = jSONLexerBase.matchStat == 5 ? null : Boolean.valueOf(jSONLexerBase.scanFieldBoolean(cArr));
                                if (jSONLexerBase.matchStat > 0) {
                                    z2 = true;
                                    z3 = true;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                } else {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                            } else if (cls == Float.TYPE || cls == Float.class) {
                                cls2 = cls;
                                float fScanFieldFloat = jSONLexerBase.scanFieldFloat(cArr);
                                objValueOf = (fScanFieldFloat == 0.0f && jSONLexerBase.matchStat == 5) ? null : Float.valueOf(fScanFieldFloat);
                                if (jSONLexerBase.matchStat > 0) {
                                    z2 = true;
                                    z3 = true;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                } else {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                            } else if (cls == Double.TYPE || cls == Double.class) {
                                double dScanFieldDouble = jSONLexerBase.scanFieldDouble(cArr);
                                if (dScanFieldDouble == 0.0d) {
                                    cls2 = cls;
                                    if (jSONLexerBase.matchStat == 5) {
                                        objValueOf = null;
                                    }
                                    if (jSONLexerBase.matchStat <= 0) {
                                        if (jSONLexerBase.matchStat == -2) {
                                        }
                                        z2 = false;
                                        obj27 = obj29;
                                        z3 = false;
                                        obj26 = obj27;
                                        obj15 = obj26;
                                        if (z2) {
                                        }
                                        if (obj3 == null) {
                                        }
                                    }
                                    z2 = true;
                                    z3 = true;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                } else {
                                    cls2 = cls;
                                }
                                objValueOf = Double.valueOf(dScanFieldDouble);
                                if (jSONLexerBase.matchStat <= 0) {
                                }
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            } else if (cls.isEnum() && (defaultJSONParser.getConfig().getDeserializer(cls) instanceof EnumDeserializer) && (annotation == null || annotation.deserializeUsing() == Void.class)) {
                                obj5 = obj29;
                                if (fieldDeserializer instanceof DefaultFieldDeserializer) {
                                    objValueOf = scanEnum(jSONLexerBase, cArr, ((DefaultFieldDeserializer) fieldDeserializer).fieldValueDeserilizer);
                                    if (jSONLexerBase.matchStat > 0) {
                                        z2 = true;
                                        z3 = true;
                                    } else if (jSONLexerBase.matchStat != -2) {
                                        z2 = false;
                                        z3 = false;
                                    }
                                    cls2 = cls;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                            } else if (cls == int[].class) {
                                objValueOf = jSONLexerBase.scanFieldIntArray(cArr);
                                if (jSONLexerBase.matchStat <= 0) {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    cls2 = cls;
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                                cls2 = cls;
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            } else if (cls == float[].class) {
                                objValueOf = jSONLexerBase.scanFieldFloatArray(cArr);
                                if (jSONLexerBase.matchStat <= 0) {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    cls2 = cls;
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                                cls2 = cls;
                                z2 = true;
                                z3 = true;
                                obj26 = obj29;
                                obj15 = obj26;
                                if (z2) {
                                }
                                if (obj3 == null) {
                                }
                            } else if (cls == float[][].class) {
                                objValueOf = jSONLexerBase.scanFieldFloatArray2(cArr);
                                if (jSONLexerBase.matchStat > 0) {
                                    cls2 = cls;
                                    z2 = true;
                                    z3 = true;
                                    obj26 = obj29;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                } else {
                                    if (jSONLexerBase.matchStat == -2) {
                                    }
                                    cls2 = cls;
                                    z2 = false;
                                    obj27 = obj29;
                                    z3 = false;
                                    obj26 = obj27;
                                    obj15 = obj26;
                                    if (z2) {
                                    }
                                    if (obj3 == null) {
                                    }
                                }
                            } else if (jSONLexerBase.matchField(cArr)) {
                                obj28 = obj29;
                            } else {
                                i9 = i3;
                                obj11 = obj29;
                                obj10 = obj11;
                                c = '\r';
                                obj9 = obj10;
                                parseContext = parseContext5;
                                i7 = i5;
                                i6 = i9;
                                i8 = 16;
                                obj8 = obj9;
                                obj13 = obj13;
                                obj14 = obj8;
                                i13 = i8;
                                i4 = i7 + 1;
                                iArr2 = iArr3;
                                obj3 = obj14;
                                parseContext5 = parseContext;
                                i3 = i6;
                            }
                            i6 = i3 + 1;
                            parseContext = parseContext5;
                            i7 = i5;
                            i8 = 16;
                            obj9 = obj29;
                            obj8 = obj9;
                            obj13 = obj13;
                            obj14 = obj8;
                            i13 = i8;
                            i4 = i7 + 1;
                            iArr2 = iArr3;
                            obj3 = obj14;
                            parseContext5 = parseContext;
                            i3 = i6;
                        }
                        if (parseContext2 != null) {
                            parseContext2.object = r62;
                        }
                        defaultJSONParser.setContext(parseContext);
                        throw th;
                    }
                    cls2 = cls;
                    int iScanFieldInt = jSONLexerBase.scanFieldInt(cArr);
                    if (iScanFieldInt == 0) {
                        c2 = 5;
                        if (jSONLexerBase.matchStat == 5) {
                            objValueOf = null;
                        }
                        if (jSONLexerBase.matchStat <= 0) {
                            z2 = true;
                            z3 = true;
                            obj15 = obj29;
                            if (z2) {
                            }
                            if (obj3 == null) {
                            }
                            if (parseContext2 != null) {
                            }
                            defaultJSONParser.setContext(parseContext);
                            throw th;
                        }
                        obj6 = obj29;
                        if (jSONLexerBase.matchStat == -2) {
                            i6 = i3 + 1;
                            parseContext = parseContext5;
                            i7 = i5;
                            i8 = 16;
                            obj8 = obj29;
                            obj13 = obj13;
                            obj14 = obj8;
                            i13 = i8;
                            i4 = i7 + 1;
                            iArr2 = iArr3;
                            obj3 = obj14;
                            parseContext5 = parseContext;
                            i3 = i6;
                        }
                        z2 = false;
                        z3 = false;
                        obj15 = obj6;
                        if (z2) {
                        }
                        if (obj3 == null) {
                        }
                        if (parseContext2 != null) {
                        }
                        defaultJSONParser.setContext(parseContext);
                        throw th;
                    }
                    c2 = 5;
                    objValueOf = Integer.valueOf(iScanFieldInt);
                    if (jSONLexerBase.matchStat <= 0) {
                    }
                }
                cls2 = cls;
                objValueOf = null;
                z2 = true;
                obj27 = obj28;
                z3 = false;
                obj26 = obj27;
                obj15 = obj26;
                if (z2) {
                }
                if (obj3 == null) {
                }
                if (parseContext2 != null) {
                }
                defaultJSONParser.setContext(parseContext);
                throw th;
            }
            obj5 = obj3;
            iArr3 = iArr2;
            cls2 = cls;
            objValueOf = null;
            obj6 = obj5;
            z2 = false;
            z3 = false;
            obj15 = obj6;
            if (z2) {
            }
            if (obj3 == null) {
            }
            if (parseContext2 != null) {
            }
            defaultJSONParser.setContext(parseContext);
            throw th;
        }
        HashMap map3 = map;
        context = parseContext3;
        ?? r1 = (T) obj22;
        if (r1 == 0) {
            if (map3 == null) {
                try {
                    T t7 = (T) createInstance((DefaultJSONParser) defaultJSONParser, type);
                    if (context == null) {
                        parseContext = parseContext4;
                        try {
                            context = defaultJSONParser.setContext(parseContext, t7, obj);
                        } catch (Throwable th20) {
                            th = th20;
                            r6 = t7;
                        }
                    } else {
                        parseContext = parseContext4;
                    }
                    if (context != null) {
                        context.object = t7;
                    }
                    defaultJSONParser.setContext(parseContext);
                    return t7;
                } catch (Throwable th21) {
                    th = th21;
                    parseContext = parseContext4;
                    r63 = r1;
                    parseContext2 = context;
                    th = th;
                    r62 = r63;
                    if (parseContext2 != null) {
                    }
                    defaultJSONParser.setContext(parseContext);
                    throw th;
                }
            }
            parseContext = parseContext4;
            try {
                String[] strArr = this.beanInfo.creatorConstructorParameters;
                if (strArr != null) {
                    objArr = new Object[strArr.length];
                    int i19 = b;
                    while (i19 < strArr.length) {
                        Object objRemove = map3.remove(strArr[i19]);
                        if (objRemove == null) {
                            Type type2 = this.beanInfo.creatorConstructorParameterTypes[i19];
                            FieldInfo fieldInfo3 = this.beanInfo.fields[i19];
                            if (type2 == Byte.TYPE) {
                                objRemove = Byte.valueOf(b);
                            } else if (type2 == Short.TYPE) {
                                objRemove = Short.valueOf(b);
                            } else if (type2 == Integer.TYPE) {
                                objRemove = Integer.valueOf(b);
                            } else if (type2 == Long.TYPE) {
                                objRemove = 0L;
                            } else if (type2 == Float.TYPE) {
                                objRemove = Float.valueOf(0.0f);
                            } else if (type2 == Double.TYPE) {
                                objRemove = Double.valueOf(0.0d);
                            } else if (type2 == Boolean.TYPE) {
                                objRemove = Boolean.FALSE;
                            } else if (type2 == String.class && (fieldInfo3.parserFeatures & Feature.InitStringFieldAsEmpty.mask) != 0) {
                                objRemove = "";
                            }
                        } else {
                            if (this.beanInfo.creatorConstructorParameterTypes != null && i19 < this.beanInfo.creatorConstructorParameterTypes.length) {
                                Type type3 = this.beanInfo.creatorConstructorParameterTypes[i19];
                                if (type3 instanceof Class) {
                                    Class cls7 = (Class) type3;
                                    if (!cls7.isInstance(objRemove) && (objRemove instanceof List)) {
                                        List list = (List) objRemove;
                                        if (list.size() == 1) {
                                            b2 = b;
                                            if (cls7.isInstance(list.get(b2))) {
                                                objRemove = list.get(b2);
                                            }
                                        }
                                    }
                                }
                            }
                            objArr[i19] = objRemove;
                            i19++;
                            b = b2;
                        }
                        b2 = b;
                        objArr[i19] = objRemove;
                        i19++;
                        b = b2;
                    }
                } else {
                    FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                    int length = fieldInfoArr.length;
                    Object[] objArr2 = new Object[length];
                    for (int i20 = b; i20 < length; i20++) {
                        FieldInfo fieldInfo4 = fieldInfoArr[i20];
                        Object objValueOf2 = map3.get(fieldInfo4.name);
                        if (objValueOf2 == null) {
                            Type type4 = fieldInfo4.fieldType;
                            if (type4 == Byte.TYPE) {
                                objValueOf2 = (byte) 0;
                            } else if (type4 == Short.TYPE) {
                                objValueOf2 = (short) 0;
                            } else if (type4 == Integer.TYPE) {
                                objValueOf2 = 0;
                            } else if (type4 == Long.TYPE) {
                                objValueOf2 = 0L;
                            } else if (type4 == Float.TYPE) {
                                objValueOf2 = Float.valueOf(0.0f);
                            } else if (type4 == Double.TYPE) {
                                objValueOf2 = Double.valueOf(0.0d);
                            } else if (type4 == Boolean.TYPE) {
                                objValueOf2 = Boolean.FALSE;
                            } else if (type4 == String.class && (fieldInfo4.parserFeatures & Feature.InitStringFieldAsEmpty.mask) != 0) {
                                objValueOf2 = "";
                            }
                        }
                        objArr2[i20] = objValueOf2;
                    }
                    objArr = objArr2;
                }
                if (this.beanInfo.creatorConstructor != null) {
                    ?? r65 = this.beanInfo.kotlin;
                    if (r65 != 0) {
                        r65 = 0;
                        while (true) {
                            if (r65 >= objArr.length) {
                                break;
                            }
                            if (objArr[r65] != null || this.beanInfo.fields == null || r65 >= this.beanInfo.fields.length) {
                                r65++;
                            } else {
                                r65 = this.beanInfo.fields[r65].fieldClass;
                                if (r65 == String.class) {
                                    z4 = true;
                                    r64 = r65;
                                }
                            }
                        }
                    }
                    z4 = false;
                    r64 = r65;
                    try {
                        if (z4) {
                            try {
                                if (this.beanInfo.kotlinDefaultConstructor != null) {
                                    Object objNewInstance2 = this.beanInfo.kotlinDefaultConstructor.newInstance(new Object[0]);
                                    char c3 = 0;
                                    while (c3 < objArr.length) {
                                        try {
                                            Object obj33 = objArr[c3];
                                            if (obj33 != null && this.beanInfo.fields != null && c3 < this.beanInfo.fields.length) {
                                                this.beanInfo.fields[c3].set(objNewInstance2, obj33);
                                            }
                                            c3 = (T) (c3 + 1);
                                        } catch (Exception e2) {
                                            e = e2;
                                            throw new JSONException("create instance error, " + strArr + ", " + this.beanInfo.creatorConstructor.toGenericString(), e);
                                        }
                                    }
                                    objNewInstance = objNewInstance2;
                                    r1 = c3;
                                }
                                if (strArr != null) {
                                    try {
                                        for (Map.Entry<String, Object> entry : map3.entrySet()) {
                                            FieldDeserializer fieldDeserializer3 = getFieldDeserializer(entry.getKey());
                                            if (fieldDeserializer3 != null) {
                                                fieldDeserializer3.setValue(objNewInstance, entry.getValue());
                                            }
                                        }
                                    } catch (Throwable th22) {
                                        th = th22;
                                        r6 = objNewInstance;
                                    }
                                }
                                r1 = (T) objNewInstance;
                            } catch (Exception e3) {
                                e = e3;
                            }
                        }
                        objNewInstance = this.beanInfo.creatorConstructor.newInstance(objArr);
                        r1 = r1;
                        if (strArr != null) {
                        }
                        r1 = (T) objNewInstance;
                    } catch (Throwable th23) {
                        th = th23;
                        r6 = r64;
                    }
                } else {
                    r1 = r1;
                    if (this.beanInfo.factoryMethod != null) {
                        try {
                            r1 = (T) this.beanInfo.factoryMethod.invoke(null, objArr);
                        } catch (Exception e4) {
                            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e4);
                        }
                    }
                }
                if (context != null) {
                    context.object = (Object) r1;
                }
            } catch (Throwable th24) {
                th = th24;
                r63 = r1;
                parseContext2 = context;
                th = th;
                r62 = r63;
                if (parseContext2 != null) {
                }
                defaultJSONParser.setContext(parseContext);
                throw th;
            }
            parseContext2 = context;
            r62 = r6;
            if (parseContext2 != null) {
            }
            defaultJSONParser.setContext(parseContext);
            throw th;
        }
        parseContext = parseContext4;
        Method method = this.beanInfo.buildMethod;
        if (method == null) {
            if (context != null) {
                context.object = (Object) r1;
            }
            defaultJSONParser.setContext(parseContext);
            return (T) r1;
        }
        try {
            T t8 = (T) method.invoke((Object) r1, new Object[0]);
            if (context != null) {
                context.object = (Object) r1;
            }
            defaultJSONParser.setContext(parseContext);
            return t8;
        } catch (Exception e5) {
            throw new JSONException("build object error", e5);
        }
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Object objScanDecimal;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        T t = (T) createInstance(defaultJSONParser, type);
        int i = 0;
        int length = this.sortedFieldDeserializers.length;
        while (true) {
            if (i >= length) {
                break;
            }
            char c = i == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
            } else {
                if (cls.isEnum()) {
                    char current = jSONLexer.getCurrent();
                    objScanDecimal = (current == '\"' || current == 'n') ? jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c) : (current < '0' || current > '9') ? scanEnum(jSONLexer, c) : ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                } else if (cls == Boolean.TYPE) {
                    fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
                } else if (cls == Float.TYPE) {
                    objScanDecimal = Float.valueOf(jSONLexer.scanFloat(c));
                } else if (cls == Double.TYPE) {
                    objScanDecimal = Double.valueOf(jSONLexer.scanDouble(c));
                } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                    fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
                } else if (cls == BigDecimal.class) {
                    objScanDecimal = jSONLexer.scanDecimal(c);
                } else {
                    jSONLexer.nextToken(14);
                    fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                    if (jSONLexer.token() == 15) {
                        break;
                    }
                    check(jSONLexer, c == ']' ? 15 : 16);
                }
                fieldDeserializer.setValue(t, objScanDecimal);
            }
            i++;
        }
        jSONLexer.nextToken(16);
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public FieldDeserializer getFieldDeserializer(long j) {
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                jArr[i] = TypeUtils.fnv1a_64(this.sortedFieldDeserializers[i].fieldInfo.name);
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.hashArray, j);
        if (iBinarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                int iBinarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(this.sortedFieldDeserializers[i2].fieldInfo.name));
                if (iBinarySearch2 >= 0) {
                    sArr[iBinarySearch2] = (short) i2;
                }
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[iBinarySearch];
        if (s != -1) {
            return this.sortedFieldDeserializers[s];
        }
        return null;
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        if (this.fieldDeserializerMap != null && (fieldDeserializer = this.fieldDeserializerMap.get(str)) != null) {
            return fieldDeserializer;
        }
        int i = 0;
        int length = this.sortedFieldDeserializers.length - 1;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int iCompareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i = i2 + 1;
            } else {
                if (iCompareTo <= 0) {
                    if (isSetFlag(i2, iArr)) {
                        return null;
                    }
                    return this.sortedFieldDeserializers[i2];
                }
                length = i2 - 1;
            }
        }
        if (this.alterNameFieldDeserializers != null) {
            return this.alterNameFieldDeserializers.get(str);
        }
        return null;
    }

    public Type getFieldType(int i) {
        return this.sortedFieldDeserializers[i].fieldInfo.fieldType;
    }

    protected JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> cls : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v4 */
    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map, int[] iArr) {
        ?? r17;
        JSONLexer jSONLexer;
        JSONLexer jSONLexer2 = defaultJSONParser.lexer;
        int i = Feature.DisableFieldSmartMatch.mask;
        FieldDeserializer fieldDeserializer = (jSONLexer2.isEnabled(i) || (i & this.beanInfo.parserFeatures) != 0) ? getFieldDeserializer(str) : smartMatch(str, iArr);
        int i2 = Feature.SupportNonPublicField.mask;
        if (fieldDeserializer != null || (!jSONLexer2.isEnabled(i2) && (i2 & this.beanInfo.parserFeatures) == 0)) {
            r17 = 1;
            jSONLexer = jSONLexer2;
        } else {
            if (this.extraFieldDeserializers == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1, 0.75f, 1);
                for (Class<?> superclass = this.clazz; superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                    for (Field field : superclass.getDeclaredFields()) {
                        String name = field.getName();
                        if (getFieldDeserializer(name) == null) {
                            int modifiers = field.getModifiers();
                            if ((modifiers & 16) == 0 && (modifiers & 8) == 0) {
                                concurrentHashMap.put(name, field);
                            }
                        }
                    }
                }
                this.extraFieldDeserializers = concurrentHashMap;
            }
            Object obj2 = this.extraFieldDeserializers.get(str);
            if (obj2 == null) {
                jSONLexer = jSONLexer2;
                r17 = 1;
            } else if (obj2 instanceof FieldDeserializer) {
                fieldDeserializer = (FieldDeserializer) obj2;
                jSONLexer = jSONLexer2;
                r17 = 1;
            } else {
                Field field2 = (Field) obj2;
                field2.setAccessible(true);
                r17 = 1;
                jSONLexer = jSONLexer2;
                fieldDeserializer = new DefaultFieldDeserializer(defaultJSONParser.getConfig(), this.clazz, new FieldInfo(str, field2.getDeclaringClass(), field2.getType(), field2.getGenericType(), field2, 0, 0, 0));
                this.extraFieldDeserializers.put(str, fieldDeserializer);
            }
        }
        if (fieldDeserializer != null) {
            JSONLexer jSONLexer3 = jSONLexer;
            int i3 = 0;
            while (true) {
                if (i3 >= this.sortedFieldDeserializers.length) {
                    i3 = -1;
                    break;
                }
                if (this.sortedFieldDeserializers[i3] == fieldDeserializer) {
                    break;
                }
                i3++;
            }
            if (i3 != -1 && iArr != null && str.startsWith("_") && isSetFlag(i3, iArr)) {
                defaultJSONParser.parseExtra(obj, str);
                return false;
            }
            jSONLexer3.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
            fieldDeserializer.parseField(defaultJSONParser, obj, type, map);
            if (iArr != null) {
                int i4 = i3 / 32;
                iArr[i4] = iArr[i4] | (r17 << (i3 % 32));
            }
            return r17;
        }
        if (!jSONLexer.isEnabled(Feature.IgnoreNotMatch)) {
            throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + str);
        }
        int i5 = -1;
        for (int i6 = 0; i6 < this.sortedFieldDeserializers.length; i6++) {
            FieldDeserializer fieldDeserializer2 = this.sortedFieldDeserializers[i6];
            FieldInfo fieldInfo = fieldDeserializer2.fieldInfo;
            if (fieldInfo.unwrapped && (fieldDeserializer2 instanceof DefaultFieldDeserializer)) {
                if (fieldInfo.field != null) {
                    DefaultFieldDeserializer defaultFieldDeserializer = (DefaultFieldDeserializer) fieldDeserializer2;
                    ObjectDeserializer fieldValueDeserilizer = defaultFieldDeserializer.getFieldValueDeserilizer(defaultJSONParser.getConfig());
                    if (fieldValueDeserilizer instanceof JavaBeanDeserializer) {
                        FieldDeserializer fieldDeserializer3 = ((JavaBeanDeserializer) fieldValueDeserilizer).getFieldDeserializer(str);
                        if (fieldDeserializer3 != null) {
                            try {
                                Object objCreateInstance = fieldInfo.field.get(obj);
                                if (objCreateInstance == null) {
                                    objCreateInstance = ((JavaBeanDeserializer) fieldValueDeserilizer).createInstance(defaultJSONParser, fieldInfo.fieldType);
                                    fieldDeserializer2.setValue(obj, objCreateInstance);
                                }
                                jSONLexer.nextTokenWithColon(defaultFieldDeserializer.getFastMatchToken());
                                fieldDeserializer3.parseField(defaultJSONParser, objCreateInstance, type, map);
                                i5 = i6;
                            } catch (Exception e) {
                                throw new JSONException("parse unwrapped field error.", e);
                            }
                        } else {
                            continue;
                        }
                    } else if (fieldValueDeserilizer instanceof MapDeserializer) {
                        MapDeserializer mapDeserializer = (MapDeserializer) fieldValueDeserilizer;
                        try {
                            Map<Object, Object> mapCreateMap = (Map) fieldInfo.field.get(obj);
                            if (mapCreateMap == null) {
                                mapCreateMap = mapDeserializer.createMap(fieldInfo.fieldType);
                                fieldDeserializer2.setValue(obj, mapCreateMap);
                            }
                            jSONLexer.nextTokenWithColon();
                            mapCreateMap.put(str, defaultJSONParser.parse(str));
                            i5 = i6;
                        } catch (Exception e2) {
                            throw new JSONException("parse unwrapped field error.", e2);
                        }
                    } else {
                        continue;
                    }
                } else if (fieldInfo.method.getParameterTypes().length == 2) {
                    jSONLexer.nextTokenWithColon();
                    Object obj3 = defaultJSONParser.parse(str);
                    try {
                        Method method = fieldInfo.method;
                        Object[] objArr = new Object[2];
                        objArr[0] = str;
                        objArr[r17] = obj3;
                        method.invoke(obj, objArr);
                        i5 = i6;
                    } catch (Exception e3) {
                        throw new JSONException("parse unwrapped field error.", e3);
                    }
                } else {
                    continue;
                }
            }
        }
        if (i5 == -1) {
            defaultJSONParser.parseExtra(obj, str);
            return false;
        }
        if (iArr != null) {
            int i7 = i5 / 32;
            iArr[i7] = iArr[i7] | (r17 << (i5 % 32));
        }
        return r17;
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i) {
        return parseRest(defaultJSONParser, type, obj, obj2, i, new int[0]);
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i, iArr);
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    protected Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long jScanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(jScanEnumSymbol);
        if (enumByHashCode == null) {
            if (jScanEnumSymbol == -3750763034362895579L) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FieldDeserializer smartMatch(String str, int[] iArr) {
        boolean zStartsWith;
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer2 = getFieldDeserializer(str, iArr);
        if (fieldDeserializer2 != null) {
            return fieldDeserializer2;
        }
        long jFnv1a_64_lower = TypeUtils.fnv1a_64_lower(str);
        if (this.smartMatchHashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                jArr[i] = TypeUtils.fnv1a_64_lower(this.sortedFieldDeserializers[i].fieldInfo.name);
            }
            Arrays.sort(jArr);
            this.smartMatchHashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, jFnv1a_64_lower);
        if (iBinarySearch < 0) {
            zStartsWith = str.startsWith("is");
            if (zStartsWith) {
                iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(str.substring(2)));
            }
        } else {
            zStartsWith = false;
        }
        if (iBinarySearch < 0) {
            fieldDeserializer = fieldDeserializer2;
        } else {
            if (this.smartMatchHashArrayMapping == null) {
                short[] sArr = new short[this.smartMatchHashArray.length];
                Arrays.fill(sArr, (short) -1);
                for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                    int iBinarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(this.sortedFieldDeserializers[i2].fieldInfo.name));
                    if (iBinarySearch2 >= 0) {
                        sArr[iBinarySearch2] = (short) i2;
                    }
                }
                this.smartMatchHashArrayMapping = sArr;
            }
            short s = this.smartMatchHashArrayMapping[iBinarySearch];
            if (s != -1 && !isSetFlag(s, iArr)) {
                fieldDeserializer = this.sortedFieldDeserializers[s];
            }
        }
        if (fieldDeserializer == null) {
            return fieldDeserializer;
        }
        FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
        if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
            return null;
        }
        Class<?> cls = fieldInfo.fieldClass;
        if (!zStartsWith || cls == Boolean.TYPE || cls == Boolean.class) {
            return fieldDeserializer;
        }
        return null;
    }
}
