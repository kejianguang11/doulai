package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class CharArrayCodec implements ObjectDeserializer {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        boolean z;
        String string;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 4) {
            string = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
        } else if (jSONLexer.token() == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken(16);
            string = numberIntegerValue.toString();
        } else {
            Object obj = defaultJSONParser.parse();
            if (!(obj instanceof String)) {
                if (!(obj instanceof Collection)) {
                    if (obj == null) {
                        return null;
                    }
                    return (T) JSON.toJSONString(obj).toCharArray();
                }
                Collection collection = (Collection) obj;
                Iterator it = collection.iterator();
                while (true) {
                    z = true;
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if ((next instanceof String) && ((String) next).length() != 1) {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    throw new JSONException("can not cast to char[]");
                }
                char[] cArr = new char[collection.size()];
                Iterator it2 = collection.iterator();
                int i = 0;
                while (it2.hasNext()) {
                    cArr[i] = ((String) it2.next()).charAt(0);
                    i++;
                }
                return cArr;
            }
            string = (String) obj;
        }
        return (T) string.toCharArray();
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}
