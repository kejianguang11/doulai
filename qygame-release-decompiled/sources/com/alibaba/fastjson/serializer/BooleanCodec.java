package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class BooleanCodec implements ObjectDeserializer, ObjectSerializer {
    public static final BooleanCodec instance = new BooleanCodec();

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004a A[RETURN] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        try {
            if (jSONLexer.token() == 6) {
                jSONLexer.nextToken(16);
            } else {
                if (jSONLexer.token() == 7) {
                    jSONLexer.nextToken(16);
                } else {
                    if (jSONLexer.token() != 2) {
                        Object obj3 = defaultJSONParser.parse();
                        if (obj3 == null) {
                            return null;
                        }
                        obj2 = (T) TypeUtils.castToBoolean(obj3);
                        return type == AtomicBoolean.class ? (T) new AtomicBoolean(((Boolean) obj2).booleanValue()) : (T) obj2;
                    }
                    int iIntValue = jSONLexer.intValue();
                    jSONLexer.nextToken(16);
                    if (iIntValue == 1) {
                    }
                }
                obj2 = (T) Boolean.FALSE;
                if (type == AtomicBoolean.class) {
                }
            }
            obj2 = (T) Boolean.TRUE;
            if (type == AtomicBoolean.class) {
            }
        } catch (Exception e) {
            throw new JSONException("parseBoolean error, field : " + obj, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 6;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        Boolean bool = (Boolean) obj;
        if (bool == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullBooleanAsFalse);
        } else {
            serializeWriter.write(bool.booleanValue() ? "true" : "false");
        }
    }
}
