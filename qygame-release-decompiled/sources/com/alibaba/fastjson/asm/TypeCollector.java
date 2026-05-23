package com.alibaba.fastjson.asm;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.ASMUtils;
import com.alipay.sdk.util.h;
import com.igexin.push.core.b;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TypeCollector {
    private static String JSONType = ASMUtils.desc((Class<?>) JSONType.class);
    private static final Map<String, String> primitives = new HashMap<String, String>() { // from class: com.alibaba.fastjson.asm.TypeCollector.1
        {
            put("int", "I");
            put("boolean", "Z");
            put("byte", "B");
            put("char", "C");
            put("short", "S");
            put("float", "F");
            put("long", "J");
            put("double", "D");
        }
    };
    protected MethodCollector collector = null;
    protected boolean jsonType;
    private final String methodName;
    private final Class<?>[] parameterTypes;

    public TypeCollector(String str, Class<?>[] clsArr) {
        this.methodName = str;
        this.parameterTypes = clsArr;
    }

    private boolean correctTypeName(Type type, String str) {
        StringBuilder sb;
        String str2;
        String className = type.getClassName();
        String str3 = "";
        while (className.endsWith("[]")) {
            str3 = str3 + "[";
            className = className.substring(0, className.length() - 2);
        }
        if (!str3.equals("")) {
            if (primitives.containsKey(className)) {
                sb = new StringBuilder();
                sb.append(str3);
                str2 = primitives.get(className);
            } else {
                sb = new StringBuilder();
                sb.append(str3);
                sb.append("L");
                sb.append(className);
                str2 = h.b;
            }
            sb.append(str2);
            className = sb.toString();
        }
        return className.equals(str);
    }

    public String[] getParameterNamesForMethod() {
        return (this.collector == null || !this.collector.debugInfoPresent) ? new String[0] : this.collector.getResult().split(b.an);
    }

    public boolean hasJsonType() {
        return this.jsonType;
    }

    public boolean matched() {
        return this.collector != null;
    }

    public void visitAnnotation(String str) {
        if (JSONType.equals(str)) {
            this.jsonType = true;
        }
    }

    protected MethodCollector visitMethod(int i, String str, String str2) {
        if (this.collector != null || !str.equals(this.methodName)) {
            return null;
        }
        Type[] argumentTypes = Type.getArgumentTypes(str2);
        int i2 = 0;
        for (Type type : argumentTypes) {
            String className = type.getClassName();
            if (className.equals("long") || className.equals("double")) {
                i2++;
            }
        }
        if (argumentTypes.length != this.parameterTypes.length) {
            return null;
        }
        for (int i3 = 0; i3 < argumentTypes.length; i3++) {
            if (!correctTypeName(argumentTypes[i3], this.parameterTypes[i3].getName())) {
                return null;
            }
        }
        MethodCollector methodCollector = new MethodCollector(!Modifier.isStatic(i) ? 1 : 0, argumentTypes.length + i2);
        this.collector = methodCollector;
        return methodCollector;
    }
}
