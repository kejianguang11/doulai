package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    public JSONLexerBase(int i) {
        this.stringDefaultValue = null;
        this.features = i;
        if ((i & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
        this.sbuf = SBUF_LOCAL.get();
        if (this.sbuf == null) {
            this.sbuf = new char[512];
        }
    }

    public static boolean isWhitespace(char c) {
        return c <= ' ' && (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b');
    }

    public static String readString(char[] cArr, int i) {
        int i2;
        char[] cArr2 = new char[i];
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            char c = cArr[i3];
            if (c != '\\') {
                cArr2[i4] = c;
                i4++;
            } else {
                i3++;
                char c2 = cArr[i3];
                switch (c2) {
                    case '/':
                        i2 = i4 + 1;
                        cArr2[i4] = '/';
                        break;
                    case '0':
                        i2 = i4 + 1;
                        cArr2[i4] = 0;
                        break;
                    case '1':
                        i2 = i4 + 1;
                        cArr2[i4] = 1;
                        break;
                    case '2':
                        i2 = i4 + 1;
                        cArr2[i4] = 2;
                        break;
                    case '3':
                        i2 = i4 + 1;
                        cArr2[i4] = 3;
                        break;
                    case '4':
                        i2 = i4 + 1;
                        cArr2[i4] = 4;
                        break;
                    case '5':
                        i2 = i4 + 1;
                        cArr2[i4] = 5;
                        break;
                    case '6':
                        i2 = i4 + 1;
                        cArr2[i4] = 6;
                        break;
                    case '7':
                        i2 = i4 + 1;
                        cArr2[i4] = 7;
                        break;
                    default:
                        switch (c2) {
                            case 't':
                                i2 = i4 + 1;
                                cArr2[i4] = '\t';
                                break;
                            case 'u':
                                i2 = i4 + 1;
                                int i5 = i3 + 1;
                                int i6 = i5 + 1;
                                int i7 = i6 + 1;
                                i3 = i7 + 1;
                                cArr2[i4] = (char) Integer.parseInt(new String(new char[]{cArr[i5], cArr[i6], cArr[i7], cArr[i3]}), 16);
                                break;
                            case 'v':
                                i2 = i4 + 1;
                                cArr2[i4] = 11;
                                break;
                            default:
                                switch (c2) {
                                    case '\"':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\"';
                                        break;
                                    case '\'':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\'';
                                        break;
                                    case 'F':
                                    case 'f':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\f';
                                        break;
                                    case '\\':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\\';
                                        break;
                                    case 'b':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\b';
                                        break;
                                    case 'n':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\n';
                                        break;
                                    case 'r':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '\r';
                                        break;
                                    case 'x':
                                        i2 = i4 + 1;
                                        int i8 = i3 + 1;
                                        int i9 = digits[cArr[i8]] * 16;
                                        i3 = i8 + 1;
                                        cArr2[i4] = (char) (i9 + digits[cArr[i3]]);
                                        break;
                                    default:
                                        throw new JSONException("unclosed.str.lit");
                                }
                                break;
                        }
                        break;
                }
                i4 = i2;
            }
            i3++;
        }
        return new String(cArr2, 0, i4);
    }

    private void scanStringSingleQuote() {
        int i;
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\'') {
                this.token = 4;
                next();
                return;
            }
            char c = JSONLexer.EOI;
            if (next3 != 26) {
                c = '\\';
                boolean z = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp > this.sbuf.length) {
                            char[] cArr = new char[this.sp * 2];
                            System.arraycopy(this.sbuf, 0, cArr, 0, this.sbuf.length);
                            this.sbuf = cArr;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    switch (next4) {
                        case '/':
                            next3 = '/';
                            putChar(next3);
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case '1':
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            next3 = 5;
                            putChar(next3);
                            break;
                        case '6':
                            next3 = 6;
                            putChar(next3);
                            break;
                        case '7':
                            next3 = 7;
                            putChar(next3);
                            break;
                        default:
                            switch (next4) {
                                case 't':
                                    next3 = '\t';
                                    putChar(next3);
                                    break;
                                case 'u':
                                    i = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                    next3 = (char) i;
                                    putChar(next3);
                                    break;
                                case 'v':
                                    next3 = 11;
                                    putChar(next3);
                                    break;
                                default:
                                    switch (next4) {
                                        case '\"':
                                            next3 = '\"';
                                            putChar(next3);
                                            break;
                                        case '\'':
                                            putChar('\'');
                                            break;
                                        case 'F':
                                        case 'f':
                                            next3 = '\f';
                                            putChar(next3);
                                            break;
                                        case '\\':
                                            break;
                                        case 'b':
                                            next3 = '\b';
                                            putChar(next3);
                                            break;
                                        case 'n':
                                            next3 = '\n';
                                            putChar(next3);
                                            break;
                                        case 'r':
                                            next3 = '\r';
                                            putChar(next3);
                                            break;
                                        case 'x':
                                            next = next();
                                            next2 = next();
                                            boolean z2 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                            if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                                z = false;
                                            }
                                            if (z2 && z) {
                                                i = (digits[next] * 16) + digits[next2];
                                                next3 = (char) i;
                                                putChar(next3);
                                            }
                                            break;
                                        default:
                                            this.ch = next4;
                                            throw new JSONException("unclosed single-quote string");
                                    }
                                    break;
                            }
                            break;
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(next3);
                } else {
                    char[] cArr2 = this.sbuf;
                    int i2 = this.sp;
                    this.sp = i2 + 1;
                    cArr2[i2] = next3;
                }
            } else if (isEOF()) {
                throw new JSONException("unclosed single-quote string");
            }
            putChar(c);
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    protected abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    protected abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.sbuf.length <= 8192) {
            SBUF_LOCAL.set(this.sbuf);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean z) {
        this.features = Feature.config(this.features, feature, z);
        if ((this.features & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    protected abstract void copyTo(int i, int i2, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean z) {
        char cCharAt = charAt((this.np + this.sp) - 1);
        try {
            return cCharAt == 'F' ? Float.valueOf(Float.parseFloat(numberString())) : cCharAt == 'D' ? Double.valueOf(Double.parseDouble(numberString())) : z ? decimalValue() : Double.valueOf(doubleValue());
        } catch (NumberFormatException e) {
            throw new JSONException(e.getMessage() + ", " + info());
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() {
        char cCharAt;
        String strNumberString = numberString();
        float f = Float.parseFloat(strNumberString);
        if ((f != 0.0f && f != Float.POSITIVE_INFINITY) || (cCharAt = strNumberString.charAt(0)) <= '0' || cCharAt > '9') {
            return f;
        }
        throw new JSONException("float overflow : " + strNumberString);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int getFeatures() {
        return this.features;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public abstract int indexOf(char c, int i);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "";
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int i;
        boolean z;
        int i2 = 0;
        if (this.np == -1) {
            this.np = 0;
        }
        int i3 = this.np;
        int i4 = this.np + this.sp;
        if (charAt(this.np) == '-') {
            i3++;
            i = Integer.MIN_VALUE;
            z = true;
        } else {
            i = -2147483647;
            z = false;
        }
        if (i3 < i4) {
            i2 = -(charAt(i3) - '0');
            i3++;
        }
        while (i3 < i4) {
            int i5 = i3 + 1;
            char cCharAt = charAt(i3);
            if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B') {
                i3 = i5;
                break;
            }
            int i6 = cCharAt - '0';
            if (i2 < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int i7 = i2 * 10;
            if (i7 < i + i6) {
                throw new NumberFormatException(numberString());
            }
            i2 = i7 - i6;
            i3 = i5;
        }
        if (!z) {
            return -i2;
        }
        if (i3 > this.np + 1) {
            return i2;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number integerValue() throws NumberFormatException {
        long j;
        long j2;
        boolean z = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int i2 = this.np + this.sp;
        char c = ' ';
        char cCharAt = charAt(i2 - 1);
        if (cCharAt == 'B') {
            i2--;
            c = 'B';
        } else if (cCharAt == 'L') {
            i2--;
            c = 'L';
        } else if (cCharAt == 'S') {
            i2--;
            c = 'S';
        }
        if (charAt(this.np) == '-') {
            j = Long.MIN_VALUE;
            i++;
            z = true;
        } else {
            j = -9223372036854775807L;
        }
        long j3 = MULTMIN_RADIX_TEN;
        if (i < i2) {
            j2 = -(charAt(i) - '0');
            i++;
        } else {
            j2 = 0;
        }
        while (i < i2) {
            int i3 = i + 1;
            int iCharAt = charAt(i) - '0';
            if (j2 < j3) {
                return new BigInteger(numberString());
            }
            long j4 = j2 * 10;
            long j5 = iCharAt;
            if (j4 < j + j5) {
                return new BigInteger(numberString());
            }
            j2 = j4 - j5;
            i = i3;
            j3 = MULTMIN_RADIX_TEN;
        }
        if (!z) {
            long j6 = -j2;
            return (j6 > 2147483647L || c == 'L') ? Long.valueOf(j6) : c == 'S' ? Short.valueOf((short) j6) : c == 'B' ? Byte.valueOf((byte) j6) : Integer.valueOf((int) j6);
        }
        if (i > this.np + 1) {
            return (j2 < -2147483648L || c == 'L') ? Long.valueOf(j2) : c == 'S' ? Short.valueOf((short) j2) : c == 'B' ? Byte.valueOf((byte) j2) : Integer.valueOf((int) j2);
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char cCharAt = charAt(i);
            if (cCharAt == 26) {
                this.token = 20;
                return true;
            }
            if (!isWhitespace(cCharAt)) {
                return false;
            }
            i++;
        }
    }

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int i) {
        return (this.features & i) != 0;
    }

    public final boolean isEnabled(int i, int i2) {
        return ((this.features & i2) == 0 && (i & i2) == 0) ? false : true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    protected void lexError(String str, Object... objArr) {
        this.token = 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0088  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x005f -> B:12:0x0036). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long longValue() throws NumberFormatException {
        long j;
        long j2;
        boolean z = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int i2 = this.np + this.sp;
        if (charAt(this.np) == '-') {
            j = Long.MIN_VALUE;
            i++;
            z = true;
        } else {
            j = -9223372036854775807L;
        }
        if (i < i2) {
            int i3 = i + 1;
            j2 = -(charAt(i) - '0');
            i = i3;
            if (i < i2) {
                i3 = i + 1;
                char cCharAt = charAt(i);
                if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B') {
                    i = i3;
                } else {
                    int i4 = cCharAt - '0';
                    if (j2 < MULTMIN_RADIX_TEN) {
                        throw new NumberFormatException(numberString());
                    }
                    long j3 = j2 * 10;
                    long j4 = i4;
                    if (j3 < j + j4) {
                        throw new NumberFormatException(numberString());
                    }
                    j2 = j3 - j4;
                    i = i3;
                    if (i < i2) {
                    }
                }
            }
            if (z) {
                return -j2;
            }
            if (i > this.np + 1) {
                return j2;
            }
            throw new NumberFormatException(numberString());
        }
        j2 = 0;
        if (i < i2) {
        }
        if (z) {
        }
    }

    public int matchField(long j) {
        throw new UnsupportedOperationException();
    }

    public final boolean matchField(char[] cArr) {
        int i;
        while (!charArrayCompare(cArr)) {
            if (!isWhitespace(this.ch)) {
                return false;
            }
            next();
        }
        this.bp += cArr.length;
        this.ch = charAt(this.bp);
        if (this.ch == '{') {
            next();
            i = 12;
        } else if (this.ch == '[') {
            next();
            i = 14;
        } else {
            if (this.ch != 'S' || charAt(this.bp + 1) != 'e' || charAt(this.bp + 2) != 't' || charAt(this.bp + 3) != '[') {
                nextToken();
                return true;
            }
            this.bp += 3;
            this.ch = charAt(this.bp);
            i = 21;
        }
        this.token = i;
        return true;
    }

    public boolean matchField2(char[] cArr) {
        throw new UnsupportedOperationException();
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public Collection<String> newCollectionByType(Class<?> cls) {
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (this.ch == '_' || this.ch == '$' || Character.isLetter(this.ch)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            if (this.ch == '/') {
                skipComment();
            } else {
                if (this.ch == '\"') {
                    scanString();
                    return;
                }
                if (this.ch == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                if (this.ch >= '0' && this.ch <= '9') {
                    scanNumber();
                    return;
                }
                if (this.ch == '-') {
                    scanNumber();
                    return;
                }
                switch (this.ch) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        next();
                        break;
                    case '\'':
                        if (!isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("Feature.AllowSingleQuotes is false");
                        }
                        scanStringSingleQuote();
                        return;
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case '+':
                        next();
                        scanNumber();
                        return;
                    case '.':
                        next();
                        this.token = 25;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case 'x':
                        scanHex();
                        return;
                    case '{':
                        next();
                        this.token = 12;
                        return;
                    case '}':
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (isEOF()) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i = this.bp;
                            this.pos = i;
                            this.eofPos = i;
                            return;
                        }
                        if (this.ch > 31 && this.ch != 127) {
                            lexError("illegal.char", String.valueOf((int) this.ch));
                            next();
                            return;
                        }
                        next();
                        break;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0085 A[SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void nextToken(int i) {
        this.sp = 0;
        while (true) {
            if (i == 2) {
                if (this.ch >= '0' && this.ch <= '9') {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                }
                if (this.ch == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                } else if (this.ch == '[') {
                    this.token = 14;
                    next();
                    return;
                } else if (this.ch == '{') {
                    this.token = 12;
                    next();
                    return;
                }
            } else if (i == 4) {
                if (this.ch == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                }
                if (this.ch >= '0' && this.ch <= '9') {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                } else if (this.ch == '[') {
                    this.token = 14;
                    next();
                    return;
                } else if (this.ch == '{') {
                    this.token = 12;
                    next();
                    return;
                }
            } else if (i != 12) {
                if (i == 18) {
                    nextIdent();
                    return;
                }
                if (i != 20) {
                    switch (i) {
                        case 14:
                            if (this.ch == '[') {
                                this.token = 14;
                                next();
                            } else if (this.ch == '{') {
                                this.token = 12;
                                next();
                            }
                            break;
                        case 15:
                            if (this.ch == ']') {
                                this.token = 15;
                                next();
                            }
                            if (this.ch == 26) {
                                this.token = 20;
                            }
                            break;
                        case 16:
                            if (this.ch == ',') {
                                this.token = 16;
                                next();
                            } else if (this.ch == '}') {
                                this.token = 13;
                                next();
                            } else if (this.ch == ']') {
                                this.token = 15;
                                next();
                            } else if (this.ch == 26) {
                                this.token = 20;
                            } else if (this.ch == 'n') {
                                scanNullOrNew(false);
                            }
                            break;
                    }
                    return;
                }
                if (this.ch == 26) {
                }
            } else if (this.ch == '{') {
                this.token = 12;
                next();
                return;
            } else if (this.ch == '[') {
                this.token = 14;
                next();
                return;
            }
            if (this.ch != ' ' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != '\f' && this.ch != '\b') {
                nextToken();
                return;
            }
            next();
        }
    }

    public final void nextTokenWithChar(char c) {
        this.sp = 0;
        while (this.ch != c) {
            if (this.ch != ' ' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != '\f' && this.ch != '\b') {
                throw new JSONException("not match " + c + " - " + this.ch + ", info : " + info());
            }
            next();
        }
        next();
        nextToken();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int i) {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    protected final void putChar(char c) {
        if (this.sp == this.sbuf.length) {
            char[] cArr = new char[this.sbuf.length * 2];
            System.arraycopy(this.sbuf, 0, cArr, 0, this.sbuf.length);
            this.sbuf = cArr;
        }
        char[] cArr2 = this.sbuf;
        int i = this.sp;
        this.sp = i + 1;
        cArr2[i] = c;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ad  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean scanBoolean(char c) {
        boolean z = false;
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        int i = 2;
        if (cCharAt != 't') {
            if (cCharAt == 'f') {
                if (charAt(this.bp + 1) != 'a' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 's' || charAt(this.bp + 1 + 3) != 'e') {
                    this.matchStat = -1;
                    return false;
                }
                cCharAt = charAt(this.bp + 5);
                i = 6;
            } else if (cCharAt == '1') {
                cCharAt = charAt(this.bp + 1);
            } else if (cCharAt == '0') {
                cCharAt = charAt(this.bp + 1);
            } else {
                i = 1;
            }
            while (cCharAt != c) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return z;
                }
                cCharAt = charAt(this.bp + i);
                i++;
            }
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return z;
        }
        if (charAt(this.bp + 1) != 'r' || charAt(this.bp + 1 + 1) != 'u' || charAt(this.bp + 1 + 2) != 'e') {
            this.matchStat = -1;
            return false;
        }
        cCharAt = charAt(this.bp + 4);
        i = 5;
        z = true;
        while (cCharAt != c) {
        }
        this.bp += i;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        return z;
    }

    public Date scanDate(char c) {
        long j;
        int i;
        Date date;
        char cCharAt;
        int i2;
        boolean z = false;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf('\"', this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i3 = this.bp + 1;
            String strSubString = subString(i3, iIndexOf - i3);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i4 = 0;
                    for (int i5 = iIndexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                        i4++;
                    }
                    if (i4 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf('\"', iIndexOf + 1);
                }
                int i6 = iIndexOf - (this.bp + 1);
                strSubString = readString(sub_chars(this.bp + 1, i6), i6);
            }
            int i7 = (iIndexOf - (this.bp + 1)) + 1 + 1;
            int i8 = i7 + 1;
            cCharAt2 = charAt(this.bp + i7);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (!jSONScanner.scanISO8601DateIfMatch(false)) {
                    this.matchStat = -1;
                    return null;
                }
                date = jSONScanner.getCalendar().getTime();
                jSONScanner.close();
                i = i8;
            } finally {
                jSONScanner.close();
            }
        } else {
            int i9 = 2;
            char c2 = '9';
            char c3 = '0';
            if (cCharAt2 == '-' || (cCharAt2 >= '0' && cCharAt2 <= '9')) {
                if (cCharAt2 == '-') {
                    cCharAt2 = charAt(this.bp + 1);
                    z = true;
                } else {
                    i9 = 1;
                }
                if (cCharAt2 < '0' || cCharAt2 > '9') {
                    j = 0;
                    i = i9;
                } else {
                    j = cCharAt2 - '0';
                    while (true) {
                        i = i9 + 1;
                        cCharAt2 = charAt(this.bp + i9);
                        if (cCharAt2 < c3 || cCharAt2 > c2) {
                            break;
                        }
                        j = (j * 10) + ((long) (cCharAt2 - '0'));
                        i9 = i;
                        c2 = '9';
                        c3 = '0';
                    }
                }
                if (j < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            } else {
                if (cCharAt2 != 'n' || charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 5;
                cCharAt2 = charAt(this.bp + 4);
                i = 5;
                date = null;
            }
        }
        if (cCharAt2 == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        if (cCharAt2 != ']') {
            this.matchStat = -1;
            return null;
        }
        int i10 = i + 1;
        char cCharAt3 = charAt(this.bp + i);
        if (cCharAt3 == ',') {
            i2 = 16;
        } else if (cCharAt3 == ']') {
            i2 = 15;
        } else {
            if (cCharAt3 != '}') {
                if (cCharAt3 != 26) {
                    this.matchStat = -1;
                    return null;
                }
                this.token = 20;
                this.bp += i10 - 1;
                cCharAt = JSONLexer.EOI;
                this.ch = cCharAt;
                this.matchStat = 4;
                return date;
            }
            i2 = 13;
        }
        this.token = i2;
        this.bp += i10;
        cCharAt = charAt(this.bp);
        this.ch = cCharAt;
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00ae A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00b0 -> B:50:0x009e). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BigDecimal scanDecimal(char c) {
        int i;
        int i2;
        int i3;
        char cCharAt;
        int i4;
        int i5;
        char cCharAt2;
        char cCharAt3;
        int i6;
        this.matchStat = 0;
        char cCharAt4 = charAt(this.bp + 0);
        boolean z = cCharAt4 == '\"';
        if (z) {
            cCharAt4 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        if (cCharAt4 == '-') {
            cCharAt4 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt4 < '0' || cCharAt4 > '9') {
            if (cCharAt4 != 'n' || charAt(this.bp + i) != 'u' || charAt(this.bp + i + 1) != 'l' || charAt(this.bp + i + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 5;
            int i7 = i + 3;
            int i8 = i7 + 1;
            char cCharAt5 = charAt(this.bp + i7);
            if (z && cCharAt5 == '\"') {
                i2 = i8 + 1;
                cCharAt5 = charAt(this.bp + i8);
            } else {
                i2 = i8;
            }
            while (cCharAt5 != ',') {
                if (cCharAt5 == '}') {
                    this.bp += i2;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (!isWhitespace(cCharAt5)) {
                    this.matchStat = -1;
                    return null;
                }
                cCharAt5 = charAt(this.bp + i2);
                i2++;
            }
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        while (true) {
            i3 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i = i3;
        }
        if (cCharAt == '.') {
            int i9 = i3 + 1;
            char cCharAt6 = charAt(this.bp + i3);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                this.matchStat = -1;
                return null;
            }
            while (true) {
                i3 = i9 + 1;
                cCharAt = charAt(this.bp + i9);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i9 = i3;
            }
        }
        if (cCharAt == 'e' || cCharAt == 'E') {
            int i10 = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt == '+' || cCharAt == '-') {
                cCharAt = charAt(this.bp + i10);
                i3 = i10 + 1;
            } else {
                i3 = i10;
            }
            if (cCharAt >= '0' && cCharAt <= '9') {
                i10 = i3 + 1;
                cCharAt = charAt(this.bp + i3);
                i3 = i10;
                if (cCharAt >= '0') {
                    i10 = i3 + 1;
                    cCharAt = charAt(this.bp + i3);
                    i3 = i10;
                    if (cCharAt >= '0') {
                    }
                }
            }
        }
        if (!z) {
            i4 = this.bp;
            i5 = ((this.bp + i3) - i4) - 1;
            cCharAt2 = cCharAt;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return null;
            }
            int i11 = i3 + 1;
            cCharAt2 = charAt(this.bp + i3);
            i4 = this.bp + 1;
            i5 = ((this.bp + i11) - i4) - 2;
            i3 = i11;
        }
        BigDecimal bigDecimal = new BigDecimal(sub_chars(i4, i5));
        if (cCharAt2 == ',') {
            this.bp += i3;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return bigDecimal;
        }
        if (cCharAt2 != ']') {
            this.matchStat = -1;
            return null;
        }
        int i12 = i3 + 1;
        char cCharAt7 = charAt(this.bp + i3);
        if (cCharAt7 == ',') {
            this.token = 16;
        } else {
            if (cCharAt7 == ']') {
                i6 = 15;
            } else {
                if (cCharAt7 != '}') {
                    if (cCharAt7 != 26) {
                        this.matchStat = -1;
                        return null;
                    }
                    this.token = 20;
                    this.bp += i12 - 1;
                    cCharAt3 = JSONLexer.EOI;
                    this.ch = cCharAt3;
                    this.matchStat = 4;
                    return bigDecimal;
                }
                i6 = 13;
            }
            this.token = i6;
        }
        this.bp += i12;
        cCharAt3 = charAt(this.bp);
        this.ch = cCharAt3;
        this.matchStat = 4;
        return bigDecimal;
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x00d9 -> B:54:0x00c7). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public double scanDouble(char c) {
        int i;
        int i2;
        char cCharAt;
        boolean z;
        long j;
        int i3;
        int i4;
        char cCharAt2;
        int i5;
        double d;
        int i6;
        char cCharAt3;
        this.matchStat = 0;
        char cCharAt4 = charAt(this.bp + 0);
        boolean z2 = cCharAt4 == '\"';
        if (z2) {
            cCharAt4 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z3 = cCharAt4 == '-';
        if (z3) {
            cCharAt4 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt4 < '0' || cCharAt4 > '9') {
            if (cCharAt4 != 'n' || charAt(this.bp + i) != 'u' || charAt(this.bp + i + 1) != 'l' || charAt(this.bp + i + 2) != 'l') {
                this.matchStat = -1;
                return 0.0d;
            }
            this.matchStat = 5;
            int i7 = i + 3;
            int i8 = i7 + 1;
            char cCharAt5 = charAt(this.bp + i7);
            if (z2 && cCharAt5 == '\"') {
                cCharAt5 = charAt(this.bp + i8);
                i8++;
            }
            while (cCharAt5 != ',') {
                if (cCharAt5 == ']') {
                    this.bp += i8;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0d;
                }
                if (!isWhitespace(cCharAt5)) {
                    this.matchStat = -1;
                    return 0.0d;
                }
                cCharAt5 = charAt(this.bp + i8);
                i8++;
            }
            this.bp += i8;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        long j2 = cCharAt4 - '0';
        while (true) {
            i2 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + ((long) (cCharAt - '0'));
            i = i2;
        }
        if (cCharAt == '.') {
            int i9 = i2 + 1;
            char cCharAt6 = charAt(this.bp + i2);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                this.matchStat = -1;
                return 0.0d;
            }
            j2 = (j2 * 10) + ((long) (cCharAt6 - '0'));
            long j3 = 10;
            while (true) {
                i6 = i9 + 1;
                cCharAt3 = charAt(this.bp + i9);
                if (cCharAt3 < '0' || cCharAt3 > '9') {
                    break;
                }
                j2 = (j2 * 10) + ((long) (cCharAt3 - '0'));
                j3 *= 10;
                i9 = i6;
                z3 = z3;
            }
            z = z3;
            i2 = i6;
            long j4 = j3;
            cCharAt = cCharAt3;
            j = j4;
        } else {
            z = z3;
            j = 1;
        }
        boolean z4 = cCharAt == 'e' || cCharAt == 'E';
        if (z4) {
            int i10 = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            if (cCharAt == '+' || cCharAt == '-') {
                cCharAt = charAt(this.bp + i10);
                i2 = i10 + 1;
            } else {
                i2 = i10;
            }
            if (cCharAt >= '0' && cCharAt <= '9') {
                i10 = i2 + 1;
                cCharAt = charAt(this.bp + i2);
                i2 = i10;
                if (cCharAt >= '0') {
                    i10 = i2 + 1;
                    cCharAt = charAt(this.bp + i2);
                    i2 = i10;
                    if (cCharAt >= '0') {
                    }
                }
            }
        }
        if (!z2) {
            i3 = this.bp;
            i4 = ((this.bp + i2) - i3) - 1;
            cCharAt2 = cCharAt;
            i5 = i2;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0.0d;
            }
            i5 = i2 + 1;
            cCharAt2 = charAt(this.bp + i2);
            i3 = this.bp + 1;
            i4 = ((this.bp + i5) - i3) - 2;
        }
        if (z4 || i4 >= 17) {
            d = Double.parseDouble(subString(i3, i4));
        } else {
            d = j2 / j;
            if (z) {
                d = -d;
            }
        }
        if (cCharAt2 != c) {
            this.matchStat = -1;
            return d;
        }
        this.bp += i5;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        this.token = 16;
        return d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> cls, SymbolTable symbolTable, char c) {
        String strScanSymbolWithSeperator = scanSymbolWithSeperator(symbolTable, c);
        if (strScanSymbolWithSeperator == null) {
            return null;
        }
        return Enum.valueOf(cls, strScanSymbolWithSeperator);
    }

    public long scanEnumSymbol(char[] cArr) {
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(this.bp + i2);
            if (cCharAt == '\"') {
                int i4 = i3 + 1;
                char cCharAt2 = charAt(this.bp + i3);
                if (cCharAt2 == ',') {
                    this.bp += i4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return j;
                }
                if (cCharAt2 != '}') {
                    this.matchStat = -1;
                    return 0L;
                }
                int i5 = i4 + 1;
                char cCharAt3 = charAt(this.bp + i4);
                if (cCharAt3 == ',') {
                    i = 16;
                } else if (cCharAt3 == ']') {
                    i = 15;
                } else {
                    if (cCharAt3 != '}') {
                        if (cCharAt3 != 26) {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.token = 20;
                        this.bp += i5 - 1;
                        this.ch = JSONLexer.EOI;
                        this.matchStat = 4;
                        return j;
                    }
                    i = 13;
                }
                this.token = i;
                this.bp += i5;
                this.ch = charAt(this.bp);
                this.matchStat = 4;
                return j;
            }
            j = (j ^ ((long) ((cCharAt < 'A' || cCharAt > 'Z') ? cCharAt : cCharAt + ' '))) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i2 = i3;
        }
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != ' ' && this.ch != ',' && this.ch != '}' && this.ch != ']' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != 26 && this.ch != '\f' && this.ch != '\b' && this.ch != ':' && this.ch != '/') {
            throw new JSONException("scan false error");
        }
        this.token = 7;
    }

    public BigInteger scanFieldBigInteger(char[] cArr) {
        int i;
        char cCharAt;
        int length;
        int i2;
        BigInteger bigIntegerValueOf;
        char cCharAt2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char cCharAt3 = charAt(this.bp + length2);
        boolean z = cCharAt3 == '\"';
        if (z) {
            cCharAt3 = charAt(this.bp + i3);
            i3++;
        }
        boolean z2 = cCharAt3 == '-';
        if (z2) {
            cCharAt3 = charAt(this.bp + i3);
            i3++;
        }
        char c = '0';
        if (cCharAt3 < '0' || cCharAt3 > '9') {
            if (cCharAt3 != 'n' || charAt(this.bp + i3) != 'u' || charAt(this.bp + i3 + 1) != 'l' || charAt(this.bp + i3 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 5;
            int i4 = i3 + 3;
            int i5 = i4 + 1;
            char cCharAt4 = charAt(this.bp + i4);
            if (z && cCharAt4 == '\"') {
                cCharAt4 = charAt(this.bp + i5);
                i5++;
            }
            while (cCharAt4 != ',') {
                if (cCharAt4 == '}') {
                    this.bp += i5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (!isWhitespace(cCharAt4)) {
                    this.matchStat = -1;
                    return null;
                }
                cCharAt4 = charAt(this.bp + i5);
                i5++;
            }
            this.bp += i5;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        long j = cCharAt3 - '0';
        while (true) {
            i = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < c || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i3 = i;
            c = '0';
        }
        if (!z) {
            length = this.bp + cArr.length;
            i2 = ((this.bp + i) - length) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return null;
            }
            int i6 = i + 1;
            cCharAt = charAt(this.bp + i);
            length = this.bp + cArr.length + 1;
            i2 = ((this.bp + i6) - length) - 2;
            i = i6;
        }
        if (i2 < 20 || (z2 && i2 < 21)) {
            if (z2) {
                j = -j;
            }
            bigIntegerValueOf = BigInteger.valueOf(j);
        } else {
            bigIntegerValueOf = new BigInteger(subString(length, i2));
        }
        if (cCharAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return bigIntegerValueOf;
        }
        int i7 = 16;
        if (cCharAt != '}') {
            this.matchStat = -1;
            return null;
        }
        int i8 = i + 1;
        char cCharAt5 = charAt(this.bp + i);
        if (cCharAt5 != ',') {
            if (cCharAt5 == ']') {
                i7 = 15;
            } else if (cCharAt5 == '}') {
                i7 = 13;
            } else {
                if (cCharAt5 != 26) {
                    this.matchStat = -1;
                    return null;
                }
                this.token = 20;
                this.bp += i8 - 1;
                cCharAt2 = JSONLexer.EOI;
            }
            this.token = i7;
            this.bp += i8;
            cCharAt2 = charAt(this.bp);
        } else {
            this.token = i7;
            this.bp += i8;
            cCharAt2 = charAt(this.bp);
        }
        this.ch = cCharAt2;
        this.matchStat = 4;
        return bigIntegerValueOf;
    }

    public boolean scanFieldBoolean(char[] cArr) {
        boolean z;
        int i;
        int i2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt = charAt(this.bp + length);
        if (cCharAt == 't') {
            int i4 = i3 + 1;
            if (charAt(this.bp + i3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i5 = i4 + 1;
            if (charAt(this.bp + i4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            i = i5 + 1;
            if (charAt(this.bp + i5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = true;
        } else {
            if (cCharAt != 'f') {
                this.matchStat = -1;
                return false;
            }
            int i6 = i3 + 1;
            if (charAt(this.bp + i3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i7 = i6 + 1;
            if (charAt(this.bp + i6) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i8 = i7 + 1;
            if (charAt(this.bp + i7) != 's') {
                this.matchStat = -1;
                return false;
            }
            int i9 = i8 + 1;
            if (charAt(this.bp + i8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = false;
            i = i9;
        }
        int i10 = i + 1;
        char cCharAt2 = charAt(this.bp + i);
        if (cCharAt2 == ',') {
            this.bp += i10;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z;
        }
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return false;
        }
        int i11 = i10 + 1;
        char cCharAt3 = charAt(this.bp + i10);
        if (cCharAt3 == ',') {
            this.token = 16;
        } else {
            if (cCharAt3 == ']') {
                i2 = 15;
            } else {
                if (cCharAt3 != '}') {
                    if (cCharAt3 != 26) {
                        this.matchStat = -1;
                        return false;
                    }
                    this.token = 20;
                    this.bp += i11 - 1;
                    this.ch = JSONLexer.EOI;
                    this.matchStat = 4;
                    return z;
                }
                i2 = 13;
            }
            this.token = i2;
        }
        this.bp += i11;
        this.ch = charAt(this.bp);
        this.matchStat = 4;
        return z;
    }

    public Date scanFieldDate(char[] cArr) {
        char cCharAt;
        int i;
        long j;
        Date date;
        int i2;
        boolean z = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int length2 = this.bp + cArr.length + 1;
            String strSubString = subString(length2, iIndexOf - length2);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i4 = 0;
                    for (int i5 = iIndexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                        i4++;
                    }
                    if (i4 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf('\"', iIndexOf + 1);
                }
                int length3 = iIndexOf - ((this.bp + cArr.length) + 1);
                strSubString = readString(sub_chars(this.bp + cArr.length + 1, length3), length3);
            }
            int length4 = i3 + (iIndexOf - ((this.bp + cArr.length) + 1)) + 1;
            i = length4 + 1;
            cCharAt = charAt(this.bp + length4);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (!jSONScanner.scanISO8601DateIfMatch(false)) {
                    this.matchStat = -1;
                    return null;
                }
                date = jSONScanner.getCalendar().getTime();
            } finally {
                jSONScanner.close();
            }
        } else {
            if (cCharAt2 != '-' && (cCharAt2 < '0' || cCharAt2 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt2 == '-') {
                cCharAt2 = charAt(this.bp + i3);
                i3++;
                z = true;
            }
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                cCharAt = cCharAt2;
                i = i3;
                j = 0;
            } else {
                j = cCharAt2 - '0';
                while (true) {
                    i = i3 + 1;
                    cCharAt = charAt(this.bp + i3);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j = (j * 10) + ((long) (cCharAt - '0'));
                    i3 = i;
                }
            }
            if (j < 0) {
                this.matchStat = -1;
                return null;
            }
            if (z) {
                j = -j;
            }
            date = new Date(j);
        }
        if (cCharAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return date;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return null;
        }
        int i6 = i + 1;
        char cCharAt3 = charAt(this.bp + i);
        if (cCharAt3 == ',') {
            i2 = 16;
        } else if (cCharAt3 == ']') {
            i2 = 15;
        } else {
            if (cCharAt3 != '}') {
                if (cCharAt3 != 26) {
                    this.matchStat = -1;
                    return null;
                }
                this.token = 20;
                this.bp += i6 - 1;
                this.ch = JSONLexer.EOI;
                this.matchStat = 4;
                return date;
            }
            i2 = 13;
        }
        this.token = i2;
        this.bp += i6;
        this.ch = charAt(this.bp);
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00bc A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00be -> B:52:0x00ac). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BigDecimal scanFieldDecimal(char[] cArr) {
        int i;
        char cCharAt;
        int length;
        int i2;
        char cCharAt2;
        char cCharAt3;
        int i3;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length2 = cArr.length;
        int i4 = length2 + 1;
        char cCharAt4 = charAt(this.bp + length2);
        boolean z = cCharAt4 == '\"';
        if (z) {
            cCharAt4 = charAt(this.bp + i4);
            i4++;
        }
        if (cCharAt4 == '-') {
            cCharAt4 = charAt(this.bp + i4);
            i4++;
        }
        if (cCharAt4 < '0' || cCharAt4 > '9') {
            if (cCharAt4 != 'n' || charAt(this.bp + i4) != 'u' || charAt(this.bp + i4 + 1) != 'l' || charAt(this.bp + i4 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 5;
            int i5 = i4 + 3;
            int i6 = i5 + 1;
            char cCharAt5 = charAt(this.bp + i5);
            if (z && cCharAt5 == '\"') {
                cCharAt5 = charAt(this.bp + i6);
                i6++;
            }
            while (cCharAt5 != ',') {
                if (cCharAt5 == '}') {
                    this.bp += i6;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (!isWhitespace(cCharAt5)) {
                    this.matchStat = -1;
                    return null;
                }
                cCharAt5 = charAt(this.bp + i6);
                i6++;
            }
            this.bp += i6;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        while (true) {
            i = i4 + 1;
            cCharAt = charAt(this.bp + i4);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i4 = i;
        }
        if (cCharAt == '.') {
            int i7 = i + 1;
            char cCharAt6 = charAt(this.bp + i);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                this.matchStat = -1;
                return null;
            }
            while (true) {
                i = i7 + 1;
                cCharAt = charAt(this.bp + i7);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i7 = i;
            }
        }
        if (cCharAt == 'e' || cCharAt == 'E') {
            int i8 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt == '+' || cCharAt == '-') {
                cCharAt = charAt(this.bp + i8);
                i = i8 + 1;
            } else {
                i = i8;
            }
            if (cCharAt >= '0' && cCharAt <= '9') {
                i8 = i + 1;
                cCharAt = charAt(this.bp + i);
                i = i8;
                if (cCharAt >= '0') {
                    i8 = i + 1;
                    cCharAt = charAt(this.bp + i);
                    i = i8;
                    if (cCharAt >= '0') {
                    }
                }
            }
        }
        if (!z) {
            length = this.bp + cArr.length;
            i2 = ((this.bp + i) - length) - 1;
            cCharAt2 = cCharAt;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return null;
            }
            int i9 = i + 1;
            cCharAt2 = charAt(this.bp + i);
            length = this.bp + cArr.length + 1;
            i2 = ((this.bp + i9) - length) - 2;
            i = i9;
        }
        BigDecimal bigDecimal = new BigDecimal(sub_chars(length, i2));
        if (cCharAt2 == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return bigDecimal;
        }
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return null;
        }
        int i10 = i + 1;
        char cCharAt7 = charAt(this.bp + i);
        if (cCharAt7 == ',') {
            this.token = 16;
        } else {
            if (cCharAt7 == ']') {
                i3 = 15;
            } else {
                if (cCharAt7 != '}') {
                    if (cCharAt7 != 26) {
                        this.matchStat = -1;
                        return null;
                    }
                    this.token = 20;
                    this.bp += i10 - 1;
                    cCharAt3 = JSONLexer.EOI;
                    this.ch = cCharAt3;
                    this.matchStat = 4;
                    return bigDecimal;
                }
                i3 = 13;
            }
            this.token = i3;
        }
        this.bp += i10;
        cCharAt3 = charAt(this.bp);
        this.ch = cCharAt3;
        this.matchStat = 4;
        return bigDecimal;
    }

    public final double scanFieldDouble(char[] cArr) {
        int i;
        char cCharAt;
        boolean z;
        long j;
        int length;
        int i2;
        char cCharAt2;
        int i3;
        double d;
        char cCharAt3;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int length2 = cArr.length;
        int i5 = length2 + 1;
        char cCharAt4 = charAt(this.bp + length2);
        boolean z2 = cCharAt4 == '\"';
        if (z2) {
            cCharAt4 = charAt(this.bp + i5);
            i5++;
        }
        boolean z3 = cCharAt4 == '-';
        if (z3) {
            cCharAt4 = charAt(this.bp + i5);
            i5++;
        }
        char c = '0';
        if (cCharAt4 < '0' || cCharAt4 > '9') {
            boolean z4 = z2;
            if (cCharAt4 != 'n' || charAt(this.bp + i5) != 'u' || charAt(this.bp + i5 + 1) != 'l' || charAt(this.bp + i5 + 2) != 'l') {
                this.matchStat = -1;
                return 0.0d;
            }
            this.matchStat = 5;
            int i6 = i5 + 3;
            int i7 = i6 + 1;
            char cCharAt5 = charAt(this.bp + i6);
            if (z4 && cCharAt5 == '\"') {
                cCharAt5 = charAt(this.bp + i7);
                i7++;
            }
            while (cCharAt5 != ',') {
                if (cCharAt5 == '}') {
                    this.bp += i7;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0d;
                }
                if (!isWhitespace(cCharAt5)) {
                    this.matchStat = -1;
                    return 0.0d;
                }
                cCharAt5 = charAt(this.bp + i7);
                i7++;
            }
            this.bp += i7;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        long j2 = cCharAt4 - '0';
        while (true) {
            i = i5 + 1;
            cCharAt = charAt(this.bp + i5);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + ((long) (cCharAt - '0'));
            i5 = i;
        }
        if (cCharAt == '.') {
            int i8 = i + 1;
            char cCharAt6 = charAt(this.bp + i);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                this.matchStat = -1;
                return 0.0d;
            }
            z = z2;
            j2 = (j2 * 10) + ((long) (cCharAt6 - '0'));
            j = 10;
            while (true) {
                i4 = i8 + 1;
                cCharAt = charAt(this.bp + i8);
                if (cCharAt < c || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                j *= 10;
                i8 = i4;
                c = '0';
            }
            i = i4;
        } else {
            z = z2;
            j = 1;
        }
        boolean z5 = cCharAt == 'e' || cCharAt == 'E';
        if (z5) {
            int i9 = i + 1;
            char cCharAt7 = charAt(this.bp + i);
            if (cCharAt7 == '+' || cCharAt7 == '-') {
                int i10 = i9 + 1;
                cCharAt = charAt(this.bp + i9);
                i = i10;
            } else {
                i = i9;
                cCharAt = cCharAt7;
            }
            while (cCharAt >= '0' && cCharAt <= '9') {
                cCharAt = charAt(this.bp + i);
                i++;
            }
        }
        if (!z) {
            length = this.bp + cArr.length;
            i2 = ((this.bp + i) - length) - 1;
            cCharAt2 = cCharAt;
            i3 = i;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0.0d;
            }
            i3 = i + 1;
            cCharAt2 = charAt(this.bp + i);
            length = this.bp + cArr.length + 1;
            i2 = ((this.bp + i3) - length) - 2;
        }
        if (z5 || i2 >= 17) {
            d = Double.parseDouble(subString(length, i2));
        } else {
            d = j2 / j;
            if (z3) {
                d = -d;
            }
        }
        if (cCharAt2 == ',') {
            this.bp += i3;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return d;
        }
        int i11 = 16;
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return 0.0d;
        }
        int i12 = i3 + 1;
        char cCharAt8 = charAt(this.bp + i3);
        if (cCharAt8 != ',') {
            if (cCharAt8 == ']') {
                i11 = 15;
            } else if (cCharAt8 == '}') {
                i11 = 13;
            } else {
                if (cCharAt8 != 26) {
                    this.matchStat = -1;
                    return 0.0d;
                }
                this.token = 20;
                this.bp += i12 - 1;
                cCharAt3 = JSONLexer.EOI;
            }
            this.token = i11;
            this.bp += i12;
            cCharAt3 = charAt(this.bp);
        } else {
            this.token = i11;
            this.bp += i12;
            cCharAt3 = charAt(this.bp);
        }
        this.ch = cCharAt3;
        this.matchStat = 4;
        return d;
    }

    public final float scanFieldFloat(char[] cArr) {
        int i;
        char cCharAt;
        boolean z;
        long j;
        int length;
        int i2;
        char cCharAt2;
        float f;
        char cCharAt3;
        int i3;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int length2 = cArr.length;
        int i5 = length2 + 1;
        char cCharAt4 = charAt(this.bp + length2);
        boolean z2 = cCharAt4 == '\"';
        if (z2) {
            cCharAt4 = charAt(this.bp + i5);
            i5++;
        }
        boolean z3 = cCharAt4 == '-';
        if (z3) {
            cCharAt4 = charAt(this.bp + i5);
            i5++;
        }
        if (cCharAt4 >= '0') {
            char c = '9';
            if (cCharAt4 <= '9') {
                long j2 = cCharAt4 - '0';
                while (true) {
                    i = i5 + 1;
                    cCharAt = charAt(this.bp + i5);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                    i5 = i;
                }
                if (cCharAt == '.') {
                    int i6 = i + 1;
                    char cCharAt5 = charAt(this.bp + i);
                    if (cCharAt5 < '0' || cCharAt5 > '9') {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    z = z2;
                    j2 = (j2 * 10) + ((long) (cCharAt5 - '0'));
                    j = 10;
                    while (true) {
                        i4 = i6 + 1;
                        cCharAt = charAt(this.bp + i6);
                        if (cCharAt < '0' || cCharAt > c) {
                            break;
                        }
                        j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                        j *= 10;
                        i6 = i4;
                        c = '9';
                    }
                    i = i4;
                } else {
                    z = z2;
                    j = 1;
                }
                boolean z4 = cCharAt == 'e' || cCharAt == 'E';
                if (z4) {
                    int i7 = i + 1;
                    char cCharAt6 = charAt(this.bp + i);
                    if (cCharAt6 == '+' || cCharAt6 == '-') {
                        int i8 = i7 + 1;
                        cCharAt = charAt(this.bp + i7);
                        i = i8;
                    } else {
                        i = i7;
                        cCharAt = cCharAt6;
                    }
                    while (cCharAt >= '0' && cCharAt <= '9') {
                        int i9 = i + 1;
                        cCharAt = charAt(this.bp + i);
                        i = i9;
                    }
                }
                if (!z) {
                    length = this.bp + cArr.length;
                    i2 = ((this.bp + i) - length) - 1;
                    cCharAt2 = cCharAt;
                } else {
                    if (cCharAt != '\"') {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    int i10 = i + 1;
                    cCharAt2 = charAt(this.bp + i);
                    length = this.bp + cArr.length + 1;
                    i2 = ((this.bp + i10) - length) - 2;
                    i = i10;
                }
                if (z4 || i2 >= 17) {
                    f = Float.parseFloat(subString(length, i2));
                } else {
                    f = (float) (j2 / j);
                    if (z3) {
                        f = -f;
                    }
                }
                if (cCharAt2 == ',') {
                    this.bp += i;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    this.token = 16;
                    return f;
                }
                if (cCharAt2 != '}') {
                    this.matchStat = -1;
                    return 0.0f;
                }
                int i11 = i + 1;
                char cCharAt7 = charAt(this.bp + i);
                if (cCharAt7 == ',') {
                    this.token = 16;
                } else {
                    if (cCharAt7 == ']') {
                        i3 = 15;
                    } else {
                        if (cCharAt7 != '}') {
                            if (cCharAt7 != 26) {
                                this.matchStat = -1;
                                return 0.0f;
                            }
                            this.bp += i11 - 1;
                            this.token = 20;
                            cCharAt3 = JSONLexer.EOI;
                            this.ch = cCharAt3;
                            this.matchStat = 4;
                            return f;
                        }
                        i3 = 13;
                    }
                    this.token = i3;
                }
                this.bp += i11;
                cCharAt3 = charAt(this.bp);
                this.ch = cCharAt3;
                this.matchStat = 4;
                return f;
            }
        }
        boolean z5 = z2;
        if (cCharAt4 != 'n' || charAt(this.bp + i5) != 'u' || charAt(this.bp + i5 + 1) != 'l' || charAt(this.bp + i5 + 2) != 'l') {
            this.matchStat = -1;
            return 0.0f;
        }
        this.matchStat = 5;
        int i12 = i5 + 3;
        int i13 = i12 + 1;
        char cCharAt8 = charAt(this.bp + i12);
        if (z5 && cCharAt8 == '\"') {
            cCharAt8 = charAt(this.bp + i13);
            i13++;
        }
        while (cCharAt8 != ',') {
            if (cCharAt8 == '}') {
                this.bp += i13;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 13;
                return 0.0f;
            }
            if (!isWhitespace(cCharAt8)) {
                this.matchStat = -1;
                return 0.0f;
            }
            cCharAt8 = charAt(this.bp + i13);
            i13++;
        }
        this.bp += i13;
        this.ch = charAt(this.bp);
        this.matchStat = 5;
        this.token = 16;
        return 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0198, code lost:
    
        r2 = r4;
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x019b, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a0, code lost:
    
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a2, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float[] scanFieldFloatArray(char[] cArr) {
        int i;
        char cCharAt;
        char cCharAt2;
        int i2;
        float f;
        int i3;
        boolean z = false;
        this.matchStat = 0;
        float[] fArr = null;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i4 = length + 1;
        if (charAt(this.bp + length) != '[') {
            this.matchStat = -2;
            return null;
        }
        int i5 = i4 + 1;
        char cCharAt3 = charAt(this.bp + i4);
        float[] fArr2 = new float[16];
        int i6 = 0;
        while (true) {
            int i7 = (this.bp + i5) - 1;
            boolean z2 = cCharAt3 == '-' ? true : z;
            if (z2) {
                cCharAt3 = charAt(this.bp + i5);
                i5++;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                break;
            }
            int i8 = cCharAt3 - '0';
            while (true) {
                i = i5 + 1;
                cCharAt = charAt(this.bp + i5);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i8 = (i8 * 10) + (cCharAt - '0');
                i5 = i;
            }
            if (cCharAt == '.' ? true : z) {
                int i9 = i + 1;
                char cCharAt4 = charAt(this.bp + i);
                if (cCharAt4 < '0' || cCharAt4 > '9') {
                    break;
                }
                i8 = (i8 * 10) + (cCharAt4 - '0');
                i2 = 10;
                while (true) {
                    i = i9 + 1;
                    cCharAt2 = charAt(this.bp + i9);
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        break;
                    }
                    i8 = (i8 * 10) + (cCharAt2 - '0');
                    i2 *= 10;
                    i9 = i;
                }
            } else {
                cCharAt2 = cCharAt;
                i2 = 1;
            }
            boolean z3 = cCharAt2 == 'e' || cCharAt2 == 'E';
            if (z3) {
                int i10 = i + 1;
                cCharAt2 = charAt(this.bp + i);
                if (cCharAt2 == '+' || cCharAt2 == '-') {
                    int i11 = i10 + 1;
                    cCharAt2 = charAt(this.bp + i10);
                    i = i11;
                } else {
                    i = i10;
                }
                while (cCharAt2 >= '0' && cCharAt2 <= '9') {
                    int i12 = i + 1;
                    cCharAt2 = charAt(this.bp + i);
                    i = i12;
                }
            }
            int i13 = ((this.bp + i) - i7) - 1;
            if (z3 || i13 >= 10) {
                f = Float.parseFloat(subString(i7, i13));
            } else {
                f = i8 / i2;
                if (z2) {
                    f = -f;
                }
            }
            if (i6 >= fArr2.length) {
                float[] fArr3 = new float[(fArr2.length * 3) / 2];
                System.arraycopy(fArr2, 0, fArr3, 0, i6);
                fArr2 = fArr3;
            }
            int i14 = i6 + 1;
            fArr2[i6] = f;
            if (cCharAt2 == ',') {
                cCharAt2 = charAt(this.bp + i);
                i++;
            } else if (cCharAt2 == ']') {
                int i15 = i + 1;
                char cCharAt5 = charAt(this.bp + i);
                if (i14 != fArr2.length) {
                    float[] fArr4 = new float[i14];
                    System.arraycopy(fArr2, 0, fArr4, 0, i14);
                    fArr2 = fArr4;
                }
                if (cCharAt5 == ',') {
                    this.bp += i15 - 1;
                    next();
                    this.matchStat = 3;
                    this.token = 16;
                    return fArr2;
                }
                if (cCharAt5 != '}') {
                    this.matchStat = -1;
                    return null;
                }
                int i16 = i15 + 1;
                char cCharAt6 = charAt(this.bp + i15);
                if (cCharAt6 == ',') {
                    this.token = 16;
                } else {
                    if (cCharAt6 == ']') {
                        i3 = 15;
                    } else {
                        if (cCharAt6 != '}') {
                            if (cCharAt6 != 26) {
                                this.matchStat = -1;
                                return null;
                            }
                            this.bp += i16 - 1;
                            this.token = 20;
                            this.ch = JSONLexer.EOI;
                            this.matchStat = 4;
                            return fArr2;
                        }
                        i3 = 13;
                    }
                    this.token = i3;
                }
                this.bp += i16 - 1;
                next();
                this.matchStat = 4;
                return fArr2;
            }
            i6 = i14;
            i5 = i;
            fArr = null;
            cCharAt3 = cCharAt2;
            z = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b5, code lost:
    
        r20.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b9, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0137, code lost:
    
        r2 = r17 + 1;
        r1 = charAt(r20.bp + r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0142, code lost:
    
        if (r4 == r3.length) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0144, code lost:
    
        r5 = new float[r4];
        r6 = 0;
        java.lang.System.arraycopy(r3, 0, r5, 0, r4);
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x014c, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x014e, code lost:
    
        if (r7 < r8.length) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0150, code lost:
    
        r5 = new float[(r8.length * 3) / 2][];
        java.lang.System.arraycopy(r3, r6, r5, r6, r4);
        r8 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x015b, code lost:
    
        r4 = r7 + 1;
        r8[r7] = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0161, code lost:
    
        if (r1 != ',') goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0163, code lost:
    
        r3 = r2 + 1;
        r1 = charAt(r20.bp + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x016c, code lost:
    
        r2 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0170, code lost:
    
        if (r1 != ']') goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0172, code lost:
    
        r3 = r2 + 1;
        r2 = charAt(r20.bp + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x017c, code lost:
    
        r3 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0191, code lost:
    
        r20.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0197, code lost:
    
        return (float[][]) null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float[][] scanFieldFloatArray2(char[] cArr) {
        int i;
        float[][] fArr;
        int i2;
        int i3;
        char cCharAt;
        int i4;
        float f;
        char cCharAt2;
        int i5 = 0;
        this.matchStat = 0;
        float[][] fArr2 = null;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return (float[][]) null;
        }
        int length = cArr.length;
        int i6 = length + 1;
        char c = '[';
        if (charAt(this.bp + length) != '[') {
            this.matchStat = -2;
            return (float[][]) null;
        }
        int i7 = i6 + 1;
        char cCharAt3 = charAt(this.bp + i6);
        int i8 = 16;
        float[][] fArr3 = new float[16][];
        int i9 = 0;
        loop0: while (true) {
            if (cCharAt3 != c) {
                i = i9;
                break;
            }
            int i10 = i7 + 1;
            char cCharAt4 = charAt(this.bp + i7);
            float[] fArr4 = new float[i8];
            int i11 = i5;
            while (true) {
                int i12 = (this.bp + i10) - 1;
                int i13 = cCharAt4 == '-' ? 1 : i5;
                if (i13 != 0) {
                    cCharAt4 = charAt(this.bp + i10);
                    i10++;
                }
                if (cCharAt4 < '0' || cCharAt4 > '9') {
                    break loop0;
                }
                int i14 = cCharAt4 - '0';
                while (true) {
                    i3 = i10 + 1;
                    cCharAt = charAt(this.bp + i10);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    i14 = (i14 * 10) + (cCharAt - '0');
                    i10 = i3;
                }
                if (cCharAt == '.') {
                    int i15 = i3 + 1;
                    char cCharAt5 = charAt(this.bp + i3);
                    if (cCharAt5 < '0' || cCharAt5 > '9') {
                        break loop0;
                    }
                    i14 = (i14 * 10) + (cCharAt5 - '0');
                    int i16 = 10;
                    while (true) {
                        i3 = i15 + 1;
                        cCharAt2 = charAt(this.bp + i15);
                        if (cCharAt2 < '0' || cCharAt2 > '9') {
                            break;
                        }
                        i14 = (i14 * 10) + (cCharAt2 - '0');
                        i16 *= 10;
                        i15 = i3;
                    }
                    i4 = i16;
                    cCharAt = cCharAt2;
                } else {
                    i4 = 1;
                }
                boolean z = cCharAt == 'e' || cCharAt == 'E';
                if (z) {
                    int i17 = i3 + 1;
                    cCharAt = charAt(this.bp + i3);
                    if (cCharAt == '+' || cCharAt == '-') {
                        int i18 = i17 + 1;
                        cCharAt = charAt(this.bp + i17);
                        i3 = i18;
                    } else {
                        i3 = i17;
                    }
                    while (cCharAt >= '0' && cCharAt <= '9') {
                        int i19 = i3 + 1;
                        cCharAt = charAt(this.bp + i3);
                        i3 = i19;
                    }
                }
                int i20 = ((this.bp + i3) - i12) - 1;
                if (z || i20 >= 10) {
                    f = Float.parseFloat(subString(i12, i20));
                } else {
                    f = i14 / i4;
                    if (i13 != 0) {
                        f = -f;
                    }
                }
                if (i11 >= fArr4.length) {
                    float[] fArr5 = new float[(fArr4.length * 3) / 2];
                    System.arraycopy(fArr4, 0, fArr5, 0, i11);
                    fArr4 = fArr5;
                }
                int i21 = i11 + 1;
                fArr4[i11] = f;
                if (cCharAt == ',') {
                    i10 = i3 + 1;
                    cCharAt4 = charAt(this.bp + i3);
                } else {
                    if (cCharAt == ']') {
                        break;
                    }
                    cCharAt4 = cCharAt;
                    i10 = i3;
                }
                i11 = i21;
                i5 = 0;
                fArr2 = null;
            }
            i9 = i;
            i5 = 0;
            fArr2 = null;
            c = '[';
            i8 = 16;
        }
        if (i != fArr3.length) {
            fArr = new float[i][];
            System.arraycopy(fArr3, 0, fArr, 0, i);
        } else {
            fArr = fArr3;
        }
        if (cCharAt3 == ',') {
            this.bp += i7 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return fArr;
        }
        if (cCharAt3 != '}') {
            this.matchStat = -1;
            return (float[][]) null;
        }
        int i22 = i7 + 1;
        char cCharAt6 = charAt(this.bp + i7);
        if (cCharAt6 == ',') {
            this.token = 16;
        } else {
            if (cCharAt6 == ']') {
                i2 = 15;
            } else {
                if (cCharAt6 != '}') {
                    if (cCharAt6 != 26) {
                        this.matchStat = -1;
                        return (float[][]) null;
                    }
                    this.bp += i22 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                    this.matchStat = 4;
                    return fArr;
                }
                i2 = 13;
            }
            this.token = i2;
        }
        this.bp += i22 - 1;
        next();
        this.matchStat = 4;
        return fArr;
    }

    public int scanFieldInt(char[] cArr) {
        int i;
        char cCharAt;
        char cCharAt2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char cCharAt3 = charAt(this.bp + length);
        boolean z = cCharAt3 == '-';
        if (z) {
            cCharAt3 = charAt(this.bp + i2);
            i2++;
        }
        if (cCharAt3 < '0' || cCharAt3 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i3 = cCharAt3 - '0';
        while (true) {
            i = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i3 = (i3 * 10) + (cCharAt - '0');
            i2 = i;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0;
        }
        if ((i3 < 0 || i > cArr.length + 14) && !(i3 == Integer.MIN_VALUE && i == 17 && z)) {
            this.matchStat = -1;
            return 0;
        }
        int i4 = 16;
        if (cCharAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z ? -i3 : i3;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return 0;
        }
        int i5 = i + 1;
        char cCharAt4 = charAt(this.bp + i);
        if (cCharAt4 != ',') {
            if (cCharAt4 == ']') {
                i4 = 15;
            } else if (cCharAt4 == '}') {
                i4 = 13;
            } else {
                cCharAt2 = JSONLexer.EOI;
                if (cCharAt4 != 26) {
                    this.matchStat = -1;
                    return 0;
                }
                this.token = 20;
                this.bp += i5 - 1;
            }
            this.token = i4;
            this.bp += i5;
            cCharAt2 = charAt(this.bp);
        } else {
            this.token = i4;
            this.bp += i5;
            cCharAt2 = charAt(this.bp);
        }
        this.ch = cCharAt2;
        this.matchStat = 4;
        return z ? -i3 : i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x010e, code lost:
    
        r2 = r4;
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0111, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int[] scanFieldIntArray(char[] cArr) {
        boolean z;
        int i;
        char cCharAt;
        int i2;
        int i3;
        char cCharAt2;
        int[] iArr;
        char cCharAt3;
        int[] iArr2;
        int i4;
        this.matchStat = 0;
        int[] iArr3 = null;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i5 = length + 1;
        if (charAt(this.bp + length) != '[') {
            this.matchStat = -2;
            return null;
        }
        int i6 = i5 + 1;
        char cCharAt4 = charAt(this.bp + i5);
        int[] iArr4 = new int[16];
        if (cCharAt4 == ']') {
            i3 = i6 + 1;
            cCharAt2 = charAt(this.bp + i6);
            i2 = 0;
        } else {
            int i7 = 0;
            while (true) {
                if (cCharAt4 == '-') {
                    cCharAt4 = charAt(this.bp + i6);
                    i6++;
                    z = true;
                } else {
                    z = false;
                }
                if (cCharAt4 < '0' || cCharAt4 > '9') {
                    break;
                }
                int i8 = cCharAt4 - '0';
                while (true) {
                    i = i6 + 1;
                    cCharAt = charAt(this.bp + i6);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    i8 = (i8 * 10) + (cCharAt - '0');
                    i6 = i;
                }
                if (i7 >= iArr4.length) {
                    int[] iArr5 = new int[(iArr4.length * 3) / 2];
                    System.arraycopy(iArr4, 0, iArr5, 0, i7);
                    iArr4 = iArr5;
                }
                i2 = i7 + 1;
                if (z) {
                    i8 = -i8;
                }
                iArr4[i7] = i8;
                if (cCharAt == ',') {
                    i6 = i + 1;
                    cCharAt3 = charAt(this.bp + i);
                    iArr = null;
                } else {
                    if (cCharAt == ']') {
                        i3 = i + 1;
                        cCharAt2 = charAt(this.bp + i);
                        break;
                    }
                    iArr = null;
                    cCharAt3 = cCharAt;
                    i6 = i;
                }
                iArr3 = iArr;
                cCharAt4 = cCharAt3;
                i7 = i2;
            }
        }
        if (i2 != iArr4.length) {
            iArr2 = new int[i2];
            System.arraycopy(iArr4, 0, iArr2, 0, i2);
        } else {
            iArr2 = iArr4;
        }
        if (cCharAt2 == ',') {
            this.bp += i3 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return iArr2;
        }
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return null;
        }
        int i9 = i3 + 1;
        char cCharAt5 = charAt(this.bp + i3);
        if (cCharAt5 == ',') {
            this.token = 16;
        } else {
            if (cCharAt5 == ']') {
                i4 = 15;
            } else {
                if (cCharAt5 != '}') {
                    if (cCharAt5 != 26) {
                        this.matchStat = -1;
                        return null;
                    }
                    this.bp += i9 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                    this.matchStat = 4;
                    return iArr2;
                }
                i4 = 13;
            }
            this.token = i4;
        }
        this.bp += i9 - 1;
        next();
        this.matchStat = 4;
        return iArr2;
    }

    public long scanFieldLong(char[] cArr) {
        int i;
        boolean z;
        int i2;
        char cCharAt;
        char cCharAt2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt3 = charAt(this.bp + length);
        if (cCharAt3 == '-') {
            i = i3 + 1;
            cCharAt3 = charAt(this.bp + i3);
            z = true;
        } else {
            i = i3;
            z = false;
        }
        if (cCharAt3 < '0' || cCharAt3 > '9') {
            this.matchStat = -1;
            return 0L;
        }
        long j = cCharAt3 - '0';
        while (true) {
            i2 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i = i2;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (!(i2 - cArr.length < 21 && (j >= 0 || (j == Long.MIN_VALUE && z)))) {
            this.matchStat = -1;
            return 0L;
        }
        int i4 = 16;
        if (cCharAt == ',') {
            this.bp += i2;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z ? -j : j;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return 0L;
        }
        int i5 = i2 + 1;
        char cCharAt4 = charAt(this.bp + i2);
        if (cCharAt4 != ',') {
            if (cCharAt4 == ']') {
                i4 = 15;
            } else if (cCharAt4 == '}') {
                i4 = 13;
            } else {
                cCharAt2 = JSONLexer.EOI;
                if (cCharAt4 != 26) {
                    this.matchStat = -1;
                    return 0L;
                }
                this.token = 20;
                this.bp += i5 - 1;
            }
            this.token = i4;
            this.bp += i5;
            cCharAt2 = charAt(this.bp);
        } else {
            this.token = i4;
            this.bp += i5;
            cCharAt2 = charAt(this.bp);
        }
        this.ch = cCharAt2;
        this.matchStat = 4;
        return z ? -j : j;
    }

    public String scanFieldString(char[] cArr) {
        int i;
        this.matchStat = 0;
        if (charArrayCompare(cArr)) {
            int length = cArr.length;
            int i2 = length + 1;
            if (charAt(this.bp + length) == '\"') {
                int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
                if (iIndexOf == -1) {
                    throw new JSONException("unclosed str");
                }
                int length2 = this.bp + cArr.length + 1;
                String strSubString = subString(length2, iIndexOf - length2);
                if (strSubString.indexOf(92) != -1) {
                    while (true) {
                        int i3 = 0;
                        for (int i4 = iIndexOf - 1; i4 >= 0 && charAt(i4) == '\\'; i4--) {
                            i3++;
                        }
                        if (i3 % 2 == 0) {
                            break;
                        }
                        iIndexOf = indexOf('\"', iIndexOf + 1);
                    }
                    int length3 = iIndexOf - ((this.bp + cArr.length) + 1);
                    strSubString = readString(sub_chars(this.bp + cArr.length + 1, length3), length3);
                }
                int length4 = i2 + (iIndexOf - ((this.bp + cArr.length) + 1)) + 1;
                int i5 = length4 + 1;
                char cCharAt = charAt(this.bp + length4);
                if (cCharAt == ',') {
                    this.bp += i5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return strSubString;
                }
                if (cCharAt == '}') {
                    int i6 = i5 + 1;
                    char cCharAt2 = charAt(this.bp + i5);
                    if (cCharAt2 == ',') {
                        i = 16;
                    } else if (cCharAt2 == ']') {
                        i = 15;
                    } else if (cCharAt2 == '}') {
                        i = 13;
                    } else if (cCharAt2 == 26) {
                        this.token = 20;
                        this.bp += i6 - 1;
                        this.ch = JSONLexer.EOI;
                        this.matchStat = 4;
                        return strSubString;
                    }
                    this.token = i;
                    this.bp += i6;
                    this.ch = charAt(this.bp);
                    this.matchStat = 4;
                    return strSubString;
                }
            }
            this.matchStat = -1;
        } else {
            this.matchStat = -2;
        }
        return stringDefaultValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e0, code lost:
    
        if (r12 != ']') goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e6, code lost:
    
        if (r13.size() != 0) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e8, code lost:
    
        r0 = r1 + 1;
        r12 = r11.bp + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0151, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illega str");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Collection<String> scanFieldStringArray(char[] cArr, Class<?> cls) {
        int i;
        int i2;
        int i3;
        char cCharAt;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> collectionNewCollectionByType = newCollectionByType(cls);
        int length = cArr.length;
        int i5 = length + 1;
        if (charAt(this.bp + length) != '[') {
            this.matchStat = -1;
            return null;
        }
        int i6 = i5 + 1;
        int i7 = this.bp + i5;
        while (true) {
            char cCharAt2 = charAt(i7);
            if (cCharAt2 == '\"') {
                int iIndexOf = indexOf('\"', this.bp + i6);
                if (iIndexOf == -1) {
                    throw new JSONException("unclosed str");
                }
                int i8 = this.bp + i6;
                String strSubString = subString(i8, iIndexOf - i8);
                if (strSubString.indexOf(92) != -1) {
                    while (true) {
                        int i9 = 0;
                        for (int i10 = iIndexOf - 1; i10 >= 0 && charAt(i10) == '\\'; i10--) {
                            i9++;
                        }
                        if (i9 % 2 == 0) {
                            break;
                        }
                        iIndexOf = indexOf('\"', iIndexOf + 1);
                    }
                    int i11 = iIndexOf - (this.bp + i6);
                    strSubString = readString(sub_chars(this.bp + i6, i11), i11);
                }
                int i12 = i6 + (iIndexOf - (this.bp + i6)) + 1;
                i3 = i12 + 1;
                cCharAt = charAt(this.bp + i12);
                collectionNewCollectionByType.add(strSubString);
            } else {
                if (cCharAt2 != 'n' || charAt(this.bp + i6) != 'u' || charAt(this.bp + i6 + 1) != 'l' || charAt(this.bp + i6 + 2) != 'l') {
                    break;
                }
                int i13 = i6 + 3;
                i3 = i13 + 1;
                cCharAt = charAt(this.bp + i13);
                collectionNewCollectionByType.add(null);
            }
            if (cCharAt == ',') {
                i6 = i3 + 1;
                i7 = this.bp + i3;
            } else {
                if (cCharAt != ']') {
                    this.matchStat = -1;
                    return null;
                }
                i = i3 + 1;
                i2 = this.bp + i3;
            }
        }
        char cCharAt3 = charAt(i2);
        if (cCharAt3 == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return collectionNewCollectionByType;
        }
        if (cCharAt3 != '}') {
            this.matchStat = -1;
            return null;
        }
        int i14 = i + 1;
        char cCharAt4 = charAt(this.bp + i);
        if (cCharAt4 == ',') {
            i4 = 16;
        } else if (cCharAt4 == ']') {
            i4 = 15;
        } else {
            if (cCharAt4 != '}') {
                if (cCharAt4 != 26) {
                    this.matchStat = -1;
                    return null;
                }
                this.bp += i14 - 1;
                this.token = 20;
                this.ch = JSONLexer.EOI;
                this.matchStat = 4;
                return collectionNewCollectionByType;
            }
            i4 = 13;
        }
        this.token = i4;
        this.bp += i14;
        this.ch = charAt(this.bp);
        this.matchStat = 4;
        return collectionNewCollectionByType;
    }

    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        throw new UnsupportedOperationException();
    }

    public long scanFieldSymbol(char[] cArr) {
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(this.bp + i2);
            if (cCharAt == '\"') {
                int i4 = i3 + 1;
                char cCharAt2 = charAt(this.bp + i3);
                if (cCharAt2 == ',') {
                    this.bp += i4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return j;
                }
                if (cCharAt2 != '}') {
                    this.matchStat = -1;
                    return 0L;
                }
                int i5 = i4 + 1;
                char cCharAt3 = charAt(this.bp + i4);
                if (cCharAt3 == ',') {
                    i = 16;
                } else if (cCharAt3 == ']') {
                    i = 15;
                } else {
                    if (cCharAt3 != '}') {
                        if (cCharAt3 != 26) {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.token = 20;
                        this.bp += i5 - 1;
                        this.ch = JSONLexer.EOI;
                        this.matchStat = 4;
                        return j;
                    }
                    i = 13;
                }
                this.token = i;
                this.bp += i5;
                this.ch = charAt(this.bp);
                this.matchStat = 4;
                return j;
            }
            j = (j ^ ((long) cCharAt)) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i2 = i3;
        }
    }

    public UUID scanFieldUUID(char[] cArr) {
        int i;
        char cCharAt;
        UUID uuid;
        char cCharAt2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i17 = length + 1;
        char cCharAt3 = charAt(this.bp + length);
        char c = 4;
        if (cCharAt3 != '\"') {
            if (cCharAt3 == 'n') {
                int i18 = i17 + 1;
                if (charAt(this.bp + i17) == 'u') {
                    int i19 = i18 + 1;
                    if (charAt(this.bp + i18) == 'l') {
                        int i20 = i19 + 1;
                        if (charAt(this.bp + i19) == 'l') {
                            i = i20 + 1;
                            cCharAt = charAt(this.bp + i20);
                            uuid = null;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        int length2 = this.bp + cArr.length + 1;
        int i21 = iIndexOf - length2;
        char c2 = 'F';
        char c3 = 'f';
        char c4 = 'A';
        char c5 = 'a';
        char c6 = '0';
        if (i21 != 36) {
            i3 = iIndexOf;
            if (i21 == 32) {
                int i22 = 0;
                long j = 0;
                for (int i23 = 16; i22 < i23; i23 = 16) {
                    char cCharAt4 = charAt(length2 + i22);
                    if (cCharAt4 < '0' || cCharAt4 > '9') {
                        if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                            i5 = cCharAt4 - 'a';
                        } else {
                            if (cCharAt4 < 'A' || cCharAt4 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i5 = cCharAt4 - 'A';
                        }
                        i6 = i5 + 10;
                    } else {
                        i6 = cCharAt4 - '0';
                    }
                    j = (j << 4) | ((long) i6);
                    i22++;
                }
                int i24 = 16;
                long j2 = 0;
                while (i24 < 32) {
                    char cCharAt5 = charAt(length2 + i24);
                    if (cCharAt5 >= c6 && cCharAt5 <= '9') {
                        i4 = cCharAt5 - '0';
                    } else if (cCharAt5 >= c5 && cCharAt5 <= 'f') {
                        i4 = (cCharAt5 - 'a') + 10;
                    } else {
                        if (cCharAt5 < 'A' || cCharAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i4 = (cCharAt5 - 'A') + 10;
                    }
                    j2 = (j2 << 4) | ((long) i4);
                    i24++;
                    c6 = '0';
                    c5 = 'a';
                }
                uuid = new UUID(j, j2);
            }
            this.matchStat = -1;
            return null;
        }
        int i25 = 0;
        long j3 = 0;
        while (i25 < 8) {
            char cCharAt6 = charAt(length2 + i25);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                if (cCharAt6 >= 'a' && cCharAt6 <= 'f') {
                    i15 = cCharAt6 - 'a';
                } else {
                    if (cCharAt6 < c4 || cCharAt6 > c2) {
                        this.matchStat = -2;
                        return null;
                    }
                    i15 = cCharAt6 - 'A';
                }
                i16 = i15 + 10;
            } else {
                i16 = cCharAt6 - '0';
            }
            j3 = (j3 << 4) | ((long) i16);
            i25++;
            c4 = 'A';
            c2 = 'F';
        }
        int i26 = 9;
        while (i26 < 13) {
            char cCharAt7 = charAt(length2 + i26);
            if (cCharAt7 < '0' || cCharAt7 > '9') {
                if (cCharAt7 >= 'a' && cCharAt7 <= c3) {
                    i13 = cCharAt7 - 'a';
                } else {
                    if (cCharAt7 < 'A' || cCharAt7 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    i13 = cCharAt7 - 'A';
                }
                i14 = i13 + 10;
            } else {
                i14 = cCharAt7 - '0';
            }
            j3 = (j3 << 4) | ((long) i14);
            i26++;
            iIndexOf = iIndexOf;
            c3 = 'f';
        }
        i3 = iIndexOf;
        long j4 = j3;
        for (int i27 = 14; i27 < 18; i27++) {
            char cCharAt8 = charAt(length2 + i27);
            if (cCharAt8 < '0' || cCharAt8 > '9') {
                if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                    i11 = cCharAt8 - 'a';
                } else {
                    if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    i11 = cCharAt8 - 'A';
                }
                i12 = i11 + 10;
            } else {
                i12 = cCharAt8 - '0';
            }
            j4 = (j4 << 4) | ((long) i12);
        }
        int i28 = 19;
        long j5 = 0;
        while (i28 < 23) {
            char cCharAt9 = charAt(length2 + i28);
            if (cCharAt9 < '0' || cCharAt9 > '9') {
                if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                    i9 = cCharAt9 - 'a';
                } else {
                    if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    i9 = cCharAt9 - 'A';
                }
                i10 = i9 + 10;
            } else {
                i10 = cCharAt9 - '0';
            }
            j5 = (j5 << c) | ((long) i10);
            i28++;
            j4 = j4;
            c = 4;
        }
        long j6 = j4;
        long j7 = j5;
        for (int i29 = 24; i29 < 36; i29++) {
            char cCharAt10 = charAt(length2 + i29);
            if (cCharAt10 < '0' || cCharAt10 > '9') {
                if (cCharAt10 >= 'a' && cCharAt10 <= 'f') {
                    i7 = cCharAt10 - 'a';
                } else {
                    if (cCharAt10 < 'A' || cCharAt10 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    i7 = cCharAt10 - 'A';
                }
                i8 = i7 + 10;
            } else {
                i8 = cCharAt10 - '0';
            }
            j7 = (j7 << 4) | ((long) i8);
        }
        uuid = new UUID(j6, j7);
        int length3 = i17 + (i3 - ((this.bp + cArr.length) + 1)) + 1;
        i = length3 + 1;
        cCharAt = charAt(this.bp + length3);
        if (cCharAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        }
        if (cCharAt == '}') {
            int i30 = i + 1;
            char cCharAt11 = charAt(this.bp + i);
            if (cCharAt11 == ',') {
                this.token = 16;
            } else {
                if (cCharAt11 == ']') {
                    i2 = 15;
                } else if (cCharAt11 == '}') {
                    i2 = 13;
                } else if (cCharAt11 == 26) {
                    this.token = 20;
                    this.bp += i30 - 1;
                    cCharAt2 = JSONLexer.EOI;
                    this.ch = cCharAt2;
                    this.matchStat = 4;
                    return uuid;
                }
                this.token = i2;
            }
            this.bp += i30;
            cCharAt2 = charAt(this.bp);
            this.ch = cCharAt2;
            this.matchStat = 4;
            return uuid;
        }
        this.matchStat = -1;
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00ca A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00cc -> B:52:0x00b8). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float scanFloat(char c) {
        int i;
        int i2;
        char cCharAt;
        int i3;
        int i4;
        float f;
        int i5;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            if (cCharAt2 != 'n' || charAt(this.bp + i) != 'u' || charAt(this.bp + i + 1) != 'l' || charAt(this.bp + i + 2) != 'l') {
                this.matchStat = -1;
                return 0.0f;
            }
            this.matchStat = 5;
            int i6 = i + 3;
            int i7 = i6 + 1;
            char cCharAt3 = charAt(this.bp + i6);
            if (z && cCharAt3 == '\"') {
                cCharAt3 = charAt(this.bp + i7);
                i7++;
            }
            while (cCharAt3 != ',') {
                if (cCharAt3 == ']') {
                    this.bp += i7;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                }
                if (!isWhitespace(cCharAt3)) {
                    this.matchStat = -1;
                    return 0.0f;
                }
                cCharAt3 = charAt(this.bp + i7);
                i7++;
            }
            this.bp += i7;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        long j = cCharAt2 - '0';
        while (true) {
            i2 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i = i2;
        }
        long j2 = 1;
        if (cCharAt == '.') {
            int i8 = i2 + 1;
            char cCharAt4 = charAt(this.bp + i2);
            if (cCharAt4 < '0' || cCharAt4 > '9') {
                this.matchStat = -1;
                return 0.0f;
            }
            j = (j * 10) + ((long) (cCharAt4 - '0'));
            j2 = 10;
            while (true) {
                i5 = i8 + 1;
                cCharAt = charAt(this.bp + i8);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j = (j * 10) + ((long) (cCharAt - '0'));
                j2 *= 10;
                i8 = i5;
            }
            i2 = i5;
        }
        long j3 = j2;
        boolean z3 = cCharAt == 'e' || cCharAt == 'E';
        if (z3) {
            int i9 = i2 + 1;
            char cCharAt5 = charAt(this.bp + i2);
            if (cCharAt5 == '+' || cCharAt5 == '-') {
                int i10 = i9 + 1;
                cCharAt = charAt(this.bp + i9);
                i2 = i10;
            } else {
                i2 = i9;
                cCharAt = cCharAt5;
            }
            if (cCharAt >= '0' && cCharAt <= '9') {
                i9 = i2 + 1;
                cCharAt5 = charAt(this.bp + i2);
                i2 = i9;
                cCharAt = cCharAt5;
                if (cCharAt >= '0') {
                    i9 = i2 + 1;
                    cCharAt5 = charAt(this.bp + i2);
                    i2 = i9;
                    cCharAt = cCharAt5;
                    if (cCharAt >= '0') {
                    }
                }
            }
        }
        if (!z) {
            i3 = this.bp;
            i4 = ((this.bp + i2) - i3) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0.0f;
            }
            int i11 = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            i3 = this.bp + 1;
            i4 = ((this.bp + i11) - i3) - 2;
            i2 = i11;
        }
        if (z3 || i4 >= 17) {
            f = Float.parseFloat(subString(i3, i4));
        } else {
            f = (float) (j / j3);
            if (z2) {
                f = -f;
            }
        }
        if (cCharAt != c) {
            this.matchStat = -1;
            return f;
        }
        this.bp += i2;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        this.token = 16;
        return f;
    }

    public final void scanHex() {
        char next;
        if (this.ch != 'x') {
            throw new JSONException("illegal state. " + this.ch);
        }
        next();
        if (this.ch != '\'') {
            throw new JSONException("illegal state. " + this.ch);
        }
        this.np = this.bp;
        next();
        if (this.ch == '\'') {
            next();
            this.token = 26;
            return;
        }
        while (true) {
            next = next();
            if ((next < '0' || next > '9') && (next < 'A' || next > 'F')) {
                break;
            } else {
                this.sp++;
            }
        }
        if (next == '\'') {
            this.sp++;
            next();
            this.token = 26;
        } else {
            throw new JSONException("illegal state. " + next);
        }
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String strStringVal = stringVal();
        this.token = "null".equalsIgnoreCase(strStringVal) ? 8 : "new".equals(strStringVal) ? 9 : "true".equals(strStringVal) ? 6 : "false".equals(strStringVal) ? 7 : "undefined".equals(strStringVal) ? 23 : "Set".equals(strStringVal) ? 21 : "TreeSet".equals(strStringVal) ? 22 : 18;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e5  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00ce -> B:57:0x00cf). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int scanInt(char c) {
        int i;
        int i2;
        int i3;
        char cCharAt;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            int i4 = cCharAt2 - '0';
            while (true) {
                i3 = i + 1;
                cCharAt = charAt(this.bp + i);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i4 = (i4 * 10) + (cCharAt - '0');
                i = i3;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (i4 < 0) {
                this.matchStat = -1;
                return 0;
            }
            while (cCharAt != c) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return z2 ? -i4 : i4;
                }
                char cCharAt3 = charAt(this.bp + i3);
                i3++;
                cCharAt = cCharAt3;
            }
            this.bp += i3;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z2 ? -i4 : i4;
        }
        if (cCharAt2 != 'n' || charAt(this.bp + i) != 'u' || charAt(this.bp + i + 1) != 'l' || charAt(this.bp + i + 2) != 'l') {
            this.matchStat = -1;
            return 0;
        }
        this.matchStat = 5;
        int i5 = i + 3;
        int i6 = i5 + 1;
        char cCharAt4 = charAt(this.bp + i5);
        if (z && cCharAt4 == '\"') {
            i2 = i6 + 1;
            cCharAt4 = charAt(this.bp + i6);
        } else {
            i2 = i6;
        }
        if (cCharAt4 == ',') {
            if (cCharAt4 == ']') {
                this.bp += i2;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 15;
                return 0;
            }
            if (!isWhitespace(cCharAt4)) {
                this.matchStat = -1;
                return 0;
            }
            i6 = i2 + 1;
            cCharAt4 = charAt(this.bp + i2);
            i2 = i6;
            if (cCharAt4 == ',') {
                this.bp += i2;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 16;
                return 0;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0122  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:68:0x010b -> B:69:0x010c). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long scanLong(char c) {
        int i;
        int i2;
        int i3;
        char cCharAt;
        char c2;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j = cCharAt2 - '0';
            while (true) {
                i3 = i + 1;
                cCharAt = charAt(this.bp + i);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j = (j * 10) + ((long) (cCharAt - '0'));
                i = i3;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (!(j >= 0 || (j == Long.MIN_VALUE && z2))) {
                throw new NumberFormatException(subString(this.bp, i3 - 1));
            }
            if (!z) {
                c2 = c;
            } else {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(this.bp + i3);
                c2 = c;
                i3++;
            }
            while (cCharAt != c2) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return j;
                }
                cCharAt = charAt(this.bp + i3);
                i3++;
            }
            this.bp += i3;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z2 ? -j : j;
        }
        if (cCharAt2 != 'n' || charAt(this.bp + i) != 'u' || charAt(this.bp + i + 1) != 'l' || charAt(this.bp + i + 2) != 'l') {
            this.matchStat = -1;
            return 0L;
        }
        this.matchStat = 5;
        int i4 = i + 3;
        int i5 = i4 + 1;
        char cCharAt3 = charAt(this.bp + i4);
        if (z && cCharAt3 == '\"') {
            i2 = i5 + 1;
            cCharAt3 = charAt(this.bp + i5);
        } else {
            i2 = i5;
        }
        if (cCharAt3 == ',') {
            if (cCharAt3 == ']') {
                this.bp += i2;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 15;
                return 0L;
            }
            if (!isWhitespace(cCharAt3)) {
                this.matchStat = -1;
                return 0L;
            }
            i5 = i2 + 1;
            cCharAt3 = charAt(this.bp + i2);
            i2 = i5;
            if (cCharAt3 == ',') {
                this.bp += i2;
                this.ch = charAt(this.bp);
                this.matchStat = 5;
                this.token = 16;
                return 0L;
            }
        }
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    public final void scanNullOrNew(boolean z) {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        next();
        if (this.ch != 'u') {
            if (this.ch != 'e') {
                throw new JSONException("error parse new");
            }
            next();
            if (this.ch != 'w') {
                throw new JSONException("error parse new");
            }
            next();
            if (this.ch != ' ' && this.ch != ',' && this.ch != '}' && this.ch != ']' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != 26 && this.ch != '\f' && this.ch != '\b') {
                throw new JSONException("scan new error");
            }
            this.token = 9;
            return;
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        if (this.ch != ' ' && this.ch != ',' && this.ch != '}' && this.ch != ']' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != 26 && ((this.ch != ':' || !z) && this.ch != '\f' && this.ch != '\b')) {
            throw new JSONException("scan null error");
        }
        this.token = 8;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x008b, code lost:
    
        if (r9.ch != '-') goto L44;
     */
    /* JADX WARN: Path cross not found for [B:43:0x008d, B:41:0x0089], limit reached: 58 */
    /* JADX WARN: Path cross not found for [B:43:0x008d, B:44:0x0095], limit reached: 58 */
    /* JADX WARN: Path cross not found for [B:44:0x0095, B:43:0x008d], limit reached: 58 */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ad  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x009b -> B:43:0x008d). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void scanNumber() {
        this.np = this.bp;
        boolean z = true;
        if (this.ch == '-') {
            this.sp++;
            next();
        }
        while (this.ch >= '0' && this.ch <= '9') {
            this.sp++;
            next();
        }
        boolean z2 = false;
        if (this.ch == '.') {
            do {
                this.sp++;
                next();
                if (this.ch < '0') {
                    break;
                }
            } while (this.ch <= '9');
            z2 = true;
        }
        if (this.ch != 'L' && this.ch != 'S' && this.ch != 'B') {
            if (this.ch != 'F' && this.ch != 'D') {
                if (this.ch == 'e' || this.ch == 'E') {
                    this.sp++;
                    next();
                    if (this.ch != '+') {
                    }
                    this.sp++;
                    next();
                    if (this.ch >= '0' || this.ch > '9') {
                        if (this.ch != 'D' || this.ch == 'F') {
                            this.sp++;
                            next();
                        }
                    }
                    this.sp++;
                    next();
                    if (this.ch >= '0') {
                    }
                    if (this.ch != 'D') {
                        this.sp++;
                        next();
                    }
                }
            }
            this.token = !z ? 3 : 2;
        }
        this.sp++;
        next();
        z = z2;
        this.token = !z ? 3 : 2;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char c) {
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            if (charAt(this.bp + 4) != c) {
                this.matchStat = -1;
                return null;
            }
            this.bp += 5;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return null;
        }
        int i = 1;
        while (cCharAt != '\"') {
            if (!isWhitespace(cCharAt)) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            cCharAt = charAt(this.bp + i);
            i++;
        }
        int i2 = this.bp + i;
        int iIndexOf = indexOf('\"', i2);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        String strSubString = subString(this.bp + i, iIndexOf - i2);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i3 = 0;
                for (int i4 = iIndexOf - 1; i4 >= 0 && charAt(i4) == '\\'; i4--) {
                    i3++;
                }
                if (i3 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf('\"', iIndexOf + 1);
            }
            int i5 = iIndexOf - i2;
            strSubString = readString(sub_chars(this.bp + 1, i5), i5);
        }
        int i6 = i + (iIndexOf - i2) + 1;
        int i7 = i6 + 1;
        char cCharAt2 = charAt(this.bp + i6);
        while (cCharAt2 != c) {
            if (!isWhitespace(cCharAt2)) {
                this.matchStat = -1;
                return strSubString;
            }
            cCharAt2 = charAt(this.bp + i7);
            i7++;
        }
        this.bp += i7;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        this.token = 16;
        return strSubString;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        int i;
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            }
            char c = JSONLexer.EOI;
            if (next3 != 26) {
                c = '\\';
                boolean z = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp >= this.sbuf.length) {
                            int length = this.sbuf.length * 2;
                            if (this.sp > length) {
                                length = this.sp;
                            }
                            char[] cArr = new char[length];
                            System.arraycopy(this.sbuf, 0, cArr, 0, this.sbuf.length);
                            this.sbuf = cArr;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    switch (next4) {
                        case '/':
                            next3 = '/';
                            putChar(next3);
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case '1':
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            next3 = 5;
                            putChar(next3);
                            break;
                        case '6':
                            next3 = 6;
                            putChar(next3);
                            break;
                        case '7':
                            next3 = 7;
                            putChar(next3);
                            break;
                        default:
                            switch (next4) {
                                case 't':
                                    next3 = '\t';
                                    putChar(next3);
                                    break;
                                case 'u':
                                    i = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                    next3 = (char) i;
                                    putChar(next3);
                                    break;
                                case 'v':
                                    next3 = 11;
                                    putChar(next3);
                                    break;
                                default:
                                    switch (next4) {
                                        case '\"':
                                            putChar('\"');
                                            break;
                                        case '\'':
                                            next3 = '\'';
                                            putChar(next3);
                                            break;
                                        case 'F':
                                        case 'f':
                                            next3 = '\f';
                                            putChar(next3);
                                            break;
                                        case '\\':
                                            break;
                                        case 'b':
                                            next3 = '\b';
                                            putChar(next3);
                                            break;
                                        case 'n':
                                            next3 = '\n';
                                            putChar(next3);
                                            break;
                                        case 'r':
                                            next3 = '\r';
                                            putChar(next3);
                                            break;
                                        case 'x':
                                            next = next();
                                            next2 = next();
                                            boolean z2 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                            if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                                z = false;
                                            }
                                            if (z2 && z) {
                                                i = (digits[next] * 16) + digits[next2];
                                                next3 = (char) i;
                                                putChar(next3);
                                            }
                                            break;
                                        default:
                                            this.ch = next4;
                                            throw new JSONException("unclosed string : " + next4);
                                    }
                                    break;
                            }
                            break;
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(next3);
                } else {
                    char[] cArr2 = this.sbuf;
                    int i2 = this.sp;
                    this.sp = i2 + 1;
                    cArr2[i2] = next3;
                }
            } else if (isEOF()) {
                throw new JSONException("unclosed string : " + next3);
            }
            putChar(c);
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void scanStringArray(Collection<String> collection, char c) {
        int i;
        char cCharAt;
        int i2;
        int i3;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        char c2 = 'u';
        char c3 = 'n';
        if (cCharAt2 == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l' && charAt(this.bp + 1 + 3) == c) {
            this.bp += 5;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            return;
        }
        if (cCharAt2 != '[') {
            this.matchStat = -1;
            return;
        }
        char cCharAt3 = charAt(this.bp + 1);
        int i4 = 2;
        while (true) {
            if (cCharAt3 == c3 && charAt(this.bp + i4) == c2 && charAt(this.bp + i4 + 1) == 'l' && charAt(this.bp + i4 + 2) == 'l') {
                int i5 = i4 + 3;
                i = i5 + 1;
                cCharAt = charAt(this.bp + i5);
                collection.add(null);
            } else {
                if (cCharAt3 == ']' && collection.size() == 0) {
                    i2 = i4 + 1;
                    i3 = this.bp + i4;
                    break;
                }
                if (cCharAt3 != '\"') {
                    this.matchStat = -1;
                    return;
                }
                int i6 = this.bp + i4;
                int iIndexOf = indexOf('\"', i6);
                if (iIndexOf == -1) {
                    throw new JSONException("unclosed str");
                }
                String strSubString = subString(this.bp + i4, iIndexOf - i6);
                if (strSubString.indexOf(92) != -1) {
                    while (true) {
                        int i7 = 0;
                        for (int i8 = iIndexOf - 1; i8 >= 0 && charAt(i8) == '\\'; i8--) {
                            i7++;
                        }
                        if (i7 % 2 == 0) {
                            break;
                        } else {
                            iIndexOf = indexOf('\"', iIndexOf + 1);
                        }
                    }
                    int i9 = iIndexOf - i6;
                    strSubString = readString(sub_chars(this.bp + i4, i9), i9);
                }
                int i10 = i4 + (iIndexOf - (this.bp + i4)) + 1;
                i = i10 + 1;
                cCharAt = charAt(this.bp + i10);
                collection.add(strSubString);
            }
            if (cCharAt == ',') {
                i4 = i + 1;
                cCharAt3 = charAt(this.bp + i);
                c2 = 'u';
                c3 = 'n';
            } else if (cCharAt != ']') {
                this.matchStat = -1;
                return;
            } else {
                i2 = i + 1;
                i3 = this.bp + i;
            }
        }
        if (charAt(i3) != c) {
            this.matchStat = -1;
            return;
        }
        this.bp += i2;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        int i;
        skipWhitespace();
        if (this.ch == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (this.ch == '\'') {
            if (isEnabled(Feature.AllowSingleQuotes)) {
                return scanSymbol(symbolTable, '\'');
            }
            throw new JSONException("syntax error");
        }
        if (this.ch == '}') {
            next();
            i = 13;
        } else if (this.ch == ',') {
            next();
            i = 16;
        } else {
            if (this.ch != 26) {
                if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
                    return scanSymbolUnQuoted(symbolTable);
                }
                throw new JSONException("syntax error");
            }
            i = 20;
        }
        this.token = i;
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char c) {
        String strAddSymbol;
        int i;
        this.np = this.bp;
        this.sp = 0;
        boolean z = false;
        int i2 = 0;
        while (true) {
            char next = next();
            if (next == c) {
                this.token = 4;
                if (z) {
                    strAddSymbol = symbolTable.addSymbol(this.sbuf, 0, this.sp, i2);
                } else {
                    strAddSymbol = addSymbol(this.np == -1 ? 0 : this.np + 1, this.sp, i2, symbolTable);
                }
                this.sp = 0;
                next();
                return strAddSymbol;
            }
            if (next == 26) {
                throw new JSONException("unclosed.str");
            }
            if (next == '\\') {
                next = 2;
                if (!z) {
                    if (this.sp >= this.sbuf.length) {
                        int length = this.sbuf.length * 2;
                        if (this.sp > length) {
                            length = this.sp;
                        }
                        char[] cArr = new char[length];
                        System.arraycopy(this.sbuf, 0, cArr, 0, this.sbuf.length);
                        this.sbuf = cArr;
                    }
                    arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    z = true;
                }
                char next2 = next();
                switch (next2) {
                    case '/':
                        i = i2 * 31;
                        next = '/';
                        i2 = i + next;
                        putChar(next);
                        break;
                    case '0':
                        i2 = (i2 * 31) + next2;
                        putChar((char) 0);
                        break;
                    case '1':
                        i2 = (i2 * 31) + next2;
                        putChar((char) 1);
                        break;
                    case '2':
                        i2 = (i2 * 31) + next2;
                        putChar(next);
                        break;
                    case '3':
                        i2 = (i2 * 31) + next2;
                        putChar((char) 3);
                        break;
                    case '4':
                        i2 = (i2 * 31) + next2;
                        putChar((char) 4);
                        break;
                    case '5':
                        i2 = (i2 * 31) + next2;
                        next = 5;
                        putChar(next);
                        break;
                    case '6':
                        i2 = (i2 * 31) + next2;
                        next = 6;
                        putChar(next);
                        break;
                    case '7':
                        i2 = (i2 * 31) + next2;
                        next = 7;
                        putChar(next);
                        break;
                    default:
                        switch (next2) {
                            case 't':
                                i = i2 * 31;
                                next = '\t';
                                i2 = i + next;
                                putChar(next);
                                break;
                            case 'u':
                                int i3 = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                i2 = (i2 * 31) + i3;
                                next = (char) i3;
                                putChar(next);
                                break;
                            case 'v':
                                i2 = (i2 * 31) + 11;
                                next = 11;
                                putChar(next);
                                break;
                            default:
                                switch (next2) {
                                    case '\"':
                                        i = i2 * 31;
                                        next = '\"';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case '\'':
                                        i = i2 * 31;
                                        next = '\'';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case 'F':
                                    case 'f':
                                        i = i2 * 31;
                                        next = '\f';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case '\\':
                                        i2 = (i2 * 31) + 92;
                                        putChar('\\');
                                        break;
                                    case 'b':
                                        i = i2 * 31;
                                        next = '\b';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case 'n':
                                        i = i2 * 31;
                                        next = '\n';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case 'r':
                                        i = i2 * 31;
                                        next = '\r';
                                        i2 = i + next;
                                        putChar(next);
                                        break;
                                    case 'x':
                                        char next3 = next();
                                        this.ch = next3;
                                        char next4 = next();
                                        this.ch = next4;
                                        next = (char) ((digits[next3] * 16) + digits[next4]);
                                        i2 = (i2 * 31) + next;
                                        putChar(next);
                                        break;
                                    default:
                                        this.ch = next2;
                                        throw new JSONException("unclosed.str.lit");
                                }
                                break;
                        }
                        break;
                }
            } else {
                i2 = (i2 * 31) + next;
                if (!z) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(next);
                } else {
                    char[] cArr2 = this.sbuf;
                    int i4 = this.sp;
                    this.sp = i4 + 1;
                    cArr2[i4] = next;
                }
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] zArr = IOUtils.firstIdentifierFlags;
        int i = this.ch;
        if (!(this.ch >= zArr.length || zArr[i])) {
            throw new JSONException("illegal identifier : " + this.ch + info());
        }
        boolean[] zArr2 = IOUtils.identifierFlags;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char next = next();
            if (next < zArr2.length && !zArr2[next]) {
                break;
            }
            i = (i * 31) + next;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && i == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
            return null;
        }
        return symbolTable == null ? subString(this.np, this.sp) : addSymbol(this.np, this.sp, i, symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char c) {
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            if (charAt(this.bp + 4) != c) {
                this.matchStat = -1;
                return null;
            }
            this.bp += 5;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return null;
        }
        if (cCharAt != '\"') {
            this.matchStat = -1;
            return null;
        }
        int i = 0;
        int i2 = 1;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt2 = charAt(this.bp + i2);
            if (cCharAt2 == '\"') {
                int i4 = this.bp + 0 + 1;
                String strAddSymbol = addSymbol(i4, ((this.bp + i3) - i4) - 1, i, symbolTable);
                int i5 = i3 + 1;
                char cCharAt3 = charAt(this.bp + i3);
                while (cCharAt3 != c) {
                    if (!isWhitespace(cCharAt3)) {
                        this.matchStat = -1;
                        return strAddSymbol;
                    }
                    cCharAt3 = charAt(this.bp + i5);
                    i5++;
                }
                this.bp += i5;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                return strAddSymbol;
            }
            i = (i * 31) + cCharAt2;
            if (cCharAt2 == '\\') {
                this.matchStat = -1;
                return null;
            }
            i2 = i3;
        }
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != ' ' && this.ch != ',' && this.ch != '}' && this.ch != ']' && this.ch != '\n' && this.ch != '\r' && this.ch != '\t' && this.ch != 26 && this.ch != '\f' && this.ch != '\b' && this.ch != ':' && this.ch != '/') {
            throw new JSONException("scan true error");
        }
        this.token = 6;
    }

    public final int scanType(String str) {
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(typeFieldName)) {
            return -2;
        }
        int length = this.bp + typeFieldName.length;
        int length2 = str.length();
        for (int i2 = 0; i2 < length2; i2++) {
            if (str.charAt(i2) != charAt(length + i2)) {
                return -1;
            }
        }
        int i3 = length + length2;
        if (charAt(i3) != '\"') {
            return -1;
        }
        int i4 = i3 + 1;
        this.ch = charAt(i4);
        if (this.ch == ',') {
            int i5 = i4 + 1;
            this.ch = charAt(i5);
            this.bp = i5;
            this.token = 16;
            return 3;
        }
        if (this.ch == '}') {
            i4++;
            this.ch = charAt(i4);
            if (this.ch == ',') {
                this.token = 16;
            } else {
                if (this.ch == ']') {
                    i = 15;
                } else if (this.ch == '}') {
                    i = 13;
                } else {
                    if (this.ch != 26) {
                        return -1;
                    }
                    this.token = 20;
                    this.matchStat = 4;
                }
                this.token = i;
            }
            i4++;
            this.ch = charAt(i4);
            this.matchStat = 4;
        }
        this.bp = i4;
        return this.matchStat;
    }

    public UUID scanUUID(char c) {
        int i;
        char cCharAt;
        UUID uuid;
        char cCharAt2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        this.matchStat = 0;
        char cCharAt3 = charAt(this.bp + 0);
        char c2 = 4;
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf('\"', this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i17 = this.bp + 1;
            int i18 = iIndexOf - i17;
            char c3 = 'F';
            char c4 = 'f';
            char c5 = '9';
            char c6 = 'A';
            char c7 = 'a';
            char c8 = '0';
            if (i18 != 36) {
                i3 = iIndexOf;
                if (i18 == 32) {
                    long j = 0;
                    for (int i19 = 0; i19 < 16; i19++) {
                        char cCharAt4 = charAt(i17 + i19);
                        if (cCharAt4 < '0' || cCharAt4 > '9') {
                            if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                                i5 = cCharAt4 - 'a';
                            } else {
                                if (cCharAt4 < 'A' || cCharAt4 > 'F') {
                                    this.matchStat = -2;
                                    return null;
                                }
                                i5 = cCharAt4 - 'A';
                            }
                            i6 = i5 + 10;
                        } else {
                            i6 = cCharAt4 - '0';
                        }
                        j = (j << 4) | ((long) i6);
                    }
                    long j2 = 0;
                    for (int i20 = 16; i20 < 32; i20++) {
                        char cCharAt5 = charAt(i17 + i20);
                        if (cCharAt5 >= '0' && cCharAt5 <= '9') {
                            i4 = cCharAt5 - '0';
                        } else if (cCharAt5 >= 'a' && cCharAt5 <= 'f') {
                            i4 = (cCharAt5 - 'a') + 10;
                        } else {
                            if (cCharAt5 < 'A' || cCharAt5 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i4 = (cCharAt5 - 'A') + 10;
                        }
                        j2 = (j2 << 4) | ((long) i4);
                    }
                    uuid = new UUID(j, j2);
                }
                this.matchStat = -1;
                return null;
            }
            int i21 = 0;
            long j3 = 0;
            while (i21 < 8) {
                char cCharAt6 = charAt(i17 + i21);
                if (cCharAt6 < '0' || cCharAt6 > '9') {
                    if (cCharAt6 >= 'a' && cCharAt6 <= c4) {
                        i15 = cCharAt6 - 'a';
                    } else {
                        if (cCharAt6 < 'A' || cCharAt6 > c3) {
                            this.matchStat = -2;
                            return null;
                        }
                        i15 = cCharAt6 - 'A';
                    }
                    i16 = i15 + 10;
                } else {
                    i16 = cCharAt6 - '0';
                }
                j3 = (j3 << 4) | ((long) i16);
                i21++;
                c3 = 'F';
                c4 = 'f';
            }
            int i22 = 9;
            while (i22 < 13) {
                char cCharAt7 = charAt(i17 + i22);
                if (cCharAt7 < '0' || cCharAt7 > '9') {
                    if (cCharAt7 >= c7 && cCharAt7 <= 'f') {
                        i13 = cCharAt7 - 'a';
                    } else {
                        if (cCharAt7 < c6 || cCharAt7 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i13 = cCharAt7 - 'A';
                    }
                    i14 = i13 + 10;
                } else {
                    i14 = cCharAt7 - '0';
                }
                j3 = (j3 << 4) | ((long) i14);
                i22++;
                c6 = 'A';
                c7 = 'a';
            }
            long j4 = j3;
            for (int i23 = 14; i23 < 18; i23++) {
                char cCharAt8 = charAt(i17 + i23);
                if (cCharAt8 < '0' || cCharAt8 > '9') {
                    if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                        i11 = cCharAt8 - 'a';
                    } else {
                        if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i11 = cCharAt8 - 'A';
                    }
                    i12 = i11 + 10;
                } else {
                    i12 = cCharAt8 - '0';
                }
                j4 = (j4 << 4) | ((long) i12);
            }
            int i24 = 19;
            long j5 = 0;
            while (i24 < 23) {
                char cCharAt9 = charAt(i17 + i24);
                if (cCharAt9 < '0' || cCharAt9 > c5) {
                    if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                        i9 = cCharAt9 - 'a';
                    } else {
                        if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i9 = cCharAt9 - 'A';
                    }
                    i10 = i9 + 10;
                } else {
                    i10 = cCharAt9 - '0';
                }
                j5 = (j5 << 4) | ((long) i10);
                i24++;
                iIndexOf = iIndexOf;
                c5 = '9';
            }
            i3 = iIndexOf;
            int i25 = 24;
            long j6 = j5;
            while (i25 < 36) {
                char cCharAt10 = charAt(i17 + i25);
                if (cCharAt10 < c8 || cCharAt10 > '9') {
                    if (cCharAt10 >= 'a' && cCharAt10 <= 'f') {
                        i7 = cCharAt10 - 'a';
                    } else {
                        if (cCharAt10 < 'A' || cCharAt10 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i7 = cCharAt10 - 'A';
                    }
                    i8 = i7 + 10;
                } else {
                    i8 = cCharAt10 - '0';
                }
                j6 = (j6 << c2) | ((long) i8);
                i25++;
                c8 = '0';
                c2 = 4;
            }
            uuid = new UUID(j4, j6);
            int i26 = (i3 - (this.bp + 1)) + 1 + 1;
            i = i26 + 1;
            cCharAt = charAt(this.bp + i26);
        } else {
            if (cCharAt3 != 'n' || charAt(this.bp + 1) != 'u' || charAt(this.bp + 2) != 'l' || charAt(this.bp + 3) != 'l') {
                this.matchStat = -1;
                return null;
            }
            i = 5;
            cCharAt = charAt(this.bp + 4);
            uuid = null;
        }
        if (cCharAt == ',') {
            this.bp += i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return uuid;
        }
        if (cCharAt == ']') {
            int i27 = i + 1;
            char cCharAt11 = charAt(this.bp + i);
            if (cCharAt11 == ',') {
                this.token = 16;
            } else {
                if (cCharAt11 == ']') {
                    i2 = 15;
                } else if (cCharAt11 == '}') {
                    i2 = 13;
                } else if (cCharAt11 == 26) {
                    this.token = 20;
                    this.bp += i27 - 1;
                    cCharAt2 = JSONLexer.EOI;
                    this.ch = cCharAt2;
                    this.matchStat = 4;
                    return uuid;
                }
                this.token = i2;
            }
            this.bp += i27;
            cCharAt2 = charAt(this.bp);
            this.ch = cCharAt2;
            this.matchStat = 4;
            return uuid;
        }
        this.matchStat = -1;
        return null;
    }

    public boolean seekArrayToItem(int i) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long j, boolean z) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long[] jArr) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setToken(int i) {
        this.token = i;
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    protected void skipComment() {
        next();
        if (this.ch == '/') {
            do {
                next();
                if (this.ch == '\n') {
                    next();
                    return;
                }
            } while (this.ch != 26);
            return;
        }
        if (this.ch != '*') {
            throw new JSONException("invalid comment");
        }
        while (true) {
            next();
            while (this.ch != 26) {
                if (this.ch == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                }
            }
            return;
        }
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                next();
            } else if (this.ch != '/') {
                return;
            } else {
                skipComment();
            }
        }
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    protected abstract char[] sub_chars(int i, int i2);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }
}
