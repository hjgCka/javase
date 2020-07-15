package com.hjg.resource.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 获取多个资源。
 */
public class ClassLoaderResourceApp2 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        //这个名称应该也是要用解析之后的名称
        String packageName = "com/hjg/resource/example/model";
        ClassLoader cls = Thread.currentThread().getContextClassLoader();

        URL url1 = cls.getResource(packageName);
        System.out.println("URL = " + url1);
        System.out.println("URI = " + url1.toURI());

        parseAndLoad(url1);
    }

    /**
     * 通过url对象获取Class对象，并将其导入。
     * @param url
     */
    private static List<String> parseAndLoad(URL url) throws URISyntaxException, IOException {

        List<String> list = new ArrayList<>();

        //在idea运行时是file:开头的前缀，而以jar运行则是jar:开头的前缀。

        //假设先处理file
        Path path = Paths.get(url.toURI());

        Stream<Path> stream = Files.list(path);
        //遍历得到的文件带有.class。
        stream.forEach(p1 -> System.out.println("文件：" + p1.getFileName()));

        //TODO 遍历jar文件内的内容，需要java.net.JarURLConnection对象，JarFile等对象 JarEntry。
        //使用第三方jar文件即可模拟。

        return null;
    }
}
