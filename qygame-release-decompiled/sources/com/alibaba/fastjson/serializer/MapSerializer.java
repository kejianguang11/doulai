package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public class MapSerializer extends SerializeFilterable implements ObjectSerializer {
    public static MapSerializer instance = new MapSerializer();
    private static final int NON_STRINGKEY_AS_STRING = SerializerFeature.of(new SerializerFeature[]{SerializerFeature.BrowserCompatible, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.BrowserSecure});

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0159 A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0022 A[PHI: r1
      0x0022: PHI (r1v64 java.util.Map<java.lang.String, java.lang.Object>) = 
      (r1v2 java.util.Map<java.lang.String, java.lang.Object>)
      (r1v2 java.util.Map<java.lang.String, java.lang.Object>)
      (r1v2 java.util.Map<java.lang.String, java.lang.Object>)
      (r1v1 java.util.Map<java.lang.String, java.lang.Object>)
     binds: [B:16:0x0030, B:18:0x0034, B:220:0x0022, B:9:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0252 A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x028c A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0299 A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f1 A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0125 A[Catch: all -> 0x0056, TryCatch #0 {all -> 0x0056, blocks: (B:28:0x0052, B:31:0x0059, B:33:0x0065, B:44:0x0084, B:46:0x0095, B:47:0x00a5, B:49:0x00ab, B:51:0x00bd, B:54:0x00c5, B:57:0x00ca, B:59:0x00d4, B:61:0x00d8, B:64:0x00e3, B:67:0x00f1, B:69:0x00f5, B:72:0x00fd, B:75:0x0102, B:77:0x010c, B:79:0x0110, B:82:0x011b, B:85:0x0125, B:87:0x0129, B:90:0x0131, B:93:0x0136, B:95:0x0140, B:97:0x0144, B:100:0x014f, B:103:0x0159, B:105:0x015d, B:108:0x0165, B:111:0x016a, B:113:0x0174, B:115:0x0178, B:118:0x0184, B:121:0x018f, B:123:0x0193, B:126:0x019b, B:129:0x01a0, B:131:0x01aa, B:133:0x01ae, B:134:0x01b2, B:135:0x01b7, B:136:0x01ba, B:138:0x01be, B:141:0x01c6, B:144:0x01cb, B:146:0x01d5, B:148:0x01d9, B:149:0x01dd, B:150:0x01e2, B:153:0x01e8, B:156:0x01ed, B:158:0x01f1, B:164:0x01fb, B:169:0x023a, B:172:0x024c, B:174:0x0252, B:176:0x0257, B:177:0x025a, B:179:0x0262, B:180:0x0265, B:191:0x028c, B:193:0x0299, B:195:0x02a1, B:197:0x02ad, B:199:0x02b5, B:201:0x02b9, B:203:0x02bd, B:205:0x02c8, B:207:0x02ce, B:208:0x02dc, B:182:0x026b, B:183:0x026e, B:185:0x0276, B:187:0x027a, B:189:0x0285, B:188:0x0282, B:166:0x0221, B:39:0x0079), top: B:221:0x0052 }] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i, boolean z) throws IOException {
        Map<String, Object> treeMap;
        boolean z2;
        String str;
        Class<?> cls;
        boolean z3;
        Object objProcessValue;
        Object obj3;
        Class<?> cls2;
        ObjectSerializer objectWriter;
        String jSONString;
        String jSONString2;
        MapSerializer mapSerializer = this;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        Map<String, Object> innerMap = (Map) obj;
        int i2 = SerializerFeature.MapSortField.mask;
        if ((serializeWriter.features & i2) != 0 || (i2 & i) != 0) {
            if (innerMap instanceof JSONObject) {
                innerMap = ((JSONObject) innerMap).getInnerMap();
            }
            if ((innerMap instanceof SortedMap) || (innerMap instanceof LinkedHashMap)) {
                treeMap = innerMap;
            } else {
                try {
                    treeMap = new TreeMap(innerMap);
                } catch (Exception unused) {
                    treeMap = innerMap;
                }
            }
        }
        if (jSONSerializer.containsReference(obj)) {
            jSONSerializer.writeReference(obj);
            return;
        }
        SerialContext serialContext = jSONSerializer.context;
        boolean z4 = false;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        if (!z) {
            try {
                serializeWriter.write(123);
            } catch (Throwable th) {
                jSONSerializer.context = serialContext;
                throw th;
            }
        }
        jSONSerializer.incrementIndent();
        boolean z5 = true;
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            String str2 = jSONSerializer.config.typeKey;
            Class<?> cls3 = treeMap.getClass();
            if (((cls3 == JSONObject.class || cls3 == HashMap.class || cls3 == LinkedHashMap.class) && treeMap.containsKey(str2)) == true) {
                z2 = true;
            } else {
                serializeWriter.writeFieldName(str2);
                serializeWriter.writeString(obj.getClass().getName());
                z2 = false;
            }
        }
        boolean z6 = z2;
        Class<?> cls4 = null;
        ObjectSerializer objectSerializer = null;
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            List<PropertyPreFilter> list = jSONSerializer.propertyPreFilters;
            if (list == null || list.size() <= 0) {
                List<PropertyPreFilter> list2 = mapSerializer.propertyPreFilters;
                if (list2 == null || list2.size() <= 0) {
                    List<PropertyFilter> list3 = jSONSerializer.propertyFilters;
                    if (list3 == null || list3.size() <= 0) {
                        List<PropertyFilter> list4 = mapSerializer.propertyFilters;
                        if (list4 != null && list4.size() > 0) {
                            if (key == null || (key instanceof String)) {
                                if (!mapSerializer.apply(jSONSerializer, obj, key, value)) {
                                    cls = cls4;
                                    z3 = z5;
                                    z5 = z3;
                                    cls4 = cls;
                                    mapSerializer = this;
                                    z4 = false;
                                }
                            } else if ((key.getClass().isPrimitive() || (key instanceof Number)) && !mapSerializer.apply(jSONSerializer, obj, JSON.toJSONString(key), value)) {
                                cls = cls4;
                                z3 = z5;
                                z5 = z3;
                                cls4 = cls;
                                mapSerializer = this;
                                z4 = false;
                            }
                        }
                        List<NameFilter> list5 = jSONSerializer.nameFilters;
                        if (list5 != null && list5.size() > 0) {
                            if (key == null || (key instanceof String)) {
                                jSONString2 = key;
                            } else if (key.getClass().isPrimitive() || (key instanceof Number)) {
                                jSONString2 = JSON.toJSONString(key);
                            }
                            key = mapSerializer.processKey(jSONSerializer, obj, jSONString2, value);
                        }
                        List<NameFilter> list6 = mapSerializer.nameFilters;
                        if (list6 != null && list6.size() > 0) {
                            if (key == null || (key instanceof String)) {
                                jSONString = key;
                            } else if (key.getClass().isPrimitive() || (key instanceof Number)) {
                                jSONString = JSON.toJSONString(key);
                            }
                            key = mapSerializer.processKey(jSONSerializer, obj, jSONString, value);
                        }
                        String str3 = key;
                        if (str3 == null || (str3 instanceof String)) {
                            str = str3;
                            cls = cls4;
                            z3 = z5;
                            objProcessValue = processValue(jSONSerializer, null, obj, str, value);
                        } else if (((str3 instanceof Map) || (str3 instanceof Collection)) ? z5 : z4) {
                            str = str3;
                            cls = cls4;
                            z3 = z5;
                            obj3 = value;
                            if (obj3 == null || SerializerFeature.isEnabled(serializeWriter.features, i, SerializerFeature.WriteMapNullValue)) {
                                if (str instanceof String) {
                                    if (!z6) {
                                        serializeWriter.write(44);
                                    }
                                    if (!serializeWriter.isEnabled(NON_STRINGKEY_AS_STRING) || (str instanceof Enum)) {
                                        jSONSerializer.write((Object) str);
                                    } else {
                                        jSONSerializer.write(JSON.toJSONString(str));
                                    }
                                    serializeWriter.write(58);
                                } else {
                                    String str4 = str;
                                    if (!z6) {
                                        serializeWriter.write(44);
                                    }
                                    if (serializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
                                        jSONSerializer.println();
                                    }
                                    serializeWriter.writeFieldName(str4, z3);
                                }
                                if (obj3 != null) {
                                    serializeWriter.writeNull();
                                    z5 = z3 ? 1 : 0;
                                    cls4 = cls;
                                } else {
                                    Class<?> cls5 = obj3.getClass();
                                    Class<?> cls6 = cls;
                                    if (cls5 != cls6) {
                                        cls2 = cls5;
                                        objectWriter = jSONSerializer.getObjectWriter(cls5);
                                    } else {
                                        cls2 = cls6;
                                        objectWriter = objectSerializer;
                                    }
                                    if (!SerializerFeature.isEnabled(i, SerializerFeature.WriteClassName) || !(objectWriter instanceof JavaBeanSerializer)) {
                                        objectSerializer = objectWriter;
                                        objectSerializer.write(jSONSerializer, obj3, str, null, i);
                                    } else if (type instanceof ParameterizedType) {
                                        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                                        Type type2 = actualTypeArguments.length == 2 ? actualTypeArguments[z3 ? 1 : 0] : null;
                                        objectSerializer = objectWriter;
                                        ((JavaBeanSerializer) objectWriter).writeNoneASM(jSONSerializer, obj3, str, type2, i);
                                    }
                                    z5 = z3 ? 1 : 0;
                                    cls4 = cls2;
                                }
                                mapSerializer = this;
                                z4 = false;
                                z6 = false;
                            } else {
                                z5 = z3;
                                cls4 = cls;
                                mapSerializer = this;
                                z4 = false;
                            }
                        } else {
                            str = str3;
                            cls = cls4;
                            z3 = z5;
                            objProcessValue = processValue(jSONSerializer, null, obj, JSON.toJSONString(str3), value);
                        }
                        obj3 = objProcessValue;
                        if (obj3 == null) {
                        }
                        if (str instanceof String) {
                        }
                        if (obj3 != null) {
                        }
                        mapSerializer = this;
                        z4 = false;
                        z6 = false;
                    } else if (key == null || (key instanceof String)) {
                        if (!mapSerializer.apply(jSONSerializer, obj, key, value)) {
                        }
                        cls = cls4;
                        z3 = z5;
                        z5 = z3;
                        cls4 = cls;
                        mapSerializer = this;
                        z4 = false;
                    } else {
                        if ((key.getClass().isPrimitive() || (key instanceof Number)) && !mapSerializer.apply(jSONSerializer, obj, JSON.toJSONString(key), value)) {
                        }
                        cls = cls4;
                        z3 = z5;
                        z5 = z3;
                        cls4 = cls;
                        mapSerializer = this;
                        z4 = false;
                    }
                } else if (key == null || (key instanceof String)) {
                    if (!mapSerializer.applyName(jSONSerializer, obj, key)) {
                    }
                    cls = cls4;
                    z3 = z5;
                    z5 = z3;
                    cls4 = cls;
                    mapSerializer = this;
                    z4 = false;
                } else {
                    if ((key.getClass().isPrimitive() || (key instanceof Number)) && !mapSerializer.applyName(jSONSerializer, obj, JSON.toJSONString(key))) {
                    }
                    cls = cls4;
                    z3 = z5;
                    z5 = z3;
                    cls4 = cls;
                    mapSerializer = this;
                    z4 = false;
                }
            } else if (key == null || (key instanceof String)) {
                if (!mapSerializer.applyName(jSONSerializer, obj, key)) {
                }
                cls = cls4;
                z3 = z5;
                z5 = z3;
                cls4 = cls;
                mapSerializer = this;
                z4 = false;
            } else {
                if ((key.getClass().isPrimitive() || (key instanceof Number)) && !mapSerializer.applyName(jSONSerializer, obj, JSON.toJSONString(key))) {
                }
                cls = cls4;
                z3 = z5;
                z5 = z3;
                cls4 = cls;
                mapSerializer = this;
                z4 = false;
            }
        }
        jSONSerializer.context = serialContext;
        jSONSerializer.decrementIdent();
        if (serializeWriter.isEnabled(SerializerFeature.PrettyFormat) && treeMap.size() > 0) {
            jSONSerializer.println();
        }
        if (z) {
            return;
        }
        serializeWriter.write(125);
    }
}
