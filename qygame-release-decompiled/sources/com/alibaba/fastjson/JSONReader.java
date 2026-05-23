package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.alibaba.fastjson.util.TypeUtils;
import com.gme.av.sdk.AVError;
import java.io.Closeable;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class JSONReader implements Closeable {
    private JSONStreamContext context;
    private final DefaultJSONParser parser;

    public JSONReader(DefaultJSONParser defaultJSONParser) {
        this.parser = defaultJSONParser;
    }

    public JSONReader(JSONLexer jSONLexer) {
        this(new DefaultJSONParser(jSONLexer));
    }

    public JSONReader(Reader reader) {
        this(reader, new Feature[0]);
    }

    public JSONReader(Reader reader, Feature... featureArr) {
        this(new JSONReaderScanner(reader));
        for (Feature feature : featureArr) {
            config(feature, true);
        }
    }

    private void endStructure() {
        int i;
        this.context = this.context.parent;
        if (this.context == null) {
            return;
        }
        switch (this.context.state) {
            case 1001:
            case 1003:
                i = 1002;
                break;
            case 1002:
                i = 1003;
                break;
            case 1004:
                i = AVError.AV_ERR_TIMEOUT;
                break;
            default:
                i = -1;
                break;
        }
        if (i != -1) {
            this.context.state = i;
        }
    }

    private void readAfter() {
        int i = this.context.state;
        int i2 = 1002;
        switch (i) {
            case 1001:
            case 1003:
                break;
            case 1002:
                i2 = 1003;
                break;
            case 1004:
                i2 = AVError.AV_ERR_TIMEOUT;
                break;
            case AVError.AV_ERR_TIMEOUT /* 1005 */:
                i2 = -1;
                break;
            default:
                throw new JSONException("illegal state : " + i);
        }
        if (i2 != -1) {
            this.context.state = i2;
        }
    }

    private void readBefore() {
        int i = this.context.state;
        switch (i) {
            case 1001:
            case 1004:
                return;
            case 1002:
                this.parser.accept(17);
                return;
            case 1003:
                this.parser.accept(16, 18);
                return;
            case AVError.AV_ERR_TIMEOUT /* 1005 */:
                this.parser.accept(16);
                return;
            default:
                throw new JSONException("illegal state : " + i);
        }
    }

    private void startStructure() {
        DefaultJSONParser defaultJSONParser;
        int i;
        switch (this.context.state) {
            case 1001:
            case 1004:
                return;
            case 1002:
                defaultJSONParser = this.parser;
                i = 17;
                break;
            case 1003:
            case AVError.AV_ERR_TIMEOUT /* 1005 */:
                defaultJSONParser = this.parser;
                i = 16;
                break;
            default:
                throw new JSONException("illegal state : " + this.context.state);
        }
        defaultJSONParser.accept(i);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.parser.close();
    }

    public void config(Feature feature, boolean z) {
        this.parser.config(feature, z);
    }

    public void endArray() {
        this.parser.accept(15);
        endStructure();
    }

    public void endObject() {
        this.parser.accept(13);
        endStructure();
    }

    public Locale getLocal() {
        return this.parser.lexer.getLocale();
    }

    public TimeZone getTimzeZone() {
        return this.parser.lexer.getTimeZone();
    }

    public boolean hasNext() {
        if (this.context == null) {
            throw new JSONException("context is null");
        }
        int i = this.parser.lexer.token();
        int i2 = this.context.state;
        switch (i2) {
            case 1001:
            case 1003:
                return i != 13;
            case 1002:
            default:
                throw new JSONException("illegal state : " + i2);
            case 1004:
            case AVError.AV_ERR_TIMEOUT /* 1005 */:
                return i != 15;
        }
    }

    public int peek() {
        return this.parser.lexer.token();
    }

    public Integer readInteger() {
        Object obj;
        if (this.context == null) {
            obj = this.parser.parse();
        } else {
            readBefore();
            Object obj2 = this.parser.parse();
            readAfter();
            obj = obj2;
        }
        return TypeUtils.castToInt(obj);
    }

    public Long readLong() {
        Object obj;
        if (this.context == null) {
            obj = this.parser.parse();
        } else {
            readBefore();
            Object obj2 = this.parser.parse();
            readAfter();
            obj = obj2;
        }
        return TypeUtils.castToLong(obj);
    }

    public Object readObject() {
        if (this.context == null) {
            return this.parser.parse();
        }
        readBefore();
        int i = this.context.state;
        Object key = (i == 1001 || i == 1003) ? this.parser.parseKey() : this.parser.parse();
        readAfter();
        return key;
    }

    public <T> T readObject(TypeReference<T> typeReference) {
        return (T) readObject(typeReference.getType());
    }

    public <T> T readObject(Class<T> cls) {
        if (this.context == null) {
            return (T) this.parser.parseObject((Class) cls);
        }
        readBefore();
        T t = (T) this.parser.parseObject((Class) cls);
        readAfter();
        return t;
    }

    public <T> T readObject(Type type) {
        if (this.context == null) {
            return (T) this.parser.parseObject(type);
        }
        readBefore();
        T t = (T) this.parser.parseObject(type);
        readAfter();
        return t;
    }

    public Object readObject(Map map) {
        if (this.context == null) {
            return this.parser.parseObject(map);
        }
        readBefore();
        Object object = this.parser.parseObject(map);
        readAfter();
        return object;
    }

    public void readObject(Object obj) {
        if (this.context == null) {
            this.parser.parseObject(obj);
            return;
        }
        readBefore();
        this.parser.parseObject(obj);
        readAfter();
    }

    public String readString() {
        Object obj;
        Object obj2;
        if (this.context == null) {
            obj2 = this.parser.parse();
        } else {
            readBefore();
            JSONLexer jSONLexer = this.parser.lexer;
            if (this.context.state == 1001 && jSONLexer.token() == 18) {
                String strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken();
                obj = strStringVal;
            } else {
                obj = this.parser.parse();
            }
            readAfter();
            obj2 = obj;
        }
        return TypeUtils.castToString(obj2);
    }

    public void setLocale(Locale locale) {
        this.parser.lexer.setLocale(locale);
    }

    public void setTimzeZone(TimeZone timeZone) {
        this.parser.lexer.setTimeZone(timeZone);
    }

    public void startArray() {
        JSONStreamContext jSONStreamContext;
        if (this.context == null) {
            jSONStreamContext = new JSONStreamContext(null, 1004);
        } else {
            startStructure();
            jSONStreamContext = new JSONStreamContext(this.context, 1004);
        }
        this.context = jSONStreamContext;
        this.parser.accept(14);
    }

    public void startObject() {
        JSONStreamContext jSONStreamContext;
        if (this.context == null) {
            jSONStreamContext = new JSONStreamContext(null, 1001);
        } else {
            startStructure();
            jSONStreamContext = new JSONStreamContext(this.context, 1001);
        }
        this.context = jSONStreamContext;
        this.parser.accept(12, 18);
    }
}
