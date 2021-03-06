package com.plain.string;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringApp {

    public static void main(String[] args) {
        String str = "HI，我是小智，您的私人智能语音秘书。当您不想接电话、没空接电话和无法接电话时，" +
                "我都可以帮你接听，问清来电意图向您汇报。";

        String unicode = stringToUnicode(str);
        System.out.println("unicode=" + unicode);

        String str2 = unicodeToString(unicode);
        System.out.println("str2=" + str2);

        String[] array = {"222", "111"};
        String listStr = Stream.of(array).collect(Collectors.joining(","));
        System.out.println(listStr);

        String unicodeStr = "\\u79fb\\u52a8\\u5c0f\\u667a\\u670d\\u52a1\\u53f7";
        String wechatId = unicodeToString(unicodeStr);
        System.out.println("wechatId=" + wechatId);
    }

    //String对象转换为原始的unicode编码。
    private static String stringToUnicode(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }

    private static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }
}
