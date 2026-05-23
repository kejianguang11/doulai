package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.alipay.sdk.util.h;
import com.igexin.push.core.d.d;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class ASMSerializerFactory implements Opcodes {
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String ObjectSerializer = ASMUtils.type(ObjectSerializer.class);
    static final String ObjectSerializer_desc = "L" + ObjectSerializer + h.b;
    static final String SerializeWriter = ASMUtils.type(SerializeWriter.class);
    static final String SerializeWriter_desc = "L" + SerializeWriter + h.b;
    static final String JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
    static final String JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + h.b;
    static final String SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
    static final String SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);

    static class Context {
        static final int features = 5;
        static int fieldName = 6;
        static final int obj = 2;
        static int original = 7;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 8;
        static final int serializer = 1;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] fieldInfoArr, SerializeBeanInfo serializeBeanInfo, String str, boolean z, boolean z2) {
            this.getters = fieldInfoArr;
            this.className = str;
            this.beanInfo = serializeBeanInfo;
            this.writeDirect = z;
            this.nonContext = z2 || serializeBeanInfo.beanType.isEnum();
        }

        public int getFieldOrinal(String str) {
            int length = this.getters.length;
            for (int i = 0; i < length; i++) {
                if (this.getters[i].name.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public int var(String str) {
            if (this.variants.get(str) == null) {
                Map<String, Integer> map = this.variants;
                int i = this.variantIndex;
                this.variantIndex = i + 1;
                map.put(str, Integer.valueOf(i));
            }
            return this.variants.get(str).intValue();
        }

        public int var(String str, int i) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i;
            }
            return this.variants.get(str).intValue();
        }
    }

    private void _after(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _apply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        String str;
        String str2;
        String str3;
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            str = "java/lang/Byte";
            str2 = "valueOf";
            str3 = "(B)Ljava/lang/Byte;";
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            str = "java/lang/Short";
            str2 = "valueOf";
            str3 = "(S)Ljava/lang/Short;";
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            str = "java/lang/Integer";
            str2 = "valueOf";
            str3 = "(I)Ljava/lang/Integer;";
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            str = "java/lang/Character";
            str2 = "valueOf";
            str3 = "(C)Ljava/lang/Character;";
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            str = "java/lang/Long";
            str2 = "valueOf";
            str3 = "(J)Ljava/lang/Long;";
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            str = "java/lang/Float";
            str2 = "valueOf";
            str3 = "(F)Ljava/lang/Float;";
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            str = "java/lang/Double";
            str2 = "valueOf";
            str3 = "(D)Ljava/lang/Double;";
        } else {
            if (cls != Boolean.TYPE) {
                methodVisitor.visitVarInsn(25, context.var(cls == BigDecimal.class ? "decimal" : cls == String.class ? "string" : cls.isEnum() ? "enum" : List.class.isAssignableFrom(cls) ? "list" : "object"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
            }
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            str = "java/lang/Boolean";
            str2 = "valueOf";
            str3 = "(Z)Ljava/lang/Boolean;";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, str, str2, str3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _before(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _decimal(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("decimal"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label3);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitLabel(label);
    }

    private void _double(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(57, context.var("double", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(24, context.var("double", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _enum(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        String str;
        String str2;
        String str3;
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label3);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/lang/Enum");
        methodVisitor.visitVarInsn(58, context.var("enum"));
        _filters(methodVisitor, fieldInfo, context, label3);
        methodVisitor.visitVarInsn(25, context.var("enum"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;");
            str = SerializeWriter;
            str2 = "writeFieldValueStringWithDoubleQuote";
            str3 = "(CLjava/lang/String;Ljava/lang/String;)V";
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            str = JSONSerializer;
            str2 = "writeWithFieldName";
            str3 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, str2, str3);
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label3);
    }

    private void _filters(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (fieldInfo.fieldTransient) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _notWriteDefault(methodVisitor, fieldInfo, context, label);
        if (context.writeDirect) {
            return;
        }
        _apply(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        _processKey(methodVisitor, fieldInfo, context);
        _processValue(methodVisitor, fieldInfo, context, label);
    }

    private void _float(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(56, context.var("float"));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(23, context.var("float"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _get(MethodVisitor methodVisitor, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            methodVisitor.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (method.getReturnType().equals(fieldInfo.fieldClass)) {
                return;
            }
        } else {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            Field field = fieldInfo.field;
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
            if (field.getType().equals(fieldInfo.fieldClass)) {
                return;
            }
        }
        methodVisitor.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
    }

    private void _getFieldSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
    }

    private void _getListFieldItemSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void _if_write_null(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        int mask;
        int mask2;
        SerializerFeature serializerFeature;
        SerializerFeature serializerFeature2;
        int iValueOf;
        Class<?> cls = fieldInfo.fieldClass;
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label);
        JSONField annotation = fieldInfo.getAnnotation();
        int iOf = annotation != null ? SerializerFeature.of(annotation.serialzeFeatures()) : 0;
        JSONType jSONType = context.beanInfo.jsonType;
        if (jSONType != null) {
            iOf |= SerializerFeature.of(jSONType.serialzeFeatures());
        }
        if (cls == String.class) {
            mask2 = SerializerFeature.WriteMapNullValue.getMask();
            serializerFeature = SerializerFeature.WriteNullStringAsEmpty;
        } else if (Number.class.isAssignableFrom(cls)) {
            mask2 = SerializerFeature.WriteMapNullValue.getMask();
            serializerFeature = SerializerFeature.WriteNullNumberAsZero;
        } else if (Collection.class.isAssignableFrom(cls)) {
            mask2 = SerializerFeature.WriteMapNullValue.getMask();
            serializerFeature = SerializerFeature.WriteNullListAsEmpty;
        } else {
            if (Boolean.class != cls) {
                mask = SerializerFeature.WRITE_MAP_NULL_FEATURES;
                if ((iOf & mask) == 0) {
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitLdcInsn(Integer.valueOf(mask));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
                }
                methodVisitor.visitLabel(label3);
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitVarInsn(21, context.var("seperator"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
                _writeFieldName(methodVisitor, context);
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitLdcInsn(Integer.valueOf(iOf));
                if (cls != String.class || cls == Character.class) {
                    serializerFeature2 = SerializerFeature.WriteNullStringAsEmpty;
                } else if (Number.class.isAssignableFrom(cls)) {
                    serializerFeature2 = SerializerFeature.WriteNullNumberAsZero;
                } else if (cls == Boolean.class) {
                    serializerFeature2 = SerializerFeature.WriteNullBooleanAsFalse;
                } else {
                    if (!Collection.class.isAssignableFrom(cls) && !cls.isArray()) {
                        iValueOf = 0;
                        methodVisitor.visitLdcInsn(iValueOf);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "(II)V");
                        _seperator(methodVisitor, context);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
                        methodVisitor.visitLabel(label2);
                        methodVisitor.visitLabel(label4);
                    }
                    serializerFeature2 = SerializerFeature.WriteNullListAsEmpty;
                }
                iValueOf = Integer.valueOf(serializerFeature2.mask);
                methodVisitor.visitLdcInsn(iValueOf);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "(II)V");
                _seperator(methodVisitor, context);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
                methodVisitor.visitLabel(label2);
                methodVisitor.visitLabel(label4);
            }
            mask2 = SerializerFeature.WriteMapNullValue.getMask();
            serializerFeature = SerializerFeature.WriteNullBooleanAsFalse;
        }
        mask = mask2 | serializerFeature.getMask();
        if ((iOf & mask) == 0) {
        }
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(Integer.valueOf(iOf));
        if (cls != String.class) {
            serializerFeature2 = SerializerFeature.WriteNullStringAsEmpty;
            iValueOf = Integer.valueOf(serializerFeature2.mask);
        }
        methodVisitor.visitLdcInsn(iValueOf);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "(II)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label4);
    }

    private void _int(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, int i, char c) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(54, i);
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(21, i);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + c + ")V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _labelApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(fieldInfo.label);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _list(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label;
        Label label2;
        Label label3;
        Label label4;
        int i;
        String str;
        String str2;
        String str3;
        int i2;
        int i3;
        int i4;
        java.lang.reflect.Type collectionItemType = TypeUtils.getCollectionItemType(fieldInfo.fieldType);
        Class<?> cls2 = collectionItemType instanceof Class ? (Class) collectionItemType : null;
        if (cls2 == Object.class || cls2 == Serializable.class) {
            cls2 = null;
        }
        Label label5 = new Label();
        Label label6 = new Label();
        Label label7 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label5);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/util/List");
        methodVisitor.visitVarInsn(58, context.var("list"));
        _filters(methodVisitor, fieldInfo, context, label5);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label6);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label7);
        methodVisitor.visitLabel(label6);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
        methodVisitor.visitVarInsn(54, context.var("size"));
        Label label8 = new Label();
        Label label9 = new Label();
        methodVisitor.visitVarInsn(21, context.var("size"));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label8);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn("[]");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label9);
        methodVisitor.visitLabel(label8);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (collectionItemType == String.class && context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/util/List;)V");
            label = label5;
            label2 = label7;
            i3 = 182;
            i4 = 1;
            i2 = 25;
            label4 = label9;
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 91);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            Label label10 = new Label();
            Label label11 = new Label();
            Label label12 = new Label();
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, context.var(d.e));
            methodVisitor.visitLabel(label10);
            methodVisitor.visitVarInsn(21, context.var(d.e));
            methodVisitor.visitVarInsn(21, context.var("size"));
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label12);
            methodVisitor.visitVarInsn(21, context.var(d.e));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label11);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitLabel(label11);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(21, context.var(d.e));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
            methodVisitor.visitVarInsn(58, context.var("list_item"));
            Label label13 = new Label();
            Label label14 = new Label();
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label14);
            methodVisitor.visitVarInsn(25, context.var("out"));
            label = label5;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
            methodVisitor.visitLabel(label14);
            Label label15 = new Label();
            Label label16 = new Label();
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                label2 = label7;
                label3 = label12;
                label4 = label9;
            } else {
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                label2 = label7;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label16);
                _getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                Label label17 = new Label();
                Label label18 = new Label();
                if (context.writeDirect) {
                    String str4 = (context.nonContext && context.writeDirect) ? "writeDirectNonContext" : "write";
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label17);
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    label4 = label9;
                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                        label3 = label12;
                    } else {
                        methodVisitor.visitVarInsn(21, context.var(d.e));
                        label3 = label12;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label18);
                    methodVisitor.visitLabel(label17);
                } else {
                    label3 = label12;
                    label4 = label9;
                }
                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context.var(d.e));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label18);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label15);
            }
            methodVisitor.visitLabel(label16);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context.var(d.e));
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                i = Opcodes.INVOKEVIRTUAL;
                str = JSONSerializer;
                str2 = "writeWithFieldName";
                str3 = "(Ljava/lang/Object;Ljava/lang/Object;)V";
            } else {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) collectionItemType)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                str = JSONSerializer;
                str2 = "writeWithFieldName";
                str3 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
                i = Opcodes.INVOKEVIRTUAL;
            }
            methodVisitor.visitMethodInsn(i, str, str2, str3);
            methodVisitor.visitLabel(label15);
            methodVisitor.visitLabel(label13);
            methodVisitor.visitIincInsn(context.var(d.e), 1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label10);
            methodVisitor.visitLabel(label3);
            i2 = 25;
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            String str5 = SerializeWriter;
            i3 = Opcodes.INVOKEVIRTUAL;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "write", "(I)V");
            i4 = 1;
        }
        methodVisitor.visitVarInsn(i2, i4);
        methodVisitor.visitMethodInsn(i3, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(label4);
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label);
    }

    private void _long(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(55, context.var("long", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(22, context.var("long", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _nameApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            _labelApply(methodVisitor, fieldInfo, context, label);
        }
        if (fieldInfo.field == null) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
    }

    private void _notWriteDefault(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        int i;
        String str;
        if (context.writeDirect) {
            return;
        }
        Label label2 = new Label();
        methodVisitor.visitVarInsn(21, context.var("notWriteDefaultValue"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
        Class<?> cls = fieldInfo.fieldClass;
        if (cls == Boolean.TYPE) {
            str = "boolean";
        } else if (cls == Byte.TYPE) {
            str = "byte";
        } else if (cls == Short.TYPE) {
            str = "short";
        } else {
            if (cls != Integer.TYPE) {
                if (cls == Long.TYPE) {
                    methodVisitor.visitVarInsn(22, context.var("long"));
                    methodVisitor.visitInsn(9);
                    i = Opcodes.LCMP;
                } else {
                    if (cls != Float.TYPE) {
                        if (cls == Double.TYPE) {
                            methodVisitor.visitVarInsn(24, context.var("double"));
                            methodVisitor.visitInsn(14);
                            i = Opcodes.DCMPL;
                        }
                        methodVisitor.visitLabel(label2);
                    }
                    methodVisitor.visitVarInsn(23, context.var("float"));
                    methodVisitor.visitInsn(11);
                    i = Opcodes.FCMPL;
                }
                methodVisitor.visitInsn(i);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
                methodVisitor.visitLabel(label2);
            }
            str = "int";
        }
        methodVisitor.visitVarInsn(21, context.var(str));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        methodVisitor.visitLabel(label2);
    }

    private void _object(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("object"));
        _filters(methodVisitor, fieldInfo, context, label);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitLabel(label);
    }

    private void _processKey(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        String str;
        String str2;
        String str3;
        Label label = new Label();
        methodVisitor.visitVarInsn(21, context.var("hasNameFilters"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            str = "java/lang/Byte";
            str2 = "valueOf";
            str3 = "(B)Ljava/lang/Byte;";
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            str = "java/lang/Short";
            str2 = "valueOf";
            str3 = "(S)Ljava/lang/Short;";
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            str = "java/lang/Integer";
            str2 = "valueOf";
            str3 = "(I)Ljava/lang/Integer;";
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            str = "java/lang/Character";
            str2 = "valueOf";
            str3 = "(C)Ljava/lang/Character;";
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            str = "java/lang/Long";
            str2 = "valueOf";
            str3 = "(J)Ljava/lang/Long;";
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            str = "java/lang/Float";
            str2 = "valueOf";
            str3 = "(F)Ljava/lang/Float;";
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            str = "java/lang/Double";
            str2 = "valueOf";
            str3 = "(D)Ljava/lang/Double;";
        } else {
            if (cls != Boolean.TYPE) {
                methodVisitor.visitVarInsn(25, context.var(cls == BigDecimal.class ? "decimal" : cls == String.class ? "string" : cls.isEnum() ? "enum" : List.class.isAssignableFrom(cls) ? "list" : "object"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
                methodVisitor.visitVarInsn(58, Context.fieldName);
                methodVisitor.visitLabel(label);
            }
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            str = "java/lang/Boolean";
            str2 = "valueOf";
            str3 = "(Z)Ljava/lang/Boolean;";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, str, str2, str3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        methodVisitor.visitVarInsn(58, Context.fieldName);
        methodVisitor.visitLabel(label);
    }

    private void _processValue(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        String str;
        String str2;
        String str3;
        Label label2 = new Label();
        Class<?> cls = fieldInfo.fieldClass;
        if (cls.isPrimitive()) {
            Label label3 = new Label();
            methodVisitor.visitVarInsn(21, context.var("checkValue"));
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label3);
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) BeanContext.class));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            str = "java/lang/Byte";
            str2 = "valueOf";
            str3 = "(B)Ljava/lang/Byte;";
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            str = "java/lang/Short";
            str2 = "valueOf";
            str3 = "(S)Ljava/lang/Short;";
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            str = "java/lang/Integer";
            str2 = "valueOf";
            str3 = "(I)Ljava/lang/Integer;";
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            str = "java/lang/Character";
            str2 = "valueOf";
            str3 = "(C)Ljava/lang/Character;";
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            str = "java/lang/Long";
            str2 = "valueOf";
            str3 = "(J)Ljava/lang/Long;";
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            str = "java/lang/Float";
            str2 = "valueOf";
            str3 = "(F)Ljava/lang/Float;";
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            str = "java/lang/Double";
            str2 = "valueOf";
            str3 = "(D)Ljava/lang/Double;";
        } else {
            if (cls != Boolean.TYPE) {
                methodVisitor.visitVarInsn(25, context.var(cls == BigDecimal.class ? "decimal" : cls == String.class ? "string" : cls.isEnum() ? "enum" : List.class.isAssignableFrom(cls) ? "list" : "object"));
                methodVisitor.visitVarInsn(58, Context.original);
                methodVisitor.visitVarInsn(25, Context.original);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processValue", "(L" + JSONSerializer + h.b + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
                methodVisitor.visitVarInsn(58, Context.processValue);
                methodVisitor.visitVarInsn(25, Context.original);
                methodVisitor.visitVarInsn(25, Context.processValue);
                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label2);
                _writeObject(methodVisitor, fieldInfo, context, label);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
                methodVisitor.visitLabel(label2);
            }
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            str = "java/lang/Boolean";
            str2 = "valueOf";
            str3 = "(Z)Ljava/lang/Boolean;";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, str, str2, str3);
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, Context.original);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processValue", "(L" + JSONSerializer + h.b + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label2);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label2);
    }

    private void _seperator(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _string(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        String str;
        String str2;
        Label label = new Label();
        if (fieldInfo.name.equals(context.beanInfo.typeKey)) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("string"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("string"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        methodVisitor.visitLabel(label2);
        if ("trim".equals(fieldInfo.format)) {
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;");
            methodVisitor.visitVarInsn(58, context.var("string"));
        }
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            str = SerializeWriter;
            str2 = "writeFieldValueStringWithDoubleQuoteCheck";
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            str = SerializeWriter;
            str2 = "writeFieldValue";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, str2, "(CLjava/lang/String;Ljava/lang/String;)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLabel(label);
    }

    private void _writeFieldName(MethodVisitor methodVisitor, Context context) {
        String str;
        String str2;
        String str3;
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            str = SerializeWriter;
            str2 = "writeFieldNameDirect";
            str3 = "(Ljava/lang/String;)V";
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            str = SerializeWriter;
            str2 = "writeFieldName";
            str3 = "(Ljava/lang/String;Z)V";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, str2, str3);
    }

    private void _writeObject(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        String str;
        String str2;
        String str3;
        String format = fieldInfo.getFormat();
        Class<?> cls = fieldInfo.fieldClass;
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.writeDirect ? context.var("object") : Context.processValue);
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context.var("object"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        Label label3 = new Label();
        Label label4 = new Label();
        if (Modifier.isPublic(cls.getModifiers()) && !ParserConfig.isPrimitive2(cls)) {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label4);
            _getFieldSer(context, methodVisitor, fieldInfo);
            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
            Label label5 = new Label();
            Label label6 = new Label();
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label5);
            boolean z = (fieldInfo.serialzeFeatures & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
            boolean z2 = (fieldInfo.serialzeFeatures & SerializerFeature.BeanToArray.mask) != 0;
            String str4 = (z || (context.nonContext && context.writeDirect)) ? z2 ? "writeAsArrayNonContext" : "writeDirectNonContext" : z2 ? "writeAsArray" : "write";
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label6);
            methodVisitor.visitLabel(label5);
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(label6);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        }
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.writeDirect ? context.var("object") : Context.processValue);
        if (format != null) {
            methodVisitor.visitLdcInsn(format);
            str = JSONSerializer;
            str2 = "writeWithFormat";
            str3 = "(Ljava/lang/Object;Ljava/lang/String;)V";
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                str = JSONSerializer;
                str2 = "writeWithFieldName";
                str3 = "(Ljava/lang/Object;Ljava/lang/Object;)V";
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) String.class)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                str = JSONSerializer;
                str2 = "writeWithFieldName";
                str3 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
            }
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, str2, str3);
        methodVisitor.visitLabel(label3);
        _seperator(methodVisitor, context);
    }

    private void generateWriteAsArray(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        ASMSerializerFactory aSMSerializerFactory;
        int i;
        char c;
        int i2;
        int i3;
        char c2;
        boolean z;
        int i4;
        int i5;
        int i6;
        Class<?> cls2;
        Label label;
        int i7;
        int i8;
        String str;
        String str2;
        String str3;
        int i9;
        int i10;
        int i11;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        ASMSerializerFactory aSMSerializerFactory2 = this;
        FieldInfo[] fieldInfoArr2 = fieldInfoArr;
        Label label2 = new Label();
        int i12 = 25;
        methodVisitor.visitVarInsn(25, 1);
        char c3 = 0;
        methodVisitor.visitVarInsn(25, 0);
        String str10 = JSONSerializer;
        String str11 = "(" + SerializeFilterable_desc + ")Z";
        int i13 = Opcodes.INVOKEVIRTUAL;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str10, "hasPropertyFilters", str11);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label2);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, 3);
        int i14 = 4;
        methodVisitor.visitVarInsn(25, 4);
        methodVisitor.visitVarInsn(21, 5);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "writeNoneASM", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("out"));
        int i15 = 16;
        methodVisitor.visitVarInsn(16, 91);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        int length = fieldInfoArr2.length;
        int i16 = 93;
        if (length == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            return;
        }
        int i17 = 0;
        while (i17 < length) {
            int i18 = i17 == length + (-1) ? i16 : 44;
            FieldInfo fieldInfo = fieldInfoArr2[i17];
            Class<?> cls3 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                aSMSerializerFactory = aSMSerializerFactory2;
                i = i12;
                c = c3;
                i2 = length;
                i3 = i17;
                c2 = 21;
                z = true;
                methodVisitor.visitVarInsn(i, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                String str12 = SerializeWriter;
                i4 = Opcodes.INVOKEVIRTUAL;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str12, "writeInt", "(I)V");
                i5 = 16;
                methodVisitor.visitVarInsn(16, i18);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            } else {
                if (cls3 == Long.TYPE) {
                    methodVisitor.visitVarInsn(i12, context.var("out"));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    str7 = SerializeWriter;
                    str8 = "writeLong";
                    str9 = "(J)V";
                } else if (cls3 == Float.TYPE) {
                    methodVisitor.visitVarInsn(i12, context.var("out"));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(i14);
                    str7 = SerializeWriter;
                    str8 = "writeFloat";
                    str9 = "(FZ)V";
                } else if (cls3 == Double.TYPE) {
                    methodVisitor.visitVarInsn(i12, context.var("out"));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    methodVisitor.visitInsn(i14);
                    str7 = SerializeWriter;
                    str8 = "writeDouble";
                    str9 = "(DZ)V";
                } else if (cls3 == Boolean.TYPE) {
                    methodVisitor.visitVarInsn(i12, context.var("out"));
                    methodVisitor.visitInsn(89);
                    aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    str7 = SerializeWriter;
                    str8 = "write";
                    str9 = "(Z)V";
                } else {
                    if (cls3 == Character.TYPE) {
                        methodVisitor.visitVarInsn(i12, context.var("out"));
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                    } else if (cls3 == String.class) {
                        methodVisitor.visitVarInsn(i12, context.var("out"));
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                    } else if (cls3.isEnum()) {
                        methodVisitor.visitVarInsn(i12, context.var("out"));
                        methodVisitor.visitInsn(89);
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitMethodInsn(i13, SerializeWriter, "writeEnum", "(Ljava/lang/Enum;)V");
                        methodVisitor.visitVarInsn(i15, i18);
                        str4 = SerializeWriter;
                        str5 = "write";
                        str6 = "(I)V";
                        methodVisitor.visitMethodInsn(i13, str4, str5, str6);
                        aSMSerializerFactory = aSMSerializerFactory2;
                        i5 = i15;
                        i = i12;
                        c = c3;
                        i2 = length;
                        i4 = i13;
                        i3 = i17;
                        c2 = 21;
                        z = true;
                    } else if (List.class.isAssignableFrom(cls3)) {
                        java.lang.reflect.Type type = fieldInfo.fieldType;
                        java.lang.reflect.Type type2 = type instanceof Class ? Object.class : ((ParameterizedType) type).getActualTypeArguments()[c3];
                        if (!(type2 instanceof Class) || (cls2 = (Class) type2) == Object.class) {
                            cls2 = null;
                        }
                        aSMSerializerFactory2._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitTypeInsn(192, "java/util/List");
                        methodVisitor.visitVarInsn(58, context.var("list"));
                        if (cls2 == String.class && context.writeDirect) {
                            methodVisitor.visitVarInsn(i12, context.var("out"));
                            methodVisitor.visitVarInsn(i12, context.var("list"));
                            methodVisitor.visitMethodInsn(i13, SerializeWriter, "write", "(Ljava/util/List;)V");
                            i9 = i12;
                            i2 = length;
                            i11 = i13;
                            i3 = i17;
                            i7 = i18;
                            i10 = 16;
                            c2 = 21;
                        } else {
                            Label label3 = new Label();
                            Label label4 = new Label();
                            methodVisitor.visitVarInsn(i12, context.var("list"));
                            methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label4);
                            methodVisitor.visitVarInsn(i12, context.var("out"));
                            methodVisitor.visitMethodInsn(i13, SerializeWriter, "writeNull", "()V");
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
                            methodVisitor.visitLabel(label4);
                            methodVisitor.visitVarInsn(25, context.var("list"));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
                            methodVisitor.visitVarInsn(54, context.var("size"));
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            methodVisitor.visitVarInsn(16, 91);
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
                            Label label5 = new Label();
                            Label label6 = new Label();
                            Label label7 = new Label();
                            methodVisitor.visitInsn(3);
                            methodVisitor.visitVarInsn(54, context.var(d.e));
                            methodVisitor.visitLabel(label5);
                            methodVisitor.visitVarInsn(21, context.var(d.e));
                            methodVisitor.visitVarInsn(21, context.var("size"));
                            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label7);
                            methodVisitor.visitVarInsn(21, context.var(d.e));
                            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label6);
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            methodVisitor.visitVarInsn(16, 44);
                            i2 = length;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
                            methodVisitor.visitLabel(label6);
                            methodVisitor.visitVarInsn(25, context.var("list"));
                            methodVisitor.visitVarInsn(21, context.var(d.e));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                            methodVisitor.visitVarInsn(58, context.var("list_item"));
                            Label label8 = new Label();
                            Label label9 = new Label();
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label9);
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            i3 = i17;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label8);
                            methodVisitor.visitLabel(label9);
                            Label label10 = new Label();
                            Label label11 = new Label();
                            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                                label = label3;
                                i7 = i18;
                            } else {
                                methodVisitor.visitVarInsn(25, context.var("list_item"));
                                i7 = i18;
                                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label11);
                                aSMSerializerFactory2._getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                                Label label12 = new Label();
                                Label label13 = new Label();
                                if (context.writeDirect) {
                                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                    methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label12);
                                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                                    methodVisitor.visitVarInsn(25, 1);
                                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                                    if (context.nonContext) {
                                        methodVisitor.visitInsn(1);
                                        label = label3;
                                    } else {
                                        methodVisitor.visitVarInsn(21, context.var(d.e));
                                        label = label3;
                                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                    }
                                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
                                    methodVisitor.visitLabel(label12);
                                } else {
                                    label = label3;
                                }
                                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                                methodVisitor.visitVarInsn(25, 1);
                                methodVisitor.visitVarInsn(25, context.var("list_item"));
                                if (context.nonContext) {
                                    methodVisitor.visitInsn(1);
                                } else {
                                    methodVisitor.visitVarInsn(21, context.var(d.e));
                                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                }
                                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                                methodVisitor.visitLabel(label13);
                                methodVisitor.visitJumpInsn(Opcodes.GOTO, label10);
                            }
                            methodVisitor.visitLabel(label11);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                methodVisitor.visitInsn(1);
                                c2 = 21;
                            } else {
                                c2 = 21;
                                methodVisitor.visitVarInsn(21, context.var(d.e));
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                                i8 = Opcodes.INVOKEVIRTUAL;
                                str = JSONSerializer;
                                str2 = "writeWithFieldName";
                                str3 = "(Ljava/lang/Object;Ljava/lang/Object;)V";
                            } else {
                                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) type2)));
                                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                str = JSONSerializer;
                                str2 = "writeWithFieldName";
                                str3 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
                                i8 = Opcodes.INVOKEVIRTUAL;
                            }
                            methodVisitor.visitMethodInsn(i8, str, str2, str3);
                            methodVisitor.visitLabel(label10);
                            methodVisitor.visitLabel(label8);
                            methodVisitor.visitIincInsn(context.var(d.e), 1);
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
                            methodVisitor.visitLabel(label7);
                            i9 = 25;
                            methodVisitor.visitVarInsn(25, context.var("out"));
                            i10 = 16;
                            methodVisitor.visitVarInsn(16, 93);
                            String str13 = SerializeWriter;
                            i11 = Opcodes.INVOKEVIRTUAL;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "write", "(I)V");
                            methodVisitor.visitLabel(label);
                        }
                        methodVisitor.visitVarInsn(i9, context.var("out"));
                        methodVisitor.visitVarInsn(i10, i7);
                        methodVisitor.visitMethodInsn(i11, SerializeWriter, "write", "(I)V");
                        i4 = i11;
                        i5 = 16;
                        i = 25;
                        aSMSerializerFactory = this;
                        c = 0;
                        z = true;
                    } else {
                        i2 = length;
                        i3 = i17;
                        c2 = 21;
                        Label label14 = new Label();
                        Label label15 = new Label();
                        aSMSerializerFactory = this;
                        aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                        methodVisitor.visitInsn(89);
                        methodVisitor.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label15);
                        methodVisitor.visitVarInsn(25, context.var("out"));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label14);
                        methodVisitor.visitLabel(label15);
                        Label label16 = new Label();
                        Label label17 = new Label();
                        methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label17);
                        aSMSerializerFactory._getFieldSer(context, methodVisitor, fieldInfo);
                        methodVisitor.visitVarInsn(58, context.var("fied_ser"));
                        Label label18 = new Label();
                        Label label19 = new Label();
                        if (context.writeDirect && Modifier.isPublic(cls3.getModifiers())) {
                            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label18);
                            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                            methodVisitor.visitVarInsn(25, Context.fieldName);
                            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label19);
                            methodVisitor.visitLabel(label18);
                        }
                        methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        methodVisitor.visitVarInsn(25, Context.fieldName);
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                        methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        methodVisitor.visitLabel(label19);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label16);
                        methodVisitor.visitLabel(label17);
                        String format = fieldInfo.getFormat();
                        z = true;
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        if (format != null) {
                            methodVisitor.visitLdcInsn(format);
                            String str14 = JSONSerializer;
                            i6 = Opcodes.INVOKEVIRTUAL;
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str14, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                        } else {
                            methodVisitor.visitVarInsn(25, Context.fieldName);
                            if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                                i6 = 182;
                            } else {
                                c = 0;
                                methodVisitor.visitVarInsn(25, 0);
                                methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                String str15 = JSONSerializer;
                                i6 = Opcodes.INVOKEVIRTUAL;
                                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str15, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                                methodVisitor.visitLabel(label16);
                                methodVisitor.visitLabel(label14);
                                i = 25;
                                methodVisitor.visitVarInsn(25, context.var("out"));
                                methodVisitor.visitVarInsn(16, i18);
                                methodVisitor.visitMethodInsn(i6, SerializeWriter, "write", "(I)V");
                                i4 = i6;
                                i5 = 16;
                            }
                        }
                        c = 0;
                        methodVisitor.visitLabel(label16);
                        methodVisitor.visitLabel(label14);
                        i = 25;
                        methodVisitor.visitVarInsn(25, context.var("out"));
                        methodVisitor.visitVarInsn(16, i18);
                        methodVisitor.visitMethodInsn(i6, SerializeWriter, "write", "(I)V");
                        i4 = i6;
                        i5 = 16;
                    }
                    methodVisitor.visitVarInsn(i15, i18);
                    str4 = SerializeWriter;
                    str5 = "writeString";
                    str6 = "(Ljava/lang/String;C)V";
                    methodVisitor.visitMethodInsn(i13, str4, str5, str6);
                    aSMSerializerFactory = aSMSerializerFactory2;
                    i5 = i15;
                    i = i12;
                    c = c3;
                    i2 = length;
                    i4 = i13;
                    i3 = i17;
                    c2 = 21;
                    z = true;
                }
                methodVisitor.visitMethodInsn(i13, str7, str8, str9);
                methodVisitor.visitVarInsn(i15, i18);
                methodVisitor.visitMethodInsn(i13, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory2;
                i5 = i15;
                i = i12;
                c = c3;
                i2 = length;
                i4 = i13;
                i3 = i17;
                c2 = 21;
                z = true;
            }
            i17 = i3 + 1;
            i15 = i5;
            aSMSerializerFactory2 = aSMSerializerFactory;
            i13 = i4;
            length = i2;
            i14 = 4;
            i16 = 93;
            c3 = c;
            i12 = i;
            fieldInfoArr2 = fieldInfoArr;
        }
    }

    private void generateWriteMethod(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        int i;
        int i2;
        int i3;
        int iVar;
        char c;
        ASMSerializerFactory aSMSerializerFactory;
        Class<?> cls2;
        MethodVisitor methodVisitor2;
        Context context2;
        Label label = new Label();
        int length = fieldInfoArr.length;
        int i4 = 25;
        if (!context.writeDirect) {
            Label label2 = new Label();
            Label label3 = new Label();
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label3);
            boolean z = false;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                if (fieldInfo.method != null) {
                    z = true;
                }
            }
            if (z) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
            } else {
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            }
            methodVisitor.visitLabel(label3);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label2);
        }
        if (!context.nonContext) {
            Label label4 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label4);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label4);
        }
        String str = context.writeDirect ? context.nonContext ? "writeAsArrayNonContext" : "writeAsArray" : "writeAsArrayNormal";
        if ((context.beanInfo.features & SerializerFeature.BeanToArray.mask) == 0) {
            Label label5 = new Label();
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.BeanToArray.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label5);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label5);
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
        }
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getContext", "()" + SerialContext_desc);
            methodVisitor.visitVarInsn(58, context.var("parent"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("parent"));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean z2 = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        int i5 = 16;
        if (z2 || !context.writeDirect) {
            Label label6 = new Label();
            Label label7 = new Label();
            Label label8 = new Label();
            if (!z2) {
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, 4);
                methodVisitor.visitVarInsn(25, 2);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label7);
            }
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label7);
            methodVisitor.visitLabel(label8);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                methodVisitor.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                methodVisitor.visitInsn(1);
            }
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            i5 = 16;
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label6);
            methodVisitor.visitLabel(label7);
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitLabel(label6);
        } else {
            methodVisitor.visitVarInsn(16, 123);
        }
        methodVisitor.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            _before(methodVisitor, context);
        }
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor.visitVarInsn(54, context.var("notWriteDefaultValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "checkValue", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("checkValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "hasNameFilters", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("hasNameFilters"));
        }
        int i6 = 0;
        while (i6 < length) {
            FieldInfo fieldInfo2 = fieldInfoArr[i6];
            Class<?> cls3 = fieldInfo2.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo2.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                i = i6;
                i2 = i5;
                i3 = i4;
                iVar = context.var(cls3.getName());
                c = 'I';
            } else {
                if (cls3 == Long.TYPE) {
                    _long(cls, methodVisitor, fieldInfo2, context);
                } else if (cls3 == Float.TYPE) {
                    _float(cls, methodVisitor, fieldInfo2, context);
                } else if (cls3 == Double.TYPE) {
                    _double(cls, methodVisitor, fieldInfo2, context);
                } else if (cls3 == Boolean.TYPE) {
                    aSMSerializerFactory = this;
                    cls2 = cls;
                    methodVisitor2 = methodVisitor;
                    i = i6;
                    context2 = context;
                    i2 = i5;
                    iVar = context.var("boolean");
                    i3 = i4;
                    c = 'Z';
                    aSMSerializerFactory._int(cls2, methodVisitor2, fieldInfo2, context2, iVar, c);
                    i6 = i + 1;
                    i4 = i3;
                    i5 = i2;
                } else {
                    i = i6;
                    i2 = i5;
                    i3 = i4;
                    if (cls3 == Character.TYPE) {
                        iVar = context.var("char");
                        c = 'C';
                    } else {
                        if (cls3 == String.class) {
                            _string(cls, methodVisitor, fieldInfo2, context);
                        } else if (cls3 == BigDecimal.class) {
                            _decimal(cls, methodVisitor, fieldInfo2, context);
                        } else if (List.class.isAssignableFrom(cls3)) {
                            _list(cls, methodVisitor, fieldInfo2, context);
                        } else if (cls3.isEnum()) {
                            _enum(cls, methodVisitor, fieldInfo2, context);
                        } else {
                            _object(cls, methodVisitor, fieldInfo2, context);
                        }
                        i6 = i + 1;
                        i4 = i3;
                        i5 = i2;
                    }
                }
                i = i6;
                i2 = i5;
                i3 = i4;
                i6 = i + 1;
                i4 = i3;
                i5 = i2;
            }
            aSMSerializerFactory = this;
            cls2 = cls;
            methodVisitor2 = methodVisitor;
            context2 = context;
            aSMSerializerFactory._int(cls2, methodVisitor2, fieldInfo2, context2, iVar, c);
            i6 = i + 1;
            i4 = i3;
            i5 = i2;
        }
        int i7 = i5;
        int i8 = i4;
        if (!context.writeDirect) {
            _after(methodVisitor, context);
        }
        Label label9 = new Label();
        Label label10 = new Label();
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitIntInsn(i7, 123);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label9);
        methodVisitor.visitVarInsn(i8, context.var("out"));
        methodVisitor.visitVarInsn(i7, 123);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel(label9);
        methodVisitor.visitVarInsn(i8, context.var("out"));
        methodVisitor.visitVarInsn(i7, 125);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel(label10);
        methodVisitor.visitLabel(label);
        if (context.nonContext) {
            return;
        }
        methodVisitor.visitVarInsn(i8, 1);
        methodVisitor.visitVarInsn(i8, context.var("parent"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) throws Exception {
        String str;
        String str2;
        boolean z;
        int i;
        String str3;
        boolean z2;
        boolean z3;
        String str4;
        boolean z4;
        boolean z5;
        String str5;
        JSONType jSONType;
        char c;
        ClassWriter classWriter;
        int i2;
        Class<?> cls = serializeBeanInfo.beanType;
        if (cls.isPrimitive()) {
            throw new JSONException("unsupportd class " + cls.getName());
        }
        JSONType jSONType2 = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
        FieldInfo[] fieldInfoArr = serializeBeanInfo.fields;
        for (FieldInfo fieldInfo : fieldInfoArr) {
            if (fieldInfo.field == null && fieldInfo.method != null && fieldInfo.method.getDeclaringClass().isInterface()) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
        }
        FieldInfo[] fieldInfoArr2 = serializeBeanInfo.sortedFields;
        boolean z6 = serializeBeanInfo.sortedFields == serializeBeanInfo.fields;
        if (fieldInfoArr2.length > 256) {
            return new JavaBeanSerializer(serializeBeanInfo);
        }
        for (FieldInfo fieldInfo2 : fieldInfoArr2) {
            if (!ASMUtils.checkName(fieldInfo2.getMember().getName())) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
        }
        String str6 = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + cls.getSimpleName();
        Package r2 = ASMSerializerFactory.class.getPackage();
        if (r2 != null) {
            String name = r2.getName();
            str = name + "." + str6;
            str2 = name.replace('.', '/') + "/" + str6;
        } else {
            str = str6;
            str2 = str;
        }
        ASMSerializerFactory.class.getPackage().getName();
        ClassWriter classWriter2 = new ClassWriter();
        classWriter2.visit(49, 33, str2, JavaBeanSerializer, new String[]{ObjectSerializer});
        int length = fieldInfoArr2.length;
        int i3 = 0;
        while (i3 < length) {
            FieldInfo fieldInfo3 = fieldInfoArr2[i3];
            if (fieldInfo3.fieldClass.isPrimitive() || fieldInfo3.fieldClass == String.class) {
                i2 = length;
            } else {
                i2 = length;
                new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
                if (List.class.isAssignableFrom(fieldInfo3.fieldClass)) {
                    new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                }
                new FieldWriter(classWriter2, 1, fieldInfo3.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
            }
            i3++;
            length = i2;
        }
        MethodWriter methodWriter = new MethodWriter(classWriter2, 1, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V", null, null);
        int i4 = 25;
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V");
        int i5 = 0;
        while (i5 < fieldInfoArr2.length) {
            FieldInfo fieldInfo4 = fieldInfoArr2[i5];
            if (fieldInfo4.fieldClass.isPrimitive()) {
                classWriter = classWriter2;
            } else if (fieldInfo4.fieldClass == String.class) {
                classWriter = classWriter2;
            } else {
                methodWriter.visitVarInsn(i4, 0);
                if (fieldInfo4.method != null) {
                    methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo4.declaringClass)));
                    methodWriter.visitLdcInsn(fieldInfo4.method.getName());
                    classWriter = classWriter2;
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                } else {
                    classWriter = classWriter2;
                    methodWriter.visitVarInsn(i4, 0);
                    methodWriter.visitLdcInsn(Integer.valueOf(i5));
                    methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                }
                methodWriter.visitFieldInsn(Opcodes.PUTFIELD, str2, fieldInfo4.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            }
            i5++;
            classWriter2 = classWriter;
            i4 = 25;
        }
        ClassWriter classWriter3 = classWriter2;
        int i6 = Opcodes.RETURN;
        methodWriter.visitInsn(Opcodes.RETURN);
        methodWriter.visitMaxs(4, 4);
        methodWriter.visitEnd();
        if (jSONType2 != null) {
            for (SerializerFeature serializerFeature : jSONType2.serialzeFeatures()) {
                if (serializerFeature == SerializerFeature.DisableCircularReferenceDetect) {
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        int i7 = 0;
        while (i7 < 3) {
            if (i7 == 0) {
                str4 = "write";
                z5 = z;
                z4 = true;
            } else if (i7 == 1) {
                str4 = "writeNormal";
                z5 = z;
                z4 = false;
            } else {
                str4 = "writeDirectNonContext";
                z4 = true;
                z5 = true;
            }
            String str7 = str;
            String str8 = str2;
            Context context = new Context(fieldInfoArr2, serializeBeanInfo, str2, z4, z5);
            int i8 = i7;
            MethodWriter methodWriter2 = new MethodWriter(classWriter3, 1, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            Label label = new Label();
            methodWriter2.visitVarInsn(25, 2);
            methodWriter2.visitJumpInsn(Opcodes.IFNONNULL, label);
            methodWriter2.visitVarInsn(25, 1);
            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeNull", "()V");
            methodWriter2.visitInsn(i6);
            methodWriter2.visitLabel(label);
            methodWriter2.visitVarInsn(25, 1);
            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
            methodWriter2.visitVarInsn(58, context.var("out"));
            if (z6 || context.writeDirect || !(jSONType2 == null || jSONType2.alphabetic())) {
                str5 = str8;
            } else {
                Label label2 = new Label();
                methodWriter2.visitVarInsn(25, context.var("out"));
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isSortField", "()Z");
                methodWriter2.visitJumpInsn(Opcodes.IFNE, label2);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                str5 = str8;
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodWriter2.visitInsn(Opcodes.RETURN);
                methodWriter2.visitLabel(label2);
            }
            if (!context.writeDirect || z5) {
                jSONType = jSONType2;
                c = 4;
            } else {
                Label label3 = new Label();
                Label label4 = new Label();
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                String str9 = JavaBeanSerializer;
                StringBuilder sb = new StringBuilder();
                jSONType = jSONType2;
                sb.append("(L");
                sb.append(JSONSerializer);
                sb.append(";)Z");
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "writeDirect", sb.toString());
                methodWriter2.visitJumpInsn(Opcodes.IFNE, label4);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeNormal", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodWriter2.visitInsn(Opcodes.RETURN);
                methodWriter2.visitLabel(label4);
                methodWriter2.visitVarInsn(25, context.var("out"));
                methodWriter2.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                methodWriter2.visitJumpInsn(Opcodes.IFEQ, label3);
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitVarInsn(25, 3);
                c = 4;
                methodWriter2.visitVarInsn(25, 4);
                methodWriter2.visitVarInsn(21, 5);
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeDirectNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodWriter2.visitInsn(Opcodes.RETURN);
                methodWriter2.visitLabel(label3);
            }
            methodWriter2.visitVarInsn(25, 2);
            methodWriter2.visitTypeInsn(192, ASMUtils.type(cls));
            methodWriter2.visitVarInsn(58, context.var("entity"));
            generateWriteMethod(cls, methodWriter2, fieldInfoArr2, context);
            methodWriter2.visitInsn(Opcodes.RETURN);
            methodWriter2.visitMaxs(7, context.variantIndex + 2);
            methodWriter2.visitEnd();
            i7 = i8 + 1;
            str2 = str5;
            str = str7;
            jSONType2 = jSONType;
            i6 = Opcodes.RETURN;
        }
        String str10 = str;
        String str11 = str2;
        if (z6) {
            i = 3;
        } else {
            Context context2 = new Context(fieldInfoArr2, serializeBeanInfo, str11, false, z);
            i = 3;
            MethodWriter methodWriter3 = new MethodWriter(classWriter3, 1, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            methodWriter3.visitVarInsn(25, 1);
            methodWriter3.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
            methodWriter3.visitVarInsn(58, context2.var("out"));
            methodWriter3.visitVarInsn(25, 2);
            methodWriter3.visitTypeInsn(192, ASMUtils.type(cls));
            methodWriter3.visitVarInsn(58, context2.var("entity"));
            generateWriteMethod(cls, methodWriter3, fieldInfoArr, context2);
            methodWriter3.visitInsn(Opcodes.RETURN);
            methodWriter3.visitMaxs(7, context2.variantIndex + 2);
            methodWriter3.visitEnd();
        }
        int i9 = 0;
        while (i9 < i) {
            if (i9 == 0) {
                str3 = "writeAsArray";
                z3 = z;
                z2 = true;
            } else if (i9 == 1) {
                str3 = "writeAsArrayNormal";
                z3 = z;
                z2 = false;
            } else {
                str3 = "writeAsArrayNonContext";
                z2 = true;
                z3 = true;
            }
            Context context3 = new Context(fieldInfoArr2, serializeBeanInfo, str11, z2, z3);
            MethodWriter methodWriter4 = new MethodWriter(classWriter3, 1, str3, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            methodWriter4.visitVarInsn(25, 1);
            methodWriter4.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
            methodWriter4.visitVarInsn(58, context3.var("out"));
            methodWriter4.visitVarInsn(25, 2);
            methodWriter4.visitTypeInsn(192, ASMUtils.type(cls));
            methodWriter4.visitVarInsn(58, context3.var("entity"));
            generateWriteAsArray(cls, methodWriter4, fieldInfoArr2, context3);
            methodWriter4.visitInsn(Opcodes.RETURN);
            methodWriter4.visitMaxs(7, context3.variantIndex + 2);
            methodWriter4.visitEnd();
            i9++;
            i = i;
        }
        byte[] byteArray = classWriter3.toByteArray();
        return (JavaBeanSerializer) this.classLoader.defineClassPublic(str10, byteArray, 0, byteArray.length).getConstructor(SerializeBeanInfo.class).newInstance(serializeBeanInfo);
    }
}
