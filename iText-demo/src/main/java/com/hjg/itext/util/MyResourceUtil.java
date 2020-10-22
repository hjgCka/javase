package com.hjg.itext.util;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/12
 */
public class MyResourceUtil {

    public static String getFileName(String data) {
        return MyResourceUtil.class.getClassLoader().getResource(data).getFile();
    }
}
