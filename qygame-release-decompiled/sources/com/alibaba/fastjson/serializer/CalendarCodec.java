package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/* JADX INFO: loaded from: classes.dex */
public class CalendarCodec extends ContextObjectDeserializer implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    public static final CalendarCodec instance = new CalendarCodec();
    private DatatypeFactory dateFactory;

    public XMLGregorianCalendar createXMLGregorianCalendar(Calendar calendar) {
        if (this.dateFactory == null) {
            try {
                this.dateFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                throw new IllegalStateException("Could not obtain an instance of DatatypeFactory.", e);
            }
        }
        return this.dateFactory.newXMLGregorianCalendar((GregorianCalendar) calendar);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v3, types: [T, java.util.Calendar] */
    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        T t = (T) DateCodec.instance.deserialze(defaultJSONParser, type, obj, str, i);
        if (t instanceof Calendar) {
            return t;
        }
        Date date = (Date) t;
        if (date == null) {
            return null;
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        ?? r7 = (T) Calendar.getInstance(jSONLexer.getTimeZone(), jSONLexer.getLocale());
        r7.setTime(date);
        return type == XMLGregorianCalendar.class ? (T) createXMLGregorianCalendar((GregorianCalendar) r7) : r7;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        String format = beanContext.getFormat();
        Calendar calendar = (Calendar) obj;
        if (format.equals("unixtime")) {
            serializeWriter.writeInt((int) (calendar.getTimeInMillis() / 1000));
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(jSONSerializer.timeZone);
        serializeWriter.writeString(simpleDateFormat.format(calendar.getTime()));
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d2  */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        int i2;
        char[] charArray;
        int i3;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        Calendar gregorianCalendar = obj instanceof XMLGregorianCalendar ? ((XMLGregorianCalendar) obj).toGregorianCalendar() : (Calendar) obj;
        if (!serializeWriter.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            jSONSerializer.write(gregorianCalendar.getTime());
            return;
        }
        char c = serializeWriter.isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
        serializeWriter.append(c);
        int i4 = gregorianCalendar.get(1);
        int i5 = gregorianCalendar.get(2) + 1;
        int i6 = gregorianCalendar.get(5);
        int i7 = gregorianCalendar.get(11);
        int i8 = gregorianCalendar.get(12);
        int i9 = gregorianCalendar.get(13);
        int i10 = gregorianCalendar.get(14);
        if (i10 != 0) {
            charArray = "0000-00-00T00:00:00.000".toCharArray();
            IOUtils.getChars(i10, 23, charArray);
            IOUtils.getChars(i9, 19, charArray);
            IOUtils.getChars(i8, 16, charArray);
            IOUtils.getChars(i7, 13, charArray);
            IOUtils.getChars(i6, 10, charArray);
            IOUtils.getChars(i5, 7, charArray);
            i2 = 4;
        } else {
            i2 = 4;
            if (i9 != 0 || i8 != 0 || i7 != 0) {
                charArray = "0000-00-00T00:00:00".toCharArray();
                IOUtils.getChars(i9, 19, charArray);
                IOUtils.getChars(i8, 16, charArray);
                IOUtils.getChars(i7, 13, charArray);
                IOUtils.getChars(i6, 10, charArray);
                IOUtils.getChars(i5, 7, charArray);
                IOUtils.getChars(i4, 4, charArray);
                serializeWriter.write(charArray);
                float offset = gregorianCalendar.getTimeZone().getOffset(gregorianCalendar.getTimeInMillis()) / 3600000.0f;
                i3 = (int) offset;
                if (i3 != 0.0d) {
                    serializeWriter.write(90);
                } else {
                    if (i3 > 9) {
                        serializeWriter.write(43);
                    } else if (i3 > 0) {
                        serializeWriter.write(43);
                        serializeWriter.write(48);
                    } else if (i3 < -9) {
                        serializeWriter.write(45);
                    } else {
                        if (i3 < 0) {
                            serializeWriter.write(45);
                            serializeWriter.write(48);
                            serializeWriter.writeInt(-i3);
                        }
                        serializeWriter.write(58);
                        serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) ((offset - i3) * 60.0f))));
                    }
                    serializeWriter.writeInt(i3);
                    serializeWriter.write(58);
                    serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) ((offset - i3) * 60.0f))));
                }
                serializeWriter.append(c);
            }
            charArray = "0000-00-00".toCharArray();
            IOUtils.getChars(i6, 10, charArray);
            IOUtils.getChars(i5, 7, charArray);
        }
        IOUtils.getChars(i4, i2, charArray);
        serializeWriter.write(charArray);
        float offset2 = gregorianCalendar.getTimeZone().getOffset(gregorianCalendar.getTimeInMillis()) / 3600000.0f;
        i3 = (int) offset2;
        if (i3 != 0.0d) {
        }
        serializeWriter.append(c);
    }
}
