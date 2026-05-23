package com.nirvana.tools.jsoner;

import android.text.TextUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JSONUtils {
    private static final JsonCache JSON_CACHE = new JsonCache();

    public static <T> T fromJson(JSONObject jSONObject, JsonType<T> jsonType, List<Field> list) {
        T tNewInstance = jsonType.newInstance();
        if (!(tNewInstance instanceof Jsoner)) {
            return (T) fromJson(jSONObject, tNewInstance, list);
        }
        ((Jsoner) tNewInstance).fromJson(jSONObject);
        return tNewInstance;
    }

    public static <T> T fromJson(JSONObject jSONObject, T t, List<Field> list) {
        if (jSONObject != null && t != null) {
            Class<?> cls = t.getClass();
            a jsonClass = JSON_CACHE.getJsonClass(cls);
            if (jsonClass == null) {
                jsonClass = new a(cls);
                JSON_CACHE.putJsonClass(cls, jsonClass);
            }
            List<Field> list2 = jsonClass.a;
            if (list2 != null && list2.size() > 0) {
                for (Field field : list2) {
                    b bVarA = jsonClass.a(field.getName());
                    if (bVarA == null) {
                        bVarA = new b(field);
                        jsonClass.a(field.getName(), bVarA);
                    }
                    if (!bVarA.c) {
                        String str = bVarA.a;
                        if (jSONObject.has(str)) {
                            if (bVarA.a()) {
                                setOriginalType(field, str, jSONObject, t);
                            } else if (bVarA.b()) {
                                try {
                                    field.setAccessible(true);
                                    Jsoner jsoner = (Jsoner) bVarA.b.newInstance();
                                    Object objOpt = jSONObject.opt(str);
                                    if (objOpt instanceof JSONObject) {
                                        jsoner.fromJson((JSONObject) objOpt);
                                    } else if (objOpt instanceof String) {
                                        jsoner.fromJson(new JSONObject(String.valueOf(objOpt)));
                                    }
                                    field.set(t, jsoner);
                                    field = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (field != null && list != null) {
                            list.add(field);
                        }
                    }
                }
            }
        }
        return t;
    }

    public static boolean isOriginalBoolean(Class<?> cls) {
        return Boolean.TYPE.equals(cls) || Boolean.class.equals(cls) || boolean[].class.equals(cls) || Boolean[].class.equals(cls);
    }

    public static boolean isOriginalChar(Class<?> cls) {
        return Byte.TYPE.equals(cls) || Byte.class.equals(cls) || byte[].class.equals(cls) || Byte[].class.equals(cls) || Character.TYPE.equals(cls) || Character.class.equals(cls) || char[].class.equals(cls) || Character[].class.equals(cls);
    }

    public static boolean isOriginalNumber(Class<?> cls) {
        return Integer.TYPE.equals(cls) || Integer.class.equals(cls) || int[].class.equals(cls) || Integer[].class.equals(cls) || Short.TYPE.equals(cls) || Short.class.equals(cls) || short[].class.equals(cls) || Short[].class.equals(cls) || Long.TYPE.equals(cls) || Long.class.equals(cls) || long[].class.equals(cls) || Long[].class.equals(cls) || Float.TYPE.equals(cls) || Float.class.equals(cls) || float[].class.equals(cls) || Float[].class.equals(cls) || Double.TYPE.equals(cls) || Double.class.equals(cls) || double[].class.equals(cls) || Double[].class.equals(cls);
    }

    public static boolean isOriginalString(Class<?> cls) {
        return String.class.equals(cls) || StringBuilder.class.equals(cls) || String[].class.equals(cls) || StringBuilder[].class.equals(cls) || StringBuffer.class.equals(cls) || CharSequence.class.equals(cls) || StringBuffer[].class.equals(cls) || CharSequence[].class.equals(cls);
    }

    public static Map<String, Integer> json2MapForStringInteger(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.length() <= 0) {
                return null;
            }
            HashMap map = new HashMap(jSONObject.length());
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, Integer.valueOf(jSONObject.getInt(next)));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> json2MapForStringString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return json2MapForStringString(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> json2MapForStringString(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            if (jSONObject.length() <= 0) {
                return null;
            }
            HashMap map = new HashMap(jSONObject.length());
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Jsoner> JSONArray jsonerCollection2JsonArray(Collection<T> collection) {
        JSONArray jSONArray = new JSONArray();
        if (collection != null && collection.size() > 0) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().toJson());
            }
        }
        return jSONArray;
    }

    public static JSONArray jsonerCollectionString2JsonArray(Collection<String> collection) {
        JSONArray jSONArray = new JSONArray();
        if (collection != null && collection.size() > 0) {
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
        }
        return jSONArray;
    }

    public static <T extends Jsoner> JSONArray jsonerList2JsonArray(List<T> list) {
        return jsonerCollection2JsonArray(list);
    }

    public static <T extends Jsoner> List<T> parseJsonArray2JsonerList(String str, JsonType<T> jsonType) {
        try {
            return parseJsonArray2JsonerList(new JSONArray(str), jsonType);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Jsoner> List<T> parseJsonArray2JsonerList(JSONArray jSONArray, JsonType<T> jsonType) {
        if (jSONArray == null) {
            return null;
        }
        try {
            int length = jSONArray.length();
            if (length <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                String string = jSONArray.getString(i);
                if (!TextUtils.isEmpty(string)) {
                    JSONObject jSONObject = new JSONObject(string);
                    T tNewInstance = jsonType.newInstance();
                    tNewInstance.fromJson(jSONObject);
                    arrayList.add(tNewInstance);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean setOriginalType(Field field, String str, JSONObject jSONObject, Object obj) {
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            field.set(obj, String.class.equals(type) ? jSONObject.optString(str) : (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) ? Boolean.valueOf(jSONObject.optBoolean(str)) : (Integer.TYPE.equals(type) || Integer.class.equals(type)) ? Integer.valueOf(jSONObject.optInt(str)) : (Long.TYPE.equals(type) || Long.class.equals(type)) ? Long.valueOf(jSONObject.optLong(str)) : (Double.TYPE.equals(type) || Double.class.equals(type)) ? Double.valueOf(jSONObject.optDouble(str)) : jSONObject.opt(str));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject toJson(Object obj, List<Field> list) {
        return toJson(obj, list, false);
    }

    public static JSONObject toJson(Object obj, List<Field> list, boolean z) {
        JSONObject json;
        JSONObject jSONObject = new JSONObject();
        Class<?> cls = obj.getClass();
        a jsonClass = JSON_CACHE.getJsonClass(cls);
        if (jsonClass == null) {
            jsonClass = new a(cls);
            JSON_CACHE.putJsonClass(cls, jsonClass);
        }
        List<Field> list2 = jsonClass.a;
        if (list2 != null && list2.size() > 0) {
            for (Field field : list2) {
                b bVarA = jsonClass.a(field.getName());
                if (bVarA == null) {
                    bVarA = new b(field);
                    jsonClass.a(field.getName(), bVarA);
                }
                if (!bVarA.c) {
                    Field field2 = null;
                    if (bVarA.a()) {
                        try {
                            field.setAccessible(true);
                            Object obj2 = field.get(obj);
                            if (!z || obj2 != null) {
                                jSONObject.put(bVarA.a, obj2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            field2 = field;
                        }
                        if (field2 == null && list != null) {
                            list.add(field2);
                        }
                    } else {
                        if (bVarA.b()) {
                            field.setAccessible(true);
                            Jsoner jsoner = (Jsoner) field.get(obj);
                            if (jsoner != null && ((json = jsoner.toJson()) != null || !z)) {
                                jSONObject.put(bVarA.a, json);
                            }
                        } else {
                            field2 = field;
                        }
                        if (field2 == null) {
                        }
                    }
                }
            }
        }
        return jSONObject;
    }
}
