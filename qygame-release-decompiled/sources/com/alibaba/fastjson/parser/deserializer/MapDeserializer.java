package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public class MapDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    /* JADX WARN: Removed duplicated region for block: B:36:0x00ad A[Catch: all -> 0x015c, TRY_LEAVE, TryCatch #0 {all -> 0x015c, blocks: (B:10:0x0049, B:12:0x0051, B:15:0x0058, B:17:0x0060, B:19:0x0066, B:21:0x006e, B:23:0x0077, B:25:0x0083, B:26:0x0085, B:34:0x00a4, B:36:0x00ad, B:39:0x00b4, B:40:0x00bb, B:27:0x0088, B:30:0x0091, B:32:0x0095, B:33:0x0098, B:41:0x00bc, B:42:0x00da, B:43:0x00db, B:45:0x00e1, B:47:0x00e7, B:49:0x00f3, B:51:0x00fb, B:53:0x0107, B:56:0x010e, B:57:0x0115, B:59:0x0121, B:61:0x0138, B:62:0x0141, B:63:0x015b), top: B:67:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b4 A[Catch: all -> 0x015c, TRY_ENTER, TryCatch #0 {all -> 0x015c, blocks: (B:10:0x0049, B:12:0x0051, B:15:0x0058, B:17:0x0060, B:19:0x0066, B:21:0x006e, B:23:0x0077, B:25:0x0083, B:26:0x0085, B:34:0x00a4, B:36:0x00ad, B:39:0x00b4, B:40:0x00bb, B:27:0x0088, B:30:0x0091, B:32:0x0095, B:33:0x0098, B:41:0x00bc, B:42:0x00da, B:43:0x00db, B:45:0x00e1, B:47:0x00e7, B:49:0x00f3, B:51:0x00fb, B:53:0x0107, B:56:0x010e, B:57:0x0115, B:59:0x0121, B:61:0x0138, B:62:0x0141, B:63:0x015b), top: B:67:0x0049 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object parseMap(DefaultJSONParser defaultJSONParser, Map<Object, Object> map, Type type, Type type2, Object obj) {
        ParseContext parseContext;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + jSONLexer.tokenName());
        }
        ObjectDeserializer deserializer = defaultJSONParser.getConfig().getDeserializer(type);
        ObjectDeserializer deserializer2 = defaultJSONParser.getConfig().getDeserializer(type2);
        jSONLexer.nextToken(deserializer.getFastMatchToken());
        ParseContext context = defaultJSONParser.getContext();
        while (jSONLexer.token() != 13) {
            try {
                Object obj2 = null;
                if (jSONLexer.token() == 4 && jSONLexer.isRef() && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    jSONLexer.nextTokenWithColon(4);
                    if (jSONLexer.token() != 4) {
                        throw new JSONException("illegal ref, " + JSONToken.name(jSONLexer.token()));
                    }
                    String strStringVal = jSONLexer.stringVal();
                    if ("..".equals(strStringVal)) {
                        parseContext = context.parent;
                    } else {
                        if (!"$".equals(strStringVal)) {
                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(context, strStringVal));
                            defaultJSONParser.setResolveStatus(1);
                            jSONLexer.nextToken(13);
                            if (jSONLexer.token() == 13) {
                                throw new JSONException("illegal ref");
                            }
                            jSONLexer.nextToken(16);
                            return obj2;
                        }
                        parseContext = context;
                        while (parseContext.parent != null) {
                            parseContext = parseContext.parent;
                        }
                    }
                    obj2 = parseContext.object;
                    jSONLexer.nextToken(13);
                    if (jSONLexer.token() == 13) {
                    }
                } else {
                    if (map.size() == 0 && jSONLexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal()) && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                        jSONLexer.nextTokenWithColon(4);
                        jSONLexer.nextToken(16);
                        if (jSONLexer.token() == 13) {
                            jSONLexer.nextToken();
                            return map;
                        }
                        jSONLexer.nextToken(deserializer.getFastMatchToken());
                    }
                    Object objDeserialze = deserializer.deserialze(defaultJSONParser, type, null);
                    if (jSONLexer.token() != 17) {
                        throw new JSONException("syntax error, expect :, actual " + jSONLexer.token());
                    }
                    jSONLexer.nextToken(deserializer2.getFastMatchToken());
                    Object objDeserialze2 = deserializer2.deserialze(defaultJSONParser, type2, objDeserialze);
                    defaultJSONParser.checkMapResolve(map, objDeserialze);
                    map.put(objDeserialze, objDeserialze2);
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(deserializer.getFastMatchToken());
                    }
                }
            } finally {
                defaultJSONParser.setContext(context);
            }
        }
        jSONLexer.nextToken(16);
        return map;
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x01f4, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map parseMap(DefaultJSONParser defaultJSONParser, Map<String, Object> map, Type type, Object obj) {
        String strScanSymbolUnQuoted;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        int i2 = 0;
        if (i != 12) {
            String str = "syntax error, expect {, actual " + jSONLexer.tokenName();
            if (obj instanceof String) {
                str = (str + ", fieldName ") + obj;
            }
            String str2 = (str + ", ") + jSONLexer.info();
            if (i != 4) {
                JSONArray jSONArray = new JSONArray();
                defaultJSONParser.parseArray(jSONArray, obj);
                if (jSONArray.size() == 1) {
                    Object obj2 = jSONArray.get(0);
                    if (obj2 instanceof JSONObject) {
                        return (JSONObject) obj2;
                    }
                }
            }
            throw new JSONException(str2);
        }
        ParseContext context = defaultJSONParser.getContext();
        while (true) {
            try {
                jSONLexer.skipWhitespace();
                char current = jSONLexer.getCurrent();
                if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (current == ',') {
                        jSONLexer.next();
                        jSONLexer.skipWhitespace();
                        current = jSONLexer.getCurrent();
                    }
                }
                if (current == '\"') {
                    strScanSymbolUnQuoted = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable(), '\"');
                    jSONLexer.skipWhitespace();
                    if (jSONLexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + jSONLexer.pos());
                    }
                } else {
                    if (current == '}') {
                        jSONLexer.next();
                        jSONLexer.resetStringPosition();
                        jSONLexer.nextToken(16);
                        return map;
                    }
                    if (current == '\'') {
                        if (!jSONLexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        strScanSymbolUnQuoted = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable(), '\'');
                        jSONLexer.skipWhitespace();
                        if (jSONLexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + jSONLexer.pos());
                        }
                    } else {
                        if (!jSONLexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                            throw new JSONException("syntax error");
                        }
                        strScanSymbolUnQuoted = jSONLexer.scanSymbolUnQuoted(defaultJSONParser.getSymbolTable());
                        jSONLexer.skipWhitespace();
                        char current2 = jSONLexer.getCurrent();
                        if (current2 != ':') {
                            throw new JSONException("expect ':' at " + jSONLexer.pos() + ", actual " + current2);
                        }
                    }
                }
                jSONLexer.next();
                jSONLexer.skipWhitespace();
                jSONLexer.getCurrent();
                jSONLexer.resetStringPosition();
                Object object = null;
                if (strScanSymbolUnQuoted != JSON.DEFAULT_TYPE_KEY || jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    jSONLexer.nextToken();
                    if (i2 != 0) {
                        defaultJSONParser.setContext(context);
                    }
                    if (jSONLexer.token() == 8) {
                        jSONLexer.nextToken();
                    } else {
                        object = defaultJSONParser.parseObject(type, strScanSymbolUnQuoted);
                    }
                    map.put(strScanSymbolUnQuoted, object);
                    defaultJSONParser.checkMapResolve(map, strScanSymbolUnQuoted);
                    defaultJSONParser.setContext(context, object, strScanSymbolUnQuoted);
                    defaultJSONParser.setContext(context);
                    int i3 = jSONLexer.token();
                    if (i3 == 20 || i3 == 15) {
                        break;
                    }
                    if (i3 == 13) {
                        jSONLexer.nextToken();
                        return map;
                    }
                } else {
                    String strScanSymbol = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable(), '\"');
                    ParserConfig config = defaultJSONParser.getConfig();
                    Class<?> clsCheckAutoType = config.checkAutoType(strScanSymbol, null, jSONLexer.getFeatures());
                    if (!Map.class.isAssignableFrom(clsCheckAutoType)) {
                        ObjectDeserializer deserializer = config.getDeserializer(clsCheckAutoType);
                        jSONLexer.nextToken(16);
                        defaultJSONParser.setResolveStatus(2);
                        if (context != null && !(obj instanceof Integer)) {
                            defaultJSONParser.popContext();
                        }
                        return (Map) deserializer.deserialze(defaultJSONParser, clsCheckAutoType, obj);
                    }
                    jSONLexer.nextToken(16);
                    if (jSONLexer.token() == 13) {
                        jSONLexer.nextToken(16);
                        return map;
                    }
                }
                i2++;
            } finally {
                defaultJSONParser.setContext(context);
            }
        }
    }

    public Map<Object, Object> createMap(Type type) {
        return createMap(type, JSON.DEFAULT_GENERATE_FEATURE);
    }

    public Map<Object, Object> createMap(Type type, int i) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class) {
            return (Feature.OrderedField.mask & i) != 0 ? new LinkedHashMap() : new HashMap();
        }
        if (type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return EnumMap.class.equals(rawType) ? new EnumMap((Class) parameterizedType.getActualTypeArguments()[0]) : createMap(rawType, i);
        }
        Class cls = (Class) type;
        if (cls.isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        if ("java.util.Collections$UnmodifiableMap".equals(cls.getName())) {
            return new HashMap();
        }
        try {
            return (Map) cls.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type " + type, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == JSONObject.class && defaultJSONParser.getFieldTypeResolver() == null) {
            return (T) defaultJSONParser.parseObject();
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        boolean z = (type instanceof Class) && "java.util.Collections$UnmodifiableMap".equals(((Class) type).getName());
        Map<Object, Object> mapCreateMap = (jSONLexer.getFeatures() & Feature.OrderedField.mask) != 0 ? createMap(type, jSONLexer.getFeatures()) : createMap(type);
        ParseContext context = defaultJSONParser.getContext();
        try {
            defaultJSONParser.setContext(context, mapCreateMap, obj);
            Map map = (T) deserialze(defaultJSONParser, type, obj, mapCreateMap);
            if (z) {
                map = (T) Collections.unmodifiableMap(map);
            }
            return (T) map;
        } finally {
            defaultJSONParser.setContext(context);
        }
    }

    protected Object deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, Map map) {
        if (!(type instanceof ParameterizedType)) {
            return defaultJSONParser.parseObject(map, obj);
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type type2 = parameterizedType.getActualTypeArguments()[0];
        Type type3 = map.getClass().getName().equals("org.springframework.util.LinkedMultiValueMap") ? List.class : parameterizedType.getActualTypeArguments()[1];
        return String.class == type2 ? parseMap(defaultJSONParser, map, type3, obj) : parseMap(defaultJSONParser, map, type2, type3, obj);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}
