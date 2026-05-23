package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes.dex */
public class JodaCodec implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final JodaCodec instance = new JodaCodec();
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormat.forPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormat.forPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormat.forPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormat.forPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormat.forPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormat.forPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormat.forPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormat.forPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormat.forPattern(defaultPatttern).withZone(DateTimeZone.getDefault());
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormat.forPattern(formatter_iso8601_pattern);

    private void write(SerializeWriter serializeWriter, ReadablePartial readablePartial, String str) {
        serializeWriter.writeString((str == formatter_iso8601_pattern ? formatter_iso8601 : DateTimeFormat.forPattern(str)).print(readablePartial));
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [T, org.joda.time.LocalDateTime] */
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() != 4) {
            if (jSONLexer.token() != 2) {
                throw new UnsupportedOperationException();
            }
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken();
            TimeZone timeZone = JSON.defaultTimeZone;
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            if (type == DateTime.class) {
                return (T) new DateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
            }
            ?? r3 = (T) new LocalDateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
            if (type == LocalDateTime.class) {
                return r3;
            }
            if (type == LocalDate.class) {
                return (T) r3.toLocalDate();
            }
            if (type == LocalTime.class) {
                return (T) r3.toLocalTime();
            }
            if (type == Instant.class) {
                return (T) new Instant(jLongValue);
            }
            throw new UnsupportedOperationException();
        }
        String strStringVal = jSONLexer.stringVal();
        jSONLexer.nextToken();
        DateTimeFormatter dateTimeFormatterForPattern = str != null ? defaultPatttern.equals(str) ? defaultFormatter : DateTimeFormat.forPattern(str) : null;
        if ("".equals(strStringVal)) {
            return null;
        }
        if (type == LocalDateTime.class) {
            return (strStringVal.length() == 10 || strStringVal.length() == 8) ? (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern).toLocalDateTime(LocalTime.MIDNIGHT) : (T) parseDateTime(strStringVal, dateTimeFormatterForPattern);
        }
        if (type == LocalDate.class) {
            return strStringVal.length() == 23 ? (T) LocalDateTime.parse(strStringVal).toLocalDate() : (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern);
        }
        if (type == LocalTime.class) {
            return strStringVal.length() == 23 ? (T) LocalDateTime.parse(strStringVal).toLocalTime() : (T) LocalTime.parse(strStringVal);
        }
        if (type == DateTime.class) {
            if (dateTimeFormatterForPattern == defaultFormatter) {
                dateTimeFormatterForPattern = ISO_FIXED_FORMAT;
            }
            return (T) parseZonedDateTime(strStringVal, dateTimeFormatterForPattern);
        }
        if (type == DateTimeZone.class) {
            return (T) DateTimeZone.forID(strStringVal);
        }
        if (type == Period.class) {
            return (T) Period.parse(strStringVal);
        }
        if (type == Duration.class) {
            return (T) Duration.parse(strStringVal);
        }
        if (type == Instant.class) {
            return (T) Instant.parse(strStringVal);
        }
        if (type == DateTimeFormatter.class) {
            return (T) DateTimeFormat.forPattern(strStringVal);
        }
        return null;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a7, code lost:
    
        if (r12.equals("AU") == false) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected LocalDateTime parseDateTime(String str, DateTimeFormatter dateTimeFormatter) {
        DateTimeFormatter dateTimeFormatter2;
        if (dateTimeFormatter == null) {
            if (str.length() == 19) {
                char cCharAt = str.charAt(4);
                char cCharAt2 = str.charAt(7);
                char cCharAt3 = str.charAt(10);
                char cCharAt4 = str.charAt(13);
                char cCharAt5 = str.charAt(16);
                if (cCharAt4 == ':' && cCharAt5 == ':') {
                    if (cCharAt == '-' && cCharAt2 == '-') {
                        if (cCharAt3 == 'T') {
                            dateTimeFormatter = formatter_iso8601;
                        } else if (cCharAt3 == ' ') {
                        }
                    } else if (cCharAt == '-' && cCharAt2 == '-') {
                        dateTimeFormatter = defaultFormatter;
                    } else if (cCharAt == '/' && cCharAt2 == '/') {
                        dateTimeFormatter = formatter_dt19_tw;
                    } else {
                        char cCharAt6 = str.charAt(0);
                        char cCharAt7 = str.charAt(1);
                        char cCharAt8 = str.charAt(2);
                        char cCharAt9 = str.charAt(3);
                        char cCharAt10 = str.charAt(5);
                        if (cCharAt8 == '/' && cCharAt10 == '/') {
                            int i = ((cCharAt9 - '0') * 10) + (cCharAt - '0');
                            if (((cCharAt6 - '0') * 10) + (cCharAt7 - '0') <= 12) {
                                if (i <= 12) {
                                    String country = Locale.getDefault().getCountry();
                                    if (!country.equals("US")) {
                                        if (!country.equals("BR")) {
                                        }
                                        dateTimeFormatter2 = formatter_dt19_eur;
                                        dateTimeFormatter = dateTimeFormatter2;
                                    }
                                }
                                dateTimeFormatter2 = formatter_dt19_us;
                                dateTimeFormatter = dateTimeFormatter2;
                            } else {
                                dateTimeFormatter2 = formatter_dt19_eur;
                                dateTimeFormatter = dateTimeFormatter2;
                            }
                        } else if (cCharAt8 == '.' && cCharAt10 == '.') {
                            dateTimeFormatter = formatter_dt19_de;
                        } else if (cCharAt8 == '-' && cCharAt10 == '-') {
                            dateTimeFormatter = formatter_dt19_in;
                        }
                    }
                }
                if (str.length() >= 17) {
                    char cCharAt11 = str.charAt(4);
                    if (cCharAt11 == 24180) {
                        dateTimeFormatter = str.charAt(str.length() - 1) == 31186 ? formatter_dt19_cn_1 : formatter_dt19_cn;
                    } else if (cCharAt11 == 45380) {
                        dateTimeFormatter = formatter_dt19_kr;
                    }
                }
            } else {
                if (str.length() == 23) {
                    char cCharAt12 = str.charAt(4);
                    char cCharAt13 = str.charAt(7);
                    char cCharAt14 = str.charAt(10);
                    char cCharAt15 = str.charAt(13);
                    char cCharAt16 = str.charAt(16);
                    char cCharAt17 = str.charAt(19);
                    if (cCharAt15 == ':' && cCharAt16 == ':' && cCharAt12 == '-' && cCharAt13 == '-' && cCharAt14 == ' ' && cCharAt17 == '.') {
                        dateTimeFormatter2 = defaultFormatter_23;
                        dateTimeFormatter = dateTimeFormatter2;
                    }
                }
                if (str.length() >= 17) {
                }
            }
        }
        return dateTimeFormatter == null ? LocalDateTime.parse(str) : LocalDateTime.parse(str, dateTimeFormatter);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected LocalDate parseLocalDate(String str, String str2, DateTimeFormatter dateTimeFormatter) {
        DateTimeFormatter dateTimeFormatter2;
        if (dateTimeFormatter == null) {
            if (str.length() == 8) {
                dateTimeFormatter = formatter_d8;
            }
            if (str.length() == 10) {
                char cCharAt = str.charAt(4);
                char cCharAt2 = str.charAt(7);
                if (cCharAt == '/' && cCharAt2 == '/') {
                    dateTimeFormatter = formatter_d10_tw;
                }
                char cCharAt3 = str.charAt(0);
                char cCharAt4 = str.charAt(1);
                char cCharAt5 = str.charAt(2);
                char cCharAt6 = str.charAt(3);
                char cCharAt7 = str.charAt(5);
                if (cCharAt5 == '/' && cCharAt7 == '/') {
                    int i = ((cCharAt6 - '0') * 10) + (cCharAt - '0');
                    if (((cCharAt3 - '0') * 10) + (cCharAt4 - '0') <= 12) {
                        if (i > 12) {
                            dateTimeFormatter = formatter_d10_us;
                        } else {
                            String country = Locale.getDefault().getCountry();
                            if (!country.equals("US")) {
                                if (country.equals("BR") || country.equals("AU")) {
                                    dateTimeFormatter = formatter_d10_eur;
                                }
                            }
                        }
                    }
                } else {
                    if (cCharAt5 == '.' && cCharAt7 == '.') {
                        dateTimeFormatter2 = formatter_d10_de;
                    } else if (cCharAt5 == '-' && cCharAt7 == '-') {
                        dateTimeFormatter2 = formatter_d10_in;
                    }
                    dateTimeFormatter = dateTimeFormatter2;
                }
            }
            if (str.length() >= 9) {
                char cCharAt8 = str.charAt(4);
                if (cCharAt8 == 24180) {
                    dateTimeFormatter = formatter_d10_cn;
                } else if (cCharAt8 == 45380) {
                    dateTimeFormatter = formatter_d10_kr;
                }
            }
        }
        return dateTimeFormatter == null ? LocalDate.parse(str) : LocalDate.parse(str, dateTimeFormatter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003e, code lost:
    
        if (r4 == ' ') goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected DateTime parseZonedDateTime(String str, DateTimeFormatter dateTimeFormatter) {
        DateTimeFormatter dateTimeFormatter2;
        if (dateTimeFormatter == null) {
            if (str.length() == 19) {
                char cCharAt = str.charAt(4);
                char cCharAt2 = str.charAt(7);
                char cCharAt3 = str.charAt(10);
                char cCharAt4 = str.charAt(13);
                char cCharAt5 = str.charAt(16);
                if (cCharAt4 == ':' && cCharAt5 == ':') {
                    if (cCharAt != '-' || cCharAt2 != '-') {
                        if (cCharAt == '-' && cCharAt2 == '-') {
                            dateTimeFormatter2 = defaultFormatter;
                        } else if (cCharAt == '/' && cCharAt2 == '/') {
                            dateTimeFormatter2 = formatter_dt19_tw;
                        } else {
                            char cCharAt6 = str.charAt(0);
                            char cCharAt7 = str.charAt(1);
                            char cCharAt8 = str.charAt(2);
                            char cCharAt9 = str.charAt(3);
                            char cCharAt10 = str.charAt(5);
                            if (cCharAt8 == '/' && cCharAt10 == '/') {
                                int i = ((cCharAt9 - '0') * 10) + (cCharAt - '0');
                                if (((cCharAt6 - '0') * 10) + (cCharAt7 - '0') <= 12) {
                                    if (i > 12) {
                                        dateTimeFormatter = formatter_dt19_us;
                                    } else {
                                        String country = Locale.getDefault().getCountry();
                                        if (!country.equals("US")) {
                                            if (country.equals("BR") || country.equals("AU")) {
                                                dateTimeFormatter = formatter_dt19_eur;
                                            }
                                        }
                                    }
                                }
                            } else if (cCharAt8 == '.' && cCharAt10 == '.') {
                                dateTimeFormatter2 = formatter_dt19_de;
                            } else if (cCharAt8 == '-' && cCharAt10 == '-') {
                                dateTimeFormatter2 = formatter_dt19_in;
                            }
                        }
                        dateTimeFormatter = dateTimeFormatter2;
                    } else if (cCharAt3 == 'T') {
                        dateTimeFormatter2 = formatter_iso8601;
                        dateTimeFormatter = dateTimeFormatter2;
                    }
                }
            }
            if (str.length() >= 17) {
                char cCharAt11 = str.charAt(4);
                if (cCharAt11 == 24180) {
                    dateTimeFormatter = str.charAt(str.length() - 1) == 31186 ? formatter_dt19_cn_1 : formatter_dt19_cn;
                } else if (cCharAt11 == 45380) {
                    dateTimeFormatter = formatter_dt19_kr;
                }
            }
        }
        return dateTimeFormatter == null ? DateTime.parse(str) : DateTime.parse(str, dateTimeFormatter);
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        write(jSONSerializer.out, (ReadablePartial) obj, beanContext.getFormat());
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (type == null) {
            type = obj.getClass();
        }
        if (type != LocalDateTime.class) {
            serializeWriter.writeString(obj.toString());
            return;
        }
        int mask = SerializerFeature.UseISO8601DateFormat.getMask();
        LocalDateTime localDateTime = (LocalDateTime) obj;
        String dateFormatPattern = jSONSerializer.getDateFormatPattern();
        if (dateFormatPattern == null) {
            dateFormatPattern = ((mask & i) != 0 || jSONSerializer.isEnabled(SerializerFeature.UseISO8601DateFormat)) ? formatter_iso8601_pattern : localDateTime.getMillisOfSecond() == 0 ? formatter_iso8601_pattern_23 : formatter_iso8601_pattern_29;
        }
        if (dateFormatPattern != null) {
            write(serializeWriter, (ReadablePartial) localDateTime, dateFormatPattern);
        } else if (serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            write(serializeWriter, (ReadablePartial) localDateTime, JSON.DEFFAULT_DATE_FORMAT);
        } else {
            serializeWriter.writeLong(localDateTime.toDateTime(DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toInstant().getMillis());
        }
    }
}
