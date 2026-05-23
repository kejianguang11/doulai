package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class LongCodec implements ObjectDeserializer, ObjectSerializer {
    public static LongCodec instance = new LongCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Map map;
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        try {
            int i = jSONLexer.token();
            if (i == 2) {
                long jLongValue = jSONLexer.longValue();
                jSONLexer.nextToken(16);
                obj2 = (T) Long.valueOf(jLongValue);
            } else if (i == 3) {
                Long lValueOf = Long.valueOf(TypeUtils.longValue(jSONLexer.decimalValue()));
                jSONLexer.nextToken(16);
                obj2 = (T) lValueOf;
            } else {
                if (i == 12) {
                    JSONObject jSONObject = new JSONObject(true);
                    defaultJSONParser.parseObject((Map) jSONObject);
                    map = jSONObject;
                } else {
                    map = defaultJSONParser.parse();
                }
                obj2 = (T) TypeUtils.castToLong(map);
                if (obj2 == null) {
                    return null;
                }
            }
            return type == AtomicLong.class ? (T) new AtomicLong(((Long) obj2).longValue()) : (T) obj2;
        } catch (Exception e) {
            throw new JSONException("parseLong error, field : " + obj, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        long jLongValue = ((Long) obj).longValue();
        serializeWriter.writeLong(jLongValue);
        if (!serializeWriter.isEnabled(SerializerFeature.WriteClassName) || jLongValue > 2147483647L || jLongValue < -2147483648L || type == Long.class || type == Long.TYPE) {
            return;
        }
        serializeWriter.write(76);
    }
}
