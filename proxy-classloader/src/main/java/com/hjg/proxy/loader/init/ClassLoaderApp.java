package com.hjg.proxy.loader.init;

public class ClassLoaderApp {

    static {
        System.out.println("开始初始化");
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Class.forName("com.hjg.proxy.loader.init.StaticC1");
        //Class.forName("com.hjg.proxy.loader.init.StaticC1");

        ClassLoader cls = Thread.currentThread().getContextClassLoader();
        //cl.newInstance之前，调用cls.loadClass时，并未执行静态代码块。
        Class cl = cls.loadClass("com.hjg.proxy.loader.init.StaticC1");
        Object obj = cl.newInstance();

        System.out.println("程序结束");
    }
}
