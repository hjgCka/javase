package com.hjg.plain.poi;

import java.io.InputStream;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/16
 */
public class TextExtractionApp {

    public static void main(String[] args) {
        String name = "/data.xlsx";
        InputStream is = TextExtractionApp.class.getResourceAsStream(name);


    }
}
