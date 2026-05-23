package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.igexin.push.core.b;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class DateCodec extends AbstractDateDeserializer implements ObjectDeserializer, ObjectSerializer {
    public static final DateCodec instance = new DateCodec();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [T, java.util.Calendar] */
    /* JADX WARN: Type inference failed for: r2v12, types: [T, java.util.Calendar] */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    public <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        if (obj2 == 0) {
            return null;
        }
        if (obj2 instanceof Date) {
            return obj2;
        }
        if (obj2 instanceof BigDecimal) {
            return (T) new Date(TypeUtils.longValue((BigDecimal) obj2));
        }
        if (obj2 instanceof Number) {
            return (T) new Date(((Number) obj2).longValue());
        }
        if (!(obj2 instanceof String)) {
            throw new JSONException("parse error");
        }
        String strSubstring = (String) obj2;
        if (strSubstring.length() == 0) {
            return null;
        }
        JSONScanner jSONScanner = new JSONScanner(strSubstring);
        try {
            if (jSONScanner.scanISO8601DateIfMatch(false)) {
                ?? r2 = (T) jSONScanner.getCalendar();
                return type == Calendar.class ? r2 : (T) r2.getTime();
            }
            jSONScanner.close();
            if (strSubstring.length() == defaultJSONParser.getDateFomartPattern().length() || (strSubstring.length() == 22 && defaultJSONParser.getDateFomartPattern().equals("yyyyMMddHHmmssSSSZ"))) {
                try {
                    return (T) defaultJSONParser.getDateFormat().parse(strSubstring);
                } catch (ParseException unused) {
                }
            }
            if (strSubstring.startsWith("/Date(") && strSubstring.endsWith(")/")) {
                strSubstring = strSubstring.substring(6, strSubstring.length() - 2);
            }
            if ("0000-00-00".equals(strSubstring) || "0000-00-00T00:00:00".equalsIgnoreCase(strSubstring) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(strSubstring)) {
                return null;
            }
            int iLastIndexOf = strSubstring.lastIndexOf(124);
            if (iLastIndexOf > 20) {
                TimeZone timeZone = TimeZone.getTimeZone(strSubstring.substring(iLastIndexOf + 1));
                if (!"GMT".equals(timeZone.getID())) {
                    JSONScanner jSONScanner2 = new JSONScanner(strSubstring.substring(0, iLastIndexOf));
                    try {
                        if (jSONScanner2.scanISO8601DateIfMatch(false)) {
                            ?? r22 = (T) jSONScanner2.getCalendar();
                            r22.setTimeZone(timeZone);
                            return type == Calendar.class ? r22 : (T) r22.getTime();
                        }
                    } finally {
                    }
                }
            }
            return (T) new Date(Long.parseLong(strSubstring));
        } finally {
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0182  */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        int i2;
        char[] charArray;
        int i3;
        int i4;
        String string;
        long time;
        int offset;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        Class<?> cls = obj.getClass();
        if (!(cls == java.sql.Date.class && ((offset = jSONSerializer.timeZone.getOffset((time = ((java.sql.Date) obj).getTime()))) == 0 || time % ((long) offset) == 0)) && (cls != Time.class || ((Time) obj).getTime() >= b.J)) {
            Date dateCastToDate = obj instanceof Date ? (Date) obj : TypeUtils.castToDate(obj);
            if (!serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
                if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && cls != type) {
                    if (cls == Date.class) {
                        serializeWriter.write("new Date(");
                        serializeWriter.writeLong(((Date) obj).getTime());
                        i4 = 41;
                    } else {
                        serializeWriter.write(123);
                        serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                        jSONSerializer.write(cls.getName());
                        serializeWriter.writeFieldValue(',', "val", ((Date) obj).getTime());
                        i4 = 125;
                    }
                    serializeWriter.write(i4);
                    return;
                }
                long time2 = dateCastToDate.getTime();
                if (!serializeWriter.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                    serializeWriter.writeLong(time2);
                    return;
                }
                int i5 = serializeWriter.isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
                serializeWriter.write(i5);
                Calendar calendar = Calendar.getInstance(jSONSerializer.timeZone, jSONSerializer.locale);
                calendar.setTimeInMillis(time2);
                int i6 = calendar.get(1);
                int i7 = calendar.get(2) + 1;
                int i8 = calendar.get(5);
                int i9 = calendar.get(11);
                int i10 = calendar.get(12);
                int i11 = calendar.get(13);
                int i12 = calendar.get(14);
                if (i12 != 0) {
                    charArray = "0000-00-00T00:00:00.000".toCharArray();
                    IOUtils.getChars(i12, 23, charArray);
                    IOUtils.getChars(i11, 19, charArray);
                    IOUtils.getChars(i10, 16, charArray);
                    IOUtils.getChars(i9, 13, charArray);
                    IOUtils.getChars(i8, 10, charArray);
                    IOUtils.getChars(i7, 7, charArray);
                    i2 = 4;
                } else {
                    i2 = 4;
                    if (i11 != 0 || i10 != 0 || i9 != 0) {
                        charArray = "0000-00-00T00:00:00".toCharArray();
                        IOUtils.getChars(i11, 19, charArray);
                        IOUtils.getChars(i10, 16, charArray);
                        IOUtils.getChars(i9, 13, charArray);
                        IOUtils.getChars(i8, 10, charArray);
                        IOUtils.getChars(i7, 7, charArray);
                        IOUtils.getChars(i6, 4, charArray);
                        serializeWriter.write(charArray);
                        float offset2 = calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 3600000.0f;
                        i3 = (int) offset2;
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
                                serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) ((offset2 - i3) * 60.0f))));
                            }
                            serializeWriter.writeInt(i3);
                            serializeWriter.write(58);
                            serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) ((offset2 - i3) * 60.0f))));
                        }
                        serializeWriter.write(i5);
                        return;
                    }
                    charArray = "0000-00-00".toCharArray();
                    IOUtils.getChars(i8, 10, charArray);
                    IOUtils.getChars(i7, 7, charArray);
                }
                IOUtils.getChars(i6, i2, charArray);
                serializeWriter.write(charArray);
                float offset22 = calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 3600000.0f;
                i3 = (int) offset22;
                if (i3 != 0.0d) {
                }
                serializeWriter.write(i5);
                return;
            }
            DateFormat dateFormat = jSONSerializer.getDateFormat();
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, jSONSerializer.locale);
                dateFormat.setTimeZone(jSONSerializer.timeZone);
            }
            string = dateFormat.format(dateCastToDate);
        } else {
            string = obj.toString();
        }
        serializeWriter.writeString(string);
    }
}
