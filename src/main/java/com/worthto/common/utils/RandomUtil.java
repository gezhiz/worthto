package com.worthto.common.utils;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by xali on 15/5/13.
 */
public class RandomUtil {
    public static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static int randomNumber(int max) {
        Random r = new Random();

        int random = r.nextInt(max + 1);

        return random;
    }

    public static int randomNumberMaxZero(int max) {
        Random r = new Random();

        int random = r.nextInt(max + 1);

        if (random == 0){

            random = 1;
        }

        return random;
    }

    public static double randomFloatMaxZero(double max) {
        Random r = new Random();

        double random = r.nextFloat();
        double rst = random*max;
        rst = new Double(String.format("%.2f",rst));

        if (rst<0.01d){
            rst = 0.01d;
        }

        return rst;
    }

    public static String randomStr(int length) {
        return randomCode(chars, length);
    }

    private static String randomCode(String chars, int size) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String shortStr(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        String[] shortStrs = generateCode(str);
        if (shortStrs == null || shortStrs.length == 0) {
            return "";
        }

        return shortStrs[randomNumber(shortStrs.length - 1)];
    }

    private static String[] generateCode(String str) {
        String key = "bbbingo"; // 网址的混合KEY
        String hex = toMd5(key + str);// 对传入网址和混合KEY进行MD5加密
        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars.charAt((int) index);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }

        return resUrl;
    }

    private static String toMd5(String arg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(arg.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args){

        System.out.println(randomFloatMaxZero(0.05f*100.00f));

    }


}
