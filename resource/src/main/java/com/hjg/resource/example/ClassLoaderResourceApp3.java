package com.hjg.resource.example;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoaderResourceApp3 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String packageName = "com/fasterxml/jackson/databind/type";

        ClassLoader cls = Thread.currentThread().getContextClassLoader();
        URL url = cls.getResource(packageName);

        System.out.println("URL = " + url);
        System.out.println("URI = " + url.toURI());
        System.out.println("File = " + url.getFile());

        //是以jar:file:开头，不能再用Paths和Files，因为这2是用于文件的。
        /*Path path = Paths.get(url.getFile());
        Files.list(path).forEach(e -> System.out.println("文件：" + e.getFileName()));*/

        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();

        Enumeration<JarEntry> entries = jarFile.entries();
        while(entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if(jarEntry.isDirectory()) {
                System.out.println("Directory = " + jarEntry.getName());
            } else {
                System.out.println(jarEntry.getName());
            }
        }

        //Jar类同与Zip，同样有ZipInputStream ZipFile ZipEntry等类分别用于读写jar和zip文件。
    }
}
