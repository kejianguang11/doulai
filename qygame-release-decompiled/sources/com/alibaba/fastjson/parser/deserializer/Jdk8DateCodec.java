package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class Jdk8DateCodec extends ContextObjectDeserializer implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormatter.ofPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormatter.ofPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormatter.ofPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormatter.ofPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter.ofPattern(defaultPatttern).withZone(ZoneId.systemDefault());
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormatter.ofPattern(formatter_iso8601_pattern);

    private void write(SerializeWriter serializeWriter, TemporalAccessor temporalAccessor, String str) {
        if ("unixtime".equals(str) && (temporalAccessor instanceof ChronoZonedDateTime)) {
            serializeWriter.writeInt((int) ((ChronoZonedDateTime) temporalAccessor).toEpochSecond());
        } else {
            serializeWriter.writeString((str == formatter_iso8601_pattern ? formatter_iso8601 : DateTimeFormatter.ofPattern(str)).format(temporalAccessor));
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
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
            if ("unixtime".equals(str)) {
                jLongValue *= 1000;
            }
            if (type == LocalDateTime.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId());
            }
            if (type == LocalDate.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId()).toLocalDate();
            }
            if (type == LocalTime.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId()).toLocalTime();
            }
            if (type == ZonedDateTime.class) {
                return (T) ZonedDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId());
            }
            throw new UnsupportedOperationException();
        }
        String strStringVal = jSONLexer.stringVal();
        jSONLexer.nextToken();
        DateTimeFormatter dateTimeFormatterOfPattern = str != null ? defaultPatttern.equals(str) ? defaultFormatter : DateTimeFormatter.ofPattern(str) : null;
        if ("".equals(strStringVal)) {
            return null;
        }
        if (type == LocalDateTime.class) {
            return (strStringVal.length() == 10 || strStringVal.length() == 8) ? (T) LocalDateTime.of(parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern), LocalTime.MIN) : (T) parseDateTime(strStringVal, dateTimeFormatterOfPattern);
        }
        if (type == LocalDate.class) {
            if (strStringVal.length() != 23) {
                return (T) parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern);
            }
            LocalDateTime localDateTime = LocalDateTime.parse(strStringVal);
            return (T) LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
        }
        if (type == LocalTime.class) {
            if (strStringVal.length() != 23) {
                return (T) LocalTime.parse(strStringVal);
            }
            LocalDateTime localDateTime2 = LocalDateTime.parse(strStringVal);
            return (T) LocalTime.of(localDateTime2.getHour(), localDateTime2.getMinute(), localDateTime2.getSecond(), localDateTime2.getNano());
        }
        if (type == ZonedDateTime.class) {
            if (dateTimeFormatterOfPattern == defaultFormatter) {
                dateTimeFormatterOfPattern = ISO_FIXED_FORMAT;
            }
            if (dateTimeFormatterOfPattern == null && strStringVal.length() <= 19) {
                JSONScanner jSONScanner = new JSONScanner(strStringVal);
                TimeZone timeZone = defaultJSONParser.lexer.getTimeZone();
                jSONScanner.setTimeZone(timeZone);
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    return (T) ZonedDateTime.ofInstant(jSONScanner.getCalendar().getTime().toInstant(), timeZone.toZoneId());
                }
            }
            return (T) parseZonedDateTime(strStringVal, dateTimeFormatterOfPattern);
        }
        if (type == OffsetDateTime.class) {
            return (T) OffsetDateTime.parse(strStringVal);
        }
        if (type == OffsetTime.class) {
            return (T) OffsetTime.parse(strStringVal);
        }
        if (type == ZoneId.class) {
            return (T) ZoneId.of(strStringVal);
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
        return null;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected LocalDateTime parseDateTime(String str, DateTimeFormatter dateTimeFormatter) {
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
                            dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
                            dateTimeFormatter = formatter_dt19_de;
                        } else if (cCharAt8 == '-' && cCharAt10 == '-') {
                            dateTimeFormatter = formatter_dt19_in;
                        }
                    }
                }
            } else if (str.length() == 23) {
                char cCharAt11 = str.charAt(4);
                char cCharAt12 = str.charAt(7);
                char cCharAt13 = str.charAt(10);
                char cCharAt14 = str.charAt(13);
                char cCharAt15 = str.charAt(16);
                char cCharAt16 = str.charAt(19);
                if (cCharAt14 == ':' && cCharAt15 == ':' && cCharAt11 == '-' && cCharAt12 == '-' && cCharAt13 == ' ' && cCharAt16 == '.') {
                    dateTimeFormatter = defaultFormatter_23;
                }
            }
            if (str.length() >= 17) {
                char cCharAt17 = str.charAt(4);
                if (cCharAt17 == 24180) {
                    dateTimeFormatter = str.charAt(str.length() - 1) == 31186 ? formatter_dt19_cn_1 : formatter_dt19_cn;
                } else if (cCharAt17 == 45380) {
                    dateTimeFormatter = formatter_dt19_kr;
                }
            }
        }
        if (dateTimeFormatter == null) {
            JSONScanner jSONScanner = new JSONScanner(str);
            if (jSONScanner.scanISO8601DateIfMatch(false)) {
                return LocalDateTime.ofInstant(jSONScanner.getCalendar().toInstant(), ZoneId.systemDefault());
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
    protected ZonedDateTime parseZonedDateTime(String str, DateTimeFormatter dateTimeFormatter) {
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
                        dateTimeFormatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
        return dateTimeFormatter == null ? ZonedDateTime.parse(str) : ZonedDateTime.parse(str, dateTimeFormatter);
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        write(jSONSerializer.out, (TemporalAccessor) obj, beanContext.getFormat());
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [java.time.ZonedDateTime] */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        int nano;
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
            dateFormatPattern = ((mask & i) != 0 || jSONSerializer.isEnabled(SerializerFeature.UseISO8601DateFormat) || (nano = localDateTime.getNano()) == 0) ? formatter_iso8601_pattern : nano % 1000000 == 0 ? formatter_iso8601_pattern_23 : formatter_iso8601_pattern_29;
        }
        if (dateFormatPattern != null) {
            write(serializeWriter, localDateTime, dateFormatPattern);
        } else if (serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            write(serializeWriter, localDateTime, JSON.DEFFAULT_DATE_FORMAT);
        } else {
            serializeWriter.writeLong(localDateTime.atZone(JSON.defaultTimeZone.toZoneId()).toInstant().toEpochMilli());
        }
    }
}
