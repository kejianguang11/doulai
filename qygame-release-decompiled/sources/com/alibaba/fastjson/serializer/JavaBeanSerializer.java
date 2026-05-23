package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        boolean z;
        this.beanInfo = serializeBeanInfo;
        this.sortedGetters = new FieldSerializer[serializeBeanInfo.sortedFields.length];
        for (int i = 0; i < this.sortedGetters.length; i++) {
            this.sortedGetters[i] = new FieldSerializer(serializeBeanInfo.beanType, serializeBeanInfo.sortedFields[i]);
        }
        if (serializeBeanInfo.fields == serializeBeanInfo.sortedFields) {
            this.getters = this.sortedGetters;
        } else {
            this.getters = new FieldSerializer[serializeBeanInfo.fields.length];
            int i2 = 0;
            while (true) {
                if (i2 >= this.getters.length) {
                    z = false;
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(serializeBeanInfo.fields[i2].name);
                if (fieldSerializer == null) {
                    z = true;
                    break;
                } else {
                    this.getters[i2] = fieldSerializer;
                    i2++;
                }
            }
            if (z) {
                System.arraycopy(this.sortedGetters, 0, this.getters, 0, this.sortedGetters.length);
            }
        }
        if (serializeBeanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> cls : serializeBeanInfo.jsonType.serialzeFilters()) {
                try {
                    addFilter(cls.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception unused) {
                }
            }
        }
        if (serializeBeanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> cls2 : serializeBeanInfo.jsonType.serialzeFilters()) {
                try {
                    addFilter(cls2.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception unused2) {
                }
            }
        }
    }

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> cls, Map<String, String> map) {
        this(TypeUtils.buildBeanInfo(cls, map, null));
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, createAliasMap(strArr));
    }

    static Map<String, String> createAliasMap(String... strArr) {
        HashMap map = new HashMap();
        for (String str : strArr) {
            map.put(str, str);
        }
        return map;
    }

    protected boolean applyLabel(JSONSerializer jSONSerializer, String str) {
        if (jSONSerializer.labelFilters != null) {
            Iterator<LabelFilter> it = jSONSerializer.labelFilters.iterator();
            while (it.hasNext()) {
                if (!it.next().apply(str)) {
                    return false;
                }
            }
        }
        if (this.labelFilters == null) {
            return true;
        }
        Iterator<LabelFilter> it2 = this.labelFilters.iterator();
        while (it2.hasNext()) {
            if (!it2.next().apply(str)) {
                return false;
            }
        }
        return true;
    }

    protected BeanContext getBeanContext(int i) {
        return this.sortedGetters[i].fieldContext;
    }

    public Set<String> getFieldNames(Object obj) throws Exception {
        HashSet hashSet = new HashSet();
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                hashSet.add(fieldSerializer.fieldInfo.name);
            }
        }
        return hashSet;
    }

    public FieldSerializer getFieldSerializer(long j) {
        PropertyNamingStrategy[] propertyNamingStrategyArrValues;
        int iBinarySearch;
        if (this.hashArray == null) {
            propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            long[] jArr = new long[this.sortedGetters.length * propertyNamingStrategyArrValues.length];
            int i = 0;
            int i2 = 0;
            while (i < this.sortedGetters.length) {
                String str = this.sortedGetters[i].fieldInfo.name;
                int i3 = i2 + 1;
                jArr[i2] = TypeUtils.fnv1a_64(str);
                for (PropertyNamingStrategy propertyNamingStrategy : propertyNamingStrategyArrValues) {
                    String strTranslate = propertyNamingStrategy.translate(str);
                    if (!str.equals(strTranslate)) {
                        jArr[i3] = TypeUtils.fnv1a_64(strTranslate);
                        i3++;
                    }
                }
                i++;
                i2 = i3;
            }
            Arrays.sort(jArr, 0, i2);
            this.hashArray = new long[i2];
            System.arraycopy(jArr, 0, this.hashArray, 0, i2);
        } else {
            propertyNamingStrategyArrValues = null;
        }
        int iBinarySearch2 = Arrays.binarySearch(this.hashArray, j);
        if (iBinarySearch2 < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (propertyNamingStrategyArrValues == null) {
                propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            }
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            for (int i4 = 0; i4 < this.sortedGetters.length; i4++) {
                String str2 = this.sortedGetters[i4].fieldInfo.name;
                int iBinarySearch3 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(str2));
                if (iBinarySearch3 >= 0) {
                    sArr[iBinarySearch3] = (short) i4;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : propertyNamingStrategyArrValues) {
                    String strTranslate2 = propertyNamingStrategy2.translate(str2);
                    if (!str2.equals(strTranslate2) && (iBinarySearch = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(strTranslate2))) >= 0) {
                        sArr[iBinarySearch] = (short) i4;
                    }
                }
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[iBinarySearch2];
        if (s != -1) {
            return this.sortedGetters[s];
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        int length = this.sortedGetters.length - 1;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int iCompareTo = this.sortedGetters[i2].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i = i2 + 1;
            } else {
                if (iCompareTo <= 0) {
                    return this.sortedGetters[i2];
                }
                length = i2 - 1;
            }
        }
        return null;
    }

    protected Type getFieldType(int i) {
        return this.sortedGetters[i].fieldInfo.fieldType;
    }

    public Object getFieldValue(Object obj, String str) {
        FieldSerializer fieldSerializer = getFieldSerializer(str);
        if (fieldSerializer == null) {
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e) {
            throw new JSONException("getFieldValue error." + str, e);
        } catch (InvocationTargetException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        }
    }

    public Object getFieldValue(Object obj, String str, long j, boolean z) {
        FieldSerializer fieldSerializer = getFieldSerializer(j);
        if (fieldSerializer == null) {
            if (!z) {
                return null;
            }
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e) {
            throw new JSONException("getFieldValue error." + str, e);
        } catch (InvocationTargetException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        }
    }

    public List<Object> getFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            arrayList.add(fieldSerializer.getPropertyValue(obj));
        }
        return arrayList;
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            boolean zIsEnabled = SerializerFeature.isEnabled(fieldSerializer.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = fieldSerializer.fieldInfo;
            if (!zIsEnabled || fieldInfo == null || !fieldInfo.fieldTransient) {
                linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
            }
        }
        return linkedHashMap;
    }

    public List<Object> getObjectFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            Class<?> cls = fieldSerializer.fieldInfo.fieldClass;
            if (!cls.isPrimitive() && !cls.getName().startsWith("java.lang.")) {
                arrayList.add(fieldSerializer.getPropertyValue(obj));
            }
        }
        return arrayList;
    }

    public int getSize(Object obj) throws Exception {
        int i = 0;
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                i++;
            }
        }
        return i;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return isWriteAsArray(jSONSerializer, 0);
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer, int i) {
        int i2 = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & i2) == 0 && !jSONSerializer.out.beanToArray && (i & i2) == 0) ? false : true;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0269 A[PHI: r1
      0x0269: PHI (r1v41 java.lang.Object) = 
      (r1v40 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v45 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v46 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v47 java.lang.Object)
      (r1v40 java.lang.Object)
      (r1v48 java.lang.Object)
      (r1v40 java.lang.Object)
     binds: [B:114:0x019c, B:174:0x0257, B:176:0x025b, B:178:0x0265, B:173:0x0252, B:172:0x0250, B:159:0x0227, B:158:0x0225, B:145:0x01fd, B:144:0x01fb, B:131:0x01d5, B:130:0x01d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x026b A[Catch: Exception -> 0x0403, all -> 0x044a, TryCatch #3 {all -> 0x044a, blocks: (B:66:0x00df, B:73:0x0100, B:79:0x0113, B:81:0x0119, B:87:0x0127, B:89:0x012d, B:91:0x0137, B:104:0x0159, B:109:0x016d, B:112:0x0179, B:113:0x017f, B:115:0x019e, B:117:0x01a6, B:120:0x01b7, B:122:0x01c2, B:124:0x01c6, B:127:0x01cd, B:129:0x01d0, B:131:0x01d5, B:134:0x01df, B:136:0x01ea, B:138:0x01ee, B:141:0x01f5, B:143:0x01f8, B:146:0x0201, B:148:0x0209, B:150:0x0214, B:152:0x0218, B:155:0x021f, B:157:0x0222, B:159:0x0227, B:160:0x022c, B:162:0x0234, B:164:0x023f, B:166:0x0243, B:169:0x024a, B:171:0x024d, B:173:0x0252, B:175:0x0259, B:177:0x025d, B:181:0x026b, B:183:0x026f, B:185:0x0278, B:187:0x0283, B:189:0x0289, B:191:0x028d, B:194:0x0298, B:196:0x029c, B:198:0x02a0, B:201:0x02ab, B:203:0x02af, B:205:0x02b3, B:208:0x02be, B:210:0x02c2, B:212:0x02c6, B:215:0x02d4, B:217:0x02d8, B:219:0x02dc, B:222:0x02e9, B:224:0x02ed, B:226:0x02f1, B:229:0x02ff, B:231:0x0303, B:233:0x0307, B:237:0x0313, B:239:0x0317, B:241:0x031b, B:244:0x0329, B:246:0x0334, B:250:0x033d, B:252:0x0343, B:291:0x03c7, B:293:0x03cb, B:295:0x03cf, B:298:0x03d9, B:300:0x03e1, B:301:0x03e9, B:303:0x03ef, B:256:0x034f, B:259:0x0357, B:262:0x035d, B:266:0x0372, B:269:0x037c, B:272:0x0386, B:274:0x038f, B:277:0x0399, B:278:0x039d, B:279:0x03a3, B:281:0x03a8, B:282:0x03ac, B:283:0x03b0, B:285:0x03b4, B:287:0x03b8, B:290:0x03c4, B:263:0x0368, B:97:0x0145, B:101:0x014e, B:315:0x0409, B:323:0x0428, B:325:0x0430, B:327:0x0438, B:329:0x0440), top: B:366:0x00df }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0313 A[Catch: Exception -> 0x0403, all -> 0x044a, TryCatch #3 {all -> 0x044a, blocks: (B:66:0x00df, B:73:0x0100, B:79:0x0113, B:81:0x0119, B:87:0x0127, B:89:0x012d, B:91:0x0137, B:104:0x0159, B:109:0x016d, B:112:0x0179, B:113:0x017f, B:115:0x019e, B:117:0x01a6, B:120:0x01b7, B:122:0x01c2, B:124:0x01c6, B:127:0x01cd, B:129:0x01d0, B:131:0x01d5, B:134:0x01df, B:136:0x01ea, B:138:0x01ee, B:141:0x01f5, B:143:0x01f8, B:146:0x0201, B:148:0x0209, B:150:0x0214, B:152:0x0218, B:155:0x021f, B:157:0x0222, B:159:0x0227, B:160:0x022c, B:162:0x0234, B:164:0x023f, B:166:0x0243, B:169:0x024a, B:171:0x024d, B:173:0x0252, B:175:0x0259, B:177:0x025d, B:181:0x026b, B:183:0x026f, B:185:0x0278, B:187:0x0283, B:189:0x0289, B:191:0x028d, B:194:0x0298, B:196:0x029c, B:198:0x02a0, B:201:0x02ab, B:203:0x02af, B:205:0x02b3, B:208:0x02be, B:210:0x02c2, B:212:0x02c6, B:215:0x02d4, B:217:0x02d8, B:219:0x02dc, B:222:0x02e9, B:224:0x02ed, B:226:0x02f1, B:229:0x02ff, B:231:0x0303, B:233:0x0307, B:237:0x0313, B:239:0x0317, B:241:0x031b, B:244:0x0329, B:246:0x0334, B:250:0x033d, B:252:0x0343, B:291:0x03c7, B:293:0x03cb, B:295:0x03cf, B:298:0x03d9, B:300:0x03e1, B:301:0x03e9, B:303:0x03ef, B:256:0x034f, B:259:0x0357, B:262:0x035d, B:266:0x0372, B:269:0x037c, B:272:0x0386, B:274:0x038f, B:277:0x0399, B:278:0x039d, B:279:0x03a3, B:281:0x03a8, B:282:0x03ac, B:283:0x03b0, B:285:0x03b4, B:287:0x03b8, B:290:0x03c4, B:263:0x0368, B:97:0x0145, B:101:0x014e, B:315:0x0409, B:323:0x0428, B:325:0x0430, B:327:0x0438, B:329:0x0440), top: B:366:0x00df }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x03cb A[Catch: Exception -> 0x0403, all -> 0x044a, TryCatch #3 {all -> 0x044a, blocks: (B:66:0x00df, B:73:0x0100, B:79:0x0113, B:81:0x0119, B:87:0x0127, B:89:0x012d, B:91:0x0137, B:104:0x0159, B:109:0x016d, B:112:0x0179, B:113:0x017f, B:115:0x019e, B:117:0x01a6, B:120:0x01b7, B:122:0x01c2, B:124:0x01c6, B:127:0x01cd, B:129:0x01d0, B:131:0x01d5, B:134:0x01df, B:136:0x01ea, B:138:0x01ee, B:141:0x01f5, B:143:0x01f8, B:146:0x0201, B:148:0x0209, B:150:0x0214, B:152:0x0218, B:155:0x021f, B:157:0x0222, B:159:0x0227, B:160:0x022c, B:162:0x0234, B:164:0x023f, B:166:0x0243, B:169:0x024a, B:171:0x024d, B:173:0x0252, B:175:0x0259, B:177:0x025d, B:181:0x026b, B:183:0x026f, B:185:0x0278, B:187:0x0283, B:189:0x0289, B:191:0x028d, B:194:0x0298, B:196:0x029c, B:198:0x02a0, B:201:0x02ab, B:203:0x02af, B:205:0x02b3, B:208:0x02be, B:210:0x02c2, B:212:0x02c6, B:215:0x02d4, B:217:0x02d8, B:219:0x02dc, B:222:0x02e9, B:224:0x02ed, B:226:0x02f1, B:229:0x02ff, B:231:0x0303, B:233:0x0307, B:237:0x0313, B:239:0x0317, B:241:0x031b, B:244:0x0329, B:246:0x0334, B:250:0x033d, B:252:0x0343, B:291:0x03c7, B:293:0x03cb, B:295:0x03cf, B:298:0x03d9, B:300:0x03e1, B:301:0x03e9, B:303:0x03ef, B:256:0x034f, B:259:0x0357, B:262:0x035d, B:266:0x0372, B:269:0x037c, B:272:0x0386, B:274:0x038f, B:277:0x0399, B:278:0x039d, B:279:0x03a3, B:281:0x03a8, B:282:0x03ac, B:283:0x03b0, B:285:0x03b4, B:287:0x03b8, B:290:0x03c4, B:263:0x0368, B:97:0x0145, B:101:0x014e, B:315:0x0409, B:323:0x0428, B:325:0x0430, B:327:0x0438, B:329:0x0440), top: B:366:0x00df }] */
    /* JADX WARN: Removed duplicated region for block: B:309:0x03fb  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x047a A[Catch: all -> 0x04fb, TryCatch #4 {all -> 0x04fb, blocks: (B:340:0x0459, B:343:0x047a, B:344:0x048a, B:352:0x04ca, B:354:0x04d0, B:355:0x04e8, B:357:0x04ec, B:361:0x04f5, B:362:0x04fa, B:346:0x0491, B:348:0x0495, B:350:0x049b, B:351:0x04b2), top: B:372:0x0459 }] */
    /* JADX WARN: Removed duplicated region for block: B:345:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x04d0 A[Catch: all -> 0x04fb, TryCatch #4 {all -> 0x04fb, blocks: (B:340:0x0459, B:343:0x047a, B:344:0x048a, B:352:0x04ca, B:354:0x04d0, B:355:0x04e8, B:357:0x04ec, B:361:0x04f5, B:362:0x04fa, B:346:0x0491, B:348:0x0495, B:350:0x049b, B:351:0x04b2), top: B:372:0x0459 }] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x04ec A[Catch: all -> 0x04fb, TryCatch #4 {all -> 0x04fb, blocks: (B:340:0x0459, B:343:0x047a, B:344:0x048a, B:352:0x04ca, B:354:0x04d0, B:355:0x04e8, B:357:0x04ec, B:361:0x04f5, B:362:0x04fa, B:346:0x0491, B:348:0x0495, B:350:0x049b, B:351:0x04b2), top: B:372:0x0459 }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0459 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i, boolean z) throws Throwable {
        SerialContext serialContext;
        FieldSerializer fieldSerializer;
        Exception exc;
        StringBuilder sb;
        boolean z2;
        SerialContext serialContext2;
        boolean z3;
        FieldSerializer fieldSerializer2;
        Object propertyValueDirect;
        int i2;
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        Class<?> cls;
        Type type2 = type;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (writeReference(jSONSerializer, obj, i)) {
            return;
        }
        FieldSerializer[] fieldSerializerArr = serializeWriter.sortField ? this.sortedGetters : this.getters;
        SerialContext serialContext3 = jSONSerializer.context;
        if (!this.beanInfo.beanType.isEnum()) {
            jSONSerializer.setContext(serialContext3, obj, obj2, this.beanInfo.features, i);
        }
        boolean zIsWriteAsArray = isWriteAsArray(jSONSerializer, i);
        char c7 = zIsWriteAsArray ? '[' : '{';
        char c8 = zIsWriteAsArray ? ']' : '}';
        if (!z) {
            try {
                try {
                    serializeWriter.append(c7);
                } catch (Exception e) {
                    serialContext = serialContext3;
                    fieldSerializer = null;
                    exc = e;
                    String str = "write javaBean error, fastjson version 1.2.60";
                    if (obj != null) {
                    }
                    String string = str;
                    if (obj2 != null) {
                    }
                    string = sb.toString();
                    if (exc.getMessage() != null) {
                    }
                    if (exc instanceof InvocationTargetException) {
                    }
                    throw new JSONException(string, cause == null ? exc : cause);
                }
            } catch (Throwable th) {
                th = th;
                serialContext = serialContext3;
            }
        }
        if (fieldSerializerArr.length > 0 && serializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
            jSONSerializer.incrementIndent();
            jSONSerializer.println();
        }
        if ((this.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0 || (i & SerializerFeature.WriteClassName.mask) != 0 || jSONSerializer.isWriteClassName(type2, obj)) {
            Class<?> cls2 = obj.getClass();
            if (cls2 != ((cls2 == type2 || !(type2 instanceof WildcardType)) ? type2 : TypeUtils.getClass(type))) {
                writeClassName(jSONSerializer, this.beanInfo.typeKey, obj);
                z2 = true;
            } else {
                z2 = false;
            }
            char c9 = ',';
            char c10 = z2 ? ',' : (char) 0;
            boolean zIsEnabled = serializeWriter.isEnabled(SerializerFeature.WriteClassName);
            char c11 = writeBefore(jSONSerializer, obj, c10) == ',' ? (char) 1 : (char) 0;
            boolean zIsEnabled2 = serializeWriter.isEnabled(SerializerFeature.SkipTransientField);
            boolean zIsEnabled3 = serializeWriter.isEnabled(SerializerFeature.IgnoreNonFieldGetter);
            char c12 = c11;
            fieldSerializer = null;
            int i3 = 0;
            while (i3 < fieldSerializerArr.length) {
                try {
                    FieldSerializer fieldSerializer3 = fieldSerializerArr[i3];
                    Field field = fieldSerializer3.fieldInfo.field;
                    serialContext2 = serialContext3;
                    try {
                        try {
                            FieldInfo fieldInfo = fieldSerializer3.fieldInfo;
                            String str2 = fieldInfo.name;
                            FieldSerializer[] fieldSerializerArr2 = fieldSerializerArr;
                            Class<?> cls3 = fieldInfo.fieldClass;
                            boolean zIsEnabled4 = SerializerFeature.isEnabled(serializeWriter.features, fieldInfo.serialzeFeatures, SerializerFeature.UseSingleQuotes);
                            boolean z4 = serializeWriter.quoteFieldNames && !zIsEnabled4;
                            if ((!zIsEnabled2 || fieldInfo == null || !fieldInfo.fieldTransient) && (!zIsEnabled3 || field != null)) {
                                if (!applyName(jSONSerializer, obj, str2) || !applyLabel(jSONSerializer, fieldInfo.label)) {
                                    z3 = zIsWriteAsArray;
                                    i2 = i3;
                                    c2 = c8;
                                    c = ',';
                                }
                                if (this.beanInfo.typeKey != null && str2.equals(this.beanInfo.typeKey) && jSONSerializer.isWriteClassName(type2, obj)) {
                                    i2 = i3;
                                    c2 = c8;
                                    c = ',';
                                } else {
                                    try {
                                        if (z3) {
                                            fieldSerializer2 = fieldSerializer;
                                        } else {
                                            try {
                                                propertyValueDirect = fieldSerializer3.getPropertyValueDirect(obj);
                                                fieldSerializer2 = fieldSerializer;
                                            } catch (InvocationTargetException e2) {
                                                try {
                                                    if (!serializeWriter.isEnabled(SerializerFeature.IgnoreErrorGetter)) {
                                                        throw e2;
                                                    }
                                                    fieldSerializer2 = fieldSerializer3;
                                                    propertyValueDirect = null;
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    fieldSerializer = fieldSerializer3;
                                                    serialContext = serialContext2;
                                                    exc = e;
                                                    String str3 = "write javaBean error, fastjson version 1.2.60";
                                                    if (obj != null) {
                                                        try {
                                                            str3 = "write javaBean error, fastjson version 1.2.60, class " + obj.getClass().getName();
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                        }
                                                    }
                                                    String string2 = str3;
                                                    if (obj2 != null) {
                                                        if (fieldSerializer != null && fieldSerializer.fieldInfo != null) {
                                                            FieldInfo fieldInfo2 = fieldSerializer.fieldInfo;
                                                            if (fieldInfo2.method != null) {
                                                                sb = new StringBuilder();
                                                                sb.append(string2);
                                                                sb.append(", method : ");
                                                                sb.append(fieldInfo2.method.getName());
                                                            } else {
                                                                string2 = string2 + ", fieldName : " + fieldSerializer.fieldInfo.name;
                                                            }
                                                        }
                                                        if (exc.getMessage() != null) {
                                                            string2 = string2 + ", " + exc.getMessage();
                                                        }
                                                        Throwable cause = exc instanceof InvocationTargetException ? exc.getCause() : null;
                                                        throw new JSONException(string2, cause == null ? exc : cause);
                                                    }
                                                    sb = new StringBuilder();
                                                    sb.append(string2);
                                                    sb.append(", fieldName : ");
                                                    sb.append(obj2);
                                                    string2 = sb.toString();
                                                    if (exc.getMessage() != null) {
                                                    }
                                                    if (exc instanceof InvocationTargetException) {
                                                    }
                                                    throw new JSONException(string2, cause == null ? exc : cause);
                                                }
                                                String str32 = "write javaBean error, fastjson version 1.2.60";
                                                if (obj != null) {
                                                }
                                                String string22 = str32;
                                                if (obj2 != null) {
                                                }
                                                string22 = sb.toString();
                                                if (exc.getMessage() != null) {
                                                }
                                                if (exc instanceof InvocationTargetException) {
                                                }
                                                throw new JSONException(string22, cause == null ? exc : cause);
                                            }
                                            if (apply(jSONSerializer, obj, str2, propertyValueDirect)) {
                                                i2 = i3;
                                                c2 = c8;
                                                c = ',';
                                            } else {
                                                if (cls3 == String.class && "trim".equals(fieldInfo.format) && propertyValueDirect != null) {
                                                    propertyValueDirect = ((String) propertyValueDirect).trim();
                                                }
                                                String strProcessKey = processKey(jSONSerializer, obj, str2, propertyValueDirect);
                                                i2 = i3;
                                                c = ',';
                                                c2 = c8;
                                                Object objProcessValue = processValue(jSONSerializer, fieldSerializer3.fieldContext, obj, str2, propertyValueDirect);
                                                if (objProcessValue == null) {
                                                    int iOf = fieldInfo.serialzeFeatures;
                                                    if (this.beanInfo.jsonType != null) {
                                                        iOf |= SerializerFeature.of(this.beanInfo.jsonType.serialzeFeatures());
                                                    }
                                                    if (cls3 == Boolean.class) {
                                                        int i4 = SerializerFeature.WriteNullBooleanAsFalse.mask;
                                                        int i5 = SerializerFeature.WriteMapNullValue.mask | i4;
                                                        if (zIsWriteAsArray || (iOf & i5) != 0 || (i5 & serializeWriter.features) != 0) {
                                                            if ((iOf & i4) != 0 || (serializeWriter.features & i4) != 0) {
                                                                objProcessValue = false;
                                                            }
                                                            if (objProcessValue == null || ((!serializeWriter.notWriteDefaultValue && (fieldInfo.serialzeFeatures & SerializerFeature.NotWriteDefaultValue.mask) == 0 && (this.beanInfo.features & SerializerFeature.NotWriteDefaultValue.mask) == 0) || (((cls = fieldInfo.fieldClass) != Byte.TYPE || !(objProcessValue instanceof Byte) || ((Byte) objProcessValue).byteValue() != 0) && ((cls != Short.TYPE || !(objProcessValue instanceof Short) || ((Short) objProcessValue).shortValue() != 0) && ((cls != Integer.TYPE || !(objProcessValue instanceof Integer) || ((Integer) objProcessValue).intValue() != 0) && ((cls != Long.TYPE || !(objProcessValue instanceof Long) || ((Long) objProcessValue).longValue() != 0) && ((cls != Float.TYPE || !(objProcessValue instanceof Float) || ((Float) objProcessValue).floatValue() != 0.0f) && ((cls != Double.TYPE || !(objProcessValue instanceof Double) || ((Double) objProcessValue).doubleValue() != 0.0d) && (cls != Boolean.TYPE || !(objProcessValue instanceof Boolean) || ((Boolean) objProcessValue).booleanValue()))))))))) {
                                                                if (c12 != 0) {
                                                                    if (!fieldInfo.unwrapped || !(objProcessValue instanceof Map) || ((Map) objProcessValue).size() != 0) {
                                                                        serializeWriter.write(44);
                                                                        if (serializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
                                                                            jSONSerializer.println();
                                                                        }
                                                                    }
                                                                }
                                                                if (strProcessKey != str2) {
                                                                    c3 = 1;
                                                                    if (propertyValueDirect == objProcessValue) {
                                                                        if (zIsWriteAsArray || (!zIsEnabled && fieldInfo.unwrapped)) {
                                                                            c4 = 0;
                                                                        } else if (z4) {
                                                                            c4 = 0;
                                                                            serializeWriter.write(fieldInfo.name_chars, 0, fieldInfo.name_chars.length);
                                                                        } else {
                                                                            c4 = 0;
                                                                            fieldSerializer3.writePrefix(jSONSerializer);
                                                                        }
                                                                        if (zIsWriteAsArray) {
                                                                            fieldSerializer3.writeValue(jSONSerializer, objProcessValue);
                                                                            if (fieldInfo.unwrapped) {
                                                                            }
                                                                        } else {
                                                                            JSONField annotation = fieldInfo.getAnnotation();
                                                                            if (cls3 == String.class && (annotation == null || annotation.serializeUsing() == Void.class)) {
                                                                                if (objProcessValue != null) {
                                                                                    String str4 = (String) objProcessValue;
                                                                                    if (zIsEnabled4) {
                                                                                        serializeWriter.writeStringWithSingleQuote(str4);
                                                                                    } else {
                                                                                        serializeWriter.writeStringWithDoubleQuote(str4, c4);
                                                                                    }
                                                                                } else if ((serializeWriter.features & SerializerFeature.WriteNullStringAsEmpty.mask) == 0 && (fieldSerializer3.features & SerializerFeature.WriteNullStringAsEmpty.mask) == 0) {
                                                                                    serializeWriter.writeNull();
                                                                                } else {
                                                                                    serializeWriter.writeString("");
                                                                                }
                                                                                if (fieldInfo.unwrapped) {
                                                                                    c5 = c4;
                                                                                    if (c5 == 0) {
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                if (fieldInfo.unwrapped && (objProcessValue instanceof Map) && ((Map) objProcessValue).size() == 0) {
                                                                                    c12 = c4;
                                                                                }
                                                                                fieldSerializer3.writeValue(jSONSerializer, objProcessValue);
                                                                                if (fieldInfo.unwrapped && (objProcessValue instanceof Map)) {
                                                                                    Map map = (Map) objProcessValue;
                                                                                    if (map.size() != 0) {
                                                                                        if (!jSONSerializer.isEnabled(SerializerFeature.WriteMapNullValue)) {
                                                                                            Iterator it = map.values().iterator();
                                                                                            while (true) {
                                                                                                if (!it.hasNext()) {
                                                                                                    c6 = c4;
                                                                                                    break;
                                                                                                } else if (it.next() != null) {
                                                                                                    c6 = c3;
                                                                                                    break;
                                                                                                }
                                                                                            }
                                                                                            if (c6 == 0) {
                                                                                            }
                                                                                            if (c5 == 0) {
                                                                                            }
                                                                                        }
                                                                                        c5 = c4;
                                                                                        if (c5 == 0) {
                                                                                        }
                                                                                    }
                                                                                    c5 = c3;
                                                                                    if (c5 == 0) {
                                                                                    }
                                                                                } else {
                                                                                    c5 = c4;
                                                                                    if (c5 == 0) {
                                                                                        c12 = c3;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    } else if (!zIsWriteAsArray) {
                                                                        fieldSerializer3.writePrefix(jSONSerializer);
                                                                    }
                                                                } else if (zIsWriteAsArray) {
                                                                    c3 = 1;
                                                                } else {
                                                                    c3 = 1;
                                                                    serializeWriter.writeFieldName(strProcessKey, true);
                                                                }
                                                                jSONSerializer.write(objProcessValue);
                                                                c4 = 0;
                                                                if (fieldInfo.unwrapped) {
                                                                }
                                                            }
                                                        }
                                                    } else if (cls3 == String.class) {
                                                        int i6 = SerializerFeature.WriteNullStringAsEmpty.mask;
                                                        int i7 = SerializerFeature.WriteMapNullValue.mask | i6;
                                                        if (zIsWriteAsArray || (iOf & i7) != 0 || (i7 & serializeWriter.features) != 0) {
                                                            if ((iOf & i6) != 0 || (serializeWriter.features & i6) != 0) {
                                                                objProcessValue = "";
                                                            }
                                                            if (objProcessValue == null) {
                                                                if (c12 != 0) {
                                                                }
                                                                if (strProcessKey != str2) {
                                                                }
                                                                jSONSerializer.write(objProcessValue);
                                                                c4 = 0;
                                                                if (fieldInfo.unwrapped) {
                                                                }
                                                            }
                                                        }
                                                    } else if (Number.class.isAssignableFrom(cls3)) {
                                                        int i8 = SerializerFeature.WriteNullNumberAsZero.mask;
                                                        int i9 = SerializerFeature.WriteMapNullValue.mask | i8;
                                                        if (zIsWriteAsArray || (iOf & i9) != 0 || (i9 & serializeWriter.features) != 0) {
                                                            if ((iOf & i8) != 0 || (serializeWriter.features & i8) != 0) {
                                                                objProcessValue = 0;
                                                            }
                                                            if (objProcessValue == null) {
                                                            }
                                                        }
                                                    } else if (Collection.class.isAssignableFrom(cls3)) {
                                                        int i10 = SerializerFeature.WriteNullListAsEmpty.mask;
                                                        int i11 = SerializerFeature.WriteMapNullValue.mask | i10;
                                                        if (zIsWriteAsArray || (iOf & i11) != 0 || (i11 & serializeWriter.features) != 0) {
                                                            if ((iOf & i10) != 0 || (serializeWriter.features & i10) != 0) {
                                                                objProcessValue = Collections.emptyList();
                                                            }
                                                            if (objProcessValue == null) {
                                                            }
                                                        }
                                                    } else if (zIsWriteAsArray || fieldSerializer3.writeNull || serializeWriter.isEnabled(SerializerFeature.WriteMapNullValue.mask)) {
                                                        if (objProcessValue == null) {
                                                        }
                                                    }
                                                }
                                            }
                                            fieldSerializer = fieldSerializer2;
                                        }
                                        if (apply(jSONSerializer, obj, str2, propertyValueDirect)) {
                                        }
                                        fieldSerializer = fieldSerializer2;
                                    } catch (Exception e4) {
                                        serialContext = serialContext2;
                                        fieldSerializer = fieldSerializer2;
                                        exc = e4;
                                        String str322 = "write javaBean error, fastjson version 1.2.60";
                                        if (obj != null) {
                                        }
                                        String string222 = str322;
                                        if (obj2 != null) {
                                        }
                                        string222 = sb.toString();
                                        if (exc.getMessage() != null) {
                                        }
                                        if (exc instanceof InvocationTargetException) {
                                        }
                                        throw new JSONException(string222, cause == null ? exc : cause);
                                    }
                                    propertyValueDirect = null;
                                }
                            }
                            i3 = i2 + 1;
                            c9 = c;
                            serialContext3 = serialContext2;
                            fieldSerializerArr = fieldSerializerArr2;
                            c8 = c2;
                            type2 = type;
                        } catch (Exception e5) {
                            e = e5;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        serialContext = serialContext2;
                    }
                } catch (Exception e6) {
                    serialContext = serialContext3;
                    exc = e6;
                }
            }
            char c13 = c9;
            char c14 = c8;
            FieldSerializer[] fieldSerializerArr3 = fieldSerializerArr;
            serialContext2 = serialContext3;
            if (c12 == 0) {
                c13 = 0;
            }
            writeAfter(jSONSerializer, obj, c13);
            if (fieldSerializerArr3.length > 0 && serializeWriter.isEnabled(SerializerFeature.PrettyFormat)) {
                jSONSerializer.decrementIdent();
                jSONSerializer.println();
            }
            if (!z) {
                serializeWriter.append(c14);
            }
            jSONSerializer.context = serialContext2;
            return;
        }
        jSONSerializer.context = serialContext;
        throw th;
    }

    protected char writeAfter(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.afterFilters != null) {
            Iterator<AfterFilter> it = jSONSerializer.afterFilters.iterator();
            while (it.hasNext()) {
                c = it.next().writeAfter(jSONSerializer, obj, c);
            }
        }
        if (this.afterFilters != null) {
            Iterator<AfterFilter> it2 = this.afterFilters.iterator();
            while (it2.hasNext()) {
                c = it2.next().writeAfter(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    public void writeAsArray(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    protected char writeBefore(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.beforeFilters != null) {
            Iterator<BeforeFilter> it = jSONSerializer.beforeFilters.iterator();
            while (it.hasNext()) {
                c = it.next().writeBefore(jSONSerializer, obj, c);
            }
        }
        if (this.beforeFilters != null) {
            Iterator<BeforeFilter> it2 = this.beforeFilters.iterator();
            while (it2.hasNext()) {
                c = it2.next().writeBefore(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    protected void writeClassName(JSONSerializer jSONSerializer, String str, Object obj) {
        if (str == null) {
            str = jSONSerializer.config.typeKey;
        }
        jSONSerializer.out.writeFieldName(str, false);
        String name = this.beanInfo.typeName;
        if (name == null) {
            Class<?> superclass = obj.getClass();
            if (TypeUtils.isProxy(superclass)) {
                superclass = superclass.getSuperclass();
            }
            name = superclass.getName();
        }
        jSONSerializer.write(name);
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeNoneASM(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    public boolean writeReference(JSONSerializer jSONSerializer, Object obj, int i) {
        SerialContext serialContext = jSONSerializer.context;
        int i2 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & i2) != 0 || (i & i2) != 0 || jSONSerializer.references == null || !jSONSerializer.references.containsKey(obj)) {
            return false;
        }
        jSONSerializer.writeReference(obj);
        return true;
    }
}
