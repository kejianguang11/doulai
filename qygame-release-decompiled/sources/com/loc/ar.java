package com.loc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ar {
    private static Map<Class<? extends aq>, aq> d = new HashMap();
    private au a;
    private SQLiteDatabase b;
    private aq c;

    public ar(Context context, aq aqVar) {
        try {
            this.a = new au(context.getApplicationContext(), aqVar.a(), aqVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.c = aqVar;
    }

    private static ContentValues a(Object obj, as asVar) {
        ContentValues contentValues = new ContentValues();
        for (Field field : a(obj.getClass(), asVar.b())) {
            field.setAccessible(true);
            a(obj, field, contentValues);
        }
        return contentValues;
    }

    private SQLiteDatabase a() {
        try {
            if (this.b == null) {
                this.b = this.a.getReadableDatabase();
            }
        } catch (Throwable th) {
            ak.a(th, "dbs", "grd");
        }
        return this.b;
    }

    public static synchronized aq a(Class<? extends aq> cls) throws IllegalAccessException, InstantiationException {
        if (d.get(cls) == null) {
            d.put(cls, cls.newInstance());
        }
        return d.get(cls);
    }

    private static <T> T a(Cursor cursor, Class<T> cls, as asVar) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object objValueOf;
        Field[] fieldArrA = a((Class<?>) cls, asVar.b());
        Constructor<T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
        declaredConstructor.setAccessible(true);
        T tNewInstance = declaredConstructor.newInstance(new Object[0]);
        for (Field field : fieldArrA) {
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(at.class);
            if (annotation != null) {
                at atVar = (at) annotation;
                int iB = atVar.b();
                int columnIndex = cursor.getColumnIndex(atVar.a());
                switch (iB) {
                    case 1:
                        objValueOf = Short.valueOf(cursor.getShort(columnIndex));
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 2:
                        objValueOf = Integer.valueOf(cursor.getInt(columnIndex));
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 3:
                        objValueOf = Float.valueOf(cursor.getFloat(columnIndex));
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 4:
                        objValueOf = Double.valueOf(cursor.getDouble(columnIndex));
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 5:
                        objValueOf = Long.valueOf(cursor.getLong(columnIndex));
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 6:
                        objValueOf = cursor.getString(columnIndex);
                        field.set(tNewInstance, objValueOf);
                        break;
                    case 7:
                        objValueOf = cursor.getBlob(columnIndex);
                        field.set(tNewInstance, objValueOf);
                        break;
                }
            }
        }
        return tNewInstance;
    }

    private static <T> String a(as asVar) {
        if (asVar == null) {
            return null;
        }
        return asVar.a();
    }

    private static <T> void a(SQLiteDatabase sQLiteDatabase, T t) {
        as asVarB = b((Class) t.getClass());
        String strA = a(asVarB);
        if (TextUtils.isEmpty(strA) || t == null || sQLiteDatabase == null) {
            return;
        }
        sQLiteDatabase.insert(strA, null, a(t, asVarB));
    }

    private <T> void a(T t) {
        b(t);
    }

    private static void a(Object obj, Field field, ContentValues contentValues) {
        Annotation annotation = field.getAnnotation(at.class);
        if (annotation == null) {
        }
        at atVar = (at) annotation;
        try {
            switch (atVar.b()) {
                case 1:
                    contentValues.put(atVar.a(), Short.valueOf(field.getShort(obj)));
                    break;
                case 2:
                    contentValues.put(atVar.a(), Integer.valueOf(field.getInt(obj)));
                    break;
                case 3:
                    contentValues.put(atVar.a(), Float.valueOf(field.getFloat(obj)));
                    break;
                case 4:
                    contentValues.put(atVar.a(), Double.valueOf(field.getDouble(obj)));
                    break;
                case 5:
                    contentValues.put(atVar.a(), Long.valueOf(field.getLong(obj)));
                    break;
                case 6:
                    contentValues.put(atVar.a(), (String) field.get(obj));
                    break;
                case 7:
                    contentValues.put(atVar.a(), (byte[]) field.get(obj));
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <T> void a(String str, Object obj) {
        synchronized (this.c) {
            try {
                if (obj == null) {
                    return;
                }
                as asVarB = b((Class) obj.getClass());
                String strA = a(asVarB);
                if (TextUtils.isEmpty(strA)) {
                    return;
                }
                ContentValues contentValuesA = a(obj, asVarB);
                this.b = b();
                if (this.b == null) {
                    return;
                }
                try {
                    try {
                        this.b.update(strA, contentValuesA, str, null);
                    } catch (Throwable th) {
                        ak.a(th, "dbs", "udd");
                        if (this.b != null) {
                            this.b.close();
                        }
                    }
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                } catch (Throwable th2) {
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                    throw th2;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    private static Field[] a(Class<?> cls, boolean z) {
        if (cls == null) {
            return null;
        }
        return z ? cls.getSuperclass().getDeclaredFields() : cls.getDeclaredFields();
    }

    private SQLiteDatabase b() {
        try {
            if (this.b == null || this.b.isReadOnly()) {
                if (this.b != null) {
                    this.b.close();
                }
                this.b = this.a.getWritableDatabase();
            }
        } catch (Throwable th) {
            ak.a(th, "dbs", "gwd");
        }
        return this.b;
    }

    private static <T> as b(Class<T> cls) {
        Annotation annotation = cls.getAnnotation(as.class);
        if (annotation != null) {
            return (as) annotation;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:? A[Catch: all -> 0x00e9, SYNTHETIC, TryCatch #4 {, blocks: (B:4:0x0003, B:6:0x0014, B:7:0x001a, B:9:0x001e, B:18:0x0040, B:22:0x004c, B:24:0x0050, B:28:0x0060, B:27:0x0059, B:21:0x0045, B:37:0x0074, B:41:0x0080, B:43:0x0084, B:65:0x00c1, B:47:0x0091, B:40:0x0079, B:55:0x00a3, B:59:0x00af, B:61:0x00b3, B:58:0x00a8, B:69:0x00c6, B:73:0x00d2, B:75:0x00d6, B:79:0x00e6, B:78:0x00df, B:72:0x00cb, B:80:0x00e7), top: B:91:0x0003, inners: #2, #5, #6, #7, #10, #11 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0050 A[Catch: Throwable -> 0x0058, all -> 0x00e9, TRY_LEAVE, TryCatch #11 {Throwable -> 0x0058, blocks: (B:22:0x004c, B:24:0x0050), top: B:102:0x004c, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d6 A[Catch: Throwable -> 0x00de, all -> 0x00e9, TRY_LEAVE, TryCatch #2 {Throwable -> 0x00de, blocks: (B:73:0x00d2, B:75:0x00d6), top: B:88:0x00d2, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private <T> List<T> b(String str, Class<T> cls) {
        Cursor cursorQuery;
        String str2;
        String str3;
        synchronized (this.c) {
            ArrayList arrayList = new ArrayList();
            as asVarB = b((Class) cls);
            String strA = a(asVarB);
            if (this.b == null) {
                this.b = a();
            }
            if (this.b == null || TextUtils.isEmpty(strA) || str == 0) {
                return arrayList;
            }
            try {
                try {
                    cursorQuery = this.b.query(strA, null, str, null, null, null, null);
                    try {
                    } catch (Throwable th) {
                        th = th;
                        ak.a(th, "dbs", "sld");
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th2) {
                                ak.a(th2, "dbs", "sld");
                            }
                        }
                        try {
                            if (this.b != null) {
                                this.b.close();
                                this.b = null;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            str2 = "dbs";
                            str3 = "sld";
                            ak.a(th, str2, str3);
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (str == 0) {
                        try {
                            str.close();
                        } catch (Throwable th5) {
                            ak.a(th5, "dbs", "sld");
                        }
                        try {
                            if (this.b != null) {
                                throw th;
                            }
                            this.b.close();
                            this.b = null;
                            throw th;
                        } catch (Throwable th6) {
                            ak.a(th6, "dbs", "sld");
                            throw th;
                        }
                    }
                    if (this.b != null) {
                    }
                }
            } catch (Throwable th7) {
                th = th7;
                cursorQuery = null;
            }
            if (cursorQuery == null) {
                this.b.close();
                this.b = null;
                if (cursorQuery == null) {
                    if (this.b != null) {
                    }
                    return arrayList;
                }
                try {
                    cursorQuery.close();
                } catch (Throwable th8) {
                    ak.a(th8, "dbs", "sld");
                }
                try {
                    if (this.b != null) {
                        this.b.close();
                        this.b = null;
                    }
                } catch (Throwable th9) {
                    ak.a(th9, "dbs", "sld");
                }
                return arrayList;
            }
            while (cursorQuery.moveToNext()) {
                arrayList.add(a(cursorQuery, cls, asVarB));
            }
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Throwable th10) {
                    ak.a(th10, "dbs", "sld");
                }
            }
            try {
                if (this.b != null) {
                    this.b.close();
                    this.b = null;
                }
            } catch (Throwable th11) {
                th = th11;
                str2 = "dbs";
                str3 = "sld";
                ak.a(th, str2, str3);
            }
            return arrayList;
        }
    }

    private <T> void b(T t) {
        synchronized (this.c) {
            this.b = b();
            if (this.b == null) {
                return;
            }
            try {
                try {
                    a(this.b, t);
                } catch (Throwable th) {
                    ak.a(th, "dbs", "itd");
                    if (this.b != null) {
                        this.b.close();
                    }
                }
                if (this.b != null) {
                    this.b.close();
                    this.b = null;
                }
            } catch (Throwable th2) {
                if (this.b != null) {
                    this.b.close();
                    this.b = null;
                }
                throw th2;
            }
        }
    }

    private <T> void b(String str, Object obj) {
        a(str, obj);
    }

    public final <T> List<T> a(String str, Class<T> cls) {
        return b(str, (Class) cls);
    }

    public final void a(Object obj, String str) {
        synchronized (this.c) {
            List listA = a(str, (Class) obj.getClass());
            if (listA == null || listA.size() == 0) {
                a(obj);
            } else {
                b(str, obj);
            }
        }
    }
}
