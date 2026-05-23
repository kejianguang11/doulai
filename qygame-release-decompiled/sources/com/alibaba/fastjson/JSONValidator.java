package com.alibaba.fastjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* JADX INFO: loaded from: classes.dex */
public abstract class JSONValidator implements Cloneable {
    protected char ch;
    protected boolean eof;
    protected Type type;
    protected int pos = -1;
    protected int count = 0;
    protected boolean supportMultiValue = true;

    static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;
        final Reader r;
        private int end = -1;
        private int readCount = 0;

        ReaderValidator(Reader reader) {
            this.r = reader;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.r.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            if (this.pos < this.end) {
                char[] cArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = cArr[i];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                int i2 = this.r.read(this.buf, 0, this.buf.length);
                this.readCount++;
                if (i2 > 0) {
                    this.ch = this.buf[0];
                    this.pos = 0;
                    this.end = i2 - 1;
                } else {
                    if (i2 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    public enum Type {
        Object,
        Array,
        Value
    }

    static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str) {
            this.str = str;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            if (this.pos < this.str.length()) {
                this.ch = this.str.charAt(this.pos);
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private final InputStream is;
        private int end = -1;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream inputStream) {
            this.is = inputStream;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.is.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            if (this.pos < this.end) {
                byte[] bArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = (char) bArr[i];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                int i2 = this.is.read(this.buf, 0, this.buf.length);
                this.readCount++;
                if (i2 > 0) {
                    this.ch = (char) this.buf[0];
                    this.pos = 0;
                    this.end = i2 - 1;
                } else {
                    if (i2 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bArr) {
            this.bytes = bArr;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            if (this.pos < this.bytes.length) {
                this.ch = (char) this.bytes[this.pos];
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    public static JSONValidator from(Reader reader) {
        return new ReaderValidator(reader);
    }

    public static JSONValidator from(String str) {
        return new UTF16Validator(str);
    }

    public static JSONValidator fromUtf8(InputStream inputStream) {
        return new UTF8InputStreamValidator(inputStream);
    }

    public static JSONValidator fromUtf8(byte[] bArr) {
        return new UTF8Validator(bArr);
    }

    static final boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f' || c == '\b';
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x01a3 A[LOOP:3: B:131:0x01a3->B:183:?, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x01e1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void any() {
        Type type;
        char c = this.ch;
        if (c != '\"') {
            if (c != '+' && c != '-') {
                if (c == '[') {
                    next();
                    skipWhiteSpace();
                    if (this.ch != ']') {
                        while (true) {
                            any();
                            skipWhiteSpace();
                            if (this.ch == ',') {
                                next();
                                skipWhiteSpace();
                            } else if (this.ch == ']') {
                                break;
                            } else {
                                error();
                            }
                        }
                    }
                    next();
                    type = Type.Array;
                } else if (c == 'f') {
                    next();
                    if (this.ch != 'a') {
                        error();
                    }
                    next();
                    if (this.ch != 'l') {
                        error();
                    }
                    next();
                    if (this.ch != 's') {
                        error();
                    }
                    next();
                    if (this.ch != 'e') {
                        error();
                    }
                    next();
                    if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                        error();
                        next();
                        if (this.ch != 'u') {
                            error();
                        }
                        next();
                        if (this.ch != 'l') {
                            error();
                        }
                        next();
                        if (this.ch != 'l') {
                            error();
                        }
                        next();
                        if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                            error();
                            error();
                            return;
                        }
                    }
                } else if (c == 'n') {
                    next();
                    if (this.ch != 'u') {
                    }
                    next();
                    if (this.ch != 'l') {
                    }
                    next();
                    if (this.ch != 'l') {
                    }
                    next();
                    if (!isWhiteSpace(this.ch)) {
                        error();
                        error();
                        return;
                    }
                } else if (c == 't') {
                    next();
                    if (this.ch != 'r') {
                        error();
                    }
                    next();
                    if (this.ch != 'u') {
                        error();
                    }
                    next();
                    if (this.ch != 'e') {
                        error();
                    }
                    next();
                    if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                        error();
                        next();
                        if (this.ch != 'a') {
                        }
                        next();
                        if (this.ch != 'l') {
                        }
                        next();
                        if (this.ch != 's') {
                        }
                        next();
                        if (this.ch != 'e') {
                        }
                        next();
                        if (!isWhiteSpace(this.ch)) {
                            error();
                            next();
                            if (this.ch != 'u') {
                            }
                            next();
                            if (this.ch != 'l') {
                            }
                            next();
                            if (this.ch != 'l') {
                            }
                            next();
                            if (!isWhiteSpace(this.ch)) {
                            }
                        }
                    }
                } else {
                    if (c != '{') {
                        switch (c) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                break;
                            default:
                                error();
                                break;
                        }
                        return;
                    }
                    next();
                    skipWhiteSpace();
                    if (this.ch != '}') {
                        while (true) {
                            if (this.ch == '\"') {
                                fieldName();
                            } else {
                                error();
                            }
                            skipWhiteSpace();
                            if (this.ch == ':') {
                                next();
                            } else {
                                error();
                            }
                            skipWhiteSpace();
                            any();
                            skipWhiteSpace();
                            if (this.ch == ',') {
                                next();
                                skipWhiteSpace();
                            } else if (this.ch == '}') {
                                break;
                            }
                        }
                    }
                    next();
                    type = Type.Object;
                }
                this.type = type;
            }
            if (this.ch == '-' || this.ch == '+') {
                next();
                skipWhiteSpace();
                if (this.ch < '0' || this.ch > '9') {
                    error();
                }
            }
            do {
                next();
                if (this.ch >= '0') {
                }
                if (this.ch == '.') {
                    do {
                        next();
                        if (this.ch >= '0') {
                        }
                    } while (this.ch <= '9');
                }
                if (this.ch != 'e' || this.ch == 'E') {
                    next();
                    if (this.ch != '-' || this.ch == '+') {
                        next();
                    }
                    if (this.ch >= '0' || this.ch > '9') {
                        error();
                    } else {
                        next();
                    }
                    do {
                        next();
                        if (this.ch < '0') {
                        }
                    } while (this.ch <= '9');
                }
                this.type = Type.Value;
                return;
            } while (this.ch <= '9');
            if (this.ch == '.') {
            }
            if (this.ch != 'e') {
                next();
                if (this.ch != '-') {
                    next();
                    if (this.ch >= '0') {
                        error();
                        do {
                            next();
                            if (this.ch < '0') {
                            }
                        } while (this.ch <= '9');
                    }
                }
            }
            this.type = Type.Value;
            return;
        }
        while (true) {
            next();
            if (this.ch == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                }
            } else if (this.ch == '\"') {
                break;
            }
        }
        next();
        type = Type.Value;
        this.type = type;
    }

    public void close() throws IOException {
    }

    void error() {
        throw new JSONException("error : " + this.pos);
    }

    protected void fieldName() {
        while (true) {
            next();
            if (this.ch == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                }
            } else if (this.ch == '\"') {
                next();
                return;
            }
        }
    }

    public Type getType() {
        return this.type;
    }

    abstract void next();

    void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    public boolean validate() {
        do {
            any();
            this.count++;
            if (!this.supportMultiValue || this.eof) {
                break;
            }
            skipWhiteSpace();
        } while (!this.eof);
        return true;
    }
}
