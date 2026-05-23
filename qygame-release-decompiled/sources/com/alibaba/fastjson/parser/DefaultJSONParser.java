package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    static {
        for (Class<?> cls : new Class[]{Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class}) {
            primitiveClasses.add(cls);
        }
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        JSONLexerBase jSONLexerBase;
        int i;
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            jSONLexerBase = (JSONLexerBase) jSONLexer;
            i = 12;
        } else if (current != '[') {
            jSONLexer.nextToken();
            return;
        } else {
            jSONLexer.next();
            jSONLexerBase = (JSONLexerBase) jSONLexer;
            i = 14;
        }
        jSONLexerBase.token = i;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(str, new JSONScanner(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(cArr, new JSONScanner(cArr, i, i2), parserConfig);
    }

    private void addContext(ParseContext parseContext) {
        ParseContext[] parseContextArr;
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        if (this.contextArray != null) {
            if (i >= this.contextArray.length) {
                parseContextArr = new ParseContext[(this.contextArray.length * 3) / 2];
                System.arraycopy(this.contextArray, 0, parseContextArr, 0, this.contextArray.length);
            }
            this.contextArray[i] = parseContext;
        }
        parseContextArr = new ParseContext[8];
        this.contextArray = parseContextArr;
        this.contextArray[i] = parseContext;
    }

    public final void accept(int i) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public final void accept(int i, int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken(i2);
        } else {
            throwException(i);
        }
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (!str.equals(jSONLexer.stringVal())) {
            throw new JSONException("type not match error");
        }
        jSONLexer.nextToken();
        if (jSONLexer.token() == 16) {
            jSONLexer.nextToken();
        }
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus == 1) {
            if (collection instanceof List) {
                int size = collection.size() - 1;
                ResolveTask lastResolveTask = getLastResolveTask();
                lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, size);
                lastResolveTask.ownerContext = this.context;
            } else {
                ResolveTask lastResolveTask2 = getLastResolveTask();
                lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
                lastResolveTask2.ownerContext = this.context;
            }
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public ParseContext getContext() {
        return this.context;
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public String getInput() {
        return this.input instanceof char[] ? new String((char[]) this.input) : this.input.toString();
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public Object getObject(String str) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (str.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public void handleResovleTask(Object obj) {
        Object objEval;
        if (this.resolveTaskList == null) {
            return;
        }
        int size = this.resolveTaskList.size();
        for (int i = 0; i < size; i++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i);
            String str = resolveTask.referenceValue;
            Object obj2 = resolveTask.ownerContext != null ? resolveTask.ownerContext.object : null;
            if (str.startsWith("$")) {
                objEval = getObject(str);
                if (objEval == null) {
                    try {
                        objEval = JSONPath.eval(obj, str);
                    } catch (JSONPathException unused) {
                    }
                }
            } else {
                objEval = resolveTask.context.object;
            }
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                if (objEval != null && objEval.getClass() == JSONObject.class && fieldDeserializer.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeserializer.fieldInfo.fieldClass)) {
                    objEval = JSONPath.eval(this.contextArray[0].object, str);
                }
                fieldDeserializer.setValue(obj2, objEval);
            }
        }
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public Object parse() {
        return parse(null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0238, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object parse(PropertyProcessable propertyProcessable, Object obj) {
        String strScanSymbolUnQuoted;
        int i = 0;
        if (this.lexer.token() != 12) {
            String str = "syntax error, expect {, actual " + this.lexer.tokenName();
            if (obj instanceof String) {
                str = (str + ", fieldName ") + obj;
            }
            String str2 = (str + ", ") + this.lexer.info();
            JSONArray jSONArray = new JSONArray();
            parseArray(jSONArray, obj);
            if (jSONArray.size() == 1) {
                Object obj2 = jSONArray.get(0);
                if (obj2 instanceof JSONObject) {
                    return (JSONObject) obj2;
                }
            }
            throw new JSONException(str2);
        }
        ParseContext parseContext = this.context;
        while (true) {
            try {
                this.lexer.skipWhitespace();
                char current = this.lexer.getCurrent();
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (current == ',') {
                        this.lexer.next();
                        this.lexer.skipWhitespace();
                        current = this.lexer.getCurrent();
                    }
                }
                if (current == '\"') {
                    strScanSymbolUnQuoted = this.lexer.scanSymbol(this.symbolTable, '\"');
                    this.lexer.skipWhitespace();
                    if (this.lexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + this.lexer.pos());
                    }
                } else {
                    if (current == '}') {
                        this.lexer.next();
                        this.lexer.resetStringPosition();
                        this.lexer.nextToken(16);
                        return propertyProcessable;
                    }
                    if (current == '\'') {
                        if (!this.lexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        strScanSymbolUnQuoted = this.lexer.scanSymbol(this.symbolTable, '\'');
                        this.lexer.skipWhitespace();
                        if (this.lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos());
                        }
                    } else {
                        if (!this.lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                            throw new JSONException("syntax error");
                        }
                        strScanSymbolUnQuoted = this.lexer.scanSymbolUnQuoted(this.symbolTable);
                        this.lexer.skipWhitespace();
                        char current2 = this.lexer.getCurrent();
                        if (current2 != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos() + ", actual " + current2);
                        }
                    }
                }
                this.lexer.next();
                this.lexer.skipWhitespace();
                this.lexer.getCurrent();
                this.lexer.resetStringPosition();
                Object object = null;
                if (strScanSymbolUnQuoted != JSON.DEFAULT_TYPE_KEY || this.lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    this.lexer.nextToken();
                    if (i != 0) {
                        setContext(parseContext);
                    }
                    Type type = propertyProcessable.getType(strScanSymbolUnQuoted);
                    if (this.lexer.token() == 8) {
                        this.lexer.nextToken();
                    } else {
                        object = parseObject(type, strScanSymbolUnQuoted);
                    }
                    propertyProcessable.apply(strScanSymbolUnQuoted, object);
                    setContext(parseContext, object, strScanSymbolUnQuoted);
                    setContext(parseContext);
                    int i2 = this.lexer.token();
                    if (i2 == 20 || i2 == 15) {
                        break;
                    }
                    if (i2 == 13) {
                        this.lexer.nextToken();
                        return propertyProcessable;
                    }
                } else {
                    Class<?> clsCheckAutoType = this.config.checkAutoType(this.lexer.scanSymbol(this.symbolTable, '\"'), null, this.lexer.getFeatures());
                    if (!Map.class.isAssignableFrom(clsCheckAutoType)) {
                        ObjectDeserializer deserializer = this.config.getDeserializer(clsCheckAutoType);
                        this.lexer.nextToken(16);
                        setResolveStatus(2);
                        if (parseContext != null && !(obj instanceof Integer)) {
                            popContext();
                        }
                        return (Map) deserializer.deserialze(this, clsCheckAutoType, obj);
                    }
                    this.lexer.nextToken(16);
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return propertyProcessable;
                    }
                }
                i++;
            } finally {
                setContext(parseContext);
            }
        }
    }

    public Object parse(Object obj) {
        Collection hashSet;
        JSONLexer jSONLexer = this.lexer;
        switch (jSONLexer.token()) {
            case 2:
                Number numberIntegerValue = jSONLexer.integerValue();
                jSONLexer.nextToken();
                return numberIntegerValue;
            case 3:
                Number numberDecimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
                jSONLexer.nextToken();
                return numberDecimalValue;
            case 4:
                String strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner jSONScanner = new JSONScanner(strStringVal);
                    try {
                        if (jSONScanner.scanISO8601DateIfMatch()) {
                            return jSONScanner.getCalendar().getTime();
                        }
                    } finally {
                        jSONScanner.close();
                    }
                }
                return strStringVal;
            case 5:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 24:
            case 25:
            default:
                throw new JSONException("syntax error, " + jSONLexer.info());
            case 6:
                jSONLexer.nextToken();
                return Boolean.TRUE;
            case 7:
                jSONLexer.nextToken();
                return Boolean.FALSE;
            case 8:
                jSONLexer.nextToken();
                return null;
            case 9:
                jSONLexer.nextToken(18);
                if (jSONLexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken(10);
                accept(10);
                long jLongValue = jSONLexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(jLongValue);
            case 12:
                return parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), obj);
            case 14:
                JSONArray jSONArray = new JSONArray();
                parseArray(jSONArray, obj);
                return jSONLexer.isEnabled(Feature.UseObjectArray) ? jSONArray.toArray() : jSONArray;
            case 18:
                if ("NaN".equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + jSONLexer.info());
            case 20:
                if (jSONLexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + jSONLexer.info());
            case 21:
                jSONLexer.nextToken();
                hashSet = new HashSet();
                break;
            case 22:
                jSONLexer.nextToken();
                hashSet = new TreeSet();
                break;
            case 23:
                jSONLexer.nextToken();
                return null;
            case 26:
                byte[] bArrBytesValue = jSONLexer.bytesValue();
                jSONLexer.nextToken();
                return bArrBytesValue;
        }
        parseArray(hashSet, obj);
        return hashSet;
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0056 A[Catch: all -> 0x00e0, LOOP:1: B:20:0x0056->B:22:0x005e, LOOP_START, TryCatch #0 {all -> 0x00e0, blocks: (B:18:0x004a, B:20:0x0056, B:22:0x005e, B:23:0x0064, B:28:0x0077, B:30:0x007c, B:46:0x00cb, B:48:0x00d3, B:33:0x008a, B:35:0x0092, B:40:0x00a9, B:36:0x009e, B:39:0x00a5, B:41:0x00ad, B:43:0x00b7, B:45:0x00c5, B:44:0x00bd), top: B:55:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0077 A[Catch: all -> 0x00e0, TRY_ENTER, TryCatch #0 {all -> 0x00e0, blocks: (B:18:0x004a, B:20:0x0056, B:22:0x005e, B:23:0x0064, B:28:0x0077, B:30:0x007c, B:46:0x00cb, B:48:0x00d3, B:33:0x008a, B:35:0x0092, B:40:0x00a9, B:36:0x009e, B:39:0x00a5, B:41:0x00ad, B:43:0x00b7, B:45:0x00c5, B:44:0x00bd), top: B:55:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x006e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        JSONLexer jSONLexer;
        int fastMatchToken;
        int i;
        int i2 = this.lexer.token();
        if (i2 == 21 || i2 == 22) {
            this.lexer.nextToken();
            i2 = this.lexer.token();
        }
        if (i2 != 14) {
            throw new JSONException("expect '[', but " + JSONToken.name(i2) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            jSONLexer = this.lexer;
            fastMatchToken = 2;
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
            ParseContext parseContext = this.context;
            setContext(collection, obj);
            i = 0;
            while (true) {
                try {
                    if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() != 15) {
                        setContext(parseContext);
                        this.lexer.nextToken(16);
                        return;
                    }
                    Object objDeserialze = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            objDeserialze = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 != null) {
                                objDeserialze = obj2.toString();
                            }
                        }
                        collection.add(objDeserialze);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        collection.add(objDeserialze);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i++;
                } catch (Throwable th) {
                    setContext(parseContext);
                    throw th;
                }
            }
        } else {
            deserializer = this.config.getDeserializer(type);
            jSONLexer = this.lexer;
            fastMatchToken = deserializer.getFastMatchToken();
        }
        jSONLexer.nextToken(fastMatchToken);
        ParseContext parseContext2 = this.context;
        setContext(collection, obj);
        i = 0;
        while (true) {
            if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
            }
            if (this.lexer.token() != 15) {
            }
            i++;
        }
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00df A[Catch: all -> 0x00e6, TRY_LEAVE, TryCatch #0 {all -> 0x00e6, blocks: (B:10:0x0028, B:12:0x0032, B:14:0x0038, B:15:0x003c, B:16:0x0041, B:17:0x0044, B:43:0x00d3, B:45:0x00df, B:18:0x004a, B:19:0x004f, B:20:0x0056, B:21:0x0057, B:24:0x005e, B:26:0x0072, B:27:0x0077, B:28:0x008b, B:29:0x008d, B:30:0x0091, B:31:0x0094, B:33:0x00a3, B:35:0x00ae, B:36:0x00b6, B:37:0x00ba, B:39:0x00c2, B:41:0x00c9, B:42:0x00ce), top: B:52:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00e2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parseArray(Collection collection, Object obj) {
        Object objDecimalValue;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() != 14) {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
        jSONLexer.nextToken(4);
        ParseContext parseContext = this.context;
        setContext(collection, obj);
        int i = 0;
        while (true) {
            try {
                if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (jSONLexer.token() == 16) {
                        jSONLexer.nextToken();
                    }
                }
                Object object = null;
                switch (jSONLexer.token()) {
                    case 2:
                        objDecimalValue = jSONLexer.integerValue();
                        jSONLexer.nextToken(16);
                        object = objDecimalValue;
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                            jSONLexer.nextToken(4);
                        }
                        i++;
                        break;
                    case 3:
                        objDecimalValue = jSONLexer.isEnabled(Feature.UseBigDecimal) ? jSONLexer.decimalValue(true) : jSONLexer.decimalValue(false);
                        jSONLexer.nextToken(16);
                        object = objDecimalValue;
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 4:
                        String strStringVal = jSONLexer.stringVal();
                        jSONLexer.nextToken(16);
                        object = strStringVal;
                        if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner jSONScanner = new JSONScanner(strStringVal);
                            Object time = strStringVal;
                            if (jSONScanner.scanISO8601DateIfMatch()) {
                                time = jSONScanner.getCalendar().getTime();
                            }
                            jSONScanner.close();
                            object = time;
                        }
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 6:
                        objDecimalValue = Boolean.TRUE;
                        jSONLexer.nextToken(16);
                        object = objDecimalValue;
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 7:
                        objDecimalValue = Boolean.FALSE;
                        jSONLexer.nextToken(16);
                        object = objDecimalValue;
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 8:
                    case 23:
                        jSONLexer.nextToken(4);
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 12:
                        object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i));
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 14:
                        JSONArray jSONArray = new JSONArray();
                        parseArray(jSONArray, Integer.valueOf(i));
                        object = jSONArray;
                        if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                            object = jSONArray.toArray();
                        }
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                    case 15:
                        jSONLexer.nextToken(16);
                        return;
                    case 20:
                        throw new JSONException("unclosed jsonArray");
                    default:
                        object = parse();
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                        }
                        i++;
                        break;
                }
            } finally {
                setContext(parseContext);
            }
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object objValueOf;
        Class<?> componentType;
        boolean zIsArray;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] objArr = new Object[typeArr.length];
        if (typeArr.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        for (int i = 0; i < typeArr.length; i++) {
            if (this.lexer.token() == 8) {
                this.lexer.nextToken(16);
                objValueOf = null;
            } else {
                Type type = typeArr[i];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        objValueOf = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    }
                    objValueOf = TypeUtils.cast(parse(), type, this.config);
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        objValueOf = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    }
                    objValueOf = TypeUtils.cast(parse(), type, this.config);
                } else {
                    if (i == typeArr.length - 1 && (type instanceof Class)) {
                        Class cls = (Class) type;
                        zIsArray = cls.isArray();
                        componentType = cls.getComponentType();
                    } else {
                        componentType = null;
                        zIsArray = false;
                    }
                    if (!zIsArray || this.lexer.token() == 14) {
                        objValueOf = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i));
                    } else {
                        ArrayList arrayList = new ArrayList();
                        ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                        int fastMatchToken = deserializer.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                arrayList.add(deserializer.deserialze(this, type, null));
                                if (this.lexer.token() != 16) {
                                    break;
                                }
                                this.lexer.nextToken(fastMatchToken);
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        objValueOf = TypeUtils.cast(arrayList, type, this.config);
                    }
                }
            }
            objArr[i] = objValueOf;
            if (this.lexer.token() == 15) {
                break;
            }
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i == typeArr.length - 1) {
                this.lexer.nextToken(15);
            } else {
                this.lexer.nextToken(2);
            }
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return objArr;
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class<?>) type2, (Collection) arrayList);
            return arrayList;
        }
        if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (!Object.class.equals(type3)) {
                ArrayList arrayList2 = new ArrayList();
                parseArray((Class<?>) type3, (Collection) arrayList2);
                return arrayList2;
            }
            if (wildcardType.getLowerBounds().length == 0) {
                return parse();
            }
            throw new JSONException("not support type : " + type);
        }
        if (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type type4 = bounds[0];
            if (type4 instanceof Class) {
                ArrayList arrayList3 = new ArrayList();
                parseArray((Class<?>) type4, (Collection) arrayList3);
                return arrayList3;
            }
        }
        if (type2 instanceof ParameterizedType) {
            ArrayList arrayList4 = new ArrayList();
            parseArray((ParameterizedType) type2, arrayList4);
            return arrayList4;
        }
        throw new JSONException("TODO : " + type);
    }

    public void parseExtra(Object obj, String str) {
        this.lexer.nextTokenWithColon();
        Type extraType = null;
        if (this.extraTypeProviders != null) {
            Iterator<ExtraTypeProvider> it = this.extraTypeProviders.iterator();
            while (it.hasNext()) {
                extraType = it.next().getExtraType(obj, str);
            }
        }
        Object object = extraType == null ? parse() : parseObject(extraType);
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, object);
            return;
        }
        if (this.extraProcessors != null) {
            Iterator<ExtraProcessor> it2 = this.extraProcessors.iterator();
            while (it2.hasNext()) {
                it2.next().processExtra(obj, str, object);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse(null);
        }
        String strStringVal = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return strStringVal;
    }

    public JSONObject parseObject() {
        Object object = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        if (object == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) object);
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() != JavaBeanDeserializer.class) {
                return (T) deserializer.deserialze(this, type, obj);
            }
            if (this.lexer.token() != 12 && this.lexer.token() != 14) {
                throw new JSONException("syntax error,except start with { or [,but actually start with " + this.lexer.tokenName());
            }
            return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:132:0x0280, code lost:
    
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x028b, code lost:
    
        if (r3.token() != 13) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x028d, code lost:
    
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0290, code lost:
    
        r0 = r18.config.getDeserializer(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0298, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x029a, code lost:
    
        r0 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r0;
        r2 = r0.createInstance(r18, r6);
        r3 = r8.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x02ac, code lost:
    
        if (r3.hasNext() == false) goto L407;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x02ae, code lost:
    
        r4 = (java.util.Map.Entry) r3.next();
        r7 = r4.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x02ba, code lost:
    
        if ((r7 instanceof java.lang.String) == false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02bc, code lost:
    
        r7 = r0.getFieldDeserializer((java.lang.String) r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02c2, code lost:
    
        if (r7 == null) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02c4, code lost:
    
        r7.setValue(r2, r4.getValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02cc, code lost:
    
        r2 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x02cd, code lost:
    
        if (r2 != null) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02d1, code lost:
    
        if (r6 != java.lang.Cloneable.class) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02d3, code lost:
    
        r2 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02df, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r5) == false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02e1, code lost:
    
        r2 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02ec, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r5) == false) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02ee, code lost:
    
        r2 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02f8, code lost:
    
        r2 = r6.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02fc, code lost:
    
        setContext(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02ff, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0300, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0308, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0309, code lost:
    
        setResolveStatus(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x030f, code lost:
    
        if (r18.context == null) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0311, code lost:
    
        if (r20 == null) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0315, code lost:
    
        if ((r20 instanceof java.lang.Integer) != false) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x031d, code lost:
    
        if ((r18.context.fieldName instanceof java.lang.Integer) != false) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x031f, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0326, code lost:
    
        if (r19.size() <= 0) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0328, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r19, (java.lang.Class<java.lang.Object>) r6, r18.config);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0331, code lost:
    
        setContext(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0334, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0335, code lost:
    
        r0 = r18.config.getDeserializer(r6);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0345, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0349, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x034d, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x034f, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0356, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0359, code lost:
    
        r0 = r0.deserialze(r18, r6, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x035d, code lost:
    
        setContext(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0360, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0210 A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x044d A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:260:0x047b A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04a4  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x04cd A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x05d2  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x05d7 A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:337:0x05e3 A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x05ef A[Catch: all -> 0x068c, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0604 A[Catch: all -> 0x068c, TRY_ENTER, TryCatch #2 {all -> 0x068c, blocks: (B:30:0x007f, B:33:0x0092, B:36:0x00ac, B:106:0x0210, B:107:0x0216, B:109:0x0221, B:111:0x0229, B:115:0x0240, B:117:0x024e, B:131:0x027a, B:132:0x0280, B:134:0x028d, B:135:0x0290, B:137:0x029a, B:138:0x02a8, B:140:0x02ae, B:142:0x02bc, B:144:0x02c4, B:149:0x02d3, B:150:0x02d9, B:152:0x02e1, B:153:0x02e6, B:155:0x02ee, B:156:0x02f8, B:160:0x0301, B:161:0x0308, B:162:0x0309, B:165:0x0313, B:167:0x0317, B:169:0x031f, B:170:0x0322, B:172:0x0328, B:175:0x0335, B:181:0x034f, B:185:0x0359, B:182:0x0354, B:119:0x0255, B:121:0x025b, B:125:0x0264, B:128:0x026a, B:192:0x0369, B:194:0x0371, B:196:0x037b, B:198:0x038c, B:200:0x0397, B:202:0x039f, B:204:0x03a3, B:206:0x03ab, B:209:0x03b0, B:211:0x03b4, B:231:0x03ff, B:233:0x0407, B:236:0x0410, B:237:0x042a, B:212:0x03b9, B:214:0x03c1, B:216:0x03c5, B:217:0x03c8, B:218:0x03d0, B:219:0x03d4, B:222:0x03dd, B:224:0x03e1, B:225:0x03e4, B:227:0x03e8, B:228:0x03ec, B:229:0x03f5, B:238:0x042b, B:239:0x0449, B:242:0x044d, B:244:0x0451, B:246:0x0457, B:248:0x045d, B:249:0x0461, B:254:0x046b, B:260:0x047b, B:262:0x048a, B:264:0x0495, B:265:0x049d, B:266:0x04a0, B:274:0x04c4, B:276:0x04cd, B:279:0x04d8, B:282:0x04e8, B:283:0x050a, B:270:0x04aa, B:272:0x04b4, B:273:0x04b9, B:286:0x050f, B:288:0x0519, B:290:0x0521, B:291:0x0524, B:293:0x052f, B:294:0x0533, B:296:0x053e, B:299:0x0545, B:302:0x0552, B:303:0x0559, B:306:0x055e, B:308:0x0563, B:312:0x056f, B:314:0x0577, B:316:0x058c, B:320:0x05ab, B:322:0x05b1, B:325:0x05b7, B:327:0x05bd, B:329:0x05c5, B:332:0x05d7, B:335:0x05df, B:337:0x05e3, B:338:0x05ea, B:340:0x05ef, B:341:0x05f2, B:343:0x05fa, B:346:0x0604, B:349:0x060e, B:350:0x0613, B:351:0x0618, B:352:0x0632, B:317:0x0597, B:318:0x059e, B:353:0x0633, B:355:0x0645, B:358:0x064c, B:361:0x0659, B:362:0x067b, B:39:0x00be, B:40:0x00e0, B:42:0x00e3, B:44:0x00ee, B:46:0x00f2, B:48:0x00f8, B:50:0x00fe, B:52:0x0102, B:59:0x0111, B:61:0x0119, B:64:0x0129, B:65:0x0143, B:66:0x0144, B:67:0x014b, B:74:0x0158, B:75:0x015e, B:77:0x0165, B:79:0x016e, B:81:0x0176, B:83:0x017b, B:86:0x0183, B:87:0x019d, B:78:0x016a, B:88:0x019e, B:89:0x01b8, B:95:0x01c2, B:97:0x01ca, B:100:0x01db, B:101:0x01fd, B:102:0x01fe, B:103:0x0205, B:104:0x0206, B:363:0x067c, B:364:0x0683, B:365:0x0684, B:366:0x068b), top: B:376:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:394:0x04d6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x05fa A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object parseObject(Map map, Object obj) throws Throwable {
        ParseContext parseContext;
        ParseContext context;
        Object objScanSymbolUnQuoted;
        boolean z;
        char current;
        char c;
        Map jSONObject;
        boolean z2;
        Object object;
        Object objIntegerValue;
        char current2;
        char c2;
        Object obj2;
        Object obj3;
        Class<?> clsCheckAutoType;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() == 13) {
            jSONLexer.nextToken();
            return map;
        }
        if (jSONLexer.token() == 4 && jSONLexer.stringVal().length() == 0) {
            jSONLexer.nextToken();
            return map;
        }
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + jSONLexer.tokenName() + ", " + jSONLexer.info());
        }
        ParseContext parseContext2 = this.context;
        try {
            Map innerMap = map instanceof JSONObject ? ((JSONObject) map).getInnerMap() : map;
            parseContext = parseContext2;
            boolean z3 = false;
            while (true) {
                try {
                    jSONLexer.skipWhitespace();
                    char current3 = jSONLexer.getCurrent();
                    if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (current3 == ',') {
                            jSONLexer.next();
                            jSONLexer.skipWhitespace();
                            current3 = jSONLexer.getCurrent();
                        }
                    }
                    boolean z4 = true;
                    if (current3 == '\"') {
                        objScanSymbolUnQuoted = jSONLexer.scanSymbol(this.symbolTable, '\"');
                        jSONLexer.skipWhitespace();
                        if (jSONLexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + jSONLexer.pos() + ", name " + objScanSymbolUnQuoted);
                        }
                    } else {
                        if (current3 == '}') {
                            jSONLexer.next();
                            jSONLexer.resetStringPosition();
                            jSONLexer.nextToken();
                            if (!z3) {
                                if (this.context != null && obj == this.context.fieldName && map == this.context.object) {
                                    context = this.context;
                                } else {
                                    context = setContext(map, obj);
                                    if (parseContext == null) {
                                    }
                                }
                                parseContext = context;
                            }
                            setContext(parseContext);
                            return map;
                        }
                        if (current3 == '\'') {
                            if (!jSONLexer.isEnabled(Feature.AllowSingleQuotes)) {
                                throw new JSONException("syntax error");
                            }
                            objScanSymbolUnQuoted = jSONLexer.scanSymbol(this.symbolTable, '\'');
                            jSONLexer.skipWhitespace();
                            if (jSONLexer.getCurrent() != ':') {
                                throw new JSONException("expect ':' at " + jSONLexer.pos());
                            }
                        } else {
                            if (current3 == 26) {
                                throw new JSONException("syntax error");
                            }
                            if (current3 == ',') {
                                throw new JSONException("syntax error");
                            }
                            if ((current3 < '0' || current3 > '9') && current3 != '-') {
                                if (current3 != '{' && current3 != '[') {
                                    if (!jSONLexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                                        throw new JSONException("syntax error");
                                    }
                                    objScanSymbolUnQuoted = jSONLexer.scanSymbolUnQuoted(this.symbolTable);
                                    jSONLexer.skipWhitespace();
                                    char current4 = jSONLexer.getCurrent();
                                    if (current4 != ':') {
                                        throw new JSONException("expect ':' at " + jSONLexer.pos() + ", actual " + current4);
                                    }
                                }
                                jSONLexer.nextToken();
                                objScanSymbolUnQuoted = parse();
                                z = true;
                                if (!z) {
                                    jSONLexer.next();
                                    jSONLexer.skipWhitespace();
                                }
                                current = jSONLexer.getCurrent();
                                jSONLexer.resetStringPosition();
                                if (objScanSymbolUnQuoted != JSON.DEFAULT_TYPE_KEY && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                                    String strScanSymbol = jSONLexer.scanSymbol(this.symbolTable, '\"');
                                    if (!jSONLexer.isEnabled(Feature.IgnoreAutoType)) {
                                        if (map == null || !map.getClass().getName().equals(strScanSymbol)) {
                                            for (int i = 0; i < strScanSymbol.length(); i++) {
                                                char cCharAt = strScanSymbol.charAt(i);
                                                if (cCharAt >= '0' && cCharAt <= '9') {
                                                }
                                                z4 = false;
                                            }
                                            if (z4) {
                                                obj3 = null;
                                                clsCheckAutoType = null;
                                            } else {
                                                obj3 = null;
                                                clsCheckAutoType = this.config.checkAutoType(strScanSymbol, null, jSONLexer.getFeatures());
                                            }
                                        } else {
                                            clsCheckAutoType = map.getClass();
                                            obj3 = null;
                                        }
                                        if (clsCheckAutoType != null) {
                                            break;
                                        }
                                        innerMap.put(JSON.DEFAULT_TYPE_KEY, strScanSymbol);
                                    }
                                    c2 = 4;
                                } else if (objScanSymbolUnQuoted == "$ref" || parseContext == null || jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                                    if (!z3) {
                                        if (this.context != null && obj == this.context.fieldName && map == this.context.object) {
                                            parseContext = this.context;
                                        } else {
                                            ParseContext context2 = setContext(map, obj);
                                            if (parseContext != null) {
                                                context2 = parseContext;
                                            }
                                            parseContext = context2;
                                            z3 = true;
                                        }
                                    }
                                    if (map.getClass() == JSONObject.class && objScanSymbolUnQuoted == null) {
                                        objScanSymbolUnQuoted = "null";
                                    }
                                    if (current != '\"') {
                                        jSONLexer.scanString();
                                        String strStringVal = jSONLexer.stringVal();
                                        objIntegerValue = strStringVal;
                                        if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                                            JSONScanner jSONScanner = new JSONScanner(strStringVal);
                                            Object time = strStringVal;
                                            if (jSONScanner.scanISO8601DateIfMatch()) {
                                                time = jSONScanner.getCalendar().getTime();
                                            }
                                            jSONScanner.close();
                                            objIntegerValue = time;
                                        }
                                    } else if ((current >= '0' && current <= '9') || current == '-') {
                                        jSONLexer.scanNumber();
                                        objIntegerValue = jSONLexer.token() == 2 ? jSONLexer.integerValue() : jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
                                    } else if (current == '[') {
                                        jSONLexer.nextToken();
                                        JSONArray jSONArray = new JSONArray();
                                        if (obj != null) {
                                            obj.getClass();
                                        }
                                        if (obj == null) {
                                            setContext(parseContext);
                                        }
                                        parseArray(jSONArray, objScanSymbolUnQuoted);
                                        JSONArray array = jSONArray;
                                        if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                            array = jSONArray.toArray();
                                        }
                                        innerMap.put(objScanSymbolUnQuoted, array);
                                        if (jSONLexer.token() == 13) {
                                            jSONLexer.nextToken();
                                            setContext(parseContext);
                                            return map;
                                        }
                                        if (jSONLexer.token() != 16) {
                                            throw new JSONException("syntax error");
                                        }
                                        c = 16;
                                    } else if (current == '{') {
                                        jSONLexer.nextToken();
                                        boolean z5 = obj != null && obj.getClass() == Integer.class;
                                        if (jSONLexer.isEnabled(Feature.CustomMapDeserializer)) {
                                            MapDeserializer mapDeserializer = (MapDeserializer) this.config.getDeserializer(Map.class);
                                            jSONObject = (jSONLexer.getFeatures() & Feature.OrderedField.mask) != 0 ? mapDeserializer.createMap(Map.class, jSONLexer.getFeatures()) : mapDeserializer.createMap(Map.class);
                                        } else {
                                            jSONObject = new JSONObject(jSONLexer.isEnabled(Feature.OrderedField));
                                        }
                                        ParseContext context3 = !z5 ? setContext(parseContext, jSONObject, objScanSymbolUnQuoted) : null;
                                        if (this.fieldTypeResolver == null) {
                                            z2 = false;
                                            object = null;
                                            if (!z2) {
                                                object = parseObject(jSONObject, objScanSymbolUnQuoted);
                                            }
                                            if (context3 != null && jSONObject != object) {
                                                context3.object = map;
                                            }
                                            if (objScanSymbolUnQuoted != null) {
                                                checkMapResolve(map, objScanSymbolUnQuoted.toString());
                                            }
                                            innerMap.put(objScanSymbolUnQuoted, object);
                                            if (z5) {
                                                setContext(object, objScanSymbolUnQuoted);
                                            }
                                            if (jSONLexer.token() != 13) {
                                                jSONLexer.nextToken();
                                                setContext(parseContext);
                                                setContext(parseContext);
                                                return map;
                                            }
                                            if (jSONLexer.token() != 16) {
                                                throw new JSONException("syntax error, " + jSONLexer.tokenName());
                                            }
                                            if (z5) {
                                                popContext();
                                            } else {
                                                setContext(parseContext);
                                            }
                                            c = 16;
                                        } else {
                                            Type typeResolve = this.fieldTypeResolver.resolve(map, objScanSymbolUnQuoted != null ? objScanSymbolUnQuoted.toString() : null);
                                            if (typeResolve != null) {
                                                object = this.config.getDeserializer(typeResolve).deserialze(this, typeResolve, objScanSymbolUnQuoted);
                                                z2 = true;
                                            }
                                            if (!z2) {
                                            }
                                            if (context3 != null) {
                                                context3.object = map;
                                            }
                                            if (objScanSymbolUnQuoted != null) {
                                            }
                                            innerMap.put(objScanSymbolUnQuoted, object);
                                            if (z5) {
                                            }
                                            if (jSONLexer.token() != 13) {
                                            }
                                        }
                                    } else {
                                        jSONLexer.nextToken();
                                        innerMap.put(objScanSymbolUnQuoted, parse());
                                        if (jSONLexer.token() == 13) {
                                            jSONLexer.nextToken();
                                            setContext(parseContext);
                                            return map;
                                        }
                                        c = 16;
                                        if (jSONLexer.token() != 16) {
                                            throw new JSONException("syntax error, position at " + jSONLexer.pos() + ", name " + objScanSymbolUnQuoted);
                                        }
                                    }
                                    innerMap.put(objScanSymbolUnQuoted, objIntegerValue);
                                    jSONLexer.skipWhitespace();
                                    current2 = jSONLexer.getCurrent();
                                    if (current2 == ',') {
                                        if (current2 == '}') {
                                            jSONLexer.next();
                                            jSONLexer.resetStringPosition();
                                            jSONLexer.nextToken();
                                            setContext(objIntegerValue, objScanSymbolUnQuoted);
                                            setContext(parseContext);
                                            return map;
                                        }
                                        throw new JSONException("syntax error, position at " + jSONLexer.pos() + ", name " + objScanSymbolUnQuoted);
                                    }
                                    jSONLexer.next();
                                    c = 16;
                                } else {
                                    c2 = 4;
                                    jSONLexer.nextToken(4);
                                    if (jSONLexer.token() != 4) {
                                        throw new JSONException("illegal ref, " + JSONToken.name(jSONLexer.token()));
                                    }
                                    String strStringVal2 = jSONLexer.stringVal();
                                    jSONLexer.nextToken(13);
                                    if (jSONLexer.token() != 16) {
                                        if (!"@".equals(strStringVal2)) {
                                            if (!"..".equals(strStringVal2)) {
                                                if ("$".equals(strStringVal2)) {
                                                    ParseContext parseContext3 = parseContext;
                                                    while (parseContext3.parent != null) {
                                                        parseContext3 = parseContext3.parent;
                                                    }
                                                    if (parseContext3.object != null) {
                                                        obj2 = parseContext3.object;
                                                    } else {
                                                        addResolveTask(new ResolveTask(parseContext3, strStringVal2));
                                                    }
                                                } else {
                                                    addResolveTask(new ResolveTask(parseContext, strStringVal2));
                                                }
                                                setResolveStatus(1);
                                            } else if (parseContext.object != null) {
                                                obj2 = parseContext.object;
                                            } else {
                                                addResolveTask(new ResolveTask(parseContext, strStringVal2));
                                                setResolveStatus(1);
                                            }
                                        } else if (this.context != null) {
                                            ParseContext parseContext4 = this.context;
                                            obj2 = parseContext4.object;
                                            if (!(obj2 instanceof Object[]) && !(obj2 instanceof Collection)) {
                                                obj2 = parseContext4.parent != null ? parseContext4.parent.object : null;
                                            }
                                        }
                                        if (jSONLexer.token() == 13) {
                                            jSONLexer.nextToken(16);
                                            setContext(parseContext);
                                            return obj2;
                                        }
                                        throw new JSONException("syntax error, " + jSONLexer.info());
                                    }
                                    innerMap.put(objScanSymbolUnQuoted, strStringVal2);
                                }
                            } else {
                                jSONLexer.resetStringPosition();
                                jSONLexer.scanNumber();
                                try {
                                    Object objIntegerValue2 = jSONLexer.token() == 2 ? jSONLexer.integerValue() : jSONLexer.decimalValue(true);
                                    Object string = objIntegerValue2;
                                    if (jSONLexer.isEnabled(Feature.NonStringKeyAsString)) {
                                        string = objIntegerValue2.toString();
                                    }
                                    objScanSymbolUnQuoted = string;
                                    if (jSONLexer.getCurrent() != ':') {
                                        throw new JSONException("parse number key error" + jSONLexer.info());
                                    }
                                } catch (NumberFormatException unused) {
                                    throw new JSONException("parse number key error" + jSONLexer.info());
                                }
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                    current = jSONLexer.getCurrent();
                    jSONLexer.resetStringPosition();
                    if (objScanSymbolUnQuoted != JSON.DEFAULT_TYPE_KEY) {
                        if (objScanSymbolUnQuoted == "$ref") {
                        }
                        if (!z3) {
                        }
                        if (map.getClass() == JSONObject.class) {
                            objScanSymbolUnQuoted = "null";
                        }
                        if (current != '\"') {
                        }
                        innerMap.put(objScanSymbolUnQuoted, objIntegerValue);
                        jSONLexer.skipWhitespace();
                        current2 = jSONLexer.getCurrent();
                        if (current2 == ',') {
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    setContext(parseContext);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            parseContext = parseContext2;
        }
    }

    public void parseObject(Object obj) {
        Object objDeserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String strScanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (strScanSymbol == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(strScanSymbol) : null;
            if (fieldDeserializer != null) {
                Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                Type type = fieldDeserializer.fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    objDeserialze = StringCodec.deserialze(this);
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = LongCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                    objDeserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, objDeserialze);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            } else {
                if (!this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + strScanSymbol);
                }
                this.lexer.nextTokenWithColon();
                parse();
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken();
                    return;
                }
            }
        }
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        if (this.contextArrayIndex <= 0) {
            return;
        }
        this.contextArrayIndex--;
        this.contextArray[this.contextArrayIndex] = null;
    }

    public Object resolveReference(String str) {
        if (this.contextArray == null) {
            return null;
        }
        for (int i = 0; i < this.contextArray.length && i < this.contextArrayIndex; i++) {
            ParseContext parseContext = this.contextArray[i];
            if (parseContext.toString().equals(str)) {
                return parseContext.object;
            }
        }
        return null;
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parseContext, obj, obj2);
        addContext(this.context);
        return this.context;
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setResolveStatus(int i) {
        this.resolveStatus = i;
    }

    public void throwException(int i) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(this.lexer.token()));
    }
}
