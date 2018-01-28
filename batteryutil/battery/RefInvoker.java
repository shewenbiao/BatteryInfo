package com.common.utils.battery;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RefInvoker {

    public static final String TAG = "RefInvoker";

    private static final ClassLoader SYSTEM_CLASS_LOADER = ClassLoader.getSystemClassLoader();
    private static final ClassLoader PARENT = SYSTEM_CLASS_LOADER.getParent();
    private static final ClassLoader LOADER = RefInvoker.class.getClassLoader();
    private static HashMap<String, Class> sClassHashMap = new HashMap();
    private static final Map<Class<?>, Class<?>> CLASS_MAP = new HashMap();

    static {
        CLASS_MAP.put(Boolean.TYPE, Boolean.class);
        CLASS_MAP.put(Byte.TYPE, Byte.class);
        CLASS_MAP.put(Character.TYPE, Character.class);
        CLASS_MAP.put(Short.TYPE, Short.class);
        CLASS_MAP.put(Integer.TYPE, Integer.class);
        CLASS_MAP.put(Long.TYPE, Long.class);
        CLASS_MAP.put(Double.TYPE, Double.class);
        CLASS_MAP.put(Float.TYPE, Float.class);
        CLASS_MAP.put(Void.TYPE, Void.TYPE);
    }

    public static Class getClass(String str) throws ClassNotFoundException {
        Class cls = (Class) sClassHashMap.get(str);
        if (cls == null) {
            cls = Class.forName(str);
            ClassLoader classLoader = cls.getClassLoader();
            if (classLoader == SYSTEM_CLASS_LOADER || classLoader == LOADER || classLoader == PARENT) {
                sClassHashMap.put(str, cls);
            }
        }
        return cls;
    }

    public static Object getObjectFromConstructor(String str, Class[] clsArr, Object[] objArr) {
        try {
            Constructor constructor = RefInvoker.getClass(str).getConstructor(clsArr);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(objArr);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Object getObjectFromMethod(Object obj, String str, String str2, Class[] clsArr, Object[] objArr) {
        try {
            return RefInvoker.getObjectFromMethod(obj, RefInvoker.getClass(str), str2, clsArr, objArr);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Object getObjectFromMethod(Object obj, Class cls, String str, Class[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
            return declaredMethod.invoke(obj, objArr);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Object getDeclaredField(Object obj, String str, String str2) {
        try {
            return RefInvoker.getDeclaredField(obj, RefInvoker.getClass(str), str2);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static Object getDeclaredField(Object obj, Class cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            return declaredField.get(obj);
        } catch (Throwable e) {
            try {
                Field declaredField2 = cls.getSuperclass().getDeclaredField(str);
                declaredField2.setAccessible(true);
                return declaredField2.get(obj);
            } catch (Throwable e2) {
                Log.e(TAG, e2.getMessage());
                return null;
            }
        }
    }
}
