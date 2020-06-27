package com.hjg.resource.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ClassResourceApp {

    public static void main(String[] args) {
        //资源与所使用的类不在同一个路径，使用绝对路径
        String path1 = "/readme.txt";
        String path2 = "/txt/book/book1.txt";

        //如果资源与所使用的类在相同路径，则可以用这个相对路径
        String path3 = "book2.txt";

        InputStream is = ClassResourceApp.class.getResourceAsStream(path2);

        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String content = br.lines().collect(Collectors.joining());

        System.out.println("content = " + content);

        String baseName = ClassResourceApp.class.getName();
        System.out.println("baseName = " + baseName);
    }
}
