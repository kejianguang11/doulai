package com.alibaba.fastjson.parser;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class ParseContext {
    public final Object fieldName;
    public Object object;
    public final ParseContext parent;
    private transient String path;
    public Type type;

    public ParseContext(ParseContext parseContext, Object obj, Object obj2) {
        this.parent = parseContext;
        this.object = obj;
        this.fieldName = obj2;
    }

    public String toString() {
        StringBuilder sb;
        String string;
        if (this.path == null) {
            if (this.parent == null) {
                string = "$";
            } else {
                if (this.fieldName instanceof Integer) {
                    sb = new StringBuilder();
                    sb.append(this.parent.toString());
                    sb.append("[");
                    sb.append(this.fieldName);
                    sb.append("]");
                } else {
                    sb = new StringBuilder();
                    sb.append(this.parent.toString());
                    sb.append(".");
                    sb.append(this.fieldName);
                }
                string = sb.toString();
            }
            this.path = string;
        }
        return this.path;
    }
}
