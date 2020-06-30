package com.hjg.proxy.customloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class DelegationClassLoader extends ClassLoader{

    private String classpath;

    public DelegationClassLoader(String classpath, ClassLoader parent) {
        super(parent);
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String classFilePath = this.classpath + name.replace(".", "/") + ".class";
        try(InputStream is = new FileInputStream(classFilePath)) {

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                output.write(buffer, 0, n);
            }

            byte[] classBytes = output.toByteArray();

            return super.defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        sun.applet.Main main1 = new sun.applet.Main();

        String classpath = "proxy-classloader/target/classes/";
        DelegationClassLoader cl = new DelegationClassLoader(classpath, ClassLoader.getSystemClassLoader());

        String name = "sun.applet.Main";
        Class<?> clz = cl.loadClass(name);
        Object main2 = clz.newInstance();

        System.out.println("main1 class: " + main1.getClass());
        System.out.println("main2 class: " + main2.getClass());
        System.out.println("main1 classloader: " + main1.getClass().getClassLoader());
        System.out.println("main2 classloader: " + main2.getClass().getClassLoader());

        //打印自定义的ClassLoader以及它的父级ClassLoader
        ClassLoader itrCl = cl;
        while (itrCl != null) {
            System.out.println(itrCl);
            itrCl = itrCl.getParent();
        }
    }
}
