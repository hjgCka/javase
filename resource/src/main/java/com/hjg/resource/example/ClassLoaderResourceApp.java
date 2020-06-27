package com.hjg.resource.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ClassLoaderResourceApp {

    public static void main(String[] args) {
        //其实Class.getResourceAsStream的用法也是调用ClassLoader，但是在这之前进行了name的解析
        //结果就是得到的name全部都是不以"/"作为开头字符
        String path1 = "readme.txt";

        String path2 = "txt/book/book1.txt";

        String path3 = "com/hjg/resource/example/book2.txt";

        InputStream is = ClassLoaderResourceApp.class.getClassLoader().getResourceAsStream(path3);

        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String content = br.lines().collect(Collectors.joining());

        System.out.println("content = " + content);

    }
}
