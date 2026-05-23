package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.igexin.push.core.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig parserConfig, Class<?> cls) {
        super(parserConfig, cls, cls);
    }

    private Throwable createException(String str, Throwable th, Class<?> cls) throws Exception {
        Object objNewInstance;
        Constructor<?> constructor = null;
        Constructor<?> constructor2 = null;
        Constructor<?> constructor3 = null;
        for (Constructor<?> constructor4 : cls.getConstructors()) {
            Class<?>[] parameterTypes = constructor4.getParameterTypes();
            if (parameterTypes.length == 0) {
                constructor3 = constructor4;
            } else if (parameterTypes.length == 1 && parameterTypes[0] == String.class) {
                constructor2 = constructor4;
            } else if (parameterTypes.length == 2 && parameterTypes[0] == String.class && parameterTypes[1] == Throwable.class) {
                constructor = constructor4;
            }
        }
        if (constructor != null) {
            objNewInstance = constructor.newInstance(str, th);
        } else if (constructor2 != null) {
            objNewInstance = constructor2.newInstance(str);
        } else {
            if (constructor3 == null) {
                return null;
            }
            objNewInstance = constructor3.newInstance(new Object[0]);
        }
        return (Throwable) objNewInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0128  */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5, types: [com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer] */
    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) throws Throwable {
        Class<?> cls;
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
        } else if (jSONLexer.token() != 12) {
            throw new JSONException("syntax error");
        }
        if (type == null || !(type instanceof Class)) {
            cls = null;
        } else {
            cls = (Class) type;
            if (!Throwable.class.isAssignableFrom(cls)) {
            }
        }
        Class<?> clsCheckAutoType = cls;
        HashMap map = null;
        Throwable th = null;
        String strStringVal = null;
        StackTraceElement[] stackTraceElementArr = null;
        while (true) {
            String strScanSymbol = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable());
            if (strScanSymbol == null) {
                if (jSONLexer.token() == 13) {
                    break;
                }
                if (jSONLexer.token() != 16 || !jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            jSONLexer.nextTokenWithColon(4);
            if (JSON.DEFAULT_TYPE_KEY.equals(strScanSymbol)) {
                if (jSONLexer.token() != 4) {
                    throw new JSONException("syntax error");
                }
                clsCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), Throwable.class, jSONLexer.getFeatures());
                jSONLexer.nextToken(16);
            } else if (b.Z.equals(strScanSymbol)) {
                if (jSONLexer.token() == 8) {
                    strStringVal = null;
                } else {
                    if (jSONLexer.token() != 4) {
                        throw new JSONException("syntax error");
                    }
                    strStringVal = jSONLexer.stringVal();
                }
                jSONLexer.nextToken();
            } else if ("cause".equals(strScanSymbol)) {
                th = (Throwable) deserialze(defaultJSONParser, null, "cause");
            } else if ("stackTrace".equals(strScanSymbol)) {
                stackTraceElementArr = (StackTraceElement[]) defaultJSONParser.parseObject((Class) StackTraceElement[].class);
            } else {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(strScanSymbol, defaultJSONParser.parse());
            }
            if (jSONLexer.token() == 13) {
                break;
            }
        }
        jSONLexer.nextToken(16);
        if (clsCheckAutoType == null) {
            obj2 = (T) new Exception(strStringVal, th);
        } else {
            if (!Throwable.class.isAssignableFrom(clsCheckAutoType)) {
                throw new JSONException("type not match, not Throwable. " + clsCheckAutoType.getName());
            }
            try {
                obj2 = (T) createException(strStringVal, th, clsCheckAutoType);
                if (obj2 == null) {
                    obj2 = (T) new Exception(strStringVal, th);
                }
            } catch (Exception e) {
                throw new JSONException("create instance error", e);
            }
        }
        if (stackTraceElementArr != null) {
            ((Throwable) obj2).setStackTrace(stackTraceElementArr);
        }
        if (map != null) {
            if (clsCheckAutoType != null) {
                Class<?> cls2 = this.clazz;
                ?? r11 = this;
                if (clsCheckAutoType != cls2) {
                    ObjectDeserializer deserializer = defaultJSONParser.getConfig().getDeserializer(clsCheckAutoType);
                    r11 = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : 0;
                }
                if (r11 != 0) {
                    for (Map.Entry entry : map.entrySet()) {
                        String str = (String) entry.getKey();
                        Object value = entry.getValue();
                        FieldDeserializer fieldDeserializer = r11.getFieldDeserializer(str);
                        if (fieldDeserializer != null) {
                            fieldDeserializer.setValue(obj2, value);
                        }
                    }
                }
            }
        }
        return (T) obj2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}
