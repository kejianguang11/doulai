package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.d.d;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class ASMDeserializerFactory implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    static class Context {
        static final int fieldName = 3;
        static final int parser = 1;
        static final int type = 2;
        private final JavaBeanInfo beanInfo;
        private final String className;
        private final Class<?> clazz;
        private FieldInfo[] fieldInfoList;
        private int variantIndex;
        private final Map<String, Integer> variants = new HashMap();

        public Context(String str, ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, int i) {
            this.variantIndex = -1;
            this.className = str;
            this.clazz = javaBeanInfo.clazz;
            this.variantIndex = i;
            this.beanInfo = javaBeanInfo;
            this.fieldInfoList = javaBeanInfo.fields;
        }

        public Class<?> getInstClass() {
            Class<?> cls = this.beanInfo.builderClass;
            return cls == null ? this.clazz : cls;
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

    public ASMDeserializerFactory(ClassLoader classLoader) {
        this.classLoader = classLoader instanceof ASMClassLoader ? (ASMClassLoader) classLoader : new ASMClassLoader(classLoader);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor) {
        _batchSet(context, methodVisitor, true);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor, boolean z) {
        int length = context.fieldInfoList.length;
        for (int i = 0; i < length; i++) {
            Label label = new Label();
            if (z) {
                _isFlag(methodVisitor, context, i, label);
            }
            _loadAndSet(context, methodVisitor, context.fieldInfoList[i]);
            if (z) {
                methodVisitor.visitLabel(label);
            }
        }
    }

    private void _createInstance(ClassWriter classWriter, Context context) {
        if (Modifier.isPublic(context.beanInfo.defaultConstructor.getModifiers())) {
            MethodWriter methodWriter = new MethodWriter(classWriter, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
            methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            methodWriter.visitInsn(89);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(context.getInstClass()), "<init>", "()V");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitMaxs(3, 3);
            methodWriter.visitEnd();
        }
    }

    private void _createInstance(Context context, MethodVisitor methodVisitor) {
        Constructor<?> constructor = context.beanInfo.defaultConstructor;
        if (Modifier.isPublic(constructor.getModifiers())) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(constructor.getDeclaringClass()), "<init>", "()V");
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(JavaBeanDeserializer.class), "clazz", "Ljava/lang/Class;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(192, ASMUtils.type(context.getInstClass()));
        }
        methodVisitor.visitVarInsn(58, context.var("instance"));
    }

    private void _deserObject(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls, int i) {
        _getFieldDeser(context, methodVisitor, fieldInfo);
        Label label = new Label();
        Label label2 = new Label();
        if ((fieldInfo.parserFeatures & Feature.SupportArrayToBean.mask) != 0) {
            methodVisitor.visitInsn(89);
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            methodVisitor.visitTypeInsn(192, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitVarInsn(25, 1);
            if (fieldInfo.fieldType instanceof Class) {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            } else {
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitLdcInsn(Integer.valueOf(i));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.parserFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label);
        }
        methodVisitor.visitVarInsn(25, 1);
        if (fieldInfo.fieldType instanceof Class) {
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitLabel(label2);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor methodVisitor, Label label) {
        methodVisitor.visitIntInsn(21, context.var("matchedCount"));
        methodVisitor.visitJumpInsn(Opcodes.IFLE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _quickNextTokenComma(context, methodVisitor);
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x0d98  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0dc6  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0e14 A[PHI: r12 r19
      0x0e14: PHI (r12v6 com.alibaba.fastjson.asm.MethodWriter) = (r12v5 com.alibaba.fastjson.asm.MethodWriter), (r12v12 com.alibaba.fastjson.asm.MethodWriter) binds: [B:143:0x0e12, B:140:0x0df6] A[DONT_GENERATE, DONT_INLINE]
      0x0e14: PHI (r19v2 int) = (r19v1 int), (r19v3 int) binds: [B:143:0x0e12, B:140:0x0df6] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void _deserialze(ClassWriter classWriter, Context context) {
        int i;
        MethodWriter methodWriter;
        char c;
        char c2;
        StringBuilder sb;
        Label label;
        StringBuilder sb2;
        int i2;
        int iVar;
        StringBuilder sb3;
        StringBuilder sb4;
        Label label2;
        int i3;
        StringBuilder sb5;
        int iVar2;
        if (context.fieldInfoList.length == 0) {
            return;
        }
        for (FieldInfo fieldInfo : context.fieldInfoList) {
            Class<?> cls = fieldInfo.fieldClass;
            java.lang.reflect.Type type = fieldInfo.fieldType;
            if (cls == Character.TYPE) {
                return;
            }
            if (Collection.class.isAssignableFrom(cls) && (!(type instanceof ParameterizedType) || !(((ParameterizedType) type).getActualTypeArguments()[0] instanceof Class))) {
                return;
            }
        }
        JavaBeanInfo javaBeanInfo = context.beanInfo;
        context.fieldInfoList = javaBeanInfo.sortedFields;
        MethodWriter methodWriter2 = new MethodWriter(classWriter, 1, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        defineVarLexer(context, methodWriter2);
        Label label7 = new Label();
        int i4 = 25;
        methodWriter2.visitVarInsn(25, context.var("lexer"));
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodWriter2.visitLdcInsn(14);
        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label7);
        if ((javaBeanInfo.parserFeatures & Feature.SupportArrayToBean.mask) == 0) {
            methodWriter2.visitVarInsn(25, context.var("lexer"));
            methodWriter2.visitVarInsn(21, 4);
            methodWriter2.visitLdcInsn(Integer.valueOf(Feature.SupportArrayToBean.mask));
            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(II)Z");
            methodWriter2.visitJumpInsn(Opcodes.IFEQ, label7);
        }
        methodWriter2.visitVarInsn(25, 0);
        int i5 = 1;
        methodWriter2.visitVarInsn(25, 1);
        methodWriter2.visitVarInsn(25, 2);
        methodWriter2.visitVarInsn(25, 3);
        methodWriter2.visitInsn(1);
        methodWriter2.visitMethodInsn(Opcodes.INVOKESPECIAL, context.className, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        methodWriter2.visitInsn(Opcodes.ARETURN);
        methodWriter2.visitLabel(label7);
        methodWriter2.visitVarInsn(25, context.var("lexer"));
        methodWriter2.visitLdcInsn(Integer.valueOf(Feature.SortFeidFastMatch.mask));
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
        methodWriter2.visitJumpInsn(Opcodes.IFEQ, label4);
        methodWriter2.visitVarInsn(25, context.var("lexer"));
        methodWriter2.visitLdcInsn(context.clazz.getName());
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanType", "(Ljava/lang/String;)I");
        methodWriter2.visitLdcInsn(-1);
        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label4);
        methodWriter2.visitVarInsn(25, 1);
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodWriter2.visitVarInsn(58, context.var("mark_context"));
        methodWriter2.visitInsn(3);
        methodWriter2.visitVarInsn(54, context.var("matchedCount"));
        _createInstance(context, methodWriter2);
        methodWriter2.visitVarInsn(25, 1);
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodWriter2.visitVarInsn(58, context.var("context"));
        methodWriter2.visitVarInsn(25, 1);
        methodWriter2.visitVarInsn(25, context.var("context"));
        methodWriter2.visitVarInsn(25, context.var("instance"));
        methodWriter2.visitVarInsn(25, 3);
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodWriter2.visitVarInsn(58, context.var("childContext"));
        methodWriter2.visitVarInsn(25, context.var("lexer"));
        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
        methodWriter2.visitLdcInsn(4);
        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label5);
        int i6 = 3;
        methodWriter2.visitInsn(3);
        methodWriter2.visitIntInsn(54, context.var("matchStat"));
        int length = context.fieldInfoList.length;
        int i7 = 0;
        while (i7 < length) {
            methodWriter2.visitInsn(i6);
            methodWriter2.visitVarInsn(54, context.var("_asm_flag_" + (i7 / 32)));
            i7 += 32;
            i6 = 3;
        }
        methodWriter2.visitVarInsn(25, context.var("lexer"));
        methodWriter2.visitLdcInsn(Integer.valueOf(Feature.InitStringFieldAsEmpty.mask));
        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
        methodWriter2.visitIntInsn(54, context.var("initStringFieldAsEmpty"));
        int i8 = 0;
        while (i8 < length) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i8];
            Class<?> cls2 = fieldInfo2.fieldClass;
            if (cls2 == Boolean.TYPE || cls2 == Byte.TYPE || cls2 == Short.TYPE || cls2 == Integer.TYPE) {
                label2 = label4;
                methodWriter2.visitInsn(3);
                methodWriter2.visitVarInsn(54, context.var(fieldInfo2.name + "_asm"));
            } else {
                if (cls2 == Long.TYPE) {
                    methodWriter2.visitInsn(9);
                    i3 = 55;
                    sb5 = new StringBuilder();
                } else if (cls2 == Float.TYPE) {
                    methodWriter2.visitInsn(11);
                    i3 = 56;
                    iVar2 = context.var(fieldInfo2.name + "_asm");
                    methodWriter2.visitVarInsn(i3, iVar2);
                    label2 = label4;
                } else if (cls2 == Double.TYPE) {
                    methodWriter2.visitInsn(14);
                    i3 = 57;
                    sb5 = new StringBuilder();
                } else {
                    if (cls2 == String.class) {
                        Label label8 = new Label();
                        Label label9 = new Label();
                        methodWriter2.visitVarInsn(21, context.var("initStringFieldAsEmpty"));
                        methodWriter2.visitJumpInsn(Opcodes.IFEQ, label9);
                        _setFlag(methodWriter2, context, i8);
                        methodWriter2.visitVarInsn(i4, context.var("lexer"));
                        label2 = label4;
                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "stringDefaultValue", "()Ljava/lang/String;");
                        methodWriter2.visitJumpInsn(Opcodes.GOTO, label8);
                        methodWriter2.visitLabel(label9);
                        methodWriter2.visitInsn(1);
                        methodWriter2.visitLabel(label8);
                    } else {
                        label2 = label4;
                        methodWriter2.visitInsn(i5);
                    }
                    methodWriter2.visitTypeInsn(192, ASMUtils.type(cls2));
                    methodWriter2.visitVarInsn(58, context.var(fieldInfo2.name + "_asm"));
                }
                sb5.append(fieldInfo2.name);
                sb5.append("_asm");
                iVar2 = context.var(sb5.toString(), 2);
                methodWriter2.visitVarInsn(i3, iVar2);
                label2 = label4;
            }
            i8++;
            label4 = label2;
            i5 = 1;
            i4 = 25;
        }
        Label label10 = label4;
        int i9 = 0;
        while (i9 < length) {
            FieldInfo fieldInfo3 = context.fieldInfoList[i9];
            Class<?> cls3 = fieldInfo3.fieldClass;
            java.lang.reflect.Type type2 = fieldInfo3.fieldType;
            Label label11 = new Label();
            if (cls3 == Boolean.TYPE) {
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBoolean", "([C)Z");
                sb4 = new StringBuilder();
            } else if (cls3 == Byte.TYPE) {
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitVarInsn(25, 0);
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                sb4 = new StringBuilder();
            } else {
                if (cls3 == Byte.class) {
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitVarInsn(25, 0);
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    label = new Label();
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter2.visitLdcInsn(5);
                    methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                    methodWriter2.visitInsn(1);
                    sb2 = new StringBuilder();
                } else {
                    if (cls3 == Short.TYPE) {
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitVarInsn(25, 0);
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        sb3 = new StringBuilder();
                    } else if (cls3 == Short.class) {
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitVarInsn(25, 0);
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                        methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                        label = new Label();
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter2.visitLdcInsn(5);
                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                        methodWriter2.visitInsn(1);
                        sb2 = new StringBuilder();
                    } else if (cls3 == Integer.TYPE) {
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitVarInsn(25, 0);
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        sb3 = new StringBuilder();
                    } else if (cls3 == Integer.class) {
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitVarInsn(25, 0);
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                        label = new Label();
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter2.visitLdcInsn(5);
                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                        methodWriter2.visitInsn(1);
                        sb2 = new StringBuilder();
                    } else {
                        if (cls3 == Long.TYPE) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                            i2 = 55;
                            iVar = context.var(fieldInfo3.name + "_asm", 2);
                        } else if (cls3 == Long.class) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                            methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                            label = new Label();
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitLdcInsn(5);
                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                            methodWriter2.visitInsn(1);
                            sb2 = new StringBuilder();
                        } else if (cls3 == Float.TYPE) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                            i2 = 56;
                            iVar = context.var(fieldInfo3.name + "_asm");
                        } else if (cls3 == Float.class) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                            methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                            label = new Label();
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitLdcInsn(5);
                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                            methodWriter2.visitInsn(1);
                            sb2 = new StringBuilder();
                        } else if (cls3 == Double.TYPE) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                            i2 = 57;
                            iVar = context.var(fieldInfo3.name + "_asm", 2);
                        } else if (cls3 == Double.class) {
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitVarInsn(25, 0);
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                            methodWriter2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                            methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                            label = new Label();
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitLdcInsn(5);
                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                            methodWriter2.visitInsn(1);
                            sb2 = new StringBuilder();
                        } else {
                            if (cls3 == String.class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldString", "([C)Ljava/lang/String;");
                                sb = new StringBuilder();
                            } else if (cls3 == Date.class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDate", "([C)Ljava/util/Date;");
                                sb = new StringBuilder();
                            } else if (cls3 == UUID.class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldUUID", "([C)Ljava/util/UUID;");
                                sb = new StringBuilder();
                            } else if (cls3 == BigDecimal.class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDecimal", "([C)Ljava/math/BigDecimal;");
                                sb = new StringBuilder();
                            } else if (cls3 == BigInteger.class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBigInteger", "([C)Ljava/math/BigInteger;");
                                sb = new StringBuilder();
                            } else if (cls3 == int[].class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldIntArray", "([C)[I");
                                sb = new StringBuilder();
                            } else if (cls3 == float[].class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray", "([C)[F");
                                sb = new StringBuilder();
                            } else if (cls3 == float[][].class) {
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray2", "([C)[[F");
                                sb = new StringBuilder();
                            } else if (cls3.isEnum()) {
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitVarInsn(25, context.var("lexer"));
                                methodWriter2.visitVarInsn(25, 0);
                                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                _getFieldDeser(context, methodWriter2, fieldInfo3);
                                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";[C" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + ")Ljava/lang/Enum;");
                                methodWriter2.visitTypeInsn(192, ASMUtils.type(cls3));
                                sb = new StringBuilder();
                            } else {
                                if (Collection.class.isAssignableFrom(cls3)) {
                                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                                    methodWriter2.visitVarInsn(25, 0);
                                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                                    Class<?> collectionItemClass = TypeUtils.getCollectionItemClass(type2);
                                    if (collectionItemClass == String.class) {
                                        methodWriter2.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                                        c = 182;
                                        methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.desc((Class<?>) Collection.class));
                                        c2 = ':';
                                        methodWriter2.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                        Label label12 = new Label();
                                        methodWriter2.visitJumpInsn(Opcodes.IFLE, label12);
                                        _setFlag(methodWriter2, context, i9);
                                        methodWriter2.visitLabel(label12);
                                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                        methodWriter2.visitInsn(89);
                                        methodWriter2.visitVarInsn(54, context.var("matchStat"));
                                        methodWriter2.visitLdcInsn(-1);
                                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
                                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                        methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
                                        methodWriter2.visitVarInsn(21, context.var("matchedCount"));
                                        methodWriter2.visitInsn(4);
                                        methodWriter2.visitInsn(96);
                                        methodWriter2.visitVarInsn(54, context.var("matchedCount"));
                                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                        methodWriter2.visitLdcInsn(4);
                                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
                                        methodWriter2.visitLabel(label11);
                                        if (i9 != length - 1) {
                                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                            methodWriter2.visitLdcInsn(4);
                                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
                                            i = length;
                                        } else {
                                            i = length;
                                        }
                                        methodWriter = methodWriter2;
                                    } else {
                                        i = length;
                                        methodWriter = methodWriter2;
                                        _deserialze_list_obj(context, methodWriter2, label3, fieldInfo3, cls3, collectionItemClass, i9);
                                        if (i9 == i - 1) {
                                            _deserialize_endCheck(context, methodWriter, label3);
                                        }
                                    }
                                } else {
                                    i = length;
                                    methodWriter = methodWriter2;
                                    _deserialze_obj(context, methodWriter, label3, fieldInfo3, cls3, i9);
                                    if (i9 == i - 1) {
                                    }
                                }
                                i9++;
                                methodWriter2 = methodWriter;
                                length = i;
                            }
                            sb.append(fieldInfo3.name);
                            sb.append("_asm");
                            methodWriter2.visitVarInsn(58, context.var(sb.toString()));
                            c2 = ':';
                            c = 182;
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            Label label122 = new Label();
                            methodWriter2.visitJumpInsn(Opcodes.IFLE, label122);
                            _setFlag(methodWriter2, context, i9);
                            methodWriter2.visitLabel(label122);
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitInsn(89);
                            methodWriter2.visitVarInsn(54, context.var("matchStat"));
                            methodWriter2.visitLdcInsn(-1);
                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
                            methodWriter2.visitVarInsn(21, context.var("matchedCount"));
                            methodWriter2.visitInsn(4);
                            methodWriter2.visitInsn(96);
                            methodWriter2.visitVarInsn(54, context.var("matchedCount"));
                            methodWriter2.visitVarInsn(25, context.var("lexer"));
                            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter2.visitLdcInsn(4);
                            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
                            methodWriter2.visitLabel(label11);
                            if (i9 != length - 1) {
                            }
                            methodWriter = methodWriter2;
                            i9++;
                            methodWriter2 = methodWriter;
                            length = i;
                        }
                        methodWriter2.visitVarInsn(i2, iVar);
                        c = 182;
                        c2 = ':';
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        Label label1222 = new Label();
                        methodWriter2.visitJumpInsn(Opcodes.IFLE, label1222);
                        _setFlag(methodWriter2, context, i9);
                        methodWriter2.visitLabel(label1222);
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter2.visitInsn(89);
                        methodWriter2.visitVarInsn(54, context.var("matchStat"));
                        methodWriter2.visitLdcInsn(-1);
                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
                        methodWriter2.visitVarInsn(21, context.var("matchedCount"));
                        methodWriter2.visitInsn(4);
                        methodWriter2.visitInsn(96);
                        methodWriter2.visitVarInsn(54, context.var("matchedCount"));
                        methodWriter2.visitVarInsn(25, context.var("lexer"));
                        methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter2.visitLdcInsn(4);
                        methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
                        methodWriter2.visitLabel(label11);
                        if (i9 != length - 1) {
                        }
                        methodWriter = methodWriter2;
                        i9++;
                        methodWriter2 = methodWriter;
                        length = i;
                    }
                    sb3.append(fieldInfo3.name);
                    sb3.append("_asm");
                    methodWriter2.visitVarInsn(54, context.var(sb3.toString()));
                    c = 182;
                    c2 = ':';
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    Label label12222 = new Label();
                    methodWriter2.visitJumpInsn(Opcodes.IFLE, label12222);
                    _setFlag(methodWriter2, context, i9);
                    methodWriter2.visitLabel(label12222);
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter2.visitInsn(89);
                    methodWriter2.visitVarInsn(54, context.var("matchStat"));
                    methodWriter2.visitLdcInsn(-1);
                    methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
                    methodWriter2.visitVarInsn(21, context.var("matchedCount"));
                    methodWriter2.visitInsn(4);
                    methodWriter2.visitInsn(96);
                    methodWriter2.visitVarInsn(54, context.var("matchedCount"));
                    methodWriter2.visitVarInsn(25, context.var("lexer"));
                    methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter2.visitLdcInsn(4);
                    methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
                    methodWriter2.visitLabel(label11);
                    if (i9 != length - 1) {
                    }
                    methodWriter = methodWriter2;
                    i9++;
                    methodWriter2 = methodWriter;
                    length = i;
                }
                sb2.append(fieldInfo3.name);
                sb2.append("_asm");
                methodWriter2.visitVarInsn(58, context.var(sb2.toString()));
                methodWriter2.visitLabel(label);
                c = 182;
                c2 = ':';
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                Label label122222 = new Label();
                methodWriter2.visitJumpInsn(Opcodes.IFLE, label122222);
                _setFlag(methodWriter2, context, i9);
                methodWriter2.visitLabel(label122222);
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter2.visitInsn(89);
                methodWriter2.visitVarInsn(54, context.var("matchStat"));
                methodWriter2.visitLdcInsn(-1);
                methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
                methodWriter2.visitVarInsn(21, context.var("matchedCount"));
                methodWriter2.visitInsn(4);
                methodWriter2.visitInsn(96);
                methodWriter2.visitVarInsn(54, context.var("matchedCount"));
                methodWriter2.visitVarInsn(25, context.var("lexer"));
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter2.visitLdcInsn(4);
                methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
                methodWriter2.visitLabel(label11);
                if (i9 != length - 1) {
                }
                methodWriter = methodWriter2;
                i9++;
                methodWriter2 = methodWriter;
                length = i;
            }
            sb4.append(fieldInfo3.name);
            sb4.append("_asm");
            methodWriter2.visitVarInsn(54, context.var(sb4.toString()));
            c = 182;
            c2 = ':';
            methodWriter2.visitVarInsn(25, context.var("lexer"));
            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
            Label label1222222 = new Label();
            methodWriter2.visitJumpInsn(Opcodes.IFLE, label1222222);
            _setFlag(methodWriter2, context, i9);
            methodWriter2.visitLabel(label1222222);
            methodWriter2.visitVarInsn(25, context.var("lexer"));
            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
            methodWriter2.visitInsn(89);
            methodWriter2.visitVarInsn(54, context.var("matchStat"));
            methodWriter2.visitLdcInsn(-1);
            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
            methodWriter2.visitVarInsn(25, context.var("lexer"));
            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
            methodWriter2.visitJumpInsn(Opcodes.IFLE, label11);
            methodWriter2.visitVarInsn(21, context.var("matchedCount"));
            methodWriter2.visitInsn(4);
            methodWriter2.visitInsn(96);
            methodWriter2.visitVarInsn(54, context.var("matchedCount"));
            methodWriter2.visitVarInsn(25, context.var("lexer"));
            methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
            methodWriter2.visitLdcInsn(4);
            methodWriter2.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
            methodWriter2.visitLabel(label11);
            if (i9 != length - 1) {
            }
            methodWriter = methodWriter2;
            i9++;
            methodWriter2 = methodWriter;
            length = i;
        }
        int i10 = length;
        MethodWriter methodWriter3 = methodWriter2;
        methodWriter3.visitLabel(label6);
        if (!context.clazz.isInterface() && !Modifier.isAbstract(context.clazz.getModifiers())) {
            _batchSet(context, methodWriter3);
        }
        methodWriter3.visitLabel(label5);
        _setContext(context, methodWriter3);
        methodWriter3.visitVarInsn(25, context.var("instance"));
        Method method = context.beanInfo.buildMethod;
        if (method != null) {
            methodWriter3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), method.getName(), "()" + ASMUtils.desc(method.getReturnType()));
        }
        methodWriter3.visitInsn(Opcodes.ARETURN);
        methodWriter3.visitLabel(label3);
        _batchSet(context, methodWriter3);
        methodWriter3.visitVarInsn(25, 0);
        methodWriter3.visitVarInsn(25, 1);
        methodWriter3.visitVarInsn(25, 2);
        methodWriter3.visitVarInsn(25, 3);
        methodWriter3.visitVarInsn(25, context.var("instance"));
        methodWriter3.visitVarInsn(21, 4);
        int i11 = i10 / 32;
        if (i10 != 0 && i10 % 32 != 0) {
            i11++;
        }
        if (i11 == 1) {
            methodWriter3.visitInsn(4);
        } else {
            methodWriter3.visitIntInsn(16, i11);
        }
        methodWriter3.visitIntInsn(Opcodes.NEWARRAY, 10);
        for (int i12 = 0; i12 < i11; i12++) {
            methodWriter3.visitInsn(89);
            if (i12 == 0) {
                methodWriter3.visitInsn(3);
            } else if (i12 == 1) {
                methodWriter3.visitInsn(4);
            } else {
                methodWriter3.visitIntInsn(16, i12);
            }
            methodWriter3.visitVarInsn(21, context.var("_asm_flag_" + i12));
            methodWriter3.visitInsn(79);
        }
        methodWriter3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "parseRest", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;I[I)Ljava/lang/Object;");
        methodWriter3.visitTypeInsn(192, ASMUtils.type(context.clazz));
        methodWriter3.visitInsn(Opcodes.ARETURN);
        methodWriter3.visitLabel(label10);
        methodWriter3.visitVarInsn(25, 0);
        methodWriter3.visitVarInsn(25, 1);
        methodWriter3.visitVarInsn(25, 2);
        methodWriter3.visitVarInsn(25, 3);
        methodWriter3.visitVarInsn(21, 4);
        methodWriter3.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
        methodWriter3.visitInsn(Opcodes.ARETURN);
        methodWriter3.visitMaxs(10, context.variantIndex);
        methodWriter3.visitEnd();
    }

    private void _deserialzeArrayMapping(ClassWriter classWriter, Context context) {
        FieldInfo[] fieldInfoArr;
        int iVar;
        int i;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        Label label;
        StringBuilder sb4;
        int i2;
        StringBuilder sb5;
        int iVar2;
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        defineVarLexer(context, methodWriter);
        _createInstance(context, methodWriter);
        FieldInfo[] fieldInfoArr2 = context.beanInfo.sortedFields;
        int length = fieldInfoArr2.length;
        int i3 = 0;
        while (i3 < length) {
            boolean z = i3 == length + (-1);
            int i4 = z ? 93 : 44;
            FieldInfo fieldInfo = fieldInfoArr2[i3];
            Class<?> cls = fieldInfo.fieldClass;
            java.lang.reflect.Type type = fieldInfo.fieldType;
            if (cls == Byte.TYPE || cls == Short.TYPE || cls == Integer.TYPE) {
                fieldInfoArr = fieldInfoArr2;
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(16, i4);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                iVar = context.var(fieldInfo.name + "_asm");
                i = 54;
            } else {
                if (cls == Byte.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i4);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    label = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                    methodWriter.visitInsn(1);
                    sb4 = new StringBuilder();
                } else if (cls == Short.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i4);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    label = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                    methodWriter.visitInsn(1);
                    sb4 = new StringBuilder();
                } else if (cls == Integer.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i4);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    label = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                    methodWriter.visitInsn(1);
                    sb4 = new StringBuilder();
                } else {
                    if (cls == Long.TYPE) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i4);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                        i2 = 55;
                        sb5 = new StringBuilder();
                    } else if (cls == Long.class) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i4);
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                        methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                        label = new Label();
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                        methodWriter.visitLdcInsn(5);
                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                        methodWriter.visitInsn(1);
                        sb4 = new StringBuilder();
                    } else {
                        if (cls == Boolean.TYPE) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanBoolean", "(C)Z");
                            sb3 = new StringBuilder();
                        } else if (cls == Float.TYPE) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                            i2 = 56;
                            iVar2 = context.var(fieldInfo.name + "_asm");
                            methodWriter.visitVarInsn(i2, iVar2);
                            fieldInfoArr = fieldInfoArr2;
                            i3++;
                            fieldInfoArr2 = fieldInfoArr;
                        } else if (cls == Float.class) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                            methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                            label = new Label();
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter.visitLdcInsn(5);
                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                            methodWriter.visitInsn(1);
                            sb4 = new StringBuilder();
                        } else if (cls == Double.TYPE) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                            i2 = 57;
                            sb5 = new StringBuilder();
                        } else if (cls == Double.class) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                            methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                            label = new Label();
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            methodWriter.visitLdcInsn(5);
                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                            methodWriter.visitInsn(1);
                            sb4 = new StringBuilder();
                        } else if (cls == Character.TYPE) {
                            methodWriter.visitVarInsn(25, context.var("lexer"));
                            methodWriter.visitVarInsn(16, i4);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                            methodWriter.visitInsn(3);
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C");
                            sb3 = new StringBuilder();
                        } else {
                            if (cls == String.class) {
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitVarInsn(16, i4);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                                sb2 = new StringBuilder();
                            } else if (cls == BigDecimal.class) {
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitVarInsn(16, i4);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDecimal", "(C)Ljava/math/BigDecimal;");
                                sb2 = new StringBuilder();
                            } else if (cls == Date.class) {
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitVarInsn(16, i4);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDate", "(C)Ljava/util/Date;");
                                sb2 = new StringBuilder();
                            } else if (cls == UUID.class) {
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitVarInsn(16, i4);
                                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanUUID", "(C)Ljava/util/UUID;");
                                sb2 = new StringBuilder();
                            } else {
                                if (cls.isEnum()) {
                                    Label label2 = new Label();
                                    Label label3 = new Label();
                                    Label label4 = new Label();
                                    Label label5 = new Label();
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    fieldInfoArr = fieldInfoArr2;
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                                    methodWriter.visitInsn(89);
                                    methodWriter.visitVarInsn(54, context.var("ch"));
                                    methodWriter.visitLdcInsn(110);
                                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label5);
                                    methodWriter.visitVarInsn(21, context.var("ch"));
                                    methodWriter.visitLdcInsn(34);
                                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label2);
                                    methodWriter.visitLabel(label5);
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
                                    methodWriter.visitVarInsn(25, 1);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
                                    methodWriter.visitVarInsn(16, i4);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc((Class<?>) SymbolTable.class) + "C)Ljava/lang/Enum;");
                                    methodWriter.visitJumpInsn(Opcodes.GOTO, label4);
                                    methodWriter.visitLabel(label2);
                                    methodWriter.visitVarInsn(21, context.var("ch"));
                                    methodWriter.visitLdcInsn(48);
                                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPLT, label3);
                                    methodWriter.visitVarInsn(21, context.var("ch"));
                                    methodWriter.visitLdcInsn(57);
                                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPGT, label3);
                                    _getFieldDeser(context, methodWriter, fieldInfo);
                                    methodWriter.visitTypeInsn(192, ASMUtils.type(EnumDeserializer.class));
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    methodWriter.visitVarInsn(16, i4);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                                    methodWriter.visitJumpInsn(Opcodes.GOTO, label4);
                                    methodWriter.visitLabel(label3);
                                    methodWriter.visitVarInsn(25, 0);
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    methodWriter.visitVarInsn(16, i4);
                                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";C)Ljava/lang/Enum;");
                                    methodWriter.visitLabel(label4);
                                    methodWriter.visitTypeInsn(192, ASMUtils.type(cls));
                                    sb = new StringBuilder();
                                } else {
                                    fieldInfoArr = fieldInfoArr2;
                                    if (Collection.class.isAssignableFrom(cls)) {
                                        Class<?> collectionItemClass = TypeUtils.getCollectionItemClass(type);
                                        if (collectionItemClass == String.class) {
                                            if (cls == List.class || cls == Collections.class || cls == ArrayList.class) {
                                                methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(ArrayList.class));
                                                methodWriter.visitInsn(89);
                                                methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(ArrayList.class), "<init>", "()V");
                                            } else {
                                                methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
                                                methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                                            }
                                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
                                            methodWriter.visitVarInsn(16, i4);
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanStringArray", "(Ljava/util/Collection;C)V");
                                            Label label6 = new Label();
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                                            methodWriter.visitLdcInsn(5);
                                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label6);
                                            methodWriter.visitInsn(1);
                                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                                            methodWriter.visitLabel(label6);
                                        } else {
                                            Label label7 = new Label();
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
                                            methodWriter.visitVarInsn(54, context.var(AssistPushConsts.MSG_TYPE_TOKEN));
                                            methodWriter.visitVarInsn(21, context.var(AssistPushConsts.MSG_TYPE_TOKEN));
                                            int i5 = i3 == 0 ? 14 : 16;
                                            methodWriter.visitLdcInsn(Integer.valueOf(i5));
                                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label7);
                                            methodWriter.visitVarInsn(25, 1);
                                            methodWriter.visitLdcInsn(Integer.valueOf(i5));
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "throwException", "(I)V");
                                            methodWriter.visitLabel(label7);
                                            Label label8 = new Label();
                                            Label label9 = new Label();
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                                            methodWriter.visitVarInsn(16, 91);
                                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label8);
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
                                            methodWriter.visitInsn(87);
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitLdcInsn(14);
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
                                            methodWriter.visitJumpInsn(Opcodes.GOTO, label9);
                                            methodWriter.visitLabel(label8);
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitLdcInsn(14);
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                                            methodWriter.visitLabel(label9);
                                            _newCollection(methodWriter, cls, i3, false);
                                            methodWriter.visitInsn(89);
                                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                                            _getCollectionFieldItemDeser(context, methodWriter, fieldInfo, collectionItemClass);
                                            methodWriter.visitVarInsn(25, 1);
                                            methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(collectionItemClass)));
                                            methodWriter.visitVarInsn(25, 3);
                                            methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(JavaBeanDeserializer.class), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + "L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                                        }
                                    } else if (cls.isArray()) {
                                        methodWriter.visitVarInsn(25, context.var("lexer"));
                                        methodWriter.visitLdcInsn(14);
                                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                                        methodWriter.visitVarInsn(25, 1);
                                        methodWriter.visitVarInsn(25, 0);
                                        methodWriter.visitLdcInsn(Integer.valueOf(i3));
                                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                                        methodWriter.visitTypeInsn(192, ASMUtils.type(cls));
                                        sb = new StringBuilder();
                                    } else {
                                        Label label10 = new Label();
                                        Label label11 = new Label();
                                        if (cls == Date.class) {
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                                            methodWriter.visitLdcInsn(49);
                                            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label10);
                                            methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(Date.class));
                                            methodWriter.visitInsn(89);
                                            methodWriter.visitVarInsn(25, context.var("lexer"));
                                            methodWriter.visitVarInsn(16, i4);
                                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                                            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(Date.class), "<init>", "(J)V");
                                            methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                                            methodWriter.visitJumpInsn(Opcodes.GOTO, label11);
                                        }
                                        methodWriter.visitLabel(label10);
                                        _quickNextToken(context, methodWriter, 14);
                                        _deserObject(context, methodWriter, fieldInfo, cls, i3);
                                        methodWriter.visitVarInsn(25, context.var("lexer"));
                                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
                                        methodWriter.visitLdcInsn(15);
                                        methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label11);
                                        methodWriter.visitVarInsn(25, 0);
                                        methodWriter.visitVarInsn(25, context.var("lexer"));
                                        methodWriter.visitLdcInsn(Integer.valueOf(!z ? 16 : 15));
                                        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "check", "(" + ASMUtils.desc((Class<?>) JSONLexer.class) + "I)V");
                                        methodWriter.visitLabel(label11);
                                    }
                                    i3++;
                                    fieldInfoArr2 = fieldInfoArr;
                                }
                                sb.append(fieldInfo.name);
                                sb.append("_asm");
                                iVar = context.var(sb.toString());
                                i = 58;
                            }
                            sb2.append(fieldInfo.name);
                            sb2.append("_asm");
                            methodWriter.visitVarInsn(58, context.var(sb2.toString()));
                            fieldInfoArr = fieldInfoArr2;
                            i3++;
                            fieldInfoArr2 = fieldInfoArr;
                        }
                        sb3.append(fieldInfo.name);
                        sb3.append("_asm");
                        iVar2 = context.var(sb3.toString());
                        i2 = 54;
                        methodWriter.visitVarInsn(i2, iVar2);
                        fieldInfoArr = fieldInfoArr2;
                        i3++;
                        fieldInfoArr2 = fieldInfoArr;
                    }
                    sb5.append(fieldInfo.name);
                    sb5.append("_asm");
                    iVar2 = context.var(sb5.toString(), 2);
                    methodWriter.visitVarInsn(i2, iVar2);
                    fieldInfoArr = fieldInfoArr2;
                    i3++;
                    fieldInfoArr2 = fieldInfoArr;
                }
                sb4.append(fieldInfo.name);
                sb4.append("_asm");
                methodWriter.visitVarInsn(58, context.var(sb4.toString()));
                methodWriter.visitLabel(label);
                fieldInfoArr = fieldInfoArr2;
                i3++;
                fieldInfoArr2 = fieldInfoArr;
            }
            methodWriter.visitVarInsn(i, iVar);
            i3++;
            fieldInfoArr2 = fieldInfoArr;
        }
        _batchSet(context, methodWriter, false);
        Label label12 = new Label();
        Label label13 = new Label();
        Label label14 = new Label();
        Label label15 = new Label();
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        methodWriter.visitInsn(89);
        methodWriter.visitVarInsn(54, context.var("ch"));
        methodWriter.visitVarInsn(16, 44);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label13);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label15);
        methodWriter.visitLabel(label13);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 93);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label14);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(15);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label15);
        methodWriter.visitLabel(label14);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 26);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label12);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(20);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label15);
        methodWriter.visitLabel(label12);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodWriter.visitLabel(label15);
        methodWriter.visitVarInsn(25, context.var("instance"));
        methodWriter.visitInsn(Opcodes.ARETURN);
        methodWriter.visitMaxs(5, context.variantIndex);
        methodWriter.visitEnd();
    }

    private void _deserialze_list_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, Class<?> cls2, int i) {
        String strType;
        String str;
        String str2;
        String strType2;
        String str3;
        String str4;
        int i2;
        Label label2 = new Label();
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
        _setFlag(methodVisitor, context, i);
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(8);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label3);
        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(21);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label5);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        _newCollection(methodVisitor, cls, i, true);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label5);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(12);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _newCollection(methodVisitor, cls, i, false);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitInsn(3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String strType3 = ASMUtils.type(ObjectDeserializer.class);
        String str5 = "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;";
        int i3 = Opcodes.INVOKEINTERFACE;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, strType3, "deserialze", str5);
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (cls.isInterface()) {
            strType = ASMUtils.type(cls);
            str = "add";
            str2 = "(Ljava/lang/Object;)Z";
        } else {
            strType = ASMUtils.type(cls);
            str = "add";
            str2 = "(Ljava/lang/Object;)Z";
            i3 = Opcodes.INVOKEVIRTUAL;
        }
        methodVisitor.visitMethodInsn(i3, strType, str, str2);
        methodVisitor.visitInsn(87);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label6);
        _newCollection(methodVisitor, cls, i, false);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        boolean zIsPrimitive2 = ParserConfig.isPrimitive2(fieldInfo.fieldClass);
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        if (zIsPrimitive2) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "getFastMatchToken", "()I");
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        } else {
            methodVisitor.visitInsn(87);
            methodVisitor.visitLdcInsn(12);
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            _quickNextToken(context, methodVisitor, 12);
        }
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitVarInsn(58, context.var("listContext"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitInsn(87);
        Label label7 = new Label();
        Label label8 = new Label();
        methodVisitor.visitInsn(3);
        methodVisitor.visitVarInsn(54, context.var(d.e));
        methodVisitor.visitLabel(label7);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label8);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitVarInsn(21, context.var(d.e));
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitIincInsn(context.var(d.e), 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (cls.isInterface()) {
            strType2 = ASMUtils.type(cls);
            str3 = "add";
            str4 = "(Ljava/lang/Object;)Z";
            i2 = Opcodes.INVOKEINTERFACE;
        } else {
            strType2 = ASMUtils.type(cls);
            str3 = "add";
            str4 = "(Ljava/lang/Object;)Z";
            i2 = Opcodes.INVOKEVIRTUAL;
        }
        methodVisitor.visitMethodInsn(i2, strType2, str3, str4);
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "checkListResolve", "(Ljava/util/Collection;)V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label7);
        if (zIsPrimitive2) {
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        } else {
            _quickNextToken(context, methodVisitor, 12);
        }
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label7);
        methodVisitor.visitLabel(label8);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("listContext"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, AssistPushConsts.MSG_TYPE_TOKEN, "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _quickNextTokenComma(context, methodVisitor);
        methodVisitor.visitLabel(label2);
    }

    private void _deserialze_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, int i) {
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_prefix__", "[C");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label2);
        methodVisitor.visitInsn(1);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        methodVisitor.visitLabel(label2);
        _setFlag(methodVisitor, context, i);
        methodVisitor.visitVarInsn(21, context.var("matchedCount"));
        methodVisitor.visitInsn(4);
        methodVisitor.visitInsn(96);
        methodVisitor.visitVarInsn(54, context.var("matchedCount"));
        _deserObject(context, methodVisitor, fieldInfo, cls, i);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getResolveStatus", "()I");
        methodVisitor.visitLdcInsn(1);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getLastResolveTask", "()" + ASMUtils.desc((Class<?>) DefaultJSONParser.ResolveTask.class));
        methodVisitor.visitVarInsn(58, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "ownerContext", ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc((Class<?>) FieldDeserializer.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "fieldDeserializer", ASMUtils.desc((Class<?>) FieldDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setResolveStatus", "(I)V");
        methodVisitor.visitLabel(label3);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    private void _getFieldDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    private void _init(ClassWriter classWriter, Context context) {
        int length = context.fieldInfoList.length;
        for (int i = 0; i < length; i++) {
            new FieldWriter(classWriter, 1, context.fieldInfoList[i].name + "_asm_prefix__", "[C").visitEnd();
        }
        int length2 = context.fieldInfoList.length;
        for (int i2 = 0; i2 < length2; i2++) {
            FieldInfo fieldInfo = context.fieldInfoList[i2];
            Class<?> cls = fieldInfo.fieldClass;
            if (!cls.isPrimitive()) {
                (Collection.class.isAssignableFrom(cls) ? new FieldWriter(classWriter, 1, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class)) : new FieldWriter(classWriter, 1, fieldInfo.name + "_asm_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class))).visitEnd();
            }
        }
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V", null, null);
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V");
        int length3 = context.fieldInfoList.length;
        for (int i3 = 0; i3 < length3; i3++) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i3];
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitLdcInsn("\"" + fieldInfo2.name + "\":");
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C");
            methodWriter.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo2.name + "_asm_prefix__", "[C");
        }
        methodWriter.visitInsn(Opcodes.RETURN);
        methodWriter.visitMaxs(4, 4);
        methodWriter.visitEnd();
    }

    private void _isFlag(MethodVisitor methodVisitor, Context context, int i, Label label) {
        methodVisitor.visitVarInsn(21, context.var("_asm_flag_" + (i / 32)));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i));
        methodVisitor.visitInsn(126);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _loadAndSet(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        StringBuilder sb;
        int i;
        int iVar;
        Class<?> cls = fieldInfo.fieldClass;
        java.lang.reflect.Type type = fieldInfo.fieldType;
        if (cls == Boolean.TYPE || cls == Byte.TYPE || cls == Short.TYPE || cls == Integer.TYPE || cls == Character.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(fieldInfo.name);
            sb2.append("_asm");
            methodVisitor.visitVarInsn(21, context.var(sb2.toString()));
            _set(context, methodVisitor, fieldInfo);
        }
        if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(22, context.var(fieldInfo.name + "_asm", 2));
            if (fieldInfo.method == null) {
                methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
                return;
            }
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
            if (fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                return;
            }
            methodVisitor.visitInsn(87);
            return;
        }
        if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            i = 23;
            iVar = context.var(fieldInfo.name + "_asm");
        } else {
            if (cls != Double.TYPE) {
                if (cls == String.class || cls.isEnum() || !Collection.class.isAssignableFrom(cls)) {
                    methodVisitor.visitVarInsn(25, context.var("instance"));
                    sb = new StringBuilder();
                } else {
                    methodVisitor.visitVarInsn(25, context.var("instance"));
                    if (TypeUtils.getCollectionItemClass(type) == String.class) {
                        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
                        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
                        _set(context, methodVisitor, fieldInfo);
                    }
                    sb = new StringBuilder();
                }
                sb.append(fieldInfo.name);
                sb.append("_asm");
                methodVisitor.visitVarInsn(25, context.var(sb.toString()));
                _set(context, methodVisitor, fieldInfo);
            }
            methodVisitor.visitVarInsn(25, context.var("instance"));
            i = 24;
            iVar = context.var(fieldInfo.name + "_asm", 2);
        }
        methodVisitor.visitVarInsn(i, iVar);
        _set(context, methodVisitor, fieldInfo);
    }

    private void _newCollection(MethodVisitor methodVisitor, Class<?> cls, int i, boolean z) {
        Class cls2;
        String strType;
        if (!cls.isAssignableFrom(ArrayList.class) || z) {
            if (!cls.isAssignableFrom(LinkedList.class) || z) {
                if (cls.isAssignableFrom(HashSet.class)) {
                    methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
                    methodVisitor.visitInsn(89);
                    cls2 = HashSet.class;
                } else if (cls.isAssignableFrom(TreeSet.class)) {
                    methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(TreeSet.class));
                    methodVisitor.visitInsn(89);
                    cls2 = TreeSet.class;
                } else if (cls.isAssignableFrom(LinkedHashSet.class)) {
                    methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedHashSet.class));
                    methodVisitor.visitInsn(89);
                    cls2 = LinkedHashSet.class;
                } else {
                    if (!z) {
                        methodVisitor.visitVarInsn(25, 0);
                        methodVisitor.visitLdcInsn(Integer.valueOf(i));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
                    }
                    methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
                    methodVisitor.visitInsn(89);
                    cls2 = HashSet.class;
                }
                methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            }
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedList.class));
            methodVisitor.visitInsn(89);
            cls2 = LinkedList.class;
            strType = ASMUtils.type(cls2);
        } else {
            methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            methodVisitor.visitInsn(89);
            strType = "java/util/ArrayList";
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, strType, "<init>", "()V");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
    }

    private void _quickNextToken(Context context, MethodVisitor methodVisitor, int i) {
        int i2;
        Label label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        if (i == 12) {
            i2 = 123;
        } else {
            if (i != 14) {
                throw new IllegalStateException();
            }
            i2 = 91;
        }
        methodVisitor.visitVarInsn(16, i2);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitLabel(label2);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor methodVisitor) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(54, context.var("ch"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label2);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 93);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 26);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(20);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "()V");
        methodVisitor.visitLabel(label5);
    }

    private void _set(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method == null) {
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
            return;
        }
        methodVisitor.visitMethodInsn(method.getDeclaringClass().isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldInfo.declaringClass), method.getName(), ASMUtils.desc(method));
        if (fieldInfo.method.getReturnType().equals(Void.TYPE)) {
            return;
        }
        methodVisitor.visitInsn(87);
    }

    private void _setContext(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("context"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        Label label = new Label();
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitJumpInsn(Opcodes.IFNULL, label);
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitVarInsn(25, context.var("instance"));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(ParseContext.class), "object", "Ljava/lang/Object;");
        methodVisitor.visitLabel(label);
    }

    private void _setFlag(MethodVisitor methodVisitor, Context context, int i) {
        String str = "_asm_flag_" + (i / 32);
        methodVisitor.visitVarInsn(21, context.var(str));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i));
        methodVisitor.visitInsn(128);
        methodVisitor.visitVarInsn(54, context.var(str));
    }

    private void defineVarLexer(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, DefaultJSONParser, "lexer", ASMUtils.desc((Class<?>) JSONLexer.class));
        methodVisitor.visitTypeInsn(192, JSONLexerBase);
        methodVisitor.visitVarInsn(58, context.var("lexer"));
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) throws Exception {
        String str;
        Class<?> cls = javaBeanInfo.clazz;
        if (cls.isPrimitive()) {
            throw new IllegalArgumentException("not support type :" + cls.getName());
        }
        String str2 = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + "_" + cls.getSimpleName();
        Package r1 = ASMDeserializerFactory.class.getPackage();
        if (r1 != null) {
            String name = r1.getName();
            String str3 = name.replace('.', '/') + "/" + str2;
            str = name + "." + str2;
            str2 = str3;
        } else {
            str = str2;
        }
        ClassWriter classWriter = new ClassWriter();
        classWriter.visit(49, 33, str2, ASMUtils.type(JavaBeanDeserializer.class), null);
        _init(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
        _createInstance(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
        _deserialze(classWriter, new Context(str2, parserConfig, javaBeanInfo, 5));
        _deserialzeArrayMapping(classWriter, new Context(str2, parserConfig, javaBeanInfo, 4));
        byte[] byteArray = classWriter.toByteArray();
        return (ObjectDeserializer) this.classLoader.defineClassPublic(str, byteArray, 0, byteArray.length).getConstructor(ParserConfig.class, JavaBeanInfo.class).newInstance(parserConfig, javaBeanInfo);
    }
}
