package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class FloatCodec implements ObjectDeserializer, ObjectSerializer {
    public static FloatCodec instance = new FloatCodec();
    private NumberFormat decimalFormat;

    public FloatCodec() {
    }

    public FloatCodec(String str) {
        this(new DecimalFormat(str));
    }

    public FloatCodec(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        float fFloatValue;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            String strNumberString = jSONLexer.numberString();
            jSONLexer.nextToken(16);
            fFloatValue = Float.parseFloat(strNumberString);
        } else {
            if (jSONLexer.token() != 3) {
                Object obj = defaultJSONParser.parse();
                if (obj == null) {
                    return null;
                }
                return (T) TypeUtils.castToFloat(obj);
            }
            fFloatValue = jSONLexer.floatValue();
            jSONLexer.nextToken(16);
        }
        return (T) Float.valueOf(fFloatValue);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            return (T) deserialze(defaultJSONParser);
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
        float fFloatValue = ((Float) obj).floatValue();
        if (this.decimalFormat != null) {
            serializeWriter.write(this.decimalFormat.format(fFloatValue));
        } else {
            serializeWriter.writeFloat(fFloatValue, true);
        }
    }
}
