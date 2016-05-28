package com.worthto.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by wenjie on 16/1/19.
 */
public class NumberUtils {
    public static int toInteger(String string) {
        if (StringUtils.isBlank(string)) {
            return 0;
        }
        try {
            return Integer.valueOf(string);
        } catch (Exception e) {

        }

        return 0;

    }

    public static long toLong(String string) {
        if (StringUtils.isBlank(string)) {
            return 0;
        }
        try {
            return Long.valueOf(string);
        } catch (Exception e) {

        }

        return 0;

    }

    public static float toFloat(String string) {
        if (StringUtils.isBlank(string)) {
            return 0.0f;
        }
        try {
            return Float.valueOf(string);
        } catch (Exception e) {

        }

        return 0.0f;

    }

    public static double toDouble(String string) {
        if (StringUtils.isBlank(string)) {
            return 0.0d;
        }
        try {
            return Double.valueOf(string);
        } catch (Exception e) {

        }

        return 0.0d;

    }

    public static boolean toBoolean(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        try {
            return Boolean.valueOf(string);
        } catch (Exception e) {

        }

        return false;
    }
}
