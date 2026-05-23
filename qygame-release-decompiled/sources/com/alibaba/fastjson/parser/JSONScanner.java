package com.alibaba.fastjson.parser;

import androidx.core.internal.view.SupportMenu;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String str, int i) {
        super(i);
        this.text = str;
        this.len = this.text.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    public JSONScanner(char[] cArr, int i) {
        this(cArr, i, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArr, int i, int i2) {
        this(new String(cArr, 0, i), i2);
    }

    static boolean charArrayCompare(String str, int i, char[] cArr) {
        int length = cArr.length;
        if (length + i > str.length()) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (cArr[i2] != str.charAt(i + i2)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkDate(char c, char c2, char c3, char c4, char c5, char c6, int i, int i2) {
        if (c >= '0' && c <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9') {
            if (c5 == '0') {
                if (c6 < '1' || c6 > '9') {
                    return false;
                }
            } else if (c5 != '1' || (c6 != '0' && c6 != '1' && c6 != '2')) {
                return false;
            }
            if (i == 48) {
                return i2 >= 49 && i2 <= 57;
            }
            if (i != 49 && i != 50) {
                return i == 51 && (i2 == 48 || i2 == 49);
            }
            if (i2 >= 48 && i2 <= 57) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTime(char c, char c2, char c3, char c4, char c5, char c6) {
        if (c == '0') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else {
            if (c != '1') {
                if (c == '2' && c2 >= '0' && c2 <= '4') {
                }
                return false;
            }
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        }
        if (c3 < '0' || c3 > '5') {
            if (c3 != '6' || c4 != '0') {
                return false;
            }
        } else if (c4 < '0' || c4 > '9') {
            return false;
        }
        return (c5 < '0' || c5 > '5') ? c5 == '6' && c6 == '0' : c6 >= '0' && c6 <= '9';
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0205 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0568 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0569  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean scanISO8601DateIfMatch(boolean z, int i) {
        int i2;
        boolean z2;
        char cCharAt;
        char c;
        char c2;
        char c3;
        int i3;
        int i4;
        int i5;
        int i6;
        char c4;
        char cCharAt2;
        char cCharAt3;
        char c5;
        char c6;
        int i7;
        char c7;
        char c8;
        char c9;
        char c10;
        int i8;
        int i9;
        int i10;
        char cCharAt4;
        int i11;
        char c11;
        char cCharAt5;
        char cCharAt6;
        int i12;
        char cCharAt7;
        char cCharAt8;
        char cCharAt9;
        if (i < 8) {
            return false;
        }
        char cCharAt10 = charAt(this.bp);
        char cCharAt11 = charAt(this.bp + 1);
        char cCharAt12 = charAt(this.bp + 2);
        char cCharAt13 = charAt(this.bp + 3);
        char cCharAt14 = charAt(this.bp + 4);
        char cCharAt15 = charAt(this.bp + 5);
        char cCharAt16 = charAt(this.bp + 6);
        char cCharAt17 = charAt(this.bp + 7);
        if (!z && i > 13) {
            char cCharAt18 = charAt((this.bp + i) - 1);
            char cCharAt19 = charAt((this.bp + i) - 2);
            if (cCharAt10 == '/' && cCharAt11 == 'D' && cCharAt12 == 'a' && cCharAt13 == 't' && cCharAt14 == 'e' && cCharAt15 == '(' && cCharAt18 == '/' && cCharAt19 == ')') {
                int i13 = -1;
                for (int i14 = 6; i14 < i; i14++) {
                    char cCharAt20 = charAt(this.bp + i14);
                    if (cCharAt20 != '+') {
                        if (cCharAt20 < '0' || cCharAt20 > '9') {
                            break;
                        }
                    } else {
                        i13 = i14;
                    }
                }
                if (i13 == -1) {
                    return false;
                }
                int i15 = this.bp + 6;
                long j = Long.parseLong(subString(i15, (this.bp + i13) - i15));
                this.calendar = Calendar.getInstance(this.timeZone, this.locale);
                this.calendar.setTimeInMillis(j);
                this.token = 5;
                return true;
            }
        }
        if (i == 8 || i == 14) {
            i2 = 5;
            z2 = false;
            if (!z) {
                return z2;
            }
            char cCharAt21 = charAt(this.bp + 8);
            boolean z3 = cCharAt14 == '-' && cCharAt17 == '-';
            boolean z4 = z3 && i == 16;
            boolean z5 = z3 && i == 17;
            if (z5 || z4) {
                cCharAt = charAt(this.bp + 9);
                c = cCharAt15;
                c2 = cCharAt16;
                c3 = cCharAt21;
            } else if (cCharAt14 == '-' && cCharAt16 == '-') {
                c2 = cCharAt15;
                cCharAt = cCharAt17;
                c = '0';
                c3 = '0';
            } else {
                c = cCharAt14;
                c2 = cCharAt15;
                c3 = cCharAt16;
                cCharAt = cCharAt17;
            }
            if (!checkDate(cCharAt10, cCharAt11, cCharAt12, cCharAt13, c, c2, c3, cCharAt)) {
                return false;
            }
            setCalendar(cCharAt10, cCharAt11, cCharAt12, cCharAt13, c, c2, c3, cCharAt);
            if (i != 8) {
                char cCharAt22 = charAt(this.bp + 9);
                char cCharAt23 = charAt(this.bp + 10);
                char cCharAt24 = charAt(this.bp + 11);
                char cCharAt25 = charAt(this.bp + 12);
                char cCharAt26 = charAt(this.bp + 13);
                if ((z5 && cCharAt23 == 'T' && cCharAt26 == ':' && charAt(this.bp + 16) == 'Z') || (z4 && ((cCharAt23 == ' ' || cCharAt23 == 'T') && cCharAt26 == ':'))) {
                    cCharAt2 = charAt(this.bp + 14);
                    cCharAt3 = charAt(this.bp + 15);
                    cCharAt21 = cCharAt24;
                    c4 = cCharAt25;
                    c5 = '0';
                    c6 = '0';
                } else {
                    c4 = cCharAt22;
                    cCharAt2 = cCharAt23;
                    cCharAt3 = cCharAt24;
                    c5 = cCharAt25;
                    c6 = cCharAt26;
                }
                if (!checkTime(cCharAt21, c4, cCharAt2, cCharAt3, c5, c6)) {
                    return false;
                }
                if (i != 17 || z5) {
                    i7 = 0;
                } else {
                    char cCharAt27 = charAt(this.bp + 14);
                    char cCharAt28 = charAt(this.bp + 15);
                    char cCharAt29 = charAt(this.bp + 16);
                    if (cCharAt27 < '0' || cCharAt27 > '9' || cCharAt28 < '0' || cCharAt28 > '9' || cCharAt29 < '0' || cCharAt29 > '9') {
                        return false;
                    }
                    i7 = ((cCharAt27 - '0') * 100) + ((cCharAt28 - '0') * 10) + (cCharAt29 - '0');
                }
                i5 = ((cCharAt2 - '0') * 10) + (cCharAt3 - '0');
                i6 = ((c5 - '0') * 10) + (c6 - '0');
                i4 = i7;
                i3 = ((cCharAt21 - '0') * 10) + (c4 - '0');
            } else {
                i3 = 0;
                i4 = 0;
                i5 = 0;
                i6 = 0;
            }
            this.calendar.set(11, i3);
            this.calendar.set(12, i5);
            this.calendar.set(13, i6);
            this.calendar.set(14, i4);
        } else if (i != 16) {
            if (i != 17 || charAt(this.bp + 6) == '-') {
                if (i < 9) {
                    return false;
                }
                char cCharAt30 = charAt(this.bp + 8);
                char cCharAt31 = charAt(this.bp + 9);
                if ((cCharAt14 == '-' && cCharAt17 == '-') || (cCharAt14 == '/' && cCharAt17 == '/')) {
                    cCharAt17 = cCharAt11;
                    c8 = cCharAt31;
                    c9 = cCharAt30;
                    c7 = cCharAt15;
                } else if (cCharAt14 != '-' || cCharAt16 != '-') {
                    if ((cCharAt12 == '.' && cCharAt15 == '.') || (cCharAt12 == '-' && cCharAt15 == '-')) {
                        c8 = cCharAt11;
                        cCharAt12 = cCharAt30;
                        c9 = cCharAt10;
                        c10 = cCharAt14;
                        cCharAt10 = cCharAt16;
                        i8 = 10;
                        cCharAt13 = cCharAt31;
                        c7 = cCharAt13;
                    } else if (cCharAt30 == 'T') {
                        c7 = cCharAt14;
                        c10 = cCharAt15;
                        c9 = cCharAt16;
                        c8 = cCharAt17;
                        i8 = 8;
                        cCharAt17 = cCharAt11;
                    } else {
                        if (cCharAt14 != 24180 && cCharAt14 != 45380) {
                            return false;
                        }
                        if (cCharAt17 != 26376 && cCharAt17 != 50900) {
                            if (cCharAt16 != 26376 && cCharAt16 != 50900) {
                                return false;
                            }
                            if (cCharAt30 == 26085 || cCharAt30 == 51068) {
                                c10 = cCharAt15;
                                c8 = cCharAt17;
                                i8 = 10;
                                c7 = '0';
                                c9 = '0';
                                cCharAt17 = cCharAt11;
                            } else {
                                if (cCharAt31 != 26085 && cCharAt31 != 51068) {
                                    return false;
                                }
                                c9 = cCharAt17;
                                c8 = cCharAt30;
                                i8 = 10;
                                c7 = '0';
                                cCharAt17 = cCharAt11;
                                c10 = cCharAt15;
                            }
                        } else if (cCharAt31 == 26085 || cCharAt31 == 51068) {
                            cCharAt17 = cCharAt11;
                            c7 = cCharAt15;
                            c8 = cCharAt30;
                            c9 = '0';
                        } else {
                            if (charAt(this.bp + 10) != 26085 && charAt(this.bp + 10) != 51068) {
                                return false;
                            }
                            cCharAt17 = cCharAt11;
                            c8 = cCharAt31;
                            c7 = cCharAt15;
                            c10 = cCharAt16;
                            i8 = 11;
                            c9 = cCharAt30;
                        }
                    }
                    if (checkDate(cCharAt10, cCharAt17, cCharAt12, cCharAt13, c7, c10, c9, c8)) {
                        return false;
                    }
                    int i16 = i8;
                    i2 = 5;
                    setCalendar(cCharAt10, cCharAt17, cCharAt12, cCharAt13, c7, c10, c9, c8);
                    char cCharAt32 = charAt(this.bp + i16);
                    if (cCharAt32 == 'T' && i == 16 && i16 == 8 && charAt(this.bp + 15) == 'Z') {
                        char cCharAt33 = charAt(this.bp + i16 + 1);
                        char cCharAt34 = charAt(this.bp + i16 + 2);
                        char cCharAt35 = charAt(this.bp + i16 + 3);
                        char cCharAt36 = charAt(this.bp + i16 + 4);
                        char cCharAt37 = charAt(this.bp + i16 + 5);
                        char cCharAt38 = charAt(this.bp + i16 + 6);
                        if (!checkTime(cCharAt33, cCharAt34, cCharAt35, cCharAt36, cCharAt37, cCharAt38)) {
                            return false;
                        }
                        setTime(cCharAt33, cCharAt34, cCharAt35, cCharAt36, cCharAt37, cCharAt38);
                        this.calendar.set(14, 0);
                        if (this.calendar.getTimeZone().getRawOffset() != 0) {
                            String[] availableIDs = TimeZone.getAvailableIDs(0);
                            if (availableIDs.length > 0) {
                                this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
                            }
                        }
                    } else {
                        if (cCharAt32 == 'T' || (cCharAt32 == ' ' && !z)) {
                            if (i < i16 + 9 || charAt(this.bp + i16 + 3) != ':' || charAt(this.bp + i16 + 6) != ':') {
                                return false;
                            }
                            char cCharAt39 = charAt(this.bp + i16 + 1);
                            char cCharAt40 = charAt(this.bp + i16 + 2);
                            char cCharAt41 = charAt(this.bp + i16 + 4);
                            char cCharAt42 = charAt(this.bp + i16 + 5);
                            char cCharAt43 = charAt(this.bp + i16 + 7);
                            char cCharAt44 = charAt(this.bp + i16 + 8);
                            if (!checkTime(cCharAt39, cCharAt40, cCharAt41, cCharAt42, cCharAt43, cCharAt44)) {
                                return false;
                            }
                            setTime(cCharAt39, cCharAt40, cCharAt41, cCharAt42, cCharAt43, cCharAt44);
                            if (charAt(this.bp + i16 + 9) == '.') {
                                int i17 = i16 + 11;
                                if (i < i17 || (cCharAt7 = charAt(this.bp + i16 + 10)) < '0' || cCharAt7 > '9') {
                                    return false;
                                }
                                int i18 = cCharAt7 - '0';
                                if (i <= i17 || (cCharAt9 = charAt(this.bp + i16 + 11)) < '0' || cCharAt9 > '9') {
                                    i10 = i18;
                                    i9 = 1;
                                } else {
                                    i10 = (i18 * 10) + (cCharAt9 - '0');
                                    i9 = 2;
                                }
                                if (i9 == 2 && (cCharAt8 = charAt(this.bp + i16 + 12)) >= '0' && cCharAt8 <= '9') {
                                    i10 = (i10 * 10) + (cCharAt8 - '0');
                                    i9 = 3;
                                }
                            } else {
                                i9 = -1;
                                i10 = 0;
                            }
                            this.calendar.set(14, i10);
                            char cCharAt45 = charAt(this.bp + i16 + 10 + i9);
                            if (cCharAt45 == ' ') {
                                i9++;
                                cCharAt45 = charAt(this.bp + i16 + 10 + i9);
                            }
                            int i19 = i9;
                            if (cCharAt45 == '+' || cCharAt45 == '-') {
                                char cCharAt46 = charAt(this.bp + i16 + 10 + i19 + 1);
                                if (cCharAt46 < '0' || cCharAt46 > '1' || (cCharAt4 = charAt(this.bp + i16 + 10 + i19 + 2)) < '0' || cCharAt4 > '9') {
                                    return false;
                                }
                                char cCharAt47 = charAt(this.bp + i16 + 10 + i19 + 3);
                                if (cCharAt47 == ':') {
                                    char cCharAt48 = charAt(this.bp + i16 + 10 + i19 + 4);
                                    if ((cCharAt48 != '0' && cCharAt48 != '3') || (cCharAt5 = charAt(this.bp + i16 + 10 + i19 + 5)) != '0') {
                                        return false;
                                    }
                                    c11 = cCharAt48;
                                    i11 = 6;
                                } else {
                                    if (cCharAt47 == '0') {
                                        cCharAt6 = charAt(this.bp + i16 + 10 + i19 + 4);
                                        if (cCharAt6 != '0' && cCharAt6 != '3') {
                                            return false;
                                        }
                                    } else if (cCharAt47 == '3' && charAt(this.bp + i16 + 10 + i19 + 4) == '0') {
                                        cCharAt6 = '3';
                                    } else if (cCharAt47 == '4' && charAt(this.bp + i16 + 10 + i19 + 4) == '5') {
                                        cCharAt5 = '5';
                                        i11 = 5;
                                        c11 = '4';
                                    } else {
                                        i11 = 3;
                                        c11 = '0';
                                        cCharAt5 = '0';
                                    }
                                    c11 = cCharAt6;
                                    i11 = 5;
                                    cCharAt5 = '0';
                                }
                                setTimeZone(cCharAt45, cCharAt46, cCharAt4, c11, cCharAt5);
                            } else if (cCharAt45 == 'Z') {
                                if (this.calendar.getTimeZone().getRawOffset() != 0) {
                                    String[] availableIDs2 = TimeZone.getAvailableIDs(0);
                                    if (availableIDs2.length > 0) {
                                        this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs2[0]));
                                    }
                                }
                                i11 = 1;
                            } else {
                                i11 = 0;
                            }
                            int i20 = i16 + 10 + i19 + i11;
                            char cCharAt49 = charAt(this.bp + i20);
                            if (cCharAt49 != 26 && cCharAt49 != '\"') {
                                return false;
                            }
                            i12 = this.bp + i20;
                        } else {
                            if (cCharAt32 != '\"' && cCharAt32 != 26 && cCharAt32 != 26085 && cCharAt32 != 51068) {
                                if ((cCharAt32 != '+' && cCharAt32 != '-') || this.len != i16 + 6 || charAt(this.bp + i16 + 3) != ':' || charAt(this.bp + i16 + 4) != '0' || charAt(this.bp + i16 + 5) != '0') {
                                    return false;
                                }
                                setTime('0', '0', '0', '0', '0', '0');
                                this.calendar.set(14, 0);
                                setTimeZone(cCharAt32, charAt(this.bp + i16 + 1), charAt(this.bp + i16 + 2));
                                return true;
                            }
                            this.calendar.set(11, 0);
                            this.calendar.set(12, 0);
                            this.calendar.set(13, 0);
                            this.calendar.set(14, 0);
                            i12 = this.bp + i16;
                        }
                        this.bp = i12;
                        this.ch = charAt(i12);
                    }
                } else if (cCharAt30 == ' ') {
                    c10 = cCharAt15;
                    c8 = cCharAt17;
                    i8 = 8;
                    c7 = '0';
                    c9 = '0';
                    cCharAt17 = cCharAt11;
                    if (checkDate(cCharAt10, cCharAt17, cCharAt12, cCharAt13, c7, c10, c9, c8)) {
                    }
                } else {
                    c9 = cCharAt17;
                    c8 = cCharAt30;
                    i8 = 9;
                    c7 = '0';
                    cCharAt17 = cCharAt11;
                    c10 = cCharAt15;
                    if (checkDate(cCharAt10, cCharAt17, cCharAt12, cCharAt13, c7, c10, c9, c8)) {
                    }
                }
                c10 = cCharAt16;
                i8 = 10;
                if (checkDate(cCharAt10, cCharAt17, cCharAt12, cCharAt13, c7, c10, c9, c8)) {
                }
            }
            z2 = false;
            i2 = 5;
            if (!z) {
            }
        } else {
            char cCharAt50 = charAt(this.bp + 10);
            if (cCharAt50 != 'T') {
                if (cCharAt50 != ' ') {
                }
                z2 = false;
                i2 = 5;
                if (!z) {
                }
            }
            i2 = 5;
            z2 = false;
            if (!z) {
            }
        }
        this.token = i2;
        return true;
    }

    private void setCalendar(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar.set(1, ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(2, (((c5 - '0') * 10) + (c6 - '0')) - 1);
        this.calendar.set(5, ((c7 - '0') * 10) + (c8 - '0'));
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int i, int i2, int i3, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, i, i2, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int i, char[] cArr, int i2, int i3) {
        this.text.getChars(i, i3 + i, cArr, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token != 26) {
            return !this.hasSpecial ? IOUtils.decodeBase64(this.text, this.np + 1, this.sp) : IOUtils.decodeBase64(new String(this.sbuf, 0, this.sp));
        }
        int i = this.np + 1;
        int i2 = this.sp;
        if (i2 % 2 != 0) {
            throw new JSONException("illegal state. " + i2);
        }
        byte[] bArr = new byte[i2 / 2];
        for (int i3 = 0; i3 < bArr.length; i3++) {
            int i4 = (i3 * 2) + i;
            char cCharAt = this.text.charAt(i4);
            char cCharAt2 = this.text.charAt(i4 + 1);
            char c = '7';
            int i5 = cCharAt - (cCharAt <= '9' ? '0' : '7');
            if (cCharAt2 <= '9') {
                c = '0';
            }
            bArr[i3] = (byte) ((i5 << 4) | (cCharAt2 - c));
        }
        return bArr;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] cArr) {
        return charArrayCompare(this.text, this.bp, cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int i) {
        return i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int i, int i2, char[] cArr) {
        this.text.getChars(i, i2 + i, cArr, 0);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i--;
        }
        int i2 = this.np;
        if (i < this.sbuf.length) {
            this.text.getChars(i2, i2 + i, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, i);
        }
        char[] cArr = new char[i];
        this.text.getChars(i2, i + i2, cArr, 0);
        return new BigDecimal(cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char c, int i) {
        return this.text.indexOf(c, i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 1;
        int i3 = 1;
        while (i < this.bp) {
            if (this.text.charAt(i) == '\n') {
                i2++;
                i3 = 1;
            }
            i++;
            i3++;
        }
        sb.append("pos ");
        sb.append(this.bp);
        sb.append(", line ");
        sb.append(i2);
        sb.append(", column ");
        sb.append(i3);
        sb.append(this.text.length() < 65535 ? this.text : this.text.substring(0, SupportMenu.USER_MASK));
        return sb.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        if (this.bp != this.len) {
            return this.ch == 26 && this.bp + 1 >= this.len;
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] cArr) {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = this.bp + cArr.length;
        int i = length + 1;
        char cCharAt = this.text.charAt(length);
        while (isWhitespace(cCharAt)) {
            cCharAt = this.text.charAt(i);
            i++;
        }
        if (cCharAt != ':') {
            this.matchStat = -2;
            return false;
        }
        this.bp = i;
        this.ch = charAt(this.bp);
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
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

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i = this.bp + 1;
        this.bp = i;
        char cCharAt = i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
        this.ch = cCharAt;
        return cCharAt;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i--;
        }
        return subString(this.np, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00fb  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Date scanDate(char c) {
        boolean z;
        int i;
        char cCharAt;
        long j;
        char cCharAt2;
        Date date;
        int i2;
        int i3;
        int i4;
        this.matchStat = 0;
        int i5 = this.bp;
        char c2 = this.ch;
        int i6 = this.bp;
        int i7 = i6 + 1;
        char cCharAt3 = charAt(i6);
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf('\"', i7);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i7;
            if (scanISO8601DateIfMatch(false, iIndexOf - i7)) {
                date = this.calendar.getTime();
                cCharAt2 = charAt(iIndexOf + 1);
                this.bp = i5;
                while (cCharAt2 != ',' && cCharAt2 != ']') {
                    if (isWhitespace(cCharAt2)) {
                        iIndexOf++;
                        cCharAt2 = charAt(iIndexOf + 1);
                    }
                }
                this.bp = iIndexOf + 1;
                this.ch = cCharAt2;
                if (cCharAt2 != ',') {
                    int i8 = this.bp + 1;
                    this.bp = i8;
                    this.ch = charAt(i8);
                    i4 = 3;
                } else {
                    int i9 = this.bp + 1;
                    this.bp = i9;
                    char cCharAt4 = charAt(i9);
                    if (cCharAt4 == ',') {
                        i3 = 16;
                    } else if (cCharAt4 == ']') {
                        i3 = 15;
                    } else if (cCharAt4 == '}') {
                        i3 = 13;
                    } else if (cCharAt4 == 26) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        i4 = 4;
                    }
                    this.token = i3;
                    int i10 = this.bp + 1;
                    this.bp = i10;
                    this.ch = charAt(i10);
                    i4 = 4;
                }
                this.matchStat = i4;
                return date;
            }
            this.bp = i5;
            this.ch = c2;
            this.matchStat = -1;
            return null;
        }
        char c3 = '9';
        char c4 = '0';
        if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
            if (cCharAt3 == 'n') {
                int i11 = i7 + 1;
                if (charAt(i7) == 'u') {
                    int i12 = i11 + 1;
                    if (charAt(i11) == 'l') {
                        int i13 = i12 + 1;
                        if (charAt(i12) == 'l') {
                            cCharAt2 = charAt(i13);
                            this.bp = i13;
                            date = null;
                        }
                    }
                }
            }
            this.bp = i5;
            this.ch = c2;
            this.matchStat = -1;
            return null;
        }
        if (cCharAt3 == '-') {
            i = i7 + 1;
            cCharAt3 = charAt(i7);
            z = true;
        } else {
            z = false;
            i = i7;
        }
        if (cCharAt3 < '0' || cCharAt3 > '9') {
            cCharAt = cCharAt3;
            j = 0;
        } else {
            j = cCharAt3 - '0';
            while (true) {
                i2 = i + 1;
                cCharAt = charAt(i);
                if (cCharAt < c4 || cCharAt > c3) {
                    break;
                }
                j = (j * 10) + ((long) (cCharAt - '0'));
                i = i2;
                c3 = '9';
                c4 = '0';
            }
            if (cCharAt == ',' || cCharAt == ']') {
                this.bp = i2 - 1;
            }
        }
        if (j >= 0) {
            if (z) {
                j = -j;
            }
            cCharAt2 = cCharAt;
            date = new Date(j);
        }
        if (cCharAt2 != ',') {
        }
        this.matchStat = i4;
        return date;
        this.bp = i5;
        this.ch = c2;
        this.matchStat = -1;
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00c0  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x00c4 -> B:52:0x00b4). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public double scanDouble(char c) {
        int i;
        char cCharAt;
        long j;
        int i2;
        int i3;
        double d;
        int i4;
        this.matchStat = 0;
        int i5 = this.bp;
        int i6 = i5 + 1;
        char cCharAt2 = charAt(i5);
        boolean z = cCharAt2 == '\"';
        if (z) {
            int i7 = i6 + 1;
            char cCharAt3 = charAt(i6);
            i6 = i7;
            cCharAt2 = cCharAt3;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            int i8 = i6 + 1;
            char cCharAt4 = charAt(i6);
            i6 = i8;
            cCharAt2 = cCharAt4;
        }
        if (cCharAt2 >= '0') {
            char c2 = '9';
            if (cCharAt2 <= '9') {
                long j2 = cCharAt2 - '0';
                while (true) {
                    i = i6 + 1;
                    cCharAt = charAt(i6);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                    i6 = i;
                }
                if (cCharAt == '.') {
                    int i9 = i + 1;
                    char cCharAt5 = charAt(i);
                    if (cCharAt5 < '0' || cCharAt5 > '9') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    j2 = (j2 * 10) + ((long) (cCharAt5 - '0'));
                    j = 10;
                    while (true) {
                        i4 = i9 + 1;
                        cCharAt = charAt(i9);
                        if (cCharAt < '0' || cCharAt > c2) {
                            break;
                        }
                        j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                        j *= 10;
                        i9 = i4;
                        c2 = '9';
                    }
                    i = i4;
                } else {
                    j = 1;
                }
                boolean z3 = cCharAt == 'e' || cCharAt == 'E';
                if (z3) {
                    int i10 = i + 1;
                    char cCharAt6 = charAt(i);
                    if (cCharAt6 == '+' || cCharAt6 == '-') {
                        int i11 = i10 + 1;
                        cCharAt = charAt(i10);
                        i = i11;
                    } else {
                        i = i10;
                        cCharAt = cCharAt6;
                    }
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        i10 = i + 1;
                        cCharAt6 = charAt(i);
                        i = i10;
                        cCharAt = cCharAt6;
                        if (cCharAt >= '0') {
                            i10 = i + 1;
                            cCharAt6 = charAt(i);
                            i = i10;
                            cCharAt = cCharAt6;
                            if (cCharAt >= '0') {
                            }
                        }
                    }
                }
                if (!z) {
                    i2 = this.bp;
                    i3 = (i - i2) - 1;
                } else {
                    if (cCharAt != '\"') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    int i12 = i + 1;
                    char cCharAt7 = charAt(i);
                    i2 = this.bp + 1;
                    i3 = (i12 - i2) - 2;
                    i = i12;
                    cCharAt = cCharAt7;
                }
                if (z3 || i3 >= 18) {
                    d = Double.parseDouble(subString(i2, i3));
                } else {
                    d = j2 / j;
                    if (z2) {
                        d = -d;
                    }
                }
                if (cCharAt != c) {
                    this.matchStat = -1;
                    return d;
                }
                this.bp = i;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return d;
            }
        }
        if (cCharAt2 == 'n') {
            int i13 = i6 + 1;
            if (charAt(i6) == 'u') {
                int i14 = i13 + 1;
                if (charAt(i13) == 'l') {
                    int i15 = i14 + 1;
                    if (charAt(i14) == 'l') {
                        this.matchStat = 5;
                        int i16 = i15 + 1;
                        char cCharAt8 = charAt(i15);
                        if (z && cCharAt8 == '\"') {
                            int i17 = i16 + 1;
                            char cCharAt9 = charAt(i16);
                            i16 = i17;
                            cCharAt8 = cCharAt9;
                        }
                        while (cCharAt8 != ',') {
                            if (cCharAt8 == ']') {
                                this.bp = i16;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            }
                            if (!isWhitespace(cCharAt8)) {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                            int i18 = i16 + 1;
                            char cCharAt10 = charAt(i16);
                            i16 = i18;
                            cCharAt8 = cCharAt10;
                        }
                        this.bp = i16;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x00da A[PHI: r2
      0x00da: PHI (r2v8 int) = (r2v5 int), (r2v13 int) binds: [B:66:0x00cd, B:50:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean scanFieldBoolean(char[] cArr) {
        int i;
        char cCharAt;
        int i2;
        int i3;
        char cCharAt2;
        boolean z;
        char cCharAt3;
        int i4;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return false;
        }
        int i5 = this.bp;
        int length = this.bp + cArr.length;
        int i6 = length + 1;
        char cCharAt4 = charAt(length);
        boolean z2 = cCharAt4 == '\"';
        if (z2) {
            i = i6 + 1;
            cCharAt = charAt(i6);
        } else {
            i = i6;
            cCharAt = cCharAt4;
        }
        if (cCharAt == 't') {
            int i7 = i + 1;
            if (charAt(i) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i8 = i7 + 1;
            if (charAt(i7) != 'u') {
                this.matchStat = -1;
                return false;
            }
            i3 = i8 + 1;
            if (charAt(i8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (z2) {
                i = i3 + 1;
                if (charAt(i3) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
                i3 = i;
            }
            this.bp = i3;
            cCharAt2 = charAt(this.bp);
            z = true;
        } else if (cCharAt == 'f') {
            int i9 = i + 1;
            if (charAt(i) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i10 = i9 + 1;
            if (charAt(i9) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i11 = i10 + 1;
            if (charAt(i10) != 's') {
                this.matchStat = -1;
                return false;
            }
            i = i11 + 1;
            if (charAt(i11) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (z2) {
                i2 = i + 1;
                if (charAt(i) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                i2 = i;
            }
            this.bp = i2;
            cCharAt2 = charAt(this.bp);
            z = false;
        } else if (cCharAt == '1') {
            if (z2) {
                i3 = i + 1;
                if (charAt(i) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                i3 = i;
            }
            this.bp = i3;
            cCharAt2 = charAt(this.bp);
            z = true;
        } else {
            if (cCharAt != '0') {
                this.matchStat = -1;
                return false;
            }
            if (z2) {
                i2 = i + 1;
                if (charAt(i) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            }
            this.bp = i2;
            cCharAt2 = charAt(this.bp);
            z = false;
        }
        while (true) {
            if (cCharAt2 == ',') {
                int i12 = this.bp + 1;
                this.bp = i12;
                this.ch = charAt(i12);
                this.matchStat = 3;
                this.token = 16;
                break;
            }
            if (cCharAt2 == '}') {
                do {
                    int i13 = this.bp + 1;
                    this.bp = i13;
                    cCharAt3 = charAt(i13);
                    if (cCharAt3 == ',') {
                        this.token = 16;
                    } else {
                        if (cCharAt3 == ']') {
                            i4 = 15;
                        } else if (cCharAt3 == '}') {
                            i4 = 13;
                        } else if (cCharAt3 == 26) {
                            this.token = 20;
                            this.matchStat = 4;
                        }
                        this.token = i4;
                    }
                    int i14 = this.bp + 1;
                    this.bp = i14;
                    this.ch = charAt(i14);
                    this.matchStat = 4;
                } while (isWhitespace(cCharAt3));
                this.matchStat = -1;
                return false;
            }
            if (!isWhitespace(cCharAt2)) {
                this.bp = i5;
                charAt(this.bp);
                this.matchStat = -1;
                return false;
            }
            int i15 = this.bp + 1;
            this.bp = i15;
            cCharAt2 = charAt(i15);
        }
        return z;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] cArr) {
        char cCharAt;
        long j;
        Date date;
        int i;
        int i2;
        boolean z = false;
        this.matchStat = 0;
        int i3 = this.bp;
        char c = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = this.bp + cArr.length;
        int i4 = length + 1;
        char cCharAt2 = charAt(length);
        if (cCharAt2 != '\"') {
            char c2 = '9';
            char c3 = '0';
            if (cCharAt2 != '-' && (cCharAt2 < '0' || cCharAt2 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt2 == '-') {
                cCharAt2 = charAt(i4);
                i4++;
                z = true;
            }
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                cCharAt = cCharAt2;
                j = 0;
            } else {
                j = cCharAt2 - '0';
                while (true) {
                    i = i4 + 1;
                    cCharAt = charAt(i4);
                    if (cCharAt < c3 || cCharAt > c2) {
                        break;
                    }
                    j = (j * 10) + ((long) (cCharAt - '0'));
                    i4 = i;
                    c2 = '9';
                    c3 = '0';
                }
                if (cCharAt == ',' || cCharAt == '}') {
                    this.bp = i - 1;
                }
            }
            if (j >= 0) {
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            }
            this.matchStat = -1;
            return null;
        }
        int iIndexOf = indexOf('\"', i4);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        this.bp = i4;
        if (!scanISO8601DateIfMatch(false, iIndexOf - i4)) {
            this.bp = i3;
            this.matchStat = -1;
            return null;
        }
        Date time = this.calendar.getTime();
        char cCharAt3 = charAt(iIndexOf + 1);
        this.bp = i3;
        while (cCharAt3 != ',' && cCharAt3 != '}') {
            if (!isWhitespace(cCharAt3)) {
                this.matchStat = -1;
                return null;
            }
            iIndexOf++;
            cCharAt3 = charAt(iIndexOf + 1);
        }
        this.bp = iIndexOf + 1;
        this.ch = cCharAt3;
        char c4 = cCharAt3;
        date = time;
        cCharAt = c4;
        if (cCharAt == ',') {
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        int i6 = this.bp + 1;
        this.bp = i6;
        char cCharAt4 = charAt(i6);
        if (cCharAt4 == ',') {
            this.token = 16;
        } else {
            if (cCharAt4 == ']') {
                i2 = 15;
            } else {
                if (cCharAt4 != '}') {
                    if (cCharAt4 == 26) {
                        this.token = 20;
                        this.matchStat = 4;
                        return date;
                    }
                    this.bp = i3;
                    this.ch = c;
                    this.matchStat = -1;
                    return null;
                }
                i2 = 13;
            }
            this.token = i2;
        }
        int i7 = this.bp + 1;
        this.bp = i7;
        this.ch = charAt(i7);
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:?, code lost:
    
        return r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
    
        if (r3 != '.') goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006a, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006c, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006d, code lost:
    
        if (r15 >= 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006f, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0071, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0072, code lost:
    
        if (r6 == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0074, code lost:
    
        if (r3 == '\"') goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0076, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0078, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0079, code lost:
    
        r4 = charAt(r11);
        r11 = r11 + 1;
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0085, code lost:
    
        if (r3 == ',') goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0087, code lost:
    
        if (r3 != '}') goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008e, code lost:
    
        if (isWhitespace(r3) == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0091, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0093, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0094, code lost:
    
        r11 = r11 - 1;
        r14.bp = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0099, code lost:
    
        if (r3 != ',') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009b, code lost:
    
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ab, code lost:
    
        if (r7 == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ae, code lost:
    
        return -r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00af, code lost:
    
        if (r3 != '}') goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b1, code lost:
    
        r14.bp = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b3, code lost:
    
        r3 = r14.bp + 1;
        r14.bp = r3;
        r3 = charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00bc, code lost:
    
        if (r3 != ',') goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00be, code lost:
    
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c0, code lost:
    
        r0 = r14.bp + 1;
        r14.bp = r0;
        r14.ch = charAt(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ce, code lost:
    
        if (r3 != ']') goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d0, code lost:
    
        r0 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d2, code lost:
    
        r14.token = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00d5, code lost:
    
        if (r3 != '}') goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00d7, code lost:
    
        r0 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00dc, code lost:
    
        if (r3 != 26) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00de, code lost:
    
        r14.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e2, code lost:
    
        r14.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00ea, code lost:
    
        if (isWhitespace(r3) == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00ed, code lost:
    
        r14.bp = r1;
        r14.ch = r2;
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00f3, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00f4, code lost:
    
        if (r7 == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00f7, code lost:
    
        return -r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0079, code lost:
    
        r4 = charAt(r11);
        r11 = r11 + 1;
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:?, code lost:
    
        return r15;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int scanFieldInt(char[] cArr) {
        int i;
        char cCharAt;
        this.matchStat = 0;
        int i2 = this.bp;
        char c = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        char cCharAt2 = charAt(length);
        boolean z = cCharAt2 == '\"';
        if (z) {
            i = i3 + 1;
            cCharAt = charAt(i3);
        } else {
            i = i3;
            cCharAt = cCharAt2;
        }
        boolean z2 = cCharAt == '-';
        if (z2) {
            int i4 = i + 1;
            char cCharAt3 = charAt(i);
            i = i4;
            cCharAt = cCharAt3;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i5 = cCharAt - '0';
        while (true) {
            int i6 = i + 1;
            char cCharAt4 = charAt(i);
            if (cCharAt4 < '0' || cCharAt4 > '9') {
                break;
            }
            int i7 = i5 * 10;
            if (i7 < i5) {
                this.matchStat = -1;
                return 0;
            }
            i5 = i7 + (cCharAt4 - '0');
            i = i6;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0103  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long scanFieldLong(char[] cArr) {
        int i;
        char cCharAt;
        boolean z;
        int i2;
        char cCharAt2;
        int i3;
        char cCharAt3;
        char cCharAt4;
        int i4;
        this.matchStat = 0;
        int i5 = this.bp;
        char c = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i6 = length + 1;
        char cCharAt5 = charAt(length);
        boolean z2 = cCharAt5 == '\"';
        if (z2) {
            i = i6 + 1;
            cCharAt = charAt(i6);
        } else {
            i = i6;
            cCharAt = cCharAt5;
        }
        if (cCharAt == '-') {
            int i7 = i + 1;
            char cCharAt6 = charAt(i);
            z = true;
            i = i7;
            cCharAt = cCharAt6;
        } else {
            z = false;
        }
        if (cCharAt >= '0') {
            char c2 = '9';
            if (cCharAt <= '9') {
                long j = cCharAt - '0';
                while (true) {
                    i2 = i + 1;
                    cCharAt2 = charAt(i);
                    if (cCharAt2 < '0' || cCharAt2 > c2) {
                        break;
                    }
                    j = (j * 10) + ((long) (cCharAt2 - '0'));
                    i = i2;
                    c2 = '9';
                }
                if (cCharAt2 == '.') {
                    this.matchStat = -1;
                    return 0L;
                }
                if (!z2) {
                    i3 = i2;
                    cCharAt3 = cCharAt2;
                } else {
                    if (cCharAt2 != '\"') {
                        this.matchStat = -1;
                        return 0L;
                    }
                    i3 = i2 + 1;
                    cCharAt3 = charAt(i2);
                }
                if (cCharAt3 == ',' || cCharAt3 == '}') {
                    this.bp = i3 - 1;
                }
                if (!(j >= 0 || (j == Long.MIN_VALUE && z))) {
                    this.bp = i5;
                    this.ch = c;
                    this.matchStat = -1;
                    return 0L;
                }
                while (cCharAt3 != ',') {
                    if (cCharAt3 == '}') {
                        do {
                            int i8 = this.bp + 1;
                            this.bp = i8;
                            cCharAt4 = charAt(i8);
                            if (cCharAt4 == ',') {
                                this.token = 16;
                            } else {
                                if (cCharAt4 == ']') {
                                    i4 = 15;
                                } else if (cCharAt4 == '}') {
                                    i4 = 13;
                                } else if (cCharAt4 == 26) {
                                    this.token = 20;
                                    this.matchStat = 4;
                                    return !z ? -j : j;
                                }
                                this.token = i4;
                            }
                            int i9 = this.bp + 1;
                            this.bp = i9;
                            this.ch = charAt(i9);
                            this.matchStat = 4;
                            if (!z) {
                            }
                        } while (isWhitespace(cCharAt4));
                        this.bp = i5;
                        this.ch = c;
                        this.matchStat = -1;
                        return 0L;
                    }
                    if (!isWhitespace(cCharAt3)) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.bp = i3;
                    int i10 = i3 + 1;
                    char cCharAt7 = charAt(i3);
                    i3 = i10;
                    cCharAt3 = cCharAt7;
                }
                int i11 = this.bp + 1;
                this.bp = i11;
                this.ch = charAt(i11);
                this.matchStat = 3;
                this.token = 16;
                return z ? -j : j;
            }
        }
        this.bp = i5;
        this.ch = c;
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] cArr) {
        char cCharAt;
        int i;
        int i2;
        this.matchStat = 0;
        int i3 = this.bp;
        char c = this.ch;
        while (true) {
            if (!charArrayCompare(this.text, this.bp, cArr)) {
                if (!isWhitespace(this.ch)) {
                    this.matchStat = -2;
                    break;
                }
                next();
            } else {
                int length = this.bp + cArr.length;
                int i4 = length + 1;
                if (charAt(length) == '\"') {
                    int iIndexOf = indexOf('\"', i4);
                    if (iIndexOf == -1) {
                        throw new JSONException("unclosed str");
                    }
                    String strSubString = subString(i4, iIndexOf - i4);
                    if (strSubString.indexOf(92) != -1) {
                        while (true) {
                            int i5 = 0;
                            for (int i6 = iIndexOf - 1; i6 >= 0 && charAt(i6) == '\\'; i6--) {
                                i5++;
                            }
                            if (i5 % 2 == 0) {
                                break;
                            }
                            iIndexOf = indexOf('\"', iIndexOf + 1);
                        }
                        int length2 = iIndexOf - ((this.bp + cArr.length) + 1);
                        strSubString = readString(sub_chars(this.bp + cArr.length + 1, length2), length2);
                    }
                    while (true) {
                        cCharAt = charAt(iIndexOf + 1);
                        if (cCharAt == ',' || cCharAt == '}') {
                            break;
                        }
                        if (!isWhitespace(cCharAt)) {
                            break;
                        }
                        iIndexOf++;
                    }
                    this.bp = iIndexOf + 1;
                    this.ch = cCharAt;
                    if (cCharAt == ',') {
                        int i7 = this.bp + 1;
                        this.bp = i7;
                        this.ch = charAt(i7);
                        i2 = 3;
                    } else {
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        char cCharAt2 = charAt(i8);
                        if (cCharAt2 == ',') {
                            i = 16;
                        } else if (cCharAt2 == ']') {
                            i = 15;
                        } else if (cCharAt2 == '}') {
                            i = 13;
                        } else if (cCharAt2 == 26) {
                            this.token = 20;
                            i2 = 4;
                        } else {
                            this.bp = i3;
                            this.ch = c;
                        }
                        this.token = i;
                        int i9 = this.bp + 1;
                        this.bp = i9;
                        this.ch = charAt(i9);
                        i2 = 4;
                    }
                    this.matchStat = i2;
                    return strSubString;
                }
                this.matchStat = -1;
            }
        }
        return stringDefaultValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d9, code lost:
    
        if (r1 != ']') goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00df, code lost:
    
        if (r3.size() != 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e1, code lost:
    
        r1 = r9 + 1;
        r2 = charAt(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00eb, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ed, code lost:
    
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Collection<String> scanFieldStringArray(char[] cArr, Class<?> cls) {
        int i;
        Collection<String> collection;
        char cCharAt;
        int i2;
        int i3;
        boolean z;
        char cCharAt2;
        int i4;
        char cCharAt3;
        this.matchStat = 0;
        while (true) {
            if (this.ch != '\n' && this.ch != ' ') {
                break;
            }
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = i5 >= this.len ? (char) 26 : this.text.charAt(i5);
        }
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> collectionNewCollectionByType = newCollectionByType(cls);
        int i6 = this.bp;
        char c = this.ch;
        int length = this.bp + cArr.length;
        int i7 = length + 1;
        if (charAt(length) == '[') {
            int i8 = i7 + 1;
            char cCharAt4 = charAt(i7);
            while (true) {
                if (cCharAt4 == '\"') {
                    int iIndexOf = indexOf('\"', i8);
                    if (iIndexOf == -1) {
                        throw new JSONException("unclosed str");
                    }
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
                        int i11 = iIndexOf - i8;
                        strSubString = readString(sub_chars(i8, i11), i11);
                    }
                    int i12 = iIndexOf + 1;
                    i4 = i12 + 1;
                    cCharAt3 = charAt(i12);
                    collectionNewCollectionByType.add(strSubString);
                } else {
                    if (cCharAt4 != 'n' || !this.text.startsWith("ull", i8)) {
                        break;
                    }
                    int i13 = i8 + 3;
                    i4 = i13 + 1;
                    cCharAt3 = charAt(i13);
                    collectionNewCollectionByType.add(null);
                }
                if (cCharAt3 == ',') {
                    i8 = i4 + 1;
                    cCharAt4 = charAt(i4);
                } else {
                    if (cCharAt3 != ']') {
                        this.matchStat = -1;
                        return null;
                    }
                    i2 = i4 + 1;
                    cCharAt2 = charAt(i4);
                    while (isWhitespace(cCharAt2)) {
                        cCharAt2 = charAt(i2);
                        i2++;
                    }
                }
            }
            collection = collectionNewCollectionByType;
            cCharAt = cCharAt2;
            i = 3;
        } else {
            if (!this.text.startsWith("ull", i7)) {
                this.matchStat = -1;
                return null;
            }
            i = 3;
            int i14 = i7 + 3;
            collection = null;
            cCharAt = charAt(i14);
            i2 = i14 + 1;
        }
        this.bp = i2;
        if (cCharAt == ',') {
            this.ch = charAt(this.bp);
            this.matchStat = i;
            return collection;
        }
        if (cCharAt != '}') {
            this.ch = c;
            this.bp = i6;
            this.matchStat = -1;
            return null;
        }
        char cCharAt5 = charAt(this.bp);
        do {
            if (cCharAt5 == ',') {
                i3 = 16;
            } else if (cCharAt5 == ']') {
                i3 = 15;
            } else if (cCharAt5 == '}') {
                i3 = 13;
            } else {
                if (cCharAt5 == 26) {
                    this.token = 20;
                    this.ch = cCharAt5;
                    this.matchStat = 4;
                    return collection;
                }
                z = false;
                while (isWhitespace(cCharAt5)) {
                    int i15 = i2 + 1;
                    char cCharAt6 = charAt(i2);
                    this.bp = i15;
                    z = true;
                    cCharAt5 = cCharAt6;
                    i2 = i15;
                }
            }
            this.token = i3;
            int i16 = this.bp + 1;
            this.bp = i16;
            this.ch = charAt(i16);
            this.matchStat = 4;
            return collection;
        } while (z);
        this.matchStat = -1;
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        int i2;
        char cCharAt;
        int i3 = this.bp;
        char c = this.ch;
        while (isWhitespace(this.ch)) {
            next();
        }
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(cArr)) {
                this.matchStat = -2;
                return null;
            }
            int length = this.bp + cArr.length;
            int i4 = length + 1;
            char cCharAt2 = this.text.charAt(length);
            while (isWhitespace(cCharAt2)) {
                cCharAt2 = this.text.charAt(i4);
                i4++;
            }
            if (cCharAt2 != ':') {
                this.matchStat = -1;
                return null;
            }
            i2 = i4 + 1;
            cCharAt = this.text.charAt(i4);
            while (isWhitespace(cCharAt)) {
                cCharAt = this.text.charAt(i2);
                i2++;
            }
        } else {
            i2 = this.bp + 1;
            cCharAt = this.ch;
        }
        if (cCharAt != '[') {
            if (cCharAt != 'n' || !this.text.startsWith("ull", this.bp + 1)) {
                this.matchStat = -1;
                return null;
            }
            this.bp += 4;
            this.ch = this.text.charAt(this.bp);
            return null;
        }
        this.bp = i2;
        this.ch = this.text.charAt(this.bp);
        String[] strArr = i >= 0 ? new String[i] : new String[4];
        int i5 = 0;
        while (true) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                if (this.ch != '\"') {
                    this.bp = i3;
                    this.ch = c;
                    this.matchStat = -1;
                    return null;
                }
                String strScanSymbol = scanSymbol(symbolTable, '\"');
                if (i5 == strArr.length) {
                    String[] strArr2 = new String[strArr.length + (strArr.length >> 1) + 1];
                    System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                    strArr = strArr2;
                }
                int i6 = i5 + 1;
                strArr[i5] = strScanSymbol;
                while (isWhitespace(this.ch)) {
                    next();
                }
                if (this.ch != ',') {
                    if (strArr.length != i6) {
                        String[] strArr3 = new String[i6];
                        System.arraycopy(strArr, 0, strArr3, 0, i6);
                        strArr = strArr3;
                    }
                    while (isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ']') {
                        next();
                        return strArr;
                    }
                    this.bp = i3;
                    this.ch = c;
                    this.matchStat = -1;
                    return null;
                }
                next();
                i5 = i6;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] cArr) {
        int i;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(i2);
            if (cCharAt == '\"') {
                this.bp = i3;
                char cCharAt2 = charAt(this.bp);
                this.ch = cCharAt2;
                while (cCharAt2 != ',') {
                    if (cCharAt2 == '}') {
                        next();
                        skipWhitespace();
                        char current = getCurrent();
                        if (current == ',') {
                            i = 16;
                        } else if (current == ']') {
                            i = 15;
                        } else {
                            if (current != '}') {
                                if (current != 26) {
                                    this.matchStat = -1;
                                    return 0L;
                                }
                                this.token = 20;
                                this.matchStat = 4;
                                return j;
                            }
                            i = 13;
                        }
                        this.token = i;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                        this.matchStat = 4;
                        return j;
                    }
                    if (!isWhitespace(cCharAt2)) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    cCharAt2 = charAt(i5);
                }
                int i6 = this.bp + 1;
                this.bp = i6;
                this.ch = charAt(i6);
                this.matchStat = 3;
                return j;
            }
            if (i3 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            j = (j ^ ((long) cCharAt)) * 1099511628211L;
            i2 = i3;
        }
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean z) {
        return scanISO8601DateIfMatch(z, this.len - this.bp);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
    
        if (r4 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0084, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0086, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0087, code lost:
    
        if (r7 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0089, code lost:
    
        if (r4 == '\"') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008b, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008d, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008e, code lost:
    
        r2 = r13 + 1;
        r4 = charAt(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0095, code lost:
    
        r2 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0096, code lost:
    
        if (r3 >= 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0098, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009a, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009d, code lost:
    
        if (r4 != r17) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009f, code lost:
    
        r16.bp = r2;
        r16.ch = charAt(r16.bp);
        r16.matchStat = 3;
        r16.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ae, code lost:
    
        if (r8 == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b1, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b6, code lost:
    
        if (isWhitespace(r4) == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b8, code lost:
    
        r4 = charAt(r2);
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c2, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c4, code lost:
    
        if (r8 == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c7, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return r3;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int scanInt(char c) {
        this.matchStat = 0;
        int i = this.bp;
        int i2 = this.bp;
        int i3 = i2 + 1;
        char cCharAt = charAt(i2);
        while (isWhitespace(cCharAt)) {
            int i4 = i3 + 1;
            char cCharAt2 = charAt(i3);
            i3 = i4;
            cCharAt = cCharAt2;
        }
        boolean z = cCharAt == '\"';
        if (z) {
            int i5 = i3 + 1;
            char cCharAt3 = charAt(i3);
            i3 = i5;
            cCharAt = cCharAt3;
        }
        boolean z2 = cCharAt == '-';
        if (z2) {
            int i6 = i3 + 1;
            char cCharAt4 = charAt(i3);
            i3 = i6;
            cCharAt = cCharAt4;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            if (cCharAt == 'n') {
                int i7 = i3 + 1;
                if (charAt(i3) == 'u') {
                    int i8 = i7 + 1;
                    if (charAt(i7) == 'l') {
                        int i9 = i8 + 1;
                        if (charAt(i8) == 'l') {
                            this.matchStat = 5;
                            int i10 = i9 + 1;
                            char cCharAt5 = charAt(i9);
                            if (z && cCharAt5 == '\"') {
                                int i11 = i10 + 1;
                                char cCharAt6 = charAt(i10);
                                i10 = i11;
                                cCharAt5 = cCharAt6;
                            }
                            while (cCharAt5 != ',') {
                                if (cCharAt5 == ']') {
                                    this.bp = i10;
                                    this.ch = charAt(this.bp);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return 0;
                                }
                                if (!isWhitespace(cCharAt5)) {
                                    this.matchStat = -1;
                                    return 0;
                                }
                                int i12 = i10 + 1;
                                char cCharAt7 = charAt(i10);
                                i10 = i12;
                                cCharAt5 = cCharAt7;
                            }
                            this.bp = i10;
                            this.ch = charAt(this.bp);
                            this.matchStat = 5;
                            this.token = 16;
                            return 0;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return 0;
        }
        int i13 = cCharAt - '0';
        while (true) {
            int i14 = i3 + 1;
            char cCharAt8 = charAt(i3);
            if (cCharAt8 < '0' || cCharAt8 > '9') {
                break;
            }
            int i15 = i13 * 10;
            if (i15 < i13) {
                throw new JSONException("parseInt error : " + subString(i, i14 - 1));
            }
            i13 = i15 + (cCharAt8 - '0');
            i3 = i14;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c) {
        int i;
        char cCharAt;
        boolean z = false;
        this.matchStat = 0;
        int i2 = this.bp;
        int i3 = i2 + 1;
        char cCharAt2 = charAt(i2);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            int i4 = i3 + 1;
            char cCharAt3 = charAt(i3);
            i3 = i4;
            cCharAt2 = cCharAt3;
        }
        boolean z3 = cCharAt2 == '-';
        if (z3) {
            int i5 = i3 + 1;
            char cCharAt4 = charAt(i3);
            i3 = i5;
            cCharAt2 = cCharAt4;
        }
        char c2 = '0';
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j = cCharAt2 - '0';
            while (true) {
                i = i3 + 1;
                cCharAt = charAt(i3);
                if (cCharAt < c2 || cCharAt > '9') {
                    break;
                }
                j = (j * 10) + ((long) (cCharAt - '0'));
                i3 = i;
                c2 = '0';
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (z2) {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(i);
                i++;
            }
            if (j >= 0 || (j == Long.MIN_VALUE && z3)) {
                z = true;
            }
            if (!z) {
                this.matchStat = -1;
                return 0L;
            }
            while (cCharAt != c) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return j;
                }
                cCharAt = charAt(i);
                i++;
            }
            this.bp = i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z3 ? -j : j;
        }
        if (cCharAt2 == 'n') {
            int i6 = i3 + 1;
            if (charAt(i3) == 'u') {
                int i7 = i6 + 1;
                if (charAt(i6) == 'l') {
                    int i8 = i7 + 1;
                    if (charAt(i7) == 'l') {
                        this.matchStat = 5;
                        int i9 = i8 + 1;
                        char cCharAt5 = charAt(i8);
                        if (z2 && cCharAt5 == '\"') {
                            int i10 = i9 + 1;
                            char cCharAt6 = charAt(i9);
                            i9 = i10;
                            cCharAt5 = cCharAt6;
                        }
                        while (cCharAt5 != ',') {
                            if (cCharAt5 == ']') {
                                this.bp = i9;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (!isWhitespace(cCharAt5)) {
                                this.matchStat = -1;
                                return 0L;
                            }
                            int i11 = i9 + 1;
                            char cCharAt7 = charAt(i9);
                            i9 = i11;
                            cCharAt5 = cCharAt7;
                        }
                        this.bp = i9;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0091, code lost:
    
        if (r3 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009b, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json.");
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean seekArrayToItem(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index must > 0, but " + i);
        }
        if (this.token == 20) {
            return false;
        }
        if (this.token != 14) {
            throw new UnsupportedOperationException();
        }
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= i) {
                nextToken();
                return true;
            }
            skipWhitespace();
            if (this.ch == '\"' || this.ch == '\'') {
                skipString();
                if (this.ch != ',') {
                    if (this.ch != ']') {
                        throw new JSONException("illegal json.");
                    }
                    next();
                    nextToken(16);
                    return false;
                }
                next();
            } else {
                if (this.ch != '{') {
                    if (this.ch != '[') {
                        int i3 = this.bp + 1;
                        while (true) {
                            if (i3 >= this.text.length()) {
                                z = false;
                                break;
                            }
                            char cCharAt = this.text.charAt(i3);
                            if (cCharAt == ',') {
                                this.bp = i3 + 1;
                                this.ch = charAt(this.bp);
                                break;
                            }
                            if (cCharAt == ']') {
                                this.bp = i3 + 1;
                                this.ch = charAt(this.bp);
                                nextToken();
                                return false;
                            }
                            i3++;
                        }
                    } else {
                        next();
                        this.token = 14;
                        skipArray(false);
                    }
                } else {
                    next();
                    this.token = 12;
                    skipObject(false);
                }
                if (this.token != 16) {
                    if (this.token == 15) {
                        return false;
                    }
                    throw new UnsupportedOperationException();
                }
            }
            i2++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:246:0x032e, code lost:
    
        if (r16.ch != '+') goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0339, code lost:
    
        if (r16.ch <= '9') goto L247;
     */
    /* JADX WARN: Path cross not found for [B:247:0x0330, B:245:0x032c], limit reached: 283 */
    /* JADX WARN: Path cross not found for [B:247:0x0330, B:248:0x0333], limit reached: 283 */
    /* JADX WARN: Path cross not found for [B:248:0x0333, B:247:0x0330], limit reached: 283 */
    /* JADX WARN: Removed duplicated region for block: B:147:0x020c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:251:0x0339 -> B:247:0x0330). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int seekObjectToField(long j, boolean z) {
        int i;
        int i2 = -1;
        if (this.token == 20) {
            return -1;
        }
        if (this.token == 13 || this.token == 15) {
            nextToken();
            return -1;
        }
        if (this.token != 12 && this.token != 16) {
            throw new UnsupportedOperationException(JSONToken.name(this.token));
        }
        while (this.ch != '}') {
            char c = this.ch;
            char cCharAt = JSONLexer.EOI;
            if (c == 26) {
                return i2;
            }
            if (this.ch != '\"') {
                skipWhitespace();
            }
            if (this.ch != '\"') {
                throw new UnsupportedOperationException();
            }
            long j2 = -3750763034362895579L;
            int i3 = this.bp + 1;
            while (true) {
                if (i3 >= this.text.length()) {
                    break;
                }
                char cCharAt2 = this.text.charAt(i3);
                if (cCharAt2 == '\\') {
                    i3++;
                    if (i3 == this.text.length()) {
                        throw new JSONException("unclosed str, " + info());
                    }
                    cCharAt2 = this.text.charAt(i3);
                }
                if (cCharAt2 == '\"') {
                    this.bp = i3 + 1;
                    this.ch = this.bp >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                } else {
                    j2 = (j2 ^ ((long) cCharAt2)) * 1099511628211L;
                    i3++;
                }
            }
            if (j2 == j) {
                if (this.ch != ':') {
                    skipWhitespace();
                }
                if (this.ch != ':') {
                    return 3;
                }
                int i4 = this.bp + 1;
                this.bp = i4;
                this.ch = i4 >= this.text.length() ? (char) 26 : this.text.charAt(i4);
                if (this.ch == ',') {
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    if (i5 < this.text.length()) {
                        cCharAt = this.text.charAt(i5);
                    }
                    this.ch = cCharAt;
                    i = 16;
                } else {
                    if (this.ch != ']') {
                        if (this.ch == '}') {
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            if (i6 < this.text.length()) {
                                cCharAt = this.text.charAt(i6);
                            }
                            this.ch = cCharAt;
                            this.token = 13;
                            return 3;
                        }
                        if (this.ch < '0' || this.ch > '9') {
                            nextToken(2);
                            return 3;
                        }
                        this.sp = 0;
                        this.pos = this.bp;
                        scanNumber();
                        return 3;
                    }
                    int i7 = this.bp + 1;
                    this.bp = i7;
                    if (i7 < this.text.length()) {
                        cCharAt = this.text.charAt(i7);
                    }
                    this.ch = cCharAt;
                    i = 15;
                }
                this.token = i;
                return 3;
            }
            if (this.ch != ':') {
                skipWhitespace();
            }
            if (this.ch != ':') {
                throw new JSONException("illegal json, " + info());
            }
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = i8 >= this.text.length() ? (char) 26 : this.text.charAt(i8);
            if (this.ch != '\"' && this.ch != '\'' && this.ch != '{' && this.ch != '[' && this.ch != '0' && this.ch != '1' && this.ch != '2' && this.ch != '3' && this.ch != '4' && this.ch != '5' && this.ch != '6' && this.ch != '7' && this.ch != '8' && this.ch != '9' && this.ch != '+' && this.ch != '-') {
                skipWhitespace();
            }
            if (this.ch == '-' || this.ch == '+' || (this.ch >= '0' && this.ch <= '9')) {
                do {
                    next();
                    if (this.ch < '0') {
                        break;
                    }
                } while (this.ch <= '9');
                if (this.ch == '.') {
                    do {
                        next();
                        if (this.ch < '0') {
                            break;
                        }
                    } while (this.ch <= '9');
                }
                if (this.ch == 'E' || this.ch == 'e') {
                    next();
                    if (this.ch != '-') {
                    }
                    next();
                    if (this.ch >= '0') {
                    }
                }
                if (this.ch != ',') {
                    skipWhitespace();
                }
                if (this.ch == ',') {
                    next();
                }
            } else if (this.ch == '\"') {
                skipString();
                if (this.ch != ',' && this.ch != '}') {
                    skipWhitespace();
                }
                if (this.ch == ',') {
                }
            } else if (this.ch == 't') {
                next();
                if (this.ch == 'r') {
                    next();
                    if (this.ch == 'u') {
                        next();
                        if (this.ch == 'e') {
                            next();
                        }
                    }
                }
                if (this.ch != ',' && this.ch != '}') {
                    skipWhitespace();
                }
                if (this.ch == ',') {
                }
            } else if (this.ch == 'n') {
                next();
                if (this.ch == 'u') {
                    next();
                    if (this.ch == 'l') {
                        next();
                        if (this.ch == 'l') {
                            next();
                        }
                    }
                }
                if (this.ch != ',' && this.ch != '}') {
                    skipWhitespace();
                }
                if (this.ch == ',') {
                }
            } else if (this.ch == 'f') {
                next();
                if (this.ch == 'a') {
                    next();
                    if (this.ch == 'l') {
                        next();
                        if (this.ch == 's') {
                            next();
                            if (this.ch == 'e') {
                                next();
                            }
                        }
                    }
                }
                if (this.ch != ',' && this.ch != '}') {
                    skipWhitespace();
                }
                if (this.ch == ',') {
                }
            } else if (this.ch == '{') {
                int i9 = this.bp + 1;
                this.bp = i9;
                this.ch = i9 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i9);
                if (z) {
                    this.token = 12;
                    return 1;
                }
                skipObject(false);
                if (this.token == 13) {
                    return -1;
                }
            } else {
                if (this.ch != '[') {
                    throw new UnsupportedOperationException();
                }
                next();
                if (z) {
                    this.token = 14;
                    return 2;
                }
                skipArray(false);
                if (this.token == 13) {
                    return -1;
                }
            }
            i2 = -1;
        }
        next();
        nextToken();
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x018e, code lost:
    
        if (r14.ch == '\'') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0192, code lost:
    
        if (r14.ch == '{') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0196, code lost:
    
        if (r14.ch == '[') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x019a, code lost:
    
        if (r14.ch == '0') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01a0, code lost:
    
        if (r14.ch == '1') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01a6, code lost:
    
        if (r14.ch == '2') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01ac, code lost:
    
        if (r14.ch == '3') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01b2, code lost:
    
        if (r14.ch == '4') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01b8, code lost:
    
        if (r14.ch == '5') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x01be, code lost:
    
        if (r14.ch == '6') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01c4, code lost:
    
        if (r14.ch == '7') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01ca, code lost:
    
        if (r14.ch == '8') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01ce, code lost:
    
        if (r14.ch == '9') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01d2, code lost:
    
        if (r14.ch == '+') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x01d6, code lost:
    
        if (r14.ch == '-') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x01d8, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01dd, code lost:
    
        if (r14.ch == '-') goto L197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x01e1, code lost:
    
        if (r14.ch == '+') goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01e5, code lost:
    
        if (r14.ch < '0') goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01e9, code lost:
    
        if (r14.ch > '9') goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01ee, code lost:
    
        if (r14.ch != '\"') goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01f0, code lost:
    
        skipString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01f5, code lost:
    
        if (r14.ch == ',') goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01f9, code lost:
    
        if (r14.ch == '}') goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01fb, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0200, code lost:
    
        if (r14.ch != ',') goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0202, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0209, code lost:
    
        if (r14.ch != '{') goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x020b, code lost:
    
        r2 = r14.bp + 1;
        r14.bp = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0217, code lost:
    
        if (r2 < r14.text.length()) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x021a, code lost:
    
        r4 = r14.text.charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0220, code lost:
    
        r14.ch = r4;
        skipObject(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0229, code lost:
    
        if (r14.ch != '[') goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x022b, code lost:
    
        next();
        skipArray(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0238, code lost:
    
        throw new java.lang.UnsupportedOperationException();
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0239, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x023e, code lost:
    
        if (r14.ch < '0') goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0242, code lost:
    
        if (r14.ch > '9') goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0249, code lost:
    
        if (r14.ch != '.') goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x024b, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0250, code lost:
    
        if (r14.ch < '0') goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0254, code lost:
    
        if (r14.ch > '9') goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x025b, code lost:
    
        if (r14.ch == 'E') goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0261, code lost:
    
        if (r14.ch != 'e') goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0263, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0268, code lost:
    
        if (r14.ch == '-') goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x026c, code lost:
    
        if (r14.ch != '+') goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x026e, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0273, code lost:
    
        if (r14.ch < '0') goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0277, code lost:
    
        if (r14.ch > '9') goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x027c, code lost:
    
        if (r14.ch == ',') goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x027e, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0283, code lost:
    
        if (r14.ch != ',') goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x02a1, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json, " + info());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a6, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a9, code lost:
    
        if (r8 >= r15.length) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00af, code lost:
    
        if (r6 != r15[r8]) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b2, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b5, code lost:
    
        r8 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00be, code lost:
    
        if (r8 == (-1)) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c2, code lost:
    
        if (r14.ch == ':') goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c4, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c9, code lost:
    
        if (r14.ch != ':') goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00cb, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d7, code lost:
    
        if (r15 < r14.text.length()) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d9, code lost:
    
        r15 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00db, code lost:
    
        r15 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e1, code lost:
    
        r14.ch = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00e5, code lost:
    
        if (r14.ch != ',') goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00e7, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00f3, code lost:
    
        if (r15 < r14.text.length()) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00f6, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00fc, code lost:
    
        r14.ch = r4;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0105, code lost:
    
        if (r14.ch != ']') goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0107, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0113, code lost:
    
        if (r15 < r14.text.length()) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0116, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x011c, code lost:
    
        r14.ch = r4;
        r15 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0120, code lost:
    
        r14.token = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0125, code lost:
    
        if (r14.ch != '}') goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0127, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0133, code lost:
    
        if (r15 < r14.text.length()) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0136, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x013c, code lost:
    
        r14.ch = r4;
        r15 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0143, code lost:
    
        if (r14.ch < '0') goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0147, code lost:
    
        if (r14.ch > '9') goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0149, code lost:
    
        r14.sp = 0;
        r14.pos = r14.bp;
        scanNumber();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0153, code lost:
    
        nextToken(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0157, code lost:
    
        r14.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x015a, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x015d, code lost:
    
        if (r14.ch == ':') goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x015f, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0164, code lost:
    
        if (r14.ch != ':') goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0166, code lost:
    
        r3 = r14.bp + 1;
        r14.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0172, code lost:
    
        if (r3 < r14.text.length()) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0174, code lost:
    
        r3 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0176, code lost:
    
        r3 = r14.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x017c, code lost:
    
        r14.ch = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0188, code lost:
    
        if (r14.ch == '\"') goto L130;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:185:0x0277 -> B:181:0x026e). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int seekObjectToField(long[] jArr) {
        if (this.token != 12 && this.token != 16) {
            throw new UnsupportedOperationException();
        }
        while (this.ch != '}') {
            char c = this.ch;
            char cCharAt = JSONLexer.EOI;
            if (c == 26) {
                this.matchStat = -1;
                return -1;
            }
            if (this.ch != '\"') {
                skipWhitespace();
            }
            if (this.ch != '\"') {
                throw new UnsupportedOperationException();
            }
            long j = -3750763034362895579L;
            int i = this.bp;
            while (true) {
                i++;
                if (i >= this.text.length()) {
                    break;
                }
                char cCharAt2 = this.text.charAt(i);
                if (cCharAt2 == '\\') {
                    i++;
                    if (i == this.text.length()) {
                        throw new JSONException("unclosed str, " + info());
                    }
                    cCharAt2 = this.text.charAt(i);
                }
                if (cCharAt2 == '\"') {
                    this.bp = i + 1;
                    this.ch = this.bp >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                } else {
                    j = (j ^ ((long) cCharAt2)) * 1099511628211L;
                }
            }
        }
        next();
        nextToken();
        this.matchStat = -1;
        return -1;
    }

    protected void setTime(char c, char c2, char c3, char c4, char c5, char c6) {
        this.calendar.set(11, ((c - '0') * 10) + (c2 - '0'));
        this.calendar.set(12, ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(13, ((c5 - '0') * 10) + (c6 - '0'));
    }

    protected void setTimeZone(char c, char c2, char c3) {
        setTimeZone(c, c2, c3, '0', '0');
    }

    protected void setTimeZone(char c, char c2, char c3, char c4, char c5) {
        int i = ((((c2 - '0') * 10) + (c3 - '0')) * 3600 * 1000) + ((((c4 - '0') * 10) + (c5 - '0')) * 60 * 1000);
        if (c == '-') {
            i = -i;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i) {
            String[] availableIDs = TimeZone.getAvailableIDs(i);
            if (availableIDs.length > 0) {
                this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    public final void skipArray(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char cCharAt = this.text.charAt(i);
            if (cCharAt == '\\') {
                if (i >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
                i++;
            } else if (cCharAt == '\"') {
                z2 = !z2;
            } else if (cCharAt != '[') {
                char cCharAt2 = JSONLexer.EOI;
                if (cCharAt == '{' && z) {
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    if (i3 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i3);
                    }
                    this.ch = cCharAt2;
                    skipObject(z);
                } else if (cCharAt == ']' && !z2 && i2 - 1 == -1) {
                    this.bp = i + 1;
                    if (this.bp == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    } else {
                        this.ch = this.text.charAt(this.bp);
                        nextToken(16);
                        return;
                    }
                }
            } else if (!z2) {
                i2++;
            }
            i++;
        }
        if (i != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char cCharAt = this.text.charAt(i);
            if (cCharAt == '\\') {
                if (i >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
                i++;
            } else if (cCharAt == '\"') {
                z2 = !z2;
            } else if (cCharAt == '{') {
                if (!z2) {
                    i2++;
                }
            } else if (cCharAt == '}' && !z2 && i2 - 1 == -1) {
                this.bp = i + 1;
                int i3 = this.bp;
                int length = this.text.length();
                char cCharAt2 = JSONLexer.EOI;
                if (i3 == length) {
                    this.ch = JSONLexer.EOI;
                    this.token = 20;
                    return;
                }
                this.ch = this.text.charAt(this.bp);
                if (this.ch == ',') {
                    this.token = 16;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    if (i4 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i4);
                    }
                    this.ch = cCharAt2;
                    return;
                }
                if (this.ch == '}') {
                    this.token = 13;
                    next();
                    return;
                } else if (this.ch != ']') {
                    nextToken(16);
                    return;
                } else {
                    this.token = 15;
                    next();
                    return;
                }
            }
            i++;
        }
        if (i != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    public final void skipString() {
        if (this.ch != '\"') {
            throw new UnsupportedOperationException();
        }
        int i = this.bp;
        while (true) {
            i++;
            if (i >= this.text.length()) {
                throw new JSONException("unclosed str");
            }
            char cCharAt = this.text.charAt(i);
            if (cCharAt == '\\') {
                if (i < this.len - 1) {
                    i++;
                }
            } else if (cCharAt == '\"') {
                String str = this.text;
                int i2 = i + 1;
                this.bp = i2;
                this.ch = str.charAt(i2);
                return;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        return !this.hasSpecial ? subString(this.np + 1, this.sp) : new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int i, int i2) {
        if (!ASMUtils.IS_ANDROID) {
            return this.text.substring(i, i2 + i);
        }
        if (i2 < this.sbuf.length) {
            this.text.getChars(i, i + i2, this.sbuf, 0);
            return new String(this.sbuf, 0, i2);
        }
        char[] cArr = new char[i2];
        this.text.getChars(i, i2 + i, cArr, 0);
        return new String(cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int i, int i2) {
        if (ASMUtils.IS_ANDROID && i2 < this.sbuf.length) {
            this.text.getChars(i, i2 + i, this.sbuf, 0);
            return this.sbuf;
        }
        char[] cArr = new char[i2];
        this.text.getChars(i, i2 + i, cArr, 0);
        return cArr;
    }
}
