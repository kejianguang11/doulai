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
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class IntegerCodec implements ObjectDeserializer, ObjectSerializer {
    public static IntegerCodec instance = new IntegerCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Map map;
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        try {
            if (i == 2) {
                int iIntValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                obj2 = (T) Integer.valueOf(iIntValue);
            } else if (i == 3) {
                Integer numValueOf = Integer.valueOf(TypeUtils.intValue(jSONLexer.decimalValue()));
                jSONLexer.nextToken(16);
                obj2 = (T) numValueOf;
            } else {
                if (i == 12) {
                    JSONObject jSONObject = new JSONObject(true);
                    defaultJSONParser.parseObject((Map) jSONObject);
                    map = jSONObject;
                } else {
                    map = defaultJSONParser.parse();
                }
                obj2 = (T) TypeUtils.castToInt(map);
            }
            return type == AtomicInteger.class ? (T) new AtomicInteger(((Integer) obj2).intValue()) : (T) obj2;
        } catch (Exception e) {
            String str = "parseInt error";
            if (obj != null) {
                str = "parseInt error, field : " + obj;
            }
            throw new JSONException(str, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        int i2;
        SerializeWriter serializeWriter = jSONSerializer.out;
        Number number = (Number) obj;
        if (number == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        if (obj instanceof Long) {
            serializeWriter.writeLong(number.longValue());
        } else {
            serializeWriter.writeInt(number.intValue());
        }
        if (serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            Class<?> cls = number.getClass();
            if (cls == Byte.class) {
                i2 = 66;
            } else if (cls != Short.class) {
                return;
            } else {
                i2 = 83;
            }
            serializeWriter.write(i2);
        }
    }
}
