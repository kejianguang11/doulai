package com.alibaba.fastjson.serializer;

/* JADX INFO: loaded from: classes.dex */
public class SerialContext {
    public final int features;
    public final Object fieldName;
    public final Object object;
    public final SerialContext parent;

    public SerialContext(SerialContext serialContext, Object obj, Object obj2, int i, int i2) {
        this.parent = serialContext;
        this.object = obj;
        this.fieldName = obj2;
        this.features = i;
    }

    public Object getFieldName() {
        return this.fieldName;
    }

    public Object getObject() {
        return this.object;
    }

    public SerialContext getParent() {
        return this.parent;
    }

    public String getPath() {
        return toString();
    }

    public String toString() {
        if (this.parent == null) {
            return "$";
        }
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    protected void toString(StringBuilder sb) {
        String string;
        boolean z;
        char c;
        if (this.parent != null) {
            this.parent.toString(sb);
            if (this.fieldName == null) {
                string = ".null";
            } else if (this.fieldName instanceof Integer) {
                sb.append('[');
                sb.append(((Integer) this.fieldName).intValue());
                c = ']';
            } else {
                sb.append('.');
                string = this.fieldName.toString();
                int i = 0;
                while (true) {
                    if (i >= string.length()) {
                        z = false;
                        break;
                    }
                    char cCharAt = string.charAt(i);
                    if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt < 'a' || cCharAt > 'z') && cCharAt <= 128))) {
                        z = true;
                        break;
                    }
                    i++;
                }
                if (z) {
                    for (int i2 = 0; i2 < string.length(); i2++) {
                        char cCharAt2 = string.charAt(i2);
                        if (cCharAt2 == '\\') {
                            sb.append('\\');
                        } else {
                            if ((cCharAt2 < '0' || cCharAt2 > '9') && ((cCharAt2 < 'A' || cCharAt2 > 'Z') && ((cCharAt2 < 'a' || cCharAt2 > 'z') && cCharAt2 <= 128))) {
                            }
                            sb.append(cCharAt2);
                        }
                        sb.append('\\');
                        sb.append('\\');
                        sb.append(cCharAt2);
                    }
                    return;
                }
            }
            sb.append(string);
            return;
        }
        c = '$';
        sb.append(c);
    }
}
