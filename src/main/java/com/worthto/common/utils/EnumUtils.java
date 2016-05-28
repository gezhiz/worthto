package com.worthto.common.utils;


import java.util.*;

/**
 * Created by wenjie on 16/1/6.
 */
public class EnumUtils {
    public interface IntegerValueEnum{
        public int val();
    }
    private static final Map<Class, LinkedHashMap<String, Enum>> ALL = new HashMap<Class, LinkedHashMap<String, Enum>>();

    static {
//        EnumUtils.register(UserProject.ProjectBudget.class);
    }

    public static <T extends Enum> void register(Class<T> clss) {
        T[] vs = clss.getEnumConstants();
        String[] names = new String[0];
        try {
            names = (String[]) clss.getDeclaredField("NAMES").get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (vs != null) {
            LinkedHashMap<String, Enum> map = new LinkedHashMap<String, Enum>();
            for (int i = 0; i < vs.length; i++) {
                T v = vs[i];
                if (names != null && i < names.length) {
                    map.put(names[i], v);
                } else {
                    map.put(v.toString(), v);
                }
            }
            ALL.put(clss, map);
        }
    }

    public static <T extends Enum> T type(Class<T> clss, String s) {
        LinkedHashMap<String, Enum> map = ALL.get(clss);
        if (map != null) {
            return (T) map.get(s);
        }
        return null;
    }

    public static <T extends IntegerValueEnum> T type(Class<T> clss, int val) {
        LinkedHashMap<String, Enum> map = ALL.get(clss);
        if (map != null) {
            for (String key : map.keySet()) {
                IntegerValueEnum result = (IntegerValueEnum)map.get(key);
                if (val == result.val()) {
                    return (T)map.get(key);
                }
            }
        }
        return null;
    }
    public static <T extends IntegerValueEnum> String nameByVal(Class<T> clss, int val) {
        LinkedHashMap<String, Enum> map = ALL.get(clss);
        if (map != null) {
            for (String key : map.keySet()) {
                IntegerValueEnum result = (IntegerValueEnum)map.get(key);
                if (val == result.val()) {
                    return key;
                }
            }
        }
        return null;
    }
    public static <T extends Enum> String name(T t) {
        LinkedHashMap<String, Enum> map = ALL.get(t.getClass());
        if (map != null) {
            for (String key : map.keySet()) {
                if (t == map.get(key)) {
                    return key;
                }
            }
        }
        return t.toString();
    }

    public static <T extends Enum> Integer val(T t) {
        if (t instanceof IntegerValueEnum) {
            return ((IntegerValueEnum) t).val();
        }
        return 0;
    }

    public static <T extends Enum> List<String> list(Class<T> clss) {
        LinkedHashMap<String, Enum> map = ALL.get(clss);
        if (map != null) {
            return new ArrayList<String>(map.keySet());
        }
        return Collections.EMPTY_LIST;
    }
}
