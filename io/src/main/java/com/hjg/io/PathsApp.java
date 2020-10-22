package com.hjg.io;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/11
 */
public class PathsApp {

    public static void main(String[] args) {
        String data = "order.txt";

        System.out.println(PathsApp.class.getClassLoader().getResource(data).getFile());

        System.out.println(PathsApp.class.getClassLoader().getResource(data).getPath());
    }
}
