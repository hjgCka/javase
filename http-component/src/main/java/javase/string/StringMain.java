package javase.string;

import java.nio.charset.StandardCharsets;

public class StringMain {

    public static void main(String[] args) {
        String str = "移动小智";

        //unicode编码是\u开头的十六进制数，表示在unicode字符集中的位置(有4个字节定位位置)。
        //除此之外还有utf-8, utf-16等多种编码方式，它们一般是可变长的编码方式，解决数据传输过大问题。
        String unicode = stringToUnicode(str);
        //\u79fb\u52a8\u5c0f\u667a
        System.out.println("unicode=" + unicode);

        String str2 = unicodeToString(unicode);
        System.out.println("str2=" + str2);

        String str3 = strToUtf8Encoded(str);
        System.out.println("str3=" + str3);
    }

    /**
     * 字符串转换为unicode字符串。
     * @param str
     * @return
     */
    private static String stringToUnicode(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }

    /**
     * unicode字符串转换为普通字符串。
     * @param unicode
     * @return
     */
    private static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

    private static String strToUtf8Encoded(String str) {
        //各种编码方式原始的字节数字都是一样的，构造String需要选择适当的编码方式
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }

}
